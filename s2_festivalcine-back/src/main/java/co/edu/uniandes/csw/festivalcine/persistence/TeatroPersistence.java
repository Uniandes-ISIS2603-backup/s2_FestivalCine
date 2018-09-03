/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.persistence;

import co.edu.uniandes.csw.festivalcine.entities.TeatroEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author estudiante
 */
public class TeatroPersistence {

private static final Logger LOGGER = Logger.getLogger(TeatroPersistence.class.getName());    
protected EntityManager em;    

/**
 * Crea un teatro en la base de datos
 *
 * @param teatroEntity objeto teatro que se creará en la base de datos
 * @return devuelve la entidad creada con un id dado por la base de datos.
 */
public TeatroEntity create(TeatroEntity teatroEntity) {
        LOGGER.log(Level.INFO, "Creando un teatro nuevo");
        
        em.persist(teatroEntity);
        LOGGER.log(Level.INFO, "Teatro creado");
        return teatroEntity;
    }

/**
 * Devuelve todos los teatros de la base de datos.
 *
 * @return una lista con todos los teatros que encuentre en la base de
 * datos, "select u from TeatroEntity u" es como un "select * from
 * TeatroEntity;" - "SELECT * FROM table_name" en SQL.
 */
public List<TeatroEntity> findAll() {
    LOGGER.log(Level.INFO, "Consultando todos los teatros");    
    TypedQuery query = em.createQuery("select u from TeatroEntity u", TeatroEntity.class);
        
    return query.getResultList();
 }

    /**
     * Busca si hay algun teatro con el id que se envía de argumento
     *
     * @param teatroId: id correspondiente al teatro buscado.
     * @return un teatro.
     */
    public TeatroEntity find(Long teatroId) {
        LOGGER.log(Level.INFO, "Consultando el teatro con id={0}", teatroId);

        return em.find(TeatroEntity.class, teatroId);
    }

    /**
     * Actualiza un teatro.
     *
     * @param teatroEntity: el teatro que viene con los nuevos cambios. Por
     * ejemplo el nombre pudo cambiar. En ese caso, se haria uso del método
     * update.
     * @return unateatro con los cambios aplicados.
     */
    public TeatroEntity update(TeatroEntity teatroEntity) {
        LOGGER.log(Level.INFO, "Actualizando el teatro con id={0}", teatroEntity.getId());

        return em.merge(teatroEntity);
    }

    /**
     * Borra un teatro de la base de datos recibiendo como argumento el id del
     *teatro
     *
     * @param teatroId: id correspondiente al teatro a borrar.
     */
    public void delete(Long teatroId) {

        LOGGER.log(Level.INFO, "Borrando el author con id={0}", teatroId);
        TeatroEntity teatroEntity = em.find(TeatroEntity.class, teatroId);

        em.remove(teatroEntity);
    }
}
