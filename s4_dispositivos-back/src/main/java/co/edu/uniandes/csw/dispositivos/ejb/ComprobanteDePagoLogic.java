/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.ejb;

import co.edu.uniandes.csw.dispositivos.entities.ComprobanteDePagoEntity;
import co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.dispositivos.persistence.ComprobanteDePagoPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que evalua reglas de negocio para ComprobanteDePago
 * @author Dianis Caro
 */
@Stateless
public class ComprobanteDePagoLogic
{
    @Inject
    private ComprobanteDePagoPersistence persistence;
    /**
     * Constructor de la clase
     */
    public ComprobanteDePagoLogic()
    {
        //Clase constructora 
    }
    /**
     * Obtiene la lista de los registros de comprobantes de pago
     * @return Colección de objetos de ComprobanteDePagoEntity
     */
    public List<ComprobanteDePagoEntity> getComprobantes() 
    {
        List<ComprobanteDePagoEntity> comprobantes = persistence.findAll();
        return comprobantes;
    }
    /**
     * Obtiene los datos de una instancia de ComprobanteDePago a partir de su ID
     * @param comprobanteId Identificador de la instancia a consultar
     * @return Instancia de ComprobanteDePago con los datos del ComprobanteDePago Id consultado.
     */
    public ComprobanteDePagoEntity getComprobante(Long comprobanteId) 
    {
        ComprobanteDePagoEntity facturaEntity = persistence.find(comprobanteId);  
        return facturaEntity;
    }
     /**
     * Actualiza la información de un ComprobanteDePago
     * @param comprobanteId: id del ComprobanteDePago para buscarla en la base de datos.
     * @param comprobanteEntity: ComprobanteDePago con los cambios para actualizar.
     * @return el ComprobanteDePago con los cambios actualizados en la base de datos.
     * @throws co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException
     */
     public ComprobanteDePagoEntity updateComprobanteDePago(Long comprobanteId, ComprobanteDePagoEntity comprobanteEntity) throws BusinessLogicException {
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
        ComprobanteDePagoEntity newEntity = persistence.update(comprobanteEntity);
        return newEntity;
    }
    /**
     * Elimina una instancia de ComprobanteDePago de la base de datos
     * @param comprobanteId Identificador de la instancia a eliminar
     */
    public void deleteComprobante(Long comprobanteId)
    {
        persistence.delete(comprobanteId);
    }
    /**
     *Se encarga de crear un comprobante de pago en la base de datos
     * @param comprobanteEntity Objeto de ComprobanteDePagoEntity con los datos nuevos
     * @return Objeto de ComprobanteDePagoEntity con los datos nuevos y su ID
     * @throws BusinessLogicException Cuando no se cumple una de las reglas de negocio
     */
    public ComprobanteDePagoEntity createComprobante(ComprobanteDePagoEntity comprobanteEntity) throws BusinessLogicException
    {
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
        
        comprobanteEntity = persistence.create(comprobanteEntity);
        return comprobanteEntity;
     
    }
}
