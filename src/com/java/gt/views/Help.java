/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.gt.views;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 *
 * @author Brondon Nono
 */
public class Help extends JDialog{
    
    public Help(JFrame parent){
        super(parent);
        setTitle("Rubrique d'aide");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        //this.setBounds(300, 100, 500, 300);
        this.setSize(this.getToolkit().getScreenSize());
        this.setIconImage((new ImageIcon(getClass().getResource("/com/java/gt"
                + "/img/help.png"))).getImage());
    }
    
}
