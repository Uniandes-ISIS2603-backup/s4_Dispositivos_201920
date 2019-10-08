package co.edu.uniandes.csw.dispositivos.persistence;

import co.edu.uniandes.csw.dispositivos.entities.ComprobanteDePagoEntity;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.logging.Logger;

/**
 *Persistencia de la clase ComprobanteDePagoEntity
 * @author Dianis Caro
 */
@Stateless
public class ComprobanteDePagoPersistence 
{
    /**
     * Elemento principal para acceder a la BD
     */
    @PersistenceContext(unitName = "dispositivosPU")
    protected EntityManager em;
        
    private static final Logger LOGGER = Logger.getLogger(ComprobanteDePagoPersistence.class.getName());

    /**
     * Busca un comprobante de pago por su id
     * @param id llave del comprobante a buscar
     * @return comprobante de pago correspondiente si lo encuentra,
     * de lo contrario null
     */
    public ComprobanteDePagoEntity find(Long clienteId, Long id)
    {
        LOGGER.log(Level.INFO, "Consultando el comprobante de pago con id = {0} del cliente con id = " + clienteId, id);
        TypedQuery<ComprobanteDePagoEntity> q = em.createQuery("select p from ComprobanteDePagoEntity p where (p.cliente.id = :clienteid) and (p.id = :comprobanteId)", ComprobanteDePagoEntity.class);
        q.setParameter("clienteid", clienteId);
        q.setParameter("comprobanteId", id);
        List<ComprobanteDePagoEntity> results = q.getResultList();
        ComprobanteDePagoEntity review = null;
        if (results == null) {
            review = null;
        } else if (results.isEmpty()) {
            review = null;
        } else if (results.size() >= 1) {
            review = results.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar el comprobante de pago con id = {0} del cliente con id =" + clienteId, id);
        return review;    
    }
    /**
     * Persiste un comprobante de pago
     * @param comprobante Comprobante de pago que se quiere persistir
     * @return comprobante de pago con el id generado
     */
    public ComprobanteDePagoEntity create(ComprobanteDePagoEntity comprobante)
    {
        em.persist(comprobante);
        return comprobante;
    }
    /**
     * Retorna todos los comprobantes de pago de BD
     * @return comprobantes de pago de la BD
     */
    public List<ComprobanteDePagoEntity> findAll()
    {
        Query query = em.createQuery("select u from ComprobanteDePagoEntity u");
        return query.getResultList();
    }
    /**
     * Modifica los elementos de un comprobante de pago existente
     * @param comprobanteEntity nuevo comprobante de pago
     * @return nuevo comprobante de pago con el id antiguo
     */
    public ComprobanteDePagoEntity update(ComprobanteDePagoEntity comprobanteEntity) 
    {
        return em.merge(comprobanteEntity);
    }
    /**
     * Elimina un comprobante de pago de la BD
     * @param comprobanteId id del comprobante de pago a eliminar
     */
    public void delete(Long comprobanteId)
    {
        ComprobanteDePagoEntity entity = em.find(ComprobanteDePagoEntity.class, comprobanteId);
        em.remove(entity);
    }
    /**
     * Busca si hay algun ComprobanteDePago con el número de factura que se envía como parámetro
     * @param numFactura Número de factura del ComprobanteDePago que se está buscando
     * @return null si no existe ningun ComprobanteDePago con el número de factura
     * Si existe alguno devuelve el primero.
     */
    public ComprobanteDePagoEntity findByNumFactura(Integer numFactura) 
    {
        TypedQuery query = em.createQuery("Select e From ComprobanteDePagoEntity e where e.numeroDeFactura = :numFac", ComprobanteDePagoEntity.class);
        query = query.setParameter("numFac", numFactura);
        List<ComprobanteDePagoEntity> sameNumFac = query.getResultList();
        ComprobanteDePagoEntity result;
        if (sameNumFac == null) {
            result = null;
        } else if (sameNumFac.isEmpty()) {
            result = null;
        } else {
            result = sameNumFac.get(0);
        }
        return result;
    }
}