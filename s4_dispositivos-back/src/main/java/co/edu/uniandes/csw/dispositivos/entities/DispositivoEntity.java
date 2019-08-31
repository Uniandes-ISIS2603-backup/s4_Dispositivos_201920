/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.entities;

import java.io.Serializable;
import java.util.Arrays;
import javax.persistence.Entity;

/**
 *
 * @author Santiago Fajardo
 */
@Entity
public class DispositivoEntity extends BaseEntity implements Serializable {

    /**
     * Atributos
     */
    private String modelo;
    private String descripcion;
    private String nombre;
    
    private MediaEntity imagenes;

    private double precio;
    private double precioImportacion;
    private double descuento;

    private boolean promocion;
    private boolean enStock;

    /**
     * Constructor vacio. Necesario para su implementacion en la DB
     */
    public DispositivoEntity() {

        /**
         * Constructor vacio. Necesario para su implementacion en la DB
         */
    }

 /**
  * Metodo Constructor con Parametros.
  * @param modelo
  * @param descripcion
  * @param nombre
  * @param imagenes
  * @param precio
  * @param precioImportacion
  * @param descuento
  * @param promocion
  * @param enStock 
  */
    public DispositivoEntity(String modelo, String descripcion, String nombre, 
            MediaEntity imagenes, double precio, double precioImportacion, double descuento, boolean promocion, boolean enStock) {
        
        this.modelo = modelo;
        this.descripcion = descripcion; 
        this.nombre = nombre; 
        this.imagenes = imagenes; 
        this.precio = precio; 
        this.precioImportacion = precioImportacion;
        this.descuento = descuento; 
        this.promocion = promocion; 
        this.enStock = enStock;
    }

    /**
     * Metodos
     */
    //Getters 
    public String getModelo() {
        return modelo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public MediaEntity getImagenes() {
        return imagenes;
    }

    public double getPrecio() {
        return precio;
    }

    public double getPrecioImportacion() {
        return precioImportacion;
    }

    public double getDescuento() {
        return descuento;
    }

    public boolean isEnStock() {
        return enStock;
    }

    public boolean isPromocion() {
        return promocion;
    }

    //Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setImagenes(MediaEntity imagenes) {
        this.imagenes = imagenes;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public void setPrecioImportacion(double precioImportacion) {
        this.precioImportacion = precioImportacion;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setEnStock(boolean enStock) {
        this.enStock = enStock;
    }

    public void setPromocion(boolean promocion) {
        this.promocion = promocion;
    }

    /**
     * Se sobre escribe el metodo equals
     *
     * @param obj a comparar
     * @return true si son iguales. false si son distintos
     */
    @Override
    public boolean equals(Object obj) {
        boolean resp = super.equals(obj);
        boolean fin = false;
        final DispositivoEntity other = (DispositivoEntity) obj;

        if (!resp) {
            return fin;
        } else {
            if ((this.descripcion.compareToIgnoreCase(other.descripcion) == 0) && (this.descuento == other.descuento) && (this.enStock == other.enStock)
                    && (this.modelo.compareTo(other.modelo) == 0) && (this.nombre.compareTo(other.nombre) == 0) && (this.precio == other.precio)
                    && (this.precioImportacion == other.precioImportacion) && (this.promocion == other.promocion) && (Arrays.equals(this.imagenes.getLinks(), other.imagenes.getLinks()))) {
                fin = true;
                return fin;
            }
        }

        return fin;
    }

}
