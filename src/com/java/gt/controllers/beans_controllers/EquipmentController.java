package com.java.gt.controllers.beans_controllers;

import com.java.gt.beans.Equipment;
import com.java.gt.controllers.store_controllers.StorageController;

/**
 * @author Arléon Zemtsop
 * Cette classe nous permet de manipuler un équipement ainsi que
 * l'espace de stockage associé au différentes tâches de maintenance 
 * de l'équipement. Ces tâches sont stockés dans des fichiers et leur
 * accès est guarrantis pas l'objet storageController définis plus bas
*/
public class EquipmentController {
    // Définition de l'équipement
    private Equipment equipment;
    // Définition du module de stockage des tâches relatives à l'équipement
    private StorageController storageController;

    // Définition des constructeurs
    public EquipmentController() {}

    public EquipmentController(Equipment equipment, StorageController storageController) {
        this.equipment = equipment;
        this.storageController = storageController;
    }
 
    // Définition des getters et setters
    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public StorageController getStorageController() {
        return storageController;
    }

    public void setStorageController(StorageController storageController) {
        this.storageController = storageController;
    }
}