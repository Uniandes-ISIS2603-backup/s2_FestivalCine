/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.entities;

import java.io.Serializable;
import javax.persistence.Entity;

/**
 * Clase que representa una silla en la persistencia y permite su
 * serialización.
 * @author estudiante
 */

@Entity
public class SillaEntity extends BaseEntity implements Serializable {

    private Boolean disponible;
    private Boolean tipo;
    private Integer numero;
    
    
    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public Boolean getTipo() {
        return tipo;
    }

    public void setTipo(Boolean tipo) {
        this.tipo = tipo;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }
    

}
