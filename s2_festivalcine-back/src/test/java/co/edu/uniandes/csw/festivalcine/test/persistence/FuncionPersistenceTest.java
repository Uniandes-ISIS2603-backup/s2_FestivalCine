/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.test.persistence;

import co.edu.uniandes.csw.festivalcine.entities.FuncionEntity;
import co.edu.uniandes.csw.festivalcine.persistence.FuncionPersistence;
import javax.inject.Inject;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;


/**
 *
 * @author estudiante
 */
@RunWith(Arquillian.class)

public class FuncionPersistenceTest {
    
    public FuncionPersistenceTest() {
    }
    
    
    @Inject
    private FuncionPersistence funcionPersistence;
    
    
    /**
     * Prueba de crear una función
     * 
     */
    
    @Test
    public void createFuncionTest(){
        PodamFactory factory = new PodamFactoryImpl();
        FuncionEntity newEntity = factory.manufacturePojo(FuncionEntity.class);
        FuncionEntity result = funcionPersistence.create(newEntity);
        
        
        Assert.assertNotNull(result);
        
        //FALTA
           
    }
}
