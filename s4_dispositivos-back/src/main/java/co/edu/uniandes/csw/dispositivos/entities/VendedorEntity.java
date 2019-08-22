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
    
    /**
     * Constructor vacío
     */
    public VendedorEntity()
    {   }

    /**
     * Constructor con parámetros
     * @param correoElectronico
     * @param nombre
     * @param apellido
     * @param celular
     * @param cedula
     * @param usuario
     * @param contrasena
     * @param ventas 
     */
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
    public int getCelular() {
        return celular;
    }

    /**
     * 
     * @param celular 
     */
    public void setCelular(int celular) {
        this.celular = celular;
    }

    /**
     * 
     * @return cedula
     */
    public int getCedula() {
        return cedula;
    }

    /**
     * 
     * @param cedula 
     */
    public void setCedula(int cedula) {
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

    /**
     * 
     * @return ventas
     */
    public List<VentaEntity> getVentas() 
    {
        return ventas;
    }

    /**
     * 
     * @param ventas 
     */
    public void setVentas(List<VentaEntity> ventas) 
    {
        this.ventas = ventas;
    }        
}