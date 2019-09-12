/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.test.logic;

import co.edu.uniandes.csw.dispositivos.ejb.VendedorLogic;
import co.edu.uniandes.csw.dispositivos.entities.VendedorEntity;
import co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException;
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
public class VendedorLogicTest 
{
    @PersistenceContext(unitName="dispositivosPU")
    protected EntityManager vrm;
    
    @Deployment
    public static JavaArchive createDeployment() 
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(VendedorEntity.class.getPackage())
                .addPackage(VendedorLogic.class.getPackage())
                .addPackage(VendedorPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    private PodamFactory vrlfactory = new PodamFactoryImpl(); 
    
    @Inject
    private VendedorLogic vrlogic;
    
    @Test
    public void createVendedorTest() throws BusinessLogicException 
    {
        VendedorEntity vendedor = vrlfactory.manufacturePojo(VendedorEntity.class);
        VendedorEntity obtainedvr = vrlogic.createVendedor(vendedor);
        Assert.assertNotNull(obtainedvr);
    }
    
    @Test(expected=BusinessLogicException.class)
    public void createNullVendedorTest() throws BusinessLogicException
    {
        
    }
    
    @Test
    public void findVendedorTest() throws BusinessLogicException
    {
        
    }
    
    @Test
    public void findAllVendedoresTest() throws BusinessLogicException
    {
        
    }
    
    @Test
    public void updateVendedorTest() throws BusinessLogicException
    {
        
    }
    
    @Test
    public void deleteVendedorTest() throws BusinessLogicException
    {
        
    }
}
