package co.edu.uniandes.csw.dispositivos.test.logic;

import co.edu.uniandes.csw.dispositivos.ejb.ClienteComprobanteLogic;
import co.edu.uniandes.csw.dispositivos.ejb.ClienteLogic;
import co.edu.uniandes.csw.dispositivos.entities.ClienteEntity;
import co.edu.uniandes.csw.dispositivos.entities.ComprobanteDePagoEntity;
import co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.dispositivos.persistence.ClientePersistence;
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
 * Pruebas de logica de la relacion Cliente - Comprobantes
 *
 * @author Carlos Salazar
 */
@RunWith(Arquillian.class)
public class ClienteComprobanteLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private ClienteLogic clienteLogic;
    @Inject
    private ClienteComprobanteLogic clienteComprobanteLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<ClienteEntity> data = new ArrayList<ClienteEntity>();

    private List<ComprobanteDePagoEntity> comprobantesData = new ArrayList();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ClienteEntity.class.getPackage())
                .addPackage(ClienteLogic.class.getPackage())
                .addPackage(ClientePersistence.class.getPackage())
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
        em.createQuery("delete from ComprobanteDePagoEntity").executeUpdate();
        em.createQuery("delete from ClienteEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ComprobanteDePagoEntity comprobantes = factory.manufacturePojo(ComprobanteDePagoEntity.class);
            em.persist(comprobantes);
            comprobantesData.add(comprobantes);
        }
        for (int i = 0; i < 3; i++) {
            ClienteEntity entity = factory.manufacturePojo(ClienteEntity.class);
            em.persist(entity);
            data.add(entity);
            if (i == 0) {
                comprobantesData.get(i).setCliente(entity);
                List<ComprobanteDePagoEntity> add = new ArrayList<ComprobanteDePagoEntity>();
                add.add(comprobantesData.get(0));
                entity.setComprobantesRecibidos(add);
            }
        }
    }

    /**
     * Prueba para asociar un Comprobantes existente a un Cliente.
     */
    @Test
    public void addComprobantesTest() {
        ClienteEntity entity = data.get(0);
        ComprobanteDePagoEntity comprobanteEntity = comprobantesData.get(0);
        ComprobanteDePagoEntity response = clienteComprobanteLogic.addComprobante(comprobanteEntity.getId(), entity.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(comprobanteEntity.getId(), response.getId());
    }

    /**
     * Prueba para obtener una colección de instancias de Comprobantes asociadas
     * a una instancia Cliente.
     */
    @Test
    public void getComprobantesRecibidosTest() {
        List<ComprobanteDePagoEntity> list = clienteComprobanteLogic.getComprobantes(data.get(0).getId());

        Assert.assertEquals(1, list.size());
    }

    /**
     * Prueba para obtener una instancia de Comprobantes asociada a una
     * instancia Cliente.
     *
     * @throws BusinessLogicException
     */
    @Test
    public void getComprobanteTest() throws BusinessLogicException {
        ClienteEntity clienteEntity = data.get(0);
        ComprobanteDePagoEntity comprobanteEntity = comprobantesData.get(0);
        ComprobanteDePagoEntity response = clienteComprobanteLogic.getComprobante(clienteEntity.getId(), comprobanteEntity.getId());

        Assert.assertEquals(comprobanteEntity.getId(), response.getId());
        Assert.assertEquals(comprobanteEntity.getFechaDeFactura(), response.getFechaDeFactura());
        Assert.assertEquals(comprobanteEntity.getImpuestos(), response.getImpuestos());
        Assert.assertEquals(comprobanteEntity.getNumeroDeFactura(), response.getNumeroDeFactura());
        Assert.assertEquals(comprobanteEntity.getTotalDePago(), response.getTotalDePago());
        Assert.assertEquals(comprobanteEntity.getNumeroDeTarjeta(), response.getNumeroDeTarjeta());

    }

    /**
     * Prueba para obtener una instancia de Comprobantes asociada a una
     * instancia Cliente que no le pertenece.
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void getComprobanteNoAsociadoTest() throws BusinessLogicException {
        ClienteEntity entity = data.get(0);
        ComprobanteDePagoEntity comprobanteEntity = comprobantesData.get(1);
        clienteComprobanteLogic.getComprobante(entity.getId(), comprobanteEntity.getId());
    }

    /**
     * Prueba para remplazar las instancias de Comprobantes asociadas a una
     * instancia de Cliente.
     */
    @Test
    public void replaceComprobantesTest() {
        ClienteEntity entity = data.get(0);
        List<ComprobanteDePagoEntity> list = comprobantesData.subList(1, 3);
        clienteComprobanteLogic.replaceComprobantes(entity.getId(), list);

        entity = clienteLogic.getCliente(entity.getId());
        Assert.assertFalse(entity.getComprobantesRecibidos().contains(comprobantesData.get(0)));
        Assert.assertTrue(entity.getComprobantesRecibidos().contains(comprobantesData.get(1)));
        Assert.assertTrue(entity.getComprobantesRecibidos().contains(comprobantesData.get(2)));
    }
}
