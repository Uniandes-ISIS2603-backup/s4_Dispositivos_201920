/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.resources;

import co.edu.uniandes.csw.dispositivos.dtos.MarcaDTO;
import co.edu.uniandes.csw.dispositivos.dtos.DispositivoDetailDTO;
import co.edu.uniandes.csw.dispositivos.ejb.DispositivoLogic;
import co.edu.uniandes.csw.dispositivos.ejb.DispositivoMarcaLogic;
import co.edu.uniandes.csw.dispositivos.ejb.MarcaLogic;
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
@Path("dispositivos/{dispositivosId: \\d+}/marca")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DispositivoMarcaResource {

    private static final Logger LOGGER = Logger.getLogger(DispositivoMarcaResource.class.getName());

    @Inject
    private DispositivoLogic dispositivoLogic;

    @Inject
    private DispositivoMarcaLogic dispositivoMarcaLogic;

    @Inject
    private MarcaLogic marcaLogic;

    /**
     *
     * @param dispositivosId
     * @param marca
     * @return
     */
    @PUT
    public DispositivoDetailDTO replaceMarca(@PathParam("dispositivosId") Long dispositivosId, MarcaDTO marca) {
        LOGGER.log(Level.INFO, "DispositivoMarcalResource replaceMarca: input: dispositivosId{0} , Marca:{1}", new Object[]{dispositivosId, marca});
        if (dispositivoLogic.getDispositivo(dispositivosId) == null) {
            throw new WebApplicationException("El recurso /dispositivos/" + dispositivosId + " no existe.", 404);
        }
        if (marcaLogic.getMarca(marca.getId()) == null) {
            throw new WebApplicationException("El recurso /editorials/" + marca.getId() + " no existe.", 404);
        }
        DispositivoDetailDTO dispositivoDetailDTO = new DispositivoDetailDTO(dispositivoMarcaLogic.replaceMarca(dispositivosId, marca.getId()));
        LOGGER.log(Level.INFO, "DispositivoMarcaResource replaceMarca: output: {0}", dispositivoDetailDTO);
        return dispositivoDetailDTO;
    }

}
