/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.gt.store;

import com.java.gt.beans.History;
import com.java.gt.configurations.StorageConfig;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 * @author Brondon
 */
public class CustomFileWriterHistory {
    private File fileHistory;
    
    public CustomFileWriterHistory(String folderName){
        this.fileHistory = new File(StorageConfig.DEFAULT_FOLDER_STORAGE_NAME + "/" + folderName + "/" + StorageConfig.DEFAULT_HISTORY_FILE_STORAGE_NAME);
        StorageConfig.createFileIfNotExist(new File(StorageConfig.DEFAULT_FOLDER_STORAGE_NAME + "/" + folderName));
        StorageConfig.createFileIfNotExist(this.fileHistory);
    }
    
    
    public String computeFileLine(History history) {
        //System.out.println("compute "+this.fileHistory);
        SimpleDateFormat DateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        String date = DateFormat.format(history.getDate());
        String line = history.getId()+ "-" + history.getArticle()+ "-" + date + "-" + history.getOperator();
        //System.out.println("line "+line);
        return line;
    }
    
    
    public void saveData(History history) {
        String line = this.computeFileLine(history);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.fileHistory.getAbsolutePath(), true))) {
            writer.write(line);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        };
    }    
    
    public void renderAllHistory(ArrayList<History> newHistoryList){
        this.reinitilaliseFileHistory();
        
        for(History newHistory: newHistoryList)
            this.saveData(newHistory);
    }    
    
    public void reinitilaliseFileHistory() {
        if(this.fileHistory.exists())
            this.fileHistory.delete();
        if(!this.fileHistory.exists()){
            try {
                this.fileHistory.createNewFile();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
}
