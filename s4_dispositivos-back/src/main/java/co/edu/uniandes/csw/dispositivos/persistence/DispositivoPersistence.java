/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.persistence;

import co.edu.uniandes.csw.dispositivos.entities.DispositivoEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
    
    public DispositivoEntity find(Long id){
        return em.find(DispositivoEntity.class, id);
    }
    
    public List<DispositivoPersistence> findAll(){
        
        Query query = em.createQuery("select u from DispositivoEntity u"); 
        return query.getResultList(); 
    }
    
    public DispositivoEntity update(DispositivoEntity dispositivoEntity){
        return em.merge(dispositivoEntity);
    }
    
    public void delete(Long dispositivoId){
        DispositivoEntity find = em.find(DispositivoEntity.class, dispositivoId);
        em.remove(find);
    }
    
    
}
