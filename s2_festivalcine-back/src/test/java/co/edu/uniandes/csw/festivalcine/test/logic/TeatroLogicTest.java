/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.test.logic;

import co.edu.uniandes.csw.festivalcine.ejb.TeatroLogic;
import co.edu.uniandes.csw.festivalcine.entities.TeatroEntity;
import co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.festivalcine.persistence.TeatroPersistence;
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
public class TeatroLogicTest
{
       private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private TeatroLogic teatroLogic;

    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;

    private List<TeatroEntity> data = new ArrayList<TeatroEntity>();
    
    
      /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(TeatroEntity.class.getPackage())
                .addPackage(TeatroLogic.class.getPackage())
                .addPackage(TeatroPersistence.class.getPackage())
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

        em.createQuery("delete from TeatroEntity").executeUpdate();
    }
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {

        for (int i = 0; i < 3; i++) 
        {
            TeatroEntity entity = factory.manufacturePojo(TeatroEntity.class);
            em.persist(entity);
            data.add(entity);

        }
    }
    
     /* Prueba para crear un Teatro.
     *
     * @throws co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException
     */
    @Test
    public void createTeatroTest() throws BusinessLogicException 
    {
        TeatroEntity newEntity = factory.manufacturePojo(TeatroEntity.class);
        TeatroEntity result = teatroLogic.createTeatro(newEntity);
        Assert.assertNotNull(result);
        TeatroEntity entity = em.find(TeatroEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }
    
    /**
     * Prueba para crear un Teatro con el mismo nombre de un teatro que ya
     * existe.
     *
     * @throws co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createTeatroConMismoNombreTest() throws BusinessLogicException {
        TeatroEntity newEntity = factory.manufacturePojo(TeatroEntity.class);
        newEntity.setNombre(data.get(0).getNombre());
        teatroLogic.createTeatro(newEntity);
    }
    
     /**
     * Prueba para consultar la lista de teatros.
     */
    @Test
    public void getTeatrosTest() 
    {
        List<TeatroEntity> list = teatroLogic.getTeatros();
        Assert.assertEquals(data.size(), list.size());
        for (TeatroEntity entity : list) 
        {
            boolean found = false;
            for (TeatroEntity storedEntity : data) 
            {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    /**
     * Prueba para consultar un teatro.
     */
    @Test
    public void getTeatroTest() {
        TeatroEntity entity = data.get(0);
        TeatroEntity resultEntity = teatroLogic.getTeatro(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getNombre(), resultEntity.getNombre());
    }
    
    /**
     * Prueba para actualizar un teatro.
     */
    @Test
    public void updateTeatroTest() {
        TeatroEntity entity = data.get(0);
        TeatroEntity pojoEntity = factory.manufacturePojo(TeatroEntity.class);
        pojoEntity.setId(entity.getId());
        teatroLogic.updateTeatro(pojoEntity.getId(), pojoEntity);
        TeatroEntity resp = em.find(TeatroEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getNombre(), resp.getNombre());
    }

    /**
     * Prueba para eliminar un teatro.
     *
     * @throws co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException
     */
    @Test
    public void deleteTeatroTest() throws BusinessLogicException {
        TeatroEntity entity = data.get(1);
        teatroLogic.deleteTeatro(entity.getId());
        TeatroEntity deleted = em.find(TeatroEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para eliminar un teatro con funciones asociadas.
     *
     * @throws co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void deleteUsuarioConReservasAsociadasTest() throws BusinessLogicException {
        TeatroEntity entity = data.get(0);
        teatroLogic.deleteTeatro(entity.getId());
    } 
}
