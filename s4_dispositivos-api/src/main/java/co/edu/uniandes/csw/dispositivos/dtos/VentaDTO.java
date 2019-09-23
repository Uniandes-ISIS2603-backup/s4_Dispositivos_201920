/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.dtos;

import co.edu.uniandes.csw.dispositivos.entities.VentaEntity;
import java.io.Serializable;

/**
 *
 * @author Zharet Bautista Montes
 */
public class VentaDTO implements Serializable
{
    private Long id; 
    private Double precioReventa;   
    
    /**
     * Constructor vacío
     */
    public VentaDTO() 
    {    }
    
    /**
     * Constructor a partir de la entidad
     * @param vaentity
     */
    public VentaDTO(VentaEntity vaentity) 
    { 
        if(vaentity != null)
        {
            id = vaentity.getId();
            precioReventa = vaentity.getPrecioReventa(); 
        }
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
    
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
    
    public VentaEntity toEntity()
    {
        VentaEntity venta = new VentaEntity();
        venta.setId(id);
        venta.setPrecioReventa(precioReventa);
        return venta;     
    }
}