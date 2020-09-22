/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.gt.components;

import java.awt.Color;
import java.awt.Component;
import java.util.Date;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Brondon Nono
 */
public class NotificationCellRenderer extends DefaultTableCellRenderer {
    
    private String elem;
    
    public NotificationCellRenderer(String elem){
        this.elem = elem;
    }
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        if(elem.equalsIgnoreCase("type")){
            String type = (String) value;
            setText(type);
            if(type.equals("Alert"))
                setBackground(Color.red);
            else
                setBackground(Color.yellow);
            
        }       
      
        return this;
    }

}
