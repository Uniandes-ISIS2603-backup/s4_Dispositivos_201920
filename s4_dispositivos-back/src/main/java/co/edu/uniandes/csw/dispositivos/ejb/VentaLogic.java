/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.ejb;

import co.edu.uniandes.csw.dispositivos.entities.VendedorEntity;
import co.edu.uniandes.csw.dispositivos.entities.VentaEntity;
import co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.dispositivos.persistence.VendedorPersistence;
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
    /**
     * Conexión con la capa de persistencia de Venta
     */
    @Inject
    private VentaPersistence vapersistence;
    
    /**
     * Conexión con la capa de persistencia de Vendedor
     */
    @Inject
    private VendedorPersistence vrpersistence;
    
    /**
     * Validación del método agregar venta
     * @param vendedor
     * @param venta
     * @return venta creada
     * @throws co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException
     */
    public VentaEntity createVenta(Long vendedor, VentaEntity venta) throws BusinessLogicException
    {
        if(venta.getVendedor() == null || vrpersistence.find(vendedor) == null)
            throw new BusinessLogicException("No se puede registrar una venta sin un vendedor asociado");
        
        if(venta.getPrecioReventa() == null || venta.getPrecioReventa() < 0)
            throw new BusinessLogicException("El precio de reventa no puede ser negativo");
        
        VendedorEntity vre = vrpersistence.find(vendedor);
        venta.setVendedor(vre);
        venta = vapersistence.create(venta); 
        return venta; 
    }
    
    /**
     * Validación del método buscar venta
     * @param idfVendedor
     * @param idfVenta
     * @return venta encontrada
     */
    public VentaEntity findVenta(Long idfVendedor, Long idfVenta)
    {       
        return vapersistence.find(idfVendedor, idfVenta);
    }
    
    /**
     * Validación del método encontrar todas las ventas
     * @param vendedorId
     * @return lista de las ventas del vendedor 
     */
    public List<VentaEntity> findAllVentas(Long vendedorId)
    {
        VendedorEntity ventity = vrpersistence.find(vendedorId); 
        return ventity.getVentas();
    }
    
    /**
     * Validación del método cambiar venta
     * @param vrID
     * @param uvaEntity
     * @return venta actualizada
     * @throws co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException
     */
    public VentaEntity updateVenta(Long vrID, VentaEntity uvaEntity) throws BusinessLogicException
    {
        if(uvaEntity.getVendedor() == null || vrpersistence.find(vrID) == null)
            throw new BusinessLogicException("No se puede definir el vendedor de una venta como null");
        
        if(uvaEntity.getPrecioReventa() == null || uvaEntity.getPrecioReventa() < 0)
            throw new BusinessLogicException("El precio de reventa no puede ser negativo");
        VendedorEntity vendedor = vrpersistence.find(vrID);
        uvaEntity.setVendedor(vendedor);
        return vapersistence.update(uvaEntity);
    }
    
    /**
     * Validación del método borrar venta
     * @param iddVendedor
     * @param iddVenta
     * @throws co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException
     */
    public void deleteVenta(Long iddVendedor, Long iddVenta) throws BusinessLogicException
    {
        VentaEntity nonventa = findVenta(iddVendedor, iddVenta);
        if(nonventa == null) throw new BusinessLogicException("No se encuentra la venta con id = " + iddVenta); 
        vapersistence.delete(nonventa.getId()); 
    }
}
