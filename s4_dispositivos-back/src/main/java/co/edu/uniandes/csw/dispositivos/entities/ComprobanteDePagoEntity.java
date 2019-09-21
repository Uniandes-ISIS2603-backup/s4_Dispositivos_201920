package co.edu.uniandes.csw.dispositivos.entities;
import co.edu.uniandes.csw.dispositivos.podam.DateStrategy;
import co.edu.uniandes.csw.dispositivos.podam.NumeroDeTarjetaStrategy;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamDoubleValue;
import uk.co.jemos.podam.common.PodamIntValue;
import uk.co.jemos.podam.common.PodamStrategyValue;

/**
 *Clase que corresponde a la tabla BD para clase ComprobanteDePago
 * @author Dianis Caro
 */
@Entity 
public class ComprobanteDePagoEntity extends BaseEntity
{
    /**
     * Numero de factura emitido
     */
    @PodamIntValue(minValue = 1, maxValue = Integer.MAX_VALUE)
    private Integer numeroDeFactura;
    /**
     * Total pagado en la transacción por los dispositivos
     */
    @PodamDoubleValue(minValue = 1.0, maxValue = Double.MAX_VALUE)
    private Double totalDePago;
    /**
     * Impuestos generados a los productos
     */
    @PodamDoubleValue(minValue = 1.0, maxValue = Double.MAX_VALUE)
    private Double impuestos;
    /**
     * Número de tarjeta con el cual se genero la compra
     */
    @PodamStrategyValue(NumeroDeTarjetaStrategy.class)
    private String numeroDeTarjeta;
    /**
     * Fecha de compra de los dispositivos electronicos
     */
    @Temporal(TemporalType.DATE)
    @PodamStrategyValue(DateStrategy.class)
    private Date fechaDeFactura;
    /**
     * Constructor vacio para Serializable
     */
    public ComprobanteDePagoEntity()
    { 
        //Clase constructora 
    }
    /**
     * Constructor de la clase
     * @param pNumeroFac numero de factura emtido
     * @param pTotalPago total a pagar por los productos
     * @param pImpuesto impuestos asignados a los productos en caso de importacion
     * @param pNumeroDeTarjeta numero de tarjeta con la cual se genro la compra
     * @param pFechaDeFac fecha de compra de los dispositivos
     */
    public ComprobanteDePagoEntity(Integer pNumeroFac, Double pTotalPago, Double pImpuesto, String pNumeroDeTarjeta, Date pFechaDeFac)
    {
        this.numeroDeFactura = pNumeroFac;
        this.totalDePago = pTotalPago;
        this.impuestos = pImpuesto;
        this.numeroDeTarjeta = pNumeroDeTarjeta;
        this.fechaDeFactura = pFechaDeFac;
    }
    /**
     * Retorna el numero de facturacion
     * @return numero de factura solicitado
     */
    public Integer getNumeroDeFactura() {
        return numeroDeFactura;
    }
    /**
     * Modifica el numero de facturacion
     * @param pNumFac nuevo numero de factura
     */
    public void setNumeroDeFactura(Integer pNumFac) 
    {
        this.numeroDeFactura = pNumFac;
    }
    /**
     * Retorna el total a pagar por los dispositivos
     * @return total a pagar
     */
    public Double getTotalDePago() {
        return totalDePago;
    }
    /**
     * Modifica el total a pgar por los dispositivos
     * @param pTotalPago nuevo total a pagar
     */
    public void setTotalDePago(Double pTotalPago) 
    {
        this.totalDePago = pTotalPago;
    }
    /**
     * Retorna el impuesto generado para los dispositivos
     * @return impuesto de los productos
     */
    public Double getImpuestos() {
        return impuestos;
    }
    /**
     * Modifica el impuesto establecido para los dispositivos
     * @param pImpuestos nuevo impuesto
     */
    public void setImpuestos(Double pImpuestos) 
    {
        this.impuestos = pImpuestos;
    }
    /**
     * Retorna el numero de tarjeta con la cual se genero la compra
     * @return numero de tarjeta
     */
    public String getNumeroDeTarjeta() {
        return numeroDeTarjeta;
    }
    /**
     * Modifica el numero de tarjeta de la compra
     * @param pNumeroT nuevo numero de tarjeta
     */
    public void setNumeroDeTarjeta(String pNumeroT) 
    {
        this.numeroDeTarjeta = pNumeroT;
    }
    /**
     * Retorna la fecha de facturación de los dispositivos
     * @return fecha de facturacion
     */
    public Date getFechaDeFactura() {
        return fechaDeFactura;
    }
    /**
     * Modifica la fecha de facturación de los dispositivos
     * @param pFecha nueva fecha de facturacion
     */
    public void setFechaDeFactura(Date pFecha) 
    {
        this.fechaDeFactura = pFecha;
    }
   /**
     * Metodo no usado 
     * @param obj Object que se compara.
     * @return Despreciado. 
     * @deprecated (Solo Arregla Code Smell)
     */
    @Override
    @Deprecated
    public boolean equals(Object obj){
        return super.equals(obj);
    }
    /**
     * Metodo no usado
     * @return nada.
     * @deprecated (Solo Arregla Code Smell)
     */
    @Override
    @Deprecated
    public int hashCode(){
        return super.hashCode();
    }
}
