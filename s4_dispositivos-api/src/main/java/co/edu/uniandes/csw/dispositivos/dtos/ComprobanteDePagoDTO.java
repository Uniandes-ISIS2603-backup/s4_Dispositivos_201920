/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.dtos;

import co.edu.uniandes.csw.dispositivos.entities.ComprobanteDePagoEntity;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Objeto de transferencia de datos de ComprobanteDePago. Los DTO contienen
 * las representaciones de los JSON que se transfieren entre el cliente y el
 * servidor.
 * @author Dianis Caro
 */
public class ComprobanteDePagoDTO 
{
    /**
     * Identificador del comprobante de pago
     */
    private Long id;
    /**
     * Numero de factura emitido
     */
    private Integer numeroDeFactura;
    /**
     * Total pagado en la transacción por los dispositivos
     */
    private Double totalDePago;
    /**
     * Impuestos generados a los productos
     */
    private Double impuestos;
    /**
     * Número de tarjeta con el cual se genero la compra
     */
    private String numeroDeTarjeta;
    /**
     * Fecha de compra de los dispositivos electronicos
     */
    private Date fechaDeFactura;
    /**
     * Constructor de la clase
     */
    public ComprobanteDePagoDTO()
    {
    }
    /**
     * Constructor de la clase
     * @param comprobante  Entidad del comprobante de pago a crear
     */
    public ComprobanteDePagoDTO(ComprobanteDePagoEntity comprobante)
    { 
        if(comprobante != null)
        {
            this.id = comprobante.getId();
            this.numeroDeFactura = comprobante.getNumeroDeFactura();
            this.totalDePago = comprobante.getTotalDePago();
            this.impuestos = comprobante.getImpuestos();
            this.numeroDeTarjeta = comprobante.getNumeroDeTarjeta();
            this.fechaDeFactura = comprobante.getFechaDeFactura();
        }
    }
    /**
     * Método para transformar el DTO a una entidad
     * @return La entidad del comprobante de pago asociado
     */
    public ComprobanteDePagoEntity toEntity()
    {
        ComprobanteDePagoEntity comprobanteEntity = new ComprobanteDePagoEntity();
        comprobanteEntity.setId(this.id);
        comprobanteEntity.setNumeroDeFactura(this.numeroDeFactura);
        comprobanteEntity.setTotalDePago(this.totalDePago);
        comprobanteEntity.setImpuestos(this.impuestos);
        comprobanteEntity.setNumeroDeTarjeta(this.numeroDeTarjeta);
        comprobanteEntity.setFechaDeFactura(this.fechaDeFactura);
        
        return comprobanteEntity;
    }
    /**
     * @return el identificador de ComprobanteDePago
     */
    public Long getId() {
        return id;
    }
    /**
     * Modifica el idntificador de ComprobanteDePago
     * @param id nuevo identificador
     */
    public void setId(Long id) {
        this.id = id;
    }
    /**
     * @return número de factura del ComprobanteDePago
     */
    public Integer getNumeroDeFactura() {
        return numeroDeFactura;
    }
    /**
     * Modifica el número de factura del ComprobanteDePago
     * @param numeroDeFactura nuevo número de comprobante
     */
    public void setNumeroDeFactura(Integer numeroDeFactura) {
        this.numeroDeFactura = numeroDeFactura;
    }
    /**
     * @return total a pagar del ComprobanteDePago
     */
    public Double getTotalDePago() {
        return totalDePago;
    }
    /**
     * Modifica el total a pagar del ComprobanteDePago
     * @param totalDePago nuevo total a pagar
     */
    public void setTotalDePago(Double totalDePago) {
        this.totalDePago = totalDePago;
    }
    /**
     * @return impuestos del ComprobanteDePago
     */
    public Double getImpuestos() {
        return impuestos;
    }
    /**
     * Modifica los impuestos del ComprobanteDePago
     * @param impuestos nuevo impuesto
     */
    public void setImpuestos(Double impuestos) {
        this.impuestos = impuestos;
    }
    /**
     * @return número de tarjeta del ComprobanteDePago
     */
    public String getNumeroDeTarjeta() {
        return numeroDeTarjeta;
    }
    /**
     * Modifica el número de tarjeta del ComprobanteDePago
     * @param numeroDeTarjeta nuevo número de tarjeta
     */
    public void setNumeroDeTarjeta(String numeroDeTarjeta) {
        this.numeroDeTarjeta = numeroDeTarjeta;
    }
    /**
     * @return fecha en la cual se generó el ComprobanteDePago
     */
    public Date getFechaDeFactura() {
        return fechaDeFactura;
    }
    /**
     * Modifica la fecha del ComprobanteDePago
     * @param fechaDeFactura nueva fecha
     */
    public void setFechaDeFactura(Date fechaDeFactura) {
        this.fechaDeFactura = fechaDeFactura;
    }
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
