/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.resources;

import co.edu.uniandes.csw.dispositivos.dtos.DispositivoDTO;
import co.edu.uniandes.csw.dispositivos.dtos.FacturaDTO;
import co.edu.uniandes.csw.dispositivos.dtos.FacturaDetailDTO;
import co.edu.uniandes.csw.dispositivos.ejb.DispositivoLogic;
import co.edu.uniandes.csw.dispositivos.ejb.FacturaLogic;
import co.edu.uniandes.csw.dispositivos.entities.DispositivoEntity;
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
    @Inject
    DispositivoLogic dispositivoLogic;

    /**
     *
     * @param facturaId id de la factura
     * @param factura factura
     * @param dispositivos dispositivos de la factura
     * @return
     * @throws BusinessLogicException
     */
    @POST
    public FacturaDTO createFactura(@PathParam("facturaId") Long facturaId, FacturaDTO factura, List<DispositivoDTO> dispositivos) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "FacturaResource createFactura: input: {0}", factura);
        FacturaEntity facturaEntity = factura.toEntity();
        List<DispositivoEntity> disps = new ArrayList<DispositivoEntity>();
        for (DispositivoDTO dispositivo : dispositivos) {
            if (dispositivoLogic.getDispositivo(dispositivo.getId()) == null) {
                DispositivoEntity dispositivoEntity = dispositivoLogic.createDispositivo(dispositivo.toEntity());
                disps.add(dispositivoEntity);
            } else {
                DispositivoEntity d = dispositivo.toEntity();
                disps.add(d);
            }
        }
        facturaEntity.setDispositivos(disps);
        FacturaEntity nuevoMedioEntity = facturaLogic.createFactura(facturaEntity);
        FacturaDTO nuevoMedioDTO = new FacturaDTO(nuevoMedioEntity);
        LOGGER.log(Level.INFO, "FacturaResource createFactura: output: {0}", nuevoMedioDTO);
        return nuevoMedioDTO;
    }

    /**
     *
     * @param clienteId
     * @return
     */
    @GET
    public List<FacturaDetailDTO> getFacturas(@PathParam("clienteId") Long clienteId) {
        LOGGER.log(Level.INFO, "FacturaDePagoResource getFacturas: input: {0}", clienteId);
        List<FacturaDetailDTO> listaDTOs = listEntity2DetailDTO(facturaLogic.getFacturas(clienteId));
        LOGGER.log(Level.INFO, "FacturaDePagoResource getFacturas: output: {0}", listaDTOs);
        return listaDTOs;
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

    /**
     *
     * @param clienteId
     * @param facturaId
     * @param factura
     * @return
     * @throws BusinessLogicException
     */
    @PUT
    @Path("{facturaId: \\d+}")
    public FacturaDTO updateFactura(@PathParam("clienteId") Long clienteId, @PathParam("facturaId") Long facturaId, FacturaDTO factura) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "FacturaResource updateFactura: input: clienteId: {0} , facturaId: {1} , factura:{2}", new Object[]{clienteId, facturaId, factura});
        if (!facturaId.equals(factura.getId())) {
            throw new BusinessLogicException("Los ids del factura no coinciden.");
        }
        FacturaEntity entity = facturaLogic.getFactura(clienteId, facturaId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /clientes/" + clienteId + "/factura/" + facturaId + " no existe.", 404);
        }
        FacturaDTO facturaDTO = new FacturaDTO(facturaLogic.updateFactura(clienteId, factura.toEntity()));
        LOGGER.log(Level.INFO, "FacturaResource updateFactura: output:{0}", facturaDTO);
        return facturaDTO;
    }

    /**
     *
     * @param clienteId
     * @param facturaId
     * @throws BusinessLogicException
     */
    @DELETE
    @Path("{facturaId: \\d+}")
    public void deleteFactura(@PathParam("clienteId") Long clienteId, @PathParam("facturaId") Long facturaId) throws BusinessLogicException {
        FacturaEntity entity = facturaLogic.getFactura(clienteId, facturaId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /clientes/" + clienteId + "/factura/" + facturaId + " no existe.", 404);
        }

        facturaLogic.deleteFactura(facturaId, clienteId);
    }
}
