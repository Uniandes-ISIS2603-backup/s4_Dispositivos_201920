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
 * @author Carlos Salazar
 */
@Entity
public class MarcaEntity extends BaseEntity implements Serializable {

    /**
     * Atributo que modela el nombre de la marca.
     */
    private String nombreMarca;

    /**
     * atributo que modela la imagen de la marca.
     */
    private String imagen;

    /**
     * Constructor creado vacio para no tener problemas al implementar
     * Serializable
     */
    public MarcaEntity() {

    }

    /**
     * Crea una nueva MarcaEntity.
     *
     * @param pNombreMarca nombre de la marca a establecer.
     * @param pImagen ruta de la imagen a establecer.
     */
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
        return this.imagen;
    }

    /**
     * @param imagen the imagen to set
     */
    public void setImagen(String imagen) {
        this.imagen = imagen;
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
