/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.test.persistence;

import co.edu.uniandes.csw.festivalcine.entities.FestivalEntity;
import co.edu.uniandes.csw.festivalcine.persistence.FestivalPersistence;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author estudiante
 */
@RunWith(Arquillian.class)
public class FestivalPersistenceTest {
    
    @Inject
    private FestivalPersistence festPersistence;
    
    @PersistenceContext
    private EntityManager em;
    
    public FestivalPersistenceTest() {
    }
    
    @Test
    public void createFestivalTest(){
        PodamFactory factory = new PodamFactoryImpl();
        
        FestivalEntity newEntity = factory.manufacturePojo(FestivalEntity.class);
        FestivalEntity result = festPersistence.create(newEntity);
        
        FestivalEntity entity = em.find(FestivalEntity.class, result.getId());

        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
        
    }
    
        @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(FestivalEntity.class.getPackage())
                .addPackage(FestivalPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
}

