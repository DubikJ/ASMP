package com.avatlantik.asmp.repository;

import android.content.ContentValues;
import android.database.Cursor;

import com.avatlantik.asmp.db.DbContract.AnimalContract;
import com.avatlantik.asmp.db.DbContract.AnimalDisposContract;
import com.avatlantik.asmp.db.DbContract.AnimalHistoryContract;
import com.avatlantik.asmp.db.DbContract.AnimalStatusContract;
import com.avatlantik.asmp.db.DbContract.AnimalTypeContract;
import com.avatlantik.asmp.db.DbContract.BreedContract;
import com.avatlantik.asmp.db.DbContract.FarrowingCycleContract;
import com.avatlantik.asmp.db.DbContract.HertTypeContract;
import com.avatlantik.asmp.db.DbContract.HousingContract;
import com.avatlantik.asmp.db.DbContract.ServiceContract;
import com.avatlantik.asmp.db.DbContract.ServiceDoneContract;
import com.avatlantik.asmp.db.DbContract.VeterinaryExerciseContract;
import com.avatlantik.asmp.db.DbContract.VeterinaryPrepatratContract;
import com.avatlantik.asmp.db.DbContract.ServiceDoneVetExerciseContract;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static com.avatlantik.asmp.common.Consts.CLEAR_GUID;

class ModelConverter {

    private final static SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");

    static ContentValues convertService(ServiceData serviceData) {
        ContentValues values = new ContentValues();
        values.put(ServiceContract.EXTERNAL_ID, serviceData.getExternalId());
        values.put(ServiceContract.NAME, serviceData.getName());
        values.put(ServiceContract.TYPE_RESULT, serviceData.getTypeResult());
        return values;
    }

    static ContentValues convertAnimal(Animal animal) {
        ContentValues values = new ContentValues();
        values.put(AnimalContract.EXTERNAL_ID, animal.getExternalId());
        values.put(AnimalContract.TYPE_ANIMAL, animal.getTypeAnimal());
        values.put(AnimalContract.RFID, animal.getRfid());
        values.put(AnimalContract.CODE, animal.getCode());
        values.put(AnimalContract.ADD_CODE, animal.getAddCode());
        values.put(AnimalContract.NAME, animal.getName());
        values.put(AnimalContract.IS_GROUP, animal.isGroup());
        values.put(AnimalContract.IS_GROUP_ANIMAL, animal.isGroupAnimal());
        values.put(AnimalContract.GROUP_ID, animal.getGroup() == null ||
                animal.getGroup().isEmpty() ? CLEAR_GUID : animal.getGroup());
        values.put(AnimalContract.CORPS_ID, animal.getCorp());
        values.put(AnimalContract.SECTOR_ID, animal.getSector());
        values.put(AnimalContract.CELL_ID, animal.getCell());
        values.put(AnimalContract.NUMBER, animal.getNumber());
        values.put(AnimalContract.PHOTO, animal.getPhoto());
        values.put(AnimalContract.DATE_REC, animal.getDateRec()== null?
                null: animal.getDateRec().getTime() + TimeZone.getDefault().getRawOffset());
        values.put(AnimalContract.STATUS, animal.getStatus());
        values.put(AnimalContract.BREED, animal.getBreed());
        values.put(AnimalContract.HERD, animal.getHerd());
        return values;
    }

