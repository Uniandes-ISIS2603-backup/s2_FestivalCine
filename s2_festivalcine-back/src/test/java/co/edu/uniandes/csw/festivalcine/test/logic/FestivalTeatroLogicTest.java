/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.test.logic;


import co.edu.uniandes.csw.festivalcine.ejb.FestivalLogic;
import co.edu.uniandes.csw.festivalcine.ejb.FestivalTeatroLogic;



import co.edu.uniandes.csw.festivalcine.entities.FestivalEntity;
import co.edu.uniandes.csw.festivalcine.entities.TeatroEntity;
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
 * @author estudiante
 */
@RunWith(Arquillian.class)
public class FestivalTeatroLogicTest 
{
     private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private FestivalLogic festivalLogic;

    @Inject
    private FestivalTeatroLogic festivalTeatroLogic;
     
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;

    private List<FestivalEntity> data = new ArrayList<>();

    private List<TeatroEntity> teatrosData = new ArrayList<>();
    
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
        em.createQuery("delete from TeatroEntity").executeUpdate();
        em.createQuery("delete from FestivalEntity").executeUpdate();
    }
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) 
        {
            TeatroEntity teatros = factory.manufacturePojo(TeatroEntity.class);
            
            em.persist(teatros);
            teatrosData.add(teatros);
           
        }
        for (int i = 0; i < 3; i++) 
        {
            List<FestivalEntity> list = new ArrayList();
            FestivalEntity entity = factory.manufacturePojo(FestivalEntity.class);
            list.add(entity);
            em.persist(entity);
            data.add(entity);
            if (i == 0) 
            {
                teatrosData.get(i).setFestival(list);
            }
        }
    }
    
     /**
     * Prueba para asociar una reservas existente a un Usuario.
     */
    @Test
    public void addTeatrosTest() 
    {
        FestivalEntity entity = data.get(0);
        TeatroEntity teatroEntity = teatrosData.get(1);
        TeatroEntity response = festivalTeatroLogic.addTeatro(teatroEntity.getId(), entity.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(teatroEntity.getId(), response.getId());
        Assert.assertEquals(teatroEntity.getDireccion(), response.getDireccion());
        Assert.assertEquals(teatroEntity.getNombre(), response.getNombre());
        Assert.assertEquals(teatroEntity.getNumSalasFest(), response.getNumSalasFest());
    }
    
    /**
     * Prueba para obtener una colección de instancias de Reservas asociadas a una
     * instancia Usuario.
     */
    @Test
    public void getTeatrosTest() 
    {
        List<TeatroEntity> list = festivalTeatroLogic.getTeatros(data.get(0).getId());

        Assert.assertEquals(1, list.size());
    }
    
     @Test
    public void getTeatroTest() throws BusinessLogicException 
    {
        FestivalEntity entity = data.get(0);
        TeatroEntity teatroEntity = teatrosData.get(0);
        TeatroEntity response = festivalTeatroLogic.getTeatro(entity.getId(), teatroEntity.getId());

        Assert.assertEquals(teatroEntity.getId(), response.getId());
        Assert.assertEquals(teatroEntity.getDireccion(), response.getDireccion());
        Assert.assertEquals(teatroEntity.getNombre(), response.getNombre());
        Assert.assertEquals(teatroEntity.getNumSalasFest(), response.getNumSalasFest());
        
        
        
        
    }
}
