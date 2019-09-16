/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.dtos;

import java.io.Serializable;

/**
 *
 * @author Zharet Bautista Montes
 */
public class VentaDTO implements Serializable
{
    private double precioReventa;
    
    /**
     * Constructor vacío
     */
    public VentaDTO() 
    {    }

    /**
     * @return the precioReventa
     */
    public double getPrecioReventa() 
    {
        return precioReventa;
    }

    /**
     * @param precioReventa the precioReventa to set
     */
    public void setPrecioReventa(double precioReventa) 
    {
        this.precioReventa = precioReventa;
    }
}
