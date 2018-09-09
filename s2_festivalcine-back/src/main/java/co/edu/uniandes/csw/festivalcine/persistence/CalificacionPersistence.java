/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.persistence;

import co.edu.uniandes.csw.festivalcine.entities.CalificacionEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author estudiante
 */
@Stateless
public class CalificacionPersistence 
{
    private static final Logger LOGGER = Logger.getLogger(CalificacionPersistence.class.getName());
    
    @PersistenceContext(unitName = "TarantinoPU") protected EntityManager em;
    
    public CalificacionEntity create(CalificacionEntity calificacionEntity)
    {
        LOGGER.log(Level.INFO, "Crando una calificación nueva");
        em.persist(calificacionEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear una calificación nueva");
        return calificacionEntity;
    }
    
    public List<CalificacionEntity> findAll()
    {
        LOGGER.log(Level.INFO, "Consultando todos los criticos");
        TypedQuery query = em.createQuery("select u from CalificacionEntity u", CalificacionEntity.class);
        return query.getResultList();
    }
    
    public CalificacionEntity find(Long calificacionesId)
    {
        LOGGER.log(Level.INFO,"Consultando la calificacion con id={0}", calificacionesId);
        return em.find(CalificacionEntity.class, calificacionesId);
    }
    
    public CalificacionEntity update(CalificacionEntity calificacionEntity)
    {
        LOGGER.log(Level.INFO, "Actualizando la calificacion con id={0}", calificacionEntity.getId());
        return em.merge(calificacionEntity);
    }
    
    public void delete( Long calificacionesId)
    {
        LOGGER.log(Level.INFO, "Borrando la calificacion con id={0}", calificacionesId);
        CalificacionEntity calificacionEntity = em.find(CalificacionEntity.class, calificacionesId);
    }
}
