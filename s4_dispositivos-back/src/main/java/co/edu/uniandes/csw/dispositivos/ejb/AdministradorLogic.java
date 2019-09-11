/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.ejb;

import co.edu.uniandes.csw.dispositivos.entities.AdministradorEntity;
import co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.dispositivos.persistence.AdministradorPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que evalua reglas de negocio para Administrador
 * @author Dianis Caro
 */
@Stateless
public class AdministradorLogic 
{
 @Inject
 private AdministradorPersistence persistence;
 
    /**
     *
     * @param admin
     * @return
     * @throws BusinessLogicException
     */
    public AdministradorEntity createAdministrador(AdministradorEntity admin) throws BusinessLogicException
 {
     if(admin.getUsuario()==null)
         throw new BusinessLogicException("El usuario suministrado es nulo");
     if(admin.getContrasena()==null)
         throw new BusinessLogicException("La constraseña suministrada es nula");
     if(admin.getCorreo().endsWith("@wireless.com"))
         throw new BusinessLogicException("El correo ingresado no es corporativo");
     else
     {
        admin = persistence.create(admin);
        return admin;
     }
 }
}
