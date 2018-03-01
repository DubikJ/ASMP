package com.avatlantik.asmp.model.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DownloadWorkDayDTO extends WorkDayDTO {


    @Expose
    List<DownloadServiceDTO> services;
    @Expose
    List<DownloadAnimalDTO> animals;
    @Expose
    @SerializedName("services_done")
    private List<ServiceDoneDTO> serviceDoneDTO;
    @Expose
    @SerializedName("housing_list")
    List<HousingDTO> housings;
    @Expose
    @SerializedName("users_server")
    List<String> usersServer;
    @Expose
    @SerializedName("history_list")
    private List<AnimalHistoryDTO> animalHistoryDTOList;
    @Expose
    @SerializedName("farrowing_cycle_list")
    private List<AnimalHistoryDTO> farrowingCyclesDTOList;
    @Expose
    @SerializedName("conf_serv_gr")
    private List<ConformityServiceToGroupDTO> confServGroupDTOList;
    @Expose
    @SerializedName("breed_list")
    private List<BreedDTO> breedDTOList;
    @Expose
    @SerializedName("cond_anim")
    private List<AnimalStatusDTO> animalStatusDTOList;
    @Expose
    @SerializedName("dispos_anim")
    private List<AnimalDisposDTO> animalDisposDTOList;
    @Expose
    @SerializedName("herd_type")
    private List<HertTypeDTO> hertTypeDTOList;

    @Expose
    @SerializedName("vet_еxercisе_list")
    private List<VetExerciseDTO> vetExerciseDTOList;

    @Expose
    @SerializedName("vet_preparat_list")
    private List<VetPreparatDTO> vetPreparatDTOList;

    @Expose
    @SerializedName("animal_photo")
    private List<PhotoDTO> photoDTOList;

    public DownloadWorkDayDTO(int numMessage,
                              List<DownloadServiceDTO> services,
                              List<DownloadAnimalDTO> animals,
                              List<ServiceDoneDTO> serviceDoneDTO,
                              List<HousingDTO> housings,
                              List<String> usersServer,
                              List<AnimalHistoryDTO> animalHistoryDTOList,
                              List<AnimalHistoryDTO> farrowingCyclesDTOList,
                              List<ConformityServiceToGroupDTO> confServGroupDTOList,
                              List<BreedDTO> breedDTOList,
                              List<AnimalStatusDTO> animalStatusDTOList,
                              List<AnimalDisposDTO> animalDisposDTOList,
                              List<HertTypeDTO> hertTypeDTOList,
                              List<VetExerciseDTO> vetExerciseDTOList,
                              List<VetPreparatDTO> vetPreparatDTOList,
                              List<PhotoDTO> photoDTOList) {
        super(numMessage);
        this.services = services;
        this.animals = animals;
        this.serviceDoneDTO = serviceDoneDTO;
        this.housings = housings;
        this.usersServer = usersServer;
        this.animalHistoryDTOList = animalHistoryDTOList;
        this.farrowingCyclesDTOList = farrowingCyclesDTOList;
        this.confServGroupDTOList = confServGroupDTOList;
        this.breedDTOList = breedDTOList;
        this.animalStatusDTOList = animalStatusDTOList;
        this.animalDisposDTOList = animalDisposDTOList;
        this.hertTypeDTOList = hertTypeDTOList;
        this.vetExerciseDTOList = vetExerciseDTOList;
        this.vetPreparatDTOList = vetPreparatDTOList;
        this.photoDTOList = photoDTOList;
    }

    public List<DownloadServiceDTO> getServices() {
        return services;
    }

    public List<DownloadAnimalDTO> getAnimals() {
        return animals;
    }

    public List<ServiceDoneDTO> getServiceDoneDTO() {
        return serviceDoneDTO;
    }

    public List<HousingDTO> getHousings() {
        return housings;
    }

    public List<String> getUsersServer() {
        return usersServer;
    }

    public List<AnimalHistoryDTO> getAnimalHistoryDTOList() {
        return animalHistoryDTOList;
    }

    public List<AnimalHistoryDTO> getFarrowingCyclesDTOList() {
        return farrowingCyclesDTOList;
    }

    public List<ConformityServiceToGroupDTO> getConfServGroupDTOList() {
        return confServGroupDTOList;
    }

    public List<BreedDTO> getBreedDTOList() {
        return breedDTOList;
    }

    public List<AnimalStatusDTO> getAnimalStatusDTOList() {
        return animalStatusDTOList;
    }

    public List<AnimalDisposDTO> getAnimalDisposDTOList() {
        return animalDisposDTOList;
    }

    public List<HertTypeDTO> getHertTypeDTOList() {
        return hertTypeDTOList;
    }

    public List<VetExerciseDTO> getVetExerciseDTOList() {
        return vetExerciseDTOList;
    }

    public List<VetPreparatDTO> getVetPreparatDTOList() {
        return vetPreparatDTOList;
    }

    public List<PhotoDTO> getPhotoDTOList() {
        return photoDTOList;
    }
}
