package com.avatlantik.asmp.utils;

import com.avatlantik.asmp.model.db.Animal;
import com.avatlantik.asmp.model.db.AnimalDispos;
import com.avatlantik.asmp.model.db.AnimalHistory;
import com.avatlantik.asmp.model.db.AnimalStatus;
import com.avatlantik.asmp.model.db.AnimalType;
import com.avatlantik.asmp.model.db.Breed;
import com.avatlantik.asmp.model.db.FarrowingCycle;
import com.avatlantik.asmp.model.db.HertType;
import com.avatlantik.asmp.model.db.Housing;
import com.avatlantik.asmp.model.db.ServiceData;
import com.avatlantik.asmp.model.db.ServiceDone;
import com.avatlantik.asmp.model.db.ServiceDoneVetExercise;
import com.avatlantik.asmp.model.db.VetExercise;
import com.avatlantik.asmp.model.db.VetPreparat;
import com.avatlantik.asmp.model.json.AnimalDTO;
import com.avatlantik.asmp.model.json.AnimalDisposDTO;
import com.avatlantik.asmp.model.json.AnimalHistoryDTO;
import com.avatlantik.asmp.model.json.AnimalStatusDTO;
import com.avatlantik.asmp.model.json.BreedDTO;
import com.avatlantik.asmp.model.json.DownloadAnimalDTO;
import com.avatlantik.asmp.model.json.DownloadServiceDTO;
import com.avatlantik.asmp.model.json.HertTypeDTO;
import com.avatlantik.asmp.model.json.HousingDTO;
import com.avatlantik.asmp.model.json.ServiceDoneDTO;
import com.avatlantik.asmp.model.json.VetExerciseDTO;
import com.avatlantik.asmp.model.json.VetPreparatDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Json2DbModelConverter {

    public static List<ServiceData> convertServiceData(List<DownloadServiceDTO> serviceDTOs) {
        if (serviceDTOs == null) return Collections.emptyList();
        List<ServiceData> result = new ArrayList<>();
        for (DownloadServiceDTO serviceDTO : serviceDTOs) {
            result.add(ServiceData.builder().externalId(serviceDTO.getExternalId())
                    .name(serviceDTO.getName())
                    .typeResult(serviceDTO.getTypeRes()).build());
        }
        return result;
    }

    public static List<ServiceData> convertServiceDataToAnimalType(List<DownloadServiceDTO> serviceDTOs) {
        if (serviceDTOs == null) return Collections.emptyList();
        List<ServiceData> result = new ArrayList<>();
        for (DownloadServiceDTO serviceDTO : serviceDTOs) {
            result.add(ServiceData.builder().externalId(serviceDTO.getExternalId())
                    .name(serviceDTO.getName())
                    .typeResult(serviceDTO.getTypeRes()).build());
        }
        return result;
    }

    public static List<AnimalType> convertAnimalType(List<DownloadAnimalDTO> animalDTOs) {
        if (animalDTOs == null) return Collections.emptyList();
        List<AnimalType> result = new ArrayList<>();
        for (DownloadAnimalDTO animalDTO : animalDTOs) {
            if(animalDTO.getTypeAnimal()==0){ continue;}
            result.add(AnimalType.builder().name(animalDTO.getName())
                    .typeAnimal(animalDTO.getTypeAnimal()).build());
        }
        return result;
    }

    public static void convertServiceDone(List<ServiceDoneDTO> serviceDTOList,
                                          List<ServiceDone> serviceDoneList,
                                          List<ServiceDoneVetExercise> serviceDoneVetExerciseList) {
        for (ServiceDoneDTO serviceDoneDTO : serviceDTOList) {
            if (serviceDoneDTO == null)
                continue;
            serviceDoneList.add(
                    ServiceDone.builder()
                            .date(new Date(Long.valueOf(serviceDoneDTO.getDate()) * 1000L))
                            .dateDay(new Date(Long.valueOf(serviceDoneDTO.getDate()) * 1000L))
                            .serviceId(serviceDoneDTO.getServiceId())
                            .animalId(serviceDoneDTO.getAnimalId())
                            .isPlane(serviceDoneDTO.getPlane())
                            .done(serviceDoneDTO.getDone())
                            .number(serviceDoneDTO.getNumber())
                            .live(serviceDoneDTO.getLive())
                            .weak(serviceDoneDTO.getWeak())
                            .death(serviceDoneDTO.getDeath())
                            .mummy(serviceDoneDTO.getMummy())
                            .tecnGroupTo(serviceDoneDTO.getTecnGroupTo())
                            .weight(serviceDoneDTO.getWeight())
                            .boar1(serviceDoneDTO.getBoar1())
                            .boar2(serviceDoneDTO.getBoar2())
                            .boar3(serviceDoneDTO.getBoar3())
                            .note(serviceDoneDTO.getNote())
                            .corpTo(serviceDoneDTO.getCorpTo())
                            .sectorTo(serviceDoneDTO.getSectorTo())
                            .cellTo(serviceDoneDTO.getCellTo())
                            .admNumber(serviceDoneDTO.getAdmNumber())
                            .status(serviceDoneDTO.getStatus())
                            .disposAnim(serviceDoneDTO.getDisposAnim())
                            .length(serviceDoneDTO.getLength())
                            .bread(serviceDoneDTO.getBread())
                            .exterior(serviceDoneDTO.getExterior())
                            .depthMysz(serviceDoneDTO.getDepthMysz())
                            .newCode(serviceDoneDTO.getNewCode())
                            .artifInsemen(serviceDoneDTO.getArtifInsemen())
                            .animalGroupTo(serviceDoneDTO.getAnimGroupTo())
                            .build());
            if(serviceDoneDTO.getVetExercise()!=null ||
                    !serviceDoneDTO.getVetExercise().isEmpty()) {

                serviceDoneVetExerciseList.add(ServiceDoneVetExercise.builder()
                        .animalId(serviceDoneDTO.getAnimalId())
                        .exerciseId(serviceDoneDTO.getVetExercise())
                        .preparatId(serviceDoneDTO.getVetPreparat())
                        .build());
            }
        }
    }

    public static List<Animal> convertAnimal(List<DownloadAnimalDTO> animalDTOs) {
        if (animalDTOs == null) return Collections.emptyList();
        List<Animal> result = new ArrayList<>();
        for (DownloadAnimalDTO animaTypelDTO : animalDTOs) {
            List<Animal> animalitemList = new ArrayList<>();
            for (AnimalDTO animalDTO : animaTypelDTO.getAnimalsDTO()) {
                if (animalDTO == null)
                    continue;
                animalitemList.add(
                        Animal.builder()
                                .externalId(animalDTO.getExternalId())
                                .typeAnimal(animaTypelDTO.getTypeAnimal())
                                .rfid(animalDTO.getRfid())
                                .code(animalDTO.getCode())
                                .addCode(animalDTO.getAddCode())
                                .name(animalDTO.getName())
                                .isGroup(animalDTO.isGroup())
                                .isGroupAnimal(animalDTO.isGroupAnimal())
                                .group(animalDTO.getGroup())
                                .corp(animalDTO.getCorp())
                                .sector(animalDTO.getSector())
                                .cell(animalDTO.getCell())
                                .number(animalDTO.getNumber())
                                .photo(animalDTO.getPhoto())
                                .dateRec(new Date(Long.valueOf(animalDTO.getDateRec()*1000L)))
                                .status(animalDTO.getStatus())
                                .breed(animalDTO.getBreed())
                                .herd(animalDTO.getHerd())
                                .build());
            }
            result.addAll(animalitemList);
        }

        return result;
    }

    public static List<AnimalHistory> convertHistoryList(List<AnimalHistoryDTO> animalHistoryDTOs) {
        if (animalHistoryDTOs == null) return Collections.emptyList();
        List<AnimalHistory> result = new ArrayList<>();
        for (AnimalHistoryDTO animalHistory : animalHistoryDTOs) {
            if (animalHistory == null)
                continue;
            result.add(
                    AnimalHistory.builder()
                            .externalId(animalHistory.getExternalId())
                            .animalId(animalHistory.getAnimalId())
                            .date(new Date(Long.valueOf(animalHistory.getDate() * 1000L)))
                            .serviceData(animalHistory.getService())
                            .result(animalHistory.getResult())
                            .build());
        }

        return result;
    }

    public static List<FarrowingCycle> convertFarrowingCycle(List<AnimalHistoryDTO> animalFarrowingCycleDTOs) {
        if (animalFarrowingCycleDTOs == null) return Collections.emptyList();
        List<FarrowingCycle> result = new ArrayList<>();

        for (AnimalHistoryDTO farrowingCycle : animalFarrowingCycleDTOs) {
            if (farrowingCycle == null)
                continue;
            result.add(
                    FarrowingCycle.builder()
                            .externalId(farrowingCycle.getExternalId())
                            .animalId(farrowingCycle.getAnimalId())
                            .date(new Date(Long.valueOf(farrowingCycle.getDate()*1000L)))
                            .serviceData(farrowingCycle.getService())
                            .result(farrowingCycle.getResult())
                            .build());
        }

        return result;
    }

    public static List<Housing> convertHousings(List<HousingDTO> housingDTOList) {
        if (housingDTOList == null) return Collections.emptyList();
        List<Housing> result = new ArrayList<>();
        for (HousingDTO housingDTO : housingDTOList) {
            result.add(Housing.builder()
                            .externalId(housingDTO.getExternalId())
                            .type(housingDTO.getType())
                            .name(housingDTO.getName())
                            .parentId(housingDTO.getParentId())
                            .build());
        }
        return result;
    }

    public static List<Breed> convertBreedList(List<BreedDTO> breedDTOList) {
        if (breedDTOList == null) return Collections.emptyList();
        List<Breed> result = new ArrayList<>();
        for (BreedDTO breedDTO : breedDTOList) {
            result.add(Breed.builder()
                    .externalId(breedDTO.getExternalId())
                    .name(breedDTO.getName())
                    .build());
        }
        return result;
    }

    public static List<AnimalStatus> convertAnimalStatusList(List<AnimalStatusDTO> animalStatusDTOList) {
        if (animalStatusDTOList == null) return Collections.emptyList();
        List<AnimalStatus> result = new ArrayList<>();
        for (AnimalStatusDTO animalStatusDTO : animalStatusDTOList) {
            result.add(AnimalStatus.builder()
                    .externalId(animalStatusDTO.getExternalId())
                    .name(animalStatusDTO.getName())
                    .build());
        }
        return result;
    }

    public static List<AnimalDispos> convertAnimalDisposList(List<AnimalDisposDTO> breedDTOList) {
        if (breedDTOList == null) return Collections.emptyList();
        List<AnimalDispos> result = new ArrayList<>();
        for (AnimalDisposDTO breedDTO : breedDTOList) {
            result.add(AnimalDispos.builder()
                    .externalId(breedDTO.getExternalId())
                    .name(breedDTO.getName())
                    .build());
        }
        return result;
    }

    public static List<HertType> convertHertTypeList(List<HertTypeDTO> hertTypeDTOList) {
        if (hertTypeDTOList == null) return Collections.emptyList();
        List<HertType> result = new ArrayList<>();
        for (HertTypeDTO hertTypeDTO : hertTypeDTOList) {
            result.add(HertType.builder()
                    .externalId(hertTypeDTO.getExternalId())
                    .name(hertTypeDTO.getName())
                    .build());
        }
        return result;
    }


    public static List<VetExercise> convertVetExerciseList(List<VetExerciseDTO> vetExerciseDTOList) {
        if (vetExerciseDTOList == null) return Collections.emptyList();
        List<VetExercise> result = new ArrayList<>();
        for (VetExerciseDTO exerciseDTO : vetExerciseDTOList) {
            result.add(VetExercise.builder()
                    .externalId(exerciseDTO.getExternalId())
                    .name(exerciseDTO.getName())
                    .build());
        }
        return result;
    }

    public static List<VetPreparat> convertVetPreparatList(List<VetPreparatDTO> preparatDTOList) {
        if (preparatDTOList == null) return Collections.emptyList();
        List<VetPreparat> result = new ArrayList<>();
        for (VetPreparatDTO vetPreparatDTO : preparatDTOList) {
            result.add(VetPreparat.builder()
                    .externalId(vetPreparatDTO.getExternalId())
                    .name(vetPreparatDTO.getName())
                    .build());
        }
        return result;
    }

}
