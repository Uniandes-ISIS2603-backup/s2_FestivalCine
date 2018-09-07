/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.persistence;

import co.edu.uniandes.csw.festivalcine.entities.SalaEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * Clase que maneja la persistencia para Sala. Se conecta a través Entity
 * Manager de javax.persistance con la base de datos SQL.
 *
 * @author María Juliana Moya
 */
@Stateless
public class SalaPersistence {
    
    private static final Logger LOGGER = Logger.getLogger(SalaPersistence.class.getName());
    protected EntityManager em;

    /**
     * Método para persisitir la entidad en la base de datos.
     *
     * @param salaEntity objeto sala que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public SalaEntity create(SalaEntity salaEntity) {
        LOGGER.log(Level.INFO, "Creando una sala nueva");
        em.persist(salaEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear una sala nueva");
        return salaEntity;
    }
	
    /**
     * Devuelve todas las salas de la base de datos.
     *
     * @return una lista con todas las salas que encuentre en la base de
     */
    public List<SalaEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todas las salas");
        TypedQuery query = em.createQuery("select u from SalaEntity u", SalaEntity.class);
        return query.getResultList();
    }
	
    /**
     * Busca si hay alguna sala con el id que se envía de argumento
     * @param salaId: id correspondiente a la sala buscada.
     * @return una salal.
     */
    public SalaEntity find(Long salaId) {
        LOGGER.log(Level.INFO, "Consultando sala con id={0}", salaId);
        return em.find(SalaEntity.class, salaId);
    }
    
    /**
     * Actualiza una sala.
     *
     * @param salaEntity: la sala que viene con los nuevos cambios.
     * @return una sala con los cambios aplicados.
     */
    public SalaEntity update(SalaEntity salaEntity) {
        LOGGER.log(Level.INFO, "Actualizando sala con id = {0}", salaEntity.getId());
        LOGGER.log(Level.INFO, "Saliendo de actualizar la sala con id = {0}", salaEntity.getId());
        return em.merge(salaEntity);
    }
	
    /**
     *
     * Borra una sala de la base de datos recibiendo como argumento el id
     * de la sala
     *
     * @param salasId: id correspondiente a la sala a borrar.
     */
    public void delete(Long salasId) {
        LOGGER.log(Level.INFO, "Borrando sala con id = {0}", salasId);
        SalaEntity entity = em.find(SalaEntity.class, salasId);
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar la sala con id = {0}", salasId);
    }
}
