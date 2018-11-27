/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.ejb;

import co.edu.uniandes.csw.festivalcine.entities.CriticoEntity;
import co.edu.uniandes.csw.festivalcine.entities.FestivalEntity;
import co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.festivalcine.persistence.CriticoPersistence;
import co.edu.uniandes.csw.festivalcine.persistence.FestivalPersistence;
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
public class FestivalCriticoLogic 
{
    private static final Logger LOGGER = Logger.getLogger(FestivalCriticoLogic.class.getName());
    
    @Inject
    private FestivalPersistence persistence;
    
    @Inject
    private CriticoPersistence criticoPersistence;
    
     /**
     * Agregar un nuevo critico al festival
     *
     * @param criticoId El id ldel critico a guardar
     * @param festivalId El id del festival en el cual se va a guardar el critico.
     * @return El critico creado.
     */
    public CriticoEntity addCritico(Long criticoId, Long festivalId) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de agregarle un critico al festival con id = {0}", festivalId);
        FestivalEntity festivalEntity = persistence.find(festivalId);
        CriticoEntity criticoEntity = criticoPersistence.find(criticoId);
        criticoEntity.darFestival().add(festivalEntity);
        LOGGER.log(Level.INFO, "Termina proceso de agregarle un critico al festival con id = {0}", festivalId);
        return criticoEntity;
    }
    
     /**
     * Retorna todos los criticos asociados a un festival
     *
     * @param festivalId El ID del festival buscado
     * @return La lista de criticos del festival
     */
    public List<CriticoEntity> getCriticos(Long festivalId) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los criticos asociados al festival con id = {0}", festivalId);
        return persistence.find(festivalId).getCriticos();
    }
    
    /**
     * Retorna un critico asociado a un festival
     *
     * @param festivalId El id del festival a buscar.
     * @param criticoId El id del critico a buscar
     * @return El critico encontrado dentro del festival.
     * @throws BusinessLogicException Si el critico no se encuentra en el festival
     */
    public CriticoEntity getCritico(Long festivalId, Long criticoId) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el critico con id = {0} del festival con id = " + criticoId , festivalId);
        List<CriticoEntity> criticos = persistence.find(festivalId).getCriticos();
        CriticoEntity criticoEntity = criticoPersistence.find(criticoId);
        
        for(int i = 0; i < criticos.size(); i++)
        {
            if(criticos.get(i).getId().equals(criticoEntity.getId()))
            {
                return criticos.get(i);
            }
        }       
        throw new BusinessLogicException("El critico no esta asociado al festival");
    }
    
    
    
    
    
    
    
    
    
    
}
