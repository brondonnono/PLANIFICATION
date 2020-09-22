package com.java.gt.beans;

import com.java.gt.controllers.beans_controllers.TaskController;
import java.util.Date;

/**
 * @author Arléon Zemtsop
 * @class Equipment
 * Cette classe permet de représenter les différentes tâches
 * Chaque équipement possède un type précis
*/
public class Task {
    // Définition des attributs
    // Définition de la liste des types de tâches
    public static String[] TASK_TYPE = {"GRAISSAGE_HUILE", "REGLAGE", "NETTOYAGE"};
    public static String[] TASK_TYPE_LABEL = {"Graissage à l'huile", "Réglage", "Néttoyage"};

    private Equipment equipment;
    private int id;
    private String name;
    // Fréquence de déroulement de la tâche en Heures
    private int interval;
    // Secteur de la tâche
    private String secteur;
    // Date de la dernière maintenance 
    private Date lastMaintaintDate;
    // Temps d'exécution de la tâche courante depuis la dernière maintenance 
    private int operatingTime;
    private String type;
    
    // Définition des constructeurs
    public Task() {}

    public Task(int id, String name, int interval, String secteur, Date lastMaintaintDate, int operatingTime, String type) {
        this.id = id;
        this.name = name;
        this.interval = interval;
        this.secteur = secteur;
        this.lastMaintaintDate = lastMaintaintDate;
        this.operatingTime = operatingTime;
        this.type = type;
    }
    
    // Définition des getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
            return this.name;
    }
    public String displayName() {
            String type = "";
            switch (this.type) {
                    case "GRAISSAGE_HUILE":
                            type = "Graissage à l'huile";
                            break;
                    case "REGLAGE":
                            type = "Réglage";
                            break;
                    case "NETTOYAGE":
                            type = "Néttoyage";
                            break;
                    default:
                            type ="Tous";
                            break;
            }
            return this.equipment.getName() + " " + type;
    }
    public void setName(String name) {
            this.name = name;
    }
    public int getInterval() {
            return interval;
    }
    public String displayInterval() {
            if(this.interval > 0 && this.interval < 24) {
                    return this.interval + " heure(s)";
            }
            if(this.interval >= 24 && this.interval < 168) {
                    return Math.round(this.interval/24) + " jour(s)";
            }
            if(this.interval >= 168 && this.interval < 672) {
                    return Math.round(this.interval/168) + " semaine(s)";
            }
            if(this.interval >= 672 && this.interval < 8760) {
                    return Math.round(this.interval/672) + " mois";
            }
            if(this.interval >= 8760) {
                    return Math.round(this.interval/8760) + " an(s)";
            }
            return "";
    }
    public void setInterval(int interval) {
            this.interval = interval;
    }
    public String getSecteur() {
            return secteur;
    }
    public void setSecteur(String secteur) {
            this.secteur = secteur;
    }
    public Date getLastMaintaintDate() {
            return lastMaintaintDate;
    }
    public void setLastMaintaintDate(Date lastMaintaintDate) {
            this.lastMaintaintDate = lastMaintaintDate;
    }
    public int getOperatingTime() {
            return operatingTime;
    }
    public int getOperatingTimeInHours() { 
            return this.operatingTime/3600;
    }
    public String displayOperatingTime() { 
            String opTimeInHours = Math.round(this.operatingTime/3600) + "";
            return opTimeInHours + "/" + interval + "                      "+ TaskController.displayPourcentage(this);
    }
    public void setOperatingTime(int operatingTime) {
            this.operatingTime = operatingTime;
    }
    public String getType() {
            return type;
    }
    public void setType(String type) {
            this.type = type;
    }
    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }
    
    @Override
    public String toString() {
            return "Task [interval=" + interval + ", lastMaintaintDate=" + lastMaintaintDate + ", name=" + name
                            + ", operatingTime=" + operatingTime + ", secteur=" + secteur + ", type=" + type + "]";
    }
}