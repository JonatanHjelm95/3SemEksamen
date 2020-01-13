package facades;

import entities.Cargo;
import entities.Delivery;
import entities.Driver;
import entities.Truck;
import utils.EMF_Creator;
import entities.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import utils.Settings;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class DeliveryFacadeTest {

    private static EntityManagerFactory emf;
    private static DeliveryFacade facade;

    public DeliveryFacadeTest() {
    }

    //@BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactory(
                "pu",
                "jdbc:mysql://localhost:3307/EKSAMEN_test",
                "dev",
                "ax2",
                EMF_Creator.Strategy.CREATE);
        facade = DeliveryFacade.getDeliveryFacade(emf);
    }

    /*   **** HINT **** 
        A better way to handle configuration values, compared to the UNUSED example above, is to store those values
        ONE COMMON place accessible from anywhere.
        The file config.properties and the corresponding helper class utils.Settings is added just to do that. 
        See below for how to use these files. This is our RECOMENDED strategy
     */
    @BeforeAll
    public static void setUpClassV2() {
        emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST, Strategy.DROP_AND_CREATE);
        facade = DeliveryFacade.getDeliveryFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the script below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            //Trucks
            em.createNamedQuery("Truck.deleteAllRows").executeUpdate();
            em.persist(new Truck("TestTruck1", 5));
            em.persist(new Truck("TestTruck2", 5));
            em.persist(new Truck("TestTruck3", 5));
            //Drivers
            em.createNamedQuery("Driver.deleteAllRows").executeUpdate();
            em.persist(new Driver("TestDriver1"));
            em.persist(new Driver("TestDriver2"));
            //Deliveries
            em.createNamedQuery("Delivery.deleteAllRows").executeUpdate();
            em.persist(new Delivery("test", "test", "test"));
            //Cargo
            em.createNamedQuery("Cargo.deleteAllRows").executeUpdate();
            em.persist(new Cargo("TestCargo1", 0, 0));
            em.persist(new Cargo("TestCargo2", 0, 0));
            em.persist(new Cargo("TestCargo3", 0, 0));
            em.persist(new Cargo("TestCargo4", 0, 0));
            em.persist(new Cargo("TestCargo5", 0, 0));

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Truck.deleteAllRows").executeUpdate();
            em.createNamedQuery("Driver.deleteAllRows").executeUpdate();
            em.createNamedQuery("Delivery.deleteAllRows").executeUpdate();
            em.createNamedQuery("Cargo.deleteAllRows").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
//        Remove any data after each test was run
    }

    // TODO: Delete or change this method 
    @Test
    public void testAFacadeMethod() {
        assertEquals(3, facade.getTruckCount(), "Expects 3 rows in the database");
        assertEquals(2, facade.getDriverCount(), "Expects 2 rows in the database");
        assertEquals(1, facade.getDeliveryCount(), "Expects 1 rows in the database");
        assertEquals(5, facade.getCargoCount(), "Expects 5 rows in the database");

    }

}
