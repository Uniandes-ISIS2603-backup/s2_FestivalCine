/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.dtos;

import co.edu.uniandes.csw.festivalcine.entities.FuncionEntity;
import co.edu.uniandes.csw.festivalcine.entities.ReservaEntity;
import co.edu.uniandes.csw.festivalcine.entities.SillaEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
/**
 *
 * @author PAULA VELANDIA
 */
public class ReservaDetailDTO extends ReservaDTO implements Serializable
{
    /**
     * Lista de tipo sillasDTO, contiene las sillas asociadas con la reserva
     */
    private List<SillaDTO> sillas;
    /**
     * Lista de tipo FuncionDTO, contiene las funciones asociadas con la reserva
     */
    private List<FuncionDTO> funciones;
    
  
    //Constructor ----------------------------------------------------------------------------
    
    /**
     * Constructor por defecto
     */
    public ReservaDetailDTO()
    {
    }
    
    /**
     * Constructor para transformar un Entity a un DTO
     * @param reservaEntity La entidad del critico para transformar a DTO.
     */
    public ReservaDetailDTO(ReservaEntity reservaEntity)
    {
        super(reservaEntity);
        if(reservaEntity != null)
        {
            if(reservaEntity.getSillas() != null)
            {
                sillas = new ArrayList<>();
                for(SillaEntity entitySilla : reservaEntity.getSillas())
                {
                    sillas.add(new SillaDTO(entitySilla));
                }
            }
            
            if(reservaEntity.getFunciones() != null)
            {
                funciones = new ArrayList<>();
                for(FuncionEntity entityFuncion : reservaEntity.getFunciones())
                {
                    funciones.add(new FuncionDTO(entityFuncion));
                }
            }
        }
    }
    
    /**
     * Transforma un DTO a un Entity
     * @return El DTO de la reserva para transformar a Entity
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
            reservaEntity.setSillas(sillaEntity);
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
    
    /**
     * Metodo get sillas
     * @return 
     */
     public List<SillaDTO> getSillas() 
    {
        return sillas;
    }
     
     /**
      * Metodo set sillas
      * @param sillas 
      */
    public void setSillas(List<SillaDTO> sillas) 
    {
        this.sillas = sillas;
    }
    
    /**
     * Metodo get funciones
     * @return 
     */
    public List getFunciones() 
    {
        return funciones;
    }
    
    /**
     * Metodo set funciones
     * @param funciones 
     */
    public void setFunciones(List funciones) {
        this.funciones = funciones;
    }
    
    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
