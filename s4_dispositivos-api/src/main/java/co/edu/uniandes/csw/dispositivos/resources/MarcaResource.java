/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.resources;

import co.edu.uniandes.csw.dispositivos.dtos.MarcaDTO;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;

/**
 *
 * @author Carlos Salazar
 */
@Path("marcas")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class MarcaResource {

    @POST
    public MarcaDTO createMarca(@PathParam("marcaId") Long marcaId, MarcaDTO marca) {
        return null;
    }

    @GET
    public List<MarcaDTO> getMarcas(@PathParam("marcaId") Long marcaId) {
        return null;
    }

    @GET
    @Path("{marcaId: \\d+}")
    public MarcaDTO getMarca(@PathParam("marcaId") Long marcaId) {
        return null;
    }

    @PUT
    @Path("{marcaId: \\d+}")
    public MarcaDTO updateMarca(@PathParam("marcaId") Long marcaId, MarcaDTO marca) {
        return null;
    }

    @DELETE
    @Path("{marcaId: \\d+}")
    public void deleteMarca(@PathParam("marcaId") Long marcaId) {
    }
}
