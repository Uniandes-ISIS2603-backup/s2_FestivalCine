/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//author= cc.cardenas
package co.edu.uniandes.csw.festivalcine.test.logic;

import co.edu.uniandes.csw.festivalcine.ejb.PeliculaLogic;
import co.edu.uniandes.csw.festivalcine.entities.PeliculaEntity;
import co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException;
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
 * @author estudiante
 */
@RunWith(Arquillian.class)
public class PeliculaLogicTest 
{
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private PeliculaLogic peliculaLogic;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;
    
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
                .addPackage(PeliculaLogic.class.getPackage())
                .addPackage(PeliculaPersistence.class.getPackage())
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
        em.createQuery("delete from PeliculaEntity").executeUpdate();
        em.createQuery("delete from UsuarioEntity").executeUpdate();
    }
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() 
    {
        PeliculaEntity pelicula = factory.manufacturePojo(PeliculaEntity.class);
        for(int i = 0; i < 3; i++)
        {
            PeliculaEntity entity = factory.manufacturePojo(PeliculaEntity.class);
       
            em.persist(entity);
            data.add(entity);
        }
    }
    
    @Test
    public void createPeliculaTest() throws BusinessLogicException
    {
        PeliculaEntity newEntity = factory.manufacturePojo(PeliculaEntity.class);
       
        PeliculaEntity result = peliculaLogic.createPelicula(newEntity);
        Assert.assertNotNull(result);
        PeliculaEntity entity = em.find(PeliculaEntity.class, result.getId());
       
    }
    
    @Test
    public void createPelicuaIdInvalidaTest() throws BusinessLogicException
    {
        PeliculaEntity newEntity = factory.manufacturePojo(PeliculaEntity.class);
  
    }
    
    @Test
    public void getPeliculaTest() {
        PeliculaEntity entity = data.get(0);
        PeliculaEntity resultEntity = peliculaLogic.findById(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getNombre(), resultEntity.getNombre());
        Assert.assertEquals(entity.getCreditos(), resultEntity.getCreditos());
        Assert.assertEquals(entity.getDirector(), resultEntity.getDirector());
        Assert.assertEquals(entity.getDuracion(), resultEntity.getDuracion());
        Assert.assertEquals(entity.getPais(), resultEntity.getPais());
        Assert.assertEquals(entity.getPoster(), resultEntity.getPoster());
        Assert.assertEquals(entity.getPais(), resultEntity.getPais());
        Assert.assertEquals(entity.getPuntaje(), resultEntity.getPuntaje());
        Assert.assertEquals(entity.getTrailer(), resultEntity.getTrailer());
    }
    
    @Test
    public void getPeliculasTest()
    {
        List<PeliculaEntity> list = peliculaLogic.getPeliculas();
        Assert.assertEquals(data.size(), list.size());
        for(PeliculaEntity entity : list)
        {
            boolean found = false;
            for(PeliculaEntity storedEntity : data)
            {
                if(entity.getId().equals(storedEntity.getId()))
                {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    @Test
    public void getPeliculaByNameTest() {
        PeliculaEntity entity = data.get(0);
        List<PeliculaEntity> resultEntity = peliculaLogic.findByName(entity.getNombre());
        Assert.assertNotNull(resultEntity);
 
    }
    
    @Test
    public void getPeliculaByDirectorTest() {
        PeliculaEntity entity = data.get(0);
        List<PeliculaEntity> resultEntity = peliculaLogic.findByDirector(entity.getDirector());
        Assert.assertNotNull(resultEntity);
 
    }
   
    @Test
    public void getPeliculaByPuntajeTest() {
        PeliculaEntity entity = data.get(0);
        List<PeliculaEntity> resultEntity = peliculaLogic.findByPuntaje(entity.getPuntaje());
        Assert.assertNotNull(resultEntity);
 
    }
    
    /**
     * Prueba para actualizar un Usuario.
     */
    @Test
    public void updatePeliculaTest() {
        PeliculaEntity entity = data.get(0);
        PeliculaEntity pojoEntity = factory.manufacturePojo(PeliculaEntity.class);
        pojoEntity.setId(entity.getId());
        peliculaLogic.update(pojoEntity.getId(), pojoEntity);
        PeliculaEntity resp = em.find(PeliculaEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(resp.getNombre(), pojoEntity.getNombre());
        Assert.assertEquals(resp.getCreditos(), pojoEntity.getCreditos());
        Assert.assertEquals(resp.getDirector(), pojoEntity.getDirector());
        Assert.assertEquals(resp.getDuracion(), pojoEntity.getDuracion());
        Assert.assertEquals(resp.getPais(), pojoEntity.getPais());
        Assert.assertEquals(resp.getPoster(), pojoEntity.getPoster());
        Assert.assertEquals(resp.getPais(), pojoEntity.getPais());
        Assert.assertEquals(resp.getPuntaje(), pojoEntity.getPuntaje());
        Assert.assertEquals(resp.getTrailer(), pojoEntity.getTrailer());
    }
    
     /**
     * Prueba para eliminar un Usuario.
     *
     * @throws co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException
     */
    @Test
    public void deletePeliculaTest() throws BusinessLogicException {
        PeliculaEntity entity = data.get(1);
        peliculaLogic.delete(entity.getId());
        PeliculaEntity deleted = em.find(PeliculaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
}
