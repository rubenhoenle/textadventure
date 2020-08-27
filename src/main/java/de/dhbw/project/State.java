package de.dhbw.project;

import com.google.gson.annotations.SerializedName;

public enum State {
    @SerializedName("1")
    ACTIVE(1), @SerializedName("0")
    INACTIVE(0), @SerializedName("-1")
    NOT_USABLE(-1);

    State(int i) {
    }
}
