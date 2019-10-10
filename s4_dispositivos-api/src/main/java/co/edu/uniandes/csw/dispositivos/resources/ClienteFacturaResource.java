/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.resources;

import co.edu.uniandes.csw.dispositivos.dtos.FacturaDTO;
import co.edu.uniandes.csw.dispositivos.dtos.FacturaDetailDTO;
import co.edu.uniandes.csw.dispositivos.ejb.ClienteFacturaLogic;
import co.edu.uniandes.csw.dispositivos.ejb.FacturaLogic;
import co.edu.uniandes.csw.dispositivos.entities.FacturaEntity;
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
 * @author Estudiante
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClienteFacturaResource {

    private static final Logger LOGGER = Logger.getLogger(ClienteFacturaResource.class.getName());

    @Inject
    private ClienteFacturaLogic clienteFacturaLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private FacturaLogic facturaLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Guarda un libro dentro de una cliente con la informacion que recibe el la
     * URL. Se devuelve el libro que se guarda en la cliente.
     *
     * @param clienteId Identificador de la cliente que se esta actualizando.
     * Este debe ser una cadena de dígitos.
     * @param facturaId Identificador del libro que se desea guardar. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link FacturaDTO} - El libro guardado en la cliente.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro.
     */
    @POST
    @Path("{facturaId: \\d+}")
    public FacturaDTO addFactura(@PathParam("clienteId") Long clienteId, @PathParam("facturaId") Long facturaId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ClienteFacturaResource addFactura: input: clientesID: {0} , facturaId: {1}", new Object[]{clienteId, facturaId});
        if (facturaLogic.getFactura(clienteId, facturaId) == null) {
            throw new WebApplicationException("El recurso /facturas/" + facturaId + " no existe.", 404);
        }
        FacturaDTO facturaDTO = new FacturaDTO(clienteFacturaLogic.addFactura(facturaId, clienteId));
        LOGGER.log(Level.INFO, "ClienteFacturaResource addFactura: output: {0}", facturaDTO);
        return facturaDTO;
    }

    /**
     * Busca y devuelve todos los libros que existen en la cliente.
     *
     * @param clienteId Identificador de la cliente que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @return JSONArray {@link FacturaDetailDTO} - Los libros encontrados en la
     * cliente. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<FacturaDetailDTO> getFacturas(@PathParam("clienteId") Long clienteId) {
        LOGGER.log(Level.INFO, "ClienteFacturaResource getFactura: input: {0}", clienteId);
        List<FacturaDetailDTO> listaDetailDTOs = facturasListEntity2DTO(clienteFacturaLogic.getFacturas(clienteId));
        LOGGER.log(Level.INFO, "ClienteFacturaResource getFactura: output: {0}", listaDetailDTOs);
        return listaDetailDTOs;
    }

    /**
     * Busca el libro con el id asociado dentro de la cliente con id asociado.
     *
     * @param clienteId Identificador de la cliente que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @param facturaId Identificador del libro que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link FacturaDetailDTO} - El libro buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro en la
     * cliente.
     */
    @GET
    @Path("{facturaId: \\d+}")
    public FacturaDetailDTO getFactura(@PathParam("clienteId") Long clienteId, @PathParam("facturaId") Long facturaId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ClienteFacturaResource getFactura: input: clientesID: {0} , facturaId: {1}", new Object[]{clienteId, facturaId});
        if (facturaLogic.getFactura(clienteId, facturaId) == null) {
            throw new WebApplicationException("El recurso /clientes/" + clienteId + "/facturas/" + facturaId + " no existe.", 404);
        }
        FacturaDetailDTO facturaDetailDTO = new FacturaDetailDTO(clienteFacturaLogic.getFactura(clienteId, facturaId));
        LOGGER.log(Level.INFO, "ClienteFacturaResource getFactura: output: {0}", facturaDetailDTO);
        return facturaDetailDTO;
    }

    /**
     * Remplaza las instancias de Factura asociadas a una instancia de Cliente
     *
     * @param clienteId Identificador de la cliente que se esta remplazando.
     * Este debe ser una cadena de dígitos.
     * @param facturas JSONArray {@link FacturaDTO} El arreglo de libros nuevo
     * para la cliente.
     * @return JSON {@link FacturaDTO} - El arreglo de libros guardado en la
     * cliente.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro.
     * @throws BusinessLogicException
     */
    @PUT
    public List<FacturaDetailDTO> replaceFactura(@PathParam("clienteId") Long clienteId, List<FacturaDetailDTO> facturas) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ClienteFacturaResource replaceFactura: input: clienteId: {0} , facturas: {1}", new Object[]{clienteId, facturas});
        for (FacturaDetailDTO factura : facturas) {
            if (facturaLogic.getFactura(clienteId, factura.getId()) == null) {
                throw new WebApplicationException("El recurso /facturas/" + factura.getId() + " no existe.", 404);
            }
        }
        List<FacturaDetailDTO> listaDetailDTOs = facturasListEntity2DTO(clienteFacturaLogic.replaceFacturas(clienteId, facturasListDTO2Entity(facturas)));
        LOGGER.log(Level.INFO, "ClienteFacturaResource replaceFactura: output: {0}", listaDetailDTOs);
        return listaDetailDTOs;
    }

    /**
     * Convierte una lista de FacturaEntity a una lista de FacturaDetailDTO.
     *
     * @param entityList Lista de FacturaEntity a convertir.
     * @return Lista de FacturaDTO convertida.
     */
    private List<FacturaDetailDTO> facturasListEntity2DTO(List<FacturaEntity> entityList) {
        List<FacturaDetailDTO> list = new ArrayList();
        for (FacturaEntity entity : entityList) {
            list.add(new FacturaDetailDTO(entity));
        }
        return list;
    }

    /**
     * Convierte una lista de FacturaDetailDTO a una lista de FacturaEntity.
     *
     * @param dtos Lista de FacturaDetailDTO a convertir.
     * @return Lista de FacturaEntity convertida.
     */
    private List<FacturaEntity> facturasListDTO2Entity(List<FacturaDetailDTO> dtos) {
        List<FacturaEntity> list = new ArrayList<>();
        for (FacturaDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
}
