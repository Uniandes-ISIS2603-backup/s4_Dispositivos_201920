/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.test.logic;

import co.edu.uniandes.csw.dispositivos.ejb.VentaLogic;
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

    @PersistenceContext
    private EntityManager vam;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(VentaEntity.class.getPackage())
                .addPackage(VentaLogic.class.getPackage())
                .addPackage(VentaPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    private PodamFactory valfactory = new PodamFactoryImpl();

    @Inject
    private VentaLogic valogic;

    @Inject
    UserTransaction utxn;

    private final List<VentaEntity> valist = new ArrayList<>();

    /**
     * Establece las configuraciones iniciales del test
     */
    @Before
    public void prepareTest() {
        try {
            utxn.begin();
            vam.joinTransaction();
            vam.createQuery("delete from VentaEntity").executeUpdate();
            PodamFactory vafactory = new PodamFactoryImpl();
            for (int i = 0; i < 5; i++) {
                VentaEntity venta = vafactory.manufacturePojo(VentaEntity.class);
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

    @Test
    public void createVentaTest() throws BusinessLogicException {
        VentaEntity venta = valfactory.manufacturePojo(VentaEntity.class);
        VentaEntity obtainedva = valogic.createVenta(venta);
        Assert.assertNotNull(obtainedva);
    }

    @Test(expected = BusinessLogicException.class)
    public void createNegativeVentaTest() throws BusinessLogicException {
        VentaEntity venta = valfactory.manufacturePojo(VentaEntity.class);
        venta.setPrecioReventa(-1.0);
        valogic.createVenta(venta);
    }

    @Test
    public void findVentaTest() throws BusinessLogicException {
        VentaEntity ref = valist.get(0), block = valogic.findVenta(ref.getId());
        Assert.assertNotNull(block);
        Assert.assertEquals(ref.getId(), block.getId());
        Assert.assertEquals(ref.getPrecioReventa(), block.getPrecioReventa(), 0.0);
    }

    @Test
    public void findAllVentasTest() throws BusinessLogicException {
        List<VentaEntity> allgotten = valogic.findAllVentas();
        Assert.assertEquals(allgotten.size(), valist.size());
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

    @Test
    public void updateVentaTest() throws BusinessLogicException {
        VentaEntity venta = valist.get(0);
        VentaEntity updating = valfactory.manufacturePojo(VentaEntity.class);
        updating.setId(venta.getId());
        valogic.updateVenta(updating);
        VentaEntity updated = vam.find(VentaEntity.class, updating.getId());
        Assert.assertEquals(updating.getId(), updated.getId());
        Assert.assertEquals(updating.getPrecioReventa(), updated.getPrecioReventa(), 0.0);
    }

    @Test(expected = BusinessLogicException.class)
    public void updateNullVendedorTest() throws BusinessLogicException {
        VentaEntity updating = null;
        valogic.updateVenta(updating);
    }

    @Test
    public void deleteVentaTest() throws BusinessLogicException {
        VentaEntity vaentity = valist.get(0);
        valogic.deleteVenta(vaentity.getId());
        VentaEntity goneva = vam.find(VentaEntity.class, vaentity.getId());
        Assert.assertNull(goneva);
    }

//    @Test(expected=BusinessLogicException.class)
//    public void deleteVendedorNullTest() throws BusinessLogicException
//    {
//        VentaEntity vaentity = valist.get(0);
//        Long deleteid = vaentity.getId();
//        vaentity = null;
//        valogic.deleteVenta(deleteid);
//    }
}
