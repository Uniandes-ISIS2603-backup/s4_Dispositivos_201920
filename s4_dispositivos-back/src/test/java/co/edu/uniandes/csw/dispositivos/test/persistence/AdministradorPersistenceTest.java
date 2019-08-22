
package co.edu.uniandes.csw.dispositivos.test.persistence;

import co.edu.uniandes.csw.dispositivos.entities.AdministradorEntity;
import co.edu.uniandes.csw.dispositivos.persistence.AdministradorPersistence;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
/**
 *Test de persistencia de la clase AdminstradorEntity
 */
@RunWith(Arquillian.class)
public class AdministradorPersistenceTest 
{
    /**
     * Inyeccion de la dependencia a la clase AdministradorPersistence
     */
    @Inject
    private AdministradorPersistence persistence;
    /**
     * Contexto de Persistencia que se va a utilizar para acceder a la BD
     */
    @PersistenceContext
    private EntityManager em;
    /**
     * Construye el despliegue de la prueba a realizar
     * @return jar, es decir JavaArchive. El jar contiene las clases de XYZ, el descriptor de la
     * base de datos y el archivo beans.xml para resolver la inyección de
     * dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() 
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(AdministradorEntity.class.getPackage())
                .addPackage(AdministradorPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
   }
   /**
    * Constructor de la clase
    */
   public AdministradorPersistenceTest()
   {
   }
   /**
     * Prueba para crear un administrador
     */
   @Test
    public void testCreate()
    {
        PodamFactory factory = new PodamFactoryImpl();
        AdministradorEntity admin = factory.manufacturePojo(AdministradorEntity.class);
        AdministradorEntity result = persistence.create(admin);
        Assert.assertNotNull(result);
        AdministradorEntity entity = em.find(AdministradorEntity.class, result.getId());
        Assert.assertEquals(admin.getUsuario(), entity.getUsuario());
        Assert.assertEquals(admin.getId(), entity.getId());
        Assert.assertEquals(admin.getContrasena(), entity.getContrasena());
    }
    /**
     * Prueba para encontrar un administrador
     */
    @Test
    public void testFind()
    {
        PodamFactory factory = new PodamFactoryImpl();
        AdministradorEntity nuevoAdmin = factory.manufacturePojo(AdministradorEntity.class);
        AdministradorEntity entity = persistence.create(nuevoAdmin);
        
        AdministradorEntity newEntity = persistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getUsuario(), newEntity.getUsuario());
        Assert.assertEquals(entity.getId(), newEntity.getId());
        Assert.assertEquals(entity.getContrasena(), newEntity.getContrasena());
    }
    /**
     * Prueba para actualizar un administrador
     */
    @Test
    public void testUpdate() 
    {
        PodamFactory factory = new PodamFactoryImpl();
        AdministradorEntity nuevoAdmin = factory.manufacturePojo(AdministradorEntity.class);
        AdministradorEntity entity = persistence.create(nuevoAdmin);
        
        AdministradorEntity newEntity = factory.manufacturePojo(AdministradorEntity.class);

        newEntity.setId(entity.getId());

        persistence.update(newEntity);

        AdministradorEntity resp = em.find(AdministradorEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getId(), resp.getId());
        Assert.assertEquals(newEntity.getUsuario(), resp.getUsuario());
        Assert.assertEquals(newEntity.getContrasena(), resp.getContrasena());
    }
    /**
     * Prueba para eliminar un administrador
     */
    @Test
    public void testDelete() 
    {
        PodamFactory factory = new PodamFactoryImpl();
        AdministradorEntity nuevoAdmin = factory.manufacturePojo(AdministradorEntity.class);
        AdministradorEntity entity = persistence.create(nuevoAdmin);
        
        persistence.delete(entity.getId());
        AdministradorEntity deleted = em.find(AdministradorEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
