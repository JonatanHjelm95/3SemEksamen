/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

//import DTO.JsonArrayHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import errorhandling.ReeQuestException;

import errorhandling.ExceptionDTO;
import java.lang.annotation.ElementType;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import DTO.QueryDTO;
import facades.ProductFacade;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import utils.ReeQuest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import javax.ws.rs.core.MediaType;
import python.DbaWorker;
import python.GogWorker;
import utils.EMF_Creator;
import utils.ProcessTask;
import java.net.MalformedURLException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import python.PythonRunner;
import utils.ValidateUrls;

/**
 *
 * @author Niels Bang
 */
@Path("poormansgoogle")
public class GoogleResource {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    //ID of the custom search engine to use
    private static final String CX = "003739661964971794239:gvzfdunqtk6";
    private static final String CX2 = "003739661964971794239:gdyspwwocdq";
    private static final String CX3 = "002136386867548137828:8fyed999ueo";
    private static final String CX4 = "002136386867548137828:bj4nupmijjn";

    //API Key
    private static final String KEY = "AIzaSyBXm8bLJjCtmNHDZ17xLMzd9rhkpjauPQk";
    private static final String KEY2 = "e1eaeeeb0f804cbca10c195c61881545"; //Key to Microsoft Azure
    private static final String KEY3 = "AIzaSyDqo10PAAUk9-COUIAJ3YXsq0u55_O5op0";

    private final String URL = "https://www.googleapis.com";
    private final String PATH = "customsearch/v1";

    private final String URL2 = "https://api-eur.cognitive.microsofttranslator.com";
    private final String PATH2 = "translate";

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Poormansgoogle API\"}";
    }

    @Path("/{query}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String search(@PathParam("query") String query) throws Exception {
        List<JsonObject> jsons = new ArrayList();
        ExecutorService executor = Executors.newWorkStealingPool();
        Future<String> usedSearchFuture, ebaySearchFuture, amazonSearchFuture;

        Callable<String> usedSearch = () -> {
            try {
                ReeQuest req = new ReeQuest("hvad fuck bruger du source til martin??", URL);

                Map<String, String> params = new HashMap();
                params.put("cx", CX4);
                params.put("key", KEY3);
                params.put("q", query);

                return req.getRequest(PATH, params, "", null, "GET");

            } catch (ReeQuestException | MalformedURLException e) {
                return e.getMessage();
            }
        };

        Callable<String> ebaySearch = () -> {
            try {
                ReeQuest req = new ReeQuest("Jeg ved stadig ikke hvad du bruger den her til martin", URL2);
                String body = "[{\"Text\":\"" + query + "\"}]";

                HashMap<String, String> param1 = new HashMap();
                param1.put("api-version", "3.0");
                param1.put("from", "da");
                param1.put("to", "en");

                HashMap<String, String> headers = new HashMap();
                headers.put("Content-Type", "application/json;charset=UTF-8");
                headers.put("Ocp-Apim-Subscription-Key", KEY2);

                String translations = GSON.toJson(req.getRequest(PATH2, param1, body, headers, "POST"));

                String split = translations.split("text")[1].split("to")[0];
                String trans = split.substring(5, split.length() - 5);

                ReeQuest req2 = new ReeQuest("", URL);

                Map<String, String> param2 = new HashMap();
                param2.put("cx", CX2);
                param2.put("key", KEY);
                param2.put("q", trans);

                return req2.getRequest(PATH, param2, "", null, "GET");

            } catch (ReeQuestException | MalformedURLException e) {
                return e.getMessage();
            }
        };

        Callable<String> amazonSearch = () -> {
            try {

                ReeQuest req = new ReeQuest("Jeg ved stadig ikke hvad du bruger den her til martin", URL2);
                String body = "[{\"Text\":\"" + query + "\"}]";

                HashMap<String, String> param1 = new HashMap();
                param1.put("api-version", "3.0");
                param1.put("from", "da");
                param1.put("to", "en");

                HashMap<String, String> headers = new HashMap();
                headers.put("Content-Type", "application/json;charset=UTF-8");
                headers.put("Ocp-Apim-Subscription-Key", KEY2);

                String translations = GSON.toJson(req.getRequest(PATH2, param1, body, headers, "POST"));

                String split = translations.split("text")[1].split("to")[0];
                String trans = split.substring(5, split.length() - 5);

                ReeQuest req2 = new ReeQuest("æg og bacon", URL);

                Map<String, String> params = new HashMap();
                params.put("cx", CX3);
                params.put("key", KEY);
                params.put("q", trans);

                return req2.getRequest(PATH, params, "", null, "GET");

            } catch (ReeQuestException | MalformedURLException e) {
                return e.getMessage();
            }

        };

        usedSearchFuture = executor.submit(usedSearch);
        //ebaySearchFuture = executor.submit(ebaySearch);
        //amazonSearchFuture = executor.submit(amazonSearch);
//        jsons.add("[DBA & GUL OG GRATIS]");
        JsonObject usedJson = GSON.fromJson(usedSearchFuture.get(), JsonObject.class);
        JsonArray jsonItem = usedJson.getAsJsonArray("items");
        List<QueryDTO> urls = new ArrayList();
        for (int i = 0; i < jsonItem.size(); i++) {
            QueryDTO q = new QueryDTO(jsonItem.get(i).getAsJsonObject());
            urls.add(q);
            System.out.println(q.getUrl());
        }
        System.out.println("____");
        ValidateUrls validate = new ValidateUrls();
        List filteredUrls = validate.createLinks(query, urls);
        //RUNNING PYTHON SCRIPTS
        PythonRunner runner = new PythonRunner(filteredUrls, 5);
        runner.run();
        
        //ExecutorService service = Executors.newFixedThreadPool(filteredUrls.size());

//        for (int i = 0; i < filteredUrls.size(); i++) {
//            if (filteredUrls.get(i).toString().contains("www.dba.dk")) {
//                long runTimeSeconds = 5;
//                service.execute(new DbaWorker(filteredUrls.get(i).toString(), runTimeSeconds));
//                service.awaitTermination(runTimeSeconds, TimeUnit.SECONDS);
//
//            }
//            if (filteredUrls.get(i).toString().contains("www.guloggratis.dk")) {
//                long runTimeSeconds = 5;
//                service.execute(new GogWorker(filteredUrls.get(i).toString(), runTimeSeconds));
//                service.awaitTermination(runTimeSeconds, TimeUnit.SECONDS);
//            }
//        }
        ProductFacade pf = ProductFacade.getFacade(EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.CREATE));
        return GSON.toJson(pf.getProductsfromQuery(query));
    }
}
