package Entities;

import java.util.Date;

public class Equipment {
        private int id;
        private String name;
        private String type;
        private String description;
        private int quantity;
        private Date purchaseDate;
        private String equipmentCondition;

        public Equipment(int id, String name, String type, String description, int quantity, Date purchaseDate, String equipmentCondition) {
            this.id = id;
            this.name = name;
            this.type = type;
            this.description = description;
            this.quantity = quantity;
            this.purchaseDate = purchaseDate;
            this.equipmentCondition = equipmentCondition;
        }


    public Equipment(String name, String type, String description, int quantity, Date purchaseDate, String equipmentCondition) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.quantity = quantity;
        this.purchaseDate = purchaseDate;
        this.equipmentCondition = equipmentCondition;
    }

    public Equipment() {

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getEquipmentCondition() {
        return equipmentCondition;
    }

    public void setEquipmentCondition(String equipmentCondition) {
        this.equipmentCondition = equipmentCondition;
    }
// Getters and setters for the properties
        // ...
}
