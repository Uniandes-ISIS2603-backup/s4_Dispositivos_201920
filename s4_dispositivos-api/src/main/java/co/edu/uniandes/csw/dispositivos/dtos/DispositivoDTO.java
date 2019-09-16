/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.dtos;

/**
 *
 * @author Estudiante
 */
public class DispositivoDTO {

    private String nombre;
    private String descripcion;
    private String modelo;

    private Double precio;
    private Double descuento;
    private Double precioImportacion;

    private boolean promocion;
    private boolean enStock;
    private boolean esImportado;
    private boolean usado;

    /**
     *
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     *
     * @return
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     *
     * @return
     */
    public String getModelo() {
        return modelo;
    }

    public Double getPrecio() {
        return precio;
    }

    /**
     *
     * @return
     */
    public Double getDescuento() {
        return descuento;
    }

    /**
     *
     * @return
     */
    public Double getPrecioImportacion() {
        return precioImportacion;
    }

    /**
     *
     * @return
     */
    public boolean isPromocion() {
        return promocion;
    }

    /**
     *
     * @return
     */
    public boolean isEnStock() {
        return enStock;
    }

    /**
     *
     * @return
     */
    public boolean isEsImportado() {
        return esImportado;
    }

    /**
     *
     * @return
     */
    public boolean isUsado() {
        return usado;
    }

    /**
     *
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     *
     * @param descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     *
     * @param modelo
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /**
     *
     * @param precio
     */
    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    /**
     *
     * @param descuento
     */
    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    /**
     *
     * @param precioImportacion
     */
    public void setPrecioImportacion(Double precioImportacion) {
        this.precioImportacion = precioImportacion;
    }

    /**
     *
     * @param promocion
     */
    public void setPromocion(boolean promocion) {
        this.promocion = promocion;
    }

    /**
     *
     * @param enStock
     */
    public void setEnStock(boolean enStock) {
        this.enStock = enStock;
    }

    /**
     *
     * @param esImportado
     */
    public void setEsImportado(boolean esImportado) {
        this.esImportado = esImportado;
    }

    /**
     *
     * @param usado
     */
    public void setUsado(boolean usado) {
        this.usado = usado;
    }

}
