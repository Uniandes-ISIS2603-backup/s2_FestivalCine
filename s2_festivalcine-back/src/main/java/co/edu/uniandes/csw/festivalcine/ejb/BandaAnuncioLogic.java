/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.ejb;
import co.edu.uniandes.csw.festivalcine.entities.BandaAnuncioEntity;
import co.edu.uniandes.csw.festivalcine.persistence.BandaAnuncioPersistence;
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
public class BandaAnuncioLogic {
    private static final Logger LOGGER = Logger.getLogger(BandaAnuncioLogic.class.getName());
    
    @Inject
    private BandaAnuncioPersistence persistence;
    
    
    public BandaAnuncioEntity createBandaAnuncio(BandaAnuncioEntity entity){
       LOGGER.info("INICIANDO CREACION BANDA ANUNCIO");
       persistence.create(entity);
       LOGGER.info("FINALIZA PROCESO CREACION BANDA ANUNCIO");
       return entity;
    }
    
   public BandaAnuncioEntity update(BandaAnuncioEntity banda){
         BandaAnuncioEntity  bandaAntigua = persistence.findById(banda.getId());
        
   
        //Valida que la banda modificar si exista en el sistema
        if(bandaAntigua == null){
            throw new WebApplicationException("No se encontró ninguna Banda con el id: " + banda.getId() + "", 404);
        }
        
        return persistence.update(bandaAntigua);
    }
    
    public BandaAnuncioEntity findById(Long id){
        BandaAnuncioEntity bandaBuscada = persistence.findById(id);
        
        //Valida si existe la banda con el id especificado
        if(bandaBuscada == null){
            throw new WebApplicationException("La banda con el id:" + id + "No existe.", 404);
        }
       
        return bandaBuscada;
    }
    
     public List<BandaAnuncioEntity> getBandas() 
    {
        LOGGER.info("Inicia proceso de consultar todas las bandas");
        List<BandaAnuncioEntity> bandas = persistence.findAll();
        LOGGER.info("Termina proceso de consultar todos las bandas");
        return bandas;
    }
    
    public void delete(Long id){
         LOGGER.log(Level.INFO, "Inicia proceso de borrar Usuario con id={0}", id);
         persistence.delete(id);
    }
}
