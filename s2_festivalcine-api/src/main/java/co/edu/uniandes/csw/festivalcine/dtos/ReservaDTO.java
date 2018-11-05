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
 * @author PAULA VELANDIA
 */
public class ReservaDTO implements Serializable
{
    /**
     * id dela reserva
     */
    private Long id;
    
    /**
     * Tiene o no abono
     */
    private Boolean abono;
    
    /**
     * Cual es el descuento
     */
    private Integer descuento;
    
    /**
     * Precio total de la reserva
     */
    private Integer precioTotal;
    
    private UsuarioDTO usuario;

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }
    
    
    /**
     * Constructor de la clase
     */
    public ReservaDTO()
    {
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
     * Metodo set id
     * @param id 
     */
    public void setId(Long id) 
    {
        this.id = id;
    }

    /**
     * Metodo set abono
     * @param abono 
     */
    public void setAbono(Boolean abono) 
    {
        this.abono = abono;
    }
    
    /**
     * Metodo get descuento
     * @return 
     */
    public Integer getDescuento() 
    {
        return descuento;
    }
    
    /**
     * Metodo set descuento
     * @param descuento 
     */
    public void setDescuento(Integer descuento) 
    {
        this.descuento = descuento;
    }

    /**
     * Metodo get precio total
     * @return 
     */
    public Integer getPrecioTotal() 
    {
        return precioTotal;
    }
    
    /**
     * Metodo set precio Total
     * @param precioTotal 
     */
    public void setPrecioTotal(Integer precioTotal) 
    {
        this.precioTotal = precioTotal;
    }
    
     /**
     * Metodo get abono
     * @return the abono
     */
    public Boolean getAbono() 
    {
        return abono;
    }
    
    /**
     * Metodo constructor que recibe un Entity
     * @param reservaEntity 
     */
    public ReservaDTO(ReservaEntity reservaEntity) 
    {
        if (reservaEntity != null) 
        {
            this.id = reservaEntity.getId();
            this.abono = reservaEntity.getAbono();
            this.descuento = reservaEntity.getDescuento();
            this.precioTotal = reservaEntity.getPrecioTotal();
            if (reservaEntity.getUsuario() != null) {
                this.usuario = new UsuarioDTO(reservaEntity.getUsuario());
            } else {
                this.usuario = null;
            }
        }
    }
    
    /**
     * Metodo que convierte a entity
     * @return 
     */
    public ReservaEntity toEntity()
    {
        ReservaEntity reservaEntity = new ReservaEntity();
        reservaEntity.setId(this.getId());
        reservaEntity.setAbono(this.getAbono());
        reservaEntity.setDescuento(this.getDescuento());
        reservaEntity.setPrecioTotal(this.getPrecioTotal());
        if (this.usuario != null) {
            reservaEntity.setUsuario(this.usuario.toEntity());
        }
        return reservaEntity;
    }

   @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
