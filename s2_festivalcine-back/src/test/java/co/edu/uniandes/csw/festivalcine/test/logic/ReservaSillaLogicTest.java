/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.test.logic;

import co.edu.uniandes.csw.festivalcine.ejb.ReservaLogic;
import co.edu.uniandes.csw.festivalcine.ejb.ReservaSillasLogic;
import co.edu.uniandes.csw.festivalcine.entities.ReservaEntity;
import co.edu.uniandes.csw.festivalcine.entities.SillaEntity;
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
 * @author PAULA VELANDIA
 */
@RunWith(Arquillian.class)
public class ReservaSillaLogicTest 
{
    
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private ReservaLogic reservaLogic;
    
    @Inject
    private ReservaSillasLogic reservaSillasLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<ReservaEntity> data = new ArrayList<ReservaEntity>();

    private List<SillaEntity> sillasData = new ArrayList();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ReservaEntity.class.getPackage())
                .addPackage(ReservaLogic.class.getPackage())
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
        em.createQuery("delete from SillaEntity").executeUpdate();
        em.createQuery("delete from ReservaEntity").executeUpdate();
    }
    
    
    private void insertData() 
    {
        for (int i = 0; i < 3; i++) 
        {
            SillaEntity sillas = factory.manufacturePojo(SillaEntity.class);
            em.persist(sillas);
            sillasData.add(sillas);
        }
        for (int i = 0; i < 3; i++) 
        {
            ReservaEntity entity = factory.manufacturePojo(ReservaEntity.class);
            em.persist(entity);
            data.add(entity);
            if (i == 0) 
            {
                sillasData.get(i).setReserva(entity);
            }
        }
    }
    
     /**
     * Prueba para asociar una silla existente a una reserva.
     */
    @Test
    public void addSillasTest() 
    {
        ReservaEntity entity = data.get(0);
        SillaEntity sillaEntity = sillasData.get(1);
        SillaEntity response = reservaSillasLogic.addSilla(entity.getId(), sillaEntity.getId());
        
        Assert.assertNotNull(response);
        Assert.assertEquals(sillaEntity.getId(), response.getId());
        Assert.assertEquals(sillaEntity.getDisponible(), response.getDisponible());
        Assert.assertEquals(sillaEntity.getNumero(), response.getNumero());
    }
    
      /**
     * Prueba para obtener una colección de instancias de Sillas asociadas a una
     * instancia Reserva.
     */
    @Test
    public void getSillasReservaTest() 
    {
        List<SillaEntity> list = reservaSillasLogic.getSillasReserva(data.get(0).getId());
        Assert.assertEquals(1, list.size());
    }
    
     
    
    
    
    
    
    
    
    
    
    
    
    /**
     * Prueba para obtener una instancia de sillas asociada a una instancia
     * reserva.
     *
     * @throws co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException
     */
    @Test
    public void getSillaTest() throws BusinessLogicException 
    {
        ReservaEntity entity = data.get(0);
        SillaEntity sillaEntity = sillasData.get(0);
        SillaEntity response = reservaSillasLogic.getSilla(entity.getId(), sillaEntity.getId());

        Assert.assertEquals(sillaEntity.getId(), response.getId());
        Assert.assertEquals(sillaEntity.getDisponible(), response.getDisponible());
        Assert.assertEquals(sillaEntity.getNumero(), response.getNumero());
        Assert.assertEquals(sillaEntity.getSala(), response.getSala());
        Assert.assertEquals(sillaEntity.getTipo(), response.getTipo());
    }
    
    /**
     * Prueba para obtener una instancia de Sillas asociada a una instancia
     * Reserva que no le pertenece.
     *
     * @throws co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void getSillaNoAsociadaTest() throws BusinessLogicException {
        ReservaEntity entity = data.get(0);
        SillaEntity sillaEntity = sillasData.get(2);
        reservaSillasLogic.getSilla(entity.getId(), sillasData.get(2).getId());
    }
    
}
