/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.ejb;

import co.edu.uniandes.csw.dispositivos.entities.CalificacionEntity;
import co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.dispositivos.persistence.CalificacionPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Javier Peniche
 */
@Stateless
public class CalificacionLogic {
    @Inject
    private CalificacionPersistence persistence;
    /**
     * 
     * @param calificacion
     * @return
     * @throws BusinessLogicException 
     */
    public CalificacionEntity createCalificacion(CalificacionEntity calificacion) throws BusinessLogicException{
            if(calificacion.getCalificacionNumerica()<0 || calificacion.getCalificacionNumerica()>10){
                 throw new BusinessLogicException("Existe una calificacion con numero menor a 0 o mayor a 10 \"" + calificacion.getId() + "\"");        
    }
        calificacion= persistence.create(calificacion);
        return calificacion;
    }
    
    /**
     *
     * Obtener todas las editoriales existentes en la base de datos.
     *
     * @return una lista de editoriales.
     */
    public List<CalificacionEntity> getCalificaciones() {
        List<CalificacionEntity> editorials = persistence.findAll();
        return editorials;
    }
    
    public CalificacionEntity getCalificacion(Long editorialsId) {
        CalificacionEntity editorialEntity = persistence.find(editorialsId);
        return editorialEntity;
    }
    
    
    
}

    


