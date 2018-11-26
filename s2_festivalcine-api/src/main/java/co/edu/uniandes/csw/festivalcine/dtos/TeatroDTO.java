/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.dtos;

import co.edu.uniandes.csw.festivalcine.entities.TeatroEntity;
import java.io.Serializable;



import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Mario Andrade
 */
public class TeatroDTO implements Serializable {

        
    /**
     * Constructor vacio
     */
    public TeatroDTO(){   
    }
    
    
    private Long id;
    private String nombre;
    private String direccion;
    private Integer numSalasFest;
    

    
    /**
     * Constructor que recibe un entity
     * @param newTeatro 
     */
    public TeatroDTO(TeatroEntity newTeatro)
    {
        if(newTeatro != null)
        {
            this.id = newTeatro.getId();
            this.direccion = newTeatro.getDireccion();
            this.nombre = newTeatro.getNombre();
            this.numSalasFest = newTeatro.getNumSalasFest();
        }
    }
    
    /**
     * Devuelve el nombre del teatro.
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Devuelve la dirección del teatro.
     * @return direccion
     */
    public String getDireccion() {
        return direccion;
    }
    /**
     * Devuelve el numero de salas del teatro.
     * @return numSalasFest
     */
    public Integer getNumSalasFest() {
        return numSalasFest;
    }
    /**
     * Devuelve el id del teatro.
     * @return 
     */
    public Long getId() {
        return id;
    }
    /**
     * Establece el id del teatro.
     * @param id 
     */
    public void setId(Long id) {
        this.id = id;
    }
    /**
     * Establece el nombre del teatro.
     * @param nombre 
     */
        public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    /**
     * Establece la dirección del teatro.
     * @param direccion
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    /**
     * Establece el numero de salas del teatro.
     * @param numSalasFest 
     */
    public void setNumSalasFest(Integer numSalasFest) {
        this.numSalasFest = numSalasFest;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
    /**
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
    public TeatroEntity toEntity()
    {
        TeatroEntity teatro = new TeatroEntity();
        
        teatro.setNombre(this.nombre);
        teatro.setDireccion(this.direccion);
        teatro.setId(this.id);
        teatro.setNumSalasFest(this.numSalasFest);
        
        return teatro;
    }
}
