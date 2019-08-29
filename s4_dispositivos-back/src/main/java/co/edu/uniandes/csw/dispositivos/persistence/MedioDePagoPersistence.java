/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.persistence;

import co.edu.uniandes.csw.dispositivos.entities.MedioDePagoEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.TypedQuery;

/**
 *
 * @author Juan L
 */
@Stateless
public class MedioDePagoPersistence {

    private static final Logger LOGGER = Logger.getLogger(CategoriaPersistence.class.getName());

    @PersistenceContext(unitName = "dispositivosPU")
    protected EntityManager em;

    /**
     * Buscar un medio de pago, con el id asociado que recibe por parámetro.
     *
     * @param medioId El id del metodo de pago que se desea buscar.
     * @return Retorna el medio de pago asociado al id recibido.
     */
    public MedioDePagoEntity find(Long medioId) {
        return em.find(MedioDePagoEntity.class, medioId);
    }

    /**
     * Crea un medio de pago con la informaición recibida de la entidad.
     *
     * @param MedioPagoEntity La entidad que representa el nuevo medio de pago.
     * @return La entidad creada.
     */
    public MedioDePagoEntity create(MedioDePagoEntity MedioPagoEntity) {
        LOGGER.log(Level.INFO, "Creando un medio de pago nuevo");
        em.persist(MedioPagoEntity);
        LOGGER.log(Level.INFO, "Medio de pago creado");
        return MedioPagoEntity;
    }

    /**
     * Actualizar un metodo de pago, con la informacion recibida por parámetro.
     *
     * @param medioPagoEntity El medio de pago que se desea actualizar.
     * @return El medio de pago actualizado.
     */
    public MedioDePagoEntity update(MedioDePagoEntity medioPagoEntity) {
        LOGGER.log(Level.INFO, "Actualizando un medio de pago con id = {0}", medioPagoEntity.getId());
        return em.merge(medioPagoEntity);
    }

    /**
     * Elimina un medio de pago que tenga el id asoiado que recibe por
     * parámetro.
     *
     * @param medioPagoId El id del medio de pago que se desea eliminar.
     */
    public void delete(Long medioPagoId) {
        LOGGER.log(Level.INFO, "Borrando medio de pago con id = {0}", medioPagoId);
        MedioDePagoEntity MedioPEntity = em.find(MedioDePagoEntity.class, medioPagoId);
        em.remove(MedioPEntity);
        LOGGER.log(Level.INFO, "Saliendo de borrar Medio de pago con id = {0}", medioPagoId);
    }

    /**
     * Retorna todos los medios de pago de la base de datos.
     *
     * @return La lista de todos los medios de pago.
     */
    public List<MedioDePagoEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todos los medios de pago");
        TypedQuery query = em.createQuery("select u from MedioDePagoEntity u", MedioDePagoEntity.class);
        return query.getResultList();
    }
}
