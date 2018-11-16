/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.resources;


import co.edu.uniandes.csw.festivalcine.dtos.CriticoDTO;
import co.edu.uniandes.csw.festivalcine.dtos.CriticoDetailDTO;
import co.edu.uniandes.csw.festivalcine.dtos.TeatroDTO;
import co.edu.uniandes.csw.festivalcine.dtos.TeatroDetailDTO;
import co.edu.uniandes.csw.festivalcine.ejb.FestivalTeatroLogic;
import co.edu.uniandes.csw.festivalcine.ejb.TeatroLogic;

import co.edu.uniandes.csw.festivalcine.entities.TeatroEntity;
import co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.festivalcine.mappers.BusinessLogicExceptionMapper;
import co.edu.uniandes.csw.festivalcine.mappers.WebApplicationExceptionMapper;
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
 * Clase que implementa el recurso "festivals/{id}/teatros".
 * @author Mario Andrade
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FestivalTeatroResource 
{
       private static final Logger LOGGER = Logger.getLogger(FestivalTeatroResource.class.getName());
   
   @Inject
   private FestivalTeatroLogic festivalTeatroLogic;
   
   @Inject
   private TeatroLogic teatroLogic;
   
   
       /**
     * Guarda un teatro dentro de un festival con la informacion que recibe el
     * la URL. Se devuelve el teatro que se guarda en el festival.
     *
     * @param festivalId Identificador del festival que se esta
     * actualizando. Este debe ser una cadena de dígitos.
     * @param teatroId Identificador del teatro que se desea guardar. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link CriticoDTO} - El teatro guardado en el festival.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el teatro.
     */
    @POST
    @Path("{teatroId: \\d+}")
    public TeatroDTO addTeatro(@PathParam("festivalId") Long festivalId, @PathParam("teatroId") Long teatroId)
    {
        LOGGER.log(Level.INFO, "FestivalResource addTeatro: input: festivalId: {0} , teatroId: {1}", new Object[]{festivalId, teatroId});
        if (teatroLogic.getTeatro(teatroId) == null) {
            throw new WebApplicationException("El recurso /teatros/" + teatroId + " no existe.", 404);
        }
        TeatroDTO teatroDTO = new TeatroDTO(festivalTeatroLogic.addTeatro(teatroId, festivalId));
        LOGGER.log(Level.INFO, "Festival addTeatro: output: {0}", teatroDTO);
        return teatroDTO;
    }
    
    /**
     * Busca la reserva con el id asociado dentro de un festival con id asociado.
     *
     * @param festivalId Identificador del festival que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @param teatroId Identificador del teatro que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link CriticoDetailDTO} - El teatro buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el critico.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el teatro en el
     * festival.
     */
    @GET
    @Path("{teatroId: \\d+}")
    public TeatroDetailDTO getTeatro(@PathParam("festivalId") Long festivalId, @PathParam("teatroId") Long teatroId) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "FestivalResource getTeatro: input: festivalId: {0} , teatroId: {1}", new Object[]{festivalId, teatroId});
        if (teatroLogic.getTeatro(teatroId) == null) 
        {
            throw new WebApplicationException("El recurso /festivales/" + festivalId + "/teatros/" + teatroId + " no existe.", 404);
        }
        TeatroDetailDTO teatroDetailDTO = new TeatroDetailDTO(festivalTeatroLogic.getTeatro(festivalId, teatroId));
        LOGGER.log(Level.INFO, "FestivalTeatroResource getTeatro: output: {0}", teatroDetailDTO);
        return teatroDetailDTO;
    }  
}
