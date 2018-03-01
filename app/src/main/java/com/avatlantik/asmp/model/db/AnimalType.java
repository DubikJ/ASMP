package com.avatlantik.asmp.model.db;

import com.avatlantik.asmp.R;

import static com.avatlantik.asmp.common.Consts.TYPE_GROUP_ANIMAL_BOAR;
import static com.avatlantik.asmp.common.Consts.TYPE_GROUP_ANIMAL_CULTIVATION;
import static com.avatlantik.asmp.common.Consts.TYPE_GROUP_ANIMAL_FATTENING;
import static com.avatlantik.asmp.common.Consts.TYPE_GROUP_ANIMAL_SOW;
import static com.avatlantik.asmp.common.Consts.TYPE_GROUP_ANIMAL_WEANING;

public class AnimalType {
    private int id;
    private int typeAnimal;
    private String name;

    public AnimalType(int id, int typeAnimal, String name) {
        this.id = id;
        this.typeAnimal = typeAnimal;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTypeAnimal() {
        return typeAnimal;
    }

    public void setTypeAnimal(int typeAnimal) {
        this.typeAnimal = typeAnimal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDravableTypeAnimal() {

        switch (typeAnimal) {
            case TYPE_GROUP_ANIMAL_SOW:
                return R.drawable.ic_sow;
            case TYPE_GROUP_ANIMAL_BOAR:
                return R.drawable.ic_boar;
            case TYPE_GROUP_ANIMAL_WEANING:
                return R.drawable.ic_absence;
            case TYPE_GROUP_ANIMAL_CULTIVATION:
                return R.drawable.ic_growth;
            case TYPE_GROUP_ANIMAL_FATTENING:
                return R.drawable.ic_fattening;
            default:
                return R.drawable.ic_pig;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int id;
        private int typeAnimal;
        private String name;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder typeAnimal(int typeAnimal) {
            this.typeAnimal = typeAnimal;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public AnimalType build() {
            return new AnimalType(id, typeAnimal, name);
        }
    }
}
