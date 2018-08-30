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
 * @author María Juliana Moya
 */
public class SalaDTO implements Serializable{
    
    
    //Atributos -------------------------------------------------------------------------
    
    private Long id;
    private Integer numSillasGene;
    private Integer numSillasPref;

    //Constructor ----------------------------------------------------------------------------
    
    /**
     * Constructor por defecto
     */
    public SalaDTO (){}
    
    /**
     * Convertir Entity a DTO (Crea un nuevoDTO con los valores que recibe en
     * la entidad que vienede argumento.)
     * @param salaEntity: es la entidad que se va a convertir a DTO
     */
    //public SalaDTO (SalaEntity salaEntity)
    //{
    //    if(salaEntity != null)
    //    {
    //        this.id = salaEntity.getId();
    //        this.numSillasGene = salaEntity.getSillasGeneral();
    //        this.numSillasPref= salaEntity.getSillasPreferencial();
    //    }
    
    
    
    //Métodos -------------------------------------------------------------------------------
    
    /**
     * Devuelve el ID de de la sala
     * @return Long 
     */
    public Long getId()
    {
        return id;
    }
    
    /**
     * Devuelve la cantidad de sillas generales
     * @return Integer
     */
    public Integer getSilasGeneral()
    {
        return numSillasGene;
    }
    
        
    /**
     * Devuelve la cantidad de sillas preferenciales
     * @return Integer
     */
    public Integer getSilasPreferencial()
    {
        return numSillasPref;
    }
    
    
    /**
     * Modifica el ID de la sala
     * @param id el id nuevo
     */
    public void setId(Long id)
    {
        this.id = id;
    }
    
    /**
     * Modifica la cantidad de sillas preferenciales 
     * @param numSillasGene
     */
    public void setSillasGenerales(Integer numSillasGene)
    {
        this.numSillasGene =numSillasGene;
    }
    
    /**
     * Modifica la cantidad de sillas preferenciales 
     * @param numSillasPref
     */
    public void setSillasPreferenciales(Integer numSillasPref)
    {
        this.numSillasPref =numSillasPref;
    }
     
    /**
     * Convertir DTO a Entity
     * @return Entity funcion con los valores de FuncionDTO
     */
    //public SalaEntity toEntity()
    //{
    //    SalaEntity salaEntity = new SalaEntity();
    //    salaEntity.setId(this.id);
    //    salaEntity.setSillasGenerales (this.numSillasGene);
    //    salaEntity.setSillasPreferenciales (this.numSillasPref);
    //    return salaEntity ;
    //}
    
    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
