/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.test.persistence;

import co.edu.uniandes.csw.dispositivos.entities.MediaEntity;
import co.edu.uniandes.csw.dispositivos.persistence.MediaPersistence;
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
public class MediaPersistenceTest {

    @Inject
    private MediaPersistence cp;

    @PersistenceContext
    private EntityManager em;

    private List<MediaEntity> data = new ArrayList<>();
    
    @Inject
    UserTransaction utx;

    @Deployment
    public static JavaArchive deployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(MediaEntity.class.getPackage())
                .addPackage(MediaPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");

    }

    private void clearData() {
        em.createQuery("delete from MediaEntity").executeUpdate();
    }

    /**
     *
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            MediaEntity entity = factory.manufacturePojo(MediaEntity.class);
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
    public void createMediaTest() {
        PodamFactory factory = new PodamFactoryImpl();
        MediaEntity newEntity = factory.manufacturePojo(MediaEntity.class);
        MediaEntity ce = cp.create(newEntity);

        Assert.assertNotNull(ce);
        MediaEntity entity = em.find(MediaEntity.class, ce.getId());
        Assert.assertEquals(newEntity.getLink(), entity.getLink());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getMarca(), entity.getMarca());
    }
    
    @Test
    public void getMediaTest() {
        MediaEntity entity = data.get(0);
        MediaEntity newEntity = cp.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getLink(), newEntity.getLink());
        Assert.assertEquals(entity.getMarca(), newEntity.getMarca());
        Assert.assertEquals(entity.getId(), newEntity.getId());
    }
    
    @Test
    public void deleteMediaTest() {
        MediaEntity entity = data.get(0);
        cp.delete(entity.getId());
        MediaEntity deleted = em.find(MediaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    @Test
    public void updateMediaTest() {
        MediaEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        MediaEntity newEntity = factory.manufacturePojo(MediaEntity.class);

        newEntity.setId(entity.getId());

        cp.update(newEntity);

        MediaEntity resp = em.find(MediaEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getLink(), resp.getLink());
        Assert.assertEquals(newEntity.getId(), resp.getId());
    }
}
