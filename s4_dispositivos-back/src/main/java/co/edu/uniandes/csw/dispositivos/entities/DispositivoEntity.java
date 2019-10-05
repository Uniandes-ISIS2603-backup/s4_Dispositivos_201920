/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.entities;

import co.edu.uniandes.csw.dispositivos.enu.EstadoDispositivo;
import co.edu.uniandes.csw.dispositivos.enu.Tipo;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamDoubleValue;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Santiago Fajardo
 */
@Entity
public class DispositivoEntity extends BaseEntity implements Serializable {

    /**
     * Strings
     */
    private String modelo;
    private String descripcion;
    private String nombre;

    /**
     * Clases
     */
    @PodamExclude
    private MediaEntity imagenes;

    @PodamExclude
    @ManyToOne(cascade = CascadeType.ALL)
    private FacturaEntity factura;

    @PodamExclude
    @ManyToOne(cascade = CascadeType.ALL)
    private MarcaEntity marca;

    @PodamExclude
    @ManyToOne
    private CategoriaEntity categoria;

    @PodamExclude
    @OneToMany(mappedBy = "dispositivo", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<CalificacionEntity> calificaciones;

    /**
     * Doubles
     */
    @PodamDoubleValue(minValue = 1.0, maxValue = Double.MAX_VALUE)
    private Double precio;
    @PodamDoubleValue(minValue = 1.0, maxValue = Double.MAX_VALUE)
    private Double precioImportacion;
    @PodamDoubleValue(minValue = 1.0, maxValue = Double.MAX_VALUE)
    private Double descuento;

    /**
     * Booleans
     */
    private boolean promocion;
    private boolean enStock;
    private boolean esImportado;
    private boolean usado;

    /**
     * Enums
     */
    private Tipo tipo;
    private EstadoDispositivo estado;

    /**
     * Constructor vacio. Necesario para su implementacion en la DB
     */
    public DispositivoEntity() {

        /**
         * Constructor vacio. Necesario para su implementacion en la DB
         */
    }

    /**
     * Metodo Constructor Con Parametros
     *
     * @param modelo
     * @param descripcion
     * @param nombre
     * @param precio
     * @param precioImportacion
     * @param descuento
     * @param promocion
     * @param enStock
     * @param usado
     * @param esImportado
     * @param imagenes
     * @param factura
     * @param tipo
     * @param estado
     * @param marca
     * @param calificaciones
     */
    public DispositivoEntity(String modelo, String descripcion, String nombre, double precio, double precioImportacion,
            double descuento, boolean promocion, boolean enStock, boolean usado, boolean esImportado, MediaEntity imagenes, FacturaEntity factura,
            Tipo tipo, EstadoDispositivo estado, MarcaEntity marca, List<CalificacionEntity> calificaciones, CategoriaEntity categoria) {

        this.modelo = modelo;
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.imagenes = imagenes;
        this.precio = precio;
        this.precioImportacion = precioImportacion;
        this.descuento = descuento;
        this.promocion = promocion;
        this.enStock = enStock;
        this.usado = usado;
        this.factura = factura;
        this.esImportado = esImportado;
        this.tipo = tipo;
        this.estado = estado;
        this.marca = marca;
        this.calificaciones = calificaciones;
        this.categoria = categoria;
    }

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

    public FacturaEntity getFactura() {
        return factura;
    }

    public Double getPrecio() {
        return precio;
    }

    public Double getPrecioImportacion() {
        return precioImportacion;
    }

    public Double getDescuento() {
        return descuento;
    }

    public boolean isPromocion() {
        return promocion;
    }

    public boolean isEnStock() {
        return enStock;
    }

    public boolean isEsImportado() {
        return esImportado;
    }

    public boolean isUsado() {
        return usado;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public EstadoDispositivo getEstado() {
        return estado;
    }

    public MarcaEntity getMarca() {
        return marca;
    }

    public CategoriaEntity getCategoria() {
        return categoria;
    }

    public List<CalificacionEntity> getCalificaciones() {
        return calificaciones;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setImagenes(MediaEntity imagenes) {
        this.imagenes = imagenes;
    }

    public void setFactura(FacturaEntity factura) {
        this.factura = factura;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public void setPrecioImportacion(Double precioImportacion) {
        this.precioImportacion = precioImportacion;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    public void setPromocion(boolean promocion) {
        this.promocion = promocion;
    }

    public void setEnStock(boolean enStock) {
        this.enStock = enStock;
    }

    public void setEsImportado(boolean esImportado) {
        this.esImportado = esImportado;
    }

    public void setUsado(boolean usado) {
        this.usado = usado;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public void setEstado(EstadoDispositivo estado) {
        this.estado = estado;
    }

    public void setMarca(MarcaEntity marca) {
        this.marca = marca;
    }

    public void setCategoria(CategoriaEntity categoria) {
        this.categoria = categoria;
    }

    public void setCalificaciones(List<CalificacionEntity> calificaciones) {
        this.calificaciones = calificaciones;
    }

    /**
     * Metodo no usado
     *
     * @param obj Object que se compara.
     * @return Despreciado.
     * @deprecated (Solo Arregla Code Smell)
     */
    @Override
    @Deprecated
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    /**
     * Metodo no usado.
     *
     * @return nada.
     * @deprecated (Solo Arregla Code Smell)
     */
    @Override
    @Deprecated
    public int hashCode() {
        return super.hashCode();
    }

}
