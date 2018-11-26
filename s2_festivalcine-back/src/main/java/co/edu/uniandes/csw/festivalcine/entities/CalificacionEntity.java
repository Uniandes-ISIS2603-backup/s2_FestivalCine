/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author estudiante
 */
@Entity
public class CalificacionEntity extends BaseEntity implements Serializable
{
    private Double puntaje;
    
    private String comentario;
    
    @PodamExclude
    @ManyToOne(fetch = FetchType.LAZY)
    private UsuarioEntity usuario;
    
    @PodamExclude
    @ManyToOne(fetch = FetchType.LAZY)
    private PeliculaEntity pelicula;
    
    @PodamExclude
    @ManyToOne(fetch = FetchType.LAZY)
    private CriticoEntity critico;
    
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
    
    @Override
    public boolean equals(Object obj) 
    {
     CalificacionEntity obje = (CalificacionEntity) obj;
      return !super.id.equals(obje.id);    
    }

    @Override
    public int hashCode() 
    {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
        return hash;
    }
}
