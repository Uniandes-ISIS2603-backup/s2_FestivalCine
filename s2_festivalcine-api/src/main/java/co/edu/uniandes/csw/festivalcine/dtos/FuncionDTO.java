
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.dtos;
import co.edu.uniandes.csw.festivalcine.dtos.CriticoDTO;
import co.edu.uniandes.csw.festivalcine.entities.FuncionEntity;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 
 * @author María Juliana Moya
 */
public class FuncionDTO implements Serializable{
    
    //Atributos -------------------------------------------------------------------------
    private Long id;
   
    /*
    * Fecha de la función
    */
    private Date fecha;
    
   /*
    * Hora de la función
    */
    private Integer hora;
    
    
   /*
    * Precio base de la función
    */
    private Integer precioBase;
    
    
    /*
    * Relación a una sala
    * dado que esta tiene cardinalidad 1.
     */

    private SalaDTO sala;
    
    /*
    * Relación a una película  
    * dado que esta tiene cardinalidad 1.
     */

    private PeliculaDTO pelicula;
    
    /*
    * Relación a un crítico 
    * dado que esta tiene cardinalidad 1.
     */

    private CriticoDTO critico;
    
   
    //Constructor ----------------------------------------------------------------------------
    
    /**
     * Constructor por defecto
     */
    public FuncionDTO(){}
    /**
     * Convertir Entity a DTO (Crea un nuevoDTO con los valores que recibe en
     * la entidad que vienede argumento.)
     * @param funcionEntity: es la entidad que se va a convertir a DTO
     */
    public FuncionDTO(FuncionEntity funcionEntity)
    {
        if(funcionEntity != null)
        {
           this.id = funcionEntity.getId();
           this.fecha = funcionEntity.getFecha();
           this.hora = funcionEntity.getHora();
           this.precioBase = funcionEntity.getPrecioBase();
           
           if (funcionEntity.getCritico() != null) {
                this.critico = new CriticoDTO(funcionEntity.getCritico());
           } else {
                this.critico = null;
           }
           
           if (funcionEntity.getSala() != null) {
                this.sala = new SalaDTO(funcionEntity.getSala());
           } else {
                this.sala = null;
           }
           
           if (funcionEntity.getPelicula() != null) {
                this.pelicula = new PeliculaDTO(funcionEntity.getPelicula());
           } else {
                this.pelicula = null;
           }
           
       }
    }
   
    
    //Métodos -------------------------------------------------------------------------------
    
    /**
     * Devuelve el ID de de la funcion
     * @return Long 
     */
    public Long getId()
    {
        return id;
    }
    
    /**
     * Devuelve la fecha de la función
     * @return Date 
     */
    public Date getFecha()
    {
        return fecha;
    }
    
    /**
     * Devuelve la hora de la función
     * @return Date 
     */
    public Integer getHora()
    {
        return hora;
    }
    
     /**
     * Devuelve el precio base de la función
     * @return Integer
     */
    public Integer getPrecioBase()
    {
        return precioBase;
    }
    
     /**
     * Devuelve la sala en la que se presentará la función
     * @return SalaDTO
     */
    public SalaDTO getSala()
    {
        return sala;
    }
    
     /**
     * Devuelve la pelicula que se presentará en la función
     * @return SalaDTO
     */
    public PeliculaDTO getPelicula()
    {
        return pelicula;
    }
    
     /**
     * Devuelve el crítico asignado en la función
     * @return CriticoDTO
     */
    public CriticoDTO getCritico()
   {
        return critico;
   }
      
    /**
     * Modifica el ID de la función
     * @param id el id nuevo
     */
    public void setId(Long id)
    {
        this.id = id;
    }
    
    /**
     * Modifica la fecha de la función
     * @param fecha
     */
    public void setFecha(Date fecha)
    {
        this.fecha= fecha;
    }
    
        /**
     * Modifica la hora de la función
     * @param hora
     */
    public void setHora(Integer hora)
    {
        this.hora= hora;
    }
    
     /**
     * Modifica el precio base de la función
     * @param precioBase 
     */
    public void setPrecioBase(Integer precioBase)
    {
        this.precioBase = precioBase;
    }
    
     /**
     * Modifica la sala de la función
     * @param sala
     */
    public void setSala(SalaDTO sala)
    {
        this.sala = sala;
    }
       
     /**
     * Modifica la sala de la función
     * @param pelicula PeliculaDTO 
     */
    public void setPelicula(PeliculaDTO pelicula)
    {
        this.pelicula = pelicula;
    }
    
    /**
     * Modifica el crítico de la función
     * @param critico CriticoDTO 
     */
    public void setCritico(CriticoDTO critico)
    {
        this.critico = critico;
    }
   
    /**
     * Convertir DTO a Entity
     * @return Entity funcion con los valores de FuncionDTO
     */
    public FuncionEntity toEntity()
   {
      FuncionEntity funcionEntity = new FuncionEntity();
      funcionEntity.setId(this.id);
      funcionEntity.setFecha(this.fecha);
      funcionEntity.setHora(this.hora);
      funcionEntity.setPrecioBase(this.precioBase);
              
      if (this.critico != null) {
            funcionEntity.setCritico(this.critico.toEntity());
      }
      if (this.sala != null) {
            funcionEntity.setSala(this.sala.toEntity());
      }
      
     if (this.pelicula != null) {
          funcionEntity.setCritico(this.critico.toEntity());
     }
      return funcionEntity ;
    }
    
    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}

