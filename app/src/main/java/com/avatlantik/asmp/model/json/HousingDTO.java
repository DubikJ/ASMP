package com.avatlantik.asmp.model.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HousingDTO {
    @Expose
    @SerializedName("external_id")
    private String externalId;
    @Expose
    private String name;
    @Expose
    private int type;
    @SerializedName("parent_id")
    private String parentId;

    public HousingDTO(String externalId, String name, int type, String parentId) {
        this.externalId = externalId;
        this.name = name;
        this.type = type;
        this.parentId = parentId;
    }

    public String getExternalId() {
        return externalId;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public String getParentId() {
        return parentId;
    }
}
