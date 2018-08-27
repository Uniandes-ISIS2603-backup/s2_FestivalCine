/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.uniandes.edu.csw.festivalcine.dtos;

/**
 *
 * @author estudiante
 */
public class ReservaDTO 
{
    private Long id;
    private boolean abono;
    private Integer descuento;
    private Integer precioTotal;
    
    public ReservaDTO()
    {
        id = null;
        abono = false;
        descuento = 0;
        precioTotal = 0;
    }
    
    public ReservaDTO ReservaDTO(ReservaEntity reserva) 
    {
    }
    
    
   public void toEntity()
   {
       
   }
}
