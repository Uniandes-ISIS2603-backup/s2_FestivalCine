/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * Clase que representa una sala en la persistencia y permite su
 * serialización.
 * @author María Juliana Moya
 */

@Entity
public class SalaEntity extends BaseEntity implements Serializable {
    
    private Integer numero;
    
    @PodamExclude
    @OneToMany(mappedBy = "sala", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SillaEntity> sillas = new ArrayList<>();
    
    @PodamExclude
    @OneToMany(mappedBy = "sala", fetch = FetchType.LAZY)
    private List<FuncionEntity> funciones;
    
    @PodamExclude
    @ManyToOne(fetch =FetchType.LAZY)
    private TeatroEntity teatro;
    
    
    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }
    public List<SillaEntity> getSillas() {
        return sillas;
    }

    public void setSillas(List<SillaEntity> sillas) {
        this.sillas = sillas;
    }
    
    public void setFuncion(List<FuncionEntity> funciones) {
        this.funciones = funciones;
    }

    public void setTeatro(TeatroEntity teatro) {
        this.teatro = teatro;
    }

    public List<FuncionEntity> getFuncion() {
        return funciones;
    }

    public TeatroEntity getTeatro() {
        return teatro;
    }
    
    public boolean equals(SalaEntity obj) 
    {
      return !super.id.equals(obj.id);    
    }

    @Override
    public int hashCode() 
    {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
        return hash;
    }
}
