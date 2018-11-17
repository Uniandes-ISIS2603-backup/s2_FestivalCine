/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.resources;

import co.edu.uniandes.csw.festivalcine.dtos.FuncionDTO;
import co.edu.uniandes.csw.festivalcine.ejb.FuncionLogic;
import co.edu.uniandes.csw.festivalcine.ejb.TeatroFuncionLogic;
import co.edu.uniandes.csw.festivalcine.entities.FuncionEntity;
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
 *Clase que implementa el recurso "teatros/{id}/funciones".
 * @author Mario Andrade
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TeatroFuncionResource 
{
       private static final Logger LOGGER = Logger.getLogger(TeatroFuncionResource.class.getName());
   
   @Inject
   private TeatroFuncionLogic teatroFuncionLogic;
   
   @Inject
   private FuncionLogic funcionLogic;
   
   
       /**
     * Guarda una funcion dentro de un tatro con la informacion que recibe el
     * la URL. Se devuelve la funcion que se guarda en el festival.
     *
     * @param teatroId Identificador del teatro que se esta
     * actualizando. Este debe ser una cadena de dígitos.
     * @param funcionId Identificador de la funcion que se desea guardar. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link FuncionDTO} - La funcion guardada en el teatro.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la funcion.
     */
    @POST
    @Path("{funcionId: \\d+}")
    public FuncionDTO addFuncion(@PathParam("teatroId") Long teatroId, @PathParam("funcionId") Long funcionId)
    {
        LOGGER.log(Level.INFO, "TeatroResource addFuncion: input: teatroId: {0} , funcionId: {1}", new Object[]{teatroId, funcionId});
        if (funcionLogic.getFuncion(funcionId) == null) {
            throw new WebApplicationException("El recurso /funciones/" + funcionId + " no existe.", 404);
        }
        FuncionDTO funcionDTO = new FuncionDTO(teatroFuncionLogic.addFuncion(funcionId, teatroId));
        LOGGER.log(Level.INFO, "Teatro addFuncion: output: {0}", funcionDTO);
        return funcionDTO;
    }
    
    /**
     * Busca la funcion con el id asociado dentro de un teatro con id asociado.
     *
     * @param teatroId Identificador del teatro que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @param funcionID Identificador de la funcion que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link FuncionDetailDTO} - La funcion buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la funcion.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la funcion en el
     * teatro.
     */
    @GET
    @Path("{funcionId: \\d+}")
    public FuncionDTO getFuncion(@PathParam("teatroId") Long teatroId, @PathParam("funcionId") Long funcionID) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "TeatroResource getFuncion: input: teatroId: {0} , funcionId: {1}", new Object[]{teatroId, funcionID});
        if (funcionLogic.getFuncion(funcionID) == null) 
        {
            throw new WebApplicationException("El recurso /teatros/" + teatroId + "/funciones/" + funcionID + " no existe.", 404);
        }
        FuncionDTO funcionDetailDTO = new FuncionDTO(teatroFuncionLogic.getFuncion(teatroId, funcionID));
        LOGGER.log(Level.INFO, "TeatroFuncionResource getFuncion: output: {0}", funcionDetailDTO);
        return funcionDetailDTO;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
