/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.ejb;

import co.edu.uniandes.csw.festivalcine.entities.SillaEntity;
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
    private SalaPersistence salaPersistence;
     
    /**
     * Crea una silla en la persistencia.
     *
     * @param sillaEntity La entidad que representa la silla a
     * persistir.
     * @return La entidad de la silla luego de persistirla.
     */
    public SillaEntity createSilla(SillaEntity sillaEntity){
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la silla");        
        // Invoca la persistencia para crear la silla
        
        persistence.create(sillaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación de la silla");
        return sillaEntity;
    }

    /**
     *
     * Obtener todas las sillas existentes en la base de datos.
     *
     * @return una lista de salas
     */
    public List<SillaEntity> getSillas() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las sillas");
        List<SillaEntity> sillas = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las sillas");
        return sillas;
    }
    
    /**
     *
     * Obtener una silla por medio de su id.
     *
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
     *
     * Actualizar una silla
     *
     * @param sillasId: id de la silla para buscarla en la base de
     * datos.
     * @param sillaEntity: silla con los cambios para ser actualizada,
     * @return la silla con los cambios actualizados en la base de datos.
     */
    public SillaEntity updateSilla(Long sillasId, SillaEntity sillaEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la sala con id = {0}", sillasId);
        SillaEntity newEntity = persistence.update(sillaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la sala con id = {0}", sillaEntity.getId());
        return newEntity;
    }

    /**
     * Borrar una silla
     *
     * @param sillasId: id de la silla a borrar
     */
    public void deleteSilla(Long sillasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la silla con id = {0}", sillasId);
        persistence.delete(sillasId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la silla con id = {0}", sillasId);
    }
}
