/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.uniandes.csw.festivalcine.dtos;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author María Juliana Moya
 */
public class FuncionDTO implements Serializable{
    
    //Atributos -------------------------------------------------------------------------
    private Long id;
    private Date horaInicio;
    private Date horaFin;
    private Integer precioBase;
    
    /*
    * Relación a una sala
    * dado que esta tiene cardinalidad 1.
     */

    private SalaDTO sala;
    
    /*
    * Relación a una película  
    * dado que esta tiene cardinalidad 1.
     */

    //private PeliculaDTO pelicula;
    
    /*
    * Relación a un crítico 
    * dado que esta tiene cardinalidad 1.
     */

    //private CriticoDTO critico;
    
   
    //Constructor ----------------------------------------------------------------------------
    
    /**
     * Constructor por defecto
     */
    public FuncionDTO(){}
    /**
     * Convertir Entity a DTO (Crea un nuevoDTO con los valores que recibe en
     * la entidad que vienede argumento.)
     * @param funcionEntity: es la entidad que se va a convertir a DTO
     */
    //public FuncionDTO(FuncionEntity funcionEntity)
    //{
    //    if(funcionEntity != null)
    //    {
    //        this.id = funcionEntity.getId();
    //        this.horaInicio = funcionEntity.getHoraInicio();
    //        this.horaFin = funcionEntity.getHoraFin();
    //        this.precioBase = funcionEntity.getPrecioBase();
    //   }
    //}
   
    
    //Métodos -------------------------------------------------------------------------------
    
    /**
     * Devuelve el ID de de la funcion
     * @return Long 
     */
    public Long getId()
    {
        return id;
    }
    
    /**
     * Devuelve la hora de inicio de la función
     * @return Date 
     */
    public Date getHoraInicio()
    {
        return horaInicio;
    }
    
    /**
     * Devuelve la hora de fin de la función
     * @return Date 
     */
    public Date getHoraFin()
    {
        return horaFin;
    }
    
     /**
     * Devuelve el precio base de la función
     * @return Integer
     */
    public Integer getPrecioBase()
    {
        return precioBase;
    }
    
     /**
     * Devuelve la sala en la que se presentará la función
     * @return SalaDTO
     */
    public SalaDTO getSala()
    {
        return sala;
    }
    
     /**
     * Devuelve la pelicula que se presentará en la función
     * @return SalaDTO
     */
    //public PeliculaDTO getPelicula()
    //{
    //    return pelicula;
    //}
    
     /**
     * Devuelve el crítico asignado en la función
     * @return CriticoDTO
     */
   // public CriticoDTO getCritico()
   //{
   //     return critico;
   //}
      
    /**
     * Modifica el ID de la función
     * @param id el id nuevo
     */
    public void setId(Long id)
    {
        this.id = id;
    }
    
    /**
     * Modifica la hora de inicio de la función
     * @param horaInicio
     */
    public void setHoraInicio(Date horaInicio)
    {
        this.horaInicio= horaInicio;
    }
    
        /**
     * Modifica la hora fin de la función
     * @param horaFin
     */
    public void setHoraFin(Date horaFin)
    {
        this.horaFin= horaFin;
    }
    
     /**
     * Modifica el precio base de la función
     * @param precioBase 
     */
    public void setPrecioBase(Integer precioBase)
    {
        this.precioBase = precioBase;
    }
    
     /**
     * Modifica la sala de la función
     * @param sala
     */
    public void setSala(SalaDTO sala)
    {
        this.sala = sala;
    }
       
     /**
     * Modifica la sala de la función
     * @param PeliculaDTO 
     */
    //public void setPelicula(PeliculaDTO pelicula)
    //{
    //    this.pelicula = pelicula;
    //}
    
    /**
     * Modifica el crítico de la función
     * @param CriticoDTO 
     */
    //public void setCritico(CriticoDTO critico)
    //{
    //    this.critico = critico;
    //}
   
    /**
     * Convertir DTO a Entity
     * @return Entity funcion con los valores de FuncionDTO
     */
   // public FuncionEntity toEntity()
   //{
     // funcionEntity.setId(this.id);
     // funcionEntity.setHoraInicio(this.horaInicio);
     // funcionEntity.setHoraFin(this.horaFin);
     // funcionEntity.setPrecioBase(this.precioBase);
     // funcionEntity.setPelicula(this.pelicula);
     // funcionEntity.setSala(this.sala);
     //   return funcionEntity ;
    //}
    
    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}

