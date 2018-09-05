/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.entities;

import java.io.Serializable;

/**
 *
 * @author estudiante
 */
public class CalificacionEntity extends BaseEntity implements Serializable
{
    private double puntaje;
    
    private String comentario;
    
    //private UsuarioEntity usuario;
    
    public double getPuntaje()
    {
        return puntaje;
    }
    
    public String getComentario()
    {
        return comentario;
    }
    
    //public UsuarioEntity getUsuario()
    //{
      //  return usuario;
    //}
    
    public void setPuntaje(double puntaje)
    {
        this.puntaje = puntaje;
    }
    
    public void setComentario(String comentario)
    {
        this.comentario = comentario;
    }
    
    //public void setUsuario(UsuarioEntity usuario)
    //{
      //  this.usuario = usuario;
    //}
}
