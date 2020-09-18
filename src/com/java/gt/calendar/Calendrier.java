package com.java.gt.calendar;

import java.util.*;

public class Calendrier {
  public String [] TabMois = {"Janvier","Fevrier","Mars","Avril","Mai","Juin", "Juillet","Aout","Septembre","Octobre","Novembre","Decembre"};
  public String [] TabJour = {"jour incorrect","Dimanche","Lundi","Mardi","Mercredi","Jeudi","Vendredi","Samedi"};
  public int jourActuel;  // jour actuel
  public int moisActuel;  // mois actuel
  public int anneeActuel;  //annee actuelle
  public int jourSem; // jour de la semaine
  public int jour; // jour sur lequel on se situe...
  public int mois; // mois sur lequel on se situe apres avoir clique sur un des boutons
  public int annee; // annee sur lequel on se situe apres avoir clique sur un des boutons
  public int premierJour;  // premierJour du mois
  public int nbDayInMonth; // nbre de jour dans le mois actuel
  //cr�ation de l'objet GregorianCalendar
  private GregorianCalendar calendrier = new GregorianCalendar();

  public Calendrier() {
    // Initialisation de la date actuelle j/M/A
    jourActuel = jour = calendrier.get(Calendar.DAY_OF_MONTH);
    jourSem = calendrier.get(Calendar.DAY_OF_WEEK);
    moisActuel = mois = calendrier.get(Calendar.MONTH);
    anneeActuel = annee = calendrier.get(Calendar.YEAR);
    nbDayInMonth = calendrier.getActualMaximum(Calendar.DAY_OF_MONTH);
    premierJourDuMois();
  }

  private void premierJourDuMois()
  {
    // R�cup�ration du premier jour du mois dans DAY_OF_MONTH
    calendrier.set(Calendar.DAY_OF_MONTH,1);
    premierJour = calendrier.get(Calendar.DAY_OF_WEEK);
    // On r�initialise DAY_OF_MONTH au jour courant
    calendrier.set(Calendar.DAY_OF_MONTH,jourActuel);
  }

  public void moisSuivant()
  {
    calendrier.set(Calendar.DAY_OF_MONTH,1);
    jour = calendrier.get(Calendar.DAY_OF_MONTH);
    calendrier.set(Calendar.MONTH,mois+1);
    mois = calendrier.get(Calendar.MONTH);
    nbDayInMonth = calendrier.getActualMaximum(Calendar.DAY_OF_MONTH);
    annee = calendrier.get(Calendar.YEAR);
    premierJourDuMois();
  }

  public void moisPrecedent()
  {
    calendrier.set(Calendar.DAY_OF_MONTH,1);
    jour = calendrier.get(Calendar.DAY_OF_MONTH);
    calendrier.set(Calendar.MONTH,mois-1);
    mois = calendrier.get(Calendar.MONTH);
    nbDayInMonth = calendrier.getActualMaximum(Calendar.DAY_OF_MONTH);
    annee = calendrier.get(Calendar.YEAR);
    premierJourDuMois();
  }

  public void AnneeSuivant()
  {
    calendrier.set(Calendar.DAY_OF_MONTH,1);
    jour = calendrier.get(Calendar.DAY_OF_MONTH);
    calendrier.set(Calendar.YEAR,annee+1);
    mois = calendrier.get(Calendar.MONTH);
    nbDayInMonth = calendrier.getActualMaximum(Calendar.DAY_OF_MONTH);
    annee = calendrier.get(Calendar.YEAR);
    premierJourDuMois();

  }

  public void AnneePrecedent()
  {
    calendrier.set(Calendar.DAY_OF_MONTH,1);
    jour = calendrier.get(Calendar.DAY_OF_MONTH);
    calendrier.set(Calendar.YEAR,annee-1);
    mois = calendrier.get(Calendar.MONTH);
    nbDayInMonth = calendrier.getActualMaximum(Calendar.DAY_OF_MONTH);
    annee = calendrier.get(Calendar.YEAR);
    premierJourDuMois();
  }

  boolean calculJourFerie(int j, int m, int a)
  {
    boolean resultat = false;
    double jf1, jf2, jf3, jf4, jf5, jf6;
    int jfj, jfm;
    GregorianCalendar jourACompare = new GregorianCalendar();
    // Declaration de tous les jours feries
    GregorianCalendar nvelleAn = new GregorianCalendar();
    GregorianCalendar paques = new GregorianCalendar();
    GregorianCalendar lunpaq = new GregorianCalendar();
    GregorianCalendar feteTravail = new GregorianCalendar();
    GregorianCalendar victoire1945 = new GregorianCalendar();
    GregorianCalendar ascension = new GregorianCalendar();
    GregorianCalendar lunpent = new GregorianCalendar();
    GregorianCalendar feteNational = new GregorianCalendar();
    GregorianCalendar asomption = new GregorianCalendar();
    GregorianCalendar toussaint = new GregorianCalendar();
    GregorianCalendar armistice = new GregorianCalendar();
    GregorianCalendar noel = new GregorianCalendar();
    // Declaration du tableau contenant tous les jours feries
    GregorianCalendar [] jourFerie = {nvelleAn,paques,lunpaq,feteTravail,ascension,
                                    lunpent,victoire1945,feteNational,asomption,
                                    toussaint,armistice,noel};
    // Initialisation du jour a compare
    jourACompare.set(a, m, j);
    // Initilisation des jours feries
    nvelleAn.set(a, 0 , 1);
    feteTravail.set(a, 4, 1);
    victoire1945.set(a, 4, 8);
    feteNational.set(a, 6, 14);
    asomption.set(a, 7, 15);
    toussaint.set(a, 10, 1);
    armistice.set(a, 10, 11);
    noel.set(a, 11, 25);
    /* Calcul des jours feries (paques lunpaq ascension lunpent) */
    jf1 = a - 1900;
    jf2 = jf1 % 19;
    jf3 = (int)((7*jf2+1)/19);
    jf4=(11*jf2+4-jf3)%29;
    jf5=(int)(jf1/4);
    jf6=(jf1+jf5+31-jf4)%7;
    jfj=(int)(25-jf4-jf6);
    jfm=3; // avril
    if (jfj<=0)
    {
      jfm=2; // mars
      jfj=jfj+31;
    }
    /* Fin du calcul des jours feries*/
    paques.set(a, jfm, jfj);
    lunpaq.set(a, jfm, jfj+1);
    ascension.set(a, jfm, jfj+39);
    lunpent.set(a, jfm, jfj+50);

    for(int i=0; i<jourFerie.length;i++)
    {
      if(jourFerie[i].equals(jourACompare))
        return resultat = true;
    }

    return resultat;
  }
}
