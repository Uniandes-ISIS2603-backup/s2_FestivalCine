/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.ejb;

import co.edu.uniandes.csw.festivalcine.entities.SalaEntity;
import co.edu.uniandes.csw.festivalcine.entities.TeatroEntity;
import co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.festivalcine.persistence.SalaPersistence;
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
public class TeatroSalaLogic 
{
 private static final Logger LOGGER = Logger.getLogger(TeatroFuncionLogic.class.getName());
    
    @Inject
    private TeatroPersistence persistence;
    
    @Inject
    private SalaPersistence salaPersistence;
    
     /**
     * Agregar un nuevo sala al teatro
     *
     * @param salaId El id ldel sala a guardar
     * @param teatroId El id del teatro en el cual se va a guardar el critico.
     * @return El sala creado.
     */
    public SalaEntity addSala(Long salaId, Long teatroId) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de agregarle una sala al teatro con id = {0}", teatroId);
        TeatroEntity teatroEntity = persistence.find(teatroId);
        SalaEntity salaEntity = salaPersistence.find(salaId);
        salaEntity.setTeatro(teatroEntity);
        LOGGER.log(Level.INFO, "Termina proceso de agregarle una sala al teatro con id = {0}", teatroId);
        return salaEntity;
    }
    
     /**
     * Retorna todas las salas asociados a un teatro
     *
     * @param teatroId El ID del teatro buscado
     * @return La lista de salas del festival
     */
    public List<SalaEntity> getSalas(Long teatroId) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar las salas asociados al teatro con id = {0}", teatroId);
        return persistence.find(teatroId).getSalas();
    }
    
    /**
     * Retorna una sala asociado a un teatro
     *
     * @param teatroId El id del teatro a buscar.
     * @param salaId El id de la sala a buscar
     * @return La sala encontrada dentro del teatro.
     * @throws BusinessLogicException Si la sala no se encuentra en el teatro
     */
    public SalaEntity getSala(Long teatroId, Long salaId) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la sala con id = {0} del teatro con id = " + salaId , teatroId);
        List<SalaEntity> salas = persistence.find(teatroId).getSalas();
        SalaEntity salaEntity = salaPersistence.find(salaId);
        for(int i = 0; i < salas.size(); i++)
        {
            if(salas.get(i).getId().equals(salaEntity.getId()))
            {
                return salas.get(i);
            }
        }   
        LOGGER.log(Level.INFO, "Termina proceso de consultar la sala");      
        throw new BusinessLogicException("La sala no esta asociada a la teatro");
    }   
    
    
    
    
    
    
    
}
