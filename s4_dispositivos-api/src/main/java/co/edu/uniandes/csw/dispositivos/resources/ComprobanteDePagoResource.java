/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.resources;

import co.edu.uniandes.csw.dispositivos.dtos.ComprobanteDePagoDTO;
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
 * Clase que implementa el recurso "comprobanteDePago"
 * @author Dianis Caro
 */
@Path("comprobantes")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ComprobanteDePagoResource 
{    
 @POST
    public ComprobanteDePagoDTO createReview(@PathParam("clienteId") Long clienteId, ComprobanteDePagoDTO comprobante){
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
