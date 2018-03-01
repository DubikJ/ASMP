package com.avatlantik.asmp.repository;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;

import com.avatlantik.asmp.db.DbContract.AnimalContract;
import com.avatlantik.asmp.db.DbContract.AnimalDisposContract;
import com.avatlantik.asmp.db.DbContract.AnimalHistoryContract;
import com.avatlantik.asmp.db.DbContract.AnimalStatusContract;
import com.avatlantik.asmp.db.DbContract.AnimalTypeContract;
import com.avatlantik.asmp.db.DbContract.BreedContract;
import com.avatlantik.asmp.db.DbContract.ChangingContrack;
import com.avatlantik.asmp.db.DbContract.ConformityServiceToGroupContrack;
import com.avatlantik.asmp.db.DbContract.FarrowingCycleContract;
import com.avatlantik.asmp.db.DbContract.HertTypeContract;
import com.avatlantik.asmp.db.DbContract.HousingContract;
import com.avatlantik.asmp.db.DbContract.ImageContract;
import com.avatlantik.asmp.db.DbContract.ServiceContract;
import com.avatlantik.asmp.db.DbContract.ServiceDoneContract;
import com.avatlantik.asmp.db.DbContract.ServiceDoneVetExerciseContract;
import com.avatlantik.asmp.db.DbContract.ServiceToAnimalTypeContract;
import com.avatlantik.asmp.db.DbContract.UserSettingsContract;
import com.avatlantik.asmp.db.DbContract.UsersServerContract;
import com.avatlantik.asmp.db.DbContract.VeterinaryExerciseContract;
import com.avatlantik.asmp.db.DbContract.VeterinaryPrepatratContract;
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

import org.joda.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.avatlantik.asmp.common.Consts.CLEAR_GUID;
import static com.avatlantik.asmp.common.Consts.TYPE_GROUP_ANIMAL_ALL;
import static com.avatlantik.asmp.common.Consts.TYPE_GROUP_ANIMAL_BOAR;
import static com.avatlantik.asmp.common.Consts.TYPE_GROUP_ANIMAL_SOW;
import static com.avatlantik.asmp.repository.ModelConverter.buildAnimal;
import static com.avatlantik.asmp.repository.ModelConverter.buildAnimalDispos;
import static com.avatlantik.asmp.repository.ModelConverter.buildAnimalHistory;
import static com.avatlantik.asmp.repository.ModelConverter.buildAnimalStatus;
import static com.avatlantik.asmp.repository.ModelConverter.buildAnimalType;
import static com.avatlantik.asmp.repository.ModelConverter.buildBreed;
import static com.avatlantik.asmp.repository.ModelConverter.buildFarrowingCycle;
import static com.avatlantik.asmp.repository.ModelConverter.buildHertType;
import static com.avatlantik.asmp.repository.ModelConverter.buildHousing;
import static com.avatlantik.asmp.repository.ModelConverter.buildService;
import static com.avatlantik.asmp.repository.ModelConverter.buildServiceDone;
import static com.avatlantik.asmp.repository.ModelConverter.buildServiceDoneVetExercise;
import static com.avatlantik.asmp.repository.ModelConverter.buildVetExercise;
import static com.avatlantik.asmp.repository.ModelConverter.buildVetPreparat;
import static com.avatlantik.asmp.repository.ModelConverter.convertAnimal;
import static com.avatlantik.asmp.repository.ModelConverter.convertAnimalDispos;
import static com.avatlantik.asmp.repository.ModelConverter.convertAnimalHistory;
import static com.avatlantik.asmp.repository.ModelConverter.convertAnimalStatus;
import static com.avatlantik.asmp.repository.ModelConverter.convertAnimalType;
import static com.avatlantik.asmp.repository.ModelConverter.convertBreed;
import static com.avatlantik.asmp.repository.ModelConverter.convertFarrowingCycle;
import static com.avatlantik.asmp.repository.ModelConverter.convertHertType;
import static com.avatlantik.asmp.repository.ModelConverter.convertHousing;
import static com.avatlantik.asmp.repository.ModelConverter.convertService;
import static com.avatlantik.asmp.repository.ModelConverter.convertServiceDone;
import static com.avatlantik.asmp.repository.ModelConverter.convertServiceDoneVetExercise;
import static com.avatlantik.asmp.repository.ModelConverter.convertVetExercise;
import static com.avatlantik.asmp.repository.ModelConverter.convertVetPreparat;
import static com.avatlantik.asmp.repository.ModelConverter.getIntValueByColumName;
import static com.avatlantik.asmp.repository.ModelConverter.getStringValueByColumName;

public class DataRepositoryImpl implements DataRepository {

    private ContentResolver contentResolver;

