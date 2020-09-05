package com.java.gt.beans;

import java.util.ArrayList;

/**
 * @author Arléon Zemtsop
 * @class Equipment
 * Cette classe permet de représenter les différents équipements à maintenir
 * Chaque équipement contient la liste des tâches de maintenance à appliquer sur celui-ci
*/
public class Equipment {
    // Définition des attributs
    private int id;
    private String name;
    private ArrayList<Task> taskList;

    // Définition des constructeurs
    public Equipment() {
    }

    public Equipment(int id, String name, ArrayList<Task> taskList) {
        this.id = id;
        this.name = name;
        this.taskList = taskList;
    }
    
    /**
     * @param index
     * @return Task
     * Cette fonction retourne une tâche à partir de son index
     */
    public Task getTaskById(int index) {
        return this.taskList.get(index);
    }
    
    // Définition des getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

    @Override
    public String toString() {
        return "equipment [name=" + name + ", taskList=" + taskList + "]";
    }
    
}