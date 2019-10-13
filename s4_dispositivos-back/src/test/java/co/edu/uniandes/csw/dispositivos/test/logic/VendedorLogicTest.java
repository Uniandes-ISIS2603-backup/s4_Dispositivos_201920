/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.test.logic;

import co.edu.uniandes.csw.dispositivos.ejb.VendedorLogic;
import co.edu.uniandes.csw.dispositivos.entities.VendedorEntity;
import co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.dispositivos.persistence.VendedorPersistence;
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
 * @author Zharet Bautista Montes
 */
@RunWith(Arquillian.class)
public class VendedorLogicTest {

    /**
     * Persistencia donde operan los tests
     */
    @PersistenceContext
    protected EntityManager vrm;

    /**
     * @return Contexto con el que se ejecutan los tests
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(VendedorEntity.class.getPackage())
                .addPackage(VendedorLogic.class.getPackage())
                .addPackage(VendedorPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * Creador de entidades de prueba
     */
    private PodamFactory vrlfactory = new PodamFactoryImpl();

    /**
     * Relación con la lógica de la clase
     */
    @Inject
    private VendedorLogic vrlogic;

    /**
     * Auxiliar de transacción
     */
    @Inject
    UserTransaction utxn;

    /**
     * Contenedor auxiliar con las entidades de la clase
     */
    private final List<VendedorEntity> vrlist = new ArrayList<>();

