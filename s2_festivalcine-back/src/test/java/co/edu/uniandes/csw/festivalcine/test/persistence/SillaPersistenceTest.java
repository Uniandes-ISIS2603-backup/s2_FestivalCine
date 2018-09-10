/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.test.persistence;
import co.edu.uniandes.csw.festivalcine.entities.SillaEntity;
import co.edu.uniandes.csw.festivalcine.persistence.SillaPersistence;
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
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import org.junit.runner.RunWith;

/**
 *
 * @author María Juliana Moya
 */

@RunWith(Arquillian.class)
public class SillaPersistenceTest {
     @Inject
    private SillaPersistence sillaPersistence;
    
    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;
    
    private List<SillaEntity> data = new ArrayList<SillaEntity>();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(SillaEntity.class.getPackage())
                .addPackage(SillaPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    /**
     * Prueba de crear una Sala
     * 
     */
    
    @Test
    public void createSalaTest(){
        PodamFactory factory = new PodamFactoryImpl();
        SillaEntity newEntity = factory.manufacturePojo(SillaEntity.class);
        SillaEntity result = sillaPersistence.create(newEntity);
        
        
        Assert.assertNotNull(result);
        
        SillaEntity entity = em.find(SillaEntity.class, result.getId());

        Assert.assertEquals(newEntity.getId(), entity.getId());          
    }
    
     /**
     * Prueba para consultar la lista de salas.
     */
    @Test
    public void getSalasTest() {
        List<SillaEntity> list = sillaPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (SillaEntity ent : list) {
            boolean found = false;
            for (SillaEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar una Sala
     */
    @Test
    public void getSalaTest() {
        SillaEntity entity = data.get(0);
        SillaEntity newEntity = sillaPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNumero(), newEntity.getNumero());
    }

    /**
     * Prueba para eliminar una Funcion
     */
    @Test
    public void deleteFuncionTest() {
        SillaEntity entity = data.get(0);
        sillaPersistence.delete(entity.getId());
        SillaEntity deleted = em.find(SillaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar una Funcion
     */
    @Test
    public void updateFuncionTest() {
        SillaEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        SillaEntity newEntity = factory.manufacturePojo(SillaEntity.class);

        newEntity.setId(entity.getId());

        sillaPersistence.update(newEntity);

        SillaEntity resp = em.find(SillaEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getNumero(), resp.getNumero());
    }
}
