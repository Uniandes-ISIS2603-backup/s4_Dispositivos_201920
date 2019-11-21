/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.test.persistence;

import co.edu.uniandes.csw.dispositivos.entities.FacturaEntity;
import co.edu.uniandes.csw.dispositivos.entities.VendedorEntity;
import co.edu.uniandes.csw.dispositivos.entities.VentaEntity;
import co.edu.uniandes.csw.dispositivos.persistence.FacturaPersistence;
import co.edu.uniandes.csw.dispositivos.persistence.VendedorPersistence;
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
    /**
     * Base de datos donde operan los tests
     */
    @PersistenceContext(unitName="dispositivosPU")
    protected EntityManager vam;

    /**
     * @return Contexto con el que se ejecutan los tests
     */
    @Deployment
    public static JavaArchive createDeployment() 
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(VentaEntity.class.getPackage())
                .addPackage(VentaPersistence.class.getPackage())
                .addPackage(VendedorEntity.class.getPackage())
                .addPackage(VendedorPersistence.class.getPackage())
                .addPackage(FacturaEntity.class.getPackage())
                .addPackage(FacturaPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * Relación con la persistencia de la clase
     */
    @Inject
    private VentaPersistence vap;

    /**
     * Auxiliar de transacción
     */
    @Inject
    UserTransaction utxn;

    /**
     * Contenedor con las entidades de la clase
     */
    private final List<VentaEntity> valist = new ArrayList<>();
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
            vam.joinTransaction();
            vam.createQuery("delete from VentaEntity").executeUpdate();
            vam.createQuery("delete from VendedorEntity").executeUpdate();
            PodamFactory vafactory = new PodamFactoryImpl();
            for (int u = 0; u < 5; u++) 
            {
                VendedorEntity vendedor = vafactory.manufacturePojo(VendedorEntity.class);
                vam.persist(vendedor);
                vrlist.add(vendedor);
            }
            for (int v = 0; v < 5; v++) 
            {
                VentaEntity venta = vafactory.manufacturePojo(VentaEntity.class);
                if(v == 0) venta.setVendedor(vrlist.get(0));
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
        FacturaEntity  vfactura = factory.manufacturePojo(FacturaEntity.class);
        String[] testfotos = new String[2];
        VentaEntity newva = new VentaEntity(35000.0, auxvr, vfactura, testfotos);
        Assert.assertEquals(35000.0, newva.getPrecioReventa(), 0.0);
        Assert.assertEquals(auxvr, newva.getVendedor());
        Assert.assertEquals(vfactura, newva.getFacturaOriginal());
    }
    
    /**
     * Test del método agregar venta
     */
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
        Assert.assertEquals(venta.getFacturaOriginal(), vaentity.getFacturaOriginal()); 
    }

    /**
     * Test del método buscar venta
     */
    @Test
    public void findVentaTest() 
    {
        VentaEntity ref = valist.get(0); 
        VentaEntity block = vap.find(vrlist.get(0).getId(), ref.getId());
        Assert.assertNotNull(block);
        Assert.assertEquals(ref.getId(), block.getId());
        Assert.assertEquals(ref.getPrecioReventa(), block.getPrecioReventa(), 0.0);
        Assert.assertEquals(ref.getVendedor(), block.getVendedor()); 
    }

    /**
     * Test del método enco trar todas las ventas
     */
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

    /**
     * Test del método cambiar venta
     */
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
        Assert.assertEquals(updating.getFacturaOriginal(), updated.getFacturaOriginal()); 
    }

    /**
     * Test del método borrar venta
     */
    @Test
    public void deleteVentaTest() 
    {
        VentaEntity deleting = valist.get(0);
        vap.delete(deleting.getId());
        VentaEntity deleted = vam.find(VentaEntity.class, deleting.getId());
        Assert.assertNull(deleted);
    }
}
