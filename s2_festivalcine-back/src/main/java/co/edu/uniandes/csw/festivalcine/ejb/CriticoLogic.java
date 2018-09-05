/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.ejb;

import co.edu.uniandes.csw.festivalcine.entities.CriticoEntity;
import co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.festivalcine.persistence.CriticoPersistence;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author estudiante
 */
@Stateless
public class CriticoLogic {
    
    private static final Logger LOGGER = Logger.getLogger(CriticoLogic.class.getName());
    
    @Inject
    private CriticoPersistence persistence;
    
    public CriticoEntity createCritico(CriticoEntity entity) throws BusinessLogicException
    {
        //LOGGER.log(Level.INFO, "Inicia proceso de creación del critico");
        //if(persistence.findByIdentificacion(entity.darIdentificacion()))
        //{
            //throw new BusinessLogicException("Ya existe un critico con el nombre \"" + entity.darIdentificacion() + "\"");
        //}
        //persistence.create(entity);
        //LOGGER.log(Level.INFO, "Termina proceso de creacion del critico");
        //return entity;
        return null;
    }
    
}
