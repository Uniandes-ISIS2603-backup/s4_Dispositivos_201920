/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.resources;

import co.edu.uniandes.csw.dispositivos.dtos.VentaDTO;
import co.edu.uniandes.csw.dispositivos.dtos.VentaDetailDTO;
import co.edu.uniandes.csw.dispositivos.ejb.VendedorVentaLogic;
import co.edu.uniandes.csw.dispositivos.ejb.VentaLogic;
import co.edu.uniandes.csw.dispositivos.entities.VentaEntity;
import co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Zharet Bautista Montes
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VendedorVentaResource 
{
    private static final Logger LOGGER = Logger.getLogger(VendedorVentaResource.class.getName());
    
    @Inject
    private VentaLogic valogic; 
    
    @Inject
    private VendedorVentaLogic vralogic; 

    /**
     * Crea la referentva mediante el DTO recibido por el URL.
     * @param Idvendedor
     * @param Idventa
     * @return Venta creada
     * @throws BusinessLogicException
     */
    @POST
    @Path("{ventaID: \\d+}")
    public VentaDTO createVenta(@PathParam("vendedorID") Long Idvendedor, @PathParam("ventaID") Long Idventa) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "VendedorVentaResource createVenta: input: vendedorID: {0}, ventaID: {1}", new Object[]{Idvendedor, Idventa});
        VentaEntity required = valogic.findVenta(Idvendedor, Idventa);
        if(required == null)
            throw new WebApplicationException("No se encuentra el recurso /ventas/" + Idventa, 404);
        VentaDTO newventa = new VentaDTO(vralogic.createVenta(Idvendedor, Idventa));
        LOGGER.log(Level.INFO, "VendedorVentaResource createVenta: output: {0}", newventa);
        return newventa;
    }

    /**
     * Obtiene la lista de todas las ventas existentes.
     * @param Idvendedor
     * @return Lista de todas las ventas
     * @throws BusinessLogicException
     */
    @GET
    public List<VentaDetailDTO> getAllVentas(@PathParam("vendedorID") Long Idvendedor) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "VendedorVentaResource getAllVentas: input: {0}", Idvendedor);
        List<VentaEntity> vaconteo = vralogic.findAllVentas(Idvendedor); 
        List<VentaDetailDTO> valisted = new ArrayList<>();
        for(VentaEntity referentva : vaconteo)
            valisted.add(new VentaDetailDTO(referentva));
        LOGGER.log(Level.INFO, "VendedorVentaResource getAllVentas: output: {0}", valisted);
        return valisted;
    }

    /**
     * Obtiene la referentva mediante el id recibido por el URL.
     * @param Idvendedor
     * @param Idventa
     * @return Venta obtenida
     * @throws BusinessLogicException
     * @throws WebApplicationException
     */
    @GET
    @Path("{ventaID: \\d+}")
    public VentaDetailDTO getVenta(@PathParam("vendedorID") Long Idvendedor, @PathParam("ventaID") Long Idventa) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "VendedorVentaResource getVenta: input: vendedorID: {0}, ventaID: {1}", new Object[]{Idvendedor, Idventa});
        VentaEntity wantedva = valogic.findVenta(Idvendedor, Idventa);
        if(wantedva == null)
            throw new WebApplicationException("No se encuentra el recurso /vendedores/" + Idvendedor + "/ventas/" + Idventa, 404);
        VentaDetailDTO vadetail = new VentaDetailDTO(vralogic.findVenta(Idvendedor, Idventa)); 
        LOGGER.log(Level.INFO, "VendedorVentaResource getVenta: output: {0}", vadetail);
        return vadetail;        
    }
    
    /**
     * Reemplaza las instancias de ComprobanteDePago asociadas a una instancia de
     * Cliente
     *
     * @param Idvendedor 
     * @param ventas 
     * @return JSON {@link ComprobanteDePagoDTO} - El arreglo de libros guardado
     * en el cliente.
     * @throws WebApplicationException 
     * @throws BusinessLogicException
     */
    @PUT
    public List<VentaDetailDTO> replaceVentas(@PathParam("vendedorID") Long Idvendedor, List<VentaDetailDTO> ventas) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "VendedorVentaResource replaceVentas: input: vendedorID: {0}, ventas: {1}", new Object[]{Idvendedor, ventas});
        List<VentaEntity> plantilla = new ArrayList<>();
        for (VentaDetailDTO vadetaildto : ventas) 
        {
            if (valogic.findVenta(Idvendedor, vadetaildto.getId()) == null)
                throw new WebApplicationException("No se encuentra el recurso /ventas/" + Idvendedor, 404);
            else plantilla.add(vadetaildto.toEntity()); 
        }
        List<VentaEntity> referentva = vralogic.replaceVentas(Idvendedor, plantilla); 
        List<VentaDetailDTO> refreshedventas = new ArrayList<>();
        for(VentaEntity venta : referentva)
            refreshedventas.add(new VentaDetailDTO(venta));
        LOGGER.log(Level.INFO, "VendedorVentaResource replaceVentas: output: {0}", refreshedventas);
        return refreshedventas;
    }
}
