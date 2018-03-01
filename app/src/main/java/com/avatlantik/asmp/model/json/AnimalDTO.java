package com.avatlantik.asmp.model.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AnimalDTO {
    @Expose
    @SerializedName("external_id")
    private String externalId;
    @Expose
    private String rfid;
    @Expose
    private String addCode;
    @Expose
    private String code;
    @Expose
    private boolean isGroup;
    @Expose
    private boolean isGroupAnimal;
    @Expose
    private String name;
    @Expose
    private String group;
    @Expose
    private String corp;
    @Expose
    private String sector;
    @Expose
    private String cell;
    @Expose
    private int number;
    @Expose
    private String photo;
    @Expose
    private int dateRec;
    @Expose
    private String status;
    @Expose
    private String breed;
    @Expose
    private String herd;

    public AnimalDTO(String externalId, String rfid, String addCode, String code,
                     boolean isGroup, boolean isGroupAnimal, String name,
                     String group, String corp, String sector, String cell, int number,
                     String photo, int dateRec, String status, String breed, String herd) {
        this.externalId = externalId;
        this.rfid = rfid;
        this.addCode = addCode;
        this.code = code;
        this.isGroup = isGroup;
        this.isGroupAnimal = isGroupAnimal;
        this.name = name;
        this.group = group;
        this.corp = corp;
        this.sector = sector;
        this.cell = cell;
        this.number = number;
        this.photo = photo;
        this.dateRec = dateRec;
        this.status = status;
        this.breed = breed;
        this.herd = herd;
    }

    public String getExternalId() {
        return externalId;
    }

    public String getRfid() {
        return rfid;
    }

    public String getAddCode() {
        return addCode;
    }

    public String getCode() {
        return code;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public boolean isGroupAnimal() {
        return isGroupAnimal;
    }

    public String getName() {
        return name;
    }

    public String getGroup() {
        return group;
    }

    public String getCorp() {
        return corp;
    }

    public String getSector() {
        return sector;
    }

    public String getCell() {
        return cell;
    }

    public int getNumber() {
        return number;
    }

    public String getPhoto() {
        return photo;
    }

    public int getDateRec() {
        return dateRec;
    }

    public String getStatus() {
        return status;
    }

    public String getBreed() {
        return breed;
    }

    public String getHerd() {
        return herd;
    }
}
