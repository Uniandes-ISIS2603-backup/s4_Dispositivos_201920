/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.resources;

import co.edu.uniandes.csw.dispositivos.dtos.ComprobanteDePagoDTO;
import co.edu.uniandes.csw.dispositivos.ejb.ClienteComprobanteLogic;
import co.edu.uniandes.csw.dispositivos.ejb.ComprobanteDePagoLogic;
import co.edu.uniandes.csw.dispositivos.entities.ComprobanteDePagoEntity;
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
public class ClienteComprobanteResource {

    private static final Logger LOGGER = Logger.getLogger(ClienteComprobanteResource.class.getName());
    private static final String RUTA_ERROR_COMPROBANTE = "El recurso /comprobantes/";
    @Inject
    private ClienteComprobanteLogic clienteComprobanteDePagoLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private ComprobanteDePagoLogic comprobanteLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Guarda un libro dentro de una cliente con la informacion que recibe el la
     * URL. Se devuelve el libro que se guarda en el cliente.
     *
     * @param clienteId Identificador de el cliente que se esta actualizando.
     * Este debe ser una cadena de dígitos.
     * @param comprobanteId Identificador del libro que se desea guardar. Este
     * debe ser una cadena de dígitos.
     * @return JSON {@link ComprobanteDePagoDTO} - El libro guardado en la
     * cliente.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro.
     */
    @POST
    @Path("{comprobanteId: \\d+}")
    public ComprobanteDePagoDTO addComprobanteDePago(@PathParam("clienteId") Long clienteId, @PathParam("comprobanteId") Long comprobanteId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ClienteComprobanteDePagoResource addComprobanteDePago: input: clientesID: {0} , comprobanteId: {1}", new Object[]{clienteId, comprobanteId});
        if (comprobanteLogic.getComprobante(comprobanteId, clienteId) == null) {
            throw new WebApplicationException( RUTA_ERROR_COMPROBANTE + comprobanteId + " no existe 1.", 404);
        }
        ComprobanteDePagoDTO comprobanteDTO = new ComprobanteDePagoDTO(clienteComprobanteDePagoLogic.addComprobante(comprobanteId, clienteId));
        LOGGER.log(Level.INFO, "ClienteComprobanteDePagoResource addComprobanteDePago: output: {0}", comprobanteDTO);
        return comprobanteDTO;
    }

    /**
     * Busca y devuelve todos los libros que existen en el cliente.
     *
     * @param clienteId Identificador de el cliente que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @return JSONArray {@link ComprobanteDePagoDTO} - Los libros encontrados
     * en el cliente. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<ComprobanteDePagoDTO> getComprobanteDePagos(@PathParam("clienteId") Long clienteId) {
        LOGGER.log(Level.INFO, "ClienteComprobanteDePagoResource getComprobanteDePago: input: {0}", clienteId);
        List<ComprobanteDePagoDTO> listaDetailDTOs = comprobantesListEntity2DTO(clienteComprobanteDePagoLogic.getComprobantes(clienteId));
        LOGGER.log(Level.INFO, "ClienteComprobanteDePagoResource getComprobanteDePago: output: {0}", listaDetailDTOs);
        return listaDetailDTOs;
    }

    /**
     * Busca el libro con el id asociado dentro de el cliente con id asociado.
     *
     * @param clienteId Identificador de el cliente que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @param comprobanteId Identificador del libro que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @return JSON {@link ComprobanteDePagoDTO} - El libro buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro en la
     * cliente.
     */
    @GET
    @Path("{comprobanteId: \\d+}")
    public ComprobanteDePagoDTO getComprobanteDePago(@PathParam("clienteId") Long clienteId, @PathParam("comprobanteId") Long comprobanteId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ClienteComprobanteDePagoResource getComprobanteDePago: input: clientesID: {0} , comprobanteId: {1}", new Object[]{clienteId, comprobanteId});
        if (comprobanteLogic.getComprobante(comprobanteId, clienteId) == null) {
            throw new WebApplicationException("El recurso /clientes/" + clienteId + "/comprobantes/" + comprobanteId + " no existe 2.", 404);
        }
        ComprobanteDePagoDTO comprobanteDetailDTO = new ComprobanteDePagoDTO(clienteComprobanteDePagoLogic.getComprobante(clienteId, comprobanteId));
        LOGGER.log(Level.INFO, "ClienteComprobanteDePagoResource getComprobanteDePago: output: {0}", comprobanteDetailDTO);
        return comprobanteDetailDTO;
    }

    /**
     * Remplaza las instancias de ComprobanteDePago asociadas a una instancia de
     * Cliente
     *
     * @param clienteId Identificador de el cliente que se esta remplazando.
     * Este debe ser una cadena de dígitos.
     * @param comprobantes JSONArray {@link ComprobanteDePagoDTO} El arreglo de
     * libros nuevo para el cliente.
     * @return JSON {@link ComprobanteDePagoDTO} - El arreglo de libros guardado
     * en el cliente.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro.
     * @throws BusinessLogicException
     */
    @PUT
    public List<ComprobanteDePagoDTO> replaceComprobanteDePago(@PathParam("clienteId") Long clienteId, List<ComprobanteDePagoDTO> comprobantes) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ClienteComprobanteDePagoResource replaceComprobanteDePago: input: clienteId: {0} , comprobantes: {1}", new Object[]{clienteId, comprobantes});
        for (ComprobanteDePagoDTO comprobante : comprobantes) {
            if (comprobanteLogic.getComprobante(comprobante.getId(), clienteId) == null) {
                throw new WebApplicationException(RUTA_ERROR_COMPROBANTE + comprobante.getId() + " no existe 3.", 404);
            }
        }
        List<ComprobanteDePagoDTO> listaDetailDTOs = comprobantesListEntity2DTO(clienteComprobanteDePagoLogic.replaceComprobantes(clienteId, comprobantesListDTO2Entity(comprobantes)));
        LOGGER.log(Level.INFO, "ClienteComprobanteDePagoResource replaceComprobanteDePago: output: {0}", listaDetailDTOs);
        return listaDetailDTOs;
    }

    /**
     * Convierte una lista de ComprobanteDePagoEntity a una lista de
     * ComprobanteDePagoDTO.
     *
     * @param entityList Lista de ComprobanteDePagoEntity a convertir.
     * @return Lista de ComprobanteDePagoDTO convertida.
     */
    private List<ComprobanteDePagoDTO> comprobantesListEntity2DTO(List<ComprobanteDePagoEntity> entityList) {
        List<ComprobanteDePagoDTO> list = new ArrayList();
        for (ComprobanteDePagoEntity entity : entityList) {
            list.add(new ComprobanteDePagoDTO(entity));
        }
        return list;
    }

    /**
     * Convierte una lista de ComprobanteDePagoDTO a una lista de
     * ComprobanteDePagoEntity.
     *
     * @param dtos Lista de ComprobanteDePagoDTO a convertir.
     * @return Lista de ComprobanteDePagoEntity convertida.
     */
    private List<ComprobanteDePagoEntity> comprobantesListDTO2Entity(List<ComprobanteDePagoDTO> dtos) {
        List<ComprobanteDePagoEntity> list = new ArrayList<>();
        for (ComprobanteDePagoDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
}
