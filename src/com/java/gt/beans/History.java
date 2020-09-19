/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.gt.beans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Brondon Nono
 */
public class History {
    private int id;
    private String article, operator;
    private Date date;
    
    public History() {}
    
    public History(int id, String article, Date date, String operator) {
        this.id = id;
        this.article = article;
        this.date = date;
        this.operator = operator;
    }
    
    public History(int id, String article, String date, String operator) {
        this.id = id;
        this.article = article;
        this.date = null;
        try{
            this.date  = new SimpleDateFormat("dd/MM/yyyy hh:mm").parse(date);
        }catch(ParseException e){}
        this.operator = operator;
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
    
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    
    public String getOperator() {
        return operator;
    }
    public void setOperator(String operator) {
        this.operator = operator;
    }
    
        @Override
    public String toString() {
            return " Article: "+ article +" Date: "+ date +" Operateur: "+ operator;
    }
}
