/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.ejb;

import co.edu.uniandes.csw.dispositivos.entities.ClienteEntity;
import co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.dispositivos.persistence.ClientePersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 *
 * @author Carlos Salazar
 */
@Stateless
public class ClienteLogic {

    @Inject
    private ClientePersistence cp;

    /**
     * Crea un cliente
     *
     * @param cliente La entidad cliente a crear.
     * @return La entidad cliente luego de crearla
     * @throws BusinessLogicException <br>
     * Si el apelldo del cliente està vacìo. <br>
     * Si el nombre del cliente está vacío <br>
     * Si el correo del cliente está vacío <br>
     * Si la dirección del cliente está vacía <br>
     * Si el usuario del cliente está vacío <br>
     * Si la contraseña del cliente está vacía <br>
     * Si la cédula del cliente es menor o igual a 0 o es igual a null <br>
     * Si ya existe un cliente con la misma cédula.<br>
     * Si ya existe un cliente con el mismo email.<br>
     * Si ya existe un cliente con el mismo usuario.
     */
    public ClienteEntity createCliente(ClienteEntity cliente) throws BusinessLogicException {
        if (cliente.getApellido() == null || cliente.getApellido().trim().equals("")) {
            throw new BusinessLogicException("El apellido del cliente está vacío");
        } else if (cliente.getNombre() == null || cliente.getNombre().trim().equals("")) {
            throw new BusinessLogicException("El nombre del cliente está vacío");
        } else if (cliente.getCorreoElectronico() == null || cliente.getCorreoElectronico().trim().equals("") || !validarEmail(cliente.getCorreoElectronico())) {
            throw new BusinessLogicException("El correo del cliente está vacío");
        } else if (cliente.getDireccion() == null || cliente.getDireccion().trim().equals("")) {
            throw new BusinessLogicException("La dirección del cliente está vacía");
        } else if (cliente.getUsuario() == null || cliente.getUsuario().trim().equals("")) {
            throw new BusinessLogicException("El usuario del cliente está vacío");
        } else if (cliente.getContrasena() == null || cliente.getContrasena().trim().equals("")) {
            throw new BusinessLogicException("La contraseña del cliente está vacía");
        } else if (cliente.getCedula() == null || cliente.getCedula() <= 0.0) {
            throw new BusinessLogicException("La cédula del cliente es menor o igual a 0 o es igual a null");
        } else if (cp.findByCedula(cliente.getCedula()) != null) {
            throw new BusinessLogicException("Ya existe un cliente con la misma cédula");
        } else if (cp.findByEmail(cliente.getCorreoElectronico()) != null) {
            throw new BusinessLogicException("Ya existe un cliente con el mismo email");
        } else if (cp.findByUsuario(cliente.getUsuario()) != null) {
            throw new BusinessLogicException("Ya existe un cliente con el mismo usuario");
        }
        cliente = cp.create(cliente);
        return cliente;
    }

    /**
     * Dice si el email es válido.
     *
     * @return Lista de entidades de tipo cliente.
     * @param email email a validar.
     */
    public static boolean validarEmail(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    /**
     * Devuelve todos los clientes que hay en la base de datos.
     *
     * @return Lista de entidades de tipo cliente.
     */
    public List<ClienteEntity> getClientes() {
        List<ClienteEntity> clientes = cp.findAll();
        return clientes;
    }

    /**
     * Busca un cliente por ID
     *
     * @param clienteId El id del cliente a buscar
     * @return El cliente encontrado, null si no lo encuentra.
     */
    public ClienteEntity getCliente(Long clienteId) {
        ClienteEntity clienteEntity = cp.find(clienteId);
        return clienteEntity;
    }

    /**
     * Actualizar un cliente por ID
     *
     * @param clienteId El ID del cliente a actualizar
     * @param clienteEntity La entidad del cliente con los cambios deseados
     * @return La entidad del cliente luego de actualizarla
     * @throws BusinessLogicException <br>
     * Si el apelldo del cliente està vacìo. <br>
     * Si el nombre del cliente está vacío <br>
     * Si el correo del cliente está vacío <br>
     * Si la dirección del cliente está vacía <br>
     * Si el usuario del cliente está vacío <br>
     * Si la contraseña del cliente está vacía <br>
     * Si la cédula del cliente es menor o igual a 0 o es igual a null <br>
     * Si ya existe un cliente con la misma cédula.<br>
     * Si ya existe un cliente con el mismo email.<br>
     * Si ya existe un cliente con el mismo usuario.
     */
    public ClienteEntity updateCliente(Long clienteId, ClienteEntity clienteEntity) throws BusinessLogicException {
        if (clienteEntity.getApellido() == null || clienteEntity.getApellido().trim().equals("")) {
            throw new BusinessLogicException("El apellido del cliente está vacío");
        } else if (clienteEntity.getNombre() == null || clienteEntity.getNombre().trim().equals("")) {
            throw new BusinessLogicException("El nombre del cliente está vacío");
        } else if (clienteEntity.getCorreoElectronico() == null || clienteEntity.getCorreoElectronico().trim().equals("")) {
            throw new BusinessLogicException("El correo del cliente está vacío");
        } else if (clienteEntity.getDireccion() == null || clienteEntity.getDireccion().trim().equals("")) {
            throw new BusinessLogicException("La dirección del cliente está vacía");
        } else if (clienteEntity.getUsuario() == null || clienteEntity.getUsuario().trim().equals("")) {
            throw new BusinessLogicException("El usuario del cliente está vacío");
        } else if (clienteEntity.getContrasena() == null || clienteEntity.getContrasena().trim().equals("")) {
            throw new BusinessLogicException("La contraseña del cliente está vacía");
        } else if (clienteEntity.getCedula() == null || clienteEntity.getCedula() <= 0.0) {
            throw new BusinessLogicException("La cédula del cliente es menor o igual a 0 o es igual a null");
        } else if (cp.findByCedula(clienteEntity.getCedula()) != null) {
            throw new BusinessLogicException("Ya existe un cliente con la misma cédula");
        } else if (cp.findByEmail(clienteEntity.getCorreoElectronico()) != null) {
            throw new BusinessLogicException("Ya existe un cliente con el mismo email");
        } else if (cp.findByUsuario(clienteEntity.getUsuario()) != null) {
            throw new BusinessLogicException("Ya existe un cliente con el mismo usuario");
        }
        ClienteEntity newEntity = cp.update(clienteEntity);
        return newEntity;
    }

    /**
     * Eliminar un cliente por ID
     *
     * @param clienteId El ID del cliente a eliminar
     */
    public void deleteCliente(Long clienteId) {

        cp.delete(clienteId);
    }
}
