/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.resources;

import co.edu.uniandes.csw.festivalcine.dtos.CalificacionDTO;
import co.edu.uniandes.csw.festivalcine.dtos.ReservaDetailDTO;
import co.edu.uniandes.csw.festivalcine.dtos.UsuarioDetailDTO;
import co.edu.uniandes.csw.festivalcine.ejb.CalificacionLogic;
import co.edu.uniandes.csw.festivalcine.ejb.ReservaLogic;
import co.edu.uniandes.csw.festivalcine.ejb.UsuarioCalificacionesLogic;
import co.edu.uniandes.csw.festivalcine.entities.CalificacionEntity;
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
public class UsuarioCalificacionesResource 
{
    private static final Logger LOGGER = Logger.getLogger(UsuarioReservasResource.class.getName());

    @Inject
    private UsuarioCalificacionesLogic usuarioCalificacionesLogic; 

    @Inject
    private CalificacionLogic calificacionLogic;
    
     /**
     * Guarda una calificacion dentro de un usuario con la informacion que recibe el
     * la URL. Se devuelve la calificacion que se guarda en el usuario.
     *
     * @param usuariosId Identificador del usuarui que se esta
     * actualizando. Este debe ser una cadena de dígitos.
     * @param calificacionesId Identificador de la calificacion que se desea guardar. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link CalificacionDTO} - La calificacion guardado en el usuario.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la calificacion.
     */
    @POST
    @Path("{calificacionId: \\d+}")
    public CalificacionDTO addCalificacion(@PathParam("usuariosId") Long usuariosId, @PathParam("calificacionesId") Long calificacionId) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "UsuarioCalificacionesResource addCalificacion: input: usuariosID: {0} , calificacionsId: {1}", new Object[]{usuariosId, calificacionId});
        if (calificacionLogic.getCalificacion(calificacionId) == null) 
        {
            throw new WebApplicationException("El recurso /calificaciones/" + calificacionId + " no existe.", 404);
        }
        CalificacionDTO calificacionDTO = new CalificacionDTO(usuarioCalificacionesLogic.addCalificacion(calificacionId, usuariosId));
        LOGGER.log(Level.INFO, "UsuarioCalificacionesResource addCalificacion: output: {0}", calificacionDTO.toString());
        return calificacionDTO;
    }
    
     /**
     * Método que retorna las calificaciones de un usuario
     * @param usuariosId
     * @return lista de las calificaciones correspondientes al usuario ingresado por parametro
     */
    @GET
    @Path("{usuariosId: \\d+}/calificaciones")
    public List<CalificacionDTO> getCalificaciones(@PathParam("usuariosId") Long usuariosId) 
    {
        LOGGER.log(Level.INFO, "UsuarioCalificacionesResource getCalificaciones: input: {0}", usuariosId);
        List<CalificacionDTO> listaDTOs = calificacionesListEntity2DTO(usuarioCalificacionesLogic.getCalificaciones(usuariosId));
        LOGGER.log(Level.INFO, "UsuarioCalificacionesResource getCalificaciones: output: {0}", listaDTOs.toString());
        return listaDTOs;    
    }
    
     /**
     * Busca la calificacion con el id asociado dentro de un usuario con id asociado.
     *
     * @param usuariosId Identificador del usuario que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @param calificacionId Identificador de la calificacion que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link CalificacionDetailDTO} - La calificacion buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la calificacion.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la calificacion en la
     * usuaria .
     */
    @GET
    @Path("{calificacionId: \\d+}")
    public CalificacionDTO getCalificacion(@PathParam("usuariosId") Long usuariosId, @PathParam("calificacionId") Long calificacionId) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "UsuarioResource getReserva: input: usuariosID: {0} , calificacionId: {1}", new Object[]{usuariosId, calificacionId});
        if (calificacionLogic.getCalificacion(calificacionId) == null) 
        {
            throw new WebApplicationException("El recurso /usuarios/" + usuariosId + "/calificaciones/" + calificacionId + " no existe.", 404);
        }
        CalificacionDTO calificacionDTO = new CalificacionDTO(usuarioCalificacionesLogic.getCalificacion(usuariosId, calificacionId));
        LOGGER.log(Level.INFO, "UsuarioResource getCalificacion: output: {0}", calificacionDTO.toString());
        return calificacionDTO;
    }
    
    /**
    * Convierte una lista de ReservaEntity a una lista de ReservaDetailDTO.
    * @param entityList Lista de ReservaEntity a convertir.
     * @return Lista de ReservaDTO convertida.
     */
    private List<CalificacionDTO> calificacionesListEntity2DTO(List<CalificacionEntity> entityList) 
    {
        List<CalificacionDTO> list = new ArrayList();
        for (CalificacionEntity entity : entityList) 
        {
            list.add(new CalificacionDTO(entity));
        }
        return list;
    }
    
}
