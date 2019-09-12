/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.test.logic;

import co.edu.uniandes.csw.dispositivos.ejb.VentaLogic;
import co.edu.uniandes.csw.dispositivos.entities.VentaEntity;
import co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.dispositivos.persistence.VentaPersistence;
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
 * @author Zharet Bautista Montes
 */

@RunWith(Arquillian.class)
public class VentaLogicTest 
{
    @PersistenceContext(unitName="dispositivosPU")
    private EntityManager vam;
    
    @Deployment
    public static JavaArchive createDeployment() 
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(VentaEntity.class.getPackage())
                .addPackage(VentaLogic.class.getPackage())
                .addPackage(VentaPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    private PodamFactory valfactory = new PodamFactoryImpl(); 
    
    @Inject
    private VentaLogic valogic; 
    
    @Test
    public void createVentaTest() throws BusinessLogicException 
    {
        VentaEntity venta = valfactory.manufacturePojo(VentaEntity.class);
        VentaEntity obtainedva = valogic.createVenta(venta);
        Assert.assertNotNull(obtainedva);
    }
    
    @Test(expected=BusinessLogicException.class)
    public void createNullVentaTest() throws BusinessLogicException
    {
        
    }
    
    @Test
    public void findVentaTest() throws BusinessLogicException
    {
        
    }
    
    @Test
    public void findAllVentasTest() throws BusinessLogicException
    {
        
    }
    
    @Test
    public void updateVentaTest() throws BusinessLogicException
    {
        
    }
    
    @Test
    public void deleteVentaTest() throws BusinessLogicException
    {
        
    }
}
