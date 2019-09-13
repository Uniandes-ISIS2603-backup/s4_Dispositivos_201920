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

    private String[] links;

    public MediaEntity() {
        /**
         * Constructor Vacio Para Entitiy.
         */

    }

    /**
     * Constructor para pruebas
     *
     * @param links arreglo con los links de las imagenes y videos de un
     * dispositivo.
     */
    public MediaEntity(String[] links) {
        this.links = links;
    }

    /**
     * @return the links
     */
    public String[] getLinks() {
        return links;
    }

    /**
     * @param links the links to set
     */
    public void setLinks(String[] links) {
        this.links = links;
    }
}
