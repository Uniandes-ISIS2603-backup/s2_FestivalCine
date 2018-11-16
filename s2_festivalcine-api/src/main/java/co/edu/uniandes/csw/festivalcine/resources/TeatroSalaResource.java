/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.resources;

import co.edu.uniandes.csw.festivalcine.dtos.SalaDTO;
import co.edu.uniandes.csw.festivalcine.dtos.SalaDetailDTO;
import co.edu.uniandes.csw.festivalcine.ejb.SalaLogic;
import co.edu.uniandes.csw.festivalcine.ejb.TeatroSalaLogic;
import co.edu.uniandes.csw.festivalcine.entities.SalaEntity;
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
 * Clase que implementa el recurso "teatros/{id}/salas".
 * @author Mario Andrade
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TeatroSalaResource 
{
    private static final Logger LOGGER = Logger.getLogger(TeatroSalaResource.class.getName());
   
   @Inject
   private TeatroSalaLogic teatroSalaLogic;
   
   @Inject
   private SalaLogic salaLogic;
   
   
       /**
     * Guarda una sala dentro de un tatro con la informacion que recibe el
     * la URL. Se devuelve la sala que se guarda en el festival.
     *
     * @param teatroId Identificador del teatro que se esta
     * actualizando. Este debe ser una cadena de dígitos.
     * @param salaId Identificador de la sala que se desea guardar. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link SalaDTO} - La sala guardada en el teatro.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la sala.
     */
    @POST
    @Path("{salaId: \\d+}")
    public SalaDTO addSala(@PathParam("teatroId") Long teatroId, @PathParam("salaId") Long salaId)
    {
        LOGGER.log(Level.INFO, "TeatroResource addSala: input: teatroId: {0} , salaId: {1}", new Object[]{teatroId, salaId});
        if (salaLogic.getSala(salaId) == null) {
            throw new WebApplicationException("El recurso /salas/" + salaId + " no existe.", 404);
        }
        SalaDTO salaDTO = new SalaDTO(teatroSalaLogic.addSala(salaId, teatroId));
        LOGGER.log(Level.INFO, "Teatro addSala: output: {0}", salaDTO.toString());
        return salaDTO;
    }
    
    /**
     * Busca la sala con el id asociado dentro de un teatro con id asociado.
     *
     * @param teatroId Identificador del teatro que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @param salaId Identificador de la sala que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link SalaDetailDTO} - La sala buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la sala.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la sala en el
     * teatro.
     */
    @GET
    @Path("{salaId: \\d+}")
    public SalaDetailDTO getSala(@PathParam("teatroId") Long teatroId, @PathParam("salaId") Long salaId) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "SalaResource getFuncion: input: teatroId: {0} , funcionId: {1}", new Object[]{teatroId, salaId});
        if (salaLogic.getSala(salaId) == null) 
        {
            throw new WebApplicationException("El recurso /teatros/" + teatroId + "/salas/" + salaId + " no existe.", 404);
        }
        SalaDetailDTO salaDetailDTO = new SalaDetailDTO(teatroSalaLogic.getSala(teatroId, salaId));
        LOGGER.log(Level.INFO, "TeatroSalaResource getSala: output: {0}", salaDetailDTO.toString());
        return salaDetailDTO;
    }
    
        /**
     * Convierte una lista de SalaEntity a una lista de SalaDetailDTO.
     *
     * @param entityList Lista de SalaEntity a convertir.
     * @return Lista de SalaDTO convertida.
     */
    private List<SalaDTO> salasListEntity2DTO(List<SalaEntity> entityList) 
    {
       List<SalaDTO> list = new ArrayList();
        for (SalaEntity entity : entityList) 
        {
            list.add(new SalaDTO(entity));
        }
        return list;
    }
    
    /**
     * Convierte una lista de SalaDetailDTO a una lista de SalaEntity.
     *
     * @param dtos Lista de SalaDetailDTO a convertir.
     * @return Lista de SalaEntity convertida.
     */
    private List<SalaEntity> salasListDTO2Entity(List<SalaDTO> dtos) 
    {
        List<SalaEntity> list = new ArrayList<>();
        for (SalaDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }        
}
