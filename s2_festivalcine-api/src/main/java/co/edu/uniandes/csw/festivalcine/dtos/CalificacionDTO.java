/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.dtos;

import co.edu.uniandes.csw.festivalcine.entities.CalificacionEntity;
import java.io.Serializable;



/**
 *
 * @author estudiante
 */
public class CalificacionDTO implements Serializable
{
    private Long id;
    
    private Double puntaje;
    
    private String comentario;
    
    private UsuarioDTO usuario;

   
    
    
    //Constructor ----------------------------------------------------------------------------
    
    public CalificacionDTO(){}
    
    public CalificacionDTO(CalificacionEntity calificacionEntity)
    {
        if(calificacionEntity != null)
        {
            this.id = calificacionEntity.getId();
            this.puntaje = calificacionEntity.getPuntaje();
            this.comentario = calificacionEntity.getComentario();
            this.usuario = new UsuarioDTO(calificacionEntity.getUsuario());
            
        }
    }
    
    //Métodos -------------------------------------------------------------------------------
    
    public Long getId()
    {
        return id;
    }
    
    public double getPuntaje()
    {
        return puntaje;
    }
    
    public String getComentario()
    {
        return comentario;
    }
    
     public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }
    
    public void setId(Long id)
    {
        this.id = id;
    }
    
    public void setPuntaje(double puntaje)
    {
        this.puntaje = puntaje;
    }
    
    public void setComentario(String comentario)
    {
        this.comentario = comentario;
    }
    
    
    
   
    
    
    public CalificacionEntity toEntity()
    {
        CalificacionEntity calificacionEntity = new CalificacionEntity();
        calificacionEntity.setId(this.id);
        calificacionEntity.setPuntaje(this.puntaje);
        calificacionEntity.setComentario(this.comentario);
        
        return calificacionEntity;
    }
    
    
    
    
    
}
