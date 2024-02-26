package Entities;

import java.util.Date;

public class MedicalVisit {

    private int id;

    private int idHorse;
    private int idVet;
    private String description;
    private Date visitDate;

    public MedicalVisit(int id,int idHorse, int idVet, String description, Date visitDate) {
        this.id = id;
        this.idHorse = idHorse;
        this.idVet = idVet;
        this.description = description;
        this.visitDate = visitDate;
    }

    public MedicalVisit(int idHorse, int idVet, String description, Date visitDate) {
        this.idHorse = idHorse;
        this.idVet = idVet;
        this.description = description;
        this.visitDate = visitDate;
    }

    public int getIdHorse() {
        return idHorse;
    }

    public void setIdHorse(int idHorse) {
        this.idHorse = idHorse;
    }

    public int getIdVet() {
        return idVet;
    }

    public void setIdVet(int idVet) {
        this.idVet = idVet;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }
}