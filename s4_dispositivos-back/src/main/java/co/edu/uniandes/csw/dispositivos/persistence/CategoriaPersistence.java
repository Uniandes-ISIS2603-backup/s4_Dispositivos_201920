/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.persistence;

import co.edu.uniandes.csw.dispositivos.entities.CategoriaEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Estudiante
 */
@Stateless
public class CategoriaPersistence {

    @PersistenceContext(unitName = "dispositivosPU")
    protected EntityManager em;

    /**
     * Crea una categoria con la información recibida en la entidad.
     *
     * @param categoriaEntity La entidad que representa la nueva categoria.
     * @return La entidad creada.
     */
    public CategoriaEntity create(CategoriaEntity categoriaEntity) {
        em.persist(categoriaEntity);
        return categoriaEntity;
    }

    /**
     * Actualizar una categoria, con la que recibe por parametro.
     *
     * @param categoriaEntity La entidad actualizada que se desea guardar.
     * @return La entidad actualizada.
     */
    public CategoriaEntity update(CategoriaEntity categoriaEntity) {
        return em.merge(categoriaEntity);
    }

    /**
     * Eliminar una categoria, con el id asociado que recibe por parámetro.
     *
     * @param categoriaId El id de la categoria que se desea eliminar.
     */
    public void delete(Long categoriaId) {
        CategoriaEntity categoriaEntity = em.find(CategoriaEntity.class, categoriaId);
        em.remove(categoriaEntity);
    }

    /**
     * Buscar una categoria, que tenga el id asociado que recibe por parámetro.
     *
     * @param medioId El id de la categoria que se desea buscar.
     * @return Retorna la categoria con el id asociado.
     */
    public CategoriaEntity find(Long medioId) {
        return em.find(CategoriaEntity.class, medioId);
    }

    /**
     * Retorna todos los medios de pago de la base de datos.
     *
     * @return La lista de todos los medios de pago.
     */
    public List<CategoriaEntity> findAll() {
        TypedQuery query = em.createQuery("select u from CategoriaEntity u", CategoriaEntity.class);
        return query.getResultList();
    }

    /**
     * Busca si hay alguna categoria con el nombre que se envía de argumento
     *
     * @param pNombreCategoria: Nombre de la categoria que se está buscando
     * @return null si no existe ninguna categoria con el nombre del argumento.
     * Si existe alguna devuelve la primera.
     */
    public CategoriaEntity findByName(String pNombreCategoria) {
        TypedQuery query = em.createQuery("Select u From CategoriaEntity u where u.nombreCategoria = :nombreCat", CategoriaEntity.class);
        query = query.setParameter("nombreCat", pNombreCategoria);
        List<CategoriaEntity> sameName = query.getResultList();
        CategoriaEntity result;
        if (sameName == null) {
            result = null;
        } else if (sameName.isEmpty()) {
            result = null;
        } else {
            result = sameName.get(0);
        }
        return result;
    }
    
    
}