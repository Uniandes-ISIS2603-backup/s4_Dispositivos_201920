/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.test.persistence;

import co.edu.uniandes.csw.dispositivos.entities.DispositivoEntity;
import co.edu.uniandes.csw.dispositivos.persistence.DispositivoPersistence;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.eclipse.persistence.internal.jpa.EntityManagerImpl;
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
 *
 * @author Santiago Fajardo
 */
@RunWith(Arquillian.class)
public class DispositivoPersistenceTest {
    
    @PersistenceContext(unitName = "dispositivosPU")
    protected EntityManager em; 
    
    
    @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(DispositivoEntity.class)
                .addClass(DispositivoPersistence.class)
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml"); 
    }
    
    @Inject
    DispositivoPersistence dp;
    
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
    
   
}
