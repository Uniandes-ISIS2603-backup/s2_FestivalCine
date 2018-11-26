/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.resources;

/**
 *
 * @author estudiante
 */

import co.edu.uniandes.csw.festivalcine.dtos.FuncionDTO;
import co.edu.uniandes.csw.festivalcine.ejb.FuncionLogic;
import co.edu.uniandes.csw.festivalcine.ejb.ReservaFuncionLogic;
import co.edu.uniandes.csw.festivalcine.entities.FuncionEntity;
import co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
/**
 *
 * @author PAULA VELANDIA
 */
public class ReservaFuncionResource 
{
    String noexiste = "no existe";
    private static final Logger LOGGER = Logger.getLogger(ReservaSillasResource.class.getName());
     
     @Inject
     private ReservaFuncionLogic reservaFuncionLogic;
     
      @Inject
    private FuncionLogic funcionLogic;
      
      /**
     * Guarda una funcion dentro de una reserva con la informacion que recibe el
     * la URL. Se devuelve la funcion que se guarda en la reserva.
     *
     * @param reservasId Identificador de la reserva que se esta
     * actualizando. Este debe ser una cadena de dígitos.
     * @param funcionesId Identificador de la funcion que se desea guardar. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link FuncionDTO} - La funcion guardado en la reserva.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la funcion .
     */
    @POST
    @Path("{funcionesId: \\d+}")
    public FuncionDTO addFuncion(@PathParam("reservasId") Long reservasId, @PathParam("funcionesId") Long funcionesId) {
        LOGGER.log(Level.INFO, "ReservasResource addReserva: input: reservasID: {0} , funcionesId: {1}", new Object[]{reservasId, funcionesId});
        if (funcionLogic.getFuncion(funcionesId) == null) {
            throw new WebApplicationException("El recurso /funciones/" + funcionesId + noexiste, 404);
        }
        FuncionDTO funcionDTO = new FuncionDTO(reservaFuncionLogic.addFuncion(funcionesId, reservasId));
        LOGGER.log(Level.INFO, "UsuariosResource addFuncion: output: {0}", funcionDTO);
        return funcionDTO;
    }
    
    /**
     * Busca y devuelve todos las funciones que existen en una reserva.
     *
     * @param authorsId El ID del autor del cual se buscan los libros
     * @return JSONArray {@link BookDetailDTO} - Los libros encontrados en el
     * autor. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<FuncionDTO> getFunciones(@PathParam("reservasId") Long reservasId) 
    {
        LOGGER.log(Level.INFO, "ReservaFuncionResource getFunciones: input: {0}", reservasId);
        List<FuncionDTO> lista = funcionesListEntity2DTO(reservaFuncionLogic.getFunciones(reservasId));
        LOGGER.log(Level.INFO, "ReservaFuncionResource getFunciones: output: {0}", lista);
        return lista;
    }
    
    /**
     * Busca la funcion con el id asociado dentro de la reserva con id asociado.
     *
     * @param reservasId Identificador de la reserva que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @param funcionesId Identificador de la funcion que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link FuncionDetailDTO} - La reserva buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la funcion.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra LA FUNCION en la
     * reserva.
     */
    @GET
    @Path("{funcionesId: \\d+}")
    public FuncionDTO getFuncion(@PathParam("reservasId") Long reservasId, @PathParam("funcionesId") Long funcionesId) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "ReservaResource getFuncion: input: reservasID: {0} , funcionesId: {1}", new Object[]{reservasId, funcionesId});
        if (funcionLogic.getFuncion(funcionesId) == null) {
            throw new WebApplicationException("El recurso /reservas/" + reservasId + "/funciones/" + funcionesId + noexiste, 404);
        }
        FuncionDTO funcionDTO = new FuncionDTO(reservaFuncionLogic.getFuncion(reservasId, funcionesId));
        LOGGER.log(Level.INFO, "ReservaResource getFuncion: output: {0}", funcionDTO);
        return funcionDTO;
    }
  
    private List<FuncionDTO> funcionesListEntity2DTO(List<FuncionEntity> entityList) {
        List<FuncionDTO> list = new ArrayList<>();
        for (FuncionEntity entity : entityList) {
            list.add(new FuncionDTO(entity));
        }
        return list;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
