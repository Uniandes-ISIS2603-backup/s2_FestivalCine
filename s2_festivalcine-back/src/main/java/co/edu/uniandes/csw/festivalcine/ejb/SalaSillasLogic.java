/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.ejb;

import co.edu.uniandes.csw.festivalcine.entities.SalaEntity;
import co.edu.uniandes.csw.festivalcine.entities.SillaEntity;
import co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.festivalcine.persistence.SalaPersistence;
import co.edu.uniandes.csw.festivalcine.persistence.SillaPersistence;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la relación entre
 * la entidad de Sala y Silla
 *
 * @author María Juliana Moya 
 */
@Stateless
public class SalaSillasLogic {
    private static final Logger LOGGER = Logger.getLogger(SalaSillasLogic.class.getName());

    @Inject
    private SalaPersistence salaPersistence;

    @Inject
    private SillaPersistence sillaPersistence;

    /**
     * Agregar una silla a la sala
     *
     * @param sillasId El id de la silla a asginar
     * @param salasId El id de la sala en la cual se va a asignar la silla
     * @return La silla asignada.
     * @throws co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException
     */
    public SillaEntity addSilla(Long sillasId, Long salasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de asignarle una silla a la sala con id = {0}", salasId);
        SalaEntity salaEntity = salaPersistence.find(salasId);
        SillaEntity sillaEntity = sillaPersistence.find(sillasId);
        
        List<SillaEntity> sillasSala = salaEntity.getSillas();
        
        //Regla de negocio: No se puede asignar una silla con el mismo número a la sala
        for (SillaEntity silla: sillasSala){
            if (Objects.equals(silla.getNumero(), sillaEntity.getNumero()))
                throw new BusinessLogicException("No se puede asignar una silla con el mismo número a la sala");
        }
        
        sillaEntity.setSala(salaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asignarle una silla a la sala con id = {0}", salasId);
        return sillaEntity;
    }

    /**
     * Retorna todas las sillas asociadas a la sala
     *
     * @param salasId El ID de la sala buscada
     * @return La lista de sillas de la sala.
     */
    public List<SillaEntity> getSillas(Long salasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar las sillas asociadas a la sala con id = {0}", salasId);
        return salaPersistence.find(salasId).getSillas();
    }

    /**
     * Retorna una silla asociada a una sala
     *
     * @param salasId El id de la sala a buscar.
     * @param sillasId El id de la silla a buscar
     * @return La silla encontrada en la sala.
     * @throws BusinessLogicException Si la silla no se encuentra en la
     * sala
     */
    public SillaEntity getSilla(Long salasId, Long sillasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la silla con id = {0} de la sala con id = " + salasId, sillasId);
        List<SillaEntity> sillas = salaPersistence.find(salasId).getSillas();
        SillaEntity sillaEntity = sillaPersistence.find(sillasId);
        int index = sillas.indexOf(sillaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar la silla con id = {0} de la sala con id = " + salasId, sillasId);
        if (index >= 0) {
            return sillas.get(index);
        }
        throw new BusinessLogicException("La silla no está asignada a la sala");
    }
}
