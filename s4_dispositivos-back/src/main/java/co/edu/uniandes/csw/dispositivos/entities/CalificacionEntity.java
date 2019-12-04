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
import uk.co.jemos.podam.common.PodamDoubleValue;

/**
 *
 * @author Javier Peniche
 */
@Entity
public class CalificacionEntity extends BaseEntity 
{


    @PodamDoubleValue(minValue = 0.0, maxValue = 5.0)
    double calificacionNumerica;

    String comentario;


    @ManyToOne(cascade = CascadeType.PERSIST)
    private DispositivoEntity dispositivo;

    public CalificacionEntity() {

    }


    public CalificacionEntity(String comentario, Double calificacion, DispositivoEntity dispositivo) {
        this.comentario = comentario;
        this.calificacionNumerica = calificacion;
        this.dispositivo = dispositivo;
    }

    public double getCalificacionNumerica() {
        return calificacionNumerica;
    }

    public String getComentario() {
        return comentario;
    }

    public DispositivoEntity getDispositivo() {
        return dispositivo;
    }

    public void setCalificacionNumerica(double calificacionNumerica) {
        this.calificacionNumerica = calificacionNumerica;
    }


    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public void setDispositivo(DispositivoEntity dispositivo) {
        this.dispositivo = dispositivo;

    }
    
        /**
     * Metodo no usado
     *
     * @param obj Object que se compara.
     * @return despreciado.
     * @deprecated (solo arregla code smell)
     */
    @Override
    @Deprecated
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    /**
     * Metodo no usado
     *
     * @return nada.
     * @deprecated (solo arregla code smell)
     */
    @Override
    @Deprecated
    public int hashCode() {
        return super.hashCode();
    }

}