package com.java.gt.beans;

import java.util.Date;

public class Notification {
    private int id;
    private Date createdAt;
    private String message;
    private String type;

    public Notification() {}

    public Notification(int id, Date createdAt, String message, String type) {
        this.id = id;
        this.createdAt = createdAt;
        this.message = message;
        this.type = type;
    }

    public int getId() {
        return this.id;
    }    
    public void setId(int id) {
        this.id = id;
    }
    public Date getCreatedAt() {
        return this.createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    public String getMessage() {
        return this.message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Notification [createdAt=" + createdAt + ", id=" + id + ", message=" + message + ", type="+ type +"]";
    }
}
