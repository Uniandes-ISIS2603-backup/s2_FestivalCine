/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.uniandes.edu.csw.festivalcine.dtos;

/**
 *
 * @author estudiante
 */
public class UsuarioDTO 
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
    
    public void UsuarioDTO(Long pId, String pNombres, String pApellidos, String pIdentificacion, String pCelular, String pEmail,Integer pTipoPersona, String pNickname, String pPassword)
    {
        id = pId;
        nombres = pNombres;
        apellidos = pApellidos;
        identificacion = pIdentificacion;
        celular = pCelular;
        email = pEmail;
        tipoPersona = pTipoPersona;
        nickname = pNickname;
        password = pPassword;
    }
    
    public UsuarioDTO()
    {
        id = null;
        nombres="";
        apellidos="";
        identificacion="";
        celular="";
        email="";
        tipoPersona=0;
        nickname="";
        password="";
    }
    
    public void UsuarioDTO(UsuarioEntity usuario)
    {
        
    }
    
    public void toEntity()
    {
        
    }
}
