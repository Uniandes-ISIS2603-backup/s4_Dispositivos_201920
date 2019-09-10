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
 * 
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
     */
    public AdministradorEntity createAdministrador(AdministradorEntity admin) throws BusinessLogicException
 {
     
     admin = persistence.create(admin);
     return admin;
 }
}
