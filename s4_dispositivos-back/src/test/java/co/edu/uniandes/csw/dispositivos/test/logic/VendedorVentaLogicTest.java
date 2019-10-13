/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.test.logic;

import co.edu.uniandes.csw.dispositivos.ejb.VendedorLogic;
import co.edu.uniandes.csw.dispositivos.ejb.VendedorVentaLogic;
import co.edu.uniandes.csw.dispositivos.entities.VendedorEntity;
import co.edu.uniandes.csw.dispositivos.entities.VentaEntity;
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
public class VendedorVentaLogicTest {

    /**
     * Persistencia donde operan los tests
     */
    @PersistenceContext
    private EntityManager varm;

    /**
     * @return Contexto con el que se ejecutan los tests
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(VendedorEntity.class.getPackage())
                .addPackage(VendedorLogic.class.getPackage())
                .addPackage(VendedorPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * Creador de entidades de prueba
     */
    private PodamFactory varfactory = new PodamFactoryImpl();

    /**
     * Relación con la lógica de la clase Vendedor
     */
    @Inject
    private VendedorLogic vrlogic;
    
    /**
     * Relación con la lógica de la clase VendedorVenta
     */
    @Inject
    private VendedorVentaLogic varlogic;

    /**
     * Auxiliar de transacción
     */
    @Inject
    UserTransaction utxn;

    /**
     * Contenedor auxiliar con las entidades de la clase
     */
    private final List<VentaEntity> valist = new ArrayList<>();

    /**
     * Contenedor auxiliar con las entidades de la clase
     */
    private final List<VendedorEntity> vrlist = new ArrayList<>();
    
    /**
     * Establece las configuraciones iniciales del test
     */
    @Before
    public void prepareTest() {
        try {
            utxn.begin();
            varm.joinTransaction();
            varm.createQuery("delete from VentaEntity").executeUpdate();
            varm.createQuery("delete from VendedorEntity").executeUpdate();
            for (int i = 0; i < 5; i++) {
                VentaEntity venta = varfactory.manufacturePojo(VentaEntity.class);
                varm.persist(venta);
                valist.add(venta);
            }
            for (int j = 0; j < 5; j++) {
                VendedorEntity vendedor = varfactory.manufacturePojo(VendedorEntity.class);
                varm.persist(vendedor);
                vrlist.add(vendedor);
                valist.get(j).setVendedor(vendedor);
                List<VentaEntity> vregister = new ArrayList<>();
                vregister.add(valist.get(j));
                vendedor.setVentas(vregister);
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
        VendedorEntity vcontainer = vrlist.get(0);
        VentaEntity vcontained = valist.get(0); 
        VentaEntity vresult = varlogic.createVenta(vcontainer.getId(), vcontained.getId());
        Assert.assertNotNull(vresult);
        Assert.assertEquals(vresult.getId(), vcontained.getId());
    }

    /**
     * Test de la validación de buscar venta
     * @throws co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException
     */
    @Test
    public void findVentaTest() throws BusinessLogicException {
        VendedorEntity vcontainer = vrlist.get(0);
        VentaEntity vcontained = valist.get(0); 
        VentaEntity vresult = varlogic.findVenta(vcontainer.getId(), vcontained.getId());
        Assert.assertNotNull(vresult);
        Assert.assertEquals(vresult.getId(), vcontained.getId());
        Assert.assertEquals(vresult.getPrecioReventa(), vcontained.getPrecioReventa(), 0.0);
    }

    /**
     * Test de la validación de encontrar todas las ventas
     */
    @Test
    public void findAllVentasTest(){
        List<VentaEntity> allgotten = varlogic.findAllVentas(vrlist.get(0).getId());
        Assert.assertEquals(1, allgotten.size());
    }
    
    /**
     * Test de validación de reemplazar las ventas de un vendedor.
     */
    @Test
    public void replaceComprobantesTest() {
        VendedorEntity vmanager = vrlist.get(0);
        List<VentaEntity> listedva = valist.subList(2, 4);
        varlogic.replaceVentas(vmanager.getId(), listedva);

        vmanager = vrlogic.findVendedor(vmanager.getId());
        Assert.assertFalse(vmanager.getVentas().contains(valist.get(1)));
        Assert.assertTrue(vmanager.getVentas().contains(valist.get(2)));
        Assert.assertTrue(vmanager.getVentas().contains(valist.get(3)));
    }
}
