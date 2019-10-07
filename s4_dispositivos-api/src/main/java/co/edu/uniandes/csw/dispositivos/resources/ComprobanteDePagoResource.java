/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.resources;

import co.edu.uniandes.csw.dispositivos.dtos.ComprobanteDePagoDTO;
import co.edu.uniandes.csw.dispositivos.ejb.ComprobanteDePagoLogic;
import co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;

/**
 * Clase que implementa el recurso "comprobanteDePago"
 * @author Dianis Caro
 */
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ComprobanteDePagoResource 
{    
    @Inject
    private ComprobanteDePagoLogic comprobanteLogic;

    private static final Logger LOGGER = Logger.getLogger(ComprobanteDePagoResource.class.getName());
    
     @POST
    public ComprobanteDePagoDTO createReview(@PathParam("clienteId") Long clienteId, ComprobanteDePagoDTO comprobante) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "ReviewResource createReview: input: {0}", comprobante);
        //ComprobanteDePagoDTO nuevoReviewDTO = new ComprobanteDePagoDTO(comprobanteLogic.createComprobante(clienteId, comprobante.toEntity()));
        LOGGER.log(Level.INFO, "ReviewResource createReview: output: {0}", nuevoReviewDTO);
       // return nuevoReviewDTO;    
    return null;
    }

    @GET
    public List<ComprobanteDePagoDTO> getReviews(@PathParam("clienteId") Long clienteId) {
        return null;
    }

    @GET
    @Path("{comprobanteId: \\d+}")
    public ComprobanteDePagoDTO getReview(@PathParam("clienteId") Long clienteId, @PathParam("comprobanteId") Long comprobanteId) {
        return null;
    }

    @PUT
    @Path("{comprobanteId: \\d+}")
    public ComprobanteDePagoDTO updateReview(@PathParam("clienteId") Long clienteId, @PathParam("comprobanteId") Long reviewsId, ComprobanteDePagoDTO comprobante) {
        return null;
    }

    @DELETE
    @Path("{comprobanteId: \\d+}")
    public void deleteReview(@PathParam("clienteId") Long clienteId, @PathParam("comprobanteId") Long comprobanteId){
        
    }
}
