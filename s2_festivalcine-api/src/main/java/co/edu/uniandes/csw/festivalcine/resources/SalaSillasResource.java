/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.resources;

import co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException;
import co.uniandes.csw.festivalcine.dtos.SillaDTO;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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

   // private static final Logger LOGGER = Logger.getLogger(SalaSillasResource.class.getName());

    //@Inject
    //private EditorialBooksLogic editorialBooksLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    //@Inject
    //private BookLogic bookLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Asigna una silla a la sala con la informacion que recibe el
     * la URL. Se devuelve la silla que se asignó a la sala. 
     *
     * @param salasId Identificador de la sala que se esta
     * actualizando. Este debe ser una cadena de dígitos.
     * @param sillasId Identificador dde la silla que se desea asignar. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link SillaDTO} - La silla asignada a la sala
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro.
     */
    @POST
    @Path("{sillasId: \\d+}")
    public SillaDTO asignarSilla(@PathParam("salasId") Long salasId, @PathParam("sillasId") Long sillasId) {
        //LOGGER.log(Level.INFO, "EditorialBooksResource addBook: input: editorialsID: {0} , booksId: {1}", new Object[]{editorialsId, booksId});
        //if (bookLogic.getBook(booksId) == null) {
        //    throw new WebApplicationException("El recurso /books/" + booksId + " no existe.", 404);
        //}
        //SillaDTO sillaDTO = new SillaDTO(editorialBooksLogic.addBook(booksId, editorialsId));
        //LOGGER.log(Level.INFO, "EditorialBooksResource addBook: output: {0}", bookDTO.toString());
        return null;
    }

    /**
     * Busca y devuelve todos las sillas que existen en la sala.
     *
     * @param salasId Identificador de la sala que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSONArray {@link SillaDTO} - Los sillas encontradas en la sala.
     * Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<SillaDTO> getSillas(@PathParam("salasId") Long salasId) {
        //LOGGER.log(Level.INFO, "EditorialBooksResource getBooks: input: {0}", editorialsId);
        //List<BookDetailDTO> listaDetailDTOs = booksListEntity2DTO(editorialBooksLogic.getBooks(editorialsId));
        //LOGGER.log(Level.INFO, "EditorialBooksResource getBooks: output: {0}", listaDetailDTOs.toString());
        return null;
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
    public SillaDTO getSilla(@PathParam("salasId") Long salasId, @PathParam("sillasId") Long booksId) throws BusinessLogicException {
        //LOGGER.log(Level.INFO, "EditorialBooksResource getBook: input: editorialsID: {0} , booksId: {1}", new Object[]{editorialsId, booksId});
        //if (bookLogic.getBook(booksId) == null) {
        //    throw new WebApplicationException("El recurso /editorials/" + editorialsId + "/books/" + booksId + " no existe.", 404);
        //}
        //BookDetailDTO bookDetailDTO = new BookDetailDTO(editorialBooksLogic.getBook(editorialsId, booksId));
        //LOGGER.log(Level.INFO, "EditorialBooksResource getBook: output: {0}", bookDetailDTO.toString());
        return null;
    }

    /**
     * Remplaza las instancias de Silla asociadas a una instancia de Sala
     *
     * @param salasId Identificador de la sala que se esta
     * remplazando. Este debe ser una cadena de dígitos.
     * @param sillas JSONArray {@link SillaDTO} El arreglo de sillas nuevo para la
     * sala.
     * @return JSON {@link sillaDTO} - El arreglo de Sillas asignado a la sala. 
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la silla.
     */
    @PUT
    public List<SillaDTO> replaceSillas(@PathParam("salasId") Long salasId, List<SillaDTO> sillass) {
        //LOGGER.log(Level.INFO, "EditorialBooksResource replaceBooks: input: editorialsId: {0} , books: {1}", new Object[]{editorialsId, books.toString()});
        //for (BookDetailDTO book : books) {
        //    if (bookLogic.getBook(book.getId()) == null) {
        //        throw new WebApplicationException("El recurso /books/" + book.getId() + " no existe.", 404);
        //    }
        //}
        //List<BookDetailDTO> listaDetailDTOs = booksListEntity2DTO(editorialBooksLogic.replaceBooks(editorialsId, booksListDTO2Entity(books)));
        //LOGGER.log(Level.INFO, "EditorialBooksResource replaceBooks: output: {0}", listaDetailDTOs.toString());
        return null;
    }
    
}
