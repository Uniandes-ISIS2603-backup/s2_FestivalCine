/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.dtos;

import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Clase que extiende de TeatroDTO para manejar las relaciones entre los
 * TeatroDTO y otros DTOs.
 *
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "id": number,
 *      "nombre": string,
 *      "direccion": string,
 *      "numSalasFest": number,
 *      "funciones": [{@link FuncionesDTO}],
 *      "salasAsignadas": [{@link salasDTO}]
 *   }
 * </pre> Por ejemplo un autor se representa asi:<br>
 * 
 * <pre>
 *    {  
 *      "id": "456173783",
 *      "nombre": "Teatro Sol",
 *      "direccion": "Cra 15 #14-2",
 *      "numSalasFest": "2"
 *      "funciones": [
 *          {
 *              "id": "321375873",
 *              "horaInicio": "00:00:00-05:00",
 *              "horaFin": "00:00:00-08:00",
 *              "precioBase": "10.000"
 *          },
 *          {
 *              "id": "12846546",
 *              "horaInicio": "00:00:00-09:00",
 *              "horaFin": "00:00:00-11:00",
 *              "precioBase": "8.000"
 *          }],
 *      "salas": [
 *          {
 *              "id": "15543456",
 *              "numSillasGene": "40",
 *              "numSillasPref": "10"
 *          },
 *          {
 *              "id": "8948746",
 *              "numSillasGene": "35",
 *              "numSillasPref": "10"
 *          }]
 *      }
 *      
 * <\pre>
 */
public class TeatroDetailDTO extends TeatroDTO implements Serializable
{
  // relación de cero a muchas funciones.
  private List<FuncionDTO> funciones;
  
  //relación de cero a muchas salas asignadas.
  private List<SalaDTO> salasAsignadas;
  
  public TeatroDetailDTO()
  {
      super();
  }
    /**
     * Devuelve la lista con las funciones del teatro.
     * @return funciones
     */
    public List<FuncionDTO> getFunciones() {
        return funciones;
    }
    
    /**
     * Devuelve la lista con las salas del teatro asignadas al festival.
     * @param salasAsignadas 
     */
    public List<SalasDTO> getSalasAsignadas() {
        return salasAsignadas;
    }

    /**
     * Modifica la lista con las funciones del teatro.
     * @param funciones 
     */
    public void setFunciones(List<FuncionDTO> funciones) {
        this.funciones = funciones;
    }

    /**
     * Modifica la lista con las salas del teatro asignadas a festival.
     * @param salasAsignadas 
     */
    public void setSalasAsignadas(List<SalasDTO> salasAsignadas) {
        this.salasAsignadas = salasAsignadas;
    }
    
       @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
  
  
}
