/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.resources;

import co.edu.uniandes.csw.dispositivos.dtos.DispositivoDTO;
import co.edu.uniandes.csw.dispositivos.dtos.DispositivoDetailDTO;
import co.edu.uniandes.csw.dispositivos.ejb.DispositivoCategoriaLogic;
import co.edu.uniandes.csw.dispositivos.ejb.DispositivoLogic;
import co.edu.uniandes.csw.dispositivos.ejb.DispositivoMarcaLogic;
import co.edu.uniandes.csw.dispositivos.entities.DispositivoEntity;
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
 * @author Santiago Fajardo
 */
@Path("dispositivos")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class DispositivoResource {

    private static final Logger LOGGER = Logger.getLogger(DispositivoResource.class.getName());

    @Inject
    private DispositivoLogic dispositivoLogic;

    @Inject
    private DispositivoMarcaLogic dispositivoMarcaLogic;

    @Inject
    private DispositivoCategoriaLogic dispositivoCategoriaLogic;

    /**
     *
     * @param dispositivo
     * @return
     * @throws BusinessLogicException
     */
    @POST
    public DispositivoDTO createDispositivo(DispositivoDTO dispositivo) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "DispositivoResource createDispositivo: input: {0}", dispositivo);
        DispositivoDTO newDispositivoDTO = new DispositivoDTO(dispositivoLogic.createDispositivo(dispositivo.toEntity()));
        LOGGER.log(Level.INFO, "DispositivoResource createDispositivo: output: {0}", newDispositivoDTO);
        return newDispositivoDTO;
    }

    /**
     *
     * @return
     */
    @GET
    public List<DispositivoDetailDTO> getDispositivos() {
        LOGGER.info("DispositivoResource getDispositivos: input: void");
        List<DispositivoDetailDTO> listaDispositivo = listEntity2DetailDTO(dispositivoLogic.getDispositivos());
        LOGGER.log(Level.INFO, "DispositivoResource getDispositivo: output: {0}", listaDispositivo);
        return listaDispositivo;
    }

    /**
     *
     * @param dispositivosId
     * @return
     */
    @GET
    @Path("{dispositivosId: \\d+}")
    public DispositivoDetailDTO getDispositivo(@PathParam("dispositivosId") long dispositivosId) {
        LOGGER.log(Level.INFO, "DispositivoResource getDispositivo: input: {0}", dispositivosId);
        DispositivoEntity dispositivoEntity = dispositivoLogic.getDispositivo(dispositivosId);
        if (dispositivoEntity == null) {
            throw new WebApplicationException("El recurso /dispositivos/" + dispositivosId + " no existe.", 404);
        }
        DispositivoDetailDTO dispositivoDetailDTO = new DispositivoDetailDTO(dispositivoEntity);
        LOGGER.log(Level.INFO, "DispositivoResource getDispositivo: output: {0}", dispositivoDetailDTO);
        return dispositivoDetailDTO;
    }

    /**
     *
     * @param dispositivosId
     * @param dispositivo
     * @return
     * @throws BusinessLogicException
     */
    @PUT
    @Path("{dispositivosId: \\d+}")
    public DispositivoDTO updateDispositivo(@PathParam("dispositivosId") long dispositivosId, DispositivoDetailDTO dispositivo) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "DispositivoResource updateDispositivo: input: id: {0} , dispositivo: {1}", new Object[]{dispositivosId, dispositivo});
        dispositivo.setId(dispositivosId);
        if (dispositivoLogic.getDispositivo(dispositivo.getId()) == null) {
            throw new WebApplicationException("El recurso /dispositivos/" + dispositivosId + " no existe.", 404);

        }
        DispositivoDetailDTO detailDTO = new DispositivoDetailDTO(dispositivoLogic.updateDispositivo(dispositivosId, dispositivo.toEntity()));
        LOGGER.log(Level.INFO, "DispositivoResource updateDIspositivo: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     *
     * @param dispositivosId
     * @throws BusinessLogicException
     */
    @DELETE
    @Path("{dispositivosId: \\d+}")
    public void deleteDispositivo(@PathParam("dispositivosId") Long dispositivosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "DispositivoResource deleteDispositivo: input: {0}", dispositivosId);
        DispositivoEntity entity = dispositivoLogic.getDispositivo(dispositivosId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /dispositivos/" + dispositivosId + " no existe.", 404);
        }
        dispositivoCategoriaLogic.removeCategoria(dispositivosId);
        dispositivoMarcaLogic.removeMarca(dispositivosId);
        dispositivoLogic.deleteDispositivo(dispositivosId);
        LOGGER.info("DispositivoResource deleteDispositivo: output: void");

    }

    private List<DispositivoDetailDTO> listEntity2DetailDTO(List<DispositivoEntity> entityList) {
        List<DispositivoDetailDTO> list = new ArrayList<DispositivoDetailDTO>();
        for (DispositivoEntity entity : entityList) {
            list.add(new DispositivoDetailDTO(entity));
        }
        return list;
    }
    
    @Path("{dispositivosId: \\d+}/calificaciones")
    public Class<CalificacionResource> getCalificacionResource(@PathParam("dispositivosId") Long dispositivosId) {
        if (dispositivoLogic.getDispositivo(dispositivosId) == null) {
            throw new WebApplicationException("El recurso /dispositivos/" + dispositivosId + "/calificaciones no existe.", 404);
        }
        return CalificacionResource.class;
    }

}
