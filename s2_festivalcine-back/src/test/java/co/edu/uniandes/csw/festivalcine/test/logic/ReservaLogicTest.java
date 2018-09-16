/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.festivalcine.test.logic;

import co.edu.uniandes.csw.festivalcine.ejb.ReservaLogic;
import co.edu.uniandes.csw.festivalcine.entities.ReservaEntity;
import co.edu.uniandes.csw.festivalcine.entities.SillaEntity;
import co.edu.uniandes.csw.festivalcine.entities.UsuarioEntity;
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
 * @author estudiante
 */
@RunWith(Arquillian.class)
public class ReservaLogicTest 
{
     private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private ReservaLogic reservaLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<ReservaEntity> data = new ArrayList<ReservaEntity>();

    private List<UsuarioEntity> usuarioData = new ArrayList();
    
    private List<SillaEntity> sillaData = new ArrayList();
    
    
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
        em.createQuery("delete from ReservaEntity").executeUpdate();
        em.createQuery("delete from UsuarioEntity").executeUpdate();
        em.createQuery("delete from SillaEntity").executeUpdate();
        em.createQuery("delete from FuncionEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() 
    {
        for (int i = 0; i < 3; i++) 
        {
            SillaEntity sillas = factory.manufacturePojo(SillaEntity.class);
            em.persist(sillas);
            sillaData.add(sillas);
        }
        for (int i = 0; i < 3; i++) 
        {
            UsuarioEntity usuario = factory.manufacturePojo(UsuarioEntity.class);
            em.persist(usuario);
            usuarioData.add(usuario);
        }
        for (int i = 0; i < 3; i++) 
        {
            ReservaEntity entity = factory.manufacturePojo(ReservaEntity.class);
            entity.setUsuario(usuarioData.get(0));
            entity.setSillas(sillaData);
            em.persist(entity);
            data.add(entity);
        }
    }
    
    private void insertaDataPruebasSilla() 
    {
        for (int i = 0; i < 3; i++) 
        {
            SillaEntity sillas = factory.manufacturePojo(SillaEntity.class);
            em.persist(sillas);
            sillaData.add(sillas);
        }
        for (int i = 0; i < 3; i++) 
        {
            ReservaEntity entity = factory.manufacturePojo(ReservaEntity.class);
            em.persist(entity);
            data.add(entity);
            if (i == 0) 
            {
                sillaData.get(i).setReserva(entity);
            }
        }
    }
    
    /**
     * Prueba para crear unA rESERVA
     *
     * @throws co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException
     */
    @Test
    public void createReservaTest() throws BusinessLogicException 
    {
        ReservaEntity newEntity = factory.manufacturePojo(ReservaEntity.class);
        newEntity.setUsuario(usuarioData.get(0));
        ReservaEntity result = reservaLogic.createReserva(newEntity);
        Assert.assertNotNull(result);
        ReservaEntity entity = em.find(ReservaEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
    }
    
    /**
     * Prueba para crear una reserva con Id inválido
     *
     * @throws co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createReservaTestConIdInvalido() throws BusinessLogicException {
        ReservaEntity newEntity = factory.manufacturePojo(ReservaEntity.class);
        newEntity.setUsuario(usuarioData.get(0));
        newEntity.setId((long)-922337203);
        reservaLogic.createReserva(newEntity);
    }
    
    /**
     * Prueba para crear una reserva con Id inválido
     *
     * @throws co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createReservaTestConIdInvalido2() throws BusinessLogicException {
        ReservaEntity newEntity = factory.manufacturePojo(ReservaEntity.class);
        newEntity.setUsuario(usuarioData.get(0));
        newEntity.setId(null);
        reservaLogic.createReserva(newEntity);
    }
    
     /**
     * Prueba para crear una reserva con Id inválido
     *
     * @throws co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createReservaTestConIdInexistente() throws BusinessLogicException {
        ReservaEntity newEntity = factory.manufacturePojo(ReservaEntity.class);
        newEntity.setUsuario(usuarioData.get(0));
        newEntity.setId(data.get(0).getId());
        reservaLogic.createReserva(newEntity);
    }
    
     /**
     * Prueba para crear un Reserva con un usuario que no existe.
     *
     * @throws co.edu.uniandes.csw.festival.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createReservaTestConUsuarioInexistente() throws BusinessLogicException {
        ReservaEntity newEntity = factory.manufacturePojo(ReservaEntity.class);
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId(Long.MAX_VALUE);
        newEntity.setUsuario(null);
        reservaLogic.createReserva(newEntity);
    }
    
    /**
     * Prueba para consultar la lista de Reservas.
     */
    @Test
    public void getReservasTest() {
        List<ReservaEntity> list = reservaLogic.getReservas();
        Assert.assertEquals(data.size(), list.size());
        for (ReservaEntity entity : list) 
        {
            boolean found = false;
            for (ReservaEntity storedEntity : data) 
            {
                if (entity.getId().equals(storedEntity.getId())) 
                {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    /**
     * Prueba para consultar una Reserva.
     */
    @Test
    public void getReservaTest() {
        ReservaEntity entity = data.get(0);
        ReservaEntity resultEntity = reservaLogic.getReserva(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getAbono(), resultEntity.getAbono());
        Assert.assertEquals(entity.getPrecioTotal(), resultEntity.getPrecioTotal());
        Assert.assertEquals(entity.getDescuento(), resultEntity.getDescuento());
    }
    
    /**
     * Prueba para actualizar una reserva.
     *
     * @throws co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException
     */
    @Test
    public void updateReservaTest() throws BusinessLogicException {
        ReservaEntity entity = data.get(0);
        ReservaEntity pojoEntity = factory.manufacturePojo(ReservaEntity.class);
        pojoEntity.setId(entity.getId());
        reservaLogic.updateReserva(pojoEntity.getId(), pojoEntity);
        ReservaEntity resp = em.find(ReservaEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getAbono(), resp.getAbono());
        Assert.assertEquals(pojoEntity.getPrecioTotal(), resp.getPrecioTotal());
        Assert.assertEquals(pojoEntity.getDescuento(), resp.getDescuento());
    }
    
    /**
     * Prueba para actualizar un Reserva con Id inválido.
     *
     * @throws co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateReservaConIdInvalidoTest() throws BusinessLogicException {
        ReservaEntity entity = data.get(0);
        ReservaEntity pojoEntity = factory.manufacturePojo(ReservaEntity.class);
        pojoEntity.setId(Long.MIN_VALUE);
        reservaLogic.updateReserva(pojoEntity.getId(), pojoEntity);
    }

    /**
     * Prueba para actualizar una Reserva con Id inválido.
     *
     * @throws co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateReservaConIdInvalidoTest2() throws BusinessLogicException 
    {
        ReservaEntity entity = data.get(0);
        ReservaEntity pojoEntity = factory.manufacturePojo(ReservaEntity.class);
        pojoEntity.setId(null);
        reservaLogic.updateReserva(pojoEntity.getId(), pojoEntity);
    }
    
    /**
     * Prueba para eliminar una reserva.
     *
     * @throws co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException
     */
    @Test
    public void deleteReservaTest() throws BusinessLogicException {
        ReservaEntity entity = data.get(0);
        reservaLogic.deleteReserva(entity.getId());
        ReservaEntity deleted = em.find(ReservaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    
    /**
     * No se realizan pruebas para eliminar una reserva si tiene silla, puesto que
     * pueden haber sillas sin reserva. En el ejemplo book, no pueden haber autores sin libros. 
     */
    
    /**
     * PRUEBAS DE LA RELACIÓN CON USUARIO
     */
    
    /**
     * Prueba para remplazar las instancias de Reservas asociadas a una instancia
     * de Usuario.
     */
    @Test
    public void replaceUsuarioTest() 
    {
        ReservaEntity entity = data.get(0);
        reservaLogic.replaceUsuario(entity.getId(), usuarioData.get(1).getId());
        entity = reservaLogic.getReserva(entity.getId());
        Assert.assertEquals(entity.getUsuario(), usuarioData.get(1));
    }

    /**
     * Prueba para desasociar una Reserva existente de un Usuario existente
     *
     * @throws co.edu.uniandes.csw.festivalcine.exceptions.BusinessLogicException
     */
    @Test
    public void removeReservasTest() throws BusinessLogicException 
    {
        reservaLogic.removeUsuario(data.get(0).getId());
        ReservaEntity response = reservaLogic.getReserva(data.get(0).getId());
        Assert.assertNull(response.getUsuario());
    }
    
    /**
     * PRUEBAS DE LA RELACIÓN CON SILLA
     */
    
    /**
     * Prueba para asociar un Silla existente a un Reserva.
     */
    //@Test
    public void addSillasTest() 
    {
        ReservaEntity entity = data.get(0);
        SillaEntity sillaEntity = sillaData.get(1);
        SillaEntity response = reservaLogic.addSilla(sillaEntity.getId(), entity.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(sillaEntity.getId(), response.getId());
    }
    
//     /**
//     * Prueba para obtener una colección de instancias de Sillas asociadas a una
//     * instancia Reserva.
//     */
//    @Test
//    public void getSillasTest() 
//    {
//        List<SillaEntity> list = reservaLogic.getSillas(data.get(0).getId());
//
//        Assert.assertEquals(1, list.size());
//    }
    
    
}
