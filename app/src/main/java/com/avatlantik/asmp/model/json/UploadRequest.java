package com.avatlantik.asmp.model.json;

import com.google.gson.annotations.Expose;

public class UploadRequest {
    @Expose
    private UploadDataDTO uploadDataDTO;

    public UploadRequest(UploadDataDTO uploadDataDTO) {
        this.uploadDataDTO = uploadDataDTO;
    }

    public UploadDataDTO getUploadDataDTO() {
        return uploadDataDTO;
    }
}
