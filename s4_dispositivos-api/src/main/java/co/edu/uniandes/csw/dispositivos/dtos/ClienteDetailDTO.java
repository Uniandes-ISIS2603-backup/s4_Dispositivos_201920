/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.dtos;

import co.edu.uniandes.csw.dispositivos.entities.ClienteEntity;
import co.edu.uniandes.csw.dispositivos.entities.ComprobanteDePagoEntity;
import co.edu.uniandes.csw.dispositivos.entities.DispositivoEntity;
import co.edu.uniandes.csw.dispositivos.entities.FacturaEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlos Salazar
 */
public class ClienteDetailDTO extends ClienteDTO implements Serializable {

    // relación  1 o muchos dispositivos
    private List<DispositivoDTO> listaDeDeseos;
    private List<DispositivoDTO> carritoDeCompras;
    private List<ComprobanteDePagoDTO> comprobantesRecibidos;
    private List<FacturaDTO> facturas;
    private MedioDePagoDTO metodoDePago;

    public ClienteDetailDTO() {
        super();
    }

    /**
     * Constructor para transformar un Entity a un DTO
     *
     * @param clienteEntity La entidad de la cual se construye el DTO
     */
    public ClienteDetailDTO(ClienteEntity clienteEntity) {
        super(clienteEntity);
        if (clienteEntity.getListaDeDeseos() != null) {
            listaDeDeseos = new ArrayList<>();
            for (DispositivoEntity entityDispositivo : clienteEntity.getListaDeDeseos()) {
                listaDeDeseos.add(new DispositivoDTO(entityDispositivo));
            }
        }
        if (clienteEntity.getCarritoDeCompras() != null) {
            carritoDeCompras = new ArrayList<>();
            for (DispositivoEntity entityDispositivo : clienteEntity.getCarritoDeCompras()) {
                carritoDeCompras.add(new DispositivoDTO(entityDispositivo));
            }
        }
        if (clienteEntity.getComprobantesRecibidos() != null) {
            comprobantesRecibidos = new ArrayList<>();
            for (ComprobanteDePagoEntity entityComprobante : clienteEntity.getComprobantesRecibidos()) {
                comprobantesRecibidos.add(new ComprobanteDePagoDTO(entityComprobante));
            }
        }
        if (clienteEntity.getFacturas() != null) {
            facturas = new ArrayList<>();
            for (FacturaEntity entityFactura : clienteEntity.getFacturas()) {
                facturas.add(new FacturaDTO(entityFactura));
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
        if (getListaDeDeseos() != null) {
            List<DispositivoEntity> dispositivosEntity = new ArrayList<>();
            for (DispositivoDTO dispositivo : getListaDeDeseos()) {
                dispositivosEntity.add(dispositivo.toEntity());
            }
            clienteEntity.setListaDeDeseos(dispositivosEntity);
        }
        if (getCarritoDeCompras() != null) {
            List<DispositivoEntity> dispositivosEntity = new ArrayList<>();
            for (DispositivoDTO dispositivo : getCarritoDeCompras()) {
                dispositivosEntity.add(dispositivo.toEntity());
            }
            clienteEntity.setCarritoDeCompras(dispositivosEntity);
        }
        if (getComprobantesRecibidos() != null) {
            List<ComprobanteDePagoEntity> dispositivosEntity = new ArrayList<>();
            for (ComprobanteDePagoDTO comprobante : getComprobantesRecibidos()) {
                dispositivosEntity.add(comprobante.toEntity());
            }
            clienteEntity.setComprobantesRecibidos(dispositivosEntity);
        }
        if (getFacturas() != null) {
            List<FacturaEntity> facturaEntity = new ArrayList<>();
            for (FacturaDTO factura : getFacturas()) {
                facturaEntity.add(factura.toEntity());
            }
            clienteEntity.setFacturas(facturaEntity);
        }
        return clienteEntity;
    }

    /**
     * @return the listaDeDeseos
     */
    public List<DispositivoDTO> getListaDeDeseos() {
        return listaDeDeseos;
    }

    /**
     * @param listaDeDeseos the listaDeDeseos to set
     */
    public void setListaDeDeseos(List<DispositivoDTO> listaDeDeseos) {
        this.listaDeDeseos = listaDeDeseos;
    }

    /**
     * @return the carritoDeCompras
     */
    public List<DispositivoDTO> getCarritoDeCompras() {
        return carritoDeCompras;
    }

    /**
     * @param carritoDeCompras the carritoDeCompras to set
     */
    public void setCarritoDeCompras(List<DispositivoDTO> carritoDeCompras) {
        this.carritoDeCompras = carritoDeCompras;
    }

    /**
     * @return the comprobantesRecibidos
     */
    public List<ComprobanteDePagoDTO> getComprobantesRecibidos() {
        return comprobantesRecibidos;
    }

    /**
     * @param comprobantesRecibidos the comprobantesRecibidos to set
     */
    public void setComprobantesRecibidos(List<ComprobanteDePagoDTO> comprobantesRecibidos) {
        this.comprobantesRecibidos = comprobantesRecibidos;
    }

    /**
     * @return the facturas
     */
    public List<FacturaDTO> getFacturas() {
        return facturas;
    }

    /**
     * @param facturas the facturas to set
     */
    public void setFacturas(List<FacturaDTO> facturas) {
        this.facturas = facturas;
    }

    /**
     * @return the metodoDePago
     */
    public MedioDePagoDTO getMetodoDePago() {
        return metodoDePago;
    }

    /**
     * @param metodoDePago the metodoDePago to set
     */
    public void setMetodoDePago(MedioDePagoDTO metodoDePago) {
        this.metodoDePago = metodoDePago;
    }
}
