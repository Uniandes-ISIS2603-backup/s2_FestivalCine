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
 * @author paula velandia
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
     * LÓGICA DE LA RELACIÓN CON SILLA
     */
    /**
     * Asocia una Silla existente a una Reserva
     *
     * @param reservaId Identificador de la instancia de Reserva
     * @param sillaId Identificador de la instancia de Silla
     * @return Instancia de SillaEntity que fue asociada a Reserva
     */
    public SillaEntity addSilla(Long reservaId, Long sillaId) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de asociarle una silla a la reserva con id = {0}", reservaId);
        
        ReservaEntity reservaEntity = persistence.findReserva(reservaId);
        
        SillaEntity sillaEntity = sillaPersistence.find(sillaId);
        
        sillaEntity.setReserva(reservaEntity);
        sillaPersistence.update(sillaEntity);
                
        
        List sillasNuevas = reservaEntity.getSillas();
        sillasNuevas.add(sillaEntity);
        reservaEntity.setSillas(sillasNuevas);
        
        LOGGER.log(Level.INFO, "Termina proceso de asociarle una silla a la reserva con id = {0}", reservaId);
        
        return sillaEntity;
    }
    
     /**
     * Retorna todos las sillas asociadas a una reserva
     *
     * @param reservasId El ID de la reserva buscada
     * @return La lista de sillas de la reserva
     */
    public List<SillaEntity> getSillasReserva(Long reservasId) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar las sillas asociados a la reserva con id = {0}", reservasId);
        return persistence.findReserva(reservasId).getSillas();
    }
    
    
    
     /**
     * Retorna una silla asociado a una reserva
     *
     * @param reservasId El id de la reserva a buscar.
     * @param sillasId El id de la sillaa buscar
     * @return La silla encontrado dentro de la reserva.
     * @throws BusinessLogicException Si la silla no se encuentra en la
     * reserva
     */
    public SillaEntity getSilla(Long reservasId, Long sillasId) throws BusinessLogicException {
        List<SillaEntity> sillas = persistence.findReserva(reservasId).getSillas();
        SillaEntity sillaEntity = sillaPersistence.find(sillasId);
        for(int i = 0; i < sillas.size(); i++)
        {
            if(sillas.get(i).getId().equals(sillaEntity.getId()))
            {
                return sillas.get(i);
            }
        }   
        LOGGER.log(Level.INFO, "Termina proceso de consultar la silla");      
        throw new BusinessLogicException("La silla no esta asociada a la reserva");
    }
}
