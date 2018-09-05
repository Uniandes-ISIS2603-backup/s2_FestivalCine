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

/**
 *
 * @author Andres Felipe Rodriguez Murillo
 */
@Entity
public class CriticoEntity extends BaseEntity implements Serializable
{    
    /**
     * Nombres del critico
     */
    private String nombres;
    
    
    /**
     * Apellidos del critico
     */
    private String apellidos;
    
    /**
     * Número de identificacion de cedula del critico
     */
    private String identificacion;
    
    /**
     * Número celular del critico
     */
    private String celular;
    
    /**
     * Correo electronico del critico
     */
    private String email;
    
    /**
     * tipo de persona del critico
     */
    private int tipoPersona;
    
    /**
     * NickName del critico
     */
    private String nickName;
    
    /**
     * Contraseña del critico
     */
    private String password;
    
    /**
     * Puntaje promedio de las calificaciones del critico
     */
    private double puntaje;
    
    /**
     * Credencial del critico
     */
    private String credencial;
    
    /*
    * Lista de calificaciones del critico
    */
    private List<CalificacionEntity> calificaciones = new ArrayList<CalificacionEntity>();
    
    /**
     * Lista de funciones del critico
     */
    private List funciones = new ArrayList();
    
    /**
     * Lista de peliculas del critico
     */
    private List peliculas = new ArrayList();

    
    /**
     * Retorna los nombres del critico
     * @return String con los nombres del critico
     */
    public String darNombres()
    {
        return nombres;
    }
    
    /**
     * Retorna los apellidos del critico
     * @return String con los apellidos del critico
     */
    public String darApellidos()
    {
        return apellidos;
    }
    
    /**
     * Retorna el número de identificación del critico
     * @return String con identificacion del critico
     */
    public String darIdentificacion()
    {
        return identificacion;
    }
    
    /**
     * Retorna el número celular del critico
     * @return String con el número de celular del critico
     */
    public String darCelular()
    {
        return celular;
    }
    
    /**
     * Retorna la dirección de correo electrónico del critico
     * @return String con el correo electrónico del critico
     */
    public String darEmail()
    {
        return email;
    }
    
    /**
     * Retorna el tipo de persona del critico
     * @return int con el tipo de persona del critico
     */
    public int darTipoPersona()
    {
        return tipoPersona;
    }
    
    /**
     * retorna el nickname (apodo) del critico
     * @return String con el nickname del critico
     */
    public String darNickName()
    {
        return nickName;
    }
    
    /**
     * Retorna la contaseña del critico
     * @return String con la contraseña del critico
     */
    public String darPassword()
    {
        return password;
    }
    
    /**
     * Retorna el puntaje promedio de calificaciones del critico
     * @return double con puntaje del critico
     */
    public double darPuntaje()
    {
        return puntaje;
    }
    
    /**
     * Retorna la credencial del critico
     * @return String con la credencial del critico
     */
    public String darCredencial()
    {
        return credencial;
    }
    
    /**
     * Devuelve la lista de calificaciones del critico
     * @return List con las calificaciones
     */
    public List<CalificacionEntity> darCalificaciones()
    {
        return calificaciones;
    }
    
    /**
     * Devuelve la lista de funciones del critico
     * @return List con las funciones
     */
    public List darFunciones()
    {
        return funciones;
    }
    
    /**
     * Devuelve la lista de peliculas del critico
     * @return List de peliculas
     */
    public List darPeliculas()
    {
        return peliculas;
    }
    
    public void setNombres(String nombres)
    {
        this.nombres = nombres;
    }
    
    public void setApellidos(String apellidos)
    {
        this.apellidos = apellidos;
    }
    
    public void setIdentificacion(String identificacion)
    {
        this.identificacion = identificacion;
    }
    
    public void setCelular(String celular)
    {
        this.celular = celular;
    }
    
    public void setEmail(String email)
    {
        this.email = email;
    }
    
    public void setTipoPersona(int tipoPersona)
    {
        this.tipoPersona = tipoPersona;
    }
    
    public void setNickName(String nickName)
    {
        this.nickName = nickName;
    }
    
    public void setPassword(String password)
    {
        this.password = password;
    }
    
    public void setPuntaje(double puntaje)
    {
        this.puntaje = puntaje;
    }
    
    public void setCredencial(String credencial)
    {
        this.credencial = credencial;
    }
    
    public void setCalificaciones(List<CalificacionEntity> calificaciones)
    {
        this.calificaciones = calificaciones;
    }
    
    public void setFunciones(List funciones)
    {
        this.funciones= funciones;
    }
    
    public void setPeliculas(List peliculas)
    {
        this.peliculas = peliculas;
    }
}
