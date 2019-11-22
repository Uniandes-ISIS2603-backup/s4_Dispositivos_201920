/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.resources;

import co.edu.uniandes.csw.dispositivos.dtos.DispositivoDTO;
import co.edu.uniandes.csw.dispositivos.dtos.DispositivoDetailDTO;
import co.edu.uniandes.csw.dispositivos.ejb.CategoriaLogic;
import co.edu.uniandes.csw.dispositivos.ejb.CategoriaDispositivoLogic;
import co.edu.uniandes.csw.dispositivos.ejb.DispositivoLogic;
import co.edu.uniandes.csw.dispositivos.entities.DispositivoEntity;
import co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author JuanL
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CategoriaDispositivoResource {

    private static final Logger LOGGER = Logger.getLogger(CategoriaDispositivoResource.class.getName());

    @Inject
    private DispositivoLogic dispositivoLogic;

    @Inject
    private CategoriaDispositivoLogic categoriaDispositivoLogic;

    @Inject
    private CategoriaLogic categoriaLogic;

    /**
     * Guarda un dispositivo dentro de una categoria con la informacion que
     * recibe el la URL. Se devuelve el dispositivo que se guarda en la
     * categoria.
     *
     * @param categoriaId Identificador de la categoria que se esta
     * actualizando. Este debe ser una cadena de dígitos.
     * @param dispositivoId Identificador del dispositivo que se desea guardar.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link DispositivoDTO} - El dispositivo guardado en la
     * categoria.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el dispositivo.
     */
    @POST
    @Path("{dispositivoId: \\d+}")
    public DispositivoDTO addDispositivo(@PathParam("categoriasId") Long categoriaId, @PathParam("dispositivoId") Long dispositivoId) {
        LOGGER.log(Level.INFO, "CategoriaDispositivoResource addDispositivo: input: categoriaID: {0} , dispositivoId: {1}", new Object[]{categoriaId, dispositivoId});
        if (dispositivoLogic.getDispositivo(dispositivoId) == null) {
            throw new WebApplicationException("El recurso /dispositivo/" + dispositivoId + " no existe", 404);
        }
        DispositivoDTO dispositivoDTO = new DispositivoDTO(categoriaDispositivoLogic.addDispositivo(dispositivoId, categoriaId));
        LOGGER.log(Level.INFO, "CategoriaDispositivoResource addBook: output: {0}", dispositivoDTO);
        return dispositivoDTO;
    }

    /**
     * Busca y devuelve todos los dispositivos que existen en la categoria.
     *
     * @param categoriaId Identificador de la categoria que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSONArray {@link DispositivoDetailDTO} - Los libros encontrados
     * en la editorial. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<DispositivoDetailDTO> getDispositivos(@PathParam("categoriasId") Long categoriaId) {
        LOGGER.log(Level.INFO, "CategoriaDispostivoResource getDispositivo: input: {0}", categoriaId);
        List<DispositivoDetailDTO> listaDetailDTOs = dispositivosListEntity2DTO(categoriaDispositivoLogic.getDispositivos(categoriaId));
        LOGGER.log(Level.INFO, "CategoriaDispositivoResource getDispositivos: output: {0}", listaDetailDTOs);
        return listaDetailDTOs;
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
     * Busca el dispositivo con el id asociado dentro de la categoria con id
     * asociado.
     *
     * @param categoriaId Identificador de la categoria que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @param dispositivoId Identificador del dispositivo que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link DispositivoDetailDTO} - El dispositivo buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el dispositivo.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el dispositivo en la
     * categoria.
     */
    @GET
    @Path("{dispositivoId: \\d+}")
    public DispositivoDetailDTO getDispositivo(@PathParam("categoriasId") Long categoriaId, @PathParam("dispositivoId") Long dispositivoId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "CategoriaDispositivoResource getDispositivo: input: categoriasID: {0} , dispositivoId: {1}", new Object[]{categoriaId, dispositivoId});
        if (dispositivoLogic.getDispositivo(dispositivoId) == null) {
            throw new WebApplicationException("El recurso /categorias/" + categoriaId + "/dispositivo/" + dispositivoId + " no existe.", 404);
        }
        DispositivoDetailDTO dispositivoDetailDTO = new DispositivoDetailDTO(categoriaDispositivoLogic.getDispositivo(categoriaId, dispositivoId));
        LOGGER.log(Level.INFO, "CategoriaDispositvoResource getDispositivo: output: {0}", dispositivoDetailDTO);
        return dispositivoDetailDTO;
    }

    /**
     * Remplaza las instancias de dispositivo asociadas a una instancia de
     * categoria.
     *
     * @param categoriaId Identificador de la categoria que se esta remplazando.
     * Este debe ser una cadena de dígitos.
     * @param dispositivos JSONArray {@link DisositivoDetailDTO} El arreglo de
     * dispositivos nuevo para la categoria.
     * @return JSON {@link DispositivoDetailDTO} - El arreglo de dispositivos
     * guardado en la categoria.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el dispositivo.
     */
    @PUT
    public List<DispositivoDetailDTO> replaceDispositivos(@PathParam("categoriasId") Long categoriaId, List<DispositivoDetailDTO> dispositivos) {
        LOGGER.log(Level.INFO, "CategoriaDispostivoResource replaceDispositivo: input: categoriasId: {0} , dispositivos: {1}", new Object[]{categoriaId, dispositivos});
        for (DispositivoDetailDTO dispositivo : dispositivos) {
            if (dispositivoLogic.getDispositivo(dispositivo.getId()) == null) {
                throw new WebApplicationException("El recurso /dispositivo/" + dispositivo.getId() + " no existe.", 404);
            }
        }
        List<DispositivoDetailDTO> listaDetailDTOs = dispositivosListEntity2DTO(categoriaDispositivoLogic.replaceDispositivos(categoriaId, dispositivosListDTO2Entity(dispositivos)));
        LOGGER.log(Level.INFO, "CategoriaDispositivoResource replaceDispositivo: output: {0}", listaDetailDTOs);
        return listaDetailDTOs;
    }

    /**
     * Convierte una lista de DispositivosDetailDTO a una lista de
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
