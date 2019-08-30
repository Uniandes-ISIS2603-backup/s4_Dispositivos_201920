/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.entities;

import java.io.Serializable;
import javax.persistence.Entity;

/**
 *
 * @author Zharet Bautista Montes
 */

@Entity 
public class VentaEntity extends BaseEntity implements Serializable
{
    private double precioReventa;

    /**
     * Constructor vacío
     */
    public VentaEntity() 
    {    }

    /**
     * Constructor que recibe parámetros 
     * @param precioReventa
     */
    public VentaEntity(double precioReventa) 
    {
        this.precioReventa = precioReventa;
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
     * 
     * @param oe 
     */
    @Override
    public boolean equals(Object oe)
    {
        boolean answer = super.equals(oe), end = false;
        final VentaEntity another = (VentaEntity) oe;
        if(answer && this.getId().equals(another.getId()))
            end = true;
        return end;
    }
}