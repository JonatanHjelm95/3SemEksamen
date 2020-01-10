/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package python;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import rest.GoogleResource;

/**
 *
 * @author jonab
 */
public class DbaWorker implements Runnable {

    String command;
    String url;
    long runTimeSeconds;

    public DbaWorker(String url, long runtime) {
        this.command = "python C:\\Users\\jonab\\.ssh\\3Sem\\Genbrugsen\\webscraper\\dbaWebscraper.py ";
        this.url = url;
        this.runTimeSeconds = runtime;
    }

    @Override
    public void run() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = runtime.exec(command + url);
            System.out.println(process.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(GoogleResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            System.out.println("script terminated after "+runTimeSeconds+" seconds");
        }
    }

}
