/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.persistence;

import co.edu.uniandes.csw.dispositivos.entities.VentaEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Zharet Bautista Montes
 */
@Stateless
public class VentaPersistence 
{
    private static final Logger LOGGER = Logger.getLogger(VentaPersistence.class.getName());
    
    @PersistenceContext(unitName = "dispositivosPU")
    protected EntityManager em; 
    
    public VentaEntity create(VentaEntity venta)
    {
        LOGGER.log(Level.INFO, "Creando una nueva venta");
        em.persist(venta); 
        LOGGER.log(Level.INFO, "Venta creada");
        return venta;
    }
    
    public VentaEntity find(Long ventaID)
    {
        LOGGER.log(Level.INFO, "Consultando la venta con el ID={0}", ventaID);
        return em.find(VentaEntity.class, ventaID);
    }
    
    public List<VentaEntity> findAll()
    {
        LOGGER.log(Level.INFO, "Consultando todas las ventas registradas");
        Query vaq = em.createQuery("select u from VentaEntity u");
        return vaq.getResultList();
    }
}