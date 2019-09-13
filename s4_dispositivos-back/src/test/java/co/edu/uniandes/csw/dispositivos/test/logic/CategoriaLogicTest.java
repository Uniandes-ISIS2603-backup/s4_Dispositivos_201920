/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.test.logic;

import co.edu.uniandes.csw.dispositivos.ejb.CategoriaLogic;
import co.edu.uniandes.csw.dispositivos.entities.CategoriaEntity;
import co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.dispositivos.persistence.CategoriaPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import javax.validation.constraints.AssertTrue;
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
public class CategoriaLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private CategoriaLogic categoriaLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

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
                .addPackage(CategoriaLogic.class.getPackage())
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
     * Prueba para crear un categoria.
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test
    public void createCategoriaTest() throws BusinessLogicException {

        CategoriaEntity newEntity = factory.manufacturePojo(CategoriaEntity.class);
        CategoriaEntity result = categoriaLogic.createCategoria(newEntity);
        Assert.assertNotNull(result);
        CategoriaEntity entity = em.find(CategoriaEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getNombreCategoria(), entity.getNombreCategoria());
    }

    /**
     * Prueba para crear una categoria con el mismo nombre de un categoria que
     * ya existe.
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createCategoriaConMismoNombreTest() throws BusinessLogicException {
        CategoriaEntity newEntity = factory.manufacturePojo(CategoriaEntity.class);
        newEntity.setNombreCategoria(data.get(0).getNombreCategoria());
        categoriaLogic.createCategoria(newEntity);
    }

    /**
     * Prueba para consultar la lista de categorias.
     */
    @Test
    public void getCategoriasTest() {
        List<CategoriaEntity> list = categoriaLogic.getCategorias();
        Assert.assertEquals(data.size(), list.size());
        for (CategoriaEntity entity : list) {
            boolean found = false;
            for (CategoriaEntity entity2 : data) {
                if (entity.getId().equals(entity2.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Categoria.
     */
    @Test
    public void getCategoriaTest() {
        CategoriaEntity entity = data.get(0);
        CategoriaEntity resultEntity = categoriaLogic.getCategoria(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getNombreCategoria(), resultEntity.getNombreCategoria());
    }
    
        /**
     * Prueba para actualizar una categoria.
     */
    @Test
    public void updateCategoriaTest() throws BusinessLogicException {
        CategoriaEntity entity = data.get(0);
        CategoriaEntity pojoEntity = factory.manufacturePojo(CategoriaEntity.class);
        pojoEntity.setId(entity.getId());
        categoriaLogic.updateCategoria(pojoEntity.getId(), pojoEntity);
        CategoriaEntity resp = em.find(CategoriaEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getNombreCategoria(), resp.getNombreCategoria());
    }

    /**
     * Prueba para eliminar un categoria.
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test
    public void deleteMedioDePagoTest() throws BusinessLogicException {
        CategoriaEntity entity = data.get(1);
        categoriaLogic.deleteCategoria(entity.getId());
        CategoriaEntity deleted = em.find(CategoriaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    @Test
    public void verificarReglasNegocioCategoriaTest() throws BusinessLogicException
    {
        CategoriaEntity entity = new CategoriaEntity("Esta No Existe");
        Assert.assertTrue(categoriaLogic.verificaLasReglasNegocioCategoria(entity));
    }
}
