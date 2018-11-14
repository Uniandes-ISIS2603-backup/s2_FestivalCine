/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.dtos;

import co.edu.uniandes.csw.festivalcine.entities.SalaEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Clase que representa una sala en la persistencia y permite su
 * serialización.
 * @author María Juliana Moya
 */
public class SalaDTO implements Serializable{
    
    
    //Atributos -------------------------------------------------------------------------
    
    private Long id;

    
    /**
     * Número de la sala
     */
    private Integer numero;

   /*
    * Relación a un teatro
    * dado que esta tiene cardinalidad 1.
    */

    private TeatroDTO teatro;


    
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
    public SalaDTO (SalaEntity salaEntity)
    {
        if(salaEntity != null)
        {
            this.id = salaEntity.getId();
            this.numero = salaEntity.getNumero();
            
            if (salaEntity.getTeatro() != null) {
                this.teatro = new TeatroDTO(salaEntity.getTeatro());
           } else {
                this.teatro = null;
           }
        }
    
    }
    
    //Métodos -------------------------------------------------------------------------------
    
    /**
     * Devuelve el ID de de la sala
     * @return Long 
     */
    public Long getId()
    {
        return id;
    }
    
   public void setId(Long id) {
        this.id = id;
    }
    
    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }
    
    public TeatroDTO getTeatro() {
        return teatro;
    }

    public void setTeatro(TeatroDTO teatro) {
        this.teatro = teatro;
    }
     
    /**
     * Convertir DTO a Entity
     * @return Entity funcion con los valores de FuncionDTO
     */
    public SalaEntity toEntity()
    {
        SalaEntity salaEntity = new SalaEntity();
        salaEntity.setId(this.id);
        salaEntity.setNumero(this.numero);
         if (this.teatro != null) {
            salaEntity.setTeatro(this.teatro.toEntity());
      }
        return salaEntity ;
    }
    
    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
