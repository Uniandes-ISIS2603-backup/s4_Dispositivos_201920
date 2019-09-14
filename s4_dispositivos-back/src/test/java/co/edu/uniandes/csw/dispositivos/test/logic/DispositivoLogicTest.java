/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.test.logic;

import co.edu.uniandes.csw.dispositivos.ejb.DispositivoLogic;
import co.edu.uniandes.csw.dispositivos.entities.DispositivoEntity;
import co.edu.uniandes.csw.dispositivos.entities.FacturaEntity;
import co.edu.uniandes.csw.dispositivos.entities.MediaEntity;
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
import uk.co.jemos.podam.common.PodamExclude;

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

        MediaEntity correctMedia = new MediaEntity("https://www.google.com/imgs");
        FacturaEntity correctFactura = new FacturaEntity(140, 245000.0, 34000.0, "Macbook pro");

        DispositivoEntity newEntity = factory.manufacturePojo(DispositivoEntity.class);
        newEntity.setFactura(correctFactura);
        newEntity.setImagenes(correctMedia);
        DispositivoEntity result = dispositivoLogic.createDispositivo(newEntity);
        Assert.assertNotNull(result);
    }
}
