package com.avatlantik.asmp.model.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public final class UploadAnimalDTO {
    @Expose
    @SerializedName("external_id")
    private String externalId;
    @Expose
    @SerializedName("type_animal")
    private int typeAnimal;
    @Expose
    private String rfid;
    @Expose
    @SerializedName("add_code")
    private String addCode;
    @Expose
    private String code;
    @Expose
    @SerializedName("is_group_animal")
    private boolean isGroupAnimal;
    @Expose
    private String name;
    @Expose
    private String group;
    @Expose
    private int number;
    @Expose
    private String photo;
    @Expose
    private long dateRec;
    @Expose
    private String breed;
    @Expose
    private String herd;

    public UploadAnimalDTO(String externalId, int typeAnimal, String rfid, String addCode,
                           String code, boolean isGroupAnimal, String name,
                           String group, int number, String photo, long dateRec,
                           String breed, String herd) {
        this.externalId = externalId;
        this.typeAnimal = typeAnimal;
        this.rfid = rfid;
        this.addCode = addCode;
        this.code = code;
        this.isGroupAnimal = isGroupAnimal;
        this.name = name;
        this.group = group;
        this.number = number;
        this.photo = photo;
        this.dateRec = dateRec;
        this.breed = breed;
        this.herd = herd;
    }
}
