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
public class VendedorDetailDTO extends VendedorDTO implements Serializable
{
    private List<VentaDTO> ventas; 
    
    /**
     * Constructor vacío
     */
    public VendedorDetailDTO() 
    {    }
}
