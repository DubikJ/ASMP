package com.avatlantik.asmp.db;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class DbContract {
    private static final  String CONTENT = "content://";
    public static final String AUTHORITY = "com.avatlantik.asmp.dbProvider";

    public static final Uri CONTENT_URI
            = Uri.parse(CONTENT + AUTHORITY);

    public static final class UserSettingsContract implements BaseColumns {

        public static final String TABLE_NAME = "user_settings";

        public static final String USER_SETTING_ID = "setting_id";
        public static final String SETTING_VALUE = "value";

        public static final Uri CONTENT_URI =
                Uri.withAppendedPath(
                        DbContract.CONTENT_URI,
                        TABLE_NAME);

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE
                        + "/vnd."
                        + AUTHORITY + "."
                        + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE
                        + "/vnd."
                        + AUTHORITY + "."
                        + TABLE_NAME;

        public static final String[] PROJECTION_ALL =
                {_ID, USER_SETTING_ID, SETTING_VALUE};

        public static final String[] UNIQUE_COLUMNS =
                {USER_SETTING_ID};

        public static final String DEFAULT_SORT_ORDER = SETTING_VALUE + " ASC";

    }

    public static final class ServiceContract implements BaseColumns {

        public static final String TABLE_NAME = "service";

        public static final String EXTERNAL_ID = "external_id";
        public static final String NAME = "name";
        public static final String TYPE_RESULT = "type_result";


        public static final Uri CONTENT_URI =
                Uri.withAppendedPath(
                        DbContract.CONTENT_URI,
                        TABLE_NAME);

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE
                        + "/vnd."
                        + AUTHORITY + "."
                        + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE
                        + "/vnd."
                        + AUTHORITY + "."
                        + TABLE_NAME;

        public static final String[] PROJECTION_ALL =
                {_ID, EXTERNAL_ID, NAME, TYPE_RESULT};

        public static final String[] UNIQUE_COLUMNS =
                {EXTERNAL_ID};

        public static final String DEFAULT_SORT_ORDER = NAME + " ASC";
    }

    public static final class AnimalContract implements BaseColumns {

        public static final String TABLE_NAME = "animal";

        public static final String EXTERNAL_ID = "external_id";
        public static final String TYPE_ANIMAL = "type_animal";
        public static final String RFID = "rfid";
        public static final String CODE = "code";
        public static final String ADD_CODE = "add_code";
        public static final String NAME = "name";
        public static final String IS_GROUP = "is_group";
        public static final String IS_GROUP_ANIMAL = "is_group_animal";
        public static final String GROUP_ID = "group_id";
        public static final String CORPS_ID = "corps_id";
        public static final String SECTOR_ID = "sector_id";
        public static final String CELL_ID = "cell_id";
        public static final String NUMBER = "number";
        public static final String PHOTO = "photo";
        public static final String DATE_REC = "date_rec";
        public static final String STATUS = "status";
        public static final String BREED = "breed";
        public static final String HERD = "herd";

        public static final Uri CONTENT_URI =
                Uri.withAppendedPath(
                        DbContract.CONTENT_URI,
                        TABLE_NAME);

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE
                        + "/vnd."
                        + AUTHORITY + "."
                        + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE
                        + "/vnd."
                        + AUTHORITY + "."
                        + TABLE_NAME;

        public static final String[] PROJECTION_ALL =
                {_ID, EXTERNAL_ID, TYPE_ANIMAL, RFID, CODE, ADD_CODE, NAME, IS_GROUP, IS_GROUP_ANIMAL,
                        GROUP_ID, CORPS_ID, SECTOR_ID, CELL_ID, NUMBER, PHOTO, DATE_REC, STATUS,
                        BREED, HERD};

        public static final String[] UNIQUE_COLUMNS =
                {EXTERNAL_ID};

        public static final String DEFAULT_SORT_ORDER = CODE + " ASC";
    }

    public static final class ServiceDoneContract implements BaseColumns {

        public static final String TABLE_NAME = "done_service";

        public static final String DATE = "date";
        public static final String DATE_DAY = "date_day";
        public static final String SERVICE_ID = "service_id";
        public static final String ANIMAL_ID = "animal_id";
        public static final String ISPLANE = "is_plane";
        public static final String NUMBER = "number";
        public static final String DONE = "done";
        public static final String LIVE = "live";
        public static final String NORMAl = "normal";
        public static final String WEAK = "weak";
        public static final String DEATH = "death";
        public static final String MUMMY = "mummy";
        public static final String TECN_GROUP_TO = "tecn_group_to";
        public static final String WEIGHT = "weight";
        public static final String BOAR_1 = "boar_1";
        public static final String BOAR_2 = "boar_2";
        public static final String BOAR_3 = "boar_3";
        public static final String NOTE = "note";
        public static final String CORP_TO = "corp_to";
        public static final String SECTOR_TO = "sector_to";
        public static final String CELL_TO = "cell_to";
        public static final String RESULT_SERVICE = "result_service";
        public static final String ADM_NUMBER = "adm_number";
        public static final String STATUS = "status";
        public static final String DISPOSANIM = "dispos_anim";
        public static final String LENGHT = "lenght";
        public static final String BREAD = "bread";
        public static final String EXTERIOR = "exterior";
        public static final String DEPTHMYSZ = "depth_mysz";
        public static final String NEW_CODE = "new_code";
        public static final String ARTIF_INSEMEN = "artif_insemen";
        public static final String ANIM_GROUP_TO = "anim_group_to";

        public static final Uri CONTENT_URI =
                Uri.withAppendedPath(
                        DbContract.CONTENT_URI,
                        TABLE_NAME);

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE
                        + "/vnd."
                        + AUTHORITY + "."
                        + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE
                        + "/vnd."
                        + AUTHORITY + "."
                        + TABLE_NAME;

        public static final String[] PROJECTION_ALL =
                {_ID, DATE, DATE_DAY, SERVICE_ID, ANIMAL_ID, ISPLANE, NUMBER, DONE, LIVE, NORMAl, WEAK,
                        DEATH, MUMMY, TECN_GROUP_TO, WEIGHT, BOAR_1, BOAR_2, BOAR_3, NOTE,
                        CORP_TO, SECTOR_TO, CELL_TO, RESULT_SERVICE, ADM_NUMBER, STATUS, DISPOSANIM,
                        LENGHT, BREAD, EXTERIOR, DEPTHMYSZ, NEW_CODE, ARTIF_INSEMEN, ANIM_GROUP_TO};

        public static final String[] UNIQUE_COLUMNS =
                {DATE_DAY, SERVICE_ID, ANIMAL_ID};

        public static final String DEFAULT_SORT_ORDER = DATE + " ASC";
    }

    public static final class AnimalTypeContract implements BaseColumns {

        public static final String TABLE_NAME = "animal_type";

        public static final String TYPE_ANIMAL = "type_animal";
        public static final String NAME = "name";

        public static final Uri CONTENT_URI =
                Uri.withAppendedPath(
                        DbContract.CONTENT_URI,
                        TABLE_NAME);

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE
                        + "/vnd."
                        + AUTHORITY + "."
                        + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE
                        + "/vnd."
                        + AUTHORITY + "."
                        + TABLE_NAME;

        public static final String[] PROJECTION_ALL =
                {_ID, TYPE_ANIMAL, NAME};

        public static final String[] UNIQUE_COLUMNS =
                {TYPE_ANIMAL, NAME};

        public static final String DEFAULT_SORT_ORDER = TYPE_ANIMAL + " ASC";
    }

    public static final class HousingContract implements BaseColumns {

        public static final String TABLE_NAME = "housing";

        public static final String EXTERNAL_ID = "external_id";
        public static final String TYPE = "type";
        public static final String NAME = "name";
        public static final String PARENT_ID = "parent_id";

        public static final Uri CONTENT_URI =
                Uri.withAppendedPath(
                        DbContract.CONTENT_URI,
                        TABLE_NAME);

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE
                        + "/vnd."
                        + AUTHORITY + "."
                        + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE
                        + "/vnd."
                        + AUTHORITY + "."
                        + TABLE_NAME;

        public static final String[] PROJECTION_ALL =
                {_ID, EXTERNAL_ID, TYPE, NAME, PARENT_ID};

        public static final String[] UNIQUE_COLUMNS =
                {EXTERNAL_ID, TYPE};

        public static final String DEFAULT_SORT_ORDER = NAME + " ASC";
    }

    public static final class AnimalHistoryContract implements BaseColumns {

        public static final String TABLE_NAME = "animal_history";

        public static final String EXTERNAL_ID = "external_id";
        public static final String ANIMAL_ID = "animal_id";
        public static final String DATE = "date";
        public static final String SERVICE_DATA = "service_data";
        public static final String RESULT = "result";


        public static final Uri CONTENT_URI =
                Uri.withAppendedPath(
                        DbContract.CONTENT_URI,
                        TABLE_NAME);

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE
                        + "/vnd."
                        + AUTHORITY + "."
                        + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE
                        + "/vnd."
                        + AUTHORITY + "."
                        + TABLE_NAME;

        public static final String[] PROJECTION_ALL =
                {_ID, EXTERNAL_ID, ANIMAL_ID, DATE, SERVICE_DATA, RESULT};

        public static final String[] UNIQUE_COLUMNS =
                {EXTERNAL_ID};

        public static final String DEFAULT_SORT_ORDER = DATE + " ASC";
    }

    public static final class FarrowingCycleContract implements BaseColumns {

        public static final String TABLE_NAME = "cycle_farrowing";

        public static final String EXTERNAL_ID = "external_id";
        public static final String ANIMAL_ID = "animal_id";
        public static final String DATE = "date";
        public static final String SERVICE_DATA = "service_data";
        public static final String RESULT = "result";


        public static final Uri CONTENT_URI =
                Uri.withAppendedPath(
                        DbContract.CONTENT_URI,
                        TABLE_NAME);

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE
                        + "/vnd."
                        + AUTHORITY + "."
                        + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE
                        + "/vnd."
                        + AUTHORITY + "."
                        + TABLE_NAME;

        public static final String[] PROJECTION_ALL =
                {_ID, EXTERNAL_ID, ANIMAL_ID, DATE, SERVICE_DATA, RESULT};

        public static final String[] UNIQUE_COLUMNS =
                {EXTERNAL_ID};

        public static final String DEFAULT_SORT_ORDER = DATE + " ASC";
    }

    public static final class UsersServerContract implements BaseColumns {

        public static final String TABLE_NAME = "users_server";

        public static final String NAME = "NAME";

        public static final Uri CONTENT_URI =
                Uri.withAppendedPath(
                        DbContract.CONTENT_URI,
                        TABLE_NAME);

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE
                        + "/vnd."
                        + AUTHORITY + "."
                        + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE
                        + "/vnd."
                        + AUTHORITY + "."
                        + TABLE_NAME;

        public static final String[] PROJECTION_ALL =
                {_ID, NAME};

        public static final String[] UNIQUE_COLUMNS =
                {NAME};

        public static final String DEFAULT_SORT_ORDER = NAME + " ASC";

    }

    public static final class ChangingContrack implements BaseColumns {

        public static final String TABLE_NAME = "changed_element";

        public static final String MANE_ELEMENT = "name";
        public static final String ELEMENT_ID = "externa_id";

        public static final Uri CONTENT_URI =
                Uri.withAppendedPath(
                        DbContract.CONTENT_URI,
                        TABLE_NAME);

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE
                        + "/vnd."
                        + AUTHORITY + "."
                        + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE
                        + "/vnd."
                        + AUTHORITY + "."
                        + TABLE_NAME;

        public static final String[] PROJECTION_ALL =
                {_ID, MANE_ELEMENT, ELEMENT_ID};

        public static final String[] UNIQUE_COLUMNS =
                {MANE_ELEMENT, ELEMENT_ID};

        public static final String DEFAULT_SORT_ORDER = ELEMENT_ID + " ASC";

    }

    public static final class ConformityServiceToGroupContrack implements BaseColumns {

        public static final String TABLE_NAME = "conformity_service_group";

        public static final String SERVICE_ID = "service_id";
        public static final String ANIMAL_ID = "animal_id";
        public static final String TYPE_ANIMAL = "type_animal";

        public static final Uri CONTENT_URI =
                Uri.withAppendedPath(
                        DbContract.CONTENT_URI,
                        TABLE_NAME);

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE
                        + "/vnd."
                        + AUTHORITY + "."
                        + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE
                        + "/vnd."
                        + AUTHORITY + "."
                        + TABLE_NAME;

        public static final String[] PROJECTION_ALL =
                {_ID, SERVICE_ID, TYPE_ANIMAL, ANIMAL_ID};

        public static final String[] UNIQUE_COLUMNS =
                {SERVICE_ID, TYPE_ANIMAL, ANIMAL_ID};

        public static final String DEFAULT_SORT_ORDER = SERVICE_ID + " ASC";

    }

    public static final class BreedContract implements BaseColumns {

        public static final String TABLE_NAME = "breed";

        public static final String EXTERNAL_ID = "external_id";
        public static final String NAME = "name";

        public static final Uri CONTENT_URI =
                Uri.withAppendedPath(
                        DbContract.CONTENT_URI,
                        TABLE_NAME);

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE
                        + "/vnd."
                        + AUTHORITY + "."
                        + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE
                        + "/vnd."
                        + AUTHORITY + "."
                        + TABLE_NAME;

        public static final String[] PROJECTION_ALL =
                {_ID, EXTERNAL_ID, NAME};

        public static final String[] UNIQUE_COLUMNS =
                {EXTERNAL_ID};

        public static final String DEFAULT_SORT_ORDER = NAME + " ASC";
    }

    public static final class AnimalStatusContract implements BaseColumns {

        public static final String TABLE_NAME = "animal_status";

        public static final String EXTERNAL_ID = "external_id";
        public static final String NAME = "name";

        public static final Uri CONTENT_URI =
                Uri.withAppendedPath(
                        DbContract.CONTENT_URI,
                        TABLE_NAME);

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE
                        + "/vnd."
                        + AUTHORITY + "."
                        + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE
                        + "/vnd."
                        + AUTHORITY + "."
                        + TABLE_NAME;

        public static final String[] PROJECTION_ALL =
                {_ID, EXTERNAL_ID, NAME};

        public static final String[] UNIQUE_COLUMNS =
                {EXTERNAL_ID};

        public static final String DEFAULT_SORT_ORDER = NAME + " ASC";
    }

    public static final class AnimalDisposContract implements BaseColumns {

        public static final String TABLE_NAME = "animal_dispos";

        public static final String EXTERNAL_ID = "external_id";
        public static final String NAME = "name";

        public static final Uri CONTENT_URI =
                Uri.withAppendedPath(
                        DbContract.CONTENT_URI,
                        TABLE_NAME);

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE
                        + "/vnd."
                        + AUTHORITY + "."
                        + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE
                        + "/vnd."
                        + AUTHORITY + "."
                        + TABLE_NAME;

        public static final String[] PROJECTION_ALL =
                {_ID, EXTERNAL_ID, NAME};

        public static final String[] UNIQUE_COLUMNS =
                {EXTERNAL_ID};

        public static final String DEFAULT_SORT_ORDER = NAME + " ASC";
    }

    public static final class HertTypeContract implements BaseColumns {

        public static final String TABLE_NAME = "hert_type";

        public static final String EXTERNAL_ID = "external_id";
        public static final String NAME = "name";

        public static final Uri CONTENT_URI =
                Uri.withAppendedPath(
                        DbContract.CONTENT_URI,
                        TABLE_NAME);

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE
                        + "/vnd."
                        + AUTHORITY + "."
                        + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE
                        + "/vnd."
                        + AUTHORITY + "."
                        + TABLE_NAME;

        public static final String[] PROJECTION_ALL =
                {_ID, EXTERNAL_ID, NAME};

        public static final String[] UNIQUE_COLUMNS =
                {EXTERNAL_ID};

        public static final String DEFAULT_SORT_ORDER = NAME + " ASC";
    }

    public static final class ServiceToAnimalTypeContract implements BaseColumns{

        public static final String TABLE_NAME = "service_to_animal";

        public static final String SERVICE_ID = "service_id";
        public static final String TYPE_ANIMAL = "type_animal";

        public static final Uri CONTENT_URI =
                Uri.withAppendedPath(
                        DbContract.CONTENT_URI,
                        TABLE_NAME);

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE
                        + "/vnd."
                        + AUTHORITY + "."
                        + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE
                        + "/vnd."
                        + AUTHORITY + "."
                        + TABLE_NAME;

        public static final String[] PROJECTION_ALL =
                {_ID, SERVICE_ID, TYPE_ANIMAL};

        public static final String[] UNIQUE_COLUMNS =
                {SERVICE_ID, TYPE_ANIMAL};

        public static final String DEFAULT_SORT_ORDER = SERVICE_ID + " ASC";
    }

    public static final class VeterinaryExerciseContract implements BaseColumns{

        public static final String TABLE_NAME = "vet_exercise";

        public static final String EXTERNAL_ID = "external_id";
        public static final String NAME = "name";

        public static final Uri CONTENT_URI =
                Uri.withAppendedPath(
                        DbContract.CONTENT_URI,
                        TABLE_NAME);

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE
                        + "/vnd."
                        + AUTHORITY + "."
                        + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE
                        + "/vnd."
                        + AUTHORITY + "."
                        + TABLE_NAME;

        public static final String[] PROJECTION_ALL =
                {_ID, EXTERNAL_ID, NAME};

        public static final String[] UNIQUE_COLUMNS =
                {EXTERNAL_ID};

        public static final String DEFAULT_SORT_ORDER = NAME + " ASC";
    }

    public static final class VeterinaryPrepatratContract implements BaseColumns{

        public static final String TABLE_NAME = "vet_preparat";

        public static final String EXTERNAL_ID = "external_id";
        public static final String NAME = "name";

        public static final Uri CONTENT_URI =
                Uri.withAppendedPath(
                        DbContract.CONTENT_URI,
                        TABLE_NAME);

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE
                        + "/vnd."
                        + AUTHORITY + "."
                        + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE
                        + "/vnd."
                        + AUTHORITY + "."
                        + TABLE_NAME;

        public static final String[] PROJECTION_ALL =
                {_ID, EXTERNAL_ID, NAME};

        public static final String[] UNIQUE_COLUMNS =
                {EXTERNAL_ID};

        public static final String DEFAULT_SORT_ORDER = NAME + " ASC";
    }

    public static final class ServiceDoneVetExerciseContract implements BaseColumns{

        public static final String TABLE_NAME = "service_vet_exercise";

        public static final String ANIMAL_ID = "animal_id";
        public static final String EXERCISE_ID = "evercise_id";
        public static final String PREPARAT_ID = "preparat_id";

        public static final Uri CONTENT_URI =
                Uri.withAppendedPath(
                        DbContract.CONTENT_URI,
                        TABLE_NAME);

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE
                        + "/vnd."
                        + AUTHORITY + "."
                        + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE
                        + "/vnd."
                        + AUTHORITY + "."
                        + TABLE_NAME;

        public static final String[] PROJECTION_ALL =
                {_ID, ANIMAL_ID, EXERCISE_ID, PREPARAT_ID};

        public static final String[] UNIQUE_COLUMNS =
               {ANIMAL_ID, EXERCISE_ID};

        public static final String DEFAULT_SORT_ORDER = _ID + " ASC";
    }

    public static final class ImageContract implements BaseColumns{

        public static final String TABLE_NAME = "image_list";

        public static final String EXTERNAL_ID = "external_id";
        public static final String IMAGE = "image";

        public static final Uri CONTENT_URI =
                Uri.withAppendedPath(
                        DbContract.CONTENT_URI,
                        TABLE_NAME);

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE
                        + "/vnd."
                        + AUTHORITY + "."
                        + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE
                        + "/vnd."
                        + AUTHORITY + "."
                        + TABLE_NAME;

        public static final String[] PROJECTION_ALL =
                {_ID, EXTERNAL_ID, IMAGE};

        public static final String[] UNIQUE_COLUMNS =
                {EXTERNAL_ID};

        public static final String DEFAULT_SORT_ORDER = _ID + " ASC";
    }
}
