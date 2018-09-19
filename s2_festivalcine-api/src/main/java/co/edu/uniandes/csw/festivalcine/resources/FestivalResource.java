/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.resources;

import co.edu.uniandes.csw.festivalcine.dtos.FestivalDTO;
import co.edu.uniandes.csw.festivalcine.dtos.FestivalDetailDTO;
import co.edu.uniandes.csw.festivalcine.dtos.TeatroDetailDTO;
import co.edu.uniandes.csw.festivalcine.ejb.FestivalLogic;
import co.edu.uniandes.csw.festivalcine.entities.FestivalEntity;
import co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.festivalcine.mappers.WebApplicationExceptionMapper;
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
 * Clase que implementa el recurso "festivales".
 *
 * @author Mario Andrade
 * @version 1.0
 */
@Path("festivales")
@Consumes("application/json")
@Produces("application/json")
@RequestScoped
public class FestivalResource {

    private static final Logger LOGGER = Logger.getLogger(FestivalResource.class.getName());

    /**
     * Inyecta la logica de Festival
     */
    @Inject
    FestivalLogic festivalLogic;

    /**
     * Crea un nuevo festival con la informacion que se recibe en el cuerpo de la
     * petición y se regresa un objeto identico con un id auto-generado por la
     * base de datos.
     *
     * @param festival - EL festival que se desea guardar.
     * @return JSON  - El festival guardado con el atributo id
     * autogenerado.
     */
    @POST
    public FestivalDTO createFestival(FestivalDTO festival) throws BusinessLogicException
    {
       
        LOGGER.log(Level.INFO, "FestivalResource createFestival: input: {0}", festival.toString());
        FestivalEntity festivalEntity = festival.toEntity();
        FestivalEntity nuevoFestivalEntity = festivalLogic.createFestival(festivalEntity);
        FestivalDTO nuevoTeatro = new FestivalDTO(nuevoFestivalEntity);
        LOGGER.log(Level.INFO, "TeatroResource createTeatro: output: {0}", nuevoTeatro.toString());
        return nuevoTeatro;
    }

    /**
     * Busca y devuelve todos los festivales que existen en la aplicacion.
     *
     * @return JSONArray - Los festivales encontrados en la
     * aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<FestivalDetailDTO> getFestivals() {
        LOGGER.info("FestivalResource getFestivales: input: void");
        List<FestivalDetailDTO> listFestivales = listEntity2DetailDTO(festivalLogic.getFestivales());
        LOGGER.log(Level.INFO, "FestivalResource getFestivales: output: {0}", listFestivales.toString());
        return listFestivales;
    }

    /**
     * Busca el festival con el id asociado recibido en la URL y lo devuelve.
     *
     * @param id Identificador del autor que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON  - El festival buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el festival.
     */
    @GET
    @Path("{id: \\d+}")
    public FestivalDTO getFestival(@PathParam("id") Long id) throws WebApplicationException 
    {
        LOGGER.log(Level.INFO, "FestivalResource getTeatro: input: {0}", id);
       FestivalEntity teatroEntity = festivalLogic.getFestival(id);
        if (teatroEntity == null) 
        {
            throw new WebApplicationException("El recurso /festivales/" + id + " no existe.", 404);
        }
        FestivalDetailDTO detailDTO = new FestivalDetailDTO(teatroEntity);
        LOGGER.log(Level.INFO, "FestivalResource getFestival: output: {0}", detailDTO.toString());
        return detailDTO;
    }

    /**
     * Actualiza el festival con el id recibido en la URL con la información que se
     * recibe en el cuerpo de la petición.
     *
     * @param id Identificador del festival que se desea actualizar. Este
     * debe ser una cadena de dígitos.
     * @param festival  El festival que se desea guardar.
     * @return JSON - El festival guardado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el festival a
     * actualizar.
     */
    @PUT
    @Path("{id: \\d+}")
    public FestivalDTO updateFestival(@PathParam("id") Long id, FestivalDTO festival) throws WebApplicationException
    {
        LOGGER.log(Level.INFO, "FestivalResource updateFestival: input: id:{0} , festival: {1}", new Object[]{id, festival.toString()});
        festival.setId(id);
        if (festivalLogic.getFestival(id) == null) 
        {
            throw new WebApplicationException("El recurso /festivales/" + id + " no existe.", 404);
        }
        FestivalDetailDTO detailDTO = new FestivalDetailDTO(festivalLogic.updateFestival(id, festival.toEntity()));
        LOGGER.log(Level.INFO, "FestivalResource updateFestival: output: {0}", detailDTO.toString());
        return detailDTO;
    }
    
     @DELETE
    @Path("{id: \\d+}")
    public void deleteFestival(@PathParam("id") Long id) throws BusinessLogicException
    {
       if (festivalLogic.getFestival(id) == null) {
        throw new WebApplicationException("El recurso /festivales/" + id + " no existe.", 404);
        }
        festivalLogic.deleteFestival(id);
        LOGGER.info("FestivalResource deleteFestival: output: void");
    }
    
    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos UsuarioEntity a una lista de
     * objetos UsuarioDetailDTO (json)
     *
     * @param entityList corresponde a la lista de usuarios de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de usuarios en forma DTO (json)
     */
    private List<FestivalDetailDTO> listEntity2DetailDTO(List<FestivalEntity> entityList) {
        List<FestivalDetailDTO> list = new ArrayList<>();
        for (FestivalEntity entity : entityList) 
        {
            list.add(new FestivalDetailDTO(entity));
        }
        return list;
    }
    
        /**
     * Método que retorna los teatros de un festival
     * @param id
     * @return lista de las funciones correspondientes al festival ingresado por parametro
     */
    @GET
    @Path("{id: \\d+}/teatros")
    public Class<FestivalTeatroResource> getFestivalTeatroResource(@PathParam("id") Long id) 
    {
        if (festivalLogic.getFestival(id) == null) 
        {
            throw new WebApplicationException("El recurso /festivales/" + id + " no existe.", 404);
        }
        return FestivalTeatroResource.class;
    }
    
    /**
     * Método que retorna los criticos de un festival
     * @param id
     * @return lista de los criticos correspondientes al festival ingresado por parametro
     */
    @GET
    @Path("{id: \\d+}/criticos")
    public Class<FestivalCriticoResource> getFestivalCriticoResource(@PathParam("id") Long id) 
    {
        if (festivalLogic.getFestival(id) == null) 
        {
            throw new WebApplicationException("El recurso /festivales/" + id + " no existe.", 404);
        }
        return FestivalCriticoResource.class;
    }

}
