/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.persistence;

import co.edu.uniandes.csw.dispositivos.entities.ComprobanteDePagoEntity;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Estudiante
 */
@Stateless
public class ComprobanteDePagoPersistence 
{
    @PersistenceContext(unitName = "dispositivosPU")
    protected EntityManager em;
    
    public ComprobanteDePagoEntity find(Long id)
    {
        return em.find(ComprobanteDePagoEntity.class, id);
    }
    
    public ComprobanteDePagoEntity create(ComprobanteDePagoEntity comprobante)
    {
        em.persist(comprobante);
        return comprobante;
                
    }
}

