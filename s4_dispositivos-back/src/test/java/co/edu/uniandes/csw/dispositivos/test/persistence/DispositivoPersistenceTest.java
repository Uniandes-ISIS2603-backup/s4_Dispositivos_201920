/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.test.persistence;

import co.edu.uniandes.csw.dispositivos.entities.DispositivoEntity;
import co.edu.uniandes.csw.dispositivos.persistence.DispositivoPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.eclipse.persistence.internal.jpa.EntityManagerImpl;
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
 * @author Santiago Fajardo
 */
@RunWith(Arquillian.class)
public class DispositivoPersistenceTest {
    
    @PersistenceContext(unitName = "dispositivosPU")
    protected EntityManager em; 
    
    /**
     * Constructor vacio 
     */
    public DispositivoPersistenceTest(){
        
    }
    
    @Inject
    UserTransaction utx;

    private List<DispositivoEntity> data =  new ArrayList<>(); 
    
    
    @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(DispositivoEntity.class)
                .addClass(DispositivoPersistence.class)
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml"); 
    }
    
     @Before
    public void configTest() 
    {   
        try 
        {
        utx.begin();
        em.joinTransaction();
        clearData();
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) 
        {
            DispositivoEntity entity = factory.manufacturePojo(DispositivoEntity.class);
            em.persist(entity);
            data.add(entity);
        }    
        } 
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    /**
     * Elimina todos los elementos de 
     * la BD antes de hacer el test
     */
    private void clearData() 
    {
        em.createQuery("delete from DispositivoEntity").executeUpdate();
    }
    
    @Inject
    DispositivoPersistence dp;
    
    /**
     * Prueba para create de DispositivoEntity
     */
    @Test
    public void createTest(){
        PodamFactory factory = new PodamFactoryImpl();
        DispositivoEntity dispositivo = factory.manufacturePojo(DispositivoEntity.class); 
        DispositivoEntity result = dp.create(dispositivo); 
        Assert.assertNotNull(result);
       
        DispositivoEntity entity = em.find(DispositivoEntity.class, result.getId()); 
        
        //Prueba crear el nombre y encontrarlo
        Assert.assertEquals(dispositivo.getNombre(), entity.getNombre());
        
        //Prueba crear modelo y encontrarlo
        Assert.assertEquals(dispositivo.getModelo(), entity.getModelo());
        
        //Prueba crear Descripcion y encontrarla
        Assert.assertEquals(dispositivo.getDescripcion(), dispositivo.getDescripcion());
        
        //Prueba crear Precio y encontrarlo
        Assert.assertEquals(dispositivo.getPrecio(), entity.getPrecio(), 0);
        
        //Prueba crear Precio de importacion y encontrarlo
        Assert.assertEquals(dispositivo.getPrecioImportacion(), entity.getPrecioImportacion(), 0);
        
        //Prueba crear desucento y encontrarlo
        Assert.assertEquals(dispositivo.getDescuento(), entity.getDescuento(), 0);
 
        //Prueba crear si esta en stcok y verifica
        Assert.assertEquals(dispositivo.isEnStock(), entity.isEnStock());
        
        //Prueba crea si esta en promocion y verifica
        Assert.assertEquals(dispositivo.isPromocion(), entity.isPromocion());        
    }
    
    /**
     * Prueba para find de DispositivoEntity
     */
    @Test
   public void testFind(){
       PodamFactory factory = new PodamFactoryImpl(); 
       DispositivoEntity dispositivo = factory.manufacturePojo(DispositivoEntity.class); 
       DispositivoEntity entity = dp.create(dispositivo); 
       
       DispositivoEntity newEntity = dp.find(entity.getId()); 
       Assert.assertNotNull(newEntity);
       
       //Prueba crear el nombre y encontrarlo
        Assert.assertEquals(dispositivo.getNombre(), newEntity.getNombre());
        
        //Prueba crear modelo y encontrarlo
        Assert.assertEquals(dispositivo.getModelo(), newEntity.getModelo());
        
        //Prueba crear Descripcion y encontrarla
        Assert.assertEquals(dispositivo.getDescripcion(), newEntity.getDescripcion());
        
        //Prueba crear Precio y encontrarlo
        Assert.assertEquals(dispositivo.getPrecio(), newEntity.getPrecio(), 0);
        
        //Prueba crear Precio de importacion y encontrarlo
        Assert.assertEquals(dispositivo.getPrecioImportacion(), newEntity.getPrecioImportacion(), 0);
        
        //Prueba crear desucento y encontrarlo
        Assert.assertEquals(dispositivo.getDescuento(), newEntity.getDescuento(), 0);
 
        //Prueba crear si esta en stcok y verifica
        Assert.assertEquals(dispositivo.isEnStock(), newEntity.isEnStock());
        
        //Prueba crea si esta en promocion y verifica
        Assert.assertEquals(dispositivo.isPromocion(), newEntity.isPromocion());          
   }
   
   @Test
   public void findAllTest(){
       List<DispositivoEntity> list = dp.findAll(); 
        System.out.println("Size:" + list.size());
        Assert.assertEquals(data.size(), list.size());
        for (DispositivoEntity ent : list) {
            boolean found = false;
            for (DispositivoEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
   }
   
   /**
    * Prueba para update de DispositivoEntity
    */
   @Test
   public void testUpdate(){
       PodamFactory factory = new PodamFactoryImpl(); 
       DispositivoEntity newDispositivo = factory.manufacturePojo(DispositivoEntity.class); 
       DispositivoEntity entity = dp.create(newDispositivo); 
       
       DispositivoEntity newEntity = factory.manufacturePojo(DispositivoEntity.class); 
       
       newEntity.setId(entity.getId());
       
       dp.update(newEntity); 
       
       DispositivoEntity resp = em.find(DispositivoEntity.class, entity.getId()); 
       
        //Prueba crear el nombre y encontrarlo
        Assert.assertEquals(newEntity.getNombre(), resp.getNombre());
        
        //Prueba crear modelo y encontrarlo
        Assert.assertEquals(newEntity.getModelo(), resp.getModelo());
        
        //Prueba crear Descripcion y encontrarla
        Assert.assertEquals(newEntity.getDescripcion(), resp.getDescripcion());
        
        //Prueba crear Precio y encontrarlo
        Assert.assertEquals(newEntity.getPrecio(), resp.getPrecio(), 0);
        
        //Prueba crear Precio de importacion y encontrarlo
        Assert.assertEquals(newEntity.getPrecioImportacion(), resp.getPrecioImportacion(), 0);
        
        //Prueba crear desucento y encontrarlo
        Assert.assertEquals(newEntity.getDescuento(), resp.getDescuento(), 0);
 
        //Prueba crear si esta en stcok y verifica
        Assert.assertEquals(newEntity.isEnStock(), resp.isEnStock());
        
        //Prueba crea si esta en promocion y verifica
        Assert.assertEquals(newEntity.isPromocion(), resp.isPromocion());   
   }
   
   /**
    * Prueba para delete de DispositivoEntity
    */
   @Test
   public void testDelete(){
       PodamFactory factory = new PodamFactoryImpl(); 
       DispositivoEntity newDispositivo = factory.manufacturePojo(DispositivoEntity.class); 
       DispositivoEntity entity = dp.create(newDispositivo); 
       
       dp.delete(entity.getId());
       DispositivoEntity deleted = em.find(DispositivoEntity.class, entity.getId()); 
       Assert.assertNull(deleted);
   }
   
   @Test 
   public void testEquals(){
       DispositivoEntity d1 = new DispositivoEntity("M", "A", "S", "r", 3, 2, 1, true, false); 
       DispositivoEntity d2 = new DispositivoEntity("M", "A", "S", "r", 3, 2, 1, true, false); 
       DispositivoEntity d3 = new DispositivoEntity("M", "A", "S", "r", 3, 4, 1, true, false);
       
       DispositivoEntity entity = dp.create(d1);
       
       Assert.assertTrue(entity.equals(d2));
       Assert.assertFalse(entity.equals(d3));
   }
}
