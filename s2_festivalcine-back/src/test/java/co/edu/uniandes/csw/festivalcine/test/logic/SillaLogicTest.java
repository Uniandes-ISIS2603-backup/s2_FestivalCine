/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.test.logic;

import co.edu.uniandes.csw.festivalcine.ejb.SillaLogic;
import co.edu.uniandes.csw.festivalcine.entities.SalaEntity;
import co.edu.uniandes.csw.festivalcine.entities.SillaEntity;
import co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.festivalcine.persistence.SillaPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Clase que prueba sillaLogic
 * @author María Juliana Moya
 */
@RunWith(Arquillian.class)
public class SillaLogicTest {
     private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private SillaLogic sillaLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;
    
    private List<SillaEntity> data = new ArrayList<>();
    private List<SalaEntity> dataSala = new ArrayList<>();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(SillaEntity.class.getPackage())
                .addPackage(SillaLogic.class.getPackage())
                .addPackage(SillaPersistence.class.getPackage())
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
        em.createQuery("delete from SillaEntity").executeUpdate();
        em.createQuery("delete from SalaEntity").executeUpdate();
    }
    
    /**
     * Inserta los datos iniciales para el correcto sillaamiento de las
     * pruebas.
     */
    private void insertData() {
        
         for (int i = 0; i < 3; i++) {
            SalaEntity sala = factory.manufacturePojo(SalaEntity.class);
            em.persist(sala);
            dataSala.add(sala);
        }
        
        for (int i = 0; i < 3; i++) {
            SillaEntity silla = factory.manufacturePojo(SillaEntity.class);
            silla.setSala(dataSala.get(i));
            em.persist(silla);
            data.add(silla);
        }
        
    }
    
    /**
     * Prueba para crear una Función
     *
     * @throws BusinessLogicException
     */
    @Test
    public void createSillaTest() throws BusinessLogicException {
        SillaEntity newEntity = factory.manufacturePojo(SillaEntity.class);
        newEntity.setSala(dataSala.get(0));
        SillaEntity result = sillaLogic.createSilla(newEntity);
        Assert.assertNotNull(result);
        SillaEntity entity = em.find(SillaEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getNumero(), entity.getNumero());
    }
    
        /**
     * Prueba para crear una silla con una sala invalida
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createSillaSalaInvalidaTest() throws BusinessLogicException {
        SillaEntity newEntity = factory.manufacturePojo(SillaEntity.class);
        SalaEntity sala = factory.manufacturePojo(SalaEntity.class);
        newEntity.setSala(sala);
        SillaEntity result = sillaLogic.createSilla(newEntity);
        Assert.assertNotNull(result);
        SillaEntity entity = em.find(SillaEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getNumero(), entity.getNumero());
    }
    
    
    /**
     * Prueba para consultar la lista de Sillaes.
     * BusinessLogicException
     */
    @Test
    public void getSillasTest() throws BusinessLogicException {
        List<SillaEntity> list = sillaLogic.getSillas();
        Assert.assertEquals(data.size(), list.size());
        for (SillaEntity entity : list) {
            boolean found = false;
            for (SillaEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    /**
     * Prueba para consultar una Silla
     */
    @Test
    public void getSillaTest() {
        SillaEntity entity = data.get(0);
        SillaEntity resultEntity = sillaLogic.getSilla(data.get(0).getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(resultEntity.getNumero(), entity.getNumero());
    }
    
    /**
     * Prueba para actualizar una Silla.
     * @throws co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException
     */
    @Test
    public void updateSillaTest() throws BusinessLogicException {
        SillaEntity entity = data.get(0);
        SillaEntity pojoEntity = factory.manufacturePojo(SillaEntity.class);
        pojoEntity.setId(entity.getId());
        sillaLogic.updateSilla(pojoEntity.getId(), pojoEntity);

        SillaEntity resp = em.find(SillaEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getNumero(), resp.getNumero());
    }
    
    /**
     * Prueba para eliminar una Silla.
     *
     * @throws co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException
     */
    @Test
    public void deleteSillaTest() throws BusinessLogicException {
        SillaEntity entity = data.get(0);
        sillaLogic.deleteSilla(entity.getId());
        SillaEntity deleted = em.find(SillaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
}
