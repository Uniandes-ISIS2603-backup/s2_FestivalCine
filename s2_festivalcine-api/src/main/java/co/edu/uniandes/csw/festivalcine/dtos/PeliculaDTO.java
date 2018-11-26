/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.dtos;

import co.edu.uniandes.csw.festivalcine.entities.PeliculaEntity;
import java.io.Serializable;

/**
 *
 * @author cc.cardenas
 */
public class PeliculaDTO implements Serializable{
    private Long id;
    private String nombre;
    private String director;
    private String creditos;
    private String pais;
    private Integer duracion;
    private Double puntaje;
    private String trailer;
    private String poster;

    
    
    
    
    public PeliculaDTO(){
        
    }
    
    public PeliculaDTO(PeliculaEntity pelicula)
    {
        if(pelicula != null)
        {
            this.id = pelicula.getId();
            this.nombre= pelicula.getNombre();
            this.director= pelicula.getDirector();
            this.creditos= pelicula.getCreditos();
            this.pais=pelicula.getPais();
            this.duracion=pelicula.getDuracion();
            this.puntaje=pelicula.getPuntaje();
            this.trailer = pelicula.getTrailer();
            this.poster = pelicula.getPoster();
        }
               
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
    
     /**
     * Convertir DTO a Entity
     * @return Un Entity con los valores del DTO 
     */
    public PeliculaEntity toEntity() {
       PeliculaEntity entity = new PeliculaEntity();
       
       entity.setId(this.id);
       entity.setNombre(this.nombre);
       entity.setDirector(this.director);
       entity.setCreditos(this.creditos);
       entity.setDuracion(this.duracion);
       entity.setPais(this.pais);
       entity.setPuntaje(this.puntaje);

        
       
        return entity;
    }
}
