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
        if(verifyBlanks(vendedor))
        {  throw new BusinessLogicException("No puede haber ningún campo vacío"); }        
        else if(vrpersistence.findByCedula(vendedor.getCedula()) != null)
        { throw new BusinessLogicException("Ya existe un vendedor con esa cédula"); }        
        else if(vrpersistence.findByUsuario(vendedor.getUsuario()) != null)
        { throw new BusinessLogicException("Ya existe un vendedor con ese usuario"); }        
        else if(vrpersistence.findByEmail(vendedor.getCorreoElectronico()) != null)
        { throw new BusinessLogicException("Ya existe un vendedor con ese correo electrónico"); }       
        vrpersistence.create(vendedor); 
        return vendedor; 
    }
    
    /**
     * Validación del método buscar vendedor
     * @param idfVendedor
     * @return vendedor encontrado 
     */
    public VendedorEntity findVendedor(Long idfVendedor)
    {        
        return  vrpersistence.find(idfVendedor); 
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
        return vrpersistence.findByCedula(cedulaVendedorf);
    }
    
    /**
     * Validación del método buscar vendedor por cédula
     * @param usuarioVendedorf
     * @return vendedor obtenido por su cédula
     * @throws co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException 
     */
    public VendedorEntity findByUsuarioVendedor(String usuarioVendedorf) throws BusinessLogicException
    {
        if(usuarioVendedorf == null)
        { throw new BusinessLogicException("No se recibió ningún usuario"); }
        
        return  vrpersistence.findByUsuario(usuarioVendedorf);
    }
    
    /**
     * Validación del método buscar vendedor por cédula
     * @param emailVendedorf
     * @return vendedor obtenido por su cédula
     * @throws co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException 
     */
    public VendedorEntity findByEmailVendedor(String emailVendedorf) throws BusinessLogicException
    {
        if(emailVendedorf == null)
        { throw new BusinessLogicException("No se recibió ningún correo electrónico"); }
        
        return vrpersistence.findByEmail(emailVendedorf);
    }
    
    /**
     * Validación del método encontrar todos los vendedores
     * @return lista de los vendedores existentes
     */
    public List<VendedorEntity> findAllVendedores()
    { 
        return vrpersistence.findAll();
    }
    
    /**
     * Validación del método cambiar vendedor
     * @param uvrEntity
     * @return vendedor actualizado
     * @throws co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException
     */
    public VendedorEntity updateVendedor(VendedorEntity uvrEntity) throws BusinessLogicException
    {
        if(verifyBlanks(uvrEntity))
        {  throw new BusinessLogicException("No puede haber ningún campo vacío"); }
        else if(vrpersistence.findByCedula(uvrEntity.getCedula()) != null)
        { throw new BusinessLogicException("Ya existe un vendedor con esa cédula"); }        
        else if(vrpersistence.findByUsuario(uvrEntity.getUsuario()) != null)
        { throw new BusinessLogicException("Ya existe un vendedor con ese usuario"); }        
        else if(vrpersistence.findByEmail(uvrEntity.getCorreoElectronico()) != null)
        { throw new BusinessLogicException("Ya existe un vendedor con ese correo electrónico"); }
        return vrpersistence.update(uvrEntity);
    }
    
    /**
     * Validación del método borrar vendedor
     * @param iddVendedor
     * @throws co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException
     */
    public void deleteVendedor(Long iddVendedor) throws BusinessLogicException 
    {
        VendedorEntity nonvendedor = findVendedor(iddVendedor);
        if(nonvendedor == null) throw new BusinessLogicException("No se encuentra el vendedor con id = " + iddVendedor); 
        vrpersistence.delete(iddVendedor); 
    }
    
    /**
     * Método auxiliar para hallar valores nulos
     * @param vendedorB
     * @return false si ningún valor es nulo, true de lo contrario
     */
    public boolean verifyBlanks(VendedorEntity vendedorB)
    {
        return (vendedorB.getNombre() == null || vendedorB.getNombre().trim().equals("")) || 
               (vendedorB.getApellido() == null || vendedorB.getApellido().trim().equals("")) ||
               (vendedorB.getUsuario() == null || vendedorB.getUsuario().trim().equals("")) ||
               (vendedorB.getContrasena() == null || vendedorB.getContrasena().trim().equals("")) ||
               (vendedorB.getCorreoElectronico() == null || vendedorB.getCorreoElectronico().trim().equals("")) ||
               (vendedorB.getCedula() == null || vendedorB.getCelular() == null) || 
               (vendedorB.getCedula() < 0 || vendedorB.getCelular() < 0);
    }
}
