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
 * @author Carlos Salazar
 */
@Entity
public class FacturaEntity extends BaseEntity implements Serializable {

    /**
     * Atributo que modela el numero de la factura.
     */
    private Integer numeroDeFactura;

    /**
     * Atributo que modela el total del pago de la factura.
     */
    private Double totalPago;

    /**
     * Atributo que modela el porcentaje de impuestos de la factura.
     */
    private Double impuestos;

    /**
     * Atributo que modela los dispositivos en la factura.
     */
    private String dispositivos;

    /**
     * Constructor creado vacio para no tener problemas al implementar
     * Serializable
     */
    public FacturaEntity() {
    }

    /**
     * Crea una nueva FacturaEntity.
     *
     * @param pNumeroDeFactura numero de factura a establecer.
     * @param pTotalPago pago total a establecer.
     * @param pImpuestos valor de impuestos a establecer.
     * @param pDispositivios dispositivos a establecer.
     */
    public FacturaEntity(Integer pNumeroDeFactura, Double pTotalPago, Double pImpuestos, String pDispositivos) {
        this.numeroDeFactura = pNumeroDeFactura;
        this.totalPago = pTotalPago;
        this.impuestos = pImpuestos;
        this.dispositivos = pDispositivos;
    }

    /**
     * @return the numeroDeFactura
     */
    public Integer getNumeroDeFactura() {
        return numeroDeFactura;
    }

    /**
     * @param numeroDeFactura the numeroDeFactura to set
     */
    public void setNumeroDeFactura(Integer numeroDeFactura) {
        this.numeroDeFactura = numeroDeFactura;
    }

    /**
     * @return the totalPago
     */
    public Double getTotalPago() {
        return totalPago;
    }

    /**
     * @param totalPago the totalPago to set
     */
    public void setTotalPago(Double totalPago) {
        this.totalPago = totalPago;
    }

    /**
     * @return the impuestos
     */
    public Double getImpuestos() {
        return impuestos;
    }

    /**
     * @param impuestos the impuestos to set
     */
    public void setImpuestos(Double impuestos) {
        this.impuestos = impuestos;
    }

    /**
     * @return the dispositivos
     */
    public String getDispositivos() {
        return dispositivos;
    }

    /**
     * @param dispositivos the dispositivos to set
     */
    public void setDispositivos(String dispositivos) {
        this.dispositivos = dispositivos;
    }

   
}
