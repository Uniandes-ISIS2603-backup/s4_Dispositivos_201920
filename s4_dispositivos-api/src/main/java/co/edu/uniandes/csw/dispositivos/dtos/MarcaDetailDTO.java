/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.dtos;

import co.edu.uniandes.csw.dispositivos.entities.DispositivoEntity;
import co.edu.uniandes.csw.dispositivos.entities.MarcaEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Carlos Salazar
 */
public class MarcaDetailDTO extends MarcaDTO implements Serializable {

    private List<DispositivoDTO> dispositivos;

    //private String logo;
    public MarcaDetailDTO() {
        super();
    }

    /**
     * Constructor para transformar un Entity a un DTO
     *
     * @param marcaEntity La entidad de la cual se construye el DTO
     */
    public MarcaDetailDTO(MarcaEntity marcaEntity) {
        super(marcaEntity);
        if (marcaEntity.getDispositivos() != null) {
            dispositivos = new ArrayList<>();
            for (DispositivoEntity entityDispositivo : marcaEntity.getDispositivos()) {
                dispositivos.add(new DispositivoDTO(entityDispositivo));
            }
        }
        if (marcaEntity.getLogo() != null) {
            //logo = marcaEntity.getLogo();
        }
    }

    /**
     * Transformar el DTO a una entidad
     *
     * @return La entidad que representa la marca.
     */
    @Override
    public MarcaEntity toEntity() {
        MarcaEntity marcaEntity = super.toEntity();
        if (dispositivos != null) {
            List<DispositivoEntity> dispositivosEntity = new ArrayList<>();
            for (DispositivoDTO dtoReview : getDispositivos()) {
                dispositivosEntity.add(dtoReview.toEntity());
            }
            marcaEntity.setDispositivos(dispositivosEntity);
        }
        //if (logo != null) {
        //    marcaEntity.setLogo(logo);
        // }
        return marcaEntity;
    }

    /**
     * Devuelve los dispositivos de la marca
     *
     * @return DTO de dispositivos
     */
    public List<DispositivoDTO> getDispositivos() {
        return dispositivos;
    }

    /**
     * Modifica los dispositivos de la marca
     *
     * @param dispositivosE Lista de dispositivos
     */
    public void setDispositivos(List<DispositivoDTO> dispositivosE) {
        this.dispositivos = dispositivosE;
    }
}
