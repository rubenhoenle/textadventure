package de.dhbw.project;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum WayState {
    ACTIVE, INACTIVE, INVISIBLE, BLOCKED, NEED_EQUIPMENT;

    public static List<String> getAllNames() {
        return Stream.of(WayState.values()).map(WayState::name).collect(Collectors.toList());
    }
}
