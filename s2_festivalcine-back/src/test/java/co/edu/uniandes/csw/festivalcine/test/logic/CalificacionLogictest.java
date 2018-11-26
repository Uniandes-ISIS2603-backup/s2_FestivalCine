/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.test.logic;

import co.edu.uniandes.csw.festivalcine.ejb.CalificacionLogic;
import co.edu.uniandes.csw.festivalcine.ejb.UsuarioLogic;
import co.edu.uniandes.csw.festivalcine.entities.CalificacionEntity;
import co.edu.uniandes.csw.festivalcine.entities.ReservaEntity;
import co.edu.uniandes.csw.festivalcine.entities.SillaEntity;
import co.edu.uniandes.csw.festivalcine.entities.UsuarioEntity;
import co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.festivalcine.persistence.CalificacionPersistence;
import co.edu.uniandes.csw.festivalcine.persistence.ReservaPersistence;
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
 * @author Andres Felipe Rodriguez Murillo
 */
@RunWith(Arquillian.class)
public class CalificacionLogictest 
{
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private CalificacionLogic calificacionLogic;
    
    @Inject
    private UsuarioLogic usuarioLogic;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;
    
    private List<CalificacionEntity> data = new ArrayList<CalificacionEntity>();
    
    private UsuarioEntity usuarioData;
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CalificacionEntity.class.getPackage())
                .addPackage(CalificacionLogic.class.getPackage())
                .addPackage(CalificacionPersistence.class.getPackage())
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
        em.createQuery("delete from CalificacionEntity").executeUpdate();
        em.createQuery("delete from UsuarioEntity").executeUpdate();
    }
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() 
    {
        UsuarioEntity usuario = factory.manufacturePojo(UsuarioEntity.class);
        em.persist(usuario);
        for(int i = 0; i < 3; i++)
        {
            CalificacionEntity entity = factory.manufacturePojo(CalificacionEntity.class);
            entity.setUsuario(usuario);
            em.persist(entity);
            data.add(entity);
        }        
    }
    
    @Test
    public void createCalificacionTest()
    {
        CalificacionEntity newEntity = factory.manufacturePojo(CalificacionEntity.class);
        newEntity.setUsuario(usuarioData);
        CalificacionEntity result = calificacionLogic.createCalificacion(newEntity);
        Assert.assertNotNull(result);
        CalificacionEntity entity = em.find(CalificacionEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createCalificacionConUsuarioInexistenteTest() throws BusinessLogicException
    {
        CalificacionEntity newEntity = factory.manufacturePojo(CalificacionEntity.class);
        newEntity = calificacionLogic.createCalificacion(newEntity);
        calificacionLogic.addUsuario(newEntity.getId(), Long.MIN_VALUE);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void deleteCalificacionDespuesUsuarioInexisteneTest() throws BusinessLogicException
    {
        CalificacionEntity newEntity = factory.manufacturePojo(CalificacionEntity.class);
        newEntity = calificacionLogic.createCalificacion(newEntity);
        try
        {
        calificacionLogic.addUsuario(newEntity.getId(), Long.MIN_VALUE);
        }catch (Exception e){}
        calificacionLogic.getCalificacion(newEntity.getId());
    }
    
    @Test
    public void updateCalificacionTest() throws BusinessLogicException
    {
        CalificacionEntity original = factory.manufacturePojo(CalificacionEntity.class);
        calificacionLogic.createCalificacion(original);
        original.setComentario("123");
        original.setPuntaje(Long.MAX_VALUE);
        calificacionLogic.updateCalificacion(original);
        CalificacionEntity result = calificacionLogic.getCalificacion(original.getId());
        Assert.assertEquals(original.getComentario(), result.getComentario());
        Assert.assertEquals(original.getPuntaje(), result.getPuntaje());
    }
    
    @Test(expected = BusinessLogicException.class)
    public void updateCalificacionIdNegativoTest() throws BusinessLogicException
    {
        CalificacionEntity original = factory.manufacturePojo(CalificacionEntity.class);
        calificacionLogic.createCalificacion(original);
        original.setId(Long.MIN_VALUE);
        calificacionLogic.updateCalificacion(original);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void updateCalificacionIdNuloTest() throws BusinessLogicException
    {
        CalificacionEntity original = factory.manufacturePojo(CalificacionEntity.class);
        calificacionLogic.createCalificacion(original);
        original.setId(null);
        calificacionLogic.updateCalificacion(original);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void updateCalificacionIdInexistenteTest() throws BusinessLogicException
    {
        CalificacionEntity original = factory.manufacturePojo(CalificacionEntity.class);
        calificacionLogic.createCalificacion(original);
        original.setId(Long.MAX_VALUE);
        calificacionLogic.updateCalificacion(original);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void deleteCalificacionTest() throws BusinessLogicException
    {
        CalificacionEntity newEntity = factory.manufacturePojo(CalificacionEntity.class);
        calificacionLogic.createCalificacion(newEntity);
        calificacionLogic.deleteCalificacion(newEntity.getId());
            calificacionLogic.getCalificacion(newEntity.getId());
        
    }
    
    @Test
    public void getCalificacionesTest()
    {
        List<CalificacionEntity> list = calificacionLogic.getCalificaciones();
        Assert.assertEquals(data.size(), list.size());
        for(CalificacionEntity entity : list)
        {
            boolean found = false;
            for(CalificacionEntity storedEntity : data)
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
    public void getCalificacionsTest() throws BusinessLogicException
    {
        CalificacionEntity entity = data.get(0);
        CalificacionEntity resultEntity = calificacionLogic.getCalificacion(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getComentario(), resultEntity.getComentario());
        Assert.assertEquals(entity.getPuntaje(), resultEntity.getPuntaje());
        Assert.assertEquals(entity.getUsuario(), resultEntity.getUsuario());
    }
    
}
