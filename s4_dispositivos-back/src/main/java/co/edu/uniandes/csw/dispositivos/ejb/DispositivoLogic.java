/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.ejb;

import co.edu.uniandes.csw.dispositivos.entities.DispositivoEntity;
import co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.dispositivos.persistence.DispositivoPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Santiago Fajardo
 */
@Stateless
public class DispositivoLogic {

    @Inject
    private DispositivoPersistence persistence;

    public DispositivoEntity createDispositivo(DispositivoEntity dispositivo) throws BusinessLogicException {

        if (dispositivo != null && dispositivo.getFactura() != null && dispositivo.getImagenes() != null) {

            if (!dispositivo.isUsado()) {
                if (!dispositivo.isEsImportado()) {
                    if (dispositivo.getDescripcion() == null || dispositivo.getNombre() == null || dispositivo.getModelo() == null
                            || dispositivo.getPrecio() < 0 || dispositivo.getImagenes() == null || !dispositivo.isEnStock()) {

                        throw new BusinessLogicException("Los datos basicos no cumplen los requerimentos de postulacion o no existe en stock");
                    }
                } else {
                    if (dispositivo.getDescripcion() == null || dispositivo.getNombre() == null || dispositivo.getModelo() == null
                            || dispositivo.getPrecio() < 0 || dispositivo.getPrecioImportacion() < 0 || dispositivo.getImagenes() == null || !dispositivo.isEnStock()) {

                        throw new BusinessLogicException("Los datos basicos no cumplen los requerimentos de postulacion o no existe en stock");
                    }
                }

                if (dispositivo.isPromocion()) {
                    if (dispositivo.getPrecio() < dispositivo.getDescuento()) {

                        throw new BusinessLogicException("El descuento es mayor al precio del dispositivo");
                    }
                }

            } else {
                if (dispositivo.getFactura() == null || dispositivo.getImagenes() == null || dispositivo.getPrecio() <= 0) {
                    throw new BusinessLogicException("El dispositivo no puede ser vendido porque no cumple los requerimentos minimos");
                }
            }

        }

        dispositivo = persistence.create(dispositivo);
        return dispositivo;
    }

}
