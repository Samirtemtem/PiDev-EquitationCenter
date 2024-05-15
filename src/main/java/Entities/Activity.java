package Entities;

import java.util.Arrays;
import java.util.Date;

public class Activity {

    private int id;
    private Date date;
    private String typeActivity;
    private String title;
    private String description;
    private double price;
    private byte[] imageData; // Store image data as byte array

    public Activity() {
    }

    public Activity(int id, Date date, String typeActivity, String title, String description, double price, byte[] imageData) {
        this.id = id;
        this.date = date;
        this.typeActivity = typeActivity;
        this.title = title;
        this.description = description;
        this.price = price;
        this.imageData = imageData;
    }

    @Override
    public String toString() {
        return getId() + " - " + getTitle();
    }

    public Activity(Date date, String typeActivity, String title, String description, double price, byte[] imageData) {
        this.date = date;
        this.typeActivity = typeActivity;
        this.title = title;
        this.description = description;
        this.price = price;
        this.imageData = imageData;
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

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }
}