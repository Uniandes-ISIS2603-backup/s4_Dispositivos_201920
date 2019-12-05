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
import java.util.logging.Logger;
import java.util.logging.Level;
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
 * @author Juan L
 */
@Path("calificaciones")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class CalificacionResource {

    private static final Logger LOGGER = Logger.getLogger(MedioDePagoResource.class.getName());

    @Inject
    private CalificacionLogic calificacionLogic;

    //@PathParam("calificacionId") Long calificacionId,
    @POST
    public CalificacionDTO createCalificacion(CalificacionDTO pCalificacion) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "CalificacionResource createCalificacion: input: {0}", pCalificacion);
        CalificacionEntity calificacionEntity = pCalificacion.toEntity();
        CalificacionEntity nuevoCalificacionEntity = calificacionLogic.createCalificacion(calificacionEntity);
        CalificacionDTO nuevoCategoriaDTO = new CalificacionDTO(nuevoCalificacionEntity);
        LOGGER.log(Level.INFO, "CalificacionResource createCalificacion: output: {0}", nuevoCategoriaDTO);
        return nuevoCategoriaDTO;
    }

    @GET
    public List<CalificacionDTO> getCalificaciones(@PathParam("calificacionId") Long calificacionId) {
        LOGGER.info("CalificacionResource getCalificacion: input: void");
        List<CalificacionDTO> listaCalificaciones = listEntity2DetailDTO(calificacionLogic.getCalificaciones());
        LOGGER.log(Level.INFO, "CalificacionResource getCalificacion: output: {0}", listaCalificaciones);
        return listaCalificaciones;
    }

    @GET
    @Path("{calificacionId: \\d+}")
    public CalificacionDTO getCalificacion(@PathParam("calificacionId") Long calificacionId) throws BusinessLogicException {
                LOGGER.log(Level.INFO, "CalificacionResource getCalificacion: input: {0}", calificacionId);
        CalificacionEntity calificacionEntity = calificacionLogic.getCalificacion(calificacionId);
        if (calificacionEntity == null) {
            throw new WebApplicationException("El recurso calificacion/" + calificacionId + " no existe", 404);
        }
        CalificacionDTO detailDTO = new CalificacionDTO(calificacionEntity);
        LOGGER.log(Level.INFO, "CalificacionResource getCalificacion: output: {0}", detailDTO);
        return detailDTO;
    }

    @PUT
    @Path("{calificacionId: \\d+}")
    public CalificacionDTO updateCalificacion(@PathParam("calificacionId") Long calificacionId, CalificacionDTO calificacion) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "CalificacionResource updateCalificacion: input: id:{0} , calificacion: {1}", new Object[]{calificacionId, calificacion});
        calificacion.setId(calificacionId);
        if (calificacionLogic.getCalificacion(calificacionId) == null) {
            throw new WebApplicationException("El recurso /categorias/" + calificacionId + " no existe.", 404);
        }
        CalificacionDTO detailDTO = new CalificacionDTO(calificacionLogic.updateCalificacion(calificacionId, calificacion.toEntity()));
        LOGGER.log(Level.INFO, "CategoriaResource updateCategoria: output: {0}", detailDTO);
        return detailDTO;
    }

    @DELETE
    @Path("{calificacionId: \\d+}")
    public void deleteCalificacion(@PathParam("calificacionId") Long calificacionId) {
        LOGGER.log(Level.INFO, "CalificacionResource deleteCalificacion: input: {0}", calificacionId);
        if (calificacionLogic.getCalificacion(calificacionId) == null) {
            throw new WebApplicationException("Recurso calificaiones/" + calificacionId + " no existe ", 404);
        }
        calificacionLogic.deleteCalificacion(calificacionId);
        LOGGER.info("CategoriaResource deleteCategoria: output: void");
    }

    private List<CalificacionDTO> listEntity2DetailDTO(List<CalificacionEntity> entityList) {
        List<CalificacionDTO> list = new ArrayList<>();
        for (CalificacionEntity entity : entityList) {
            list.add(new CalificacionDTO(entity));
        }
        return list;
    }
}
