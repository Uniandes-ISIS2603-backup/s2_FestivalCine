/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.dtos;

import co.edu.uniandes.csw.festivalcine.entities.ReservaEntity;
import co.edu.uniandes.csw.festivalcine.entities.UsuarioEntity;
import co.edu.uniandes.csw.festivalcine.dtos.ReservaDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author estudiante
 */
public class UsuarioDetailDTO extends UsuarioDTO implements Serializable 
{
    private List<ReservaDTO> reservas;

    
    
    public UsuarioDetailDTO()
    {
        
    }
    
     /**
     * Constructor para transformar un Entity a un DTO
     * @param usuarioEntity La entidad del critico para transformar a DTO.
     */
    public UsuarioDetailDTO(UsuarioEntity usuarioEntity)
    {
        super(usuarioEntity);
        if(usuarioEntity != null)
        {
            if(usuarioEntity.darReservas() != null)
            {
                reservas = new ArrayList<ReservaDTO>();
                for(ReservaEntity entityReserva : usuarioEntity.darReservas())
                {
                    reservas.add(new ReservaDTO(entityReserva));
                }
            }    
        }
    }
    
     /**
     * Transforma un DTO a un Entity
     * @return El DTO del critico para transformar a Entity
     */
    @Override
    public UsuarioEntity toEntity()
    {
        UsuarioEntity usuarioEntity = super.toEntity();
        if(reservas != null)
        {
            List<ReservaEntity> reservaEntity =  new ArrayList<>();
            for(ReservaDTO dtoReserva : reservas)
            {
                reservaEntity.add(dtoReserva.toEntity());
            }
            usuarioEntity.setReservas(reservaEntity);
        }
        return usuarioEntity;
    }
    
    public List<ReservaDTO> getReservas() {
        return reservas;
    }

    public void setReservas(List<ReservaDTO> reservas) {
        this.reservas = reservas;
    }
    
      @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
