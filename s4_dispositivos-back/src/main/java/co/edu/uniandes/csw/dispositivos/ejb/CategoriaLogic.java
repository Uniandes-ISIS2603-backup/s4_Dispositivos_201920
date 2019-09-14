/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.ejb;

import co.edu.uniandes.csw.dispositivos.entities.CategoriaEntity;
import co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.dispositivos.persistence.CategoriaPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Juan L
 */
@Stateless
public class CategoriaLogic {

    private static final Logger LOGGER = Logger.getLogger(CategoriaLogic.class.getName());

    @Inject
    private CategoriaPersistence persistence;

    /**
     * Guarda un nueva categoria.
     *
     * @param categoriaEntity la entidad de tipo categoria que se desea
     * persistir.
     * @return La entidad que se desea persistir.
     * @throws BusinessLogicException Si el noombre es inválido o ya existe en
     * la persistencia.
     */
    public CategoriaEntity createCategoria(CategoriaEntity categoriaEntity) throws BusinessLogicException {

        LOGGER.log(Level.INFO, "Inicia proceso de creación de una categoria.");

        boolean ret = verificaLasReglasNegocioCategoria(categoriaEntity);
        if (ret == true) {
            persistence.create(categoriaEntity);
            LOGGER.log(Level.INFO, "Termina proceso de creación de una categoria.");
        }

        return categoriaEntity;
    }

    /**
     *
     * Obtener todas las categorias existentes en la base de datos.
     *
     * @return una lista de las categorias.
     */
    public List<CategoriaEntity> getCategorias() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las categorias");
        List<CategoriaEntity> categorias = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las categorias.");
        return categorias;
    }

    /**
     *
     * Obtener una categoria por medio de su id.
     *
     * @param pCategoriaId: id de la categoria para ser buscada.
     * @return la categoria solicitada por medio de su id.
     */
    public CategoriaEntity getCategoria(Long pCategoriaId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la categoria con id = {0}", pCategoriaId);
        CategoriaEntity categoriaEntity = persistence.find(pCategoriaId);
        if (categoriaEntity == null) {
            LOGGER.log(Level.SEVERE, "La categoria con el id = {0} no existe", pCategoriaId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar la categoria con id = {0}", pCategoriaId);
        return categoriaEntity;
    }

    /**
     *
     * Actualizar una categoria.
     *
     * @param pCategoriaId: id de la categoria para buscarla en la base de
     * datos.
     * @param categoriaEntity: categoria con los cambios para ser actualizada,
     * por ejemplo el nombre.
     * @return la categoria con los cambios actualizados en la base de datos.
     * Null en el caso de no poder actualizarla.
     */
    public CategoriaEntity updateCategoria(Long pCategoriaId, CategoriaEntity categoriaEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la categoria con id = {0}", pCategoriaId);
        if (persistence.findByName(categoriaEntity.getNombreCategoria()) == null) {
            CategoriaEntity newEntity = persistence.update(categoriaEntity);
            LOGGER.log(Level.INFO, "Termina proceso de actualizar la categoria con id = {0}", categoriaEntity.getId());
            return newEntity;
        } else {
            throw new BusinessLogicException("La categoria no puede ser creada, ya que existe una con el nombre es inválida");
        }
    }

    /**
     * Borrar un categoria
     *
     * @param pCategoriaId: id de la categoria a borrar
     * @throws BusinessLogicException Si la categoria a eliminar.
     */
    public void deleteCategoria(Long pCategoriaId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la categoria con id = {0}", pCategoriaId);
        persistence.delete(pCategoriaId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la categoria con id = {0}", pCategoriaId);
    }

    public boolean verificaLasReglasNegocioCategoria(CategoriaEntity categoriaEntity) throws BusinessLogicException {
        
        if (categoriaEntity.getNombreCategoria().trim().equals("") && categoriaEntity.getNombreCategoria().trim() == null )
        {
            throw  new BusinessLogicException("La cadena no puede ser vacia.");
        }
        if (persistence.findByName(categoriaEntity.getNombreCategoria()) != null) {
            throw new BusinessLogicException("La categoria es inválida, ya que existe una con el nombre.");
        }
        return true;
    }
}
