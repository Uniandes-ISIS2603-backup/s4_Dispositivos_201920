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
public class MediaPersistenceTest {
    @Inject
    private MediaPersistence mp;
    
    @PersistenceContext 
    private EntityManager em;
    
    private List<MediaEntity> data = new ArrayList<>();
    
    @Inject
    UserTransaction utx;
    
    @Deployment
    public static JavaArchive deployment(){
      return ShrinkWrap.create(JavaArchive.class)
         .addPackage(MediaEntity.class.getPackage())
              .addPackage(MediaPersistence.class.getPackage())
              .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
              .addAsManifestResource("META-INF/beans.xml", "beans.xml");
              
    }
    
    private void clearData() {
        em.createQuery("delete from MediaEntity").executeUpdate();
    }

    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {

            MediaEntity entity = factory.manufacturePojo(MediaEntity.class);

            em.persist(entity);

            data.add(entity);
        }
    }
    
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
    
    @Test
    public void createMediaTest(){
        PodamFactory factory= new PodamFactoryImpl();
        MediaEntity newEntity = factory.manufacturePojo(MediaEntity.class);
        MediaEntity ce= mp.create(newEntity);
        
        Assert.assertNotNull(ce);
        MediaEntity entity= em.find(MediaEntity.class, ce.getId());
        
        Assert.assertEquals(newEntity.getLinks(), entity.getLinks());
    }
    
    @Test
    public void getMediaTest() {
        List<MediaEntity> list = mp.findAll();
        Assert.assertEquals(list.size(),data.size());
        for (MediaEntity ent : list) {
            boolean found = false;
            for (MediaEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    @Test
    public void updateMediaTest() {
        MediaEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        MediaEntity newEntity = factory.manufacturePojo(MediaEntity.class);

        newEntity.setId(entity.getId());

        mp.update(newEntity);

        MediaEntity resp = em.find(MediaEntity.class, entity.getId());
        Assert.assertArrayEquals(newEntity.getLinks(), resp.getLinks());

    }
    
    /**
     * 
     */
    @Test
    public void deleteMediaTest() {
        MediaEntity entity = data.get(0);
        mp.delete(entity.getId());
        MediaEntity deleted = em.find(MediaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    /**
     * 
     */
    @Test
    public void equalsTest(){
        String[] links1 = {
            "e.com", "r.com"
        }; 
        
        String[] links2 = {
            "z.oom", "f.com"
        };
        
        MediaEntity m1 = new MediaEntity(links1); 
        MediaEntity m2 = new MediaEntity(links1);
        MediaEntity m3 = new MediaEntity(links2); 
        
        MediaEntity entity = mp.create(m1);
        
        Assert.assertNotNull(entity);
        
        Assert.assertArrayEquals(entity.getLinks(), m2.getLinks());
         Assert.assertThat(entity.getLinks(), IsNot.not(IsEqual.equalTo(m3.getLinks())));
    }
}
