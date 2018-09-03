/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.resources;

import co.edu.uniandes.csw.festivalcine.dtos.TeatroDetailDTO;
import co.edu.uniandes.csw.festivalcine.dtos.TeatroDTO;
import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
     * Crea un nuevo teatro con la informacion que se recibe en el cuerpo de la
     * petición y se regresa un objeto identico con un id auto-generado por la
     * base de datos.
     *
     * @param teatro - EL teatro que se desea guardar.
     * @return JSON  - El teatro guardado con el atributo id
     * autogenerado.
     */
    @POST
    public TeatroDTO createTeatro(TeatroDTO teatro) {
        return teatro;
    }
    
        /**
     * Busca y devuelve todos los teatros que existen en la aplicacion.
     *
     * @return JSONArray - Los teatros encontrados en la
     * aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<TeatroDTO> getTeatro() {

        return null;
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
    public TeatroDTO getTeatro(@PathParam("id") Long id) {

        return null;
    }
    
        /**
     * Actualiza el teatro con el id recibido en la URL con la información que se
     * recibe en el cuerpo de la petición.
     *
     * @param id Identificador del teatro que se desea actualizar. Este
     * debe ser una cadena de dígitos.
     * @param tatro  El teatro que se desea guardar.
     * @return JSON - El teatro guardado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el teatro a
     * actualizar.
     */
    @PUT
    @Path("{id: \\d+}")
    public TeatroDTO updateTatro(@PathParam("id") Long id, TeatroDTO teatro) {
        
        return teatro;
    }
    
    @DELETE
    @Path("{id: \\d+}")
    public void deleteTeatro(@PathParam("id") Long id)
    {
       
    }
}
