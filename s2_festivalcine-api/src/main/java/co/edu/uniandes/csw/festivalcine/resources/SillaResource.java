/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.resources;

import co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.festivalcine.dtos.SillaDTO;
import co.edu.uniandes.csw.festivalcine.ejb.SillaLogic;
import co.edu.uniandes.csw.festivalcine.entities.SillaEntity;
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
 * Clase que implementa el recurso "sillas".
 * @author: María Juliana Moya
 */
@Path("sillas")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped



public class SillaResource {
    private static final Logger LOGGER = Logger.getLogger(SillaResource.class.getName());
    

    @Inject
    SillaLogic sillaLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

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
     * Error de lógica que se genera cuando ya existe la silla.
     */
    @POST
    public SillaDTO createSilla(SillaDTO silla) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "SillaResource createSilla: input: {0}", silla.toString());
       // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        SillaEntity sillaEntity = silla.toEntity();
        // Invoca la lógica para crear la editorial nuev
        SillaEntity nuevoSillaEntity = sillaLogic.createSilla(sillaEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        SillaDTO nuevaSillaDTO = new SillaDTO(nuevoSillaEntity);
        LOGGER.log(Level.INFO, "SillaResource createSilla: output: {0}", nuevaSillaDTO.toString());
        return nuevaSillaDTO;
    }

    /**
     * Busca y devuelve todas las sillas que existen en la aplicacion.
     *
     * @return JSONArray {@link SillaDTO} - Las sillas encontradas en
     * la aplicación. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<SillaDTO> getSillas() {
       LOGGER.info("SillaResource getsillas: input: void");
        List<SillaDTO> listaSillas = listEntity2DetailDTO(sillaLogic.getSillas());
        LOGGER.log(Level.INFO, "SillaResource getsillas: output: {0}", listaSillas.toString());
        return listaSillas;
    }

    /**
     * Busca la silla con el id asociado recibido en la URL y la devuelve.
     *
     * @param sillasId Identificador de la silla que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link SillaDTO} - La silla buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la silla.
     */
    @GET
    @Path("{sillasId: \\d+}")
    public SillaDTO getSilla(@PathParam("sillasId") Long sillasId) throws WebApplicationException {
       LOGGER.log(Level.INFO, "SillaResource getSilla: input: {0}", sillasId);
       SillaEntity sillaEntity = sillaLogic.getSilla(sillasId);
       if (sillaEntity == null) {
           throw new WebApplicationException("El recurso /sillas/" + sillasId + " no existe.", 404);
       }
       SillaDTO detailDTO = new SillaDTO(sillaEntity);
       LOGGER.log(Level.INFO, "SillaResource getSilla: output: {0}", detailDTO.toString());
       return detailDTO;
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
       LOGGER.log(Level.INFO, "SillaResource updateSilla: input: id:{0} , silla: {1}", new Object[]{sillasId, silla.toString()});
       silla.setId(sillasId);
       if (sillaLogic.getSilla(sillasId) == null) {
           throw new WebApplicationException("El recurso /sillas/" + sillasId + " no existe.", 404);
       }
       SillaDTO detailDTO = new SillaDTO(sillaLogic.updateSilla(sillasId, silla.toEntity()));
       LOGGER.log(Level.INFO, "SillaResource updateSilla: output: {0}", detailDTO.toString());
       return silla;
    }

    /**
     * Borra la silla con el id asociado recibido en la URL.
     *
     * @param sillasId Identificador de la esilla que se desea borrar.
     * Este debe ser una cadena de dígitos.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar la silla
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la silla.
     */
    @DELETE
    @Path("{sillasId: \\d+}")
    public void deleteSilla(@PathParam("sillasId") Long sillasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "SillaResource deleteSilla: input: {0}", sillasId);
        if (sillaLogic.getSilla(sillasId) == null) {
            throw new WebApplicationException("El recurso /sillas/" + sillasId + " no existe.", 404);
        }
        sillaLogic.deleteSilla(sillasId);
        LOGGER.info("SillaResource deleteSilla: output: void");

    }
    
        /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos SillaEntity a una lista de
     * objetos SillaDTO (json)
     *
     * @param sillaList corresponde a la lista de funciones de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de sillas en forma DTO (json)
     */
    private List<SillaDTO> listEntity2DetailDTO(List<SillaEntity> entityList) {
        List<SillaDTO> list = new ArrayList<>();
        for (SillaEntity entity : entityList) {
            list.add(new SillaDTO(entity));
        }
        return list;
    }
}
