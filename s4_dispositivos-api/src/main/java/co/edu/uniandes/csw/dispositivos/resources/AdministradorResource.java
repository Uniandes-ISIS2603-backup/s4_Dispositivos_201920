/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.resources;

import co.edu.uniandes.csw.dispositivos.dtos.AdministradorDTO;
import co.edu.uniandes.csw.dispositivos.ejb.AdministradorLogic;
import co.edu.uniandes.csw.dispositivos.entities.AdministradorEntity;
import co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

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
    @Inject
    private AdministradorLogic adminLogic;
    
    @POST
    public AdministradorDTO createAdministrador(AdministradorDTO administrador) throws BusinessLogicException {
        AdministradorDTO nuevoAdminDTO = new AdministradorDTO(adminLogic.createAdministrador(administrador.toEntity()));
        return nuevoAdminDTO;
    }
        
    @GET
    @Path("{adminId: \\d+}")
    public AdministradorDTO getAdministrador(@PathParam("adminId") Long adminId) throws BusinessLogicException
    {
        AdministradorEntity entity = adminLogic.getAdministrador(adminId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /administradores/" + adminId + " no existe.", 404);
        }
        AdministradorDTO adminDTO = new AdministradorDTO(entity);
        return adminDTO;
    }
    
    @PUT
    @Path("{adminId: \\d+}")
    public AdministradorDTO updateBook(@PathParam("adminId") Long adminId, AdministradorDTO admin) throws BusinessLogicException {
        admin.setId(adminId);
        if (adminLogic.getAdministrador(adminId) == null) 
        {
            throw new WebApplicationException("El recurso /administradores/" + adminId + " no existe.", 404);
        }
        AdministradorDTO adminDTO = new AdministradorDTO(adminLogic.updateAdministrador(adminId, admin.toEntity()));
        return adminDTO;
    }
    
    @DELETE
    @Path("{adminId: \\d+}")
    public void deleteBook(@PathParam("adminId") Long adminId) throws BusinessLogicException {
        AdministradorEntity entity = adminLogic.getAdministrador(adminId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /administradores/" + adminId + " no existe.", 404);
        }
        adminLogic.deleteAdministrador(adminId);
    }
    
    @GET
    public List<AdministradorDTO> getBooks() {
        List<AdministradorDTO> listaAdmins = listEntity2DetailDTO(adminLogic.getAdministradores());
        return listaAdmins;
    }
    
    private List<AdministradorDTO> listEntity2DetailDTO(List<AdministradorEntity> entityList) {
        List<AdministradorDTO> list = new ArrayList<>();
        for (AdministradorEntity entity : entityList) 
        {
            list.add(new AdministradorDTO(entity));
        }
        return list;
    }
    
}
