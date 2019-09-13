/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.entities;

import javax.persistence.Entity;

/**
 *
 * @author Juan L
 */
@Entity
public class MedioDePagoEntity extends BaseEntity {

    /**
     * Representa el numero de una tarjeta.
     */
    private String numeroTarjeta;

    /**
     * Representa el numero de verificacion de una tarjeta.
     */
    private String numeroDeVerificacion;

    /**
     * Representa el tipo de tarjeta.
     */
    private String tipoTarjeta;

    /**
     * Representa el tipo de credito.
     */
    private String tipoCredito;

    /**
     * Crea un medio de pago vacío.
     */
    public MedioDePagoEntity() {

    }

    /**
     * Crea un medio de pago con la información pasada por parámetro.
     *
     * @param numeroTarjeta Numero de la tarjeta.
     * @param numeroDeVerificacion Código de verificación. Null si no tiene.
     * @param tipoTarjeta Tipo de Tarjeta. tipoTarjeta = (CREDITO, DEBITO)
     * @param tipoCredito Tipo de crédito. tipoCredito = (VISA, MASTERCARD),
     * NULL si no tiene crédito.
     */
    public MedioDePagoEntity(String numeroTarjeta, String numeroDeVerificacion, String tipoTarjeta, String tipoCredito) {
        this.numeroTarjeta = numeroTarjeta;
        this.numeroDeVerificacion = numeroDeVerificacion;
        this.tipoCredito = tipoCredito;
        this.tipoTarjeta = tipoTarjeta;
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
    
        /**
     * Metodo no usado
     *
     * @param obj Object que se compara.
     * @return despreciado.
     * @Ddeprecated (solo arregla code smell)
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
     * @Ddeprecated (solo arregla code smell)
     */
    @Override
    @Deprecated
    public int hashCode() {
        return super.hashCode();
    }

}
