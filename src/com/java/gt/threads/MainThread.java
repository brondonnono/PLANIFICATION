package com.java.gt.threads;

import com.java.gt.controllers.store_controllers.StorageController;
import com.java.gt.configurations.StorageConfig;import com.java.gt.views.Accueil;
import java.util.logging.Level;
import java.util.logging.Logger;
;

/**
 * @author Arléon Zemtsop
 * Ce module les opérations s'exécutant en parallèle
 * au cours du fonctionnement du programme principal
 * Il s'agit entre autre des opérations de sauvegarde 
 * du temps d'exécution d'une tâche depuis sa dernière
 * maintenance
*/
public class MainThread extends Thread {
    private StorageController storageController;
    public static boolean[] SAFE_SAVES = {false,false,false,false};
    public MainThread() {}

    public MainThread(StorageController storageController) {
        this.storageController = storageController;
    }

    /**
     * Cette fonction ajoute 2 seconde au temps de fonctionnement des tâches d'un équipement
     */
    @Override
    public void run() {
        String equipmentName = this.storageController.getFolderName();
        while(!Accueil.SHOULD_STOP_APPLICATION) {
            // Ce bout de code sera exécuté toutes les 2 secondes
            switch(equipmentName){
                case "margeur": MainThread.SAFE_SAVES[0] = false;
                break;
                case "unite": MainThread.SAFE_SAVES[1] = false;
                break;
                case "reception": MainThread.SAFE_SAVES[2] = false;
                break;
                case "autres": MainThread.SAFE_SAVES[3] = false;
                break; 
                
                default : break;
            }
            this.storageController.getFileWriter().renderOperatingTime(StorageConfig.DEFAULT_OPERATING_TIME_SAVE_DATA_FREQUENCY_IN_SECOND, this.storageController.getFileReader().getTaskList());
            this.storageController.getFileWriter().renderAllTasks(this.storageController.getFileReader().getTaskList());
            try{
                Thread.sleep(StorageConfig.DEFAULT_OPERATING_TIME_SAVE_DATA_FREQUENCY_IN_SECOND * 1000);
            } catch(Exception e) {}
        }
        switch(equipmentName){
                case "margeur": MainThread.SAFE_SAVES[0] = true;
                break;
                case "unite": MainThread.SAFE_SAVES[1] = true;
                break;
                case "reception": MainThread.SAFE_SAVES[2] = true;
                break;
                case "autres": MainThread.SAFE_SAVES[3] = true;
                break; 
                
                default : break;
        }
        try {
            this.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(MainThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public StorageController getStorageController() {
        return storageController;
    }

    public void setStorageController(StorageController storageController) {
        this.storageController = storageController;
    }
}