    static ContentValues convertServiceDone(ServiceDone serviceDone) {
        ContentValues values = new ContentValues();
        values.put(ServiceDoneContract.DATE, serviceDone.getDate()== null?
                null: serviceDone.getDate().getTime() + TimeZone.getDefault().getRawOffset());
        values.put(ServiceDoneContract.DATE_DAY, serviceDone.getDateDay()== null?
                null: dateFormatter.format(serviceDone.getDateDay().getTime() + TimeZone.getDefault().getRawOffset()));
        values.put(ServiceDoneContract.ANIMAL_ID, serviceDone.getAnimalId());
        values.put(ServiceDoneContract.SERVICE_ID, serviceDone.getServiceId());
        values.put(ServiceDoneContract.ISPLANE, serviceDone.getPlane());
        values.put(ServiceDoneContract.DONE, serviceDone.getDone());
        values.put(ServiceDoneContract.NUMBER, serviceDone.getNumber());
        values.put(ServiceDoneContract.LIVE, serviceDone.getLive());
        values.put(ServiceDoneContract.NORMAl, serviceDone.getNormal());
        values.put(ServiceDoneContract.WEAK, serviceDone.getWeak());
        values.put(ServiceDoneContract.DEATH, serviceDone.getDeath());
        values.put(ServiceDoneContract.MUMMY, serviceDone.getMummy());
        values.put(ServiceDoneContract.TECN_GROUP_TO, serviceDone.getTecnGroupTo());
        values.put(ServiceDoneContract.WEIGHT, serviceDone.getWeight());
        values.put(ServiceDoneContract.BOAR_1, serviceDone.getBoar1());
        values.put(ServiceDoneContract.BOAR_2, serviceDone.getBoar2());
        values.put(ServiceDoneContract.BOAR_3, serviceDone.getBoar3());
        values.put(ServiceDoneContract.NOTE, serviceDone.getNote());
        values.put(ServiceDoneContract.CORP_TO, serviceDone.getCorpTo());
        values.put(ServiceDoneContract.SECTOR_TO, serviceDone.getSectorTo());
        values.put(ServiceDoneContract.CELL_TO, serviceDone.getCellTo());
        values.put(ServiceDoneContract.RESULT_SERVICE, serviceDone.getResultService());
        values.put(ServiceDoneContract.ADM_NUMBER, serviceDone.getAdmNumber());
        values.put(ServiceDoneContract.STATUS, serviceDone.getStatus());
        values.put(ServiceDoneContract.DISPOSANIM, serviceDone.getDisposAnim());
        values.put(ServiceDoneContract.LENGHT, serviceDone.getLength());
        values.put(ServiceDoneContract.BREAD, serviceDone.getBread());
        values.put(ServiceDoneContract.EXTERIOR, serviceDone.getExterior());
        values.put(ServiceDoneContract.DEPTHMYSZ, serviceDone.getDepthMysz());
        values.put(ServiceDoneContract.NEW_CODE, serviceDone.getNewCode());
        values.put(ServiceDoneContract.ARTIF_INSEMEN, serviceDone.isArtifInsemen());
        values.put(ServiceDoneContract.ANIM_GROUP_TO, serviceDone.getAnimalGroupTo());

        return values;
    }

    static ContentValues convertAnimalHistory(AnimalHistory animalHistory) {
        ContentValues values = new ContentValues();
        values.put(AnimalHistoryContract.EXTERNAL_ID, animalHistory.getExternalId());
        values.put(AnimalHistoryContract.ANIMAL_ID, animalHistory.getAnimalId());
        values.put(AnimalHistoryContract.DATE, animalHistory.getDate()== null?
                                               null: animalHistory.getDate().getTime());
        values.put(AnimalHistoryContract.SERVICE_DATA, animalHistory.getServiceData());
        values.put(AnimalHistoryContract.RESULT, animalHistory.getResult());
        return values;
    }

    static ContentValues convertAnimalType(AnimalType animalType) {
        ContentValues values = new ContentValues();
        values.put(AnimalTypeContract.TYPE_ANIMAL, animalType.getTypeAnimal());
        values.put(AnimalTypeContract.NAME, animalType.getName());
        return values;
    }

    static ContentValues convertHousing(Housing housing) {
        ContentValues values = new ContentValues();
        values.put(HousingContract.EXTERNAL_ID, housing.getExternalId());
        values.put(HousingContract.TYPE, housing.getType());
        values.put(HousingContract.NAME, housing.getName());
        values.put(HousingContract.PARENT_ID, housing.getParentId());
        return values;
    }

