package co.edu.uniandes.csw.dispositivos.test.persistence;

import co.edu.uniandes.csw.dispositivos.entities.ClienteEntity;
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
 * @author Dianis Caro
 */
@RunWith(Arquillian.class)
public class ComprobanteDePagoPersistenceTest {

    /**
     * Inyeccion de la dependencia a la clase ComprobanteDePagoPersistence
     */
    @Inject
    private ComprobanteDePagoPersistence persistence;
    /**
     * Contexto de Persistencia que se va a utilizar para acceder a la BD
     */
    @PersistenceContext(unitName = "dispositivosPU")
    private EntityManager em;

    @Inject
    UserTransaction utx;

    private List<ComprobanteDePagoEntity> data = new ArrayList<ComprobanteDePagoEntity>();
	
    private List<ClienteEntity> dataCliente = new ArrayList<ClienteEntity>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() 
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ComprobanteDePagoEntity.class.getPackage())
                .addPackage(ComprobanteDePagoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * Configuración inicial de la prueba
     */
    @Before
    public void configTest() {
        try {
            utx.begin();
            em.joinTransaction();
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
     * Limpia las tablas que están implicadas en la prueba
     */
    private void clearData() {
        em.createQuery("delete from ComprobanteDePagoEntity").executeUpdate();
        em.createQuery("delete from ClienteEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            ClienteEntity entity = factory.manufacturePojo(ClienteEntity.class);
            em.persist(entity);
            dataCliente.add(entity);
        }
        for (int k = 0; k < 3; k++)
        {
            ComprobanteDePagoEntity entity = factory.manufacturePojo(ComprobanteDePagoEntity.class);
            if (k == 0)
                entity.setCliente(dataCliente.get(0));
            
            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un ComprobanteDePago
     */
    @Test
    public void createComprobanteDePagoTest() 
    {
        PodamFactory factory = new PodamFactoryImpl();
        ComprobanteDePagoEntity newEntity = factory.manufacturePojo(ComprobanteDePagoEntity.class);
        ComprobanteDePagoEntity result = persistence.create(newEntity);

        Assert.assertNotNull(result);

        ComprobanteDePagoEntity entity = em.find(ComprobanteDePagoEntity.class, result.getId());

        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getFechaDeFactura(), entity.getFechaDeFactura());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getImpuestos(), entity.getImpuestos(), 0);
        Assert.assertEquals(newEntity.getNumeroDeFactura(), entity.getNumeroDeFactura());
        Assert.assertEquals(newEntity.getNumeroDeTarjeta(), entity.getNumeroDeTarjeta());
        Assert.assertEquals(newEntity.getTotalDePago(), entity.getTotalDePago());
    }

    /**
     * Prueba para consultar un comprobante de pago
     */
    @Test
    public void getComprobanteDePagoTest() 
    {
        ComprobanteDePagoEntity entity = data.get(0);
        ComprobanteDePagoEntity newEntity = persistence.find(dataCliente.get(0).getId(), entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getFechaDeFactura(), entity.getFechaDeFactura());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getImpuestos(), entity.getImpuestos(), 0);
        Assert.assertEquals(newEntity.getNumeroDeFactura(), entity.getNumeroDeFactura());
        Assert.assertEquals(newEntity.getNumeroDeTarjeta(), entity.getNumeroDeTarjeta());
        Assert.assertEquals(newEntity.getTotalDePago(), entity.getTotalDePago());
    }

    /**
     * Prueba para eliminar un comprobante de pago
     */
    @Test
    public void deleteReviewTest() {
        ComprobanteDePagoEntity entity = data.get(0);
        persistence.delete(entity.getId());
        ComprobanteDePagoEntity deleted = em.find(ComprobanteDePagoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar un comprobante de pago
     */
    @Test
    public void updateReviewTest() {
        ComprobanteDePagoEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        ComprobanteDePagoEntity newEntity = factory.manufacturePojo(ComprobanteDePagoEntity.class);

        newEntity.setId(entity.getId());

        persistence.update(newEntity);

        ComprobanteDePagoEntity resp = em.find(ComprobanteDePagoEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getId(), resp.getId());
        Assert.assertEquals(newEntity.getFechaDeFactura(), resp.getFechaDeFactura());
        Assert.assertEquals(newEntity.getId(), resp.getId());
        Assert.assertEquals(newEntity.getImpuestos(), resp.getImpuestos(), 0);
        Assert.assertEquals(newEntity.getNumeroDeFactura(), resp.getNumeroDeFactura());
        Assert.assertEquals(newEntity.getNumeroDeTarjeta(), resp.getNumeroDeTarjeta());
        Assert.assertEquals(newEntity.getTotalDePago(), resp.getTotalDePago());
    }

    /**
     * Prueba del constructor Comprobante
     */
    @Test
    public void testConstructorComprobante() {
        Date date = new Date();
        ComprobanteDePagoEntity newEntity = new ComprobanteDePagoEntity(123, 100.500, 13.456, "0", date, null);
        Assert.assertEquals(123, newEntity.getNumeroDeFactura(),0);
        Assert.assertEquals(13.456, newEntity.getImpuestos(), 0);
        Double num = (Double) 100.50;
        Assert.assertEquals(num, newEntity.getTotalDePago());
        Assert.assertEquals("0", newEntity.getNumeroDeTarjeta());
        Assert.assertEquals(date, newEntity.getFechaDeFactura());
        Assert.assertEquals(null, newEntity.getCliente());
    }
    /**
     * Prueba para consultar un comprobante de pago por número de factura
     */
    @Test
    public void testFindByNumFactura() 
    {
        ComprobanteDePagoEntity entity = data.get(0);
        ComprobanteDePagoEntity newEntity = persistence.findByNumFactura(entity.getNumeroDeFactura());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getFechaDeFactura(), entity.getFechaDeFactura());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getImpuestos(), entity.getImpuestos(), 0);
        Assert.assertEquals(newEntity.getNumeroDeFactura(), entity.getNumeroDeFactura());
        Assert.assertEquals(newEntity.getNumeroDeTarjeta(), entity.getNumeroDeTarjeta());
        Assert.assertEquals(newEntity.getTotalDePago(), entity.getTotalDePago());
    }
}