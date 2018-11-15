/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.resources;

import co.edu.uniandes.csw.festivalcine.dtos.CalificacionDTO;
import co.edu.uniandes.csw.festivalcine.dtos.CriticoDTO;
import co.edu.uniandes.csw.festivalcine.dtos.CriticoDetailDTO;
import co.edu.uniandes.csw.festivalcine.dtos.FuncionDTO;
import co.edu.uniandes.csw.festivalcine.dtos.PeliculaDTO;
import co.edu.uniandes.csw.festivalcine.ejb.CalificacionLogic;
import co.edu.uniandes.csw.festivalcine.ejb.CriticoLogic;
import co.edu.uniandes.csw.festivalcine.ejb.FuncionLogic;
import co.edu.uniandes.csw.festivalcine.ejb.PeliculaLogic;
import co.edu.uniandes.csw.festivalcine.entities.CalificacionEntity;
import co.edu.uniandes.csw.festivalcine.entities.CriticoEntity;
import co.edu.uniandes.csw.festivalcine.entities.FuncionEntity;
import co.edu.uniandes.csw.festivalcine.entities.PeliculaEntity;
import co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

@Path("criticos")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped

//Clase critico

/**
 *  Clase que implementa el recurso "criticos"
 * 
 * @author Andres Felipe Rodriguez Murillo - 201716905
 */
public class CriticoResource {
    
    @Inject
    private CriticoLogic criticoLogic;
    
    @Inject
    private FuncionLogic funcionLogic;
    
    @Inject
    private PeliculaLogic peliculaLogic;
            
    @Inject
    private CalificacionLogic calificacionLogic;
    
    private static final Logger LOGGER = Logger.getLogger(CriticoResource.class.getName());

