package com.java.gt.threads;

import com.java.gt.controllers.store_controllers.StorageController;
import com.java.gt.configurations.StorageConfig;;

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

    public MainThread() {}

    public MainThread(StorageController storageController) {
        this.storageController = storageController;
    }

    /**
     * Cette fonction ajoute 2 seconde au temps de fonctionnement des tâches d'un équipement
     */
    @Override
	public void run() {
		while(true) {
            // Ce bout de code sera exécuté toutes les 2 secondes
            this.storageController.getFileWriter().renderOperatingTime(StorageConfig.DEFAULT_OPERATING_TIME_SAVE_DATA_FREQUENCY_IN_SECOND, this.storageController.getFileReader().getTaskList());
            this.storageController.getFileWriter().renderAllTasks(this.storageController.getFileReader().getTaskList());
           // System.out.println(this.storageController.getFileReader().getTaskList());
			try{
                Thread.sleep(StorageConfig.DEFAULT_OPERATING_TIME_SAVE_DATA_FREQUENCY_IN_SECOND * 1000);
			} catch(Exception e) {}
		}
	}

    public StorageController getStorageController() {
        return storageController;
    }

    public void setStorageController(StorageController storageController) {
        this.storageController = storageController;
    }
}
