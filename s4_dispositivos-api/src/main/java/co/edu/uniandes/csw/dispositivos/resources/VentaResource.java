/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.resources;

import co.edu.uniandes.csw.dispositivos.dtos.VentaDTO;
import co.edu.uniandes.csw.dispositivos.dtos.VentaDetailDTO;
import co.edu.uniandes.csw.dispositivos.ejb.VentaLogic;
import co.edu.uniandes.csw.dispositivos.entities.VentaEntity;
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
 *
 * @author Zharet Bautista Montes
 */
@Path("ventas")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class VentaResource 
{
    private static final Logger LOGGER = Logger.getLogger(VentaResource.class.getName());
    
    @Inject
    private VentaLogic valogic;  

    /**
     * Crea la venta mediante el DTO recibido por el URL.
     * @param venta
     * @return Venta creada
     * @throws BusinessLogicException
     */
    @POST
    public VentaDTO createVenta(VentaDTO venta) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "VentaResource createVenta: input: {0}", venta);
        VentaDTO newventa = new VentaDTO(valogic.createVenta(venta.toEntity()));
        LOGGER.log(Level.INFO, "VentaResource createVenta: output: {0}", newventa);
        return newventa;
    }

    /**
     * Obtiene la lista de todas las ventas existentes.
     * @return Lista de todas las ventas
     * @throws BusinessLogicException
     */
    @GET
    public List<VentaDetailDTO> getAllVentas() throws BusinessLogicException
    {
        LOGGER.info("VentaResource getAllVentas: input: void");
        List<VentaEntity> vaconteo = valogic.findAllVentas(); 
        List<VentaDetailDTO> valisted = new ArrayList<>();
        for(VentaEntity vendedor : vaconteo)
            valisted.add(new VentaDetailDTO(vendedor));
        LOGGER.log(Level.INFO, "VentaResource getAllVentas: output: {0}", valisted);
        return valisted;
    }

    /**
     * Obtiene la venta mediante el id recibido por el URL.
     * @param idVenta
     * @return Venta obtenida
     * @throws BusinessLogicException
     * @throws WebApplicationException
     */
    @GET
    @Path("{ventaID: \\d+}")
    public VentaDetailDTO getVenta(@PathParam("ventaID") Long idVenta) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "VentaResource getVenta: input: {0}", idVenta);
        VentaEntity wantedva = valogic.findVenta(idVenta);
        if(wantedva == null)
            throw new WebApplicationException("No se encuentra el recurso /venta/" + idVenta, 404);
        VentaDetailDTO vadetail = new VentaDetailDTO(wantedva);
        LOGGER.log(Level.INFO, "VentaResource getVenta: output: {0}", vadetail);
        return vadetail;        
    }

    /**
     * Actualiza la venta mediante el id y la nueva definición de la venta recibidos por el URL.
     * @param idVenta
     * @param vadto
     * @return Venta actualizada
     * @throws BusinessLogicException
     * @throws WebApplicationException
     */
    @PUT
    @Path("{ventaID: \\d+}")
    public VentaDTO updateVenta(@PathParam("ventaID") Long idVenta, VentaDTO vadto) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "VentaResource updateVenta: input: {0}, venta: {1}", new Object[]{idVenta, vadto});
        vadto.setId(idVenta);
        if(valogic.findVenta(idVenta) == null)
            throw new WebApplicationException("No se encuentra el recurso /venta/" + idVenta, 404);
        VentaDTO detailVenta = new VentaDTO(valogic.updateVenta(vadto.toEntity()));
        LOGGER.log(Level.INFO, "VentaResource updateVenta: output: {0}", detailVenta);
        return detailVenta;
    }

    /**
     * Borra la venta mediante el id recibido por el URL.
     * @param idVenta
     * @throws BusinessLogicException
     * @throws WebApplicationException
     */
    @DELETE
    @Path("{ventaID: \\d+}")
    public void deleteVenta(@PathParam("ventaID") Long idVenta) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "VentaResource deleteVenta: input: {0}", idVenta);
        VentaEntity notventa = valogic.findVenta(idVenta); 
        if(notventa == null)
            throw new WebApplicationException("No se encuentra el recurso /venta/" + idVenta, 404); 

        valogic.deleteVenta(idVenta);
        LOGGER.info("VentaResource deleteVenta: output: void");
    }
}
