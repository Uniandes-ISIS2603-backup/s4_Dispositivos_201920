/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Zharet Bautista Montes
 */

@Entity 
public class VentaEntity extends BaseEntity implements Serializable
{
    private double precioReventa;
    
    /**
    @PodamExclude
    @ManyToOne
    private VendedorEntity vendedor;
    */
    
    /*
    @PodamExclude
    @OneToOne
    private FacturaEntity facturaOriginal;
    */

    /**
     * Constructor vacío
     */
    public VentaEntity() 
    {    }

    /**
     * Constructor que recibe parámetros 
     * @param precioReventa
     * @param vendedor 
     */
    public VentaEntity(double precioReventa, VendedorEntity vendedor) 
    {
        this.precioReventa = precioReventa;
        //.vendedor = vendedor;
    }
    
    /**
     * Retorna el precio de reventa
     * @return precioReventa
     */
    public double getPrecioReventa() 
    {
        return precioReventa;
    }

    /**
     * 
     * @param precioReventa 
     */
    public void setPrecioReventa(double precioReventa) 
    {
        this.precioReventa = precioReventa;
    }

    /**
     * Retorna el vendedor asociado  
     * @return vendedor
    */
     
    /**
    public VendedorEntity getVendedor() 
    {
        return vendedor;
    }
    */

    /**
     * 
     * @param vendedor 
     */
    /**
    public void setVendedor(VendedorEntity vendedor) 
    {
        this.vendedor = vendedor;
    } 
    */
}