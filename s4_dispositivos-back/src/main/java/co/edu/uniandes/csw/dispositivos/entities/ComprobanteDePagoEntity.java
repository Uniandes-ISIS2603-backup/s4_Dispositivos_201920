package co.edu.uniandes.csw.dispositivos.entities;
import java.util.Date;
import javax.persistence.Entity;

/**
 *Clase que corresponde a la tabla BD para clase ComprobanteDePago
 */
@Entity 
public class ComprobanteDePagoEntity extends BaseEntity
{
    /**
     * Numero de factura emitido
     */
    private int numeroDeFactura;
    /**
     * Total pagado en la transacción por los dispositivos
     */
    private double totalPago;
    /**
     * Impuestos generados a los productos
     */
    private double impuestos;
    /**
     * Número de tarjeta con el cual se genero la compra
     */
    private String numeroDeTarjeta;
    /**
     * Fecha de compra de los dispositivos electronicos
     */
    private Date fechaDeFactura;
    /**
     * Constructor vacio para Serializable
     */
    public ComprobanteDePagoEntity()
    { 
    }
    /**
     * Constructor de la clase
     * @param pNumeroFac numero de factura emtido
     * @param pTotalPago total a pagar por los productos
     * @param pImpuesto impuestos asignados a los productos en caso de importacion
     * @param pNumeroDeTarjeta numero de tarjeta con la cual se genro la compra
     * @param pFechaDeFac fecha de compra de los dispositivos
     */
    public ComprobanteDePagoEntity(int pNumeroFac, double pTotalPago, double pImpuesto, String pNumeroDeTarjeta, Date pFechaDeFac)
    {
        this.numeroDeFactura = pNumeroFac;
        this.totalPago = pTotalPago;
        this.impuestos = pImpuesto;
        this.numeroDeTarjeta = pNumeroDeTarjeta;
        this.fechaDeFactura = pFechaDeFac;
    }
    /**
     * Retorna el numero de facturacion
     * @return numero de factura solicitado
     */
    public int getNumeroDeFactura() {
        return numeroDeFactura;
    }
    /**
     * Modifica el numero de facturacion
     * @param pNumFac nuevo numero de factura
     */
    public void setNumeroDeFactura(int pNumFac) 
    {
        this.numeroDeFactura = pNumFac;
    }
    /**
     * Retorna el total a pagar por los dispositivos
     * @return total a pagar
     */
    public double getTotalDePago() {
        return totalPago;
    }
    /**
     * Modifica el total a pgar por los dispositivos
     * @param pTotalPago nuevo total a pagar
     */
    public void setTotalDePago(double pTotalPago) 
    {
        this.totalPago = pTotalPago;
    }
    /**
     * Retorna el impuesto generado para los dispositivos
     * @return impuesto de los productos
     */
    public double getImpuestos() {
        return impuestos;
    }
    /**
     * Modifica el impuesto establecido para los dispositivos
     * @param pImpuestos nuevo impuesto
     */
    public void setImpuestos(double pImpuestos) 
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
     * Evalua un objeto con el actual
     * @param obj Objecto a comparar
     * @return true cuando ambos objetos son iguales, false de lo contrario
     */
    @Override
    public boolean equals(Object obj)
    {
        final ComprobanteDePagoEntity other = (ComprobanteDePagoEntity) obj;
        boolean resp = super.equals(other);
        
        if(!resp)
        {
            return false;
        }
        else
        {
            if(this.fechaDeFactura.equals(other.fechaDeFactura)&& this.impuestos == other.impuestos && this.numeroDeFactura == other.numeroDeFactura && numeroDeTarjeta.equalsIgnoreCase(other.numeroDeTarjeta))
                        return true;
            
            return false;
        }
    }
}
