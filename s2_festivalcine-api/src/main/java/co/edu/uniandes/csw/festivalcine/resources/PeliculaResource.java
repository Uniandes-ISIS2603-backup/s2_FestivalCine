/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.resources;

///IMPORTS
import co.edu.uniandes.csw.festivalcine.dtos.PeliculaDTO;
import co.edu.uniandes.csw.festivalcine.ejb.PeliculaLogic;
import co.edu.uniandes.csw.festivalcine.entities.PeliculaEntity;
import co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException;

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
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Cristian
 */

//URI:/peliculas
@Path("/peliculas")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class PeliculaResource {
    @Inject 
    private PeliculaLogic peliculaLogic;
    
    private static final Logger LOGGER = Logger.getLogger(PeliculaResource.class.getName());
    
      /**
     * Convierte una lista de BandaAnuncioEntity a una lista de BandaAnuncioDTO.
     *
     * @param entityList Lista de BandaAnuncioEntity a convertir.
     * @return Lista de BandaAnuncioDTO convertida.
     * 
     */
    private List<PeliculaDTO> listEntity2DTO(List<PeliculaEntity> entityList) {
        List<PeliculaDTO> list = new ArrayList<>();
        for (PeliculaEntity entity : entityList) {
            list.add(new PeliculaDTO(entity)); 
        }
        return list;
    }
    
    
    /**
     * Obtiene la lista de los registros de la Pelicula
     *
     * @return Colección de objetos de la PeliculaDetailDTO
     * 
     */
    @GET
    public List<PeliculaDTO> getPeliculas() {
        return listEntity2DTO(peliculaLogic.getPeliculas());
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
    public PeliculaDTO getPelicula(@PathParam("id") Long id) {
        PeliculaEntity entity = peliculaLogic.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Lapelicula no existe", 404);
        }
        return new PeliculaDTO(entity);
    }

    /**
     * Se encarga de crear un la Pelicula en la base de datos
     *
     * @param dto Objeto de la PeliculaDetailDTO con los datos nuevos
     * @return Objeto de la PeliculaDetailDTOcon los datos nuevos y su ID
     * @throws co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException
     * 
     */
    @POST
    public PeliculaDTO createEstudiante(PeliculaDTO dto){
      LOGGER.log(Level.INFO,"PeliculaResource createPelicula: input: {0}",  dto);
        PeliculaDTO nuevoPeliculaDTO = new PeliculaDTO(peliculaLogic.createPelicula(dto.toEntity()));
        LOGGER.log(Level.INFO, "PeliculaResource createPelicula: output: {0}", nuevoPeliculaDTO);
        return nuevoPeliculaDTO;
    }


    /**
     * Actualiza la información de una instancia de la Pelicula
     *
     * @param id Identificador de la instancia de la Pelicula a modificar
     * @param dto
   
     * @return Instancia de la PeliculaDetailDTO con los datos actualizados
     * 
     */
    @PUT
    @Path("{id: \\d+}")
    public PeliculaDTO updateEstudiante(@PathParam("id") Long id, PeliculaDTO dto) {
       PeliculaEntity entity = dto.toEntity();
        entity.setId(id);
       PeliculaEntity oldEntity = peliculaLogic.findById(id);
        if (oldEntity == null) {
            throw new WebApplicationException("La pelicula no existe", 404);
        }
        
        return new PeliculaDTO(peliculaLogic.update(dto.toEntity()));
    }

    /**
     * Elimina una instancia de la Pelicula de la base de datos
     *
     * @param id Identificador de la instancia a eliminar
     * @throws co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException
     * 
     */
    @DELETE
    @Path("{id: \\d+}")
    public void deletePelicula(@PathParam("id") Long id){
        PeliculaEntity entity = peliculaLogic.findById(id);
        if (entity == null) {
            throw new WebApplicationException("La pelicula no existe", 404);
        }
       peliculaLogic.delete(id);
    }
    
    
}
