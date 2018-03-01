package com.avatlantik.asmp.model.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DownloadServiceDTO extends ServiceDTO {

    @Expose
    @SerializedName("animals_type")
    private List<Integer> animalsTypeDTO;


    public DownloadServiceDTO(String externalId, String name, int typeRes) {
        super(externalId, name, typeRes);
    }

    public List<Integer> getAnimalsTypeDTO() {
        return animalsTypeDTO;
    }
}