    static ContentValues convertFarrowingCycle(FarrowingCycle farrowingCycle) {
        ContentValues values = new ContentValues();
        values.put(FarrowingCycleContract.EXTERNAL_ID, farrowingCycle.getExternalId());
        values.put(FarrowingCycleContract.ANIMAL_ID, farrowingCycle.getAnimalId());
        values.put(FarrowingCycleContract.DATE, farrowingCycle.getDate()== null?
                null: farrowingCycle.getDate().getTime());
        values.put(FarrowingCycleContract.SERVICE_DATA, farrowingCycle.getServiceData());
        values.put(FarrowingCycleContract.RESULT, farrowingCycle.getResult());
        return values;
    }

    static ContentValues convertBreed(Breed breed) {
        ContentValues values = new ContentValues();
        values.put(BreedContract.EXTERNAL_ID, breed.getExternalId());
        values.put(BreedContract.NAME, breed.getName());
        return values;
    }

    static ContentValues convertAnimalStatus(AnimalStatus animalStatus) {
        ContentValues values = new ContentValues();
        values.put(AnimalStatusContract.EXTERNAL_ID, animalStatus.getExternalId());
        values.put(AnimalStatusContract.NAME, animalStatus.getName());
        return values;
    }

    static ContentValues convertAnimalDispos(AnimalDispos animalDispos) {
        ContentValues values = new ContentValues();
        values.put(AnimalDisposContract.EXTERNAL_ID, animalDispos.getExternalId());
        values.put(AnimalDisposContract.NAME, animalDispos.getName());
        return values;
    }

    static ContentValues convertHertType(HertType hertType) {
        ContentValues values = new ContentValues();
        values.put(HertTypeContract.EXTERNAL_ID, hertType.getExternalId());
        values.put(HertTypeContract.NAME, hertType.getName());
        return values;
    }

    static ContentValues convertVetExercise(VetExercise vetExercise) {
        ContentValues values = new ContentValues();
        values.put(VeterinaryExerciseContract.EXTERNAL_ID, vetExercise.getExternalId());
        values.put(VeterinaryExerciseContract.NAME, vetExercise.getName());
        return values;
    }

    static ContentValues convertVetPreparat(VetPreparat vetPreparat) {
        ContentValues values = new ContentValues();
        values.put(VeterinaryPrepatratContract.EXTERNAL_ID, vetPreparat.getExternalId());
        values.put(VeterinaryPrepatratContract.NAME, vetPreparat.getName());
        return values;
    }

    static ContentValues convertServiceDoneVetExercise(ServiceDoneVetExercise serviceDoneVetExercise) {
        ContentValues values = new ContentValues();
        values.put(ServiceDoneVetExerciseContract.ANIMAL_ID, serviceDoneVetExercise.getAnimalId());
        values.put(ServiceDoneVetExerciseContract.EXERCISE_ID, serviceDoneVetExercise.getExerciseId());
        values.put(ServiceDoneVetExerciseContract.PREPARAT_ID, serviceDoneVetExercise.getPreparatId());
        return values;
    }

    static ServiceData buildService(Cursor cursor) {
        return ServiceData.builder()
                .id(cursor.getInt(cursor.getColumnIndex(ServiceContract._ID)))
                .name(cursor.getString(cursor.getColumnIndex(ServiceContract.NAME)))
                .externalId(cursor.getString(cursor.getColumnIndex(ServiceContract.EXTERNAL_ID)))
                .typeResult(cursor.getInt(cursor.getColumnIndex(ServiceContract.TYPE_RESULT)))
                .build();
    }

