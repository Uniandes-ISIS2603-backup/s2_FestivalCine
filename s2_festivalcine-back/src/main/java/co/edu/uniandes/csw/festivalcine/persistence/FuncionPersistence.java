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
import javax.persistence.PersistenceContext;
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
     * @param FuncionEntity objeto funcion que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public FuncionEntity create(FuncionEntity funcionEntity) {
        LOGGER.log(Level.INFO, "Creando una función nueva");
        
        /* Note que hacemos uso de un método propio de EntityManager para persistir la editorial en la base de datos.
        Es similar a "INSERT INTO table_name (column1, column2, column3, ...) VALUES (value1, value2, value3, ...);" en SQL.
         */
        
        em.persist(funcionEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear una funcion nueva");
        return funcionEntity;
    }
    
    	/**
     * Devuelve todas las funciones de la base de datos.
     *
     * @return una lista con todas las funciones que encuentre en la base de
     * datos, "select u from FuncionEntity u" es como un "select * from
     * FunciionEntity;" - "SELECT * FROM table_name" en SQL.
     */
    public List<FuncionEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todas las funciones");
        // Se crea un query para buscar todas las editoriales en la base de datos.
        TypedQuery query = em.createQuery("select u from FuncionEntity u", FuncionEntity.class);
        // Note que en el query se hace uso del método getResultList() que obtiene una lista de editoriales.
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
        /* Note que se hace uso del metodo "find" propio del EntityManager, el cual recibe como argumento 
        el tipo de la clase y el objeto que nos hara el filtro en la base de datos en este caso el "id"
        Suponga que es algo similar a "select * from EditorialEntity where id=id;" - "SELECT * FROM table_name WHERE condition;" en SQL.
         */
        return em.find(FuncionEntity.class, funcionesId);
    }

    /**
     * Actualiza una función
     *
     * @param funcionEntity: la funcion que viene con los nuevos cambios.
     * @return una funcion con los cambios aplicados.
     */
    public FuncionEntity update(FuncionEntity funcionEntity) {
        LOGGER.log(Level.INFO, "Actualizando editorial con id = {0}", funcionEntity.getId());
        /* Note que hacemos uso de un método propio del EntityManager llamado merge() que recibe como argumento
        la editorial con los cambios, esto es similar a 
        "UPDATE table_name SET column1 = value1, column2 = value2, ... WHERE condition;" en SQL.
         */
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
        LOGGER.log(Level.INFO, "Borrando editorial con id = {0}", funcionesId);
        // Se hace uso de mismo método que esta explicado en public EditorialEntity find(Long id) para obtener la editorial a borrar.
        FuncionEntity entity = em.find(FuncionEntity.class, funcionesId);
        /* Note que una vez obtenido el objeto desde la base de datos llamado "entity", volvemos hacer uso de un método propio del
         EntityManager para eliminar de la base de datos el objeto que encontramos y queremos borrar.
         Es similar a "delete from EditorialEntity where id=id;" - "DELETE FROM table_name WHERE condition;" en SQL.*/
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar la función con id = {0}", funcionesId);
    }
 
}
