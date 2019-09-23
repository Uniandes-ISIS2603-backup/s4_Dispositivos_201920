/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.test.logic;

import co.edu.uniandes.csw.dispositivos.ejb.DispositivoLogic;
import co.edu.uniandes.csw.dispositivos.ejb.DispositivoMarcaLogic;
import co.edu.uniandes.csw.dispositivos.entities.DispositivoEntity;
import co.edu.uniandes.csw.dispositivos.entities.MarcaEntity;
import co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.dispositivos.persistence.MarcaPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.junit.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Santiago Fajardo
 */
@RunWith(Arquillian.class)
public class DispositivoMarcaLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private DispositivoLogic dispositivoLogic;

    @Inject
    private DispositivoMarcaLogic dispositivoMarcaLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<MarcaEntity> data = new ArrayList<MarcaEntity>();

    private List<DispositivoEntity> dispositivosData = new ArrayList<>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(MarcaEntity.class.getPackage())
                .addPackage(DispositivoLogic.class.getPackage())
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
        em.createQuery("delete from DispositivoEntity").executeUpdate();
        em.createQuery("delete from MarcaEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            DispositivoEntity books = factory.manufacturePojo(DispositivoEntity.class);
            em.persist(books);
            dispositivosData.add(books);
        }
        for (int i = 0; i < 3; i++) {
            MarcaEntity entity = factory.manufacturePojo(MarcaEntity.class);
            em.persist(entity);
            data.add(entity);
            if (i == 0) {
                dispositivosData.get(i).setMarca(entity);
            }
        }
    }

    /**
     * Prueba para remplazar las instancias de Dispositivo asociadas a una
     * instancia de Marca.
     */
    @Test
    public void replaceMarcaTest() {
        DispositivoEntity entity = dispositivosData.get(0);
        dispositivoMarcaLogic.replaceMarca(entity.getId(), data.get(1).getId());
        entity = dispositivoLogic.find(entity.getId());
        Assert.assertEquals(entity.getMarca(), data.get(1));
    }

    /**
     * Prueba para desasociar un Dispositivo existente de una Marca existente
     *
     * @throws BusinessLogicException
     */
    @Test
    public void removeDispositivosTest() throws BusinessLogicException {
        dispositivoMarcaLogic.removeMarca(dispositivosData.get(0).getId());
        DispositivoEntity response = dispositivoLogic.find(dispositivosData.get(0).getId());
        Assert.assertNull(response.getMarca());
    }
}
