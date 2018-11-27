/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.test.logic;

import co.edu.uniandes.csw.festivalcine.ejb.FuncionLogic;
import co.edu.uniandes.csw.festivalcine.entities.CriticoEntity;
import co.edu.uniandes.csw.festivalcine.entities.FuncionEntity;
import co.edu.uniandes.csw.festivalcine.entities.PeliculaEntity;
import co.edu.uniandes.csw.festivalcine.entities.ReservaEntity;
import co.edu.uniandes.csw.festivalcine.entities.SalaEntity;
import co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.festivalcine.persistence.FuncionPersistence;
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
 * @author María Juliana Moya
 */
@RunWith(Arquillian.class)
public class FuncionLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private FuncionLogic funcionLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<FuncionEntity> data = new ArrayList<>();
    
    private List<SalaEntity> salaData = new ArrayList<>();
    
    private List<PeliculaEntity> peliculaData = new ArrayList<>();
    
    private List<CriticoEntity> criticoData = new ArrayList<>();
    
    

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(FuncionEntity.class.getPackage())
                .addPackage(FuncionLogic.class.getPackage())
                .addPackage(FuncionPersistence.class.getPackage())
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
        em.createQuery("delete from PeliculaEntity").executeUpdate();
        em.createQuery("delete from SalaEntity").executeUpdate();
        em.createQuery("delete from CriticoEntity").executeUpdate();
        em.createQuery("delete from ReservaEntity").executeUpdate();
    }
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            PeliculaEntity pelicula = factory.manufacturePojo(PeliculaEntity.class);
            em.persist(pelicula);
            peliculaData.add(pelicula);
        }

        for (int i = 0; i < 3; i++) {
            SalaEntity sala = factory.manufacturePojo(SalaEntity.class);
            em.persist(sala);
            salaData.add(sala);
        }
        
        for (int i = 0; i < 3; i++) {
            CriticoEntity critico = factory.manufacturePojo(CriticoEntity.class);
            em.persist(critico);
            criticoData.add(critico);
        }

        for (int i = 0; i < 3; i++) {
            FuncionEntity entity = factory.manufacturePojo(FuncionEntity.class);
            entity.setPelicula(peliculaData.get(i));
            entity.setSala(salaData.get(i));
            if(i<2){
                entity.setCritico(criticoData.get(i));
            }
            em.persist(entity);
            data.add(entity);
        }
        
        FuncionEntity funcion = data.get(2);
        ReservaEntity entity = factory.manufacturePojo(ReservaEntity.class);
        entity.getFunciones().add(funcion);
        em.persist(entity);
        funcion.getReservas().add(entity);
        
    }
    
    /**
     * Prueba para crear una Función
     *
     * @throws BusinessLogicException
     */
    @Test
    public void createFuncionTest() throws BusinessLogicException 
    {
        FuncionEntity newEntity = factory.manufacturePojo(FuncionEntity.class);
        newEntity.setPelicula(peliculaData.get(0));
        newEntity.setSala(salaData.get(0));
        newEntity.setCritico(criticoData.get(0));
        FuncionEntity result = funcionLogic.createFuncion(newEntity);
        Assert.assertNotNull(result);
        FuncionEntity entity = em.find(FuncionEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getPrecioBase(), entity.getPrecioBase());
        Assert.assertEquals(newEntity.getFecha(), entity.getFecha());
        Assert.assertEquals(newEntity.getHora(), entity.getHora());
    }
    /**
     * Prueba para crear una funcion con Pelicula inválida
     * @throws co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createFuncionTestConPeliculaInvalida() throws BusinessLogicException {
        FuncionEntity newEntity = factory.manufacturePojo(FuncionEntity.class);
        PeliculaEntity pelicula = factory.manufacturePojo(PeliculaEntity.class);
        newEntity.setPelicula(pelicula);
        newEntity.setSala(salaData.get(0));
        newEntity.setCritico(criticoData.get(0));
        funcionLogic.createFuncion(newEntity);
    }
    
    /**
     * Prueba para crear una funcion con Sala inválida
     * @throws co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createFuncionTestConSalaInvalida() throws BusinessLogicException {
        FuncionEntity newEntity = factory.manufacturePojo(FuncionEntity.class);
        SalaEntity sala = factory.manufacturePojo(SalaEntity.class);
        newEntity.setPelicula(peliculaData.get(0));
        newEntity.setSala(sala);
        newEntity.setCritico(criticoData.get(0));
        funcionLogic.createFuncion(newEntity);
    }
    
    /**
     * Prueba para consultar la lista de Funciones.
     * BusinessLogicException
     */
    @Test
    public void getFuncionesTest() throws BusinessLogicException {
        List<FuncionEntity> list = funcionLogic.getFunciones();
        Assert.assertEquals(data.size(), list.size());
        for (FuncionEntity entity : list) {
            boolean found = false;
            for (FuncionEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    /**
     * Prueba para consultar una Funcion
     */
    @Test
    public void getFuncionTest() {
        FuncionEntity entity = data.get(0);
        FuncionEntity resultEntity = funcionLogic.getFuncion(data.get(0).getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(resultEntity.getPrecioBase(), entity.getPrecioBase());
        Assert.assertEquals(resultEntity.getFecha(), entity.getFecha());
        Assert.assertEquals(resultEntity.getHora(), entity.getHora());

    }
    
    /**
     * Prueba para actualizar una Funcion.
     * @throws co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException
     */
    @Test
    public void updateFuncionTest() throws BusinessLogicException {
        FuncionEntity entity = data.get(0);
        FuncionEntity pojoEntity = factory.manufacturePojo(FuncionEntity.class);
        pojoEntity.setId(entity.getId());
        pojoEntity.setPelicula(peliculaData.get(0));
        pojoEntity.setSala(salaData.get(0));
        funcionLogic.updateFuncion(pojoEntity.getId(), pojoEntity);

        FuncionEntity resp = em.find(FuncionEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getPrecioBase(), resp.getPrecioBase());
        Assert.assertEquals(pojoEntity.getFecha(), resp.getFecha());
        Assert.assertEquals(pojoEntity.getHora(), resp.getHora());
    }
    
    
    /**
     * Prueba para actualizar una Funcion con Pelicula inválida.
     *
     * @throws co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateFuncionConPeliculaInvalidaTest() throws BusinessLogicException {
        FuncionEntity entity = data.get(0);
        FuncionEntity pojoEntity = factory.manufacturePojo(FuncionEntity.class);
        
        PeliculaEntity pelicula = factory.manufacturePojo(PeliculaEntity.class);
        pojoEntity.setPelicula(pelicula);
        pojoEntity.setId(entity.getId());
        
        funcionLogic.updateFuncion(pojoEntity.getId(), pojoEntity);
    }
    
    /**
     * Prueba para actualizar una Funcion con Sala inválida.
     *
     * @throws co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateFuncionConSalaInvalidaTest() throws BusinessLogicException {
        FuncionEntity entity = data.get(0);
        FuncionEntity pojoEntity = factory.manufacturePojo(FuncionEntity.class);
        
        SalaEntity sala = factory.manufacturePojo(SalaEntity.class);
        pojoEntity.setSala(sala);
        pojoEntity.setId(entity.getId());
        
        funcionLogic.updateFuncion(pojoEntity.getId(), pojoEntity);
    }
    
    /**
     * Prueba para eliminar una Funcion.
     *
     * @throws co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException
     */
    @Test
    public void deleteFuncionTest() throws BusinessLogicException {
        FuncionEntity entity = data.get(0);
        funcionLogic.deleteFuncion(entity.getId());
        FuncionEntity deleted = em.find(FuncionEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    /**
     * Prueba para eliminar una Funcion con reservas
     *
     * @throws co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void deleteFuncionConReservasTest() throws BusinessLogicException {
        FuncionEntity entity = data.get(2);
        funcionLogic.deleteFuncion(entity.getId());
    }

    /**
     * Prueba para desasociar una Funcion existente de un Critico existente
     *
     * @throws co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException
     */
    @Test
    public void removeCriticoTest() throws BusinessLogicException 
    {
        funcionLogic.removeCritico(data.get(0).getId());
        FuncionEntity response = funcionLogic.getFuncion(data.get(0).getId());
        Assert.assertNull(response.getCritico());
    }
    
    
    /**
     * Prueba para desasociar un Critico de una Funcion que no lo tiene asociado
     *
     * @throws co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException
     */
    @Test (expected = BusinessLogicException.class)
    public void removeCriticoInvalidoTest() throws BusinessLogicException 
    {
        funcionLogic.removeCritico(data.get(2).getId());
        FuncionEntity response = funcionLogic.getFuncion(data.get(2).getId());
        Assert.assertNull(response.getCritico());
    }
    
}
