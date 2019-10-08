/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.dtos;

import co.edu.uniandes.csw.dispositivos.entities.CalificacionEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Estudiante
 */
public class CalificacionDTO implements Serializable {

    private Long id;
    private double calificacionNumerica;
    private String comentario;

    private DispositivoDTO dispositivo;

    public CalificacionDTO(){}

    public CalificacionDTO(CalificacionEntity calificacionEntity) {
        if (calificacionEntity != null) {
            this.id = calificacionEntity.getId();
            this.calificacionNumerica = calificacionEntity.getCalificacionNumerica();
            this.comentario = calificacionEntity.getComentario();
            if (calificacionEntity.getDispositivo() != null) {
                this.dispositivo = new DispositivoDTO(calificacionEntity.getDispositivo());
            }
        }
    }

    /**
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
    public CalificacionEntity toEntity() {
        CalificacionEntity entity = new CalificacionEntity();
        entity.setId(this.id);
        entity.setCalificacionNumerica(this.calificacionNumerica);
        entity.setComentario(this.comentario);
        if (this.dispositivo != null) {
            entity.setDispositivo(this.dispositivo.toEntity());
        }
        return entity;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the calificacionNumerica
     */
    public double getCalificacionNumerica() {
        return calificacionNumerica;
    }

    /**
     * @param calificacionNumerica the calificacionNumerica to set
     */
    public void setCalificacionNumerica(double calificacionNumerica) {
        this.calificacionNumerica = calificacionNumerica;
    }

    /**
     * @return the comentario
     */

    public String getComentario() {
        return comentario;

    }

    /**
     * @param comentario the comentario to set
     */

    public void setComentario(String comentario) {
        this.comentario = comentario;

    }
}
