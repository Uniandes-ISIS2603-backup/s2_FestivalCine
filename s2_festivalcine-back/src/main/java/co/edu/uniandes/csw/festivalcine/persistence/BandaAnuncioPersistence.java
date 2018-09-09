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
     @PersistenceContext(unitName= "TarantinoPU")
     
     protected  EntityManager em;
     
     
     public  BandaAnuncioEntity create ( BandaAnuncioEntity entity){
        LOGGER.info("Se esta creando un recurso Banda Anuncio nuevo");
         em.persist(entity);
        return entity;
     }
     public BandaAnuncioEntity update (BandaAnuncioEntity entity){
         LOGGER.info("Actualizando el recurso"+ System.currentTimeMillis());
         return em.merge(entity);
     }
     
     public BandaAnuncioEntity findById(Long id){
         LOGGER.info("Buscando un recurso Banda Anuncio con id="+id);
         return em.find(BandaAnuncioEntity.class, id);
              }
     
     public List<BandaAnuncioEntity> findAll(){
         LOGGER.info("Buscando todos los recursos Banda Anuncio");
        TypedQuery query = em.createQuery("select u from BandaAnuncioEntity u", BandaAnuncioEntity.class);
        return query.getResultList();
     }
     public void delete(Long id){
    BandaAnuncioEntity entity= em.find(BandaAnuncioEntity.class, id);
    em.remove(entity);
    }
}
