/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.resources;

import co.edu.uniandes.csw.dispositivos.dtos.MediaDTO;
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
 *
 * @author Estudiante
 */
@Path("medias")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class MediaResource {
    
    @POST
    public MediaDTO createMedia(@PathParam("mediaId") Long mediaId, MediaDTO media) {
        return null;
    }

    @GET
    public List<MediaDTO> getMedias(@PathParam("mediaId") Long mediaId) {
        return null;
    }

    @GET
    @Path("{mediaId: \\d+}")
    public MediaDTO getMedia(@PathParam("mediaId") Long mediaId) {
        return null;
    }

    @PUT
    @Path("{mediaId: \\d+}")
    public MediaDTO updateMedia(@PathParam("mediaId") Long mediaId, MediaDTO media) {
        return null;
    }

    @DELETE
    @Path("{mediaId: \\d+}")
    public void deleteMedia(@PathParam("mediaId") Long mediaId) {
    }
    
}
