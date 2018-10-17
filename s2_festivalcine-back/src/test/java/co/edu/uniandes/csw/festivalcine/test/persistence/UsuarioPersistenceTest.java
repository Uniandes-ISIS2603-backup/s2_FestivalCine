package co.edu.uniandes.csw.festivalcine.test.persistence;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import co.edu.uniandes.csw.festivalcine.entities.UsuarioEntity;
import co.edu.uniandes.csw.festivalcine.persistence.UsuarioPersistence;
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
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author PAULA VELANDIA
 */
@RunWith(Arquillian.class)
public class UsuarioPersistenceTest {
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UsuarioPersistence usuarioPersistence;
    
    @Inject
    UserTransaction utx;
    
    /**
     * Lista que tiene los datos de prueba.
     */
    private List<UsuarioEntity> data = new ArrayList<UsuarioEntity>();
    
    @Deployment
    public static JavaArchive createDeployment() 
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(UsuarioEntity.class.getPackage())
                .addPackage(UsuarioPersistence.class.getPackage())
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

            UsuarioEntity entity = factory.manufacturePojo(UsuarioEntity.class);

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
        em.createQuery("delete from UsuarioEntity").executeUpdate();
    }
    
    public UsuarioPersistenceTest() 
    {
    }
    
    /**
     * Prueba para crear un usuario
     */
    @Test
    public void createUsuarioTest()
    {
        PodamFactory factory = new PodamFactoryImpl();
        UsuarioEntity newEntity = factory.manufacturePojo(UsuarioEntity.class);
        UsuarioEntity result = usuarioPersistence.createUsuario(newEntity);
        
        Assert.assertNotNull(result);
        
        UsuarioEntity entity = em.find(UsuarioEntity.class, result.getId());

        Assert.assertEquals(newEntity.getId(), entity.getId());
    }
    
    /**
     * Prueba para encontrar todos los usuarios
     */
     @Test
    public void findAllUsuariosTest()
    {
         List<UsuarioEntity> list = usuarioPersistence.findAllUsuarios();
        Assert.assertEquals(data.size(), list.size());
        for (UsuarioEntity ent : list) 
        {
            boolean found = false;
            for (UsuarioEntity entity : data) 
            {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    /**
     * Prueba para encontrar un usuario
     */
    @Test
    public void findUsuarioTest()
    {
        UsuarioEntity entity = data.get(0);
        UsuarioEntity newEntity = usuarioPersistence.findUsuario(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getId(), newEntity.getId());
    }
    
    /**
     * Prueba para hacer update de un usuario
     */
    @Test
    public void updateUsuarioTest()
    {
        UsuarioEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        UsuarioEntity newEntity = factory.manufacturePojo(UsuarioEntity.class);

        newEntity.setId(entity.getId());

        usuarioPersistence.updateUsuario(newEntity);

        UsuarioEntity resp = em.find(UsuarioEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getId(), resp.getId());
    }
    
    /**
     * Prueba para borrar
     */
    @Test
    public void deleteTest()
    {
        UsuarioEntity entity = data.get(0);
        usuarioPersistence.deleteUsuario(entity.getId());
        UsuarioEntity deleted = em.find(UsuarioEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    /**
     * Prueba para encontrar un usuario
     */
     //@Test
     public void findUserByEmailTest()
     {
        UsuarioEntity entity = data.get(0);
        UsuarioEntity newEntity = usuarioPersistence.findUserByCorreo(entity.getEmail());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getEmail(), newEntity.getEmail());

        newEntity = usuarioPersistence.findUserByCorreo(null);
        Assert.assertNull(newEntity);
     }

    
    
}
