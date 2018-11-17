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
import javax.persistence.OneToOne;
import uk.co.jemos.podam.common.*;
/**
 *
 * @author Cristian
 */
@Entity
public class BandaAnuncioEntity extends BaseEntity implements Serializable{
    
    
   private Integer duracion;
   
   @PodamExclude
   @OneToOne(fetch = FetchType.LAZY)
   private PeliculaEntity pelicula;
   
   public PeliculaEntity getPelicula()
   {
       return pelicula;
   }
   
   public void setPelicula(PeliculaEntity pelicula)
   {
       this.pelicula = pelicula;
   }
    
   public Integer getDuracion(){
       return this.duracion;
   }
   public void setDuracion(Integer duracion){
       this.duracion=duracion;
   }
   
   @Override
    public boolean equals(Object obj) 
    {
      return !super.equals(obj);    
    }

    @Override
    public int hashCode() 
    {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
        return hash;
    }
    
}
