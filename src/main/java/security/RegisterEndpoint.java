package security;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import facades.UserFacade;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import entities.User;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import errorhandling.AuthenticationException;
import errorhandling.GenericExceptionMapper;
import javax.persistence.EntityManagerFactory;
import static javax.ws.rs.client.Entity.json;
import utils.EMF_Creator;

@Path("register")
public class RegisterEndpoint {

    public static final int TOKEN_EXPIRE_TIME = 1000 * 60 * 30; //30 min
    //private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.CREATE);
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory("pu", null, null, null, EMF_Creator.Strategy.CREATE);
    public static final UserFacade USER_FACADE = UserFacade.getUserFacade(EMF);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(String jsonString) throws AuthenticationException {
        try {
            JsonObject json = new JsonParser().parse(jsonString).getAsJsonObject();
            String username = json.get("username").getAsString();
            String password = json.get("password").getAsString();
            User user = USER_FACADE.registerUser(username, password, "user");
            JsonObject responseJson = new JsonObject();
            responseJson.addProperty("username", username);
            return Response.ok(new Gson().toJson(responseJson)).build();
        } catch (Exception e) {
            throw new AuthenticationException("Username already exists in database, try again");
        }

    }
    //  @POST
    //  @Consumes(MediaType.APPLICATION_JSON)
    //  @Produces(MediaType.APPLICATION_JSON)
    //  public Response register(String jsonString) throws AuthenticationException {
    //    JsonObject json = new JsonParser().parse(jsonString).getAsJsonObject();
    //    String username = json.get("username").getAsString();
    //    String password = json.get("password").getAsString();
    //    try {
    //      User user = USER_FACADE.registerUser(username, password);
    ////      String token = createToken(username, user.getRolesAsStrings());
    ////      JsonObject responseJson = new JsonObject();
    ////      responseJson.addProperty("username", username);
    ////      responseJson.addProperty("token", token);
    //      //return Response.ok(new Gson().toJson(responseJson)).build();
    //
    //    } catch (JOSEException | AuthenticationException ex) {
    //      if (ex instanceof AuthenticationException) {
    //        throw (AuthenticationException) ex;
    //      }
    //      Logger.getLogger(GenericExceptionMapper.class.getName()).log(Level.SEVERE, null, ex);
    //    }
    //    throw new AuthenticationException("user already exists in the database");
    //  }
    //

    private String createToken(String userName, List<String> roles) throws JOSEException {

        StringBuilder res = new StringBuilder();
        for (String string : roles) {
            res.append(string);
            res.append(",");
        }
        String rolesAsString = res.length() > 0 ? res.substring(0, res.length() - 1) : "";
        String issuer = "semesterstartcode-dat3";

        JWSSigner signer = new MACSigner(SharedSecret.getSharedKey());
        Date date = new Date();
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(userName)
                .claim("username", userName)
                .claim("roles", rolesAsString)
                .claim("issuer", issuer)
                .issueTime(date)
                .expirationTime(new Date(date.getTime() + TOKEN_EXPIRE_TIME))
                .build();
        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
        signedJWT.sign(signer);
        return signedJWT.serialize();

    }
}
