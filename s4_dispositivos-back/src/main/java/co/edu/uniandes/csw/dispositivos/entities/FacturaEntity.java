/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.entities;

import co.edu.uniandes.csw.dispositivos.podam.DateStrategy;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamDoubleValue;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamIntValue;
import uk.co.jemos.podam.common.PodamStrategyValue;

/**
 *
 * @author Carlos Salazar
 */
@Entity
public class FacturaEntity extends BaseEntity implements Serializable {

    /**
     * Atributo que modela el numero de la factura.
     */
    @PodamIntValue(minValue = 1, maxValue = Integer.MAX_VALUE)
    private Integer numeroDeFactura;

    /**
     * Atributo que modela el total del pago de la factura.
     */
    @PodamDoubleValue(minValue = 1.0, maxValue = Double.MAX_VALUE)
    private Double totalPago;

    /**
     * Atributo que modela el porcentaje de impuestos de la factura.
     */
    @PodamDoubleValue(minValue = 0.0, maxValue = Double.MAX_VALUE)
    private Double impuestos;

    /**
     * Atributo que modela los dispositivos en la factura.
     */
    @PodamExclude
    @OneToMany
    private List<DispositivoEntity> dispositivos;

    /**
     * Atributo que modela al cliente de la factura.
     */
    @PodamExclude
    @ManyToOne(cascade = CascadeType.PERSIST)
    private ClienteEntity cliente;

    /**
     * Atributo que modela la venta asociada a la factura.
     */
    @PodamExclude
    @OneToOne
    private VentaEntity venta;

    /**
     * Fecha de pago de los dispositivos electronicos.
     */
    @Temporal(TemporalType.DATE)
    @PodamStrategyValue(DateStrategy.class)
    private Date fechaDePago;

    /**
     * Constructor creado vacio para no tener problemas al implementar.
     * Serializable.
     */
    public FacturaEntity() {
    }

    /**
     * Crea una nueva FacturaEntity.
     *
     * @param pNumeroDeFactura numero de factura a establecer.
     * @param pTotalPago pago total a establecer.
     * @param pImpuestos valor de impuestos a establecer.
     * @param pDispositivos dispositivos a establecer.
     * @param pFechaDePago fecha de pago a establecer.
     * @param venta venta asociada.
     */
    public FacturaEntity(Integer pNumeroDeFactura, Double pTotalPago, Double pImpuestos, List<DispositivoEntity> pDispositivos, Date pFechaDePago, VentaEntity venta) {
        this.numeroDeFactura = pNumeroDeFactura;
        this.totalPago = pTotalPago;
        this.impuestos = pImpuestos;
        this.dispositivos = pDispositivos;
        this.fechaDePago = pFechaDePago;
        this.venta = venta;
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
    public List<DispositivoEntity> getDispositivos() {
        return dispositivos;
    }

    /**
     * @param dispositivos the dispositivos to set
     */
    public void setDispositivos(List<DispositivoEntity> dispositivos) {
        this.dispositivos = dispositivos;
    }

    /**
     * Metodo no usado
     *
     * @param obj Object que se compara.
     * @return despreciado.
     * @deprecated (solo arregla code smell)
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
     * @deprecated (solo arregla code smell)
     */
    @Override
    @Deprecated
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * @return the fechaDePago
     */
    public Date getFechaDePago() {
        return fechaDePago;
    }

    /**
     * @param fechaDePago the fechaDePago to set
     */
    public void setFechaDePago(Date fechaDePago) {
        this.fechaDePago = fechaDePago;
    }

    /**
     * @return the cliente
     */
    public ClienteEntity getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(ClienteEntity cliente) {
        this.cliente = cliente;
    }

    /**
     * @return the venta
     */
    public VentaEntity getVenta() {
        return venta;
    }

    /**
     * @param venta the venta to set
     */
    public void setVenta(VentaEntity venta) {
        this.venta = venta;
    }

}
