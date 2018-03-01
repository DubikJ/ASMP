package com.avatlantik.asmp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import com.avatlantik.asmp.db.DbContract.AnimalContract;
import com.avatlantik.asmp.db.DbContract.ServiceContract;
import com.avatlantik.asmp.db.DbContract.ServiceDoneContract;
import com.avatlantik.asmp.db.DbContract.UserSettingsContract;
import com.avatlantik.asmp.db.DbContract.AnimalTypeContract;
import com.avatlantik.asmp.db.DbContract.HousingContract;
import com.avatlantik.asmp.db.DbContract.AnimalHistoryContract;
import com.avatlantik.asmp.db.DbContract.FarrowingCycleContract;
import com.avatlantik.asmp.db.DbContract.UsersServerContract;
import com.avatlantik.asmp.db.DbContract.ChangingContrack;
import com.avatlantik.asmp.db.DbContract.ConformityServiceToGroupContrack;
import com.avatlantik.asmp.db.DbContract.BreedContract;
import com.avatlantik.asmp.db.DbContract.AnimalStatusContract;
import com.avatlantik.asmp.db.DbContract.AnimalDisposContract;
import com.avatlantik.asmp.db.DbContract.HertTypeContract;
import com.avatlantik.asmp.db.DbContract.ServiceToAnimalTypeContract;
import com.avatlantik.asmp.db.DbContract.VeterinaryExerciseContract;
import com.avatlantik.asmp.db.DbContract.VeterinaryPrepatratContract;
import com.avatlantik.asmp.db.DbContract.ServiceDoneVetExerciseContract;
import com.avatlantik.asmp.db.DbContract.ImageContract;


public class AsmpDb extends SQLiteOpenHelper {

    private static final String DB_NAME = "asmp";
    private static final int DB_VERSION = 35;

    public AsmpDb(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + UserSettingsContract.TABLE_NAME + "("
                + UserSettingsContract._ID + " integer primary key AUTOINCREMENT,"
                + UserSettingsContract.USER_SETTING_ID + " text,"
                + UserSettingsContract.SETTING_VALUE + " text,"
                + "UNIQUE (" + TextUtils.join(",", UserSettingsContract.UNIQUE_COLUMNS) + ")"
                + ");");

        db.execSQL("create table " + ServiceContract.TABLE_NAME + "("
                + ServiceContract._ID + " integer primary key AUTOINCREMENT,"
                + ServiceContract.EXTERNAL_ID + " text,"
                + ServiceContract.NAME + " text,"
                + ServiceContract.TYPE_RESULT + " integer,"
                + "UNIQUE (" + TextUtils.join(", ", ServiceContract.UNIQUE_COLUMNS) + ")"
                + ");");

        db.execSQL("create table " + AnimalContract.TABLE_NAME + "("
                + AnimalContract._ID + " integer primary key AUTOINCREMENT,"
                + AnimalContract.EXTERNAL_ID + " text,"
                + AnimalContract.TYPE_ANIMAL + " integer,"
                + AnimalContract.RFID + " text,"
                + AnimalContract.CODE + " text,"
                + AnimalContract.ADD_CODE + " text,"
                + AnimalContract.NAME + " text,"
                + AnimalContract.IS_GROUP + " numeric,"
                + AnimalContract.IS_GROUP_ANIMAL + " numeric,"
                + AnimalContract.GROUP_ID + " text,"
                + AnimalContract.CORPS_ID + " text,"
                + AnimalContract.SECTOR_ID + " text,"
                + AnimalContract.CELL_ID + " text,"
                + AnimalContract.NUMBER + " integer,"
                + AnimalContract.PHOTO + " text,"
                + AnimalContract.DATE_REC + " integer,"
                + AnimalContract.STATUS + " text,"
                + AnimalContract.BREED + " text,"
                + AnimalContract.HERD + " text,"
                + "UNIQUE (" + TextUtils.join(", ", AnimalContract.UNIQUE_COLUMNS) + ")"
                + ");");

