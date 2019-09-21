/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.dtos;

import co.edu.uniandes.csw.dispositivos.entities.VentaEntity;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Zharet Bautista Montes
 */
public class VentaDetailDTO extends VentaDTO implements Serializable
{
    private VendedorDTO vendedor; 
    
    //private List<MediaDTO> fotos; 
    
    /**
     * Constructor vacío
     */
    public VentaDetailDTO() 
    {    }
    
    /**
     * Constructor basado en las entidad
     * @param refva
     */
    public VentaDetailDTO(VentaEntity refva) 
    {
        super(refva);
        if(refva != null)
        {    
            //vendedor = refva.getVendedor();
        }
    }
    
    /**
     * @return the vendedor
     */
    public VendedorDTO getVendedor() 
    {
        return vendedor;
    }

    /**
     * @param vendedor the vendedor to set
     */
    public void setVendedor(VendedorDTO vendedor) 
    {
        this.vendedor = vendedor;
    }
    
    @Override
    public VentaEntity toEntity()
    {
        VentaEntity extventa = super.toEntity();
        if(vendedor != null)
        {
            //extventa.setVendedor(vendedor); 
        }
        return extventa; 
    }
}
