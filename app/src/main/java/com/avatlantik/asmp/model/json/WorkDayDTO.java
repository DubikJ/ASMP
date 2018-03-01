package com.avatlantik.asmp.model.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WorkDayDTO {
    @Expose
    @SerializedName("num_message")
    int numMessage;

    public WorkDayDTO(int numMessage) {
        this.numMessage = numMessage;
    }

    public int getNumMessage() {
        return numMessage;
    }

}
