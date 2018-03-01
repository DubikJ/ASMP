package com.avatlantik.asmp.model.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UploadDataDTO{

    @Expose
    @SerializedName("service_done")
    private List<UploadServiceDoneDTO> uploadServiceDoneDTOS;
    @Expose
    @SerializedName("animals")
    private List<UploadAnimalDTO> uploadAnimalDTOS;

    public UploadDataDTO(List<UploadServiceDoneDTO> uploadServiceDoneDTOS,
                         List<UploadAnimalDTO> uploadAnimalDTOS) {
        this.uploadServiceDoneDTOS = uploadServiceDoneDTOS;
        this.uploadAnimalDTOS = uploadAnimalDTOS;
    }

    public List<UploadServiceDoneDTO> getUploadServiceDoneDTOS() {
        return uploadServiceDoneDTOS;
    }

    public List<UploadAnimalDTO> getUploadAnimalDTOS() {
        return uploadAnimalDTOS;
    }
}
