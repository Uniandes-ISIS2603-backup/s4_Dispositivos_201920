/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.dtos;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Santiago Fajardo
 */
public class DispositivoDetailDTO extends DispositivoDTO implements Serializable {

    private MarcaDTO marca;
    private CategoriaDTO categoria;
    private List<MediaDTO> multimedia;

}
