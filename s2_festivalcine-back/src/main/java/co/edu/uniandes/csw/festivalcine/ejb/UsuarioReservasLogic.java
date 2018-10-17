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
 * @author PAULA VELANDIA
 */
@Stateless
public class UsuarioReservasLogic {
    
    private static final Logger LOGGER = Logger.getLogger(UsuarioLogic.class.getName());
    
    @Inject
    private UsuarioPersistence persistence;
    
    @Inject
    private ReservaPersistence reservaPersistence;
    
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
         int index = reservas.indexOf(reservaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar la reserva con id = {0} del usuario con id = " + usuariosId, reservasId);
        if (index >= 0) {
            return reservas.get(index);
        }
        throw new BusinessLogicException("La reserva no esta asociada al usuario");
    }
}
