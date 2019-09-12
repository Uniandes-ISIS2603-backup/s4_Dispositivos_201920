/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.test.logic;

import co.edu.uniandes.csw.dispositivos.ejb.ClienteLogic;
import co.edu.uniandes.csw.dispositivos.entities.ClienteEntity;
import co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.dispositivos.persistence.ClientePersistence;
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
public class ClienteLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @PersistenceContext(unitName = "dispositivosPU")
    private EntityManager em;

    @Inject
    private ClienteLogic clienteLogic;

    @Inject
    private UserTransaction utx;

    private List<ClienteEntity> data = new ArrayList<ClienteEntity>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ClienteEntity.class.getPackage())
                .addPackage(ClienteLogic.class.getPackage())
                .addPackage(ClientePersistence.class.getPackage())
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
        em.createQuery("delete from ClienteEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ClienteEntity entity = factory.manufacturePojo(ClienteEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }

    @Test
    public void createClienteTest() throws BusinessLogicException {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        ClienteEntity result = clienteLogic.createCliente(newEntity);
        Assert.assertNotNull(result);

        ClienteEntity entity = em.find(ClienteEntity.class, result.getId());
        Assert.assertEquals(result.getApellido(), entity.getApellido());
        Assert.assertEquals(result.getCedula(), entity.getCedula());
        Assert.assertEquals(result.getContrasena(), entity.getContrasena());
        Assert.assertEquals(result.getCorreoElectronico(), entity.getCorreoElectronico());
        Assert.assertEquals(result.getDireccion(), entity.getDireccion());
        Assert.assertEquals(result.getNombre(), entity.getNombre());
        Assert.assertEquals(result.getUsuario(), entity.getUsuario());
    }

    @Test(expected = BusinessLogicException.class)
    public void createClienteNombreNullTest() throws BusinessLogicException {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setNombre(null);
        ClienteEntity result = clienteLogic.createCliente(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createClienteApellidoNullTest() throws BusinessLogicException {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setApellido(null);
        ClienteEntity result = clienteLogic.createCliente(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createClienteCorreoNullTest() throws BusinessLogicException {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setCorreoElectronico(null);
        ClienteEntity result = clienteLogic.createCliente(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createClienteDireccionNullTest() throws BusinessLogicException {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setDireccion(null);
        ClienteEntity result = clienteLogic.createCliente(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createClienteUsuarioNullTest() throws BusinessLogicException {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setUsuario(null);
        ClienteEntity result = clienteLogic.createCliente(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createClienteContrasenaNullTest() throws BusinessLogicException {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setContrasena(null);
        ClienteEntity result = clienteLogic.createCliente(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createClienteCedulaNullTest() throws BusinessLogicException {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setCedula(null);
        ClienteEntity result = clienteLogic.createCliente(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createClienteNombreVacioTest() throws BusinessLogicException {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setNombre("                        ");
        ClienteEntity result = clienteLogic.createCliente(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createClienteApellidoVacioTest() throws BusinessLogicException {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setApellido("");
        ClienteEntity result = clienteLogic.createCliente(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createClienteCorreoVacioTest() throws BusinessLogicException {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setCorreoElectronico("  ");
        ClienteEntity result = clienteLogic.createCliente(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createClienteDireccionVaciaTest() throws BusinessLogicException {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setDireccion("                                                                            ");
        ClienteEntity result = clienteLogic.createCliente(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createClienteUsuarioVacioTest() throws BusinessLogicException {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setUsuario("                                                                                                                         ");
        ClienteEntity result = clienteLogic.createCliente(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createClienteContrasenaVaciaTest() throws BusinessLogicException {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setContrasena("");
        ClienteEntity result = clienteLogic.createCliente(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createClienteCedulaNegOCeroTest() throws BusinessLogicException {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setCedula(-0.1);
        ClienteEntity result = clienteLogic.createCliente(newEntity);
    }

    /**
     * Prueba para crear un cliente con el mismo número de cèdula de un cliente
     * que ya existe.
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createClienteMismaCedulaTest() throws BusinessLogicException {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setCedula(data.get(0).getCedula());
        clienteLogic.createCliente(newEntity);
    }

    /**
     * Prueba para crear un cliente con el mismo email de un cliente que ya
     * existe.
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createClienteMismoEmailTest() throws BusinessLogicException {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setCorreoElectronico(data.get(0).getCorreoElectronico());
        clienteLogic.createCliente(newEntity);
    }

    /**
     * Prueba para consultar la lista de clientes.
     */
    @Test
    public void getClientesTest() {
        List<ClienteEntity> list = clienteLogic.getClientes();
        Assert.assertEquals(data.size(), list.size());
        for (ClienteEntity entity : list) {
            boolean found = false;
            for (ClienteEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un cliente.
     */
    @Test
    public void getClienteTest() {
        ClienteEntity entity = data.get(0);
        ClienteEntity resultEntity = clienteLogic.getCliente(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(resultEntity.getNombre(), entity.getNombre());
        Assert.assertEquals(resultEntity.getApellido(), entity.getApellido());
        Assert.assertEquals(resultEntity.getCorreoElectronico(), entity.getCorreoElectronico());
        Assert.assertEquals(resultEntity.getCedula(), entity.getCedula(), 0);
        Assert.assertEquals(resultEntity.getDireccion(), entity.getDireccion());
        Assert.assertEquals(resultEntity.getUsuario(), entity.getUsuario());
        Assert.assertEquals(resultEntity.getContrasena(), entity.getContrasena());
    }

    /**
     * Prueba para actualizar un cliente.
     *
     * @throws BusinessLogicException
     */
    @Test
    public void updateClienteTest() throws BusinessLogicException {
        ClienteEntity entity = data.get(0);
        ClienteEntity pojoEntity = factory.manufacturePojo(ClienteEntity.class);
        pojoEntity.setId(entity.getId());
        clienteLogic.updateCliente(pojoEntity.getId(), pojoEntity);
        ClienteEntity resp = em.find(ClienteEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getNombre(), resp.getNombre());
        Assert.assertEquals(pojoEntity.getApellido(), resp.getApellido());
        Assert.assertEquals(pojoEntity.getCorreoElectronico(), resp.getCorreoElectronico());
        Assert.assertEquals(pojoEntity.getCedula(), resp.getCedula(), 0);
        Assert.assertEquals(pojoEntity.getDireccion(), resp.getDireccion());
        Assert.assertEquals(pojoEntity.getUsuario(), resp.getUsuario());
        Assert.assertEquals(pojoEntity.getContrasena(), resp.getContrasena());
    }

    /**
     * Prueba para eliminar un cliente.
     */
    @Test
    public void deleteClienteTest() {
        ClienteEntity entity = data.get(1);
        clienteLogic.deleteCliente(entity.getId());
        ClienteEntity deleted = em.find(ClienteEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para validar crear un cliente con email inválido .
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void crearClienteEmailInvalidoTest() throws BusinessLogicException {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setCorreoElectronico("joesmi:;th@example.com.co");
        ClienteEntity result = clienteLogic.createCliente(newEntity);
    }

}
