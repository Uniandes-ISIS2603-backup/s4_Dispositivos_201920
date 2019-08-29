/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.entities;

import java.io.Serializable;
import javax.persistence.Entity;

/**
 *
 * @author Carlos Salazar
 */@Entity
public class ClienteEntity extends BaseEntity implements Serializable{
    private String nombre;
    private String apellido;
    private String correoElectronico;
    private Double cedula;
    private String direccion;
    private String usuario;
    private String contrasena;
    
    public ClienteEntity()
    {
    }
     
    public ClienteEntity(String pNombre, String pApellido, String pCorreoElectronico, Double pCedula, String pDireccion, String pUsuario, String pContrasena)
    {
     this.nombre=pNombre;
     this.apellido=pApellido;
     this.correoElectronico=pCorreoElectronico;
     this.cedula=pCedula;
     this.direccion=pDireccion;
     this.usuario=pUsuario;
     this.contrasena=pContrasena;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the apellido
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * @param apellido the apellido to set
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * @return the correoElectronico
     */
    public String getCorreoElectronico() {
        return correoElectronico;
    }

    /**
     * @param correoElectronico the correoElectronico to set
     */
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    /**
     * @return the cedula
     */
    public Double getCedula() {
        return cedula;
    }

    /**
     * @param cedula the cedula to set
     */
    public void setCedula(Double cedula) {
        this.cedula = cedula;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the contrasena
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * @param contrasena the contrasena to set
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    @Override
    public boolean equals(Object obj)
    {
        boolean resp = super.equals(this);
        final ClienteEntity other = (ClienteEntity) obj;
        
        if(!resp)
        {
            return false;
        }
        else
        {
            if(this.apellido.equalsIgnoreCase(other.apellido))
                if(this.cedula == other.cedula && this.nombre.equalsIgnoreCase(other.nombre))
                    if(this.correoElectronico.equalsIgnoreCase(other.correoElectronico) && this.direccion.equalsIgnoreCase(other.direccion))
                        if(this.usuario.equalsIgnoreCase(other.usuario) && this.contrasena.equals(other.contrasena))
                            return true;

            return false;
        }
    }
}
