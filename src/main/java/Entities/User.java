package Entities;
import java.util.Date;
public class User {
  private int id;
  private String nom;
  private String prenom;
  private String email;
  private String address;
  public Role role;
  private String password;
  private Date dateJoined;

  @Override
  public String toString() {
    return "User{" +
            "id=" + id +
            ", nom='" + nom + '\'' +
            ", prenom='" + prenom + '\'' +
            ", email='" + email + '\'' +
            ", address='" + address + '\'' +
            ", role=" + role +
            ", password='" + password + '\'' +
            ", dateJoined=" + dateJoined +
            ", num_tel='" + num_tel + '\'' +
            '}';
  }

  private String num_tel;
  private byte[] imageData;

  public User() {}

  public User(int id, String email, String password) {
    this.id = id;
    this.email = email;
    this.password = password;
  }

  public User(String email, String password,String nom, String prenom, String address, String num_tel ,Date dateJoined,Role role,  byte[] imageData) {
    this.email = email;
    this.password = password;
    this.nom = nom;
    this.prenom = prenom;
    this.address = address;
    this.num_tel = num_tel;
    this.dateJoined = dateJoined;
    this.role = role;
    this.imageData = imageData;
  }

  public User(int id,String email, String password,String nom, String prenom, String address, String num_tel ,Date dateJoined,Role role,  byte[] imageData) {
    this.id = id;
    this.email = email;
    this.password = password;
    this.nom = nom;
    this.prenom = prenom;
    this.address = address;
    this.num_tel = num_tel;
    this.dateJoined = dateJoined;
    this.role = role;
    this.imageData = imageData;
  }
private String confirmPass;
  public User(int Id, String email, String password, String nom, String prenom, String addres, String numTel, String confirmPass, Date dateJoined, Role type, byte[] imageData) {
    this.id=Id;
    this.email = email;
    this.password = password;
    this.prenom = prenom;
    this.address = addres;
    this.num_tel = numTel;
    this.confirmPass=confirmPass;
    this.role=type;
    this.dateJoined = dateJoined;
    this.imageData=imageData;
    this.nom=nom;

  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public String getPrenom() {
    return prenom;
  }

  public void setPrenom(String prenom) {
    this.prenom = prenom;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
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
}