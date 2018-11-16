/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.resources;

import co.edu.uniandes.csw.festivalcine.dtos.TeatroDTO;
import co.edu.uniandes.csw.festivalcine.dtos.TeatroDetailDTO;
import co.edu.uniandes.csw.festivalcine.ejb.TeatroLogic;
import co.edu.uniandes.csw.festivalcine.entities.TeatroEntity;
import co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.festivalcine.mappers.WebApplicationExceptionMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 *
 * @author Mario Andrade
 */
@Path("teatros")
@Consumes("application/json")
@Produces("application/json")
//@RequestScoped

public class TeatroResource 
{
    private static final Logger LOGGER = Logger.getLogger(FestivalResource.class.getName());
    
    /**
     * Inyecta logica de Teatro
     */
    @Inject
    TeatroLogic teatroLogic;
    
        /**
     * Crea un nuevo teatro con la informacion que se recibe en el cuerpo de la
     * petición y se regresa un objeto identico con un id auto-generado por la
     * base de datos.
     *
     * @param teatro - EL teatro que se desea guardar.
     * @return JSON  - El teatro guardado con el atributo id
     * autogenerado.
     */
    @POST
    public TeatroDTO createTeatro(TeatroDTO teatro) throws BusinessLogicException
    {
                 LOGGER.log(Level.INFO, "TeatroResource createTeatro: input: {0}", teatro.toString());
        TeatroEntity teatroEntity = teatro.toEntity();
        TeatroEntity nuevoTeatroEntity = teatroLogic.createTeatro(teatroEntity);
        TeatroDTO nuevoTeatro = new TeatroDTO(nuevoTeatroEntity);
        LOGGER.log(Level.INFO, "TeatroResource createTeatro: output: {0}", nuevoTeatro.toString());
        return nuevoTeatro;
    }
    
        /**
     * Busca y devuelve todos los teatros que existen en la aplicacion.
     *
     * @return JSONArray - Los teatros encontrados en la
     * aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<TeatroDetailDTO> getTeatros() {
        LOGGER.info("TeatroResource getTeatros: input: void");
        List<TeatroDetailDTO> listTeatros = listEntity2DetailDTO(teatroLogic.getTeatros());
        LOGGER.log(Level.INFO, "FestivalResource getFestivales: output: {0}", listTeatros.toString());
        return listTeatros;
    }
    
        /**
     * Busca el teatro con el id asociado recibido en la URL y lo devuelve.
     *
     * @param id Identificador del autor que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON  - El teatro buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el teatro.
     */
    @GET
    @Path("{id: \\d+}")
    public TeatroDetailDTO getTeatro(@PathParam("id") Long id) throws WebApplicationException
    {
        LOGGER.log(Level.INFO, "TeatroResource getTeatro: input: {0}", id);
       TeatroEntity teatroEntity = teatroLogic.getTeatro(id);
        if (teatroEntity == null) 
        {
            throw new WebApplicationException("El recurso /teatros/" + id + " no existe.", 404);
        }
        TeatroDetailDTO detailDTO = new TeatroDetailDTO(teatroEntity);
        LOGGER.log(Level.INFO, "TeatroResource getTeatro: output: {0}", detailDTO.toString());
        return detailDTO;
    }
    
        /**
     * Actualiza el teatro con el id recibido en la URL con la información que se
     * recibe en el cuerpo de la petición.
     *
     * @param id Identificador del teatro que se desea actualizar. Este
     * debe ser una cadena de dígitos.
     * @param teatro  El teatro que se desea guardar.
     * @return JSON - El teatro guardado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el teatro a
     * actualizar.
     */
    @PUT
    @Path("{id: \\d+}")
    public TeatroDTO updateTeatro(@PathParam("id") Long id, TeatroDTO teatro) throws WebApplicationException
    {
              LOGGER.log(Level.INFO, "TeatroResource updateTeatro: input: id:{0} , teatro: {1}", new Object[]{id, teatro.toString()});
        teatro.setId(id);
        if (teatroLogic.getTeatro(id) == null) 
        {
            throw new WebApplicationException("El recurso /teatros/" + id + " no existe.", 404);
        }
       TeatroDetailDTO detailDTO = new TeatroDetailDTO(teatroLogic.updateTeatro(id, teatro.toEntity()));
        LOGGER.log(Level.INFO, "TeatroResource updateTeatro: output: {0}", detailDTO.toString());
        return detailDTO;
    }
    
    @DELETE
    @Path("{id: \\d+}")
    public void deleteTeatro(@PathParam("id") Long id) throws BusinessLogicException
    {
        if (teatroLogic.getTeatro(id) == null) {
        throw new WebApplicationException("El recurso /teatros/" + id + " no existe.", 404);
        }
        teatroLogic.deleteTeatro(id);
        LOGGER.info("TeatroResource deleteTeatro: output: void");
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
    private List<TeatroDetailDTO> listEntity2DetailDTO(List<TeatroEntity> entityList) 
    {
        List<TeatroDetailDTO> list = new ArrayList<>();
        for (TeatroEntity entity : entityList) 
        {
            list.add(new TeatroDetailDTO(entity));
        }
        return list;
    }
    
    /**
     * Método que retorna las funciones de un teatro
     * @param id
     * @return lista de las funciones correspondientes al teatro ingresado por parametro
     */
    @GET
    @Path("{id: \\d+}/funciones")
    public Class<TeatroFuncionResource> getTeatroFuncionResource(@PathParam("id") Long id) 
    {
        if (teatroLogic.getTeatro(id) == null) 
        {
            throw new WebApplicationException("El recurso /teatros/" + id + " no existe.", 404);
        }
        return TeatroFuncionResource.class;
    }
    
        
    /**
     * Método que retorna las salas de un teatro
     * @param id
     * @return lista de las salas correspondientes al teatro ingresado por parametro
     */
    @GET
    @Path("{id: \\d+}/salas")
    public Class<TeatroSalaResource> getTeatroSalaResource(@PathParam("id") Long id) 
    {
        if (teatroLogic.getTeatro(id) == null) 
        {
            throw new WebApplicationException("El recurso /teatros/" + id + " no existe.", 404);
        }
        return TeatroSalaResource.class;
    }
}
