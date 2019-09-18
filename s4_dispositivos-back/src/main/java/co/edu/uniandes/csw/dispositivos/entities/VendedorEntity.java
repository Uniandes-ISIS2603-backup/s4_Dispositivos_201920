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
 * @author Zharet Bautista Montes
 */
@Entity
public class VendedorEntity extends BaseEntity implements Serializable {

    private String correoElectronico;
    private String nombre;
    private String apellido;
    private double celular;
    private double cedula;
    private String usuario;
    private String contrasena;

    /**
     * Constructor vacío
     */
    public VendedorEntity() 
    {    }

    /**
     * Constructor con parámetros
     *
     * @param correoElectronico
     * @param nombre
     * @param apellido
     * @param celular
     * @param cedula
     * @param usuario
     * @param contrasena
     */
    public VendedorEntity(String correoElectronico, String nombre, String apellido, double celular, double cedula, String usuario, String contrasena) {
        this.correoElectronico = correoElectronico;
        this.nombre = nombre;
        this.apellido = apellido;
        this.celular = celular;
        this.cedula = cedula;
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    /**
     *
     * @return correoElectronico
     */
    public String getCorreoElectronico() {
        return correoElectronico;
    }

    /**
     *
     * @param correoElectronico
     */
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    /**
     *
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     *
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     *
     * @return apellido
     */
    public String getApellido() {
        return apellido;
    }

    /**
     *
     * @param apellido
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     *
     * @return celular
     */
    public double getCelular() {
        return celular;
    }

    /**
     *
     * @param celular
     */
    public void setCelular(double celular) {
        this.celular = celular;
    }

    /**
     *
     * @return cedula
     */
    public double getCedula() {
        return cedula;
    }

    /**
     *
     * @param cedula
     */
    public void setCedula(double cedula) {
        this.cedula = cedula;
    }

    /**
     *
     * @return usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     *
     * @param usuario
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     *
     * @return contrasena
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     *
     * @param contrasena
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }   
}
