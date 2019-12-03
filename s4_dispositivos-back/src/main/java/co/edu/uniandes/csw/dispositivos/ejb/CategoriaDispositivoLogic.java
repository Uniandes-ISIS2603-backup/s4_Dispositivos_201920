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
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author JuanL
 */
@Stateless
public class CategoriaDispositivoLogic {

    @Inject
    private DispositivoPersistence dispositivoPersistence;

    @Inject
    private CategoriaPersistence categoriaPersistence;

    /**
     * Agregar un Dispositivo a la Categoria.
     *
     * @param dispositivoId El id dispositivo a guardar
     * @param categoriaId El id de la categoria en la cual se va a guardar el
     * dispositivo.
     * @return La categoria creado.
     */
    public DispositivoEntity addDispositivo(Long dispositivoId, Long categoriaId) {
        CategoriaEntity categoriaEntity = categoriaPersistence.find(categoriaId);
        DispositivoEntity dispositivoEntity = dispositivoPersistence.find(dispositivoId);
        dispositivoEntity.setCategoria(categoriaEntity);
        return dispositivoEntity;
    }

    /**
     * Retorna todos los dispositivos asociados a una categoria
     *
     * @param categoriaId El ID de la categoria buscada
     * @return La lista de dispositivos de la categoria
     */
    public List<DispositivoEntity> getDispositivos(Long categoriaId) {
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
        List<DispositivoEntity> dispositivos = categoriaPersistence.find(categoriaId).getDispositivos();
        DispositivoEntity dispositivoEntity = dispositivoPersistence.find(dispositivoId);
        int index = dispositivos.indexOf(dispositivoEntity);
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
        CategoriaEntity categoriaEntity = categoriaPersistence.find(categoriaId);
        List<DispositivoEntity> dispoList = dispositivoPersistence.findAll();
        for (DispositivoEntity dispostivo : dispoList) {
            if (dispositivos.contains(dispostivo)) {
                dispostivo.setCategoria(categoriaEntity);
            } else if (dispostivo.getCategoria() != null && dispostivo.getCategoria().equals(categoriaEntity)) {
                dispostivo.setCategoria(null);
            }
        }
        return dispositivos;
    }
}
