package Entities;
import java.util.Date;
public class Complaint {
  private int id;
  private int userId;
  private String objet;
  private String description;
  private Date createdAt;
  private String etat;
  private String answer;

  public String getAnswer() {
    return answer;
  }
  public void SetAnswer(String answer)
  {
    this.answer=answer;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }

  public Complaint()
  {}
  public Complaint(int id, String objet, String description, String etat,Date createdAt) {
    this.userId = id;
    this.objet = objet;
    this.description = description;
    this.createdAt = createdAt;
    this.etat=etat;
  }
  public Complaint(int id, int userId, String objet, String description, String etat) {
    this.id = id;
    this.userId = userId;
    this.objet = objet;
    this.description = description;
    this.etat = etat;
  }

  public Complaint(int id, int iduser, String objet, String description, java.sql.Date createdAt, String etat) {
    this.id=id;
    this.userId=iduser;
    this.objet=objet;
    this.description=description;
    this.createdAt=createdAt;
    this.etat=etat;
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

  public String getObjet() {
    return objet;
  }

  public void setObjet(String objet) {
    this.objet = objet;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public String getEtat() {
    return etat;
  }

  public void setEtat(String etat) {
    this.etat = etat;
  }


}
