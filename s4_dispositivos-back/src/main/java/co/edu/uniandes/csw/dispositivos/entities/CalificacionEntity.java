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
public class CalificacionEntity extends BaseEntity implements Serializable{
    private int calificacionNumerica;
    private String[] comentarios;
    
    
    public CalificacionEntity(){     
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
    public String[] getComentarios() {
        return comentarios;
    }

    /**
     * @param comentarios the comentarios to set
     */
    public void setComentarios(String[] comentarios) {
        this.comentarios = comentarios;
    }
    
    @Override
    public boolean equals(Object obj){
        boolean resp= super.equals(this);
        boolean fin =false;
        final CalificacionEntity other=(CalificacionEntity) obj;
        
        if(!resp){
            return fin;
        }
        else{
            if(this.getCalificacionNumerica()==other.getCalificacionNumerica()){
                fin=true;
                return fin;
            }
             else if(Arrays.equals(this.getComentarios(), other.getComentarios()))//.equals(equals())){
                fin=true;
                return fin;
                }
        }
    
    
}