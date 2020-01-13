package facades;

import DTO.DeliveryInfo;
import entities.Cargo;
import entities.Delivery;
import entities.Driver;
import entities.Role;
import entities.Truck;
import entities.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import errorhandling.AuthenticationException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import utils.EMF_Creator;

/**
 * @author jonab
 */
public class DeliveryFacade {

    private static EntityManagerFactory emf;
    private static DeliveryFacade instance;

    private DeliveryFacade() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static DeliveryFacade getDeliveryFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new DeliveryFacade();
        }
        return instance;
    }

    public Cargo findCargoById(long id) {
        EntityManager em = emf.createEntityManager();
        return em.find(Cargo.class, id);
    }

    public Truck findTruckById(long id) {
        EntityManager em = emf.createEntityManager();
        return em.find(Truck.class, id);
    }

    public Driver findDriverById(long id) {
        EntityManager em = emf.createEntityManager();
        return em.find(Driver.class, id);
    }

    public Truck getTruck(String name) throws Exception {
        EntityManager em = emf.createEntityManager();
        Truck truck;
        try {
            truck = em.find(Truck.class, name);
            if (truck == null) {
                throw new Exception("A truck with that name doesn't exist in the database");
            }
        } finally {
            em.close();
        }
        return truck;
    }

    public int getTruckCount() {
        EntityManager em = emf.createEntityManager();
        try {
            List<Truck> trucks = em.createQuery("select truck from Truck truck").getResultList();
            return trucks.size();
        } finally {
            em.close();
        }
    }

    public int getCargoCount() {
        EntityManager em = emf.createEntityManager();
        try {
            List<Cargo> cargos = em.createQuery("select c from Cargo c").getResultList();
            return cargos.size();
        } finally {
            em.close();
        }
    }

    public List getAllTrucks() {
        EntityManager em = emf.createEntityManager();
        try {
            Query trucks = em.createNamedQuery("Truck.findAll");
            return trucks.getResultList();
        } finally {
            em.close();
        }

    }
    public List getAllCargo() {
        EntityManager em = emf.createEntityManager();
        try {
            Query cargos = em.createNamedQuery("Cargo.findAll");
            return cargos.getResultList();
        } finally {
            em.close();
        }

    }

    public List getAllDrivers() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Driver> query = em.createQuery("Select d from Driver d", Driver.class);

            return query.getResultList();
        } finally {
            em.close();
        }

    }

    public int getDriverCount() {
        EntityManager em = emf.createEntityManager();
        try {
            List<Driver> drivers = em.createQuery("select d from Driver d").getResultList();
            return drivers.size();
        } finally {
            em.close();
        }
    }

    public Driver addDriverToDB(Driver driver) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(driver);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return driver;
    }

    public Truck addTruckToDB(Truck truck) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(truck);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return truck;
    }

    public Truck updateTruck(Truck truck, long id) {
        EntityManager em = emf.createEntityManager();
        Truck t = em.find(Truck.class, id);
        try {
            em.getTransaction().begin();
            t.setName(truck.getName());
            t.setCapacity(t.getCapacity());
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return t;
    }
    
    public Driver updateDriver(Driver driver, long id) {
        EntityManager em = emf.createEntityManager();
        Driver d = em.find(Driver.class, id);
        try {
            em.getTransaction().begin();           
            d.setName(driver.getName());
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return d;
    }
    
    public Cargo updateCargo(Cargo cargo, long id) {
        EntityManager em = emf.createEntityManager();
        Cargo c = em.find(Cargo.class, id);
        try {
            em.getTransaction().begin();           
            c.setName(cargo.getName());
            c.setWeight(cargo.getWeight());
            c.setUnits(cargo.getUnits());
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return c;
    }
    
    public Delivery updateDelivery(Delivery delivery, long id, List<Cargo> cargos, Truck truck, Driver driver) {
        EntityManager em = emf.createEntityManager();
        Delivery d = em.find(Delivery.class, id);
        try {
            em.getTransaction().begin();           
            d.setShippingDate(delivery.getShippingDate());
            d.setFromLocation(delivery.getFromLocation());
            d.setDestination(delivery.getDestination());
            if(cargos != null){
                d.setCargos(cargos);
            }
            if(truck != null){
                d.setTruck(truck);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return d;
    }


    public void deleteTruck(long id) {
        EntityManager em = emf.createEntityManager();
        Truck t = em.find(Truck.class, id);
        try {
            em.getTransaction().begin();
            em.createNativeQuery("SET GLOBAL FOREIGN_KEY_CHECKS=0;").executeUpdate();
            em.remove(t);
            em.createNativeQuery("SET GLOBAL FOREIGN_KEY_CHECKS=1;").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
        
    

    public void deleteDriver(long id) {
        EntityManager em = emf.createEntityManager();
        Driver d = em.find(Driver.class, id);
        try {
            em.getTransaction().begin();
            em.createNativeQuery("SET GLOBAL FOREIGN_KEY_CHECKS=0;").executeUpdate();
            em.remove(d);
            em.createNativeQuery("SET GLOBAL FOREIGN_KEY_CHECKS=1;").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void deleteCargo(long id) {
        EntityManager em = emf.createEntityManager();
        Cargo c = em.find(Cargo.class, id);
        try {
            em.getTransaction().begin();
            em.createNativeQuery("SET GLOBAL FOREIGN_KEY_CHECKS=0;").executeUpdate();
            em.remove(c);
            em.createNativeQuery("SET GLOBAL FOREIGN_KEY_CHECKS=1;").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    
    public void deleteDelivery(long id) {
        EntityManager em = emf.createEntityManager();
        Delivery d = em.find(Delivery.class, id);
        try {
            em.getTransaction().begin();
            em.createNativeQuery("SET GLOBAL FOREIGN_KEY_CHECKS=0;").executeUpdate();
            em.remove(d);
            em.createNativeQuery("SET GLOBAL FOREIGN_KEY_CHECKS=1;").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    

//    public void removeFKConstraints() {
//        EntityManager em = emf.createEntityManager();
//        try {
//            em.getTransaction().begin();
//            em.createNativeQuery("SET GLOBAL FOREIGN_KEY_CHECKS=0;").executeUpdate();
//            em.getTransaction().commit();
//        } finally {
//            em.close();
//        }
//    }
//
//    public void enableFKConstraints() {
//        EntityManager em = emf.createEntityManager();
//        try {
//            em.getTransaction().begin();
//            em.createNativeQuery("SET GLOBAL FOREIGN_KEY_CHECKS=1;").executeUpdate();
//            em.getTransaction().commit();
//        } finally {
//            em.close();
//        }
//    }

    public Cargo addCargoToDB(Cargo cargo) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(cargo);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return cargo;
    }

    public int getDeliveryCount() {
        EntityManager em = emf.createEntityManager();
        try {
            List<Delivery> deliveries = em.createQuery("select d from Delivery d").getResultList();
            return deliveries.size();
        } finally {
            em.close();
        }
    }

    public List getAllDeliveries() {
        EntityManager em = emf.createEntityManager();
        try {
            //Query deliveries = em.createNamedQuery("Delivery.findAll");
            TypedQuery<Delivery> query = em.createQuery("Select d from Delivery d", Delivery.class);

            return query.getResultList();
        } finally {
            em.close();
        }

    }

    public String addDelivery(Delivery delivery, Driver driver, Truck truck, List<Cargo> cargos) {
        EntityManager em = emf.createEntityManager();

        driver.addTruck(truck);
        //truck.addDriver(driver);
        delivery.addCargo(cargos);
        delivery.addTruck(truck);

        List<Delivery> deliveries = new ArrayList();
        deliveries.add(delivery);
        truck.addDeliveries(deliveries);

        try {
            em.getTransaction().begin();
            
            em.persist(delivery);

            em.getTransaction().commit();
        } finally {
            em.close();
        }

        return "Delivery succesfully added";
    }

    public List getDeliveryInfo(long id) {
        EntityManager em = emf.createEntityManager();
        try {
            Query q = em.createNativeQuery("SELECT * FROM CARGO c JOIN DELIVERY_CARGO dc on c.ID=dc.cargos_ID \n"
                    + "JOIN DELIVERY d on dc.Delivery_ID=d.ID \n"
                    + "WHERE d.ID = (?)");
            q.setParameter(1, id);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public static void main(String[] args) throws AuthenticationException, Exception {
        DeliveryFacade df = DeliveryFacade.getDeliveryFacade(EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.CREATE));
//        Driver driver = new Driver("Peter");
//        Truck truck = new Truck("Peter truck", 40);
//
//        String from = "Solvej 24";
//        String toAddress = "Kim Larsens Vej 100";
//
//        Cargo ananas = new Cargo("Ananas", 1, 20);
//        Cargo oksekød = new Cargo("Oksekød", 2, 10);
//        List<Cargo> cargos = new ArrayList();
//        cargos.add(ananas);
//        cargos.add(oksekød);
//        Date date = Calendar.getInstance().getTime();
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
//        String strDate = dateFormat.format(date);
//        Delivery delivery = new Delivery(strDate, from, toAddress);
//
//        df.addDelivery(driver, truck, cargos, delivery);
//        List trucks = df.getAllTrucks();
        //Truck t = df.deleteTruck(2);
        //System.out.println(t.toString());
        //df.removeFKConstraints();

        df.deleteDelivery(6);
    }

}
