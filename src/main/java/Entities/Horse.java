package Entities;

import java.util.Date;

public class Horse {

    public int id;
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
    public Horse() {

    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public Horse(String name, Date datePension, String breed, Boolean isAvailable) {
        this.name = name;
        this.datePension = datePension;
        this.breed = breed;
        this.isAvailable = isAvailable;
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

    @Override
    public String toString() {
        return this.name;
    }

    public int getId() {
        return id;
    }

    public void setIsAvailable(Boolean available) {
        isAvailable = available;
    }
}