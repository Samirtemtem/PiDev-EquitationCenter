package Entities;

import java.util.Date;

public class Post {

    private int id;
    private Date date;
    private String description;
    private Image image;

    public Post(Date date, String description) {
        this.id = id;
        this.date = date;
        this.description = description;
        this.image = image;
    }

    public Post(Date date, String description, Image image) {
        this.date = date;
        this.description = description;
        this.image = image;
    }

    public Post(int id,Date date, String description) {
        this.id=id;
        this.date= date;
        this.description=description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }


}