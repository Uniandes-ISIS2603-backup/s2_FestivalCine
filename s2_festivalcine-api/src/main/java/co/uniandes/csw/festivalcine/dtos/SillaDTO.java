/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.uniandes.csw.festivalcine.dtos;

import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author estudiante
 */
public class SillaDTO implements Serializable{
      //Atributos -------------------------------------------------------------------------
    
    private Long id;
    private Boolean disponible;
    private Boolean tipo;
    private Integer numero;

    //Constructor ----------------------------------------------------------------------------
    
    /**
     * Constructor por defecto
     */
    public SillaDTO (){
    
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
            this.tipo= sillaEntity.getGeneral();
            this.numero = sillaEntity.getNumero();
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
    public Boolean getGeneral()
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
        return sillaEntity ;
    }
    
    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
