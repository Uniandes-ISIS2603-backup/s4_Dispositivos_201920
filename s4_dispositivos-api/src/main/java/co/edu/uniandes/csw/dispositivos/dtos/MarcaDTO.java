/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.dtos;

import co.edu.uniandes.csw.dispositivos.entities.MarcaEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Carlos Salazar
 */
public class MarcaDTO implements Serializable {

    /**
     * Atributo que modela el nombre de la marca.
     */
    private String nombreMarca;



    /**
     * atributo que modela el id de la marca.
     */
    private Long id;

    /**
     * Constructor por defecto
     */
    public MarcaDTO() {
    }

    /**
     * Constructor a partir de la entidad
     *
     * @param marcaEntity La entidad del libro
     */
    public MarcaDTO(MarcaEntity marcaEntity) {
        if (marcaEntity != null) {
            this.id = marcaEntity.getId();
            this.nombreMarca = marcaEntity.getNombreMarca();
        }
    }

    /**
     * Método para transformar el DTO a una entidad.
     *
     * @return La entidad de la marca asociada.
     */
    public MarcaEntity toEntity() {
        MarcaEntity marcaEntity = new MarcaEntity();
        marcaEntity.setId(this.getId());
        marcaEntity.setNombreMarca(this.getNombreMarca());
        return marcaEntity;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
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

}
