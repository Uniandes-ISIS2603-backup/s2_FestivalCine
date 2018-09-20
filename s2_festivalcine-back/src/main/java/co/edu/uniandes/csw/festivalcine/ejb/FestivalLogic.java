/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.ejb;

import co.edu.uniandes.csw.festivalcine.entities.FestivalEntity;
import co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.festivalcine.persistence.CriticoPersistence;
import co.edu.uniandes.csw.festivalcine.persistence.FestivalPersistence;
import co.edu.uniandes.csw.festivalcine.persistence.TeatroPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Mario Andrade
 */
@Stateless
public class FestivalLogic {
    
    private static final Logger LOGGER = Logger.getLogger(FestivalLogic.class.getName());
    
    @Inject
    private FestivalPersistence persistence;
    
    @Inject
    private TeatroPersistence teatroPersistence;
    
    @Inject
    private CriticoPersistence criticoPersistence;
    
    /**
     * Crea un festival en la persistencia.
     *
     * @param festivalEntity La entidad que representa el festival a
     * persistir.
     * @return La entidad del festival luego de persistirla.
     * @throws BusinessLogicException 
     */
    public FestivalEntity createFestival(FestivalEntity festivalEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del festival");
        
        if (persistence.findUserByName(festivalEntity.getNombre()) != null)
        {
            throw new BusinessLogicException("Ya existe un festival con el nombre \"" + festivalEntity.getNombre() + "\"");
        }
            
        // Invoca la persistencia para crear el festival
        persistence.create(festivalEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación del festival");
        return festivalEntity;
    }

    /**
     *
     * Obtener todas las festivales existentes en la base de datos.
     *
     * @return una lista de festivales
     */
    public List<FestivalEntity> getFestivales() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los festivales");
        List<FestivalEntity> festivales = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los festivales");
        return festivales;
    }
    
    /**
     *
     * Obtener un festival por medio de su id.
     *
     * @param festivalId: id del festival para ser buscado.
     * @return el festival solicitado por medio de su id.
     */
    public FestivalEntity getFestival(Long festivalId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el festival con id = {0}", festivalId);
        FestivalEntity festivalEntity = persistence.find(festivalId);
        if (festivalEntity == null) {
            LOGGER.log(Level.SEVERE, "El festival con el id = {0} no existe", festivalId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el festival con id = {0}", festivalId);
        return festivalEntity;
    }

    /**
     *
     * Actualizar un festival
     *
     * @param festivalId: id del festival para buscarlo en la base de
     * datos.
     * @param festivalEntity: festival con los cambios para ser actualizado,
     * por ejemplo el nombre 
     * @return el festival con los cambios actualizados en la base de datos.
     */
    public FestivalEntity updateFestival(Long festivalId, FestivalEntity festivalEntity)
    {
        

        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el festival con id = {0}", festivalId);
        FestivalEntity newEntity = persistence.update(festivalEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el festival con id = {0}", festivalEntity.getId());
        return newEntity;
    }

    /**
     * Borrar un festival
     *
     * @param festivalId: id del festival a borrar
     * @throws BusinessLogicException 
     */
    public void deleteFestival(Long festivalId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el festival con id = {0}", festivalId);
        
        persistence.delete(festivalId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el festival con id = {0}", festivalId);
    }    
    
}
