/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.test.persistence;

import co.edu.uniandes.csw.festivalcine.entities.ReservaEntity;
import co.edu.uniandes.csw.festivalcine.entities.UsuarioEntity;
import co.edu.uniandes.csw.festivalcine.persistence.ReservaPersistence;
import co.edu.uniandes.csw.festivalcine.persistence.UsuarioPersistence;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import static org.eclipse.aether.spi.log.NullLoggerFactory.LOGGER;
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
 * @author estudiante
 */
@RunWith(Arquillian.class)
public class ReservaPersistenceTest 
{
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private ReservaPersistence reservaPersistence;
    
     @Inject
    UserTransaction utx;
     
    /**
     * Lista que tiene los datos de prueba.
     */
    private List<ReservaEntity> data = new ArrayList<ReservaEntity>();
    
    @Deployment
    public static JavaArchive createDeployment() 
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ReservaEntity.class.getPackage())
                .addPackage(ReservaPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
     /**
     * Configuración inicial de la prueba.
     */
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
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            try 
            {
                utx.rollback();
            } 
            catch (Exception e1) 
            {
                e1.printStackTrace();
            }
        }
    }
    
      /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     *
     *
     */
    private void insertData() 
    {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {

            ReservaEntity entity = factory.manufacturePojo(ReservaEntity.class);

            em.persist(entity);

            data.add(entity);
        }
    }

    /**
     * Limpia las tablas que están implicadas en la prueba.
     *
     *
     */
    private void clearData() 
    {
        em.createQuery("delete from ReservaEntity").executeUpdate();
    }
    
    public ReservaPersistenceTest()
    {
        
    }
    
     @Test
     public void createReservaTest()
     {
        PodamFactory factory = new PodamFactoryImpl();
        ReservaEntity newEntity = factory.manufacturePojo(ReservaEntity.class);
        ReservaEntity result = reservaPersistence.create(newEntity);
        
        Assert.assertNotNull(result);
        
        ReservaEntity entity = em.find(ReservaEntity.class, result.getId());

        Assert.assertEquals(newEntity.getId(), entity.getId());
     }
     
     @Test
     public void findAllReservasTest()
     {
          List<ReservaEntity> list = reservaPersistence.findAllReservas();
        Assert.assertEquals(data.size(), list.size());
        for (ReservaEntity ent : list) 
        {
            boolean found = false;
            for (ReservaEntity entity : data) 
            {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
     }
     
     @Test
     public void findReservaTest()
     {
        ReservaEntity entity = data.get(0);
        ReservaEntity newEntity = reservaPersistence.findReserva(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getId(), newEntity.getId());
     }
     
     @Test
     public void updateReservaTest()
     {
        ReservaEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        ReservaEntity newEntity = factory.manufacturePojo(ReservaEntity.class);

        newEntity.setId(entity.getId());

        reservaPersistence.updateReserva(newEntity);

        ReservaEntity resp = em.find(ReservaEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getId(), resp.getId());
     }
     
      @Test
      public void deleteReservaTest()
      {
        ReservaEntity entity = data.get(0);
        reservaPersistence.deleteReserva(entity.getId());
        ReservaEntity deleted = em.find(ReservaEntity.class, entity.getId());
        Assert.assertNull(deleted);
      }
}
