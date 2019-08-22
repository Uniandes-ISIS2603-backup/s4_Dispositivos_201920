/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.persistence;

import co.edu.uniandes.csw.dispositivos.entities.VendedorEntity;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Zharet Bautista Montes
 */
@Stateless
public class VendedorPersistence 
{
    @PersistenceContext(unitName = "dispositivosPU")
    protected EntityManager em;
    
    public VendedorEntity create(VendedorEntity vendedor)
    {
        em.persist(vendedor);
        return vendedor; 
    }
}