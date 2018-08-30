/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.resources;

import co.edu.uniandes.csw.festivalcine.dtos.ReservaDTO;
import co.edu.uniandes.csw.festivalcine.ejb.ReservaLogic;
import co.edu.uniandes.csw.festivalcine.entities.ReservaEntity;
import co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.festivalcine.mappers.BusinessLogicExceptionMapper;
import co.edu.uniandes.csw.festivalcine.mappers.WebApplicationExceptionMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import co.edu.uniandes.csw.festivalcine.ejb.ReservaLogic;
import javax.inject.Inject;
import javax.ws.rs.Produces;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.WebApplicationException;

import co.edu.uniandes.csw.festivalcine.dtos.ReservaDTO;

/**
 *
 * @author estudiante
 */

@Path("reservas")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ReservaResource 
{
    private static final Logger LOGGER = Logger.getLogger(ReservaResource.class.getName());
    
    @Inject
    ReservaLogic reservaLogic;
    
    
    
  /**
     * Crea una nueva reserva con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * @param reserva {@link ReservaDTO} - El usuario que se desea
     * guardar.
     * @return JSON {@link ReservaDTO} - El usuario guardado con el atributo
     * id autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe la reserva.
     */
    @POST
    public ReservaDTO createReserva(ReservaDTO reserva) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "ReservaResource createReserva: input: {0}", reserva.toString());
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        ReservaEntity reservaEntity = reserva.toEntity();
        // Invoca la lógica para crear la editorial nueva
        ReservaEntity nuevaReservaEntity = reservaLogic.createReserva(reservaEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
       ReservaDTO nuevaReservaDTO = new ReservaDTO(nuevaReservaEntity);
        LOGGER.log(Level.INFO, "ReservaResource createReserva: output: {0}", nuevaReservaDTO.toString());
        return nuevaReservaDTO;
    }
    
   /**
     * Busca y devuelve todas las reservas que existen en la aplicacion.
     *
     * @return JSONArray {@link ReservaDTO} - Las reservas encontradas en
     * la aplicación. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<ReservaDTO> getReservas() {
        LOGGER.info("ReservaResource getReservas: input: void");
        List<ReservaDTO> listaReservas = listEntity2DetailDTO(reservaLogic.getReservas());
        LOGGER.log(Level.INFO, "ReservaResource getReservas: output: {0}", listaReservas.toString());
        return listaReservas;
    }

    /**
     * Busca la reserva con el id asociado recibido en la URL y la devuelve.
     *
     * @param reservasId Identificador de la reserva que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link ReservaDTO} - La reserva buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la reserva.
     */
    @GET
    @Path("{reservasId: \\d+}")
    public ReservaDTO getReserva(@PathParam("reservassId") Long reservasId) throws WebApplicationException {
        LOGGER.log(Level.INFO, "ReservaResource getReserva: input: {0}", reservasId);
        ReservaEntity reservaEntity = reservaLogic.getReserva(reservasId);
        if (reservaEntity == null) {
            throw new WebApplicationException("El recurso /reservas/" + reservasId + " no existe.", 404);
        }
        ReservaDTO detailDTO = new ReservaDTO(reservaEntity);
        LOGGER.log(Level.INFO, "ReservaResource getReserva: output: {0}", detailDTO.toString());
        return detailDTO;
    }

    /**
     * Actualiza la reserva con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param reservasId Identificador de la reserva que se desea
     * actualizar. Este debe ser una cadena de dígitos.
     * @param reserva {@link ReservaDTO} La reserva que se desea guardar.
     * @return JSON {@link ReservaDTO} - La reserva guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la reserva a
     * actualizar.
     */
    @PUT
    @Path("{reservasId: \\d+}")
    public ReservaDTO updateReserva(@PathParam("reservasId") Long reservasId, ReservaDTO reserva) throws WebApplicationException {
        LOGGER.log(Level.INFO, "ReservaResource updateReserva: input: id:{0} , reserva: {1}", new Object[]{reservasId, reserva.toString()});
        reserva.setId(reservasId);
        if (reservaLogic.getReserva(reservasId) == null) {
            throw new WebApplicationException("El recurso /reservas/" + reservasId + " no existe.", 404);
        }
        ReservaDTO detailDTO = new ReservaDTO(reservaLogic.updateEditorial(reservasId, reserva.toEntity()));
        LOGGER.log(Level.INFO, "EditorialResource updateEditorial: output: {0}", detailDTO.toString());
        return detailDTO;
    }

    /**
     * Borra la reserva con el id asociado recibido en la URL.
     *
     * @param reservasId Identificador de la reserva que se desea borrar.
     * Este debe ser una cadena de dígitos.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar la reserva.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la reserva.
     */
    @DELETE
    @Path("{reservasId: \\d+}")
    public void deleteReserva(@PathParam("reservasId") Long reservasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ReservaResource deleteReserva: input: {0}", reservasId);
        if (reservaLogic.getReserva(reservasId) == null) {
            throw new WebApplicationException("El recurso /reservas/" + reservasId + " no existe.", 404);
        }
        reservaLogic.deleteReserva(reservasId);
        LOGGER.info("ReservaResource deleteReserva: output: void");
    }

    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos EditorialEntity a una lista de
     * objetos EditorialDTO (json)
     *
     * @param entityList corresponde a la lista de editoriales de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de editoriales en forma DTO (json)
     */
    private List<ReservaDTO> listEntity2DetailDTO(List<ReservaEntity> entityList) {
        List<ReservaDTO> list = new ArrayList<>();
        for (ReservaEntity entity : entityList) {
            list.add(new ReservaDTO(entity));
        }
        return list;
    }
}
