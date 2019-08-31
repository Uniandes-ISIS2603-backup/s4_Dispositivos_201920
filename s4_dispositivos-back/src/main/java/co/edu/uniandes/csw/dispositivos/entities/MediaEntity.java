/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.entities;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.persistence.Entity;

/**
 *
 * @author Javier Peniche
 */
@Entity
public class MediaEntity extends BaseEntity implements Serializable {
    
    private String[] links;
    
    public MediaEntity(){
        /**
         * Constructor Vacio Para Entitiy. 
         */
        
    }
    /**
     * Constructor para pruebas 
     * @param links arreglo con los links de las imagenes y videos de un dispositivo. 
     */
    public MediaEntity(String[] links){
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
    
    /**
     * 
     * @param obj
     * @return 
     */
    @Override
    public boolean equals(Object obj){
        boolean resp= super.equals(obj);
        boolean fin =false;
        final MediaEntity other=(MediaEntity) obj;
        
        if(!resp){
            return fin;
        }
        else{
            if(Arrays.equals(this.links, other.links)){
                fin=true;
            }
        }
        return fin;
    }
}
