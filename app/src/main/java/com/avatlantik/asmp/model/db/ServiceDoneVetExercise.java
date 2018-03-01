package com.avatlantik.asmp.model.db;

public class ServiceDoneVetExercise {
    private String animalId;
    private String exerciseId;
    private String preparatId;
    private VetExercise vetExercise;
    private VetPreparat vetPreparat;

    public ServiceDoneVetExercise(String animalId, String exerciseId, String preparatId) {
        this.animalId = animalId;
        this.exerciseId = exerciseId;
        this.preparatId = preparatId;
    }

    public String getAnimalId() {
        return animalId;
    }

    public void setAnimalId(String animalId) {
        this.animalId = animalId;
    }

    public String getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(String exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getPreparatId() {
        return preparatId;
    }

    public void setPreparatId(String preparatId) {
        this.preparatId = preparatId;
    }

    public VetExercise getVetExercise() {
        return vetExercise;
    }

    public void setVetExercise(VetExercise vetExercise) {
        this.vetExercise = vetExercise;
    }

    public VetPreparat getVetPreparat() {
        return vetPreparat;
    }

    public void setVetPreparat(VetPreparat vetPreparat) {
        this.vetPreparat = vetPreparat;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String animalId;
        private String exerciseId;
        private String preparatId;

        public Builder animalId(String animalId) {
            this.animalId = animalId;
            return this;
        }

        public Builder exerciseId(String exerciseId) {
            this.exerciseId = exerciseId;
            return this;
        }

        public Builder preparatId(String preparatId) {
            this.preparatId = preparatId;
            return this;
        }

        public ServiceDoneVetExercise build() {
            return new ServiceDoneVetExercise(animalId, exerciseId, preparatId);
        }
    }
}
