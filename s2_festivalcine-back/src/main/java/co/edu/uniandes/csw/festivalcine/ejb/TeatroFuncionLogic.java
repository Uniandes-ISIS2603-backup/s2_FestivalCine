/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.ejb;

import co.edu.uniandes.csw.festivalcine.entities.FuncionEntity;
import co.edu.uniandes.csw.festivalcine.entities.TeatroEntity;
import co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.festivalcine.persistence.FuncionPersistence;
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
public class TeatroFuncionLogic 
{
    private static final Logger LOGGER = Logger.getLogger(TeatroFuncionLogic.class.getName());
    
    @Inject
    private TeatroPersistence persistence;
    
    @Inject
    private FuncionPersistence funcionPersistence;
    
     /**
     * Agregar un nuevo funcion al teatro
     *
     * @param funcionId El id ldel funcion a guardar
     * @param teatroId El id del teatro en el cual se va a guardar el critico.
     * @return El funcion creado.
     */
    public FuncionEntity addFuncion(Long funcionId, Long teatroId) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de agregarle una funcion al teatro con id = {0}", teatroId);
        TeatroEntity teatroEntity = persistence.find(teatroId);
        FuncionEntity funcionEntity = funcionPersistence.find(funcionId);
        funcionEntity.setTeatro(teatroEntity);
        LOGGER.log(Level.INFO, "Termina proceso de agregarle una funcion al teatro con id = {0}", teatroId);
        return funcionEntity;
    }
    
     /**
     * Retorna todas las funciones asociados a un teatro
     *
     * @param teatroId El ID del teatro buscado
     * @return La lista de funciones del festival
     */
    public List<FuncionEntity> getFunciones(Long teatroId) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar las funciones asociados al teatro con id = {0}", teatroId);
        return persistence.find(teatroId).getFunciones();
    }
    
    /**
     * Retorna una funcion asociado a un teatro
     *
     * @param teatroId El id del teatro a buscar.
     * @param funcionId El id de la funcion a buscar
     * @return La funcion encontrada dentro del teatro.
     * @throws BusinessLogicException Si la funcion no se encuentra en el teatro
     */
    public FuncionEntity getFuncion(Long teatroId, Long funcionId) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la funcion con id = {0} del teatro con id = " + funcionId , teatroId);
        List<FuncionEntity> funciones = persistence.find(teatroId).getFunciones();
        FuncionEntity funcionEntity = funcionPersistence.find(funcionId);
        
        int index = -1;
        
        if(funciones.indexOf(funcionEntity) >= 0)
        {
            index = funciones.indexOf(funcionEntity);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar la funcion con id = {0} del teatro con id = " + funcionId, teatroId);
        
        if (index >= 0) 
        {
            return funciones.get(index);
        }
        
        throw new BusinessLogicException("La función no está asociado a un teatro");
    }   
}
