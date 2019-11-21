/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

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

    @PodamExclude
    @OneToMany(mappedBy = "marca", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DispositivoEntity> dispositivos;

    /**
     * Atributo que modela la logo de la marca
     */
    private String logo;

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
     * @param pImagen logo a establecer.
     * @param pDispositivos dispositivos a establecer.
     */
    public MarcaEntity(String pNombreMarca, String pImagen, List<DispositivoEntity> pDispositivos) {
        this.nombreMarca = pNombreMarca;
        this.logo = pImagen;
        this.dispositivos = pDispositivos;
    }

    /**
     * @return the nombreMarca
     */
    public String getNombreMarca() {
        return nombreMarca;
    }

    /**
     *
     * @return dispositivos
     */
    public List<DispositivoEntity> getDispositivos() {
        return dispositivos;
    }

    /**
     *
     * @param dispositivos
     */
    public void setDispositivos(List<DispositivoEntity> dispositivos) {
        this.dispositivos = dispositivos;
    }

    /**
     * @param nombreMarca the nombreMarca to set
     */
    public void setNombreMarca(String nombreMarca) {
        this.nombreMarca = nombreMarca;
    }

    /**
     * @return the logo
     */
    public String getLogo() {
        return this.logo;
    }

    /**
     * @param logo the logo to set
     */
    public void setLogo(String logo) {
        this.logo = logo;
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
