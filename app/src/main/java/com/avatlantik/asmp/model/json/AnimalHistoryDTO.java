package com.avatlantik.asmp.model.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public final class AnimalHistoryDTO {

    @Expose
    @SerializedName("external_id")
    private String externalId;
    @Expose
    @SerializedName("animal_id")
    private String animalId;
    @Expose
    private int date;
    @Expose
    private String service;
    @Expose
    private String result;

    public AnimalHistoryDTO(String externalId, String animalId, int date, String service, String result) {
        this.externalId = externalId;
        this.animalId = animalId;
        this.date = date;
        this.service = service;
        this.result = result;
    }

    public String getExternalId() {
        return externalId;
    }

    public String getAnimalId() {
        return animalId;
    }

    public int getDate() {
        return date;
    }

    public String getService() {
        return service;
    }

    public String getResult() {
        return result;
    }
}
