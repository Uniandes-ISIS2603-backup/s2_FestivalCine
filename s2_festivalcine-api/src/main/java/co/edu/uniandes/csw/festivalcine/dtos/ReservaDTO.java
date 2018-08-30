/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.dtos;
import co.edu.uniandes.csw.festivalcine.entities.ReservaEntity;

import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author estudiante
 */
public class ReservaDTO implements Serializable
{
    private Long id;
    private boolean abono;
    private Integer descuento;
    private Integer precioTotal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isAbono() {
        return abono;
    }

    public void setAbono(boolean abono) {
        this.abono = abono;
    }

    public Integer getDescuento() {
        return descuento;
    }

    public void setDescuento(Integer descuento) {
        this.descuento = descuento;
    }

    public Integer getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(Integer precioTotal) {
        this.precioTotal = precioTotal;
    }
  
    
    public ReservaDTO()
    {
    }
    
    public ReservaDTO(ReservaEntity reservaEntity) 
    {
        if (reservaEntity != null) 
        {
            this.id = reservaEntity.getId();
            this.abono = reservaEntity.getAbono();
            this.descuento = reservaEntity.getDescuento();
            this.precioTotal = reservaEntity.getPrecioTotal();
        }
    }
    public ReservaEntity toEntity() {
        ReservaEntity reservaEntity = new ReservaEntity();
        reservaEntity.setId(this.id);
        reservaEntity.setAbono(this.abono);
        reservaEntity.setDescuento(this.descuento);
        reservaEntity.setPreciototal(this.precioTotal);
        return reservaEntity;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
