/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.resources;

import co.edu.uniandes.csw.festivalcine.dtos.ReservaDTO;
import co.edu.uniandes.csw.festivalcine.dtos.UsuarioDTO;
import co.edu.uniandes.csw.festivalcine.dtos.UsuarioDetailDTO;
import co.edu.uniandes.csw.festivalcine.ejb.UsuarioLogic;
import co.edu.uniandes.csw.festivalcine.entities.UsuarioEntity;
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
import co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException;

/**
 *
 * @author estudiante
 */
@Path("usuarios")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class UsuarioResource {

    private static final Logger LOGGER = Logger.getLogger(UsuarioResource.class.getName());

    @Inject
    UsuarioLogic usuarioLogic;

    /**
     * Crea un nuevo usuario con la informacion que se recibe en el cuerpo de la
     * petición y se regresa un objeto identico con un id auto-generado por la
     * base de datos.
     *
     * @param usuario {@link UsuarioDTO} - El usuario que se desea guardar.
     * @return JSON {@link UsuarioDTO} - El usuario guardado con el atributo id
     * autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe la editorial.
     */
    @POST
    public UsuarioDTO createUsuario(UsuarioDTO usuario) throws BusinessLogicException {
         LOGGER.log(Level.INFO, "UsuarioResource createUsuario: input: {0}", usuario.toString());

        UsuarioEntity usuarioEntity = usuario.toEntity();
 
        UsuarioEntity nuevoUsuarioEntity = usuarioLogic.createUsuario(usuarioEntity);
        
        UsuarioDTO nuevoUsuarioDTO = new UsuarioDTO(nuevoUsuarioEntity);
        LOGGER.log(Level.INFO, "UsuarioResource createUsuario: output: {0}", nuevoUsuarioDTO.toString());
        return nuevoUsuarioDTO;
    }

    /**
     * Actualiza el usuario con el id recibido en la URL con la informacion que
     * se recibe en el cuerpo de la petición.
     *
     * @param usuariossId Identificador del usuario que se desea actualizar.
     * Este debe ser una cadena de dígitos.
     * @param usuario {@link UsuarioDTO}el usuario que se desea guardar.
     * @return JSON {@link UsuarioDTO} - El usuario guardado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la editorial a
     * actualizar.
     */
    @PUT
    @Path("{usuariosId: \\d+}")
    public UsuarioDTO updateUsuario(@PathParam("usuariosId") Long usuariosId, UsuarioDTO usuario) throws WebApplicationException {
        LOGGER.log(Level.INFO, "UsuarioResource updateUsuario: input: id:{0} , usuario: {1}", new Object[]{usuariosId, usuario.toString()});
        usuario.setId(usuariosId);
        if (usuarioLogic.getUsuario(usuariosId) == null) 
        {
            throw new WebApplicationException("El recurso /usuarios/" + usuariosId + " no existe.", 404);
        }
        UsuarioDetailDTO detailDTO = new UsuarioDetailDTO(usuarioLogic.updateUsuario(usuariosId, usuario.toEntity()));
        LOGGER.log(Level.INFO, "UsuarioResource updateUsuario: output: {0}", detailDTO.toString());
        return detailDTO;
    }

    /**
     * Busca y devuelve todos los usuarios que existen en la aplicacion.
     *
     * @return JSONArray {@link UsuarioDTO} - Los usuarios encontradas en la
     * aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<UsuarioDetailDTO> getUsuarios() {

        LOGGER.info("UsuarioResource getUsuarios: input: void");
        List<UsuarioDetailDTO> listaUsuarios = listEntity2DetailDTO(usuarioLogic.getUsuarios());
        LOGGER.log(Level.INFO, "EditorialResource getEditorials: output: {0}", listaUsuarios.toString());
        return listaUsuarios;
    }

    /**
     * Busca el usuario con el id asociado recibido en la URL y la devuelve.
     *
     * @param usuariosId Identificador del usuario que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @return JSON {@link UsuarioDTO} - El usuario buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el usuario.
     */
    @GET
    @Path("{usuariosId: \\d+}")
    public UsuarioDTO getUsuario(@PathParam("usuariosId") Long usuariosId) throws WebApplicationException {

        LOGGER.log(Level.INFO, "UsuarioResource getUsuario: input: {0}", usuariosId);
        UsuarioEntity usuarioEntity = usuarioLogic.getUsuario(usuariosId);
        if (usuarioEntity == null) 
        {
            throw new WebApplicationException("El recurso /usuarios/" + usuariosId + " no existe.", 404);
        }
        UsuarioDetailDTO detailDTO = new UsuarioDetailDTO(usuarioEntity);
        LOGGER.log(Level.INFO, "UsuarioResource getUsuario: output: {0}", detailDTO.toString());
        return detailDTO;
    }

    /**
     * Borra la el usuario con el id asociado recibido en la URL.
     *
     * @param usuariosId Identificador del usuario que se desea borrar. Este
     * debe ser una cadena de dígitos.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar el usuario.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el usuario.
     */
    @DELETE
    @Path("{usuariosId: \\d+}")
    public void deleteUsuario(@PathParam("usuariosId") Long usuariosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "UsuarioResource deleteUsuario: input: {0}", usuariosId);
        if (usuarioLogic.getUsuario(usuariosId) == null) {
            throw new WebApplicationException("El recurso /usuarios/" + usuariosId + " no existe.", 404);
        }
        usuarioLogic.deleteUsuario(usuariosId);
        LOGGER.info("UsuarioResource deleteUsuario: output: void");
    }
    
    /**
     * Método que retorna las reservas de un usuario
     * @param usuariosId
     * @return lista de las reservas correspondientes al usuario ingresado por parametro
     */
    @Path("{usuariosId: \\d+}/reservas")
    public List<ReservaDTO> getUsuarioReservaResource(@PathParam("usuariosId") Long usuariosId) {
        if (usuarioLogic.getUsuario(usuariosId) == null) 
        {
            throw new WebApplicationException("El recurso /usuarios/" + usuariosId + " no existe.", 404);
        }
        UsuarioDetailDTO detailDTO = new UsuarioDetailDTO(usuarioLogic.getUsuario(usuariosId));
        LOGGER.log(Level.INFO, "UsuarioResource getUsuario: output: {0}", detailDTO.toString());
        return detailDTO.getReservas();
    }
     /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos UsuarioEntity a una lista de
     * objetos UsuarioDetailDTO (json)
     *
     * @param entityList corresponde a la lista de usuarios de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de usuarios en forma DTO (json)
     */
    private List<UsuarioDetailDTO> listEntity2DetailDTO(List<UsuarioEntity> entityList) {
        List<UsuarioDetailDTO> list = new ArrayList<>();
        for (UsuarioEntity entity : entityList) 
        {
            list.add(new UsuarioDetailDTO(entity));
        }
        return list;
    }
}
