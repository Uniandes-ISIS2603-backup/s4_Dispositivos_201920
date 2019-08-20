/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.entities;

import javax.persistence.Entity;

/**
 *
 * @author Estudiante
 */

@Entity 
public class VentaEntity extends BaseEntity
{
    private double precioReventa;  

    public VentaEntity() 
    {    }

    public VentaEntity(double precioReventa) 
    {
        this.precioReventa = precioReventa;
    }
    
    public double getPrecioReventa() 
    {
        return precioReventa;
    }

    public void setPrecioReventa(double precioReventa) 
    {
        this.precioReventa = precioReventa;
    }   
}