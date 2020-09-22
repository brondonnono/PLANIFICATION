/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.gt.store;

import com.java.gt.beans.Notification;
import com.java.gt.configurations.StorageConfig;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 * @author Brondon
 */
public class CustomFileWriterNotification {
    private File fileNotification;
    
    public CustomFileWriterNotification(){
        //this.fileNotification = new File(StorageConfig.DEFAULT_FOLDER_STORAGE_NAME + "/" +StorageConfig.DEFAULT_NOTIFICATION_FILE_NAME);
       // StorageConfig.createFileIfNotExist(this.fileNotification);
    }
    
    
    public String computeNotificationFileLine(Notification notif) {
        SimpleDateFormat DateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String createdAt= DateFormat.format(notif.getCreatedAt());
        String line = notif.getId() + "-" + notif.getType() + "-" + createdAt 
            + "-"+ notif.getMessage();
        return line;
    }  
    
    public void saveNotificationData(Notification notif) {
        String line = this.computeNotificationFileLine(notif);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.fileNotification.getAbsolutePath(), true))) {
            writer.write(line);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        };
    }
  
    public void renderAllNotifications(ArrayList<Notification> newNotificationList, File file){
        this.reinitilaliseFileNotification();
        for(Notification newNotif: newNotificationList)
            this.saveNotificationData(newNotif);
    }

    public void reinitilaliseFileNotification() {
        if(this.fileNotification.exists())
            this.fileNotification.delete();
        if(!this.fileNotification.exists()){
            try {
                this.fileNotification.createNewFile();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
}
