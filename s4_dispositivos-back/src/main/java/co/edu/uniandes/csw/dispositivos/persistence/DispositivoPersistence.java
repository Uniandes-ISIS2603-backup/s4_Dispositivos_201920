/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.persistence;

import co.edu.uniandes.csw.dispositivos.entities.DispositivoEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Santiago Fajardo
 */
@Stateless
public class DispositivoPersistence {

    @PersistenceContext(unitName = "dispositivosPU")
    protected EntityManager em;

    /**
     * Método para crear la entidad
     *
     * @param dispositivo tipo de entidad que se quiere persistir
     * @return
     */
    public DispositivoEntity create(DispositivoEntity dispositivo) {
        em.persist(dispositivo);
        return dispositivo;
    }

    public DispositivoEntity find(Long id) {
        return em.find(DispositivoEntity.class, id);
    }

    public List<DispositivoEntity> findAll() {

        Query query = em.createQuery("select u from DispositivoEntity u");
        return query.getResultList();
    }

    public DispositivoEntity update(DispositivoEntity dispositivoEntity) {
        return em.merge(dispositivoEntity);
    }

    public void delete(Long dispositivoId) {
        DispositivoEntity find = em.find(DispositivoEntity.class, dispositivoId);
        em.remove(find);
    }

    public boolean containsDispositivo(DispositivoEntity dispositivo) {
        List<DispositivoEntity> all = this.findAll();
        boolean existe = false;
        if (all.size() > 0) {
            for (DispositivoEntity dis : all) {
                if (dis.getNombre().equals(dispositivo.getNombre())) {
                    existe = true;
                }
            }
        }
        return existe;
    }

    /**
     * Busca si hay alguna dispositivo con el id que se envía de argumento
     *
     * @param dispositivoId: id correspondiente a la dispositivo buscada.
     * @return una dispositivo.
     */
    /**
     * Busca un comprobante de pago por su id
     *
     * @param id llave del comprobante a buscar
     * @param facturaId id del factura.
     * @return comprobante de pago correspondiente si lo encuentra, de lo
     * contrario null
     */
    public DispositivoEntity find(Long facturaId, Long id) {
        TypedQuery<DispositivoEntity> q = em.createQuery("select p from DispositivoEntity p where (p.factura.id = :facturaid) and (p.id = :dispositivoId)", DispositivoEntity.class);
        q.setParameter("facturaid", facturaId);
        q.setParameter("dispositivoId", id);
        List<DispositivoEntity> results = q.getResultList();
        DispositivoEntity dispositivo = null;
        if (results == null) {
            dispositivo = null;
        } else if (results.isEmpty()) {
            dispositivo = null;
        } else if (results.size() >= 1) {
            dispositivo = results.get(0);
        }
        return dispositivo;
    }

}
