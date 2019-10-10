package co.edu.uniandes.csw.dispositivos.entities;
import co.edu.uniandes.csw.dispositivos.podam.CorreoWirelessStrategy;
import java.io.Serializable;
import javax.persistence.Entity;
import uk.co.jemos.podam.common.PodamStrategyValue;

/**
 *Clase que corresponde a la tabla BD para clase Administrador
 * @author Dianis Caro
 */
@Entity
public class AdministradorEntity extends BaseEntity implements Serializable 
{
    /**
     * Usuario de identificacion en la plataforma
     */
    private String usuario;
    /**
     * ContraseNa de verificacion en la plataforma
     */
    private String contrasena;
    /**
     * Correo institucional
     */
    @PodamStrategyValue(CorreoWirelessStrategy.class)
    private String correo;
    /**
     * Constructor vacio para Serializable
     */
    public AdministradorEntity()
    {
       //Clase constructora 
    }
    /**
     * Constructor de la clase
     * @param pUsuario usuario de indetificacion
     * @param pContrasena contrasena de verificacion
     */
    public AdministradorEntity(String pUsuario, String pContrasena, String pCorreo)
    {
        this.usuario = pUsuario;
        this.correo = pCorreo;
        this.contrasena = pContrasena;
    }
    /**
     * Retorna el usuario de identificacion
     * @return usuario
     */
    public String getUsuario()
    {
        return usuario;
    }
    /**
     * Modifica el usuario de identificacion
     * @param pUsuario nuevo usuario
     */
    public void setUsuario(String pUsuario) 
    {
        this.usuario = pUsuario;
    }
    /**
     * Retorna la contraseNa de verificacion
     * @return contraseNa
     */
    public String getContrasena()
    {
        return contrasena;
    }
    /**
     * Modifica la contrseNa de verificacion
     * @param pContrasena nueva contraseNa
     */
    public void setContrasena(String pContrasena) 
    {
        this.contrasena = pContrasena;
    } 
    /**
     * Retorna la correo
     * @return correo
     */
    public String getCorreo()
    {
        return correo;
    }
    /**
     * Modifica la correo
     * @param pCorreo nuevo correo
     */
    public void setCorreo(String pCorreo) 
    {
        this.correo = pCorreo;
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
