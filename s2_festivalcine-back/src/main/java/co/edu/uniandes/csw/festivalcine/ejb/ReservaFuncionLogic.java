/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.ejb;

import co.edu.uniandes.csw.festivalcine.entities.FuncionEntity;
import co.edu.uniandes.csw.festivalcine.entities.ReservaEntity;
import co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.festivalcine.persistence.FuncionPersistence;
import co.edu.uniandes.csw.festivalcine.persistence.ReservaPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author PAULA VELANDIA
*/ 
 @Stateless
public class ReservaFuncionLogic 
{
    private static final Logger LOGGER = Logger.getLogger(ReservaLogic.class.getName());

    @Inject
    private ReservaPersistence persistence;
    
    @Inject
    private FuncionPersistence funcionPersistence;
    /**
     * LÓGICA DE LA RELACIÓN CON FUNCIÓN
     */
    
    /**
     * Asocia una Funcion existente a una Reserva
     *
     * @param reservaId Identificador de la instancia de Reserva
     * @param funcionId Identificador de la instancia de Funcion
     * @return Instancia de FunciinEntity que fue asociada a Reserva
     */
    public FuncionEntity addFuncion(Long reservaId, Long funcionId) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de asociarle una función a la reserva con id = {0}", reservaId);
        ReservaEntity reservaEntity = persistence.findReserva(reservaId);
        FuncionEntity funcionEntity = funcionPersistence.find(funcionId);
        funcionEntity.getReservas().add(reservaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociarle una funcion a la reserva con id = {0}", reservaId);
        return funcionPersistence.find(funcionId);
    }
    
    /**
     * Obtiene una colección de instancias de FuncionEntity asociadas a una
     * instancia de Reserva
     *
     * @param reservasId Identificador de la instancia de Reserva
     * @return Colección de instancias de FuncionEntity asociadas a la instancia de
     * Reserva
     */
    public List<FuncionEntity> getFunciones(Long reservasId)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos las funciones de la reserva con id = {0}", reservasId);
        return persistence.findReserva(reservasId).getFunciones();
    }
    
    /**
     * Obtiene una instancia de FuncionEntity asociada a una instancia de Reserva
     *
     * @param reservasId Identificador de la instancia de Reserva
     * @param funcionsId Identificador de la instancia de Funcion
     * @return La entidadd de la funcion de la reserva
     * @throws BusinessLogicException Si la funcion no está asociado a la reserva
     */
    public FuncionEntity getFuncion(Long reservasId, Long funcionsId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la funcion con id = {0} de la reserva con id = " + reservasId, funcionsId);
        List<FuncionEntity> funciones = persistence.findReserva(reservasId).getFunciones();
        FuncionEntity funcionEntity = funcionPersistence.find(funcionsId);
       for(int i = 0; i < funciones.size(); i++)
        {
            if(funciones.get(i).getId().equals(funcionEntity.getId()))
            {
                return funciones.get(i);
            }
        }   
        LOGGER.log(Level.INFO, "Termina proceso de consultar la funcion");      
        throw new BusinessLogicException("La funcion no esta asociada a la reserva");
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
