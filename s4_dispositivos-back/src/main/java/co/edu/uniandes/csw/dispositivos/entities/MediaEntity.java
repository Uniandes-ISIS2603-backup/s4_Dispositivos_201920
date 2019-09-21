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
public class MediaEntity extends BaseEntity implements Serializable {

    private String link;

    public MediaEntity() {
        /**
         * Constructor Vacio Para Entitiy.
         */

    }

    /**
     * Constructor para pruebas
     *
     * @param link el link de las imagenes y videos de un
     * dispositivo.
     */
    public MediaEntity(String link) {
        this.link = link;
    }

    /**
     * Retonra el link de multimedia
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
}
