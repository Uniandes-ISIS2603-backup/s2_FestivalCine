/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.entities;

import java.io.Serializable;
import javax.persistence.OneToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author estudiante
 */
public class CalificacionEntity extends BaseEntity implements Serializable
{
    private Double puntaje;
    
    private String comentario;
    
    @PodamExclude
    @OneToOne
    private UsuarioEntity usuario;
    
    public Double getPuntaje()
    {
        return puntaje;
    }
    
    public String getComentario()
    {
        return comentario;
    }
    
    public UsuarioEntity getUsuario()
    {
        return usuario;
    }
    
    public void setPuntaje(double puntaje)
    {
        this.puntaje = puntaje;
    }
    
    public void setComentario(String comentario)
    {
        this.comentario = comentario;
    }
    
    public void setUsuario(UsuarioEntity usuario)
    {
        this.usuario = usuario;
    }
}
