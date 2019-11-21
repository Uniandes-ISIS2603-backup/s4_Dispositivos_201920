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
    /**
     * Atributo correo electrónico
     */
    private String correoElectronico;

    /**
     * Atributo nombre
     */
    private String nombre;

    /**
     * Atributo apellido
     */
    private String apellido;
    
    /**
     * Atributo celular
     */
    private Double celular;

    /**
     * Atributo cedula
     */
    private Double cedula;
    
    /**
     * Atributo usuario
     */
    private String usuario; 
    
    /**
     * Atributo contraseña
     */
    private String contrasena;
    
    /**
     * Asociación con las ventas que realizó el vendedor
     */
    @PodamExclude
    @OneToMany(mappedBy = "vendedor", cascade = CascadeType.PERSIST, orphanRemoval = false)
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
    public VendedorEntity(String correoElectronico, String nombre, String apellido, Double celular, Double cedula, String usuario, String contrasena, List<VentaEntity> ventas) 
    {
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
     * Retorna el correo electrónico del vendedor
     * @return correoElectronico
     */
    public String getCorreoElectronico() {
        return correoElectronico;
    }

    /**
     * Asigna el correo electrónico del vendedor
     * @param correoElectronico 
     */
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    /**
     * Retorna el nombre del vendedor
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Retorna el apellido del vendedor
     * @return apellido
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * Retorna el celular del vendedor
     * @return celular
     */
    public Double getCelular() {
        return celular;
    }

    /**
     * Asigna el celular del vendedor
     * @param celular 
     */
    public void setCelular(Double celular) {
        this.celular = celular;
    }
    
    /**
     * Asigna el nombre del vendedor
     * @param nombre 
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Asigna el celular del vendedor
     * @param cedula 
     */
    public void setCedula(Double cedula) {
        this.cedula = cedula;
    }

    /**
     * Retorna el usuario del vendedor
     * @return usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Retorna la cedula del vendedor
     * @return cedula
     */
    public Double getCedula() {
        return cedula;
    }    
    
    /**
     * Asigna el usuario del vendedor
     * @param usuario 
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * Asigna el apellido del vendedor
     * @param apellido 
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    
    /**
     * Retorna la contraseña del vendedor
     * @return contrasena
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * Asigna la contraseña del vendedor
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

    /**
     * Retorna la lista de ventas del vendedor
     * @return the ventas
     */
    public List<VentaEntity> getVentas() {
        return ventas;
    }

    /**
     * Asigna la lista de ventas del vendedor
     * @param ventas the ventas to set
     */
    public void setVentas(List<VentaEntity> ventas) {
        this.ventas = ventas;
    }
}