/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.gt.components;

import com.java.gt.beans.Notification;
import com.java.gt.store.CustomFileReader;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Brondon Nono
 */
public class NotificationModel extends AbstractTableModel {
    
        private final String[] entetes = {"Nº Tâche", "Type", "Temps", "Messages" };
        private ArrayList<Notification>  notificationList;
        
        public NotificationModel(){
            notificationList = new CustomFileReader().readFileDataNotification();
            //System.out.println("NotificationModel : "+ notificationList);
        }

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return entetes.length;
	}

        @Override
	public String getColumnName(int columnIndex) {
		return entetes[columnIndex];
	}
        
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return notificationList.size();
	}

        @Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		switch (columnIndex) {

                    case 0:
                        //id
                        return notificationList.get(rowIndex).getId();
                        
                    case 1:     
                        // Type
                        return notificationList.get(rowIndex).getType();

                    case 2:
                        // Temps
                        return notificationList.get(rowIndex).getCreatedAt();

                    case 3:
                        // Messages
                        return notificationList.get(rowIndex).getMessage();

                    default:
                        throw new IllegalArgumentException();
                }
	}
        
        @Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {                            
			case 2:
                            return Date.class;
                        case 0:
                        case 1:    
                        case 3:
                            return String.class;
	
			default:
                            return Object.class;
		}
	}

}
