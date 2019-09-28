/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.dtos;

import co.edu.uniandes.csw.dispositivos.entities.MediaEntity;
import co.edu.uniandes.csw.dispositivos.entities.VentaEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Zharet Bautista Montes
 */
public class VentaDetailDTO extends VentaDTO implements Serializable
{
    private VendedorDTO vendedor;
    
    //private FacturaDTO facturaOriginal; 
    
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
            vendedor = new VendedorDTO(refva.getVendedor());
            /**
            facturaOriginal = new FacturaDTO(refva.getFacturaOriginal());
            if(refva.getFotos() != null)
            {
                fotos = new ArrayList<>(); 
                for(MediaEntity mediava : refva.getFotos())
                    fotos.add(new MediaDTO(mediava));
            }
            */
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
            extventa.setVendedor(vendedor.toEntity());
            /**
            extventa.setFacturaOriginal(facturaOriginal.toEntity());
            if(getFotos() != null)
            {
            List<MediaEntity> photolist = new ArrayList<>();
            for(MediaDTO dtomedia : getFotos())
                photolist.add(dtomedia.toEntity());
            extventa.setFotos(photolist); 
            }
            */
        }
        return extventa; 
    }

    /**
     * @return the facturaOriginal
     
    public FacturaDTO getFacturaOriginal() {
        return facturaOriginal;
    }

    /**
     * @param facturaOriginal the facturaOriginal to set
     
    public void setFacturaOriginal(FacturaDTO facturaOriginal) {
        this.facturaOriginal = facturaOriginal;
    }

    /**
     * @return the fotos
     
    public List<MediaDTO> getFotos() {
        return fotos;
    }

    /**
     * @param fotos the fotos to set
     
    public void setFotos(List<MediaDTO> fotos) {
        this.fotos = fotos;
    }
    */
}
