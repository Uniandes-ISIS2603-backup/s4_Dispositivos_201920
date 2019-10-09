/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.ejb;

import co.edu.uniandes.csw.dispositivos.entities.ClienteEntity;
import co.edu.uniandes.csw.dispositivos.entities.FacturaEntity;
import co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.dispositivos.persistence.ClientePersistence;
import co.edu.uniandes.csw.dispositivos.persistence.FacturaPersistence;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author Carlos Salazar
 */
public class FacturaLogic {

    @Inject
    private FacturaPersistence fp;
    @Inject
    private ClientePersistence cp;

    /**
     * Crea una factura por ID
     *
     * @param factura La entidad factura a crear
     * @return La entidad factura luego de crearla
     * @throws BusinessLogicException <br>
     * Si los dispositivos de la factura están vacíos. <br>
     * Si el numero de la factura es menor a 0 o no existe <br>
     * Si el total pago de la factura es menor a 0 o no existe <br>
     * Si los impuestos de la factura son mernos a 0 o no existen <br>
     * Si Ya existe una factura con el mismo número <br>
     */
    public FacturaEntity createFactura(FacturaEntity factura) throws BusinessLogicException {
        if (factura.getDispositivos() == null) {
            throw new BusinessLogicException("Los dispositivos de la factura están vacíos");
        } else if (factura.getNumeroDeFactura() == null || factura.getNumeroDeFactura() < 1) {
            throw new BusinessLogicException("El numero de la factura es menor a 0 o no existe");
        } else if (factura.getTotalPago() == null || factura.getTotalPago() < 0.0) {
            throw new BusinessLogicException("El total pago de la factura es menor a 0 o no existe");
        } else if (factura.getImpuestos() == null || factura.getImpuestos() < 0.0) {
            throw new BusinessLogicException("Los impuestos de la factura son mernos a 0 o no existen");
        } else if (fp.findByCode(factura.getNumeroDeFactura()) != null) {
            throw new BusinessLogicException("Ya existe una factura con el mismo número");
        }
        factura = fp.create(factura);
        return factura;
    }

    /**
     * Devuelve todas las facturas que hay en la base de datos asociadas al
     * cliente.
     *
     * @param clienteId id del cliente
     * @return Lista de entidades de tipo factura.
     */
    public List<FacturaEntity> getFacturas(Long clienteId) {
        ClienteEntity clienteEntity = cp.find(clienteId);
        return clienteEntity.getFacturas();
    }

    /**
     * Busca una factura por ID
     *
     * @param facturaId El id de la factura a buscar
     * @param clientesId id del cliente
     * @return La factura encontrada, null si no la encuentra.
     */
    public FacturaEntity getFactura(Long clientesId, Long facturaId) throws BusinessLogicException {
        List<FacturaEntity> comprobantes = cp.find(clientesId).getFacturas();
        FacturaEntity comprobanteEntity = fp.find(clientesId, facturaId);
        int index = comprobantes.indexOf(comprobanteEntity);
        if (index >= 0) {
            return comprobantes.get(index);
        }
        throw new BusinessLogicException("La factura no está asociada a el cliente");
    }

    /**
     * Actualizar una factura por ID
     *
     * @param facturaId El ID de la factura a actualizar
     * @param facturaEntity La entidad de la factura con los cambios deseados
     * @return La entidad factura luego de actualizarla
     * @throws BusinessLogicException <br>
     * Si los dispositivos de la factura están vacíos. <br>
     * Si el numero de la factura es menor a 0 o no existe <br>
     * Si el total pago de la factura es menor a 0 o no existe <br>
     * Si los impuestos de la factura son mernos a 0 o no existen <br>
     * Si Ya existe una factura con el mismo número <br>
     */
    public FacturaEntity updateFactura(Long facturaId, FacturaEntity facturaEntity) throws BusinessLogicException {
        if (facturaEntity.getDispositivos() == null) {
            throw new BusinessLogicException("Los dispositivos de la factura están vacíos");
        } else if (facturaEntity.getNumeroDeFactura() == null || facturaEntity.getNumeroDeFactura() < 1) {
            throw new BusinessLogicException("El numero de la factura es menor a 0 o no existe");
        } else if (facturaEntity.getTotalPago() == null || facturaEntity.getTotalPago() < 0.0) {
            throw new BusinessLogicException("El total pago de la factura es menor a 0 o no existe");
        } else if (facturaEntity.getImpuestos() == null || facturaEntity.getImpuestos() < 0.0) {
            throw new BusinessLogicException("Los impuestos de la factura son mernos a 0 o no existen");
        } else if (fp.findByCode(facturaEntity.getNumeroDeFactura()) != null) {
            throw new BusinessLogicException("Ya existe una factura con el mismo número");
        }
        return fp.update(facturaEntity);
    }

    /**
     * Eliminar una factura por ID
     *
     * @param facturaId El ID de la factura a eliminar
     */
    public void deleteFactura(Long facturaId) {

        fp.delete(facturaId);
    }
}
