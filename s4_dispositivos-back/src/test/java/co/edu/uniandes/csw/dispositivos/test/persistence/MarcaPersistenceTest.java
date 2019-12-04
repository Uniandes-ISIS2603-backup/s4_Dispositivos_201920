/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.test.persistence;

import co.edu.uniandes.csw.dispositivos.entities.MarcaEntity;
import co.edu.uniandes.csw.dispositivos.persistence.MarcaPersistence;
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
public class MarcaPersistenceTest {

    @Inject
    MarcaPersistence mp;

    @PersistenceContext(unitName = "dispositivosPU")
    protected EntityManager em;

    @Inject
    UserTransaction utx;

    private List<MarcaEntity> data = new ArrayList<>();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(MarcaEntity.class.getPackage())
                .addPackage(MarcaPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    @Test
    public void createTest() {
        PodamFactory factory = new PodamFactoryImpl();
        MarcaEntity marcaEntity = factory.manufacturePojo(MarcaEntity.class);
        MarcaEntity result = mp.create(marcaEntity);
        Assert.assertNotNull(result);

        MarcaEntity entity = em.find(MarcaEntity.class, result.getId());
        Assert.assertEquals(marcaEntity.getNombreMarca(), entity.getNombreMarca());
        Assert.assertEquals(marcaEntity.getLogo(), entity.getLogo());
    }

    @Before
    public void configTest() {
        try {
            utx.begin();
            em.joinTransaction();
            clearData();
            PodamFactory factory = new PodamFactoryImpl();
            for (int i = 0; i < 3; i++) {
                MarcaEntity entity = factory.manufacturePojo(MarcaEntity.class);
                em.persist(entity);
                data.add(entity);
            }
            utx.commit();
            System.out.println(mp.findAll().size());
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private void clearData() {
        em.createQuery("delete from DispositivoEntity").executeUpdate();
        em.createQuery("delete from MarcaEntity").executeUpdate();
    }

    @Test
    public void findMarcasTest() {
        List<MarcaEntity> list = mp.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (MarcaEntity ent : list) {
            boolean found = false;
            for (MarcaEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar una Marca.
     */
    @Test
    public void findMarcaTest() {
        MarcaEntity entity = data.get(0);
        MarcaEntity newEntity = mp.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNombreMarca(), newEntity.getNombreMarca());
        Assert.assertEquals(entity.getLogo(), newEntity.getLogo());
    }

    /**
     * Prueba para actualizar una marca.
     */
    @Test
    public void updateMarcaTest() {
        MarcaEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        MarcaEntity newEntity = factory.manufacturePojo(MarcaEntity.class);

        newEntity.setId(entity.getId());

        mp.update(newEntity);

        MarcaEntity resp = em.find(MarcaEntity.class, entity.getId());

        
        Assert.assertEquals(newEntity.getId(), resp.getId());
        Assert.assertEquals(newEntity.getNombreMarca(), resp.getNombreMarca());
        Assert.assertEquals(newEntity.getLogo(), resp.getLogo());
    }

    /**
     * Prueba para eliminar una marca.
     */
    @Test
    public void deleteMarcaTest() {
        MarcaEntity entity = data.get(0);
        mp.delete(entity.getId());
        MarcaEntity deleted = em.find(MarcaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para crear una marca.
     */
    @Test
    public void crearMarcaTest() {
        PodamFactory factory = new PodamFactoryImpl();
        MarcaEntity marcaPrueba = factory.manufacturePojo(MarcaEntity.class);
        MarcaEntity result = mp.create(marcaPrueba);
        Assert.assertNotNull(result);

        MarcaEntity entity = em.find(MarcaEntity.class, result.getId());

        Assert.assertEquals(entity.getLogo(), marcaPrueba.getLogo());
        Assert.assertEquals(entity.getNombreMarca(), marcaPrueba.getNombreMarca());
        Assert.assertEquals(entity.hashCode(), marcaPrueba.hashCode());
        Assert.assertTrue(entity.equals(marcaPrueba));
    }

    /**
     * Prueba para encontrar una marca por su nombre.
     */
    @Test
    public void buscarMarcaPorNombreTest() {
        PodamFactory factory = new PodamFactoryImpl();
        MarcaEntity marcaPrueba = factory.manufacturePojo(MarcaEntity.class);
        mp.create(marcaPrueba);
        Assert.assertNotNull(mp.findByNombre(marcaPrueba.getNombreMarca()));
        Assert.assertNull(mp.findByNombre("marcaPruebe"));
    }
}
