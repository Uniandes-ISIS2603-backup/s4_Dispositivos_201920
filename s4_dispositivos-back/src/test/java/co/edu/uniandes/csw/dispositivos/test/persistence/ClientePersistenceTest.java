/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.test.persistence;

import co.edu.uniandes.csw.dispositivos.entities.ClienteEntity;
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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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
public class ClientePersistenceTest {

    @Inject
    private ClientePersistence mp;

    @PersistenceContext(unitName = "dispositivosPU")
    private EntityManager em;

    @Inject
    UserTransaction utx;

    private List<ClienteEntity> data = new ArrayList<>();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ClienteEntity.class.getPackage())
                .addPackage(ClientePersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    @Test
    public void createTest() {
        PodamFactory factory = new PodamFactoryImpl();
        ClienteEntity clienteEntity = factory.manufacturePojo(ClienteEntity.class);
        ClienteEntity result = mp.create(clienteEntity);
        Assert.assertNotNull(result);

        ClienteEntity entity = em.find(ClienteEntity.class, result.getId());
        Assert.assertEquals(clienteEntity.getApellido(), entity.getApellido());
        Assert.assertEquals(clienteEntity.getCedula(), entity.getCedula());
        Assert.assertEquals(clienteEntity.getContrasena(), entity.getContrasena());
        Assert.assertEquals(clienteEntity.getCorreoElectronico(), entity.getCorreoElectronico());
        Assert.assertEquals(clienteEntity.getDireccion(), entity.getDireccion());
        Assert.assertEquals(clienteEntity.getNombre(), entity.getNombre());
        Assert.assertEquals(clienteEntity.getUsuario(), entity.getUsuario());
    }

    @Before
    public void configTest() {
        try {
            utx.begin();
            em.joinTransaction();
            clearData();
            PodamFactory factory = new PodamFactoryImpl();
            for (int i = 0; i < 3; i++) {
                ClienteEntity entity = factory.manufacturePojo(ClienteEntity.class);
                em.persist(entity);
                data.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearData() {
        em.createQuery("delete from ClienteEntity").executeUpdate();
    }

    @Test
    public void findClientesTest() {
        List<ClienteEntity> list = mp.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (ClienteEntity ent : list) {
            boolean found = false;
            for (ClienteEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
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
    public void findClienteTest() {
        ClienteEntity entity = data.get(0);
        ClienteEntity newEntity = mp.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getApellido(), newEntity.getApellido());
        Assert.assertEquals(entity.getCedula(), newEntity.getCedula());
        Assert.assertEquals(entity.getContrasena(), newEntity.getContrasena());
        Assert.assertEquals(entity.getCorreoElectronico(), newEntity.getCorreoElectronico());
        Assert.assertEquals(entity.getDireccion(), newEntity.getDireccion());
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
        Assert.assertEquals(entity.getUsuario(), newEntity.getUsuario());
    }

    /**
     * Prueba para actualizar un cliente.
     */
    @Test
    public void updateClienteTest() {
        ClienteEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);

        newEntity.setId(entity.getId());

        mp.update(newEntity);

        ClienteEntity resp = em.find(ClienteEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getApellido(), resp.getApellido());
        Assert.assertEquals(newEntity.getCedula(), resp.getCedula());
        Assert.assertEquals(newEntity.getContrasena(), resp.getContrasena());
        Assert.assertEquals(newEntity.getCorreoElectronico(), resp.getCorreoElectronico());
        Assert.assertEquals(newEntity.getDireccion(), resp.getDireccion());
        Assert.assertEquals(newEntity.getNombre(), resp.getNombre());
        Assert.assertEquals(newEntity.getUsuario(), resp.getUsuario());
    }

    /**
     * Prueba para eliminar un cliente.
     */
    @Test
    public void deleteClienteTest() {
        ClienteEntity entity = data.get(0);
        mp.delete(entity.getId());
        ClienteEntity deleted = em.find(ClienteEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para crear un cliente.
     */
    @Test
    public void crearClienteTest() {
        ClienteEntity clientePrueba = new ClienteEntity("nombre", "apellido", "email", 2.3, "direccion", "usuario", "contrasena");
        ClienteEntity clientePrueba2 = new ClienteEntity("nombre", "apellido", "email", 2.3, "direccion", "usuario", "contrasena");
        ClienteEntity clientePrueba3 = new ClienteEntity("nombre", "apellido", "email", 2.4, "direccion", "usuario", "contrasena");

        Assert.assertEquals("nombre", clientePrueba.getNombre());
        Assert.assertEquals("apellido", clientePrueba.getApellido());
        Assert.assertEquals("email", clientePrueba.getCorreoElectronico());
        Assert.assertEquals(2.3, clientePrueba.getCedula(), 0);
        Assert.assertEquals("direccion", clientePrueba.getDireccion());
        Assert.assertEquals("usuario", clientePrueba.getUsuario());
        Assert.assertEquals("contrasena", clientePrueba.getContrasena());
        Assert.assertEquals(clientePrueba3.hashCode(), clientePrueba3.hashCode());
        Assert.assertTrue(clientePrueba.equals(clientePrueba2));
    }

    /**
     * Prueba para encontrar un cliente por su cedula.
     */
    @Test
    public void buscarClientePorCedulaTest() {
        ClienteEntity clientePrueba = new ClienteEntity("nombre", "apellido", "email", 2.3, "direccion", "usuario", "contrasena");
        mp.create(clientePrueba);
        Assert.assertNotNull(mp.findByCedula(2.3));
        Assert.assertNull(mp.findByCedula(3.5));
        Assert.assertEquals("nombre", clientePrueba.getNombre());
        Assert.assertEquals("apellido", clientePrueba.getApellido());
        Assert.assertEquals("email", clientePrueba.getCorreoElectronico());
        Assert.assertEquals(2.3, clientePrueba.getCedula(), 0);
        Assert.assertEquals("direccion", clientePrueba.getDireccion());
        Assert.assertEquals("usuario", clientePrueba.getUsuario());
        Assert.assertEquals("contrasena", clientePrueba.getContrasena());
    }

    /**
     * Prueba para encontrar un cliente por su email.
     */
    @Test
    public void buscarClientePorEmailTest() {
        ClienteEntity clientePrueba = new ClienteEntity("nombre", "apellido", "email", 2.3, "direccion", "usuario", "contrasena");
        mp.create(clientePrueba);
        Assert.assertNotNull(mp.findByEmail("email"));
        Assert.assertNull(mp.findByEmail("eamil"));
        Assert.assertEquals("nombre", clientePrueba.getNombre());
        Assert.assertEquals("apellido", clientePrueba.getApellido());
        Assert.assertEquals("email", clientePrueba.getCorreoElectronico());
        Assert.assertEquals(2.3, clientePrueba.getCedula(), 0);
        Assert.assertEquals("direccion", clientePrueba.getDireccion());
        Assert.assertEquals("usuario", clientePrueba.getUsuario());
        Assert.assertEquals("contrasena", clientePrueba.getContrasena());
    }

    /**
     * Prueba para encontrar un cliente por su usuario.
     */
    @Test
    public void buscarClientePorUsuarioTest() {
        ClienteEntity clientePrueba = new ClienteEntity("nombre", "apellido", "email", 2.3, "direccion", "usuario", "contrasena");
        mp.create(clientePrueba);
        Assert.assertNotNull(mp.findByUsuario("usuario"));
        Assert.assertNull(mp.findByUsuario("oirausu"));
        Assert.assertEquals("nombre", clientePrueba.getNombre());
        Assert.assertEquals("apellido", clientePrueba.getApellido());
        Assert.assertEquals("email", clientePrueba.getCorreoElectronico());
        Assert.assertEquals(2.3, clientePrueba.getCedula(), 0);
        Assert.assertEquals("direccion", clientePrueba.getDireccion());
        Assert.assertEquals("usuario", clientePrueba.getUsuario());
        Assert.assertEquals("contrasena", clientePrueba.getContrasena());
    }
}
