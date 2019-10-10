/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.dtos;

import co.edu.uniandes.csw.dispositivos.entities.ClienteEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Carlos Salazar
 */
public class ClienteDTO implements Serializable {

    /**
     * atributo que modela el id del cliente.
     */
    private Long id;

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
    public ClienteDTO() {
    }
    /**
     * Constructor a partir de la entidad
     * @param cliente La entidad del cliente
     */
    public ClienteDTO(ClienteEntity cliente) {
        if (cliente != null) {
            this.id = cliente.getId();
            this.nombre = cliente.getNombre();
            this.apellido = cliente.getApellido();
            this.correoElectronico = cliente.getCorreoElectronico();
            this.cedula = cliente.getCedula();
            this.direccion = cliente.getDireccion();
            this.usuario = cliente.getUsuario();
            this.contrasena = cliente.getContrasena();
        }
    }

    /**
     * Método para transformar el DTO a una entidad.
     *
     * @return La entidad del cliente asociado.
     */
    public ClienteEntity toEntity() {
        ClienteEntity clienteEntity = new ClienteEntity();
        clienteEntity.setId(this.getId());
        clienteEntity.setNombre(this.getNombre());
        clienteEntity.setApellido(this.getApellido());
        clienteEntity.setCorreoElectronico(this.getCorreoElectronico());
        clienteEntity.setCedula(this.getCedula());
        clienteEntity.setDireccion(this.getDireccion());
        clienteEntity.setUsuario(this.getUsuario());
        clienteEntity.setContrasena(this.getContrasena());

        return clienteEntity;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
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
}
