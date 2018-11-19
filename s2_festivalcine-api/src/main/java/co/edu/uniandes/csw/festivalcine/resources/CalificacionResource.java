/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.resources;


import co.edu.uniandes.csw.festivalcine.dtos.CalificacionDTO;
import co.edu.uniandes.csw.festivalcine.dtos.UsuarioDetailDTO;
import co.edu.uniandes.csw.festivalcine.ejb.CalificacionLogic;
import co.edu.uniandes.csw.festivalcine.ejb.UsuarioLogic;
import co.edu.uniandes.csw.festivalcine.entities.CalificacionEntity;
import co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException;
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
 * Clase que implementa el recurso "calificaiones"
 *
 * @author Andres Felipe Rodriguez Murillo
 */
@Path("calificaciones")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class CalificacionResource 
{
    String elRecursoCalificaciones = "El recurso /calificaciones/";
    String noexiste = "no existe";
    
    @Inject
    private CalificacionLogic calificacionLogic;
    
    @Inject
    private UsuarioLogic usuarioLogic;
    
    private static final Logger LOGGER = Logger.getLogger(CalificacionResource.class.getName());
    
    /**
     * Crea una nueva calificacion con la información que se recibe en el cuerpo de la
     * petición y se regresa un objeto identico con un id auto_generado por la base de
     * datos
     * 
     * @param calificacion {@link CalificacionDTO} - La calificiacion que se desea guardar.
     * @return JSON {@link CalificacionDTO} - La calificacion guardada con el atributo id
     * autogenerado
     */
    @POST
    public CalificacionDTO createCalificacion(CalificacionDTO calificacion)
    {
        LOGGER.log(Level.INFO, "CalificacionResource createCalificacion: input: {0} " + calificacion.toString());
        CalificacionDTO nuevaCalificacionDTO = new CalificacionDTO(calificacionLogic.createCalificacion(calificacion.toEntity()));
        LOGGER.log(Level.INFO, "CalificacionResource createCalificacion: output: {0}", nuevaCalificacionDTO);
        return nuevaCalificacionDTO;
    }
    
    @GET
    public List<CalificacionDTO> getCalificaciones()
    {
        LOGGER.info("CalificacionResource getCalificaciones: input: void");
        List<CalificacionDTO> listaCalificaciones = listEntity2DTO(calificacionLogic.getCalificaciones());
        LOGGER.log(Level.INFO, "CalificacionResource getCalificaciones: ouput: {0}", listaCalificaciones);
        return listaCalificaciones;
    }
    
    @GET
    @Path("{calificacionesId: \\d+}")
    public CalificacionDTO getCalificacion(@PathParam("calificacionesId") Long calificacionesId) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "CalificaciónResource getCalificacion: input: {0}", calificacionesId);
        CalificacionEntity calificacionEntity = calificacionLogic.getCalificacion(calificacionesId);
        if(calificacionEntity == null)
        {
            throw new WebApplicationException(elRecursoCalificaciones + calificacionesId + noexiste, 404);
        }
        CalificacionDTO calificacionDTO = new CalificacionDTO(calificacionEntity);
        LOGGER.log(Level.INFO, "CalificacionResource get calificacion: output: {0}", calificacionDTO);
        return calificacionDTO;
    }
    
    @PUT
    @Path("{calificacionesID: \\d+}")
    public CalificacionDTO updateCalificacion(@PathParam("calificacionesId") Long calificacionesId, CalificacionDTO calificacion) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "CalificacionResource updateCalificacion: input id: {0}, calificacion: {1}", new Object[]{calificacionesId, calificacion});
        calificacion.setId(calificacionesId);
        if(calificacionLogic.getCalificacion(calificacionesId) == null)
        {
            throw new WebApplicationException(elRecursoCalificaciones + calificacionesId + noexiste, 404);
        }
        CalificacionDTO calificacionDTO = new CalificacionDTO(calificacionLogic.updateCalificacion(calificacion.toEntity()));
       LOGGER.log(Level.INFO, "CalificacionResource updateCalificacion: output: {0}", calificacionDTO);
       return calificacionDTO;
    }
    
    
    @DELETE
    @Path("{calificacionesID: \\d+}")
    public void deleteCalificacion(@PathParam("calificacionesId") Long calificacionesId) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "CalificacionResource deleteCalificacion: input: {0}", calificacionesId);
        CalificacionEntity entity = calificacionLogic.getCalificacion(calificacionesId);
        if(entity == null)
        {
            throw new WebApplicationException(elRecursoCalificaciones + calificacionesId + noexiste, 404);
        }
        calificacionLogic.deleteCalificacion(calificacionesId);
        LOGGER.info("CalificacionResource deleteCalificacion: output: void");
    }
    
    //----------------------------------------------------------------------------------------------------------------------------------------------------
    //Funciones
    //----------------------------------------------------------------------------------------------------------------------------------------------------
    
    @POST
    @Path("{calificacionesId: \\d+}/usuarios/{usuariosId: \\d+}")
    public UsuarioDetailDTO addUsuario(@PathParam("calificacionesId") Long calificacionesId, @PathParam("usuariosId") Long usuariosId) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "CalificacionResource addUsuario: input: calificacionesId {0}, usuariosId {1}", new Object[]{calificacionesId, usuariosId});
        if(usuarioLogic.getUsuario(usuariosId) == null)
        {
            throw new WebApplicationException("El recurso /usuarios/" + usuariosId + " no existe.", 404);
        }
        UsuarioDetailDTO detailDTO = new UsuarioDetailDTO(calificacionLogic.addUsuario(calificacionesId, usuariosId));
        LOGGER.log(Level.INFO, "CalificacionResource addUsuario: output: {0}", detailDTO);
        return detailDTO;
    }
    
    @GET
    @Path("{calificacionesId: \\d+}/usuarios/")
    public UsuarioDetailDTO getUsuario(@PathParam("calificacionesId") Long calificacionesId)
    {
        LOGGER.log(Level.INFO, "CalificacionesResource getUsuario: input: calificacionesId {0}", new Object[]{calificacionesId});
        UsuarioDetailDTO detailDTO = new UsuarioDetailDTO(calificacionLogic.getUsuario(calificacionesId));
        if(calificacionLogic.getUsuario(calificacionesId) == null)
        {
            throw new WebApplicationException("El recurso /usuarios/ de la calificacion " + calificacionesId + " debería existir.", 404);
        }
        LOGGER.log(Level.INFO, "CalificacionResource getUsuario: output: {0}", detailDTO);
        return detailDTO;
    }
    
    private List<CalificacionDTO> listEntity2DTO(List<CalificacionEntity> entityList)
    {
        List<CalificacionDTO> list = new ArrayList<>();
        for(CalificacionEntity entity : entityList)
        {
            list.add(new CalificacionDTO(entity));
        }
        return list;
    }
}
