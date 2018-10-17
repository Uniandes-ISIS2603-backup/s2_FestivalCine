/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.dtos;

import co.edu.uniandes.csw.festivalcine.entities.UsuarioEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
 

/**
 *
 * @author PAULA VELANDIA
 */
public class UsuarioDTO implements Serializable
{
    /**
     * Id del usuario
     */
    private Long id;
    
    /**
     * Nombres del usuario
     */
    private String nombres;
    
    /**
     * Apellidos del usuario
     */
    private String apellidos;
    
    /**
     * Documento de identificcación del usuario
     */
    private String identificacion;
    
    /**
     * Celular del usuario
     */
    private String celular;
    
    /**
     * Email del usuario
     */
    private String email;
    
    /**
     * Tipo de persona del usuario
     */
    private Integer tipoPersona;
    
    /**
     * Contraseña del usuario
     */
    private String password;
    
    /**
     * Constructor vacio
     */
    public UsuarioDTO()
    {
        
    }
    
    /**
     * Constructor que recibe un entity
     * @param usuarioEntity 
     */
    public UsuarioDTO(UsuarioEntity usuarioEntity)
    {
        if (usuarioEntity != null) 
        {
            this.id = usuarioEntity.getId();
            this.nombres = usuarioEntity.getNombres();
            this.apellidos = usuarioEntity.getApellidos();
            this.identificacion = usuarioEntity.getIdentificacion();
            this.celular =  usuarioEntity.getCelular();
            this.tipoPersona = usuarioEntity.getTipoPersona();
            this.password = usuarioEntity.getPassword();
            this.email = usuarioEntity.getEmail();
        }
    }

    /**
     * Metodo get Id
     * @return 
     */
    public Long getId()
    {
        return id;
    }
    
    /**
     * Metodo set Id
     * @param id 
     */
    public void setId(Long id) 
    {
        this.id = id;
    }
    
    /**
     * Metodo get nombres
     * @return 
     */
    public String getNombres() 
    {
        return nombres;
    }
    
    /**
     * Metodo set nombres
     * @param nombres 
     */
    public void setNombres(String nombres) 
    {
        this.nombres = nombres;
    }
    
    /**
     * Metodo get apellidos
     * @return 
     */
    public String getApellidos() 
    {
        return apellidos;
    }

    /**
     * Metodo set apellidos
     * @param apellidos 
     */
    public void setApellidos(String apellidos) 
    {
        this.apellidos = apellidos;
    }

    /**
     * Metodo get identificacion
     * @return 
     */
    public String getIdentificacion() 
    {
        return identificacion;
    }
    
    /**
     * Metodo set identificacion
     * @param identificacion 
     */
    public void setIdentificacion(String identificacion) 
    {
        this.identificacion = identificacion;
    }
    
    /**
     * Metodo get celular
     * @return 
     */
    public String getCelular() 
    {
        return celular;
    }
    
    /**
     * Metodo set celular
     * @param celular 
     */
    public void setCelular(String celular) 
    {
        this.celular = celular;
    }

    /**
     * Metodo set email
     * @return 
     */
    public String getEmail() 
    {
        return email;
    }

    /**
     * Metodo set email
     * @param email 
     */
    public void setEmail(String email) 
    {
        this.email = email;
    }

    /**
     * Metodo get tipo de persona
     * @return 
     */
    public Integer getTipoPersona() 
    {
        return tipoPersona;
    }
    
    /**
     * Metodo set tipo persona
     * @param tipoPersona 
     */
    public void setTipoPersona(Integer tipoPersona) 
    {
        this.tipoPersona = tipoPersona;
    }

    /**
     * Metodo get tipo persona
     * @return 
     */
    public String getPassword() 
    {
        return password;
    }
    
    /**
     * Metodo set password
     * @param password 
     */
    public void setPassword(String password) 
    {
        this.password = password;
    }
    
    /**
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
    public UsuarioEntity toEntity() 
    {
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId(this.id);
        usuarioEntity.setNombres(this.nombres);
        usuarioEntity.setApellidos(this.apellidos);
        usuarioEntity.setIdentificacion(this.identificacion);
        usuarioEntity.setCelular(this.celular);
        usuarioEntity.setTipoPersona(this.tipoPersona);
        usuarioEntity.setPassword( this.password);
        usuarioEntity.setEmail(this.email);
        return usuarioEntity;
    }
    
    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
}
