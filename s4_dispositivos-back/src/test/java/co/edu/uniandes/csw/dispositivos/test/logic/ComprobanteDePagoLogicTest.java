/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.test.logic;

import co.edu.uniandes.csw.dispositivos.ejb.ComprobanteDePagoLogic;
import co.edu.uniandes.csw.dispositivos.entities.ComprobanteDePagoEntity;
import co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.dispositivos.persistence.ComprobanteDePagoPersistence;
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
 * Test de lógica de la clase ComprobanteDePagoLogic
 *
 * @author Dianis Caro
 */
@RunWith(Arquillian.class)
public class ComprobanteDePagoLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @PersistenceContext(unitName = "dispositivosPU")
    private EntityManager em;

    @Inject
    private ComprobanteDePagoLogic comprobanteLogic;

    @Inject
    private UserTransaction utx;

    private List<ComprobanteDePagoEntity> data = new ArrayList<ComprobanteDePagoEntity>();

    /**
     * Configuración inicial de la prueba
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
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ComprobanteDePagoEntity adminEntity = factory.manufacturePojo(ComprobanteDePagoEntity.class);
            em.persist(adminEntity);
            data.add(adminEntity);
        }
    }
    /**
     * Limpia las tablas que están implicadas en la prueba
     */
    private void clearData() {
        em.createQuery("delete from ComprobanteDePagoEntity").executeUpdate();
    }
    /**
     * Construye el despliegue de la prueba a realizar
     * @return jar, es decir JavaArchive.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ComprobanteDePagoEntity.class.getPackage())
                .addPackage(ComprobanteDePagoPersistence.class.getPackage())
                .addPackage(ComprobanteDePagoLogic.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    /**
     * Prueba para eliminar un comprobante
     */
    @Test
    public void deleteComprobanteTest() {
        ComprobanteDePagoEntity entity = data.get(1);
        comprobanteLogic.deleteComprobante(entity.getId());
        ComprobanteDePagoEntity deleted = em.find(ComprobanteDePagoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    /**
     * Prueba para consultar un comprobante
     */
    @Test
    public void getComprobanteTest() {
        ComprobanteDePagoEntity entity = data.get(0);
        ComprobanteDePagoEntity resultEntity = comprobanteLogic.getComprobante(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(resultEntity.getId(), entity.getId());
        Assert.assertEquals(resultEntity.getFechaDeFactura(), entity.getFechaDeFactura());
        Assert.assertEquals(resultEntity.getId(), entity.getId());
        Assert.assertEquals(resultEntity.getImpuestos(), entity.getImpuestos());
        Assert.assertEquals(resultEntity.getNumeroDeFactura(), entity.getNumeroDeFactura());
        Assert.assertEquals(resultEntity.getNumeroDeTarjeta(), entity.getNumeroDeTarjeta());
        Assert.assertEquals(resultEntity.getTotalDePago(), entity.getTotalDePago());
    }
    /**
     * Prueba para consultar la lista de Comprobantes
     */
    @Test
    public void getComprobantesTest() {
        List<ComprobanteDePagoEntity> list = comprobanteLogic.getComprobantes();
        Assert.assertEquals(data.size(), list.size());
        for (ComprobanteDePagoEntity entity : list) {
            boolean found = false;
            for (ComprobanteDePagoEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    /**
     * Prueba para actualizar una Comprobante de pago
     * @throws co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException
     */
    @Test
    public void updateComprobanteTest() throws BusinessLogicException {
        ComprobanteDePagoEntity entity = data.get(0);
        ComprobanteDePagoEntity pojoEntity = factory.manufacturePojo(ComprobanteDePagoEntity.class);
        pojoEntity.setId(entity.getId());
        comprobanteLogic.updateComprobanteDePago(pojoEntity.getId(), pojoEntity);
        ComprobanteDePagoEntity resp = em.find(ComprobanteDePagoEntity.class, entity.getId());
        Assert.assertNotNull(resp);
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getNumeroDeFactura(), resp.getNumeroDeFactura());
        Assert.assertEquals(pojoEntity.getImpuestos(), resp.getImpuestos());
        Assert.assertEquals(pojoEntity.getNumeroDeTarjeta(), resp.getNumeroDeTarjeta());
        Assert.assertEquals(pojoEntity.getTotalDePago(), resp.getTotalDePago());
        Assert.assertEquals(pojoEntity.getFechaDeFactura(), resp.getFechaDeFactura());
    }
    /**
     * Test para crear un ComprobanteDePago con todas las reglas de negocio
     * @throws BusinessLogicException si una regla de negocio no se cumple
     */
    @Test
    public void createComprobanteTest() throws BusinessLogicException {
        ComprobanteDePagoEntity comprobanteEntity = factory.manufacturePojo(ComprobanteDePagoEntity.class);
        ComprobanteDePagoEntity result = comprobanteLogic.createComprobante(comprobanteEntity);
        Assert.assertNotNull(result);

        ComprobanteDePagoEntity entity = em.find(ComprobanteDePagoEntity.class, result.getId());
        Assert.assertNotNull(entity);
        Assert.assertEquals(entity.getId(), result.getId());
        Assert.assertEquals(entity.getFechaDeFactura(), result.getFechaDeFactura());
        Assert.assertEquals(entity.getId(), result.getId());
        Assert.assertEquals(entity.getImpuestos(), result.getImpuestos());
        Assert.assertEquals(entity.getNumeroDeFactura(), result.getNumeroDeFactura());
        Assert.assertEquals(entity.getNumeroDeTarjeta(), result.getNumeroDeTarjeta());
        Assert.assertEquals(entity.getTotalDePago(), result.getTotalDePago());
    }
    /**
     * Test para crear un comprobante de pago con un número de dígitos diferente a 16
     * @throws BusinessLogicException si una regla de negocio no se cumple
     */
    @Test(expected = BusinessLogicException.class)
    public void createNumeroTarjetaDiferente16() throws BusinessLogicException {
        ComprobanteDePagoEntity comprobanteEntity = factory.manufacturePojo(ComprobanteDePagoEntity.class);
        comprobanteEntity.setNumeroDeTarjeta("1234567890");
        comprobanteLogic.createComprobante(comprobanteEntity);
    }
    /**
     * Test para crear un comprobante de pago con una fecha en null
     * @throws BusinessLogicException si una regla de negocio no se cumple
     */
    @Test(expected = BusinessLogicException.class)
    public void createFechaNull() throws BusinessLogicException {
        ComprobanteDePagoEntity comprobanteEntity = factory.manufacturePojo(ComprobanteDePagoEntity.class);
        comprobanteEntity.setFechaDeFactura(null);
        comprobanteLogic.createComprobante(comprobanteEntity);
    }
    /**
     * Test para crear un comprobante de pago con un total de pago en null
     * @throws BusinessLogicException si una regla de negocio no se cumple
     */
    @Test(expected = BusinessLogicException.class)
    public void createTotalDePagoNull() throws BusinessLogicException {
        ComprobanteDePagoEntity comprobanteEntity = factory.manufacturePojo(ComprobanteDePagoEntity.class);
        comprobanteEntity.setTotalDePago(null);
        comprobanteLogic.createComprobante(comprobanteEntity);
    }
    /**
     * Test para crear un comprobante de pago con un nùmero de factura en null
     * @throws BusinessLogicException si una regla de negocio no se cumple
     */
    @Test(expected = BusinessLogicException.class)
    public void createNumeroDeFacturaNull() throws BusinessLogicException {
        ComprobanteDePagoEntity comprobanteEntity = factory.manufacturePojo(ComprobanteDePagoEntity.class);
        comprobanteEntity.setNumeroDeFactura(null);
        comprobanteLogic.createComprobante(comprobanteEntity);
    }
    /**
     * Test para crear un comprobante de pago con impuestos null
     * @throws BusinessLogicException si una regla de negocio no se cumple
     */
    @Test(expected = BusinessLogicException.class)
    public void createImpuestosNull() throws BusinessLogicException {
        ComprobanteDePagoEntity comprobanteEntity = factory.manufacturePojo(ComprobanteDePagoEntity.class);
        comprobanteEntity.setImpuestos(null);
        comprobanteLogic.createComprobante(comprobanteEntity);
    }
    /**
     * Test para crear un comprobante de pago con un nùmero de tarjeta en null
     * @throws BusinessLogicException si una regla de negocio no se cumple
     */
    @Test(expected = BusinessLogicException.class)
    public void createNumeroDeTarjetaNull() throws BusinessLogicException {
        ComprobanteDePagoEntity comprobanteEntity = factory.manufacturePojo(ComprobanteDePagoEntity.class);
        comprobanteEntity.setNumeroDeTarjeta(null);
        comprobanteLogic.createComprobante(comprobanteEntity);
    }
     /**
     * Test para crear un comprobante con nùmero de factura existente
     * @throws BusinessLogicException si una regla de negocio no se cumple
     */
    @Test(expected = BusinessLogicException.class)
    public  void createNumeroFacturaExistente() throws BusinessLogicException
    {
        ComprobanteDePagoEntity comprobanteEntity = factory.manufacturePojo(ComprobanteDePagoEntity.class);
        ComprobanteDePagoEntity result = comprobanteLogic.createComprobante(comprobanteEntity);
        Assert.assertNotNull(result);
        
        ComprobanteDePagoEntity comprobanteEntity2 = factory.manufacturePojo(ComprobanteDePagoEntity.class);
        comprobanteEntity2.setNumeroDeFactura(comprobanteEntity.getNumeroDeFactura());
        comprobanteLogic.createComprobante(comprobanteEntity2);
    }
    /**
     * Test para actualizar un numero de factura en 0
     * @throws BusinessLogicException si una regla de negocio no se cumple
     */
    @Test(expected = BusinessLogicException.class)
    public  void updateComprobanteNumeroFacturaCero() throws BusinessLogicException
    {
        ComprobanteDePagoEntity entity = data.get(0);
        ComprobanteDePagoEntity comprobanteUpdate = entity;
        comprobanteUpdate.setNumeroDeFactura(0);
        comprobanteLogic.updateComprobanteDePago(entity.getId(), entity);
    }
    /**
     * Test para actualizar el total de pago en 0
     * @throws BusinessLogicException si una regla de negocio no se cumple
     */
    @Test(expected = BusinessLogicException.class)
    public  void updateComprobanteTotalDePagoCero() throws BusinessLogicException
    {
        ComprobanteDePagoEntity entity = data.get(0);
        ComprobanteDePagoEntity comprobanteUpdate = entity;
        comprobanteUpdate.setTotalDePago(0.0);
        comprobanteLogic.updateComprobanteDePago(entity.getId(), entity);
    }
    /**
     * Test para actualizar los impuestos en 0
     * @throws BusinessLogicException si una regla de negocio no se cumple
     */
    @Test(expected = BusinessLogicException.class)
    public  void updateComprobanteImpuestosCero() throws BusinessLogicException
    {
        ComprobanteDePagoEntity entity = data.get(0);
        ComprobanteDePagoEntity comprobanteUpdate = entity;
        comprobanteUpdate.setImpuestos(0.0);
        comprobanteLogic.updateComprobanteDePago(entity.getId(), entity);
    }
    /**
     * Test para actualizar el número de tarjeta vacío
     * @throws BusinessLogicException si una regla de negocio no se cumple
     */
    @Test(expected = BusinessLogicException.class)
    public  void updateComprobanteTarjetaVacio() throws BusinessLogicException
    {
        ComprobanteDePagoEntity entity = data.get(0);
        ComprobanteDePagoEntity comprobanteUpdate = entity;
        comprobanteUpdate.setNumeroDeTarjeta("");
        comprobanteLogic.updateComprobanteDePago(entity.getId(), entity);
    }
    /**
     * Test para actualizarla numero de Tarjeta con dígitos diferentes a 16
     * @throws BusinessLogicException si una regla de negocio no se cumple
     */
    @Test(expected = BusinessLogicException.class)
    public  void updateComprobanteTarjeta16() throws BusinessLogicException
    {
        ComprobanteDePagoEntity entity = data.get(0);
        ComprobanteDePagoEntity comprobanteUpdate = entity;
        comprobanteUpdate.setNumeroDeTarjeta("1234567890");
        comprobanteLogic.updateComprobanteDePago(entity.getId(), entity);
    }
    /**
     * Test para actualizarla numero de factura existente
     * @throws BusinessLogicException si una regla de negocio no se cumple
     */
    @Test(expected = BusinessLogicException.class)
    public  void updateComprobanteFcaturaExistente() throws BusinessLogicException
    {
        ComprobanteDePagoEntity entity = data.get(0);
        ComprobanteDePagoEntity comprobanteUpdate = data.get(1);
        comprobanteUpdate.setNumeroDeFactura(entity.getNumeroDeFactura());
        comprobanteLogic.updateComprobanteDePago(comprobanteUpdate.getId(), comprobanteUpdate);
    }
}
