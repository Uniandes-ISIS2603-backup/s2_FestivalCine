/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.resources;

import co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.festivalcine.dtos.SillaDTO;
import co.edu.uniandes.csw.festivalcine.ejb.SalaSillasLogic;
import co.edu.uniandes.csw.festivalcine.ejb.SillaLogic;
import co.edu.uniandes.csw.festivalcine.entities.SillaEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.WebApplicationException;

 /**
 * Clase que implementa el recurso "sala/{id}/sillas".
 *
 * @author María Juliana Moya
 * @version
 */

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SalaSillasResource {

    private static final Logger LOGGER = Logger.getLogger(SalaSillasResource.class.getName());

    @Inject
    private SalaSillasLogic salaSillasLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private SillaLogic sillaLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Asigna una silla a la sala con la informacion que recibe en
     * la URL. Se devuelve la silla que se asignó a la sala. 
     *
     * @param salasId Identificador de la sala que se esta
     * actualizando. Este debe ser una cadena de dígitos.
     * @param sillasId Identificador dde la silla que se desea asignar. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link SillaDTO} - La silla asignada a la sala
     * @throws BusinessLogicException
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la silla.
     */
    @POST
    @Path("{sillasId: \\d+}")
    public SillaDTO asignarSilla(@PathParam("salasId") Long salasId, @PathParam("sillasId") Long sillasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "SalaSillasResource addSilla: input: salasId: {0} , sillasId: {1}", new Object[]{salasId, sillasId});
        if (sillaLogic.getSilla(sillasId) == null) {
            throw new WebApplicationException("El recurso /sillas/" + sillasId + " no existe.", 404);
        }
        SillaDTO sillaDTO = new SillaDTO(salaSillasLogic.addSilla(sillasId, salasId));
        LOGGER.log(Level.INFO, "SalaSillasResource addSilla: output: {0}", sillaDTO);
        return sillaDTO;
    }

    /**
     * Busca y devuelve todos las sillas que existen en la sala.
     * @param salasId Identificador de la sala que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSONArray {@link SillaDTO} - Los sillas encontradas en la sala.
     * Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<SillaDTO> getSillas(@PathParam("salasId") Long salasId) {
        LOGGER.log(Level.INFO, "SalaSIllasResource getSillas: input: {0}", salasId);
        List<SillaDTO> listaSillasDTOs = sillaListEntity2DTO(salaSillasLogic.getSillas(salasId));
        LOGGER.log(Level.INFO, "SalaSillasResource getSillas: output: {0}", listaSillasDTOs.toString());
        return listaSillasDTOs;
    }

    /**
     * Busca la silla con el id asociado dentro de la sala con id asociado.
     *
     * @param salasId Identificador de la sala que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @param sillasId Identificador de la silla que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link SillaDTO} - La silla buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la silla.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la silla en la
     * sala.
     */
    @GET
    @Path("{sillasId: \\d+}")
    public SillaDTO getSilla(@PathParam("salasId") Long salasId, @PathParam("sillasId") Long sillasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "SalaSillasResource getSilla: input: salaId: {0} , sillasId: {1}", new Object[]{salasId, sillasId});
        if (sillaLogic.getSilla(sillasId) == null) {
            throw new WebApplicationException("El recurso /salas/" + salasId + "/sillas/" + sillasId + " no existe.", 404);
        }
        SillaDTO sillaDTO = new SillaDTO(salaSillasLogic.getSilla(salasId, sillasId));
        LOGGER.log(Level.INFO, "SalaSillasResource getSilla: output: {0}", sillaDTO.toString());
        return sillaDTO;
    }
    
    /**
     * Convierte una lista de SillaEntity a una lista de SillaDTO.
     *
     * @param entityList Lista de SillaEntity a convertir.
     * @return Lista de SillaDTO convertida.
     */
    private List<SillaDTO> sillaListEntity2DTO(List<SillaEntity> entityList) {
        List<SillaDTO> list = new ArrayList();
        for (SillaEntity entity : entityList) {
            list.add(new SillaDTO(entity));
        }
        return list;
    }

}
