package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import utils.EMF_Creator;
import facades.ProductFacade;
import io.cettia.Server;
import static io.cettia.ServerSocketPredicates.tag;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import utils.CettiaBootstrap;

@Path("products")
public class ProductResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
            "pu",
            "jdbc:mysql://localhost:3307/GENBRUGSEN",
            "dev",
            "ax2",
            EMF_Creator.Strategy.CREATE);
    private static final ProductFacade FACADE = ProductFacade.getFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        Server S = CettiaBootstrap.getServer();
        Map<String, Object> output = new LinkedHashMap<>();
        output.put("sender", "products/");
        output.put("text", "Enpoint called");
        S.find(tag("channel:log")).send("message", output);
        return "{\"msg\":\"Hello World\"}";
    }

    @Path("count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getProductsCount() {
        Server S = CettiaBootstrap.getServer();
        Map<String, Object> output = new LinkedHashMap<>();
        output.put("sender", "products/count");
        output.put("text", "Enpoint called");
        S.find(tag("channel:log")).send("message", output);
        long count = FACADE.getProductCount();
        return "{\"count\":" + count + "}";  //Done manually so no need for a DTO
    }

    @Path("all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllProducts() {
        Server S = CettiaBootstrap.getServer();
        Map<String, Object> output = new LinkedHashMap<>();
        output.put("sender", "products/all");
        output.put("text", "Enpoint called");
        S.find(tag("channel:log")).send("message", output);
        List products = FACADE.getAllProducts();
        return GSON.toJson(products);
    }

}
