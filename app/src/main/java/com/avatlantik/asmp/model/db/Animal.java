package com.avatlantik.asmp.model.db;

import android.content.Context;

import com.avatlantik.asmp.R;

import java.io.File;
import java.util.Date;

import static com.avatlantik.asmp.common.Consts.TYPE_GROUP_ANIMAL_BOAR;
import static com.avatlantik.asmp.common.Consts.TYPE_GROUP_ANIMAL_CULTIVATION;
import static com.avatlantik.asmp.common.Consts.TYPE_GROUP_ANIMAL_FATTENING;
import static com.avatlantik.asmp.common.Consts.TYPE_GROUP_ANIMAL_SOW;
import static com.avatlantik.asmp.common.Consts.TYPE_GROUP_ANIMAL_WEANING;

public class Animal {
    private int id;
    private String externalId;
    private int typeAnimal;
    private String rfid;
    private String code;
    private String addCode;
    private String name;
    private boolean isGroup;
    private boolean isGroupAnimal;
    private String group;
    private String corp;
    private String sector;
    private String cell;
    private int number;
    private String photo;
    private Date dateRec;
    private String status;
    private Double weight;
    private String breed;
    private String herd;
    private File photoFile;


    public Animal(int id, String externalId, int typeAnimal, String rfid, String code,
                  String addCode, String name, boolean isGroup, boolean isGroupAnimal,String group,
                  String corp, String sector, String cell, int number, String photo, Date dateRec,
                  String status, String breed, String herd) {
        this.id = id;
        this.externalId = externalId;
        this.typeAnimal = typeAnimal;
        this.rfid = rfid;
        this.code = code;
        this.addCode = addCode;
        this.name = name;
        this.isGroup = isGroup;
        this.isGroupAnimal = isGroupAnimal;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public int getTypeAnimal() {
        return typeAnimal;
    }

    public void setTypeAnimal(int typeAnimal) {
        this.typeAnimal = typeAnimal;
    }

    public String getRfid() {
        return rfid;
    }

    public void setRfid(String rfid) {
        this.rfid = rfid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAddCode() {
        return addCode;
    }

    public void setAddCode(String addCode) {
        this.addCode = addCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isGroupAnimal() {
        return isGroupAnimal;
    }

    public void setIsGroupAnimal(boolean isGroupAnimal) {
        this.isGroupAnimal = isGroupAnimal;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getCorp() {
        return corp;
    }

    public void setCorp(String corp) {
        this.corp = corp;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public void setIsGroup(boolean isGroup) {
        this.isGroup = isGroup;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Date getDateRec() {
        return dateRec;
    }

    public void setDateRec(Date dateRec) {
        this.dateRec = dateRec;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getHerd() {
        return herd;
    }

    public void setHerd(String herd) {
        this.herd = herd;
    }

    public File getPhotoFile() {
        return photoFile;
    }

    public void setPhotoFile(File photoFile) {
        this.photoFile = photoFile;
    }

    public String getNameType(Context context) {
        if(isGroup) {
            return name;
        }else if(isGroupAnimal){
            return name;
        }else{
            if (typeAnimal == TYPE_GROUP_ANIMAL_SOW) {
                return context.getResources().getString(R.string.group_animal_sow);
            } else if (typeAnimal == TYPE_GROUP_ANIMAL_BOAR) {
                return context.getResources().getString(R.string.group_animal_boar);
            } else if (typeAnimal == TYPE_GROUP_ANIMAL_WEANING) {
                return context.getResources().getString(R.string.group_animal_weaning);
            } else if (typeAnimal == TYPE_GROUP_ANIMAL_CULTIVATION) {
                return context.getResources().getString(R.string.group_animal_cultivation);
            } else if (typeAnimal == TYPE_GROUP_ANIMAL_FATTENING) {
                return context.getResources().getString(R.string.group_animal_fatenting);
            } else {
                return context.getResources().getString(R.string.group_animal_fatenting);
            }
        }
    }

    public String getFullNameType(Context context) {
        if(isGroup){
            return name;
        }else if(isGroupAnimal){
            return context.getResources().getString(R.string.service_item_grope) + name;
        }else{
            if (typeAnimal == TYPE_GROUP_ANIMAL_SOW) {
                return context.getResources().getString(R.string.group_animal_sow) + " № " + code;
            } else if (typeAnimal == TYPE_GROUP_ANIMAL_BOAR) {
                return context.getResources().getString(R.string.group_animal_boar) + " № " + code;
            } else if (typeAnimal == TYPE_GROUP_ANIMAL_WEANING) {
                return context.getResources().getString(R.string.group_animal_weaning) + " № " + code;
            } else if (typeAnimal == TYPE_GROUP_ANIMAL_CULTIVATION) {
                return context.getResources().getString(R.string.group_animal_cultivation) + " № " + code;
            } else if (typeAnimal == TYPE_GROUP_ANIMAL_FATTENING) {
                return context.getResources().getString(R.string.group_animal_fatenting) + " № " + code;
            } else {
                return context.getResources().getString(R.string.animal_no_registered);
            }
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int id;
        private String externalId;
        private int typeAnimal;
        private String rfid;
        private String code;
        private String addCode;
        private String name;
        private boolean isGroup;
        private boolean isGroupAnimal;
        private String group;
        private String corp;
        private String sector;
        private String cell;
        private int number;
        private String photo;
        private Date dateRec;
        private String status;
        private String breed;
        private String herd;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder externalId(String externalId) {
            this.externalId = externalId;
            return this;
        }

        public Builder typeAnimal(int typeAnimal) {
            this.typeAnimal = typeAnimal;
            return this;
        }

        public Builder rfid(String rfid) {
            this.rfid = rfid;
            return this;
        }

        public Builder code(String code) {
            this.code = code;
            return this;
        }

        public Builder addCode(String addCode) {
            this.addCode = addCode;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder isGroup(boolean isGroup) {
            this.isGroup = isGroup;
            return this;
        }

        public Builder isGroupAnimal(boolean isGroupAnimal) {
            this.isGroupAnimal = isGroupAnimal;
            return this;
        }

        public Builder group(String group) {
            this.group = group;
            return this;
        }

        public Builder corp(String corp) {
            this.corp = corp;
            return this;
        }

        public Builder sector(String sector) {
            this.sector = sector;
            return this;
        }

        public Builder cell(String cell) {
            this.cell = cell;
            return this;
        }

        public Builder number(int number) {
            this.number = number;
            return this;
        }

        public Builder photo(String photo) {
            this.photo = photo;
            return this;
        }

        public Builder dateRec(Date dateRec) {
            this.dateRec = dateRec;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder breed(String breed) {
            this.breed = breed;
            return this;
        }

        public Builder herd(String herd) {
            this.herd = herd;
            return this;
        }

        public Animal build() {
            return new Animal(id, externalId, typeAnimal, rfid, code, addCode, name, isGroup, isGroupAnimal,
                    group, corp, sector, cell, number, photo, dateRec, status, breed, herd);
        }
    }
}
