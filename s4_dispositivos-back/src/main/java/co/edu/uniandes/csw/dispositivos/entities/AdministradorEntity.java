package co.edu.uniandes.csw.dispositivos.entities;
import javax.persistence.Entity;

/**
 *Clase que corresponde a la tabla BD para clase Administrador
 */
@Entity
public class AdministradorEntity extends BaseEntity
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
     * Constructor vacio para Serializable
     */
    public AdministradorEntity()
    {}
    /**
     * Constructor de la clase
     * @param pUsuario usuario de indetificacion
     * @param pContrasena contrasena de verificacion
     */
    public AdministradorEntity(String pUsuario, String pContrasena)
    {
        this.usuario = pUsuario;
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
    
    @Override
    public boolean equals(Object obj)
    {
        boolean resp = super.equals(this);
        final AdministradorEntity other = (AdministradorEntity) obj;
        
        if(!resp)
        {
            return false;
        }
        else
        {
            if(this.usuario.equalsIgnoreCase(other.usuario))
            {
                if(this.contrasena.equals(other.contrasena))
                {
                    return true;
                }
            }
            return false;
        }
    }
}
