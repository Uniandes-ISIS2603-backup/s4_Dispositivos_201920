/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.resources;

import co.edu.uniandes.csw.dispositivos.dtos.CategoriaDTO;
import co.edu.uniandes.csw.dispositivos.ejb.CategoriaLogic;
import co.edu.uniandes.csw.dispositivos.entities.CategoriaEntity;
import co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException;
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
@Path("cateogrias")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class CategoriaResource {

    private static final Logger LOGGER = Logger.getLogger(MedioDePagoResource.class.getName());

    @Inject
    private CategoriaLogic categoriaLogic;

    /**
     * Crea una nueva catogira la informacion que se recibe en el cuerpo de la
     * petición y se regresa un objeto identico con un id auto-generado por la
     * base de datos.
     *
     * @param pCategoria {@link CategoriaDTO} - La categoria que se desea
     * guardar.
     * @return JSON {@link CategoriaDTO} - La categoria guardada con el atributo
     * id autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe la categoria.
     */
    @POST
    public CategoriaDTO createCategoria(CategoriaDTO pCategoria) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "CategoriaResource createCategoria: input: {0}", pCategoria);
        CategoriaEntity categoriaEntity = pCategoria.toEntity();
        CategoriaEntity nuevoCategoriaEntity = categoriaLogic.createCategoria(categoriaEntity);
        CategoriaDTO nuevoCategoriaDTO = new CategoriaDTO(nuevoCategoriaEntity);
        LOGGER.log(Level.INFO, "CategoriaResource createCategoria: output: {0}", nuevoCategoriaDTO);
        return nuevoCategoriaDTO;
    }

    /**
     * Busca la categoria con el id asociado recibido en la URL y la devuelve.
     *
     * @param categoriaId Identificador de la categoria que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link CategoriaDTO} - La categoria buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la categoria.
     */
    @GET
    @Path("{categoriasId: \\d+}")
    public CategoriaDTO getCatego(@PathParam("categoriasId") Long categoriaId) throws WebApplicationException {
        LOGGER.log(Level.INFO, "CategoriaResource getCategoria: input: {0}", categoriaId);
        CategoriaEntity categoriaEntity = categoriaLogic.getCategoria(categoriaId);
        if (categoriaEntity == null) {
            throw new WebApplicationException("El recurso /categorias/" + categoriaId + " no existe.", 404);
        }
        CategoriaDTO detailDTO = new CategoriaDTO(categoriaEntity);
        LOGGER.log(Level.INFO, "CategoriaResource getCategoria: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Actualiza la categoria con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param categoriaId Identificador de la cateogoria que se desea
     * actualizar. Este debe ser una cadena de dígitos.
     * @param categoria {@link CategoriaDTO} La categoria que se desea guardar.
     * @return JSON {@link CategoriaDTO} - La categoria guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la categoria a
     * actualizar.
     */
    @PUT
    @Path("{categoriasId: \\d+}")
    public CategoriaDTO updateCategoria(@PathParam("categoriasId") Long categoriaId, CategoriaDTO categoria) throws WebApplicationException, BusinessLogicException {
        LOGGER.log(Level.INFO, "CategoriaResource updateCategoria: input: id:{0} , categoria: {1}", new Object[]{categoriaId, categoria});
        categoria.setId(categoriaId);
        if (categoriaLogic.getCategoria(categoriaId) == null) {
            throw new WebApplicationException("El recurso /categorias/" + categoriaId + " no existe.", 404);
        }
        CategoriaDTO detailDTO = new CategoriaDTO(categoriaLogic.updateCategoria(categoriaId, categoria.toEntity()));
        LOGGER.log(Level.INFO, "CategoriaResource updateCategoria: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Borra la categoria con el id asociado recibido en la URL.
     *
     * @param categoriaId Identificador de la categoria que se desea borrar.
     * Este debe ser una cadena de dígitos.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar la categoria.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la categoria.
     */
    @DELETE
    @Path("{categoriasId: \\d+}")
    public void deleteCategoria(@PathParam("categoriasId") Long categoriaId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "CategoriaResource deleteCategoria: input: {0}", categoriaId);
        if (categoriaLogic.getCategoria(categoriaId) == null) {
            throw new WebApplicationException("El recurso /categorias/" + categoriaId + " no existe.", 404);
        }
        categoriaLogic.deleteCategoria(categoriaId);
        LOGGER.info("CategoriaResource deleteCategoria: output: void");
    }
}
