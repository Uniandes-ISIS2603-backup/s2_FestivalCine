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
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * Clase que representa una sala en la persistencia y permite su
 * serialización.
 * @author María Juliana Moya
 */

@Entity
public class SalaEntity extends BaseEntity implements Serializable {

    private Integer numSillasGene;
    private Integer numSillasPref;
    private Integer numero;
    
    @PodamExclude
    @OneToMany(mappedBy = "sala")
    private List<SillaEntity> sillas = new ArrayList<SillaEntity>();
    

    public Integer getNumSillasGene() {
        return numSillasGene;
    }

    public void setNumSillasGene(Integer numSillasGene) {
        this.numSillasGene = numSillasGene;
    }

    public Integer getNumSillasPref() {
        return numSillasPref;
    }

    public void setNumSillasPref(Integer numSillasPref) {
        this.numSillasPref = numSillasPref;
    }
    
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
    

    

    
}
