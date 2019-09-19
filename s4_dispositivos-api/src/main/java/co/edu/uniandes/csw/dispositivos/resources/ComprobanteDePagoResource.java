/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.resources;

import co.edu.uniandes.csw.dispositivos.dtos.ComprobanteDePagoDTO;
import co.edu.uniandes.csw.dispositivos.ejb.ComprobanteDePagoLogic;
import co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import co.edu.uniandes.csw.dispositivos.entities.ComprobanteDePagoEntity;
import javax.ws.rs.DELETE;
import javax.ws.rs.WebApplicationException;

/**
 * Clase que implementa el recurso "comprobanteDePago"
 * @author Dianis Caro
 */
@Path("comprobantes")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ComprobanteDePagoResource 
{
    @Inject
    private ComprobanteDePagoLogic comprobanteLogic;
    
    private List<ComprobanteDePagoDTO> listEntity2DTO(List<ComprobanteDePagoEntity> entityList) {
        List<ComprobanteDePagoDTO> list = new ArrayList<ComprobanteDePagoDTO>();
        for (ComprobanteDePagoEntity entity : entityList) 
        {
            list.add(new ComprobanteDePagoDTO(entity));
        }
        return list;
    }
}
