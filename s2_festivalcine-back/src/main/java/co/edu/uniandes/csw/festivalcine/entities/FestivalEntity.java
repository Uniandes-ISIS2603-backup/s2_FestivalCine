    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.entities;

import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Mario Andrade
 */
@Entity
public class FestivalEntity  extends BaseEntity implements Serializable {

   @Temporal(TemporalType.TIMESTAMP)
    //@PodamStrategyValue(DateStrategy.class)
    private Date fechaInicio;
    
 @Temporal(TemporalType.TIMESTAMP)
    //@PodamStrategyValue(DateStrategy.class)
    private Date fechaFin;
    
    @PodamExclude
    @ManyToMany(mappedBy = "festivales", fetch = FetchType.LAZY)
    private List<CriticoEntity> criticos = new ArrayList<>();

    @PodamExclude
    @ManyToMany(mappedBy = "festivales")
    private List<TeatroEntity> teatros = new ArrayList<>();    

       
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
    
    /**
     * Retorna la lista de criticos del festival.
     * @return criticos
     */
    public List<CriticoEntity> getCriticos() {
        return criticos;
    }
    /**
     * Modifica los criticos del festival.
     * @param criticos nuevos criticos.
     */
    public void setCriticos(List<CriticoEntity> criticos) {
        this.criticos = criticos;
    }
    /**
     * Retorna la lista de teatros del festival.
     * @return teatros
     */
    public List<TeatroEntity> getTeatros() {
        return teatros;
    }
    /**
     * Modifica los teatros del festival.
     * @param teatros nuevos teatros.
     */
    public void setTeatros(List<TeatroEntity> teatros) {
        this.teatros = teatros;
    }

    
}
