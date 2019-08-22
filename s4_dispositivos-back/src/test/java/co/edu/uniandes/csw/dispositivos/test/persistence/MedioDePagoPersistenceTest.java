/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.test.persistence;

import co.edu.uniandes.csw.dispositivos.entities.MedioDePagoEntity;
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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Juan L
 */
@RunWith(Arquillian.class)
public class MedioDePagoPersistenceTest {

    private static final Logger LOGGER = Logger.getLogger(MedioDePagoPersistence.class.getName());

    @Inject
    private MedioDePagoPersistence medioPPersistence;

    //@Inject
    //private OrganizationPersistence organizationPersistence;
    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;

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
            em.joinTransaction();
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
        //em.createQuery("delete from OrganizationEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();

        for (int i = 0; i < 3; i++) {
            MedioDePagoEntity entity = factory.manufacturePojo(MedioDePagoEntity.class);
            //OrganizationEntity orgEntity = factory.manufacturePojo(OrganizationEntity.class);

            //orgEntity.setPrize(entity);
            //entity.setOrganization(orgEntity);
            //em.persist(orgEntity);
            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un medio de pago.
     */
    @Test
    public void createMedioDePagoTest() {
        PodamFactory factory = new PodamFactoryImpl();
        MedioDePagoEntity newEntity = factory.manufacturePojo(MedioDePagoEntity.class);
        //OrganizationEntity newOrgEntity = factory.manufacturePojo(OrganizationEntity.class);

        //newOrgEntity = organizationPersistence.create(newOrgEntity);
        //newEntity.setOrganization(newOrgEntity);
        MedioDePagoEntity result = medioPPersistence.create(newEntity);

        Assert.assertNotNull(result);

        MedioDePagoEntity entity = em.find(MedioDePagoEntity.class, result.getId());

        Assert.assertEquals(newEntity.getNumeroTarjeta(), entity.getNumeroTarjeta());
        Assert.assertEquals(newEntity.getNumeroDeVerificacion(), entity.getNumeroDeVerificacion());
    }

    /**
     * Prueba para consultar la lista de medios de pagos.
     */
    @Test
    public void getMediosDePagoTest() {
        List<MedioDePagoEntity> list = medioPPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (MedioDePagoEntity ent : list) {
            boolean found = false;
            for (MedioDePagoEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Busca si hay algun medio de pago con el id que se envía de argumento
     *
     * @param medioId: id correspondiente al medio de pago buscado.
     * @return un medio de pago.
     */
    @Test
    public void getMedioDePagoTest() {
        MedioDePagoEntity entity = data.get(0);
        MedioDePagoEntity newEntity = medioPPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNumeroTarjeta(), newEntity.getNumeroTarjeta());
        Assert.assertEquals(entity.getNumeroDeVerificacion(), newEntity.getNumeroDeVerificacion());
    }

    /**
     * Actualiza un medio de pago.
     *
     * @param medioEntity: medio de pago que viene con los nuevos cambios. Por
     * ejemplo el nombre pudo cambiar. En ese caso, se haria uso del método
     * update.
     * @return un medio de pago con los cambios aplicados.
     */
    @Test
    public void updateMedioDePagoTest() {
        MedioDePagoEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        MedioDePagoEntity newEntity = factory.manufacturePojo(MedioDePagoEntity.class);

        newEntity.setId(entity.getId());

        medioPPersistence.update(newEntity);

        MedioDePagoEntity resp = em.find(MedioDePagoEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getNumeroTarjeta(), resp.getNumeroTarjeta());
        Assert.assertEquals(newEntity.getNumeroDeVerificacion(), resp.getNumeroDeVerificacion());
    }

    /**
     * Borra un medio de pago de la base de datos recibiendo como argumento el
     * id de medio de pago.
     *
     * @param medioId: id correspondiente el medio de pago a borrar.
     */
    @Test
    public void deleteMedioDePagoTest() {
        MedioDePagoEntity entity = data.get(0);
        medioPPersistence.delete(entity.getId());
        MedioDePagoEntity deleted = em.find(MedioDePagoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
