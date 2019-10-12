/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.resources;

import co.edu.uniandes.csw.dispositivos.dtos.VendedorDTO;
import co.edu.uniandes.csw.dispositivos.dtos.VendedorDetailDTO;
import co.edu.uniandes.csw.dispositivos.ejb.VendedorLogic;
import co.edu.uniandes.csw.dispositivos.entities.VendedorEntity;
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
 * @author Zharet Bautista Montes
 */
@Path("vendedores")
@Produces("application/json")
@Consumes("application/json") 
@RequestScoped
public class VendedorResource 
{
    private static final Logger LOGGER = Logger.getLogger(VendedorResource.class.getName());
    
    @Inject
    private VendedorLogic vrlogic;  

    /**
     * Crea el vendedor mediante el DTO recibido por JSON.
     * @param vendedor
     * @return Vendedor creado
     * @throws BusinessLogicException
     */
    @POST
    public VendedorDTO createVendedor(VendedorDTO vendedor) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "VendedorResource createVendedor: input: {0}", vendedor);
        VendedorEntity refvr = vendedor.toEntity();
        VendedorEntity newvrentity = vrlogic.createVendedor(refvr); 
        VendedorDTO newvrdto = new VendedorDTO(newvrentity);
        LOGGER.log(Level.INFO, "VendedorResource createVendedor: output: {0}", newvrdto);
        return newvrdto;
    }

    /**
     * Obtiene la lista de todos los vendedores existentes.
     * @return Lista de todos los vendedores
     * @throws BusinessLogicException
     */
    @GET
    public List<VendedorDetailDTO> getAllVendedores() throws BusinessLogicException
    {
        LOGGER.info("VendedorResource getAllVendedores: input: void");
        List<VendedorEntity> vrconteo = vrlogic.findAllVendedores(); 
        List<VendedorDetailDTO> vrlisted = new ArrayList<>();
        for(VendedorEntity vendedor : vrconteo)
            vrlisted.add(new VendedorDetailDTO(vendedor));
        LOGGER.log(Level.INFO, "VendedorResource getAllVendedores: output: {0}", vrlisted);
        return vrlisted; 
    }

    /**
     * Obtiene el vendedor mediante el id recibido por el URL.
     * @param idVendedor
     * @return Vendedor obtenido
     * @throws BusinessLogicException
     * @throws WebApplicationException
     */
    @GET
    @Path("{vendedorID: \\d+}")
    public VendedorDetailDTO getVendedor(@PathParam("vendedorID") Long idVendedor) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "VendedorResource getVendedor: input: {0}", idVendedor);
        VendedorEntity wantedvr = vrlogic.findVendedor(idVendedor);
        if(wantedvr == null)
            throw new WebApplicationException("No se encuentra el recurso /vendedores/" + idVendedor, 404);
        VendedorDetailDTO vrdetail = new VendedorDetailDTO(wantedvr);
        LOGGER.log(Level.INFO, "VendedorResource getVendedor: output: {0}", vrdetail);
        return vrdetail; 
    }

    /**
     * Actualiza el vendedor mediante el id recibido por el URL y la nueva definición del vendedor recibida por JSON.
     * @param idVendedor
     * @param vrdto
     * @return Vendedor actualizado
     * @throws BusinessLogicException
     * @throws WebApplicationException
     */
    @PUT
    @Path("{vendedorID: \\d+}")
    public VendedorDetailDTO updateVendedor(@PathParam("vendedorID") Long idVendedor, VendedorDetailDTO vrdto) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "VendedorResource updateVendedor: input: {0}, vendedor: {1}", new Object[]{idVendedor, vrdto});
        vrdto.setId(idVendedor);
        if(vrlogic.findVendedor(idVendedor) == null)
            throw new WebApplicationException("No se encuentra el recurso /vendedores/" + idVendedor, 404);
        VendedorDetailDTO detailVendedor = new VendedorDetailDTO(vrlogic.updateVendedor(vrdto.toEntity()));
        LOGGER.log(Level.INFO, "VendedorResource updateVendedor: output: {0}", detailVendedor);
        return detailVendedor;
    }

    /**
     * Borra el vendedor mediante el id recibido por el URL.
     * @param idVendedor
     * @throws BusinessLogicException
     * @throws WebApplicationException
     */
    @DELETE
    @Path("{vendedorID: \\d+}")
    public void deleteVendedor(@PathParam("vendedorID") Long idVendedor) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "VendedorResource deleteVendedor: input: {0}", idVendedor);
        VendedorEntity notvendedor = vrlogic.findVendedor(idVendedor); 
        if(notvendedor == null)
            throw new WebApplicationException("No se encuentra el recurso /vendedores/" + idVendedor, 404); 
        vrlogic.deleteVendedor(idVendedor);
        LOGGER.info("VendedorResource deleteVendedor: output: void");
    }
    
    /**
     *
     * @param idVendedor
     * @return
     * @throws BusinessLogicException
     */
    @Path("{vendedorID: \\d+}/ventas")
    public Class<VendedorVentaResource> getVendedorVentaResource(@PathParam("vendedorID") Long idVendedor) throws BusinessLogicException
    {
        VendedorEntity vaowner = vrlogic.findVendedor(idVendedor); 
        if(vaowner == null)
            throw new WebApplicationException("No se encuentra el recurso /vendedores/" + idVendedor + " al cual asignarle la venta", 404); 
        return VendedorVentaResource.class;
    }
}
