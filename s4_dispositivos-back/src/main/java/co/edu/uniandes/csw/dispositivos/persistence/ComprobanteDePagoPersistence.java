package co.edu.uniandes.csw.dispositivos.persistence;

import co.edu.uniandes.csw.dispositivos.entities.ComprobanteDePagoEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *Persistencia de la clase ComprobanteDePagoEntity
 */
@Stateless
public class ComprobanteDePagoPersistence 
{
    /**
     * Elemento principal para acceder a la BD
     */
    @PersistenceContext(unitName = "dispositivosPU")
    protected EntityManager em;
    /**
     * Busca un comprobante de pago por su id
     * @param id llave del comprobante a buscar
     * @return comprobante de pago correspondiente si lo encuentra,
     * de lo contrario null
     */
    public ComprobanteDePagoEntity find(Long id)
    {
        return em.find(ComprobanteDePagoEntity.class, id);
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