package com.java.gt.store;

import com.java.gt.beans.History;
import com.java.gt.beans.Notification;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.java.gt.beans.Task;
import com.java.gt.configurations.StorageConfig;

/**
 * @author Arléon Zemtsop
 * Ce module permet de gérer toutes les opérations de lecture 
 * dans un fichier donné
*/
public class CustomFileReader {
    // Définition des attributs
    // Définition du fichier
    private File file,notificationsFile = new File(StorageConfig.DEFAULT_FOLDER_STORAGE_NAME + "/" + StorageConfig.DEFAULT_NOTIFICATION_FILE_NAME);;
    // Définition du dossier
    private File folder;
    private String folderName;
    // Définition de la liste des tâches qui seront lues dépuis le fichier
    private ArrayList<Task> taskList = new ArrayList<Task>();
    private ArrayList<Notification> notificationList = new ArrayList<Notification>();


    public CustomFileReader(){}

    /**
     * @param folderName 
     * Ce constructeur permet de créer dynamiquement le fichier
     * de tâche que l'on souhaite manipuler. Le non du dossier
     * dans lequel est stocké le fichier de tâches correspond au
     * nom de l'équipement etr ce dossier est créé s'il n'existe pas
     */
    public CustomFileReader(String folderName) {
        this.folderName = folderName;
        this.taskList = new ArrayList<Task>();
        this.file = new File(StorageConfig.DEFAULT_FOLDER_STORAGE_NAME + "/" + folderName + "/" + StorageConfig.DEFAULT_FILE_STORAGE_NAME);
        this.folder = new File(StorageConfig.DEFAULT_FOLDER_STORAGE_NAME + "/" + folderName);
        this.notificationsFile = new File(StorageConfig.DEFAULT_FOLDER_STORAGE_NAME + "/" + StorageConfig.DEFAULT_NOTIFICATION_FILE_NAME);
        StorageConfig.createFolderIfNotExist(this.folder);
        StorageConfig.createFileIfNotExist(this.file);
        StorageConfig.createFileIfNotExist(this.notificationsFile);
    }
    /**
     * @param attributeList
     * @param index 
     * Cette fonction permet de créer une tâche à partir d'une ligne du fichié
     * lue au préalable
     */
    public void computeTask(String[] attributeList, int index) {
        String name = attributeList[0];
        int interval = Integer.parseInt(attributeList[1]);
        String secteur = attributeList[2];
        Date lastMaintaintDate = null; 
        try {
            lastMaintaintDate = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").parse(attributeList[3]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int operatingTime = Integer.parseInt(attributeList[4]);
        String type =  attributeList[5];
        Task t = new Task(index, name, interval, secteur, lastMaintaintDate, operatingTime, type);
        this.taskList.add(t);
    }
    

    public void computeNotification(String[] attributeList) {
        Date createdAt = null; 
        try {
            createdAt = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").parse(attributeList[2]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int id = 0;
         try{
            id = Integer.parseInt(attributeList[0]);
        }catch(NumberFormatException e){}
        String type = attributeList[1];
        String message = attributeList[3];  
        Notification n = new Notification(id, type, createdAt, message);
        this.notificationList.add(n);
    }
     
   /**
     * @return ArrayList<Task>
     * Cette fonction parcours le fichier et le lit lignes après lignes
     * dans le but de construire un tableau de tâches à partir de chaque
     * ligne du fichier
     */
    public ArrayList<Task> readFileData() {
        String fileName = this.file.getAbsolutePath();
        Path path = Paths.get(fileName);
        try {
            if(!Files.readAllLines(path).isEmpty()) {      
                int index = 1;
                for(String line: Files.readAllLines(path)) {
                    String[] attributeList = line.split("-");
                    if(attributeList.length > 0) {
                        this.computeTask(attributeList, index);
                    }
                    index++;
                }
                this.displayTasks();
                return this.taskList;
            }
        } catch(IOException e) {} 
        return new ArrayList<Task>();
    }


    public ArrayList<Notification> readFileDataNotification() {
        String fileName = this.notificationsFile.getAbsolutePath();
        Path path = Paths.get(fileName);
        try {
            if(!Files.readAllLines(path).isEmpty()) {      
                for(String line: Files.readAllLines(path)) {
                    String[] attributeList = line.split("-");
                    if(attributeList.length > 0) {
                        this.computeNotification(attributeList);
                    }
                }
    //            for(Notification notif: this.notificationList)
   //                 System.out.println("notification: " + notif.toString());
                return this.notificationList;
            }
        } catch(IOException e) {} 
        return new ArrayList<Notification>();
    }
    
    /**
     * Afficher toutes les tâches luent dans la console
     */
    public void displayTasks() {
        if(this.taskList.size() > 0) {
            this.taskList.forEach((task) -> {
            });
        }
    }

    // Définition des getters and setters
    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }
        
    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }
    
    
    public ArrayList<Notification> getNotificationList() {
        System.out.println("getNotificationList :\n"+ this.notificationList);
        return this.notificationList;
    }
    
    public void setNotificationList(ArrayList<Notification> notificationList) {
        this.notificationList = notificationList;
    }

    @Override
    public String toString() {
        return "CustomFileReader [file=" + file + ", folder=" + folder
                + ", folderName=" + folderName + ", notificationList="
                + notificationList + ", notificationsFile=" + notificationsFile + ", taskList=" + taskList + "]";
    }
}