/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import entities.User;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import io.swagger.v3.oas.integration.api.OpenAPIConfigBuilder;
import DTO.msg;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.cettia.Server;
import static io.cettia.ServerSocketPredicates.tag;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;

/**
 * REST Web Service
 *
 * @author Martin
 */
@Path("Users")
public class UsersResource {

    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    private static EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.CREATE);

    /**
     * Creates a new instance of ExampleResource
     */
    public UsersResource() {

    }
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Retrieves representation of an instance of rest.ExampleResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("user")
    @RolesAllowed("user")
    @Operation(summary = "A method that checks if signed in as user.",
            tags = {"Authentication"},
            description = "Returns a message if logged in as user ",
            responses = {
                @ApiResponse(description = "Response",
                        content = @Content(mediaType = "application/json",
                                schema = @Schema(implementation = msg.class))),
                @ApiResponse(responseCode = "403", description = "Not logged in as user"),
                @ApiResponse(responseCode = "200", description = "succes")
            })
    public String getFromUser() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to User: " + thisuser + "\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("admin")
    @RolesAllowed("admin")
    @Operation(summary = "A method that checks if signed in as Admin.",
            tags = {"Authentication"},
            description = "Returns a message if logged in as user ",
            responses = {
                @ApiResponse(description = "Response",
                        content = @Content(mediaType = "application/json",
                                schema = @Schema(implementation = msg.class))),
                @ApiResponse(responseCode = "403", description = "Not logged in as admin"),
                @ApiResponse(responseCode = "200", description = "succes")
            })
    public String getFromAdmin() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to (admin) User: " + thisuser + "\"}";
    }

    /**
     * PUT method for updating or creating an instance of ExampleResource
     *
     * @param content representation for the resource
     */
    @POST
    @Path("postExample")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Operation(summary = "A method that is a simple post example.",
            tags = {"POST"},
            description = "Returns a message if logged in as user ",
            responses = {
                @ApiResponse(description = "Response",
                        content = @Content(mediaType = "application/json",
                                schema = @Schema(implementation = msg.class))),
                @ApiResponse(responseCode = "403", description = "wrong parameter names"),
                @ApiResponse(responseCode = "200", description = "succes")
            })
    public String vote(@FormParam("param1") String v1,
            @FormParam("param2") String v2) {
        return "{\"msg\": \"Hello, you just posted" + v1 + "and" + v2 + "\"}";
    }


}
