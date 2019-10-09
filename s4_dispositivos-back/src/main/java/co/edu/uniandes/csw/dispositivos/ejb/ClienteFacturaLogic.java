/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.ejb;

import co.edu.uniandes.csw.dispositivos.entities.FacturaEntity;
import co.edu.uniandes.csw.dispositivos.entities.ClienteEntity;
import co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.dispositivos.persistence.ClientePersistence;
import co.edu.uniandes.csw.dispositivos.persistence.FacturaPersistence;
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
public class ClienteFacturaLogic {

    private static final Logger LOGGER = Logger.getLogger(ClienteFacturaLogic.class.getName());

    @Inject
    private FacturaPersistence facturaPersistence;

    @Inject
    private ClientePersistence clientePersistence;

    /**
     * Agregar un factura a el cliente
     *
     * @param facturasId El id factura a guardar
     * @param clientesId El id de el cliente en la cual se va a guardar el
     * factura.
     * @return El factura creado.
     */
    public FacturaEntity addFactura(Long facturasId, Long clientesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de agregarle un factura a el cliente con id = {0}", clientesId);
        ClienteEntity clienteEntity = clientePersistence.find(clientesId);
        FacturaEntity facturaEntity = facturaPersistence.find(facturasId, clientesId);
        facturaEntity.setCliente(clienteEntity);
        LOGGER.log(Level.INFO, "Termina proceso de agregarle un factura a el cliente con id = {0}", clientesId);
        return facturaEntity;
    }

    /**
     * Retorna todos los facturas asociadas a una cliente
     *
     * @param clientesId El ID de el cliente buscada
     * @return La lista de facturas de el cliente
     */
    public List<FacturaEntity> getFacturas(Long clientesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los facturas asociadas a el cliente con id = {0}", clientesId);
        return clientePersistence.find(clientesId).getFacturas();
    }

    /**
     * Retorna un factura asociado a una cliente
     *
     * @param clientesId El id de el cliente a buscar.
     * @param facturasId El id del factura a buscar
     * @return El factura encontrado dentro de el cliente.
     * @throws BusinessLogicException Si el factura no se encuentra en el
     * cliente
     */
    public FacturaEntity getFactura(Long clientesId, Long facturasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el factura con id = {0} de el cliente con id = " + clientesId, facturasId);
        List<FacturaEntity> facturas = clientePersistence.find(clientesId).getFacturas();
        FacturaEntity facturaEntity = facturaPersistence.find(facturasId, clientesId);
        int index = facturas.indexOf(facturaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar el factura con id = {0} de el cliente con id = " + clientesId, facturasId);
        if (index >= 0) {
            return facturas.get(index);
        }
        throw new BusinessLogicException("El factura no está asociado a el cliente");
    }

    /**
     * Remplazar facturas de una cliente
     *
     * @param facturas Lista de facturas que serán los de el cliente.
     * @param clientesId El id de el cliente que se quiere actualizar.
     * @return La lista de facturas actualizada.
     */
    public List<FacturaEntity> replaceFacturas(Long clientesId, List<FacturaEntity> facturas) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el cliente con id = {0}", clientesId);
        ClienteEntity clienteEntity = clientePersistence.find(clientesId);
        List<FacturaEntity> facturaList = facturaPersistence.findAll();
        for (FacturaEntity factura : facturaList) {
            if (facturas.contains(factura)) {
                factura.setCliente(clienteEntity);
            } else if (factura.getCliente() != null && factura.getCliente().equals(clienteEntity)) {
                factura.setCliente(null);
            }
        }
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el cliente con id = {0}", clientesId);
        return facturas;
    }
}
