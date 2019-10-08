/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.resources;

import co.edu.uniandes.csw.dispositivos.dtos.ClienteDTO;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;

/**
 *
 * @author Carlos Salazar
 */
@Path("clientes")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ClienteResource {

    @POST
    public ClienteDTO createCliente(@PathParam("clienteId") Long clienteId, ClienteDTO cliente) {
        return null;
    }

    @GET
    public List<ClienteDTO> getClientes(@PathParam("clienteId") Long clienteId) {
        return null;
    }

    @GET
    @Path("{clienteId: \\d+}")
    public ClienteDTO getCliente(@PathParam("clienteId") Long clienteId) {
        return null;
    }

    @PUT
    @Path("{clienteId: \\d+}")
    public ClienteDTO updateCliente(@PathParam("clienteId") Long clienteId, ClienteDTO cliente) {
        return null;
    }

    @DELETE
    @Path("{clienteId: \\d+}")
    public void deleteCliente(@PathParam("clienteId") Long clienteId) {
    }
}
