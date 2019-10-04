/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.test.logic;

import co.edu.uniandes.csw.dispositivos.ejb.DispositivoLogic;
import co.edu.uniandes.csw.dispositivos.entities.CategoriaEntity;
import co.edu.uniandes.csw.dispositivos.entities.DispositivoEntity;
import co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.dispositivos.persistence.DispositivoPersistence;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
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
    private DispositivoPersistence dp;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(DispositivoEntity.class.getPackage())
                .addPackage(DispositivoLogic.class.getPackage())
                .addPackage(DispositivoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
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
        Assert.assertEquals(comparador.getFactura(), result.getFactura());
        Assert.assertEquals(comparador.getImagenes(), result.getImagenes());
        Assert.assertEquals(comparador.getModelo(), result.getModelo());
        Assert.assertEquals(comparador.getNombre(), result.getNombre());
        Assert.assertEquals(comparador.getPrecio(), result.getPrecio());
        Assert.assertEquals(comparador.getPrecioImportacion(), result.getPrecioImportacion());
        Assert.assertEquals(comparador.isEnStock(), result.isEnStock());
        Assert.assertEquals(comparador.isEsImportado(), result.isEsImportado());
        Assert.assertEquals(comparador.isPromocion(), result.isPromocion());
        Assert.assertEquals(comparador.isUsado(), result.isUsado());
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

}
