package com.java.gt.controllers.beans_controllers;

import com.java.gt.beans.Task;

/**
 * @author Arléon Zemtsop
 * Cette classe contient l'ensemble des opérations 
 * à éffectuer sur les attribut d'une tâche précise
*/
public class TaskController {
    /**
     * @param task
     * @return int
     * retourne le pourcentage de progression
     * d'une tâche depuis sa dernière maintenance
     */
    public static int getPourcentage(Task task) {
        if(task.getOperatingTimeInHours() > task.getInterval())
            return 100;
        return Math.round(task.getOperatingTimeInHours()/task.getInterval() * 100);
    }
    /**
     * @param task
     * @return String
     * retourne le pourcentage de progression
     * d'une tâche depuis sa dernière maintenance
    */
    public static String displayPourcentage(Task task) {
        return TaskController.getPourcentage(task) + "%";
    }
}