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
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private static final Logger LOGGER = Logger.getLogger(AdministradorResource.class.getName());
    /**
     * Inyección de la clase administrador de lógica
     */
    @Inject
    private AdministradorLogic adminLogic;
    /**
     * Crea un nuevo administrador con la informacion que se recibe en el cuerpo de la
     * petición y se regresa un objeto identico con un id auto-generado por la base de datos
     * @param admin EL administrador que se desea guardar
     * @return JSON El administrador guardado con el atributo id autogenerado
     * @throws BusinessLogicException Error de lógica que se genera cuando ya existe el administrador
     */
    @POST
    public AdministradorDTO createAdministrador(AdministradorDTO admin) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "AdministradorResource createAdministrador: input: {0}", admin);
        AdministradorDTO nuevoAdminDTO = new AdministradorDTO(adminLogic.createAdministrador(admin.toEntity()));
        LOGGER.log(Level.INFO, "AdministradorResource createAdministrador: output: {0}", nuevoAdminDTO);
        return nuevoAdminDTO;
    }
    /**
     * Busca y devuelve todos los administradores que existen en la aplicación
     * @return JSONArray Los administradores encontrados en la aplicación. Si no hay ninguno retorna una lista vacía.
     */
        @GET
    public List<AdministradorDTO> getAdministradores()
    {
        LOGGER.info("AdministradorResource getAdministradores: input: void");
        List<AdministradorDTO> listaAdmins = listEntity2DetailDTO(adminLogic.getAdministradores());
        LOGGER.log(Level.INFO, "AdministradorResource getAdministradores: output: {0}", listaAdmins);
        return listaAdmins;
    }
    /**
     * Busca el administrador con el id asociado recibido en la URL
     * @param adminId Identificador del administrador que se esta buscando
     * @return JSON El administrador buscado
     * @throws WebApplicationException Error de lógica que se genera cuando no se encuentra el administrador
     */
    @GET
    @Path("{administradorId: \\d+}")
    public AdministradorDTO getAdministrador(@PathParam("administradorId") Long adminId)
    {
        LOGGER.log(Level.INFO, "AdministradorResource getAdministrador: input: {0}", adminId);
        AdministradorEntity adminEntity = adminLogic.getAdministrador(adminId);
        if (adminEntity == null) {
            throw new WebApplicationException("El recurso /administradorGet/" + adminId + " no existe.", 404);
        }
        AdministradorDTO adminDTO = new AdministradorDTO(adminEntity);
        LOGGER.log(Level.INFO, "AdministradorResource getAdministrador: output: {0}", adminDTO);
        return adminDTO;
    }
    /**
     * Actualiza el administrador con el id recibido en la URL
     * @param adminId Identificador del administrador que se desea actualizara
     * @param admin El administrador que se desea guardar
     * @return JSON El administrador guardado
     * @throws WebApplicationException Error de lógica que se genera cuando no se encuentra el administrador a actualizar.
     * @throws BusinessLogicException Error de lógica que se genera cuando no se puede actualizar el administrador.
     */
    @PUT
    @Path("{administradorId: \\d+}")
    public AdministradorDTO updateAdministrador(@PathParam("administradorId") Long adminId, AdministradorDTO admin) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "AdministradorResource updateAdministrador: input: id: {0} , administrador: {1}", new Object[]{adminId, admin});
        admin.setId(adminId);
        if (adminLogic.getAdministrador(adminId) == null)
            throw new WebApplicationException("El recurso /administradorUpdate/" + adminId + " no existe.", 404);
        
        AdministradorDTO adminDTO = new AdministradorDTO(adminLogic.updateAdministrador(adminId, admin.toEntity()));
        LOGGER.log(Level.INFO, "AdministradorResource updateAdministrador: output: {0}", adminDTO);
        return adminDTO;
    }

    /**
     * Borra el administrador con el id asociado recibido en la URL
     * @param adminId Identificador del administrador que se desea borrar
     * @throws WebApplicationException Error de lógica que se genera cuando no se encuentra el libro.
     */
    @DELETE
    @Path("{administradorId: \\d+}")
    public void deleteBook(@PathParam("administradorId") Long adminId) throws WebApplicationException
    {
        LOGGER.log(Level.INFO, "AdministradorResource deleteAdministrador: input: {0}", adminId);
        AdministradorEntity entity = adminLogic.getAdministrador(adminId);
        if (entity == null)
            throw new WebApplicationException("El recurso /administradorDelete/" + adminId + " no existe.", 404);
        
        LOGGER.info("AdministradorResource deleteAdministrador: output: void");
    }
    /**
     * Convierte una lista de entidades a DTO
     * @param entityList corresponde a la lista de administradores de tipo Entity
     * @return la lista de adminstradores en forma DTO (json)
     */
    private List<AdministradorDTO> listEntity2DetailDTO(List<AdministradorEntity> entityList) {
        List<AdministradorDTO> list = new ArrayList<>();
        for (AdministradorEntity entity : entityList)
        {
            list.add(new AdministradorDTO(entity));
        }
        return list;
    }

}
