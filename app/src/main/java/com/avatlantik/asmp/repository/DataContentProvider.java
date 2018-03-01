package com.avatlantik.asmp.repository;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.avatlantik.asmp.db.AsmpDb;

import java.util.ArrayList;
import java.util.List;

import static com.avatlantik.asmp.db.DbContract.AUTHORITY;
import static com.avatlantik.asmp.db.DbContract.AnimalContract;
import static com.avatlantik.asmp.db.DbContract.AnimalDisposContract;
import static com.avatlantik.asmp.db.DbContract.AnimalHistoryContract;
import static com.avatlantik.asmp.db.DbContract.AnimalStatusContract;
import static com.avatlantik.asmp.db.DbContract.AnimalTypeContract;
import static com.avatlantik.asmp.db.DbContract.BreedContract;
import static com.avatlantik.asmp.db.DbContract.ChangingContrack;
import static com.avatlantik.asmp.db.DbContract.ConformityServiceToGroupContrack;
import static com.avatlantik.asmp.db.DbContract.FarrowingCycleContract;
import static com.avatlantik.asmp.db.DbContract.HertTypeContract;
import static com.avatlantik.asmp.db.DbContract.HousingContract;
import static com.avatlantik.asmp.db.DbContract.ImageContract;
import static com.avatlantik.asmp.db.DbContract.ServiceContract;
import static com.avatlantik.asmp.db.DbContract.ServiceDoneContract;
import static com.avatlantik.asmp.db.DbContract.ServiceDoneVetExerciseContract;
import static com.avatlantik.asmp.db.DbContract.ServiceToAnimalTypeContract;
import static com.avatlantik.asmp.db.DbContract.UserSettingsContract;
import static com.avatlantik.asmp.db.DbContract.UsersServerContract;
import static com.avatlantik.asmp.db.DbContract.VeterinaryExerciseContract;
import static com.avatlantik.asmp.db.DbContract.VeterinaryPrepatratContract;

public class DataContentProvider extends ContentProvider{

    private AsmpDb asmpDb;
    private final UriMatcher URI_MATCHER;

    private static final int USER_SETTING_LIST = 1;
    private static final int USER_SETTING_ID = 2;
    private static final int SERVICE_LIST = 3;
    private static final int SERVICE_ID = 4;
    private static final int ANIMAL_LIST = 5;
    private static final int ANIMAL_ID = 6;
    private static final int SERVICE_DONE_LIST = 7;
    private static final int SERVICE_DONE_ID = 8;
    private static final int ANIMAL_HISTORY_LIST = 9;
    private static final int ANIMAL_HISTORY_ID = 10;
    private static final int ANIMAL_TYPE_LIST = 11;
    private static final int ANIMAL_TYPE_ID = 12;
    private static final int HOUSTING_TYPE_LIST = 13;
    private static final int HOUSTING_TYPE_ID = 14;
    private static final int FARROWING_CYCLE_LIST = 15;
    private static final int FARROWING_CYCLE_ID = 16;
    private static final int USERS_SERVER_LIST = 17;
    private static final int USERS_SERVER_ID = 18;
    private static final int CHANGING_LIST = 19;
    private static final int CHANGING_ID = 20;
    private static final int CONFORM_SERVICE_GROUP_LIST = 21;
    private static final int CONFORM_SERVICE_GROUP_ID = 22;
    private static final int BREED_LIST = 23;
    private static final int BREED_ID = 24;
    private static final int ANIMAL_STATUS_LIST = 25;
    private static final int ANIMAL_STATUS_ID = 26;
    private static final int ANIMAL_DISPOS_LIST = 27;
    private static final int ANIMAL_DISPOS_ID = 28;
    private static final int HERT_TYPE_LIST = 29;
    private static final int HERT_TYPE_ID = 30;
    private static final int SERVICE_ANIMAL_TYPE_LIST = 31;
    private static final int SERVICE_ANIMAL_TYPE_ID = 32;
    private static final int VET_EXERCISE_LIST = 33;
    private static final int VET_EXERCISE_ID = 34;
    private static final int VET_PREPARAT_LIST = 35;
    private static final int VET_PREPARAT_ID = 36;
    private static final int SERVICE_VET_EXERCISE_LIST = 37;
    private static final int SERVICE_VET_EXERCISE_ID = 38;
    private static final int IMAGE_LIST = 39;
    private static final int IMAGE_ID = 40;


