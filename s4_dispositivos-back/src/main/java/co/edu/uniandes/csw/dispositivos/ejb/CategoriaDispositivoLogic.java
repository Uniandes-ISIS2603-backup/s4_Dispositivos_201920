/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.ejb;

import co.edu.uniandes.csw.dispositivos.entities.CategoriaEntity;
import co.edu.uniandes.csw.dispositivos.entities.DispositivoEntity;
import co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.dispositivos.persistence.CategoriaPersistence;
import co.edu.uniandes.csw.dispositivos.persistence.DispositivoPersistence;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author JuanL
 */
@Stateless
public class CategoriaDispositivoLogic {

    private static final Logger LOGGER = Logger.getLogger(DispositivoCategoriaLogic.class.getName());

    @Inject
    private DispositivoPersistence dispositivoPersistence;

    @Inject
    private CategoriaPersistence categoriaPersistence;

    /**
     * Agregar un Dispositivo a la Categoria.
     *
     * @param dispositivoId El id dispositivo a guardar
     * @param CategoriaId El id de la categoria en la cual se va a guardar el
     * dispositivo.
     * @return La categoria creado.
     */
    public DispositivoEntity addDispositivo(Long dispositivoId, Long CategoriaId) {
        LOGGER.log(Level.INFO, "Inicia proceso de agregarle un dispositivo a la categoria con id = {0}", CategoriaId);
        CategoriaEntity categoriaEntity = categoriaPersistence.find(CategoriaId);
        DispositivoEntity dispositivoEntity = dispositivoPersistence.find(dispositivoId);
        dispositivoEntity.setCategoria(categoriaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de agregarle una categoria a un dispositivo con id = {0}", CategoriaId);
        return dispositivoEntity;
    }

    /**
     * Retorna todos los dispositivos asociados a una categoria
     *
     * @param categoriaId El ID de la categoria buscada
     * @return La lista de dispositivos de la categoria
     */
    public List<DispositivoEntity> getDispositivos(Long categoriaId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los dispositivos asociados a la categoria con id = {0}", categoriaId);
        return categoriaPersistence.find(categoriaId).getDispositivos();
    }

    /**
     * Retorna un dispositivo asociado a una categoria
     *
     * @param categoriaId El id de la categoria a buscar.
     * @param dispositivoId El id del dispositivo a buscar
     * @return El dispositivo encontrado dentro de la categoria.
     * @throws BusinessLogicException Si el dispositivo no se encuentra en la
     * categoria
     */
    public DispositivoEntity getDispositivo(Long categoriaId, Long dispositivoId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el dispositivo con id = {0} de la categoria con id = " + categoriaId, dispositivoId);
        List<DispositivoEntity> dispositivos = categoriaPersistence.find(categoriaId).getDispositivos();
        DispositivoEntity dispositivoEntity = dispositivoPersistence.find(dispositivoId);
        int index = dispositivos.indexOf(dispositivoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar el dispositivo con id = {0} de la categoria con id = " + categoriaId, dispositivoId);
        if (index >= 0) {
            return dispositivos.get(index);
        }
        throw new BusinessLogicException("El dispositivo no está asociado a la categoria");
    }

    /**
     * Remplazar dispositivos de una categoria
     *
     * @param dispositivos Lista de dispositivos que serán los de la categoria.
     * @param categoriaId El id de la categoria que se quiere actualizar.
     * @return La lista de dispositivos actualizada.
     */
    public List<DispositivoEntity> replaceDispositivos(Long categoriaId, List<DispositivoEntity> dispositivos) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la categoria con id = {0}", categoriaId);
        CategoriaEntity categoriaEntity = categoriaPersistence.find(categoriaId);
        List<DispositivoEntity> dispoList = dispositivoPersistence.findAll();
        for (DispositivoEntity dispostivo : dispoList) {
            if (dispositivos.contains(dispostivo)) {
                dispostivo.setCategoria(categoriaEntity);
            } else if (dispostivo.getCategoria() != null && dispostivo.getCategoria().equals(categoriaEntity)) {
                dispostivo.setCategoria(null);
            }
        }
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la categoria con id = {0}", categoriaId);
        return dispositivos;
    }
}
