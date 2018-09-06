    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.entities;

import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamStrategyValue;
import java.util.Date;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Mario Andrade
 */
@Entity
public class FestivalEntity  extends BaseEntity implements Serializable {

    @Temporal(TemporalType.DATE)
    //@PodamStrategyValue(DateStrategy.class)
    private Date fechaInicio;
    
    @Temporal(TemporalType.DATE)
    //@PodamStrategyValue(DateStrategy.class)
    private Date fechaFin;
    
//    @PodamExclude
//    @OneToMany(mappedBy = "festivales")
//    private List<CriticoEntity> criticos = new ArrayList<>();

//    @PodamExclude
//    @OneToMany(mappedBy = "festivales")
//    private List<TeatroEntity> teatros = new ArrayList<>();    
       
    private String nombre;
    private Integer duracion;
    private String patrocinador;
    private String ciudad;
    
    /**
     * Retorna la fecha de inicio del festival
     * @return fechaInicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

     /**
     * modifica la fecha de inicio del festival
     * @param fechaInicio fecha en la que comienza
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
     /**
     * Retorna la fecha fin del festival.
     * @return fechaFin
     */
    public Date getFechaFin() {
        return fechaFin;
    }

     /**
     * modifica la fecha fin del festival
     * @param fechaFin fecha en la que termina
     */
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }
    /**
     * Retorna el nombre del festival
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }
    /**
     * Modifica el nombre del festival
     * @param nombre nuevo nombre.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    /**
     * Retorna la duración del festival
     * @return duracion
     */
    public Integer getDuracion() {
        return duracion;
    }
    /**
     * Modifica la duracion del festival
     * @param duracion nueva duracion
     */
    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }
    /**
     * Retorna el nombre del patrocinador del festival
     * @return patrocinador 
     */
    public String getPatrocinador() {
        return patrocinador;
    }
    /**
     * Modifica el nombre del patrocinador del festival
     * @param patrocinador nuevo nombre del patrocinador
     */
    public void setPatrocinador(String patrocinador) {
        this.patrocinador = patrocinador;
    }
    
    /**
     * Retorna la ciudad en la que se va a realizar el festival
     * @return ciudad
     */
    public String getCiudad() {
        return ciudad;
    }
    
    /**
     * Modifica la ciudad en la que se va a realizar el festival
     * @param ciudad nueva ciudad del festival
     */
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    
}
