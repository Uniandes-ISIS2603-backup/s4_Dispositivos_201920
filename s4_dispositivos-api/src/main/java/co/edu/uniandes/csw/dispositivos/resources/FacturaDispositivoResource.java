/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.resources;

import co.edu.uniandes.csw.dispositivos.dtos.DispositivoDTO;
import co.edu.uniandes.csw.dispositivos.dtos.DispositivoDetailDTO;
import co.edu.uniandes.csw.dispositivos.ejb.FacturaDispositivoLogic;
import co.edu.uniandes.csw.dispositivos.ejb.DispositivoLogic;
import co.edu.uniandes.csw.dispositivos.entities.DispositivoEntity;
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
public class FacturaDispositivoResource {

    private static final Logger LOGGER = Logger.getLogger(FacturaDispositivoResource.class.getName());

    @Inject
    private FacturaDispositivoLogic facturaDispositivoLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private DispositivoLogic dispositivoLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Guarda un libro dentro de una factura con la informacion que recibe el la
     * URL. Se devuelve el libro que se guarda en la factura.
     *
     * @param facturaId Identificador de la factura que se esta actualizando.
     * Este debe ser una cadena de dígitos.
     * @param dispositivoId Identificador del libro que se desea guardar. Este
     * debe ser una cadena de dígitos.
     * @return JSON {@link DispositivoDTO} - El libro guardado en la factura.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro.
     */
    @POST
    @Path("{dispositivoId: \\d+}")
    public DispositivoDTO addDispositivo(@PathParam("facturaId") Long facturaId, @PathParam("dispositivoId") Long dispositivoId, @PathParam("clienteId") Long clienteId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "FacturaDispositivoResource addDispositivo: input: facturasID: {0} , dispositivoId: {1}", new Object[]{facturaId, dispositivoId});
        if (dispositivoLogic.getDispositivo(facturaId, dispositivoId, clienteId) == null) {
            throw new WebApplicationException("El recurso /dispositivos/" + dispositivoId + " no existe.", 404);
        }
        DispositivoDTO dispositivoDTO = new DispositivoDTO(facturaDispositivoLogic.addDispositivo(dispositivoId, facturaId, clienteId));
        LOGGER.log(Level.INFO, "FacturaDispositivoResource addDispositivo: output: {0}", dispositivoDTO);
        return dispositivoDTO;
    }

    /**x
     * Busca y devuelve todos los libros que existen en la factura.
     *
     * @param facturaId Identificador de la factura que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @return JSONArray {@link DispositivoDetailDTO} - Los libros encontrados
     * en la factura. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<DispositivoDetailDTO> getDispositivos(@PathParam("facturaId") Long facturaId, @PathParam("clienteId") Long clienteId) {
        LOGGER.log(Level.INFO, "FacturaDispositivoResource getDispositivo: input: {0}", facturaId);
        List<DispositivoDetailDTO> listaDetailDTOs = dispositivosListEntity2DTO(facturaDispositivoLogic.getDispositivos(facturaId, clienteId));
        LOGGER.log(Level.INFO, "FacturaDispositivoResource getDispositivo: output: {0}", listaDetailDTOs);
        return listaDetailDTOs;
    }

    /**
     * Busca el libro con el id asociado dentro de la factura con id asociado.
     *
     * @param facturaId Identificador de la factura que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @param dispositivoId Identificador del libro que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @return JSON {@link DispositivoDetailDTO} - El libro buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro en la
     * factura.
     */
    @GET
    @Path("{dispositivoId: \\d+}")
    public DispositivoDetailDTO getDispositivo(@PathParam("facturaId") Long facturaId, @PathParam("dispositivoId") Long dispositivoId, @PathParam("clienteId") Long clienteId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "FacturaDispositivoResource getDispositivo: input: facturasID: {0} , dispositivoId: {1}", new Object[]{facturaId, dispositivoId});
        if (dispositivoLogic.getDispositivo(facturaId, dispositivoId, clienteId) == null) {
            throw new WebApplicationException("El recurso /facturas/" + facturaId + "/dispositivos/" + dispositivoId + " no existe.", 404);
        }
        DispositivoDetailDTO dispositivoDetailDTO = new DispositivoDetailDTO(facturaDispositivoLogic.getDispositivo(facturaId, dispositivoId, clienteId));
        LOGGER.log(Level.INFO, "FacturaDispositivoResource getDispositivo: output: {0}", dispositivoDetailDTO);
        return dispositivoDetailDTO;
    }

    /**
     * Convierte una lista de DispositivoEntity a una lista de
     * DispositivoDetailDTO.
     *
     * @param entityList Lista de DispositivoEntity a convertir.
     * @return Lista de DispositivoDTO convertida.
     */
    private List<DispositivoDetailDTO> dispositivosListEntity2DTO(List<DispositivoEntity> entityList) {
        List<DispositivoDetailDTO> list = new ArrayList();
        for (DispositivoEntity entity : entityList) {
            list.add(new DispositivoDetailDTO(entity));
        }
        return list;
    }

    /**
     * Convierte una lista de DispositivoDetailDTO a una lista de
     * DispositivoEntity.
     *
     * @param dtos Lista de DispositivoDetailDTO a convertir.
     * @return Lista de DispositivoEntity convertida.
     */
    private List<DispositivoEntity> dispositivosListDTO2Entity(List<DispositivoDetailDTO> dtos) {
        List<DispositivoEntity> list = new ArrayList<>();
        for (DispositivoDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
}
