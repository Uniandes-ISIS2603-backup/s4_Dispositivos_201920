/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.dtos;

import co.edu.uniandes.csw.dispositivos.entities.VendedorEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Zharet Bautista Montes
 */
public class VendedorDTO implements Serializable
{
    /**
     * ID del DTO
     */
    private Long id;
    
    /**
     * Correo electronico del DTO
     */
    private String correoElectronico;

    /**
     * Nombre del DTO
     */    
    private String nombre; 
    
    /**
     * Apellido del DTO
     */
    private String apellido;  
    
    /**
     * Celular del DTO
     */
    private Double celular;   
    
    /**
     * Cedula del DTO
     */
    private Double cedula;    
    
    /**
     * Usuario del DTO
     */
    private String usuario;  
    
    /**
     * Contrasena del DTO
     */
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
     * Retorna el id del DTO
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Asigna el id del DTO
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retorna el correo electronico del DTO
     * @return the correoElectronico
     */
    public String getCorreoElectronico() {
        return correoElectronico;
    }

    /**
     * Asigna el correo electronico del DTO
     * @param correoElectronico the correoElectronico to set
     */
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    /**
     * Retorna el nombre del DTO
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Asigna el nombre del DTO
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Retorna el apellido del DTO
     * @return the apellido
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * Asigna el apellido del DTO
     * @param apellido the apellido to set
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * Retorna el celular del DTO
     * @return the celular
     */
    public Double getCelular() {
        return celular;
    }

    /**
     * Asigna el celular del DTO
     * @param celular the celular to set
     */
    public void setCelular(Double celular) {
        this.celular = celular;
    }

    /**
     * Retorna el cedula del DTO
     * @return the cedula
     */
    public Double getCedula() {
        return cedula;
    }

    /**
     * Asigna el cedula del DTO
     * @param cedula the cedula to set
     */
    public void setCedula(Double cedula) {
        this.cedula = cedula;
    }

    /**
     * Retorna el usuario del DTO
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Asigna el usuario del DTO
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * Retorna el contrasena del DTO
     * @return the contrasena
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * Asigna el contrasena del DTO
     * @param contrasena the contrasena to set
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    
    /**
     * Transforma el DTO en un Entity
     * @return VendedorEntity
     */
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
    
    /**
     * Sobreescritura de la conversión a cadena de caracteres
     */
    @Override
    public String toString() 
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
