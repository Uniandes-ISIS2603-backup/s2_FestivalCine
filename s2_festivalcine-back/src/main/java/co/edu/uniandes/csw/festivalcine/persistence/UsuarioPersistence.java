/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.persistence;

import co.edu.uniandes.csw.festivalcine.entities.UsuarioEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author PAULA VELANDIA
 */
@Stateless
public class UsuarioPersistence 
{
    private static final Logger LOGGER = Logger.getLogger(UsuarioPersistence.class.getName());
    
    @PersistenceContext(unitName = "TarantinoPU")
    protected EntityManager em;
    
    /**
     * Metodo que crea un usuario a partir de una entidad
     * @param usuarioEntity
     * @return 
     */
    public UsuarioEntity createUsuario(UsuarioEntity usuarioEntity)
    {
        LOGGER.log(Level.INFO, "Creando un usuario nuevo");
        em.persist(usuarioEntity);
        LOGGER.log(Level.INFO, "Saliendo de creando un usuario nuevo");
        return usuarioEntity;
    }
    
    /**
     * Método que encuentra todos los usuarios de la aplicacion
     * @return 
     */
    public List<UsuarioEntity> findAllUsuarios() 
    {
        LOGGER.log(Level.INFO, "Consultando todas los usuarios");
        TypedQuery query = em.createQuery("select u from UsuarioEntity u", UsuarioEntity.class);
        return query.getResultList();
    }
    
    /**
     * Metodo que encuentra un usuario
     * @param usuariosId
     * @return 
     */
     public UsuarioEntity findUsuario(Long usuariosId) 
     {
        LOGGER.log(Level.INFO, "Consultando usuario con id={0}", usuariosId);
        return em.find(UsuarioEntity.class, usuariosId);
    }
    
     /**
      * Metodo que actualiza un usuario
      * @param usuarioEntity
      * @return 
      */
    public UsuarioEntity updateUsuario(UsuarioEntity usuarioEntity) 
    {
        LOGGER.log(Level.INFO, "Actualizando usuario con id = {0}", usuarioEntity.getId());
        LOGGER.log(Level.INFO, "Saliendo de actualizar lel usuario con id = {0}", usuarioEntity.getId());
        return em.merge(usuarioEntity);
    }
    
    /**
     * Metodo que elimina un usuario
     * @param usuariosId 
     */
     public void deleteUsuario(Long usuariosId) 
     {
        LOGGER.log(Level.INFO, "Borrando el usuario con id = {0}", usuariosId);
        UsuarioEntity entity = em.find(UsuarioEntity.class, usuariosId);
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar el usuario con id = {0}", usuariosId);
    }
    
     /**
      * Metodo que encuentra un usuario por su nombre
      * @param name
      * @return 
      */
     public UsuarioEntity findUserByName(String name) 
     {
        LOGGER.log(Level.INFO, "Consultando usuario por nombre ", name);
        TypedQuery query = em.createQuery("Select e From UsuarioEntity e where e.nombres = :nombres", UsuarioEntity.class); 
        query = query.setParameter("nombres", name);
        List<UsuarioEntity> sameName = query.getResultList();
        UsuarioEntity result;
        if (sameName == null) 
        {
            result = null;
        } 
        else if (sameName.isEmpty()) 
        {
            result = null;
        } 
        else 
        {
            result = sameName.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar usuario por nombre ", name);
        return result;
    }
}
