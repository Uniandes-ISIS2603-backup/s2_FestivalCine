/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.resources;

import co.edu.uniandes.csw.festivalcine.dtos.CalificacionDTO;
import co.edu.uniandes.csw.festivalcine.dtos.CriticoDTO;
import co.edu.uniandes.csw.festivalcine.dtos.CriticoDetailDTO;
import co.edu.uniandes.csw.festivalcine.dtos.FestivalDTO;
import co.edu.uniandes.csw.festivalcine.dtos.FuncionDTO;
import co.edu.uniandes.csw.festivalcine.ejb.CriticoLogic;
import co.edu.uniandes.csw.festivalcine.entities.CriticoEntity;
import java.util.ArrayList;
import java.util.List;
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

@Path("criticos")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped

/**
 *
 * @author Andres Felipe Rodriguez Murillo
 */
public class CriticoResource {
    
    @Inject
    private CriticoLogic criticoLogic;
    
    private static final Logger LOGGER = Logger.getLogger(CriticoResource.class.getName());

    @POST
    public CriticoDTO createCritico(CriticoDTO critico)
    {
        //LOGGER.info("CriticoResource createCritico: input " + critico.toString());
        //CriticoEntity criticoEntity = critico.toEntity();
        //CriticoEntity nuevoCriticoEntity = criticoLogic.createCritico(criticoEntity);
        //CriticoDTO nuevoCriticoDTO = new CriticoDTO(nuevoCriticoEntity);
        //LOGGER.info("CriticoResource createCritico: output: " + nuevoCriticoDTO.toString());
     //return nuevoCriticoDTO;
        return critico;
        
    }
    
    @GET
    @Path("{criticosId: \\d+}")
    public CriticoDTO getCritico(@PathParam("criticosId") Long criticosId) 
    {
        return new CriticoDTO();
    }
    
    @PUT
    @Path("{criticosId: \\d+}")
    public CriticoDTO updateCritico(@PathParam("criticosId") Long criticosId, CriticoDTO critico)
    {
        return critico;
    }
    
    @DELETE
    @Path("{criticosId: \\d+}")
    public void deleteCritico(@PathParam("criticosId") Long criticosId)
    {
        
    }
        
    //----------------------------------------------------------------------------------------------------------------------------------------------------
    //Funciones
    //----------------------------------------------------------------------------------------------------------------------------------------------------
    
    @POST
    @Path("{criticosId: \\d+}/funciones/{funcionesId: \\d+}")
    public FuncionDTO addFuncion(@PathParam("criticosId") Long criticosId, @PathParam("funcionesId") Long funcionesId)
    {
        return new FuncionDTO();
    }
    
    @GET
    @Path("{criticosId: \\d+}/funciones/")
    public List<FuncionDTO> getfunciones(@PathParam("criticoId") Long criticosId)
    {
        return new ArrayList();
    }
 
    @GET
    @Path("{criticosId: \\d+}/funciones/{funcionesId: \\d+}")
    public FuncionDTO getfuncion(@PathParam("criticoId") Long criticosId, @PathParam("funcionesId") Long funcionesId)
    {
        return new FuncionDTO();
    }
    
    @DELETE
    @Path("{criticosId: \\d+}/funciones/{funcionesId: \\d+}")
    public void deleteFuncion(@PathParam("criticosId:") Long criticosId, @PathParam("funcionesId") Long funcionesId)
    {
        
    }
    
    //----------------------------------------------------------------------------------------------------------------------------------------------------
    //Funciones
    //----------------------------------------------------------------------------------------------------------------------------------------------------
    
    @POST
    @Path("{criticosId: \\d+}/pelliculas/{peliculasId: \\d+}")
    public FestivalDTO addPelicula(@PathParam("criticosId") Long criticoId, @PathParam("peliculasId") Long peliculasId)
    {
        return new FestivalDTO();
    }
    
    @GET
    @Path("{criticosId: \\d+}/peliculas/")
    public List<FuncionDTO> getPeliculas(@PathParam("criticosId") Long criticosId)
    {
        return new ArrayList();
    }
    
    @GET
    @Path("{criticosId: \\d+}/pelliculas/{funcionesId: \\d+}")
    public FestivalDTO getPelicula(@PathParam("criticosId") Long criticosId, @PathParam("peliculasId") Long peliculasId)
    {
        return new FestivalDTO();
    }
    
    @DELETE
    @Path("{criticosId: \\d+}/peliculas/{peliculasId: \\d++}")
    public void deletePelicula(@PathParam("criticosId") Long criticosId)
    {
        
    }
    
    //----------------------------------------------------------------------------------------------------------------------------------------------------
    //Funciones
    //----------------------------------------------------------------------------------------------------------------------------------------------------
    
    @POST
    @Path("{criticosId: \\d+}/calificaciones/{calificacionesId: \\d+}")
    public CalificacionDTO addCalificacion(@PathParam("criticosId") Long criticoId, @PathParam("calificacionesId") Long calificacionesId)
    {
        return new CalificacionDTO();
    }
    
    @GET
    @Path("{criticosId: \\d+}/calificaciones/")
    public List<CalificacionDTO> getCalificaciones(@PathParam("criticosId") Long criticosId)
    {
        return new ArrayList();
    }
    
    @GET
    @Path("{criticosId: \\d+}/calificaciones/{calificacionesId: \\d+}")
    public CalificacionDTO getCalificacion(@PathParam("criticosId") Long criticosId, @PathParam("calificacionesId") Long calificacionesId)
    {
        return new CalificacionDTO();
    }
    
    @DELETE
    @Path("{criticosId: \\d+}/calificaciones/{calificacionesId: \\d+}")
    public void deleteCalificacion(@PathParam("criticosId") Long criticosId, @PathParam("calificacionesId")Long calificacionesId)
    {
        
    }
}
