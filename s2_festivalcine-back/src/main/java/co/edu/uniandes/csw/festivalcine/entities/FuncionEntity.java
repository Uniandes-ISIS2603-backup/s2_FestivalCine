/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.entities;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author María Juliana Moya
 */
public class FuncionEntity extends BaseEntity implements Serializable {
    
    private Long id;
    private Date horaInicio;
    private Date horaFin;
    private Integer precioBase;
    
    
    
}
