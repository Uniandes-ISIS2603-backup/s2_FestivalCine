/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.ejb;

import co.edu.uniandes.csw.festivalcine.entities.FuncionEntity;
import co.edu.uniandes.csw.festivalcine.entities.SalaEntity;
import co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.festivalcine.persistence.SalaPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;


/**
 * Clase que implementa la conexion con la persistencia para la entidad de
 * Sala
 *
 * @author María Juliana Moya
 */
@Stateless
public class SalaLogic {
    
    private static final Logger LOGGER = Logger.getLogger(SalaLogic.class.getName());

    @Inject
    private SalaPersistence persistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.
    
    /**
     * Crea una sala en la persistencia.
     *
     * @param salaEntity La entidad que representa la sala a
     * persistir.
     * @return La entidad de la sala luego de persistirla.
     */
    public SalaEntity createSala(SalaEntity salaEntity){
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la función");
        // Invoca la persistencia para crear la sala
        persistence.create(salaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación de la sala");
        return salaEntity;
    }

    /**
     *
     * Obtener todas las salas existentes en la base de datos.
     *
     * @return una lista de salas
     */
    public List<SalaEntity> getSalas() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las salas");
        List<SalaEntity> salas = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las salas");
        return salas;
    }
    
    /**
     *
     * Obtener una sala por medio de su id.
     *
     * @param salasId: id de la sala para ser buscada.
     * @return la función solicitada por medio de su id.
     */
    public SalaEntity getSala(Long salasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la sala con id = {0}", salasId);
        SalaEntity salaEntity = persistence.find(salasId);
        if (salaEntity == null) {
            LOGGER.log(Level.SEVERE, "La sala con el id = {0} no existe", salasId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar la sala con id = {0}", salasId);
        return salaEntity;
    }

    /**
     *
     * Actualizar una sala
     *
     * @param salasId: id de la sala para buscarla en la base de
     * datos.
     * @param salaEntity: sala con los cambios para ser actualizada,
     * por ejemplo el numero de sillas generales
     * @return la sala con los cambios actualizados en la base de datos.
     */
    public SalaEntity updateSala(Long salasId, SalaEntity salaEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la sala con id = {0}", salasId);
        SalaEntity newEntity = persistence.update(salaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la sala con id = {0}", salaEntity.getId());
        return newEntity;
    }

    /**
     * Borrar una sala
     *
     * @param salasId: id de la sala a borrar
     * @throws BusinessLogicException Si la sala a eliminar está asociada a una función
     */
    public void deleteSala(Long salasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la sala con id = {0}", salasId);
       
        // REGLA DE NEGOCIO: No se puede eliminar una sala con una función asignada
        List<FuncionEntity> funciones = persistence.find(salasId).getFuncion();
        if (funciones != null && !funciones.isEmpty()) {
            throw new BusinessLogicException("No se puede borrar la sala con id = " + salasId + " porque tiene funciones asociados");
        }
        persistence.delete(salasId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la sala con id = {0}", salasId);
    }   
}
