/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.resources;

import co.edu.uniandes.csw.dispositivos.dtos.VentaDTO;
import co.edu.uniandes.csw.dispositivos.ejb.VentaLogic;
import co.edu.uniandes.csw.dispositivos.entities.VentaEntity;
import co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
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

@Path("ventas")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class VentaResource 
{
    private static final Logger LOGGER = Logger.getLogger(VentaResource.class.getName());
    
    @Inject
    private VentaLogic valogic;  

    @POST
    public VentaDTO createVenta(VentaDTO venta) throws BusinessLogicException
    {
        /**
        VentaDTO newventa = new VentaDTO(valogic.createVenta(venta.toEntity()));
        return newventa;
        */
        return null;
    }

    @GET
    public List<VentaDTO> getAllVentas() throws BusinessLogicException
    {
        /**
        List<VentaEntity> vaconteo = valogic.findAllVentas(); 
        List<VentaDTO> valisted = new ArrayList<>();
        for(VentaEntity vendedor : vaconteo)
            valisted.add(new VentaDTO(vendedor));
        return valisted;
        */
        return null; 
    }

    @GET
    @Path("{ventaID: \\d+}")
    public VentaDTO getVenta(@PathParam("ventaID") Long idVenta) throws BusinessLogicException
    {
        /**
        VentaEntity wantedva = valogic.findVenta(idVenta);
        if(wantedva == null)
            throw new WebApplicationException("No se encuentra el recurso /venta/" + idVenta, 404);
        VentaDTO vadetail = new VentaDTO(wantedva);
        return vadetail;
        */
        return null; 
    }

    @PUT
    @Path("{ventaID: \\d+}")
    public VentaDTO updateVenta(@PathParam("ventaID") Long idVenta, VentaDTO vadto) throws BusinessLogicException
    {
        /**
        vadto.setId(idVenta);
        if(valogic.findVenta(idVenta) == null)
            throw new WebApplicationException("No se encuentra el recurso /venta/" + idVenta, 404);
        VentaDTO detailVenta = new VentaDTO(valogic.updateVenta(vadto.toEntity()));
        return detailVenta;
        */
        return null;
    }

    @DELETE
    @Path("{ventaID: \\d+}")
    public void deleteVenta(@PathParam("ventaID") Long idVenta) throws BusinessLogicException
    {
        /**
        VentaEntity notventa = valogic.findVenta(idVenta); 
        if(notventa == null)
            throw new WebApplicationException("No se encuentra el recurso /venta/" + idVenta, 404); 

        valogic.deleteVenta(idVenta);
        */
    }
}