/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.test.logic;

import co.edu.uniandes.csw.festivalcine.ejb.SalaLogic;
import co.edu.uniandes.csw.festivalcine.entities.FuncionEntity;
import co.edu.uniandes.csw.festivalcine.entities.SalaEntity;
import co.edu.uniandes.csw.festivalcine.entities.SillaEntity;
import co.edu.uniandes.csw.festivalcine.entities.TeatroEntity;
import co.edu.uniandes.csw.festivalcine.entities.UsuarioEntity;
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
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Clase que prueba SalaLogic
 * @author María Juliana Moya
 */
@RunWith(Arquillian.class)
public class SalaLogicTest {
      
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private SalaLogic salaLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;
    
    private List<SalaEntity> data = new ArrayList<>();
   private List<SalaEntity> sfdata = new ArrayList<>();
    

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
        em.createQuery("delete from SalaEntity").executeUpdate();
        em.createQuery("delete from FuncionEntity").executeUpdate();
        em.createQuery("delete from TeatroEntity").executeUpdate();
    }
    
    /**
     * Inserta los datos iniciales para el correcto salaamiento de las
     * pruebas.
     */
    private void insertData() {
        
        TeatroEntity teatro = factory.manufacturePojo(TeatroEntity.class);
        em.persist(teatro);
        FuncionEntity funcion = factory.manufacturePojo(FuncionEntity.class);
        List<FuncionEntity> lista = new ArrayList();
        lista.add(funcion);
        em.persist(funcion);
        for (int i = 0; i < 3; i++) 
        {
            SalaEntity sala = factory.manufacturePojo(SalaEntity.class);
            sala.setTeatro(teatro);
            sala.setFuncion(lista);
            em.persist(sala);
            data.add(sala);
        }
        for (int i = 0; i < 3; i++) 
        {
            SalaEntity sala = factory.manufacturePojo(SalaEntity.class);
            sala.setTeatro(teatro);
            em.persist(sala);
            sfdata.add(sala);
        }
        
    }
    
    /**
     * Prueba para crear una Función
     *
     * @throws BusinessLogicException
     */
    @Test
    public void createSalaTest() throws BusinessLogicException 
    {
        SalaEntity newEntity = factory.manufacturePojo(SalaEntity.class);
        newEntity.setTeatro(data.get(0).getTeatro());
        SalaEntity result = salaLogic.createSala(newEntity);
        Assert.assertNotNull(result);
        SalaEntity entity = em.find(SalaEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getNumero(), entity.getNumero());
    }
    
    /**
     * Prueba para consultar la lista de Salaes.
     * BusinessLogicException
     */
    @Test
    public void getSalasTest() throws BusinessLogicException 
    {
        List<SalaEntity> list = salaLogic.getSalas();
        int num = data.size() + sfdata.size();
        Assert.assertEquals(num, list.size());
        for (SalaEntity entity : list) {
            boolean found = false;
            for (SalaEntity storedEntity : data) 
            {
                if (entity.getId().equals(storedEntity.getId())) 
                {
                    found = true;
                }
            }
        }
    }
    
    /**
     * Prueba para consultar una Sala
     */
    @Test
    public void getSalaTest() 
    {
        SalaEntity entity = data.get(0);
        SalaEntity resultEntity = salaLogic.getSala(data.get(0).getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(resultEntity.getNumero(), entity.getNumero());
    }
    
    /**
     * Prueba para actualizar una Sala.
     * @throws co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException
     */
    @Test
    public void updateSalaTest() throws BusinessLogicException {
        SalaEntity entity = data.get(0);
        SalaEntity pojoEntity = factory.manufacturePojo(SalaEntity.class);
        pojoEntity.setId(entity.getId());
        salaLogic.updateSala(pojoEntity.getId(), pojoEntity);

        SalaEntity resp = em.find(SalaEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getNumero(), resp.getNumero());
    }
    
    /**
     * Prueba para eliminar una Sala.
     *
     * @throws co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException
     */
    @Test
    public void deleteSalaTest() throws BusinessLogicException 
    {
        SalaEntity deleted = em.find(SalaEntity.class, sfdata.get(0));
        salaLogic.deleteSala(deleted.getId());
        Assert.assertNull(salaLogic.getSala(deleted.getId()));
    }
    
    /**
     * Prueba para eliminar una Sala con función
     *
     * @throws co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void deleteSalaConFuncionesTest() throws BusinessLogicException {
        SalaEntity entity = data.get(0);       
        salaLogic.deleteSala(entity.getId());
   }
}
