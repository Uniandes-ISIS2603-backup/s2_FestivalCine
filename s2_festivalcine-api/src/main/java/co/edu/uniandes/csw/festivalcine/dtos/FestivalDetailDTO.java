/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.dtos;

import co.edu.uniandes.csw.festivalcine.entities.CriticoEntity;
import co.edu.uniandes.csw.festivalcine.entities.FestivalEntity;
import co.edu.uniandes.csw.festivalcine.entities.TeatroEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Clase que extiende de FestivalDTO para manejar las relaciones entre los
 * FestivalDTO y otros DTOs.
 *
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "id": number,
 *      "nombre": string,
 *      "patrocinador": date,
 *      "duracion": number,
 *      "fechaInicio": date,
 *      "fechaFin": date,
 *      "ciudad": string,
 *      "teatros": [{@link TeatroDTO}],
 *      "criticos": [{@link CriticoDTO}]
 * </pre> Por ejemplo un festival se representa asi:<br>
 *
 * <pre>
 *   
 *   {
 *      "id": 84602460,
 *      "nombre": "Cine Moderno",
 *      "patrocinador": "Converse",
 *      "duracion": "10",
 *      "fechaInicio": "05082018",
 *      "fechaFin": "15082018",
 *      "ciudad": "Tunja",
 *      "teatros": [
 *          {
 *              "id": "4561783783",
 *              "nombre": "Teatro Sol",
 *              "direccion": "Cra 15 #14-2",
 *              "numSalasFest": "4"
 *          }],
 *      "criticos":
 *          {
 *              "nombres": "David",
 *              "Apellidos": "Gomez Pedraza",
 *              "identificacion": "9538657",
 *              "celular": "3138654602",
 *              "email": "dgomez@gmail.com",
 *              "tipoPersona": "0",
 *              "nickName": "davog",
 *              "password": "dave123",
 *              "puntaje": "0",
 *              "credencial": "Entusiasta de sotano",
 *              "id": "0000001"
 * 
 *          }],
 *    }
 * 
 * 
 *      
 * <\pre>
 *   }
 **/
public class FestivalDetailDTO extends FestivalDTO implements Serializable
{
    //Relación de cero a muchos teatros.
    private List<TeatroDTO> teatros;
    
    //Relación de cero a muchos criticos.
    private List<CriticoDTO> criticos;
    
    public FestivalDetailDTO()
    {
        
    }
    
    public FestivalDetailDTO(FestivalEntity festEntity)
    {
        super(festEntity);
        teatros = new ArrayList<TeatroDTO>();
        for (TeatroEntity entityTeatro : festEntity.getTeatros()) 
        {
            teatros.add(new TeatroDTO(entityTeatro));
        }
        criticos = new ArrayList<CriticoDTO>();
         for (CriticoEntity entityCritico : festEntity.getCriticos()) 
        {
            criticos.add(new CriticoDTO(entityCritico));
        }       
    }

    /**
     * Devuelve la lista con los teatros del festival.
     * @return teatros
     */
    public List<TeatroDTO> getTeatros() {
        return teatros;
    }

    /**
     * Devuelve la lista con los criticos del festival.
     * @return 
     */
    public List<CriticoDTO> getCriticos() {
        return criticos;
    }
    
    /**
     * Modifica la lista de teatros para el festival.
     * @param teatros 
     */
    public void setTeatros(List<TeatroDTO> teatros) {
        this.teatros = teatros;
    }
    
    /**
     * Modifica la lista de criticos para el festival.
     * @param criticos 
     */
    public void setCriticos(List<CriticoDTO> criticos) {
        this.criticos = criticos;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
        /**
     * Transformar un DTO a un Entity
     *
     * @return El DTO de la editorial para transformar a Entity
     */
    @Override
    public FestivalEntity toEntity() 
    {
       FestivalEntity festEntity = super.toEntity();
        if (teatros != null) 
        {
            List<TeatroEntity> teatroEntity = new ArrayList<>();
            for (TeatroDTO dtoTeatro : teatros) 
            {
                teatroEntity.add(dtoTeatro.toEntity());
            }
            festEntity.setTeatros(teatroEntity);
        }
        if (criticos != null) 
        {
            List<CriticoEntity> criticoEntity = new ArrayList<>();
            for (CriticoDTO dtoCritico : criticos) 
            {
                criticoEntity.add(dtoCritico.toEntity());
            }
            festEntity.setCriticos(criticoEntity);
        }
        return festEntity;
    }
    
}
