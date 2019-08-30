/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.test.persistence;

import co.edu.uniandes.csw.dispositivos.entities.CategoriaEntity;
import co.edu.uniandes.csw.dispositivos.persistence.CategoriaPersistence;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author Juan L
 */
@RunWith(Arquillian.class)
public class CategoriaPersistenceTest {

    private static final Logger LOGGER = Logger.getLogger(CategoriaPersistence.class.getName());

    @Inject
    private CategoriaPersistence categoriaPersistence;

    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;

    private List<CategoriaEntity> data = new ArrayList<CategoriaEntity>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CategoriaEntity.class.getPackage())
                .addPackage(CategoriaPersistence.class.getPackage())
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
        em.createQuery("delete from CategoriaEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();

        for (int i = 0; i < 3; i++) {
            CategoriaEntity entity = factory.manufacturePojo(CategoriaEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un medio de pago.
     */
    @Test
    public void createCategoriaTest() {
        PodamFactory factory = new PodamFactoryImpl();
        CategoriaEntity newEntity = factory.manufacturePojo(CategoriaEntity.class);

        CategoriaEntity result = categoriaPersistence.create(newEntity);

        Assert.assertNotNull(result);

        CategoriaEntity entity = em.find(CategoriaEntity.class, result.getId());

        Assert.assertEquals(newEntity.getNombreCategoria(), entity.getNombreCategoria());
    }
    
    /**
     * Test del metodo constructor de la clase Categoria.
     */
    @Test
    public void testConstructorCategoria()
    {
        CategoriaEntity prueba = new CategoriaEntity("abc");
        Assert.assertEquals("abc", prueba.getNombreCategoria());
    }

    /**
     * Prueba para consultar la lista de medios de pagos.
     */
    @Test
    public void getCategoriaFindAllTest() {
        List<CategoriaEntity> list = categoriaPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (CategoriaEntity ent : list) {
            boolean found = false;
            for (CategoriaEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Busca si hay alguna categoria con el id que se envía de argumento
     *
     * @param cateoriaId: id correspondiente a la categoria buscada.
     * @return una categoria.
     */
    @Test
    public void getCategoriaTest() {
        CategoriaEntity entity = data.get(0);
        CategoriaEntity newEntity = categoriaPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNombreCategoria(), newEntity.getNombreCategoria());
    }

    /**
     * Actualiza una Categoria.
     *
     * @param categoriaEntity: categoria que viene con los nuevos cambios. Por
     * ejemplo el nombre pudo cambiar. En ese caso, se haria uso del método
     * update.
     * @return una categoria con los cambios aplicados.
     */
    @Test
    public void updateCategoriaTest() {
        CategoriaEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        CategoriaEntity newEntity = factory.manufacturePojo(CategoriaEntity.class);

        newEntity.setId(entity.getId());

        categoriaPersistence.update(newEntity);

        CategoriaEntity resp = em.find(CategoriaEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getNombreCategoria(), resp.getNombreCategoria());
    }

    /**
     * Borra una categoria de la base de datos recibiendo como argumento el id
     * de categoria.
     *
     * @param medioId: id correspondiente a la categoria a borrar.
     */
    @Test
    public void deleteCategoriaTest() {
        CategoriaEntity entity = data.get(0);
        categoriaPersistence.delete(entity.getId());
        CategoriaEntity deleted = em.find(CategoriaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    /**
     * Prueba del test de equals. 
     */
    @Test
    public void testEqualsCategoria()
    {
        CategoriaEntity prueba = new CategoriaEntity("abc");
        CategoriaEntity prueba2 = new CategoriaEntity("abc");
        
        Assert.assertTrue(prueba.equals(prueba2));
        
        CategoriaEntity prueba3 = new CategoriaEntity("efg");
        Assert.assertFalse(prueba.equals(prueba3));
    }
}
