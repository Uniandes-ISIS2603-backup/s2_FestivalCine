/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author PAULA VELANDIA
 */
@Entity
public class ReservaEntity extends BaseEntity implements Serializable
{
    private Boolean abono;
    
    private Integer descuento;
    
    private Integer precioTotal;
    
    @PodamExclude
    @ManyToMany
    private List<FuncionEntity> funciones = new ArrayList<>();

    @PodamExclude
    @OneToMany(mappedBy = "reserva", fetch = FetchType.LAZY)
    private List<SillaEntity> sillas = new ArrayList<>();
    
    @PodamExclude
    @ManyToOne
    private UsuarioEntity usuario;
    
    /**
     * Metodo que obtiene sillas
     * @return 
     */
    public List<SillaEntity> getSillas() {
        return sillas;
    }
    
    /**
     * Metodo que establece sillas
     * @param sillas 
     */
    public void setSillas(List<SillaEntity> sillas) 
    {
        this.sillas = sillas;
    }
    
    /**
     * Metodo que obtiene funciones
     * @return 
     */
    public List<FuncionEntity> getFunciones() {
        return funciones;
    }
    
    /**
     * Metodo que establece funciones
     * @param funciones 
     */
    public void setFunciones(List<FuncionEntity> funciones) {
        this.funciones = funciones;
    }
    
    /**
     * Metodo que obtiene un usuario
     * @return 
     */
    public UsuarioEntity getUsuario() {
        return usuario;
    }
    
    /**
     * Metodo que establece un usuario
     * @param usuario 
     */
    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }
    
    /**
     * Metodo que obtiene un abono
     * @return 
     */
    public Boolean getAbono() {
        return abono;
    }
    
    /**
     * Metodo que establece un abono
     * @param abono 
     */
    public void setAbono(Boolean abono) {
        this.abono = abono;
    }
    
    /**
     * Metodo que obtiene un descuento
     * @return 
     */
    public Integer getDescuento() {
        return descuento;
    }
    
    /**
     * Metodo que establece un descuento
     * @param descuento 
     */
    public void setDescuento(Integer descuento) {
        this.descuento = descuento;
    }
    
    /**
     * Metodo que obtiene el precio total
     * @return 
     */
    public Integer getPrecioTotal() {
        return precioTotal;
    }
    
    /**
     * Metodo que establece el precio total
     * @param precioTotal 
     */
    public void setPrecioTotal(Integer precioTotal) {
        this.precioTotal = precioTotal;
    }
}
