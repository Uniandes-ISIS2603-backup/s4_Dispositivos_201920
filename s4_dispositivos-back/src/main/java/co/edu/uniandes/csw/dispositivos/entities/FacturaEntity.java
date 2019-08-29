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

    private Integer numeroDeFactura;
    private Double totalPago;
    private Double impuestos;
    private String dispositivos;

    public FacturaEntity() {
    }

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

    @Override
    public boolean equals(Object obj) {
        boolean resp = super.equals(obj);
        final FacturaEntity other = (FacturaEntity) obj;

        if (!resp) {
            return false;
        } else {
            if (this.dispositivos.equalsIgnoreCase(other.dispositivos) && this.numeroDeFactura == other.numeroDeFactura && this.totalPago == other.totalPago && this.impuestos == other.impuestos) {
                return true;
            }

            return false;
        }
    }
}
