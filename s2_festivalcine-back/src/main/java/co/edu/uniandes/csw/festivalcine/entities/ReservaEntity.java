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
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author estudiante
 */
@Entity
public class ReservaEntity extends BaseEntity implements Serializable
{
    @Id
    private Long id;
    
    private Boolean abono;
    
    private Integer descuento;
    
    private Integer precioTotal;
    
    @PodamExclude
    @OneToMany(mappedBy = "reserva")
   private List<FuncionEntity> funciones = new ArrayList<FuncionEntity>();

    @PodamExclude
    @OneToMany(mappedBy = "reserva")
    private List<SillaEntity> sillas = new ArrayList<SillaEntity>();

    public List<SillaEntity> getSillas() {
        return sillas;
    }

    public void setSillas(List<SillaEntity> sillas) {
        this.sillas = sillas;
    }

    public List<FuncionEntity> getFunciones() {
        return funciones;
    }

    public void setFunciones(List<FuncionEntity> funciones) {
        this.funciones = funciones;
    }

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }
    
    
    @PodamExclude
    @ManyToOne
    private UsuarioEntity usuario;
    
    public ReservaEntity()
    {
        
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getAbono() {
        return abono;
    }

    public void setAbono(Boolean abono) {
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
}
