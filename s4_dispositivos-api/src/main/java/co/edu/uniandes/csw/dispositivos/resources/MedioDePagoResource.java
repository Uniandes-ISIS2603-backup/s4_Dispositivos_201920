/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.resources;

import co.edu.uniandes.csw.dispositivos.dtos.MedioDePagoDTO;
import co.edu.uniandes.csw.dispositivos.ejb.MedioDePagoLogic;
import co.edu.uniandes.csw.dispositivos.entities.MedioDePagoEntity;
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
 * @author Juan L
 */
@Path("medios")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class MedioDePagoResource {

    private static final Logger LOGGER = Logger.getLogger(MedioDePagoResource.class.getName());

    @Inject
    private MedioDePagoLogic medioLogic;

    /**
     * Crea un nuevo medio de pago con la informacion que se recibe en el cuerpo
     * de la petición y se regresa un objeto identico con un id auto-generado
     * por la base de datos.
     *
     * @param medio {@link MedioDepagoDTO} - El medio de pago que se desea
     * guardar.
     * @return JSON {@link MedioDePagoDTO} - El medio de pago guardada con el
     * atributo id autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe el medio de pago.
     */
    @POST
    public MedioDePagoDTO createMedioDePago(MedioDePagoDTO medio) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "MedioDePagoResource createMedioDePago: input: {0}", medio);
        MedioDePagoEntity medioEntity = medio.toEntity();
        MedioDePagoEntity nuevoMedioEntity = medioLogic.createMedioDePago(medioEntity);
        MedioDePagoDTO nuevoMedioDTO = new MedioDePagoDTO(nuevoMedioEntity);
        LOGGER.log(Level.INFO, "MedioDePagoResource createMedioDePago: output: {0}", nuevoMedioDTO);
        return nuevoMedioDTO;
    }

    /**
     * Busca y devuelve todas los medio de pago que existen en la aplicacion.
     *
     * @return JSONArray {@link MedioDePagoDetailDTO} - Las editoriales
     * encontradas en la aplicación. Si no hay ninguna retorna una lista vacía.
     */
    /**
     * Busca el medio de pago con el id asociado recibido en la URL y la
     * devuelve.
     *
     * @param medioId Identificador del medio de pago que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @return JSON {@link MedioDePagoDTO} - El medio de pago buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el medio de pago.
     */
    @GET
    @Path("{mediodsId: \\d+}")
    public MedioDePagoDTO getMedioDePagos(@PathParam("mediosId") Long medioId) throws WebApplicationException {
        LOGGER.log(Level.INFO, "MedioDePagoResource getMedioDePago: input: {0}", medioId);
        MedioDePagoEntity mediopEntity = medioLogic.getMedioDePago(medioId);
        if (mediopEntity == null) {
            throw new WebApplicationException("El recurso /medios/" + medioId + " no existe.", 404);
        }
        MedioDePagoDTO detailDTO = new MedioDePagoDTO(mediopEntity);
        LOGGER.log(Level.INFO, "MedioDePagoResource getMedioDePago: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Actualiza el medio de pago con el id recibido en la URL con la
     * informacion que se recibe en el cuerpo de la petición.
     *
     * @param medioId Identificador del medio de pago que se desea actualizar.
     * Este debe ser una cadena de dígitos.
     * @param medio {@link MedioDePagoDTO} El medio de pago que se desea
     * guardar.
     * @return JSON {@link MedioDePagoDetailDTO} - El medio de pago guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el medio de pago a
     * actualizar.
     */
    @PUT
    @Path("{mediosId: \\d+}")
    public MedioDePagoDTO updateMedioDePago(@PathParam("mediosId") Long medioId, MedioDePagoDTO medio) throws WebApplicationException, BusinessLogicException {
        LOGGER.log(Level.INFO, "MedioDePagoResource updateMedioDePago: input: id:{0} , medio de pago: {1}", new Object[]{medioId, medio});
        medio.setId(medioId);
        if (medioLogic.getMedioDePago(medioId) == null) {
            throw new WebApplicationException("El recurso /medios/" + medioId + " no existe.", 404);
        }
        MedioDePagoDTO detailDTO = new MedioDePagoDTO(medioLogic.updateMedioDePago(medioId, medio.toEntity()));
        LOGGER.log(Level.INFO, "MedioDePagoResource updateMedioDePago: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Borra el medio de pago con el id asociado recibido en la URL.
     *
     * @param medioId Identificador del medio de pago que se desea borrar. Este
     * debe ser una cadena de dígitos.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar el medio de
     * pago.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el medio de pago.
     */
    @DELETE
    @Path("{mediosId: \\d+}")
    public void deleteMedioDePago(@PathParam("mediosId") Long medioId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "MedioDePagoResource deleteMedioDePago: input: {0}", medioId);
        if (medioLogic.getMedioDePago(medioId) == null) {
            throw new WebApplicationException("El recurso /medios/" + medioId + " no existe.", 404);
        }
        medioLogic.deleteMedioDePago(medioId);
        LOGGER.info("MedioDePagoResource deleteMedioDePago: output: void");
    }

    @GET
    public List<MedioDePagoDTO> getMediosDePago() {
        LOGGER.info("MedioDePagoResource getMediosDePago: input: void");
        List<MedioDePagoDTO> listaMedioPago = listEntity2DetailDTO(medioLogic.getMediosDePago());
        LOGGER.log(Level.INFO, "MedioDePagoResource getMedioDePago: output: {0}", listaMedioPago);
        return listaMedioPago;
    }

    private List<MedioDePagoDTO> listEntity2DetailDTO(List<MedioDePagoEntity> entityList) {
        List<MedioDePagoDTO> list = new ArrayList<MedioDePagoDTO>();
        for (MedioDePagoEntity entity : entityList) {
            list.add(new MedioDePagoDTO(entity));
        }
        return list;
    }
}
