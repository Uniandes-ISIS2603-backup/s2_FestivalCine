/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.dtos;

import co.edu.uniandes.csw.festivalcine.entities.CalificacionEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author estudiante
 */
public class CalificacionDTO implements Serializable
{
    private Long id;
    
    private double puntaje;
    
    private String comentario;
    
    //private UsuarioDTO usuario;
    
    //Constructor ----------------------------------------------------------------------------
    
    public CalificacionDTO(){}
    
    public CalificacionDTO(CalificacionEntity calificacionEntity)
    {
        if(calificacionEntity != null)
        {
            this.id = calificacionEntity.getId();
            this.puntaje = calificacionEntity.getPuntaje();
            this.comentario = calificacionEntity.getComentario();
            //this.UsuarioDTO = new UsuarioDTO(calificacionEntity.getUsuario());
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
    
    //public UsuarioDTO getUsuarioDTO()
    //{
      //  return usuario;
    //}
    
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
    
    //public void setUsuarioDTO(UsuarioDTO usuario)
    //{
       // this.usuario = usuario;
    //}
    
    public CalificacionEntity toEntity()
    {
        CalificacionEntity calificacionEntity = new CalificacionEntity();
        calificacionEntity.setId(this.id);
        calificacionEntity.setPuntaje(this.puntaje);
        calificacionEntity.setComentario(this.comentario);
        //calificacionEntity.setUsuario(usuario.toEntity());
        return calificacionEntity;
    }
    
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
