/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.test.persistence;

import co.edu.uniandes.csw.dispositivos.entities.VendedorEntity;
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
public class VendedorPersistenceTest 
{
    @PersistenceContext(unitName="dispositivosPU")
    private EntityManager vrm;

    @Deployment
    public static JavaArchive createDeployment() 
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(VendedorEntity.class.getPackage())
                .addPackage(VendedorPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    @Inject
    private VendedorPersistence vrp;
    
    @Inject
    UserTransaction utxn;

    private List<VendedorEntity> vrlist = new ArrayList<>();
    
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
    
    /**
     * Prueba del método constructor
     */
    @Test
    public void vendedorTest()
    {
        VendedorEntity newvr = new VendedorEntity("ei.chernov@russland.com", "Eron", "Ivanovich", 75321.0, 98640.0, "E_Ivanovich", "Deu86Rus");     
        Assert.assertEquals("Ivanovich", newvr.getApellido());
        Assert.assertEquals("Eron", newvr.getNombre());
        Assert.assertEquals("ei.chernov@russland.com", newvr.getCorreoElectronico());
        Assert.assertEquals("Deu86Rus", newvr.getContrasena());
        Assert.assertEquals("E_Ivanovich", newvr.getUsuario());
        Assert.assertEquals(75321,newvr.getCelular(),0);        
        Assert.assertEquals(98640,newvr.getCedula(),0);
    }
    
    @Test
    public void createVendedorTest()
    {
        PodamFactory vrfactory = new PodamFactoryImpl();
        VendedorEntity vendedor = vrfactory.manufacturePojo(VendedorEntity.class);
        VendedorEntity obtainedvr = vrp.create(vendedor);
        Assert.assertNotNull(obtainedvr);       
        VendedorEntity vrentity = vrm.find(VendedorEntity.class,obtainedvr.getId());
        Assert.assertEquals(vendedor.getId(),vrentity.getId());
        Assert.assertEquals(vendedor.getApellido(),vrentity.getApellido());
        Assert.assertEquals(vendedor.getNombre(), vrentity.getNombre());
        Assert.assertEquals(vendedor.getCorreoElectronico(), vrentity.getCorreoElectronico());
        Assert.assertEquals(vendedor.getContrasena(), vrentity.getContrasena());
        Assert.assertEquals(vendedor.getUsuario(), vrentity.getUsuario());
        Assert.assertEquals(vendedor.getCelular(), vrentity.getCelular(),0);        
        Assert.assertEquals(vendedor.getCedula(), vrentity.getCedula(),0);
    }
    
    @Test
    public void findVendedorTest() 
    {
        VendedorEntity ref = vrlist.get(0), block = vrp.find(ref.getId());
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
    public void findAllVendedoresTest() 
    {
        List<VendedorEntity> allgotten = vrp.findAll();
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
    public void updateVendedorTest() 
    {
        VendedorEntity vendedor = vrlist.get(0);
        PodamFactory vrfactory = new PodamFactoryImpl();
        VendedorEntity updating = vrfactory.manufacturePojo(VendedorEntity.class);
        updating.setId(vendedor.getId());
        vrp.update(updating);
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
    public void deleteVendedorTest() 
    {
        VendedorEntity deleting = vrlist.get(0);
        vrp.delete(deleting.getId());
        VendedorEntity deleted = vrm.find(VendedorEntity.class, deleting.getId());
        Assert.assertNull(deleted);
    }
}