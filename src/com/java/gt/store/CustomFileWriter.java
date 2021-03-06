package com.java.gt.store;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.java.gt.beans.Task;
import com.java.gt.configurations.StorageConfig;

/**
 * @author Arléon Zemtsop
 * Ce module permet de gérer toutes les opérations d'écriture
 * dans un fichier donné
*/
public class CustomFileWriter {
    // Définition des attributs
    // Définition du fichier
    private File file;
    // Définition du dossier
    private File folder;
    private String folderName;

    // Définition des constructeurs
    public CustomFileWriter(){}
    
    public CustomFileWriter(String folderName, String[] data){
        this.folderName = folderName;
        this.file = new File(StorageConfig.DEFAULT_FOLDER_STORAGE_NAME + "/" + folderName + "/" + StorageConfig.DEFAULT_HISTORY_FILE_STORAGE_NAME);
        this.folder = new File(StorageConfig.DEFAULT_FOLDER_STORAGE_NAME + "/" + folderName);
        StorageConfig.createFolderIfNotExist(this.folder);
        StorageConfig.createFileIfNotExist(this.file);
    }
    
    public CustomFileWriter(String[] data, String defaultFileName){
        this.file = new File(StorageConfig.DEFAULT_FOLDER_STORAGE_NAME + "/" + defaultFileName);
        StorageConfig.createFileIfNotExist(this.file);
    }

    public CustomFileWriter(String folderName) {
        this.folderName = folderName;
        this.file = new File(StorageConfig.DEFAULT_FOLDER_STORAGE_NAME + "/" + folderName + "/" + StorageConfig.DEFAULT_FILE_STORAGE_NAME);
        this.folder = new File(StorageConfig.DEFAULT_FOLDER_STORAGE_NAME + "/" + folderName);
        StorageConfig.createFolderIfNotExist(this.folder);
        StorageConfig.createFileIfNotExist(this.file);
    }

    public void saveData(String[] data) {
        String line = this.computeFileLine(data);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.file.getAbsolutePath(), true))) {
            writer.write(line);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        };
    }
    
    public void saveNotificationData(String[] data) {
        String line = this.computeFileLine(data);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.file.getAbsolutePath(), true))) {
            writer.write(line);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        };
    }
   
    public void saveTask(Task task) {
        String line = this.computeFileLine(task);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.file.getAbsolutePath(), true))) {
            writer.write(line);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        };
    }

    public String computeFileLine(Task task) {
        SimpleDateFormat DateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String lastMaintaintDate= DateFormat.format(task.getLastMaintaintDate());
        String line = task.getName() + "-" + task.getInterval() + "-" + task.getSecteur() 
            + "-" + lastMaintaintDate + "-" + task.getOperatingTime() 
            + "-" + task.getType();
        return line;
    }
    
    public String computeFileLine(String[] data) {
        return data[0]+ "-" + data[1] + "-" + data[2]+ "-"+ data[3]+ "-"+ data[4];
    }

    public String computeNotificationFileLine(String[] data) {
        return data[0]+ "-" + data[1] + "-" + data[2];
    }

    public void renderAllTasks(ArrayList<Task> newTaskList) {
        this.reinitilaliseFile();
        for(Task newTask: newTaskList) {
            this.saveTask(newTask);
        }
    }

    public void renderOperatingTime(int nbSecond, ArrayList<Task> taskList) {
        for(Task task: taskList) {
            task.setOperatingTime(task.getOperatingTime() + nbSecond);
        }
    }

    public void reinitilaliseFile() {
        if(this.file.exists())
            this.file.delete();
        if(!this.file.exists()){
            try {
                this.file.createNewFile();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void reinitilaliseFile(File file) {
        if(file.exists())
            file.delete();
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getFolderName() {
	return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public File getFile(){
        return file;
    }
    
    @Override
    public String toString() {
        return "CustomFileWriter{" + "file=" + file + ", folderName=" + folderName + '}';
    }
}