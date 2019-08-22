/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Zharet Bautista Montes
 */
@Entity
public class VendedorEntity extends BaseEntity implements Serializable
{
    private String correoElectronico;    
    private String nombre;    
    private String apellido;     
    private int celular;    
    private int cedula;     
    private String usuario;    
    private String contrasena;
    
    @PodamExclude
    @OneToMany
    private List<VentaEntity> ventas;
    
    public VendedorEntity()
    {   }

    public VendedorEntity(String correoElectronico, String nombre, String apellido, int celular, int cedula, String usuario, String contrasena, List<VentaEntity> ventas) {
        this.correoElectronico = correoElectronico;
        this.nombre = nombre;
        this.apellido = apellido;
        this.celular = celular;
        this.cedula = cedula;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.ventas = ventas;
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

    public List<VentaEntity> getVentas() 
    {
        return ventas;
    }

    public void setVentas(List<VentaEntity> ventas) 
    {
        this.ventas = ventas;
    }        
}