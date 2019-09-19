/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.dtos;

import co.edu.uniandes.csw.dispositivos.entities.VendedorEntity;
import java.io.Serializable;

/**
 *
 * @author Zharet Bautista Montes
 */
public class VendedorDTO implements Serializable
{
    private Long id; 
    private String correoElectronico;    
    private String nombre;    
    private String apellido;     
    private Double celular;    
    private Double cedula;     
    private String usuario;    
    private String contrasena;
    
    /**
     * Constructor vacío
     */
    public VendedorDTO() 
    {    }
    
    /**
     * Constructor a partir de la entidad
     * @param vrentity
     */
    public VendedorDTO(VendedorEntity vrentity) 
    {  
        if(vrentity != null)
        {
            id = vrentity.getId(); 
            correoElectronico = vrentity.getCorreoElectronico();    
            nombre = vrentity.getNombre();    
            apellido = vrentity.getApellido();     
            celular = vrentity.getCelular();    
            cedula = vrentity.getCedula();     
            usuario = vrentity.getUsuario();    
            contrasena = vrentity.getContrasena();
        }
    }
    
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
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
     * @return the celular
     */
    public double getCelular() {
        return celular;
    }

    /**
     * @param celular the celular to set
     */
    public void setCelular(double celular) {
        this.celular = celular;
    }

    /**
     * @return the cedula
     */
    public double getCedula() {
        return cedula;
    }

    /**
     * @param cedula the cedula to set
     */
    public void setCedula(double cedula) {
        this.cedula = cedula;
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
    
    public VendedorEntity toEntity()
    {
        VendedorEntity vendedor = new VendedorEntity();
        vendedor.setId(id);
        vendedor.setNombre(nombre);
        vendedor.setApellido(apellido);
        vendedor.setCedula(cedula);
        vendedor.setCorreoElectronico(correoElectronico);
        vendedor.setUsuario(usuario);
        vendedor.setContrasena(contrasena);
        vendedor.setCelular(celular);
        return vendedor; 
    }
}
