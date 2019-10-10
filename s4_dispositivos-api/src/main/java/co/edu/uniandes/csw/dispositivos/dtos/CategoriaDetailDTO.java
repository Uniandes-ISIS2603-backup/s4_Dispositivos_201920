/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.dtos;

import co.edu.uniandes.csw.dispositivos.entities.CategoriaEntity;
import co.edu.uniandes.csw.dispositivos.entities.DispositivoEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Estudiante
 */
public class CategoriaDetailDTO extends CategoriaDTO implements Serializable{
    
      /*
    * Esta lista de tipo DispositivoDTO contiene los dispositivos que estan asociados a una categoria
     */
    private List<DispositivoDTO> dispositivos;
    
        //private String logo;
    public CategoriaDetailDTO() {
        super();
    }

    /**
     * Constructor para transformar un Entity a un DTO
     *
     * @param categoriaEntity La entidad de la cual se construye el DTO
     */
    public CategoriaDetailDTO(CategoriaEntity categoriaEntity) {
        super(categoriaEntity);
        if (categoriaEntity.getDispositivos() != null) {
            dispositivos = new ArrayList<>();
            for (DispositivoEntity entityDispositivo : categoriaEntity.getDispositivos()) {
                dispositivos.add(new DispositivoDTO(entityDispositivo));
            }
        }
    }
    
        /**
     * Transformar el DTO a una entidad
     *
     * @return La entidad que representa la marca.
     */
    @Override
    public CategoriaEntity toEntity() {
        CategoriaEntity categoriaEntity = super.toEntity();
        if (getDispositivos() != null) {
            List<DispositivoEntity> dispositivosEntity = new ArrayList<>();
            for (DispositivoDTO dtoReview : getDispositivos()) {
                dispositivosEntity.add(dtoReview.toEntity());
            }
            categoriaEntity.setDispositivos(dispositivosEntity);
        }
        //if (logo != null) {
        //    marcaEntity.setLogo(logo);
        // }
        return categoriaEntity;
    }

    /**
     * @return the dispositivos
     */
    public List<DispositivoDTO> getDispositivos() {
        return dispositivos;
    }

    /**
     * @param dispositivos the dispositivos to set
     */
    public void setDispositivos(List<DispositivoDTO> dispositivos) {
        this.dispositivos = dispositivos;
    }
    
}
