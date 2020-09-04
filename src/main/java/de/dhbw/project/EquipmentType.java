package de.dhbw.project;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum EquipmentType {

    WEAPON("weapon"), HEAD("head"), UPPER_BODY("upper body"), LOWER_BODY("lower body"), SHOES("shoes");

    private String description;

    EquipmentType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static List<String> getAllDescriptions() {
        return Stream.of(EquipmentType.values()).map(EquipmentType::getDescription).collect(Collectors.toList());
    }
}
