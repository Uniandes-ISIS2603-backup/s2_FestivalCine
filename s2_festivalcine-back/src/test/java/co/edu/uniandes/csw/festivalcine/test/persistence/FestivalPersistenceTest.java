/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.test.persistence;

import co.edu.uniandes.csw.festivalcine.entities.FestivalEntity;
import co.edu.uniandes.csw.festivalcine.persistence.FestivalPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Mario Andrade
 */
@RunWith(Arquillian.class)
public class FestivalPersistenceTest {
    
    @Inject
    private FestivalPersistence festPersistence;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    UserTransaction utx;
    
    private List<FestivalEntity> data = new ArrayList<FestivalEntity>();
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(FestivalEntity.class.getPackage())
                .addPackage(FestivalPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    /**
     * Configuración inicial de la prueba.
     */
    @Before
    public void configTest() {
        try {
            utx.begin();
            em.joinTransaction();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
    
    
    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        em.createQuery("delete from FestivalEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {

            FestivalEntity entity = factory.manufacturePojo(FestivalEntity.class);

            em.persist(entity);

            data.add(entity);
        }
    }
    
    @Test
    public void createFestivalTest(){
        PodamFactory factory = new PodamFactoryImpl();
        
        FestivalEntity newEntity = factory.manufacturePojo(FestivalEntity.class);
        FestivalEntity result = festPersistence.create(newEntity);
        
        FestivalEntity entity = em.find(FestivalEntity.class, result.getId());

        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
        
    }
    
        /**
     * Prueba para consultar la lista de Festivales.
     */
    @Test
    public void getFestivalsTest() {
        List<FestivalEntity> list =festPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for(FestivalEntity ent : list) {
            boolean found = false;
            for (FestivalEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
        /**
     * Prueba para consultar un Festival.
     */
    @Test
    public void getFestivalTest() {
       FestivalEntity entity = data.get(0);
        FestivalEntity newEntity = festPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
    }
    
    /**
     * Prueba para eliminar una Festival.
     */
    @Test
    public void deleteFestivalTest() {
        FestivalEntity entity = data.get(0);
        festPersistence.delete(entity.getId());
        FestivalEntity deleted = em.find(FestivalEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar una Festival.
     */
    @Test
    public void updateFestivalTest() {
        FestivalEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        FestivalEntity newEntity = factory.manufacturePojo(FestivalEntity.class);

        newEntity.setId(entity.getId());

        festPersistence.update(newEntity);

        FestivalEntity resp = em.find(FestivalEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getNombre(), resp.getNombre());
    }

}

