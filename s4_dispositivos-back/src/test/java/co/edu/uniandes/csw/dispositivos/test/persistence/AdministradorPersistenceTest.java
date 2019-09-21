package co.edu.uniandes.csw.dispositivos.test.persistence;

import co.edu.uniandes.csw.dispositivos.entities.AdministradorEntity;
import co.edu.uniandes.csw.dispositivos.persistence.AdministradorPersistence;
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
 * Test de persistencia de la clase AdminstradorEntity
 * @author Dianis Caro
 */
@RunWith(Arquillian.class)
public class AdministradorPersistenceTest {

    /**
     * Inyeccion de la dependencia a la clase ComprobanteDePagoPersistence
     */
    @Inject
    private AdministradorPersistence mp;
    /**
     * Contexto de Persistencia que se va a utilizar para acceder a la BD
     */
    @PersistenceContext(unitName = "dispositivosPU")
    private EntityManager em;

    @Inject
    UserTransaction utx;

    private List<AdministradorEntity> data = new ArrayList<>();
    /**
     * Construye el despliegue de la prueba a realizar
     * @return jar, es decir JavaArchive.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(AdministradorEntity.class.getPackage())
                .addPackage(AdministradorPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    /**
     * Constructor de la clase
     */
    public AdministradorPersistenceTest() {
    }
    /**
     * Prueba para crear un administrador
     */
    @Test
    public void testCreate() {
        PodamFactory factory = new PodamFactoryImpl();
        AdministradorEntity newEntity = factory.manufacturePojo(AdministradorEntity.class);
        AdministradorEntity result = mp.create(newEntity);
        Assert.assertNotNull(result);

        AdministradorEntity entity = em.find(AdministradorEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getUsuario(), entity.getUsuario());
        Assert.assertEquals(newEntity.getContrasena(), entity.getContrasena());
        Assert.assertEquals(newEntity.getCorreo(), entity.getCorreo());
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
                AdministradorEntity entity = factory.manufacturePojo(AdministradorEntity.class);
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
     * Prueba para obtener todos los administradores
     */
    @Test
    public void testFindAll() {
        List<AdministradorEntity> list = mp.findAll();
        System.out.println(list.size() + "   hhhhhh ");
        Assert.assertEquals(data.size(), list.size());
        for (AdministradorEntity ent : list) {
            boolean found = false;
            for (AdministradorEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    /**
     * Prueba para consultar un administrador
     */
    @Test
    public void testFind() {
        AdministradorEntity entity = data.get(0);
        AdministradorEntity newEntity = mp.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getUsuario(), entity.getUsuario());
        Assert.assertEquals(newEntity.getContrasena(), entity.getContrasena());
        Assert.assertEquals(newEntity.getCorreo(), entity.getCorreo());
    }
    /**
     * Prueba para actualizar un administrador
     */
    @Test
    public void testUpdate() {
        AdministradorEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        AdministradorEntity newEntity = factory.manufacturePojo(AdministradorEntity.class);

        newEntity.setId(entity.getId());

        mp.update(newEntity);

        AdministradorEntity resp = em.find(AdministradorEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getId(), resp.getId());
        Assert.assertEquals(newEntity.getUsuario(), resp.getUsuario());
        Assert.assertEquals(newEntity.getContrasena(), resp.getContrasena());
        Assert.assertEquals(newEntity.getCorreo(), resp.getCorreo());
    }
    /**
     * Prueba para eliminar un administrador
     */
    @Test
    public void testDelete() {
        AdministradorEntity entity = data.get(0);
        mp.delete(entity.getId());
        AdministradorEntity deleted = em.find(AdministradorEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    /**
     * Prueba del constructor Admin
     */
    @Test
    public void testConstructorAdmin() {
        AdministradorEntity newEntity = new AdministradorEntity("usuarioPrueba", "Hola1234","correo@uniandes.edu.co");

        Assert.assertEquals("usuarioPrueba", newEntity.getUsuario());
        Assert.assertEquals("Hola1234", newEntity.getContrasena());
        Assert.assertEquals("correo@uniandes.edu.co", newEntity.getCorreo());
    }
    /**
     * Prueba para consultar un administrador por usuario
     */
    @Test
    public void testFindByUser() 
    {
        AdministradorEntity entity = data.get(0);
        AdministradorEntity newEntity = mp.findByUser(entity.getUsuario());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getUsuario(), entity.getUsuario());
        Assert.assertEquals(newEntity.getContrasena(), entity.getContrasena());
        Assert.assertEquals(newEntity.getCorreo(), entity.getCorreo());
    }
    /**
     * Prueba para consultar un administrador por correo
     */
    @Test
    public void testFindByEmail() 
    {
        AdministradorEntity entity = data.get(0);
        AdministradorEntity newEntity = mp.findByEmail(entity.getCorreo());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getUsuario(), entity.getUsuario());
        Assert.assertEquals(newEntity.getContrasena(), entity.getContrasena());
        Assert.assertEquals(newEntity.getCorreo(), entity.getCorreo());
    }
}