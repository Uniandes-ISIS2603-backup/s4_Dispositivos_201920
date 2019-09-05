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
}
