/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.ejb;

import co.edu.uniandes.csw.dispositivos.entities.VendedorEntity;
import co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.dispositivos.persistence.VendedorPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Zharet Bautista Montes
 */
@Stateless
public class VendedorLogic 
{
    @Inject
    private VendedorPersistence vrpersistence; 
    
    /**
     * Validación del método agregar vendedor
     * @param vendedor
     * @return vendedor creado
     * @throws co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException
     */
    public VendedorEntity createVendedor(VendedorEntity vendedor) throws BusinessLogicException
    {
        if(vrpersistence.findByCedula(vendedor.getCedula()) != null)
        { throw new BusinessLogicException("El vendedor ya existe"); }
        
        if((((((vendedor.getNombre() == null || vendedor.getApellido() == null) || vendedor.getUsuario() == null) || vendedor.getContrasena() == null) || vendedor.getCedula() < 0) || vendedor.getCelular() < 0) || vendedor.getCorreoElectronico()==null)
        {  throw new BusinessLogicException("No puede haber ningún campo vacío"); }
        
        vrpersistence.create(vendedor); 
        return vendedor; 
    }
    
    /**
     * Validación del método buscar vendedor
     * @param idfVendedor
     * @return vendedor encontrado
     * @throws co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException 
     */
    public VendedorEntity findVendedor(Long idfVendedor) throws BusinessLogicException
    {
        VendedorEntity obtainedvr = vrpersistence.find(idfVendedor);         
        return obtainedvr; 
    }
    
    /**
     * Validación del método buscar vendedor por cédula
     * @param cedulaVendedorf
     * @return vendedor obtenido por su cédula
     * @throws co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException 
     */
    public VendedorEntity findByCedulaVendedor(Double cedulaVendedorf) throws BusinessLogicException
    {
        if(cedulaVendedorf == null)
        { throw new BusinessLogicException("No se recibió ninguna cédula"); }
        
        VendedorEntity obtainedvr = vrpersistence.findByCedula(cedulaVendedorf); 
        return obtainedvr;
    }
    
    /**
     * Validación del método encontrar todos los vendedores
     * @return lista de los vendedores existentes
     * @throws co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException
     */
    public List<VendedorEntity> findAllVendedores() throws BusinessLogicException
    {
        List<VendedorEntity> vrlisted = vrpersistence.findAll(); 
        return vrlisted;
    }
    
    /**
     * Validación del método cambiar vendedor
     * @param uvrEntity
     * @return vendedor actualizado
     * @throws co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException
     */
    public VendedorEntity updateVendedor(VendedorEntity uvrEntity) throws BusinessLogicException
    {
        if(uvrEntity == null)
        { throw new BusinessLogicException("No se recibieron datos para modificar"); }
        VendedorEntity changedvr = vrpersistence.update(uvrEntity); 
        return changedvr;
    }
    
    /**
     * Validación del método borrar vendedor
     * @param iddVendedor
     * @throws co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException
     */
    public void deleteVendedor(Long iddVendedor) throws BusinessLogicException
    {
        if(vrpersistence.find(iddVendedor) == null)
        { throw new BusinessLogicException("El vendedor ya no existe"); }
        vrpersistence.delete(iddVendedor); 
    }
}
