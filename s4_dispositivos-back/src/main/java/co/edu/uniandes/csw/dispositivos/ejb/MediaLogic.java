/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.ejb;

import co.edu.uniandes.csw.dispositivos.entities.MediaEntity;
import co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.dispositivos.persistence.MediaPersistence;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Javier Peniche
 */
@Stateless
public class MediaLogic {
    @Inject
    private MediaPersistence persistence;
    /**
     * 
     * @param media
     * @return
     * @throws BusinessLogicException 
     */
    public MediaEntity createMedia(MediaEntity media) throws BusinessLogicException{
            if(media.getLink()==null){
                 throw new BusinessLogicException("La media es nula \"" + media.getId() + "\"");        
    }
        media= persistence.create(media);
        return media;
    }
    
    /**
     *
     * Obtener todas las editoriales existentes en la base de datos.
     *
     * @return una lista de editoriales.
     */
    public List<MediaEntity> getMedias() {
        List<MediaEntity> editorials = persistence.findAll();
        return editorials;
    }
    
    public MediaEntity getMedia(Long editorialsId) {
        MediaEntity editorialEntity = persistence.find(editorialsId);
        return editorialEntity;
    }
    
    
    
}
