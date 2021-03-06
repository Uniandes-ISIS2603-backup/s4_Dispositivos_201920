/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.test.persistence;

import co.edu.uniandes.csw.dispositivos.entities.DispositivoEntity;
import co.edu.uniandes.csw.dispositivos.entities.FacturaEntity;
import co.edu.uniandes.csw.dispositivos.persistence.DispositivoPersistence;
import co.edu.uniandes.csw.dispositivos.persistence.FacturaPersistence;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
                .addPackage(DispositivoEntity.class.getPackage())
                .addPackage(DispositivoPersistence.class.getPackage())
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

    /**
     * Limpia la información de las tablas.
     */
    private void clearData() {
        em.createQuery("delete from FacturaEntity").executeUpdate();
    }

    /**
     * Prueba para consultar todas las facturas.
     */
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
        Assert.assertEquals(resp.getFechaDePago(), newEntity.getFechaDePago());

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
     * Prueba para encontrar una factura por su codigo.
     */
    @Test
    public void buscarFacturaPorCodigoTest() {
        FacturaEntity entity = data.get(0);
        FacturaEntity newEntity = mp.findByCode(entity.getNumeroDeFactura());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(newEntity.getDispositivos(), entity.getDispositivos());
        Assert.assertEquals(newEntity.getImpuestos(), entity.getImpuestos());
        Assert.assertEquals(newEntity.getNumeroDeFactura(), entity.getNumeroDeFactura());
        Assert.assertEquals(newEntity.getTotalPago(), entity.getTotalPago());
        Assert.assertEquals(entity.getFechaDePago(), newEntity.getFechaDePago());
    }
}
