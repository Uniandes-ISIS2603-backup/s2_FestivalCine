/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.resources;


/**
 * IMPORTS
 */

import co.edu.uniandes.csw.festivalcine.dtos.BandaAnuncioDTO;
import co.edu.uniandes.csw.festivalcine.ejb.BandaAnuncioLogic;
import co.edu.uniandes.csw.festivalcine.entities.BandaAnuncioEntity;
import co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
/**
 *
 * @author cc.cardenas
 */

//URI:/bandas
@Path("/bandas")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class BandaAnuncioResource {
  @Inject   
private BandaAnuncioLogic bandaLogic;
  
  
   /**
     * Convierte una lista de BandaAnuncioEntity a una lista de BandaAnuncioDTO.
     *
     * @param entityList Lista de BandaAnuncioEntity a convertir.
     * @return Lista de BandaAnuncioDTO convertida.
     * 
     */
    private List<BandaAnuncioDTO> listEntity2DTO(List<BandaAnuncioEntity> entityList) {
        List<BandaAnuncioDTO> list = new ArrayList<>();
        for (BandaAnuncioEntity entity : entityList) {
            list.add(new BandaAnuncioDTO(entity));
        }
        return list;
    }
    
     /**
     * Obtiene la lista de los registros de las bandas de Anuncio
     *
     * @return Colección de objetos de BandaAnuncioDTO
     * 
     */
    @GET
    public List<BandaAnuncioDTO> getBandas() {
        return listEntity2DTO(bandaLogic.getBandas());
    }

     @POST
    public BandaAnuncioDTO createBanda(BandaAnuncioDTO banda) throws BusinessLogicException 
    {        
         return new BandaAnuncioDTO(bandaLogic.createBandaAnuncio(banda.toEntity()));
    }

   
    @PUT
    @Path("{id: \\d+}")
    public BandaAnuncioDTO updateBanda(@PathParam("id") Long id, BandaAnuncioDTO banda) throws BusinessLogicException 
    {
        BandaAnuncioEntity entity = banda.toEntity();
        entity.setId(id);
        BandaAnuncioEntity oldEntity = bandaLogic.findById(id);
        if (oldEntity == null) 
        {
            throw new WebApplicationException("El recurso /bandas/" + id + " no existe.", 404);
        }
        return new BandaAnuncioDTO(bandaLogic.update(entity));
    }

    @DELETE
    @Path("{id: \\d+}")
    public void deleteBanda(@PathParam("id") Long id) throws BusinessLogicException 
    {
        BandaAnuncioEntity entity = bandaLogic.findById(id);
        if (entity == null) 
        {
            throw new WebApplicationException("El recurso /bandas/" + id + " no existe.", 404);
        }
        bandaLogic.delete(id);
    }
}
