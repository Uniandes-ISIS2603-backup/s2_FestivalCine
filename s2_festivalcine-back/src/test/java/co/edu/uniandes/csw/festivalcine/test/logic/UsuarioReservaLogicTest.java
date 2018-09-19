/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.test.logic;

import co.edu.uniandes.csw.festivalcine.ejb.UsuarioLogic;
import co.edu.uniandes.csw.festivalcine.ejb.UsuarioReservasLogic;
import co.edu.uniandes.csw.festivalcine.entities.CalificacionEntity;
import co.edu.uniandes.csw.festivalcine.entities.ReservaEntity;
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

/**
 *
 * @author PAULA VELANDIIA
 */
@RunWith(Arquillian.class)
public class UsuarioReservaLogicTest 
{
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private UsuarioLogic usuarioLogic;

     @Inject
    private UsuarioReservasLogic usuarioReservaLogic;
     
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;

    private List<UsuarioEntity> data = new ArrayList<UsuarioEntity>();

    private List<ReservaEntity> reservasData = new ArrayList<ReservaEntity>();

    
     /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
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
        em.createQuery("delete from ReservaEntity").executeUpdate();
        em.createQuery("delete from UsuarioEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) 
        {
            ReservaEntity reservas = factory.manufacturePojo(ReservaEntity.class);
            em.persist(reservas);
            reservasData.add(reservas);
        }
        for (int i = 0; i < 3; i++) 
        {
            UsuarioEntity entity = factory.manufacturePojo(UsuarioEntity.class);
            em.persist(entity);
            data.add(entity);
            if (i == 0) 
            {
                reservasData.get(i).setUsuario(entity);
            }
        }
    }
    
    /**

    /**
     * Prueba para asociar una reservas existente a un Usuario.
     */
    @Test
    public void addReservasTest() 
    {
        UsuarioEntity entity = data.get(0);
        ReservaEntity reservaEntity = reservasData.get(1);
        ReservaEntity response = usuarioReservaLogic.addReserva(reservaEntity.getId(), entity.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(reservaEntity.getId(), response.getId());
    }
    
    /**
     * Prueba para obtener una colección de instancias de Reservas asociadas a una
     * instancia Usuario.
     */
    @Test
    public void getReservasTest() 
    {
        List<ReservaEntity> list = usuarioReservaLogic.getReservas(data.get(0).getId());

        Assert.assertEquals(1, list.size());
    }
    
    /**
     * Prueba para obtener una instancia de Reservas asociada a una instancia
     * Usuario.
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test
    public void getReservaTest() throws BusinessLogicException 
    {
        UsuarioEntity entity = data.get(0);
        ReservaEntity reservaEntity = reservasData.get(0);
        ReservaEntity response = usuarioReservaLogic.getReserva(entity.getId(), reservaEntity.getId());

        Assert.assertEquals(reservaEntity.getId(), response.getId());
        Assert.assertEquals(reservaEntity.getAbono(), response.getAbono());
        Assert.assertEquals(reservaEntity.getPrecioTotal(), response.getPrecioTotal());
        Assert.assertEquals(reservaEntity.getDescuento(), response.getDescuento());
    }
}