    public DataContentProvider() {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(AUTHORITY, UserSettingsContract.TABLE_NAME, USER_SETTING_LIST);
        URI_MATCHER.addURI(AUTHORITY, UserSettingsContract.TABLE_NAME + "/#", USER_SETTING_ID);
        URI_MATCHER.addURI(AUTHORITY, ServiceContract.TABLE_NAME, SERVICE_LIST);
        URI_MATCHER.addURI(AUTHORITY, ServiceContract.TABLE_NAME + "/#", SERVICE_ID);
        URI_MATCHER.addURI(AUTHORITY, AnimalContract.TABLE_NAME, ANIMAL_LIST);
        URI_MATCHER.addURI(AUTHORITY, AnimalContract.TABLE_NAME + "/#", ANIMAL_ID);
        URI_MATCHER.addURI(AUTHORITY, ServiceDoneContract.TABLE_NAME, SERVICE_DONE_LIST);
        URI_MATCHER.addURI(AUTHORITY, ServiceDoneContract.TABLE_NAME + "/#", SERVICE_DONE_ID);
        URI_MATCHER.addURI(AUTHORITY, AnimalHistoryContract.TABLE_NAME, ANIMAL_HISTORY_LIST);
        URI_MATCHER.addURI(AUTHORITY, AnimalHistoryContract.TABLE_NAME + "/#", ANIMAL_HISTORY_ID);
        URI_MATCHER.addURI(AUTHORITY, AnimalTypeContract.TABLE_NAME, ANIMAL_TYPE_LIST);
        URI_MATCHER.addURI(AUTHORITY, AnimalTypeContract.TABLE_NAME + "/#", ANIMAL_TYPE_ID);
        URI_MATCHER.addURI(AUTHORITY, HousingContract.TABLE_NAME, HOUSTING_TYPE_LIST);
        URI_MATCHER.addURI(AUTHORITY, HousingContract.TABLE_NAME + "/#", HOUSTING_TYPE_ID);
        URI_MATCHER.addURI(AUTHORITY, FarrowingCycleContract.TABLE_NAME, FARROWING_CYCLE_LIST);
        URI_MATCHER.addURI(AUTHORITY, FarrowingCycleContract.TABLE_NAME + "/#", FARROWING_CYCLE_ID);
        URI_MATCHER.addURI(AUTHORITY, UsersServerContract.TABLE_NAME, USERS_SERVER_LIST);
        URI_MATCHER.addURI(AUTHORITY, UsersServerContract.TABLE_NAME + "/#", USERS_SERVER_ID);
        URI_MATCHER.addURI(AUTHORITY, ChangingContrack.TABLE_NAME, CHANGING_LIST);
        URI_MATCHER.addURI(AUTHORITY, ChangingContrack.TABLE_NAME + "/#", CHANGING_ID);
        URI_MATCHER.addURI(AUTHORITY, ConformityServiceToGroupContrack.TABLE_NAME, CONFORM_SERVICE_GROUP_LIST);
        URI_MATCHER.addURI(AUTHORITY, ConformityServiceToGroupContrack.TABLE_NAME + "/#", CONFORM_SERVICE_GROUP_ID);
        URI_MATCHER.addURI(AUTHORITY, BreedContract.TABLE_NAME, BREED_LIST);
        URI_MATCHER.addURI(AUTHORITY, BreedContract.TABLE_NAME + "/#", BREED_ID);
        URI_MATCHER.addURI(AUTHORITY, AnimalStatusContract.TABLE_NAME, ANIMAL_STATUS_LIST);
        URI_MATCHER.addURI(AUTHORITY, AnimalStatusContract.TABLE_NAME + "/#", ANIMAL_STATUS_ID);
        URI_MATCHER.addURI(AUTHORITY, AnimalDisposContract.TABLE_NAME, ANIMAL_DISPOS_LIST);
        URI_MATCHER.addURI(AUTHORITY, AnimalDisposContract.TABLE_NAME + "/#", ANIMAL_DISPOS_ID);
        URI_MATCHER.addURI(AUTHORITY, HertTypeContract.TABLE_NAME, HERT_TYPE_LIST);
        URI_MATCHER.addURI(AUTHORITY, HertTypeContract.TABLE_NAME + "/#", HERT_TYPE_ID);
        URI_MATCHER.addURI(AUTHORITY, ServiceToAnimalTypeContract.TABLE_NAME, SERVICE_ANIMAL_TYPE_LIST);
        URI_MATCHER.addURI(AUTHORITY, ServiceToAnimalTypeContract.TABLE_NAME + "/#", SERVICE_ANIMAL_TYPE_ID);
        URI_MATCHER.addURI(AUTHORITY, VeterinaryExerciseContract.TABLE_NAME, VET_EXERCISE_LIST);
        URI_MATCHER.addURI(AUTHORITY, VeterinaryExerciseContract.TABLE_NAME + "/#", VET_EXERCISE_ID);
        URI_MATCHER.addURI(AUTHORITY, VeterinaryPrepatratContract.TABLE_NAME, VET_PREPARAT_LIST);
        URI_MATCHER.addURI(AUTHORITY, VeterinaryPrepatratContract.TABLE_NAME + "/#", VET_PREPARAT_ID);
        URI_MATCHER.addURI(AUTHORITY, ServiceDoneVetExerciseContract.TABLE_NAME, SERVICE_VET_EXERCISE_LIST);
        URI_MATCHER.addURI(AUTHORITY, ServiceDoneVetExerciseContract.TABLE_NAME + "/#", SERVICE_VET_EXERCISE_ID);
        URI_MATCHER.addURI(AUTHORITY, ImageContract.TABLE_NAME, IMAGE_LIST);
        URI_MATCHER.addURI(AUTHORITY, ImageContract.TABLE_NAME + "/#", IMAGE_ID);
    }

