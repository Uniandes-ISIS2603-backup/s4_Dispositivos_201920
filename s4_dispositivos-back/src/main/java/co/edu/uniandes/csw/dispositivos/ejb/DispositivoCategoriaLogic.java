/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.ejb;

import co.edu.uniandes.csw.dispositivos.entities.CategoriaEntity;
import co.edu.uniandes.csw.dispositivos.entities.DispositivoEntity;
import co.edu.uniandes.csw.dispositivos.persistence.DispositivoPersistence;
import co.edu.uniandes.csw.dispositivos.persistence.CategoriaPersistence;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Santiago Fajardo
 */
@Stateless
public class DispositivoCategoriaLogic {

    private static final Logger LOGGER = Logger.getLogger(DispositivoCategoriaLogic.class.getName());

    @Inject
    private DispositivoPersistence dispositivoPersistence;

    @Inject
    private CategoriaPersistence categoriaPersistence;

    /**
     *
     * @param dispositivoId
     * @param categoriaId
     * @return
     */
    public DispositivoEntity replaceCategoria(Long dispositivoId, Long categoriaId) {

        CategoriaEntity categoriaEntity = categoriaPersistence.find(categoriaId);
        DispositivoEntity dispositivoEntity = dispositivoPersistence.find(dispositivoId);
        dispositivoEntity.setCategoria(categoriaEntity);

        return dispositivoEntity;
    }

    /**
     *
     * @param dispositivoId
     */
    public void removeCategoria(Long dispositivoId) {
        DispositivoEntity dispositivoEntity = dispositivoPersistence.find(dispositivoId);
        CategoriaEntity categoriaEntity = categoriaPersistence.find(dispositivoEntity.getCategoria().getId());
        dispositivoEntity.setCategoria(null);
        categoriaEntity.getDispositivos().remove(dispositivoEntity);
        dispositivoPersistence.update(dispositivoEntity);
    }

}
