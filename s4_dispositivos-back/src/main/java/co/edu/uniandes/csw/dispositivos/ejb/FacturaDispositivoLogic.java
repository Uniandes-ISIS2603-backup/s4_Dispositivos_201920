/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.ejb;

import co.edu.uniandes.csw.dispositivos.entities.DispositivoEntity;
import co.edu.uniandes.csw.dispositivos.entities.FacturaEntity;
import co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.dispositivos.persistence.FacturaPersistence;
import co.edu.uniandes.csw.dispositivos.persistence.DispositivoPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Carlos Salazar
 */
@Stateless
public class FacturaDispositivoLogic {

    private static final Logger LOGGER = Logger.getLogger(FacturaDispositivoLogic.class.getName());

    @Inject
    private DispositivoPersistence dispositivoPersistence;

    @Inject
    private FacturaPersistence facturaPersistence;

    /**
     * Agregar un dispositivo a el factura
     *
     * @param dispositivosId El id dispositivo a guardar
     * @param facturasId El id de el factura en la cual se va a guardar el
     * dispositivo.
     * @return El dispositivo creado.
     */
    public DispositivoEntity addDispositivo(Long dispositivosId, Long facturasId, Long clientesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de agregarle un dispositivo a el factura con id = {0}", facturasId);
        FacturaEntity facturaEntity = facturaPersistence.find(clientesId, facturasId);
        DispositivoEntity dispositivoEntity = dispositivoPersistence.find(dispositivosId);
        facturaEntity.getDispositivos().add(dispositivoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de agregarle un dispositivo a el factura con id = {0}", facturasId);
        return dispositivoEntity;
    }

    /**
     * Retorna todos los dispositivos asociadas a una factura
     *
     * @param facturasId El ID de el factura buscada
     * @return La lista de dispositivos de el factura
     */
    public List<DispositivoEntity> getDispositivos(Long facturasId, Long clientesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los dispositivos asociadas a el factura con id = {0}", facturasId);
        return facturaPersistence.find(clientesId, facturasId).getDispositivos();
    }

    /**
     * Retorna un dispositivo asociado a una factura
     *
     * @param facturasId El id de el factura a buscar.
     * @param dispositivosId El id del dispositivo a buscar
     * @return El dispositivo encontrado dentro de el factura.
     * @throws BusinessLogicException Si el dispositivo no se encuentra en el
     * factura
     */
    public DispositivoEntity getDispositivo(Long facturasId, Long dispositivosId, Long clientesId) throws BusinessLogicException {
        List<DispositivoEntity> dispositivo = facturaPersistence.find(clientesId, facturasId).getDispositivos();
        DispositivoEntity dispositivoEntity = dispositivoPersistence.find(facturasId, dispositivosId,clientesId);
        int index = dispositivo.indexOf(dispositivoEntity);
        if (index >= 0) {
            return dispositivo.get(index);
        }
        throw new BusinessLogicException("La dispositivo no está asociada a el factura");
    }
}
