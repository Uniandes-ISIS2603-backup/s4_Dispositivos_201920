/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.dtos;

import co.edu.uniandes.csw.dispositivos.entities.AdministradorEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Objeto de transferencia de datos de Administrador. Los DTO co    ntienen
 * las representaciones de los JSON que se transfieren entre el cliente y el
 * servidor.
 * @author Dianis Caro
 */
public class AdministradorDTO implements Serializable
{
    /**
     * Identificador del administrador
     */
    private Long id;
    /**
     * Usuario de identificacion en la plataforma
     */
    private String usuario;
    /**
     * ContraseNa de verificacion en la plataforma
     */
    private String contrasena;
    /**
     * Correo institucional
     */
    private String correo;
    /**
     * Constructor de la clase
     */
    public AdministradorDTO()
    { 
    }
    /**
     * Constructor de la clase
     * @param admin Entidad del administrador a crear
     */
    public AdministradorDTO(AdministradorEntity admin)
    { 
        if(admin != null)
        {
            this.id = admin.getId();
            this.usuario = admin.getUsuario();
            this.contrasena = admin.getContrasena();
            this.correo = admin.getCorreo();
        }
    }
    /**
     * Método para transformar el DTO a una entidad
     * @return La entidad del administrador asociado
     */
    public AdministradorEntity toEntity()
    {
        AdministradorEntity adminEntity = new AdministradorEntity();
        adminEntity.setId(this.id);
        adminEntity.setUsuario(this.usuario);
        adminEntity.setContrasena(this.contrasena);
        adminEntity.setCorreo(this.correo);

        return adminEntity;
    }
    /**
     * @return identificador del administrador
     */
    public Long getId() {
        return id;
    }
    /**
     * Modifica el id del administrador
     * @param id identificador nuevo
     */
    public void setId(Long id) {
        this.id = id;
    }
    /**
     * @return usuario del administrador
     */
    public String getUsuario() {
        return usuario;
    }
    /**
     * Modifica el usuario del administrador
     * @param usuario nuevo usuario
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    /**
     * @return contraseña del administrador
     */
    public String getContrasena() {
        return contrasena;
    }
    /**
     * Modifica la contraseña del administrador
     * @param contrasena nueva contraseña
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    /**
     * @return correo del administrador
     */
    public String getCorreo() {
        return correo;
    }
    /**
     * Modifica el correo del administrador
     * @param correo nuevo correo
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
