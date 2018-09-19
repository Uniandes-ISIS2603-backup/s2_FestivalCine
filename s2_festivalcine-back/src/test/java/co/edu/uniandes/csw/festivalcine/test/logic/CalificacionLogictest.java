/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.test.logic;

import co.edu.uniandes.csw.festivalcine.ejb.CalificacionLogic;
import co.edu.uniandes.csw.festivalcine.entities.CalificacionEntity;
import co.edu.uniandes.csw.festivalcine.entities.ReservaEntity;
import co.edu.uniandes.csw.festivalcine.entities.SillaEntity;
import co.edu.uniandes.csw.festivalcine.entities.UsuarioEntity;
import co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException;
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
 * @author estudiante
 */
@RunWith(Arquillian.class)
public class CalificacionLogictest 
{
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private CalificacionLogic calificacionLogic;
    
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
                .addPackage(ReservaPersistence.class.getPackage())
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
    public void crateCalificacionIdInvalidoTest()
    {
        CalificacionEntity newEntity = factory.manufacturePojo(CalificacionEntity.class);
        newEntity.setUsuario(usuarioData);
        newEntity.setId((long)-922337203);
        calificacionLogic.createCalificacion(newEntity);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createCalificacionIdInvalida2Test()
    {
        CalificacionEntity newEntity = factory.manufacturePojo(CalificacionEntity.class);
        newEntity.setUsuario(usuarioData);
        newEntity.setId(null);
        calificacionLogic.createCalificacion(newEntity);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createCalificacionTestConUsuarioInexistente()
    {
        CalificacionEntity newEntity = factory.manufacturePojo(CalificacionEntity.class);
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId(Long.MAX_VALUE);
        newEntity.setUsuario(null);
        calificacionLogic.createCalificacion(newEntity);
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
    public void getCalificacion()
    {
        CalificacionEntity entity = data.get(0);
        CalificacionEntity resultEntity = calificacionLogic.getCalificacion(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getComentario(), resultEntity.getComentario());
        Assert.assertEquals(entity.getPuntaje(), resultEntity.getPuntaje());
        Assert.assertEquals(entity.getUsuario(), resultEntity.getUsuario());
    }
    
}
