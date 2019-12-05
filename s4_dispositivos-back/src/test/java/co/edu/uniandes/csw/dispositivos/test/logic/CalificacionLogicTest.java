package co.edu.uniandes.csw.dispositivos.test.logic;

import co.edu.uniandes.csw.dispositivos.ejb.CalificacionLogic;
import co.edu.uniandes.csw.dispositivos.entities.CalificacionEntity;
import co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.dispositivos.persistence.CalificacionPersistence;
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

@RunWith(Arquillian.class)
public class CalificacionLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private CalificacionLogic calificacionLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<CalificacionEntity> data = new ArrayList<CalificacionEntity>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CalificacionEntity.class.getPackage())
                .addPackage(CalificacionLogic.class.getPackage())
                .addPackage(CalificacionPersistence.class.getPackage())
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
        em.createQuery("delete from CalificacionEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            CalificacionEntity entity = factory.manufacturePojo(CalificacionEntity.class);
            entity.setCalificacionNumerica(new Double(5));
            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear una calificacion.
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test
    public void createCalificacionTest() throws BusinessLogicException {
        CalificacionEntity newEntity = factory.manufacturePojo(CalificacionEntity.class);
        newEntity.setCalificacionNumerica(new Double(5));
        CalificacionEntity result = calificacionLogic.createCalificacion(newEntity);
        Assert.assertNotNull(result);
        CalificacionEntity entity = em.find(CalificacionEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getCalificacionNumerica(), entity.getCalificacionNumerica(), 0);
    }

    /**
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createCalificacionConNumeroMayorOMenor() throws BusinessLogicException {
        CalificacionEntity newEntity = factory.manufacturePojo(CalificacionEntity.class);
        newEntity.setCalificacionNumerica(new Double(-2));
        calificacionLogic.createCalificacion(newEntity);
    }

    /**
     * Prueba para consultar la lista de calificacion.
     */
    @Test
    public void getCalificacionesTest() {
        List<CalificacionEntity> list = calificacionLogic.getCalificaciones();
        Assert.assertEquals(data.size(), list.size());
        for (CalificacionEntity entity : list) {
            boolean found = false;
            for (CalificacionEntity entity2 : data) {
                if (entity.getId().equals(entity2.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar una calificacion.
     */
    @Test
    public void getCalificacionTest() {
        CalificacionEntity entity = data.get(0);
        CalificacionEntity resultEntity = calificacionLogic.getCalificacion(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getComentario(), resultEntity.getComentario());
        Assert.assertEquals(entity.getDispositivo(), resultEntity.getDispositivo());
    }

    /**
     * Prueba para eliminar una calificacion.
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test
    public void deleteCalificacionTest() throws BusinessLogicException {
        CalificacionEntity entity = data.get(1);
        calificacionLogic.deleteCalificacion(entity.getId());
        CalificacionEntity deleted = em.find(CalificacionEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
            /**
     * Prueba para actualizar una categoria.
     */
    @Test
    public void updateCalificacionTest() throws BusinessLogicException {
        CalificacionEntity entity = data.get(0);
        CalificacionEntity pojoEntity = factory.manufacturePojo(CalificacionEntity.class);
        pojoEntity.setId(entity.getId());
        calificacionLogic.updateCalificacion(pojoEntity.getId(), pojoEntity);
        CalificacionEntity resp = em.find(CalificacionEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getComentario(), resp.getComentario());
    }
}
