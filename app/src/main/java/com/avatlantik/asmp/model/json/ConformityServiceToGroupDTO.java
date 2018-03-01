package com.avatlantik.asmp.model.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public final class ConformityServiceToGroupDTO {

   @Expose
    @SerializedName("service_id")
    private String serviceID;

    @Expose
    @SerializedName("animal_type")
    private int animalType;

    @Expose
    @SerializedName("animal_id")
    private String animalId;

    public ConformityServiceToGroupDTO(String serviceID, int animalType, String animalId) {
        this.serviceID = serviceID;
        this.animalType = animalType;
        this.animalId = animalId;
    }

    public String getServiceID() {
        return serviceID;
    }

    public int getAnimalType() {
        return animalType;
    }

    public String getAnimalId() {
        return animalId;
    }
}
