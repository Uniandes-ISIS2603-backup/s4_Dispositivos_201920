/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.ejb;

import co.edu.uniandes.csw.dispositivos.entities.MarcaEntity;
import co.edu.uniandes.csw.dispositivos.entities.DispositivoEntity;
import co.edu.uniandes.csw.dispositivos.persistence.DispositivoPersistence;
import co.edu.uniandes.csw.dispositivos.persistence.MarcaPersistence;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Santiago Fajardo
 */
@Stateless
public class DispositivoMarcaLogic {

    private static final Logger LOGGER = Logger.getLogger(DispositivoMarcaLogic.class.getName());

    @Inject
    private DispositivoPersistence dispositivoPersistence;

    @Inject
    private MarcaPersistence marcaPersistence;

    /**
     *
     * @param dispositivoId
     * @param marcaId
     * @return
     */
    public DispositivoEntity replaceMarca(Long dispositivoId, Long marcaId) {

        LOGGER.log(Level.INFO, "Inicia proceso de actualizar dispositivo con id = {0}", dispositivoId);
        MarcaEntity marcaEntity = marcaPersistence.find(marcaId);
        DispositivoEntity dispositivoEntity = dispositivoPersistence.find(dispositivoId);
        dispositivoEntity.setMarca(marcaEntity);
        LOGGER.log(Level.INFO, "Finaliza proceso de actualizar dispositivo con id = {0}", dispositivoId);

        return dispositivoEntity;
    }

    /**
     *
     * @param dispositivoId
     */
    public void removeMarca(Long dispositivoId) {
        DispositivoEntity dispositivoEntity = dispositivoPersistence.find(dispositivoId);
        MarcaEntity marcaEntity = marcaPersistence.find(dispositivoEntity.getMarca().getId());
        dispositivoEntity.setMarca(null);
        marcaEntity.getDispositivos().remove(dispositivoEntity);
        dispositivoPersistence.update(dispositivoEntity);
    }

}
