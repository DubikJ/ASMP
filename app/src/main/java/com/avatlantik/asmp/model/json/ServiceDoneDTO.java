package com.avatlantik.asmp.model.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public final class ServiceDoneDTO {

    @Expose
    private int date;

    @Expose
    private String animalId;

    @Expose
    private String serviceId;

    @Expose
    private Boolean isPlane;

    @Expose
    private Boolean done;

    @Expose
    private String note;

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
    private double weight;

    @Expose
    private String boar1;

    @Expose
    private String boar2;

    @Expose
    private String boar3;

    @Expose
    @SerializedName("tecn_group_to")
    private String tecnGroupTo;

    @Expose
    private String corpTo;

    @Expose
    private String sectorTo;

    @Expose
    private String cellTo;

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

    public ServiceDoneDTO(int date, String animalId, String serviceId, Boolean isPlane, Boolean done,
                          String note, int number, int live, int normal, int weak, int death, int mummy,
                          double weight, String boar1, String boar2, String boar3, String tecnGroupTo,
                          String corpTo, String sectorTo, String cellTo, int admNumber,
                          String status, String disposAnim, int length, int bread, int exterior, int depthMysz,
                          String newCode, String vetExercise, String vetPreparat, Boolean artifInsemen, String animGroupTo) {
        this.date = date;
        this.animalId = animalId;
        this.serviceId = serviceId;
        this.isPlane = isPlane;
        this.done = done;
        this.note = note;
        this.number = number;
        this.live = live;
        this.normal = normal;
        this.weak = weak;
        this.death = death;
        this.mummy = mummy;
        this.weight = weight;
        this.boar1 = boar1;
        this.boar2 = boar2;
        this.boar3 = boar3;
        this.tecnGroupTo = tecnGroupTo;
        this.corpTo = corpTo;
        this.sectorTo = sectorTo;
        this.cellTo = cellTo;
        this.admNumber = admNumber;
        this.status = status;
        this.disposAnim = disposAnim;
        this.length = length;
        this.bread = bread;
        this.exterior = exterior;
        this.depthMysz = depthMysz;
        this.newCode = newCode;
        this.vetExercise = vetExercise;
        this.vetPreparat = vetPreparat;
        this.artifInsemen = artifInsemen;
        this.animGroupTo = animGroupTo;
    }

    public int getDate() {
        return date;
    }

    public String getAnimalId() {
        return animalId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public Boolean getPlane() {
        return isPlane;
    }

    public Boolean getDone() {
        return done;
    }

    public String getNote() {
        return note;
    }

    public int getNumber() {
        return number;
    }

    public int getLive() {
        return live;
    }

    public int getNormal() {
        return normal;
    }

    public int getWeak() {
        return weak;
    }

    public int getDeath() {
        return death;
    }

    public int getMummy() {
        return mummy;
    }

    public double getWeight() {
        return weight;
    }

    public String getBoar1() {
        return boar1;
    }

    public String getBoar2() {
        return boar2;
    }

    public String getBoar3() {
        return boar3;
    }

    public String getTecnGroupTo() {
        return tecnGroupTo;
    }

    public String getCorpTo() {
        return corpTo;
    }

    public String getSectorTo() {
        return sectorTo;
    }

    public String getCellTo() {
        return cellTo;
    }

    public int getAdmNumber() {
        return admNumber;
    }

    public String getStatus() {
        return status;
    }

    public String getDisposAnim() {
        return disposAnim;
    }

    public int getLength() {
        return length;
    }

    public int getBread() {
        return bread;
    }

    public int getExterior() {
        return exterior;
    }

    public int getDepthMysz() {
        return depthMysz;
    }

    public String getNewCode() {
        return newCode;
    }

    public String getVetExercise() {
        return vetExercise;
    }

    public String getVetPreparat() {
        return vetPreparat;
    }

    public Boolean getArtifInsemen() {return artifInsemen;}

    public String getAnimGroupTo() {return animGroupTo;}
}
