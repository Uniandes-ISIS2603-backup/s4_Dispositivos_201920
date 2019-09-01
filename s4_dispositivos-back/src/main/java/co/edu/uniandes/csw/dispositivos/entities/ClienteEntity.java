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
 */
@Entity
public class ClienteEntity extends BaseEntity implements Serializable {

    /**
     * Atributo que modela el nombre del cliente.
     */
    private String nombre;

    /**
     * Atributo que modela el apellido del cliente.
     */
    private String apellido;

    /**
     * Atributo que modela el email del cliente.
     */
    private String correoElectronico;

    /**
     * Atributo que modela la cedula del cliente.
     */
    private Double cedula;

    /**
     * Atributo que modela la direcciòn del cliente.
     */
    private String direccion;

    /**
     * Atributo que modela el usuario del cliente.
     */
    private String usuario;
    /**
     * Atributo que modela la contraseña del cliente .
     */
    private String contrasena;

    /**
     * Constructor creado vacio para no tener problemas al implementar
     * Serializable
     */
    public ClienteEntity() {
    }

    /**
     * Crea un nuevo ClienteEntity.
     *
     * @param pNombre nombre a establecer.
     * @param pApellido apellido a establecer.
     * @param pCorreoElectronico correo a establecer.
     * @param pCedula cedula a establecer
     * @param pDireccion dirección a establecer.
     * @param pUsuario usuario a estableer.
     * @param pContrasena contrasena a establecer
     *
     */
    public ClienteEntity(String pNombre, String pApellido, String pCorreoElectronico, Double pCedula, String pDireccion, String pUsuario, String pContrasena) {
        this.nombre = pNombre;
        this.apellido = pApellido;
        this.correoElectronico = pCorreoElectronico;
        this.cedula = pCedula;
        this.direccion = pDireccion;
        this.usuario = pUsuario;
        this.contrasena = pContrasena;
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

}
