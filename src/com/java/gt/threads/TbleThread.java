/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.gt.threads;

import com.java.gt.beans.Task;
import com.java.gt.configurations.StorageConfig;
import com.java.gt.views.Accueil;
import java.util.ArrayList;
import javax.swing.JTable;

/**
 *
 * @author Brondon
 */
public class TbleThread extends Thread{
    
    private Accueil fen;
    public static ArrayList<Task> taskListArranged = new ArrayList<Task>();
    
    public TbleThread() {}

    public TbleThread(Accueil fen) {
        this.fen = fen;
    }

    /**
     * Cette fonction ajoute 2 seconde au temps de fonctionnement des tâches d'un équipement
     */
    @Override
	public void run() {
		while(true) {
     			try{
                    Thread.sleep(StorageConfig.DEFAULT_OPERATING_TIME_SAVE_DATA_FREQUENCY_IN_SECOND * 1000);
			} catch(Exception e) {}
                    if(Accueil.canRead){
                        ArrayList<Task> taskList = this.fen.storageController.getFileReader().getTaskList();
                        taskListArranged = fen.arrangedTaskList(taskList,fen.checked[1]);
                        //System.out.println("Thread TaskList :"+taskList);
                        if(!taskListArranged.isEmpty())
                            for(int i = 0; i < fen.taskListArranged.size(); i++)
                                this.fen.getModel().setValueAt(taskListArranged.get(i).displayOperatingTime(), i, 5);          
                    }
		}
	}    
}
