/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.persistence;

import co.edu.uniandes.csw.dispositivos.entities.DispositivoEntity;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Santiago Fajardo 
 */
@Stateless
public class DispositivoPersistence {
    
    @PersistenceContext(unitName = "dispositivosPU")
    protected EntityManager em; 
    
    /**
     * Método para crear la entidad 
     * @param dispositivo tipo de entidad que se quiere persistir 
     * @return 
     */
    public DispositivoEntity create(DispositivoEntity dispositivo){
        em.persist(dispositivo);
        return dispositivo; 
    }
    
}
