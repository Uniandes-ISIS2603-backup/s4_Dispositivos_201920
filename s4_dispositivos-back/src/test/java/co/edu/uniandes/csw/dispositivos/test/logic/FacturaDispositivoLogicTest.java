package co.edu.uniandes.csw.dispositivos.test.logic;

import co.edu.uniandes.csw.dispositivos.ejb.FacturaDispositivoLogic;
import co.edu.uniandes.csw.dispositivos.ejb.FacturaLogic;
import co.edu.uniandes.csw.dispositivos.entities.ClienteEntity;
import co.edu.uniandes.csw.dispositivos.entities.FacturaEntity;
import co.edu.uniandes.csw.dispositivos.entities.DispositivoEntity;
import co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.dispositivos.persistence.FacturaPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * Pruebas de logica de la relacion Factura - Dispositivos
 *
 * @author Carlos Salazar
 */
@RunWith(Arquillian.class)
public class FacturaDispositivoLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private FacturaLogic facturaLogic;

    @Inject
    private FacturaDispositivoLogic facturaDispositivoLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private ClienteEntity cliente;

    private List<FacturaEntity> data = new ArrayList<FacturaEntity>();

    private List<DispositivoEntity> dispositivosData = new ArrayList();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(FacturaEntity.class.getPackage())
                .addPackage(FacturaLogic.class.getPackage())
                .addPackage(FacturaPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * Configuración inicial de la prueba.
     */
    @Before
    public void configTest() {
        try {
            utx.begin();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        em.createQuery("delete from FacturaEntity").executeUpdate();
        em.createQuery("delete from DispositivoEntity").executeUpdate();
        em.createQuery("delete from ClienteEntity").executeUpdate();

    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() throws BusinessLogicException {

        cliente = factory.manufacturePojo(ClienteEntity.class);
        em.persist(cliente);
        for (int i = 0; i < 3; i++) {
            DispositivoEntity dispositivos = factory.manufacturePojo(DispositivoEntity.class);
            em.persist(dispositivos);
            dispositivosData.add(dispositivos);
        }
        for (int i = 0; i < 3; i++) {
            FacturaEntity entity = factory.manufacturePojo(FacturaEntity.class);
            List<DispositivoEntity> dispositivos = new ArrayList<DispositivoEntity>();
            dispositivos.add(dispositivosData.get(i));
            entity.setDispositivos(dispositivos);
            facturaLogic.createFactura(cliente.getId(), entity);
            data.add(entity);

            if (i == 0) {
                List<FacturaEntity> facturas = new ArrayList<FacturaEntity>();
                facturas.add(entity);
                cliente.setFacturas(facturas);
            }
        }
    }

    /**
     * Prueba para asociar un Dispositivos existente a un Factura.
     */
    @Test
    public void addDispositivosTest() {
        FacturaEntity entity = data.get(0);
        DispositivoEntity dispositivo = dispositivosData.get(1);
        DispositivoEntity response = facturaDispositivoLogic.addDispositivo(dispositivo.getId(), entity.getId(), cliente.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(dispositivo.getId(), response.getId());
    }

    /**
     * Prueba para obtener una colección de instancias de Dispositivos asociadas
     * a una instancia Factura.
     */
    @Test
    public void getDispositivosTest() {
        List<DispositivoEntity> list = facturaDispositivoLogic.getDispositivos(data.get(0).getId(), cliente.getId());

        Assert.assertEquals(1, list.size());
    }

    /**
     * Prueba para obtener una instancia de Dispositivos asociada a una
     * instancia Factura.
     *
     * @throws BusinessLogicException
     */
    @Test
    public void getDispositivoTest() throws BusinessLogicException {
        FacturaEntity facturaEntity = data.get(0);
        DispositivoEntity dispositivoEntity = dispositivosData.get(0);
        DispositivoEntity response = facturaDispositivoLogic.getDispositivo(facturaEntity.getId(), dispositivoEntity.getId(), cliente.getId());

        Assert.assertEquals(dispositivoEntity.getId(), response.getId());
        Assert.assertEquals(dispositivoEntity.getCalificaciones(), response.getCalificaciones());
        Assert.assertEquals(dispositivoEntity.getCategoria(), response.getCategoria());
        Assert.assertEquals(dispositivoEntity.getMarca(), response.getMarca());
        Assert.assertEquals(dispositivoEntity.getModelo(), response.getModelo());
    }

    /**
     * Prueba para obtener una instancia de Dispositivos asociada a una
     * instancia Factura que no le pertenece.
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void getDispositivoNoAsociadoTest() throws BusinessLogicException {
        FacturaEntity entity = data.get(0);
        DispositivoEntity dispositivoEntity = dispositivosData.get(1);
        facturaDispositivoLogic.getDispositivo(entity.getId(), dispositivoEntity.getId(), cliente.getId());
    }
}
