/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.dtos;

import co.edu.uniandes.csw.festivalcine.entities.BandaAnuncioEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author cc.cardeans
 */
public class BandaAnuncioDTO implements Serializable{
      //Atributos -------------------------------------------------------------------------
    
    private Long id;
   private Integer duracion;

    //Constructor ----------------------------------------------------------------------------
    
    /**
     * Constructor por defecto
     */
    public BandaAnuncioDTO (){}
    
    /**
     * Convertir Entity a DTO (Crea un nuevoDTO con los valores que recibe en
     * la entidad que vienede argumento.)
     * @param bandaAnuncioEntity: es la entidad que se va a convertir a DTO
     */
    public BandaAnuncioDTO (BandaAnuncioEntity banda)
    {
        if(banda != null)
        {
            this.id = banda.getId();
            this.duracion= banda.getDuracion();
        }
    }
    
    
    //Métodos -------------------------------------------------------------------------------
    
    /**
     * Devuelve el ID de de la banda de anuncio
     * @return Long 
     */
    public Long getId()
    {
        return id;
    }
    
    public Integer getDuracion(){
        return duracion;
    }
   
    /**
     * Modifica el ID de la banda
     * @param id el id nuevo
     */
    public void setId(Long id)
    {
        this.id = id;
    }
    
    public void setDuracion(Integer duracion){
        this.duracion=duracion;
    }
    /**
     * Convertir DTO a Entity
     * @return Entity BandaAnuncio con los valores de BandaAnuncioDTO
     */
    public BandaAnuncioEntity toEntity()
    {
        BandaAnuncioEntity banda= new BandaAnuncioEntity();
        banda.setId(this.id);
        banda.setDuracion(this.duracion);
       return banda ;
    }
    
    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}