    @Override
    public boolean onCreate() {
        asmpDb = new AsmpDb(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = asmpDb.getReadableDatabase();
        String table = getTable(uri);
        return db.query(table, projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase db = asmpDb.getWritableDatabase();
        String table = getTable(uri);

        String[] uniqueColumn = getUniqueColumn(uri);
        if (uniqueColumn != null) {
            String selection = buildSelection(uniqueColumn);
            String[] args = buildSelectionArgs(uniqueColumn, values);

            db.update(table, values, selection, args);
        }

        return db.insertWithOnConflict(table, null, values, SQLiteDatabase.CONFLICT_IGNORE) != -1 ? uri : null;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        int numInserted = 0;
        String table = getTable(uri);

        SQLiteDatabase sqlDB = asmpDb.getWritableDatabase();
        sqlDB.beginTransaction();
        try {
            for (ContentValues cv : values) {

                String[] uniqueColumn = getUniqueColumn(uri);
                if (uniqueColumn != null) {
                    String selection = buildSelection(uniqueColumn);
                    String[] args = buildSelectionArgs(uniqueColumn, cv);

                    sqlDB.update(table, cv, selection, args);
                }

                sqlDB.insertWithOnConflict(table, null, cv, SQLiteDatabase.CONFLICT_IGNORE);
            }
            sqlDB.setTransactionSuccessful();
            getContext().getContentResolver().notifyChange(uri, null);
            numInserted = values.length;
        } finally {
            sqlDB.endTransaction();
        }

        return numInserted;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int deleted = 0;

        String table = getTable(uri);
        String[] uniqueColumn = getUniqueColumn(uri);

        SQLiteDatabase sqlDB = asmpDb.getWritableDatabase();
        sqlDB.beginTransaction();
        try {
            if (uniqueColumn != null) {
                deleted = sqlDB.delete(table, selection, selectionArgs);
            }

            sqlDB.setTransactionSuccessful();
            getContext().getContentResolver().notifyChange(uri, null);
            return deleted;
        } finally {
            sqlDB.endTransaction();
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int updated = 0;

        String table = getTable(uri);
        String[] uniqueColumn = getUniqueColumn(uri);

        SQLiteDatabase sqlDB = asmpDb.getWritableDatabase();
        sqlDB.beginTransaction();
        try {
            if (uniqueColumn != null) {
                updated = sqlDB.update(table, values, selection, selectionArgs);
            }

            sqlDB.setTransactionSuccessful();
            getContext().getContentResolver().notifyChange(uri, null);
            return updated;
        } finally {
            sqlDB.endTransaction();
        }
    }

    private String[] buildSelectionArgs(String[] uniqueColumn, ContentValues values) {
        List<String> args = new ArrayList<>();
        for (String str : uniqueColumn) {
            args.add(values.getAsString(str));
        }

        return args.toArray(new String[args.size()]);
    }

    private String buildSelection(String[] uniqueColumn) {
        StringBuilder builder = new StringBuilder();
        for (String column : uniqueColumn) {
            builder.append(column).append("=? AND ");
        }

        return builder.substring(0, builder.lastIndexOf(" ") - 3);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (URI_MATCHER.match(uri)) {
            case USER_SETTING_LIST:
                return UserSettingsContract.CONTENT_TYPE;
            case USER_SETTING_ID:
                return UserSettingsContract.CONTENT_ITEM_TYPE;
            case SERVICE_LIST:
                return ServiceContract.CONTENT_TYPE;
            case SERVICE_ID:
                return ServiceContract.CONTENT_ITEM_TYPE;
            case ANIMAL_LIST:
                return AnimalContract.CONTENT_TYPE;
            case ANIMAL_ID:
                return AnimalContract.CONTENT_ITEM_TYPE;
            case SERVICE_DONE_LIST:
                return ServiceDoneContract.CONTENT_TYPE;
            case SERVICE_DONE_ID:
                return ServiceDoneContract.CONTENT_ITEM_TYPE;
            case ANIMAL_HISTORY_LIST:
                return AnimalHistoryContract.CONTENT_TYPE;
            case ANIMAL_HISTORY_ID:
                return AnimalHistoryContract.CONTENT_ITEM_TYPE;
            case ANIMAL_TYPE_LIST:
                return AnimalTypeContract.CONTENT_TYPE;
            case ANIMAL_TYPE_ID:
                return AnimalTypeContract.CONTENT_ITEM_TYPE;
            case HOUSTING_TYPE_LIST:
                return HousingContract.CONTENT_TYPE;
            case HOUSTING_TYPE_ID:
                return HousingContract.CONTENT_ITEM_TYPE;
            case FARROWING_CYCLE_LIST:
                return FarrowingCycleContract.CONTENT_TYPE;
            case FARROWING_CYCLE_ID:
                return FarrowingCycleContract.CONTENT_ITEM_TYPE;
            case USERS_SERVER_LIST:
                return UsersServerContract.CONTENT_TYPE;
            case USERS_SERVER_ID:
                return UsersServerContract.CONTENT_ITEM_TYPE;
            case CHANGING_LIST:
                return ChangingContrack.CONTENT_TYPE;
            case CHANGING_ID:
                return ChangingContrack.CONTENT_ITEM_TYPE;
            case CONFORM_SERVICE_GROUP_LIST:
                return ConformityServiceToGroupContrack.CONTENT_TYPE;
            case CONFORM_SERVICE_GROUP_ID:
                return ConformityServiceToGroupContrack.CONTENT_ITEM_TYPE;
            case BREED_LIST:
                return BreedContract.CONTENT_TYPE;
            case BREED_ID:
                return BreedContract.CONTENT_ITEM_TYPE;
            case ANIMAL_STATUS_LIST:
                return AnimalStatusContract.CONTENT_TYPE;
            case ANIMAL_STATUS_ID:
                return AnimalStatusContract.CONTENT_ITEM_TYPE;
            case ANIMAL_DISPOS_LIST:
                return AnimalDisposContract.CONTENT_TYPE;
            case ANIMAL_DISPOS_ID:
                return AnimalDisposContract.CONTENT_ITEM_TYPE;
            case HERT_TYPE_LIST:
                return HertTypeContract.CONTENT_TYPE;
            case HERT_TYPE_ID:
                return HertTypeContract.CONTENT_ITEM_TYPE;
            case SERVICE_ANIMAL_TYPE_LIST:
                return ServiceToAnimalTypeContract.CONTENT_TYPE;
            case SERVICE_ANIMAL_TYPE_ID:
                return ServiceToAnimalTypeContract.CONTENT_ITEM_TYPE;
            case VET_EXERCISE_LIST:
                return VeterinaryExerciseContract.CONTENT_TYPE;
            case VET_EXERCISE_ID:
                return VeterinaryExerciseContract.CONTENT_ITEM_TYPE;
            case VET_PREPARAT_LIST:
                return VeterinaryPrepatratContract.CONTENT_TYPE;
            case VET_PREPARAT_ID:
                return VeterinaryPrepatratContract.CONTENT_ITEM_TYPE;
            case SERVICE_VET_EXERCISE_LIST:
                return ServiceDoneVetExerciseContract.CONTENT_TYPE;
            case SERVICE_VET_EXERCISE_ID:
                return ServiceDoneVetExerciseContract.CONTENT_ITEM_TYPE;
            case IMAGE_LIST:
                return ImageContract.CONTENT_TYPE;
            case IMAGE_ID:
                return ImageContract.CONTENT_ITEM_TYPE;
            default:
                throw new RuntimeException("Cannot identify uri " + uri.toString());
        }
    }

    private String getTable(Uri uri) {
        switch (URI_MATCHER.match(uri)) {
            case USER_SETTING_LIST:
            case USER_SETTING_ID:
                return UserSettingsContract.TABLE_NAME;
            case SERVICE_LIST:
            case SERVICE_ID:
                return ServiceContract.TABLE_NAME;
            case ANIMAL_LIST:
            case ANIMAL_ID:
                return AnimalContract.TABLE_NAME;
            case SERVICE_DONE_LIST:
            case SERVICE_DONE_ID:
                return ServiceDoneContract.TABLE_NAME;
            case ANIMAL_HISTORY_LIST:
            case ANIMAL_HISTORY_ID:
                return AnimalHistoryContract.TABLE_NAME;
            case ANIMAL_TYPE_LIST:
            case ANIMAL_TYPE_ID:
                return AnimalTypeContract.TABLE_NAME;
            case HOUSTING_TYPE_LIST:
            case HOUSTING_TYPE_ID:
                return HousingContract.TABLE_NAME;
            case FARROWING_CYCLE_LIST:
            case FARROWING_CYCLE_ID:
                return FarrowingCycleContract.TABLE_NAME;
            case USERS_SERVER_LIST:
            case USERS_SERVER_ID:
                return UsersServerContract.TABLE_NAME;
            case CHANGING_ID:
            case CHANGING_LIST:
                return ChangingContrack.TABLE_NAME;
            case CONFORM_SERVICE_GROUP_ID:
            case CONFORM_SERVICE_GROUP_LIST:
                return ConformityServiceToGroupContrack.TABLE_NAME;
            case BREED_ID:
            case BREED_LIST:
                return BreedContract.TABLE_NAME;
            case ANIMAL_STATUS_ID:
            case ANIMAL_STATUS_LIST:
                return AnimalStatusContract.TABLE_NAME;
            case ANIMAL_DISPOS_ID:
            case ANIMAL_DISPOS_LIST:
                return AnimalDisposContract.TABLE_NAME;
            case HERT_TYPE_ID:
            case HERT_TYPE_LIST:
                return HertTypeContract.TABLE_NAME;
            case SERVICE_ANIMAL_TYPE_ID:
            case SERVICE_ANIMAL_TYPE_LIST:
                return ServiceToAnimalTypeContract.TABLE_NAME;
            case VET_EXERCISE_ID:
            case VET_EXERCISE_LIST:
                return VeterinaryExerciseContract.TABLE_NAME;
            case VET_PREPARAT_ID:
            case VET_PREPARAT_LIST:
                return VeterinaryPrepatratContract.TABLE_NAME;
            case SERVICE_VET_EXERCISE_ID:
            case SERVICE_VET_EXERCISE_LIST:
                return ServiceDoneVetExerciseContract.TABLE_NAME;
            case IMAGE_ID:
            case IMAGE_LIST:
                return ImageContract.TABLE_NAME;
            default:
                throw new RuntimeException("Cannot identify uri " + uri.toString());
        }
    }

    private String[] getUniqueColumn(Uri uri) {
        switch (URI_MATCHER.match(uri)) {
            case USER_SETTING_LIST:
            case USER_SETTING_ID:
                return UserSettingsContract.UNIQUE_COLUMNS;
            case SERVICE_LIST:
            case SERVICE_ID:
                return ServiceContract.UNIQUE_COLUMNS;
            case ANIMAL_LIST:
            case ANIMAL_ID:
                return AnimalContract.UNIQUE_COLUMNS;
            case SERVICE_DONE_LIST:
            case SERVICE_DONE_ID:
                return ServiceDoneContract.UNIQUE_COLUMNS;
            case ANIMAL_HISTORY_LIST:
            case ANIMAL_HISTORY_ID:
                return AnimalHistoryContract.UNIQUE_COLUMNS;
            case ANIMAL_TYPE_LIST:
            case ANIMAL_TYPE_ID:
                return AnimalTypeContract.UNIQUE_COLUMNS;
            case HOUSTING_TYPE_LIST:
            case HOUSTING_TYPE_ID:
                return HousingContract.UNIQUE_COLUMNS;
            case FARROWING_CYCLE_LIST:
            case FARROWING_CYCLE_ID:
                return FarrowingCycleContract.UNIQUE_COLUMNS;
            case USERS_SERVER_LIST:
            case USERS_SERVER_ID:
                return UsersServerContract.UNIQUE_COLUMNS;
            case CHANGING_ID:
            case CHANGING_LIST:
                return ChangingContrack.UNIQUE_COLUMNS;
            case CONFORM_SERVICE_GROUP_ID:
            case CONFORM_SERVICE_GROUP_LIST:
                return ConformityServiceToGroupContrack.UNIQUE_COLUMNS;
            case BREED_ID:
            case BREED_LIST:
                return BreedContract.UNIQUE_COLUMNS;
            case ANIMAL_STATUS_ID:
            case ANIMAL_STATUS_LIST:
                return AnimalStatusContract.UNIQUE_COLUMNS;
            case ANIMAL_DISPOS_ID:
            case ANIMAL_DISPOS_LIST:
                return AnimalDisposContract.UNIQUE_COLUMNS;
            case HERT_TYPE_ID:
            case HERT_TYPE_LIST:
                return HertTypeContract.UNIQUE_COLUMNS;
            case SERVICE_ANIMAL_TYPE_ID:
            case SERVICE_ANIMAL_TYPE_LIST:
                return ServiceToAnimalTypeContract.UNIQUE_COLUMNS;
            case VET_EXERCISE_ID:
            case VET_EXERCISE_LIST:
                return VeterinaryExerciseContract.UNIQUE_COLUMNS;
            case VET_PREPARAT_ID:
            case VET_PREPARAT_LIST:
                return VeterinaryPrepatratContract.UNIQUE_COLUMNS;
            case SERVICE_VET_EXERCISE_ID:
            case SERVICE_VET_EXERCISE_LIST:
                return ServiceDoneVetExerciseContract.UNIQUE_COLUMNS;
            case IMAGE_ID:
            case IMAGE_LIST:
                return ImageContract.UNIQUE_COLUMNS;
            default:
                throw new RuntimeException("Cannot identify uri " + uri.toString());
        }
    }
}
