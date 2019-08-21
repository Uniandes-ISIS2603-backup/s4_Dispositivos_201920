package co.edu.uniandes.csw.dispositivos.persistence;

import co.edu.uniandes.csw.dispositivos.entities.ComprobanteDePagoEntity;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
}

