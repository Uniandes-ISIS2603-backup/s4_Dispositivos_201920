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
    
    public VentaEntity create(VentaEntity ventac)
    {
        LOGGER.log(Level.INFO, "Creando una nueva venta");
        em.persist(ventac); 
        LOGGER.log(Level.INFO, "Venta creada");
        return ventac;
    }
    
    public VentaEntity find(Long ventafID)
    {
        LOGGER.log(Level.INFO, "Consultando la venta con el ID={0}", ventafID);
        return em.find(VentaEntity.class, ventafID);
    }
    
    public List<VentaEntity> findAll()
    {
        LOGGER.log(Level.INFO, "Consultando todas las ventas registradas");
        Query vaq = em.createQuery("select u from VentaEntity u");
        return vaq.getResultList();
    }
    
    public VentaEntity update(VentaEntity ventau)
    {
        LOGGER.log(Level.INFO, "Actualizando la venta con id={0}", ventau.getId());
        return em.merge(ventau);
    }
    
    public void delete(Long ventadID)
    {
        LOGGER.log(Level.INFO, "Borrando la venta con id={0}", ventadID);
        VentaEntity wantedva = em.find(VentaEntity.class, ventadID); 
        em.remove(wantedva);
    }
}