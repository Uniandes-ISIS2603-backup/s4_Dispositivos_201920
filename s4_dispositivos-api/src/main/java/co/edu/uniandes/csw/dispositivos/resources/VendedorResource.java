/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.resources;

import co.edu.uniandes.csw.dispositivos.dtos.VendedorDTO;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author Zharet Bautista Montes
 */
@Path("vendedor")
@Produces("application/json")
@Consumes("application/json") 
@RequestScoped
public class VendedorResource 
{
    @POST
    public VendedorDTO createVenta(VendedorDTO venta)
    {
        return venta;
    }
}
