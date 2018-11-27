/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.test.logic;


import co.edu.uniandes.csw.festivalcine.ejb.FestivalLogic;
import co.edu.uniandes.csw.festivalcine.ejb.TeatroLogic;
import co.edu.uniandes.csw.festivalcine.ejb.TeatroSalaLogic;
import co.edu.uniandes.csw.festivalcine.entities.CriticoEntity;

import co.edu.uniandes.csw.festivalcine.entities.FestivalEntity;
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
public class TeatroSalaLogicTest 
{
     private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private TeatroLogic teatroLogic;

     @Inject
    private TeatroSalaLogic teatroSalaLogic;
     
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;

    private List<TeatroEntity> data = new ArrayList<>();

    private List<SalaEntity> salasData = new ArrayList<>();
    
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
            SalaEntity salas = factory.manufacturePojo(SalaEntity.class);
           
            em.persist(salas);
            salasData.add(salas);
           
        }
        for (int i = 0; i < 3; i++) 
        {
            TeatroEntity entity = factory.manufacturePojo(TeatroEntity.class);
            
            em.persist(entity);
            data.add(entity);
            if (i == 0) 
            {
                salasData.get(i).setTeatro(entity);
            }
        }
    }
    
     /**
     * Prueba para asociar una reservas existente a un Usuario.
     */
    @Test
    public void addSalasTest() 
    {
        TeatroEntity entity = data.get(0);
        SalaEntity salaEntity = salasData.get(1);
        SalaEntity response = teatroSalaLogic.addSala(salaEntity.getId(), entity.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(salaEntity.getId(), response.getId());
        Assert.assertEquals(salaEntity.getNumero(), response.getNumero());
        Assert.assertEquals(salaEntity.getSillas(), response.getSillas());
        
    }
    
     /**
     * Prueba para obtener una colección de instancias de Reservas asociadas a una
     * instancia Usuario.
     */
    @Test
    public void getSalasTest() 
    {
        List<SalaEntity> list = teatroSalaLogic.getSalas(data.get(0).getId());
        Assert.assertEquals(1, list.size());
    }
    
    /**
     * Prueba para obtener una instancia de Reservas asociada a una instancia
     * Usuario.
     *
     * @throws co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException     
     */
    @Test
    public void getCriticoTest() throws BusinessLogicException 
    {
        TeatroEntity entity = data.get(0);
        SalaEntity salaEntity = salasData.get(0);
        SalaEntity response = teatroSalaLogic.getSala(entity.getId(), salaEntity.getId());
        
        Assert.assertEquals(salaEntity.getId(), response.getId());
        Assert.assertEquals(salaEntity.getNumero(), response.getNumero());
        Assert.assertEquals(salaEntity.getSillas(), response.getSillas());
    }
}
