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
 * @author PAULA VELANDIA
 */
@Stateless
public class UsuarioCalificacionesLogic 
{
     private static final Logger LOGGER = Logger.getLogger(UsuarioLogic.class.getName());
    
    @Inject
    private UsuarioPersistence persistence;
    
    @Inject
    private CalificacionPersistence calificacionPersistence;
   
    
    /**
     * Agregar una calificacion al usuario
     *
     * @param calificacionesId El id la calificacion a guardar
     * @param usuariosId El id del usuario en el cual se va a guardar la
     * reserva.
     * @return La calificacion creado.
     */
    public CalificacionEntity addCalificacion(Long calificacionesId, Long usuariosId) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de agregarle una calificacion al usuario con id = {0}", usuariosId);
        UsuarioEntity usuarioEntity = persistence.findUsuario(usuariosId);
        CalificacionEntity calificacionEntity = calificacionPersistence.find(calificacionesId);
        calificacionEntity.setUsuario(usuarioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de agregarle una calificacion al usuario con id = {0}", usuariosId);
        return calificacionEntity;
    }
    
     /**
     * Retorna todos las calificaciones asociadas a un usuario
     *
     * @param usuariosId El ID del usuario buscada
     * @return La lista de calificaciones del usuario
     */
    public List<CalificacionEntity> getCalificaciones(Long usuariosId) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar las calificaciones asociadas al usuario con id = {0}", usuariosId);
        return persistence.findUsuario(usuariosId).getCalificaciones();
    }
    
    /**
     * Retorna una calificacion asociada a un usuario
     *
     * @param usuariosId El id del usuario a buscar.
     * @param calificacionesId El id de la reserva a buscar
     * @return La calificacion encontrada dentro del usuario.
     * @throws BusinessLogicException Si la reserva no se encuentra en el usuario
     */
    public CalificacionEntity getCalificacion(Long usuariosId, Long calificacionesId) throws BusinessLogicException 
    {
        
        List<CalificacionEntity> calificaciones = persistence.findUsuario(usuariosId).getCalificaciones();
        CalificacionEntity calificacionEntity = calificacionPersistence.find(calificacionesId);
        for(int i = 0; i < calificaciones.size(); i++)
        {
            if(calificaciones.get(i).getId().equals(calificacionEntity.getId()))
            {
                return calificaciones.get(i);
            }
        }   
        LOGGER.log(Level.INFO, "Termina proceso de consultar la calificacion");      
        throw new BusinessLogicException("La calificacion no esta asociada a la reserva");
    }
}