package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entities.Cargo;
import entities.Delivery;
import entities.Driver;
import entities.Truck;
import entities.User;
import errorhandling.AuthenticationException;
import facades.DeliveryFacade;
import java.util.ArrayList;
import utils.MyTypeAdapter;
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

/**
 * @author jonab
 */
@Path("deliveries")
public class DeliveryResource {

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
    public String deliveryCount() {
        int count = FACADE.getDeliveryCount();
        return GSON.toJson(count);

    }

    @Path("all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String allDeliveries() {
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting()
                .registerTypeAdapter(Delivery.class, new MyTypeAdapter<Delivery>())
                .create();
        List deliveries = FACADE.getAllDeliveries();
        return gson.toJson(deliveries);
    }

    @Path("cargo/all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String allCargo() {
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting()
                .registerTypeAdapter(Cargo.class, new MyTypeAdapter<Cargo>())
                .create();
        List cargos = FACADE.getAllCargo();
        return gson.toJson(cargos);
    }

    @Path("cargo/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getCargoById(@PathParam("id") long id) {
        return GSON.toJson(FACADE.findCargoById(id));
    }

    @Path("cargo/add")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String addCargo(String jsonString) throws AuthenticationException {
        try {
            JsonObject json = new JsonParser().parse(jsonString).getAsJsonObject();
            String name = json.get("name").getAsString();
            double weight = json.get("weight").getAsDouble();
            int unit = json.get("unit").getAsInt();
            Cargo c = new Cargo(name, weight, unit);

            Cargo cargo = FACADE.addCargoToDB(c);

            return GSON.toJson(cargo);
        } catch (Exception e) {
            throw new AuthenticationException("Cargo couldn't be added to the database");
        }

    }

    @Path("cargo/edit/{id}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String editCargo(String jsonString, @PathParam("id") long id) throws AuthenticationException {
        try {
            JsonObject json = new JsonParser().parse(jsonString).getAsJsonObject();
            String name = json.get("name").getAsString();
            double weight = json.get("weight").getAsDouble();
            int unit = json.get("unit").getAsInt();
            Cargo c = new Cargo(name, weight, unit);;

            Cargo cargo = FACADE.updateCargo(c, id);

            return GSON.toJson(cargo);
        } catch (Exception e) {
            throw new AuthenticationException("Cargo does not exist database");
        }

    }

    @Path("cargo/delete/{id}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteCargo(@PathParam("id") long id) throws AuthenticationException {
        try {
            FACADE.deleteCargo(id);
            return GSON.toJson("Succesfully deleted");
        } catch (Exception e) {
            throw new AuthenticationException("Cargo does not exist database");
        }

    }

    @Path("deliveryinfo/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getDeliveryInfo(@PathParam("id") long id) {
        return GSON.toJson(FACADE.getDeliveryInfo(id));
    }

    @Path("add")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String addDelivery(String jsonString) throws AuthenticationException {
        try {
            JsonObject json = new JsonParser().parse(jsonString).getAsJsonObject();
            String shippingDate = json.get("shippingDate").getAsString();
            String fromLocation = json.get("fromLocation").getAsString();
            String toLocation = json.get("toLocation").getAsString();
            Delivery d = new Delivery(shippingDate, fromLocation, toLocation);

            long driverID = json.get("driverID").getAsLong();
            long cargoID = json.get("cargoID").getAsLong();
            long truckID = json.get("truckID").getAsLong();

            Cargo cargo = FACADE.findCargoById(cargoID);
            List<Cargo> cargos = new ArrayList();
            cargos.add(cargo);

            Truck truck = FACADE.findTruckById(truckID);
            Driver driver = FACADE.findDriverById(driverID);

            return FACADE.addDelivery(d, driver, truck, cargos);

        } catch (Exception e) {
            throw new AuthenticationException("Delivery couldn't be added to the database");
        }

    }

    @Path("edit/{id}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String editDelivery(String jsonString, @PathParam("id") long id) throws AuthenticationException {
        try {
            JsonObject json = new JsonParser().parse(jsonString).getAsJsonObject();
            String shippingDate = json.get("shippingDate").getAsString();
            String fromLocation = json.get("fromLocation").getAsString();
            String toLocation = json.get("toLocation").getAsString();
            Delivery d = new Delivery(shippingDate, fromLocation, toLocation);

            long driverID = json.get("driverID").getAsLong();
            long cargoID = json.get("cargoID").getAsLong();
            long truckID = json.get("truckID").getAsLong();

            Driver driver = null;
            List<Cargo> cargos = null;
            Truck truck = null;

            if (cargoID != 0) {
                Cargo cargo = FACADE.findCargoById(cargoID);
                cargos = new ArrayList();
                cargos.add(cargo);
            }
            if (truckID != 0) {

                truck = FACADE.findTruckById(truckID);
            }

            if (truckID != 0) {
                driver = FACADE.findDriverById(driverID);
            }
            Delivery delivery = FACADE.updateDelivery(d, id, cargos, truck, driver);

            return GSON.toJson(delivery);
        } catch (Exception e) {
            throw new AuthenticationException("Delivery does not exist database");
        }

    }

    @Path("delete/{id}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteDelivery(@PathParam("id") long id) throws AuthenticationException {
        try {
            FACADE.deleteDelivery(id);
            return GSON.toJson("Succesfully deleted");
        } catch (Exception e) {
            throw new AuthenticationException("Delivery does not exist database");
        }

    }

}
