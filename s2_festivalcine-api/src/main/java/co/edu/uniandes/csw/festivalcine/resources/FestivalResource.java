/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.resources;

import co.edu.uniandes.csw.festivalcine.dtos.FestivalDTO;
import co.edu.uniandes.csw.festivalcine.dtos.FestivalDetailDTO;
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
     * Crea un nuevo festival con la informacion que se recibe en el cuerpo de la
     * petición y se regresa un objeto identico con un id auto-generado por la
     * base de datos.
     *
     * @param festival - EL festival que se desea guardar.
     * @return JSON  - El festival guardado con el atributo id
     * autogenerado.
     */
    @POST
    public FestivalDTO createFestival(FestivalDTO festival) {
        return festival;
    }

    /**
     * Busca y devuelve todos los festivales que existen en la aplicacion.
     *
     * @return JSONArray - Los festivales encontrados en la
     * aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<FestivalDTO> getFestivals() {

        return null;
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
    public FestivalDTO getFestival(@PathParam("id") Long id) {

        return null;
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
    public FestivalDTO updateFestival(@PathParam("id") Long id, FestivalDTO festival) {
        
        return festival;
    }
    
     @DELETE
    @Path("{id: \\d+}")
    public void deleteFestival(@PathParam("id") Long id)
    {
       
    }

}
