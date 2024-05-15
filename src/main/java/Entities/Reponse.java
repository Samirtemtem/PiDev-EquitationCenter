package Entities;
import java.util.Date;
public class Reponse {
  private int id;
  private Date dateRep;
  private String contenuRep;
  private int idComplaint;
  public Reponse(){}


  public Reponse(String contenuRep, int idComplaint) {
    this.contenuRep = contenuRep;
    this.idComplaint = idComplaint;
  }

  public Reponse(int id, String contenuRep, int idComplaint) {
    this.id = id;
    this.contenuRep = contenuRep;
    this.idComplaint = idComplaint;
  }

  public Reponse(int id, Date dateRep, String contenuRep, int idComplaint) {
    this.id = id;
    this.dateRep = dateRep;
    this.contenuRep = contenuRep;
    this.idComplaint = idComplaint;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 53 * hash + this.id;
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Reponse other = (Reponse) obj;
    if (this.id != other.id) {
      return false;
    }
    return true;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Date getDateRep() {
    return dateRep;
  }

  public void setDateRep(Date dateRep) {
    this.dateRep = dateRep;
  }

  public String getContenuRep() {
    return contenuRep;
  }

  public void setContenuRep(String contenuRep) {
    this.contenuRep = contenuRep;
  }

  public int getIdComplaint() {
    return idComplaint;
  }

  public void setIdComplaint(int idComplaint) {
    this.idComplaint = idComplaint;
  }
}
