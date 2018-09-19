/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.ejb;

import co.edu.uniandes.csw.festivalcine.entities.CalificacionEntity;
import co.edu.uniandes.csw.festivalcine.entities.CriticoEntity;
import co.edu.uniandes.csw.festivalcine.entities.FuncionEntity;
import co.edu.uniandes.csw.festivalcine.entities.PeliculaEntity;
import co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.festivalcine.persistence.CalificacionPersistence;
import co.edu.uniandes.csw.festivalcine.persistence.CriticoPersistence;
import co.edu.uniandes.csw.festivalcine.persistence.FuncionPersistence;
import co.edu.uniandes.csw.festivalcine.persistence.PeliculaPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Andres Felipe Rodriguez Murillo
 */
@Stateless
public class CriticoLogic {
    
    private static final Logger LOGGER = Logger.getLogger(CriticoLogic.class.getName());
    
    @Inject
    private CriticoPersistence persistence;
    
    @Inject
    private FuncionPersistence funcionPersistence;
    
    @Inject
    private PeliculaPersistence peliculaPersistence;
    
    @Inject
    private CalificacionPersistence calificacionPersistence;
    
    /**
     * Crea un nuevo critico con la información que se recibe en el cuerpo de la
     * petición y se regresa un objeto con la id auto-generada por la base de
     * datos
     * 
     * @param entity {@link CriticoDTO}  - El critico que se desea guardar.
     * @return JSON {@link CriticoDTO} - EL critico guardado con el atributo id
     * autogenerado.
     * @throws BusinessLogicException Si el critico con la misma identificación ya existe
     */
    public CriticoEntity createCritico(CriticoEntity entity) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del critico");
        if(persistence.findByIdentificacion(entity.darIdentificacion()) != null)
        {
            throw new BusinessLogicException("Ya existe un critico con la identificacion \"" + entity.darIdentificacion() + "\"");
        }
        persistence.create(entity);
        LOGGER.log(Level.INFO, "Termina proceso de creacion del critico");
        return entity;
    }
    
    /**
     * Obtiene una colección de instancias de CriticoEntity
     * 
     * @return Lista de entidades de tipo critico. 
     */
    public List<CriticoEntity> getCriticos()
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los criticos");
        List<CriticoEntity> criticos = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los criticos");
        return criticos;
    }
    
    /**
     * Busca un critico por id
     * 
     * @param criticosId El id del critico a buscar
     * @return El critico encontrado, null si no lo encuentra.
     */
    public CriticoEntity getCritico(Long criticosId)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el critico con id = {0}", criticosId);
        CriticoEntity criticoEntity = persistence.find(criticosId);
        if(criticoEntity == null)
        {
            LOGGER.log(Level.SEVERE, "El critico con el id = {0} no existe", criticosId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el critico con id = {0}", criticosId);
        return criticoEntity;
    }
    
    /**
     * Actualiza un critico por id
     * 
     * @param criticosId El id del critico a actualizar
     * @param criticoEntity La entidad critico con los cambios deseados
     * @return La entidad del critico luego de actualizarla
     * @throws BusinessLogicException  Si la identificacion de la actualiacion es inválida.
     */
    public CriticoEntity updateCritico(Long criticosId, CriticoEntity criticoEntity) throws BusinessLogicException
    {
       LOGGER.log(Level.INFO, "Inicia proceso de actualizar el critico con id = {0}", criticosId);
       if(!validateIdentificacion(criticoEntity.darIdentificacion()))
       {
           throw new BusinessLogicException("La identificacion es inválido");
       }
       CriticoEntity newEntity = persistence.update(criticoEntity);
       LOGGER.log(Level.INFO, "Termina proceso de actualizar el critico con el id = {0}", criticoEntity.getId());
       return newEntity;
    }
    
    /**
     * Elimina un critico por id
     * 
     * @param criticosId El id del critico a eliminar
     * @throws BusinessLogicException  si el critico tiene funciones o peliculas asociados
     */
    public void deleteCritico(Long criticosId) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el critico con id = {0}", criticosId);
        List<FuncionEntity> funciones = getCritico(criticosId).darFunciones();
        List<PeliculaEntity> peliculas = getCritico(criticosId).darPeliculas();
        if (funciones != null && !funciones.isEmpty()) {
            throw new BusinessLogicException("No se puede borrar el critico con id = " + criticosId + " porque tiene funciones asociadas");
        }
        persistence.delete(criticosId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el critico con id = {0}", criticosId);
    }
    
    /**
     * Verifica que la identificacion no sea invalida
     * 
     * @param identificacion a verificar
     * @return true si la identificacion es valida.
     */
    public boolean validateIdentificacion(String identificacion)
    {
        return !(identificacion == null || identificacion.isEmpty());
    }
    
    /**
     * Aoscia una funcion existente a un Critico
     * 
     * @param criticosId Identificador de la instancia de critico
     * @param funcionesId Identificador de la isntancia Funcion
     * @return Instancia de FuncionEntity que due asociada al Critico
     */
    public FuncionEntity addFuncion(Long criticosId, Long funcionesId)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de asociarle una función al critico con id = {0}", criticosId);
        CriticoEntity criticoEntity = persistence.find(criticosId);
        FuncionEntity funcionEntity = funcionPersistence.find(funcionesId);
        funcionEntity.setCritico(criticoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociarle una funcion al critico con id =  {0}", criticosId);
        return funcionPersistence.find(funcionesId);
    }
    
    /**
     * Obtiene una coleccion de instancias de FuncionEntity sociadas a una 
     * instancia de Critico
     * 
     * @param criticosId Identificacidor de la instancia de Critico
     * @return Coleccion de instancias de FuncionEntitty asociadas a la instancia de 
     * Critico
     */
    public List<FuncionEntity> getFunciones(Long criticosId)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las dunciones del critico con id = {0}", criticosId);
        return persistence.find(criticosId).darFunciones();
    }
    
    /**
     * Obtiene una instancia de FuncionEntity asociada a una instancia de Critico
     * 
     * @param criticosId Identificador de la instancia Critico
     * @param funcionesId Identificador de la instancia de Funcion
     * @return La entidad de la Funcion del Ccritico
     * @throws BusinessLogicException  Si el ibro no está asociado al critico
     */
    public FuncionEntity getFuncion(Long criticosId, Long funcionesId) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la funcion con id = {1} del critico con id = {0}" + criticosId, funcionesId);
        List<FuncionEntity> funciones = persistence.find(criticosId).darFunciones();
        FuncionEntity funcionEntity = funcionPersistence.find(funcionesId);
        int index = funciones.indexOf(funcionEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar la funcion con id = {1} del criticocon id = {0}" + criticosId, funcionesId);
        if(index >=0)
        {
            return funciones.get(index);
        }
        throw new BusinessLogicException("La funcion no está asociada al critico");
    }
    
    public void removeFuncion(Long criticosId, Long funcionesId)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar una funcion del critico con id = {0}", criticosId);
        CriticoEntity criticoEntity = persistence.find(criticosId);
        FuncionEntity funcionEntity = funcionPersistence.find(funcionesId);
        funcionEntity.setCritico(null);
        LOGGER.log(Level.INFO, "Termina proceso de borrar ua funcion del critico con id = {0}", criticosId);
    }
    
    public PeliculaEntity addPelicula(Long criticosId, Long peliculasId)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de asociarle una pelicula al libro con id ={0}", criticosId);
        PeliculaEntity peliculaEntity = peliculaPersistence.findById(peliculasId);
        CriticoEntity criticoEntity = persistence.find(criticosId);
        criticoEntity.darPeliculas().add(peliculaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociarle una pelicula al critico con id = {0}", criticosId);
        return peliculaPersistence.findById(peliculasId);
    }
    
    public List<PeliculaEntity> getPeliculas(Long criticosId)
    {
        LOGGER.log(Level.INFO,"Inicia proceso de consultar todas ,as peliculas del critico con id = {0}", criticosId);
        return persistence.find(criticosId).darPeliculas();
    }
    
    public PeliculaEntity getPelicula(Long criticosId, Long peliculasId)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar una pelicula del critico con id = {0}", criticosId);
        List<PeliculaEntity> peliculas =persistence.find(criticosId).darPeliculas();
        PeliculaEntity peliculaEntity = peliculaPersistence.findById(peliculasId);
        int index = peliculas.indexOf(peliculaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar una pelicula del critico con id = {0}", criticosId);
        if(index >= 0)
        {
            return peliculas.get(index);
        }
        return null;
    }
    
    public void deletePelicula(Long criticosId, Long peliculasId)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar una pelicula del critico con id = {0}", criticosId);
        PeliculaEntity peliculaEntity = peliculaPersistence.findById(peliculasId);
        CriticoEntity criticoEntity = persistence.find(criticosId);
        criticoEntity.darPeliculas().remove(peliculasId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar una pelicula del critico con id = {0}", criticosId);
    }
    
    public CalificacionEntity addCalificacion(Long criticosId, Long calificacionesId)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de asociarle una calificacion cal critico con id = {0}", criticosId);
        CalificacionEntity calificacionEntity = calificacionPersistence.find(calificacionesId);
        CriticoEntity criticoEntity = persistence.find(criticosId);
        criticoEntity.darCalificaciones().add(calificacionEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociarle una calificacion al critico con id = {0}", criticosId);
        return calificacionPersistence.find(calificacionesId);
    }
    
    public List<CalificacionEntity> getCalificaciones(Long criticosId)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las calificaciones del critico con id = {0},", criticosId);
        return persistence.find(criticosId).darCalificaciones();
    }
    
    public CalificacionEntity getCalificacion(Long criticosId, Long calificacionesId)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar una calificacion del libro con id = {0}", criticosId);
        List<CalificacionEntity> calificaciones = persistence.find(criticosId).darCalificaciones();
        CalificacionEntity calificacionEntity = calificacionPersistence.find(calificacionesId);
        int index = calificaciones.indexOf(calificacionEntity);
        LOGGER.log(Level.INFO, "termina proceso de consultar la calificacion del critico con id = {0}", criticosId);
        if(index >= 0)
                {
                    return calificaciones.get(index);
                }
        return null;
    }
    
    public void deleteCalificacion(Long criticosId, Long calificacionesId)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar una calificacion del critico con id = {0}", criticosId);
        CalificacionEntity calificacionEntity = calificacionPersistence.find(calificacionesId);
        CriticoEntity criticoEntity = persistence.find(criticosId);
        criticoEntity.darCalificaciones().remove(calificacionEntity);
        LOGGER.log(Level.INFO, "termina proceso de borrar una calificacion del critico con id = {0}", criticosId);
    }
    
}
