/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.ejb;

import co.edu.uniandes.csw.festivalcine.entities.SillaEntity;
import co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.festivalcine.persistence.SalaPersistence;
import co.edu.uniandes.csw.festivalcine.persistence.SillaPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la entidad de
 * Silla
 *
 * @author María Juliana Moya
 */
@Stateless
public class SillaLogic {
    private static final Logger LOGGER = Logger.getLogger(SillaLogic.class.getName());

    @Inject
    private SillaPersistence persistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

     @Inject
    private SalaPersistence sillaPersistence;
     
    /**
     * Crea una silla en la persistencia.
     *
     * @param sillaEntity La entidad que representa la silla a
     * persistir.
     * @return La entidad de la silla luego de persistirla.
     * @throws co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException
     */
    public SillaEntity createSilla(SillaEntity sillaEntity) throws BusinessLogicException{
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la silla"); 
        
        //Regla de negocio: la sala a la que se asigna la silla debe estar creada y persistida
        //Regla de negocio: la silla no se peude creer sin asociarla a una sala. 
        if (sillaEntity.getSala() == null || sillaPersistence.find(sillaEntity.getSala().getId()) == null) {
            throw new BusinessLogicException("La silla debe tener una sala asignada para crearse");
        }
        // Invoca la persistencia para crear la sill
        persistence.create(sillaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación de la silla");
        return sillaEntity;
    }

    /**
     * Obtener todas las sillas existentes en la base de datos
     * @return una lista de sillas
     */
    public List<SillaEntity> getSillas() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las sillas");
        List<SillaEntity> sillas = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las sillas");
        return sillas;
    }
    
    /**
     * Obtener una silla por medio de su id.
     * @param sillasId: id de la silla para ser buscada.
     * @return la silla solicitada por medio de su id.
     */
    public SillaEntity getSilla(Long sillasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la silla con id = {0}", sillasId);
        SillaEntity sillaEntity = persistence.find(sillasId);
        if (sillaEntity == null) {
            LOGGER.log(Level.SEVERE, "La silla con el id = {0} no existe", sillasId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar la silla con id = {0}", sillasId);
        return sillaEntity;
    }

   
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    /**
     * Borrar una silla
     * @param sillasId: id de la silla a borrar
     */
    public void deleteSilla(Long sillasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la silla con id = {0}", sillasId);
        persistence.delete(sillasId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la silla con id = {0}", sillasId);
    }
}
