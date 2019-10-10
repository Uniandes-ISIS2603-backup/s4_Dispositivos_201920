/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.ejb;

import co.edu.uniandes.csw.dispositivos.entities.ComprobanteDePagoEntity;
import co.edu.uniandes.csw.dispositivos.entities.ClienteEntity;
import co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.dispositivos.persistence.ClientePersistence;
import co.edu.uniandes.csw.dispositivos.persistence.ComprobanteDePagoPersistence;
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
public class ClienteComprobanteLogic {

    private static final Logger LOGGER = Logger.getLogger(ClienteComprobanteLogic.class.getName());

    @Inject
    private ComprobanteDePagoPersistence comprobantePersistence;

    @Inject
    private ClientePersistence clientePersistence;

    /**
     * Agregar un comprobante a el cliente
     *
     * @param comprobantesId El id comprobante a guardar
     * @param clientesId El id de el cliente en la cual se va a guardar el
     * comprobante.
     * @return El comprobante creado.
     */
    public ComprobanteDePagoEntity addComprobante(Long comprobantesId, Long clientesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de agregarle un comprobante a el cliente con id = {0}", clientesId);
        ClienteEntity clienteEntity = clientePersistence.find(clientesId);
        ComprobanteDePagoEntity comprobanteEntity = comprobantePersistence.find(clientesId, comprobantesId);
        comprobanteEntity.setCliente(clienteEntity);
        LOGGER.log(Level.INFO, "Termina proceso de agregarle un comprobante a el cliente con id = {0}", clientesId);
        return comprobanteEntity;
    }

    /**
     * Retorna todos los comprobantes asociadas a una cliente
     *
     * @param clientesId El ID de el cliente buscada
     * @return La lista de comprobantes de el cliente
     */
    public List<ComprobanteDePagoEntity> getComprobantes(Long clientesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los comprobantes asociadas a el cliente con id = {0}", clientesId);
        return clientePersistence.find(clientesId).getComprobantesRecibidos();
    }

    /**
     * Retorna un comprobante asociado a una cliente
     *
     * @param clientesId El id de el cliente a buscar.
     * @param comprobantesId El id del comprobante a buscar
     * @return El comprobante encontrado dentro de el cliente.
     * @throws BusinessLogicException Si el comprobante no se encuentra en el
     * cliente
     */
    public ComprobanteDePagoEntity getComprobante(Long clientesId, Long comprobantesId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el comprobante con id = {0} de el cliente con id = " + clientesId, comprobantesId);
        List<ComprobanteDePagoEntity> comprobantes = clientePersistence.find(clientesId).getComprobantesRecibidos();
        ComprobanteDePagoEntity comprobanteEntity = comprobantePersistence.find(clientesId, comprobantesId);
        int index = comprobantes.indexOf(comprobanteEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar el comprobante con id = {0} de el cliente con id = " + clientesId, comprobantesId);
        if (index >= 0) {
            return comprobantes.get(index);
        }
        throw new BusinessLogicException("El comprobante no está asociado a el cliente");
    }

    /**
     * Remplazar comprobantes de una cliente
     *
     * @param comprobantes Lista de comprobantes que serán los de el cliente.
     * @param clientesId El id de el cliente que se quiere actualizar.
     * @return La lista de comprobantes actualizada.
     */
    public List<ComprobanteDePagoEntity> replaceComprobantes(Long clientesId, List<ComprobanteDePagoEntity> comprobantes) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el cliente con id = {0}", clientesId);
        ClienteEntity clienteEntity = clientePersistence.find(clientesId);
        List<ComprobanteDePagoEntity> comprobanteList = comprobantePersistence.findAll();
        for (ComprobanteDePagoEntity comprobante : comprobanteList) {
            if (comprobantes.contains(comprobante)) {
                comprobante.setCliente(clienteEntity);
            } else if (comprobante.getCliente() != null && comprobante.getCliente().equals(clienteEntity)) {
                comprobante.setCliente(null);
            }
        }
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el cliente con id = {0}", clientesId);
        return comprobantes;
    }
}
