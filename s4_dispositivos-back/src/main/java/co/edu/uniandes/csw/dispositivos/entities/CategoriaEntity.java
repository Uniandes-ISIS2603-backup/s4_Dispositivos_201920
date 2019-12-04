/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * Clase que representa una categoria en la persistencia y permite su
 * serialización.
 *
 * @author Juan L
 */
@Entity
public class CategoriaEntity extends BaseEntity implements Serializable{

    /**
     * Representa el nombre de una categoria.
     */
    private String nombreCategoria;

    @PodamExclude
    @OneToMany(mappedBy = "categoria")
    private List<DispositivoEntity> dispositivos;

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

    public List<DispositivoEntity> getDispositivos() {
        return dispositivos;
    }

    public void setDispositivos(List<DispositivoEntity> dispositivos) {
        this.dispositivos = dispositivos;
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
