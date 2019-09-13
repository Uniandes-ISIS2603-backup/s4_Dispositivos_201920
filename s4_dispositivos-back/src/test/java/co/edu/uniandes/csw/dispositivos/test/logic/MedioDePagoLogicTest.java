/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.test.logic;

import co.edu.uniandes.csw.dispositivos.ejb.MedioDePagoLogic;
import co.edu.uniandes.csw.dispositivos.entities.MedioDePagoEntity;
import co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.dispositivos.persistence.MedioDePagoPersistence;
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
 * @author Juan Lozano
 */
@RunWith(Arquillian.class)
public class MedioDePagoLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private MedioDePagoLogic medioLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<MedioDePagoEntity> data = new ArrayList<MedioDePagoEntity>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(MedioDePagoEntity.class.getPackage())
                .addPackage(MedioDePagoLogic.class.getPackage())
                .addPackage(MedioDePagoPersistence.class.getPackage())
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
        em.createQuery("delete from MedioDePagoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();

        for (int i = 0; i < 3; i++) {
            MedioDePagoEntity entity = factory.manufacturePojo(MedioDePagoEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un medio de pago.
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test
    public void createCategoriaTest() throws BusinessLogicException {

        MedioDePagoEntity newEntity = factory.manufacturePojo(MedioDePagoEntity.class);

        newEntity.setNumeroTarjeta("51000000");
        newEntity.setNumeroDeVerificacion("123");
        MedioDePagoEntity result = medioLogic.createMedioDePago(newEntity);
        Assert.assertNotNull(result);
        MedioDePagoEntity entity = em.find(MedioDePagoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getNumeroDeVerificacion(), entity.getNumeroDeVerificacion());
        Assert.assertEquals(newEntity.getNumeroTarjeta(), entity.getNumeroTarjeta());
        Assert.assertEquals(newEntity.getTipoCredito(), entity.getTipoCredito());
        Assert.assertEquals(newEntity.getTipoTarjeta(), entity.getTipoTarjeta());

        try {
            newEntity.setNumeroTarjeta("51000000");
            MedioDePagoEntity result5 = medioLogic.createMedioDePago(newEntity);
        } catch (Exception e) {
        }

        newEntity.setTipoTarjeta("MAstercard");
        newEntity.setNumeroTarjeta("52000000");
        MedioDePagoEntity result5 = medioLogic.createMedioDePago(newEntity);
        Assert.assertNotNull(result5);

        newEntity.setNumeroTarjeta("8000000");
        newEntity.setNumeroDeVerificacion("000000");
        newEntity.setTipoTarjeta("MAstercard");
        try {
            MedioDePagoEntity result3 = medioLogic.createMedioDePago(newEntity);
        } catch (Exception e) {
        }

        newEntity.setNumeroTarjeta("40000000");
        newEntity.setTipoTarjeta("Visa");
        MedioDePagoEntity result2 = medioLogic.createMedioDePago(newEntity);
        Assert.assertNotNull(result2);

        newEntity.setNumeroTarjeta("30000000");
        newEntity.setNumeroDeVerificacion("100000");
        newEntity.setTipoTarjeta("Visa");
        try {
            MedioDePagoEntity result4 = medioLogic.createMedioDePago(newEntity);
        } catch (Exception e) {
        }
    }

    /**
     * Prueba para crear un medio de pago con el mismo numero de tarjeta que ya
     * existe.
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createMedioDePagoMismoNumeroTarjetaTest() throws BusinessLogicException {
        MedioDePagoEntity newEntity = factory.manufacturePojo(MedioDePagoEntity.class);
        newEntity.setNumeroTarjeta(data.get(0).getNumeroTarjeta());
        medioLogic.createMedioDePago(newEntity);
    }

    /**
     * Prueba para consultar la lista de metodos de pago.
     */
    @Test
    public void getMetodosDePagoTest() {
        List<MedioDePagoEntity> list = medioLogic.getMediosDePago();
        Assert.assertEquals(data.size(), list.size());
        for (MedioDePagoEntity entity : list) {
            boolean found = false;
            for (MedioDePagoEntity entity2 : data) {
                if (entity.getId().equals(entity2.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Medio de pago.
     */
    @Test
    public void getMedioDePagoTest() {
        MedioDePagoEntity entity = data.get(0);
        MedioDePagoEntity resultEntity = medioLogic.getMedioDePago(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getNumeroDeVerificacion(), resultEntity.getNumeroDeVerificacion());
        Assert.assertEquals(entity.getNumeroTarjeta(), resultEntity.getNumeroTarjeta());
        Assert.assertEquals(entity.getTipoCredito(), resultEntity.getTipoCredito());
        Assert.assertEquals(entity.getTipoTarjeta(), resultEntity.getTipoTarjeta());
    }

    /**
     * Prueba para eliminar un medio de pago.
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test
    public void deleteMedioDePagoTest() throws BusinessLogicException {
        MedioDePagoEntity entity = data.get(1);
        medioLogic.deleteMedioDePago(entity.getId());
        MedioDePagoEntity deleted = em.find(MedioDePagoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar una categoria.
     */
    @Test
    public void updateCategoriaTest() throws BusinessLogicException {
        /**
         * MedioDePagoEntity entity = data.get(0); MedioDePagoEntity pojoEntity
         * = factory.manufacturePojo(MedioDePagoEntity.class);
         * pojoEntity.setId(entity.getId());
         * pojoEntity.setNumeroTarjeta("51000000");
         * pojoEntity.setNumeroDeVerificacion("123");
         * medioLogic.updateMedioDePago(entity.getId(), pojoEntity);
         * MedioDePagoEntity resp = em.find(MedioDePagoEntity.class,
         * entity.getId()); resp.setNumeroTarjeta("51000000");
         * resp.setNumeroDeVerificacion("123");
         * Assert.assertEquals(pojoEntity.getId(), resp.getId());
         * Assert.assertEquals(entity.getNumeroDeVerificacion(),
         * resp.getNumeroDeVerificacion());
         * Assert.assertEquals(entity.getNumeroTarjeta(),
         * resp.getNumeroTarjeta());
         * Assert.assertEquals(entity.getTipoCredito(), resp.getTipoCredito());
         * Assert.assertEquals(entity.getTipoTarjeta(), resp.getTipoTarjeta());
         */

        MedioDePagoEntity entity = data.get(0);
        
        MedioDePagoEntity pojoEntity = factory.manufacturePojo(MedioDePagoEntity.class);

        pojoEntity.setId(entity.getId());
        pojoEntity.setNumeroTarjeta("400");
        pojoEntity.setNumeroDeVerificacion("123");
        
        MedioDePagoEntity result = medioLogic.createMedioDePago(pojoEntity);
        
        medioLogic.updateMedioDePago(pojoEntity.getId(), pojoEntity);
        
        MedioDePagoEntity resp = em.find(MedioDePagoEntity.class, entity.getId());
        
        Assert.assertEquals(result.getId(), resp.getId());
        Assert.assertEquals(result.getNumeroDeVerificacion(), resp.getNumeroDeVerificacion());
    }

    /**
     * Verifica las reglas de negocio de la clase MedieoDePagoLogic
     *
     * @throws BusinessLogicException
     */
    @Test
    public void verificarReglasNegocioMedioDePagoTest() throws BusinessLogicException {
        MedioDePagoEntity entity = new MedioDePagoEntity("40000000", "123", "Visa", "1");
        Assert.assertTrue(medioLogic.verificarLasReglasNegocioMedioDePago(entity));
        MedioDePagoEntity entity2 = new MedioDePagoEntity("53000000", "123", "MasterCard", "1");
        Assert.assertTrue(medioLogic.verificarLasReglasNegocioMedioDePago(entity2));
        MedioDePagoEntity entity3 = new MedioDePagoEntity("10000000", "12356", "Visa", "1");
        try {
            medioLogic.verificarLasReglasNegocioMedioDePago(entity3);

        } catch (Exception e) {
        }
        MedioDePagoEntity entity4 = new MedioDePagoEntity("80000000", "12375", "MasterCard", "1");
        try {
            medioLogic.verificarLasReglasNegocioMedioDePago(entity4);

        } catch (Exception e) {
        }
    }

}
