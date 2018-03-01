package com.avatlantik.asmp.model.json;

import com.google.gson.annotations.Expose;

public class AnimalTypeDTO {
    @Expose
    private String name;
    @Expose
    private int typeAnimal;

    public AnimalTypeDTO(String name, int typeAnimal) {
        this.name = name;
        this.typeAnimal = typeAnimal;
    }

    public String getName() {
        return name;
    }

    public int getTypeAnimal() {
        return typeAnimal;
    }
}
