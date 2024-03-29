/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.persistence;


import co.edu.uniandes.csw.festivalcine.entities.ReservaEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author PAULA VELANDIA
 */
@Stateless
public class ReservaPersistence 
{
    private static final Logger LOGGER = Logger.getLogger(ReservaPersistence.class.getName());
    
    @PersistenceContext(unitName = "TarantinoPU")
    protected EntityManager em;
    
    /**
     * Método que crea una reserva
     * @param reservaEntity
     * @return 
     */
     public ReservaEntity create(ReservaEntity reservaEntity)
    {
        LOGGER.log(Level.INFO, "Creando una reserva nueva");
        em.persist(reservaEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear una reserva nuevo");
        return reservaEntity;
    }
     
     /**
      * Método que encuentra todas las reservas
      * @return 
      */
     public List<ReservaEntity> findAllReservas() 
    {
        LOGGER.log(Level.INFO, "Consultando todas las reservas");
        TypedQuery query = em.createQuery("select u from ReservaEntity u", ReservaEntity.class);
        return query.getResultList();
    }
     
     /**
      * Metodo que encuentra una reserva
      * @param reservasId
      * @return 
      */
     public ReservaEntity findReserva(Long reservasId) 
     {
         if(reservasId == null)
         {
             return null;
         }
        LOGGER.log(Level.INFO, "Consultando reserva con id = {0}", reservasId);
        return em.find(ReservaEntity.class, reservasId);
        
    }
     
     /**
      * Metodo que actualiza una reserva
      * @param reservaEntity
      * @return 
      */
    public ReservaEntity updateReserva(ReservaEntity reservaEntity) 
    {
        LOGGER.log(Level.INFO, "Actualizando reserva con id = {0}", reservaEntity.getId());
        LOGGER.log(Level.INFO, "Saliendo de actualizar la reserva con id = {0}", reservaEntity.getId());
        return em.merge(reservaEntity);
    }
    
    /**
     * Metodo que elimina una reserva
     * @param reservasId 
     */
    public void deleteReserva(Long reservasId) 
    {
        LOGGER.log(Level.INFO, "Borrando la reserva con id = {0}", reservasId);
        ReservaEntity entity = em.find(ReservaEntity.class, reservasId);
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar la reserva con id = {0}", reservasId);
    }  
    
}