    public DataRepositoryImpl(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    @Override
    public String getUserSetting(String settingId) {
        try (Cursor cursor = contentResolver.query(UserSettingsContract.CONTENT_URI,
                UserSettingsContract.PROJECTION_ALL, UserSettingsContract.USER_SETTING_ID + " = ?",
                new String[]{settingId}, UserSettingsContract.DEFAULT_SORT_ORDER)) {

            if (cursor == null) {
                return null;
            }
            if (cursor.moveToFirst()) {
                return cursor.getString(cursor.getColumnIndex(UserSettingsContract.SETTING_VALUE));
            } else {
                return null;
            }
        }
    }

    @Override
    public List<ServiceData> getServiceList() {
        try (Cursor cursor = contentResolver.query(ServiceContract.CONTENT_URI,
                ServiceContract.PROJECTION_ALL, null, null, ServiceContract.DEFAULT_SORT_ORDER)) {

            if (cursor == null) return null;
            List<ServiceData> result = new ArrayList<>();
            while (cursor.moveToNext())
                result.add(buildService(cursor));
            return result;
        }
    }

    @Override
    public List<ServiceData> getServiceUsetToday() {
        try (Cursor cursor = contentResolver.query(
                ServiceDoneContract.CONTENT_URI,
                new String[]{"Distinct " + ServiceDoneContract.SERVICE_ID,
                        "sum(" + ServiceDoneContract.ISPLANE + ") as " + ServiceDoneContract.ISPLANE,
                        "sum(" + ServiceDoneContract.DONE + ") as " + ServiceDoneContract.DONE},
                "("+ ServiceDoneContract.DATE + ">=" + (getStartDay(LocalDateTime.now().toDate()).getTime()-1000) + " AND "
                        + ServiceDoneContract.DATE + "<=" + getEndDay(LocalDateTime.now().toDate()).getTime()+ " AND "
                        + ServiceDoneContract.SERVICE_ID + " IS NOT NULL) GROUP BY ("+ServiceDoneContract.SERVICE_ID + ")", null,
                ServiceDoneContract.DEFAULT_SORT_ORDER)) {

            if (cursor == null) return null;
            List<ServiceData> result = new ArrayList<>();
            ServiceData serviceData = null;
            while (cursor.moveToNext()) {
                serviceData = getServiceById(getStringValueByColumName(cursor, ServiceDoneContract.SERVICE_ID));
                if (serviceData == null) {
                    continue;
                }
                serviceData.setNumberPlane(getIntValueByColumName(cursor, ServiceDoneContract.ISPLANE));
                serviceData.setNumberDone(getIntValueByColumName(cursor, ServiceDoneContract.DONE));
                result.add(serviceData);
            }
            return result;
        }
    }

    @Override
    public List<ServiceData> getServiceNotUsetToday() {
        try (Cursor cursor = contentResolver.query(
                ServiceDoneContract.CONTENT_URI,
                new String[]{"Distinct " + ServiceDoneContract.SERVICE_ID},
                ServiceDoneContract.DATE + ">=" + (getStartDay(LocalDateTime.now().toDate()).getTime()-1000)  + " AND "
                        + ServiceDoneContract.DATE + "<=" + getEndDay(LocalDateTime.now().toDate()).getTime(), null,
                ServiceDoneContract.DEFAULT_SORT_ORDER)) {

            if (cursor == null) return null;
            List<String> serviceList = new ArrayList<>();
            while (cursor.moveToNext()) {
                serviceList.add(getStringValueByColumName(cursor, ServiceDoneContract.SERVICE_ID));
            }
            return getServiceNotInList(serviceList);
        }
    }

    @Override
    public List<ServiceData> getServiceNotUsetTodayByAnimalId(String externalId) {
        try (Cursor cursor = contentResolver.query(
                ServiceDoneContract.CONTENT_URI,
                new String[]{"Distinct " + ServiceDoneContract.SERVICE_ID},
                ServiceDoneContract.ANIMAL_ID + "='" + externalId + "' AND "
                        + ServiceDoneContract.DATE + ">=" + (getStartDay(LocalDateTime.now().toDate()).getTime()-1000)  + " AND "
                        + ServiceDoneContract.DATE + "<=" + getEndDay(LocalDateTime.now().toDate()).getTime(), null,
                ServiceDoneContract.DEFAULT_SORT_ORDER)) {

            if (cursor == null) return null;
            List<String> serviceList = new ArrayList<>();
            while (cursor.moveToNext()) {
                serviceList.add(getStringValueByColumName(cursor, ServiceDoneContract.SERVICE_ID));
            }
            return getServiceNotInList(serviceList);
        }
    }


    @Override
    public ServiceData getServiceById(String externalId) {
        try (Cursor cursor = contentResolver.query(
                ServiceContract.CONTENT_URI,
                ServiceContract.PROJECTION_ALL, ServiceContract.EXTERNAL_ID + "='" + externalId + "'",
                null, ServiceContract.DEFAULT_SORT_ORDER)) {

            if (cursor == null) {
                return null;
            }
            if (cursor.moveToFirst()) {
                return buildService(cursor);
            } else {
                return null;
            }
        }
    }

    @Override
    public List<ServiceData> getServiceNotInList(List<String> serviceList) {

        String[] selectionArgs  = (String[]) serviceList.toArray(new String[serviceList.size()]);

        try (Cursor cursor = contentResolver.query(
                ServiceContract.CONTENT_URI,
                ServiceContract.PROJECTION_ALL,
                selectionArgs .length > 0 ? ServiceContract.EXTERNAL_ID + " NOT IN ("+ makePlaceholders(selectionArgs .length) +")" : null,
                selectionArgs .length > 0 ? selectionArgs  : null, ServiceContract.DEFAULT_SORT_ORDER)) {

            if (cursor == null) return null;
            List<ServiceData> result = new ArrayList<>();
            while (cursor.moveToNext())
                result.add(buildService(cursor));
            return result;
        }
    }

    @Override
    public List<Animal> getAnimalList() {
        try (Cursor cursor = contentResolver.query(AnimalContract.CONTENT_URI,
                AnimalContract.PROJECTION_ALL, null, null, AnimalContract.DEFAULT_SORT_ORDER)) {

            if (cursor == null) return null;
            List<Animal> result = new ArrayList<>();
            while (cursor.moveToNext())
                result.add(buildAnimal(cursor));
            return result;
        }
    }

    @Override
    public Animal getAnimalById(String externalId) {
        try (Cursor cursor = contentResolver.query(
                AnimalContract.CONTENT_URI,
                AnimalContract.PROJECTION_ALL, AnimalContract.EXTERNAL_ID + "='" + externalId + "'",
                null, AnimalContract.DEFAULT_SORT_ORDER)) {

            if (cursor == null) {
                return null;
            }
            if (cursor.moveToFirst()) {
                return buildAnimal(cursor);
            } else {
                return null;
            }
        }
    }

    @Override
    public Animal getAnimalByRFID(String RFID) {
        try (Cursor cursor = contentResolver.query(
                AnimalContract.CONTENT_URI,
                AnimalContract.PROJECTION_ALL, AnimalContract.RFID + "='" + RFID + "'",
                null, AnimalContract.DEFAULT_SORT_ORDER)) {

            if (cursor == null) {
                return null;
            }
            if (cursor.moveToFirst()) {
                return buildAnimal(cursor);
            } else {
                return null;
            }
        }
    }

    @Override
    public List<Animal> getAnimalsByTypeAnimal(int typeAnimal) {
        try (Cursor cursor = contentResolver.query(AnimalContract.CONTENT_URI,
                AnimalContract.PROJECTION_ALL,
                typeAnimal ==0 || typeAnimal >= TYPE_GROUP_ANIMAL_ALL ? "": AnimalContract.TYPE_ANIMAL + "=" + typeAnimal + ""
                , null, AnimalContract.DEFAULT_SORT_ORDER)) {

            if (cursor == null) return null;
            List<Animal> result = new ArrayList<>();
            while (cursor.moveToNext())
                result.add(buildAnimal(cursor));
            return result;
        }
    }

    @Override
    public List<Animal> getAnimalsByTypeAnimalOnlyElement(int typeAnimal) {
        try (Cursor cursor = contentResolver.query(AnimalContract.CONTENT_URI,
                AnimalContract.PROJECTION_ALL,
                AnimalContract.IS_GROUP + " = 0"
                +(typeAnimal ==0 || typeAnimal >= TYPE_GROUP_ANIMAL_ALL ? "": " AND " + AnimalContract.TYPE_ANIMAL + "=" + typeAnimal + ""),
                null, AnimalContract.DEFAULT_SORT_ORDER)) {

            if (cursor == null) return null;
            List<Animal> result = new ArrayList<>();
            while (cursor.moveToNext())
                result.add(buildAnimal(cursor));
            return result;
        }
    }

    @Override
    public List<Animal> getAnimalsByTypeAnimalAndParenId(int typeAnimal, String externalId) {
        try (Cursor cursor = contentResolver.query(AnimalContract.CONTENT_URI,
                AnimalContract.PROJECTION_ALL,
                (typeAnimal ==0 || typeAnimal >= TYPE_GROUP_ANIMAL_ALL ? "":  AnimalContract.TYPE_ANIMAL + "=" + typeAnimal + " AND ( ")
                        + AnimalContract.GROUP_ID + "='" + (externalId == null  ? "" : externalId) + "'"
                        + (externalId == CLEAR_GUID  ? " OR " + AnimalContract.GROUP_ID + "='' OR " + AnimalContract.GROUP_ID + " is null"  : "")
                        + (typeAnimal ==0 || typeAnimal >= TYPE_GROUP_ANIMAL_ALL ? "":  " ) "),
                 null, AnimalContract.DEFAULT_SORT_ORDER)) {

            if (cursor == null) return null;
            List<Animal> result = new ArrayList<>();
            while (cursor.moveToNext())
                result.add(buildAnimal(cursor));

            if(externalId == CLEAR_GUID &&(typeAnimal == TYPE_GROUP_ANIMAL_SOW || typeAnimal == TYPE_GROUP_ANIMAL_BOAR)) {
                result.addAll(getGroupeAnimalsByTypeAnimal(0));
            }

            return result;
        }
    }

    @Override
    public List<Animal> getGroupeAnimalsByTypeAnimal(int typeAnimal) {
        try (Cursor cursor = contentResolver.query(AnimalContract.CONTENT_URI,
                AnimalContract.PROJECTION_ALL,
                AnimalContract.IS_GROUP + " = 1 "
                        + (typeAnimal >= TYPE_GROUP_ANIMAL_ALL ? "": " AND " +AnimalContract.TYPE_ANIMAL + " = " + typeAnimal + "")
                , null, AnimalContract.DEFAULT_SORT_ORDER)) {

            if (cursor == null) return null;
            List<Animal> result = new ArrayList<>();
            while (cursor.moveToNext())
                result.add(buildAnimal(cursor));
            return result;
        }
    }

    @Override
    public List<Animal> getAllGroupsAnimalsByTypeAnimal(int typeAnimal) {
        try (Cursor cursor = contentResolver.query(AnimalContract.CONTENT_URI,
                AnimalContract.PROJECTION_ALL,
                AnimalContract.IS_GROUP + " = 1 "
                        + (typeAnimal < TYPE_GROUP_ANIMAL_SOW || typeAnimal > TYPE_GROUP_ANIMAL_BOAR ? "":
                        " AND (" +AnimalContract.TYPE_ANIMAL + " = 0  OR " +AnimalContract.TYPE_ANIMAL + " = " + typeAnimal + ")"),
                null, AnimalContract.DEFAULT_SORT_ORDER)) {

            if (cursor == null) return null;
            List<Animal> result = new ArrayList<>();
            while (cursor.moveToNext())
                result.add(buildAnimal(cursor));
            return result;
        }
    }

    @Override
    public List<Animal> getTecnicalGroupsAnimalsByTypeAnimal(int typeAnimal) {
        try (Cursor cursor = contentResolver.query(AnimalContract.CONTENT_URI,
                AnimalContract.PROJECTION_ALL,
                AnimalContract.IS_GROUP_ANIMAL + " = 1 "
                        + (typeAnimal < TYPE_GROUP_ANIMAL_SOW || typeAnimal > TYPE_GROUP_ANIMAL_BOAR ? "":
                        " AND (" +AnimalContract.TYPE_ANIMAL + " = " + typeAnimal + ")"),
                null, AnimalContract.DEFAULT_SORT_ORDER)) {

            if (cursor == null) return null;
            List<Animal> result = new ArrayList<>();
            while (cursor.moveToNext())
                result.add(buildAnimal(cursor));
            return result;
        }
    }

    @Override
    public List<Animal> getAnimalsByListId(List<String> listID) {

        String[] selectionArgs  = (String[]) listID.toArray(new String[listID.size()]);

        if(selectionArgs .length == 0) return new ArrayList<>();

        try (Cursor cursor = contentResolver.query(
                AnimalContract.CONTENT_URI,
                AnimalContract.PROJECTION_ALL,
                AnimalContract.EXTERNAL_ID + " IN ("+ makePlaceholders(selectionArgs .length) +")",
                selectionArgs, AnimalContract.DEFAULT_SORT_ORDER)) {

            if (cursor == null) return null;
            List<Animal> result = new ArrayList<>();
            while (cursor.moveToNext())
                result.add(buildAnimal(cursor));
            return result;
        }
    }

    @Override
    public List<Animal> getAnimalsByGroupeAnimal(String groupId) {
        try (Cursor cursor = contentResolver.query(AnimalContract.CONTENT_URI,
                AnimalContract.PROJECTION_ALL,
                AnimalContract.GROUP_ID + "='" + (groupId == null || groupId.isEmpty() ? "" :   groupId) + "'"
                , null, AnimalContract.DEFAULT_SORT_ORDER)) {

            if (cursor == null) return null;
            List<Animal> result = new ArrayList<>();
            while (cursor.moveToNext())
                result.add(buildAnimal(cursor));
            return result;
        }
    }

    @Override
    public List<ServiceDone> getServiceDoneList() {
        try (Cursor cursor = contentResolver.query(
                ServiceDoneContract.CONTENT_URI,
                ServiceDoneContract.PROJECTION_ALL, null,
                null, ServiceDoneContract.DEFAULT_SORT_ORDER)) {

            if (cursor == null) return null;
            List<ServiceDone> result = new ArrayList<>();
            while (cursor.moveToNext())
                result.add(buildServiceDone(cursor));
            return result;
        }
    }

    @Override
    public List<ServiceDone> getServiceDoneByAnimalId(String externalId) {
        try (Cursor cursor = contentResolver.query(
                ServiceDoneContract.CONTENT_URI,
                ServiceDoneContract.PROJECTION_ALL, ServiceDoneContract.ANIMAL_ID + "='" + externalId + "' AND "
                        + ServiceDoneContract.DATE + ">=" + getStartDay(LocalDateTime.now().toDate()).getTime() + " AND "
                        + ServiceDoneContract.DATE + "<=" + getEndDay(LocalDateTime.now().toDate()).getTime(),
                null, ServiceDoneContract.DEFAULT_SORT_ORDER)) {

            if (cursor == null) return null;
            List<ServiceDone> result = new ArrayList<>();
            while (cursor.moveToNext())
                result.add(buildServiceDone(cursor));
            return result;
        }
    }

    @Override
    public List<ServiceDone> getServiceDoneByServiceId(String externalId) {
        try (Cursor cursor = contentResolver.query(
                ServiceDoneContract.CONTENT_URI,
                ServiceDoneContract.PROJECTION_ALL, ServiceDoneContract.SERVICE_ID + "='" + externalId + "' AND "
                        + ServiceDoneContract.DATE + ">=" + getStartDay(LocalDateTime.now().toDate()).getTime() + " AND "
                        + ServiceDoneContract.DATE + "<=" + getEndDay(LocalDateTime.now().toDate()).getTime(),
                null, ServiceDoneContract.DEFAULT_SORT_ORDER)) {

            if (cursor == null) return null;
            List<ServiceDone> result = new ArrayList<>();
            while (cursor.moveToNext())
                result.add(buildServiceDone(cursor));
            return result;
        }
    }

    @Override
    public List<ServiceDone> getServiceDoneByListTableId(List<String> listID) {

        String[] selectionArgs  = (String[]) listID.toArray(new String[listID.size()]);

        if(selectionArgs .length == 0) return new ArrayList<>();

        try (Cursor cursor = contentResolver.query(
                ServiceDoneContract.CONTENT_URI,
                ServiceDoneContract.PROJECTION_ALL,
                ServiceDoneContract._ID + " IN ("+ makePlaceholders(selectionArgs .length) +")",
                selectionArgs, ServiceDoneContract.DEFAULT_SORT_ORDER)) {

            if (cursor == null) return null;
            List<ServiceDone> result = new ArrayList<>();
            while (cursor.moveToNext())
                result.add(buildServiceDone(cursor));
            return result;
        }
    }

    @Override
    public ServiceDone getServiceDoneByServiceIdAnimalId(String serviceId, String animalId) {
        try (Cursor cursor = contentResolver.query(
                ServiceDoneContract.CONTENT_URI,
                ServiceDoneContract.PROJECTION_ALL,
                ServiceDoneContract.ANIMAL_ID + "='" + animalId + "' AND "
                        + ServiceDoneContract.SERVICE_ID + "='" + serviceId + "' AND "
                        + ServiceDoneContract.DATE + ">=" + getStartDay(LocalDateTime.now().toDate()).getTime() + " AND "
                        + ServiceDoneContract.DATE + "<=" + getEndDay(LocalDateTime.now().toDate()).getTime(),
                null, ServiceDoneContract.DEFAULT_SORT_ORDER)) {

            if (cursor == null) {
                return null;
            }
            if (cursor.moveToFirst()) {
                return buildServiceDone(cursor);
            } else {
                return null;
            }
        }
    }

    @Override
    public List<AnimalHistory> getAnimalHistoryByAnimalId(String externalId) {
        try (Cursor cursor = contentResolver.query(
                AnimalHistoryContract.CONTENT_URI,
                AnimalHistoryContract.PROJECTION_ALL, AnimalHistoryContract.ANIMAL_ID + "='" + externalId + "'",
                null, AnimalHistoryContract.DEFAULT_SORT_ORDER)) {

            if (cursor == null) return null;
            List<AnimalHistory> result = new ArrayList<>();
            while (cursor.moveToNext())
                result.add(buildAnimalHistory(cursor));
            return result;
        }
    }

    @Override
    public List<AnimalType> getAnimalTypeList() {
        try (Cursor cursor = contentResolver.query(
                AnimalTypeContract.CONTENT_URI,
                AnimalTypeContract.PROJECTION_ALL, null,
                null, AnimalTypeContract.DEFAULT_SORT_ORDER)) {

            if (cursor == null) return null;
            List<AnimalType> result = new ArrayList<>();
            while (cursor.moveToNext())
                result.add(buildAnimalType(cursor));
            return result;
        }
    }

    @Override
    public List<Housing> getHousingListByType(int type) {
        try (Cursor cursor = contentResolver.query(
                HousingContract.CONTENT_URI,
                HousingContract.PROJECTION_ALL, HousingContract.TYPE + "=" + type,
                null, HousingContract.DEFAULT_SORT_ORDER)) {

            if (cursor == null) return null;
            List<Housing> result = new ArrayList<>();
            while (cursor.moveToNext())
                result.add(buildHousing(cursor));
            return result;
        }
    }

    @Override
    public Housing getHousingById(String externalId) {
        try (Cursor cursor = contentResolver.query(
                HousingContract.CONTENT_URI,
                HousingContract.PROJECTION_ALL,
                HousingContract.EXTERNAL_ID + "='" + externalId + "'",
                null, HousingContract.DEFAULT_SORT_ORDER)) {

            if (cursor == null) {
                return null;
            }
            if (cursor.moveToFirst()) {
                return buildHousing(cursor);
            } else {
                return null;
            }
        }
    }

    @Override
    public List<Housing> getHousingListByTypeAndParenID(int type, String parentId) {
        try (Cursor cursor = contentResolver.query(
                HousingContract.CONTENT_URI,
                HousingContract.PROJECTION_ALL,
                HousingContract.TYPE + "=" + type
                        + (parentId == null || parentId.isEmpty() ? "" : " AND " +HousingContract.PARENT_ID + "='" + parentId + "'"),
                null, HousingContract.DEFAULT_SORT_ORDER)) {

            if (cursor == null) return null;
            List<Housing> result = new ArrayList<>();
            while (cursor.moveToNext())
                result.add(buildHousing(cursor));
            return result;
        }
    }

    @Override
    public List<Housing> getHousingListByParenID(String parentId) {
        try (Cursor cursor = contentResolver.query(
                HousingContract.CONTENT_URI,
                HousingContract.PROJECTION_ALL,
                HousingContract.PARENT_ID + "='" + parentId + "'",
                null, HousingContract.DEFAULT_SORT_ORDER)) {

            if (cursor == null) return null;
            List<Housing> result = new ArrayList<>();
            while (cursor.moveToNext())
                result.add(buildHousing(cursor));
            return result;
        }
    }

    @Override
    public List<FarrowingCycle> getFarrowingCyclesByAnimalId(String externalId) {
        try (Cursor cursor = contentResolver.query(
                FarrowingCycleContract.CONTENT_URI,
                FarrowingCycleContract.PROJECTION_ALL, FarrowingCycleContract.ANIMAL_ID + "='" + externalId + "'",
                null, FarrowingCycleContract.DEFAULT_SORT_ORDER)) {

            if (cursor == null) return null;
            List<FarrowingCycle> result = new ArrayList<>();
            while (cursor.moveToNext())
                result.add(buildFarrowingCycle(cursor));
            return result;
        }
    }

    @Override
    public List<String> getUsersServer() {
        try (Cursor cursor = contentResolver.query(
                UsersServerContract.CONTENT_URI,
                UsersServerContract.PROJECTION_ALL,
                null, null, UsersServerContract.DEFAULT_SORT_ORDER)) {

            if (cursor == null) return null;
            List<String> result = new ArrayList<>();
            while (cursor.moveToNext())
                result.add(cursor.getString(cursor.getColumnIndex(UsersServerContract.NAME)));
            return result;
        }
    }

    @Override
    public List<String> getChangedElements(String nameElement) {
        try (Cursor cursor = contentResolver.query(
                ChangingContrack.CONTENT_URI,
                ChangingContrack.PROJECTION_ALL,
                ChangingContrack.MANE_ELEMENT + "='" + nameElement + "'",
                new String[]{},
                ChangingContrack.DEFAULT_SORT_ORDER)) {

            if (cursor == null) return null;
            List<String> result = new ArrayList<>();
            while (cursor.moveToNext())
                result.add(cursor.getString(cursor.getColumnIndex(ChangingContrack.ELEMENT_ID)));
            return result;
        }
    }

    @Override
    public Animal getGroupByServiceIdAndTypeAnimal(String externalId, int typeAnimal) {
        try (Cursor cursor = contentResolver.query(
                ConformityServiceToGroupContrack.CONTENT_URI,
                new String[]{"Distinct " + ConformityServiceToGroupContrack.ANIMAL_ID},
                ConformityServiceToGroupContrack.SERVICE_ID + "='" + externalId + "' AND "
                + ConformityServiceToGroupContrack.TYPE_ANIMAL + "=" + typeAnimal + "",
                new String[]{},
                ConformityServiceToGroupContrack.DEFAULT_SORT_ORDER)) {

            if (cursor.moveToFirst()) {
                return getAnimalById(cursor.getString(cursor.getColumnIndex(ConformityServiceToGroupContrack.ANIMAL_ID)));
            } else {
                return null;
            }
        }
    }

    @Override
    public Breed getBreedById(String externalId) {
        try (Cursor cursor = contentResolver.query(
                BreedContract.CONTENT_URI,
                BreedContract.PROJECTION_ALL,
                BreedContract.EXTERNAL_ID + "='" + externalId + "'",
                null, BreedContract.DEFAULT_SORT_ORDER)) {

            if (cursor == null) {
                return null;
            }
            if (cursor.moveToFirst()) {
                return buildBreed(cursor);
            } else {
                return null;
            }
        }
    }

    @Override
    public List<Breed> getBreedList() {
        try (Cursor cursor = contentResolver.query(
                BreedContract.CONTENT_URI,
                BreedContract.PROJECTION_ALL, null, null,
                BreedContract.DEFAULT_SORT_ORDER)) {

            if (cursor == null) return null;
            List<Breed> result = new ArrayList<>();
            while (cursor.moveToNext())
                result.add(buildBreed(cursor));
            return result;
        }
    }

    @Override
    public AnimalStatus getAnimalStatusById(String externalId) {
        try (Cursor cursor = contentResolver.query(
                AnimalStatusContract.CONTENT_URI,
                AnimalStatusContract.PROJECTION_ALL,
                AnimalStatusContract.EXTERNAL_ID + "='" + externalId + "'",
                null, AnimalStatusContract.DEFAULT_SORT_ORDER)) {

            if (cursor == null) {
                return null;
            }
            if (cursor.moveToFirst()) {
                return buildAnimalStatus(cursor);
            } else {
                return null;
            }
        }
    }

    @Override
    public List<AnimalStatus> getAnimalStatusList() {
        try (Cursor cursor = contentResolver.query(
                AnimalStatusContract.CONTENT_URI,
                AnimalStatusContract.PROJECTION_ALL, null, null,
                AnimalStatusContract.DEFAULT_SORT_ORDER)) {

            if (cursor == null) return null;
            List<AnimalStatus> result = new ArrayList<>();
            while (cursor.moveToNext())
                result.add(buildAnimalStatus(cursor));
            return result;
        }
    }

    @Override
    public AnimalDispos getAnimalDisposById(String externalId) {
        try (Cursor cursor = contentResolver.query(
                AnimalDisposContract.CONTENT_URI,
                AnimalDisposContract.PROJECTION_ALL,
                AnimalDisposContract.EXTERNAL_ID + "='" + externalId + "'",
                null, AnimalDisposContract.DEFAULT_SORT_ORDER)) {

            if (cursor == null) {
                return null;
            }
            if (cursor.moveToFirst()) {
                return buildAnimalDispos(cursor);
            } else {
                return null;
            }
        }
    }

    @Override
    public List<AnimalDispos> getAnimalDisposList() {
        try (Cursor cursor = contentResolver.query(
                AnimalDisposContract.CONTENT_URI,
                AnimalDisposContract.PROJECTION_ALL, null, null,
                AnimalDisposContract.DEFAULT_SORT_ORDER)) {

            if (cursor == null) return null;
            List<AnimalDispos> result = new ArrayList<>();
            while (cursor.moveToNext())
                result.add(buildAnimalDispos(cursor));
            return result;
        }
    }

    @Override
    public HertType getHertTypeById(String externalId) {
        try (Cursor cursor = contentResolver.query(
                HertTypeContract.CONTENT_URI,
                HertTypeContract.PROJECTION_ALL,
                HertTypeContract.EXTERNAL_ID + "='" + externalId + "'",
                null, HertTypeContract.DEFAULT_SORT_ORDER)) {

            if (cursor == null) {
                return null;
            }
            if (cursor.moveToFirst()) {
                return buildHertType(cursor);
            } else {
                return null;
            }
        }
    }

    @Override
    public List<HertType> getHertTypeList() {
        try (Cursor cursor = contentResolver.query(
                HertTypeContract.CONTENT_URI,
                HertTypeContract.PROJECTION_ALL, null, null,
                HertTypeContract.DEFAULT_SORT_ORDER)) {

            if (cursor == null) return null;
            List<HertType> result = new ArrayList<>();
            while (cursor.moveToNext())
                result.add(buildHertType(cursor));
            return result;
        }
    }

    @Override
    public List<Integer> getAnimalTypeListByServiceID(String serviceID) {
        try (Cursor cursor = contentResolver.query(
                ServiceToAnimalTypeContract.CONTENT_URI,
                new String[]{"Distinct " + ServiceToAnimalTypeContract.TYPE_ANIMAL},
                ServiceToAnimalTypeContract.SERVICE_ID + "='" + serviceID + "'",
                new String[]{},
                ServiceToAnimalTypeContract.DEFAULT_SORT_ORDER)) {

            if (cursor == null) return null;
            List<Integer> result = new ArrayList<>();
            while (cursor.moveToNext())
                result.add(cursor.getInt(cursor.getColumnIndex(ServiceToAnimalTypeContract.TYPE_ANIMAL)));
            return result;
        }
    }

    @Override
    public List<AnimalType> getAnimalTypeListByListTypes(List<Integer> typeList) {

        List<String> newList = new ArrayList<String>(typeList.size());
        for (Integer myInt : typeList) {
            newList.add(String.valueOf(myInt));
        }

        String[] selectionArgs  = (String[]) newList.toArray(new String[newList.size()]);

        if(selectionArgs .length == 0) return new ArrayList<>();

        try (Cursor cursor = contentResolver.query(
                AnimalTypeContract.CONTENT_URI,
                AnimalTypeContract.PROJECTION_ALL,
                AnimalTypeContract.TYPE_ANIMAL + " IN ("+ makePlaceholders(selectionArgs .length) +")",
                selectionArgs, AnimalTypeContract.DEFAULT_SORT_ORDER)) {

            if (cursor == null) return null;
            List<AnimalType> result = new ArrayList<>();
            while (cursor.moveToNext())
                result.add(buildAnimalType(cursor));
            return result;
        }
    }

    @Override
    public VetExercise getVetExerciseById(String externalId) {
        try (Cursor cursor = contentResolver.query(
                VeterinaryExerciseContract.CONTENT_URI,
                VeterinaryExerciseContract.PROJECTION_ALL,
                VeterinaryExerciseContract.EXTERNAL_ID + "='" + externalId + "'",
                null, VeterinaryExerciseContract.DEFAULT_SORT_ORDER)) {

            if (cursor == null) {
                return null;
            }
            if (cursor.moveToFirst()) {
                return buildVetExercise(cursor);
            } else {
                return null;
            }
        }
    }

    @Override
    public List<VetExercise> getVetExerciseList() {
        try (Cursor cursor = contentResolver.query(
                VeterinaryExerciseContract.CONTENT_URI,
                VeterinaryExerciseContract.PROJECTION_ALL, null, null,
                VeterinaryExerciseContract.DEFAULT_SORT_ORDER)) {

            if (cursor == null) return null;
            List<VetExercise> result = new ArrayList<>();
            while (cursor.moveToNext())
                result.add(buildVetExercise(cursor));
            return result;
        }
    }

    @Override
    public VetPreparat getVetPreparatById(String externalId) {
        try (Cursor cursor = contentResolver.query(
                VeterinaryPrepatratContract.CONTENT_URI,
                VeterinaryPrepatratContract.PROJECTION_ALL,
                VeterinaryPrepatratContract.EXTERNAL_ID + "='" + externalId + "'",
                null, VeterinaryPrepatratContract.DEFAULT_SORT_ORDER)) {

            if (cursor == null) {
                return null;
            }
            if (cursor.moveToFirst()) {
                return buildVetPreparat(cursor);
            } else {
                return null;
            }
        }
    }

    @Override
    public List<VetPreparat> getVetPreparatList() {
        try (Cursor cursor = contentResolver.query(
                VeterinaryPrepatratContract.CONTENT_URI,
                VeterinaryPrepatratContract.PROJECTION_ALL, null, null,
                VeterinaryPrepatratContract.DEFAULT_SORT_ORDER)) {

            if (cursor == null) return null;
            List<VetPreparat> result = new ArrayList<>();
            while (cursor.moveToNext())
                result.add(buildVetPreparat(cursor));
            return result;
        }
    }

    @Override
    public List<ServiceDoneVetExercise> getServiceDoneVetExerciseByAnimalId(String animalId) {
        try (Cursor cursor = contentResolver.query(
                ServiceDoneVetExerciseContract.CONTENT_URI,
                ServiceDoneVetExerciseContract.PROJECTION_ALL,
                ServiceDoneVetExerciseContract.ANIMAL_ID + "='" + animalId + "'",
                null, ServiceDoneVetExerciseContract.DEFAULT_SORT_ORDER)) {

            if (cursor == null) return null;
            List<ServiceDoneVetExercise> result = new ArrayList<>();
            while (cursor.moveToNext())
                result.add(buildServiceDoneVetExercise(cursor));
            return result;
        }
    }

    @Override
    public byte[] getImageByID(String externalID) {
        try (Cursor cursor = contentResolver.query(
                ImageContract.CONTENT_URI,
                ImageContract.PROJECTION_ALL,
                ImageContract.EXTERNAL_ID + "='" + externalID + "'",
                new String[]{},
                ImageContract.DEFAULT_SORT_ORDER)) {

            if (cursor == null) {
                return null;
            }
            if (cursor.moveToFirst()) {
                return cursor.getBlob(cursor.getColumnIndex(ImageContract.IMAGE));
            } else {
                return null;
            }
        }
    }

    @Override
    public void insertUserSetting(ParameterInfo usersetting) {
        ContentValues values = new ContentValues();
        values.put(UserSettingsContract.USER_SETTING_ID, usersetting.getName());
        values.put(UserSettingsContract.SETTING_VALUE, usersetting.getValue());
        contentResolver.insert(UserSettingsContract.CONTENT_URI, values);
    }

    @Override
    public void clearDataBase() {
        contentResolver.delete(UserSettingsContract.CONTENT_URI, null, null);
        contentResolver.delete(ServiceContract.CONTENT_URI, null, null);
        contentResolver.delete(AnimalContract.CONTENT_URI, null, null);
        contentResolver.delete(ServiceDoneContract.CONTENT_URI, null, null);
        contentResolver.delete(AnimalTypeContract.CONTENT_URI, null, null);
        contentResolver.delete(HousingContract.CONTENT_URI, null, null);
        contentResolver.delete(AnimalHistoryContract.CONTENT_URI, null, null);
        contentResolver.delete(FarrowingCycleContract.CONTENT_URI, null, null);
        contentResolver.delete(ChangingContrack.CONTENT_URI, null, null);
        contentResolver.delete(ConformityServiceToGroupContrack.CONTENT_URI, null, null);
        contentResolver.delete(BreedContract.CONTENT_URI, null, null);
        contentResolver.delete(AnimalStatusContract.CONTENT_URI, null, null);
        contentResolver.delete(AnimalDisposContract.CONTENT_URI, null, null);
        contentResolver.delete(HertTypeContract.CONTENT_URI, null, null);
        contentResolver.delete(ServiceToAnimalTypeContract.CONTENT_URI, null, null);
        contentResolver.delete(VeterinaryExerciseContract.CONTENT_URI, null, null);
        contentResolver.delete(VeterinaryPrepatratContract.CONTENT_URI, null, null);
        contentResolver.delete(ServiceDoneVetExerciseContract.CONTENT_URI, null, null);
        contentResolver.delete(ImageContract.CONTENT_URI, null, null);
    }

    @Override
    public void insertServiceData(ServiceData serviceData) {
        ContentValues values = convertService(serviceData);
        contentResolver.insert(ServiceContract.CONTENT_URI, values);
    }

    @Override
    public void insertServiceDataList(List<ServiceData> serviceDataList) {
        for (ServiceData serviceData : serviceDataList) {
            ContentValues values = convertService(serviceData);
            contentResolver.insert(ServiceContract.CONTENT_URI, values);
        }
    }

    @Override
    public void insertAnimal(Animal animal) {
        ContentValues values = convertAnimal(animal);
        contentResolver.insert(AnimalContract.CONTENT_URI, values);
    }

    @Override
    public void insertAnimalsList(List<Animal> animalList) {
        for (Animal animal : animalList) {
            ContentValues values = convertAnimal(animal);
            contentResolver.insert(AnimalContract.CONTENT_URI, values);
        }
    }

    @Override
    public void updateAnimalExternalId(String appExternalId, String newExternalId) {

        ContentValues newMemberContentValues = new ContentValues();
        newMemberContentValues.put(AnimalContract.EXTERNAL_ID, newExternalId);

        contentResolver.update(
                AnimalContract.CONTENT_URI,
                newMemberContentValues, AnimalContract.EXTERNAL_ID + "='" + appExternalId + "'", null);


        ContentValues newServiceDoneContentValues = new ContentValues();
        newServiceDoneContentValues.put(ServiceDoneContract.ANIMAL_ID, newExternalId);

        contentResolver.update(
                ServiceDoneContract.CONTENT_URI,
                newServiceDoneContentValues,
                ServiceDoneContract.ANIMAL_ID + "='" + appExternalId + "'",
                null);

        ContentValues newChangedContentValues = new ContentValues();
        newChangedContentValues.put(ChangingContrack.ELEMENT_ID, newExternalId);

        contentResolver.update(
                ChangingContrack.CONTENT_URI,
                newChangedContentValues,
                ChangingContrack.ELEMENT_ID + "='" + appExternalId + "'",
                null);

    }

    @Override
    public void insertServiceDone(ServiceDone serviceDone) {
        serviceDone.setDate(getStartDay(serviceDone.getDate()));
        ContentValues values = convertServiceDone(serviceDone);
        contentResolver.insert(ServiceDoneContract.CONTENT_URI, values);
    }

    @Override
    public void insertServiceDoneList(List<ServiceDone> serviceDoneList) {
        for (ServiceDone serviceDone : serviceDoneList) {
            ContentValues values = convertServiceDone(serviceDone);
            contentResolver.insert(ServiceDoneContract.CONTENT_URI, values);
        }
    }

    @Override
    public void insertAnimalHistory(AnimalHistory animalHistory) {
        ContentValues values = convertAnimalHistory(animalHistory);
        contentResolver.insert(AnimalHistoryContract.CONTENT_URI, values);
    }

    @Override
    public void insertAnimalHistoryList(List<AnimalHistory> animalHistoryList) {
        for (AnimalHistory animalHistory : animalHistoryList) {
            ContentValues values = convertAnimalHistory(animalHistory);
            contentResolver.insert(AnimalHistoryContract.CONTENT_URI, values);
        }
    }

    @Override
    public void insertAnimalType(AnimalType animalType) {
        ContentValues values = convertAnimalType(animalType);
        contentResolver.insert(AnimalTypeContract.CONTENT_URI, values);
    }

    @Override
    public void insertHousingList(List<Housing> housingList) {
        for (Housing housing : housingList) {
            ContentValues values = convertHousing(housing);
            contentResolver.insert(HousingContract.CONTENT_URI, values);
        }
    }

    @Override
    public void insertHousing(Housing housing) {
        ContentValues values = convertHousing(housing);
        contentResolver.insert(HousingContract.CONTENT_URI, values);
    }

    @Override
    public void insertFarrowingCycle(FarrowingCycle farrowingCycle) {
        ContentValues values = convertFarrowingCycle(farrowingCycle);
        contentResolver.insert(FarrowingCycleContract.CONTENT_URI, values);
    }

    @Override
    public void insertFarrowingCycleList(List<FarrowingCycle> farrowingCycleList) {
        for (FarrowingCycle farrowingCycle : farrowingCycleList) {
            ContentValues values = convertFarrowingCycle(farrowingCycle);
            contentResolver.insert(FarrowingCycleContract.CONTENT_URI, values);
        }
    }

    @Override
    public void insertUsersServerList(List<String> usersServer) {
        for (String userServer : usersServer) {
            ContentValues values = new ContentValues();
            values.put(UsersServerContract.NAME, userServer);
            contentResolver.insert(UsersServerContract.CONTENT_URI, values);
        }
    }

    @Override
    public void insertChangedElement(String nameElement, String ID) {
        ContentValues values = new ContentValues();
        values.put(ChangingContrack.MANE_ELEMENT, nameElement);
        values.put(ChangingContrack.ELEMENT_ID, ID);
        contentResolver.insert(ChangingContrack.CONTENT_URI, values);
    }

    @Override
    public void insertConformityServiceGroup(List<ConformityServiceToGroupDTO> confServGroupDTOList) {
        for (ConformityServiceToGroupDTO conformity : confServGroupDTOList) {
            ContentValues values = new ContentValues();
            values.put(ConformityServiceToGroupContrack.SERVICE_ID, conformity.getServiceID());
            values.put(ConformityServiceToGroupContrack.TYPE_ANIMAL, conformity.getAnimalType());
            values.put(ConformityServiceToGroupContrack.ANIMAL_ID, conformity.getAnimalId());
            contentResolver.insert(ConformityServiceToGroupContrack.CONTENT_URI, values);
        }
    }

    @Override
    public void insertAnimalTypeList(List<AnimalType> animalTypeList) {
        for (AnimalType animalType : animalTypeList) {
            ContentValues values = convertAnimalType(animalType);
            contentResolver.insert(AnimalTypeContract.CONTENT_URI, values);
        }
    }

    @Override
    public void clearChangedElement() {
        contentResolver.delete(ChangingContrack.CONTENT_URI,
                ChangingContrack.ELEMENT_ID + " NOT LIKE '%app-%'",
                null);
    }

    @Override
    public void insertBreedList(List<Breed> breedList) {
        for (Breed breed : breedList) {
            ContentValues values = convertBreed(breed);
            contentResolver.insert(BreedContract.CONTENT_URI, values);
        }
    }

    @Override
    public void insertAnimalStatusList(List<AnimalStatus> animalStatusList) {
        for (AnimalStatus animalStatus : animalStatusList) {
            ContentValues values = convertAnimalStatus(animalStatus);
            contentResolver.insert(AnimalStatusContract.CONTENT_URI, values);
        }
    }

    @Override
    public void insertAnimalDisposList(List<AnimalDispos> animalDisposList) {
        for (AnimalDispos animalDispos : animalDisposList) {
            ContentValues values = convertAnimalDispos(animalDispos);
            contentResolver.insert(AnimalDisposContract.CONTENT_URI, values);
        }
    }

    @Override
    public void insertHertTypeList(List<HertType> hertTypeList) {
        for (HertType hertType : hertTypeList) {
            ContentValues values = convertHertType(hertType);
            contentResolver.insert(HertTypeContract.CONTENT_URI, values);
        }
    }

    @Override
    public void insertAnimalTypeToServiceList(String serviceId, List<Integer> animalTypeList) {
        for (Integer animalType : animalTypeList) {
            ContentValues values = new ContentValues();
            values.put(ServiceToAnimalTypeContract.SERVICE_ID, serviceId);
            values.put(ServiceToAnimalTypeContract.TYPE_ANIMAL, animalType);
            contentResolver.insert(ServiceToAnimalTypeContract.CONTENT_URI, values);
        }
    }

    @Override
    public void insertVetExerciseList(List<VetExercise> vetExerciseList) {
        for (VetExercise vetExercise : vetExerciseList) {
            ContentValues values = convertVetExercise(vetExercise);
            contentResolver.insert(VeterinaryExerciseContract.CONTENT_URI, values);
        }
    }

    @Override
    public void insertVetPreparatList(List<VetPreparat> vetPreparatList) {
        for (VetPreparat vetPreparat : vetPreparatList) {
            ContentValues values = convertVetPreparat(vetPreparat);
            contentResolver.insert(VeterinaryPrepatratContract.CONTENT_URI, values);
        }
    }

    @Override
    public void insertServiceDoneVetExercise(ServiceDoneVetExercise serviceDoneVetExercise) {
        ContentValues values = convertServiceDoneVetExercise(serviceDoneVetExercise);
        contentResolver.insert(ServiceDoneVetExerciseContract.CONTENT_URI, values);
    }

    @Override
    public void insertServiceDoneVetExerciseList(List<ServiceDoneVetExercise> serviceDoneVetExerciseList) {
        for (ServiceDoneVetExercise serviceDoneVetExercise : serviceDoneVetExerciseList) {
            ContentValues values = convertServiceDoneVetExercise(serviceDoneVetExercise);
            contentResolver.insert(ServiceDoneVetExerciseContract.CONTENT_URI, values);
        }

    }

    @Override
    public void insertImage(String externalID, byte[] imageBytes) {
        ContentValues values = new ContentValues();
        values.put(ImageContract.EXTERNAL_ID, externalID);
        values.put(ImageContract.IMAGE, imageBytes);
        contentResolver.insert(ImageContract.CONTENT_URI, values);
    }

    private Date getStartDay(Date date) {
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        return date;
    }

    private Date getEndDay(Date date) {
        date.setHours(23);
        date.setMinutes(59);
        date.setSeconds(59);
        return date;
    }

    private String makePlaceholders(int len) {
        StringBuilder sb = new StringBuilder(len * 2 - 1);
        sb.append("?");
        for (int i = 1; i < len; i++)
            sb.append(",?");
        return sb.toString();
    }

}
