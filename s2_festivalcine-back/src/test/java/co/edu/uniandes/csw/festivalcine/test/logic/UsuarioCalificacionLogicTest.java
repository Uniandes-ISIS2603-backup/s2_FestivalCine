package co.edu.uniandes.csw.festivalcine.test.logic;


import co.edu.uniandes.csw.festivalcine.ejb.UsuarioCalificacionesLogic;
import co.edu.uniandes.csw.festivalcine.ejb.UsuarioLogic;
import co.edu.uniandes.csw.festivalcine.entities.CalificacionEntity;
import co.edu.uniandes.csw.festivalcine.entities.UsuarioEntity;
import co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.festivalcine.persistence.UsuarioPersistence;
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author PAULA VELANDIA
 */
@RunWith(Arquillian.class)
public class UsuarioCalificacionLogicTest 
{
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private UsuarioLogic usuarioLogic;
    
    @Inject
    private UsuarioCalificacionesLogic usuarioCalificacionLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<UsuarioEntity> data = new ArrayList<UsuarioEntity>();

    private List<CalificacionEntity> calificacionesData = new ArrayList();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() 
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(UsuarioEntity.class.getPackage())
                .addPackage(UsuarioLogic.class.getPackage())
                .addPackage(UsuarioPersistence.class.getPackage())
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
        em.createQuery("delete from CalificacionEntity").executeUpdate();
        em.createQuery("delete from UsuarioEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() 
    {   
        for (int i = 0; i < 3; i++) {
            CalificacionEntity calificaciones = factory.manufacturePojo(CalificacionEntity.class);
            em.persist(calificaciones);
            calificacionesData.add(calificaciones);
        }
        for (int i = 0; i < 3; i++) {
            UsuarioEntity entity = factory.manufacturePojo(UsuarioEntity.class);
            em.persist(entity);
            data.add(entity);
            if (i == 0) {
                calificacionesData.get(i).setUsuario(entity);
            }
        }
    }
    
    /**
     * PRUEBAS DE LA RELACIÓN CON CALIFICACION
     */
     /**
     * Prueba para asociar una califiacion existente a un Usuario.
     */
    @Test
    public void addCalifiacionesTest() 
    {
        UsuarioEntity entity = data.get(0);
        CalificacionEntity calificacionEntity = calificacionesData.get(1);
        CalificacionEntity response = usuarioCalificacionLogic.addCalificacion(calificacionEntity.getId(), entity.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(calificacionEntity.getId(), response.getId());
    }
    
    /**
     * Prueba para obtener una colección de instancias de Reservas asociadas a una
     * instancia Usuario.
     */
    @Test
    public void getCalificacionesTest() 
    {
        List<CalificacionEntity> list = usuarioCalificacionLogic.getCalificaciones(data.get(0).getId());
        Assert.assertEquals(1, list.size());
    }
    
    /**
     * Prueba para obtener una instancia de Reservas asociada a una instancia
     * Usuario.
     *
     * @throws co.edu.uniandes.csw.calificacion.exceptions.BusinessLogicException
     */
    @Test
    public void getCalificacionTest() throws BusinessLogicException 
    {
        UsuarioEntity entity = data.get(0);
        CalificacionEntity calificacionEntity = calificacionesData.get(0);
        CalificacionEntity response = usuarioCalificacionLogic.getCalificacion(entity.getId(), calificacionEntity.getId());

        Assert.assertEquals(calificacionEntity.getId(), response.getId());
        Assert.assertEquals(calificacionEntity.getPuntaje(), response.getPuntaje());
        Assert.assertEquals(calificacionEntity.getComentario(), response.getComentario());
        Assert.assertEquals(calificacionEntity.getUsuario(), response.getUsuario());
    }
}
