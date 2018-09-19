/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.dtos;

import co.edu.uniandes.csw.festivalcine.entities.FestivalEntity;
import co.edu.uniandes.csw.festivalcine.entities.TeatroEntity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Mario Andrade
 */
public class FestivalDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nombre;
    private String patrocinador;
    private Integer duracion;
    private Date fechaInicio;
    private Date fechaFin;
    private String ciudad;
    
    /**
     * Constructor vacio
     */
    public FestivalDTO()
    {
    }
    
    /**
     * Constructor que recibe un entity
     * @param festivalEntity 
     */
    public FestivalDTO(FestivalEntity newFest)
    {
        if(newFest != null)
        {
            this.id = newFest.getId();
            this.ciudad = newFest.getCiudad();
            this.duracion = newFest.getDuracion();
            this.fechaFin = newFest.getFechaFin();
            this.fechaInicio = newFest.getFechaInicio();
            this.patrocinador = newFest.getPatrocinador();
            this.nombre = newFest.getNombre();
        }
    }

    /**
     * Devuelve el id del festival.
     * @return id
     */
    public Long getId() {
        return id;
    }
    
    /**
     * Devuelve el nombre del festival.
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }
    
    /**
     * Devuelve el patrocinador del festival.
     * @return patrocionador
     */
    public String getPatrocinador() {
        return patrocinador;
    }
    
    /**
     * Devuelve la duración del festival.
     * @return duración
     */
    public Integer getDuracion() {
        return duracion;
    }
    
    /**
     * Devuelve la fecha de inicio del festival.
     * @return fechaInicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }
    
    public Date getFechaFin() {
        return fechaFin;
    } 
    
    /**
     * Devuelve el nombre de la ciudad en la que se realiza el festival.
     * @return ciudad
     */
    public String getCiudad() {
        return ciudad;
    }
    
    /**
     * Establece el id del festival.
     * @param id 
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Establece el nombre del festival
     * @param nombre 
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

   /**
    * Establece el patrocinador.
    * @param patrocinador 
    */
    public void setPatrocinador(String patrocinador) {
        this.patrocinador = patrocinador;
    }

    /**
     * Establece la duracion del festival.
     * @param duracion 
     */
    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    /**
     * Establece la fecha de inicio del festival.
     * @param fechaInicio 
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * Establece la fecha de fin del festival.
     * @param fechaFin 
     */
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * Establece el nombre de la ciudad del festival.
     * @param ciudad 
     */
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
        
    /**
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
    public FestivalEntity toEntity()
    {
        FestivalEntity festivalEntity = new FestivalEntity();
        festivalEntity.setId(this.id);
        festivalEntity.setCiudad(this.ciudad);
        festivalEntity.setDuracion(this.duracion);
        festivalEntity.setFechaFin(this.fechaFin);
        festivalEntity.setFechaInicio(this.fechaInicio);
        festivalEntity.setNombre(this.nombre);
        festivalEntity.setPatrocinador(this.patrocinador);
        
        return festivalEntity;
        
    }
}
