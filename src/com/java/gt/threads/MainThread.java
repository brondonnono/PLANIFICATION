package com.java.gt.threads;

import com.java.gt.controllers.store_controllers.StorageController;

public class MainThread extends Thread {
    private StorageController storageController;

    public MainThread() {}

    public MainThread(StorageController storageController) {
        this.storageController = storageController;
    }

    @Override
	public void run() {
		while(true) {
            System.out.println("Daniela je t'aime :3 :3 :3 :3 !");
            this.storageController.getFileWriter().renderOperatingTime(2, this.storageController.getFileReader().getTaskList());
            this.storageController.getFileWriter().renderAllTasks(this.storageController.getFileReader().getTaskList());
            System.out.println(this.storageController.getFileReader().getTaskList());
			try{
                Thread.sleep(2000);
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
