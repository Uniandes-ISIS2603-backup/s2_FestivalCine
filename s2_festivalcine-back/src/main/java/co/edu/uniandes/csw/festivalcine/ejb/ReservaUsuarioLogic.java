/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.ejb;

import co.edu.uniandes.csw.festivalcine.entities.ReservaEntity;
import co.edu.uniandes.csw.festivalcine.entities.UsuarioEntity;
import co.edu.uniandes.csw.festivalcine.persistence.ReservaPersistence;
import co.edu.uniandes.csw.festivalcine.persistence.UsuarioPersistence;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author PAULA VELANDIA
 */
@Stateless
public class ReservaUsuarioLogic 
{
    private static final Logger LOGGER = Logger.getLogger(ReservaLogic.class.getName());

    @Inject
    private ReservaPersistence persistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.
    
    @Inject
    private UsuarioPersistence usuarioPersistence;
    
   
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    /**
     * Borrar una reserva de un usuario. Este metodo se utiliza para borrar la
     * relacion de una reserva.
     *
     * @param reservasId La reserva que se desea borrar del usuario.
     */
    public void removeUsuario(Long reservasId) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el usuario de la reserva con id = {0}", reservasId);
        ReservaEntity reservaEntity = persistence.findReserva(reservasId);
        UsuarioEntity usuarioEntity = usuarioPersistence.findUsuario(reservaEntity.getUsuario().getId());
        reservaEntity.setUsuario(null);
        usuarioEntity.getReservas().remove(reservaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el usuario de la reserva con id = {0}", reservaEntity.getId());
    }
}
