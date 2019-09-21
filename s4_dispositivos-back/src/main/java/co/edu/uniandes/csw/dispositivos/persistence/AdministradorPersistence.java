package co.edu.uniandes.csw.dispositivos.persistence;

import co.edu.uniandes.csw.dispositivos.entities.AdministradorEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

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
    /**
     * Busca si hay algun administrador con el usuario que se envía como parámetro
     * @param user Usuario del administrador que se está buscando
     * @return null si no existe ningun administrador con el usuario
     * Si existe alguno devuelve el primero.
     */
    public AdministradorEntity findByUser(String user) 
    {
        TypedQuery query = em.createQuery("Select e From AdministradorEntity e where e.usuario = :user", AdministradorEntity.class);
        query = query.setParameter("user", user);
        List<AdministradorEntity> sameUsuario = query.getResultList();
        AdministradorEntity result;
        if (sameUsuario == null) {
            result = null;
        } else if (sameUsuario.isEmpty()) {
            result = null;
        } else {
            result = sameUsuario.get(0);
        }
        return result;
    }
    /**
     * Busca si hay algun administrador con el correo que se envía como parámetro
     * @param correo Correo del administrador que se está buscando
     * @return null si no existe ningun administrador con el correo
     * Si existe alguno devuelve el primero.
     */
    public AdministradorEntity findByEmail(String correo) 
    {
        TypedQuery query = em.createQuery("Select e From AdministradorEntity e where e.correo = :correo", AdministradorEntity.class);
        query = query.setParameter("correo", correo);
        List<AdministradorEntity> sameEmail = query.getResultList();
        AdministradorEntity result;
        if (sameEmail == null) {
            result = null;
        } else if (sameEmail.isEmpty()) {
            result = null;
        } else {
            result = sameEmail.get(0);
        }
        return result;
    }
}
