/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.persistence;

import co.edu.uniandes.csw.dispositivos.entities.CalificacionEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Javier Peniche
 */
@Stateless
public class CalificacionPersistence {

    @PersistenceContext(unitName = "dispositivosPU")
    protected EntityManager em;

    public CalificacionEntity create(CalificacionEntity calificacionEntity) {
        em.persist(calificacionEntity);
        return calificacionEntity;
    }

    public CalificacionEntity find(Long calificacionId) {
        return em.find(CalificacionEntity.class, calificacionId);
    }
    
    public List<CalificacionEntity> findAll(){
        TypedQuery<CalificacionEntity> query= em.createQuery("select u from CalificacionEntity u", CalificacionEntity.class);
        return query.getResultList();
    }    
    
    public  CalificacionEntity update(CalificacionEntity calificacionEntity){
        return em.merge(calificacionEntity);
    }
    
    public void delete(Long calificacionId) {
        CalificacionEntity entity = em.find(CalificacionEntity.class, calificacionId);
        em.remove(entity);
    }
    
    public CalificacionEntity findByCalificacion(double calificacion) {
        TypedQuery query = em.createQuery("Select f From CalificacionEntity f where f.calificacionNumerica = :calificacion", CalificacionEntity.class);
        query = query.setParameter("calificacion", calificacion);
        List<CalificacionEntity> sameCode = query.getResultList();
        CalificacionEntity result;
        if (sameCode == null) {
            result = null;
        } else if (sameCode.isEmpty()) {
            result = null;
        } else {
            result = sameCode.get(0);
        }
        return result;
    }
    
    public CalificacionEntity find(Long dispositivoId,Long calificacionId) {
        TypedQuery<CalificacionEntity> q = em.createQuery("select p from CalificacionEntity p where (p.dispositivo.id = :dispositivoId) and (p.id = :calificacionId)", CalificacionEntity.class);
        q.setParameter("dispositivoId", dispositivoId);
        q.setParameter("calificacionId", calificacionId);
        List<CalificacionEntity> results = q.getResultList();
        CalificacionEntity review = null;
        if (results == null) {
            review = null;
        } else if (results.isEmpty()) {
            review = null;
        } else if (results.size() >= 1) {
            review = results.get(0);
        }
        return review;
    }
}
