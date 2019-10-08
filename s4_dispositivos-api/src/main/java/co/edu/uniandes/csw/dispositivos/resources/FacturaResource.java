/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.resources;

import co.edu.uniandes.csw.dispositivos.dtos.FacturaDTO;
import co.edu.uniandes.csw.dispositivos.dtos.FacturaDetailDTO;
import co.edu.uniandes.csw.dispositivos.ejb.FacturaLogic;
import co.edu.uniandes.csw.dispositivos.entities.FacturaEntity;
import co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException;
import java.util.ArrayList;
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
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author Carlos Salazar
 */
@Path("facturas")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class FacturaResource {

    private static final Logger LOGGER = Logger.getLogger(FacturaResource.class.getName());

    @Inject
    private FacturaLogic facturaLogic;

    @POST
    public FacturaDTO createFactura(@PathParam("facturaId") Long facturaId, FacturaDTO factura) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "FacturaResource createFactura: input: {0}", factura);
        FacturaEntity facturaEntity = factura.toEntity();
        FacturaEntity nuevoMedioEntity = facturaLogic.createFactura(facturaEntity);
        FacturaDTO nuevoMedioDTO = new FacturaDTO(nuevoMedioEntity);
        LOGGER.log(Level.INFO, "FacturaResource createFactura: output: {0}", nuevoMedioDTO);
        return nuevoMedioDTO;
    }

    @GET
    public List<FacturaDetailDTO> getFacturas(@PathParam("facturaId") Long facturaId) {
        LOGGER.info("FacturaResource getFacturas: input: void");
        List<FacturaDetailDTO> listaFacturas = listEntity2DetailDTO(facturaLogic.getFacturas());
        LOGGER.log(Level.INFO, "FacturaResource getFacturas: output: {0}", listaFacturas);
        return listaFacturas;
    }

    private List<FacturaDetailDTO> listEntity2DetailDTO(List<FacturaEntity> entityList) {
        List<FacturaDetailDTO> list = new ArrayList<FacturaDetailDTO>();
        for (FacturaEntity entity : entityList) {
            list.add(new FacturaDetailDTO(entity));
        }
        return list;
    }

    @GET
    @Path("{facturaId: \\d+}")
    public FacturaDTO getFactura(@PathParam("facturaId") Long facturaId) {
        return null;
    }

    @PUT
    @Path("{facturaId: \\d+}")
    public FacturaDTO updateFactura(@PathParam("facturaId") Long facturaId, FacturaDTO factura) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "FacturaResource updateFactura: input: id:{0} , factura de pago: {1}", new Object[]{facturaId, factura});
        factura.setId(facturaId);
        if (facturaLogic.getFactura(facturaId) == null) {
            throw new WebApplicationException("El recurso /facturas/" + facturaId + " no existe.", 404);
        }
        FacturaDTO detailDTO = new FacturaDTO(facturaLogic.updateFactura(facturaId, factura.toEntity()));
        LOGGER.log(Level.INFO, "FacturaResource updateFactura: output: {0}", detailDTO);
        return detailDTO;
    }

    @DELETE
    @Path("{facturaId: \\d+}")
    public void deleteFactura(@PathParam("facturaId") Long facturaId) {
        LOGGER.log(Level.INFO, "FacturaResource deleteFactura: input: {0}", facturaId);
        if (facturaLogic.getFactura(facturaId) == null) {
            throw new WebApplicationException("El recurso /facturas/" + facturaId + " no existe.", 404);
        }
        facturaLogic.deleteFactura(facturaId);
        LOGGER.info("FacturaResource deleteFactura: output: void");
    }
}
