/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.resources;

import co.edu.uniandes.csw.dispositivos.dtos.ComprobanteDePagoDTO;
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
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.WebApplicationException;

/**
 * Clase que implementa el recurso "comprobanteDePago"
 * @author Dianis Caro
 */
@Produces("application/json")
@Consumes("application/json")
public class ComprobanteDePagoResource 
{    
    private static final Logger LOGGER = Logger.getLogger(ComprobanteDePagoResource.class.getName());
    /**
     * Inyección de la lógica de comprobante de pago
     */
    @Inject
    private ComprobanteDePagoLogic comprobanteLogic;
    /**
     * Crea una nueva reseña con la informacion que se recibe en el cuerpo de la
     * petición y se regresa un objeto identico con un id auto-generado por la
     * base de datos.
     * @param clienteId El ID del cliente del cual se le agrega el comprobante de pago
     * @param comprobante El comprobante de pago que se desea guardar
     * @return JSON El comprobante de pago guardada con el atributo id autogenerado
     * @throws BusinessLogicException Error de lógica que se genera cuando ya existe el comprobante de pago
     */
    @POST
    public ComprobanteDePagoDTO createComprobanteDePago(@PathParam("clienteId") Long clienteId, ComprobanteDePagoDTO comprobante) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "ComprobanteDePagoResource createComprobanteDePago: input: {0}", comprobante);
        ComprobanteDePagoDTO nuevoComprobanteDePagoDTO = new ComprobanteDePagoDTO(comprobanteLogic.createComprobante(comprobante.toEntity(), clienteId));
        LOGGER.log(Level.INFO, "ComprobanteDePagoResource createComprobanteDePago: output: {0}", nuevoComprobanteDePagoDTO);
        return nuevoComprobanteDePagoDTO;
    }
    /**
     * Busca y devuelve todos los comprobantes de pago que existen en un cliente
     * @param clienteId El ID del cliente del cual se buscan las reseñas
     * @return JSONArray Los comprobantes de pago encontrados del cliente. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<ComprobanteDePagoDTO> getComprobantes(@PathParam("clienteId") Long clienteId)
    {
        LOGGER.log(Level.INFO, "ComprobanteDePagoResource getComprobantes: input: {0}", clienteId);
        List<ComprobanteDePagoDTO> listaDTOs = listEntity2DTO(comprobanteLogic.getComprobantes(clienteId));
        LOGGER.log(Level.INFO, "ComprobanteDePagoResource getComprobantes: output: {0}", listaDTOs);
        return listaDTOs;
    }
    /**
     * Busca y devuelve el comprobante de pago con el ID recibido en la URL, relativa a un cliente
     * @param clienteId El ID del cliente del cual se buscan los comprobantes de pago
     * @param comprobanteId El ID del comprobante de pago que se busca
     * @return El comprobante de pago encontrado en el cliente
     * @throws BusinessLogicException Error de lógica que se genera cuando no se encuentra el comprobante
     * @throws WebApplicationException Error de lógica que se genera cuando no se encuentra el comprobante
     */
    @GET
    @Path("{comprobanteId: \\d+}")
    public ComprobanteDePagoDTO getComprobanteDePago(@PathParam("clienteId") Long clienteId, @PathParam("comprobanteId") Long comprobanteId) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "ComprobanteDePagoResource getComprobanteDePago: input: {0}", comprobanteId);
        ComprobanteDePagoEntity entity = comprobanteLogic.getComprobante(comprobanteId, clienteId);
        if (entity == null) 
        {
            throw new WebApplicationException("El recurso /clientes/" + clienteId + "/comprobante/" + comprobanteId + " no existe.", 404);
        }
        ComprobanteDePagoDTO comprobanteDTO = new ComprobanteDePagoDTO(entity);
        LOGGER.log(Level.INFO, "ComprobanteDePagoResource getComprobanteDePago: output: {0}", comprobanteDTO);
        return comprobanteDTO;
    }
    /**
     * Actualiza un comprobante con la informacion que se recibe
     * @param clienteId El ID del cliente del cual se guarda el comprobante de pago
     * @param comprobanteId El ID del comprobante de pago que se va a actualizar
     * @param comprobante El comprobante de pago que se desea guardar
     * @return JSON El comprobante de pago actualizado
     * @throws BusinessLogicException Error de lógica que se genera cuando ya existe el comprobante
     * @throws WebApplicationException Error de lógica que se genera cuando no se encuentra el comprobante de pago
     */
    @PUT
    @Path("{comprobanteId: \\d+}")
    public ComprobanteDePagoDTO updateComprobanteDePago(@PathParam("clienteId") Long clienteId, @PathParam("comprobanteId") Long comprobanteId, ComprobanteDePagoDTO comprobante) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "ComprobanteDePagoResource updateComprobante: input: clienteId: {0} , comprobanteId: {1} , comprobante:{2}", new Object[]{clienteId, comprobanteId, comprobante});
        
        if (!comprobanteId.equals(comprobante.getId()))
            throw new BusinessLogicException("Los ids del comprobante no coinciden.");
        
        ComprobanteDePagoEntity entity = comprobanteLogic.getComprobante(comprobanteId, clienteId);
        if (entity == null)
            throw new WebApplicationException("El recurso /clientes/" + clienteId + "/comprobante/" + comprobanteId + " no existe.", 404);
        
        ComprobanteDePagoDTO comprobanteDTO = new ComprobanteDePagoDTO(comprobanteLogic.updateComprobanteDePago(comprobante.toEntity(), clienteId));
        LOGGER.log(Level.INFO, "ComprobanteDePagoResource updateComprobante: output:{0}", comprobanteDTO);
        return comprobanteDTO;
    }
    /**
     * Borra el comprobante de pago con el id asociado recibido en la URL
     * @param clienteId El ID del cliente del cual se va a eliminar el comprobante
     * @param comprobanteId El ID del comprobante que se va a eliminar
     * @throws BusinessLogicException Error de lógica que se genera cuando no se puede eliminar el comprobante de pago.
     * @throws WebApplicationException Error de lógica que se genera cuando no se encuentra el comprobante de pago.
     */
    @DELETE
    @Path("{comprobanteId: \\d+}")
    public void deleteComprobanteDePago(@PathParam("clienteId") Long clienteId, @PathParam("comprobanteId") Long comprobanteId) throws BusinessLogicException {
        ComprobanteDePagoEntity entity = comprobanteLogic.getComprobante(comprobanteId, clienteId);
        if (entity == null)
            throw new WebApplicationException("El recurso /clientes/" + clienteId + "/comprobante/" + comprobanteId + " no existe.", 404);

        comprobanteLogic.deleteComprobante(comprobanteId, clienteId);
    }
    /**
     * Lista de entidades a DTO.
     * Este método convierte una lista de objetos ComprobanteDePagoEntity a una lista de objetos ComprobanteDePagoDTO (json)
     * @param entityList corresponde a la lista de comprobantes de tipo Entity
     * @return la lista de comprobantes en forma DTO (json)
     */
    private List<ComprobanteDePagoDTO> listEntity2DTO(List<ComprobanteDePagoEntity> entityList) {
        List<ComprobanteDePagoDTO> list = new ArrayList<ComprobanteDePagoDTO>();
        for (ComprobanteDePagoEntity entity : entityList) {
            list.add(new ComprobanteDePagoDTO(entity));
        }
        return list;
    }
}
