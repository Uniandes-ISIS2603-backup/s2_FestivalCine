/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.persistence;
import co.edu.uniandes.csw.festivalcine.entities.BandaAnuncioEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
/**
 *
 * @author Cristian
 */

@Stateless
public class BandaAnuncioPersistence {
     private static final Logger LOGGER = Logger.getLogger(BandaAnuncioPersistence.class.getName());
     @PersistenceContext(unitName= "FestivalCinePU")
     
     protected  EntityManager em;
     
     
     public  BandaAnuncioEntity create ( BandaAnuncioEntity entity){
        LOGGER.info("Se esta creando un recurso Banda Anuncio nuevo");
         em.persist(entity);
        return entity;
     }
     public BandaAnuncioEntity update ()
     
}
