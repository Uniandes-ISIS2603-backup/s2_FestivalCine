/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.test.persistence;

import co.edu.uniandes.csw.festivalcine.entities.FuncionEntity;
import co.edu.uniandes.csw.festivalcine.persistence.FuncionPersistence;
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
 * @author María Juliana Moya
 */
@RunWith(Arquillian.class)
public class FuncionPersistenceTest {
    
    @Inject
    private FuncionPersistence funcionPersistence;
    
    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;
    
    private List<FuncionEntity> data = new ArrayList<FuncionEntity>();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(FuncionEntity.class.getPackage())
                .addPackage(FuncionPersistence.class.getPackage())
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
        em.createQuery("delete from FuncionEntity").executeUpdate();
    }
    
    private void insertData()
    {
        PodamFactory factory = new PodamFactoryImpl();
        for(int i = 0; i < 3; i++)
        {
            FuncionEntity entity = factory.manufacturePojo(FuncionEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
    
    
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
        
        FuncionEntity entity = em.find(FuncionEntity.class, result.getId());

        Assert.assertEquals(newEntity.getId(), entity.getId());          
    }
    
     /**
     * Prueba para consultar la lista de funciones.
     */
    @Test
    public void getFuncionesTest() {
        List<FuncionEntity> list = funcionPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (FuncionEntity ent : list) {
            boolean found = false;
            for (FuncionEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar una Funcion
     */
    @Test
    public void getFuncionTest() {
        FuncionEntity entity = data.get(0);
        FuncionEntity newEntity = funcionPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getPrecioBase(), newEntity.getPrecioBase());
    }

    /**
     * Prueba para eliminar una Funcion
     */
    @Test
    public void deleteFuncionTest() {
        FuncionEntity entity = data.get(0);
        funcionPersistence.delete(entity.getId());
        FuncionEntity deleted = em.find(FuncionEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar una Funcion
     */
    @Test
    public void updateFuncionTest() {
        FuncionEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        FuncionEntity newEntity = factory.manufacturePojo(FuncionEntity.class);

        newEntity.setId(entity.getId());

        funcionPersistence.update(newEntity);

        FuncionEntity resp = em.find(FuncionEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getPrecioBase(), resp.getPrecioBase());
    }

}
