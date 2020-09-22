/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.gt.store;

import com.java.gt.beans.Notification;
import com.java.gt.configurations.StorageConfig;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Brondon
 */
public class CustomFileReaderNotification {
    
    private File fileNotification;
    private ArrayList<Notification> notificationList = new ArrayList<Notification>();
    
    public CustomFileReaderNotification(){
        this.fileNotification = new File(StorageConfig.DEFAULT_FOLDER_STORAGE_NAME + "/" + StorageConfig.DEFAULT_NOTIFICATION_FILE_NAME);
        StorageConfig.createFileIfNotExist(this.fileNotification);
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
  
    public ArrayList<Notification> readFileDataNotification() {
        String fileName = this.fileNotification.getAbsolutePath();
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
    
    public ArrayList<Notification> getNotificationList() {
        System.out.println("getNotificationList :\n"+ this.notificationList);
        return this.notificationList;
    }
    
    public void setNotificationList(ArrayList<Notification> notificationList) {
        this.notificationList = notificationList;
    }
    
    public File getFileNotification() {
        return fileNotification;
    }

    public void setFileNotification(File fileNotification) {
        this.fileNotification = fileNotification;
    }
    
}
