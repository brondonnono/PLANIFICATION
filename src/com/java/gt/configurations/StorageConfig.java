/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.gt.configurations;

import java.io.File;
import java.io.IOException;

/**
 * @author Arléon Zemtsop
*/
public class StorageConfig {
    /**
     * Nom du dossier et du fichier de stockage des données par défaut
     */
    public static String DEFAULT_FOLDER_STORAGE_NAME = "data";
    public static String DEFAULT_FILE_STORAGE_NAME = "taskList.txt";
   //nom du fichier de stockage des historiques des taches 
    public static String DEFAULT_HISTORY_FILE_STORAGE_NAME = "history.txt";
    
    /**
     * @void
     * @param file 
     * Cette fonction permet de créer un dossier s'il n'existe pas au préalable
     */
    public static void createFolderIfNotExist(File file) {
        file.mkdirs();
    }
    
    /**
     * @void
     * @param file 
     * Cette fonction permet de créer un fichier s'il n'existe pas au préalable
     * On annule la création si le fichier existe déjà
     */
    public static void createFileIfNotExist(File file) {
        if(file.exists())
            return;
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch(IOException e) {}
        }
    }
}
