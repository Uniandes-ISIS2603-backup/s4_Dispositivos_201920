/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.test.logic;

import co.edu.uniandes.csw.dispositivos.ejb.VentaLogic;
import co.edu.uniandes.csw.dispositivos.entities.VendedorEntity;
import co.edu.uniandes.csw.dispositivos.entities.VentaEntity;
import co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException;
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
public class VentaLogicTest {

    /**
     * Persistencia donde operan los tests
     */
    @PersistenceContext(unitName="dispositivosPU")
    private EntityManager vam;

    /**
     * @return Contexto con el que se ejecutan los tests
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(VentaEntity.class.getPackage())
                .addPackage(VentaLogic.class.getPackage())
                .addPackage(VentaPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * Creador de entidades de prueba
     */
    private PodamFactory vafactory = new PodamFactoryImpl();

    /**
     * Relación con la lógica de la clase
     */
    @Inject
    private VentaLogic valogic;

    /**
     * Auxiliar de transacción
     */
    @Inject
    private UserTransaction utxn;

    /**
     * Contenedor auxiliar con las entidades de la clase
     */
    private final List<VentaEntity> valist = new ArrayList<>();
    private final List<VendedorEntity> vrlist = new ArrayList<>();

    /**
     * Establece las configuraciones iniciales del test
     */
    @Before
    public void prepareTest() {
        try {
            utxn.begin();
            vam.joinTransaction();
            vam.createQuery("delete from VentaEntity").executeUpdate();
            vam.createQuery("delete from VendedorEntity").executeUpdate();
            for (int u = 0; u < 5; u++) {
                VendedorEntity vendedor = vafactory.manufacturePojo(VendedorEntity.class);
                vam.persist(vendedor);
                vrlist.add(vendedor);
            }
            for (int v = 0; v < 5; v++) {
                VentaEntity venta = vafactory.manufacturePojo(VentaEntity.class);
                if(v == 0) venta.setVendedor(vrlist.get(0));
                vam.persist(venta);
                valist.add(venta);
            }
            utxn.commit();
        } catch (Exception e1) {
            e1.printStackTrace();
            try {
                utxn.rollback();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /**
     * Test de la validación de agregar venta
     * @throws co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException
     */
    @Test
    public void createVentaTest() throws BusinessLogicException {
        VentaEntity venta = vafactory.manufacturePojo(VentaEntity.class);
        venta.setVendedor(vrlist.get(1));
        VentaEntity obtainedva = valogic.createVenta(vrlist.get(1).getId(), venta);
        Assert.assertNotNull(obtainedva);
    }
    
    /**
     * Test de falla de agregar venta sin vendedor
     * @throws co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createVentaNullVendedorTest() throws BusinessLogicException {
        VentaEntity venta = vafactory.manufacturePojo(VentaEntity.class);
        venta.setVendedor(null);
        valogic.createVenta(vrlist.get(1).getId(), venta);
    }
    
    /**
     * Test de falla de agregar venta con precio de reventa nulo
     * @throws co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createNullPriceVentaTest() throws BusinessLogicException {
        VentaEntity venta = vafactory.manufacturePojo(VentaEntity.class);   
        venta.setPrecioReventa(null);
        valogic.createVenta(vrlist.get(0).getId(), venta);
    }

    /**
     * Test de falla de agregar venta con precio de reventa negativo
     * @throws co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createNegativePriceVentaTest() throws BusinessLogicException {
        VentaEntity venta = vafactory.manufacturePojo(VentaEntity.class);   
        venta.setPrecioReventa(-1.0);
        valogic.createVenta(vrlist.get(0).getId(), venta);
    }

    /**
     * Test de la validación de buscar venta
     */
    @Test
    public void findVentaTest(){
        VentaEntity ref = valist.get(0), block = valogic.findVenta(vrlist.get(0).getId(), ref.getId());
        Assert.assertNotNull(block);
        Assert.assertEquals(ref.getId(), block.getId());
        Assert.assertEquals(ref.getPrecioReventa(), block.getPrecioReventa(), 0.0);
        Assert.assertEquals(ref.getVendedor(), block.getVendedor());
        Assert.assertEquals(ref.getFacturaOriginal(), block.getFacturaOriginal());
        Assert.assertArrayEquals(ref.getFotos(), block.getFotos());
    }

    /**
     * Test de la validación de encontrar todas las ventas
     */
    @Test
    public void findAllVentasTest(){
        List<VentaEntity> allgotten = valogic.findAllVentas(vrlist.get(0).getId());
        Assert.assertEquals(1, allgotten.size());
        for (VentaEntity vablock : allgotten) {
            boolean ticked = false;
            for (VentaEntity varef : valist) {
                if (vablock.getId().equals(varef.getId())) {
                    ticked = true;
                }
            }
            Assert.assertTrue(ticked);
        }
    }

    /**
     * Test de la validación de cambiar venta
     * @throws co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException
     */
    @Test
    public void updateVentaTest() throws BusinessLogicException {
        VentaEntity venta = valist.get(0);
        VentaEntity updating = vafactory.manufacturePojo(VentaEntity.class);
        updating.setId(venta.getId());
        updating.setVendedor(vrlist.get(2));
        valogic.updateVenta(vrlist.get(0).getId(), updating);
        VentaEntity updated = vam.find(VentaEntity.class, updating.getId());
        Assert.assertEquals(updating.getId(), updated.getId());
        Assert.assertEquals(updating.getPrecioReventa(), updated.getPrecioReventa(), 0.0);
        Assert.assertEquals(updating.getVendedor(), updated.getVendedor());
        Assert.assertEquals(updating.getFacturaOriginal(), updated.getFacturaOriginal());
        Assert.assertArrayEquals(updating.getFotos(), updated.getFotos());
    }
    
    /**
     * Test de falla de cambiar venta sin vendedor
     * @throws co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateVentaNullVendedorTest() throws BusinessLogicException {
        VentaEntity uventa = valist.get(0);
        VentaEntity venta = vafactory.manufacturePojo(VentaEntity.class);
        venta.setId(uventa.getId());
        venta.setVendedor(null);
        valogic.updateVenta(vrlist.get(0).getId(), venta);
    }
    
    /**
     * Test de falla de cambiar venta con precio de reventa nulo
     * @throws co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateNullPriceVentaTest() throws BusinessLogicException {
        VentaEntity uventa = valist.get(0);
        VentaEntity venta = vafactory.manufacturePojo(VentaEntity.class);
        venta.setId(uventa.getId());
        venta.setPrecioReventa(null);
        valogic.updateVenta(vrlist.get(0).getId(), venta);
    }
    
    /**
     * Test de falla de cambiar venta con precio de reventa negativo
     * @throws co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateNegativePriceVentaTest() throws BusinessLogicException {
        VentaEntity uventa = valist.get(0);
        VentaEntity venta = vafactory.manufacturePojo(VentaEntity.class);
        venta.setId(uventa.getId());
        venta.setPrecioReventa(-1.0);
        valogic.updateVenta(vrlist.get(0).getId(), venta);
    }

    /**
     * Test de la validación de borrar una venta
     * @throws co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException
     */
    @Test
    public void deleteVentaTest() throws BusinessLogicException{
        VentaEntity vaentity = valist.get(0);
        valogic.deleteVenta(vrlist.get(0).getId(), vaentity.getId());
        VentaEntity goneva = vam.find(VentaEntity.class, vaentity.getId());
        Assert.assertNull(goneva);
    }
    
    /**
     * Test de falla de borrar una venta nula
     * @throws co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void deleteNullVentaTest() throws BusinessLogicException{
        VentaEntity vaentity = valist.get(0);
        valogic.deleteVenta(vrlist.get(0).getId(), vaentity.getId());
        valogic.deleteVenta(vrlist.get(0).getId(), vaentity.getId());
    }
}
