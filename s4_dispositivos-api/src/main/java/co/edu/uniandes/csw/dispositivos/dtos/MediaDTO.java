/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.dtos;

import co.edu.uniandes.csw.dispositivos.entities.MediaEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Estudiante
 */
public class MediaDTO implements Serializable{
        private Long id;
        private String link;
        
        public MediaDTO(){
            
        }
        
        public MediaDTO(MediaEntity mediaEntity) {
        if (mediaEntity != null) {
            this.id = mediaEntity.getId();
            this.link = mediaEntity.getLink();
        }
        }
        
        public MediaEntity toEntity() {
        MediaEntity mediaEntity = new MediaEntity();
        mediaEntity.setId(this.getId());
        mediaEntity.setLinks(this.getLink());
        return mediaEntity;
    }
        
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
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
     * @return the link
     */
    public String getLink() {
        return link;
    }

    /**
     * @param link the link to set
     */
    public void setLink(String link) {
        this.link = link;
    }
}
    

    