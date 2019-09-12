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
 * @author Dianis Caro
 */
@RunWith(Arquillian.class)
public class ComprobanteDePagoLogicTest 
{
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
     * Inserta los datos iniciales para el correcto funcionamiento de las pruebas
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) 
        {
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
        Assert.assertEquals(resultEntity.getImpuestos(), entity.getImpuestos(), 0);
        Assert.assertEquals(resultEntity.getNumeroDeFactura(), entity.getNumeroDeFactura());
        Assert.assertEquals(resultEntity.getNumeroDeTarjeta(), entity.getNumeroDeTarjeta());
        Assert.assertEquals(resultEntity.getTotalDePago(), entity.getTotalDePago(), 0);
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
    public void updateComprobanteTest() throws BusinessLogicException
    {
        ComprobanteDePagoEntity entity = data.get(0);
        ComprobanteDePagoEntity pojoEntity = factory.manufacturePojo(ComprobanteDePagoEntity.class);
        pojoEntity.setId(entity.getId());
        comprobanteLogic.updateComprobanteDePago(pojoEntity.getId(), pojoEntity);
        ComprobanteDePagoEntity resp = em.find(ComprobanteDePagoEntity.class, entity.getId());
        Assert.assertNotNull(resp);
        Assert.assertEquals(resp.getId(), entity.getId());
        Assert.assertEquals(resp.getFechaDeFactura(), entity.getFechaDeFactura());
        Assert.assertEquals(resp.getId(), entity.getId());
        Assert.assertEquals(resp.getImpuestos(), entity.getImpuestos(), 0);
        Assert.assertEquals(resp.getNumeroDeFactura(), entity.getNumeroDeFactura());
        Assert.assertEquals(resp.getNumeroDeTarjeta(), entity.getNumeroDeTarjeta());
        Assert.assertEquals(resp.getTotalDePago(), entity.getTotalDePago(), 0);
    }
    /**
     * Test para crear un ComprobanteDePago con todas las reglas de negocio
     * @throws BusinessLogicException si una regla de negocio no se cumple
     */
    @Test
    public  void createAdministrador() throws BusinessLogicException
    {
        ComprobanteDePagoEntity comprobanteEntity = factory.manufacturePojo(ComprobanteDePagoEntity.class);
        ComprobanteDePagoEntity result = comprobanteLogic.createComprobante(comprobanteEntity);
        Assert.assertNotNull(result);
        
        ComprobanteDePagoEntity entity = em.find(ComprobanteDePagoEntity.class, result.getId());
        Assert.assertNotNull(entity);
        Assert.assertEquals(entity.getId(), result.getId());
        Assert.assertEquals(entity.getFechaDeFactura(), result.getFechaDeFactura());
        Assert.assertEquals(entity.getId(), result.getId());
        Assert.assertEquals(entity.getImpuestos(), result.getImpuestos(), 0);
        Assert.assertEquals(entity.getNumeroDeFactura(), result.getNumeroDeFactura());
        Assert.assertEquals(entity.getNumeroDeTarjeta(), result.getNumeroDeTarjeta());
        Assert.assertEquals(entity.getTotalDePago(), result.getTotalDePago(), 0);
    }
}
