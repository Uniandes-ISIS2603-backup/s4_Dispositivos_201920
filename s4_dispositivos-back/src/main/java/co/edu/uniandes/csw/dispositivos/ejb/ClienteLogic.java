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
        if (validarNoVacioONull(cliente.getApellido())) {
            throw new BusinessLogicException("El apellido del cliente está vacío o es nulo");
        } else if (validarNoVacioONull(cliente.getNombre())) {
            throw new BusinessLogicException("El nombre del cliente está vacío o es nulo");
        } else if (validarNoVacioONull(cliente.getCorreoElectronico())) {
            throw new BusinessLogicException("El correo del cliente está vacío o es nulo");
        } else if (!validarEmail(cliente.getCorreoElectronico())) {
            throw new BusinessLogicException("La dirección del cliente es incorrecta");
        } else if (validarNoVacioONull(cliente.getDireccion())) {
            throw new BusinessLogicException("La dirección del cliente es nula o vacía");
        } else if (validarNoVacioONull(cliente.getUsuario())) {
            throw new BusinessLogicException("El usuario del cliente es nulo o vacío");
        } else if (validarNoVacioONull(cliente.getContrasena())) {
            throw new BusinessLogicException("La contraseña del cliente está vacía");
        } else if (cliente.getCedula() == null || cliente.getCedula().trim().equals("")) {
            throw new BusinessLogicException("La cédula del cliente es vacía");
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
    public boolean validarEmail(String email) {
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
        return cp.findAll();
    }

    /**
     * Busca un cliente por ID
     *
     * @param clienteId El id del cliente a buscar
     * @return El cliente encontrado, null si no lo encuentra.
     */
    public ClienteEntity getCliente(Long clienteId) {
        return cp.find(clienteId);
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
        if (validarNoVacioONull(clienteEntity.getApellido())) {
            throw new BusinessLogicException("El apellido del cliente está vacío o es nulo");
        } else if (validarNoVacioONull(clienteEntity.getNombre())) {
            throw new BusinessLogicException("El nombre del cliente está vacío o es nulo");
        } else if (validarNoVacioONull(clienteEntity.getCorreoElectronico())) {
            throw new BusinessLogicException("El correo del cliente está vacío o es nulo");
        } else if (!validarEmail(clienteEntity.getCorreoElectronico())) {
            throw new BusinessLogicException("La dirección del cliente es incorrecta");
        } else if (validarNoVacioONull(clienteEntity.getDireccion())) {
            throw new BusinessLogicException("La dirección del cliente es nula o vacía");
        } else if (validarNoVacioONull(clienteEntity.getUsuario())) {
            throw new BusinessLogicException("El usuario del cliente es nulo o vacío");
        } else if (validarNoVacioONull(clienteEntity.getContrasena())) {
            throw new BusinessLogicException("La contraseña del cliente está vacía");
        } else if (clienteEntity.getCedula() == null || clienteEntity.getCedula().trim().equals("")) {
            throw new BusinessLogicException("La cédula del cliente está vacía");
        } else if (cp.findByCedula(clienteEntity.getCedula()) != null && !(clienteId.equals(cp.findByCedula(clienteEntity.getCedula()).getId()))) {
            throw new BusinessLogicException("Ya existe un cliente con la misma cédula.");
        } else if (cp.findByEmail(clienteEntity.getCorreoElectronico()) != null && !(clienteId.equals(cp.findByEmail(clienteEntity.getCorreoElectronico()).getId()))) {
            throw new BusinessLogicException("Ya existe un cliente con el mismo email.");
        } else if (cp.findByUsuario(clienteEntity.getUsuario()) != null && !(clienteId.equals(cp.findByUsuario(clienteEntity.getUsuario()).getId()))) {
            throw new BusinessLogicException("Ya existe un cliente con el mismo usuario.");
        }
        return cp.update(clienteEntity);
    }

    /**
     * Valida si un string es vacío o null.
     *
     * @param aValidar String a validar si es vacío o null.
     */
    public boolean validarNoVacioONull(String aValidar) {
        return aValidar == null || aValidar.trim().equals("");
    }

    /**
     * Eliminar un cliente por ID
     *
     * @param clienteId El ID del cliente a eliminar
     */
    public void deleteCliente(Long clienteId) {

        cp.delete(clienteId);
    }
    
    public ClienteEntity getClienteUsuario(String clienteUsuario) {
        return cp.findByUsuario(clienteUsuario);
    }
}