    static Animal buildAnimal(Cursor cursor) {
        return Animal.builder()
                .id(cursor.getInt(cursor.getColumnIndex(AnimalContract._ID)))
                .externalId(cursor.getString(cursor.getColumnIndex(AnimalContract.EXTERNAL_ID)))
                .typeAnimal(cursor.getInt(cursor.getColumnIndex(AnimalContract.TYPE_ANIMAL)))
                .rfid(cursor.getString(cursor.getColumnIndex(AnimalContract.RFID)))
                .code(cursor.getString(cursor.getColumnIndex(AnimalContract.CODE)))
                .addCode(cursor.getString(cursor.getColumnIndex(AnimalContract.ADD_CODE)))
                .name(cursor.getString(cursor.getColumnIndex(AnimalContract.NAME)))
                .isGroup(cursor.getInt(cursor.getColumnIndex(AnimalContract.IS_GROUP))== 1)
                .isGroupAnimal(cursor.getInt(cursor.getColumnIndex(AnimalContract.IS_GROUP_ANIMAL))== 1)
                .group(cursor.getString(cursor.getColumnIndex(AnimalContract.GROUP_ID)))
                .corp(cursor.getString(cursor.getColumnIndex(AnimalContract.CORPS_ID)))
                .sector(cursor.getString(cursor.getColumnIndex(AnimalContract.SECTOR_ID)))
                .cell(cursor.getString(cursor.getColumnIndex(AnimalContract.CELL_ID)))
                .number(cursor.getInt(cursor.getColumnIndex(AnimalContract.NUMBER)))
                .photo(cursor.getString(cursor.getColumnIndex(AnimalContract.PHOTO)))
                .dateRec(convertDate(cursor, AnimalContract.DATE_REC))
                .status(cursor.getString(cursor.getColumnIndex(AnimalContract.STATUS)))
                .breed(cursor.getString(cursor.getColumnIndex(AnimalContract.BREED)))
                .herd(cursor.getString(cursor.getColumnIndex(AnimalContract.HERD)))
                .build();
    }

    static ServiceDone buildServiceDone(Cursor cursor) {
        return ServiceDone.builder()
                .id(cursor.getInt(cursor.getColumnIndex(ServiceDoneContract._ID)))
                .date(convertDate(cursor, ServiceDoneContract.DATE))
                .dateDay(convertDateFromString(cursor, ServiceDoneContract.DATE_DAY))
                .animalId(cursor.getString(cursor.getColumnIndex(ServiceDoneContract.ANIMAL_ID)))
                .serviceId(cursor.getString(cursor.getColumnIndex(ServiceDoneContract.SERVICE_ID)))
                .isPlane(cursor.getInt(cursor.getColumnIndex(ServiceDoneContract.ISPLANE))== 1)
                .done(cursor.getInt(cursor.getColumnIndex(ServiceDoneContract.DONE))== 1)
                .number(cursor.getInt(cursor.getColumnIndex(ServiceDoneContract.NUMBER)))
                .live(cursor.getInt(cursor.getColumnIndex(ServiceDoneContract.LIVE)))
                .normal(cursor.getInt(cursor.getColumnIndex(ServiceDoneContract.NORMAl)))
                .weak(cursor.getInt(cursor.getColumnIndex(ServiceDoneContract.WEAK)))
                .death(cursor.getInt(cursor.getColumnIndex(ServiceDoneContract.DEATH)))
                .mummy(cursor.getInt(cursor.getColumnIndex(ServiceDoneContract.MUMMY)))
                .tecnGroupTo(cursor.getString(cursor.getColumnIndex(ServiceDoneContract.TECN_GROUP_TO)))
                .weight(cursor.getDouble(cursor.getColumnIndex(ServiceDoneContract.WEIGHT)))
                .boar1(cursor.getString(cursor.getColumnIndex(ServiceDoneContract.BOAR_1)))
                .boar2(cursor.getString(cursor.getColumnIndex(ServiceDoneContract.BOAR_2)))
                .boar3(cursor.getString(cursor.getColumnIndex(ServiceDoneContract.BOAR_3)))
                .note(cursor.getString(cursor.getColumnIndex(ServiceDoneContract.NOTE)))
                .corpTo(cursor.getString(cursor.getColumnIndex(ServiceDoneContract.CORP_TO)))
                .sectorTo(cursor.getString(cursor.getColumnIndex(ServiceDoneContract.SECTOR_TO)))
                .cellTo(cursor.getString(cursor.getColumnIndex(ServiceDoneContract.CELL_TO)))
                .resultService(cursor.getString(cursor.getColumnIndex(ServiceDoneContract.RESULT_SERVICE)))
                .admNumber(cursor.getInt(cursor.getColumnIndex(ServiceDoneContract.ADM_NUMBER)))
                .status(cursor.getString(cursor.getColumnIndex(ServiceDoneContract.STATUS)))
                .disposAnim(cursor.getString(cursor.getColumnIndex(ServiceDoneContract.DISPOSANIM)))
                .length(cursor.getInt(cursor.getColumnIndex(ServiceDoneContract.LENGHT)))
                .bread(cursor.getInt(cursor.getColumnIndex(ServiceDoneContract.BREAD)))
                .exterior(cursor.getInt(cursor.getColumnIndex(ServiceDoneContract.EXTERIOR)))
                .depthMysz(cursor.getInt(cursor.getColumnIndex(ServiceDoneContract.DEPTHMYSZ)))
                .newCode(cursor.getString(cursor.getColumnIndex(ServiceDoneContract.NEW_CODE)))
                .artifInsemen(cursor.getInt(cursor.getColumnIndex(ServiceDoneContract.ARTIF_INSEMEN))== 1)
                .animalGroupTo(cursor.getString(cursor.getColumnIndex(ServiceDoneContract.ANIM_GROUP_TO)))
                .build();
    }

