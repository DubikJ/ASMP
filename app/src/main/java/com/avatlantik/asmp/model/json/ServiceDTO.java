package com.avatlantik.asmp.model.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceDTO {
    @Expose
    @SerializedName("external_id")
    private String externalId;
    @Expose
    private String name;
    @Expose
    private int typeRes;

    public ServiceDTO(String externalId, String name, int typeRes) {
        this.externalId = externalId;
        this.name = name;
        this.typeRes = typeRes;
    }

    public String getExternalId() {
        return externalId;
    }

    public String getName() {
        return name;
    }

    public int getTypeRes() {
        return typeRes;
    }

}
