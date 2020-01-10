/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.concurrent.Callable;

public class ProcessTask implements Callable<Integer> {

    private String cmds;
    private String name;

    public ProcessTask(String name, String cmds) {
        this.cmds = cmds;
        this.name = name;
    }

    @Override
    public Integer call() throws Exception {
        System.out.println("Started " + name);
        System.out.println("command: " + cmds);
        ProcessBuilder pb = new ProcessBuilder(cmds);
        pb.redirectErrorStream(true);
        Process p5 = pb.start();
        p5.waitFor();
        System.out.println("Completed " + name);
        return p5.exitValue();
    }

}
