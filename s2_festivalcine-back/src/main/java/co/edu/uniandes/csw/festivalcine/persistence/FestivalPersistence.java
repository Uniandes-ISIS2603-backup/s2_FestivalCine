/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.persistence;

import co.edu.uniandes.csw.festivalcine.entities.FestivalEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Mario Andrade
 */
@Stateless
public class FestivalPersistence {

private static final Logger LOGGER = Logger.getLogger(FestivalPersistence.class.getName());
@PersistenceContext(unitName = "TarantinoPU")
protected EntityManager em;

/**
 * Crea un festival en la base de datos
 *
 * @param festivalEntity objeto festival que se creará en la base de datos
 * @return devuelve la entidad creada con un id dado por la base de datos.
 */
public FestivalEntity create(FestivalEntity festivalEntity) {
        LOGGER.log(Level.INFO, "Creando un festival nuevo");
        
        em.persist(festivalEntity);
        LOGGER.log(Level.INFO, "Festival creado");
        return festivalEntity;
    }

/**
 * Devuelve todos los festivales de la base de datos.
 *
 * @return una lista con todos los festivales que encuentre en la base de
 * datos, "select u from FestivalEntity u" es como un "select * from
 * FestivalEntity;" - "SELECT * FROM table_name" en SQL.
 */
public List<FestivalEntity> findAll() {
    LOGGER.log(Level.INFO, "Consultando todos los festivales");    
    TypedQuery query = em.createQuery("select u from FestivalEntity u", FestivalEntity.class);
        
    return query.getResultList();
 }

    /**
     * Busca si hay algun festival con el id que se envía de argumento
     *
     * @param festivalId: id correspondiente al festival buscado.
     * @return un festival.
     */
    public FestivalEntity find(Long festivalId) {
        LOGGER.log(Level.INFO, "Consultando el festival con id={0}", festivalId);

        return em.find(FestivalEntity.class, festivalId);
    }

    /**
     * Actualiza un festival.
     *
     * @param festivalEntity: el festival que viene con los nuevos cambios. Por
     * ejemplo el nombre pudo cambiar. En ese caso, se haria uso del método
     * update.
     * @return una festival con los cambios aplicados.
     */
    public FestivalEntity update(FestivalEntity festivalEntity) {
        LOGGER.log(Level.INFO, "Actualizando el festival con id = {0}", festivalEntity.getId());

        return em.merge(festivalEntity);
    }

    /**
     * Borra un festival de la base de datos recibiendo como argumento el id del
     * festival
     *
     * @param festivalId: id correspondiente al festival a borrar.
     */
    public void delete(Long festivalId) {

        LOGGER.log(Level.INFO, "Borrando el author con id={0}", festivalId);
        FestivalEntity festivalEntity = em.find(FestivalEntity.class, festivalId);

        em.remove(festivalEntity);
    }

}
