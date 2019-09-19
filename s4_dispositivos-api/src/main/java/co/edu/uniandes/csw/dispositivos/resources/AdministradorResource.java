/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.resources;

import co.edu.uniandes.csw.dispositivos.dtos.AdministradorDTO;
import co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * Clase que implementa el recurso "administrador"
 * @author Dianis Caro
 */
@Path("administradores")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class AdministradorResource 
{    
    @POST
    public AdministradorDTO createAdministrador(AdministradorDTO administrador) throws BusinessLogicException {
        return null;
    }
        
    @GET
    @Path("{adminId: \\d+}")
    public AdministradorDTO getAdministrador(@PathParam("adminId") Long adminId) throws BusinessLogicException
    {
        return null;
    }
    
    @PUT
    @Path("{adminId: \\d+}")
    public AdministradorDTO updateBook(@PathParam("adminId") Long adminId, AdministradorDTO admin) throws BusinessLogicException {
        return null;
    }
    
    @DELETE
    @Path("{adminId: \\d+}")
    public void deleteBook(@PathParam("adminId") Long adminId) throws BusinessLogicException {
    }
    
    @GET
    public List<AdministradorDTO> getBooks() {
        return null;
    }
    
}
