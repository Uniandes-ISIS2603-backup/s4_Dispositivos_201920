/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.resources;

import co.edu.uniandes.csw.dispositivos.dtos.ClienteDTO;
import co.edu.uniandes.csw.dispositivos.dtos.ClienteDetailDTO;
import co.edu.uniandes.csw.dispositivos.ejb.ClienteLogic;
import co.edu.uniandes.csw.dispositivos.entities.ClienteEntity;
import co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
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
 *
 * @author Carlos Salazar
 */
@Path("clientes")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ClienteResource {

    private static final Logger LOGGER = Logger.getLogger(ClienteResource.class.getName());

    @Inject
    private ClienteLogic clienteLogic;

    @POST
    public ClienteDTO createCliente(@PathParam("clienteId") Long clienteId, ClienteDTO cliente) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ClienteResource createCliente: input: {0}", cliente);
        ClienteEntity clienteEntity = cliente.toEntity();
        ClienteEntity nuevoMedioEntity = clienteLogic.createCliente(clienteEntity);
        ClienteDTO nuevoMedioDTO = new ClienteDTO(nuevoMedioEntity);
        LOGGER.log(Level.INFO, "ClienteResource createCliente: output: {0}", nuevoMedioDTO);
        return nuevoMedioDTO;
    }

    @GET
    public List<ClienteDetailDTO> getClientes(@PathParam("clienteId") Long clienteId) {
        LOGGER.info("ClienteResource getClientes: input: void");
        List<ClienteDetailDTO> listaClientes = listEntity2DetailDTO(clienteLogic.getClientes());
        LOGGER.log(Level.INFO, "ClienteResource getClientes: output: {0}", listaClientes);
        return listaClientes;
    }

    private List<ClienteDetailDTO> listEntity2DetailDTO(List<ClienteEntity> entityList) {
        List<ClienteDetailDTO> list = new ArrayList<ClienteDetailDTO>();
        for (ClienteEntity entity : entityList) {
            list.add(new ClienteDetailDTO(entity));
        }
        return list;
    }

    @GET
    @Path("{clienteId: \\d+}")
    public ClienteDTO getCliente(@PathParam("clienteId") Long clienteId) {
        return null;
    }

    @PUT
    @Path("{clienteId: \\d+}")
    public ClienteDTO updateCliente(@PathParam("clienteId") Long clienteId, ClienteDTO cliente) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ClienteResource updateCliente: input: id:{0} , cliente: {1}", new Object[]{clienteId, cliente});
        cliente.setId(clienteId);
        if (clienteLogic.getCliente(clienteId) == null) {
            throw new WebApplicationException("El recurso /clientes/" + clienteId + " no existe.", 404);
        }
        ClienteDTO detailDTO = new ClienteDTO(clienteLogic.updateCliente(clienteId, cliente.toEntity()));
        LOGGER.log(Level.INFO, "ClienteResource updateCliente: output: {0}", detailDTO);
        return detailDTO;
    }

    @DELETE
    @Path("{clienteId: \\d+}")
    public void deleteCliente(@PathParam("clienteId") Long clienteId) {
        LOGGER.log(Level.INFO, "ClienteResource deleteCliente: input: {0}", clienteId);
        if (clienteLogic.getCliente(clienteId) == null) {
            throw new WebApplicationException("El recurso /clientes/" + clienteId + " no existe.", 404);
        }
        clienteLogic.deleteCliente(clienteId);
        LOGGER.info("ClienteResource deleteCliente: output: void");
    }
}
