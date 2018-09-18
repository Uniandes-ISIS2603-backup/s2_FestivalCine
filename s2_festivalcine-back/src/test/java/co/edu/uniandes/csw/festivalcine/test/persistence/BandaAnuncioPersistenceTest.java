/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.test.persistence;

import co.edu.uniandes.csw.festivalcine.entities.BandaAnuncioEntity;
import co.edu.uniandes.csw.festivalcine.persistence.BandaAnuncioPersistence;
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
public class BandaAnuncioPersistenceTest {
    
    @Inject
    private BandaAnuncioPersistence persistence ;
    
    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;
    
    private List<BandaAnuncioEntity> data = new ArrayList<BandaAnuncioEntity>();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(BandaAnuncioEntity.class.getPackage())
                .addPackage(BandaAnuncioPersistence.class.getPackage())
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
        em.createQuery("delete from BandaAnuncioEntity").executeUpdate();
    }
    
    private void insertData()
    {
        PodamFactory factory = new PodamFactoryImpl();
        for(int i = 0; i < 3; i++)
        {
            BandaAnuncioEntity entity = factory.manufacturePojo(BandaAnuncioEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
    
    
    /**
     * Prueba de crear una eBanda
     * 
     */
    
    @Test
    public void createBandaTest(){
        PodamFactory factory = new PodamFactoryImpl();
        BandaAnuncioEntity newEntity = factory.manufacturePojo(BandaAnuncioEntity.class);
        BandaAnuncioEntity result =persistence.create(newEntity);
        
        
        Assert.assertNotNull(result);
        
        BandaAnuncioEntity entity = em.find(BandaAnuncioEntity.class, result.getId());

        Assert.assertEquals(newEntity.getId(), entity.getId());          
    }
    
     /**
     * Prueba para consultar la lista de Bandas.
     */
    @Test
    public void getBandasTest() {
        List<BandaAnuncioEntity> list = persistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (BandaAnuncioEntity ent : list) {
            boolean found = false;
            for (BandaAnuncioEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar una Banda
     */
    @Test
    public void getBandaTest() {
        BandaAnuncioEntity entity = data.get(0);
       BandaAnuncioEntity newEntity = persistence.findById(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getDuracion(), newEntity.getDuracion());
    }

    /**
     * Prueba para eliminar una Banda
     */
    @Test
    public void deleteBandaTest() {
        BandaAnuncioEntity entity = data.get(0);
        persistence.delete(entity.getId());
        BandaAnuncioEntity deleted = em.find(BandaAnuncioEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar una Banda
     */
    @Test
    public void updateBandaTest() {
       BandaAnuncioEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
       BandaAnuncioEntity newEntity = factory.manufacturePojo(BandaAnuncioEntity.class);

        newEntity.setId(entity.getId());

        persistence.update(newEntity);

        BandaAnuncioEntity resp = em.find(BandaAnuncioEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getDuracion(), resp.getDuracion());
    }

}
