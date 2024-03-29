/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.resources;

import co.edu.uniandes.csw.festivalcine.dtos.SillaDTO;
import co.edu.uniandes.csw.festivalcine.ejb.ReservaSillasLogic;
import co.edu.uniandes.csw.festivalcine.ejb.SillaLogic;

import co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException;


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
public class ReservaSillasResource 
{
     private static final Logger LOGGER = Logger.getLogger(ReservaSillasResource.class.getName());
     
     @Inject
     private ReservaSillasLogic reservaSillasLogic;
     
      @Inject
    private SillaLogic sillaLogic;
     
    /**
     * Guarda una silla dentro de una reserva con la informacion que recibe el
     * la URL. Se devuelve la silla que se guarda en la reserva.
     *
     * @param reservasId Identificador de la reserva que se esta
     * actualizando. Este debe ser una cadena de dígitos.
     * @param sillasId Identificador de la silla que se desea guardar. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link SillaDTO} - La silla guardada en la reserva.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la silla.
     */
    @POST
    @Path("{sillasId: \\d+}")
    public SillaDTO addSilla(@PathParam("reservasId") Long reservasId, @PathParam("sillasId") Long sillasId) {
        LOGGER.log(Level.INFO, "ReservaSillasResource addSilla: input: reservasID: {0} , sillasId: {1}", new Object[]{reservasId, sillasId});
        if (sillaLogic.getSilla(sillasId) == null)
        {
            throw new WebApplicationException("El recurso /sillas/" + sillasId + " no existe.", 404);
        }
        SillaDTO sillaDTO = new SillaDTO(reservaSillasLogic.addSilla(reservasId, sillasId));
        LOGGER.log(Level.INFO, "ReservaSillasResource addSilla: output: {0}", sillaDTO);
        return sillaDTO;
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * Busca la funcion con el id asociado dentro de la reserva con id asociado.
     *
     * @param reservasId Identificador de la reserva que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @param sillasId Identificador de la silla que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link FuncionDetailDTO} - La reserva buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la funcion.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra LA silla en la
     * reserva.
     */
    @GET
    @Path("{sillasId: \\d+}")
    public SillaDTO getSilla(@PathParam("reservasId") Long reservasId, @PathParam("sillasId") Long sillasId) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "ReservaResource getSilla: input: reservasID: {0} , sillasId: {1}", new Object[]{reservasId, sillasId});
        if (sillaLogic.getSilla(sillasId) == null) 
        {
            throw new WebApplicationException("El recurso /reservas/" + reservasId + "/sillas/" + sillasId + " no existe.", 404);
        }
        SillaDTO sillaDTO = new SillaDTO(reservaSillasLogic.getSilla(reservasId, sillasId));
        LOGGER.log(Level.INFO, "ReservaResource getSilla: output: {0}", sillaDTO);
        return sillaDTO;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

   
}

