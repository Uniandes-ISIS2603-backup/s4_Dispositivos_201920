/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.ejb;

import co.edu.uniandes.csw.dispositivos.entities.DispositivoEntity;
import co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.dispositivos.persistence.DispositivoPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Santiago Fajardo
 */
@Stateless
public class DispositivoLogic {

    private static final Logger LOGGER = Logger.getLogger(DispositivoLogic.class.getName());

    @Inject
    private DispositivoPersistence persistence;

    public DispositivoEntity createDispositivo(DispositivoEntity dispositivo) throws BusinessLogicException {

        if (dispositivo != null) {
            if (persistence.find(dispositivo.getId()) == null) {
                if (!dispositivo.isUsado()) {
                    if (!dispositivo.isEsImportado()) {
                        if (dispositivo.getDescripcion() == null || dispositivo.getNombre() == null || dispositivo.getModelo() == null
                                || dispositivo.getPrecio() < 0 || !dispositivo.isEnStock()) {

                            throw new BusinessLogicException("Los datos basicos no cumplen los requerimentos de postulacion o no existe en stock");
                        }
                    } else {
                        if (dispositivo.getDescripcion() == null || dispositivo.getNombre() == null || dispositivo.getModelo() == null
                                || dispositivo.getPrecio() < 0 || dispositivo.getPrecioImportacion() < 0 || !dispositivo.isEnStock()) {

                            throw new BusinessLogicException("Los datos basicos no cumplen los requerimentos de postulacion o no existe en stock");
                        }
                    }

                    if (dispositivo.isPromocion()) {
                        if (dispositivo.getPrecio() < dispositivo.getDescuento()) {

                            throw new BusinessLogicException("El descuento es mayor al precio del dispositivo");
                        }
                    }

                } else {
                    if (dispositivo.getPrecio() <= 0) {
                        throw new BusinessLogicException("El dispositivo no puede ser vendido porque no cumple los requerimentos minimos");
                    }
                }
            } else {
                throw new BusinessLogicException("El dispositivo ya se encuentra registrado");
            }

        } else {
            throw new BusinessLogicException("El dispositivo no fue ingresado");
        }

        dispositivo = persistence.create(dispositivo);
        return dispositivo;
    }

    /**
     *
     * @return
     */
    public List<DispositivoEntity> getDispositivos() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los dispositivos");
        List<DispositivoEntity> dispositivos = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los dispositivos");
        return dispositivos;

    }

    /**
     *
     * @param dispositivosId
     * @return
     */
    public DispositivoEntity getDispositivo(Long dispositivosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el dispositivo con id = {0}", dispositivosId);
        DispositivoEntity dispositivoEntity = persistence.find(dispositivosId);
        if (dispositivoEntity == null) {
            LOGGER.log(Level.SEVERE, "El dispositivo con el id = {0} no existe", dispositivosId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el dispositivo con id = {0}", dispositivoEntity);
        return dispositivoEntity;
    }

    public DispositivoEntity updateDispositivo(Long dispositivosId, DispositivoEntity dispositivoEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el dispositivo con id = {0}", dispositivosId);
        if (dispositivoEntity.getDescripcion() == null) {
            throw new BusinessLogicException("No se puede agregar un dispositivo con la descripcion nula");
        }
        if (dispositivoEntity.getNombre() == null) {
            throw new BusinessLogicException("No se puede agregar un dispositivo con el nombre nulo");
        }
        if (dispositivoEntity.getModelo() == null) {
            throw new BusinessLogicException("No se puede agregar un dispositivo con el modelo nulo");
        }
        if (dispositivoEntity.getPrecio() < 0) {
            throw new BusinessLogicException("No se puede agrear un dispositivo con precio negativo");
        }
        if (dispositivoEntity.getCategoria() == null) {
            throw new BusinessLogicException("Especifique la categoria a la que pertenece el dispositivo");
        }
        if (dispositivoEntity.getMarca() == null) {
            throw new BusinessLogicException("Especifique la marca del dispositivo");
        }
        if (dispositivoEntity.isEsImportado()) {
            if (dispositivoEntity.getPrecioImportacion() < 0) {
                throw new BusinessLogicException("No se puede agregar un dispositivo con precio de importacion negativo");
            }
        }
        if (dispositivoEntity.isPromocion()) {
            if (dispositivoEntity.getPrecio() < dispositivoEntity.getDescuento()) {
                throw new BusinessLogicException("No se puede agregar un precio de descuento mayor al precio original");
            }
        }
        DispositivoEntity newEntity = persistence.update(dispositivoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el dispositivo con id = {0}", dispositivoEntity.getId());
        return newEntity;
    }

    public void deleteDispositivo(Long dispositivosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el dispositivo con id = {0}", dispositivosId);
        persistence.delete(dispositivosId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el dispositivo con id = {0}", dispositivosId);

    }
}
