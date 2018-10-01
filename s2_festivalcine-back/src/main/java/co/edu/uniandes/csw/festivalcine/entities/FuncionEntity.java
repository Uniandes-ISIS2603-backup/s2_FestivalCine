/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * Clase que representa una funcion en la persistencia y permite su
 * serialización.
 * @author María Juliana Moya
 */

@Entity
public class FuncionEntity extends BaseEntity implements Serializable {
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date horaInicio;
    
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date horaFin;
    
    private Integer precioBase;
    
    @PodamExclude
    @ManyToOne(fetch = FetchType.LAZY)
    private CriticoEntity critico;
    
    @PodamExclude
    @ManyToOne(fetch = FetchType.LAZY)
    private PeliculaEntity pelicula;
    
    @PodamExclude
    @ManyToOne(fetch = FetchType.LAZY)
    private SalaEntity sala;
    
    @PodamExclude  
    @ManyToOne(fetch = FetchType.LAZY)
    private TeatroEntity teatro;
    
    @PodamExclude
    @ManyToMany(mappedBy = "funciones", fetch = FetchType.LAZY)
    private Collection<ReservaEntity> reservas = new ArrayList<ReservaEntity>();    



    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Date getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Date horaFin) {
        this.horaFin = horaFin;
    }

    public Integer getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(Integer precioBase) {
        this.precioBase = precioBase;
    }

    public CriticoEntity getCritico() {
        return critico;
    }

    public void setCritico(CriticoEntity critico) {
        this.critico = critico;
    }
    
    public PeliculaEntity getPelicula() {
        return pelicula;
    }

    public void setPelicula(PeliculaEntity pelicula) {
        this.pelicula = pelicula;
    }

    public SalaEntity getSala() {
        return sala;
    }

    public void setSala(SalaEntity sala) {
        this.sala = sala;
    }
    
    public TeatroEntity getTeatro()
    {
        return teatro;
    }
    
    public void setTeatro(TeatroEntity teatro)
    {
        this.teatro = teatro;
    }
    
    public Collection<ReservaEntity> getReservas() {
        return reservas;
    }

    public void setReservas(Collection<ReservaEntity> reservas) {
        this.reservas = reservas;
    }
}
