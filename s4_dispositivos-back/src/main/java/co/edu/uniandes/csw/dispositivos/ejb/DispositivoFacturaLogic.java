/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.ejb;

import co.edu.uniandes.csw.dispositivos.entities.DispositivoEntity;
import co.edu.uniandes.csw.dispositivos.entities.FacturaEntity;
import co.edu.uniandes.csw.dispositivos.persistence.DispositivoPersistence;
import co.edu.uniandes.csw.dispositivos.persistence.FacturaPersistence;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Santiago Fajardo
 */
@Stateless
public class DispositivoFacturaLogic {

    private static final Logger LOGGER = Logger.getLogger(DispositivoFacturaLogic.class.getName());

    @Inject
    private DispositivoPersistence dispositivoPersistence;

    @Inject
    private FacturaPersistence facturaPersistence;

    /**
     *
     * @param dispositivoId
     * @param facturaId
     * @return
     */
    public DispositivoEntity replaceFactura(Long dispositivoId, Long facturaId) {
        DispositivoEntity dispositivoEntity = dispositivoPersistence.find(dispositivoId);
        //FacturaEntity facturaEntity = facturaPersistence.find(facturaId);
        //dispositivoEntity.setFactura(facturaEntity);
        return dispositivoEntity;
    }

    public void removeFactura(Long dispositivoId) {

        DispositivoEntity dispositivoEntity = dispositivoPersistence.find(dispositivoId);
        //FacturaEntity facturaEntity = facturaPersistence.find(dispositivoEntity.getFactura().getId());
        dispositivoEntity.setFactura(null);
        //facturaEntity.getDispositivos().remove(dispositivoEntity);
    }

}
