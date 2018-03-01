package com.avatlantik.asmp.repository;

import com.avatlantik.asmp.model.ParameterInfo;
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
import com.avatlantik.asmp.model.json.ConformityServiceToGroupDTO;

import java.util.List;

public interface DataRepository {

    String getUserSetting(String settingId);

    List<ServiceData> getServiceList();

    List<ServiceData> getServiceUsetToday();

    List<ServiceData> getServiceNotUsetToday();

    List<ServiceData> getServiceNotUsetTodayByAnimalId(String externalId);

    ServiceData getServiceById(String externalId);

    List<ServiceData> getServiceNotInList(List<String> serviceList);

    List<Animal> getAnimalList();

    Animal getAnimalById(String externalId);

    Animal getAnimalByRFID(String RFID);

    List<Animal> getAnimalsByTypeAnimal(int typeAnimal);

    List<Animal> getAnimalsByTypeAnimalOnlyElement(int typeAnimal);

    List<Animal> getAnimalsByTypeAnimalAndParenId(int typeAnimal, String externalId);

    List<Animal> getGroupeAnimalsByTypeAnimal(int typeAnimal);

    List<Animal> getAllGroupsAnimalsByTypeAnimal(int typeAnimal);

    List<Animal> getTecnicalGroupsAnimalsByTypeAnimal(int typeAnimal);

    List<Animal> getAnimalsByListId(List<String> listID);

    List<Animal> getAnimalsByGroupeAnimal(String groupId);

    List<ServiceDone>  getServiceDoneList();

    List<ServiceDone>  getServiceDoneByAnimalId(String externalId);

    List<ServiceDone>  getServiceDoneByServiceId(String externalId);

    List<ServiceDone>  getServiceDoneByListTableId(List<String> listID);

    ServiceDone  getServiceDoneByServiceIdAnimalId(String serviceId, String animalId);

    List<AnimalHistory>  getAnimalHistoryByAnimalId(String externalId);

    List<AnimalType> getAnimalTypeList();

    List<Housing> getHousingListByType(int type);

    Housing getHousingById(String externalId);

    List<Housing> getHousingListByTypeAndParenID(int type, String parentId);

    List<Housing> getHousingListByParenID(String parentId);

    List<FarrowingCycle>  getFarrowingCyclesByAnimalId(String externalId);

    List<String>  getUsersServer();

    List<String> getChangedElements(String nameElement);

    Animal getGroupByServiceIdAndTypeAnimal(String externalId, int typeAnimal);

    Breed getBreedById(String externalId);

    List<Breed> getBreedList();

    AnimalStatus getAnimalStatusById(String externalId);

    List<AnimalStatus> getAnimalStatusList();

    AnimalDispos getAnimalDisposById(String externalId);

    List<AnimalDispos> getAnimalDisposList();

    HertType getHertTypeById(String externalId);

    List<HertType> getHertTypeList();

    List<Integer> getAnimalTypeListByServiceID(String serviceID);

    List<AnimalType> getAnimalTypeListByListTypes(List<Integer> typeList);

    VetExercise getVetExerciseById(String externalId);

    List<VetExercise> getVetExerciseList();

    VetPreparat getVetPreparatById(String externalId);

    List<VetPreparat> getVetPreparatList();

    List<ServiceDoneVetExercise> getServiceDoneVetExerciseByAnimalId(String animalId);

    byte[] getImageByID(String externalID);

    void insertUserSetting(ParameterInfo usersetting);

    void clearDataBase();

    void insertServiceData(ServiceData serviceData);

    void insertServiceDataList(List<ServiceData> serviceDataList);

    void insertAnimal(Animal animal);

    void insertAnimalsList(List<Animal> animalList);

    void updateAnimalExternalId(String appExternalId, String newExternalId);

    void insertServiceDone(ServiceDone serviceDone);

    void insertServiceDoneList(List<ServiceDone> serviceDoneList);

    void insertAnimalHistory(AnimalHistory animalHistory);

    void insertAnimalHistoryList(List<AnimalHistory> animalHistoryList);

    void insertAnimalTypeList(List<AnimalType> animalTypeList);

    void insertAnimalType(AnimalType animalType);

    void insertHousingList(List<Housing> housingList);

    void insertHousing(Housing housing);

    void insertFarrowingCycle(FarrowingCycle farrowingCycle);

    void insertFarrowingCycleList(List<FarrowingCycle> farrowingCycleList);

    void insertUsersServerList(List<String> usersServer);

    void insertChangedElement(String nameElement, String ID);

    void insertConformityServiceGroup(List<ConformityServiceToGroupDTO> confServGroupDTOList);

    void clearChangedElement();

    void insertBreedList(List<Breed> breedList);

    void insertAnimalStatusList(List<AnimalStatus> animalStatusList);

    void insertAnimalDisposList(List<AnimalDispos> animalDisposList);

    void insertHertTypeList(List<HertType> hertTypeList);

    void insertAnimalTypeToServiceList(String serviceId, List<Integer> animalTypeList);

    void insertVetExerciseList(List<VetExercise> vetExerciseList);

    void insertVetPreparatList(List<VetPreparat> vetPreparatList);

    void insertServiceDoneVetExercise(ServiceDoneVetExercise serviceDoneVetExercise);

    void insertServiceDoneVetExerciseList(List<ServiceDoneVetExercise> serviceDoneVetExerciseList);

    void insertImage(String externalID, byte[] imageBytes);
}
