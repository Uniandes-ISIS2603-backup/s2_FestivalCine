/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.dtos;

import co.edu.uniandes.csw.festivalcine.entities.ReservaEntity;
import co.edu.uniandes.csw.festivalcine.entities.UsuarioEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
        super();
        reservas = new ArrayList<ReservaDTO>();
        calificaciones = new ArrayList<CalificacionDTO>();
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
        return usuarioEntity;
    }
    
    /**
     * Eliminé el método toString
     * @return 
     */
}
