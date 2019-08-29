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
 * @author Estudiante
 */
@Entity
public class MarcaEntity extends BaseEntity implements Serializable {

    private String nombreMarca;
    private String imagen;

    public MarcaEntity() {

    }

    public MarcaEntity(String pNombreMarca, String pImagen) {
        this.nombreMarca = pNombreMarca;
        this.imagen = pImagen;
    }

    /**
     * @return the nombreMarca
     */
    public String getNombreMarca() {
        return nombreMarca;
    }

    /**
     * @param nombreMarca the nombreMarca to set
     */
    public void setNombreMarca(String nombreMarca) {
        this.nombreMarca = nombreMarca;
    }

    /**
     * @return the imagen
     */
    public String getImagen() {
        return imagen;
    }

    /**
     * @param imagen the imagen to set
     */
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @Override
    public boolean equals(Object obj)
    {
        boolean resp = super.equals(this);
        final MarcaEntity other = (MarcaEntity) obj;
        
        if(!resp)
        {
            return false;
        }
        else
        {
            if(this.nombreMarca.equalsIgnoreCase(other.nombreMarca))
                if(this.imagen.equalsIgnoreCase(other.imagen))
                        return true;

            return false;
        }
    }
}
