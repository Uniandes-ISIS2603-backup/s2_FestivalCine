/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.test.logic;

import co.edu.uniandes.csw.festivalcine.ejb.SalaLogic;
import co.edu.uniandes.csw.festivalcine.ejb.SalaSillasLogic;
import co.edu.uniandes.csw.festivalcine.entities.SalaEntity;
import co.edu.uniandes.csw.festivalcine.entities.SillaEntity;
import co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException;
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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * Pruebas de logica de la relacion Sala - Sillas
 *
 * @author María Juliana Moya
 */
@RunWith(Arquillian.class)
public class SalaSillasLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private SalaLogic salaLogic;
    @Inject
    private SalaSillasLogic salaSillasLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<SalaEntity> data = new ArrayList<SalaEntity>();

    private List<SillaEntity> sillasData = new ArrayList();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(SalaEntity.class.getPackage())
                .addPackage(SalaLogic.class.getPackage())
                .addPackage(SalaPersistence.class.getPackage())
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
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            SillaEntity sillas = factory.manufacturePojo(SillaEntity.class);
            em.persist(sillas);
            sillasData.add(sillas);
        }
        for (int i = 0; i < 3; i++) {
            SalaEntity entity = factory.manufacturePojo(SalaEntity.class);
            em.persist(entity);
            data.add(entity);
            if (i == 0) {
                sillasData.get(i).setSala(entity);
            }
        }
    }

    /**
     * Prueba para asociar una Silla existente a un Sala.
     */
    
    @Test
    public void addSillasTest() throws BusinessLogicException {
        SalaEntity entity = data.get(0);
        SillaEntity sillaEntity = sillasData.get(1);
        SillaEntity response = salaSillasLogic.addSilla(sillaEntity.getId(), entity.getId());
        Assert.assertNotNull(response);
        Assert.assertEquals(sillaEntity.getId(), response.getId());
    }

    /**
     * Prueba para obtener una colección de instancias de Sillas asociadas a una
     * instancia Sala.
     */
    @Test
    public void getSillasTest() {
        List<SillaEntity> list = salaSillasLogic.getSillas(data.get(0).getId());
        Assert.assertEquals(1, list.size());
    }

    /**
     * Prueba para obtener una instancia de Sillas asociada a una instancia
     * Sala.
     *
     * @throws co.edu.uniandes.csw.sillastore.exceptions.BusinessLogicException
     */
    @Test
    public void getSillaTest() throws BusinessLogicException {
        SalaEntity entity = data.get(0);
        SillaEntity sillaEntity = sillasData.get(0);
        SillaEntity response = salaSillasLogic.getSilla(entity.getId(), sillaEntity.getId());

        Assert.assertEquals(sillaEntity.getId(), response.getId());
        Assert.assertEquals(sillaEntity.getNumero(), response.getNumero());
    }

    /**
     * Prueba para obtener una instancia de Sillas asociada a una instancia
     * Sala que no le pertenece.
     *
     * @throws co.edu.uniandes.csw.sillastore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void getSillaNoAsociadoTest() throws BusinessLogicException {
        SalaEntity entity = data.get(0);
        SillaEntity sillaEntity = sillasData.get(1);
        salaSillasLogic.getSilla(entity.getId(), sillaEntity.getId());
    }

    
}
