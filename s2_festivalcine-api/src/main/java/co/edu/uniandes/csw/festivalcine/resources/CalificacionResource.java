/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.resources;


import co.edu.uniandes.csw.festivalcine.dtos.CalificacionDTO;
import co.edu.uniandes.csw.festivalcine.dtos.UsuarioDTO;
import co.edu.uniandes.csw.festivalcine.ejb.CalificacionLogic;
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

/**
 *
 * @author Andres Felipe Rodriguez Murillo
 */
@Path("calificaciones")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class CalificacionResource 
{
    @Inject
    private CalificacionLogic calificacionLogic;
    
    private static final Logger LOGGER = Logger.getLogger(CalificacionResource.class.getName());
    
    @POST
    public CalificacionDTO createCalificacion(CalificacionDTO calificacion)
    {
    //    LOGGER.info("CalificacionResource createCalificacion: input " + calificacion.toString());
      //  CalificacionEntity calificacionEntity = calificacion.toEntity();
      //  CalificacionEntity nuevaCalificacionEntity = calificacionLogic.createCalificacion(calificacionEntity);
        //CalificacionDTO nuevaCalificacionDTO = new CalificacionDTO(nuevaCalificacionEntity);
        //LOGGER.info("CalificacionResouce createCalificacion: output: " + nuevaCalificacionDTO.toString());
        //return nuevaCalificacionDTO;
        return null;
    }
    
    @GET
    @Path("{calificacionesId: \\d+}")
    public CalificacionDTO getCalificacion(@PathParam("calificacionesId") Long calificacionesId)
    {
        return null;
    }
    
    @PUT
    @Path("{calificacionesID: \\d+}")
    public CalificacionDTO updateCalificacion(@PathParam("calificacionesId") Long calificacionesId, CalificacionDTO calificacion)
    {
        return null;
    }
    
    @DELETE
    @Path("{calificacionesID: \\d+}")
    public void deleteCalificacion(@PathParam("calificacionesId") Long calificacionesId)
    {
        
    }
    
    //----------------------------------------------------------------------------------------------------------------------------------------------------
    //Funciones
    //----------------------------------------------------------------------------------------------------------------------------------------------------
    
    @GET
    @Path("{calificacionesId: \\d+}/usuarios/")
    public UsuarioDTO getUsuario(@PathParam("calificacionesId") Long calificacionesId)
    {
        return new UsuarioDTO();
    }
}
