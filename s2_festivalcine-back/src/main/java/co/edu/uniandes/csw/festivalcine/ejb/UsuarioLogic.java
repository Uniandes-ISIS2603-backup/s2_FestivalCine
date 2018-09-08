/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.ejb;

import co.edu.uniandes.csw.festivalcine.entities.ReservaEntity;
import co.edu.uniandes.csw.festivalcine.entities.UsuarioEntity;
import co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException;
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
       
        persistence.createUsuario(usuarioEntity);
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
}
