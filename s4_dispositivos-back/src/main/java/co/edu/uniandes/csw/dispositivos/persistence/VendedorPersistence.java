/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.persistence;

import co.edu.uniandes.csw.dispositivos.entities.VendedorEntity;
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
public class VendedorPersistence 
{
    private static final Logger LOGGER = Logger.getLogger(VendedorPersistence.class.getName());
    
    @PersistenceContext(unitName = "dispositivosPU")
    protected EntityManager em;
    
    public VendedorEntity create(VendedorEntity vendedorc)
    {
        LOGGER.log(Level.INFO, "Creando una nueva vendedor");
        em.persist(vendedorc);
        LOGGER.log(Level.INFO, "Vendedor creado");
        return vendedorc; 
    }
    
    public VendedorEntity find(Long vendedorfID)
    {
        LOGGER.log(Level.INFO, "Consultando el vendedor con el ID={0}", vendedorfID);
        return em.find(VendedorEntity.class, vendedorfID);
    }
    
    public List<VendedorEntity> findAll()
    {
        LOGGER.log(Level.INFO, "Consultando todos los vendedores registrados");
        Query vrq = em.createQuery("select u from VendedorEntity u");
        return vrq.getResultList();
    }
    
    public VendedorEntity update(VendedorEntity vendedoru)
    {
        LOGGER.log(Level.INFO, "Actualizando el vendedor con id={0}", vendedoru.getId());
        return em.merge(vendedoru);
    }
    
    public void delete(Long vendedordID)
    {
        LOGGER.log(Level.INFO, "Borrando el vendedor con id={0}", vendedordID);
        VendedorEntity wantedvr = em.find(VendedorEntity.class, vendedordID); 
        em.remove(wantedvr);
    }
}