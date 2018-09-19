/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.ejb;
import co.edu.uniandes.csw.festivalcine.entities.FuncionEntity;
import co.edu.uniandes.csw.festivalcine.entities.TeatroEntity;
import co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException;
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
public class TeatroLogic {
    
    private static final Logger LOGGER = Logger.getLogger(TeatroLogic.class.getName());
    
    @Inject
    private TeatroPersistence persistence;
    
    /**
     * Crea un teatro en la persistencia.
     *
     * @param teatroEntity La entidad que representa el teatro a
     * persistir.
     * @return La entidad del teatro luego de persistirla.
     * @throws BusinessLogicException 
     */
    public TeatroEntity createTeatro(TeatroEntity teatroEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del teatro");
        
        TeatroEntity teatroVerificar = persistence.find(teatroEntity.getId());
        if(teatroVerificar.getNombre().equals(teatroEntity.getNombre()))
        {
            throw new BusinessLogicException("Ya existe un teatro con el nombre \"" + teatroEntity.getNombre() + "\"");
        }
            
        // Invoca la persistencia para crear el teatro
        persistence.create(teatroEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación del teatro");
        return teatroEntity;
    }

    /**
     *
     * Obtener todas los teatros existentes en la base de datos.
     *
     * @return una lista de teatros
     */
    public List<TeatroEntity> getTeatros() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los teatros");
        List<TeatroEntity> teatros = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los teatros");
        return teatros;
    }
    
    /**
     *
     * Obtener un teatro por medio de su id.
     *
     * @param teatroId: id del teatro para ser buscado.
     * @return el teatro solicitado por medio de su id.
     */
    public TeatroEntity getTeatro(Long teatroId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el teatro con id = {0}", teatroId);
        TeatroEntity teatroEntity = persistence.find(teatroId);
        if (teatroEntity == null) {
            LOGGER.log(Level.SEVERE, "El teatro con el id = {0} no existe", teatroId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el teatro con id = {0}", teatroId);
        return teatroEntity;
    }

    /**
     *
     * Actualizar un teatro
     *
     * @param teatroId: id del teatro para buscarlo en la base de
     * datos.
     * @param teatroEntity: teatro con los cambios para ser actualizado,
     * por ejemplo el nombre
     * @return el teatro con los cambios actualizados en la base de datos.
     */
    public TeatroEntity updateTeatro(Long teatroId, TeatroEntity teatroEntity)
    {
        

        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el teatro con id = {0}", teatroId);
        TeatroEntity newEntity = persistence.update(teatroEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el teatro con id = {0}", teatroEntity.getId());
        return newEntity;
    }

    /**
     * Borrar un teatro
     *
     * @param teatroId: id del teatro a borrar
     * @throws BusinessLogicException 
     */
    public void deleteTeatro(Long teatroId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el teatro con id = {0}", teatroId);
                List<FuncionEntity> funciones = getTeatro(teatroId).getFunciones();
        if (funciones != null && !funciones.isEmpty()) {
            throw new BusinessLogicException("No se puede borrar el teatro con id = " + teatroId + " porque tiene funciones asociadas");
        }
        persistence.delete(teatroId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el teatro con id = {0}", teatroId);
    }    
    
}

