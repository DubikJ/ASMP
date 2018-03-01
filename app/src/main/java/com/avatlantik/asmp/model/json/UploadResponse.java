package com.avatlantik.asmp.model.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UploadResponse {
    @Expose
    private String info;
    @Expose
    @SerializedName("new_animals")
    private List<ExternalIdPair> externalIdPairs;
    @Expose
    private String error;

    public UploadResponse() {
    }

    public List<ExternalIdPair> getExternalIdPairs() {
        return externalIdPairs;
    }

    public void setExternalIdPairs(List<ExternalIdPair> externalIdPairs) {
        this.externalIdPairs = externalIdPairs;
    }

    public String getInfo() {
        return info;
    }

    public String getError() {
        return error;
    }
}
