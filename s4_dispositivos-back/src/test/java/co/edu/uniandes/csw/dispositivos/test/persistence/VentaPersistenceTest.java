/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.test.persistence;

import co.edu.uniandes.csw.dispositivos.entities.VendedorEntity;
import co.edu.uniandes.csw.dispositivos.entities.VentaEntity;
import co.edu.uniandes.csw.dispositivos.persistence.VentaPersistence;
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
public class VentaPersistenceTest 
{
    @PersistenceContext(unitName="dispositivosPU")
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
    private VentaPersistence vap;

    @Inject
    UserTransaction utxn;

    private final List<VentaEntity> valist = new ArrayList<>();

    @Before
    public void prepareTest() 
    {
        try 
        {
            utxn.begin();
            vam.joinTransaction();
            vam.createQuery("delete from VentaEntity").executeUpdate();
            PodamFactory vafactory = new PodamFactoryImpl();
            for (int u = 0; u < 5; u++) 
            {
                VentaEntity venta = vafactory.manufacturePojo(VentaEntity.class);
                vam.persist(venta);
                valist.add(venta);
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
    public void ventaTest() 
    {
        PodamFactory factory = new PodamFactoryImpl();
        VendedorEntity auxvr = factory.manufacturePojo(VendedorEntity.class); 
        VentaEntity newva = new VentaEntity(35000.0, auxvr);
        Assert.assertEquals(35000.0, newva.getPrecioReventa(), 0.0);
        Assert.assertEquals(auxvr, newva.getVendedor()); 
    }

    @Test
    public void createVentaTest() 
    {
        PodamFactory vafactory = new PodamFactoryImpl();
        VentaEntity venta = vafactory.manufacturePojo(VentaEntity.class);
        VentaEntity obtainedva = vap.create(venta);
        Assert.assertNotNull(obtainedva);
        VentaEntity vaentity = vam.find(VentaEntity.class, obtainedva.getId());
        Assert.assertEquals(venta.getId(), vaentity.getId());
        Assert.assertEquals(venta.getPrecioReventa(), vaentity.getPrecioReventa(), 0.0);
        Assert.assertEquals(venta.getVendedor(), vaentity.getVendedor()); 
    }

    @Test
    public void findVentaTest() 
    {
        VentaEntity ref = valist.get(0), block = vap.find(ref.getId());
        Assert.assertNotNull(block);
        Assert.assertEquals(ref.getId(), block.getId());
        Assert.assertEquals(ref.getPrecioReventa(), block.getPrecioReventa(), 0.0);
        Assert.assertEquals(ref.getVendedor(), block.getVendedor()); 
    }

    @Test
    public void findAllVentasTest() 
    {
        List<VentaEntity> allgotten = vap.findAll();
        Assert.assertEquals(allgotten.size(), valist.size());
        for (VentaEntity vablock : allgotten) 
        {
            boolean ticked = false;
            for (VentaEntity varef : valist)
                if (vablock.getId().equals(varef.getId()))
                    ticked = true;
            Assert.assertTrue(ticked);
        }
    }

    @Test
    public void updateVentaTest() 
    {
        VentaEntity venta = valist.get(0);
        PodamFactory vafactory = new PodamFactoryImpl();
        VentaEntity updating = vafactory.manufacturePojo(VentaEntity.class);
        updating.setId(venta.getId());
        vap.update(updating);
        VentaEntity updated = vam.find(VentaEntity.class, updating.getId());
        Assert.assertEquals(updating.getId(), updated.getId());
        Assert.assertEquals(updating.getPrecioReventa(), updated.getPrecioReventa(), 0.0);
        Assert.assertEquals(updating.getVendedor(), updated.getVendedor()); 
    }

    @Test
    public void deleteVentaTest() 
    {
        VentaEntity deleting = valist.get(0);
        vap.delete(deleting.getId());
        VentaEntity deleted = vam.find(VentaEntity.class, deleting.getId());
        Assert.assertNull(deleted);
    }
}
