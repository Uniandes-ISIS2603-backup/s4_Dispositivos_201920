/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.entities;

import java.io.Serializable;
import javax.persistence.Entity;

/**
 *
 * @author Javier Peniche
 */
@Entity
public class CalificacionEntity extends BaseEntity implements Serializable {

    private int calificacionNumerica;
    private String[] comentarios;

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
    public CalificacionEntity(int calificacion, String[] comentarios) {
        this.calificacionNumerica = calificacion;
        this.comentarios = comentarios;
    }

    /**
     * @return the calificacionNumerica
     */
    public long getCalificacionNumerica() {
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
    public String[] getComentarios() {
        return comentarios;
    }

    /**
     * @param comentarios the comentarios to set
     */
    public void setComentarios(String[] comentarios) {
        this.comentarios = comentarios;
    }

    private boolean compare(String[] arr1, String[] arr2) {
        int lenght1 = arr1.length;
        int lenght2 = arr2.length;
        int counter = 0;
        boolean equal = false;

        if (lenght1 == lenght2) {
            for (int i = 0; i < lenght1; i++) {
                if (arr1[i].compareTo(arr2[i]) == 0) {
                    counter++;
                } else {
                    break;
                }
            }
            if (counter == lenght1) {
                equal = true;
            }
        }

        return equal;
    }

    @Override
    public boolean equals(Object obj) {
        boolean resp = super.equals(obj);
        boolean fin = false;

        if (!resp && obj != null) {
            final CalificacionEntity other = (CalificacionEntity) obj;

            if ((this.getCalificacionNumerica() == other.getCalificacionNumerica())
                    && (compare(this.comentarios, other.comentarios))) {

                fin = true;
            }
        }
        return fin;
    }
}
