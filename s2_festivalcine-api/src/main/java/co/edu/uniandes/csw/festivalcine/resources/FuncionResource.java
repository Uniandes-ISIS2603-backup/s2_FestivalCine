/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.resources;

import co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException;
import co.uniandes.csw.festivalcine.dtos.FuncionDTO;
import co.uniandes.csw.festivalcine.dtos.SalaDTO;
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
 * Clase que implementa el recurso "funciones"
 * @author María Juliana Moya
 */
@Path("funciones")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class FuncionResource {
    
    //private static final Logger LOGGER = Logger.getLogger(FuncionResource.class.getName());

    @Inject
    //FuncionLogic funcionLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Crea una nueva funcion con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * @param funcion {@link FuncionDTO} - La funcion que se desea
     * guardar.
     * @return JSON {@link FuncionDTO} - La funcion guardada con el atributo
     * id autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe la funcion.
     */
    @POST
    public FuncionDTO createFuncion(FuncionDTO funcion) throws BusinessLogicException {
    //    LOGGER.log(Level.INFO, "FuncionResource createFuncion: input: {0}", funcion.toString());
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
    //    FuncionEntity funcionEntity = funcion.toEntity();
        // Invoca la lógica para crear la editorial nueva
    //    FuncionlEntity nuevoFuncionEntity = funcionLogic.createFuncion(funcionEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
    //    FuncionDTO nuevoFuncionDTO = new FuncionDTO(nuevoFuncionEntity);
    //    LOGGER.log(Level.INFO, "FuncionResource createFuncion: output: {0}", nuevoFuncionDTO.toString());
        return funcion;
    }

    /**
     * Busca y devuelve todas las funciones que existen en la aplicacion.
     *
     * @return JSONArray {@link FuncionDTO} - Las funciones encontradas en
     * la aplicación. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<FuncionDTO> getFunciones() {
    //    LOGGER.info("FuncionResource getFunciones: input: void");
    //    List<FuncionDTO> listaFunciones = listEntity2DetailDTO(funcionLogic.getFunciones());
    //    LOGGER.log(Level.INFO, "FuncionResource getFunciones: output: {0}", listaFunciones.toString());
        return null;
    }

    /**
     * Busca la funcion con el id asociado recibido en la URL y la devuelve.
     * @param funcionesId Identificador de la funcion que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link FuncionDTO} - La funcion buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la editorial.
     */
    @GET
    @Path("{funcionesId: \\d+}")
    public FuncionDTO getFuncion(@PathParam("funcionesId") Long funcionesId) throws WebApplicationException {
    //    LOGGER.log(Level.INFO, "FuncionResource getFuncion: input: {0}", funcionesId);
    //    FuncionEntity funcionEntity = funcionLogic.getFuncion(funcionesId);
    //    if (funcionEntity == null) {
    //        throw new WebApplicationException("El recurso /funciones/" + funcionesId + " no existe.", 404);
    //     }
    //    FuncionDTO detailDTO = new FuncionDTO(funcionEntity);
    //    LOGGER.log(Level.INFO, "FuncionResource getFuncion: output: {0}", detailDTO.toString());
        return null;
    }
    
    
    

    /**
     * Actualiza la funcion con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param funcionesId Identificador de la editorial que se desea
     * actualizar. Este debe ser una cadena de dígitos.
     * @param funcion {@link FuncionDTO} La Funcion que se desea guardar.
     * @return JSON {@link FuncionDTO} - La Funcion guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la funcion a
     * actualizar.
     */
    @PUT
    @Path("{funcionesId: \\d+}")
    public FuncionDTO updateFuncion(@PathParam("funcionesId") Long funcionesId, FuncionDTO funcion) throws WebApplicationException {
    //    LOGGER.log(Level.INFO, "FuncionResource updateFuncion: input: id:{0} , funcion: {1}", new Object[]{funcionesId, funcion.toString()});
    //    funcion.setId(funcionesId);
    //    if (funcionLogic.getEditorial(editorialsId) == null) {
    //        throw new WebApplicationException("El recurso /funciones/" + funcionesId + " no existe.", 404);
    //    }
    //    //FuncionDTO detailDTO = new FuncionDTO(funcionLogic.updateEditorial(editorialsId, editorial.toEntity()));
    //    LOGGER.log(Level.INFO, "EditorialResource updateEditorial: output: {0}", detailDTO.toString());
        return funcion;
    }

    /**
     * Borra la funcion con el id asociado recibido en la URL.
     *
     * @param funcionesId Identificador de la funcion que se desea borrar.
     * Este debe ser una cadena de dígitos.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar la funcion.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la funcion.
     */
    @DELETE
    @Path("{funcionesId: \\d+}")
    public void deleteFuncion(@PathParam("funcionesId") Long funcionesId) throws BusinessLogicException {
    //    LOGGER.log(Level.INFO, "FuncionResource deleteFuncion: input: {0}", funcionesId);
    //    if (funcionLogic.getEditorial(funcionesId) == null) {
    //        throw new WebApplicationException("El recurso /funciones/" + funcionesId + " no existe.", 404);
    //    }
    //    funcionLogic.deleteFuncion(funcionesId);
    //    LOGGER.info("EditorialResource deleteEditorial: output: void");
    }
    
        
    /**
     * Busca la funcion con el id asociado recibido en la URL y devuelve la pelicula de esa función.
     * @param funcionesId Identificador de la funcion que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link PeliculaDTO} - La pelicula de la función buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la función.
     */
    @GET
    @Path("{funcionesId: \\d+}/peliculas")
    public PeliculaDTO getPeliculaFuncion(@PathParam("funcionesId") Long funcionesId) throws WebApplicationException {
    //    LOGGER.log(Level.INFO, "FuncionResource getFuncion: input: {0}", funcionesId);
    //    FuncionEntity funcionEntity = funcionLogic.getFuncion(funcionesId);
    //    if (funcionEntity == null) {
    //        throw new WebApplicationException("El recurso /funciones/" + funcionesId + " no existe.", 404);
    //     }
    //    FuncionDTO detailDTO = new FuncionDTO(funcionEntity);
    //    LOGGER.log(Level.INFO, "FuncionResource getFuncion: output: {0}", detailDTO.toString());
        return null;
    }
    
    /**
     * Busca la funcion con el id asociado recibido en la URL y devuelve el crítico de esa función.
     * @param funcionesId Identificador de la funcion que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link CriticoDTO} - El critico de la función buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la función.
     */
    @GET
    @Path("{funcionesId: \\d+}/criticos")
    public CriticoDTO getCriticoFuncion(@PathParam("funcionesId") Long funcionesId) throws WebApplicationException {
    //    LOGGER.log(Level.INFO, "FuncionResource getFuncion: input: {0}", funcionesId);
    //    FuncionEntity funcionEntity = funcionLogic.getFuncion(funcionesId);
    //    if (funcionEntity == null) {
    //        throw new WebApplicationException("El recurso /funciones/" + funcionesId + " no existe.", 404);
    //     }
    //    FuncionDTO detailDTO = new FuncionDTO(funcionEntity);
    //    LOGGER.log(Level.INFO, "FuncionResource getFuncion: output: {0}", detailDTO.toString());
        return null;
    }
    
    /**
     * Busca la funcion con el id asociado recibido en la URL y devuelve la sala de esa función.
     * @param funcionesId Identificador de la funcion que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link SalaDTO} - La sala de la función buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la función.
     */
    @GET
    @Path("{funcionesId: \\d+}/salas")
    public SalaDTO getSalaFuncion(@PathParam("funcionesId") Long funcionesId) throws WebApplicationException {
    //    LOGGER.log(Level.INFO, "FuncionResource getFuncion: input: {0}", funcionesId);
    //    FuncionEntity funcionEntity = funcionLogic.getFuncion(funcionesId);
    //    if (funcionEntity == null) {
    //        throw new WebApplicationException("El recurso /funciones/" + funcionesId + " no existe.", 404);
    //     }
    //    FuncionDTO detailDTO = new FuncionDTO(funcionEntity);
    //    LOGGER.log(Level.INFO, "FuncionResource getFuncion: output: {0}", detailDTO.toString());
        return null;
    }
    
    
    
    
    
   
    
    
}
