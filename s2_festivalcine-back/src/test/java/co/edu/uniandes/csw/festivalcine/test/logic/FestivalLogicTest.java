/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.test.logic;

import co.edu.uniandes.csw.festivalcine.ejb.FestivalLogic;
import co.edu.uniandes.csw.festivalcine.entities.FestivalEntity;
import co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException;
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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Mario Andrade
 */
@RunWith(Arquillian.class)
public class FestivalLogicTest 
{
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private FestivalLogic festivalLogic;

    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;

    private List<FestivalEntity> data = new ArrayList<FestivalEntity>();
    
    
      /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(FestivalEntity.class.getPackage())
                .addPackage(FestivalLogic.class.getPackage())
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

        for (int i = 0; i < 3; i++) 
        {
            FestivalEntity entity = factory.manufacturePojo(FestivalEntity.class);
            em.persist(entity);
            data.add(entity);

        }
    }
    
     /* Prueba para crear un festival.
     *
     * @throws co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException
     */
    @Test
    public void createFestivalTest() throws BusinessLogicException 
    {
        FestivalEntity newEntity = factory.manufacturePojo(FestivalEntity.class);
        FestivalEntity result = festivalLogic.createFestival(newEntity);
        Assert.assertNotNull(result);
        FestivalEntity entity = em.find(FestivalEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }
    
    /**
     * Prueba para crear un Festival con el mismo nombre de un festival que ya
     * existe.
     *
     * @throws co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createFestivalConMismoNombreTest() throws BusinessLogicException {
        FestivalEntity newEntity = factory.manufacturePojo(FestivalEntity.class);
        newEntity.setNombre(data.get(0).getNombre());
        festivalLogic.createFestival(newEntity);
    }
    
     /**
     * Prueba para consultar la lista de festivales.
     */
    @Test
    public void getFestivalesTest() 
    {
        List<FestivalEntity> list = festivalLogic.getFestivales();
        Assert.assertEquals(data.size(), list.size());
        for (FestivalEntity entity : list) 
        {
            boolean found = false;
            for (FestivalEntity storedEntity : data) 
            {
                if (entity.getId().equals(storedEntity.getId())) {
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
        FestivalEntity resultEntity = festivalLogic.getFestival(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getNombre(), resultEntity.getNombre());
    }
    
    /**
     * Prueba para actualizar un festival.
     */
    @Test
    public void updateFestivalTest() {
        FestivalEntity entity = data.get(0);
        FestivalEntity pojoEntity = factory.manufacturePojo(FestivalEntity.class);
        pojoEntity.setId(entity.getId());
        festivalLogic.updateFestival(pojoEntity.getId(), pojoEntity);
        FestivalEntity resp = em.find(FestivalEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getNombre(), resp.getNombre());
    }

    /**
     * Prueba para eliminar un festival.
     *
     * @throws co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException
     */
    @Test
    public void deleteFestivalTest() throws BusinessLogicException {
        FestivalEntity entity = data.get(1);
        festivalLogic.deleteFestival(entity.getId());
        FestivalEntity deleted = em.find(FestivalEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }


}
