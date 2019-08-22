/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.entities;

import javax.persistence.Entity;

/**
 *
 * @author Zharet Bautista Montes
 */
@Entity
public class VendedorEntity extends BaseEntity
{
    private String correoElectronico;
    
    private String nombre;
    
    private String apellido; 
    
    private int celular;
    
    private int cedula; 
    
    private String usuario;
    
    private String contrasena;
    
    public VendedorEntity()
    {   }

    public VendedorEntity(String correoElectronico, String nombre, String apellido, int celular, int cedula, String usuario, String contrasena) 
    {
        this.correoElectronico = correoElectronico;
        this.nombre = nombre;
        this.apellido = apellido;
        this.celular = celular;
        this.cedula = cedula;
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getCelular() {
        return celular;
    }

    public void setCelular(int celular) {
        this.celular = celular;
    }

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}