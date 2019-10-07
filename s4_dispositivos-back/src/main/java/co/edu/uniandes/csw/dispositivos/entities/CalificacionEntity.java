/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.entities;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 *
 * @author Javier Peniche
 */
@Entity
public class CalificacionEntity extends BaseEntity implements Serializable {

    private int calificacionNumerica;
    private String comentarios;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private DispositivoEntity dispositivo;

    /**
     * Constructor vacio para el uso de Entities.
     */
    public CalificacionEntity() {
        /**
         * Constructor vacio para la implementacion.
         */
    }


    /**
     * Constructor para realizar Junit Tests.
     *
     * @param calificacion calificacion del producto.
     * @param comentarios comentarios del producto.
     */
    public CalificacionEntity(int calificacion, String comentarios) {
        this.calificacionNumerica = calificacion;
        this.comentarios = comentarios;
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