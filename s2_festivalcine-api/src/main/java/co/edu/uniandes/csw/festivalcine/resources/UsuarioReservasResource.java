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
import co.edu.uniandes.csw.festivalcine.mappers.BusinessLogicExceptionMapper;
import co.edu.uniandes.csw.festivalcine.mappers.WebApplicationExceptionMapper;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.WebApplicationException;

/**
 * Clase que implementa el recurso "usuario/{id}/reservas".
 *
 * @author ISIS2603
 * @version 1.0
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioReservasResource 
{
    private static final Logger LOGGER = Logger.getLogger(UsuarioReservasResource.class.getName());

    @Inject
    private UsuarioReservasLogic usuarioReservasLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private ReservaLogic reservaLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.
    
    /**
     * Guarda una reserva dentro de un usuario con la informacion que recibe el
     * la URL. Se devuelve la reserva que se guarda en el usuario.
     *
     * @param usuariosId Identificador del usuario que se esta
     * actualizando. Este debe ser una cadena de dígitos.
     * @param reservasId Identificador de la reserva que se desea guardar. Esta debe
     * ser una cadena de dígitos.
     * @return JSON {@link ReservaDTO} - La reserva guardada en el usuario.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la reserva.
     */
    @POST
    @Path("{reservasId: \\d+}")
    public ReservaDTO addReserva(@PathParam("usuariosId") Long usuariosId, @PathParam("reservasId") Long reservasId) {
        LOGGER.log(Level.INFO, "UsuarioReservasResource addReserva: input: usuariossID: {0} , reservasId: {1}", new Object[]{usuariosId, reservasId});
        if (reservaLogic.getBook(reservasId) == null) {
            throw new WebApplicationException("El recurso /reservas/" + reservasId + " no existe.", 404);
        }
        ReservaDTO reservaDTO = new ReservaDTO(usuarioReservasLogic.addBook(reservasId, usuariosId));
        LOGGER.log(Level.INFO, "UsuarioReservasResource addReserva: output: {0}", reservaDTO.toString());
        return reservaDTO;
    }
    
    /**
     * Busca y devuelve todos las reservas que existen en el usuario.
     *
     * @param usuariosId Identificador del usuario que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSONArray {@link ReservaDetailDTO} - Las reservas encontrados en el
     * usuario. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<ReservaDetailDTO> getReservas(@PathParam("usuariosId") Long usuariosId) {
        LOGGER.log(Level.INFO, "UsuarioReservasResource getReservas: input: {0}", usuariosId);
        List<ReservaDetailDTO> listaDetailDTOs = reservasListEntity2DTO(usuarioReservasLogic.getReservas(usuariosId));
        LOGGER.log(Level.INFO, "UsuarioReservasResource getReservas: output: {0}", listaDetailDTOs.toString());
        return listaDetailDTOs;
    }
    
    /**
     * Busca la reserva con el id asociado dentro del usuario con id asociado.
     *
     * @param usuariosId Identificador de el usuario que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @param reservasId Identificador de la reserva que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link ReservaDetailDTO} - La reserva buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la reserva.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la reserva en el
     * usuario.
     */
    @GET
    @Path("{reservasId: \\d+}")
    public ReservaDetailDTO getReserva(@PathParam("usuariosId") Long usuariosId, @PathParam("reservasId") Long reservasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "UsuarioReservaResource getReserva: input: usuariosID: {0} , reservasId: {1}", new Object[]{usuariosId, reservasId});
        if (reservaLogic.getReserva(reservasId) == null) {
            throw new WebApplicationException("El recurso /usuarios/" + usuariosId + "/reservas/" + reservasId + " no existe.", 404);
        }
        ReservaDetailDTO reservaDetailDTO = new ReservaDetailDTO(usuarioReservasLogic.getReserva(usuariosId, reservasId));
        LOGGER.log(Level.INFO, "UsuarioReservasResource getReserva: output: {0}", reservaDetailDTO.toString());
        return reservaDetailDTO;
    }
    
    //SOLO FALTA EL @PUT 
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * Convierte una lista de ReservaEntity a una lista de ReservaDetailDTO.
     *
     * @param entityList Lista de ReservaEntity a convertir.
     * @return Lista de ReservaDTO convertida.
     */
    private List<ReservaDetailDTO> reservasListEntity2DTO(List<ReservaEntity> entityList) {
        List<ReservaDetailDTO> list = new ArrayList();
        for (ReservaEntity entity : entityList) {
            list.add(new ReservaDetailDTO(entity));
        }
        return list;
    }

    /**
     * Convierte una lista de ReservaDetailDTO a una lista de ReservaEntity.
     *
     * @param dtos Lista de ReservaDetailDTO a convertir.
     * @return Lista de ReservaEntity convertida.
     */
    private List<ReservaEntity> reservasListDTO2Entity(List<ReservaDetailDTO> dtos) {
        List<ReservaEntity> list = new ArrayList<>();
        for (ReservaDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
}
