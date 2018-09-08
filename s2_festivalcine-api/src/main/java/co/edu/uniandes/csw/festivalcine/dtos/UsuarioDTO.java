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
 * @author estudiante
 */
public class UsuarioDTO implements Serializable
{
    
    private Long id;
    private String nombres;
    private String apellidos;
    private String identificacion;
    private String celular;
    private String email;
    private Integer tipoPersona;
    private String nickname;
    private String password;
    
    public UsuarioDTO()
    {
        
    }
    
    public UsuarioDTO(UsuarioEntity usuarioEntity)
    {
        if (usuarioEntity != null) {
            this.id = usuarioEntity.getId();
            this.nombres = usuarioEntity.getNombres();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
    public UsuarioEntity toEntity() {
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId(this.id);
        usuarioEntity.setNombres(this.nombres);
        return usuarioEntity;
    }
  
    
    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
