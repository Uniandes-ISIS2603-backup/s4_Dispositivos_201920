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

/**
 *
 * @author Zharet Bautista Montes
 */
@RunWith(Arquillian.class)
public class VendedorLogicTest 
{
    @PersistenceContext
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
    
    @Inject
    UserTransaction utxn;

    private final List<VendedorEntity> vrlist = new ArrayList<>();
    
    /**
     * Establece las configuraciones iniciales del test
     */
    @Before
    public void prepareTest() 
    {   
        try 
        {
            utxn.begin();
            vrm.joinTransaction();
            vrm.createQuery("delete from VendedorEntity").executeUpdate();
            PodamFactory vrfactory = new PodamFactoryImpl();
            for (int i = 0; i < 5; i++) 
            {
                VendedorEntity vendedor = vrfactory.manufacturePojo(VendedorEntity.class);
                vrm.persist(vendedor);
                vrlist.add(vendedor);
            }
            utxn.commit();
        } 
        catch (Exception e1) 
        {   
            e1.printStackTrace(); 
            try
            { utxn.rollback(); }
            catch(Exception e2)
            { e2.printStackTrace();  }
        }
    }
    
    @Test
    public void createVendedorTest() throws BusinessLogicException 
    {
        VendedorEntity vendedor = vrlfactory.manufacturePojo(VendedorEntity.class);
        VendedorEntity obtainedvr = vrlogic.createVendedor(vendedor);
        Assert.assertNotNull(obtainedvr);
    }
    
    @Test(expected=BusinessLogicException.class)
    public void createNullFirstnameVendedorTest() throws BusinessLogicException
    {
        VendedorEntity vendedor = vrlfactory.manufacturePojo(VendedorEntity.class);
        vendedor.setNombre(null);
        vrlogic.createVendedor(vendedor); 
    }
    
    @Test(expected=BusinessLogicException.class)
    public void createNullLastnameVendedorTest() throws BusinessLogicException
    {
        VendedorEntity vendedor = vrlfactory.manufacturePojo(VendedorEntity.class);
        vendedor.setApellido(null);
        vrlogic.createVendedor(vendedor); 
    }
    
    @Test(expected=BusinessLogicException.class)
    public void createNullUserVendedorTest() throws BusinessLogicException
    {
        VendedorEntity vendedor = vrlfactory.manufacturePojo(VendedorEntity.class);
        vendedor.setUsuario(null);
        vrlogic.createVendedor(vendedor); 
    }
    
    @Test(expected=BusinessLogicException.class)
    public void createNullPasswordVendedorTest() throws BusinessLogicException
    {
        VendedorEntity vendedor = vrlfactory.manufacturePojo(VendedorEntity.class);
        vendedor.setContrasena(null);
        vrlogic.createVendedor(vendedor); 
    }
    
    @Test(expected=BusinessLogicException.class)
    public void createNullEmailVendedorTest() throws BusinessLogicException
    {
        VendedorEntity vendedor = vrlfactory.manufacturePojo(VendedorEntity.class);
        vendedor.setCorreoElectronico(null);
        vrlogic.createVendedor(vendedor); 
    }
    
    @Test
    public void findVendedorTest() throws BusinessLogicException
    {
        VendedorEntity ref = vrlist.get(0), block = vrlogic.findVendedor(ref.getId());
        Assert.assertNotNull(block);
        Assert.assertEquals(block.getId(),ref.getId());
        Assert.assertEquals(block.getApellido(),ref.getApellido());
        Assert.assertEquals(block.getNombre(), ref.getNombre());
        Assert.assertEquals(block.getCorreoElectronico(), ref.getCorreoElectronico());
        Assert.assertEquals(block.getContrasena(), ref.getContrasena());
        Assert.assertEquals(block.getUsuario(), ref.getUsuario());
        Assert.assertEquals(block.getCelular(), ref.getCelular(), 0);        
        Assert.assertEquals(block.getCedula(), ref.getCedula(), 0);
    }
    
    @Test
    public void findAllVendedoresTest() throws BusinessLogicException
    {
        List<VendedorEntity> allgotten = vrlogic.findAllVendedores();
        Assert.assertEquals(allgotten.size(), vrlist.size());
        for (VendedorEntity vrblock : allgotten) 
        {
            boolean ticked = false;
            for (VendedorEntity vrref : vrlist)
                if (vrblock.getId().equals(vrref.getId()))
                    ticked = true;
            Assert.assertTrue(ticked);
        }
    }
    
    @Test
    public void updateVendedorTest() throws BusinessLogicException
    {
        VendedorEntity vendedor = vrlist.get(0);
        VendedorEntity updating = vrlfactory.manufacturePojo(VendedorEntity.class);
        updating.setId(vendedor.getId());
        vrlogic.updateVendedor(updating);
        VendedorEntity updated = vrm.find(VendedorEntity.class, vendedor.getId());
        Assert.assertEquals(updating.getId(), updated.getId());
        Assert.assertEquals(updating.getApellido(), updated.getApellido());
        Assert.assertEquals(updating.getNombre(), updated.getNombre());
        Assert.assertEquals(updating.getCorreoElectronico(), updated.getCorreoElectronico());
        Assert.assertEquals(updating.getContrasena(), updated.getContrasena());
        Assert.assertEquals(updating.getUsuario(), updated.getUsuario());
        Assert.assertEquals(updating.getCelular(), updated.getCelular(),0);        
        Assert.assertEquals(updating.getCedula(), updated.getCedula(),0);
    }
    
    @Test
    public void deleteVendedorTest() throws BusinessLogicException
    {
        VendedorEntity vrentity = vrlist.get(0); 
        vrlogic.deleteVendedor(vrentity.getId());
        VendedorEntity gonevr = vrm.find(VendedorEntity.class, vrentity.getId()); 
        Assert.assertNull(gonevr);
    }
}