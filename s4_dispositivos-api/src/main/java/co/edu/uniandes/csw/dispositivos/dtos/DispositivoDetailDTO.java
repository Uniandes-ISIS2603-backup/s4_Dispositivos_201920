/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.dtos;

import co.edu.uniandes.csw.dispositivos.entities.CalificacionEntity;
import co.edu.uniandes.csw.dispositivos.entities.DispositivoEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Santiago Fajardo
 */
public class DispositivoDetailDTO extends DispositivoDTO implements Serializable {

    private List<CalificacionDTO> calificaciones;

    /**
     *
     */
    public DispositivoDetailDTO() {
        super();
    }

    /**
     *
     * @param dispositivoEntity
     */
    public DispositivoDetailDTO(DispositivoEntity dispositivoEntity) {
        super(dispositivoEntity);
        if (dispositivoEntity.getCalificaciones() != null) {
            calificaciones = new ArrayList<CalificacionDTO>();
            for (CalificacionEntity calificacionEntity : dispositivoEntity.getCalificaciones()) {
                calificaciones.add(new CalificacionDTO(calificacionEntity));
            }
        }
    }

    /**
     *
     * @return
     */
    @Override
    public DispositivoEntity toEntity() {
        DispositivoEntity dispositivoEntity = super.toEntity();
        if (calificaciones != null) {
            List<CalificacionEntity> calificacionesEntity = new ArrayList<CalificacionEntity>();
            for (CalificacionDTO dtoCalificacion : getCalificaciones()) {
                calificacionesEntity.add(dtoCalificacion.toEntity());
            }
            dispositivoEntity.setCalificaciones(calificacionesEntity);
        }
        return dispositivoEntity;
    }

    public List<CalificacionDTO> getCalificaciones() {
        return calificaciones;
    }

    public void setCalificaciones(List<CalificacionDTO> calificaciones) {
        this.calificaciones = calificaciones;
    }
}
