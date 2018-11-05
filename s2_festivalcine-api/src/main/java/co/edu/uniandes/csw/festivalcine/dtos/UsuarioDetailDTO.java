/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.dtos;

import co.edu.uniandes.csw.festivalcine.entities.CalificacionEntity;
import co.edu.uniandes.csw.festivalcine.entities.ReservaEntity;
import co.edu.uniandes.csw.festivalcine.entities.UsuarioEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Paula Velandia
 */
public class UsuarioDetailDTO extends UsuarioDTO implements Serializable 
{
    /**
     * Lista de reservas
     */
    private List<ReservaDTO> reservas;
    
    /**
     * Lista de calificaciones
     */
    private List<CalificacionDTO> calificaciones;
    
    /**
     * Metodo constructir vacío
     */
    public UsuarioDetailDTO()
    {
    }
    
    /**
     * Metodo constructor UsuarioDetail
     * @param usuarioEntity 
     */
    public UsuarioDetailDTO(UsuarioEntity usuarioEntity)
    {
        super(usuarioEntity);
        reservas = new ArrayList<>();
        for (ReservaEntity entityreserva : usuarioEntity.getReservas()) 
        {
            reservas.add(new ReservaDTO(entityreserva));
        }
        calificaciones = new ArrayList<>();
        for (CalificacionEntity entitycalificacion : usuarioEntity.getCalificaciones()) 
        {
            calificaciones.add(new CalificacionDTO(entitycalificacion));
        }
    }
    
    /**
     * Metodo que trae las reservas de un usuario
     * @return 
     */
    public List<ReservaDTO> getReservas() 
    {
        return reservas;
    }
    
    /**
     * Método que establece las reservas
     * @param reservas 
     */
    public void setReservas(List<ReservaDTO> reservas) 
    {
        this.reservas = reservas;
    }
    
     /***
     * Método que devuelve una lista de calificaciones
     * @return 
     */
    public List<CalificacionDTO> getCalificaciones() 
    {
        return calificaciones;
    }
    
    /**
     * Método que establece una lista de calificaciones
     * @param calificaciones 
     */
    public void setCalificaciones(List<CalificacionDTO> calificaciones) 
    {
        this.calificaciones = calificaciones;
    }
    
    /**
     * Transformar un DTO a un Entity
     *
     * @return El DTO de la editorial para transformar a Entity
     */
    @Override
    public UsuarioEntity toEntity() 
    {
       UsuarioEntity usuarioEntity = super.toEntity();
        if (reservas != null) 
        {
            List<ReservaEntity> reservasEntity = new ArrayList<>();
            for (ReservaDTO dtoReserva : reservas) 
            {
                reservasEntity.add(dtoReserva.toEntity());
            }
            usuarioEntity.setReservas(reservasEntity);
        }
         if (calificaciones != null) 
        {
            List<CalificacionEntity> calificacionesEntity = new ArrayList<>();
            for (CalificacionDTO dtoCalificacion : calificaciones) 
            {
                calificacionesEntity.add(dtoCalificacion.toEntity());
            }
            usuarioEntity.setCalificaciones(calificacionesEntity);
        }
        return usuarioEntity;
    }
    
    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
