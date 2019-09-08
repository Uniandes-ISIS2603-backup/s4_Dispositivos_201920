package co.edu.uniandes.csw.dispositivos.persistence;

import co.edu.uniandes.csw.dispositivos.entities.AdministradorEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *Persistencia de la clase AdminstradorEntity
 * @author Dianis Caro
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
    /**
     * Retorna todos los administradores de la BD
     * @return todos los administradores de BD
     */
    public List<AdministradorEntity> findAll()
    {
        Query query = em.createQuery("select u from AdministradorEntity u");
        return query.getResultList();
    }
    /**
     * Modifica los elementos de un administrador existente
     * @param adminEntity nuevo administrador
     * @return nuevo administrador con el id antiguo
     */
    public AdministradorEntity update(AdministradorEntity adminEntity) 
    {
        return em.merge(adminEntity);
    }
    /**
     * Elimina un administrador de la BD
     * @param adminID id del administrador a borrar
     */
    public void delete(Long adminID)
    {
        AdministradorEntity entity = em.find(AdministradorEntity.class, adminID);
        em.remove(entity);
    }
}