    /**
     * Establece las configuraciones iniciales del test
     */
    @Before
    public void prepareTest() {
        try {
            utxn.begin();
            vrm.joinTransaction();
            vrm.createQuery("delete from VendedorEntity").executeUpdate();
            PodamFactory vrfactory = new PodamFactoryImpl();
            for (int i = 0; i < 5; i++) {
                VendedorEntity vendedor = vrfactory.manufacturePojo(VendedorEntity.class);
                vrm.persist(vendedor);
                vrlist.add(vendedor);
            }
            utxn.commit();
        } catch (Exception e1) {
            e1.printStackTrace();
            try {
                utxn.rollback();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /**
     * Test de la validación de agregar un vendedor
     * @throws co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException
     */
    @Test
    public void createVendedorTest() throws BusinessLogicException {
        VendedorEntity vendedor = vrlfactory.manufacturePojo(VendedorEntity.class);
        VendedorEntity obtainedvr = vrlogic.createVendedor(vendedor);
        Assert.assertNotNull(obtainedvr);
    }

    /**
     * Test de falla de agregar un vendedor sin nombre
     * @throws co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createNullFirstnameVendedorTest() throws BusinessLogicException {
        VendedorEntity vendedor = vrlfactory.manufacturePojo(VendedorEntity.class);
        vendedor.setNombre(null);
        vrlogic.createVendedor(vendedor);
    }

    /**
     * Test de falla de agregar un vendedor sin apellido
     * @throws co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createNullLastnameVendedorTest() throws BusinessLogicException {
        VendedorEntity vendedor = vrlfactory.manufacturePojo(VendedorEntity.class);
        vendedor.setApellido(null);
        vrlogic.createVendedor(vendedor);
    }

    /**
     * Test de falla de agregar un vendedor sin usuario
     * @throws co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createNullUserVendedorTest() throws BusinessLogicException {
        VendedorEntity vendedor = vrlfactory.manufacturePojo(VendedorEntity.class);
        vendedor.setUsuario(null);
        vrlogic.createVendedor(vendedor);
    }

    /**
     * Test de falla de agregar un vendedor sin contraseña
     * @throws co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createNullPasswordVendedorTest() throws BusinessLogicException {
        VendedorEntity vendedor = vrlfactory.manufacturePojo(VendedorEntity.class);
        vendedor.setContrasena(null);
        vrlogic.createVendedor(vendedor);
    }

    /**
     * Test de falla de agregar un vendedor sin correo electrónico
     * @throws co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createNullEmailVendedorTest() throws BusinessLogicException {
        VendedorEntity vendedor = vrlfactory.manufacturePojo(VendedorEntity.class);
        vendedor.setCorreoElectronico(null);
        vrlogic.createVendedor(vendedor);
    }

    /**
     * Test de falla de agregar un vendedor sin cédula
     * @throws co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createNullCedulaVendedorTest() throws BusinessLogicException {
        VendedorEntity vendedor = vrlfactory.manufacturePojo(VendedorEntity.class);
        vendedor.setCedula(null);
        vrlogic.createVendedor(vendedor);
    }
    
    /**
     * Test de falla de agregar un vendedor sin celular
     * @throws co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createNegativeCelularVendedorTest() throws BusinessLogicException {
        VendedorEntity vendedor = vrlfactory.manufacturePojo(VendedorEntity.class);
        vendedor.setCelular(null);
        vrlogic.createVendedor(vendedor);
    }
    
    /**
     * Test de falla de agregar un vendedor con cédula repetida
     * @throws co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createVendedorClonedCedulaTest() throws BusinessLogicException {
        VendedorEntity ref = vrlist.get(0);
        VendedorEntity vendedor = vrlfactory.manufacturePojo(VendedorEntity.class);
        vendedor.setCedula(ref.getCedula());
        vrlogic.createVendedor(vendedor);
    }
    
    /**
     * Test de falla de agregar un vendedor con usuario repetido
     * @throws co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createVendedorClonedUserTest() throws BusinessLogicException {
        VendedorEntity ref = vrlist.get(0);
        VendedorEntity vendedor = vrlfactory.manufacturePojo(VendedorEntity.class);
        vendedor.setUsuario(ref.getUsuario());
        vrlogic.createVendedor(vendedor);
    }
    
    /**
     * Test de falla de agregar un vendedor con correo electrónico repetido
     * @throws co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createVendedorClonedEmailTest() throws BusinessLogicException {
        VendedorEntity ref = vrlist.get(0);
        VendedorEntity vendedor = vrlfactory.manufacturePojo(VendedorEntity.class);
        vendedor.setCorreoElectronico(ref.getCorreoElectronico());
        vrlogic.createVendedor(vendedor);
    }
    
    /**
     * Test de la validación de buscar un vendedor
     */
    @Test
    public void findVendedorTest() {
        VendedorEntity ref = vrlist.get(0), block = vrlogic.findVendedor(ref.getId());
        Assert.assertNotNull(block);
        Assert.assertEquals(block.getId(), ref.getId());
        Assert.assertEquals(block.getApellido(), ref.getApellido());
        Assert.assertEquals(block.getNombre(), ref.getNombre());
        Assert.assertEquals(block.getCorreoElectronico(), ref.getCorreoElectronico());
        Assert.assertEquals(block.getContrasena(), ref.getContrasena());
        Assert.assertEquals(block.getUsuario(), ref.getUsuario());
        Assert.assertEquals(block.getCelular(), ref.getCelular(), 0);
        Assert.assertEquals(block.getCedula(), ref.getCedula(), 0);
    }
    
    /**
     * Test de la validación de buscar un vendedor por cédula
     * @throws co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException
     */
    @Test
    public void findVendedorByCedulaTest() throws BusinessLogicException {
        VendedorEntity ref = vrlist.get(0), block = vrlogic.findByCedulaVendedor(ref.getCedula());
        Assert.assertNotNull(block);
        Assert.assertEquals(block.getId(), ref.getId());
        Assert.assertEquals(block.getApellido(), ref.getApellido());
        Assert.assertEquals(block.getNombre(), ref.getNombre());
        Assert.assertEquals(block.getCorreoElectronico(), ref.getCorreoElectronico());
        Assert.assertEquals(block.getContrasena(), ref.getContrasena());
        Assert.assertEquals(block.getUsuario(), ref.getUsuario());
        Assert.assertEquals(block.getCelular(), ref.getCelular(), 0);
        Assert.assertEquals(block.getCedula(), ref.getCedula(), 0);
    }
    
    /**
     * Test de falla de buscar un vendedor por cédula nula
     * @throws co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void findVendedorByNullCedulaTest() throws BusinessLogicException {
        VendedorEntity notID = vrlist.get(0);
        notID.setCedula(null);
        vrlogic.findByCedulaVendedor(notID.getCedula());
    }
    
    /**
     * Test de la validación de buscar un vendedor por usuario
     * @throws co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException
     */
    @Test
    public void findVendedorByUsuarioTest() throws BusinessLogicException {
        VendedorEntity ref = vrlist.get(0), block = vrlogic.findByUsuarioVendedor(ref.getUsuario());
        Assert.assertNotNull(block);
        Assert.assertEquals(block.getId(), ref.getId());
        Assert.assertEquals(block.getApellido(), ref.getApellido());
        Assert.assertEquals(block.getNombre(), ref.getNombre());
        Assert.assertEquals(block.getCorreoElectronico(), ref.getCorreoElectronico());
        Assert.assertEquals(block.getContrasena(), ref.getContrasena());
        Assert.assertEquals(block.getUsuario(), ref.getUsuario());
        Assert.assertEquals(block.getCelular(), ref.getCelular(), 0);
        Assert.assertEquals(block.getCedula(), ref.getCedula(), 0);
    }
    
    /**
     * Test de falla de buscar un vendedor por usuario nulo
     * @throws co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void findVendedorByNullUsuarioTest() throws BusinessLogicException {
        VendedorEntity notUser = vrlist.get(0);
        notUser.setUsuario(null);
        vrlogic.findByUsuarioVendedor(notUser.getUsuario());
    }
    
    /**
     * Test de la validación de buscar un vendedor por correo electrónico
     * @throws co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException
     */
    @Test
    public void findVendedorByEmailTest() throws BusinessLogicException {
        VendedorEntity ref = vrlist.get(0), block = vrlogic.findByEmailVendedor(ref.getCorreoElectronico());
        Assert.assertNotNull(block);
        Assert.assertEquals(block.getId(), ref.getId());
        Assert.assertEquals(block.getApellido(), ref.getApellido());
        Assert.assertEquals(block.getNombre(), ref.getNombre());
        Assert.assertEquals(block.getCorreoElectronico(), ref.getCorreoElectronico());
        Assert.assertEquals(block.getContrasena(), ref.getContrasena());
        Assert.assertEquals(block.getUsuario(), ref.getUsuario());
        Assert.assertEquals(block.getCelular(), ref.getCelular(), 0);
        Assert.assertEquals(block.getCedula(), ref.getCedula(), 0);
    }
    
    /**
     * Test de falla de buscar un vendedor por correo electrónico nulo
     * @throws co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void findVendedorByNullEmailTest() throws BusinessLogicException {
        VendedorEntity notEmail = vrlist.get(0);
        notEmail.setCorreoElectronico(null);
        vrlogic.findByEmailVendedor(notEmail.getCorreoElectronico());
    }

    /**
     * Test de la validación de encontrar todos los vendedores
     */
    @Test
    public void findAllVendedoresTest() {
        List<VendedorEntity> allgotten = vrlogic.findAllVendedores();
        Assert.assertEquals(allgotten.size(), vrlist.size());
        for (VendedorEntity vrblock : allgotten) {
            boolean ticked = false;
            for (VendedorEntity vrref : vrlist) {
                if (vrblock.getId().equals(vrref.getId())) {
                    ticked = true;
                }
            }
            Assert.assertTrue(ticked);
        }
    }

    /**
     * Test de la validación de cambiar un vendedor
     * @throws co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException
     */
    @Test
    public void updateVendedorTest() throws BusinessLogicException {
        VendedorEntity vendedor = vrlist.get(0);
        VendedorEntity updating = vrlfactory.manufacturePojo(VendedorEntity.class);
        updating.setId(vendedor.getId());
        vrlogic.updateVendedor(updating);
        VendedorEntity updated = vrm.find(VendedorEntity.class, vendedor.getId());
        Assert.assertEquals(updating.getId(), updated.getId());
        Assert.assertEquals(updating.getApellido(), updated.getApellido());
        Assert.assertEquals(updating.getNombre(), updated.getNombre());
        Assert.assertEquals(updating.getCorreoElectronico(), updated.getCorreoElectronico());
        Assert.assertEquals(updating.getContrasena(), updated.getContrasena());
        Assert.assertEquals(updating.getUsuario(), updated.getUsuario());
        Assert.assertEquals(updating.getCelular(), updated.getCelular(), 0);
        Assert.assertEquals(updating.getCedula(), updated.getCedula(), 0);
    }
    
    /**
     * Test de falla de cambiar a un vendedor sin nombre
     * @throws co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateNullFirstnameVendedorTest() throws BusinessLogicException {
        VendedorEntity ref = vrlist.get(0);
        VendedorEntity wrongvr = vrlfactory.manufacturePojo(VendedorEntity.class);
        wrongvr.setId(ref.getId());
        wrongvr.setNombre(null);
        vrlogic.updateVendedor(wrongvr);
    }

    /**
     * Test de falla de cambiar a un vendedor sin apellido
     * @throws co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateNullLastnameVendedorTest() throws BusinessLogicException {
        VendedorEntity ref = vrlist.get(0);
        VendedorEntity wrongvr = vrlfactory.manufacturePojo(VendedorEntity.class);
        wrongvr.setId(ref.getId());
        wrongvr.setApellido(null);
        vrlogic.updateVendedor(wrongvr);
    }

    /**
     * Test de falla de cambiar a un vendedor sin usuario
     * @throws co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateNullUserVendedorTest() throws BusinessLogicException {
        VendedorEntity ref = vrlist.get(0);
        VendedorEntity wrongvr = vrlfactory.manufacturePojo(VendedorEntity.class);
        wrongvr.setId(ref.getId());
        wrongvr.setUsuario(null);
        vrlogic.updateVendedor(wrongvr);
    }

    /**
     * Test de falla de cambiar a un vendedor sin contraseña
     * @throws co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateNullPasswordVendedorTest() throws BusinessLogicException {
        VendedorEntity ref = vrlist.get(0);
        VendedorEntity wrongvr = vrlfactory.manufacturePojo(VendedorEntity.class);
        wrongvr.setId(ref.getId());
        wrongvr.setContrasena(null);
        vrlogic.updateVendedor(wrongvr);
    }

    /**
     * Test de falla de cambiar a un vendedor sin correo electrónico
     * @throws co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateNullEmailVendedorTest() throws BusinessLogicException {
        VendedorEntity ref = vrlist.get(0);
        VendedorEntity wrongvr = vrlfactory.manufacturePojo(VendedorEntity.class);
        wrongvr.setId(ref.getId());
        wrongvr.setCorreoElectronico(null);
        vrlogic.updateVendedor(wrongvr);
    }

    /**
     * Test de falla de cambiar a un vendedor sin cédula
     * @throws co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateNullVendedorTest() throws BusinessLogicException {
        VendedorEntity ref = vrlist.get(0);
        VendedorEntity wrongvr = vrlfactory.manufacturePojo(VendedorEntity.class);
        wrongvr.setId(ref.getId());
        wrongvr.setCedula(null);
        vrlogic.updateVendedor(wrongvr);
    }
    
    /**
     * Test de falla de cambiar a un vendedor sin celular
     * @throws co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateNullCellphoneVendedorTest() throws BusinessLogicException {
        VendedorEntity ref = vrlist.get(0);
        VendedorEntity wrongvr = vrlfactory.manufacturePojo(VendedorEntity.class);
        wrongvr.setId(ref.getId());
        wrongvr.setCelular(null);
        vrlogic.updateVendedor(wrongvr);
    }

    /**
     * Test de la validación de borrar un vendedor
     */
    @Test
    public void deleteVendedorTest() {
        VendedorEntity vrentity = vrlist.get(0);
        vrlogic.deleteVendedor(vrentity.getId());
        VendedorEntity gonevr = vrm.find(VendedorEntity.class, vrentity.getId());
        Assert.assertNull(gonevr);
    }
}
