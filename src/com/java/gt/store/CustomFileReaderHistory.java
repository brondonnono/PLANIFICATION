/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.gt.store;

import com.java.gt.beans.History;
import com.java.gt.configurations.StorageConfig;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Brondon
 */
public class CustomFileReaderHistory {
    
    private File fileHistory;
    private ArrayList<History> historyList = new ArrayList<History>();
    
    public CustomFileReaderHistory(String folderName){
        this.fileHistory = new File(StorageConfig.DEFAULT_FOLDER_STORAGE_NAME + "/" + folderName + "/" + StorageConfig.DEFAULT_HISTORY_FILE_STORAGE_NAME);
        StorageConfig.createFolderIfNotExist(new File(StorageConfig.DEFAULT_FOLDER_STORAGE_NAME + "/" + folderName));
        StorageConfig.createFileIfNotExist(this.fileHistory);
    }
    
    public void computeHistory(String[] attributeList) {

        Date date = null;
        int id = 0;
         try{
            id = Integer.parseInt(attributeList[0]);
        }catch(NumberFormatException e){}
        String article = attributeList[1];   
        //System.out.println("article: "+article);
        try{
            date = new SimpleDateFormat("dd/MM/yyyy hh:mm").parse(attributeList[2]);
        }catch(ParseException e){}
        //String date = attributeList[2];
        String operator = attributeList[3];
        History h = new History(id, article, date, operator);
        //System.out.println("h:"+h);
        this.historyList.add(h);
    }

    
    public ArrayList<History> readFileDataHistory() {
        String fileName = this.fileHistory.getAbsolutePath();
        Path path = Paths.get(fileName);
        try {
            if(!Files.readAllLines(path).isEmpty()) {      
                for(String line: Files.readAllLines(path)) {
                    String[] attributeList = line.split("-");
                    if(attributeList.length > 0) {
                        this.computeHistory(attributeList);
                    }
                }
                for(History hist:historyList)
           // System.out.println("history: "+hist);
                return this.historyList;
            }
        } catch(IOException e) {} 
        return new ArrayList<History>();
    }
    
    public ArrayList<History> getHistoryList() {
        //System.out.println("getHistoryList :\n"+ historyList);
        return historyList;
    }
    
    public void setHistoryList(ArrayList<History> historyList) {
        this.historyList = historyList;
    }
    
    public File getFileHistory() {
        return fileHistory;
    }

    public void setFileHistory(File fileHistory) {
        this.fileHistory = fileHistory;
    }
    
}
