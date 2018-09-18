/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.ejb;

import co.edu.uniandes.csw.festivalcine.entities.ReservaEntity;
import co.edu.uniandes.csw.festivalcine.entities.SillaEntity;
import co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.festivalcine.persistence.ReservaPersistence;
import co.edu.uniandes.csw.festivalcine.persistence.SillaPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author estudiante
 */
@Stateless
public class ReservaSillasLogic 
{
    private static final Logger LOGGER = Logger.getLogger(ReservaLogic.class.getName());

    @Inject
    private ReservaPersistence persistence;
    
    @Inject
    private SillaPersistence sillaPersistence;
    
     /**
     *
     * Obtener todas las reservas existentes en la base de datos.
     *
     * @return una lista de reservas.
     */
    public List<SillaEntity> getSillas() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las sillas");
       
        List<SillaEntity> sillas = sillaPersistence.findAll();
        
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las sillas");
        return sillas;
    }

    /**
     * LÓGICA DE LA RELACIÓN CON SILLA
     */
    /**
     * Asocia una Silla existente a una Reserva
     *
     * @param reservasId Identificador de la instancia de Reserva
     * @param sillaId Identificador de la instancia de Silla
     * @return Instancia de SillaEntity que fue asociada a Reserva
     */
    public SillaEntity addSilla(Long reservaId, Long sillaId) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de asociarle una silla a la reserva con id = {0}", reservaId);
        
        ReservaEntity reservaEntity = persistence.findReserva(reservaId);
        
        SillaEntity sillaEntity = sillaPersistence.find(sillaId);
        
        sillaEntity.setReserva(reservaEntity);
        
        LOGGER.log(Level.INFO, "Termina proceso de asociarle una silla a la reserva con id = {0}", reservaId);
        
        return sillaEntity;
    }
    
     /**
     * Retorna todos las sillas asociadas a una reserva
     *
     * @param reservasId El ID de la reserva buscada
     * @return La lista de sillas de la reserva
     */
    public List<SillaEntity> getSillas(Long reservasId) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar las sillas asociados a la reserva con id = {0}", reservasId);
        return persistence.findReserva(reservasId).getSillas();
    }
    
     /**
     * Retorna una silla asociado a una reserva
     *
     * @param reservaId El id de la reserva a buscar.
     * @param sillasId El id de la sillaa buscar
     * @return La silla encontrado dentro de la reserva.
     * @throws BusinessLogicException Si la silla no se encuentra en la
     * reserva
     */
    public SillaEntity getSilla(Long reservasId, Long sillasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la silla con id = {0} de la reserva con id = " + reservasId, sillasId);
        List<SillaEntity> sillas = persistence.findReserva(reservasId).getSillas();
        SillaEntity sillaEntity = sillaPersistence.find(sillasId);
        
        int index = sillas.indexOf(sillaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar la silla con id = {0} de la reserva con id = " + reservasId, sillasId);
        if (index >= 0) 
        {
            return sillas.get(index);
        }
        throw new BusinessLogicException("La silla no está asociado a la reserva");
    }
    
    /**
     * Remplazar sillas de una reserva
     *
     * @param sillas Lista de sillas que serán las de la reserva.
     * @param reservasId El id de la reserva que se quiere actualizar.
     * @return La lista de sillas actualizada.
     */
    public List<SillaEntity> replaceSillas(Long reservasId, List<SillaEntity> sillas)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la reserva con id = {0}", reservasId);
        ReservaEntity reservaEntity = persistence.findReserva(reservasId);
        List<SillaEntity> sillaList = sillaPersistence.findAll();
        for (SillaEntity silla : sillaList) 
        {
            if (sillas.contains(silla)) 
            {
                silla.setReserva(reservaEntity);
            }
            else if (silla.getReserva() != null && silla.getReserva().equals(reservaEntity)) 
            {
                silla.setReserva(null);
            }
        }
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la reserva con id = {0}", reservasId);
        return sillas;
    } 
}
