/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.dtos;
import co.edu.uniandes.csw.festivalcine.entities.CriticoEntity;
import java.io.Serializable;


/**
 *
 * @author estudiante
 */
public class CriticoDTO implements Serializable 
{
    //Atributos -------------------------------------------------------------------------
    private Long id;
    
    private String nombres;
    
    private String apellidos;
    
    private String identificacion;
    

    
    private String email;
    

    
    private String password;
    
    private Double puntaje;
    
    private String credencial;
    
    /**
     * Constructor por defecto
     */
    public CriticoDTO(){}
    /**
     * Convertir Entity a DTO (Crea un nuevoDTO con los valores que recibe en
     * la entidad que vienede argumento.)
     * @param criticoEntity Es la entidad que se va a convertir a DTO
     */
    public CriticoDTO(CriticoEntity criticoEntity)
    {
        if(criticoEntity != null)
        {
            this.id = criticoEntity.getId();
            this.nombres= criticoEntity.darNombres();
            this.apellidos = criticoEntity.darApellidos();
            this.identificacion = criticoEntity.darIdentificacion();
            
            this.email = criticoEntity.darEmail();
            
            this.password= criticoEntity.darPassword();
            this.puntaje = criticoEntity.darPuntaje();
            this.credencial = criticoEntity.darCredencial();
        }
    }
    
    /**
     * Devuelve el ID del critico
     * @return Long con ID
     */
    public Long getId()
    {
        return id;
    }
    
    /**
     * Devuelve los nombres del critico
     * @return String con los nombres
     */
    public String getNombres()
    {
        return nombres;
    }
    
    /**
     * Devuelve los apellidos del critico
     * @return String con los apellidos
     */
    public String getApellidos()
    {
        return apellidos;
    }
    
    /**
     * Devuelve la identificacion del critico
     * @return String con la identificacion
     */
    public String getIdentificacion()
    {
        return identificacion;
    }
    
    
    
    /**
     * Devuelve el email del critico
     * @return String con email
     */
    public String getEmail()
    {
        return email;
    } 
    
    
    
    /**
     * Devuelve la contraseña del critico
     * @return String con contraseña
     */
    public String getPassword()
    {
        return password;
    }
    
    /**
     * Devuelve el puntaje del critico
     * @return double con puntaje
     */
    public double getPuntaje()
    {
        return puntaje;
    }
    
    /**
     * Devuelve la credencial del critico
     * @return String con la credencial
     */
    public String getCredencial()
    {
        return credencial;
    }
    
    /**
     * Modifica el ID del critico
     * @param id el id nuevo
     */
    public void setId(Long id)
    {
        this.id = id;
    }
    
    /**
     * Modifica los nombres del critico
     * @param nombres los nuevos nombres
     */
    public void setNombres(String nombres)
    {
        this.nombres= nombres;
    }
    
    /**
     * Modifica los apellidos del critico
     * @param apellidos los nuevos apellidos
     */
    public void setApellidos(String apellidos)
    {
        this.apellidos = apellidos;
    }
    
    /**
     * Modifica el número de identificacion del critico
     * @param identificacion nueva identificacion
     */
    public void setIdentificacion(String identificacion)
    {
        this.identificacion = identificacion;
    }
    
    
    
    /**
     * Modifica el email del critico
     * @param email nuevo email
     */
    public void setEmail(String email)
    {
        this.email = email;
    }  
    
    

    /**
     * Modifica la contraseña del critico
     * @param password nueva contraseña
     */
    public void setPassword(String password)
    {
        this.password = password;
    }
    
    /**
     * Modifica el puntaje del critico
     * @param puntaje nuevo puntaje
     */
    public void setPuntaje(double puntaje)
    {
        this.puntaje = puntaje;
    }
    
    /**
     * Modifica la credencial del critico
     * @param credencial nueva credencial
     */
    public void setCredencial(String credencial)
    {
        this.credencial = credencial;
    }
    
    /**
     * Convertir DTO a Entity
     * @return Entity critico con los valores del CriticoDTO
     */
    public CriticoEntity toEntity()
    {
        CriticoEntity criticoEntity = new CriticoEntity();
        criticoEntity.setId(this.id);
        criticoEntity.setNombres(this.nombres);
        criticoEntity.setApellidos(this.apellidos);
        criticoEntity.setIdentificacion(this.identificacion);
        
        criticoEntity.setEmail(this.email);
        
        criticoEntity.setPassword(this.password);
        criticoEntity.setPuntaje((this.puntaje == null)?0:this.puntaje);
        criticoEntity.setCredencial(this.credencial);
        return criticoEntity;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
   
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
