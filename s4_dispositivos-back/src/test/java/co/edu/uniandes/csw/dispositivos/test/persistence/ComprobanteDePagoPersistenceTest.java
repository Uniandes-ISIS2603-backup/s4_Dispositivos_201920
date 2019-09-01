package co.edu.uniandes.csw.dispositivos.test.persistence;

import co.edu.uniandes.csw.dispositivos.entities.ComprobanteDePagoEntity;
import co.edu.uniandes.csw.dispositivos.persistence.ComprobanteDePagoPersistence;
import java.util.ArrayList;
import java.util.Date;
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
 * Test de persistencia de la clase ComprobanteDePagoEntity
 */
@RunWith(Arquillian.class)
public class ComprobanteDePagoPersistenceTest {

    /**
     * Inyeccion de la dependencia a la clase ComprobanteDePagoPersistence
     */
    @Inject
    private ComprobanteDePagoPersistence mp;
    /**
     * Contexto de Persistencia que se va a utilizar para acceder a la BD
     */
    @PersistenceContext(unitName = "dispositivosPU")
    private EntityManager em;

    @Inject
    UserTransaction utx;

    private List<ComprobanteDePagoEntity> data = new ArrayList<>();

    /**
     * Construye el despliegue de la prueba a realizar
     *
     * @return jar, es decir JavaArchive.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ComprobanteDePagoEntity.class.getPackage())
                .addPackage(ComprobanteDePagoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * Constructor de la clase
     */
    public ComprobanteDePagoPersistenceTest() {
    }

    /**
     * Prueba para crear un comprobante de pago
     */
    @Test
    public void testCreate() {
        PodamFactory factory = new PodamFactoryImpl();
        ComprobanteDePagoEntity newEntity = factory.manufacturePojo(ComprobanteDePagoEntity.class);
        ComprobanteDePagoEntity result = mp.create(newEntity);
        Assert.assertNotNull(result);

        ComprobanteDePagoEntity entity = em.find(ComprobanteDePagoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getFechaDeFactura(), entity.getFechaDeFactura());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getImpuestos(), entity.getImpuestos(), 0);
        Assert.assertEquals(newEntity.getNumeroDeFactura(), entity.getNumeroDeFactura());
        Assert.assertEquals(newEntity.getNumeroDeTarjeta(), entity.getNumeroDeTarjeta());
        Assert.assertEquals(newEntity.getTotalDePago(), entity.getTotalDePago(), 0);

    }

    /**
     * Establece las configuraciones iniciales del test
     */
    @Before
    public void configTest() {
        try {
            utx.begin();
            em.joinTransaction();
            clearData();
            PodamFactory factory = new PodamFactoryImpl();
            for (int i = 0; i < 3; i++) {
                ComprobanteDePagoEntity entity = factory.manufacturePojo(ComprobanteDePagoEntity.class);
                em.persist(entity);
                data.add(entity);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Elimina todos los elementos de la BD antes de hacer el test
     */
    private void clearData() {
        em.createQuery("delete from AdministradorEntity").executeUpdate();
    }

    /**
     * Prueba para obtener todos los comprobantes de pago
     */
    @Test
    public void testFindAll() {
        List<ComprobanteDePagoEntity> list = mp.findAll();
        System.out.println(list.size() + "   hhhhhh ");
        Assert.assertEquals(data.size(), list.size());
        for (ComprobanteDePagoEntity ent : list) {
            boolean found = false;
            for (ComprobanteDePagoEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un comprobante de pago
     */
    @Test
    public void testFind() {
        ComprobanteDePagoEntity entity = data.get(0);
        ComprobanteDePagoEntity newEntity = mp.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getFechaDeFactura(), entity.getFechaDeFactura());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getImpuestos(), entity.getImpuestos(), 0);
        Assert.assertEquals(newEntity.getNumeroDeFactura(), entity.getNumeroDeFactura());
        Assert.assertEquals(newEntity.getNumeroDeTarjeta(), entity.getNumeroDeTarjeta());
        Assert.assertEquals(newEntity.getTotalDePago(), entity.getTotalDePago(), 0);
    }

    /**
     * Prueba para actualizar un comprobante de pago
     */
    @Test
    public void testUpdate() {
        ComprobanteDePagoEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        ComprobanteDePagoEntity newEntity = factory.manufacturePojo(ComprobanteDePagoEntity.class);

        newEntity.setId(entity.getId());

        mp.update(newEntity);

        ComprobanteDePagoEntity resp = em.find(ComprobanteDePagoEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getId(), resp.getId());
        Assert.assertEquals(newEntity.getFechaDeFactura(), resp.getFechaDeFactura());
        Assert.assertEquals(newEntity.getId(), resp.getId());
        Assert.assertEquals(newEntity.getImpuestos(), resp.getImpuestos(), 0);
        Assert.assertEquals(newEntity.getNumeroDeFactura(), resp.getNumeroDeFactura());
        Assert.assertEquals(newEntity.getNumeroDeTarjeta(), resp.getNumeroDeTarjeta());
        Assert.assertEquals(newEntity.getTotalDePago(), resp.getTotalDePago(), 0);
    }

    /**
     * Prueba para eliminar un comprobante de pago
     */
    @Test
    public void testDelete() {
        ComprobanteDePagoEntity entity = data.get(0);
        mp.delete(entity.getId());
        ComprobanteDePagoEntity deleted = em.find(ComprobanteDePagoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba del constructor Admin
     */
    @Test
    public void testConstructorAdmin() {
        Date date = new Date();
        ComprobanteDePagoEntity newEntity = new ComprobanteDePagoEntity(123, 100.500, 13.456, "0", date);

        Assert.assertEquals(123, newEntity.getNumeroDeFactura());
        Assert.assertEquals(13.456, newEntity.getImpuestos(), 0);
        Assert.assertEquals(100.500, newEntity.getTotalDePago(), 0);
        Assert.assertEquals("0", newEntity.getNumeroDeTarjeta());
        Assert.assertEquals(date, newEntity.getFechaDeFactura());
    }

}
