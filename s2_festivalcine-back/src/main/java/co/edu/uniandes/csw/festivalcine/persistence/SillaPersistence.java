/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.persistence;

import co.edu.uniandes.csw.festivalcine.entities.SillaEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * Clase que maneja la persistencia para Silla. Se conecta a través Entity
 * Manager de javax.persistance con la base de datos SQL.
 *
 * @author María Juliana Moya
 */
@Stateless
public class SillaPersistence {
    private static final Logger LOGGER = Logger.getLogger(SillaPersistence.class.getName());
    protected EntityManager em;

    /**
     * Método para persisitir la entidad en la base de datos.
     *
     * @param sillaEntity objeto sala que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public SillaEntity create(SillaEntity sillaEntity) {
        LOGGER.log(Level.INFO, "Creando una silla nueva");
        em.persist(sillaEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear una silla nueva");
        return sillaEntity;
    }
	
    /**
     * Devuelve todas las salas de la base de datos.
     *
     * @return una lista con todas las salas que encuentre en la base de
     */
    public List<SillaEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todas las sillas");
        TypedQuery query = em.createQuery("select u from SillaEntity u", SillaEntity.class);
        return query.getResultList();
    }
	
    /**
     * Busca si hay alguna silla con el id que se envía de argumento
     * @param sillaId: id correspondiente a la sala buscada.
     * @return una salal.
     */
    public SillaEntity find(Long sillaId) {
        LOGGER.log(Level.INFO, "Consultando silla con id={0}", sillaId);
        return em.find(SillaEntity.class, sillaId);
    }
    
    /**
     * Actualiza una silla
     * @param sillaEntity: la silla que viene con los nuevos cambios.
     * @return una silla con los cambios aplicados.
     */
    public SillaEntity update(SillaEntity sillaEntity) {
        LOGGER.log(Level.INFO, "Actualizando silla con id = {0}", sillaEntity.getId());
        LOGGER.log(Level.INFO, "Saliendo de actualizar la silla con id = {0}", sillaEntity.getId());
        return em.merge(sillaEntity);
    }
	
    /**
     *
     * Borra una silla de la base de datos recibiendo como argumento el id
     * de la silla
     *
     * @param sillasId: id correspondiente a la silla a borrar.
     */
    public void delete(Long sillasId) {
        LOGGER.log(Level.INFO, "Borrando silla con id = {0}", sillasId);
        SillaEntity entity = em.find(SillaEntity.class, sillasId);
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar la silla con id = {0}", sillasId);
    }
    
}
