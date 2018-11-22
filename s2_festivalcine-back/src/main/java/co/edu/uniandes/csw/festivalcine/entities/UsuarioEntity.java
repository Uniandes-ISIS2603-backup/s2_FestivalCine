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
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author PAULA VELANDIA
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
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<ReservaEntity> reservas= new ArrayList<>();
    
    @PodamExclude
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<CalificacionEntity> calificaciones = new ArrayList<>();

    /**
     * Metodo que obtiene todas las reservas
     * @return 
     */
    public List<ReservaEntity> getReservas() {
        return reservas;
    }
    
    /**
     * Metodo que establece todas las reservas
     * @param reservas 
     */
    public void setReservas(List<ReservaEntity> reservas) {
        this.reservas = reservas;
    }
    
    /**
     * Metodo que devuelve los nombres
     * @return 
     */
    public String getNombres() {
        return nombres;
    }
    
    /**
     * Metodo que establece los nombres
     * @param nombres 
     */
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }
    
    /**
     * Metodo que obtiene los apellidos
     * @return 
     */
    public String getApellidos() {
        return apellidos;
    }
    
    /**
     * Metodo que establce los apellidos
     * @param apellidos 
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    
    /**
     * Metodo que obtiene una id
     * @return 
     */
    public String getIdentificacion() {
        return identificacion;
    }
    
    /**
     * Metodo que establece un id
     * @param identificacion 
     */
    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }
    
    /**
     * Metodo que obtiene un celular
     * @return 
     */
    public String getCelular() {
        return celular;
    }
    
    /**
     * Metodo que establece un celular
     * @param celular 
     */
    public void setCelular(String celular) {
        this.celular = celular;
    }
    
    /**
     * Metodo que obtiene un email
     * @return 
     */
    public String getEmail() {
        return email;
    }
    
    /**
     * Metodo que establece un email
     * @param email 
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     * Metodo que obtiene un tipo de persona
     * @return 
     */
    public Integer getTipoPersona() {
        return tipoPersona;
    }
    
    /**
     * Metodo que establece el tipo de persona
     * @param tipoPersona 
     */
    public void setTipoPersona(Integer tipoPersona) {
        this.tipoPersona = tipoPersona;
    }
    
    /**
     * Metodo que obtiene una contraseña
     * @return 
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * Metodo que establece una contraseña
     * @param password 
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * Metodo que establece calificaciones
     * @param calificaciones 
     */
     public void setCalificaciones(List<CalificacionEntity> calificaciones) {
        this.calificaciones = calificaciones;
    }
     
     /**
      * Metodo que obtiene calificaciones
      * @return 
      */
    public List<CalificacionEntity> getCalificaciones() {
        return calificaciones;
    }
    
   
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
