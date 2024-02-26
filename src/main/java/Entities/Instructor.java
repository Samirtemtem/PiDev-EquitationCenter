package Entities;

import java.util.Date;

public class Instructor {

    private int id;
    private String email;
    private String password;
    private Date dateJoined;

    public Instructor(int id, String email, String password, Date dateJoined) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.dateJoined = dateJoined;
    }

    public Instructor(String email, String password, Date dateJoined) {
        this.email = email;
        this.password = password;
        this.dateJoined = dateJoined;
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