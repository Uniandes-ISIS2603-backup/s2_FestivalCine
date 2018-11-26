/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.ejb;

import co.edu.uniandes.csw.festivalcine.entities.CalificacionEntity;
import co.edu.uniandes.csw.festivalcine.entities.UsuarioEntity;
import co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.festivalcine.persistence.CalificacionPersistence;
import co.edu.uniandes.csw.festivalcine.persistence.UsuarioPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Andres Felipe Rodriguez Murillo
 */
@Stateless
public class CalificacionLogic 
{
    private static final Logger LOGGER = Logger.getLogger(CalificacionLogic.class.getName());
    
    @Inject
    private CalificacionPersistence persistence;
    
    @Inject
    private UsuarioPersistence usuarioPersistence;
    
    /**
     * Crea una nueva calificacion con la información que se recibe en el cuerpo
     * de la peticion y se regresa un objeto con la id-autogenerada por la base de
     * datos
     * 
     * @param entity {@link CalificacionDTO} - La calificacion que se desea guardar.
     * @return JSON {@link CalificacionDTO} - La calificacion guardada con ek atributo id
     */
    public CalificacionEntity createCalificacion(CalificacionEntity entity)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de creacion de la calificacion");
        persistence.create(entity);
        LOGGER.log(Level.INFO, "Termina proceso de creacion de la calificacion");
        return entity;
    }
    
    /**
     * Obtiene una colección de instancias de CalificacionEntity
     * 
     * @return Lista de entidades de tipo calificacion. 
     */
    public List<CalificacionEntity> getCalificaciones()
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos las calificaciones");
        List<CalificacionEntity> calificaciones = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las calificaciones");
        return calificaciones;
    }
    
    public CalificacionEntity getCalificacion(Long calificacionesId) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la calificacion con id = {0}", calificacionesId);
        CalificacionEntity calificacionEntity = persistence.find(calificacionesId);
        if(calificacionEntity == null)
        {
            throw new BusinessLogicException("La calificacion no existe");
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar la calificacion con id = {0}", calificacionesId);
        return calificacionEntity;
    }
    
    public CalificacionEntity updateCalificacion(CalificacionEntity calificacionEntity) throws BusinessLogicException
    {
        
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la calificacion con id = {0}", calificacionEntity.getId());
        if(validateCalificacion(calificacionEntity.getId()))
        {
            throw new BusinessLogicException("La calificacion es inválido");
        }
        CalificacionEntity newEntity = persistence.update(calificacionEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la calificacion con el id = {0}", calificacionEntity.getId());
        return newEntity;
    }
    
    public void deleteCalificacion(Long calificacionesId)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la calificacion con id = {0}", calificacionesId);
        persistence.delete(calificacionesId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la calificacion con id = {0}", calificacionesId);
    }
    
    public UsuarioEntity addUsuario(Long calificacionesId, Long usuariosId) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia proceso de asociarle el usuario a la calificacion con id = {0}", calificacionesId);
        UsuarioEntity usuarioEntity = usuarioPersistence.findUsuario(usuariosId);
        if(usuarioEntity == null)
        {
            persistence.delete(calificacionesId);
            throw new BusinessLogicException("El usuario no existe");
        }
        CalificacionEntity calificacionEntity = persistence.find(calificacionesId);
        if(calificacionEntity.getUsuario() != null)
        {
            throw new BusinessLogicException("La calificacion ya tiene un usuario asignado");
        }
        List<CalificacionEntity> lista = usuarioEntity.getCalificaciones();
        lista.add(calificacionEntity);
        usuarioEntity.setCalificaciones(lista);
        LOGGER.log(Level.INFO, "Termina proceso de asociarle el usuario a la calificacion con id = {0}", calificacionesId);
        return usuarioPersistence.findUsuario(usuariosId);
    }
    
    public UsuarioEntity getUsuario(Long calificacionesId)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el usuario del critico con id = {0}", calificacionesId);
        return persistence.find(calificacionesId).getUsuario();

    }
    
    public boolean validateCalificacion(Long calificacionesId)
    {
         return (calificacionesId == null || calificacionesId <= 0 || persistence.find(calificacionesId) == null );
       
    }
    
    
}
