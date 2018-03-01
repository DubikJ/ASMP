package com.avatlantik.asmp.utils;

import com.avatlantik.asmp.model.db.Animal;
import com.avatlantik.asmp.model.db.ServiceDone;
import com.avatlantik.asmp.model.db.ServiceDoneVetExercise;
import com.avatlantik.asmp.model.json.UploadAnimalDTO;
import com.avatlantik.asmp.model.json.UploadServiceDoneDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Db2JsonModelConverter {

    public static UploadServiceDoneDTO convertServiceDone2Json(ServiceDone serviceDone, Animal animal) {

        return new UploadServiceDoneDTO(
                (serviceDone.getDate().getTime()),
                serviceDone.getServiceId(),
                serviceDone.getAnimalId(),
                animal.getTypeAnimal(),
                serviceDone.getDone(),
                serviceDone.getNumber(),
                serviceDone.getLive(),
                serviceDone.getNormal(),
                serviceDone.getWeak(),
                serviceDone.getDeath(),
                serviceDone.getMummy(),
                serviceDone.getTecnGroupTo(),
                serviceDone.getWeight(),
                serviceDone.getBoar1(),
                serviceDone.getBoar2(),
                serviceDone.getBoar3(),
                serviceDone.getNote(),
                serviceDone.getCorpTo(),
                serviceDone.getSectorTo(),
                serviceDone.getCellTo(),
                serviceDone.getResultService(),
                serviceDone.getAdmNumber(),
                serviceDone.getStatus(),
                serviceDone.getDisposAnim(),
                serviceDone.getLength(),
                serviceDone.getBread(),
                serviceDone.getExterior(),
                serviceDone.getDepthMysz(),
                serviceDone.getNewCode(),
                serviceDone.isArtifInsemen(),
                serviceDone.getAnimalGroupTo());
    }

    public static List<UploadServiceDoneDTO> convertServiceDoneVetExercise2Json(
                                         ServiceDone serviceDone, Animal animal,
                                         List<ServiceDoneVetExercise> serviceDoneVetExerciseList) {

        if (serviceDoneVetExerciseList == null) return Collections.emptyList();
        List<UploadServiceDoneDTO> result = new ArrayList<>();
        for (ServiceDoneVetExercise serviceDoneVet : serviceDoneVetExerciseList) {

            result.add(new UploadServiceDoneDTO(
                    serviceDone.getDate().getTime(),
                    serviceDone.getServiceId(),
                    serviceDone.getAnimalId(),
                    animal.getTypeAnimal(),
                    serviceDone.getDone(),
                    serviceDoneVet.getExerciseId(),
                    serviceDoneVet.getPreparatId()));
        }

        return result;
    }

    public static UploadAnimalDTO convertAnimal2Json(Animal animal) {

        return new UploadAnimalDTO(
                animal.getExternalId(),
                animal.getTypeAnimal(),
                animal.getRfid(),
                animal.getAddCode(),
                animal.getCode(),
                animal.isGroupAnimal(),
                animal.getName(),
                animal.getGroup(),
                animal.getNumber(),
                animal.getPhoto(),
                (animal.getDateRec().getTime()),
                animal.getBreed(),
                animal.getHerd());
    }

}
