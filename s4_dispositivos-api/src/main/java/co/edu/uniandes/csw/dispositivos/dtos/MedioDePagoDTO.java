/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.dtos;

import co.edu.uniandes.csw.dispositivos.entities.MedioDePagoEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Juan L
 */
public class MedioDePagoDTO implements Serializable {

    private Long id;

    private String numeroTarjeta;

    private String numeroDeVerificacion;

    private String tipoTarjeta;

    private String tipoCredito;

    /**
     * Constructor por defecto
     */
    public MedioDePagoDTO() {
    }

    /**
     * Conviertir Entity a DTO (Crea un nuevo DTO con los valores que recibe en
     * la entidad que viene de argumento.
     *
     * @param medioEntity: Es la entidad que se va a convertir a DTO
     */
    public MedioDePagoDTO(MedioDePagoEntity medioEntity) {
        
        if (medioEntity != null) {
            this.id = medioEntity.getId();
            this.numeroTarjeta = medioEntity.getNumeroTarjeta();
            this.numeroDeVerificacion = medioEntity.getNumeroDeVerificacion();
            this.tipoCredito = medioEntity.getTipoCredito();
            this.tipoTarjeta = medioEntity.getTipoTarjeta();
        }
    }
    
        /**
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
    public MedioDePagoEntity toEntity() {
        MedioDePagoEntity medioEntity = new MedioDePagoEntity();
        medioEntity.setId(this.getId());
        medioEntity.setNumeroTarjeta(this.getNumeroTarjeta());
        medioEntity.setNumeroDeVerificacion(this.getNumeroDeVerificacion());
        medioEntity.setTipoCredito(this.getTipoCredito());
        medioEntity.setTipoTarjeta(this.getTipoTarjeta());
        return medioEntity;
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
     * @return the numeroTarjeta
     */
    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    /**
     * @param numeroTarjeta the numeroTarjeta to set
     */
    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    /**
     * @return the numeroDeVerificacion
     */
    public String getNumeroDeVerificacion() {
        return numeroDeVerificacion;
    }

    /**
     * @param numeroDeVerificacion the numeroDeVerificacion to set
     */
    public void setNumeroDeVerificacion(String numeroDeVerificacion) {
        this.numeroDeVerificacion = numeroDeVerificacion;
    }

    /**
     * @return the tipoTarjeta
     */
    public String getTipoTarjeta() {
        return tipoTarjeta;
    }

    /**
     * @param tipoTarjeta the tipoTarjeta to set
     */
    public void setTipoTarjeta(String tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }

    /**
     * @return the tipoCredito
     */
    public String getTipoCredito() {
        return tipoCredito;
    }

    /**
     * @param tipoCredito the tipoCredito to set
     */
    public void setTipoCredito(String tipoCredito) {
        this.tipoCredito = tipoCredito;
    }

}
