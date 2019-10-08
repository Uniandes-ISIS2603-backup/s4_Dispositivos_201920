/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.dtos;

/**
 *
 * @author Carlos Salazar
 */
public class ClienteDetailDTO {
        // relación  1 o muchos dispositivos
    private List<DispositivoDTO> dispositivos;

    public ClienteDetailDTO() {
        super();
    }

    /**
     * Constructor para transformar un Entity a un DTO
     *
     * @param clienteEntity La entidad de la cual se construye el DTO
     */
    public FacturaDetailDTO(ClienteEntity clienteEntity) {
        super(clienteEntity);
        if (clienteEntity.getDispositivos() != null) {
            dispositivos = new ArrayList<>();
            for (DispositivoEntity entityDispositivo : clienteEntity.getDispositivos()) {
                dispositivos.add(new DispositivoDTO(entityDispositivo));
            }
        }
    }

    /**
     * Transformar el DTO a una entidad
     *
     * @return La entidad que representa al cliente.
     */
    @Override
    public ClienteEntity toEntity() {
        ClienteEntity clienteEntity = super.toEntity();
        if (dispositivos != null) {
            List<DispositivoEntity> dispositivosEntity = new ArrayList<>();
            for (DispositivoDTO dtoReview : getDispositivos()) {
                dispositivosEntity.add(dtoReview.toEntity());
            }
            clienteEntity.setDispositivos(dispositivosEntity);
        }
        return clienteEntity;
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
