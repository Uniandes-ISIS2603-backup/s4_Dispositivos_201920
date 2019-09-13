/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.test.logic;

import co.edu.uniandes.csw.dispositivos.ejb.MarcaLogic;
import co.edu.uniandes.csw.dispositivos.entities.MarcaEntity;
import co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.dispositivos.persistence.MarcaPersistence;
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
 * @author Carlos Salazar
 */
@RunWith(Arquillian.class)
public class MarcaLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @PersistenceContext(unitName = "dispositivosPU")
    private EntityManager em;

    @Inject
    private MarcaLogic marcaLogic;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(MarcaEntity.class.getPackage())
                .addPackage(MarcaLogic.class.getPackage())
                .addPackage(MarcaPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    @Test
    public void createMarca() throws BusinessLogicException {
        MarcaEntity newEntity = factory.manufacturePojo(MarcaEntity.class);
        MarcaEntity result = marcaLogic.createMarca(newEntity);
        Assert.assertNotNull(result);

        MarcaEntity entity = em.find(MarcaEntity.class, result.getId());
        Assert.assertEquals(entity.getNombreMarca(), result.getNombreMarca());
    }

    @Test(expected = BusinessLogicException.class)
    public void CreateMarcaNotNull() throws BusinessLogicException {
        MarcaEntity newEntity = factory.manufacturePojo(MarcaEntity.class);
        newEntity.setNombreMarca(null);
        MarcaEntity result = marcaLogic.createMarca(newEntity);

    }
}
