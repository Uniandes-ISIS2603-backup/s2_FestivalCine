/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import uk.co.jemos.podam.common.PodamExclude;


//IMPORTS

/**
 *
 * @author Cristian
 */
public class PeliculaEntity extends BaseEntity implements Serializable{
    
    private String nombre;
    private String director;
    private String creditos;
    private String pais;
    private int duracion;
    private double puntaje;
    
    // @PodamExclude
   // @OneToMany (mappedBy = "peliucla", cascade = CascadeType.ALL, orphanRemoval = true)
   // private List<CalificacionesEntity> calificaciones;
      @PodamExclude
    @OneToOne (mappedBy = "pelicula", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BandaAnuncioEntity> bandas;

    public List<BandaAnuncioEntity> getBandas() {
        return bandas;
    }

    public void setBandas(List<BandaAnuncioEntity> bandas) {
        this.bandas = bandas;
    }
    

    public String getNombre() {
        return nombre;
    }

    public String getDirector() {
        return director;
    }

    public String getCreditos() {
        return creditos;
    }

    public String getPais() {
        return pais;
    }

    public int getDuracion() {
        return duracion;
    }

    public double getPuntaje() {
        return puntaje;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setCreditos(String creditos) {
        this.creditos = creditos;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public void setPuntaje(double puntaje) {
        this.puntaje = puntaje;
    }
    
    
}
