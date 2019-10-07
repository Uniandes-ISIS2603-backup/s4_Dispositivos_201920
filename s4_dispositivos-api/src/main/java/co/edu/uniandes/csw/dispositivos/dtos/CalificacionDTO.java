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
public class CalificacionDTO implements Serializable{
    
    private Long id;
    private int calificacionNumerica;
    private String comentarios;
    
    public CalificacionDTO(){
        
    }
    
        
    public CalificacionDTO(CalificacionEntity calificacionEntity) {
        if (calificacionEntity != null) {
            this.id = calificacionEntity.getId();
            this.calificacionNumerica = (int) calificacionEntity.getCalificacionNumerica();
            this.comentarios = calificacionEntity.getComentarios();
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
        entity.setComentarios(this.comentarios);
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
    public int getCalificacionNumerica() {
        return calificacionNumerica;
    }

    /**
     * @param calificacionNumerica the calificacionNumerica to set
     */
    public void setCalificacionNumerica(int calificacionNumerica) {
        this.calificacionNumerica = calificacionNumerica;
    }

    /**
     * @return the comentarios
     */
    public String getComentarios() {
        return comentarios;
    }

    /**
     * @param comentarios the comentarios to set
     */
    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
}
