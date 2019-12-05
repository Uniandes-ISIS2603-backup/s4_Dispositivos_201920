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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

    private String imagenes;

    @PodamExclude
    @ManyToOne(cascade = CascadeType.PERSIST)
    private MarcaEntity marca;

    @PodamExclude
    @ManyToOne(cascade = CascadeType.PERSIST)
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
    private Boolean promocion;
    private Boolean enStock;
    private Boolean esImportado;
    private Boolean usado;

    /**
     * Enums
     */
    @Enumerated(EnumType.ORDINAL)
    private Tipo tipo;
    @Enumerated(EnumType.ORDINAL)
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
     * @param modelo Modelo del dispositivo
     * @param descripcion Descripcion del dispositivo
     * @param nombre Nombre del dispositivo
     * @param precio Precio del dispositivo
     * @param precioImportacion Precio de importacion del dispositivo
     * @param descuento Descuento numerico del dispositivo
     * @param promocion Esta o no en promocion
     * @param enStock Esta o no en stock
     * @param usado Es usado o no
     * @param esImportado Es importado o no
     * @param imagenes Imagenes del dispositivo
     * @param tipo Tipo de dispositivo que se ingresa. {CELULAR, TABLET,
     * COMPUTADOR}
     * @param estado Estado del dispositivo que se ingresa
     * @param marca La marca a la que pertenece el dispositivo
     * @param calificaciones Las calificaciones del dispositivo. Inicialmenre
     * debe tener cero calificaciones
     * @param categoria Categoría del dispositivo
     */
    public DispositivoEntity(String modelo, String descripcion, String nombre, Double precio, Double precioImportacion,
            Double descuento, Boolean promocion, Boolean enStock, Boolean usado, Boolean esImportado, String imagenes,
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
        this.esImportado = esImportado;
        this.tipo = tipo;
        this.estado = estado;
        this.marca = marca;
        this.calificaciones = calificaciones;
        this.categoria = categoria;
    }

    /**
     * Retorna el modelo del dispositivo
     *
     * @return Modelo del dispositivo
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * Retorna la descripcion del dispositivo
     *
     * @return Descripcion del dispositivo
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Retorna el nombre del dispositivo
     *
     * @return Nombre del dispositivo
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Retorna todas las imagenes del dispositivo
     *
     * @return Imagenes del dispositivo
     */
    public String getImagenes() {
        return imagenes;
    }

    /**
     * Retorna el precio de dispositivo
     *
     * @return Precio del dispositivo
     */
    public Double getPrecio() {
        return precio;
    }

    /**
     * Retorna el precio de importacion del dispositivo. El dispositivo debe ser
     * importado
     *
     * @return Precio de importacion del dispositivo
     */
    public Double getPrecioImportacion() {
        return precioImportacion;
    }

    /**
     * Retorna el descuento del dispositivo. El dispositivo debe estar en
     * promocion
     *
     * @return Descuento del dispositivo
     */
    public Double getDescuento() {
        return descuento;
    }

    /**
     * Retorna si el dispositivo esta en promocion
     *
     * @return True si el dispositivo esta en promocion. False si no esta en
     * promocion
     */
    public Boolean isPromocion() {
        return promocion;
    }

    /**
     * Retorna si el dispositivo esta en stock
     *
     * @return True si esta en stock. False si no esta en stock
     */
    public Boolean isEnStock() {
        return enStock;
    }

    /**
     * Retorna si el dispositivo es importado
     *
     * @return True si el dispositivo es importado. False si el dispositivo no
     * es importado
     */
    public Boolean isEsImportado() {
        return esImportado;
    }

    /**
     * Retorna si el dispositivo es usado.
     *
     * @return True si el dispositivo es usado. False si el dispositivo no es
     * usado
     */
    public Boolean isUsado() {
        return usado;
    }

    /**
     * Retorna el tipo del dispositivo. {CELULAR, TABLET, COMPUTADOR}
     *
     * @return el tipo del celular
     */
    public Tipo getTipo() {
        return tipo;
    }

    /**
     * Retorna el estado del dispositivo. {NUEVO, USADO, IMPORTADO}
     *
     * @return el estado del dispositivo
     */
    public EstadoDispositivo getEstado() {
        return estado;
    }

    /**
     * Retorna la marca del dispositivo
     *
     * @return la marca del dispositivo
     */
    public MarcaEntity getMarca() {
        return marca;
    }

    /**
     * Retorna la categoria del dispositivo
     *
     * @return la categoria del dispositivo
     */
    public CategoriaEntity getCategoria() {
        return categoria;
    }

    /**
     * Retorna todas las calificaciones del dispositivo
     *
     * @return las calificaciones del dispositivo
     */
    public List<CalificacionEntity> getCalificaciones() {
        return calificaciones;
    }

    /**
     * Asigan la modelo al dispositivo
     *
     * @param modelo Modelo del dispositivo
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /**
     * Asigna una descripcion al dispositivo
     *
     * @param descripcion Descripcion del dispositivo
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Asigna un nombre al dispositivo
     *
     * @param nombre Nombre de dispositivo
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Asigan un objeto de tipo MediaEntity con las imagenes
     *
     * @param imagenes Objeto que contiene las imagenes correspondientes
     */
    public void setImagenes(String imagenes) {
        this.imagenes = imagenes;
    }

    /**
     * Asigna un precio al dispositivo
     *
     * @param precio Precio del dispositivo
     */
    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    /**
     * Asigna un precio de importacion si el dispositivo es importado
     *
     * @param precioImportacion Precio de la importacion del dispositivo
     */
    public void setPrecioImportacion(Double precioImportacion) {
        this.precioImportacion = precioImportacion;
    }

    /**
     * Asigan el descuento que se realiza sobre el precio total si el
     * dispositivo esta en promocion
     *
     * @param descuento Descuento que se realiza sobre el precio total
     */
    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    /**
     * Asigan si el dispositivo esta en promocion
     *
     * @param promocion True si el dispositivo esta en promocion. False si el no
     * esta en promocion
     */
    public void setPromocion(Boolean promocion) {
        this.promocion = promocion;
    }

    /**
     * Asigan si el dispositivo esta en el stock de la tienda
     *
     * @param enStock True si el dispositivo esta en stock. False si el
     * dispositivo no esta en stock
     */
    public void setEnStock(Boolean enStock) {
        this.enStock = enStock;
    }

    /**
     * Asigna si el dispositivo es importado
     *
     * @param esImportado True si el dispositivo es importado. False si el
     * dispositivo no es importado
     */
    public void setEsImportado(Boolean esImportado) {
        this.esImportado = esImportado;
    }

    /**
     * Asigna si el dispositivo es usado
     *
     * @param usado True si el dispositivo es usado. False si el dispositivo no
     * es usado
     */
    public void setUsado(Boolean usado) {
        this.usado = usado;
    }

    /**
     * Asigna el tipo del dispositivo
     *
     * @param tipo Tipo del dispositivo {CELULAR, TABLET, COMPUTADOR}
     */
    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    /**
     * Asigna el estado del dispositivo
     *
     * @param estado Estado del dispositivo {NUEVO, USADO, IMPORTADO}
     */
    public void setEstado(EstadoDispositivo estado) {
        this.estado = estado;
    }

    /**
     * Asigna la marca del dispositivo
     *
     * @param marca Marca del dispositivo
     */
    public void setMarca(MarcaEntity marca) {
        this.marca = marca;
    }

    /**
     * Asigna la categoria del dispositivo
     *
     * @param categoria Categoria del dispositivo
     */
    public void setCategoria(CategoriaEntity categoria) {
        this.categoria = categoria;
    }

    /**
     * Asigna las calificaciones del dispositivo
     *
     * @param calificaciones Calificaciones del dispositivo
     */
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
