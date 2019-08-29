/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.entities;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * Clase que representa una categoria en la persistencia y permite su 
 * serialización.
 * @author Juan L
 */
@Entity
public class CategoriaEntity extends BaseEntity{
    
    private String nombreCategoria;

    /**
     * Crea una categoria vacia. 
     */
    public CategoriaEntity() {
    }

    /**
     * Crea un medio de pago con la informacion pasada por parámetro.
     * @param nombreCategoria Nombre de la categoria a crear. 
     */
    public CategoriaEntity(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    /**
     * @return the nombreCategoria
     */
    public String getNombreCategoria() {
        return nombreCategoria;
    }

    /**
     * @param nombreCategoria the nombreCategoria to set
     */
    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    @Override
    public boolean equals(Object obj) {
        boolean resp = super.equals(this);
        boolean fin = false;
        final CategoriaEntity other = (CategoriaEntity) obj;
        
        if(!resp)
        {
            return fin;
        }
        else
        {
            if(this.nombreCategoria.compareTo(other.nombreCategoria) == 0)
            {
                fin = true;
                return fin;
            }
            else
            {
                return false;
            }
        }
    }
    
    
    
    
}