package Entities;

import java.util.Date;

public class Instructor {

    private int id;
    private String name;
    private String email;
    private String password;
    private Date dateJoined;
    private Image profileImage; // Adding image field

    public Instructor(int id, String email, String password, Date dateJoined, Image profileImage) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.dateJoined = dateJoined;
        this.profileImage = profileImage;
    }

    public Instructor(String email, String password, Date dateJoined, Image profileImage) {
        this.email = email;
        this.password = password;
        this.dateJoined = dateJoined;
        this.profileImage = profileImage;
    }

    public Image getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(Image profileImage) {
        this.profileImage = profileImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(Date dateJoined) {
        this.dateJoined = dateJoined;
    }
}