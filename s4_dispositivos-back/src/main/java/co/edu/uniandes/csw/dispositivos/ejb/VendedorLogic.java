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
    
    public VendedorEntity createVendedor(VendedorEntity vendedor) throws BusinessLogicException
    {
        if(vendedor==null)
        { throw new BusinessLogicException("Algún campo está vacío"); }
        
        vendedor = vrpersistence.create(vendedor); 
        return vendedor; 
    }
    
    public VendedorEntity findVendedor(Long idfVendedor) throws BusinessLogicException
    {
        if(idfVendedor==null)
        { throw new BusinessLogicException("Algo salió mal"); }
        
        VendedorEntity obtainedvr = vrpersistence.find(idfVendedor); 
        return obtainedvr;
    }
    
    public List<VendedorEntity> findAllVendedores() throws BusinessLogicException
    {
        List<VendedorEntity> vrlisted = vrpersistence.findAll(); 
        return vrlisted;
    }
    
    public VendedorEntity updateVendedor(VendedorEntity uvrEntity) throws BusinessLogicException
    {
        if(uvrEntity==null)
        { throw new BusinessLogicException("Algo salió mal"); }
        
        VendedorEntity changedvr = vrpersistence.update(uvrEntity); 
        return changedvr;
    }
    
    public void deleteVendedor(Long iddVendedor) throws BusinessLogicException
    {
        if(iddVendedor==null)
        { throw new BusinessLogicException("Algo salió mal"); }
        
        vrpersistence.delete(iddVendedor); 
    }
}
