package de.dhbw.project.item;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.gson.annotations.SerializedName;

public enum ItemState {
    @SerializedName("1")
    ACTIVE(1), @SerializedName("0")
    INACTIVE(0), @SerializedName("-1")
    NOT_USABLE(-1);

    ItemState(int i) {
    }

    // TODO: add Test
    public static List<String> getAllDescriptions() {
        return Stream.of(ItemState.values().toString()).collect(Collectors.toList());
    }
}
