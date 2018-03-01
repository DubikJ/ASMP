package com.avatlantik.asmp.model.db;

import android.content.Context;

import com.avatlantik.asmp.R;

import static com.avatlantik.asmp.common.Consts.TYPE_HOUSING_CORP;
import static com.avatlantik.asmp.common.Consts.TYPE_HOUSING_SECTOR;

public class Housing {
    private int id;
    private String externalId;
    private int type;
    private String name;
    private String parentId;

    public Housing(int id, String externalId, int type, String name, String parentId) {
        this.id = id;
        this.externalId = externalId;
        this.type = type;
        this.name = name;
        this.parentId = parentId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getNameType(Context context) {
        if(type == TYPE_HOUSING_CORP) {
            return context.getResources().getString(R.string.housing_corp) + ": " + name;
        }else if(type == TYPE_HOUSING_SECTOR) {
            return context.getResources().getString(R.string.housing_sector) + ": " + name;
        }else {
            return context.getResources().getString(R.string.housing_cell) + ": " + name;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int id;
        private String externalId;
        private int type;
        private String name;
        private String parentId;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder externalId(String externalId) {
            this.externalId = externalId;
            return this;
        }

        public Builder type(int type) {
            this.type = type;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder parentId(String parentId) {
            this.parentId = parentId;
            return this;
        }

        public Housing build() {
            return new Housing(id, externalId, type, name, parentId);
        }
    }
}
