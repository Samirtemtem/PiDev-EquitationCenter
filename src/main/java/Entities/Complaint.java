package Entities;

import java.util.Date;

public class Complaint {
    private int id;
    private int userId;
    private String complaintText;
    private Date createdAt;
    private String status;
    private String priority;

    public Complaint(int id, int userId, String complaintText, Date createdAt, String status, String priority) {
        this.id = id;
        this.userId = userId;
        this.complaintText = complaintText;
        this.createdAt = createdAt;
        this.status = status;
        this.priority = priority;
    }

    public Complaint() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getComplaintText() {
        return complaintText;
    }

    public void setComplaintText(String complaintText) {
        this.complaintText = complaintText;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Complaint(int userId, String complaintText, Date createdAt, String status, String priority) {
        this.id = id;
        this.userId = userId;
        this.complaintText = complaintText;
        this.createdAt = createdAt;
        this.status = status;
        this.priority = priority;
    }


}