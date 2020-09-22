/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.gt.threads;

import com.java.gt.beans.Notification;
import com.java.gt.beans.Task;
import com.java.gt.components.NotificationCellRenderer;
import com.java.gt.configurations.StorageConfig;
import com.java.gt.controllers.MainController;
import com.java.gt.controllers.beans_controllers.EquipmentController;
import com.java.gt.views.Accueil;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Brondon
 */
public class NotificationThread extends Thread {
    private Notification notification = new Notification();
    private Accueil fen;
    private boolean isOK = false;
    public NotificationThread(Accueil fen){
        this.fen = fen;
    };
    
    
    
    @Override
    public void run() {
        while(true) {
            for(EquipmentController equipment:MainController.equipmentList){
                for(Task task :equipment.getStorageController().getFileReader().getTaskList()){
                    if(task.notifAlert()){
                        if(!this.notificationIsExist(fen.notificationList,task.getId(),equipment.getEquipment().getId())){
                            this.notification = new Notification(task.getId(),equipment.getEquipment().getId(),new Date(),task.getSecteur(),"Alert");
                            fen.notificationList.add(notification);                            
                            fen.getNotifModel().addRow(new Object[]{equipment.getEquipment().getName()+" -> "+task.getType(), notification.getType(), notification.getCreatedAt(), notification.getMessage()});                                
                            for(int i=0; i<4; i++)
                                fen.getTableAlert().getColumnModel().getColumn(i).setCellRenderer(new NotificationCellRenderer(fen.getTableAlert().getColumnName(i)));
                        } 
                    }
                }    
            }
            fen.getNotifModel().fireTableDataChanged();
            try{
                Thread.sleep(StorageConfig.DEFAULT_RENDER_NOTIFICATION_FREQUENCY_IN_SECOND * 1000);
            } catch(Exception e) {}	
        }
    }
    
    public boolean notificationIsExist(ArrayList<Notification>notificationList, int taskId, int equipmentId){
        for(Notification notif:notificationList)
            if(notif.getTaskId() == taskId && notif.getEquipmentId() == equipmentId)
                return true;
  
        return false;
    }
        
}
