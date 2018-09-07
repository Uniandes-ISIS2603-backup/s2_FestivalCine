/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.resources;

import co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.festivalcine.dtos.SillaDTO;
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

/**
 * Clase que implementa el recurso "sillas".

 */
@Path("sillas")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped



public class SillaResource {
    
    @Inject
    //SillaLogic sillaLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Crea una nueva silla con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * @param silla {@link SillaDTO} - La silla que se desea
     * guardar.
     * @return JSON {@link SillaDTO} - La silla guardada con el atributo
     * id autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe la sala.
     */
    @POST
    public SillaDTO createSilla(SillaDTO silla) throws BusinessLogicException {
     
        return silla;
    }

    /**
     * Busca y devuelve todas las sillas que existen en la aplicacion.
     *
     * @return JSONArray {@link SillaDTO} - Las sillas encontradas en
     * la aplicación. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<SillaDTO> getSillas() {
   
        return null;
    }

    /**
     * Busca la silla con el id asociado recibido en la URL y la devuelve.
     *
     * @param sillasId Identificador de la silla que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link SillaDTO} - La silla buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la sala.
     */
    @GET
    @Path("{sillasId: \\d+}")
    public SillaDTO getSilla(@PathParam("sillasId") Long sillasId) throws WebApplicationException {

        return null;
    }

    /**
     * Actualiza la silla con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param sillasId Identificador de la silla que se desea
     * actualizar. Este debe ser una cadena de dígitos.
     * @param silla {@link SillaDTO} La Silla que se desea guardar.
     * @return JSON {@link SillaDTO} - La Silla guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la Silla a
     * actualizar.
     */
    @PUT
    @Path("{sillasId: \\d+}")
    public SillaDTO updateSilla(@PathParam("sillasId") Long sillasId, SillaDTO silla) throws WebApplicationException {

        return silla;
    }

    /**
     * Borra la silla con el id asociado recibido en la URL.
     *
     * @param sillasId Identificador de la esala que se desea borrar.
     * Este debe ser una cadena de dígitos.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar la silla
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la silla.
     */
    @DELETE
    @Path("{sillasId: \\d+}")
    public void deleteSala(@PathParam("sillasId") Long sillasId) throws BusinessLogicException {

    }
    
    
    
    
}
