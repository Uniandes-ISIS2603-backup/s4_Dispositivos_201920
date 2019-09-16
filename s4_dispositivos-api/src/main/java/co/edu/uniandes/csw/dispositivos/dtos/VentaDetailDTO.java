/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.dtos;

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
}
