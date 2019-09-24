/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
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
    private Double celular;    
    private Double cedula;     
    private String usuario;    
    private String contrasena;
    
    /**
    @PodamExclude
    @OneToMany(mappedBy = "vendedor", cascade = CascadeType.PERSIST, orphanRemoval = false)
    private List<VentaEntity> ventas;
    */
    
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
     */
    public VendedorEntity(String correoElectronico, String nombre, String apellido, Double celular, Double cedula, String usuario, String contrasena) 
    {
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
    public Double getCelular() {
        return celular;
    }

    /**
     * 
     * @param celular 
     */
    public void setCelular(Double celular) {
        this.celular = celular;
    }

    /**
     * 
     * @return cedula
     */
    public Double getCedula() {
        return cedula;
    }

    /**
     * 
     * @param cedula 
     */
    public void setCedula(Double cedula) {
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
     * Método no requerido
     * @param eo Objeto a comparar
     * @return Igual al de la superclase
     * @deprecated (Sólo se necesita para mejorar "Code Smell")
     */
    @Override
    @Deprecated
    public boolean equals(Object eo) 
    {
        return super.equals(eo);
    }
    
    /**
     * Método no requerido
     * @return Igual al de la superclase
     * @deprecated (Sólo se necesita para mejorar "Code Smell")
     */
    @Override
    @Deprecated
    public int hashCode()
    {
        return super.hashCode();
    }
}