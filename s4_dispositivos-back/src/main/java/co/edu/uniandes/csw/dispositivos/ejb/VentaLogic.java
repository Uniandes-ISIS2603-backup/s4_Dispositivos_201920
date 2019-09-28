/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.ejb;

import co.edu.uniandes.csw.dispositivos.entities.VentaEntity;
import co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.dispositivos.persistence.VentaPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Zharet Bautista Montes
 */

@Stateless
public class VentaLogic 
{
    @Inject
    private VentaPersistence vapersistence;
    
    public VentaEntity createVenta(VentaEntity venta) throws BusinessLogicException
    {
        if(venta.getPrecioReventa() < 0)
        { throw new BusinessLogicException("El precio de reventa no puede ser negativo"); }
        
        venta = vapersistence.create(venta); 
        return venta; 
    }
    
    public VentaEntity findVenta(Long idfVenta) throws BusinessLogicException
    {       
        VentaEntity obtainedvr = vapersistence.find(idfVenta);      
        return obtainedvr;
    }
    
    public List<VentaEntity> findAllVentas() throws BusinessLogicException
    {
        List<VentaEntity> valisted = vapersistence.findAll(); 
        return valisted;
    }
    
    public VentaEntity updateVenta(VentaEntity uvaEntity) throws BusinessLogicException
    {
        if(uvaEntity == null)
        { throw new BusinessLogicException("No se recibieron datos para modificar"); }
        VentaEntity changedva = vapersistence.update(uvaEntity); 
        return changedva;
    }
    
    public void deleteVenta(Long iddVenta) throws BusinessLogicException
    {
        if(vapersistence.find(iddVenta) == null)
        { throw new BusinessLogicException("La venta ya no existe"); }
        vapersistence.delete(iddVenta); 
    }
}
