
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.dtos;

import co.edu.uniandes.csw.dispositivos.entities.DispositivoEntity;
import co.edu.uniandes.csw.dispositivos.enu.EstadoDispositivo;
import co.edu.uniandes.csw.dispositivos.enu.Tipo;
import java.io.Serializable;

/**
 *
 * @author Estudiante
 */
public class DispositivoDTO implements Serializable {

    /**
     * Strings
     */
    private String modelo;
    private String descripcion;
    private String nombre;
    private String imagenes;

    /**
     * Doubles
     */
    private Double precio;
    private Double precioImportacion;
    private Double descuento;

    /**
     * Longs
     */
    private long id;

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
     * Clases de cardinalidad 1
     */
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
            this.id = dispositivo.getId();
            this.descripcion = dispositivo.getDescripcion();
            this.descuento = dispositivo.getDescuento();
            this.enStock = dispositivo.isEnStock();
            this.esImportado = dispositivo.isEsImportado();
            this.estado = dispositivo.getEstado();
            this.modelo = dispositivo.getModelo();
            this.nombre = dispositivo.getNombre();
            this.precio = dispositivo.getPrecio();
            this.precioImportacion = dispositivo.getPrecioImportacion();
            this.promocion = dispositivo.isPromocion();
            this.tipo = dispositivo.getTipo();
            this.usado = dispositivo.isUsado();
            this.imagenes = dispositivo.getImagenes();

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

        DispositivoEntity dispositivoEntity = new DispositivoEntity();
        dispositivoEntity.setId(this.getId());
        dispositivoEntity.setDescripcion(this.getDescripcion());
        dispositivoEntity.setDescuento(this.getDescuento());
        dispositivoEntity.setEnStock(this.isEnStock());
        dispositivoEntity.setEsImportado(this.isEsImportado());
        dispositivoEntity.setEstado(this.getEstado());
        dispositivoEntity.setModelo(this.getModelo());
        dispositivoEntity.setNombre(this.getNombre());
        dispositivoEntity.setPrecio(this.getPrecio());
        dispositivoEntity.setPrecioImportacion(this.getPrecioImportacion());
        dispositivoEntity.setPromocion(this.promocion);
        dispositivoEntity.setTipo(this.tipo);
        dispositivoEntity.setUsado(this.usado);
        dispositivoEntity.setImagenes(this.getImagenes());

        if (this.marca != null) {
            dispositivoEntity.setMarca(this.marca.toEntity());
        }
        if (this.categoria != null) {
            dispositivoEntity.setCategoria(this.categoria.toEntity());
        }
        return dispositivoEntity;
    }

    /**
     *
     * @return
     */
    public long getId() {
        return id;
    }

    /**
     *
     * @return
     */
    public Tipo getTipo() {
        return tipo;
    }

    public String getImagenes() {
        return imagenes;
    }

    /**
     *
     * @return
     */
    public EstadoDispositivo getEstado() {
        return estado;
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
     * @return
     */
    public MarcaDTO getMarca() {
        return marca;
    }

    /**
     *
     * @return
     */
    public CategoriaDTO getCategoria() {
        return categoria;
    }

    /**
     *
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     *
     * @param tipo
     */
    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    /**
     *
     * @param estado
     */
    public void setEstado(EstadoDispositivo estado) {
        this.estado = estado;
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

    public void setMarca(MarcaDTO marca) {
        this.marca = marca;
    }

    public void setCategoria(CategoriaDTO categoria) {
        this.categoria = categoria;
    }

    public void setImagen(String imagenes) {
        this.imagenes = imagenes;
    }

}
