/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.entities;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Javier Peniche
 */
@Entity
public class MediaEntity extends BaseEntity implements Serializable {

    private String link;

    @PodamExclude
    @OneToOne(
        mappedBy = "logo",
    	fetch = FetchType.LAZY
    )
    private MarcaEntity marca;
    
    @PodamExclude
    @OneToOne(
        mappedBy = "imagenes",
    	fetch = FetchType.LAZY
    )
    private DispositivoEntity dispositivo;
    
    @PodamExclude
    @ManyToOne
    private VentaEntity venta;

    public MediaEntity() {
        /**
         * Constructor Vacio Para Entitiy.
         */

    }

    /**
     * Constructor para pruebas
     *
     * @param link el link de las imagenes y videos de un dispositivo.
     */
    public MediaEntity(String link) {
        this.link = link;
    }

    /**
     * Retonra el link de multimedia
     *
     * @return link que referencia un video o imagen
     */
    public String getLink() {
        return link;
    }

    /**
     * @param link modifica el link de una imagen o dispositivo
     */
    public void setLinks(String link) {
        this.link = link;
    }

    /**
     * @param marca modifica la marca de una imagen
     */
    public void setMarca(MarcaEntity marca) {
        this.marca = marca;
    }

    /**
     * Establece una marca al contenido media
     */
    public MarcaEntity getMarca() {
        return marca;
    }

}
