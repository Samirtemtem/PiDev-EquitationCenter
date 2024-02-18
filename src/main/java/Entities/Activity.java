package Entities;

import java.util.Date;

public class Activity {

    private int id;
    private Date date;
    private String typeActivity;
    private String title;
    private String description;
    private double price;

    public Activity() {
    }

    public Activity(int id, Date date, String typeActivity, String title, String description, double price) {
        this.id = id;
        this.date = date;
        this.typeActivity = typeActivity;
        this.title = title;
        this.description = description;
        this.price = price;
    }

    public Activity(Date date, String typeActivity, String title, String description, double price) {
        this.date = date;
        this.typeActivity = typeActivity;
        this.title = title;
        this.description = description;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTypeActivity() {
        return typeActivity;
    }

    public void setTypeActivity(String typeActivity) {
        this.typeActivity = typeActivity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}