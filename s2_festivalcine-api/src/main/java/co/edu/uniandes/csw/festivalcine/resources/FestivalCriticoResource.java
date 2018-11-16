/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.resources;

import co.edu.uniandes.csw.festivalcine.dtos.CriticoDTO;
import co.edu.uniandes.csw.festivalcine.dtos.CriticoDetailDTO;
import co.edu.uniandes.csw.festivalcine.ejb.CriticoLogic;
import co.edu.uniandes.csw.festivalcine.ejb.FestivalCriticoLogic;
import co.edu.uniandes.csw.festivalcine.entities.CriticoEntity;
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
 * Clase que implementa el recurso "festivals/{id}/criticos".
 * @author Mario Andrade
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FestivalCriticoResource 
{
   private static final Logger LOGGER = Logger.getLogger(FestivalCriticoResource.class.getName());
   
   @Inject
   private FestivalCriticoLogic festivalCriticoLogic;
   
   @Inject
   private CriticoLogic criticoLogic;
   
   
       /**
     * Guarda un critico dentro de un festival con la informacion que recibe el
     * la URL. Se devuelve el critico que se guarda en el festival.
     *
     * @param festivalId Identificador del festival que se esta
     * actualizando. Este debe ser una cadena de dígitos.
     * @param criticoId Identificador de l teatro que se desea guardar. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link CriticoDTO} - El critico guardado en el festival.
     * @throws co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el critico.
     */
    @POST
    @Path("{criticoId: \\d+}")
    public CriticoDTO addCritico(@PathParam("festivalId") Long festivalId, @PathParam("criticoId") Long criticoId) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "FestivalResource addCritico: input: festivalId: {0} , criticoId: {1}", new Object[]{festivalId, criticoId});
        if (criticoLogic.getCritico(criticoId) == null) {
            throw new WebApplicationException("El recurso /criticos/" + criticoId + " no existe.", 404);
        }
        CriticoDTO criticoDTO = new CriticoDTO(festivalCriticoLogic.addCritico(criticoId, festivalId));
        LOGGER.log(Level.INFO, "Festival addCritico: output: {0}", criticoDTO);
        return criticoDTO;
    }
    
    /**
     * Busca el critico con el id asociado dentro de un festival con id asociado.
     *
     * @param festivalId Identificador del festival que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @param criticoId Identificador del critico que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link CriticoDetailDTO} - El critico buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el critico.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el critico en el
     * festival.
     */
    @GET
    @Path("{criticoId: \\d+}")
    public CriticoDetailDTO getCritico(@PathParam("festivalId") Long festivalId, @PathParam("criticoId") Long criticoId) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "FestivalResource getCritico: input: festivalId: {0} , criticoId: {1}", new Object[]{festivalId, criticoId});
        if (criticoLogic.getCritico(criticoId) == null) 
        {
            throw new WebApplicationException("El recurso /festivales/" + festivalId + "/criticos/" + criticoId + " no existe.", 404);
        }
        CriticoDetailDTO criticoDetailDTO = new CriticoDetailDTO(festivalCriticoLogic.getCritico(festivalId, criticoId));
        LOGGER.log(Level.INFO, "FestivalCriticoResource getCritico: output: {0}", criticoDetailDTO);
        return criticoDetailDTO;
    }
    
        /**
     * Convierte una lista de CriticoEntity a una lista de CriticoDetailDTO.
     *
     * @param entityListCriticoDTO convertida.
     */
    private List<CriticoDetailDTO> criticosListEntity2DTO(List<CriticoEntity> entityList) 
    {
        List<CriticoDetailDTO> list = new ArrayList();
        for (CriticoEntity entity : entityList) 
        {
            list.add(new CriticoDetailDTO(entity));
        }
        return list;
    }
    
    /**
     * Convierte una lista de CriticoDetailDTO a una lista de CriticoEntity.
     *
     * @param dtos Lista de CriticoDetailDTO a convertir.
     * @return Lista de CriticoEntity convertida.
     */
    private List<CriticoEntity> teatrosListDTO2Entity(List<CriticoDetailDTO> dtos) 
    {
        List<CriticoEntity> list = new ArrayList<>();
        for (CriticoDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
}
