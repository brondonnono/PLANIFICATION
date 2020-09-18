package com.java.gt.calendar;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import com.java.gt.views.Accueil;

public class CalendrierCadre extends JFrame{

  Color oldColor = null;
  BoutonJour oldJour = null;
  Accueil pere;
  int i = 0;
  ImageIcon imageArrowLeft;
  ImageIcon imageArrowRight;
  JLabel jLabelJour = new JLabel();
  JLabel jLabelMois = new JLabel();
  JLabel jLabelAnnee = new JLabel();
  // creation d'un tableau de jours
  BoutonJour [] jour = new BoutonJour [42];

  JButton jButtonPrecedent = new JButton();
  JButton jButtonSuivant = new JButton();
  JButton jButtonPrecAnnee = new JButton();
  JButton jButtonSuivAnnee = new JButton();

  TitledBorder titledBorder1;
  Border border1;
  public Calendrier cal = new Calendrier();

  /**Construire le cadre*/
  public CalendrierCadre(Accueil pere) {
    this.pere = pere;
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  /**Initialiser le composant*/
  private void jbInit() throws Exception  {
    imageArrowRight= new ImageIcon(com.java.gt.calendar.CalendrierCadre.class.getResource("images/arrow_right.gif"));
    imageArrowLeft= new ImageIcon(com.java.gt.calendar.CalendrierCadre.class.getResource("images/arrow_left.gif"));
    //setIconImage(Toolkit.getDefaultToolkit().createImage(AgendaCadre.class.getResource("[Votre ic�ne]")));

    border1 = BorderFactory.createLineBorder(SystemColor.text,2);

    this.setSize(new Dimension(300, 240));
    this.setTitle("Calendrier");

    jLabelJour.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabelJour.setText("Lun Mar Mer Jeu Ven Sam Dim");
    //jLabelJour.setBounds(new Rectangle(5, 60, 177, 17));
    jLabelJour.setBounds(new Rectangle(60, 81, 177, 17));

    // Initialisation des boutons des jours
    while(i<jour.length) {
      //for (int posy = 79; posy <= 184; posy+=21) {
      for (int posy = 100; posy <= 205; posy+=21) {
        //for (int posx = 4; posx <= 154; posx+=25) {
        for (int posx = 59; posx <= 209; posx+=25) {
          jour[i]= new BoutonJour(this);
          jour[i].setBorder(BorderFactory.createEtchedBorder());
          jour[i].setText("");
          jour[i].setBounds(new Rectangle(posx, posy, 25, 21));
          i++;
        }
      }
    }
    // Fin initialisation des boutons des jours


    // Mise a jour des numeros du com.java.gt.calendar
    updateCalendar();

    // Initialisation du label du mois
    jLabelMois.setBackground(SystemColor.inactiveCaptionBorder);
    jLabelMois.setFont(new java.awt.Font("Dialog", 1, 16));
    jLabelMois.setForeground(Color.black);
    jLabelMois.setBorder(BorderFactory.createLineBorder(Color.black));
    jLabelMois.setHorizontalAlignment(SwingConstants.CENTER);
    jLabelMois.setHorizontalTextPosition(SwingConstants.CENTER);
    //jLabelMois.setBounds(new Rectangle(28, 29, 119, 31));
    jLabelMois.setBounds(new Rectangle(83, 50, 119, 26));
    // Initialisation du label de l'annee
    jLabelAnnee.setBackground(SystemColor.inactiveCaptionBorder);
    jLabelAnnee.setFont(new java.awt.Font("Dialog", 1, 16));
    jLabelAnnee.setForeground(Color.black);
    jLabelAnnee.setBorder(BorderFactory.createLineBorder(Color.black));
    jLabelAnnee.setHorizontalAlignment(SwingConstants.CENTER);
    jLabelAnnee.setHorizontalTextPosition(SwingConstants.CENTER);
   //jLabelAnnee.setBounds(new Rectangle(51, 2, 75, 23));
    jLabelAnnee.setBounds(new Rectangle(106, 23, 75, 21));
    // Initialisation du bouton du mois precedent
    jButtonPrecedent.setBorder(null);
    jButtonPrecedent.setToolTipText("Mois precedent");
    jButtonPrecedent.setBorderPainted(false);
    jButtonPrecedent.setContentAreaFilled(false);
    jButtonPrecedent.setFocusPainted(false);
    jButtonPrecedent.setHorizontalTextPosition(SwingConstants.CENTER);
    jButtonPrecedent.setIcon(imageArrowLeft);
    //jButtonPrecedent.setBounds(new Rectangle(1, 33, 25, 27));
    jButtonPrecedent.setBounds(new Rectangle(56, 50, 25, 27));
    jButtonPrecedent.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonPrecedent_actionPerformed(e);
      }
    });
    // Initialisation du bouton du mois suivant
    jButtonSuivant.setBorder(null);
    jButtonSuivant.setToolTipText("Mois suivant");
    jButtonSuivant.setBorderPainted(false);
    jButtonSuivant.setContentAreaFilled(false);
    jButtonSuivant.setFocusPainted(false);
    jButtonSuivant.setHorizontalTextPosition(SwingConstants.CENTER);
    jButtonSuivant.setIcon(imageArrowRight);
    //jButtonSuivant.setBounds(new Rectangle(151, 33, 25, 27));
    jButtonSuivant.setBounds(new Rectangle(206, 50, 25, 27));
    jButtonSuivant.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonSuivant_actionPerformed(e);
      }
    });
    // Initialisation du bouton de l'annee precedente
    jButtonPrecAnnee.setBorder(null);
    jButtonPrecAnnee.setToolTipText("Annee precedente");
    jButtonPrecAnnee.setBorderPainted(false);
    jButtonPrecAnnee.setContentAreaFilled(false);
    jButtonPrecAnnee.setFocusPainted(false);
    jButtonPrecAnnee.setHorizontalTextPosition(SwingConstants.CENTER);
    jButtonPrecAnnee.setIcon(imageArrowLeft);
    //jButtonPrecAnnee.setBounds(new Rectangle(25, 2, 25, 27));
    jButtonPrecAnnee.setBounds(new Rectangle(80, 20, 25, 27));
    jButtonPrecAnnee.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonPrecAnnee_actionPerformed(e);
      }
    });
    // Initialisation du bouton de l'annee suivante
    jButtonSuivAnnee.setBorder(null);
    jButtonSuivAnnee.setToolTipText("Annee suivante");
    jButtonSuivAnnee.setBorderPainted(false);
    jButtonSuivAnnee.setContentAreaFilled(false);
    jButtonSuivAnnee.setFocusPainted(false);
    jButtonSuivAnnee.setHorizontalTextPosition(SwingConstants.CENTER);
    jButtonSuivAnnee.setIcon(imageArrowRight);
    //jButtonSuivAnnee.setBounds(new Rectangle(129, 2, 25, 27));
    jButtonSuivAnnee.setBounds(new Rectangle(184, 20, 25, 27));
    jButtonSuivAnnee.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonSuivAnnee_actionPerformed(e);
      }
    });
    
    pan_Cal = pere.getPan_Cal();
    
    for (int i = 0;i<jour.length;i++) {
      pan_Cal.add(jour[i], null);
    }

    pan_Cal.add(jButtonSuivant, null);
    pan_Cal.add(jButtonPrecedent, null);
    pan_Cal.add(jButtonSuivAnnee, null);
    pan_Cal.add(jButtonPrecAnnee, null);
    pan_Cal.add(jLabelMois, null);
    pan_Cal.add(jLabelJour, null);
    pan_Cal.add(jLabelAnnee, null);
    
    pere.setPan_Cal(pan_Cal);
  }

  void jButtonSuivant_actionPerformed(ActionEvent e) {
    cal.moisSuivant();
    cal.jour= 1;
    oldJour =null;
    updateCalendar();
  }

  void jButtonPrecedent_actionPerformed(ActionEvent e) {
    cal.moisPrecedent();
    cal.jour= 1;
    oldJour = null;
    updateCalendar();
  }

  void jButtonSuivAnnee_actionPerformed(ActionEvent e) {
    cal.AnneeSuivant();
    cal.jour= 1;
    oldJour = null;
    updateCalendar();
  }

  void jButtonPrecAnnee_actionPerformed(ActionEvent e) {
    cal.AnneePrecedent();
    cal.jour= 1;
    oldJour = null;
    updateCalendar();
  }

  void updateCalendar() {
    int a=1;
    String Lemois;
    String Lannee;

    Lemois = cal.TabMois[cal.mois];
    Lannee = String.valueOf(cal.annee);
    // Affichage du mois et de l'annee dans les labels
    jLabelMois.setText(Lemois);
    jLabelAnnee.setText(Lannee);
    // On ne met rien dans les cases avant le premier jour du mois
    int pjdm = cal.premierJour-1;
    if (pjdm==0) pjdm=7;
    while(a!=pjdm)
    {
      jour[a-1].setBackground(new Color(236, 233, 216));
      jour[a-1].setText("");
      a++;
    }
    // On remplit le com.java.gt.calendar
    for(int numJour=1;numJour<(cal.nbDayInMonth+1);numJour++)
    {
       // Si le jour correspond au jour actuel on le met le bouton d'une autre couleur ...
       if(numJour==cal.jourActuel && cal.mois==cal.moisActuel && cal.annee == cal.anneeActuel)
       {
          jour[a-1].setOpaque(true);
          jour[a-1].setBackground(new Color(220, 120, 255));
          jour[a-1].setText(String.valueOf(numJour));
       }
      else if(cal.calculJourFerie(numJour, cal.mois, cal.annee)) // ...sinon on regarde si le jour correspond a un jour ferie
       {
          jour[a-1].setBackground(new Color(166, 193, 238));
          jour[a-1].setText(String.valueOf(numJour));
       }
       else // ...sinon on met le bouton en couleur "control"
       {
          jour[a-1].setBackground(new Color(236, 233, 216));
          jour[a-1].setText(String.valueOf(numJour));
       }
       a++;
    }
    // On ne met rien dans les cases apres le dernier jour du mois
    while(a!=jour.length)
    {
       jour[a-1].setBackground(new Color(236, 233, 216));
       jour[a-1].setText("");
       a++;
    }
  }////// fin updateCalendar()

   void selectedOnClic(BoutonJour j) {
     if(j.getText().compareTo("")!=0) // on v�rifie qu'il y a bien un numero dans la case
     {
        if (oldJour != null) oldJour.setBackground(oldColor);
        oldColor = j.getBackground();
        j.setBackground(new Color(150, 240, 180));
        oldJour = j;
        cal.jour = new Integer(j.getText()).intValue();
     }
   }
   public JPanel pan_Cal; 
}
