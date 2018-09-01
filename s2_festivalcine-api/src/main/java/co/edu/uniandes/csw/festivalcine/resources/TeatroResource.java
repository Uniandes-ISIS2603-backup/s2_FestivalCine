/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.resources;

import co.edu.uniandes.csw.festivalcine.dtos.FestivalDetailDTO;
import co.edu.uniandes.csw.festivalcine.dtos.TeatroDTO;
import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author estudiante
 */
@Path("/teatro")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
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
    public TeatroDTO createFestival(TeatroDTO festival) {
        return null;
    }
    
        /**
     * Busca y devuelve todos los teatros que existen en la aplicacion.
     *
     * @return JSONArray - Los teatros encontrados en la
     * aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<TeatroDTO> getFestivals() {

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
    public TeatroDTO getFestival(@PathParam("id") Long id) {

        return null;
    }
    
        /**
     * Actualiza el teatro con el id recibido en la URL con la información que se
     * recibe en el cuerpo de la petición.
     *
     * @param id Identificador del teatro que se desea actualizar. Este
     * debe ser una cadena de dígitos.
     * @param festival  El teatro que se desea guardar.
     * @return JSON - El teatro guardado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el teatro a
     * actualizar.
     */
    @PUT
    @Path("{id: \\d+}")
    public FestivalDetailDTO updatFestival(@PathParam("id") Long id, FestivalDetailDTO festival) {
        
        return null;
    }
}
