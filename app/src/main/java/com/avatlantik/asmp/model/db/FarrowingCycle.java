package com.avatlantik.asmp.model.db;

import java.util.Date;

public class FarrowingCycle {
    private int id;
    private String externalId;
    private String animalId;
    private Date date;
    private String serviceData;
    private String result;

    public FarrowingCycle(int id, String externalId, String animalId, Date date, String serviceData, String result) {
        this.id = id;
        this.externalId = externalId;
        this.animalId = animalId;
        this.date = date;
        this.serviceData = serviceData;
        this.result = result;
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

    public String getAnimalId() {
        return animalId;
    }

    public void setAnimalId(String animalId) {
        this.animalId = animalId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getServiceData() {
        return serviceData;
    }

    public void setServiceData(String serviceData) {
        this.serviceData = serviceData;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int id;
        private String externalId;
        private String animalId;
        private Date date;
        private String serviceData;
        private String result;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder externalId(String externalId) {
            this.externalId = externalId;
            return this;
        }

        public Builder animalId(String animalId) {
            this.animalId = animalId;
            return this;
        }

        public Builder date(Date date) {
            this.date = date;
            return this;
        }

        public Builder serviceData(String serviceData) {
            this.serviceData = serviceData;
            return this;
        }

        public Builder result(String result) {
            this.result = result;
            return this;
        }

        public FarrowingCycle build() {
            return new FarrowingCycle(id, externalId, animalId, date, serviceData, result);
        }
    }
}
