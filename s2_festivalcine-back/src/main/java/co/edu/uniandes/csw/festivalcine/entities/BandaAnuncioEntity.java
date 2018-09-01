/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.entities;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;

import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import uk.co.jemos.podam.common.*;
/**
 *
 * @author Cristian
 */
@Entity
public class BandaAnuncioEntity extends BaseEntity implements Serializable{
    
    
    private int duracion;
    
   public int getDuracion(){
       return this.duracion;
   }
   public void setDuracion(int duracion){
       this.duracion=duracion;
   }
    
}
