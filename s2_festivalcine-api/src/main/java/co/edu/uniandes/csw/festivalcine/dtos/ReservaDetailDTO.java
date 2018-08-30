/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.dtos;

import co.edu.uniandes.csw.festivalcine.entities.SillaEntity;
import co.edu.uniandes.csw.festivalcine.entities.FuncionEntity;
import co.edu.uniandes.csw.festivalcine.entities.ReservaEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
/**
 *
 * @author estudiante
 */
public class ReservaDetailDTO extends ReservaDTO implements Serializable
{
    /**
     * Lista de tipo CalificacionDTO, contiene las calificaciones asociadas con el critico
     */
    private List<SillaDTO> sillas;

   
    
    /**
     * Lista de tipo FuncionDTO, contiene las funciones asociadas con el critico
     */
    private List funciones;
  
    //Constructor ----------------------------------------------------------------------------
    
    /**
     * Constructor por defecto
     */
    public ReservaDetailDTO(){}
    
    /**
     * Constructor para transformar un Entity a un DTO
     * @param reservaEntity La entidad del critico para transformar a DTO.
     */
    public ReservaDetailDTO(ReservaEntity reservaEntity)
    {
        super(reservaEntity);
        if(reservaEntity != null)
        {
            if(reservaEntity.darSillas() != null)
            {
                sillas = new ArrayList<SillaDTO>();
                for(SillaEntity entitySilla : reservaEntity.darSillas())
                {
                    sillas.add(new SillaDTO(entitySilla));
                }
            }
            
            if(reservaEntity.darFunciones() != null)
            {
                funciones = new ArrayList<FuncionDTO>();
                for(FuncionDTO entityFuncion : reservaEntity.darFunciones())
                {
                    funciones.add(new FuncionDTO(entityFuncion));
                }
            }
        }
    }
    
    /**
     * Transforma un DTO a un Entity
     * @return El DTO del critico para transformar a Entity
     */
    @Override
    public ReservaEntity toEntity()
    {
        ReservaEntity reservaEntity = super.toEntity();
        if(sillas != null)
        {
            List<SillaEntity> sillaEntity =  new ArrayList<>();
            for(SillaDTO dtoSilla : sillas)
            {
                sillaEntity.add(dtoSilla.toEntity());
            }
            reservaEntity.setReserva(sillaEntity);
        }
        if(funciones != null)
        {
           List<FuncionEntity> funcionEntity = new ArrayList();
           for(FuncionDTO dtoFuncion : funciones)
           {
               funcionEntity.add(dtoFuncion.toEntity());
           }
            reservaEntity.setFunciones(funcionEntity);
        }
        return reservaEntity;
    }
    
     public List<SillaDTO> getSillas() {
        return sillas;
    }

    public void setSillas(List<SillaDTO> sillas) {
        this.sillas = sillas;
    }

    public List getFunciones() {
        return funciones;
    }

    public void setFunciones(List funciones) {
        this.funciones = funciones;
    }
    
    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