        db.execSQL("create table " + ServiceDoneContract.TABLE_NAME + "("
                + ServiceDoneContract._ID + " integer primary key AUTOINCREMENT,"
                + ServiceDoneContract.DATE + " integer,"
                + ServiceDoneContract.DATE_DAY + " text,"
                + ServiceDoneContract.ANIMAL_ID + " text,"
                + ServiceDoneContract.SERVICE_ID + " text,"
                + ServiceDoneContract.ISPLANE + " numeric,"
                + ServiceDoneContract.DONE + " numeric,"
                + ServiceDoneContract.NUMBER + " integer,"
                + ServiceDoneContract.LIVE + " integer,"
                + ServiceDoneContract.NORMAl + " integer,"
                + ServiceDoneContract.WEAK + " integer,"
                + ServiceDoneContract.DEATH + " integer,"
                + ServiceDoneContract.MUMMY + " integer,"
                + ServiceDoneContract.TECN_GROUP_TO + " text,"
                + ServiceDoneContract.BOAR_1 + " text,"
                + ServiceDoneContract.BOAR_2 + " text,"
                + ServiceDoneContract.BOAR_3 + " text,"
                + ServiceDoneContract.WEIGHT + " real,"
                + ServiceDoneContract.NOTE + " text,"
                + ServiceDoneContract.CORP_TO + " text,"
                + ServiceDoneContract.SECTOR_TO + " text,"
                + ServiceDoneContract.CELL_TO + " text,"
                + ServiceDoneContract.RESULT_SERVICE + " text,"
                + ServiceDoneContract.ADM_NUMBER + " integer,"
                + ServiceDoneContract.STATUS + " text,"
                + ServiceDoneContract.DISPOSANIM + " text,"
                + ServiceDoneContract.LENGHT + " integer,"
                + ServiceDoneContract.BREAD + " integer,"
                + ServiceDoneContract.EXTERIOR + " integer,"
                + ServiceDoneContract.DEPTHMYSZ + " integer,"
                + ServiceDoneContract.NEW_CODE + " text,"
                + ServiceDoneContract.ARTIF_INSEMEN + " numeric,"
                + ServiceDoneContract.ANIM_GROUP_TO + " text,"
                + "UNIQUE (" + TextUtils.join(", ", ServiceDoneContract.UNIQUE_COLUMNS) + ")"
                + ");");

        db.execSQL("create table " + AnimalTypeContract.TABLE_NAME + "("
                + AnimalTypeContract._ID + " integer primary key AUTOINCREMENT,"
                + AnimalTypeContract.TYPE_ANIMAL + " integer,"
                + AnimalTypeContract.NAME + " text,"
                + "UNIQUE (" + TextUtils.join(", ", AnimalTypeContract.UNIQUE_COLUMNS) + ")"
                + ");");

        db.execSQL("create table " + HousingContract.TABLE_NAME + "("
                + HousingContract._ID + " integer primary key AUTOINCREMENT,"
                + HousingContract.EXTERNAL_ID + " text,"
                + HousingContract.TYPE + " integer,"
                + HousingContract.NAME + " text,"
                + HousingContract.PARENT_ID + " text,"
                + "UNIQUE (" + TextUtils.join(", ", HousingContract.UNIQUE_COLUMNS) + ")"
                + ");");

        db.execSQL("create table " + AnimalHistoryContract.TABLE_NAME + "("
                + AnimalHistoryContract._ID + " integer primary key AUTOINCREMENT,"
                + AnimalHistoryContract.EXTERNAL_ID + " text,"
                + AnimalHistoryContract.ANIMAL_ID + " text,"
                + AnimalHistoryContract.DATE + " integer,"
                + AnimalHistoryContract.SERVICE_DATA + " text,"
                + AnimalHistoryContract.RESULT + " text,"
                + "UNIQUE (" + TextUtils.join(", ", AnimalHistoryContract.UNIQUE_COLUMNS) + ")"
                + ");");

