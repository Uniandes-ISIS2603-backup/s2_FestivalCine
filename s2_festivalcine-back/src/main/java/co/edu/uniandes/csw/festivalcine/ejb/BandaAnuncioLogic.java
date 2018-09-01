/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.ejb;
import co.edu.uniandes.csw.festivalcine.persistence.BandaAnuncioPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
/**
 *
 * @author Cristian
 */
@Stateless
public class BandaAnuncioLogic {
    private static final Logger LOGGER = Logger.getLogger(BandaAnuncioLogic.class.getName());
    
    @Inject
    private BandaAnuncioPersistence persistence;
    
    
}
