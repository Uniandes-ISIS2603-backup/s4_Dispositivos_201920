/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.test.persistence;

import co.edu.uniandes.csw.dispositivos.entities.VentaEntity;
import co.edu.uniandes.csw.dispositivos.persistence.VentaPersistence;
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
 *
 * @author Zharet Bautista Montes
 */
@RunWith(Arquillian.class)
public class VentaPersistenceTest 
{
    @PersistenceContext
    protected EntityManager vam;
    
    @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(VentaEntity.class)
                .addClass(VentaPersistence.class)
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    @Inject
    VentaPersistence vap; 
    
    @Test
    public void createVentaTest()
    {
        PodamFactory vafactory = new PodamFactoryImpl();
        VentaEntity venta = vafactory.manufacturePojo(VentaEntity.class);
        VentaEntity obtainedva = vap.create(venta);
        
        Assert.assertNotNull(obtainedva);
        VentaEntity vaentity = vam.find(VentaEntity.class, obtainedva.getId());
        Assert.assertEquals(venta.getId(), vaentity.getId());
    }
}
