/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.ejb;

import co.edu.uniandes.csw.dispositivos.entities.ClienteEntity;
import co.edu.uniandes.csw.dispositivos.entities.ComprobanteDePagoEntity;
import co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.dispositivos.persistence.ClientePersistence;
import co.edu.uniandes.csw.dispositivos.persistence.ComprobanteDePagoPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que evalua reglas de negocio para ComprobanteDePago
 * @author Dianis Caro
 */
@Stateless
public class ComprobanteDePagoLogic
{
        
    private static final Logger LOGGER = Logger.getLogger(ComprobanteDePagoLogic.class.getName());
        
    @Inject
    private ComprobanteDePagoPersistence persistence;
    
    @Inject
    private ClientePersistence clientePersistence;
    /**
     * Constructor de la clase
     */
    public ComprobanteDePagoLogic()
    {
        //Clase constructora 
    }
    /**
     * Obtiene la lista de los registros de comprobantes de pago
     * @param clienteId id del cliente asociado
     * @return Colección de objetos de ComprobanteDePagoEntity
     */
    public List<ComprobanteDePagoEntity> getComprobantes(Long clienteId) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los comprobantes de pago con id = {0}", clienteId);
        ClienteEntity clienteEntt = clientePersistence.find(clienteId);
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los comprobantes de pago con id = {0}", clienteId);
        //return clienteEntt.getComprobantes();
        return null;

    }
    /**
     * Obtiene los datos de una instancia de ComprobanteDePago a partir de su ID
     * @param comprobanteId Identificador de la instancia a consultar
     * @param clienteId id del cliente asociado
     * @return Instancia de ComprobanteDePago con los datos del ComprobanteDePago Id consultado.
     */
    public ComprobanteDePagoEntity getComprobante(Long comprobanteId, Long clienteId) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el comprobante con id = {0} del cliente con id = " + clienteId, comprobanteId);
        return persistence.find(clienteId, comprobanteId);
    }
     /**
     * Actualiza la información de un ComprobanteDePago
     * @param comprobanteEntity: ComprobanteDePago con los cambios para actualizar.
     * @param clienteId id del cliente asociado
     * @return el ComprobanteDePago con los cambios actualizados en la base de datos.
     * @throws co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException
     */
     public ComprobanteDePagoEntity updateComprobanteDePago(ComprobanteDePagoEntity comprobanteEntity, Long clienteId) throws BusinessLogicException {
         LOGGER.log(Level.INFO, "Inicia proceso de actualizar un comprobante de pago.");
         
        if (comprobanteEntity.getNumeroDeFactura()==null ||comprobanteEntity.getNumeroDeFactura()<=0) 
            throw new BusinessLogicException("El número de factura del Comprobante de pago está vacío o es negativo");
        if (comprobanteEntity.getTotalDePago()==null ||0.0>=comprobanteEntity.getTotalDePago())
            throw new BusinessLogicException("El total a pagar del Comprobante está en cero o es negativo" + comprobanteEntity.getTotalDePago());
        if (comprobanteEntity.getImpuestos()==null || comprobanteEntity.getImpuestos()<=0.0)
            throw new BusinessLogicException("Los impuestos del Comprobante están en cero o es negativo");
        if(comprobanteEntity.getFechaDeFactura()==null)
            throw new BusinessLogicException("La fecha del Comprobante está vacía");
        if(comprobanteEntity.getNumeroDeTarjeta()==null || comprobanteEntity.getNumeroDeTarjeta().trim().equals(""))
            throw new BusinessLogicException("No se ha registrado el número de tarjeta");
        if(16!=comprobanteEntity.getNumeroDeTarjeta().length())
            throw new BusinessLogicException("El número de tarjeta no cuenta con 16 dígitos");
        if (persistence.findByNumFactura(comprobanteEntity.getNumeroDeFactura()) != null)
            throw new BusinessLogicException("Ya existe una comprobante de pago con el mismo número de factura");
        if (clientePersistence.find(clienteId) == null)
            throw new BusinessLogicException("No existe el cliente asociado al comprobante de pago");
        
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el comprobante de pago con id = {0} del cliente con id = " + clienteId, comprobanteEntity.getId());
        ClienteEntity clienteEntity = clientePersistence.find(clienteId);
        comprobanteEntity.setCliente(clienteEntity);
        comprobanteEntity = persistence.update(comprobanteEntity);        
        LOGGER.log(Level.INFO, "Termina proceso de actualizar un comprobante de pago.");
        return comprobanteEntity;
    }
    /**
     * Elimina una instancia de ComprobanteDePago de la base de datos
     * @param comprobanteId Identificador de la instancia a eliminar
     * @param clienteId id del cliente asociado
     * @throws co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException Cuando no se cumple una de las reglas de negocio
     */
    public void deleteComprobante(Long comprobanteId, Long clienteId) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el comprobante de pago con id = {0} del cliente con id = " + clienteId, comprobanteId);
        ComprobanteDePagoEntity old = getComprobante(comprobanteId, clienteId);
        if (old == null)
        {
            throw new BusinessLogicException("El comprobante de pago con id = " + comprobanteId + " no esta asociado al cliente con id = " + clienteId);
        }
        persistence.delete(old.getId());
        LOGGER.log(Level.INFO, "Termina proceso de borrar el comprobante de pago con id = {0} del cliente con id = " + clienteId, comprobanteId);
    }
    /**
     *Se encarga de crear un comprobante de pago en la base de datos
     * @param comprobanteEntity Objeto de ComprobanteDePagoEntity con los datos nuevos
     * @param clienteId id del cliente asociado
     * @return Objeto de ComprobanteDePagoEntity con los datos nuevos y su ID
     * @throws BusinessLogicException Cuando no se cumple una de las reglas de negocio
     */
    public ComprobanteDePagoEntity createComprobante(ComprobanteDePagoEntity comprobanteEntity, Long clienteId) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de un comprobante de pago.");

        if(comprobanteEntity.getFechaDeFactura()==null)
            throw new BusinessLogicException("El número de factura del Comprobante de pago está vacío o es negativo");
        if (comprobanteEntity.getTotalDePago()==null ||0.0 >= comprobanteEntity.getTotalDePago())
            throw new BusinessLogicException("La fecha del Comprobante está vacía");
        if (comprobanteEntity.getNumeroDeFactura()==null ||comprobanteEntity.getNumeroDeFactura()<=0) 
            throw new BusinessLogicException("El total a pagar del Comprobante está en cero o es negativo");
        if (comprobanteEntity.getImpuestos()==null || comprobanteEntity.getImpuestos()<=0.0)
            throw new BusinessLogicException("Los impuestos del Comprobante están en cero o es negativo");
        if(comprobanteEntity.getNumeroDeTarjeta()==null || comprobanteEntity.getNumeroDeTarjeta().trim().equals(""))
            throw new BusinessLogicException("No se ha registrado el número de tarjeta");
        if(16!=comprobanteEntity.getNumeroDeTarjeta().length())
            throw new BusinessLogicException("El número de tarjeta no cuenta con 16 dígitos");
        if (persistence.findByNumFactura(comprobanteEntity.getNumeroDeFactura()) != null)
            throw new BusinessLogicException("Ya existe una comprobante de pago con el mismo número de factura");
        if (clientePersistence.find(clienteId) == null)
            throw new BusinessLogicException("No existe el cliente asociado al comprobante de pago");
        
        LOGGER.log(Level.INFO, "Termina proceso de creación de un comprobante de pago.");
        ClienteEntity cliente = clientePersistence.find(clienteId);
        comprobanteEntity.setCliente(cliente);
        comprobanteEntity = persistence.create(comprobanteEntity);
        return comprobanteEntity;
     
    }
}
