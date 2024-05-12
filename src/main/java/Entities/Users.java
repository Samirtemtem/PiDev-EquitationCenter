package Entities;

import java.util.Date;

public class Users {

    public Users() {

    }

    public Users(int userId, String email, String password, String nom, String prenom, String addres, String numTel, String confirmPass, Date dateJoined, String roles, byte[] imageData) {
    this.id=userId;
    this.email=email;
    this.password=password;
    this.name=nom;
    this.lastName=prenom;
    this.address=addres;
    this.num_tel=numTel;
    this.dateJoined=dateJoined;
    this.roles=roles;
    this.imageData=imageData;

    }

    public Users(int userId, String email, String password, String nom, String prenom, String addres, String numTel, String confirmPass, Date dateJoined, byte[] imageData) {
    this.id=userId;
    this.email=email;
    this.password=password;
    this.name=nom;
    this.lastName=prenom;
    this.address=addres;
    this.num_tel=numTel;
    this.dateJoined=dateJoined;
    this.imageData=imageData;
    }


    @Override
    public String toString() {
        return name;
    }
    private int id;
    private String email;
    private String password;
    private String name;
    private String lastName;
    private String address;

    private Date dateJoined;
    private Image profileImage;
    private String roles;
    private String num_tel;
    private byte[] imageData;
    public Users(int id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }
    public Users(String email, String password, String nom, String prenom, String address, String num_tel , Date dateJoined, String roles, byte[] imageData) {
        this.email = email;
        this.password = password;
        this.name = nom;
        this.lastName = prenom;
        this.address = address;
        this.num_tel = num_tel;
        this.dateJoined = dateJoined;
        this.roles = roles;
        this.imageData = imageData;
    }
    public Users(int id, String email, String password, String name, Date dateJoined, Image profileImage, String roles) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.dateJoined = dateJoined;
        this.profileImage = profileImage;
        this.roles = roles;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNum_tel() {
        return num_tel;
    }

    public void setNum_tel(String num_tel) {
        this.num_tel = num_tel;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public Users(int id, String email, String password, String nom, String prenom, String address, String num_tel , Date dateJoined, String roles, byte[] imageData) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = nom;
        this.lastName = prenom;
        this.address = address;
        this.num_tel = num_tel;
        this.dateJoined = dateJoined;
        this.roles = roles;
        this.imageData = imageData;
    }
    public Users( String email, String password, String name, Date dateJoined, Image profileImage, String roles) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.dateJoined = dateJoined;
        this.profileImage = profileImage;
        this.roles = roles;
    }
    public Users(int id, String email, String password, String name, Date dateJoined, String roles) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.dateJoined = dateJoined;
        this.roles = roles;
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


  public String getRoles() {
    return roles;
  }

  public void setRoles(String roles) {
    this.roles = roles;
  }
}
