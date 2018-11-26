/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.test.persistence;

import co.edu.uniandes.csw.festivalcine.entities.PeliculaEntity;
import co.edu.uniandes.csw.festivalcine.persistence.PeliculaPersistence;
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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;


/**
 *
 * @author cc.cardenas
 */
@RunWith(Arquillian.class)
public class PeliculaPersistenceTest {
    
    @Inject
    private PeliculaPersistence persistence ;
    
    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;
    
    private List<PeliculaEntity> data = new ArrayList<PeliculaEntity>();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(PeliculaEntity.class.getPackage())
                .addPackage(PeliculaPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    @Before
    public void configTest()
    {
        try
        {
            utx.begin();
            em.joinTransaction();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e) 
        {
            e.printStackTrace();
            try
            {
                utx.rollback();
            } catch(Exception e1)
            {
                e1.printStackTrace();
            }
        }
    }
    
    private void clearData()
    {
        em.createQuery("delete from PeliculaEntity").executeUpdate();
    }
    
    private void insertData()
    {
        PodamFactory factory = new PodamFactoryImpl();
        for(int i = 0; i < 3; i++)
        {
            PeliculaEntity entity = factory.manufacturePojo(PeliculaEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
    
    
    /**
     * Prueba de crear una Pelicula
     * 
     */
    
    @Test
    public void createPeliculaTest(){
        PodamFactory factory = new PodamFactoryImpl();
        PeliculaEntity newEntity = factory.manufacturePojo(PeliculaEntity.class);
        PeliculaEntity result =persistence.create(newEntity);
        
        
        Assert.assertNotNull(result);
        
        PeliculaEntity entity = em.find(PeliculaEntity.class, result.getId());

        Assert.assertEquals(newEntity.getId(), entity.getId());          
    }
    
     /**
     * Prueba para consultar la lista de Pelicula.
     */
    @Test
    public void getPeliculasTest() {
        List<PeliculaEntity> list = persistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (PeliculaEntity ent : list) {
            boolean found = false;
            for (PeliculaEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar una Pelicula
     */
    @Test
    public void getPeliculaTest() {
        PeliculaEntity entity = data.get(0);
       PeliculaEntity newEntity = persistence.findById(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
    }

    /**
     * Prueba para eliminar una Banda
     */
    @Test
    public void deletePeliculaTest() {
        PeliculaEntity entity = data.get(0);
        persistence.delete(entity.getId());
        PeliculaEntity deleted = em.find(PeliculaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar una Banda
     */
    @Test
    public void updatePeliculaTest() {
       PeliculaEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
       PeliculaEntity newEntity = factory.manufacturePojo(PeliculaEntity.class);

        newEntity.setId(entity.getId());

        persistence.update(newEntity);

        PeliculaEntity resp = em.find(PeliculaEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getNombre(), resp.getNombre());
    }
    
     @Test
     public void findPeliculaByDirectorTest()
     {
        PeliculaEntity entity = data.get(0);
        List<PeliculaEntity> newEntity = persistence.findByDirector(entity.getDirector());
        Assert.assertNotNull(newEntity);
        for(int i = 0; i < newEntity.size(); i++)
        {
            Assert.assertEquals(entity.getDirector(), newEntity.get(i).getDirector());
        }
     }
     
     @Test
     public void findPeliculaByNameTest()
     {
        PeliculaEntity entity = data.get(0);
        List<PeliculaEntity> newEntity = persistence.findByName(entity.getNombre());
        Assert.assertNotNull(newEntity);
        for(int i = 0; i < newEntity.size(); i++)
        {
            Assert.assertEquals(entity.getNombre(), newEntity.get(i).getNombre());
        }
     }
     
     @Test
     public void findPeliculaByIdTest()
     {
        PeliculaEntity entity = data.get(0);
        PeliculaEntity newEntity = persistence.findById(entity.getId());
        Assert.assertNotNull(newEntity);
        
            Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
        
     }

}
