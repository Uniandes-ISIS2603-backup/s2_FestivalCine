/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.resources;

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
import co.edu.uniandes.csw.festivalcine.dtos.ReservaDetailDTO;



/**
 *
 * @author PAULA VELANDIA
 */

@Path("reservas")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ReservaResource 
{
    String elRecursoReservas = "El recurso /reservas/";
    String noexiste = "no existe";
    private static final Logger LOGGER = Logger.getLogger(ReservaResource.class.getName());
    
    
    @Inject
    private ReservaLogic reservaLogic; 
     
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
        LOGGER.log(Level.INFO, "ReservaResource createReserva: input: {0}", reserva);
        ReservaDTO nuevaReservaDTO = new ReservaDTO(reservaLogic.createReserva(reserva.toEntity()));
        LOGGER.log(Level.INFO, "ReservaResource createReserva: output: {0}", nuevaReservaDTO);
        return nuevaReservaDTO;
    }
    
   /**
     * Busca y devuelve todas las reservas que existen en la aplicacion.
     *
     * @return JSONArray {@link ReservaDTO} - Las reservas encontradas en
     * la aplicación. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<ReservaDetailDTO> getReservas() 
    {
        LOGGER.info("ReservaResource getReservas: input: void");
        List<ReservaDetailDTO> listaReservas = listEntity2DetailDTO(reservaLogic.getReservas());
        LOGGER.log(Level.INFO, "ReservaResource getReservas: output: {0}", listaReservas);
        return listaReservas;
    }

    /**
     * Busca la reserva con el id asociado recibido en la URL y la devuelve.
     * @param reservasId Identificador de la reserva que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link ReservaDTO} - La reserva buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la reserva.
     */
    @GET
    @Path("{reservasId: \\d+}")
    public ReservaDetailDTO getReserva(@PathParam("reservasId") Long reservasId)
    {   
       LOGGER.log(Level.INFO, "ReservaResource getReserva: input: {0}", reservasId);
        ReservaEntity reservaEntity = reservaLogic.getReserva(reservasId);
        if (reservaEntity == null) {
            throw new WebApplicationException(elRecursoReservas + reservasId + noexiste, 404);
        }
        ReservaDetailDTO reservaDetailDTO = new ReservaDetailDTO(reservaEntity);
        LOGGER.log(Level.INFO, "ReservaResource getReserva: output: {0}", reservaDetailDTO);
        return reservaDetailDTO;
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
    public ReservaDTO updateReserva(@PathParam("reservasId") Long reservasId, ReservaDTO reserva)
    {
        LOGGER.log(Level.INFO, "ReservaResource updateReserva: input: id: {0} , reserva: {1}", new Object[]{reservasId, reserva});
        reserva.setId(reservasId);
        ReservaDetailDTO detailDTO = null;
        if (reservaLogic.getReserva(reservasId) == null) 
        {
            throw new WebApplicationException(elRecursoReservas + reservasId + noexiste, 404);
        }
        detailDTO = new ReservaDetailDTO(reservaLogic.updateReserva(reservasId, reserva.toEntity()));
        LOGGER.log(Level.INFO, "ReservaResource updateReserva: output: {0}", detailDTO);
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
        ReservaEntity entity = reservaLogic.getReserva(reservasId);
        if (entity == null) 
        {
            throw new WebApplicationException(elRecursoReservas + reservasId + noexiste, 404);
        }
        reservaLogic.deleteReserva(reservasId);
        LOGGER.info("ReservaResource deleteReserva: output: void");        
    }
   
    /**
     * Conexión con el servicio de funciones para una reserva.
     * {@link ReservaResource}
     *
     * Este método conecta la ruta de /reservas con las rutas de /funciones que
     * dependen de la reserva, es una redirección al servicio que maneja el
     * segmento de la URL que se encarga de las funciones de una reserva.
     *
     * @param reservasId El ID de la funcion con respecto a la cual se
     * accede al servicio.
     * @return El servicio de funciones para esta reserva en paricular.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la reserva.
     */
    @Path("{reservasId: \\d+}/funciones")
    public Class<ReservaFuncionResource> getReservaFuncionesResource(@PathParam("reservasId") Long reservasId) 
    {
        if (reservaLogic.getReserva(reservasId) == null) 
        {
            throw new WebApplicationException(elRecursoReservas + reservasId + noexiste, 404);
        }
        return ReservaFuncionResource.class;
    }
 
    /**
     * Conexión con el servicio de sillas para una reserva.
     * {@link ReservaResource}
     *
     * Este método conecta la ruta de /reservas con las rutas de /sillas que
     * dependen de la reserva, es una redirección al servicio que maneja el
     * segmento de la URL que se encarga de las sillas de una reserva.
     *
     * @param reservasId El ID de la reserva con respecto a la cual se
     * accede al servicio.
     * @return El servicio de sillas para esta reserva en paricular.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la reserva.
     */
    @Path("{reservasId: \\d+}/sillas")
    public Class<ReservaSillasResource> getReservaSillasResource(@PathParam("reservasId") Long reservasId) 
    {
        if (reservaLogic.getReserva(reservasId) == null) 
        {
            throw new WebApplicationException(elRecursoReservas + reservasId + noexiste, 404);
        }
        return ReservaSillasResource.class;
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
    private List<ReservaDetailDTO> listEntity2DetailDTO(List<ReservaEntity> entityList)
    {
        List<ReservaDetailDTO> list = new ArrayList<>();
        for (ReservaEntity entity : entityList) {
            list.add(new ReservaDetailDTO(entity));
        }
        return list;
    }
}
