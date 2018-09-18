/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.ejb;

import co.edu.uniandes.csw.festivalcine.entities.FuncionEntity;
import co.edu.uniandes.csw.festivalcine.entities.ReservaEntity;
import co.edu.uniandes.csw.festivalcine.entities.SillaEntity;
import co.edu.uniandes.csw.festivalcine.entities.UsuarioEntity;
import co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.festivalcine.persistence.FuncionPersistence;
import co.edu.uniandes.csw.festivalcine.persistence.ReservaPersistence;
import co.edu.uniandes.csw.festivalcine.persistence.SillaPersistence;
import co.edu.uniandes.csw.festivalcine.persistence.UsuarioPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author estudiante
 */
@Stateless
public class ReservaLogic 
{
    private static final Logger LOGGER = Logger.getLogger(ReservaLogic.class.getName());

    @Inject
    private ReservaPersistence persistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.
    
    @Inject
    private UsuarioPersistence usuarioPersistence;
    
    @Inject
    private FuncionPersistence funcionPersistence;
    
    @Inject
    private SillaPersistence sillaPersistence;
    
    private List<SillaEntity> sillas;
    
     private List<FuncionEntity> funciones;
    
    /**
     * Crea una reserva en la persistencia.
     *
     * @param reservaEntity La entidad que representa la reserva a
     * persistir.
     * @return La reserva de la editorial luego de persistirla.
     * @throws BusinessLogicException Si la reserva a persistir ya existe.
     */
    public ReservaEntity createReserva(ReservaEntity reservaEntity) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la reserva");
        // Verifica la regla de negocio que dice que no puede haber dos editoriales con el mismo nombre
        if (reservaEntity.getId() == null || persistence.findReserva(reservaEntity.getId()) != null || reservaEntity.getId() < 0)
        {
            throw new BusinessLogicException("Ya existe una reserv con el id \"" + reservaEntity.getId() + "\"");
        }

        persistence.create(reservaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación de la reserva");
        return reservaEntity;
    }
    
     /**
     *
     * Obtener todas las reservas existentes en la base de datos.
     *
     * @return una lista de reservas.
     */
    public List<ReservaEntity> getReservas() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las reservas");
        // Note que, por medio de la inyección de dependencias se llama al método "findAll()" que se encuentra en la persistencia.
        List<ReservaEntity> reservas = persistence.findAllReservas();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las reservas");
        return reservas;
    }
    
        /**
     *
     * Obtener una reserva por medio de su id.
     *
     * @param reservasId: id de la reserva para ser buscada.
     * @return la reserva solicitada por medio de su id.
     */
    public ReservaEntity getReserva(Long reservasId) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la reserva con id = {0}", reservasId);
        // Note que, por medio de la inyección de dependencias se llama al método "find(id)" que se encuentra en la persistencia.
        ReservaEntity reservaEntity = persistence.findReserva(reservasId);
        if (reservaEntity == null) 
        {
            LOGGER.log(Level.SEVERE, "La reserva con el id = {0} no existe", reservasId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar la reserva con id = {0}", reservasId);
        return reservaEntity;
    }
    
       /**
     *
     * Actualizar una editorial.
     *
     * @param editorialsId: id de la editorial para buscarla en la base de
     * datos.
     * @param editorialEntity: editorial con los cambios para ser actualizada,
     * por ejemplo el nombre.
     * @return la editorial con los cambios actualizados en la base de datos.
     */
    public ReservaEntity updateReserva(Long reservasId, ReservaEntity reservaEntity) throws BusinessLogicException
    {
        if(reservasId == null || reservasId < 0)
        {
            throw new BusinessLogicException("Ya existe una reserva con el id \"" + reservaEntity.getId() + "\"");
        }
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la reserva con id = {0}", reservasId);
        // Note que, por medio de la inyección de dependencias se llama al método "update(entity)" que se encuentra en la persistencia.
        ReservaEntity newEntity = persistence.updateReserva(reservaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la reserva con id = {0}", reservaEntity.getId());
        return newEntity;
    }
    
     /**
     * Borrar una reserva
     *
     * @param reservasId: id de la reserva a borrar
     * @throws BusinessLogicException Si la reserva a eliminar tiene funciones.
     */
    public void deleteReserva(Long reservasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la reserva con id = {0}", reservasId);
        persistence.deleteReserva(reservasId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la reserva con id = {0}", reservasId);
    }
    
    
}
