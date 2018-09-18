/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//by cc.cardenas
package co.edu.uniandes.csw.festivalcine.resources;

import co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.festivalcine.dtos.BandaAnuncioDTO;
import co.edu.uniandes.csw.festivalcine.ejb.BandaAnuncioLogic;
import co.edu.uniandes.csw.festivalcine.entities.BandaAnuncioEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

/**
 * Clase que implementa el recurso "BandaAnuncio".

 */
@Path("bandas")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped



public class BandaAnuncioResource {
    private static final Logger LOGGER = Logger.getLogger(BandaAnuncioResource.class.getName());
    

    @Inject
    BandaAnuncioLogic bandaLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

   
    @POST
    public BandaAnuncioDTO createBanda(BandaAnuncioDTO banda) throws BusinessLogicException {
        
       // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        BandaAnuncioEntity  bandaE = banda.toEntity();
        // Invoca la lógica para crear la editorial nuev
        BandaAnuncioEntity nEntity = bandaLogic.createBandaAnuncio(bandaE);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        BandaAnuncioDTO nDTO= new BandaAnuncioDTO(nEntity);
        LOGGER.log(Level.INFO, "CREANDO BANDA ANUNCIO");
        return nDTO;
    }
 /**
     * Obtiene la lista de los registros de las bandas de Anuncio
     *
     * @return Colección de objetos de BandaAnuncioDTO
     * 
     */
    @GET
    public List<BandaAnuncioDTO> getBandas() {
        return listEntity2DetailDTO(bandaLogic.getBandas());
    }


    /**
     * Obtiene los datos de una instancia de la Pelicula a partir de su ID
     *
     * @param id Identificador de la instancia a consultar
     * @return Instancia de la PeliculaDetailDTO con los datos del la Pelicula consultado
     * 
     */
    @GET
    @Path("{id: \\d+}")
    public BandaAnuncioDTO getBanda(@PathParam("id") Long id) {
        BandaAnuncioEntity entity = bandaLogic.findById(id);
        if (entity == null) {
            throw new WebApplicationException("La banda no existe", 404);
        }
        return new BandaAnuncioDTO(entity);
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
  


      /**
     * Lista entidades a DTO
     * 
     * Este método convierte una lista de objetos BibliotecaEntity a una lista de objetos BibliotecaDetailDTO(json)
     * @param entityList corresponde a la lista de cantantes de tipo Entity
     * que se va a convertir a DTO.
     * 
     * @return  la lista de bibliotecas en forma DTO (json)
     */
    private List<BandaAnuncioDTO> listEntity2DetailDTO(List<BandaAnuncioEntity> entityList){
        List<BandaAnuncioDTO> list = new ArrayList<>();
        for(BandaAnuncioEntity entity: entityList){
            list.add(new BandaAnuncioDTO(entity));
        }
        return list;
    }
    
    
    
}
