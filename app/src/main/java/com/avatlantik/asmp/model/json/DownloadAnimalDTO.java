package com.avatlantik.asmp.model.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DownloadAnimalDTO extends AnimalTypeDTO {

    @Expose
    @SerializedName("animals_list")
    private List<AnimalDTO> animalsDTO;

    public DownloadAnimalDTO(String name, int typeAnimal,
                             List<AnimalDTO> animalsDTO) {
        super(name, typeAnimal);
        this.animalsDTO = animalsDTO;
    }

    public List<AnimalDTO> getAnimalsDTO() {
        return animalsDTO;
    }
}
