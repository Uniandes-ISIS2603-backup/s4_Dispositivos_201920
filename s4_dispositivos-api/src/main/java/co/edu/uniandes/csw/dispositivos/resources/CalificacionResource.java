/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.resources;

import co.edu.uniandes.csw.dispositivos.dtos.CalificacionDTO;
import co.edu.uniandes.csw.dispositivos.ejb.CalificacionLogic;
import co.edu.uniandes.csw.dispositivos.entities.CalificacionEntity;
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
 *
 * @author Javier Peniche
 */
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class CalificacionResource {
    
    @Inject
    private CalificacionLogic logica;
    
    @POST
    public CalificacionDTO createCalificacion(@PathParam("dispositivosId")Long dispositivosId, CalificacionDTO calificacionDTO) throws BusinessLogicException{
        CalificacionDTO nuevoDTO = new CalificacionDTO(logica.createCalificacion(dispositivosId, calificacionDTO.toEntity()));
        return nuevoDTO;
    }

    @GET
    public List<CalificacionDTO> getCalificacions(@PathParam("dispositivosId") Long dispositivoId) {
        List<CalificacionDTO> listaDTOs = listEntity2DTO(logica.getCalificaciones(dispositivoId));
        return listaDTOs;
    }

    @GET
    @Path("{calificacionesId: \\d+}")
    public CalificacionDTO getCalificacion(@PathParam("dispositivosId")Long dispositivosId,@PathParam("calificacionesId")Long calificacionesId) {
        CalificacionEntity entity = logica.getCalificacion(dispositivosId, calificacionesId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /dispositivos/" + dispositivosId + "/calificaciones/" + calificacionesId + " no existe.", 404);
        }
        CalificacionDTO DTO = new CalificacionDTO(entity);
        return DTO;
    }

    @PUT
    @Path("{calificacionesId: \\d+}")
    public CalificacionDTO updateCalificacion(@PathParam("dispositivosId") Long dispositivosId,@PathParam("calificacionesId") Long calificacionesId, CalificacionDTO calificacion) throws BusinessLogicException {
        if (calificacionesId.equals(calificacion.getId())) {
            throw new BusinessLogicException("Los ids del Review no coinciden.");
        }
        CalificacionEntity entity = logica.getCalificacion(dispositivosId, calificacionesId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /dispositivos/" + dispositivosId + "/calificaciones/" + calificacionesId + " no existe.", 404);

        }
        CalificacionDTO DTO = new CalificacionDTO(logica.updateCalificacion(dispositivosId, calificacion.toEntity()));
        return DTO;
    }

    @DELETE
    @Path("{calificacionesId: \\d+}")
    public void deleteCalificacion(@PathParam("dispositivosId") Long dispositivosId, @PathParam("calificacionesId") Long calificacionesId) throws BusinessLogicException {
        CalificacionEntity entity = logica.getCalificacion(dispositivosId, calificacionesId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /dispositivo/" + dispositivosId + "/calificaciones/" + calificacionesId + " no existe.", 404);
        }
        logica.deleteCalificacion(dispositivosId, calificacionesId);
    }
    
    private List<CalificacionDTO> listEntity2DTO(List<CalificacionEntity> entityList) {
        List<CalificacionDTO> list = new ArrayList<CalificacionDTO>();
        for (CalificacionEntity entity : entityList) {
            list.add(new CalificacionDTO(entity));
        }
        return list;
    }
}