        db.execSQL("create table " +FarrowingCycleContract.TABLE_NAME + "("
                + FarrowingCycleContract._ID + " integer primary key AUTOINCREMENT,"
                + FarrowingCycleContract.EXTERNAL_ID + " text,"
                + FarrowingCycleContract.ANIMAL_ID + " text,"
                + FarrowingCycleContract.DATE + " integer,"
                + FarrowingCycleContract.SERVICE_DATA + " text,"
                + FarrowingCycleContract.RESULT + " text,"
                + "UNIQUE (" + TextUtils.join(", ", FarrowingCycleContract.UNIQUE_COLUMNS) + ")"
                + ");");

        db.execSQL("create table " + UsersServerContract.TABLE_NAME + "("
                + UsersServerContract._ID + " integer primary key AUTOINCREMENT,"
                + UsersServerContract.NAME + " text,"
                + "UNIQUE (" + TextUtils.join(", ", UsersServerContract.UNIQUE_COLUMNS) + ")"
                + ");");

        db.execSQL("create table " + ChangingContrack.TABLE_NAME + "("
                + ChangingContrack._ID + " integer primary key AUTOINCREMENT,"
                + ChangingContrack.MANE_ELEMENT + " text,"
                + ChangingContrack.ELEMENT_ID + " text,"
                + "UNIQUE (" + TextUtils.join(",", ChangingContrack.UNIQUE_COLUMNS) + ")"
                + ");");

        db.execSQL("create table " + ConformityServiceToGroupContrack.TABLE_NAME + "("
                + ConformityServiceToGroupContrack._ID + " integer primary key AUTOINCREMENT,"
                + ConformityServiceToGroupContrack.SERVICE_ID + " text,"
                + ConformityServiceToGroupContrack.ANIMAL_ID + " text,"
                + ConformityServiceToGroupContrack.TYPE_ANIMAL + " integer,"
                + "UNIQUE (" + TextUtils.join(",", ConformityServiceToGroupContrack.UNIQUE_COLUMNS) + ")"
                + ");");

        db.execSQL("create table " + BreedContract.TABLE_NAME + "("
                + BreedContract._ID + " integer primary key AUTOINCREMENT,"
                + BreedContract.EXTERNAL_ID + " text,"
                + BreedContract.NAME + " text,"
                + "UNIQUE (" + TextUtils.join(", ", BreedContract.UNIQUE_COLUMNS) + ")"
                + ");");

        db.execSQL("create table " + AnimalStatusContract.TABLE_NAME + "("
                + AnimalStatusContract._ID + " integer primary key AUTOINCREMENT,"
                + AnimalStatusContract.EXTERNAL_ID + " text,"
                + AnimalStatusContract.NAME + " text,"
                + "UNIQUE (" + TextUtils.join(", ", AnimalStatusContract.UNIQUE_COLUMNS) + ")"
                + ");");

        db.execSQL("create table " + AnimalDisposContract.TABLE_NAME + "("
                + AnimalDisposContract._ID + " integer primary key AUTOINCREMENT,"
                + AnimalDisposContract.EXTERNAL_ID + " text,"
                + AnimalDisposContract.NAME + " text,"
                + "UNIQUE (" + TextUtils.join(", ", AnimalDisposContract.UNIQUE_COLUMNS) + ")"
                + ");");

        db.execSQL("create table " + HertTypeContract.TABLE_NAME + "("
                + HertTypeContract._ID + " integer primary key AUTOINCREMENT,"
                + HertTypeContract.EXTERNAL_ID + " text,"
                + HertTypeContract.NAME + " text,"
                + "UNIQUE (" + TextUtils.join(", ", HertTypeContract.UNIQUE_COLUMNS) + ")"
                + ");");

        db.execSQL("create table " + ServiceToAnimalTypeContract.TABLE_NAME + "("
                + ServiceToAnimalTypeContract._ID + " integer primary key AUTOINCREMENT,"
                + ServiceToAnimalTypeContract.SERVICE_ID + " text,"
                + ServiceToAnimalTypeContract.TYPE_ANIMAL + " integer,"
                + "UNIQUE (" + TextUtils.join(", ", ServiceToAnimalTypeContract.UNIQUE_COLUMNS) + ")"
                + ");");

