/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.kappabyte.kappaengine.util;

/**
 *
 * @author 903336001
 */
public final class Profiling {
    
    private static long startTime = 0;
    
    private static long threshold = 20;
    
    public static void startTimer() {
        startTime = System.currentTimeMillis();
    }
    
    public static long stopTimer(String name) {
        long currentTime = System.currentTimeMillis();
        long diff = currentTime - startTime;
        if(diff > threshold) {
            Log.debug(name + " | Time taken " + diff + "ms.");
        }
        return diff;
    }
}
