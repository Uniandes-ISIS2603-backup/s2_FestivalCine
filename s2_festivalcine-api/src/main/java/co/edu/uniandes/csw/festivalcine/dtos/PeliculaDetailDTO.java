/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.dtos;

import co.edu.uniandes.csw.festivalcine.entities.PeliculaEntity;
import co.edu.uniandes.csw.festivalcine.entities.CalificacionEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


public class PeliculaDetailDTO extends PeliculaDTO implements Serializable
{

  private List<CalificacionDTO> calificaciones;
  
 
  /**
   * Metoodo constructor vacio
   */
  public PeliculaDetailDTO()
  {
      
  }
  
  /**
   * 
   * @param 
   */
   public PeliculaDetailDTO(PeliculaEntity p)
    {
        super(p);
        calificaciones = new ArrayList<>();
      
        if(p != null)
        {
            for (CalificacionEntity entityCal : p.getCalificaciones()) 
            {
                if(entityCal != null)
                {
                    calificaciones.add(new CalificacionDTO(entityCal));
                }
            }  
        }
    }
    
   
    @Override
    public PeliculaEntity toEntity()
    {
        PeliculaEntity peliculaEntity = super.toEntity();
        if(calificaciones != null)
        {
            List<CalificacionEntity> calificacionEntity =  new ArrayList<>();
            for(CalificacionDTO dtoCal: calificaciones)
            {
                calificacionEntity.add(dtoCal.toEntity());
            }
            peliculaEntity.setCalificaciones(calificacionEntity);
        }
        
        return peliculaEntity;
    }
    
  
     public List<CalificacionDTO> getCalificaciones() 
    {
        return calificaciones;
    }
     
  
    public void setCalificaciones(List<CalificacionDTO> calificaciones) 
    {
        this.calificaciones = calificaciones;
    }

    
    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
    
    
    
    
    
    
    
    
    
    
}