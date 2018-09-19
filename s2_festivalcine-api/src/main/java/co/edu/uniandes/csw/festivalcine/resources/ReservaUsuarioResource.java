/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.resources;

import co.edu.uniandes.csw.festivalcine.dtos.ReservaDetailDTO;
import co.edu.uniandes.csw.festivalcine.dtos.UsuarioDTO;
import co.edu.uniandes.csw.festivalcine.ejb.ReservaLogic;
import co.edu.uniandes.csw.festivalcine.ejb.ReservaUsuarioLogic;
import co.edu.uniandes.csw.festivalcine.ejb.UsuarioLogic;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author PAULA VELANDIA
 */
@Path("reservas/{reservasId: \\d+}/usuario")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ReservaUsuarioResource 
{
  private static final Logger LOGGER = Logger.getLogger(ReservaUsuarioResource.class.getName());

    @Inject
    private ReservaLogic reservaLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private ReservaUsuarioLogic reservaUsuarioLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private UsuarioLogic usuarioLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Remplaza la instancia de Usuario asociada a una Reserva.
     *
     * @param reservasId Identificador de la reserva que se esta actualizando. Este
     * debe ser una cadena de dígitos.
     * @param usuario El usuario que se será de la  reserva.
     * @return JSON {@link ReservaDetailDTO} - El arreglo de reservas guardado en el
     * usuario.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el usuario de
     * la reserva.
     */
    @PUT
    public ReservaDetailDTO replaceUsuario(@PathParam("reservasId") Long reservasId, UsuarioDTO usuario) 
    {
        LOGGER.log(Level.INFO, "ReservaUsuarioResource replaceUsuario: input: reservasId{0} , Usuario:{1}", new Object[]{reservasId, usuario.toString()});
        if (reservaLogic.getReserva(reservasId) == null) {
            throw new WebApplicationException("El recurso /reservas/" + reservasId + " no existe.", 404);
        }
        if (usuarioLogic.getUsuario(usuario.getId()) == null) {
            throw new WebApplicationException("El recurso /usuarios/" + usuario.getId() + " no existe.", 404);
        }
        ReservaDetailDTO reservaDetailDTO = new ReservaDetailDTO(reservaUsuarioLogic.replaceUsuario(reservasId, usuario.getId()));
        LOGGER.log(Level.INFO, "ReservaUsuarioResource replaceUsuario: output: {0}", reservaDetailDTO.toString());
        return reservaDetailDTO;
    }   
}