    static AnimalHistory buildAnimalHistory(Cursor cursor) {
        return AnimalHistory.builder()
                .id(cursor.getInt(cursor.getColumnIndex(AnimalHistoryContract._ID)))
                .externalId(cursor.getString(cursor.getColumnIndex(AnimalHistoryContract.EXTERNAL_ID)))
                .animalId(cursor.getString(cursor.getColumnIndex(AnimalHistoryContract.ANIMAL_ID)))
                .date(convertDate(cursor, AnimalHistoryContract.DATE))
                .serviceData(cursor.getString(cursor.getColumnIndex(AnimalHistoryContract.SERVICE_DATA)))
                .result(cursor.getString(cursor.getColumnIndex(AnimalHistoryContract.RESULT)))
                .build();
    }

    static AnimalType buildAnimalType(Cursor cursor) {
        return AnimalType.builder()
                .id(cursor.getInt(cursor.getColumnIndex(AnimalTypeContract._ID)))
                .name(cursor.getString(cursor.getColumnIndex(AnimalTypeContract.NAME)))
                .typeAnimal(cursor.getInt(cursor.getColumnIndex(AnimalTypeContract.TYPE_ANIMAL)))
                .build();
    }

    static Housing buildHousing(Cursor cursor) {
        return Housing.builder()
                .id(cursor.getInt(cursor.getColumnIndex(HousingContract._ID)))
                .externalId(cursor.getString(cursor.getColumnIndex(HousingContract.EXTERNAL_ID)))
                .type(cursor.getInt(cursor.getColumnIndex(HousingContract.TYPE)))
                .name(cursor.getString(cursor.getColumnIndex(HousingContract.NAME)))
                .parentId(cursor.getString(cursor.getColumnIndex(HousingContract.PARENT_ID)))
                .build();
    }

    static FarrowingCycle buildFarrowingCycle(Cursor cursor) {
        return FarrowingCycle.builder()
                .id(cursor.getInt(cursor.getColumnIndex(FarrowingCycleContract._ID)))
                .externalId(cursor.getString(cursor.getColumnIndex(FarrowingCycleContract.EXTERNAL_ID)))
                .animalId(cursor.getString(cursor.getColumnIndex(FarrowingCycleContract.ANIMAL_ID)))
                .date(convertDate(cursor, FarrowingCycleContract.DATE))
                .serviceData(cursor.getString(cursor.getColumnIndex(FarrowingCycleContract.SERVICE_DATA)))
                .result(cursor.getString(cursor.getColumnIndex(FarrowingCycleContract.RESULT)))
                .build();
    }

