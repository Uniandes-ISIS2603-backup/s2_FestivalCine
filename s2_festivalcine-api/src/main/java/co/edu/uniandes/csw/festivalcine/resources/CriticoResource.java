/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.resources;

import co.edu.uniandes.csw.festivalcine.dtos.CriticoDTO;
import co.edu.uniandes.csw.festivalcine.dtos.CriticoDetailDTO;
import co.edu.uniandes.csw.festivalcine.ejb.CriticoLogic;
import co.edu.uniandes.csw.festivalcine.entities.CriticoEntity;
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

//Clase critico

/**
 *
 * @author estudiante
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
        return null;
        
    }
    
    @GET
    @Path("{criticosID: \\d+}")
    public CriticoDTO getCritico(@PathParam("criticosID") Long criticosID) 
    {
        return null;
    }
    
    @PUT
    @Path("{criticosID: \\d+}")
    public CriticoDTO updateCritico(@PathParam("criticosID") Long criticosID, CriticoDTO critico)
    {
        return null;
    }
    
    @DELETE
    @Path("{criticosID: \\d+}")
    public void deleteCritico(@PathParam("criticosID") Long criticosID)
    {
        
    }
}
