/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.persistence;

import co.edu.uniandes.csw.festivalcine.entities.CriticoEntity;
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
public class CriticoPersistence 
{
    private static final Logger LOGGER = Logger.getLogger(CriticoPersistence.class.getName());
    
    @PersistenceContext(unitName = "FestivalCriticos") protected EntityManager em;
    
    public CriticoEntity create(CriticoEntity criticoEntity)
    {
        LOGGER.log(Level.INFO, "Creando un critico nuevo");
        em.persist(criticoEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear un critico nuevo");
        return criticoEntity;
    }
    
    public CriticoEntity findByIdentificacion(String identificacion)
    {
        LOGGER.log(Level.INFO, "Consultando critico por identificacion", identificacion);
        TypedQuery query = em.createQuery("Select e From CriticoEntity e where e.identificacion = :identificacion",CriticoEntity.class);
        query = query.setParameter("identificacion", identificacion);
        List<CriticoEntity> sameIdentificacion = query.getResultList();
        CriticoEntity result;
        if(sameIdentificacion == null)
        {
            result = null;
        }
        else if(sameIdentificacion.isEmpty())
        {
            result = null;
        }
        else
        {
            result = sameIdentificacion.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar critico por identificacion ", identificacion);
        return result;
    }
}
