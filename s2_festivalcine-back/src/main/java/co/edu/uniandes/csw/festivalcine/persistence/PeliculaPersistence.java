/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.persistence;

//IMPORTS
import co.edu.uniandes.csw.festivalcine.entities.PeliculaEntity;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.logging.Level;
/**
 *
 * @author Cristian
 */

@Stateless
public class PeliculaPersistence {

    
 private static final Logger LOGGER = Logger.getLogger(PeliculaPersistence.class.getName());
 @PersistenceContext(unitName = "FestivalCinePU")
    protected EntityManager em;
 
     public PeliculaEntity create(PeliculaEntity entity){
        LOGGER.info("Creando un Recurso Pelicula nuevo");
        em.persist(entity);
        LOGGER.info("Creando un Recurso Pelicula nuevo");
        return entity;
    }
    
    public PeliculaEntity update(PeliculaEntity entity){
         LOGGER.info("Actualizando un Recurso Pelicula");
        return em.merge(entity);
    }
    
    public void delete(Long id){
    PeliculaEntity entity= em.find(PeliculaEntity.class, id);
    em.remove(entity);
    }
    
    public PeliculaEntity findById(Long id){
      LOGGER.info("Encontrando un Recurso Pelicula"); 
      return em.find(PeliculaEntity.class,id);
      
    }
   
    
    public List<PeliculaEntity> findAll(){
        TypedQuery query = em.createQuery("select u from PeliculaEntity u", PeliculaEntity.class);
        return query.getResultList();
    }
   public List<PeliculaEntity> findByName(String name){
        TypedQuery query = em.createQuery("select u From PeliculaEntity e where e.nombre = :name", PeliculaEntity.class);
        query = query.setParameter("name",name);
        
        List<PeliculaEntity> sameName = query.getResultList();
        if(sameName.isEmpty()){
            return null;
        }else{return sameName;
        
        }
   }
   
   public List<PeliculaEntity> findByDirector(String name){
        TypedQuery query = em.createQuery("select e From PeliculaEntity e where e.director = :name", PeliculaEntity.class);
        query = query.setParameter("name",name);
        
        List<PeliculaEntity> sameName = query.getResultList();
        if(sameName.isEmpty()){
            return null;
        }else{return sameName;
        
        }
   }
   public List <PeliculaEntity> findByPais(String pais){
        TypedQuery query = em.createQuery("select e From PeliculaEntity e where e.pais = :pais", PeliculaEntity.class);
        query = query.setParameter("pais",pais);
        
        List<PeliculaEntity> samePais = query.getResultList();
        if(samePais.isEmpty()){
            return null;
        }else{return samePais;
        
        }
   }
   
    public List<PeliculaEntity> findByDuracion(int duracion){
        TypedQuery query = em.createQuery("select u From PeliculaEntity e where e.duracion = :duracion", PeliculaEntity.class);
        query = query.setParameter("duracion",duracion);
        
        List<PeliculaEntity> sameDuracion = query.getResultList();
        if(sameDuracion.isEmpty()){
            return null;
        }else{return sameDuracion;
        
        }
   }
    
     public List<PeliculaEntity> findByPuntaje(double puntaje){
        TypedQuery query = em.createQuery("select u From PeliculaEntity e where e.puntaje = :puntaje", PeliculaEntity.class);
        query = query.setParameter("puntaje",puntaje);
        
        List<PeliculaEntity> samePuntaje = query.getResultList();
        if(samePuntaje.isEmpty()){
            return null;
        }else{return samePuntaje;
        
        }
   }
}
