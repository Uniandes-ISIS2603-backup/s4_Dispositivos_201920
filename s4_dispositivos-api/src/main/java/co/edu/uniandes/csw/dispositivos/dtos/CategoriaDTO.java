/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.dtos;

import co.edu.uniandes.csw.dispositivos.entities.CategoriaEntity;
import java.io.Serializable;

/**
 *
 * @author Juan L
 */
public class CategoriaDTO implements Serializable {

    private Long id;
    private String nombreCategoria;

    /**
     * Constructor por defecto
     */
    public CategoriaDTO() {
    }

    /**
     * Conviertir Entity a DTO (Crea un nuevo DTO con los valores que recibe en
     * la entidad que viene de argumento.
     *
     * @param categoriaEntity: Es la entidad que se va a convertir a DTO
     */
    public CategoriaDTO(CategoriaEntity categoriaEntity) {

        if (categoriaEntity != null) {
            this.id = categoriaEntity.getId();
            this.nombreCategoria = categoriaEntity.getNombreCategoria();
        }
    }

    /**
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
    public CategoriaEntity toEntity() {
        CategoriaEntity categoriaEntity = new CategoriaEntity();
        categoriaEntity.setId(this.getId());
        categoriaEntity.setNombreCategoria(this.getNombreCategoria());
        return categoriaEntity;
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

}