    static Breed buildBreed(Cursor cursor) {
        return Breed.builder()
                .id(cursor.getInt(cursor.getColumnIndex(BreedContract._ID)))
                .externalId(cursor.getString(cursor.getColumnIndex(BreedContract.EXTERNAL_ID)))
                .name(cursor.getString(cursor.getColumnIndex(BreedContract.NAME)))
                .build();
    }

    static AnimalStatus buildAnimalStatus(Cursor cursor) {
        return AnimalStatus.builder()
                .id(cursor.getInt(cursor.getColumnIndex(AnimalStatusContract._ID)))
                .externalId(cursor.getString(cursor.getColumnIndex(AnimalStatusContract.EXTERNAL_ID)))
                .name(cursor.getString(cursor.getColumnIndex(AnimalStatusContract.NAME)))
                .build();
    }

    static AnimalDispos buildAnimalDispos(Cursor cursor) {
        return AnimalDispos.builder()
                .id(cursor.getInt(cursor.getColumnIndex(AnimalDisposContract._ID)))
                .externalId(cursor.getString(cursor.getColumnIndex(AnimalDisposContract.EXTERNAL_ID)))
                .name(cursor.getString(cursor.getColumnIndex(AnimalDisposContract.NAME)))
                .build();
    }

    static HertType buildHertType(Cursor cursor) {
        return HertType.builder()
                .id(cursor.getInt(cursor.getColumnIndex(HertTypeContract._ID)))
                .externalId(cursor.getString(cursor.getColumnIndex(HertTypeContract.EXTERNAL_ID)))
                .name(cursor.getString(cursor.getColumnIndex(HertTypeContract.NAME)))
                .build();
    }

    static VetExercise buildVetExercise(Cursor cursor) {
        return VetExercise.builder()
                .id(cursor.getInt(cursor.getColumnIndex(VeterinaryExerciseContract._ID)))
                .externalId(cursor.getString(cursor.getColumnIndex(VeterinaryExerciseContract.EXTERNAL_ID)))
                .name(cursor.getString(cursor.getColumnIndex(VeterinaryExerciseContract.NAME)))
                .build();
    }

    static VetPreparat buildVetPreparat(Cursor cursor) {
        return VetPreparat.builder()
                .id(cursor.getInt(cursor.getColumnIndex(VeterinaryPrepatratContract._ID)))
                .externalId(cursor.getString(cursor.getColumnIndex(VeterinaryPrepatratContract.EXTERNAL_ID)))
                .name(cursor.getString(cursor.getColumnIndex(VeterinaryPrepatratContract.NAME)))
                .build();
    }

    static ServiceDoneVetExercise buildServiceDoneVetExercise(Cursor cursor) {
        return ServiceDoneVetExercise.builder()
                .animalId(cursor.getString(cursor.getColumnIndex(ServiceDoneVetExerciseContract.ANIMAL_ID)))
                .exerciseId(cursor.getString(cursor.getColumnIndex(ServiceDoneVetExerciseContract.EXERCISE_ID)))
                .preparatId(cursor.getString(cursor.getColumnIndex(ServiceDoneVetExerciseContract.PREPARAT_ID)))
                .build();
    }

    private static Date convertDate(Cursor cursor, String nameColum){

        String value = cursor.getString(cursor.getColumnIndex(nameColum));

        if(value==null|| value.isEmpty()){
            return new Date(Long.valueOf("0"));
        }else {
            return new Date(Long.valueOf(cursor.getString(cursor.getColumnIndex(nameColum))));
        }
    }

    private static Date convertDateFromString(Cursor cursor, String nameColum){

        String dateString = cursor.getString(cursor.getColumnIndex(nameColum));

        if(dateString!=null&&dateString.isEmpty()) {
            try {
                return dateFormatter.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
                return new Date(Long.valueOf("0"));
            }
        }else {return new Date(Long.valueOf("0"));}
    }

    static String getStringValueByColumName(Cursor cursor, String columName) {
        return cursor.getString(cursor.getColumnIndex(columName));
    }

    static int getIntValueByColumName(Cursor cursor, String columName) {
        return cursor.getInt(cursor.getColumnIndex(columName));
    }

}
