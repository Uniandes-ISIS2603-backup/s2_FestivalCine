/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.ejb;

import co.edu.uniandes.csw.festivalcine.entities.ReservaEntity;
import co.edu.uniandes.csw.festivalcine.entities.UsuarioEntity;
import co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.festivalcine.persistence.ReservaPersistence;
import co.edu.uniandes.csw.festivalcine.persistence.UsuarioPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author estudiante
 */
@Stateless
public class UsuarioLogic 
{
    private static final Logger LOGGER = Logger.getLogger(UsuarioLogic.class.getName());

    @Inject
    private UsuarioPersistence persistence;
    
    @Inject
    private ReservaPersistence reservaPersistence;
   
    /**
     * Crea un usuario en la persistencia.
     *
     * @param usuarioEntity La entidad que representa  el usuario a
     * persistir.
     * @return La entiddad del usuario luego de persistirla.
     * @throws BusinessLogicException Si el usuario a persistir ya existe.
     */
    public UsuarioEntity createUsuario(UsuarioEntity usuarioEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del usuario");
       
        if (persistence.findUserByName(usuarioEntity.getNombres()) != null) 
        {
            throw new BusinessLogicException("Ya existe un usuario con el nombre \"" + usuarioEntity.getNombres() + "\"");
        }
       
        usuarioEntity = persistence.createUsuario(usuarioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación del usuario");
        return usuarioEntity;
    }
    
    /**
     *
     * Obtener todas los usuarios existentes en la base de datos.
     *
     * @return una lista de usuario.
     */
    public List<UsuarioEntity> getUsuarios() 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas los usuarios");
        // Note que, por medio de la inyección de dependencias se llama al método "findAll()" que se encuentra en la persistencia.
        List<UsuarioEntity> usuarios = persistence.findAllUsuarios();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los usuarios");
        return usuarios;
    }
    
    /**
     *
     * Obtener un usuario por medio de su id.
     *
     * @param editorialsId: id de la editorial para ser buscada.
     * @return la editorial solicitada por medio de su id.
     */
    public UsuarioEntity getUsuario(Long usuariosId) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el usuario con id = {0}", usuariosId);
        // Note que, por medio de la inyección de dependencias se llama al método "find(id)" que se encuentra en la persistencia.
        UsuarioEntity usuarioEntity = persistence.findUsuario(usuariosId);
        if (usuarioEntity == null) 
        {
            LOGGER.log(Level.SEVERE, "La editorial con el id = {0} no existe", usuariosId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el usuario con id = {0}", usuariosId);
        return usuarioEntity;
    }
    
    /**
     *
     * Actualizar un usuario.
     *
     * @param usuariosId: id del usuario para buscarlo en la base de
     * datos.
     * @param usuarioEntity: usuario con los cambios para ser actualizado,
     * por ejemplo el nombre.
     * @return el usuario con los cambios actualizados en la base de datos.
     */
    public UsuarioEntity updateUsuario(Long usuariosId, UsuarioEntity usuarioEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el usuario con id = {0}", usuariosId);
        // Note que, por medio de la inyección de dependencias se llama al método "update(entity)" que se encuentra en la persistencia.
        UsuarioEntity newEntity = persistence.updateUsuario(usuarioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar un usuario con id = {0}", usuarioEntity.getId());
        return newEntity;
    }
    
    /**
     * Borrar un editorial
     *
     * @param usuariosId: id del usuario a borrar
     * @throws BusinessLogicException Si el usuario a eliminar tiene libros.
     */
    public void deleteUsuario(Long usuariosId) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la editorial con id = {0}", usuariosId);
        // Note que, por medio de la inyección de dependencias se llama al método "delete(id)" que se encuentra en la persistencia.
        List<ReservaEntity> reservas = getUsuario(usuariosId).getReservas();
        if (reservas != null && !reservas.isEmpty()) {
            throw new BusinessLogicException("No se puede borrar el usuario con id = " + usuariosId + " porque tiene reservas asociadas");
        }
        persistence.deleteUsuario(usuariosId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el usuario con id = {0}", usuariosId);
    }
    
    /**
     * LÓGICA DE LA RELACIÓN CON USUARIO
     */
    /**
     * Agregar una reserva al usuario
     *
     * @param reservasId El id la reserva a guardar
     * @param usuariosId El id del usuario en el cual se va a guardar la
     * reserva.
     * @return El libro creado.
     */
    public ReservaEntity addReserva(Long reservasId, Long usuariosId) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de agregarle una reserva al usuario con id = {0}", usuariosId);
        UsuarioEntity usuarioEntity = persistence.findUsuario(usuariosId);
        ReservaEntity reservaEntity = reservaPersistence.findReserva(reservasId);
        reservaEntity.setUsuario(usuarioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de agregarle una reserva al usuario con id = {0}", usuariosId);
        return reservaEntity;
    }
    
     /**
     * Retorna todos las reservas asociadas a un usuario
     *
     * @param usuariosId El ID del usuario buscada
     * @return La lista de reservas del usuario
     */
    public List<ReservaEntity> getReservas(Long usuariosId) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar las reservas asociadas al usuario con id = {0}", usuariosId);
        return persistence.findUsuario(usuariosId).getReservas();
    }
    
    /**
     * Retorna una reserva asociada a un usuario
     *
     * @param usuariosId El id del usuario a buscar.
     * @param reservasId El id de la reserva a buscar
     * @return La reserva encontrada dentro del usuario.
     * @throws BusinessLogicException Si la reserva no se encuentra en el usuario
     */
    public ReservaEntity getReserva(Long usuariosId, Long reservasId) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la reserva con id = {0} del usuario con id = " + usuariosId, reservasId);
        List<ReservaEntity> reservas = persistence.findUsuario(usuariosId).getReservas();
        ReservaEntity reservaEntity = reservaPersistence.findReserva(reservasId);
        
        int index = -1;
        
        if(reservas.indexOf(reservaEntity) >= 0)
        {
            index = reservas.indexOf(reservaEntity);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar la reserva con id = {0} del usuario con id = " + usuariosId, reservasId);
        
        if (index >= 0) 
        {
            return reservas.get(index);
        }
        
        throw new BusinessLogicException("La reserva no está asociada a un usuario");
    }
    
    /**
     * Remplazar reservas de un usuario
     *
     * @param reservas Lista de reservas que serán las del usuario.
     * @param usuariosId El id del usuario que se quiere actualizar.
     * @return La lista de reservas actualizada.
     */
    public List<ReservaEntity> replaceReservas(Long usuariosId, List<ReservaEntity> reservas) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el usuario con id = {0}", usuariosId);
        UsuarioEntity usuarioEntity = persistence.findUsuario(usuariosId);
        
        List<ReservaEntity> reservaList = reservaPersistence.findAllReservas();
        
        for (ReservaEntity reserva : reservaList)
        {
            if (reservas.contains(reserva)) 
            {
                reserva.setUsuario(usuarioEntity);
            } 
            else if (reserva.getUsuario() != null && reserva.getUsuario().equals(usuarioEntity)) 
            {
                reserva.setUsuario(null);
            }
        }
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el usuario con id = {0}", usuariosId);
        return reservas;
    }
}
