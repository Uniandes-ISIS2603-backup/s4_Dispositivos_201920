/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.dtos;

import co.edu.uniandes.csw.dispositivos.entities.DispositivoEntity;
import java.io.Serializable;

/**
 *
 * @author Santiago Fajardo
 */
public class DispositivoDetailDTO extends DispositivoDTO implements Serializable {

    /**
     *
     * @param dispositivoEntity
     */
    public DispositivoDetailDTO(DispositivoEntity dispositivoEntity) {

    }

    /**
     *
     * @return
     */
    @Override
    public DispositivoEntity toEntity() {
        DispositivoEntity dispositivoEntity = super.toEntity();
        return dispositivoEntity;
    }

}
