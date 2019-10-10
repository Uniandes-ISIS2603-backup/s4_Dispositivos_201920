/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.ejb;

import co.edu.uniandes.csw.dispositivos.entities.CalificacionEntity;
import co.edu.uniandes.csw.dispositivos.entities.DispositivoEntity;
import co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.dispositivos.persistence.CalificacionPersistence;
import co.edu.uniandes.csw.dispositivos.persistence.DispositivoPersistence;
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
    private CalificacionPersistence cp;
    @Inject
    private DispositivoPersistence dp;
    /**
     * 
     * @param calificacion
     * @return
     * @throws BusinessLogicException 
     */
    public CalificacionEntity createCalificacion(Long dispositivoId,CalificacionEntity calificacion) throws BusinessLogicException{
        if(calificacion.getCalificacionNumerica()<0 || calificacion.getCalificacionNumerica()>10){
                 throw new BusinessLogicException("Existe una calificacion con numero menor a 0 o mayor a 10 \"" + calificacion.getId() + "\"");        
            }
        DispositivoEntity dispositivo = dp.find(dispositivoId);
        calificacion.setDispositivo(dispositivo);
        return cp.create(calificacion);
    }
    
    public List<CalificacionEntity> getCalificaciones(Long dispositivoId) {
        DispositivoEntity dispositivo = dp.find(dispositivoId);
        return dispositivo.getCalificaciones();
        
    }
    
     /**
     *
     * Obtener todas las editoriales existentes en la base de datos.
     *
     * @return una lista de editoriales.
     */
    public CalificacionEntity getCalificacion(Long dispositivoId, Long calificacionId) {
        return cp.find(dispositivoId, calificacionId);
    }
    
    public CalificacionEntity updateCalificacion(Long dispositivoId, CalificacionEntity calificacion) throws BusinessLogicException{
            if(calificacion.getCalificacionNumerica()<0 || calificacion.getCalificacionNumerica()>10){
                 throw new BusinessLogicException("Existe una calificacion con numero menor a 0 o mayor a 10 \"" + calificacion.getId() + "\"");        
           }
            DispositivoEntity dispositivoEntity = dp.find(dispositivoId);
            calificacion.setDispositivo(dispositivoEntity);
            cp.update(calificacion);
            cp.update(calificacion);
            return calificacion;
    }
    
   public void deleteCalificacion(Long dispositivoId,Long calificacionId) throws BusinessLogicException {

        CalificacionEntity old = getCalificacion(dispositivoId, calificacionId);
        if (old == null) {
            throw new BusinessLogicException("La calificacion con id = " + calificacionId + " no esta asociado a el libro con id = " + dispositivoId);
        }
        cp.delete(old.getId());
    }   
}