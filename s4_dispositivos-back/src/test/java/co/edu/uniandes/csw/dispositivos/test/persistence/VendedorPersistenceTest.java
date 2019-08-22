/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.test.persistence;

import co.edu.uniandes.csw.dispositivos.entities.VendedorEntity;
import co.edu.uniandes.csw.dispositivos.persistence.VendedorPersistence;
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
public class VendedorPersistenceTest 
{
    @PersistenceContext
    protected EntityManager vrm;
    
    @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(VendedorEntity.class)
                .addClass(VendedorPersistence.class)
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    @Inject
    VendedorPersistence vrp; 
    
    @Test
    public void createVendedorTest()
    {
        PodamFactory vrfactory = new PodamFactoryImpl();
        VendedorEntity vendedor = vrfactory.manufacturePojo(VendedorEntity.class);
        VendedorEntity obtainedvr = vrp.create(vendedor);
        
        Assert.assertNotNull(obtainedvr);
        VendedorEntity vrentity = vrm.find(VendedorEntity.class, obtainedvr.getId());
        Assert.assertEquals(vendedor.getCedula(), vrentity.getCedula());
    }
}
