/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.ejb;

import co.edu.uniandes.csw.dispositivos.entities.AdministradorEntity;
import co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.dispositivos.persistence.AdministradorPersistence;
import java.util.List;
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
     * Constructor de la clase
     */
    public AdministradorLogic()
    {
        //Clase constructora 
    }
 
    /**
     *Se encarga de crear un administrador en la base de datos
     * @param admin Objeto de AdministadorEntity con los datos nuevos
     * @return Objeto de AmdinistorEntity con los datos nuevos y su ID
     * @throws BusinessLogicException Cuando se cumple una de las reglas de negocio
     *         1. Usuario es nulo
     *         2. La contraseña es nula
     *         3. El correo suministrado no termina en @wireless.com
     *         4. El correo es nulo
     *         5. El correo no ha sido registrado previamente
     *         6. El usuario no existe previamente
     */
    public AdministradorEntity createAdministrador(AdministradorEntity admin) throws BusinessLogicException
    {
     if(admin.getUsuario()==null || admin.getUsuario().trim().equals(""))
         throw new BusinessLogicException("El usuario suministrado es nulo");
     if(admin.getContrasena()==null || admin.getContrasena().trim().equals(""))
         throw new BusinessLogicException("La constraseña suministrada es nula");
     if(admin.getCorreo()==null || admin.getCorreo().trim().equals(""))
         throw new BusinessLogicException("El correo suministrado es nulo");
     if(!admin.getCorreo().endsWith("@wireless.com"))
         throw new BusinessLogicException("El correo ingresado no es corporativo");
     if (persistence.findByUser(admin.getUsuario()) != null)
            throw new BusinessLogicException("Ya existe un Administrador con el usuario \"" + admin.getUsuario()+ "\"");
     if (persistence.findByEmail(admin.getCorreo()) != null)
            throw new BusinessLogicException("Ya existe un Administrador con el correo \"" + admin.getCorreo()+ "\"");
     else
     {
        admin = persistence.create(admin);
        return admin;
     }
    }
    /**
     * Obtiene la lista de los registros de Administrador
     * @return Colección de objetos de AdministradorEntity
     */
    public List<AdministradorEntity> getAdministradores() 
    {
        List<AdministradorEntity> admins = persistence.findAll();
        return admins;
    }
    /**
     * Obtiene los datos de una instancia de Administrador a partir de su ID
     * @param adminId Identificador de la instancia a consultar
     * @return Instancia de Administrador con los datos del Administrador Id consultado.
     */
    public AdministradorEntity getAdministrador(Long adminId) 
    {
        AdministradorEntity facturaEntity = persistence.find(adminId);  
        return facturaEntity;
    }
     /**
     * Actualiza la información de un Administrador
     * @param adminId: id del Administrador para buscarla en la base de datos.
     * @param adminEntity: administrador con los cambios para actualizar.
     * @return el administrador con los cambios actualizados en la base de datos.
     * @throws co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException
     */
     public AdministradorEntity updateAdministrador(Long adminId, AdministradorEntity adminEntity) throws BusinessLogicException {
        if (adminEntity.getUsuario()== null || adminEntity.getUsuario().trim().equals("")) 
            throw new BusinessLogicException("El usuario del administrador está vacío");
        if (adminEntity.getContrasena().trim().equals("") || adminEntity.getContrasena()== null)
            throw new BusinessLogicException("La contraseña del administrador está vacía");
        if (adminEntity.getCorreo().trim().equals("") || adminEntity.getCorreo()== null)
            throw new BusinessLogicException("El correo del administrador está vacío");
        if (persistence.findByEmail(adminEntity.getCorreo()) != null)
            throw new BusinessLogicException("Ya existe una administrador con el mismo correo");
        if (persistence.findByUser(adminEntity.getUsuario()) != null)
            throw new BusinessLogicException("Ya existe una administrador con el mismo usuario");
        AdministradorEntity newEntity = persistence.update(adminEntity);
        return newEntity;
    }
    /**
     * Elimina una instancia de Administrador de la base de datos
     * @param adminId Identificador de la instancia a eliminar
     */
    public void deleteAdministrador(Long adminId)
    {
        persistence.delete(adminId);
    }
}
