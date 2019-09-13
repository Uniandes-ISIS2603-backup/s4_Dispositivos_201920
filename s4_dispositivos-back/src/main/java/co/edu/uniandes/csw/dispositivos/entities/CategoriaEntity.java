/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.entities;

import javax.persistence.Entity;

/**
 * Clase que representa una categoria en la persistencia y permite su
 * serialización.
 *
 * @author Juan L
 */
@Entity
public class CategoriaEntity extends BaseEntity {

    /**
     * Representa el nombre de una categoria.
     */
    private String nombreCategoria;

    /**
     * Crea una categoria vacia.
     */
    public CategoriaEntity() {

    }

    /**
     * Crea un medio de pago con la informacion pasada por parámetro.
     *
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
    
        /**
     * Metodo no usado
     *
     * @param obj Object que se compara.
     * @return despreciado.
     * @Ddeprecated (solo arregla code smell)
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
     * @Ddeprecated (solo arregla code smell)
     */
    @Override
    @Deprecated
    public int hashCode() {
        return super.hashCode();
    }
}
