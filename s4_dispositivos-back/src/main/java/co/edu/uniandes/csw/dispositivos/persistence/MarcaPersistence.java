/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.persistence;

import co.edu.uniandes.csw.dispositivos.entities.MarcaEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Carlos Salazar
 */
@Stateless
public class MarcaPersistence {

    private static final Logger LOGGER = Logger.getLogger(MarcaPersistence.class.getName());

    @PersistenceContext(unitName = "dispositivosPU")
    protected EntityManager em;

    public MarcaEntity create(MarcaEntity marcaEntity) {
        em.persist(marcaEntity);
        return marcaEntity;
    }

    /**
     * Devuelve todas las marcas de la base de datos.
     *
     * @return una lista con todas las marcas que encuentre en la base de datos,
     * "select u from MarcaEntity u" es como un "select * from MarcaEntity;" -
     * "SELECT * FROM table_name" en SQL.
     */
    public List<MarcaEntity> findAll() {
        TypedQuery query = em.createQuery("select u from MarcaEntity u", MarcaEntity.class);
        return query.getResultList();
    }

    /**
     * Busca si hay alguna marca con el id que se envía de argumento
     *
     * @param marcaId: id correspondiente a la marca buscada.
     * @return una marca.
     */
    public MarcaEntity find(Long marcaId) {
        return em.find(MarcaEntity.class, marcaId);
    }

    /**
     * Actualiza una marca.
     *
     * @param marcaEntity: la marca que viene con los nuevos cambios. Por
     * ejemplo el nombre pudo cambiar. En ese caso, se haria uso del método
     * update.
     * @return una marca con los cambios aplicados.
     */
    public MarcaEntity update(MarcaEntity marcaEntity) {
        return em.merge(marcaEntity);
    }

    /**
     * Borra una marca de la base de datos recibiendo como argumento el id de la
     * marca
     *
     * @param marcaId: id correspondiente a la marca a borrar.
     */
    public void delete(Long marcaId) {
        //LOGGER.log(Level.INFO, "Borrando la marca con id={0}", marcaId);
        MarcaEntity marcaEntity = em.find(MarcaEntity.class, marcaId);
        em.remove(marcaEntity);
    }
}
