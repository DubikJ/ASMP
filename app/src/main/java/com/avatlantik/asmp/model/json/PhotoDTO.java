package com.avatlantik.asmp.model.json;

import com.google.gson.annotations.Expose;

public class PhotoDTO {
    @Expose
    private String name;
    @Expose
    private String photo;

    public PhotoDTO(String name, String photo) {
        this.name = name;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public String getPhoto() {
        return photo;
    }
}
