/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.ejb;

import co.edu.uniandes.csw.dispositivos.entities.MarcaEntity;
import co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.dispositivos.persistence.MarcaPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Carlos Salazar
 */
@Stateless
public class MarcaLogic {

    @Inject
    private MarcaPersistence mp;

    public MarcaEntity createMarca(MarcaEntity marca) throws BusinessLogicException {
        if (marca.getNombreMarca() == null) {
            throw new BusinessLogicException("El nombre de la marca está vacío");
        }
        marca = mp.create(marca);
        return marca;
    }

}
