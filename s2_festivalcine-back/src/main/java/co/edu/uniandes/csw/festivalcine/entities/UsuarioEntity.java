/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author estudiante
 */
 @Entity
public class UsuarioEntity extends BaseEntity implements Serializable
{
     /**
      * Nombres del usuario
      */
    private String nombres;
    
    /**
     * Apellidos del usuario
     */
    private String apellidos;
    
    /**
     * Cedula del usuario
     */
    private String identificacion;
    
    /**
     * Celular del usuario
     */
    private String celular;
    
    /**
     * Email usuario
     */
    private String email;
    
    /**
     * Tipo de persona del usuario
     */
    private Integer tipoPersona;
    
    /**
     * Password del usuario
     */
    private String password;
    
    @PodamExclude
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<ReservaEntity> reservas= new ArrayList<ReservaEntity>();
    
    @PodamExclude
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<CalificacionEntity> calificaciones = new ArrayList();

    public List<ReservaEntity> getReservas() {
        return reservas;
    }

    public void setReservas(List<ReservaEntity> reservas) {
        this.reservas = reservas;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(Integer tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
     public void setCalificaciones(List<CalificacionEntity> calificaciones) {
        this.calificaciones = calificaciones;
    }

    public List<CalificacionEntity> getCalificaciones() {
        return calificaciones;
    }
}
