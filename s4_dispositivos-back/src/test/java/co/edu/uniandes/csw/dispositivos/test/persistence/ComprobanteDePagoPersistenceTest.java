
package co.edu.uniandes.csw.dispositivos.test.persistence;

import co.edu.uniandes.csw.dispositivos.entities.ComprobanteDePagoEntity;
import co.edu.uniandes.csw.dispositivos.persistence.ComprobanteDePagoPersistence;
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
public class ComprobanteDePagoPersistenceTest 
{
    /**
     * Inyeccion de la dependencia a la clase ComprobanteDePagoPersistence
     */
    @Inject
    private ComprobanteDePagoPersistence persistence;
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
                .addPackage(ComprobanteDePagoEntity.class.getPackage())
                .addPackage(ComprobanteDePagoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
   }
   /**
    * Constructor de la clase
    */
   public ComprobanteDePagoPersistenceTest()
   {
   }
   /**
     * Prueba para crear un administrador
     */
   @Test
    public void testCreate()
    {
        PodamFactory factory = new PodamFactoryImpl();
        ComprobanteDePagoEntity comprobante = factory.manufacturePojo(ComprobanteDePagoEntity.class);
        ComprobanteDePagoEntity result = persistence.create(comprobante);
        Assert.assertNotNull(result);
        ComprobanteDePagoEntity entity = em.find(ComprobanteDePagoEntity.class, result.getId());
        Assert.assertEquals(comprobante.getNumeroDeFactura(), entity.getNumeroDeFactura());
    }
    /**
     * Prueba para encontrar un administrador
     */
    @Test
    public void testFind()
    {
        PodamFactory factory = new PodamFactoryImpl();
        ComprobanteDePagoEntity nuevoComprobante = factory.manufacturePojo(ComprobanteDePagoEntity.class);
        ComprobanteDePagoEntity entity = persistence.create(nuevoComprobante);
        
        ComprobanteDePagoEntity newEntity = persistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNumeroDeFactura(), newEntity.getNumeroDeFactura());
        Assert.assertEquals(entity.getId(), newEntity.getId());
    }
    /**
     * Prueba para actualizar un administrador
     */
    @Test
    public void testUpdate() 
    {
        PodamFactory factory = new PodamFactoryImpl();
        ComprobanteDePagoEntity nuevoComprobante = factory.manufacturePojo(ComprobanteDePagoEntity.class);
        ComprobanteDePagoEntity entity = persistence.create(nuevoComprobante);
        
        ComprobanteDePagoEntity newEntity = factory.manufacturePojo(ComprobanteDePagoEntity.class);

        newEntity.setId(entity.getId());

        persistence.update(newEntity);

        ComprobanteDePagoEntity resp = em.find(ComprobanteDePagoEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getId(), resp.getId());
        Assert.assertEquals(newEntity.getNumeroDeFactura(), resp.getNumeroDeFactura());
    }
    /**
     * Prueba para eliminar un administrador
     */
    @Test
    public void testDelete() 
    {
        PodamFactory factory = new PodamFactoryImpl();
        ComprobanteDePagoEntity nuevoComprobante = factory.manufacturePojo(ComprobanteDePagoEntity.class);
        ComprobanteDePagoEntity entity = persistence.create(nuevoComprobante);
        
        persistence.delete(entity.getId());
        ComprobanteDePagoEntity deleted = em.find(ComprobanteDePagoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    } 
}
