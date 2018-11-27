/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.test.logic;


import co.edu.uniandes.csw.festivalcine.ejb.FestivalLogic;
import co.edu.uniandes.csw.festivalcine.ejb.TeatroFuncionLogic;
import co.edu.uniandes.csw.festivalcine.ejb.TeatroLogic;
import co.edu.uniandes.csw.festivalcine.ejb.TeatroSalaLogic;


import co.edu.uniandes.csw.festivalcine.entities.FestivalEntity;
import co.edu.uniandes.csw.festivalcine.entities.FuncionEntity;
import co.edu.uniandes.csw.festivalcine.entities.SalaEntity;
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
 * @author PAULA VELANDIA
 */
@RunWith(Arquillian.class)
public class TeatroFuncionLogicTest 
{
     private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private TeatroLogic teatroLogic;

     @Inject
    private TeatroFuncionLogic teatroFuncionLogic;
     
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;

    private List<TeatroEntity> data = new ArrayList<>();

    private List<FuncionEntity> funcionesData = new ArrayList<>();
    
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
        em.createQuery("delete from CriticoEntity").executeUpdate();
        em.createQuery("delete from FestivalEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) 
        {
            FuncionEntity funciones = factory.manufacturePojo(FuncionEntity.class);
           
            em.persist(funciones);
            funcionesData.add(funciones);
           
        }
        for (int i = 0; i < 3; i++) 
        {
            TeatroEntity entity = factory.manufacturePojo(TeatroEntity.class);
            
            em.persist(entity);
            data.add(entity);
            if (i == 0) 
            {
                funcionesData.get(i).setTeatro(entity);
            }
        }
    }
    
     /**
     * Prueba para asociar una reservas existente a un Usuario.
     */
    @Test
    public void addFuncionesTest() 
    {
        TeatroEntity entity = data.get(0);
        FuncionEntity funcionEntity = funcionesData.get(1);
        FuncionEntity response = teatroFuncionLogic.addFuncion(funcionEntity.getId(), entity.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(funcionEntity.getId(), response.getId());
        Assert.assertEquals(funcionEntity.getHora(), response.getHora());
        Assert.assertEquals(funcionEntity.getFecha(), response.getFecha());
       Assert.assertEquals(funcionEntity.getPrecioBase(), response.getPrecioBase());
    }
    
     /**
     * Prueba para obtener una colección de instancias de Reservas asociadas a una
     * instancia Usuario.
     */
    @Test
    public void getFuncionesTest() 
    {
        List<FuncionEntity> list = teatroFuncionLogic.getFunciones(data.get(0).getId());
        Assert.assertEquals(1, list.size());
    }
    
    /**
     * Prueba para obtener una instancia de Reservas asociada a una instancia
     * Usuario.
     *
     * @throws co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException     
     */
    @Test
    public void getFuncionTest() throws BusinessLogicException 
    {
        TeatroEntity entity = data.get(0);
        FuncionEntity funcionEntity = funcionesData.get(0);
        FuncionEntity response = teatroFuncionLogic.getFuncion(entity.getId(), funcionEntity.getId());
        
        Assert.assertEquals(funcionEntity.getId(), response.getId());
        Assert.assertEquals(funcionEntity.getHora(), response.getHora());
        Assert.assertEquals(funcionEntity.getFecha(), response.getFecha());
       Assert.assertEquals(funcionEntity.getPrecioBase(), response.getPrecioBase());
    }
}