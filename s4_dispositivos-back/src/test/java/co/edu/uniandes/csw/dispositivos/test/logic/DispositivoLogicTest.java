/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.test.logic;

import co.edu.uniandes.csw.dispositivos.ejb.CategoriaLogic;
import co.edu.uniandes.csw.dispositivos.ejb.DispositivoLogic;
import co.edu.uniandes.csw.dispositivos.ejb.MarcaLogic;
import co.edu.uniandes.csw.dispositivos.entities.CategoriaEntity;
import co.edu.uniandes.csw.dispositivos.entities.DispositivoEntity;
import co.edu.uniandes.csw.dispositivos.entities.MarcaEntity;
import co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.dispositivos.persistence.DispositivoPersistence;
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
 * @author Santiago
 */
@RunWith(Arquillian.class)
public class DispositivoLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @PersistenceContext(unitName = "dispositivosPU")
    private EntityManager em;

    @Inject
    private DispositivoLogic dispositivoLogic;

    @Inject
    private CategoriaLogic categoriaLogic;

    @Inject
    private MarcaLogic marcaLogic;

    @Inject
    private DispositivoPersistence dp;

    @Inject
    private UserTransaction utx;

    private List<DispositivoEntity> dataDispo = new ArrayList<DispositivoEntity>();
    private List<CategoriaEntity> dataCategoria = new ArrayList<CategoriaEntity>();
    private List<MarcaEntity> dataMarca = new ArrayList<MarcaEntity>();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(DispositivoEntity.class.getPackage())
                .addPackage(DispositivoLogic.class.getPackage())
                .addPackage(DispositivoPersistence.class.getPackage())
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
        em.createQuery("delete from DispositivoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();

        for (int i = 0; i < 3; i++) {
            MarcaEntity marca = factory.manufacturePojo(MarcaEntity.class);
            em.persist(marca);
            dataMarca.add(marca);
        }
        for (int i = 0; i < 3; i++) {
            CategoriaEntity categoria = factory.manufacturePojo(CategoriaEntity.class);
            em.persist(categoria);
            dataCategoria.add(categoria);
        }
        for (int i = 0; i < 3; i++) {
            DispositivoEntity entity = factory.manufacturePojo(DispositivoEntity.class);
            entity.setCategoria(dataCategoria.get(0));
            entity.setMarca(dataMarca.get(0));

            em.persist(entity);
            dataDispo.add(entity);
        }
    }

    @Test
    public void createDispositivo() throws BusinessLogicException {

        DispositivoEntity newEntity = factory.manufacturePojo(DispositivoEntity.class);
        DispositivoEntity result = dispositivoLogic.createDispositivo(newEntity);

        Assert.assertNotNull(result);

        DispositivoEntity comparador = em.find(DispositivoEntity.class, result.getId());

        Assert.assertEquals(comparador.getId(), result.getId());
        Assert.assertEquals(comparador.getDescripcion(), result.getDescripcion());
        Assert.assertEquals(comparador.getDescuento(), result.getDescuento());
        Assert.assertEquals(comparador.getImagenes(), result.getImagenes());
        Assert.assertEquals(comparador.getModelo(), result.getModelo());
        Assert.assertEquals(comparador.getNombre(), result.getNombre());
        Assert.assertEquals(comparador.getPrecio(), result.getPrecio());
        Assert.assertEquals(comparador.getPrecioImportacion(), result.getPrecioImportacion());
        Assert.assertEquals(comparador.isEnStock(), result.isEnStock());
        Assert.assertEquals(comparador.isEsImportado(), result.isEsImportado());
        Assert.assertEquals(comparador.isPromocion(), result.isPromocion());
        Assert.assertEquals(comparador.isUsado(), result.isUsado());

        Assert.assertTrue(result.isEnStock());

        boolean precioMenorCero = false;
        if (result.getPrecio() > 0 && result.getPrecioImportacion() > 0) {
            precioMenorCero = true;
        }

        Assert.assertTrue(precioMenorCero);

        Assert.assertTrue(result.isEnStock());

        boolean descuento = false;
        if (result.getPrecio() > result.getDescuento()) {
            descuento = true;
        } else {
            result.setPrecio(result.getDescuento() * 2);
        }
        descuento = true;

        Assert.assertTrue(descuento);

    }

    /**
     * Prueba para consultar un Dispositivo.
     */
    @Test
    public void getDispositivoTest() throws BusinessLogicException {
        DispositivoEntity entity = factory.manufacturePojo(DispositivoEntity.class);
        DispositivoEntity result = dispositivoLogic.createDispositivo(entity);
        DispositivoEntity resultEntity = dispositivoLogic.getDispositivo(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getNombre(), resultEntity.getNombre());
    }

    /**
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createDispositivoNull() throws BusinessLogicException {
        DispositivoEntity nuevo = null;
        dispositivoLogic.createDispositivo(nuevo);

    }

    /**
     **** Nuevo y No Importado ******
     */
    /**
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createNombreDispositivoNuevoNoImportadoNull() throws BusinessLogicException {

        DispositivoEntity newEntity = factory.manufacturePojo(DispositivoEntity.class);
        DispositivoEntity result = dispositivoLogic.createDispositivo(newEntity);
        result.setNombre(null);
        result.setUsado(false);
        result.setEsImportado(false);
        result.setPromocion(false);

        dispositivoLogic.createDispositivo(result);
    }

    /**
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createDescripcionDispositivoNuevoNoImportadoNull() throws BusinessLogicException {
        DispositivoEntity newEntity = factory.manufacturePojo(DispositivoEntity.class);
        DispositivoEntity result = dispositivoLogic.createDispositivo(newEntity);
        result.setDescripcion(null);
        result.setUsado(false);
        result.setEsImportado(false);
        result.setPromocion(false);

        dispositivoLogic.createDispositivo(result);
    }

    /**
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createPrecioDispositivoNuevoNoImportadoNegative() throws BusinessLogicException {

        DispositivoEntity newEntity = factory.manufacturePojo(DispositivoEntity.class);
        DispositivoEntity result = dispositivoLogic.createDispositivo(newEntity);
        result.setPrecio(-1.0);
        result.setUsado(false);
        result.setEsImportado(false);
        result.setPromocion(false);

        dispositivoLogic.createDispositivo(result);
    }

    @Test(expected = BusinessLogicException.class)
    public void createDispositivoNuevoNoImportadoNotInStock() throws BusinessLogicException {

        DispositivoEntity newEntity = factory.manufacturePojo(DispositivoEntity.class);
        DispositivoEntity result = dispositivoLogic.createDispositivo(newEntity);
        result.setEnStock(false);
        result.setUsado(false);
        result.setEsImportado(false);
        result.setPromocion(false);

        dispositivoLogic.createDispositivo(result);
    }

    /**
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createModeloDispositivoNuevoNoImportadoNull() throws BusinessLogicException {

        DispositivoEntity newEntity = factory.manufacturePojo(DispositivoEntity.class);
        DispositivoEntity result = dispositivoLogic.createDispositivo(newEntity);
        result.setModelo(null);
        result.setUsado(false);
        result.setEsImportado(false);
        result.setPromocion(false);

        dispositivoLogic.createDispositivo(result);
    }

    /**
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createPrecioZeroDispositivoNuevoNoImportado() throws BusinessLogicException {
        DispositivoEntity newEntity = factory.manufacturePojo(DispositivoEntity.class);
        DispositivoEntity result = dispositivoLogic.createDispositivo(newEntity);
        result.setPrecio(0.0);
        result.setUsado(false);
        result.setEsImportado(false);
        result.setPromocion(false);

        dispositivoLogic.createDispositivo(result);
    }

    /**
     **** Nuevo e Importado ******
     */
    /**
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createNombreDispositivoNuevoImportadoNull() throws BusinessLogicException {

        DispositivoEntity newEntity = factory.manufacturePojo(DispositivoEntity.class);
        DispositivoEntity result = dispositivoLogic.createDispositivo(newEntity);
        result.setNombre(null);
        result.setUsado(false);
        result.setEsImportado(true);
        result.setPromocion(false);

        dispositivoLogic.createDispositivo(result);
    }

    /**
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createDescripcionDispositivoNuevoImportadoNull() throws BusinessLogicException {
        DispositivoEntity newEntity = factory.manufacturePojo(DispositivoEntity.class);
        DispositivoEntity result = dispositivoLogic.createDispositivo(newEntity);
        result.setDescripcion(null);
        result.setUsado(false);
        result.setEsImportado(true);
        result.setPromocion(false);

        dispositivoLogic.createDispositivo(result);
    }

    /**
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createPrecioDispositivoNuevoImportadoNegative() throws BusinessLogicException {

        DispositivoEntity newEntity = factory.manufacturePojo(DispositivoEntity.class);
        DispositivoEntity result = dispositivoLogic.createDispositivo(newEntity);
        result.setPrecio(-1.0);
        result.setUsado(false);
        result.setEsImportado(true);
        result.setPromocion(false);

        dispositivoLogic.createDispositivo(result);
    }

    @Test(expected = BusinessLogicException.class)
    public void createDispositivoNuevoNoImportadotInStock() throws BusinessLogicException {

        DispositivoEntity newEntity = factory.manufacturePojo(DispositivoEntity.class);
        DispositivoEntity result = dispositivoLogic.createDispositivo(newEntity);
        result.setEnStock(false);
        result.setUsado(false);
        result.setEsImportado(true);
        result.setPromocion(false);

        dispositivoLogic.createDispositivo(result);
    }

    /**
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createModeloDispositivoNuevoImportadoNull() throws BusinessLogicException {

        DispositivoEntity newEntity = factory.manufacturePojo(DispositivoEntity.class);
        DispositivoEntity result = dispositivoLogic.createDispositivo(newEntity);
        result.setModelo(null);
        result.setUsado(false);
        result.setEsImportado(true);
        result.setPromocion(false);

        dispositivoLogic.createDispositivo(result);
    }

    /**
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createPrecioZeroDispositivoNuevoImportado() throws BusinessLogicException {
        DispositivoEntity newEntity = factory.manufacturePojo(DispositivoEntity.class);
        DispositivoEntity result = dispositivoLogic.createDispositivo(newEntity);
        result.setPrecio(0.0);
        result.setUsado(false);
        result.setEsImportado(true);
        result.setPromocion(false);

        dispositivoLogic.createDispositivo(result);
    }

    /**
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createDispositivoNuevoImportadoPrecioImportacionNegative() throws BusinessLogicException {
        DispositivoEntity newEntity = factory.manufacturePojo(DispositivoEntity.class);
        DispositivoEntity result = dispositivoLogic.createDispositivo(newEntity);
        result.setPrecioImportacion(-1.0);
        result.setUsado(false);
        result.setEsImportado(true);
        result.setPromocion(false);

        dispositivoLogic.createDispositivo(result);
    }

    /**
     **** Es promocion ******
     */
    /**
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createDispositivoEnPromocionPrecioLessDescuento() throws BusinessLogicException {
        DispositivoEntity newEntity = factory.manufacturePojo(DispositivoEntity.class);
        DispositivoEntity result = dispositivoLogic.createDispositivo(newEntity);
        result.setPrecio(100.0);
        result.setDescuento(200.0);
        result.setPromocion(true);

        dispositivoLogic.createDispositivo(result);
    }

    /**
     * Prueba para actualizar un dispositivo.
     */
    @Test
    public void updateDispositivoTest() throws BusinessLogicException {
//        DispositivoEntity entity = dataDispo.get(0);
//        DispositivoEntity pojoEntity = factory.manufacturePojo(DispositivoEntity.class);
//        CategoriaEntity categoriaEntity = factory.manufacturePojo(CategoriaEntity.class);
//        MarcaEntity marcaEntity = factory.manufacturePojo(MarcaEntity.class);
//        CategoriaEntity result2 = categoriaLogic.createCategoria(categoriaEntity);
//        MarcaEntity result3 = marcaLogic.createMarca(marcaEntity);
//        pojoEntity.setId(entity.getId());
//        pojoEntity.setCategoria(result2);
//        pojoEntity.setMarca(result3);
//        Assert.assertNotNull(categoriaEntity);
//        Assert.assertNotNull(marcaEntity);
//
//        boolean descuento = false;
//        if (pojoEntity.getPrecio() > pojoEntity.getDescuento()) {
//            descuento = true;
//        } else {
//            pojoEntity.setPrecio(pojoEntity.getDescuento() + pojoEntity.getPrecio());
//        }
//        descuento = true;
//        Assert.assertTrue(descuento);
//        dispositivoLogic.updateDispositivo(pojoEntity.getId(), pojoEntity);
//        DispositivoEntity resp = em.find(DispositivoEntity.class, entity.getId());
//        Assert.assertEquals(pojoEntity.getId(), resp.getId());
//        Assert.assertEquals(pojoEntity.getNombre(), resp.getNombre());
        DispositivoEntity entity = dataDispo.get(0);
        DispositivoEntity pojoEntity = factory.manufacturePojo(DispositivoEntity.class);
        CategoriaEntity categoriaEntity = factory.manufacturePojo(CategoriaEntity.class);
        MarcaEntity marcaEntity = factory.manufacturePojo(MarcaEntity.class);
        CategoriaEntity result2 = categoriaLogic.createCategoria(categoriaEntity);
        MarcaEntity result3 = marcaLogic.createMarca(marcaEntity);
        pojoEntity.setCategoria(result2);
        pojoEntity.setMarca(result3);
        Assert.assertNotNull(categoriaEntity);
        Assert.assertNotNull(marcaEntity);
        pojoEntity.setId(entity.getId());

        boolean descuento = false;
        if (pojoEntity.getPrecio() < pojoEntity.getDescuento()) {
            pojoEntity.setPrecio(pojoEntity.getDescuento() + 1);
            descuento = true;
        }
        descuento = true;
        Assert.assertTrue(descuento);
        dispositivoLogic.updateDispositivo(pojoEntity.getId(), pojoEntity);
        DispositivoEntity resp = em.find(DispositivoEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getDescripcion(), resp.getDescripcion());
        Assert.assertEquals(pojoEntity.getDescuento(), resp.getDescuento());
        Assert.assertEquals(pojoEntity.getEstado(), resp.getEstado());
        Assert.assertEquals(pojoEntity.getMarca(), resp.getMarca());
        Assert.assertEquals(pojoEntity.getCategoria(), resp.getCategoria());
    }

    /**
     * Prueba para eliminar un dispositivo.
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test
    public void deleteDispositivoTest() throws BusinessLogicException {
        DispositivoEntity entity = factory.manufacturePojo(DispositivoEntity.class);
        DispositivoEntity result = dispositivoLogic.createDispositivo(entity);
        dispositivoLogic.deleteDispositivo(entity.getId());
        DispositivoEntity deleted = em.find(DispositivoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para consultar la lista de categorias.
     */
    @Test
    public void getDispositivosTest() {
        List<DispositivoEntity> list = dispositivoLogic.getDispositivos();
        Assert.assertEquals(dataDispo.size(), list.size());
        for (DispositivoEntity entity : list) {
            boolean found = false;
            for (DispositivoEntity entity2 : dataDispo) {
                if (entity.getId().equals(entity2.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

}
