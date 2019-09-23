/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.test.logic;

import co.edu.uniandes.csw.dispositivos.ejb.DispositivoLogic;
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
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createNombreDispositivoNull() throws BusinessLogicException {

        DispositivoEntity newEntity = factory.manufacturePojo(DispositivoEntity.class);
        DispositivoEntity result = dispositivoLogic.createDispositivo(newEntity);
        result.setNombre(null);

        dispositivoLogic.createDispositivo(result);
    }

    /**
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createDescripcionDispositivoNull() throws BusinessLogicException {

        DispositivoEntity newEntity = factory.manufacturePojo(DispositivoEntity.class);
        DispositivoEntity result = dispositivoLogic.createDispositivo(newEntity);
        result.setDescripcion(null);

        dispositivoLogic.createDispositivo(result);
    }

    /**
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createModeloDispositivoNull() throws BusinessLogicException {

        DispositivoEntity newEntity = factory.manufacturePojo(DispositivoEntity.class);
        DispositivoEntity result = dispositivoLogic.createDispositivo(newEntity);
        result.setModelo(null);

        dispositivoLogic.createDispositivo(result);
    }

    /**
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createPrecioZeroDispositivo() throws BusinessLogicException {
        DispositivoEntity newEntity = factory.manufacturePojo(DispositivoEntity.class);
        DispositivoEntity result = dispositivoLogic.createDispositivo(newEntity);
        result.setPrecio(0.0);

        dispositivoLogic.createDispositivo(result);

    }
}
