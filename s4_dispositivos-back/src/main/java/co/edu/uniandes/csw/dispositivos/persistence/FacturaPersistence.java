/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.persistence;

import co.edu.uniandes.csw.dispositivos.entities.FacturaEntity;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author ca.salazara
 */
@Stateless
public class FacturaPersistence {

    private static final Logger LOGGER = Logger.getLogger(FacturaPersistence.class.getName());

    @PersistenceContext(unitName = "dispositivosPU")
    protected EntityManager em;

    public FacturaEntity create(FacturaEntity facturaEntity) {
        em.persist(facturaEntity);
        return facturaEntity;
    }

    /**
     * Devuelve todas las facturas de la base de datos.
     *
     * @return una lista con todas las facturas que encuentre en la base de datos
     */
    public List<FacturaEntity> findAll() {
        TypedQuery query = em.createQuery("select u from FacturaEntity u", FacturaEntity.class);
        return query.getResultList();
    }

    /**
     * Busca si hay alguna factura con el id que se envía de argumento
     *
     * @param facturaId: id correspondiente a la factura buscada.
     * @return una factura.
     */
    public FacturaEntity find(Long facturaId) {
        return em.find(FacturaEntity.class, facturaId);
    }

    /**
     * Actualiza una facturas.
     *
     * @param facturaEntity: la factura que viene con los nuevos cambios. Por
     * ejemplo el nombre pudo cambiar. En ese caso, se haria uso del método
     * update.
     * @return una factura con los cambios aplicados.
     */
    public FacturaEntity update(FacturaEntity facturaEntity) {
        return em.merge(facturaEntity);
    }

    /**
     * Borra una factura de la base de datos recibiendo como argumento el id de la
     * factura
     *
     * @param facturaId: id correspondiente a la factura a borrar.
     */
    public void delete(Long facturaId) {
        //LOGGER.log(Level.INFO, "Borrando la factura con id={0}", facturaId);
        FacturaEntity facturaEntity = em.find(FacturaEntity.class, facturaId);
        em.remove(facturaEntity);
    }
}
