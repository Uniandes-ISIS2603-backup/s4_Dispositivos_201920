/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;

/**
 *
 * @author Javier Peniche
 */
@Entity
public class MediaEntity extends BaseEntity implements Serializable {
    
    private List<String> links;
    
    public MediaEntity(){
        
    }

    /**
     * @return the links
     */
    public List<String> getLinks() {
        return links;
    }

    /**
     * @param links the links to set
     */
    public void setLinks(List<String> links) {
        this.links = links;
    }
    
    @Override
    public boolean equals(Object obj){
        boolean resp= super.equals(this);
        boolean fin =false;
        final MediaEntity other=(MediaEntity) obj;
        
        if(!resp){
            return fin;
        }
        else{
            if(this.getLinks()==other.getLinks()){
                fin=true;
            }
        }
        return fin;
    }
}
