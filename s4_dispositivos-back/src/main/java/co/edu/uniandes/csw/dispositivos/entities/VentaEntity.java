/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * 666
 *
 * @author Zharet Bautista Montes
 */
@Entity
public class VentaEntity extends BaseEntity implements Serializable {

    private Double precioReventa;

    @PodamExclude
    @ManyToOne(cascade = CascadeType.PERSIST)
    private VendedorEntity vendedor;

    @PodamExclude
    private List<MediaEntity> fotos;

    @PodamExclude
    @OneToOne(cascade = CascadeType.PERSIST, orphanRemoval = true)
    private FacturaEntity facturaOriginal;

    /**
     * Constructor vacío
     */
    public VentaEntity() {
    }

    /**
     * Constructor que recibe parámetros
     *
     * @param precioReventa
     * @param vendedor
     * @param facturaOriginal
     * @param fotos
     */
    public VentaEntity(Double precioReventa, VendedorEntity vendedor, FacturaEntity facturaOriginal, List<MediaEntity> fotos) {
        this.precioReventa = precioReventa;
        this.vendedor = vendedor;
        //this.fotos = fotos;
        //this.facturaOriginal = facturaOriginal;
    }

    /**
     * Retorna el precio de reventa
     *
     * @return precioReventa
     */
    public Double getPrecioReventa() {
        return this.precioReventa;
    }

    /**
     *
     * @param precioReventa
     */
    public void setPrecioReventa(Double precioReventa) {
        this.precioReventa = precioReventa;
    }

    /**
     * Método no requerido
     *
     * @param oe Objeto a comparar
     * @return Igual al de la superclase
     * @deprecated (Sólo se necesita para mejorar "Code Smell")
     */
    @Override
    @Deprecated
    public boolean equals(Object oe) {
        return super.equals(oe);
    }

    /**
     * Método no requerido
     *
     * @return Igual al de la superclase
     * @deprecated (Sólo se necesita para mejorar "Code Smell")
     */
    @Override
    @Deprecated
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * @return the vendedor
     */
    public VendedorEntity getVendedor() {
        return vendedor;
    }

    /**
     * @param vendedor the vendedor to set
     */
    public void setVendedor(VendedorEntity vendedor) {
        this.vendedor = vendedor;
    }

    //Encapsulamiento de asociaciones
    /**
     * @return the fotos
     *
     * public List<MediaEntity> getFotos() { return fotos; }
     *
     * /
     **
     * @param fotos the fotos to set
     *
     * public void setFotos(List<MediaEntity> fotos) { this.fotos = fotos; }
     *
     * /
     **
     * @return the facturaOriginal
     *
     * public FacturaEntity getFacturaOriginal() { return facturaOriginal; }
     *
     * /
     **
     * @param facturaOriginal the facturaOriginal to set
     *
     * public void setFacturaOriginal(FacturaEntity facturaOriginal) {
     * this.facturaOriginal = facturaOriginal; }
     */
}
