/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.entities;

import java.io.Serializable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Mario Andrade
 */
public class TeatroEntity extends BaseEntity implements Serializable{
    
//    @PodamExclude
//    @OneToMany(mappedBy = "teatros")
//    private List<FuncionEntity> funciones = new ArrayList<>();

//    @PodamExclude
//    @OneToMany(mappedBy = "teatros")
//    private List<SalaEntity> salas = new ArrayList<>(); 
    
 private String nombre;
 private String direccion;
 private Integer numSalasFest;

   
    /**
     * Retorna el nombre del teatro
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Modifica el nombre del teatro
     * @param nombre nuevo nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Retorna la direccion del teatro
     * @return direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Modifica la direccion del teatro
     * @param direccion nueva direccion
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Retorna el numero de salas del teatro que se usaran
     * para el festival
     * @return numSalasFest
     */
    public Integer getNumSalasFest() {
        return numSalasFest;
    }

    /**
     * Modifica el numero de salas del teatro usadas para el festival
     * @param numSalasFest nuevo numero de salas
     */
    public void setNumSalasFest(Integer numSalasFest) {
        this.numSalasFest = numSalasFest;
    }
 
 
 
 
 
    
}
