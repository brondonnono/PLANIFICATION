package com.java.gt.controllers.store_controllers;

import com.java.gt.beans.History;
import com.java.gt.beans.Task;
import com.java.gt.store.CustomFileReader;
import com.java.gt.store.CustomFileWriter;
import java.util.ArrayList;

/**
 * @author Arléon Zemtsop
 * Cette classe prend en charge les lectures et les
 * écritures dans les fichiers de tâches C'est ici
 * qu'on initialise la lecture et l'écriture dans le
 * fichier de tâche d'un équipement précis
 * 
*/
public class StorageController {
    // Module de lecture dans le fichier de tâches
    private CustomFileReader fileReader;
    // Module d'écriture dans le fichier de tâches
    private CustomFileWriter fileWriter;
    private String folderName;
    
    // Définition de la liste des tâches qui seront lues dépuis le fichier
    private ArrayList<Task> taskList;
    
    private ArrayList<History> historyList;

    // Définitions des constructeurs
    public StorageController() {}

    /**
     * @param folderName
     * Ce constructeur vas se charger d'initialiser 
     * la lecture et l'écriture dans le fichier de 
     * tâche de l'équipement sélectionné de manière
     * à lire toutes les tâches et à les initialiser
     * dès le lancement de l'application
     */
    public StorageController(String folderName) {
        this.folderName = folderName;
        this.fileReader = new CustomFileReader(folderName);
        this.taskList = new ArrayList<Task>();
       this.taskList = this.fileReader.readFileData();
       this.fileWriter = new CustomFileWriter(folderName);
    }

    // Définitions des getters and setters
    public CustomFileReader getFileReader() {
        return fileReader;
    }

    public void setFileReader(CustomFileReader fileReader) {
        this.fileReader = fileReader;
    }

    public CustomFileWriter getFileWriter() {
        return fileWriter;
    }

    public void setFileWriter(CustomFileWriter fileWriter) {
        this.fileWriter = fileWriter;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }
    
    public ArrayList<Task> getTaskList() {
    return taskList;
    }

    @Override
    public String toString() {
        return "StorageController{" + "fileReader=" + fileReader + ", fileWriter=" + fileWriter + ", folderName=" + folderName + '}';
    }
}