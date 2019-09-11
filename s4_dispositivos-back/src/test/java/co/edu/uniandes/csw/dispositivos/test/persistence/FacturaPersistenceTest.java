/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.test.persistence;

import co.edu.uniandes.csw.dispositivos.entities.FacturaEntity;
import co.edu.uniandes.csw.dispositivos.persistence.FacturaPersistence;
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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Carlos Salazar
 */
@RunWith(Arquillian.class)
public class FacturaPersistenceTest {

    @Inject
    private FacturaPersistence mp;

    @PersistenceContext(unitName = "dispositivosPU")
    private EntityManager em;

    @Inject
    UserTransaction utx;

    private List<FacturaEntity> data = new ArrayList<>();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(FacturaEntity.class.getPackage())
                .addPackage(FacturaPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    @Test
    public void createTest() {
        PodamFactory factory = new PodamFactoryImpl();
        FacturaEntity facturaEntity = factory.manufacturePojo(FacturaEntity.class);
        FacturaEntity result = mp.create(facturaEntity);
        Assert.assertNotNull(result);

        FacturaEntity entity = em.find(FacturaEntity.class, result.getId());
        Assert.assertEquals(facturaEntity.getDispositivos(), entity.getDispositivos());
        Assert.assertEquals(facturaEntity.getImpuestos(), entity.getImpuestos());
        Assert.assertEquals(facturaEntity.getNumeroDeFactura(), entity.getNumeroDeFactura());
        Assert.assertEquals(facturaEntity.getTotalPago(), entity.getTotalPago());
    }

    @Before
    public void configTest() {
        try {
            utx.begin();
            em.joinTransaction();
            clearData();
            PodamFactory factory = new PodamFactoryImpl();
            for (int i = 0; i < 3; i++) {
                FacturaEntity entity = factory.manufacturePojo(FacturaEntity.class);
                em.persist(entity);
                data.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearData() {
        em.createQuery("delete from FacturaEntity").executeUpdate();
    }

    @Test
    public void findFacturasTest() {
        List<FacturaEntity> list = mp.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (FacturaEntity ent : list) {
            boolean found = false;
            for (FacturaEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar una Factura.
     */
    @Test
    public void findFacturaTest() {
        FacturaEntity entity = data.get(0);
        FacturaEntity newEntity = mp.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getDispositivos(), newEntity.getDispositivos());
        Assert.assertEquals(entity.getImpuestos(), newEntity.getImpuestos());
        Assert.assertEquals(entity.getNumeroDeFactura(), newEntity.getNumeroDeFactura());
        Assert.assertEquals(entity.getTotalPago(), newEntity.getTotalPago());
    }

    /**
     * Prueba para actualizar una Factura.
     */
    @Test
    public void updateFacturaTest() {
        FacturaEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        FacturaEntity newEntity = factory.manufacturePojo(FacturaEntity.class);

        newEntity.setId(entity.getId());

        mp.update(newEntity);

        FacturaEntity resp = em.find(FacturaEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getDispositivos(), resp.getDispositivos());
        Assert.assertEquals(newEntity.getImpuestos(), resp.getImpuestos());
        Assert.assertEquals(newEntity.getNumeroDeFactura(), resp.getNumeroDeFactura());
        Assert.assertEquals(newEntity.getTotalPago(), resp.getTotalPago());
    }

    /**
     * Prueba para eliminar una factura.
     */
    @Test
    public void deleteFacturaTest() {
        FacturaEntity entity = data.get(0);
        mp.delete(entity.getId());
        FacturaEntity deleted = em.find(FacturaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para crear una factura.
     */
    @Test
    public void crearFacturaTest() {
        FacturaEntity facturaPrueba = new FacturaEntity(200, 15.5, 1.3, "Celular");
        FacturaEntity facturaPrueba2 = new FacturaEntity(200, 15.5, 1.3, "Celular");
        FacturaEntity facturaPrueba3 = new FacturaEntity(201, 15.5, 1.3, "Celular");

        assertEquals(200, facturaPrueba.getNumeroDeFactura(), 0);
        assertEquals(15.5, facturaPrueba.getTotalPago(), 0);
        assertEquals(1.3, facturaPrueba.getImpuestos(), 0);
        Assert.assertEquals("Celular", facturaPrueba.getDispositivos());
        Assert.assertEquals(facturaPrueba.hashCode(), facturaPrueba.hashCode());
        Assert.assertTrue(facturaPrueba.equals(facturaPrueba2));
    }

    /**
     * Prueba para encontrar una factura por su codigo.
     */
    @Test
    public void buscarFacturaPorCodigoTest() {
        FacturaEntity facturaPrueba = new FacturaEntity(200, 15.5, 1.3, "Celular");
        mp.create(facturaPrueba);
        Assert.assertNotNull(mp.findByCode(200));
        Assert.assertNull(mp.findByCode(241));
        assertEquals(200, facturaPrueba.getNumeroDeFactura(), 0);
        assertEquals(15.5, facturaPrueba.getTotalPago(), 0);
        assertEquals(1.3, facturaPrueba.getImpuestos(), 0);
        Assert.assertEquals("Celular", facturaPrueba.getDispositivos());

    }
}
