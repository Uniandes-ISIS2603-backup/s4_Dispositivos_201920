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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Zharet Bautista Montes
 */
@Entity
public class VentaEntity extends BaseEntity implements Serializable {

    private double precioReventa;
    
    /**
    @PodamExclude
    @ManyToOne(cascade = CascadeType.PERSIST)
    private VendedorEntity vendedor;
    
    @PodamExclude
    @OneToMany(mappedBy = "venta", cascade = CascadeType.PERSIST, orphanRemoval = false)
    private List<MediaEntity> fotos;
    
    @PodamExclude
    @OneToOne(cascade = CascadeType.PERSIST, orphanRemoval = true)
    private FacturaEntity facturaOriginal;
    */

    /**
     * Constructor vacío
     */
    public VentaEntity() 
    {    }

    /**
     * Constructor que recibe parámetros
     *
     * @param precioReventa
     */
    public VentaEntity(Double precioReventa) 
    {
        this.precioReventa = precioReventa;
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
     * @param oe Objeto a comparar
     * @return Igual al de la superclase
     * @deprecated (Sólo se necesita para mejorar "Code Smell")
     */
    @Override
    @Deprecated
    public boolean equals(Object oe) 
    {
        return super.equals(oe);
    }
    
    /**
     * Método no requerido
     * @return Igual al de la superclase
     * @deprecated (Sólo se necesita para mejorar "Code Smell")
     */
    @Override
    @Deprecated
    public int hashCode()
    {
        return super.hashCode();
    }
}
