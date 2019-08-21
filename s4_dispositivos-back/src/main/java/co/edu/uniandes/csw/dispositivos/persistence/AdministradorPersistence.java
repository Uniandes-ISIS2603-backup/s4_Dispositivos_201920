package co.edu.uniandes.csw.dispositivos.persistence;

import co.edu.uniandes.csw.dispositivos.entities.AdministradorEntity;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *Persistencia de la clase AdminstradorEntity
 */
@Stateless
public class AdministradorPersistence 
{
     /**
     * Elemento principal para acceder a la BD
     */
    @PersistenceContext(unitName = "dispositivosPU")
    protected EntityManager em;
    /**
     * Busca un administrador por su id
     * @param id llave del administrador a buscar
     * @return administrador correspondiente si lo encuentra,
     * de lo contrario null
     */
    public AdministradorEntity find(Long id)
    {
        return em.find(AdministradorEntity.class, id);
    }
    /**
     * Persiste un administrador
     * @param admin Administrador que se quiere persistir
     * @return administador con el id generado
     */
    public AdministradorEntity create(AdministradorEntity admin)
    {
        em.persist(admin);
        return admin;
    }
}
