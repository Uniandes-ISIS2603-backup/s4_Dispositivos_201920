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
    
    @Inject
    UserTransaction utxn;
    
    private final List<VendedorEntity> vrlist = new ArrayList<>();
    
    /**
     * Prueba del método sobreescrito equals()
     */
    @Test
    public void equalsTest()
    {
        VendedorEntity newvr1 = new VendedorEntity("vx.chernov@hannover.edu.co", "Wilhelm Hosevich", "Chernov", 3785098657, 8267503149, "WilhelmH_C", "53-Sport");
        VendedorEntity newvr2 = new VendedorEntity("vx.chernov@hannover.edu.co", "Wilhelm Hosevich", "Chernov", 3785098657, 8267503149, "WilhelmH_C", "53-Sport");
        VendedorEntity newvr3 = new VendedorEntity("vx.chernov@hannover.edu.co", "Wilhelm Hosevich", "Chernov", 3785098657, 8267503149, "WilhelmH_C", "53-Sport"); 
        Assert.assertTrue(newvr2.equals(newvr1));
        Assert.assertTrue(newvr3.equals(newvr2));
        Assert.assertTrue(newvr1.equals(newvr3));
    }
    
    /**
     * Prueba del método constructor
     */
    @Test
    public void vendedorTest()
    {
        VendedorEntity newvr = new VendedorEntity("ei.chernov@hannover.edu.co", "Eron Ivanovich", "Chernov", 3249168324, 7532109864, "EronI_C", "Deu86Rus");
        Assert.assertEquals("ei.chernov@hannover.edu.co", newvr.getCorreoElectronico());
        Assert.assertEquals("Eron Ivanovich", newvr.getNombre());
        Assert.assertEquals("Chernov", newvr.getApellido());
        Assert.assertEquals(3249168324, newvr.getCelular(), 0.0);
        Assert.assertEquals(7532109864, newvr.getCedula(), 0.0);
        Assert.assertEquals("EronI_C", newvr.getUsuario());
        Assert.assertEquals("Deu86Rus", newvr.getContrasena());
    }
    
    @Before
    public void prepareTest()
    {
        try
        {
            utxn.begin();
            vrm.joinTransaction();
            vrm.createQuery("delete from VendedorEntity").executeUpdate();
            PodamFactory vrfactory = new PodamFactoryImpl();
            for (int u = 0; u < 5; u++)
            {
                VendedorEntity vendedor = vrfactory.manufacturePojo(VendedorEntity.class);
                vrm.persist(vendedor);
                vrlist.add(vendedor);
            }
            utxn.commit();
        }
        catch(Exception e0)
        {
            e0.printStackTrace();
            try 
            {    utxn.rollback();   }
            catch(Exception e2)
            {    e2.printStackTrace();  }
        }
    }
    
    @Test
    public void createVendedorTest()
    {
        PodamFactory vrfactory = new PodamFactoryImpl();
        VendedorEntity vendedor = vrfactory.manufacturePojo(VendedorEntity.class);
        VendedorEntity obtainedvr = vrp.create(vendedor);
        Assert.assertNotNull(obtainedvr);
        VendedorEntity vrentity = vrm.find(VendedorEntity.class, obtainedvr.getId());
        Assert.assertEquals(vendedor.getId(), vrentity.getId());
        Assert.assertEquals(vendedor.getNombre(), vrentity.getNombre());
        Assert.assertEquals(vendedor.getApellido(), vrentity.getApellido());
        Assert.assertEquals(vendedor.getCedula(), vrentity.getCedula());
        Assert.assertEquals(vendedor.getCelular(), vrentity.getCelular());
        Assert.assertEquals(vendedor.getCorreoElectronico(), vrentity.getCorreoElectronico());
        Assert.assertEquals(vendedor.getContrasena(), vrentity.getContrasena());
    }
    
    @Test
    public void findVendedorTest()
    {
        VendedorEntity ref = vrlist.get(0), block = vrp.find(ref.getId());
        Assert.assertNotNull(block);
        Assert.assertEquals(ref.getId(), block.getId());
        Assert.assertEquals(ref.getNombre(), block.getNombre());
        Assert.assertEquals(ref.getApellido(), block.getApellido());
        Assert.assertEquals(ref.getCedula(), block.getCedula());
        Assert.assertEquals(ref.getCelular(), block.getCelular());
        Assert.assertEquals(ref.getCorreoElectronico(), block.getCorreoElectronico());
        Assert.assertEquals(ref.getContrasena(), block.getContrasena());
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
                if(vrblock.getId().equals(vrref.getId()))
                    ticked = true;
            Assert.assertTrue(ticked);
        }
    }
    
    @Test
    public void updateVendedorTest()
    {
        VendedorEntity updating = vrlist.get(0);
        PodamFactory vafactory = new PodamFactoryImpl();
        VendedorEntity vendedor = vafactory.manufacturePojo(VendedorEntity.class);
        vendedor.setId(updating.getId());
        vrp.update(vendedor);
        VendedorEntity updated = vrm.find(VendedorEntity.class, updating.getId());
        Assert.assertEquals(vendedor.getId(), updated.getId());
        Assert.assertEquals(vendedor.getId(), updated.getId());
        Assert.assertEquals(vendedor.getNombre(), updated.getNombre());
        Assert.assertEquals(vendedor.getApellido(), updated.getApellido());
        Assert.assertEquals(vendedor.getCedula(), updated.getCedula());
        Assert.assertEquals(vendedor.getCelular(), updated.getCelular());
        Assert.assertEquals(vendedor.getCorreoElectronico(), updated.getCorreoElectronico());
        Assert.assertEquals(vendedor.getContrasena(), updated.getContrasena());
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
