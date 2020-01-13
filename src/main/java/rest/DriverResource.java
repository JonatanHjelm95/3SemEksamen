package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entities.Delivery;
import entities.Driver;
import entities.Truck;
import entities.User;
import errorhandling.AuthenticationException;
import facades.DeliveryFacade;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import utils.EMF_Creator;
import utils.MyTypeAdapter;

/**
 * @author jonab
 */
@Path("drivers")
public class DriverResource {

    private static EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.CREATE);
    private static final DeliveryFacade FACADE = DeliveryFacade.getDeliveryFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getInfoForAll() {
        return "{\"msg\":\"Hello anonymous\"}";
    }

    @Path("count")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String driverCount() {
        int count = FACADE.getDriverCount();
        return GSON.toJson(count);

    }
    
    @Path("all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String allDrivers() {
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting()
               .registerTypeAdapter(Delivery.class, new MyTypeAdapter<Delivery>())
               .create();
        List drivers = FACADE.getAllDrivers();
        return gson.toJson(drivers);

    }
    
    @Path("driver/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getCargoById(@PathParam("id") long id) {
        return GSON.toJson(FACADE.findDriverById(id));
    }
    
    @Path("add")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String addDriver(String jsonString) throws AuthenticationException {
        try {
            JsonObject json = new JsonParser().parse(jsonString).getAsJsonObject();
            String name = json.get("name").getAsString();
            
            Driver driver = FACADE.addDriverToDB(new Driver(name));
            
            return GSON.toJson(driver);
        } catch (Exception e) {
            throw new AuthenticationException("Driver couldn't be added to the database");
        }

    }
    
    @Path("edit/{id}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String editDriver(String jsonString, @PathParam("id") long id) throws AuthenticationException {
        try {
            JsonObject json = new JsonParser().parse(jsonString).getAsJsonObject();
            String name = json.get("name").getAsString();
            
            Driver driver = FACADE.updateDriver(new Driver(name), id);
            
            return GSON.toJson(driver);
        } catch (Exception e) {
            throw new AuthenticationException("Driver does not exist database");
        }

    }
    
    @Path("delete/{id}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteDriver(@PathParam("id") long id) throws AuthenticationException {
        try {
            FACADE.deleteDriver(id);          
            return GSON.toJson("Succesfully deleted");
        } catch (Exception e) {
            throw new AuthenticationException("Driver does not exist database");
        }

    }

}
