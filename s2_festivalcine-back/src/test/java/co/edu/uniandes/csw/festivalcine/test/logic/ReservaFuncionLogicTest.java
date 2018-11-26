/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.test.logic;

import co.edu.uniandes.csw.festivalcine.ejb.FuncionLogic;
import co.edu.uniandes.csw.festivalcine.ejb.ReservaFuncionLogic;
import co.edu.uniandes.csw.festivalcine.ejb.ReservaLogic;
import co.edu.uniandes.csw.festivalcine.entities.FuncionEntity;
import co.edu.uniandes.csw.festivalcine.entities.ReservaEntity;
import co.edu.uniandes.csw.festivalcine.entities.SillaEntity;
import co.edu.uniandes.csw.festivalcine.entities.UsuarioEntity;
import co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.festivalcine.persistence.ReservaPersistence;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class ReservaFuncionLogicTest {
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private ReservaFuncionLogic reservaFuncionLogic;
    
     @Inject
    private FuncionLogic funcionLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<ReservaEntity> data = new ArrayList<ReservaEntity>();
    
    private ReservaEntity reserva = new ReservaEntity();

    private List<FuncionEntity> funcionData = new ArrayList();
    
    private UsuarioEntity usuario = new UsuarioEntity();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ReservaEntity.class.getPackage())
                .addPackage(FuncionEntity.class.getPackage())
                .addPackage(ReservaFuncionLogic.class.getPackage())
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
        em.createQuery("delete from FuncionEntity").executeUpdate();
        em.createQuery("delete from ReservaEntity").executeUpdate();
    }
    
    
    private void insertData() 
    {
        for (int i = 0; i < 3; i++) {
            usuario = factory.manufacturePojo(UsuarioEntity.class);
            em.persist(usuario);
        }
        
        reserva = factory.manufacturePojo(ReservaEntity.class);
        reserva.setId(1L);
        reserva.setFunciones(new ArrayList<>());
        em.persist(reserva);

        for (int i = 0; i < 3; i++) 
        {
            FuncionEntity entity = factory.manufacturePojo(FuncionEntity.class);
            entity.setReservas(new ArrayList<>());
            entity.getReservas().add(reserva);
            em.persist(entity);
            funcionData.add(entity);
            reserva.getFunciones().add(entity);
        }
    }
    
     /**
     * Prueba para asociar una funcion existente a una reserva.
     */
    @Test
    public void addFuncionTest() 
    {
        try 
        {
            FuncionEntity newFuncion = factory.manufacturePojo(FuncionEntity.class);
            funcionLogic.createFuncion(newFuncion);
            FuncionEntity funcionEntity = reservaFuncionLogic.addFuncion(reserva.getId(), newFuncion.getId());
            Assert.assertNotNull(funcionEntity);
            Assert.assertEquals(funcionEntity.getId(), newFuncion.getId());
            Assert.assertEquals(funcionEntity.getCritico(), newFuncion.getCritico());
        } 
        catch (BusinessLogicException ex)
        {
            Logger.getLogger(ReservaFuncionLogicTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     /**
     * Prueba para obtener una colección de instancias de Sillas asociadas a una
     * instancia Reserva.
     */
    @Test
    public void getFuncionesTest() 
    {
       List<FuncionEntity> funcionEntities = reservaFuncionLogic.getFunciones(reserva.getId());

        Assert.assertEquals(funcionData.size(), funcionEntities.size());

        for (int i = 0; i < funcionData.size(); i++) {
            Assert.assertTrue(funcionEntities.contains(funcionData.get(0)));
        }
    }
    
    /**
     * Prueba para obtener una instancia de sillas asociada a una instancia
     * reserva.
     *
     * @throws co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException
     */
    @Test
    public void getFuncionTest() throws BusinessLogicException 
    {
        FuncionEntity funcionEntity = funcionData.get(0);
        FuncionEntity funcion = reservaFuncionLogic.getFuncion(reserva.getId(), funcionEntity.getId());
        Assert.assertNotNull(funcion);

        Assert.assertEquals(funcionEntity.getId(), funcion.getId());
        Assert.assertEquals(funcionEntity.getCritico(), funcion.getCritico());
        Assert.assertEquals(funcionEntity.getPelicula(), funcion.getPelicula());
    }
    
    /**
     * Prueba desasociar un funcion con una reserva.
     *
     */
    @Test
    public void removeFuncionTest() throws BusinessLogicException 
    {  
            reservaFuncionLogic.removeFuncion(reserva.getId(), reserva.getFunciones().get(0).getId());
            Assert.assertTrue(reserva.getFunciones().get(0) == null);
           
    }
}
