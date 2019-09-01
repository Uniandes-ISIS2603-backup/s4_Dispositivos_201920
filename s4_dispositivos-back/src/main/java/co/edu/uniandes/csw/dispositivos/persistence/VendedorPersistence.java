/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.persistence;

import co.edu.uniandes.csw.dispositivos.entities.VendedorEntity;
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
public class VendedorPersistence 
{   
    @PersistenceContext(unitName = "dispositivosPU")
    protected EntityManager em;
    
    public VendedorEntity create(VendedorEntity vendedorc)
    {
        em.persist(vendedorc);
        return vendedorc; 
    }
    
    public VendedorEntity find(Long vendedorfID)
    {
        return em.find(VendedorEntity.class, vendedorfID);
    }
    
    public List<VendedorEntity> findAll()
    {
        Query vrq = em.createQuery("select u from VendedorEntity u");
        return vrq.getResultList();
    }
    
    public VendedorEntity update(VendedorEntity vendedoru)
    {
        return em.merge(vendedoru);
    }
    
    public void delete(Long vendedordID)
    {
        VendedorEntity wantedvr = em.find(VendedorEntity.class, vendedordID); 
        em.remove(wantedvr);
    }   
}