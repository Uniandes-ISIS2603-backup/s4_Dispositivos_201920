/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.resources;

import co.edu.uniandes.csw.dispositivos.dtos.DispositivoDTO;
import co.edu.uniandes.csw.dispositivos.ejb.DispositivoLogic;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author Santiago Fajardo
 */
@Path("dispositivos")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class DispositivoResource {

    private static final Logger LOGGER = Logger.getLogger(DispositivoResource.class.getName());

    /**
     *
     * @param dispositivo
     * @return
     */
    @POST
    public DispositivoDTO createDispositivo(DispositivoDTO dispositivo) {
        return dispositivo;
    }

    /**
     *
     * @param id
     * @return
     */
    @GET
    public DispositivoDTO getDispositivo(Long id) {
        return null;
    }

    /**
     *
     * @return
     */
    @PUT
    public DispositivoDTO updateDispositivo() {
        return null;
    }

    /**
     *
     * @return
     */
    @DELETE
    public DispositivoDTO deleteDispotivo() {
        return null;
    }

}
