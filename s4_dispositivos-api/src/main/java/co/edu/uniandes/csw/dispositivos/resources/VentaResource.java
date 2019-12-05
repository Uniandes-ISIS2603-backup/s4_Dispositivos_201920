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
@Produces("application/json")
@Consumes("application/json")
public class VentaResource 
{
    private static final Logger LOGGER = Logger.getLogger(VentaResource.class.getName());
    
    private static final String NOTVAMSG = "No se encuentra el recurso /ventas/";
    
    @Inject
    private VentaLogic valogic;   

    /**
     * Crea la venta mediante el DTO recibido por JSON.
     * @param vendedorId
     * @param venta
     * @return Venta creada
     * @throws BusinessLogicException
     */
    @POST
    public VentaDTO createVenta(@PathParam("vendedorId") Long vendedorId, VentaDTO venta) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "VentaResource createVenta: input: {0}", venta);
        VentaEntity varef = venta.toEntity();
        VentaEntity newvaentity = valogic.createVenta(vendedorId, varef); 
        VentaDTO newvadto = new VentaDTO(newvaentity);
        LOGGER.log(Level.INFO, "VentaResource createVenta: output: {0}", newvadto);
        return newvadto;
    }

    /**
     * Obtiene la lista de todas las ventas existentes.
     * @param vendedorId
     * @return Lista de todas las ventas
     * @throws BusinessLogicException
     */
    @GET
    public List<VentaDetailDTO> getAllVentas(@PathParam("vendedorId") Long vendedorId) throws BusinessLogicException
    {
        LOGGER.info("VentaResource getAllVentas: input: void");
        List<VentaEntity> vaconteo = valogic.findAllVentas(vendedorId); 
        List<VentaDetailDTO> valisted = new ArrayList<>();
        for(VentaEntity venta : vaconteo)
            valisted.add(new VentaDetailDTO(venta));
        LOGGER.log(Level.INFO, "VentaResource getAllVentas: output: {0}", valisted);
        return valisted;
    }

    /**
     * Obtiene la venta mediante el id recibido por el URL.
     * @param vendedorId
     * @param idVenta
     * @return Venta obtenida
     * @throws BusinessLogicException
     * @throws WebApplicationException
     */
    @GET
    @Path("{ventaID: \\d+}")
    public VentaDetailDTO getVenta(@PathParam("vendedorId") Long vendedorId, @PathParam("ventaID") Long idVenta) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "VentaResource getVenta: input: {0}", idVenta);
        VentaEntity wantedva = valogic.findVenta(vendedorId, idVenta);
        if(wantedva == null)
            throw new WebApplicationException(NOTVAMSG + idVenta, 404);
        VentaDetailDTO vadetail = new VentaDetailDTO(wantedva);
        LOGGER.log(Level.INFO, "VentaResource getVenta: output: {0}", vadetail);
        return vadetail;        
    }

    /**
     * Actualiza la venta mediante el id recibido por el URL y la nueva definición del venta recibida por JSON.
     * @param vendedorId
     * @param idVenta
     * @param vadto
     * @return Venta actualizada
     * @throws BusinessLogicException
     * @throws WebApplicationException
     */
    @PUT
    @Path("{ventaID: \\d+}")
    public VentaDTO updateVenta(@PathParam("vendedorId") Long vendedorId, @PathParam("ventaID") Long idVenta, VentaDetailDTO vadto) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "VentaResource updateVenta: input: {0}, venta: {1}", new Object[]{idVenta, vadto});
        vadto.setId(idVenta);
        if(valogic.findVenta(vendedorId, idVenta) == null)
            throw new WebApplicationException(NOTVAMSG + idVenta, 404);
        VentaDetailDTO detailVenta = new VentaDetailDTO(valogic.updateVenta(vendedorId, vadto.toEntity()));
        LOGGER.log(Level.INFO, "VentaResource updateVenta: output: {0}", detailVenta);
        return detailVenta;
    }

    /**
     * Borra la venta mediante el id recibido por el URL.
     * @param vendedorId
     * @param idVenta
     * @throws BusinessLogicException
     * @throws WebApplicationException
     */
    @DELETE
    @Path("{ventaID: \\d+}")
    public void deleteVenta(@PathParam("vendedorId") Long vendedorId, @PathParam("ventaID") Long idVenta) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "VentaResource deleteVenta: input: {0}", idVenta);
        VentaEntity notventa = valogic.findVenta(vendedorId, idVenta); 
        if(notventa == null)
            throw new WebApplicationException(NOTVAMSG + idVenta, 404); 
        valogic.deleteVenta(vendedorId, idVenta);
        LOGGER.info("VentaResource deleteVenta: output: void");
    }
}
