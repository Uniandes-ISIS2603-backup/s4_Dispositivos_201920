/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.test.persistence;

import co.edu.uniandes.csw.dispositivos.entities.VendedorEntity;
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
public class VendedorPersistenceTest {

    @Inject
    private VendedorPersistence mp;

    @PersistenceContext(unitName = "dispositivosPU")
    private EntityManager em;

    @Inject
    UserTransaction utx;

    private List<VendedorEntity> data = new ArrayList<>();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(VendedorEntity.class.getPackage())
                .addPackage(VendedorPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    public VendedorPersistenceTest() {
    }

    @Test
    public void testCreate() {
        PodamFactory factory = new PodamFactoryImpl();
        VendedorEntity newEntity = factory.manufacturePojo(VendedorEntity.class);
        VendedorEntity result = mp.create(newEntity);
        Assert.assertNotNull(result);

        VendedorEntity entity = em.find(VendedorEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getApellido(), entity.getApellido());
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
        Assert.assertEquals(newEntity.getCorreoElectronico(), entity.getCorreoElectronico());
        Assert.assertEquals(newEntity.getContrasena(), entity.getContrasena());
        Assert.assertEquals(newEntity.getUsuario(), entity.getUsuario());
        Assert.assertEquals(newEntity.getCelular(), entity.getCelular(), 0);
        Assert.assertEquals(newEntity.getCedula(), entity.getCedula(), 0);
    }

    /**
     * Establece las configuraciones iniciales del test
     */
    @Before
    public void configTest() {
        try {
            utx.begin();
            em.joinTransaction();
            clearData();
            PodamFactory factory = new PodamFactoryImpl();
            for (int i = 0; i < 3; i++) {
                VendedorEntity entity = factory.manufacturePojo(VendedorEntity.class);
                em.persist(entity);
                data.add(entity);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void clearData() {
        em.createQuery("delete from VendedorEntity").executeUpdate();
    }

    @Test
    public void testFindAll() {
        List<VendedorEntity> list = mp.findAll();
        System.out.println(list.size() + "   hhhhhh ");
        Assert.assertEquals(data.size(), list.size());
        for (VendedorEntity ent : list) {
            boolean found = false;
            for (VendedorEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    @Test
    public void testFind() {
        VendedorEntity entity = data.get(0);
        VendedorEntity newEntity = mp.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getApellido(), entity.getApellido());
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
        Assert.assertEquals(newEntity.getCorreoElectronico(), entity.getCorreoElectronico());
        Assert.assertEquals(newEntity.getContrasena(), entity.getContrasena());
        Assert.assertEquals(newEntity.getUsuario(), entity.getUsuario());
        Assert.assertEquals(newEntity.getCelular(), entity.getCelular(), 0);
        Assert.assertEquals(newEntity.getCedula(), entity.getCedula(), 0);
    }

    @Test
    public void testUpdate() {
        VendedorEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        VendedorEntity newEntity = factory.manufacturePojo(VendedorEntity.class);

        newEntity.setId(entity.getId());

        mp.update(newEntity);

        VendedorEntity resp = em.find(VendedorEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getApellido(), entity.getApellido());
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
        Assert.assertEquals(newEntity.getCorreoElectronico(), entity.getCorreoElectronico());
        Assert.assertEquals(newEntity.getContrasena(), entity.getContrasena());
        Assert.assertEquals(newEntity.getUsuario(), entity.getUsuario());
        Assert.assertEquals(newEntity.getCelular(), entity.getCelular(), 0);
        Assert.assertEquals(newEntity.getCedula(), entity.getCedula(), 0);
    }

    @Test
    public void testDelete() {
        VendedorEntity entity = data.get(0);
        mp.delete(entity.getId());
        VendedorEntity deleted = em.find(VendedorEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    @Test
    public void testConstructorAdmin() {
        VendedorEntity newEntity = new VendedorEntity("correo@correo.com", "nombre1", "apell1", 12344, 56789, "usua1", "contrase1");

        Assert.assertEquals("apell1", newEntity.getApellido());
        Assert.assertEquals("nombre1", newEntity.getNombre());
        Assert.assertEquals("correo@correo.com", newEntity.getCorreoElectronico());
        Assert.assertEquals("contrase1", newEntity.getContrasena());
        Assert.assertEquals("usua1", newEntity.getUsuario());
        Assert.assertEquals(12344, newEntity.getCelular(), 0);
        Assert.assertEquals(56789, newEntity.getCedula(), 0);
    }

}
