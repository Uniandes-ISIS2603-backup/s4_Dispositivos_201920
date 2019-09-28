/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.test.persistence;

import co.edu.uniandes.csw.dispositivos.entities.CalificacionEntity;
import co.edu.uniandes.csw.dispositivos.persistence.CalificacionPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.UserTransaction;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
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
 *
 * @author Javier Peniche
 */
@RunWith(Arquillian.class)
public class CalificacionPersistenceTest {

    @Inject
    private CalificacionPersistence cp;

    @PersistenceContext
    private EntityManager em;

    private List<CalificacionEntity> data = new ArrayList<>();

    @Inject
    UserTransaction utx;

    @Deployment
    public static JavaArchive deployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CalificacionEntity.class.getPackage())
                .addPackage(CalificacionPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");

    }

    private void clearData() {
        em.createQuery("delete from CalificacionEntity").executeUpdate();
    }

    /**
     *
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {

            CalificacionEntity entity = factory.manufacturePojo(CalificacionEntity.class);

            em.persist(entity);

            data.add(entity);
        }
    }

    /**
     *
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
     *
     */
    @Test
    public void createCalificacionTest() {
        PodamFactory factory = new PodamFactoryImpl();
        CalificacionEntity newEntity = factory.manufacturePojo(CalificacionEntity.class);
        CalificacionEntity ce = cp.create(newEntity);

        Assert.assertNotNull(ce);
        CalificacionEntity entity = em.find(CalificacionEntity.class, ce.getId());

        Assert.assertArrayEquals(newEntity.getComentarios(), entity.getComentarios());

        Assert.assertEquals(newEntity.getCalificacionNumerica(), entity.getCalificacionNumerica());
    }

    /**
     *
     */
    @Test
    public void getCalificacionTest() {
        List<CalificacionEntity> list = cp.findAll();
        Assert.assertEquals(list.size(), data.size());
        for (CalificacionEntity ent : list) {
            boolean found = false;
            for (CalificacionEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    @Test
    public void findCalificacionTest() {
        CalificacionEntity entity = data.get(0);
        CalificacionEntity newEntity = cp.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getCalificacionNumerica(), newEntity.getCalificacionNumerica());
        Assert.assertEquals(entity.getComentarios(), newEntity.getComentarios());

    }

    /**
     *
     */
    @Test
    public void updateCalificacionTest() {
        CalificacionEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        CalificacionEntity newEntity = factory.manufacturePojo(CalificacionEntity.class);

        newEntity.setId(entity.getId());

        cp.update(newEntity);

        CalificacionEntity resp = em.find(CalificacionEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getCalificacionNumerica(), resp.getCalificacionNumerica());
    }

    /**
     *
     */
    @Test
    public void deleteCalificacionTest() {
        CalificacionEntity entity = data.get(0);
        cp.delete(entity.getId());
        CalificacionEntity deleted = em.find(CalificacionEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    @Test
    public void findCalificacionByCalificacionTest() {
        CalificacionEntity entity = data.get(0);
        CalificacionEntity newEntity = cp.findByCalificacion(entity.getCalificacionNumerica());
        Assert.assertNotNull(newEntity);
        Assert.assertArrayEquals(entity.getComentarios(), newEntity.getComentarios());

        newEntity = cp.findByCalificacion(null);
        Assert.assertNull(newEntity);
    }

}
