package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entities.Delivery;
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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import static security.RegisterEndpoint.USER_FACADE;
import utils.EMF_Creator;
import utils.MyTypeAdapter;

/**
 * @author jonab
 */
@Path("trucks")
public class TruckResource {

    private static EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.CREATE);
    private static final DeliveryFacade FACADE = DeliveryFacade.getDeliveryFacade(EMF);
    private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
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
    public String truckCount() {
        int count = FACADE.getTruckCount();
        return GSON.toJson(count);

    }
    
    @Path("all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String allTrucks() {
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting()
               .registerTypeAdapter(Delivery.class, new MyTypeAdapter<Delivery>())
               .create();
        List trucks = FACADE.getAllTrucks();
        return gson.toJson(trucks);

    }
    
    @Path("truck/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getCargoById(@PathParam("id") long id) {
        return GSON.toJson(FACADE.findCargoById(id));
    }
    
    @Path("add")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String addTruck(String jsonString) throws AuthenticationException {
        try {
            JsonObject json = new JsonParser().parse(jsonString).getAsJsonObject();
            String name = json.get("name").getAsString();
            int capacity = Integer.parseInt(json.get("capacity").getAsString());
            
            Truck truck = FACADE.addTruckToDB(new Truck(name, capacity));
            
            return GSON.toJson(truck);
        } catch (Exception e) {
            throw new AuthenticationException("Truck couldn't be added to the database");
        }

    }
    
    @Path("edit/{id}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String editTruck(String jsonString, @PathParam("id") long id) throws AuthenticationException {
        try {
            JsonObject json = new JsonParser().parse(jsonString).getAsJsonObject();
            String name = json.get("name").getAsString();
            int capacity = Integer.parseInt(json.get("capacity").getAsString());
            
            Truck truck = FACADE.updateTruck(new Truck(name, capacity), id);
            
            return GSON.toJson(truck);
        } catch (Exception e) {
            throw new AuthenticationException("Truck does not exist database");
        }

    }
    
    @Path("delete/{id}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteTruck(@PathParam("id") long id) throws AuthenticationException {
        try {
            FACADE.deleteTruck(id);          
            return GSON.toJson("Succesfully deleted");
        } catch (Exception e) {
            throw new AuthenticationException("Truck does not exist database");
        }

    }

}
