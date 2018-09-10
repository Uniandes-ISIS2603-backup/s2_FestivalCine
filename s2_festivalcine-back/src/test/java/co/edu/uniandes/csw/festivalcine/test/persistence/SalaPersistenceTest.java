/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.test.persistence;
import co.edu.uniandes.csw.festivalcine.entities.SalaEntity;
import co.edu.uniandes.csw.festivalcine.persistence.SalaPersistence;
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
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author María Juliana Moya
 */
@RunWith(Arquillian.class)
public class SalaPersistenceTest {
    @Inject
    private SalaPersistence salaPersistence;
    
    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;
    
    private List<SalaEntity> data = new ArrayList<SalaEntity>();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(SalaEntity.class.getPackage())
                .addPackage(SalaPersistence.class.getPackage())
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
        SalaEntity newEntity = factory.manufacturePojo(SalaEntity.class);
        SalaEntity result = salaPersistence.create(newEntity);
        
        
        Assert.assertNotNull(result);
        
        SalaEntity entity = em.find(SalaEntity.class, result.getId());

        Assert.assertEquals(newEntity.getId(), entity.getId());          
    }
    
     /**
     * Prueba para consultar la lista de salas.
     */
    @Test
    public void getSalasTest() {
        List<SalaEntity> list = salaPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (SalaEntity ent : list) {
            boolean found = false;
            for (SalaEntity entity : data) {
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
        SalaEntity entity = data.get(0);
        SalaEntity newEntity = salaPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNumero(), newEntity.getNumero());
    }

    /**
     * Prueba para eliminar una Sala 
     */
    @Test
    public void deleteFuncionTest() {
        SalaEntity entity = data.get(0);
        salaPersistence.delete(entity.getId());
        SalaEntity deleted = em.find(SalaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar una Sala
     */
    @Test
    public void updateFuncionTest() {
        SalaEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        SalaEntity newEntity = factory.manufacturePojo(SalaEntity.class);

        newEntity.setId(entity.getId());

        salaPersistence.update(newEntity);

        SalaEntity resp = em.find(SalaEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getNumero(), resp.getNumero());
    }
}
