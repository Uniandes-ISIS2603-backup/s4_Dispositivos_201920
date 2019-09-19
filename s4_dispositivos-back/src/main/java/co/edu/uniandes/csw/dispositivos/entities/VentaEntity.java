/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.entities;

import java.io.Serializable;
import javax.persistence.Entity;

/**
 *
 * @author Zharet Bautista Montes
 */
@Entity
public class VentaEntity extends BaseEntity implements Serializable {

    private Double precioReventa;

    /**
     * Constructor vacío
     */
    public VentaEntity() 
    {    }

    /**
     * Constructor que recibe parámetros
     *
     * @param precioReventa
     */
    public VentaEntity(Double precioReventa) 
    {
        this.precioReventa = precioReventa;
    }

    /**
     * Retorna el precio de reventa
     *
     * @return precioReventa
     */
    public Double getPrecioReventa() {
        return this.precioReventa;
    }

    /**
     *
     * @param precioReventa
     */
    public void setPrecioReventa(Double precioReventa) {
        this.precioReventa = precioReventa;
    }
}
