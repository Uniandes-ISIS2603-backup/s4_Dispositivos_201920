/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.ejb;

import co.edu.uniandes.csw.dispositivos.entities.MarcaEntity;
import co.edu.uniandes.csw.dispositivos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.dispositivos.persistence.MarcaPersistence;
import java.util.List;
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
        if (marca.getNombreMarca() == null || marca.getNombreMarca().trim().equals("")) {
            throw new BusinessLogicException("El nombre de la marca está vacío");
        } else if (marca.getImagen() == null || marca.getImagen().trim().equals("")) {
            throw new BusinessLogicException("La imagen de la marca está vacía");
        } else if (mp.findByNombre(marca.getNombreMarca()) != null) {
            throw new BusinessLogicException("Ya existe una marca con el mismo nombre");
        }

        marca = mp.create(marca);
        return marca;
    }

    /**
     * Devuelve todas las marcas que hay en la base de datos.
     *
     * @return Lista de entidades de tipo marca.
     */
    public List<MarcaEntity> getMarcas() {
        List<MarcaEntity> facturas = mp.findAll();
        return facturas;
    }

    /**
     * Busca una marca por ID
     *
     * @param marcaId El id de la marca a buscar
     * @return La marca encontrada, null si no la encuentra.
     */
    public MarcaEntity getMarca(Long marcaId) {
        MarcaEntity facturaEntity = mp.find(marcaId);
        return facturaEntity;
    }

    /**
     * Actualizar una marca por ID
     *
     * @param marcaId El ID de la marca a actualizar
     * @param marcaEntity La entidad de la marca con los cambios deseados
     * @return La entidad factura luego de actualizarla
     * @throws BusinessLogicException <br>
     * Si el nombre de la marca está vacío. <br>
     * Si la imagen de la marca está vacía <br>
     * Si ya existe una marca con el mismo nombre <br>
     */
    public MarcaEntity updateMarca(Long marcaId, MarcaEntity marcaEntity) throws BusinessLogicException {
        if (marcaEntity.getNombreMarca() == null || marcaEntity.getNombreMarca().trim().equals("")) {
            throw new BusinessLogicException("El nombre de la marca está vacío");
        } else if (marcaEntity.getImagen().trim().equals("") || marcaEntity.getImagen() == null) {
            throw new BusinessLogicException("La imagen de la marca está vacía");
        } else if (mp.findByNombre(marcaEntity.getNombreMarca()) != null) {
            throw new BusinessLogicException("Ya existe una marca con el mismo nombre");
        }
        MarcaEntity newEntity = mp.update(marcaEntity);
        return newEntity;
    }

    /**
     * Eliminar una marca por ID
     *
     * @param marcaId El ID de la factura a eliminar
     */
    public void deleteMarca(Long marcaId) {

        mp.delete(marcaId);
    }

}
