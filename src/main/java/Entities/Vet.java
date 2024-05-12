package Entities;

import java.util.Date;

public class Vet {

    private int id;
    private String name;

    private String Password;
    private String email;
    private Date dateJoined;
    public Vet(int id, String name, String password, String email1, Date dateJoined) {
        this.id = id;
        this.name = name;

        Password = password;
        this.email = email1;
        this.dateJoined = dateJoined;
    }
    public Vet(String name,  String password, String email1, Date dateJoined) {
        this.name = name;

        Password = password;
        this.email = email1;
        this.dateJoined = dateJoined;
    }

    public Vet() {

    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(Date dateJoined) {
        this.dateJoined = dateJoined;
    }
}