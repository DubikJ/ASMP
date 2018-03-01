package com.avatlantik.asmp.model.db;

public class ServiceData {
    private int id;
    private String externalId;
    private String name;
    private int typeResult;
    private int numberPlane;
    private int numberDone;

    public ServiceData(int id, String externalId, String name, int typeResult) {
        this.id = id;
        this.externalId = externalId;
        this.name = name;
        this.typeResult = typeResult;
    }

    public ServiceData(int id, String externalId, String name, int typeResult, int numberPlane, int numberDone) {
        this.id = id;
        this.externalId = externalId;
        this.name = name;
        this.typeResult = typeResult;
        this.numberPlane = numberPlane;
        this.numberDone = numberDone;
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

    public int getTypeResult() {
        return typeResult;
    }

    public void setTypeResult(int typeResult) {
        this.typeResult = typeResult;
    }

    public int getNumberPlane() {
        return numberPlane;
    }

    public void setNumberPlane(int numberPlane) {
        this.numberPlane = numberPlane;
    }

    public int getNumberDone() {
        return numberDone;
    }

    public void setNumberDone(int numberDone) {
        this.numberDone = numberDone;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int id;
        private String externalId;
        private String name;
        private int typeResult;
        private int numberPlane;
        private int numberDone;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder externalId(String externalId) {
            this.externalId = externalId;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder typeResult(int typeResult) {
            this.typeResult = typeResult;
            return this;
        }

        public Builder numberPlane(int numberPlane) {
            this.numberPlane = numberPlane;
            return this;
        }

        public Builder numberDone(int numberDone) {
            this.numberDone = numberDone;
            return this;
        }


        public ServiceData build() {
            return new ServiceData(id, externalId, name, typeResult, numberPlane, numberDone);
        }
    }
}
