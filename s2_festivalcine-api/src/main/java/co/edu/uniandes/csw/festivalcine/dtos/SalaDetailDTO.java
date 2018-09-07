/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.dtos;

import co.edu.uniandes.csw.festivalcine.entities.SalaEntity;
import co.edu.uniandes.csw.festivalcine.entities.SillaEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author María Juliana Moya
 */
public class SalaDetailDTO extends SalaDTO implements Serializable {
    /*
    * Esta lista de tipo SillaDTO contiene las sillas de esa sala
     */
    private List<SillaDTO> sillas;

    /**
     * Constructor por defecto
     */
    public SalaDetailDTO() {}

    /**
     * Constructor para transformar un Entity a un DTO
    
     * @param salaEntity La entidad de la sala para transformar a DTO.
     */
    public SalaDetailDTO(SalaEntity salaEntity) {
        super(salaEntity);
        if (salaEntity != null) {
            if (salaEntity.getSillas() != null) {
                sillas= new ArrayList<>();
                for (SillaEntity entitySilla : salaEntity.getSillas()) {
                  sillas.add(new SillaDTO(entitySilla));
               }
            }
        }
    }

    /**
     * Transformar un DTO a un Entity
     *
     * @return El DTO de la editorial para transformar a Entity
     */
    @Override
    public SalaEntity toEntity() {
        SalaEntity salaEntity = super.toEntity();
        if (sillas != null) {
           List <SillaEntity> sillasEntity = new ArrayList<>();
           for (SillaDTO dtoSilla : sillas) {
                sillasEntity.add(dtoSilla.toEntity());
            }
            salaEntity.setSillas(sillasEntity);
        }
        return salaEntity;
    }

    /**
     * Devuelve la lista de sillas de la sala
     *
     * @return las sillas
     */
    public List<SillaDTO> getSillas() {
        return sillas;
    }

    /**
     * Modifica la lista de sillas de la sala
     * @param sillas the sillas to set
     */
    public void setSillas(List<SillaDTO> sillas) {
        this.sillas = sillas;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
