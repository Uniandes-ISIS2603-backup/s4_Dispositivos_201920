/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.persistence;

import co.edu.uniandes.csw.dispositivos.entities.MediaEntity;
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
public class MediaPersistence {
    @PersistenceContext(unitName = "dispositivosPU")

    protected EntityManager em;

    public MediaEntity create(MediaEntity mediaEntity) {
        em.persist(mediaEntity);
        return mediaEntity;
    }

    public MediaEntity find(Long mediaId) {
        return em.find(MediaEntity.class, mediaId);
    }
    
    public List<MediaEntity> findAll(){
        TypedQuery<MediaEntity> query= em.createQuery("select u from MediaEntity u", MediaEntity.class);
        return query.getResultList();
    }
    
    public  MediaEntity update(MediaEntity mediaEntity){
        return em.merge(mediaEntity);
    }
    
    public void delete(Long mediaId) {
        MediaEntity entity = em.find(MediaEntity.class, mediaId);
        em.remove(entity);
    }
    
}