        db.execSQL("create table " + VeterinaryExerciseContract.TABLE_NAME + "("
                + VeterinaryExerciseContract._ID + " integer primary key AUTOINCREMENT,"
                + VeterinaryExerciseContract.EXTERNAL_ID + " text,"
                + VeterinaryExerciseContract.NAME + " text,"
                + "UNIQUE (" + TextUtils.join(", ", VeterinaryExerciseContract.UNIQUE_COLUMNS) + ")"
                + ");");

        db.execSQL("create table " + VeterinaryPrepatratContract.TABLE_NAME + "("
                + VeterinaryPrepatratContract._ID + " integer primary key AUTOINCREMENT,"
                + VeterinaryPrepatratContract.EXTERNAL_ID + " text,"
                + VeterinaryPrepatratContract.NAME + " text,"
                + "UNIQUE (" + TextUtils.join(", ", VeterinaryPrepatratContract.UNIQUE_COLUMNS) + ")"
                + ");");

        db.execSQL("create table " + ServiceDoneVetExerciseContract.TABLE_NAME + "("
                + ServiceDoneVetExerciseContract._ID + " integer primary key AUTOINCREMENT,"
                + ServiceDoneVetExerciseContract.ANIMAL_ID + " text,"
                + ServiceDoneVetExerciseContract.EXERCISE_ID + " text,"
                + ServiceDoneVetExerciseContract.PREPARAT_ID + " text,"
                + "UNIQUE (" + TextUtils.join(", ", ServiceDoneVetExerciseContract.UNIQUE_COLUMNS) + ")"
                + ");");

        db.execSQL("create table " + ImageContract.TABLE_NAME + "("
                + ImageContract._ID + " integer primary key AUTOINCREMENT,"
                + ImageContract.EXTERNAL_ID + " text,"
                + ImageContract.IMAGE + " BLOB,"
                + "UNIQUE (" + TextUtils.join(",", ImageContract.UNIQUE_COLUMNS) + ")"
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + UserSettingsContract.TABLE_NAME);
        db.execSQL("drop table if exists " + ServiceContract.TABLE_NAME);
        db.execSQL("drop table if exists " + AnimalContract.TABLE_NAME);
        db.execSQL("drop table if exists " + ServiceDoneContract.TABLE_NAME);
        db.execSQL("drop table if exists " + AnimalTypeContract.TABLE_NAME);
        db.execSQL("drop table if exists " + HousingContract.TABLE_NAME);
        db.execSQL("drop table if exists " + AnimalHistoryContract.TABLE_NAME);
        db.execSQL("drop table if exists " + FarrowingCycleContract.TABLE_NAME);
        db.execSQL("drop table if exists " + UsersServerContract.TABLE_NAME);
        db.execSQL("drop table if exists " + ChangingContrack.TABLE_NAME);
        db.execSQL("drop table if exists " + ConformityServiceToGroupContrack.TABLE_NAME);
        db.execSQL("drop table if exists " + BreedContract.TABLE_NAME);
        db.execSQL("drop table if exists " + AnimalStatusContract.TABLE_NAME);
        db.execSQL("drop table if exists " + AnimalDisposContract.TABLE_NAME);
        db.execSQL("drop table if exists " + HertTypeContract.TABLE_NAME);
        db.execSQL("drop table if exists " + ServiceToAnimalTypeContract.TABLE_NAME);
        db.execSQL("drop table if exists " + VeterinaryExerciseContract.TABLE_NAME);
        db.execSQL("drop table if exists " + VeterinaryPrepatratContract.TABLE_NAME);
        db.execSQL("drop table if exists " + ServiceDoneVetExerciseContract.TABLE_NAME);
        db.execSQL("drop table if exists " + ImageContract.TABLE_NAME);
        onCreate(db);
    }
}
