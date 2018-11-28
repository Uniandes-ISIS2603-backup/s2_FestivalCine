/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.test.logic;

import co.edu.uniandes.csw.festivalcine.ejb.FestivalCriticoLogic;
import co.edu.uniandes.csw.festivalcine.ejb.FestivalLogic;



import co.edu.uniandes.csw.festivalcine.entities.CriticoEntity;
import co.edu.uniandes.csw.festivalcine.entities.FestivalEntity;


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

@RunWith(Arquillian.class)
public class FestivalCriticoLogicTest 
{
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private FestivalLogic festivalLogic;

     @Inject
    private FestivalCriticoLogic festivalCriticoLogic;
     
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;

    private List<FestivalEntity> data = new ArrayList<>();

    private List<CriticoEntity> criticosData = new ArrayList<>();

    
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
            CriticoEntity criticos = factory.manufacturePojo(CriticoEntity.class);
           
            em.persist(criticos);
             criticosData.add(criticos);
           
        }
        for (int i = 0; i < 3; i++) 
        {
            List<FestivalEntity> list = new ArrayList();
            FestivalEntity entity = factory.manufacturePojo(FestivalEntity.class);
            list.add(entity);
            em.persist(entity);
            data.add(entity);
            if (i == 0) 
            {
                criticosData.get(i).setFestivales(list);
            }
        }
    }
    
     /**
     * Prueba para asociar una reservas existente a un Usuario.
     */
    @Test
    public void addCriticosTest() 
    {
        FestivalEntity entity = data.get(0);
        CriticoEntity criticoEntity = criticosData.get(1);
        CriticoEntity response = festivalCriticoLogic.addCritico(criticoEntity.getId(), entity.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(criticoEntity.getId(), response.getId());
    }
    
    /**
     * Prueba para obtener una colección de instancias de Reservas asociadas a una
     * instancia Usuario.
     */
    @Test
    public void getCriticosTest() 
    {
        List<CriticoEntity> list = festivalCriticoLogic.getCriticos(data.get(0).getId());

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
        FestivalEntity entity = data.get(0);
        CriticoEntity criticoEntity = criticosData.get(0);
        CriticoEntity response = festivalCriticoLogic.getCritico(entity.getId(), criticoEntity.getId());

        Assert.assertEquals(criticoEntity.getId(), response.getId());
        Assert.assertEquals(criticoEntity.darNombres(), response.darNombres());
        Assert.assertEquals(criticoEntity.darApellidos(), response.darApellidos());
        Assert.assertEquals(criticoEntity.darIdentificacion(), response.darIdentificacion());
        
        Assert.assertEquals(criticoEntity.darEmail(), response.darEmail());
        
        
        Assert.assertEquals(criticoEntity.darPassword(), response.darPassword());
        Assert.assertEquals(criticoEntity.darPuntaje(), response.darPuntaje());
        
    }
}
