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
public class VentaDetailDTO extends VentaDTO implements Serializable
{    
    /**
     * Vendedor del DTODetail
     */
    private VendedorDTO vendedor;
    
    /**
     * Factura original del DTODetail
     */
    private FacturaDTO facturaOriginal; 
    
    /**
     * Lista de fotos del DTODetail
     */
    private String[] fotos; 
    
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
            facturaOriginal = new FacturaDTO(refva.getFacturaOriginal());
            if(refva.getFotos().length != 0)
            {
                fotos = new String[refva.getFotos().length];
                fotos = refva.getFotos();
            }
        }
    }
    
    /**
     * Retorna el vendedor del DTODetail
     * @return the vendedor
     */
    public VendedorDTO getVendedor() 
    {
        return vendedor;
    }

    /**
     * Asigna el vendedor del DTODetail
     * @param vendedor the vendedor to set
     */
    public void setVendedor(VendedorDTO vendedor) 
    {
        this.vendedor = vendedor;
    }
    
    /**
     * Transforma el DTODetail en un Entity con asociaciones
     * @return VentaEntity
     */
    @Override
    public VentaEntity toEntity()
    {
        VentaEntity extventa = super.toEntity();
        if(vendedor != null)
        {
            extventa.setVendedor(vendedor.toEntity());
            extventa.setFacturaOriginal(facturaOriginal.toEntity());
            if(getFotos().length != 0)
            {
            String[] photolist = getFotos();
            extventa.setFotos(photolist); 
            }
        }
        return extventa; 
    }

    /**
     * Retorna la factura del DTODetail
     * @return the facturaOriginal
     */
    public FacturaDTO getFacturaOriginal() {
        return facturaOriginal;
    }

    /** 
     * Asigna la factura del DTODetail
     * @param facturaOriginal the facturaOriginal to set
     */
    public void setFacturaOriginal(FacturaDTO facturaOriginal) {
        this.facturaOriginal = facturaOriginal;
    }

    /**
     * Retorna las fotos del DTODetail
     * @return the fotos
     */
    public String[] getFotos() {
        return fotos;
    }

    /**
     * Asigna las fotos del DTODetail
     * @param fotos the fotos to set
     */
    public void setFotos(String[] fotos) {
        this.fotos = fotos;
    }                                                                                                                                                                                                                                                                                                                                                                                                                                                       
}
