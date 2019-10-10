/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.test.logic;

import co.edu.uniandes.csw.dispositivos.ejb.CategoriaDispositivoLogic;
import co.edu.uniandes.csw.dispositivos.ejb.CategoriaLogic;
import co.edu.uniandes.csw.dispositivos.ejb.DispositivoLogic;
import co.edu.uniandes.csw.dispositivos.entities.CategoriaEntity;
import co.edu.uniandes.csw.dispositivos.entities.DispositivoEntity;
import co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.dispositivos.persistence.CategoriaPersistence;
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
 * @author JuanL
 */
@RunWith(Arquillian.class)
public class CategoriaDispositivoLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private DispositivoLogic dispositivoLogic;

    @Inject
    private CategoriaLogic categoriaLogic;

    @Inject
    private CategoriaDispositivoLogic categoriaDispositivoLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<CategoriaEntity> data = new ArrayList<CategoriaEntity>();

    private List<DispositivoEntity> dispositivos = new ArrayList<DispositivoEntity>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CategoriaEntity.class.getPackage())
                .addPackage(DispositivoLogic.class.getPackage())
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
        em.createQuery("delete from DispositivoEntity").executeUpdate();
        em.createQuery("delete from CategoriaEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            DispositivoEntity dispositivo = factory.manufacturePojo(DispositivoEntity.class);
            em.persist(dispositivo);
            dispositivos.add(dispositivo);
        }
        for (int i = 0; i < 3; i++) {
            CategoriaEntity entity = factory.manufacturePojo(CategoriaEntity.class);
            if (i == 0) {
                dispositivos.get(i).setCategoria(entity);
                List<DispositivoEntity> dispositivosw=new ArrayList<>();
                dispositivosw.add(dispositivos.get(i));
                entity.setDispositivos(dispositivosw);

            }
            em.persist(entity);
            em.merge(dispositivos.get(i));
            data.add(entity);

        }
    }

    /**
     * Prueba para asociar un dispositivo existente a una categoria.
     */
    @Test
    public void addDispositivoTest() {
        CategoriaEntity entity = data.get(0);
        DispositivoEntity dispositivoEntity = dispositivos.get(1);
        DispositivoEntity response = categoriaDispositivoLogic.addDispositivo(dispositivoEntity.getId(), entity.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(dispositivoEntity.getId(), response.getId());
    }

    /**
     * Prueba para obtener una colección de instancias de Books asociadas a una
     * instancia Editorial.
     */
    @Test
    public void getDispositivosTest() {
        List<DispositivoEntity> list = categoriaDispositivoLogic.getDispositivos(data.get(0).getId());

        Assert.assertEquals(1, list.size());
    }

    /**
     * Prueba para obtener una instancia de dispositivos asociada a una
     * instancia categoria.
     *
     * @throws BusinessLogicException
     */
    @Test
    public void getDispositvosTest() throws BusinessLogicException {

        CategoriaEntity entity = data.get(0);
        DispositivoEntity dispositivoEntity = dispositivos.get(0);

        DispositivoEntity response = categoriaDispositivoLogic.getDispositivo(entity.getId(), dispositivoEntity.getId());

        Assert.assertEquals(dispositivoEntity.getId(), response.getId());
        Assert.assertEquals(dispositivoEntity.getCategoria(), response.getCategoria());
        Assert.assertEquals(dispositivoEntity.getCalificaciones(), response.getCalificaciones());
        Assert.assertEquals(dispositivoEntity.getDescripcion(), response.getDescripcion());
        Assert.assertEquals(dispositivoEntity.getDescuento(), response.getDescuento());
        Assert.assertEquals(dispositivoEntity.getEstado(), response.getEstado());
        Assert.assertEquals(dispositivoEntity.getMarca(), response.getMarca());
        Assert.assertEquals(dispositivoEntity.getNombre(), response.getNombre());
        Assert.assertEquals(dispositivoEntity.getPrecio(), response.getPrecio());
        Assert.assertEquals(dispositivoEntity.getTipo(), response.getTipo());

    }

    /**
     * Prueba para obtener una instancia de dispositivo asociada a una instancia
     * categoria que no le pertenece.
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void getBookNoAsociadoTest() throws BusinessLogicException {
        CategoriaEntity entity = data.get(0);
        DispositivoEntity bookEntity = dispositivos.get(1);
        categoriaDispositivoLogic.getDispositivo(entity.getId(), bookEntity.getId());
    }

    /**
     * Prueba para remplazar las instancias de dispositivos asociadas a una
     * instancia de categoria.
     */
    @Test
    public void replaceBooksTest() {
        CategoriaEntity entity = data.get(0);
        List<DispositivoEntity> list = dispositivos.subList(1, 3);
        categoriaDispositivoLogic.replaceDispositivos(entity.getId(), list);

        entity = categoriaLogic.getCategoria(entity.getId());
        Assert.assertFalse(entity.getDispositivos().contains(dispositivos.get(0)));
        Assert.assertTrue(entity.getDispositivos().contains(dispositivos.get(1)));
        Assert.assertTrue(entity.getDispositivos().contains(dispositivos.get(2)));
    }
}
