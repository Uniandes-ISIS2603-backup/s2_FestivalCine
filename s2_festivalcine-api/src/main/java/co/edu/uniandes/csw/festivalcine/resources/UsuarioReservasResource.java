/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.resources;

import co.edu.uniandes.csw.festivalcine.dtos.ReservaDTO;
import co.edu.uniandes.csw.festivalcine.dtos.ReservaDetailDTO;
import co.edu.uniandes.csw.festivalcine.ejb.ReservaLogic;
import co.edu.uniandes.csw.festivalcine.ejb.UsuarioReservasLogic;
import co.edu.uniandes.csw.festivalcine.entities.ReservaEntity;
import co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author PAULA VELANDIA
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioReservasResource 
{
    private static final Logger LOGGER = Logger.getLogger(UsuarioReservasResource.class.getName());

    @Inject
    private UsuarioReservasLogic usuarioReservasLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private ReservaLogic reservaLogic;

    /**
     * Guarda una reserva dentro de un usuario con la informacion que recibe el
     * la URL. Se devuelve la reserva que se guarda en el usuario.
     *
     * @param usuariosId Identificador del usuarui que se esta
     * actualizando. Este debe ser una cadena de dígitos.
     * @param reservasId Identificador de la reserva que se desea guardar. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link ReservaDTO} - La reserva guardado en el usuario.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la reserva.
     */
    @POST
    @Path("{reservasId: \\d+}")
    public ReservaDTO addReserva(@PathParam("usuariosId") Long usuariosId, @PathParam("reservasId") Long reservasId) 
    {
        LOGGER.log(Level.INFO, "UsuarioResource addReserva: input: usuariosID: {0} , reservasId: {1}", new Object[]{usuariosId, reservasId});
        if (reservaLogic.getReserva(reservasId) == null) 
        {
            throw new WebApplicationException("El recurso /reservas/" + reservasId + " no existe.", 404);
        }
        ReservaDTO reservaDTO = new ReservaDTO(usuarioReservasLogic.addReserva(reservasId, usuariosId));
        LOGGER.log(Level.INFO, "Usuario addReserva: output: {0}", reservaDTO);
        return reservaDTO;
    }
    
    /**
     * Busca y devuelve todos los reservas que existen en el usuario.
     *
     * @param usuariosId Identificador del usuario que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSONArray {@link ReservaDetailDTO} - Las reservas del usuario.
     * Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<ReservaDetailDTO> getReservas(@PathParam("usuariosId") Long usuariosId) 
    {
        LOGGER.log(Level.INFO, "UsuarioReservasResource getReservas: input: {0}", usuariosId);
        List<ReservaDetailDTO> listaDetailDTOs = reservasListEntity2DTO(usuarioReservasLogic.getReservas(usuariosId));
        LOGGER.log(Level.INFO, "UsuarioReservasResource getReservas: output: {0}", listaDetailDTOs);
        return listaDetailDTOs;
    }
    
     /**
     * Busca la reserva con el id asociado dentro de un usuario con id asociado.
     *
     * @param usuariosId Identificador del usuario que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @param reservasId Identificador de la reserca que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link ReservaDetailDTO} - El libro buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la reserva.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la reserva en la
     * usuaria .
     */
    @GET
    @Path("{reservasId: \\d+}")
    public ReservaDetailDTO getReserva(@PathParam("usuariosId") Long usuariosId, @PathParam("reservasId") Long reservasId) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "UsuarioResource getReserva: input: usuariosID: {0} , reservasId: {1}", new Object[]{usuariosId, reservasId});
        if (reservaLogic.getReserva(reservasId) == null) 
        {
            throw new WebApplicationException("El recurso /usuarios/" + usuariosId + "/reservas/" + reservasId + " no existe.", 404);
        }
        ReservaDetailDTO reservaDetailDTO = new ReservaDetailDTO(usuarioReservasLogic.getReserva(usuariosId, reservasId));
        LOGGER.log(Level.INFO, "EditorialBooksResource getBook: output: {0}", reservaDetailDTO);
        return reservaDetailDTO;
    }
    
       
    /**
     * Convierte una lista de ReservaEntity a una lista de ReservaDetailDTO.
     *
     * @param entityList Lista de ReservaEntity a convertir.
     * @return Lista de ReservaDTO convertida.
     */
    private List<ReservaDetailDTO> reservasListEntity2DTO(List<ReservaEntity> entityList) 
    {
        List<ReservaDetailDTO> list = new ArrayList();
        for (ReservaEntity entity : entityList) 
        {
            list.add(new ReservaDetailDTO(entity));
        }
        return list;
    }
}
