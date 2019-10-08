/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.dtos;

import co.edu.uniandes.csw.dispositivos.entities.VentaEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Zharet Bautista Montes
 */
public class VentaDTO implements Serializable
{
    /**
     * ID del DTO
     */
    private Long id; 
    
    /**
     * Precio de reventa del DTO
     */
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
     * Retorna el id del DTO
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Asigna el id del DTO
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * Retorna el precio de reventa del DTO
     * @return the precioReventa
     */
    public double getPrecioReventa() 
    {
        return precioReventa;
    }

    /**
     * Asigna el precio de reventa del DTO
     * @param precioReventa the precioReventa to set
     */
    public void setPrecioReventa(double precioReventa) 
    {
        this.precioReventa = precioReventa;
    }
    
    /**
     * Transforma el DTO en un Entity
     * @return VentaEntity
     */
    public VentaEntity toEntity()
    {
        VentaEntity venta = new VentaEntity();
        venta.setId(id);
        venta.setPrecioReventa(precioReventa);
        return venta;     
    }
    
    /**
     * Sobreescritura de la conversión a cadena de caracteres
     */
    @Override
    public String toString() 
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}