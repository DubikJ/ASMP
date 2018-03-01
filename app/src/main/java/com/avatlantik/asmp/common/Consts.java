package com.avatlantik.asmp.common;

public final class Consts {
    public final static String TAGLOG = "ASMP";
    public final static String TAGLOG_SCAN = "ASMP_Scan";
    public final static String TAGLOG_IMAGE = "ASNP_Task_image";

    public final static String LOGIN = "mLogin";
    public final static String PASSWORD = "mPassword";
    public final static String SERVER = "mServer";
    public final static String USING_SCAN = "mUsingScan";
    public final static String DATE_SYNC = "mDateSync";
    public final static String AUTHORIZED = "mAutorized";
    public final static String NUMBER_MESSAGE = "mNumMessage";
    public final static String USE_EXTERNAL_STORAGE = "mUseExternalStorage";

    public static final String APPLICATION_PROPERTIES = "application.properties";
    public final static String DIRECTORY_APP = "ASMPDir";
    public final static String DIR_PICTURES = "Pictures";
    public static final String NAME_TYPEFILE_PHOTO = ".jpg";

    // Synchronization
    public final static int STATUS_STARTED_SYNC = 0;
    public final static int STATUS_FINISHED_SYNC = 1;
    public final static int STATUS_ERROR_SYNC = -1;

    // Settings
    public final static boolean CLEAR_DATABASE_IN_LOGOUT = true;
    public final static int CONNECT_TIMEOUT_SECONDS_RETROFIT = 180;
    public final static String CONNECT_PATTERN_URL = "smartfarm/hs/asmp.exchange/dataDTO";
    public final static int IMAGE_QUALITY = 60;

    // Service
    public final static int TYPE_RESULT_SERVICE_DEFAULT = 1;
    public final static int TYPE_RESULT_SERVICE_MOVEMENT = 2;
    public final static int TYPE_RESULT_SERVICE_FARROW = 3;
    public final static int TYPE_RESULT_SERVICE_INSEMINATION = 4;
    public final static int TYPE_RESULT_SERVICE_DISTILLATION = 5;
    public final static int TYPE_RESULT_SERVICE_WEANIMG = 6;
    public final static int TYPE_RESULT_SERVICE_REGISTRATION = 7;
    public final static int TYPE_RESULT_SERVICE_MOVEMENT_GROUP = 8;
    public final static int TYPE_RESULT_SERVICE_INSPECTION = 9;
    public final static int TYPE_RESULT_SERVICE_RETIREMENT = 10;
    public final static int TYPE_RESULT_SERVICE_ASSESSMENT = 11;
    public final static int TYPE_RESULT_SERVICE_WEIGNING = 12;
    public final static int TYPE_RESULT_SERVICE_NEW_NUMBER = 13;
    public final static int TYPE_RESULT_SERVICE_MOVEMENT_NO_NUMBER = 14;
    public final static int TYPE_RESULT_SERVICE_VETERINARY = 15;
    public final static int TYPE_RESULT_SERVICE_SELECTION = 16;

    // Animal
    public final static int TYPE_GROUP_ANIMAL_SOW = 1;
    public final static int TYPE_GROUP_ANIMAL_BOAR = 2;
    public final static int TYPE_GROUP_ANIMAL_WEANING = 3;
    public final static int TYPE_GROUP_ANIMAL_CULTIVATION = 4;
    public final static int TYPE_GROUP_ANIMAL_FATTENING = 5;
    public final static int TYPE_GROUP_ANIMAL_ALL = 6;

    // Housing
    public final static int TYPE_HOUSING_CORP = 1;
    public final static int TYPE_HOUSING_SECTOR = 2;
    public final static int TYPE_HOUSING_CELL = 3;

    public static final String CLEAR_GUID = "00000000-0000-0000-0000-000000000000";

}
