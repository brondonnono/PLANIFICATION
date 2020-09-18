package com.java.gt.calendar;

import javax.swing.*;
import java.awt.event.*;

public class BoutonJour extends JButton{

  public CalendrierCadre pere;

  public BoutonJour(CalendrierCadre pere) {
     // definition du lien de parent�
     this.pere = pere;

     this.addActionListener(new java.awt.event.ActionListener()  {
        public void actionPerformed(ActionEvent e) {
          affiche();
        }
      }
    );
  }

  public void affiche() {
    pere.selectedOnClic(this);
  }
}
