/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.gt.views;

import java.awt.HeadlessException;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 *
 * @author Brondon
 */
public class AlertClosingFe extends JOptionPane{
    
    public AlertClosingFe(){
        super("Closing");
        this.createDialog("Closing");
        JOptionPane.showOptionDialog(null, "Closing", "Closing title", JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
    }

    @Override
    public JDialog createDialog(String title) throws HeadlessException {
        return super.createDialog(title); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
