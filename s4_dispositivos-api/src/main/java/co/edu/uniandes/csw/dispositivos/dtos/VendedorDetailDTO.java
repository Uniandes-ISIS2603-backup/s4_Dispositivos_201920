/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.dtos;

import co.edu.uniandes.csw.dispositivos.entities.VendedorEntity;
import co.edu.uniandes.csw.dispositivos.entities.VentaEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Zharet Bautista Montes
 */
public class VendedorDetailDTO extends VendedorDTO implements Serializable {

    /**
     * Lista de ventas del DTODetail
     */
    private List<VentaDTO> ventas;

    /**
     * Constructor vacío
     */
    public VendedorDetailDTO() {
    }

    /**
     * Constructor basado en la entidad
     *
     * @param refvr
     */
    public VendedorDetailDTO(VendedorEntity refvr) {
        super(refvr);
        if (refvr != null && refvr.getVentas() != null) {
            ventas = new ArrayList<>();
            for (VentaEntity ventavr : refvr.getVentas()) {
                ventas.add(new VentaDTO(ventavr));
            }
        }
    }

    /**
     * Retorna las ventas del DTODetail
     *
     * @return the ventas
     */
    public List<VentaDTO> getVentas() {
        return ventas;
    }

    /**
     * Asigna las ventas del DTODetail
     *
     * @param ventas the ventas to set
     */
    public void setVentas(List<VentaDTO> ventas) {
        this.ventas = ventas;
    }

    /**
     * Transforma el DTODetail en un Entity con asociaciones
     *
     * @return VendedorEntity
     */
    @Override
    public VendedorEntity toEntity() {
        VendedorEntity extvendedor = super.toEntity();
        if (getVentas() != null) {
            List<VentaEntity> ventalist = new ArrayList<>();
            for (VentaDTO dtoventa : getVentas()) {
                ventalist.add(dtoventa.toEntity());
            }
            extvendedor.setVentas(ventalist);
        }
        return extvendedor;
    }
}
