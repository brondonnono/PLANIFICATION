package com.java.gt.controllers;

import java.util.ArrayList;

import com.java.gt.beans.Equipment;
import com.java.gt.beans.Task;
import com.java.gt.controllers.beans_controllers.EquipmentController;
import com.java.gt.controllers.store_controllers.StorageController;
import com.java.gt.threads.MainThread;

/**
 * @author Arléon Zemtsop
*/
public class MainController {
    /**
     * Liste des équipements manipulés
     */
    public static String[] EQUIPMENT_LIST = {"margeur","unite","reception","autres"};
    public static ArrayList<EquipmentController> equipmentList = new ArrayList<EquipmentController>();
    public static String[][] CURRENT_TASK_LIST;
    public static String[] CURRENT_TASK_LIST_HEADER = {"", "", "", "", ""};

    /**
     * Constructeurs
     */
    public MainController() {}

    public MainController(ArrayList<EquipmentController> equipmentList) {
        MainController.equipmentList = equipmentList;
    }

    /**
     * @void
     * Initialisation des données de tous les équipements
     * Lecture des données dans les fichiers  de tâche
     * des équipements correspondants des le lancemant 
     * de l'application
    */ 
    public static void init() {
        int index = 1;
        for(String equipName: MainController.EQUIPMENT_LIST) {
            StorageController storageController = new StorageController(equipName);
            ArrayList<Task> taskList;
            taskList = storageController.getFileReader().getTaskList();
            Equipment equipement = null;
            if(taskList != null) {
                equipement = new Equipment(index, equipName, taskList);
            }
            if(equipement != null) {
                //System.out.println("Storage controller: " + storageController);
                MainController.equipmentList.add(new EquipmentController(equipement, storageController));
                
                storageController.getTaskList();
            }
            index++;
        }
        MainController.initEquipementTask();
        MainController.launchThread();
    }
    
    public static void launchThread(){
        MainController.equipmentList.forEach((equipment) ->{
            new MainThread(equipment.getStorageController()).start();
        });   
    }
    
    public static void initCurrentTaskList(int indexEquip) {
        EquipmentController equipmentController = MainController.getEquipementById(indexEquip);
        for(int i=0; i<equipmentController.getEquipment().getTaskList().size(); i++) {
            for(int j=0; j<MainController.CURRENT_TASK_LIST_HEADER.length; j++) {    
            }
        }
    }
    
    /**
     * Initialisation de l'attribut equipement dans chaque tâches
     */
    public static void initEquipementTask() {
        MainController.equipmentList.forEach((equipController) -> {
            equipController.getEquipment().getTaskList().forEach((task) -> {
                task.setEquipment(equipController.getEquipment());
            });
        });
    }
    
    /**
     * @param indexEquip
     * @return ArrayList<Task>
     * Récupérer la liste des tâches d'un équipement donné
     */
    public static ArrayList<Task> getAllEquipementTasks(int indexEquip) {
        EquipmentController equipmentController = MainController.getEquipementById(indexEquip);
        return equipmentController.getEquipment().getTaskList();
    }
    
    public static ArrayList<Task> getAllEquipementTasks(String NameEquip) {
        EquipmentController equipmentController = MainController.getEquipementByTagName(NameEquip);
        return equipmentController.getEquipment().getTaskList();
    }    
    
    /**
     * @param indexEquip
     * @param indexTask
     * @return Task
     * Récupérer une tâche dans la liste des tâches d'un équipement précis
     */
    public static Task getTaskById(int indexEquip, int indexTask) {
        EquipmentController equipmentController = MainController.getEquipementById(indexEquip);
        return equipmentController.getEquipment().getTaskById(indexTask);
    }
    /**
     * @void
     * @param indexEquip
     * @param task
     * Ajouter une tâche dans la liste des tâche d'un équipement précis
     */
    public static void addTask(int indexEquip, Task task) {
        EquipmentController equipmentController = MainController.getEquipementById(indexEquip);
        equipmentController.getEquipment().getTaskList().add(task);
    } 
    /**
     * @void
     * @param indexEquip
     * @param indexTask 
     * Supprimer une tâche dans la liste des tâche d'un équipement précis
     */
    public static void deleteTask(int indexEquip, int indexTask) {
        EquipmentController equipmentController = MainController.getEquipementById(indexEquip);
        equipmentController.getEquipment().getTaskList().remove(indexTask);
    }
    /**
     * @void
     * @param indexEquip
     * @param indexTask
     * @param newTask 
     * Modifier une tâche dans la liste des tâche d'un équipement précis
     */
    public static void updateTask(int indexEquip, int indexTask, Task newTask) {
        EquipmentController equipmentController = MainController.getEquipementById(indexEquip);
        equipmentController.getEquipment().getTaskList().add(indexTask, newTask);
    }
    /**
     * @param index
     * @return EquipmentController
     * Récupérer le controlleur d'un équipement précis
     */
    public static EquipmentController getEquipementById(int indexEquip) {
        return MainController.equipmentList.get(indexEquip);
    }

    public static EquipmentController getEquipementByTagName(String NameEquip) {
        for(int i=0; i<equipmentList.size(); i++)
            if(equipmentList.get(i).getEquipment().getName()== NameEquip)
                return equipmentList.get(i);
        return null;
    }

    /**
     * @param equipmentController
     * @return boolean
     * Vérifier si le controlleur d'un équipement donné existe
     */
    public static boolean checkIfExist(EquipmentController equipmentController) {
        return MainController.equipmentList.contains(equipmentController);
    }
}