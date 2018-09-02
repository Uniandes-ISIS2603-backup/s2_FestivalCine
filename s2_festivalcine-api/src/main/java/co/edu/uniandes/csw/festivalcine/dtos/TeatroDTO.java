/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.dtos;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Mario Andrade
 */
public class TeatroDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private String nombre;
    private String direccion;
    private Integer numSalasFest;
    
    
    /**
     * Constructor vacio
     */
    public TeatroDTO()
    {   
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
    
}
