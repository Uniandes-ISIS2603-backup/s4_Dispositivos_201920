/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.resources;

import co.edu.uniandes.csw.dispositivos.dtos.CategoriaDTO;
import co.edu.uniandes.csw.dispositivos.dtos.DispositivoDetailDTO;
import co.edu.uniandes.csw.dispositivos.ejb.CategoriaLogic;
import co.edu.uniandes.csw.dispositivos.ejb.DispositivoCategoriaLogic;
import co.edu.uniandes.csw.dispositivos.ejb.DispositivoLogic;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
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
@Path("dispositivos/{dispositivosId: \\d+}/categoria")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DispositivoCategoriaResource {

    private static final Logger LOGGER = Logger.getLogger(DispositivoCategoriaResource.class.getName());

    @Inject
    private DispositivoLogic dispositivoLogic;

    @Inject
    private DispositivoCategoriaLogic dispositivoCategoriaLogic;

    @Inject
    private CategoriaLogic categoriaLogic;

    /**
     *
     * @param dispositivosId
     * @param categoria
     * @return
     */
    @PUT
    public DispositivoDetailDTO replaceCategoria(@PathParam("dispositivosId") Long dispositivosId, CategoriaDTO categoria) {
        LOGGER.log(Level.INFO, "DispositivoCategorialResource replaceCategoria: input: dispositivosId{0} , Categoria:{1}", new Object[]{dispositivosId, categoria});
        if (dispositivoLogic.getDispositivo(dispositivosId) == null) {
            throw new WebApplicationException("El recurso /dispositivos/" + dispositivosId + " no existe.", 404);
        }
        if (categoriaLogic.getCategoria(categoria.getId()) == null) {
            throw new WebApplicationException("El recurso /editorials/" + categoria.getId() + " no existe.", 404);
        }
        DispositivoDetailDTO dispositivoDetailDTO = new DispositivoDetailDTO(dispositivoCategoriaLogic.replaceCategoria(dispositivosId, categoria.getId()));
        LOGGER.log(Level.INFO, "DispositivoCategoriaResource replaceCategoria: output: {0}", dispositivoDetailDTO);
        return dispositivoDetailDTO;
    }
}
