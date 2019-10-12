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
public class VendedorVentaLogic 
{
    /**
     * Conexión con la capa de persistencia para Venta
     */
    @Inject
    private VentaPersistence vapersistence;
    
    /**
     * Conexión con la capa de persistencia para Vendedor
     */
    @Inject
    private VendedorPersistence vrpersistence;
    
    /**
     * Validación del método agregar venta
     * @param Idvendedor
     * @param Idventa
     * @return venta creada
     */
    public VentaEntity createVenta(Long Idvendedor, Long Idventa) 
    {
        VendedorEntity vrentity = vrpersistence.find(Idvendedor); 
        VentaEntity vaentity = vapersistence.find(Idventa); 
        vaentity.setVendedor(vrentity);
        return vaentity; 
    }
    
    /**
     * Validación del método buscar venta
     * @param idfVendedor
     * @param idfVenta
     * @return venta encontrada
     * @throws co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException
     */
    public VentaEntity findVenta(Long idfVendedor, Long idfVenta) throws BusinessLogicException
    {       
        List<VentaEntity> vaset = vrpersistence.find(idfVendedor).getVentas(); 
        VentaEntity obtainedvr = vapersistence.find(idfVenta); 
        int indizer = vaset.indexOf(obtainedvr);
        if(indizer < 0)
            throw new BusinessLogicException("No se encuentra ningún vendedor asociado a la venta");
        else return obtainedvr;
    }
    
    /**
     * Validación del método encontrar todas las ventas
     * @param vendedorID
     * @return lista de las ventas existentes
     * @throws co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException 
     */
    public List<VentaEntity> findAllVentas(Long vendedorID) throws BusinessLogicException
    {
        List<VentaEntity> valisted = vrpersistence.find(vendedorID).getVentas(); 
        return valisted;
    }
    
    /**
     * Reemplaza ventas de un vendedor
     * @param ventas 
     * @param vendedorId 
     * @return 
     */
    public List<VentaEntity> replaceVentas(Long vendedorId, List<VentaEntity> ventas) 
    {
        VendedorEntity vendedor = vrpersistence.find(vendedorId);
        List<VentaEntity> ventaList = vapersistence.findAll();
        for (VentaEntity ventaE : ventaList) 
        {
            if (ventas.contains(ventaE))
                ventaE.setVendedor(vendedor);
            else if (ventaE.getVendedor() != null && ventaE.getVendedor().equals(vendedor))
                ventaE.setVendedor(null);
        }
        return ventas;
    }
}
