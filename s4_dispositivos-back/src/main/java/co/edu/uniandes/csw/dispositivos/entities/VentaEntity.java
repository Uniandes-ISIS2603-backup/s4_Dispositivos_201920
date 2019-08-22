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
    
    @PodamExclude
    @ManyToOne
    private VendedorEntity vendedor;
    
    /*
    @PodamExclude
    @OneToOne
    private FacturaEntity facturaOriginal;
    */

    public VentaEntity() 
    {    }

    public VentaEntity(double precioReventa, VendedorEntity vendedor) 
    {
        this.precioReventa = precioReventa;
        this.vendedor = vendedor;
    }
    
    public double getPrecioReventa() 
    {
        return precioReventa;
    }

    public void setPrecioReventa(double precioReventa) 
    {
        this.precioReventa = precioReventa;
    }

    public VendedorEntity getVendedor() 
    {
        return vendedor;
    }

    public void setVendedor(VendedorEntity vendedor) 
    {
        this.vendedor = vendedor;
    }   
}