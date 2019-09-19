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
@Path("vendedor")
@Produces("application/json")
@Consumes("application/json") 
@RequestScoped
public class VendedorResource 
{
    @Inject
    private VendedorLogic vendedorvrlogic;  
    
    @POST
    public VendedorDTO createVendedor(VendedorDTO vendedor) throws BusinessLogicException
    {
        VendedorDTO newvendedor = new VendedorDTO(vendedorvrlogic.createVendedor(vendedor.toEntity()));
        return newvendedor;
    }
    
    @GET
    public List<VendedorDetailDTO> getAllVendedores() throws BusinessLogicException
    {
        List<VendedorEntity> vrconteo = vendedorvrlogic.findAllVendedores(); 
        List<VendedorDetailDTO> vrlisted = new ArrayList<>();
        for(VendedorEntity vendedor : vrconteo)
            vrlisted.add(new VendedorDetailDTO(vendedor));
        return vrlisted; 
    }
    
    @GET
    @Path("{vendedorID: \\d+}")
    public VendedorDetailDTO getVendedor(@PathParam("vendedorID") Long idVendedor)
    {
        VendedorEntity wantedvr = vendedorvrlogic.findVendedor(idVendedor);
        if(wantedvr == null)
            throw new WebApplicationException("No se encuentra el recurso /vendedor/" + idVendedor, 404);
        VendedorDetailDTO vrdetail = new VendedorDetailDTO(wantedvr);
        return vrdetail;
    }
    
    @PUT
    @Path("{vendedorID: \\d+}")
    public VendedorDetailDTO updateVendedor(@PathParam("vendedorID") Long idVendedor, VendedorDetailDTO vrdto) throws BusinessLogicException
    {
        vrdto.setId(idVendedor);
        if(vendedorvrlogic.findVendedor(idVendedor) == null)
            throw new WebApplicationException("No se encuentra el recurso /vendedor/" + idVendedor, 404);
        VendedorDetailDTO detailVendedor = new VendedorDetailDTO(vendedorvrlogic.updateVendedor(vrdto.toEntity()));
        return detailVendedor; 
    }
    
    @DELETE
    @Path("{vendedorID: \\d+}")
    public void deleteVendedor(@PathParam("vendedorID") Long idVendedor) throws BusinessLogicException
    {
        VendedorEntity notvendedor = vendedorvrlogic.findVendedor(idVendedor); 
        if(notvendedor == null)
            throw new WebApplicationException("No se encuentra el recurso /vendedor/" + idVendedor, 404); 
        
        vendedorvrlogic.deleteVendedor(idVendedor);
    }
}
