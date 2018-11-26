/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.test.logic;

import co.edu.uniandes.csw.festivalcine.ejb.CalificacionLogic;
import co.edu.uniandes.csw.festivalcine.ejb.CriticoLogic;
import co.edu.uniandes.csw.festivalcine.ejb.FuncionLogic;
import co.edu.uniandes.csw.festivalcine.ejb.PeliculaLogic;
import co.edu.uniandes.csw.festivalcine.entities.CalificacionEntity;
import co.edu.uniandes.csw.festivalcine.entities.CriticoEntity;
import co.edu.uniandes.csw.festivalcine.entities.FuncionEntity;
import co.edu.uniandes.csw.festivalcine.entities.PeliculaEntity;
import co.edu.uniandes.csw.festivalcine.entities.UsuarioEntity;
import co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.festivalcine.persistence.CriticoPersistence;
import co.edu.uniandes.csw.festivalcine.persistence.ReservaPersistence;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
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
 * @author Andres Felipe Rodriguez Murillo
 */
@RunWith(Arquillian.class)
public class CriticoLogicTest 
{
    
    private static final Logger LOGGER = Logger.getLogger(CalificacionLogic.class.getName());
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private CriticoLogic criticoLogic;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;
    
    private List<CriticoEntity> data = new ArrayList<CriticoEntity>();
    
   private List<FuncionEntity> funciones = new ArrayList<FuncionEntity>();
   
   private List<PeliculaEntity> peliculas = new ArrayList<PeliculaEntity>();
   
   private List<CalificacionEntity> calificaciones = new ArrayList<CalificacionEntity>();
   
   /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CriticoEntity.class.getPackage())
                .addPackage(CriticoLogic.class.getPackage())
                .addPackage(CriticoPersistence.class.getPackage())
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
        em.createQuery("delete from FuncionEntity").executeUpdate();
        em.createQuery("delete from PeliculaEntity").executeUpdate();
        em.createQuery("delete from CalificacionEntity").executeUpdate();
    }
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() 
    {
        for(int a = 0; a < 3; a++)
            {
                FuncionEntity funcion = factory.manufacturePojo(FuncionEntity.class);
                em.persist(funcion);
                funciones.add(funcion);
            }
            
            for(int a = 0; a < 3; a++)
            {
                PeliculaEntity pelicula = factory.manufacturePojo(PeliculaEntity.class);
                em.persist(pelicula);
                peliculas.add(pelicula);
            }
            
            for(int a = 0; a < 3; a++)
            {
                CalificacionEntity calificacion = factory.manufacturePojo(CalificacionEntity.class);
                em.persist(calificacion);
                calificaciones.add(calificacion);
            }
        for(int i = 0; i < 3; i++)
        {
            CriticoEntity critico = factory.manufacturePojo(CriticoEntity.class);
            critico.darFunciones().add(funciones.get(i));
            critico.darPeliculas().add(peliculas.get(i));
            critico.darCalificaciones().add(calificaciones.get(i));
            em.persist(critico);
            data.add(critico);
        }

        LOGGER.log(Level.INFO, data.size() + "");
        
    }
    
    @Test
    public void createCriticoTest() throws BusinessLogicException
    {
        CriticoEntity newEntity = factory.manufacturePojo(CriticoEntity.class);
        CriticoEntity result = criticoLogic.createCritico(newEntity);
        Assert.assertNotNull(result);
        CriticoEntity entity = em.find(CriticoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createCriticoConIdExistenteTest() throws Exception
    {
        CriticoEntity newEntity = factory.manufacturePojo(CriticoEntity.class);
        try
        {
            criticoLogic.createCritico(newEntity);
        }catch (Exception e) {throw new Exception("Error");}
        criticoLogic.createCritico(newEntity);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void getCriticoInexistenteTest() throws BusinessLogicException
    {
        criticoLogic.getCritico(Long.MAX_VALUE);
    }
    
    @Test
    public void getCriticosTest()
    {
        List<CriticoEntity> list = criticoLogic.getCriticos();
        Assert.assertEquals(data.size(), list.size());
        for(CriticoEntity entity : list)
        {
            boolean found = false;
            for(CriticoEntity storedEntity : data)
            {
                if(entity.getId().equals(storedEntity.getId()))
                {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    @Test(expected = BusinessLogicException.class)
    public void deleteCriticoTest() throws BusinessLogicException
    {
        CriticoEntity entity = data.get(0);
        criticoLogic.deleteCritico(entity.getId());
        criticoLogic.getCritico(entity.getId());
    }
    
   @Test
    public void getCriticoTest() throws BusinessLogicException
    {
        
        LOGGER.log(Level.INFO, data.get(0).darNombres());
        CriticoEntity entity = data.get(0);
        CriticoEntity resultEntity = criticoLogic.getCritico(entity.getId());
        Assert.assertNotNull(resultEntity);
    }
    
     @Test
    public void updateCriticoTest() throws BusinessLogicException
    {
        CriticoEntity original = factory.manufacturePojo(CriticoEntity.class);
        criticoLogic.createCritico(original);
        original.setNombres("123");
        original.setPuntaje(Long.MAX_VALUE);
        criticoLogic.updateCritico(original);
        CriticoEntity result = criticoLogic.getCritico(original.getId());
        Assert.assertEquals(original.darNombres(), result.darNombres());
        Assert.assertEquals(original.darPuntaje(), result.darPuntaje());
    }
    
    @Test
    public void addFuncionTest() throws BusinessLogicException
    {
        CriticoEntity entity = data.get(0);
        FuncionEntity funcionEntity = funciones.get(1);
        FuncionEntity response = criticoLogic.addFuncion(entity.getId(), funcionEntity.getId());
        
        Assert.assertNotNull(response);
        Assert.assertEquals(funcionEntity.getId(), response.getId());
    }
    
    @Test
    public void getFuncionesTest()
    {
        List<FuncionEntity> funcionEntity = criticoLogic.getFunciones(data.get(0).getId());
        
        Assert.assertEquals(funciones.size(), funcionEntity.size());
        for(int i = 0; i < data.size(); i++)
        {
            Assert.assertTrue(funcionEntity.contains(data.get(i)));
        }
    }
    
}
