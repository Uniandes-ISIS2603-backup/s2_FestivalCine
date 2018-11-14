/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.resources;

import co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.festivalcine.dtos.SalaDTO;
import co.edu.uniandes.csw.festivalcine.dtos.SalaDetailDTO;
import co.edu.uniandes.csw.festivalcine.ejb.SalaLogic;
import co.edu.uniandes.csw.festivalcine.entities.SalaEntity;
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
 * Clase que implementa el recurso "salas".
 * @author: María Juliana Moya
 */
@Path("salas")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class SalaResource {
    private static final Logger LOGGER = Logger.getLogger(SalaResource.class.getName());

    @Inject
    SalaLogic salaLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Crea una nueva sala con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * @param sala {@link SalaDTO} - La sala que se desea
     * guardar.
     * @return JSON {@link SalaDTO} - La sala guardada con el atributo
     * id autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe la sala.
     */
    @POST
    public SalaDTO createSala(SalaDTO sala) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "SalaResource createSala: input: {0}", sala.toString());
       // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        SalaEntity salaEntity = sala.toEntity();
        // Invoca la lógica para crear la editorial nueva
        SalaEntity nuevoSalaEntity = salaLogic.createSala(salaEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        SalaDTO nuevaSalaDTO = new SalaDTO(nuevoSalaEntity);
        LOGGER.log(Level.INFO, "SalaResource createSala: output: {0}", nuevaSalaDTO.toString());
        return nuevaSalaDTO;
    }

    /**
     * Busca y devuelve todas las salas que existen en la aplicacion.
     *
     * @return JSONArray {@link SalaDTO} - Las salas encontradas en
     * la aplicación. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<SalaDetailDTO> getsalas() {
        LOGGER.info("SalaResource getsalas: input: void");
        List<SalaDetailDTO> listaSalas = listEntity2DetailDTO(salaLogic.getSalas());
        LOGGER.log(Level.INFO, "SalaResource getsalas: output: {0}", listaSalas.toString());
        return listaSalas;
    }

    /**
     * Busca la sala con el id asociado recibido en la URL y la devuelve.
     *
     * @param salasId Identificador de la funcion que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link SalaDTO} - La sala buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la sala.
     */
    @GET
    @Path("{salasId: \\d+}")
    public SalaDetailDTO getSala(@PathParam("salasId") Long salasId) throws WebApplicationException {
       LOGGER.log(Level.INFO, "SalaResource getSala: input: {0}", salasId);
       SalaEntity salaEntity = salaLogic.getSala(salasId);
       if (salaEntity == null) {
           throw new WebApplicationException("El recurso /salas/" + salasId + " no existe.", 404);
       }
       SalaDetailDTO detailDTO = new SalaDetailDTO(salaEntity);
       LOGGER.log(Level.INFO, "SalaResource getSala: output: {0}", detailDTO.toString());
       return detailDTO;
    }

    /**
     * Actualiza la sala con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param salasId Identificador de la sala que se desea
     * actualizar. Este debe ser una cadena de dígitos.
     * @param sala {@link SalaDTO} La sala que se desea guardar.
     * @return JSON {@link SalaDTO} - La sala guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la sala a
     * actualizar.
     */
    @PUT
    @Path("{salasId: \\d+}")
    public SalaDTO updateSala(@PathParam("salasId") Long salasId, SalaDTO sala) throws WebApplicationException {
       LOGGER.log(Level.INFO, "SalaResource updateSala: input: id:{0} , sala: {1}", new Object[]{salasId, sala.toString()});
       if (salaLogic.getSala(salasId) == null) {
           throw new WebApplicationException("El recurso /salas/" + salasId + " no existe.", 404);
       }
       SalaDTO detailDTO = new SalaDTO(salaLogic.updateSala(salasId, sala.toEntity()));
       LOGGER.log(Level.INFO, "SalaResource updateSala: output: {0}", detailDTO.toString());
       return sala;
    }

    /**
     * Borra la sala con el id asociado recibido en la URL.
     *
     * @param salasId Identificador de la sala que se desea borrar.
     * Este debe ser una cadena de dígitos.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar la sala
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la sala.
     */
    @DELETE
    @Path("{salasId: \\d+}")
    public void deleteSala(@PathParam("salasId") Long salasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "SalaResource deleteSala: input: {0}", salasId);
        if (salaLogic.getSala(salasId) == null) {
            throw new WebApplicationException("El recurso /salas/" + salasId + " no existe.", 404);
        }
        salaLogic.deleteSala(salasId);
        LOGGER.info("SalaResource deleteSala: output: void");
    }

    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos SalaEntity a una lista de
     * objetos SalaDTO (json)
     *
     * @param salaList corresponde a la lista de funciones de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de salas en forma DTO (json)
     */
    private List<SalaDetailDTO> listEntity2DetailDTO(List<SalaEntity> entityList) {
        List<SalaDetailDTO> list = new ArrayList<>();
        for (SalaEntity entity : entityList) {
            list.add(new SalaDetailDTO(entity));
        }
        return list;
    }

    
    
}
