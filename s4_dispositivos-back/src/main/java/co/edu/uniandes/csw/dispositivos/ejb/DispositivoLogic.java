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
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Santiago Fajardo
 */
@Stateless
public class DispositivoLogic {

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
    public List<DispositivoEntity> findAll() {
        return persistence.findAll();
    }

    /**
     *
     * @param id
     * @return
     */
    public DispositivoEntity find(Long id) {
        return persistence.find(id);
    }

    /**
     *
     * @param dispositivo
     * @return
     */
    public void deleteDispositivo(DispositivoEntity dispositivo) {

        persistence.delete(dispositivo.getId());
    }

    /**
     *
     * @param dispositivo
     * @return
     * @throws BusinessLogicException
     */
    public DispositivoEntity update(DispositivoEntity dispositivo) throws BusinessLogicException {

        if (persistence.find(dispositivo.getId()) != null) {
            throw new BusinessLogicException("Ya existe un dispositivo con este Id");
        }
        if (dispositivo.getPrecio() < 0) {
            throw new BusinessLogicException("El precio es menor al permitido");
        }
        if (dispositivo.getPrecioImportacion() < 0) {
            throw new BusinessLogicException("El precio de importacion es menor al esperado");
        }
        if (dispositivo.getDescuento() > dispositivo.getPrecio()) {
            throw new BusinessLogicException("El descuento es mayor al precio del dispositivo");
        }
        if (dispositivo.getNombre() == null) {
            throw new BusinessLogicException("El nombre del dispositivo no puede ser null");
        }
        if (dispositivo.getDescripcion() == null) {
            throw new BusinessLogicException("La descripcion del dispositivo no puede ser null");
        }

        return persistence.update(dispositivo);
    }
}