    /**
     * Crea un nuevo autor con la información que se recibe en el cuerpo de la
     * petición y se regresa un objeto identico con un id auto-generado por la
     * base de datos.
     * 
     * @param critico {@link CriticoDTO} - El critico que se desea guardar.
     * @return JSON {@link CriticoDTO} - El critico guardado con el atributo id
     * autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe el critico.
     */
    @POST
    public CriticoDTO createCritico(CriticoDTO critico) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO,"CriticoResource createCritico: input: {0}" + critico.toString());
        CriticoDTO nuevoCriticoDTO = new CriticoDTO(criticoLogic.createCritico(critico.toEntity()));
        LOGGER.log(Level.INFO, "CriticoResource createCritico: output: {0}", nuevoCriticoDTO.toString());
        return nuevoCriticoDTO;
    }
    
    /**
     * Busca y devuelve todos los criticos que existen en la aplicación.
     * 
     * @return JSONArray {@link CriticoDetailDTO} - Los criticos encontrados en la
     * aplicación. Si no hay ninguno retorna una lista vacia.
     */
    @GET
    public List<CriticoDetailDTO> getCriticos()
    {
        LOGGER.info("CriticoResource getCriticos: input: void");
        List<CriticoDetailDTO> listaCriticos = listEntity2DetailDTO(criticoLogic.getCriticos());
        LOGGER.log(Level.INFO, "CriticoResource getCriticos: output: {0}", listaCriticos.toString());
        return listaCriticos;
    }
    
    /**
     * Busca el critico con el id asociado en la URL y lo devuelve.
     * 
     * @param criticosId Identificador del critico que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link CriticoDetailDTO} - El autor buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} - 
     * Error de lógica que se genera cuendo no se encuentra el critico.
     */
    @GET
    @Path("{criticosId: \\d+}")
    public CriticoDetailDTO getCritico(@PathParam("criticosId") Long criticosId) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO,"CriticoResource getCritico: input: {0}", criticosId);
        CriticoEntity criticoEntity = criticoLogic.getCritico(criticosId);
        if(criticoEntity == null)
        {
            throw new WebApplicationException("El recurso /criticos/" + criticosId + " no existe.", 404);
        }
        CriticoDetailDTO criticoDetailDTO = new CriticoDetailDTO(criticoEntity);
        LOGGER.log(Level.INFO, "CriticoResource getCritico: output: {0}", criticoDetailDTO.toString());
        return criticoDetailDTO;
    }
    
    /**
     * Actualiza el critico con el id recibido en la URL con la información que se
     * recibe en el cuerpo de la peticion.
     * 
     * @param criticosId Identificador del critico que se desa actualizar.Este
     * debe ser una cadena de digitos.
     * @param critico {@link CriticoDetailDTO} El critico que se desea guardar.
     * @return JSON {@link CriticoDetailDTO} - El critico guardado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el critico a
     * actualizar.
     */
    @PUT
    @Path("{criticosId: \\d+}")
    public CriticoDetailDTO updateCritico(@PathParam("criticosId") Long criticosId, CriticoDetailDTO critico) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "CriticoResource updateCritico: input: id: {0} , critico: {1}", new Object[]{criticosId, critico.toString()});
        critico.setId(criticosId);
        if(criticoLogic.getCritico(criticosId) == null)
        {
            throw new WebApplicationException("El recurso /criticos/" + criticosId + " no existe.", 404);      
        }
        CriticoDetailDTO detailDTO = new CriticoDetailDTO(criticoLogic.updateCritico(critico.toEntity()));
        LOGGER.log(Level.INFO, "CriticoResource updateCritico: output: {0}", detailDTO.toString());
        return detailDTO;
    }
    
    /**
     * Borra el critico con el id asociado recibido en la URL.
     * 
     * @param criticosId Identificador del critico que se desea borra. Este debe
     * ser una cadena de dígitos.
     * @throws co.edu.uniandes.csw.festivalcine.exceotion.BusinessLogicException
     * si el critico tiene funciones asociadas.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra el critico a borrar.
     */
    @DELETE
    @Path("{criticosId: \\d+}")
    public void deleteCritico(@PathParam("criticosId") Long criticosId) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "CriticoResource deleteCritico: input: {0}", criticosId);
        CriticoEntity entity = criticoLogic.getCritico(criticosId);
        if(entity == null)
        {
            throw new WebApplicationException("El recurso /critico/" + criticosId  +" no existe.", 404);
        }
        criticoLogic.deleteCritico(criticosId);
        LOGGER.info("CriticoResource deleteCritico: output: void");
    }
        
    //----------------------------------------------------------------------------------------------------------------------------------------------------
    //Funciones
    //----------------------------------------------------------------------------------------------------------------------------------------------------
    
    /**
     * Relaciona una funcion con el id recibido en la URL con el critico con la 
     * id recibida en la URL
     * 
     * @param criticosId Identificador del critico al que se le desea asignar la 
     * función. Este debe ser una cadena de digitos.
     * @param funcionesId Identificador de la función que se va a asignar al 
     * critico. Este debe ser una cadena de digitos.
     * @return JSON {@link FuncionDTO} - La funcion agregada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la funcion.
     */
    @POST
    @Path("{criticosId: \\d+}/funciones/{funcionesId: \\d+}")
    public FuncionDTO addFuncion(@PathParam("criticosId") Long criticosId, @PathParam("funcionesId") Long funcionesId) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "CriticoResource addFuncion: input: criticosId {0} , funcionesId {1}", new Object[]{criticosId, funcionesId});
        if(funcionLogic.getFuncion(funcionesId) == null)
        {
            throw new WebApplicationException("El recurso /funciones/" + funcionesId + " no existe.", 404);
        }
        FuncionDTO funcionDTO = new FuncionDTO(criticoLogic.addFuncion(criticosId, funcionesId));
        LOGGER.log(Level.INFO, "CriticoResource addBook: output: {0}", funcionDTO);
        return funcionDTO;
    }
    
    /**
     * Busca las todas las funciones de un critico con la id recibida en la URL
     * 
     * @param criticosId Identificacion del critico del que se desea bucar las
     * funciones. Este debe ser una cadena de digitos.
     * @return JSONArray {@link FuncionDTO} - Las funciones de un critico encontradas
     * en la aplicación. Si no hay ninguna retorna una lista vacia.
     */
    @GET
    @Path("{criticosId: \\d+}/funciones/")
    public List<FuncionDTO> getfunciones(@PathParam("criticoId") Long criticosId) throws WebApplicationException
    {
        LOGGER.log(Level.INFO, "CriticoResource getFunciones: input {0}", criticosId);
        List<FuncionDTO> lista = funcionesListEntity2DTO(criticoLogic.getFunciones(criticosId));
        if(lista == null)
        {
            throw new WebApplicationException("El recurso /criticos/" + criticosId + "/funciones no existe.", 404);
        }
        LOGGER.log(Level.INFO, "CriticoResource getFunciones: input: {0}", lista.toString());
        return lista;
    }
 
    /**
     * Busca la funcion con la id recibida en la URL del critico con la id recibida
     * en la URL
     * 
     * @param criticosId Identificación del critico del que se desea buscar la
     * funcion. Este debe ser una cadena de digitos
     * @param funcionesId Identificación de la función que se desea buscar. Este
     * debe ser una cadena de digitos.
     * @return JSON {@link FuncionDTO} - La funcion encontrada.
     * @throws co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException
     * si la funcion no está asociado al critico
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la funcion.
     */
    @GET
    @Path("{criticosId: \\d+}/funciones/{funcionesId: \\d+}")
    public FuncionDTO getfuncion(@PathParam("criticoId") Long criticosId, @PathParam("funcionesId") Long funcionesId) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "CriticoResource getFuncion: input: criticosId {0} , funcionesId {1}", new Object[]{criticosId, funcionesId});
        if(funcionLogic.getFuncion(funcionesId) == null)
        {
            throw new WebApplicationException("El recurso /funciones/" + funcionesId + " no existe.", 404);
        }
        FuncionDTO funcionDTO = new FuncionDTO(criticoLogic.getFuncion(criticosId, funcionesId));
        LOGGER.log(Level.INFO, "CriticoResource getFuncion: output: {0}", funcionDTO);
        return funcionDTO;
    }
    
    /**
     * Elimina la funcion con la id recibida en la URL del critico con la id recibida
     * en la URL
     * 
     * @param criticosId Identificación del critico del que se desea eliminar la 
     * función. Este debe ser una cadena de digitos.
     * @param funcionesId Identificacion de la funcion que se desea eliminar. Este
     * debe ser una cadena de digitos.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la función.
     */
    @DELETE
    @Path("{criticosId: \\d+}/funciones/{funcionesId: \\d+}")
    public void deleteFuncion(@PathParam("criticosId:") Long criticosId, @PathParam("funcionesId") Long funcionesId) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "CriticoResource deleteFuncion: input: criticosId {0} , funcionesId {1}", new Object[]{criticosId, funcionesId});
        if(criticoLogic.getFuncion(criticosId ,funcionesId) == null)
        {
            throw new WebApplicationException("El recurso /funciones/" +funcionesId + " no existe.", 404);
        }
        criticoLogic.removeFuncion(criticosId, funcionesId);
        LOGGER.info("CriticoResource deleteFuncion: output: void");
    }
    
    /**
     * Convierte una lista de FuncionENtity a una lista de FuncionDTO.
     * 
     * @param entityList Lista de FuncionEntity a convertir.
     * @return  Lista de FuncionDTO convertida.
     */
    private List<FuncionDTO> funcionesListEntity2DTO(List<FuncionEntity> entityList)
    {
        List<FuncionDTO> list = new ArrayList<>();
        for(FuncionEntity entity : entityList)
        {
            list.add(new FuncionDTO(entity));
        }
        return list;
    }
    
    //----------------------------------------------------------------------------------------------------------------------------------------------------
    //Peliculas
    //----------------------------------------------------------------------------------------------------------------------------------------------------
    
    /**
     * Relaciona una pelicula con la id recibida en la URL con el critico con la id
     * recibida en la URL
     * 
     * @param criticoId Identificación del critico al que se le desea agregar la
     * pelicula. Este debe ser una cadena de digitos.
     * @param peliculasId Identificación de la pelicula que se le dese agregar al
     * critico. Este debe ser una cadena de dígitos.
     * @return JSON {@link PeliculaDTO} Pelicula relacionada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la pelicula.
     */
    @POST
    @Path("{criticosId: \\d+}/peliculas/{peliculasId: \\d+}")
    public PeliculaDTO addPelicula(@PathParam("criticosId") Long criticosId, @PathParam("peliculasId") Long peliculasId)
    {
        LOGGER.log(Level.INFO, "CriticoResource addPelicula: input: criticosId{0}, peliculasId {1}", new Object[]{criticosId, peliculasId});
        if(peliculaLogic.findById(peliculasId) == null)
        {
            throw new WebApplicationException("El recurso /peliculas/" + peliculasId + " no existe.", 404);
        }
        PeliculaDTO peliculaDTO = new PeliculaDTO(criticoLogic.addPelicula(criticosId, peliculasId));
        LOGGER.log(Level.INFO, "CriticoResource addPelicula: output: {0}", peliculaDTO.toString());
        return peliculaDTO;
    }    
    /**
     * Muestra las peliculas de un critico con la id recibido en la URL
     * 
     * @param criticosId Identificacion del critico del que se desea saber las peliculas.
     * Este debe ser una cadena de dígitos.
     * @return JSONArray {@link PeliculaDTO} Peliculas del critico encontrasas. 
     * En caso de no encontrar se retorna vacia la lista.
     */
    @GET
    @Path("{criticosId: \\d+}/peliculas/")
    public List<PeliculaDTO> getPeliculas(@PathParam("criticosId") Long criticosId) throws WebApplicationException
    {
        LOGGER.log(Level.INFO, "CriticoResource getPeliculas: input: {0}", criticosId);
        List<PeliculaDTO> lista = peliculasListEntity2DTO(criticoLogic.getPeliculas(criticosId));
        if(lista == null)
        {
            throw new WebApplicationException("El recurso /criticos/" + criticosId + "/peliculas no existe.", 404);
        }
        LOGGER.log(Level.INFO, "CriticoResource getPeliculas: output: {0}", lista.toString());
        return lista;
    }
    
    /**
     * Busca la pelicula con la id recibida en la URL del critico con la id recibida
     * en la URL
     * 
     * @param criticosId Identificacion del critico del que se desea saber la pelicula.
     * Este debe ser una cadena de dígitos.
     * @param peliculasId Identificacion de la pelicula que se desea buscar. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link PeliculaDTO} Pelicula encontrada.
     * @throws co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException
     * si la pelicula no está asociado al critico.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la pelicula.
     */
    @GET
    @Path("{criticosId: \\d+}/peliculas/{peliculasId: \\d+}")
    public PeliculaDTO getPelicula(@PathParam("criticosId") Long criticosId, @PathParam("peliculasId") Long peliculasId)
    {
        LOGGER.log(Level.INFO, "CriticoResource getPelicula: input: criticosId {0}, peliculasId{1}", new Object[] {criticosId, peliculasId});
        if(peliculaLogic.findById(peliculasId) == null)
        {
            throw new WebApplicationException("El recurso /peliculas/" + peliculasId + " no existe." ,404);
        }
        PeliculaDTO peliculaDTO = new PeliculaDTO(criticoLogic.getPelicula(criticosId, peliculasId));
        LOGGER.log(Level.INFO, "CriticoResource getPelicula: ouput: {0}", peliculaDTO.toString());
        return peliculaDTO;
       
 
    }
    
    /**
     * Elimina la pelicula con la id recibida en la URL del critico con la id
     * recibida en la URL
     * 
     * @param criticosId Identificacion del critico del que se desea eliminar la pelicula.
     * Este debe ser una cadena de dígitos.
     * @param peliculasId Identificacion de la pelicula que se desea eliminar. Este
     * debe ser una cadena de dígitos.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la pelicula.
     */
    @DELETE
    @Path("{criticosId: \\d+}/peliculas/{peliculasId: \\d+}")
    public void deletePelicula(@PathParam("criticosId") Long criticosId, @PathParam("peliculasId") Long peliculasId)
    {
        LOGGER.log(Level.INFO, "CriticoResource deletePelicula: input: criticosId{0}, peliculasId{1}", new Object[]{criticosId, peliculasId});
        if(peliculaLogic.findById(peliculasId) == null)
        {
            throw new WebApplicationException("El recurso /peliculas/" + peliculasId + " no existe.", 404);
        }
        criticoLogic.deletePelicula(criticosId, peliculasId);
        LOGGER.info("CriticoResource deletePelicula: output: void");
    }
    
    /**
     * Convierte una  lista de PeliculaEtity a una lista de PeliculaDTO.
     * 
     * @param entityList List de PeliculaEntity a convertir.
     * @return Lista de PeliculaDTO convertida.
     */
    private List<PeliculaDTO> peliculasListEntity2DTO(List<PeliculaEntity>entityList)
    {
        List<PeliculaDTO> list = new ArrayList<>();
        for(PeliculaEntity entity : entityList)
        {
            list.add(new PeliculaDTO(entity));
        }
        return list;
    }
    
    //----------------------------------------------------------------------------------------------------------------------------------------------------
    //Funciones
    //----------------------------------------------------------------------------------------------------------------------------------------------------
    
    /**
     * Relaciona una calificacion con la id recibida en la URL al critico con 
     * la id recibida en la URL
     * 
     * @param criticoId Identificación del critico al que se le desea agregar la
     * calificacion. Este debe ser una cadena de digitos.
     * @param calificacionesId Identificación de la calificacion que se le dese 
     * agregar al critico. Este debe ser una cadena de dígitos.
     * @return JSON {@link CalificacionDTO} Calificacion relacionada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la calificacion.
     */
    @POST
    @Path("{criticosId: \\d+}/calificaciones/{calificacionesId: \\d+}")
    public CalificacionDTO addCalificacion(@PathParam("criticosId") Long criticosId, @PathParam("calificacionesId") Long calificacionesId) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "CriticoResource add: input: criticosId {0}, peliculasId {1}", new Object[]{criticosId, calificacionesId});
        if(calificacionLogic.getCalificacion(calificacionesId) == null)
        {
            throw new WebApplicationException("El recurso /calificaciones/" + calificacionesId + " no existe.", 404);
        }
        CalificacionDTO calificacionDTO = new CalificacionDTO(criticoLogic.addCalificacion(criticosId, calificacionesId));
        LOGGER.log(Level.INFO, "CriticosResource addCalificacion: output: {0}", calificacionDTO.toString());
        return calificacionDTO;
    }
    
    /**
     * Muestra todas las calificaciones del critico con la id recibida en la URL
     * 
     * @param criticosId Identificacion del critico del que se desea mostrar las
     * funciones. Este debe ser una cadena de dígitos.
     * @return JSONArray {@link CalificacionDTO} Calificaicones encontradas. Si no
     * hay ninguna retorna la lista vacia.
     */
    @GET
    @Path("{criticosId: \\d+}/calificaciones/")
    public List<CalificacionDTO> getCalificaciones(@PathParam("criticosId") Long criticosId) throws WebApplicationException
    {
        LOGGER.log(Level.INFO, "CriticoResource getCalificaciones: input; {0}", criticosId);
        List<CalificacionDTO> lista = calificacionesListEntity2DTO(criticoLogic.getCalificaciones(criticosId));
        if(lista == null)
        {
            throw new WebApplicationException("El recurso /criticos/" + criticosId + "/calificaciones no existe.", 404);
        }
        LOGGER.log(Level.INFO, "CriticoResource getCalificaciones: output: {0}", lista.toString());
        return lista;
    }
    
    /**
     * Busca y devuelve la calificacion con la id recibido en la URL de un cirito
     * con la id recibida en la URL
     * 
     * @param criticosId Identificacion del critico del que se desea buscar la 
     * calificacion. Este debe ser una cadena de dígitos.
     * @param calificacionesId Identificacion de la calificacion que se desea buscar
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link CalificacionDTO} - La calificacion encontrada
     * @throws co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException
     * si la calificacion no esta asociada al critico.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la calificación.
     */
    @GET
    @Path("{criticosId: \\d+}/calificaciones/{calificacionesId: \\d+}")
    public CalificacionDTO getCalificacion(@PathParam("criticosId") Long criticosId, @PathParam("calificacionesId") Long calificacionesId) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "criticoResource getCalificacion: input: criticosId {0}, calificacionesId {1}", new Object[]{criticosId, calificacionesId});
        if(calificacionLogic.getCalificacion(calificacionesId) == null)
        {
            throw new WebApplicationException("El recurso /Ccalificaciones/" + calificacionesId + " noexiste." , 404);
        }
        CalificacionDTO calificacionDTO = new CalificacionDTO(criticoLogic.getCalificacion(criticosId, calificacionesId));
        LOGGER.log(Level.INFO, "CriticoResource getCalificacion: output: {0}", calificacionDTO.toString());
        return calificacionDTO;
    
    }
    
    /**
     * Elimina la calificacion con la id recibida en la URL del critico con la id
     * recibida en la URL
     * 
     * @param criticosId Identitficacion del critico del que se desea eliminar la 
     * calificación. Este debe ser una cadena de cígitos.
     * @param calificacionesId Identificacion de la calificacion que se desea eliminar
     * Este debe ser una cadena de dígitos.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la calificacion.
     */
    @DELETE
    @Path("{criticosId: \\d+}/calificaciones/{calificacionesId: \\d+}")
    public void deleteCalificacion(@PathParam("criticosId") Long criticosId, @PathParam("calificacionesId")Long calificacionesId) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "CriticoResource deleteCalificacion:input_ criticosId {0}, calificacionesId {1}", new Object[]{criticosId, calificacionesId});
        if(calificacionLogic.getCalificacion(calificacionesId) == null)
        {
            throw new WebApplicationException("El recurso /calificaciones/" + calificacionesId + " n existe",404);
        }
        criticoLogic.deleteCalificacion(criticosId, calificacionesId);
        LOGGER.info("CriticosResource deleteCalificacion: ouput: void");
    }
    
    private List<CalificacionDTO> calificacionesListEntity2DTO(List<CalificacionEntity> entityList)
    {
        List<CalificacionDTO> list = new ArrayList();
        for(CalificacionEntity entity : entityList)
        {
            list.add(new CalificacionDTO(entity));
        }
        return list;
    }
    
    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos CriticoEntity a una lista de
     * objetos CriticoDetailDTO (json)
     *
     * @param entityList corresponde a la lista de criticos de tipo Entity que
     * vamos a convertir a DTO.
     * @return la lista de criticos en forma DTO (json)
     */
    private List<CriticoDetailDTO> listEntity2DetailDTO(List<CriticoEntity> entityList) 
    {
        List<CriticoDetailDTO> list = new ArrayList<>();
        for (CriticoEntity entity : entityList) {
            list.add(new CriticoDetailDTO(entity));
        }
        return list;
    }
}
