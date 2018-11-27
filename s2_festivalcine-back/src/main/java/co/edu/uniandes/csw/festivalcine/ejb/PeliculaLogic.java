/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.ejb;

//IMPORTS
import co.edu.uniandes.csw.festivalcine.entities.PeliculaEntity;
import co.edu.uniandes.csw.festivalcine.persistence.PeliculaPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
/**
 *
 * @author Cristian
 */


@Stateless
public class PeliculaLogic {
    private static final Logger LOGGER = Logger.getLogger(PeliculaLogic.class.getName());
    
    
   @Inject
   private PeliculaPersistence persistence;
   
   public PeliculaEntity createPelicula(PeliculaEntity pelicula){
     LOGGER.info("Inicia la creación de una Pelicula");  
     
     persistence.create(pelicula);
     LOGGER.info("Finaliza la creación de una Pelicula");
     return pelicula;
   }
   public PeliculaEntity findById(long id){
       PeliculaEntity peliculaBuscada= persistence.findById(id);
       
       //SE VERIFICA SI EXISTE UNA PELICULA SI ES NULL MANDA ERROR
       if(peliculaBuscada== null){
           throw new WebApplicationException("No existe la pelicula con ID"+ id, 404);
       }
       return peliculaBuscada;
       
   }
   
    /**
     *
     * @return
     */
    public List<PeliculaEntity> getPeliculas(){
        LOGGER.info("Inicia el proceso de consultar todas las peliculas");
        List<PeliculaEntity> peliculas  = persistence.findAll();
        LOGGER.info("Termina el proceso de consultar todas las peliculas");
        return peliculas;
    }
    
    public List<PeliculaEntity> findByName( String nombre){
        List<PeliculaEntity> peliculasBuscadas= persistence.findByName(nombre);
        
        if (peliculasBuscadas.isEmpty()){
            throw new WebApplicationException("No existe la pelicula con el nombre"+ nombre, 404);
        }
        
        return peliculasBuscadas;
    }
    
    
    public List<PeliculaEntity> findByDirector( String nombre){
        List<PeliculaEntity> peliculasBuscadas= persistence.findByDirector(nombre);
        
        if (peliculasBuscadas.isEmpty()){
            throw new WebApplicationException("No existe la pelicula con el director"+ nombre, 404);
        }
        
        return peliculasBuscadas;
    }

   
   public List<PeliculaEntity> findByPuntaje( double puntaje){
        List<PeliculaEntity> peliculasBuscadas= persistence.findByPuntaje(puntaje);
        
        if (peliculasBuscadas.isEmpty()){
            throw new WebApplicationException("No existe la pelicula con el puntaje"+ puntaje, 404);
        }
        
        return peliculasBuscadas;
    }

   
   
   public PeliculaEntity update(Long peliculasId, PeliculaEntity peliculaEntity){
       LOGGER.log(Level.INFO, "Inicia proceso de actualizar la pelicula con id = {0}", peliculasId);
        // Note que, por medio de la inyección de dependencias se llama al método "update(entity)" que se encuentra en la persistencia.
        PeliculaEntity newEntity = persistence.update(peliculaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la pelicula con id = {0}", peliculaEntity.getId());
        return newEntity;
   }
   
    public void delete(Long id){
         LOGGER.log(Level.INFO, "Inicia proceso de borrar una pelicula con id={0}", id);
         persistence.delete(id);
    }  
}
