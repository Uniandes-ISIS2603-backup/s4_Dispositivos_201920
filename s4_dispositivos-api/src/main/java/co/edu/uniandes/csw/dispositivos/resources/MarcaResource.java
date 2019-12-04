/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.resources;

import co.edu.uniandes.csw.dispositivos.dtos.MarcaDTO;
import co.edu.uniandes.csw.dispositivos.dtos.MarcaDetailDTO;
import co.edu.uniandes.csw.dispositivos.ejb.MarcaLogic;
import co.edu.uniandes.csw.dispositivos.entities.MarcaEntity;
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
@Path("marcas")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class MarcaResource {

    private static final Logger LOGGER = Logger.getLogger(MedioDePagoResource.class.getName());

    @Inject
    private MarcaLogic marcaLogic;

    /**
     * Crea una nueva catogira la informacion que se recibe en el cuerpo de la
     * petición y se regresa un objeto identico con un id auto-generado por la
     * base de datos.
     *
     * @param pMarca {@link MarcaDTO} - La marca que se desea guardar.
     * @return JSON {@link MarcaDTO} - La marca guardada con el atributo id
     * autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe la marca.
     */
    @POST
    public MarcaDTO createMarca(MarcaDTO pMarca) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "MarcaResource createMarca: input: {0}", pMarca);
        MarcaEntity marcaEntity = pMarca.toEntity();
        MarcaEntity nuevoMarcaEntity = marcaLogic.createMarca(marcaEntity);
        MarcaDTO nuevoMarcaDTO = new MarcaDTO(nuevoMarcaEntity);
        LOGGER.log(Level.INFO, "MarcaResource createMarca: output: {0}", nuevoMarcaDTO);
        return nuevoMarcaDTO;
    }

    /**
     * Busca la marca con el id asociado recibido en la URL y la devuelve.
     *
     * @param marcaId Identificador de la marca que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link MarcaDTO} - La marca buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la marca.
     */
    @GET
    @Path("{marcasId: \\d+}")
    public MarcaDTO getMarca(@PathParam("marcasId") Long marcaId) throws WebApplicationException {
        LOGGER.log(Level.INFO, "MarcaResource getMarca: input: {0}", marcaId);
        MarcaEntity marcaEntity = marcaLogic.getMarca(marcaId);
        if (marcaEntity == null) {
            throw new WebApplicationException("El recurso /marcasGet/" + marcaId + " no existe.", 404);
        }
        MarcaDTO detailDTO = new MarcaDTO(marcaEntity);
        LOGGER.log(Level.INFO, "MarcaResource getMarca: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Actualiza la marca con el id recibido en la URL con la informacion que se
     * recibe en el cuerpo de la petición.
     *
     * @param marcaId Identificador de la cateogoria que se desea actualizar.
     * Este debe ser una cadena de dígitos.
     * @param marca {@link MarcaDTO} La marca que se desea guardar.
     * @return JSON {@link MarcaDTO} - La marca guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la marca a
     * actualizar.
     */
    @PUT
    @Path("{marcasId: \\d+}")
    public MarcaDTO updateMarca(@PathParam("marcasId") Long marcaId, MarcaDTO marca) throws WebApplicationException, BusinessLogicException {
        LOGGER.log(Level.INFO, "MarcaResource updateMarca: input: id:{0} , marca: {1}", new Object[]{marcaId, marca});
        marca.setId(marcaId);
        if (marcaLogic.getMarca(marcaId) == null) {
            throw new WebApplicationException("El recurso /marcasUpdate/" + marcaId + " no existe.", 404);
        }
        MarcaDTO detailDTO = new MarcaDTO(marcaLogic.updateMarca(marcaId, marca.toEntity()));
        LOGGER.log(Level.INFO, "MarcaResource updateMarca: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Borra la marca con el id asociado recibido en la URL.
     *
     * @param marcaId Identificador de la marca que se desea borrar. Este debe
     * ser una cadena de dígitos.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar la marca.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la marca.
     */
    @DELETE
    @Path("{marcasId: \\d+}")
    public void deleteMarca(@PathParam("marcasId") Long marcaId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "MarcaResource deleteMarca: input: {0}", marcaId);
        if (marcaLogic.getMarca(marcaId) == null) {
            throw new WebApplicationException("El recurso /marcasDelete/" + marcaId + " no existe.", 404);
        }
        marcaLogic.deleteMarca(marcaId);
        LOGGER.info("MarcaResource deleteMarca: output: void");
    }

    @GET
    public List<MarcaDetailDTO> getMarcas() {
        LOGGER.info("MarcaResource getMarcas: input: void");
        List<MarcaDetailDTO> listaMarcas = listEntity2DetailDTO(marcaLogic.getMarcas());
        LOGGER.log(Level.INFO, "MarcaResource getMarca: output: {0}", listaMarcas);
        return listaMarcas;
    }

    private List<MarcaDetailDTO> listEntity2DetailDTO(List<MarcaEntity> entityList) {
        List<MarcaDetailDTO> list = new ArrayList<>();
        for (MarcaEntity entity : entityList) {
            list.add(new MarcaDetailDTO(entity));
        }
        return list;
    }
}
