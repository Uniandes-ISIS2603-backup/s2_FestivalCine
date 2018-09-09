/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.test.persistence;

import co.edu.uniandes.csw.festivalcine.entities.CriticoEntity;
import co.edu.uniandes.csw.festivalcine.persistence.CriticoPersistence;
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
import org.junit.Before;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author estudiante
 */
@RunWith(Arquillian.class)
public class CriticoPersistenceTest 
{
    @Inject
    private CriticoPersistence criticoPersistence;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    UserTransaction utx;
    
    private List<CriticoEntity> data = new ArrayList();
    
    @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CriticoEntity.class.getPackage())
                .addPackage(CriticoPersistence.class.getPackage())
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
        em.createQuery("delete from CriticoEntity").executeUpdate();
    }
    
    private void insertData()
    {
        PodamFactory factory = new PodamFactoryImpl();
        for(int i = 0; i < 3; i++)
        {
            CriticoEntity entity = factory.manufacturePojo(CriticoEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
    
    @Test
    public void createCritico()
    {
        PodamFactory factory = new PodamFactoryImpl();
        CriticoEntity newEntity = factory.manufacturePojo(CriticoEntity.class);
        CriticoEntity result = criticoPersistence.create(newEntity);
        
        Assert.assertNotNull(result);
        
        CriticoEntity entity = em.find(CriticoEntity.class, result.getId());
        Assert.assertEquals(newEntity.darNombres(), entity.darNombres());
    }
    
    @Test
    public void getCriticosTest()
    {
        List<CriticoEntity> list = criticoPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for(CriticoEntity ent : list)
        {
            boolean found = false;
            for(CriticoEntity entity : data)
            {
                if(ent.getId().equals(entity.getId()))
                {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    @Test
    public void getCriticoTest()
    {
        CriticoEntity entity = data.get(0);
        CriticoEntity newEntity = criticoPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.darNombres(), newEntity.darNombres());
        Assert.assertEquals(entity.darApellidos(), newEntity.darApellidos());
        Assert.assertEquals(entity.darCelular(), newEntity.darCelular());
        Assert.assertEquals(entity.darCredencial(), newEntity.darCredencial());
    }
    
    @Test
    public void updateCriticoTest()
    {
        CriticoEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        CriticoEntity newEntity = factory.manufacturePojo(CriticoEntity.class);
        
        newEntity.setId(entity.getId());
        
        criticoPersistence.update(newEntity);
        
        CriticoEntity resp = em.find(CriticoEntity.class, entity.getId());
        Assert.assertEquals(newEntity.darNombres(), resp.darNombres());
    }
    
    @Test
    public void deleteCriticoTest()
    {
        CriticoEntity entity = data.get(0);
        criticoPersistence.delete(entity.getId());
        CriticoEntity deleted = em.find(CriticoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
//    @Test
//    public void findCriticoByIdentificacionTest()
//    {
//        CriticoEntity entity = data.get(0);
//        CriticoEntity newEntity = criticoPersistence.findByIdentificacion(entity.darIdentificacion());
//        Assert.assertNotNull(newEntity);
//        Assert.assertEquals(entity.darIdentificacion(), newEntity.darIdentificacion());
//        
//        newEntity = criticoPersistence.findByIdentificacion(null);
//        Assert.assertNull(newEntity);
//    }
    
}
