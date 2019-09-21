/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.test.logic;

import co.edu.uniandes.csw.dispositivos.ejb.FacturaLogic;
import co.edu.uniandes.csw.dispositivos.entities.FacturaEntity;
import co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException;
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
public class FacturaLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @PersistenceContext(unitName = "dispositivosPU")
    private EntityManager em;

    @Inject
    private FacturaLogic facturaLogic;

    @Inject
    private UserTransaction utx;

    private List<FacturaEntity> data = new ArrayList<FacturaEntity>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(FacturaEntity.class.getPackage())
                .addPackage(FacturaLogic.class.getPackage())
                .addPackage(FacturaPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * Configuración inicial de la prueba.
     */
    @Before
    public void configTest() {
        try {
            utx.begin();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        em.createQuery("delete from FacturaEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            FacturaEntity entity = factory.manufacturePojo(FacturaEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }

    @Test
    public void createFacturaTest() throws BusinessLogicException {
        FacturaEntity newEntity = factory.manufacturePojo(FacturaEntity.class);
        FacturaEntity result = facturaLogic.createFactura(newEntity);
        Assert.assertNotNull(result);

        FacturaEntity entity = em.find(FacturaEntity.class, result.getId());
        Assert.assertEquals(entity.getDispositivos(), result.getDispositivos());
        Assert.assertEquals(entity.getImpuestos(), result.getImpuestos());
        Assert.assertEquals(entity.getNumeroDeFactura(), result.getNumeroDeFactura());
        Assert.assertEquals(entity.getTotalPago(), result.getTotalPago());

    }

    @Test(expected = BusinessLogicException.class)
    public void createFacturaDispositivosNullTest() throws BusinessLogicException {
        FacturaEntity newEntity = factory.manufacturePojo(FacturaEntity.class);
        newEntity.setDispositivos(null);
        FacturaEntity result = facturaLogic.createFactura(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createFacturaDispositivosVacioTest() throws BusinessLogicException {
        FacturaEntity newEntity = factory.manufacturePojo(FacturaEntity.class);
        newEntity.setDispositivos("");
        FacturaEntity result = facturaLogic.createFactura(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createFacturaNumeroNullTest() throws BusinessLogicException {
        FacturaEntity newEntity = factory.manufacturePojo(FacturaEntity.class);
        newEntity.setNumeroDeFactura(null);
        FacturaEntity result = facturaLogic.createFactura(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createFacturaNumeroNegaOCeroTest() throws BusinessLogicException {
        FacturaEntity newEntity = factory.manufacturePojo(FacturaEntity.class);
        newEntity.setNumeroDeFactura(0);
        FacturaEntity result = facturaLogic.createFactura(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createFacturaImpuestosNullTest() throws BusinessLogicException {
        FacturaEntity newEntity = factory.manufacturePojo(FacturaEntity.class);
        newEntity.setImpuestos(null);
        FacturaEntity result = facturaLogic.createFactura(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createFacturaImpuestosNegativosTest() throws BusinessLogicException {
        FacturaEntity newEntity = factory.manufacturePojo(FacturaEntity.class);
        newEntity.setImpuestos(-1.572);
        FacturaEntity result = facturaLogic.createFactura(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createFacturaTotalPagoNullTest() throws BusinessLogicException {
        FacturaEntity newEntity = factory.manufacturePojo(FacturaEntity.class);
        newEntity.setTotalPago(null);
        FacturaEntity result = facturaLogic.createFactura(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createFacturaTotalPagoNegOCeroTest() throws BusinessLogicException {
        FacturaEntity newEntity = factory.manufacturePojo(FacturaEntity.class);
        newEntity.setTotalPago(-0.12);
        FacturaEntity result = facturaLogic.createFactura(newEntity);
    }

    /**
     * Prueba para crear una factura con el mismo número de una factura que ya
     * existe.
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createFacturaConMismoNumeroTest() throws BusinessLogicException {
        FacturaEntity newEntity = factory.manufacturePojo(FacturaEntity.class);
        newEntity.setNumeroDeFactura(data.get(0).getNumeroDeFactura());
        facturaLogic.createFactura(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void updateFacturaDispositivosNullTest() throws BusinessLogicException {
        FacturaEntity newEntity = factory.manufacturePojo(FacturaEntity.class);
        newEntity.setDispositivos(null);
        FacturaEntity entity = data.get(0);
        FacturaEntity result = facturaLogic.updateFactura(entity.getId(), newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void updateFacturaDispositivosVacioTest() throws BusinessLogicException {
        FacturaEntity newEntity = factory.manufacturePojo(FacturaEntity.class);
        newEntity.setDispositivos("");
        FacturaEntity entity = data.get(0);
        FacturaEntity result = facturaLogic.updateFactura(entity.getId(), newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void updateFacturaNumeroNullTest() throws BusinessLogicException {
        FacturaEntity newEntity = factory.manufacturePojo(FacturaEntity.class);
        newEntity.setNumeroDeFactura(null);
        FacturaEntity entity = data.get(0);
        FacturaEntity result = facturaLogic.updateFactura(entity.getId(), newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void updateFacturaNumeroNegaOCeroTest() throws BusinessLogicException {
        FacturaEntity newEntity = factory.manufacturePojo(FacturaEntity.class);
        newEntity.setNumeroDeFactura(0);
        FacturaEntity entity = data.get(0);
        FacturaEntity result = facturaLogic.updateFactura(entity.getId(), newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void updateFacturaImpuestosNullTest() throws BusinessLogicException {
        FacturaEntity newEntity = factory.manufacturePojo(FacturaEntity.class);
        newEntity.setImpuestos(null);
        FacturaEntity entity = data.get(0);
        FacturaEntity result = facturaLogic.updateFactura(entity.getId(), newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void updateFacturaImpuestosNegativosTest() throws BusinessLogicException {
        FacturaEntity newEntity = factory.manufacturePojo(FacturaEntity.class);
        newEntity.setImpuestos(-1.572);
        FacturaEntity entity = data.get(0);
        FacturaEntity result = facturaLogic.updateFactura(entity.getId(), newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void updateFacturaTotalPagoNullTest() throws BusinessLogicException {
        FacturaEntity newEntity = factory.manufacturePojo(FacturaEntity.class);
        newEntity.setTotalPago(null);
        FacturaEntity entity = data.get(0);
        FacturaEntity result = facturaLogic.updateFactura(entity.getId(), newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void updateFacturaTotalPagoNegOCeroTest() throws BusinessLogicException {
        FacturaEntity newEntity = factory.manufacturePojo(FacturaEntity.class);
        newEntity.setTotalPago(-0.12);
        FacturaEntity entity = data.get(0);
        FacturaEntity result = facturaLogic.updateFactura(entity.getId(), newEntity);
    }

    /**
     * Prueba para crear una factura con el mismo número de una factura que ya
     * existe.
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateFacturaConMismoNumeroTest() throws BusinessLogicException {
        FacturaEntity newEntity = factory.manufacturePojo(FacturaEntity.class);
        newEntity.setNumeroDeFactura(data.get(0).getNumeroDeFactura());
        facturaLogic.updateFactura(data.get(1).getId(), newEntity);
    }

    /**
     * Prueba para consultar la lista de facturas.
     */
    @Test
    public void getFacturasTest() {
        List<FacturaEntity> list = facturaLogic.getFacturas();
        Assert.assertEquals(data.size(), list.size());
        for (FacturaEntity entity : list) {
            boolean found = false;
            for (FacturaEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar una factura.
     */
    @Test
    public void getFacturaTest() {
        FacturaEntity entity = data.get(0);
        FacturaEntity resultEntity = facturaLogic.getFactura(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getDispositivos(), resultEntity.getDispositivos());
        Assert.assertEquals(entity.getImpuestos(), resultEntity.getImpuestos());
        Assert.assertEquals(entity.getNumeroDeFactura(), resultEntity.getNumeroDeFactura());
        Assert.assertEquals(entity.getTotalPago(), resultEntity.getTotalPago());
        Assert.assertEquals(entity.getFechaDePago(), resultEntity.getFechaDePago());
    }

    /**
     * Prueba para actualizar una Factura.
     *
     * @throws BusinessLogicException
     */
    @Test
    public void updateFacturaTest() throws BusinessLogicException {
        FacturaEntity entity = data.get(0);
        FacturaEntity pojoEntity = factory.manufacturePojo(FacturaEntity.class);
        pojoEntity.setId(entity.getId());
        facturaLogic.updateFactura(pojoEntity.getId(), pojoEntity);
        FacturaEntity resp = em.find(FacturaEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getDispositivos(), resp.getDispositivos());
        Assert.assertEquals(pojoEntity.getImpuestos(), resp.getImpuestos());
        Assert.assertEquals(pojoEntity.getNumeroDeFactura(), resp.getNumeroDeFactura());
        Assert.assertEquals(pojoEntity.getTotalPago(), resp.getTotalPago());
        Assert.assertEquals(pojoEntity.getFechaDePago(), resp.getFechaDePago());

    }

    /**
     * Prueba para eliminar una Factura.
     */
    @Test
    public void deleteFacturaTest() {
        FacturaEntity entity = data.get(1);
        facturaLogic.deleteFactura(entity.getId());
        FacturaEntity deleted = em.find(FacturaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
