/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.ejb;

import co.edu.uniandes.csw.festivalcine.entities.CriticoEntity;
import co.edu.uniandes.csw.festivalcine.entities.FuncionEntity;
import co.edu.uniandes.csw.festivalcine.entities.ReservaEntity;
import co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.festivalcine.persistence.CriticoPersistence;
import co.edu.uniandes.csw.festivalcine.persistence.FuncionPersistence;
import co.edu.uniandes.csw.festivalcine.persistence.PeliculaPersistence;
import co.edu.uniandes.csw.festivalcine.persistence.ReservaPersistence;
import co.edu.uniandes.csw.festivalcine.persistence.SalaPersistence;
import java.util.List;
import javax.ejb.Stateless;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la entidad de
 * Función.
 *
 * @author María Juliana Moya
 */
@Stateless
public class FuncionLogic {
    
    private static final Logger LOGGER = Logger.getLogger(FuncionLogic.class.getName());

    @Inject
    private FuncionPersistence persistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    @Inject
    private CriticoPersistence criticoPersistence;
    
    @Inject
    private PeliculaPersistence peliculaPersistence;
     
    @Inject
    private SalaPersistence salaPersistence;
    
    @Inject
    private ReservaPersistence reservaPersistence;
    
        
    /**
     * Crea una función en la persistencia.
     *
     * @param funcionEntity La entidad que representa la función a
     * persistir.
     * @return La entidad de la función luego de persistirla.
     * @throws BusinessLogicException Si la sala o la película son invalidas
     */
    public FuncionEntity createFuncion(FuncionEntity funcionEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la función");
        
        if (funcionEntity.getPelicula() != null || peliculaPersistence.findById(funcionEntity.getPelicula().getId()) == null) {
            throw new BusinessLogicException("La película es invalida");
        }
        if(funcionEntity.getSala() != null || salaPersistence.find(funcionEntity.getSala().getId())== null ){
            throw new BusinessLogicException("La sala es invalida");
        }
            
        // Invoca la persistencia para crear la función
        persistence.create(funcionEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación de la función");
        return funcionEntity;
    }

    /**
     *
     * Obtener todas las funciones existentes en la base de datos.
     *
     * @return una lista de funciones
     */
    public List<FuncionEntity> getFunciones() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las funciones");
        List<FuncionEntity> funciones = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las funciones");
        return funciones;
    }
    
    /**
     *
     * Obtener una función por medio de su id.
     *
     * @param funcionesId: id de la función para ser buscada.
     * @return la función solicitada por medio de su id.
     */
    public FuncionEntity getFuncion(Long funcionesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la función con id = {0}", funcionesId);
        FuncionEntity funcionEntity = persistence.find(funcionesId);
        if (funcionEntity == null) {
            LOGGER.log(Level.SEVERE, "La función con el id = {0} no existe", funcionesId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar la función con id = {0}", funcionesId);
        return funcionEntity;
    }

    /**
     *
     * Actualizar una función
     *
     * @param funcionesId: id de la funcion para buscarla en la base de
     * datos.
     * @param funcionEntity: funcion con los cambios para ser actualizada,
     * por ejemplo el precio base
     * @throws BusinessLogicException Si la sala o la película son invalidas
     * @return la función con los cambios actualizados en la base de datos.
     */
    public FuncionEntity updateFuncion(Long funcionesId, FuncionEntity funcionEntity)throws BusinessLogicException {
        
        if (funcionEntity.getPelicula() != null || peliculaPersistence.findById(funcionEntity.getPelicula().getId()) == null) {
            throw new BusinessLogicException("La película es invalida");
        }
        if(funcionEntity.getSala() != null || salaPersistence.find(funcionEntity.getSala().getId())== null ){
            throw new BusinessLogicException("La sala es invalida");
        }
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la función con id = {0}", funcionesId);
        FuncionEntity newEntity = persistence.update(funcionEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la función con id = {0}", funcionEntity.getId());
        return newEntity;
    }

    /**
     * Borrar un función
     *
     * @param funcionesId: id de la función a borrar
     * @throws BusinessLogicException Si la función a eliminar tiene reservas.
     */
    public void deleteFuncion(Long funcionesId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la función con id = {0}", funcionesId);
        //FALTA DEFINIR REGLA DE NEGOCIO CON RESERVAS
        persistence.delete(funcionesId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la función con id = {0}", funcionesId);
    }
    
    /**
     * Borrar el critico de una función
     *
     * @param funcionesId La función que se desea borrar del crítico
     * @throws BusinessLogicException si la función no tiene crítico
     */
    public void removeCritico(Long funcionesId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el crítico de la funcion con id = {0}", funcionesId);
        FuncionEntity funcionEntity = persistence.find(funcionesId);
        if (funcionEntity.getCritico() == null) {
            throw new BusinessLogicException("La función no tiene crítico");
        }
        CriticoEntity criticoEntity = criticoPersistence.find(funcionEntity.getCritico().getId());
        funcionEntity.setCritico(null);
        criticoEntity.darFunciones().remove(funcionEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el crítico con id = {0} de la función con id = " + funcionesId, criticoEntity.getId());
    }
 
    
}
