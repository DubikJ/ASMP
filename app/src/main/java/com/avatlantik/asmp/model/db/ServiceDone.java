package com.avatlantik.asmp.model.db;

import java.util.Date;

public class ServiceDone {
    private int id;
    private Date date;
    private Date dateDay;
    private String animalId;
    private String serviceId;
    private Boolean isPlane;
    private Boolean done;
    private String note;
    private int number;
    private int live;
    private int normal;
    private int weak;
    private int death;
    private int mummy;
    private double weight;
    private String boar1;
    private String boar2;
    private String boar3;
    private String tecnGroupTo;
    private String corpTo;
    private String sectorTo;
    private String cellTo;
    private String resultService;
    private int admNumber;
    private String status;
    private String disposAnim;
    private int length;
    private int bread;
    private int exterior;
    private int depthMysz;
    private String newCode;
    private Boolean artifInsemen;
    private String animalGroupTo;

    public ServiceDone(int id, Date date, Date dateDay, String animalId, String serviceId,
                       Boolean isPlane, Boolean done, String note, int number,
                       int live, int normal, int weak, int death, int mummy, double weight,
                       String boar1, String boar2, String boar3, String tecnGroupTo,
                       String corpTo, String sectorTo, String cellTo, String resultService,
                       int admNumber, String status, String disposAnim, int length, int bread,
                       int exterior, int depthMysz, String newCode, Boolean artifInsemen, String animalGroupTo) {
        this.id = id;
        this.date = date;
        this.dateDay = dateDay;
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
        this.resultService = resultService;
        this.admNumber = admNumber;
        this.status = status;
        this.disposAnim = disposAnim;
        this.length = length;
        this.bread = bread;
        this.exterior = exterior;
        this.depthMysz = depthMysz;
        this.newCode = newCode;
        this.artifInsemen = artifInsemen;
        this.animalGroupTo = animalGroupTo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDateDay() {
        return dateDay;
    }

    public void setDateDay(Date dateDay) {
        this.dateDay = dateDay;
    }

    public String getAnimalId() {
        return animalId;
    }

    public void setAnimalId(String animalId) {
        this.animalId = animalId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public Boolean getPlane() {
        return isPlane;
    }

    public void setPlane(Boolean plane) {
        isPlane = plane;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getLive() {
        return live;
    }

    public void setLive(int live) {
        this.live = live;
    }

    public int getNormal() {
        return normal;
    }

    public void setNormal(int normal) {
        this.normal = normal;
    }

    public int getWeak() {
        return weak;
    }

    public void setWeak(int weak) {
        this.weak = weak;
    }

    public int getDeath() {
        return death;
    }

    public void setDeath(int death) {
        this.death = death;
    }

    public int getMummy() {
        return mummy;
    }

    public void setMummy(int mummy) {
        this.mummy = mummy;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getBoar1() {
        return boar1;
    }

    public void setBoar1(String boar1) {
        this.boar1 = boar1;
    }

    public String getBoar2() {
        return boar2;
    }

    public void setBoar2(String boar2) {
        this.boar2 = boar2;
    }

    public String getBoar3() {
        return boar3;
    }

    public void setBoar3(String boar3) {
        this.boar3 = boar3;
    }

    public String getTecnGroupTo() {
        return tecnGroupTo;
    }

    public void setTecnGroupTo(String tecnGroupTo) {
        this.tecnGroupTo = tecnGroupTo;
    }

    public String getCorpTo() {
        return corpTo;
    }

    public void setCorpTo(String corpTo) {
        this.corpTo = corpTo;
    }

    public String getSectorTo() {
        return sectorTo;
    }

    public void setSectorTo(String sectorTo) {
        this.sectorTo = sectorTo;
    }

    public String getCellTo() {
        return cellTo;
    }

    public void setCellTo(String cellTo) {
        this.cellTo = cellTo;
    }

    public String getResultService() {
        return resultService;
    }

    public void setResultService(String resultService) {
        this.resultService = resultService;
    }

    public int getAdmNumber() {
        return admNumber;
    }

    public void setAdmNumber(int admNumber) {
        this.admNumber = admNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDisposAnim() {
        return disposAnim;
    }

    public void setDisposAnim(String disposAnim) {
        this.disposAnim = disposAnim;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getBread() {
        return bread;
    }

    public void setBread(int bread) {
        this.bread = bread;
    }

    public int getExterior() {
        return exterior;
    }

    public void setExterior(int exterior) {
        this.exterior = exterior;
    }

    public int getDepthMysz() {
        return depthMysz;
    }

    public void setDepthMysz(int depthMysz) {
        this.depthMysz = depthMysz;
    }

    public String getNewCode() {
        return newCode;
    }

    public void setNewCode(String newCode) {
        this.newCode = newCode;
    }

    public Boolean isArtifInsemen() {
        return artifInsemen;
    }

    public void setArtifInsemen(Boolean artifInsemen) {
        this.artifInsemen = artifInsemen;
    }

    public String getAnimalGroupTo() {
        return animalGroupTo;
    }

    public void setAnimalGroupTo(String animalGroupTo) {
        this.animalGroupTo = animalGroupTo;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int id;
        private Date date;
        private Date dateDay;
        private String animalId;
        private String serviceId;
        private Boolean isPlane;
        private Boolean done;
        private String note;
        private int number;
        private int live;
        private int normal;
        private int weak;
        private int death;
        private int mummy;
        private double weight;
        private String boar1;
        private String boar2;
        private String boar3;
        private String tecnGroupTo;
        private String corpTo;
        private String sectorTo;
        private String cellTo;
        private String resultService;
        private int admNumber;
        private String status;
        private String disposAnim;
        private int length;
        private int bread;
        private int exterior;
        private int depthMysz;
        private String newCode;
        private boolean artifInsemen;
        private String animalGroupTo;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder dateDay(Date dateDay) {
            this.dateDay = dateDay;
            return this;
        }

        public Builder date(Date date) {
            this.date = date;
            return this;
        }

        public Builder animalId(String animalId) {
            this.animalId = animalId;
            return this;
        }

        public Builder serviceId(String serviceId) {
            this.serviceId = serviceId;
            return this;
        }

        public Builder isPlane(Boolean isPlane) {
            this.isPlane = isPlane;
            return this;
        }

        public Builder done(Boolean done) {
            this.done = done;
            return this;
        }

        public Builder note(String note) {
            this.note = note;
            return this;
        }

        public Builder number(int number) {
            this.number = number;
            return this;
        }

        public Builder live(int live) {
            this.live = live;
            return this;
        }

        public Builder normal(int normal) {
            this.normal = normal;
            return this;
        }

        public Builder weak(int weak) {
            this.weak = weak;
            return this;
        }

        public Builder death(int death) {
            this.death = death;
            return this;
        }

        public Builder mummy(int mummy) {
            this.mummy = mummy;
            return this;
        }

        public Builder weight(double weight) {
            this.weight = weight;
            return this;
        }

        public Builder boar1(String boar1) {
            this.boar1 = boar1;
            return this;
        }

        public Builder boar2(String boar2) {
            this.boar2 = boar2;
            return this;
        }

        public Builder boar3(String boar3) {
            this.boar3 = boar3;
            return this;
        }

        public Builder tecnGroupTo(String tecnGroupTo) {
            this.tecnGroupTo = tecnGroupTo;
            return this;
        }

        public Builder corpTo(String corpTo) {
            this.corpTo = corpTo;
            return this;
        }

        public Builder sectorTo(String sectorTo) {
            this.sectorTo = sectorTo;
            return this;
        }

        public Builder cellTo(String cellTo) {
            this.cellTo = cellTo;
            return this;
        }

        public Builder resultService(String resultService) {
            this.resultService = resultService;
            return this;
        }

        public Builder admNumber(int admNumber) {
            this.admNumber = admNumber;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder disposAnim(String disposAnim) {
            this.disposAnim = disposAnim;
            return this;
        }

        public Builder length(int length) {
            this.length = length;
            return this;
        }

        public Builder bread(int bread) {
            this.bread = bread;
            return this;
        }

        public Builder exterior(int exterior) {
            this.exterior = exterior;
            return this;
        }

        public Builder depthMysz(int depthMysz) {
            this.depthMysz = depthMysz;
            return this;
        }

        public Builder newCode(String newCode) {
            this.newCode = newCode;
            return this;
        }

        public Builder artifInsemen(Boolean artifInsemen) {
            this.artifInsemen = artifInsemen;
            return this;
        }

        public Builder animalGroupTo(String animalGroupTo) {
            this.animalGroupTo = animalGroupTo;
            return this;
        }

        public ServiceDone build() {
            return new ServiceDone(id, date, dateDay, animalId, serviceId, isPlane, done, note,
                    number, live, normal, weak, death, mummy, weight, boar1, boar2, boar3, tecnGroupTo, corpTo,
                    sectorTo, cellTo, resultService, admNumber, status, disposAnim, length, bread,
                    exterior, depthMysz, newCode, artifInsemen, animalGroupTo);
        }
    }
}
