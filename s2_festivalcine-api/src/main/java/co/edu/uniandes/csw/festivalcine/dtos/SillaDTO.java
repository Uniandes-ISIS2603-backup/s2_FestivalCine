/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.dtos;

import co.edu.uniandes.csw.festivalcine.entities.SillaEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author María Juliana Moya
 */
public class SillaDTO implements Serializable{
      //Atributos -------------------------------------------------------------------------
    
    private Long id;
    /**
     * Indicia si la silla está disponible o tiene una reserva
     */
    private Boolean disponible;
    
    /**
     * Indicia si la silla es de tipo general
     */
    private Boolean tipo;
    
    /**
     * Numero de la silla
     */
    private Integer numero;
    
     /*
    * Relación a una sala
    * dado que esta tiene cardinalidad 1.
     */

    private SalaDTO sala;

    //Constructor ----------------------------------------------------------------------------
    
    /**
     * Constructor por defecto
     */
    public SillaDTO (){}
    
    /**
     * Convertir Entity a DTO (Crea un nuevoDTO con los valores que recibe en
     * la entidad que vienede argumento.)
     * @param sillaEntity: es la entidad que se va a convertir a DTO
     */
    public SillaDTO (SillaEntity sillaEntity)
    {
        if(sillaEntity != null)
        {
            this.id = sillaEntity.getId();
            this.disponible = sillaEntity.getDisponible();
            this.tipo= sillaEntity.getTipo();
            this.numero = sillaEntity.getNumero();
            
                       
           if (sillaEntity.getSala() != null) {
                this.sala = new SalaDTO(sillaEntity.getSala());
           } else {
                this.sala = null;
           }
        }
    }
    
    
    //Métodos -------------------------------------------------------------------------------
    
    /**
     * Devuelve el ID de de la silla
     * @return Long 
     */
    public Long getId()
    {
        return id;
    }
    
    /**
     * Devuelve el número de la silla
     * @return Integer
     */
    public Integer getNumero()
    {
        return numero;
    }
    
        
    /**
     * Devuelve si la silla está disponible
     * @return Booelan
     */
    public Boolean getDisponible()
    {
        return disponible;
    }
    
        /**
     * Devuelve si la silla es tipo general
     * @return Booelan
     */
    public Boolean getTipo()
    {
        return tipo;
    }
    
    
    /**
     * Modifica el ID de la silla
     * @param id el id nuevo
     */
    public void setId(Long id)
    {
        this.id = id;
    }
    
    /**
     * Modifica el número de la silla
     * @param numero
     */
    public void setNumero(Integer numero)
    {
        this.numero =numero;
    }
    
    /**
     * Modifica la disponibilidad de la silla
     * @param disponible
     */
    public void setDisponible(Boolean disponible)
    {
        this.disponible =disponible;
    }
     
     /**
     * Modifica el tipo de la silla
     * @param tipo
     */
    public void setTipo(Boolean tipo)
    {
        this.tipo =tipo;
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
     * Modifica la sala de la función
     * @param sala
     */
    public void setSala(SalaDTO sala)
    {
        this.sala = sala;
    }
    /**
     * Convertir DTO a Entity
     * @return Entity funcion con los valores de FuncionDTO
     */
    public SillaEntity toEntity()
    {
       SillaEntity sillaEntity = new SillaEntity();
       sillaEntity.setId(this.id);
       sillaEntity.setNumero(this.numero);
       sillaEntity.setDisponible( this.disponible);
       sillaEntity.setTipo ( this.tipo);
             if (this.sala != null) {
            sillaEntity.setSala(this.sala.toEntity());
      }
       
       return sillaEntity ;
    }
    
    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
