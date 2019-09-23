/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.dtos;

import co.edu.uniandes.csw.dispositivos.entities.FacturaEntity;
import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Carlos Salazar
 */
public class FacturaDTO implements Serializable {

    /**
     * atributo que modela el id de la factura.
     */
    private Long id;

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
     * Fecha de pago de los dispositivos electronicos
     */
    private Date fechaDePago;

    /**
     * Constructor creado vacio para no tener problemas al implementar
     * Serializable
     */
    public FacturaDTO() {
    }

    /**
     * Constructor a partir de la entidad
     *
     * @param factura La entidad de la factura.
     */
    public FacturaDTO(FacturaEntity factura) {
        if (factura != null) {
            this.id = factura.getId();
            this.numeroDeFactura = factura.getNumeroDeFactura();
            this.totalPago = factura.getTotalPago();
            this.impuestos = factura.getImpuestos();
            this.fechaDePago = factura.getFechaDePago();
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    /**
     * @return the numeroDeFactura
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the numeroDeFactura
     */
    public Integer getNumeroDeFactura() {
        return numeroDeFactura;
    }

    /**
     * Método para transformar el DTO a una entidad.
     *
     * @return La entidad de la factura asociada.
     */
    public FacturaEntity toEntity() {
        FacturaEntity facturaEntity = new FacturaEntity();
        facturaEntity.setId(this.getId());
        facturaEntity.setNumeroDeFactura(this.getNumeroDeFactura());
        facturaEntity.setTotalPago(this.getTotalPago());
        facturaEntity.setImpuestos(this.getImpuestos());
        facturaEntity.setFechaDePago(this.getFechaDePago());
        return facturaEntity;
    }

    /**
     * @return the totalPago
     */
    public Double getTotalPago() {
        return totalPago;
    }

    /**
     * @param numeroDeFactura the numeroDeFactura to set
     */
    public void setNumeroDeFactura(Integer numeroDeFactura) {
        this.numeroDeFactura = numeroDeFactura;
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
     * @param fechaDePago the fechaDePago to set
     */
    public void setFechaDePago(Date fechaDePago) {
        this.fechaDePago = fechaDePago;
    }

    /**
     * @return the fechaDePago
     */
    public Date getFechaDePago() {
        return fechaDePago;
    }

}
