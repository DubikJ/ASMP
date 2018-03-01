package com.avatlantik.asmp.model.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VetPreparatDTO {

    @Expose
    @SerializedName("external_id")
    private String externalId;
    @Expose
    private String name;

    public VetPreparatDTO(String externalId, String name) {
        this.externalId = externalId;
        this.name = name;
    }

    public String getExternalId() {
        return externalId;
    }

    public String getName() {
        return name;
    }
}
