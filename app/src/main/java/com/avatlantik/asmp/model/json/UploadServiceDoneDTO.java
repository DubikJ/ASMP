package com.avatlantik.asmp.model.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public final class UploadServiceDoneDTO {

    @Expose
    private long date;

    @Expose
    @SerializedName("service_id")
    private String serviceId;

    @Expose
    @SerializedName("animal_id")
    private String animalId;

    @Expose
    @SerializedName("type_animal")
    private int typeAnimal;

    @Expose
    private Boolean done;

    @Expose
    private int number;

    @Expose
    private int live;

    @Expose
    private int normal;

    @Expose
    private int weak;

    @Expose
    private int death;

    @Expose
    private int mummy;

    @Expose
    @SerializedName("tecn_group_to")
    private String tecnGroupTo;

    @Expose
    private double weight;

    @Expose
    private String boar1;

    @Expose
    private String boar2;

    @Expose
    private String boar3;

    @Expose
    private String note;

    @Expose
    @SerializedName("corp_to")
    private String corpTo;

    @Expose
    @SerializedName("sector_to")
    private String sectorTo;

    @Expose
    @SerializedName("cell_to")
    private String cellTo;

    @Expose
    @SerializedName("service_result")
    private String serviceResult;

    @Expose
    @SerializedName("adm_number")
    private int admNumber;

    @Expose
    private String status;

    @Expose
    @SerializedName("dispos_anim")
    private String disposAnim;

    @Expose
    private int length;

    @Expose
    private int bread;

    @Expose
    private int exterior;

    @Expose
    @SerializedName("depth_mysz")
    private int depthMysz;

    @Expose
    @SerializedName("new_code")
    private String newCode;

    @Expose
    @SerializedName("vet_exercise")
    private String vetExercise;

    @Expose
    @SerializedName("vet_preparat")
    private String vetPreparat;

    @Expose
    @SerializedName("artif_insemen")
    private Boolean artifInsemen;

    @Expose
    @SerializedName("anim_group_to")
    private String animGroupTo;

    public UploadServiceDoneDTO(long date, String serviceId, String animalId, int typeAnimal, Boolean done,
                                int number, int live, int normal, int weak, int death, int mummy,
                                String tecnGroupTo, double weight, String boar1, String boar2, String boar3,
                                String note, String corpTo, String sectorTo, String cellTo,
                                String serviceResult, int admNumber, String status,
                                String disposAnim, int length, int bread, int exterior,
                                int depthMysz, String newCode, Boolean artifInsemen, String animGroupTo) {
        this.date = date;
        this.serviceId = serviceId;
        this.animalId = animalId;
        this.typeAnimal = typeAnimal;
        this.done = done;
        this.number = number;
        this.live = live;
        this.normal = normal;
        this.weak = weak;
        this.death = death;
        this.mummy = mummy;
        this.tecnGroupTo = tecnGroupTo;
        this.weight = weight;
        this.boar1 = boar1;
        this.boar2 = boar2;
        this.boar3 = boar3;
        this.note = note;
        this.corpTo = corpTo;
        this.sectorTo = sectorTo;
        this.cellTo = cellTo;
        this.serviceResult = serviceResult;
        this.admNumber = admNumber;
        this.status = status;
        this.disposAnim = disposAnim;
        this.length = length;
        this.bread = bread;
        this.exterior = exterior;
        this.depthMysz = depthMysz;
        this.newCode = newCode;
        this.artifInsemen = artifInsemen;
        this.animGroupTo = animGroupTo;
    }

    public UploadServiceDoneDTO(long date, String serviceId, String animalId, int typeAnimal, Boolean done,
                                String vetExercise, String vetPreparat) {
        this.date = date;
        this.serviceId = serviceId;
        this.animalId = animalId;
        this.vetExercise = vetExercise;
        this.vetPreparat = vetPreparat;
    }
}
