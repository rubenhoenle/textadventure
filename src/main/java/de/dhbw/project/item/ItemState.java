package de.dhbw.project.item;

import com.google.gson.annotations.SerializedName;

public enum ItemState {
    @SerializedName("1")
    ACTIVE(1), @SerializedName("0")
    INACTIVE(0), @SerializedName("-1")
    NOT_USABLE(-1);

    ItemState(int i) {
    }
}
