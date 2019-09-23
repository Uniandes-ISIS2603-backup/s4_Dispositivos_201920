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
    @PersistenceContext(unitName = "dispositivosPU")
    protected EntityManager em;

    public VentaEntity create(VentaEntity ventac) 
    {
        em.persist(ventac);
        return ventac;
    }

    public VentaEntity find(Long ventafID) 
    {
        return em.find(VentaEntity.class, ventafID);
    }

    public List<VentaEntity> findAll() 
    {
        Query vaq = em.createQuery("select u from VentaEntity u");
        return vaq.getResultList();
    }

    public VentaEntity update(VentaEntity ventau) 
    {
        return em.merge(ventau);
    }

    public void delete(Long ventadID) 
    {
        VentaEntity wantedva = em.find(VentaEntity.class, ventadID);
        em.remove(wantedva);
    }
}