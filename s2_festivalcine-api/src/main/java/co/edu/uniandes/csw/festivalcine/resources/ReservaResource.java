/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.resources;

import com.sun.istack.internal.logging.Logger;
import co.uniandes.edu.csw.festivalcine.dtos.ReservaDTO;

/**
 *
 * @author estudiante
 */

@Path("reservas")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ReservaResource 
{
    @Inject
    ReservaLogic reservaLogic;
    
    private static final Logger LOGGER = Logger.getLogger(ReservaResource.class.getName());
    
    @POST
    public ReservaDTO createReserva(ReservaDTO reserva) throws BusinessLogicException
    {
        LOGGER.info("ReservaResource createEditorial: indput:" + reserva.toString());
        ReservaEntity reservaEntity = reserva.toEntity();
        ReservaEntity nuevaReservaEntity = reservaLogic.createReserva(reservaEntity);
        
        ReservaDTO nuevaReservaDTO = new ReservaDTO(nuevaReservaEntity);
        LOGGER.info("ReservaResource createEditorial: output:" + nuevaReservaDTO.toString() );
        return nuevaReservaDTO;
    }
    
    @PUT
    public ReservaDTO changeReserva(ReservaDTO reserva)
    {
        LOGGER.info("ReservaResource changeReserva: input:" + reserva.toString());
        ReservaEntity reservaEntity = reserva.toEntity();
        ReservaEntity nuevaReservaEntity = reservaLogic.changeReserva(reservaEntity);
        
        ReservaDTO nuevaReservaDTO = new ReservaDTO(nuevaReservaEntity);
        LOGGER.info("ReservaResource changeREserva: output:" + nuevaReservaDTO.toString());
        return nuevaReservaDTO;
    }
    
    @DELETE
    public ReservaDTO eraseReserva(ReservaDTO reserva)
    {
        LOGGER.info("ReservaResource deleteReserva: input:" + reserva.toString());
        ReservaEntity reservaEntity = reserva.toEntity();
        ReservaEntity nuevaReservaEntity = reservaLogic.deleteReserva(reservaEntity);
        
        ReservaDTO nuevaReservaDTO = new ReservaDTO(nuevaReservaEntity);
        LOGGER.info("ReservaResource deleteReserva: output:" + nuevaReservaDTO.toString());
        return nuevaReservaDTO;
    }
    
}
