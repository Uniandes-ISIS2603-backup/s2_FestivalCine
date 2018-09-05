/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.dtos;

import co.edu.uniandes.csw.festivalcine.entities.CalificacionEntity;
import co.edu.uniandes.csw.festivalcine.entities.CriticoEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author estudiante
 */
public class CriticoDetailDTO extends CriticoDTO implements Serializable
{
    //Atributos ------------------------------------------------------------------------------
    /**
     * Lista de tipo CalificacionDTO, contiene las calificaciones asociadas con el critico
     */
    private List<CalificacionDTO> calificaciones;
    
    /**
     * Lista de tipo FuncionDTO, contiene las funciones asociadas con el critico
     */
    private List funciones;
    
    /**
     * Lista de tipo PeliculaDto, contien las peliculas asociadas con el critico
     */
    private List peliculas;
    
    //Constructor ----------------------------------------------------------------------------
    
    /**
     * Constructor por defecto
     */
    public CriticoDetailDTO(){}
    
    /**
     * Constructor para transformar un Entity a un DTO
     * @param criticoEntity La entidad del critico para transformar a DTO.
     */
    public CriticoDetailDTO(CriticoEntity criticoEntity)
    {
        super(criticoEntity);
        if(criticoEntity != null)
        {
            if(criticoEntity.darCalificaciones() != null)
            {
                calificaciones = new ArrayList<CalificacionDTO>();
                for(CalificacionEntity entityCalificacion : criticoEntity.darCalificaciones())
                {
                    calificaciones.add(new CalificacionDTO(entityCalificacion));
                }
            }
            
            //if(criticoEntity.darFunciones() != null)
            //{
             //   funciones = new ArrayList<FuncionDTO>();
             //   for(FuncionDTO entityFuncion : criticoEntity.darFunciones())
             //   {
             //       funciones.add(new FuncionDTO(entityFuncion));
             //   }
            //}
            
            //if(criticoEntity.darPeliculas() != null)
            //{
               //peliculas = new ArrayList<PeliculaDTO>();
                //for(PeliculaDTO entityPelicula : criticoEntity.darPeliculas())
                //{
                  //  peliculas.add(new PeliculaDTO(entityPelicula));
                //} 
           // }
        }
    }
    
    /**
     * Transforma un DTO a un Entity
     * @return El DTO del critico para transformar a Entity
     */
    @Override
    public CriticoEntity toEntity()
    {
        CriticoEntity criticoEntity = super.toEntity();
        if(calificaciones != null)
        {
            List<CalificacionEntity> calificacionEntity =  new ArrayList<>();
            for(CalificacionDTO dtoCalificacion : calificaciones)
            {
                calificacionEntity.add(dtoCalificacion.toEntity());
            }
            criticoEntity.setCalificaciones(calificacionEntity);
        }
        //if(funciones != null)
        //{
          //  List<FuncionEntity> funcionEntity = new ArrayList();
          //  for(FuncionDTO dtoFuncion : funciones)
          //  {
           //     funcionEntity.add(dtoFuncion.toEntity());
          // }
          //  criticoEntity.setFunciones(funcionEntity);
        //}
        //if(peliculas != null)
        //{
          //  List<PeliculaEntity> peliculaEntity = new ArrayList<>();
           // for(PelicualDTO dtoPelicula : peliculas)
            //{
              //  peliculaEntity.add(dtoPelicula.toEntity());
            //}
            //criticoEntity.setPeliculas(peliculaEntity);
        //}
        return criticoEntity;
    }
    
    /**
     * Devuelve la lista de calificaciones del critico
     * @return Lista con calificaciones
     * 
     */
    public List<CalificacionDTO> getCalificaciones()
    {
        return calificaciones;
    }

    
    /**
     * Devuelve la lista de funciones del critico
     * @return List con funciones
     */
    public List getFunciones()
    {
        return funciones;
    }
    
    /**
     * Devuelve la lista de peliculas del critico
     * @return List con peliculas
     */
    public List getPeliculas()
    {
        return peliculas;
    }
    
    /**
     * Modifica la lista de calificaciones del critico
     * @param calificaciones List nueva de calificaciones
     */
    public void setCalificaciones(List<CalificacionDTO> calificaciones)
    {
        this.calificaciones = calificaciones;
    }
    /**
     * Modifica la lista de funciones del critico
     * @param funciones List nueva de funciones
     */
    public void setFunciones(List funciones)
    {
        this.funciones = funciones;
    }
    
    /**
     * Modifica la lista de peliculas del critico
     * @param peliculas Lista nueva de peliculas
     */
    public void setPeliculas(List peliculas)
    {
        this.peliculas = peliculas;
    }
    
    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
