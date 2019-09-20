/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.persistence;

import co.edu.uniandes.csw.dispositivos.entities.ClienteEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Carlos Salazar
 */
@Stateless
public class ClientePersistence {

    @PersistenceContext(unitName = "dispositivosPU")
    protected EntityManager em;

    public ClienteEntity create(ClienteEntity clienteEntity) {
        em.persist(clienteEntity);
        return clienteEntity;
    }

    /**
     * Devuelve todas las marcas de la base de datos.
     *
     * @return una lista con todas las marcas que encuentre en la base de datos,
     * "select u from ClienteEntity u" es como un "select * from ClienteEntity;"
     * - "SELECT * FROM table_name" en SQL.
     */
    public List<ClienteEntity> findAll() {
        TypedQuery query = em.createQuery("select u from ClienteEntity u", ClienteEntity.class);
        return query.getResultList();
    }

    /**
     * Busca si hay algun cliente con el id que se envía de argumento
     *
     * @param clienteId: id correspondiente a el cliente buscado.
     * @return una marca.
     */
    public ClienteEntity find(Long clienteId) {
        return em.find(ClienteEntity.class, clienteId);
    }

    /**
     * Actualiza un cliente.
     *
     * @param clienteEntity: el cliente que viene con los nuevos cambios. Por
     * ejemplo el nombre pudo cambiar. En ese caso, se haria uso del método
     * update.
     * @return un client con los cambios aplicados.
     */
    public ClienteEntity update(ClienteEntity clienteEntity) {
        return em.merge(clienteEntity);
    }

    /**
     * Borra un cliente de la base de datos recibiendo como argumento el id del
     * cliente
     *
     * @param clienteId: id correspondiente al cliente a borrar.
     */
    public void delete(Long clienteId) {
        ClienteEntity clienteEntity = em.find(ClienteEntity.class, clienteId);
        em.remove(clienteEntity);
    }

    /**
     * Busca si hay algùn cliente con la cèdula que se envía de argumento
     *
     * @param cedula: cèdula del cliente que se está buscando
     * @return null si no existe ningùn cliente con el código del parametro. Si
     * existe alguna devuelve la primera.
     */
    public ClienteEntity findByCedula(Double cedula) {
        TypedQuery query = em.createQuery("Select c From ClienteEntity c where c.cedula = :cedula", ClienteEntity.class);
        query = query.setParameter("cedula", cedula);
        List<ClienteEntity> sameCode = query.getResultList();
        ClienteEntity result;
        if (sameCode == null) {
            result = null;
        } else if (sameCode.isEmpty()) {
            result = null;
        } else {
            result = sameCode.get(0);
        }
        return result;
    }

    /**
     * Busca si hay algùn cliente con el email que se envía de argumento
     *
     * @param email: email del cliente que se está buscando
     * @return null si no existe ningùn cliente con el código del parametro. Si
     * existe alguna devuelve la primera.
     */
    public ClienteEntity findByEmail(String email) {
        TypedQuery query = em.createQuery("Select c From ClienteEntity c where c.correoElectronico = :email", ClienteEntity.class);
        query = query.setParameter("email", email);
        List<ClienteEntity> sameCode = query.getResultList();
        ClienteEntity result;
        if (sameCode == null) {
            result = null;
        } else if (sameCode.isEmpty()) {
            result = null;
        } else {
            result = sameCode.get(0);
        }
        return result;
    }

    /**
     * Busca si hay algùn cliente con el usuario que se envía de argumento
     *
     * @param usuario: usuario del cliente que se está buscando
     * @return null si no existe ningùn cliente con el código del parametro. Si
     * existe alguna devuelve la primera.
     */
    public ClienteEntity findByUsuario(String usuario) {
        TypedQuery query = em.createQuery("Select c From ClienteEntity c where c.usuario = :usuario", ClienteEntity.class);
        query = query.setParameter("usuario", usuario);
        List<ClienteEntity> sameCode = query.getResultList();
        ClienteEntity result;
        if (sameCode == null) {
            result = null;
        } else if (sameCode.isEmpty()) {
            result = null;
        } else {
            result = sameCode.get(0);
        }
        return result;
    }
}
