/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.ejb;

import co.edu.uniandes.csw.dispositivos.entities.CalificacionEntity;
import co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.dispositivos.persistence.CalificacionPersistence;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Javier Peniche
 */
@Stateless
public class CalificacionLogic {
    
    private static final Logger LOGGER = Logger.getLogger(CategoriaLogic.class.getName());

    @Inject
    private CalificacionPersistence cp;
    
    /**
     *
     * @param calificacion
     * @return
     * @throws BusinessLogicException
     */
    public CalificacionEntity createCalificacion(CalificacionEntity calificacion) throws BusinessLogicException {
        if (calificacion.getCalificacionNumerica() < 0 || calificacion.getCalificacionNumerica() > 10) {
            throw new BusinessLogicException("Existe una calificacion con numero menor a 0 o mayor a 10 \"" + calificacion.getId() + "\"");
        }
        return cp.create(calificacion);
    }

    public CalificacionEntity updateCalificacion(Long pCategoriaId, CalificacionEntity calificacion) throws BusinessLogicException {
        
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la calificacion con id = {0}", pCategoriaId);
        if (!calificacion.getComentario().equals("")) {
            CalificacionEntity newEntity = cp.update(calificacion);
            LOGGER.log(Level.INFO, "Termina proceso de actualizar la categoria con id = {0}", calificacion.getId());
            return newEntity;
        } else {
            throw new BusinessLogicException("La categoria no puede ser creada, ya que existe una con el nombre es inválida");
        }
    }

    /**
     *
     * Obtener todas las editoriales existentes en la base de datos.
     *
     * @return una lista de editoriales.
     */
    public List<CalificacionEntity> getCalificaciones() {
        return cp.findAll();
    }

    public CalificacionEntity getCalificacion(Long calificacionId) {
        return cp.find(calificacionId);
    }

    public void deleteCalificacion(Long calificacionId) {

        cp.delete(calificacionId);
    }
}
