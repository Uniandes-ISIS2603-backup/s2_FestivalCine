/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import uk.co.jemos.podam.common.PodamExclude;


//IMPORTS

/**
 *
 * @author Cristian
 */
@Entity
public class PeliculaEntity extends BaseEntity implements Serializable{
    
    private String nombre;
    private String director;
    private String creditos;
    private String pais;
    private Integer duracion;
    private Double puntaje;
    
    @PodamExclude
    @OneToMany (mappedBy = "pelicula", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CalificacionEntity> calificaciones;
    
     @PodamExclude
    @OneToOne(mappedBy = "pelicula", fetch = FetchType.LAZY)
    private BandaAnuncioEntity banda;
      
    @PodamExclude
    @ManyToMany(fetch = FetchType.LAZY)
    private List<CriticoEntity> criticos = new ArrayList();
    
    @PodamExclude
    @OneToMany(mappedBy = "pelicula", fetch = FetchType.LAZY)
    private List<FuncionEntity> funciones = new ArrayList();
    
    public void setCalificaciones(List<CalificacionEntity> calificaciones) {
        this.calificaciones = calificaciones;
    }

    public List<CalificacionEntity> getCalificaciones() {
        return calificaciones;
    }
    
    public BandaAnuncioEntity getBanda() {
        return banda;
    }
    
    public List<CriticoEntity> getCriticos()
    {
        return criticos;
    }
    
    public List<FuncionEntity> getFunciones() 
    {
        return funciones;
    }

    public void setBanda(BandaAnuncioEntity banda) {
        this.banda = banda;
    }
    
    public void setCriticos(List<CriticoEntity> criticos)
    {
        this.criticos = criticos;
    }
    
    public void setFunciones(List<FuncionEntity> funciones)
    {
        this.funciones = funciones;
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

    public Integer getDuracion() {
        return duracion;
    }

    public Double getPuntaje() {
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

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public void setPuntaje(Double puntaje) {
        this.puntaje = puntaje;
    }
    
    
}
