/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author María Juliana Moya
 */
public class FuncionEntity extends BaseEntity implements Serializable {
    
    private Long id;
    @Temporal(TemporalType.DATE)
    private Date horaInicio;
    
    @Temporal(TemporalType.DATE)
    private Date horaFin;
    
    private Integer precioBase;
    
    @PodamExclude
    @ManyToOne
    private CriticoEntity critico;

    
    
}
