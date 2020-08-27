package de.dhbw.project;

public enum EquipmentType {

    WEAPON("weapon"), HEAD("head"), UPPER_BODY("upper body"), LOWER_BODY("lower body"), SHOES("shoes");

    private String description;

    EquipmentType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
