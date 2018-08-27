/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.resources;

import co.uniandes.edu.csw.festivalcine.dtos.UsuarioDTO;
import com.sun.istack.internal.logging.Logger;

/**
 *
 * @author estudiante
 */
@Path("reservas")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class UsuarioResource 
{
    @Inject
    UsuarioLogic usuarioLogic;
    
    private static final Logger LOGGER = Logger.getLogger(UsuarioResource.class.getName());
    
     @POST
    public UsuarioDTO createUsuario(UsuarioDTO usuario) throws BusinessLogicException
    {
        LOGGER.info("UsuarioResource createUsuario: input:" + usuario.toString());
        UsuarioEntity usuarioEntity = usuario.toEntity();
        UsuarioEntity nuevoUsuarioEntity = usuarioLogic.createReserva(usuarioEntity);
        
        UsuarioDTO nuevoUsuarioDTO = new UsuarioDTO(nuevoUsuarioEntity);
        LOGGER.info("UsuarioResource createEditorial: output:" + nuevoUsuarioDTO.toString() );
        return nuevoUsuarioDTO;
    }
    
    @PUT
    public UsuarioDTO changeUsuario(UsuarioDTO usuario) throws BusinessLogicException
    {
        LOGGER.info("UsuarioResource changeUsuario: indput:" + usuario.toString());
        UsuarioEntity usuarioEntity = usuario.toEntity();
        UsuarioEntity nuevoUsuarioEntity = usuarioLogic.changeUsuario(usuarioEntity);
        
        UsuarioDTO nuevoUsuarioDTO = new UsuarioDTO(nuevoUsuarioEntity);
        LOGGER.info("UsuarioResource changeUsuario: output:" + nuevoUsuarioDTO.toString() );
        return nuevoUsuarioDTO;
    }
    
     @GET
    public UsuarioDTO getUsuario(UsuarioDTO usuario) throws BusinessLogicException
    {
        LOGGER.info("UsuarioResource getUsuario: input:" + usuario.toString());
        UsuarioEntity usuarioEntity = usuario.toEntity();
        UsuarioEntity nuevoUsuarioEntity = usuarioLogic.getUsuario(usuarioEntity);
        
        UsuarioDTO nuevoUsuarioDTO = new UsuarioDTO(nuevoUsuarioEntity);
        LOGGER.info("UsuarioResource getUsuario: output:" + nuevoUsuarioDTO.toString() );
        return nuevoUsuarioDTO;
    }
    
     @DELETE
    public UsuarioDTO deleteUsuario(UsuarioDTO usuario) throws BusinessLogicException
    {
        LOGGER.info("UsuarioResource deleteUsuario: input:" + usuario.toString());
        UsuarioEntity usuarioEntity = usuario.toEntity();
        UsuarioEntity nuevoUsuarioEntity = usuarioLogic.changeUsuario(usuarioEntity);
        
        UsuarioDTO nuevoUsuarioDTO = new UsuarioDTO(nuevoUsuarioEntity);
        LOGGER.info("UsuarioResource deleteUsuario: output:" + nuevoUsuarioDTO.toString() );
        return nuevoUsuarioDTO;
    }
}
