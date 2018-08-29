/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.dtos;
/*
imports
*/


/**
 *
 * @author cc.cardenas
 */
public class BandaAnuncioDTO {
   private Long id;
   private int duracionMin;
   
  
   //Constructor//
  public BandaAnuncioDTO(){
      
  }
  /**
     * Conviertir Entity a DTO
     * (Crea un nuevo DTO con los valores que recibe en la entidad que viene de argumento.
     * @param bandaAnuncio: Es la entidad que se va a convertir a DTO 
     */
public BandaAnuncioDTO (BandaAnuncioEntity banda){
this.id= banda.getId();
this.duracionMin= banda.getDuracion();
}


/**
 * @return el id de la banda
 */
public long getId(){
    return id;
}

/**
 * @param id  se hace set del id de la banda
 */

public void setId(){
    this.id=id;
}

/**
 * @return la duracion de la banda en minutos
 */
public long getDuracion(){
    return duracionMin;
}

/**
 * @param integer de duracion se hace set de la duracion de la banda
 */

public void setDuracion(int duracion){
    this.duracionMin=duracion;
}

 /**
     * Convertir DTO a Entity
     * @return Un Entity con los valores del DTO 
     */
    public BandaAnuncioEntity toEntity() {
       BandaAnuncioEntity entity = new BandaAnuncioEntity();
        entity.setId(this.id);
        entity.setDuracion(this.duracionMin);

        
       
        return entity;
    }
}