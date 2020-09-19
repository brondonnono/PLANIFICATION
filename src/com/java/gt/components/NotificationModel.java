/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.gt.components;

import com.java.gt.beans.Notification;
import com.java.gt.store.CustomFileReader;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Brondon Nono
 */
public class NotificationModel extends AbstractTableModel {
    
        private final String[] entetes = {"Type", "Temps", "Messages" };
        private ArrayList<Notification>  notificationList;
        
        public NotificationModel(){
            notificationList = new CustomFileReader().readFileDataNotification();
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
                            // Type
                            return notificationList.get(rowIndex).getType();
                            
                    case 1:
                            // Temps
                            return notificationList.get(rowIndex).getCreatedAt();

                    case 2:
                            // Messages
                            return notificationList.get(rowIndex).getMessage();

                    default:
                            throw new IllegalArgumentException();
                }
	}
        
        @Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
			case 0:
			case 1:
                        case 2:
				return String.class;
	
			default:
				return Object.class;
		}
	}

}
