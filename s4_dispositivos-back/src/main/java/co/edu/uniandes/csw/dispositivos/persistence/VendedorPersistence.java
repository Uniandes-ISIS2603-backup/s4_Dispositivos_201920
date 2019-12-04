/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.dispositivos.persistence;

import co.edu.uniandes.csw.dispositivos.entities.VendedorEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Zharet Bautista Montes
 */
@Stateless
public class VendedorPersistence 
{   
    /**
     * Base de datos donde opera la clase
     */
    @PersistenceContext(unitName = "dispositivosPU")
    protected EntityManager em;
    
    /**
     * Método para agregar un vendedor
     * @param vendedorc
     * @return created Vendedor
     */
    public VendedorEntity create(VendedorEntity vendedorc)
    {
        em.persist(vendedorc);
        return vendedorc; 
    }
    
    /**
     * Método para buscar un vendedor
     * @param vendedorfID
     * @return found Vendedor
     */
    public VendedorEntity find(Long vendedorfID)
    {
        return em.find(VendedorEntity.class, vendedorfID);
    }
    
    /**
     * Método para buscar un vendedor por su cédula
     * @param idCard
     * @return found Vendedor by Cedula
     */
    public VendedorEntity findByCedula(Double idCard)
    {
        TypedQuery<VendedorEntity> tq = em.createQuery("select e from VendedorEntity e where e.cedula = :cedula", VendedorEntity.class);
        tq = tq.setParameter("cedula", idCard); 
        List<VendedorEntity> cedulaFound = tq.getResultList(); 
        return (cedulaFound == null || cedulaFound.isEmpty()) ? null : cedulaFound.get(0);       
    }
    
    /**
     * Método para buscar un vendedor por su usuario
     * @param idUser
     * @return found Vendedor by Usuario
     */
    public VendedorEntity findByUsuario(String idUser)
    {
        TypedQuery<VendedorEntity> tq = em.createQuery("select e from VendedorEntity e where e.usuario = :usuario", VendedorEntity.class);
        tq = tq.setParameter("usuario", idUser); 
        List<VendedorEntity> usuarioFound = tq.getResultList(); 
        return (usuarioFound == null || usuarioFound.isEmpty()) ? null : usuarioFound.get(0);       
    }
    
    /**
     * Método para buscar un vendedor por su correo electrónico
     * @param idEmail
     * @return found Vendedor by Correo Electrónico
     */
    public VendedorEntity findByEmail(String idEmail)
    {
        TypedQuery<VendedorEntity> tq = em.createQuery("select e from VendedorEntity e where e.correoElectronico = :correoElectronico", VendedorEntity.class);
        tq = tq.setParameter("correoElectronico", idEmail); 
        List<VendedorEntity> emailFound = tq.getResultList();
        return (emailFound == null || emailFound.isEmpty()) ? null : emailFound.get(0);       
    }
    
    /**
     * Método para encontrar todos los vendedores
     * @return Vendedores List
     */
    public List<VendedorEntity> findAll()
    {
        Query vrq = em.createQuery("select u from VendedorEntity u");
        return vrq.getResultList();
    }
    
    /**
     * Método para cambiar un vendedor
     * @param vendedoru
     * @return updated Vendedor
     */
    public VendedorEntity update(VendedorEntity vendedoru)
    {
        return em.merge(vendedoru);
    }
    
    /**
     * Método para borrar un vendedor
     * @param vendedordID
     */
    public void delete(Long vendedordID)
    {
        VendedorEntity wantedvr = em.find(VendedorEntity.class, vendedordID); 
        em.remove(wantedvr);
    }   
}