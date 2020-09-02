package de.dhbw.project.item;

import de.dhbw.project.WayState;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum LampState {
    ON, OFF, HAS_NO_LAMP;

    public static List<String> getAllNames() {
        return Stream.of(WayState.values()).map(WayState::name).collect(Collectors.toList());
    }
}
