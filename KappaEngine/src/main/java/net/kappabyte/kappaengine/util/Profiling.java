/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.kappabyte.kappaengine.util;

import java.util.HashMap;

/**
 *
 * @author 903336001
 */
public final class Profiling {

    private static HashMap<String, Long> startTime = new HashMap<>();

    private static long threshold = 20;

    public static void startTimer(String timerName) {
        startTime.remove(timerName);
        startTime.put(timerName, System.currentTimeMillis());
    }

    public static long stopTimer(String timerName) {
        long diff = System.currentTimeMillis() - startTime.remove(timerName);
        if(diff > threshold) {
            Log.debug(timerName + " | Time taken " + diff + "ms.");
        }
        return diff;
    }
}
