/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.dtos;

import co.edu.uniandes.csw.dispositivos.entities.DispositivoEntity;
import java.io.Serializable;

/**
 *
 * @author Estudiante
 */
public class DispositivoDTO implements Serializable {

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

    private MarcaDTO marca;
    private CategoriaDTO categoria;

    /**
     *
     */
    public DispositivoDTO() {

    }

    /**
     * Convierte DTO a ENTITY
     *
     * @param dispositivo
     */
    public DispositivoDTO(DispositivoEntity dispositivo) {
        if (dispositivo != null) {
            this.nombre = dispositivo.getNombre();
            this.descripcion = dispositivo.getDescripcion();
            this.descuento = dispositivo.getDescuento();
            this.enStock = dispositivo.isEnStock();
            this.esImportado = dispositivo.isEsImportado();
            this.modelo = dispositivo.getNombre();
            this.precio = dispositivo.getPrecio();
            this.precioImportacion = dispositivo.getPrecioImportacion();
            this.promocion = dispositivo.isPromocion();
            this.usado = dispositivo.isUsado();
            if (dispositivo.getMarca() != null) {
                this.marca = new MarcaDTO(dispositivo.getMarca());
            } else {
                this.marca = null;
            }
            if (dispositivo.getCategoria() != null) {
                this.categoria = new CategoriaDTO(dispositivo.getCategoria());
            } else {
                this.categoria = null;
            }
        }
    }

    public DispositivoEntity toEntity() {
        DispositivoEntity entity = new DispositivoEntity();
        entity.setDescripcion(this.descripcion);
        entity.setDescuento(this.descuento);
        entity.setEnStock(this.enStock);
        entity.setEsImportado(this.isEsImportado());
        entity.setModelo(this.modelo);
        entity.setNombre(this.nombre);
        entity.setPrecio(this.precio);
        entity.setPrecioImportacion(this.precioImportacion);
        entity.setPromocion(this.promocion);
        entity.setUsado(this.usado);

        if (this.marca != null) {
            entity.setMarca(this.marca.toEntity());
        }
        if (this.categoria != null) {
            entity.setCategoria(this.categoria.toEntity());
        }

        return entity;
    }

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
