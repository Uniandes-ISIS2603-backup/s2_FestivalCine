/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.persistence;

import co.edu.uniandes.csw.festivalcine.entities.FuncionEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author estudiante
 */
@Stateless
public class FuncionPersistence {
    
    private static final Logger LOGGER = Logger.getLogger(FuncionPersistence.class.getName());

    protected EntityManager em;

    /**
     * Método para persisitir la entidad en la base de datos.
     *
     * @param funcionEntity objeto funcion que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public FuncionEntity create(FuncionEntity funcionEntity) {
        LOGGER.log(Level.INFO, "Creando una función nueva");
        em.persist(funcionEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear una funcion nueva");
        return funcionEntity;
    }
    
    	/**
     * Devuelve todas las funciones de la base de datos.
     * @return una lista con todas las funciones que encuentre en la base de
     * datos, "select u from FuncionEntity u" es como un "select * from
     * FunciionEntity;" - "SELECT * FROM table_name" en SQL.
     */
    public List<FuncionEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todas las funciones");
        TypedQuery query = em.createQuery("select u from FuncionEntity u", FuncionEntity.class);
        return query.getResultList();
    }
	
    /**
     * Busca si hay alguna función  con el id que se envía de argumento
     *
     * @param funcionesId: id correspondiente a la funcion buscada.
     * @return una editorial.
     */
    public FuncionEntity find(Long funcionesId) {
        LOGGER.log(Level.INFO, "Consultando funcion con id={0}", funcionesId);
        return em.find(FuncionEntity.class, funcionesId);
    }

    /**
     * Actualiza una función
     * @param funcionEntity: la funcion que viene con los nuevos cambios.
     * @return una funcion con los cambios aplicados.
     */
    public FuncionEntity update(FuncionEntity funcionEntity) {
        LOGGER.log(Level.INFO, "Actualizando funcion con id = {0}", funcionEntity.getId());
        LOGGER.log(Level.INFO, "Saliendo de actualizar la editorial con id = {0}", funcionEntity.getId());
        return em.merge(funcionEntity);
    }
	
    /**
     *
     * Borra una funcion de la base de datos recibiendo como argumento el id
     * de la funcion
     *
     * @param funcionesId: id correspondiente a la funcion a borrar.
     */
    public void delete(Long funcionesId) {
        LOGGER.log(Level.INFO, "Borrando funcion con id = {0}", funcionesId);
        FuncionEntity entity = em.find(FuncionEntity.class, funcionesId);
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar la función con id = {0}", funcionesId);
    }
 
}
