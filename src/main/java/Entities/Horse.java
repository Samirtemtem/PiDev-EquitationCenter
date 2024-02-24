package Entities;

import java.util.Date;

public class Horse {

    private int id;
    private String name;
    private Date datePension;
    private String breed;
    private Boolean isAvailable;

    public Horse(int id, String name, Date datePension, String breed, Boolean isAvailable) {
        this.id = id;
        this.name = name;
        this.datePension = datePension;
        this.breed = breed;
        this.isAvailable = isAvailable;
    }

    public Horse(String name, Date datePension, String breed, Boolean isAvailable) {
        this.name = name;
        this.datePension = datePension;
        this.breed = breed;
        this.isAvailable = isAvailable;
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

    public Date getDatePension() {
        return datePension;
    }

    public void setDatePension(Date datePension) {
        this.datePension = datePension;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean available) {
        isAvailable = available;
    }
}