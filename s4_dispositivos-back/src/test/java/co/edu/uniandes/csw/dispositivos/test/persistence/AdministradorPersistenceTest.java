
package co.edu.uniandes.csw.dispositivos.test.persistence;

import co.edu.uniandes.csw.dispositivos.entities.AdministradorEntity;
import co.edu.uniandes.csw.dispositivos.persistence.AdministradorPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import static junit.framework.Assert.fail;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

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
     * Variable para marcar las transacciones del em cuando se crean/borran datos para las pruebas
     */
    @Inject
    UserTransaction utx;
     /**
     * Arreglo que contendrá el conjunto de datos de prueba
     */
    private List<AdministradorEntity> data = new ArrayList<AdministradorEntity>();
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
    
    @Test
    public void testFind() throws Exception
    {
        fail("testFind");
    }
   
    @Test
    public void testCreate() throws Exception
    {
        fail("testCreate");
    }
    
}
