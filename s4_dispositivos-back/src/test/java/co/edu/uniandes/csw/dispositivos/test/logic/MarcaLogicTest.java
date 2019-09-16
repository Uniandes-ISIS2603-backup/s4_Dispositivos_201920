/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.test.logic;

import co.edu.uniandes.csw.dispositivos.ejb.MarcaLogic;
import co.edu.uniandes.csw.dispositivos.entities.MarcaEntity;
import co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException;
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
public class MarcaLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @PersistenceContext(unitName = "dispositivosPU")
    private EntityManager em;

    @Inject
    private MarcaLogic marcaLogic;

    @Inject
    private UserTransaction utx;

    private List<MarcaEntity> data = new ArrayList<MarcaEntity>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(MarcaEntity.class.getPackage())
                .addPackage(MarcaLogic.class.getPackage())
                .addPackage(MarcaPersistence.class.getPackage())
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
        em.createQuery("delete from MarcaEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            MarcaEntity entity = factory.manufacturePojo(MarcaEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }

    @Test
    public void createMarcaTest() throws BusinessLogicException {
        MarcaEntity newEntity = factory.manufacturePojo(MarcaEntity.class);
        MarcaEntity result = marcaLogic.createMarca(newEntity);
        Assert.assertNotNull(result);

        MarcaEntity entity = em.find(MarcaEntity.class, result.getId());
        Assert.assertEquals(entity.getNombreMarca(), result.getNombreMarca());
        Assert.assertEquals(entity.getImagen(), result.getImagen());
    }

    @Test(expected = BusinessLogicException.class)
    public void createMarcaNombreNullTest() throws BusinessLogicException {
        MarcaEntity newEntity = factory.manufacturePojo(MarcaEntity.class);
        newEntity.setNombreMarca(null);
        MarcaEntity result = marcaLogic.createMarca(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createMarcaImagenNullTest() throws BusinessLogicException {
        MarcaEntity newEntity = factory.manufacturePojo(MarcaEntity.class);
        newEntity.setImagen(null);
        MarcaEntity result = marcaLogic.createMarca(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createMarcaNombreVacioTest() throws BusinessLogicException {
        MarcaEntity newEntity = factory.manufacturePojo(MarcaEntity.class);
        newEntity.setNombreMarca("                    ");
        MarcaEntity result = marcaLogic.createMarca(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createMarcaImagenVaciaTest() throws BusinessLogicException {
        MarcaEntity newEntity = factory.manufacturePojo(MarcaEntity.class);
        newEntity.setImagen("");
        MarcaEntity result = marcaLogic.createMarca(newEntity);
    }

    /**
     * Prueba para crear una marca con el mismo nombre de una marca que ya
     * existe.
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createMarcaConMismoNombreTest() throws BusinessLogicException {
        MarcaEntity newEntity = factory.manufacturePojo(MarcaEntity.class);
        newEntity.setNombreMarca(data.get(0).getNombreMarca());
        marcaLogic.createMarca(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void updateMarcaNombreNullTest() throws BusinessLogicException {
        MarcaEntity newEntity = factory.manufacturePojo(MarcaEntity.class);
        newEntity.setNombreMarca(null);
        MarcaEntity entity = data.get(0);
        MarcaEntity result = marcaLogic.updateMarca(entity.getId(), newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void updateMarcaImagenNullTest() throws BusinessLogicException {
        MarcaEntity newEntity = factory.manufacturePojo(MarcaEntity.class);
        newEntity.setImagen(null);
        MarcaEntity entity = data.get(0);
        MarcaEntity result = marcaLogic.updateMarca(entity.getId(), newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void updateMarcaNombreVacioTest() throws BusinessLogicException {
        MarcaEntity newEntity = factory.manufacturePojo(MarcaEntity.class);
        newEntity.setNombreMarca("                    ");
        MarcaEntity entity = data.get(0);
        MarcaEntity result = marcaLogic.updateMarca(entity.getId(), newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void updateMarcaImagenVaciaTest() throws BusinessLogicException {
        MarcaEntity newEntity = factory.manufacturePojo(MarcaEntity.class);
        newEntity.setImagen("");
        MarcaEntity entity = data.get(0);
        MarcaEntity result = marcaLogic.updateMarca(entity.getId(), newEntity);
    }

    /**
     * Prueba para crear una marca con el mismo nombre de una marca que ya
     * existe.
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateMarcaConMismoNombreTest() throws BusinessLogicException {
        MarcaEntity newEntity = factory.manufacturePojo(MarcaEntity.class);
        newEntity.setNombreMarca(data.get(0).getNombreMarca());
        MarcaEntity entity = data.get(1);
        MarcaEntity result = marcaLogic.updateMarca(entity.getId(), newEntity);
    }

    /**
     * Prueba para consultar la lista de Marcas.
     */
    @Test
    public void getMarcasTest() {
        List<MarcaEntity> list = marcaLogic.getMarcas();
        Assert.assertEquals(data.size(), list.size());
        for (MarcaEntity entity : list) {
            boolean found = false;
            for (MarcaEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
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
    public void getMarcaTest() {
        MarcaEntity entity = data.get(0);
        MarcaEntity resultEntity = marcaLogic.getMarca(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getNombreMarca(), resultEntity.getNombreMarca());
        Assert.assertEquals(entity.getImagen(), resultEntity.getImagen());
    }

    /**
     * Prueba para actualizar una Marca.
     *
     * @throws BusinessLogicException
     */
    @Test
    public void updateMarcaTest() throws BusinessLogicException {
        MarcaEntity entity = data.get(0);
        MarcaEntity pojoEntity = factory.manufacturePojo(MarcaEntity.class);
        pojoEntity.setId(entity.getId());
        marcaLogic.updateMarca(pojoEntity.getId(), pojoEntity);
        MarcaEntity resp = em.find(MarcaEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getNombreMarca(), resp.getNombreMarca());
        Assert.assertEquals(pojoEntity.getImagen(), resp.getImagen());
    }

    /**
     * Prueba para eliminar una Marca.
     */
    @Test
    public void deleteMarcaTest() {
        MarcaEntity entity = data.get(1);
        marcaLogic.deleteMarca(entity.getId());
        MarcaEntity deleted = em.find(MarcaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

}
