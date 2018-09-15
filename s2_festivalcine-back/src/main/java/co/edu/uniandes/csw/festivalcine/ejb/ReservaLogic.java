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
        if (reservaEntity.getUsuario() == null || reservaEntity.getId() == null || persistence.findReserva(reservaEntity.getId()) != null || reservaEntity.getId() < 0)
        {
            throw new BusinessLogicException("Ya existe una reserva con el id \"" + reservaEntity.getId() + "\"");
        }
        // Invoca la persistencia para crear la editorial
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
    
     /**
     *
     * Obtener todas las reservas existentes en la base de datos.
     *
     * @return una lista de reservas.
     */
    public List<SillaEntity> getSillas() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las sillas");
       
        List<SillaEntity> sillas = persistence.findAllSillas();
        
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las sillas");
        return sillas;
    }
    
     /**
     *
     * Obtener todas las funciones existentes en la base de datos.
     *
     * @return una lista de reservas.
     */
    public List<FuncionEntity> getFunciones() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las funciones");
       
        List<FuncionEntity> funciones = persistence.findAllFunciones();
        
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las funciones");
        return funciones;
    }
    
    
    /**
     * LÓGICA DE LA RELACIÓN CON USUARIO
     */
    
    /**
     * Remplazar el usuario de una reserva.
     *
     * @param reservasId id de la reserva que se quiere actualizar.
     * @param usuariosId El id del usuario que se será del libro.
     * @return la nueva reserva.
     */
    public ReservaEntity replaceUsuario(Long reservasId, Long usuariosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar reserva con id = {0}", reservasId);
        UsuarioEntity usuarioEntity = usuarioPersistence.findUsuario(usuariosId);
        ReservaEntity reservaEntity = persistence.findReserva(reservasId);
        reservaEntity.setUsuario(usuarioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar reserva con id = {0}", reservaEntity.getId());
        return reservaEntity;
    }

    /**
     * Borrar una reserva de un usuario. Este metodo se utiliza para borrar la
     * relacion de una reserva.
     *
     * @param reservasId La reserva que se desea borrar del usuario.
     */
    public void removeUsuario(Long reservasId) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el usuario de la reserva con id = {0}", reservasId);
        ReservaEntity reservaEntity = persistence.findReserva(reservasId);
        UsuarioEntity usuarioEntity = usuarioPersistence.findUsuario(reservaEntity.getUsuario().getId());
        reservaEntity.setUsuario(null);
        usuarioEntity.getReservas().remove(reservaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el usuario de la reserva con id = {0}", reservaEntity.getId());
    }
    
    /**
     * LÓGICA DE LA RELACIÓN CON FUNCIÓN
     */
    
    /**
     * Asocia una Funcion existente a una Reserva
     *
     * @param reservasId Identificador de la instancia de Reserva
     * @param funcionesId Identificador de la instancia de Funcion
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
        
        int index = funciones.indexOf(funcionEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar la funcion con id = {0} de la reserva con id  = " + reservasId, funcionsId);
        if (index >= 0) 
        {
            return funciones.get(index);
        }
        throw new BusinessLogicException("La función no está asociada a la reserva");
    }
    
    /**
     * Remplaza las instancias de Funcion asociadas a una instancia de Reserva
     *
     * @param reservaId Identificador de la instancia de Reserva
     * @param funciones Colección de instancias de FuncionEntity a asociar a instancia
     * de Reserva
     * @return Nueva colección de FuncionEntity asociada a la instancia de Reserva
     */
//    public List<FuncionEntity> replaceFunciones(Long reservaId, List<FuncionEntity> funciones) 
//    {
//        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar las funciones asocidas a la reserva con id = {0}", reservaId);
//        ReservaEntity reservaEntity = funcionPersistence.findReserva(reservaId);
//        
//        List<FuncionEntity> funcionList = funcionPersistence.findAll();
//        for (FuncionEntity funcion : funcionList) {
//            if (funciones.contains(funcion)) {
//                if (!funcion.getReservas().contains(reservaEntity)) 
//                {
//                    funcion.getReservas().add(reservaEntity);
//                }
//            } 
//            else 
//            {
//                funcion.getReservas().remove(reservaEntity);
//            }
//        }
//        reservaEntity.setFunciones(funciones);
//        LOGGER.log(Level.INFO, "Termina proceso de reemplazar las funciones asocidas a la reserva con id = {0}", reservaId);
//        return reservaEntity.getFunciones();
//    }
    
    /**
     * Desasocia una funcion existente de una reserva existente
     *
     * @param reservasId Identificador de la instancia de Reserva
     * @param funcionId Identificador de la instancia de Funcion
     */
    public void removeFuncion(Long reservasId, Long funcionId) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar una funcion de la reserva con id = {0}", reservasId);
        ReservaEntity reservaEntity = persistence.findReserva(reservasId);
        FuncionEntity funcionEntity = funcionPersistence.find(funcionId);
        funcionEntity.getReservas().remove(reservaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar una función de la reserva con id = {0}", reservasId);
    }
    
    /**
     * LÓGICA DE LA RELACIÓN CON SILLA
     */
    /**
     * Asocia una Silla existente a una Reserva
     *
     * @param reservasId Identificador de la instancia de Reserva
     * @param sillaId Identificador de la instancia de Silla
     * @return Instancia de SillaEntity que fue asociada a Reserva
     */
    public SillaEntity addSilla(Long reservaId, Long sillaId) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de asociarle una silla a la reserva con id = {0}", reservaId);
        
        ReservaEntity reservaEntity = persistence.findReserva(reservaId);
        
        SillaEntity sillaEntity = sillaPersistence.find(sillaId);
        
        sillaEntity.setReserva(reservaEntity);
        
        LOGGER.log(Level.INFO, "Termina proceso de asociarle una silla a la reserva con id = {0}", reservaId);
        
        return sillaEntity;
    }
    
     /**
     * Retorna todos las sillas asociadas a una reserva
     *
     * @param reservasId El ID de la reserva buscada
     * @return La lista de sillas de la reserva
     */
    public List<SillaEntity> getSillas(Long reservasId) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar las sillas asociados a la reserva con id = {0}", reservasId);
        return persistence.findReserva(reservasId).getSillas();
    }
    
     /**
     * Retorna una silla asociado a una reserva
     *
     * @param reservaId El id de la reserva a buscar.
     * @param sillasId El id de la sillaa buscar
     * @return La silla encontrado dentro de la reserva.
     * @throws BusinessLogicException Si la silla no se encuentra en la
     * reserva
     */
    public SillaEntity getSilla(Long reservasId, Long sillasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la silla con id = {0} de la reserva con id = " + reservasId, sillasId);
        List<SillaEntity> sillas = persistence.findReserva(reservasId).getSillas();
        SillaEntity sillaEntity = sillaPersistence.find(sillasId);
        
        int index = sillas.indexOf(sillaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar la silla con id = {0} de la reserva con id = " + reservasId, sillasId);
        if (index >= 0) 
        {
            return sillas.get(index);
        }
        throw new BusinessLogicException("La silla no está asociado a la reserva");
    }
    
    /**
     * Remplazar sillas de una reserva
     *
     * @param sillas Lista de sillas que serán las de la reserva.
     * @param reservasId El id de la reserva que se quiere actualizar.
     * @return La lista de sillas actualizada.
     */
    public List<SillaEntity> replaceSillas(Long reservasId, List<SillaEntity> sillas)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la reserva con id = {0}", reservasId);
        ReservaEntity reservaEntity = persistence.findReserva(reservasId);
        List<SillaEntity> sillaList = sillaPersistence.findAll();
        for (SillaEntity silla : sillaList) 
        {
            if (sillas.contains(silla)) 
            {
                silla.setReserva(reservaEntity);
            }
            else if (silla.getReserva() != null && silla.getReserva().equals(reservaEntity)) 
            {
                silla.setReserva(null);
            }
        }
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la reserva con id = {0}", reservasId);
        return sillas;
    } 
}
