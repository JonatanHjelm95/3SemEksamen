/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package python;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author jonab
 */
public class PythonRunner {

    ExecutorService service;
    long runTimeSeconds;
    List urls;

    public PythonRunner(List urls, long duration) {
        this.service = Executors.newFixedThreadPool(urls.size());
        this.runTimeSeconds = duration;
        this.urls = urls;
    }
    
    public void run() throws InterruptedException{
        for (int i = 0; i < urls.size(); i++) {
            if (urls.get(i).toString().contains("www.dba.dk")) {
                service.execute(new DbaWorker(urls.get(i).toString(), runTimeSeconds));
                service.awaitTermination(runTimeSeconds, TimeUnit.SECONDS);

            }
            if (urls.get(i).toString().contains("www.guloggratis.dk")) {
                service.execute(new GogWorker(urls.get(i).toString(), runTimeSeconds));
                service.awaitTermination(runTimeSeconds, TimeUnit.SECONDS);
            }
        }
    }
    
}
