/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.gt.beans;

/**
 *
 * @author Brondon Nono
 */
public class History {
    private int id;
    private String article, date, operator, hour;
    
    public History() {}
    
    public History(int id, String article, String date, String operator, String hour) {
        this.id = id;
        this.article = article;
        this.date = date;
        this.operator = operator;
        this.hour = hour;
    }
    
    public int getId(){
        return id;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public String getArticle(){
        return article;
    }
    public void setArticle(String article) {
        this.article = article;
    }
    
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    
    public String getOperator() {
        return operator;
    }
    public void setOperator(String operator) {
        this.operator = operator;
    }
    
    public String getHour() {
        return hour;
    }
    public void setHour(String hour) {
        this.hour = hour;
    }
    
        @Override
    public String toString() {
            return " Article: "+ article +" Date: "+ date +" Operateur: "+ operator +" Heure: "+ hour;  
    }
}
