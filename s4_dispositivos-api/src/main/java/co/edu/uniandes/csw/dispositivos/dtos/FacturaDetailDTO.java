/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.dtos;

import co.edu.uniandes.csw.dispositivos.entities.DispositivoEntity;
import co.edu.uniandes.csw.dispositivos.entities.FacturaEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlos Salazar
 */
public class FacturaDetailDTO extends FacturaDTO implements Serializable {

    // relación  1 o muchos dispositivos
    private List<DispositivoDTO> dispositivos;

    public FacturaDetailDTO() {
        super();
    }

    /**
     * Constructor para transformar un Entity a un DTO
     *
     * @param facturaEntity La entidad de la cual se construye el DTO
     */
    public FacturaDetailDTO(FacturaEntity facturaEntity) {
        super(facturaEntity);
        if (facturaEntity.getDispositivos() != null) {
            dispositivos = new ArrayList<>();
            for (DispositivoEntity entityDispositivo : facturaEntity.getDispositivos()) {
                dispositivos.add(new DispositivoDTO(entityDispositivo));
            }
        }
    }

    /**
     * Transformar el DTO a una entidad
     *
     * @return La entidad que representa la factura.
     */
    @Override
    public FacturaEntity toEntity() {
        FacturaEntity facturaEntity = super.toEntity();
        if (dispositivos != null) {
            List<DispositivoEntity> dispositivosEntity = new ArrayList<>();
            for (DispositivoDTO dtoReview : getDispositivos()) {
                dispositivosEntity.add(dtoReview.toEntity());
            }
            facturaEntity.setDispositivos(dispositivosEntity);
        }
        return facturaEntity;
    }

    /**
     * Devuelve los dispositivos de la factura
     *
     * @return DTO de dispositivos
     */
    public List<DispositivoDTO> getDispositivos() {
        return dispositivos;
    }

    /**
     * Modifica los dispositivos de la factura
     *
     * @param dispositivosE Lista de dispositivos
     */
    public void setDispositivos(List<DispositivoDTO> dispositivosE) {
        this.dispositivos = dispositivosE;
    }

}
