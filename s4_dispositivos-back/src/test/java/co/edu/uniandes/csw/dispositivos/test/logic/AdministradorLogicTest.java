/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.test.logic;

import co.edu.uniandes.csw.dispositivos.ejb.AdministradorLogic;
import co.edu.uniandes.csw.dispositivos.entities.AdministradorEntity;
import co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.dispositivos.persistence.AdministradorPersistence;
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
 * Test de lógica de la clase AdminstradorLogic
 * @author Dianis Caro
 */
@RunWith(Arquillian.class)
public class AdministradorLogicTest 
{
    private PodamFactory factory = new PodamFactoryImpl();
    
    @PersistenceContext(unitName = "dispositivosPU")
    private EntityManager em;

    @Inject 
    private AdministradorLogic adminLogic;

    @Inject
    private UserTransaction utx;

    private List<AdministradorEntity> data = new ArrayList<AdministradorEntity>();
    /**
     * Configuración inicial de la prueba
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
     * Inserta los datos iniciales para el correcto funcionamiento de las pruebas
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) 
        {
            AdministradorEntity adminEntity = factory.manufacturePojo(AdministradorEntity.class);
            adminEntity.setCorreo("correoW"+i+"@wireless.com");
            em.persist(adminEntity);
            data.add(adminEntity);
        }
    }
    /**
     * Limpia las tablas que están implicadas en la prueba
     */
    private void clearData() {
        em.createQuery("delete from AdministradorEntity").executeUpdate();
    }
    /**
     * Construye el despliegue de la prueba a realizar
     * @return jar, es decir JavaArchive.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(AdministradorEntity.class.getPackage())
                .addPackage(AdministradorPersistence.class.getPackage())
                .addPackage(AdministradorLogic.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    /**
     * Test para crear un administrador con todas las reglas de negocio
     * @throws BusinessLogicException si una regla de negocio no se cumple
     */
    @Test
    public  void createAdministrador() throws BusinessLogicException
    {
        AdministradorEntity adminEntity = factory.manufacturePojo(AdministradorEntity.class);
        adminEntity.setCorreo("correoW@wireless.com");
        AdministradorEntity result = adminLogic.createAdministrador(adminEntity);
        Assert.assertNotNull(result);
        
        AdministradorEntity entity = em.find(AdministradorEntity.class, result.getId());
        Assert.assertEquals(entity.getUsuario(), result.getUsuario());
        Assert.assertEquals(entity.getContrasena(), result.getContrasena());
        Assert.assertEquals(entity.getCorreo(), result.getCorreo());
    }
    /**
     * Test para crear un administrador sin usuario
     * @throws BusinessLogicException si una regla de negocio no se cumple
     */
    @Test(expected = BusinessLogicException.class)
    public  void createAdministradorUsuarioNull() throws BusinessLogicException
    {
        AdministradorEntity adminEntity = factory.manufacturePojo(AdministradorEntity.class);
        adminEntity.setUsuario(null);
        adminLogic.createAdministrador(adminEntity);
    }
    /**
     * Test para crear un administrador sin contrseña
     * @throws BusinessLogicException si una regla de negocio no se cumple
     */
    @Test(expected = BusinessLogicException.class)
    public  void createAdministradorContrasenaNull() throws BusinessLogicException
    {
        AdministradorEntity adminEntity = factory.manufacturePojo(AdministradorEntity.class);
        adminEntity.setContrasena(null);
        adminLogic.createAdministrador(adminEntity);
    }
    /**
     * Test para crear un administrador sin correo
     * @throws BusinessLogicException si una regla de negocio no se cumple
     */
    @Test(expected = BusinessLogicException.class)
    public  void createAdministradorCorreoNull() throws BusinessLogicException
    {
        AdministradorEntity adminEntity = factory.manufacturePojo(AdministradorEntity.class);
        adminEntity.setCorreo(null);
        adminLogic.createAdministrador(adminEntity);
    }
    /**
     * Test para crear un administrador sin correo corporativo
     * @throws BusinessLogicException si una regla de negocio no se cumple
     */
    @Test(expected = BusinessLogicException.class)
    public  void createAdministradorCorreoNoCorp() throws BusinessLogicException
    {
        AdministradorEntity adminEntity = factory.manufacturePojo(AdministradorEntity.class);
        adminEntity.setCorreo("correo@uniandes.edu.co");
        adminLogic.createAdministrador(adminEntity);
    }
    /**
     * Test para crear un administrador con correo corporativo existente
     * @throws BusinessLogicException si una regla de negocio no se cumple
     */
    @Test(expected = BusinessLogicException.class)
    public  void createAdministradorCorreoExistente() throws BusinessLogicException
    {
        AdministradorEntity adminEntity = factory.manufacturePojo(AdministradorEntity.class);
        adminEntity.setCorreo("correo@wireless.com");
        AdministradorEntity result = adminLogic.createAdministrador(adminEntity);
        Assert.assertNotNull(result);
        
        AdministradorEntity adminEntity2 = factory.manufacturePojo(AdministradorEntity.class);
        adminEntity2.setCorreo("correo@wireless.com");
        adminLogic.createAdministrador(adminEntity2);
    }
     /**
     * Test para crear un administrador con usuario existente
     * @throws BusinessLogicException si una regla de negocio no se cumple
     */
    @Test(expected = BusinessLogicException.class)
    public  void createAdministradorUsuarioExistente() throws BusinessLogicException
    {
        AdministradorEntity adminEntity = factory.manufacturePojo(AdministradorEntity.class);
        adminEntity.setCorreo("correo1@wireless.com");
        adminEntity.setUsuario("admin");
        AdministradorEntity result = adminLogic.createAdministrador(adminEntity);
        Assert.assertNotNull(result);
        
        AdministradorEntity adminEntity2 = factory.manufacturePojo(AdministradorEntity.class);
        adminEntity2.setCorreo("correo2@wireless.com");
        adminEntity2.setUsuario("admin");
        adminLogic.createAdministrador(adminEntity2);
    }
    
}
