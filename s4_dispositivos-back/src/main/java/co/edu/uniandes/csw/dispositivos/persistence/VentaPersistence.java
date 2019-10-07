/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.persistence;

import co.edu.uniandes.csw.dispositivos.entities.VentaEntity;
import java.util.List;
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
    /**
     * Base de datos donde opera la clase
     */
    @PersistenceContext(unitName = "dispositivosPU")
    protected EntityManager em;

    /**
     * Método para agregar una venta
     * @param ventac
     * @return created Venta
     */
    public VentaEntity create(VentaEntity ventac) 
    {
        em.persist(ventac);
        return ventac;
    }

    /**
     * Método para buscar una venta
     * @param ventafID
     * @return found Venta
     */
    public VentaEntity find(Long ventafID) 
    {
        return em.find(VentaEntity.class, ventafID);
    }

    /**
     * Método para encontrar todas las ventas
     * @return  Ventas List
     */
    public List<VentaEntity> findAll() 
    {
        Query vaq = em.createQuery("select u from VentaEntity u");
        return vaq.getResultList();
    }

    /**
     * Método para cambiar una venta
     * @param ventau
     * @return updated Venta
     */
    public VentaEntity update(VentaEntity ventau) 
    {
        return em.merge(ventau);
    }

    /**
     * Método para borrar una venta
     * @param ventadID
     */
    public void delete(Long ventadID) 
    {
        VentaEntity wantedva = em.find(VentaEntity.class, ventadID);
        em.remove(wantedva);
    }
}