/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.test.logic;

import co.edu.uniandes.csw.dispositivos.ejb.DispositivoCategoriaLogic;
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
 * @author Estudiante
 */
@RunWith(Arquillian.class)
public class DispositivoCategoriaLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private DispositivoLogic dispositivoLogic;

    @Inject
    private DispositivoCategoriaLogic dispositivoCategoriaLogic;

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

            }
            em.persist(entity);
            em.merge(dispositivos.get(i));
            data.add(entity);

        }
    }

    /**
     * Prueba para remplazar las instancias de Dispositivo asociadas a una
     * instancia de Categoria .
     */
    @Test
    public void replaceCategoriaTest() {
        DispositivoEntity entity = dispositivos.get(0);
        dispositivoCategoriaLogic.replaceCategoria(entity.getId(), data.get(1).getId());
        entity = dispositivoLogic.getDispositivo(entity.getId());
        Assert.assertEquals(entity.getCategoria(), data.get(1));
    }

    /**
     * Prueba para desasociar un Dispositivo existente de una Categoria
     * existente
     *
     * @throws BusinessLogicException
     */
    @Test
    public void removeCategoriaTest() throws BusinessLogicException {

        DispositivoEntity entity = dispositivos.get(0);
        dispositivoCategoriaLogic.removeCategoria(entity.getId());
        entity = dispositivoLogic.getDispositivo(entity.getId());
        Assert.assertNull(entity.getCategoria());
    }
}
