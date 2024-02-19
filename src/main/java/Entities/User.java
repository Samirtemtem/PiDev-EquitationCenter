package Entities;

import java.util.Date;

public class User {

    public User() {

    }

    public enum Role {
        ADMIN,
        CLIENT,
        INSTRUCTOR,
        VET
    }

    private int id;
    private String email;
    private String password;
    private String name;
    private Date dateJoined;
    private Image profileImage;
    private Role role;

    public User(int id, String email, String password, String name, Date dateJoined, Image profileImage, Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.dateJoined = dateJoined;
        this.profileImage = profileImage;
        this.role = role;
    }
    public User( String email, String password, String name, Date dateJoined, Image profileImage, Role role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.dateJoined = dateJoined;
        this.profileImage = profileImage;
        this.role = role;
    }
    public User(int id, String email, String password, String name, Date dateJoined, Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.dateJoined = dateJoined;
        this.role = role;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(Date dateJoined) {
        this.dateJoined = dateJoined;
    }

    public Image getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(Image profileImage) {
        this.profileImage = profileImage;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}