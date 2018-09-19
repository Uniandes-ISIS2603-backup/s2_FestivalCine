/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.ejb;

import co.edu.uniandes.csw.festivalcine.entities.FestivalEntity;
import co.edu.uniandes.csw.festivalcine.entities.TeatroEntity;
import co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.festivalcine.persistence.FestivalPersistence;
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
public class FestivalTeatroLogic 
{
    private static final Logger LOGGER = Logger.getLogger(FestivalTeatroLogic.class.getName());
    
    @Inject
    private FestivalPersistence persistence;
    
    @Inject
    private TeatroPersistence teatroPersistence;
    
     /**
     * Agregar un nuevo teatro al festival
     *
     * @param teatroId El id ldel teatro a guardar
     * @param festivalId El id del festival en el cual se va a guardar el teatro.
     * @return El teatro creado.
     */
    public TeatroEntity addTeatro(Long teatroId, Long festivalId) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de agregarle un teatro al festival con id = {0}", festivalId);
        FestivalEntity festivalEntity = persistence.find(festivalId);
        TeatroEntity teatroEntity = teatroPersistence.find(teatroId);
        teatroEntity.getFestival().add(festivalEntity);
        LOGGER.log(Level.INFO, "Termina proceso de agregarle un teatro al festival con id = {0}", festivalId);
        return teatroEntity;
    }
    
     /**
     * Retorna todos los teatros asociados a un festival
     *
     * @param festivalId El ID del festival buscado
     * @return La lista de teatros del festival
     */
    public List<TeatroEntity> getTeatros(Long festivalId) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los teatros asociados al festival con id = {0}", festivalId);
        return persistence.find(festivalId).getTeatros();
    }
    
    /**
     * Retorna un teatro asociado a un festival
     *
     * @param festivalId El id del festival a buscar.
     * @param teatroId El id del teatro a buscar
     * @return El teatro encontrado dentro del festival.
     * @throws BusinessLogicException Si el teatro no se encuentra en el festival
     */
    public TeatroEntity getTeatro(Long festivalId, Long teatroId) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el teatro con id = {0} del festival con id = " + teatroId , festivalId);
        List<TeatroEntity> teatros = persistence.find(festivalId).getTeatros();
        TeatroEntity teatroEntity = teatroPersistence.find(teatroId);
        
        int index = -1;
        
        if(teatros.indexOf(teatroEntity) >= 0)
        {
            index = teatros.indexOf(teatroEntity);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el teatro con id = {0} del festival con id = " + teatroId, festivalId);
        
        if (index >= 0) 
        {
            return teatros.get(index);
        }
        
        throw new BusinessLogicException("El teatro no está asociado a un festival");
    }  
}
