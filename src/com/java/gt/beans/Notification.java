package com.java.gt.beans;

import java.util.Date;

public class Notification {
    private int id,taskId,equipmentId;
    private Date createdAt;
    private String message;
    private String type;

    public Notification() {}

    public Notification(int id, String type, Date createdAt, String message) {
        this.id = id;
        this.createdAt = createdAt;
        this.message = message;
        this.type = type;
    }

    public Notification(int taskId, int equipmentId, Date createdAt, String message, String type) {
        this.taskId = taskId;
        this.equipmentId = equipmentId;
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

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }
    
    @Override
    public String toString() {
        return "Notification [id=" + id + ", type="+ type +", message=" + message + ", createdAt=" + createdAt +"]";
    }
}
