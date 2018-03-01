package com.avatlantik.asmp.activity;


import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.avatlantik.asmp.R;
import com.avatlantik.asmp.app.ASMPApplication;
import com.avatlantik.asmp.db.DbContract;
import com.avatlantik.asmp.model.PhotoItem;
import com.avatlantik.asmp.model.db.Animal;
import com.avatlantik.asmp.model.db.AnimalStatus;
import com.avatlantik.asmp.model.db.AnimalType;
import com.avatlantik.asmp.model.db.Breed;
import com.avatlantik.asmp.model.db.HertType;
import com.avatlantik.asmp.repository.DataRepository;
import com.avatlantik.asmp.utils.ActivityUtils;
import com.avatlantik.asmp.utils.PhotoFIleUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;

import org.joda.time.LocalDateTime;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import static com.avatlantik.asmp.activity.ImageActivity.IMAGE_FULL_NAME;
import static com.avatlantik.asmp.activity.ImageActivity.IMAGE_NAME;
import static com.avatlantik.asmp.activity.ServiceActivity.ID_ANIMAL_ELEMENT_FOR_SERVICE;
import static com.avatlantik.asmp.common.Consts.CLEAR_GUID;
import static com.avatlantik.asmp.common.Consts.NAME_TYPEFILE_PHOTO;
import static com.avatlantik.asmp.common.Consts.TAGLOG_IMAGE;
import static com.avatlantik.asmp.common.Consts.TYPE_GROUP_ANIMAL_ALL;
import static com.avatlantik.asmp.common.Consts.TYPE_GROUP_ANIMAL_BOAR;
import static com.avatlantik.asmp.common.Consts.TYPE_GROUP_ANIMAL_SOW;
import static com.avatlantik.asmp.common.Consts.USE_EXTERNAL_STORAGE;

public class NewAnimalActivity extends AppCompatActivity{

    private static final int CAPTURE_CAMERA_ACTIVITY_REQ = 1005;
    public static final String ANIMAL_ACTIVITY_PARAM_RFID_ID = "animal_activity_param_rfid_id";
    public static final String ANIMAL_ACTIVITY_PARAM_TYPE_ANIMAL = "animal_activity_param_type_animal";
    public static final String ID_SELECTED_ELEMENT_FOR_ANIMAL = "id_selected_element_for_animal";
    public static final String ID_GROUP_ELEMENT_FOR_ANIMAL = "id_selected_group_for_animal";
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");



    @Inject
    DataRepository dataRepository;
    @Inject
    ActivityUtils activityUtils;
    @Inject
    PhotoFIleUtils photoFileUtils;

    private CollapsingToolbarLayout collapsingToolbar;
    private ImageView imageView;
    private Spinner mSpinner;
    private TextView rfidTV, codeTV, codeAddTV, nameTV, nameGroupeTV, animalNumberTV,
            dateRecTV;
    private EditText breedTV, herdTypeTV, statusTV;
    private LinearLayout nameLL, groupNameLL, codeLL;
    private AnimalType selectedAnimalType;
    private List<String> listSearchSettings = new ArrayList<String>();
    private List<AnimalType> animalTypeList;
    private String rfidCode;
    private Date dateRec;
    private Breed breed;
    private HertType hertType;
    private AnimalStatus status;
    private boolean isGroupe, inited, initedWindow;
    private Uri path = null;
    private File pathPictureDir;
    private PhotoItem selectedPhoto;
    private String externalId;
    private int typeAnimal;
    private boolean useExternalStorage;


    private SlideDateTimeListener dateTimeListener = new SlideDateTimeListener() {
        @Override
        public void onDateTimeSet(Date date) {
            dateRec = date;
            dateRecTV.setText(dateFormatter.format(date));
            initedWindow=false;
        }

        @Override
        public void onDateTimeCancel() {
            initedWindow=false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_new);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        ((ASMPApplication) getApplication()).getComponent().inject(this);

        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        imageView = (ImageView) findViewById(R.id.backdrop);

        loadBackdrop();

        ImageView selectedImage = (ImageView)findViewById(R.id.backdrop);
        selectedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedPhoto ==null){
                    createPhoto();
                }else {
                    Intent intent = new Intent(NewAnimalActivity.this, ImageActivity.class);
                    intent.putExtra(IMAGE_NAME, selectedPhoto.getTitle());
                    intent.putExtra(IMAGE_FULL_NAME, selectedPhoto.getExternalId());
                    startActivity(intent);
                }
            }
        });

        mSpinner = (Spinner) findViewById(R.id.animal_gruoup_spinner);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedAnimalType = animalTypeList.get(position);
                TextInputLayout statusTIL = (TextInputLayout) findViewById(R.id.animal_status_til);
                statusTIL.setVisibility(selectedAnimalType.getTypeAnimal() == TYPE_GROUP_ANIMAL_SOW ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dateRec = LocalDateTime.now().toDate();

        rfidTV = (TextView)findViewById(R.id.animal_rfid);
        codeTV = (TextView)findViewById(R.id.animal_code_animal);
        codeAddTV = (TextView)findViewById(R.id.animal_add_code_animal);
        codeLL = (LinearLayout) findViewById(R.id.animal_code_ll);
        nameLL = (LinearLayout) findViewById(R.id.animal_name_ll);
        nameTV = (TextView)findViewById(R.id.animal_name_animal);
        groupNameLL = (LinearLayout) findViewById(R.id.animal_group_name_ll);
        nameGroupeTV = (TextView)findViewById(R.id.animal_name_group);
        animalNumberTV = (TextView)findViewById(R.id.animal_group_number);
        dateRecTV = (TextView)findViewById(R.id.animal_date_receipt);
        dateRecTV.setText(dateFormatter.format(dateRec));
        dateRecTV.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Date date = new Date();
                if(dateRec!=null){
                    date = dateRec;
                }

                if(!initedWindow) {
                    new SlideDateTimePicker.Builder(getSupportFragmentManager())
                            .setListener(dateTimeListener)
                            .setInitialDate(date)
                            .setIs24HourTime(true)
                            .setIndicatorColor(getResources().getColor(R.color.colorPrimary))
                            .setTheme(SlideDateTimePicker.HOLO_LIGHT)
                            .build()
                            .show();
                    initedWindow = true;
                }
                return true;
            }
        });

        breedTV = (EditText) findViewById(R.id.animal_breed);
        breedTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final List<Breed> breedList = dataRepository.getBreedList();

                List<String> breedListNew = new ArrayList<String>();

                for (Breed breed : breedList) {
                    breedListNew.add(breed.getName());
                }

                activityUtils.showSelectionList(NewAnimalActivity.this,
                        getString(R.string.select_from_the_list), breedListNew,
                        new ActivityUtils.ListItemClick() {
                            @Override
                            public void onItemClik(int item, String text) {

                                breed = breedList.get(item);

                                breedTV.setText(breed.getName());

                            }
                        });
            }
        });

        herdTypeTV = (EditText) findViewById(R.id.animal_herd);
        herdTypeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final List<HertType> hertTypeList = dataRepository.getHertTypeList();

                List<String> hertTypeListNew = new ArrayList<String>();

                for (HertType hertType : hertTypeList) {
                    hertTypeListNew.add(hertType.getName());
                }

                activityUtils.showSelectionList(NewAnimalActivity.this,
                        getString(R.string.select_from_the_list), hertTypeListNew,
                        new ActivityUtils.ListItemClick() {
                            @Override
                            public void onItemClik(int item, String text) {

                                hertType = hertTypeList.get(item);

                                herdTypeTV.setText(hertType.getName());

                            }
                        });
            }
        });

        statusTV = (EditText) findViewById(R.id.animal_status_et);
        statusTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final List<AnimalStatus> animalStatusList = dataRepository.getAnimalStatusList();

                List<String> animalStatusListNew = new ArrayList<String>();

                for (AnimalStatus animalStatus : animalStatusList) {
                    animalStatusListNew.add(animalStatus.getName());
                }

                activityUtils.showSelectionList(NewAnimalActivity.this,
                        getString(R.string.select_from_the_list), animalStatusListNew,
                        new ActivityUtils.ListItemClick() {
                            @Override
                            public void onItemClik(int item, String text) {

                                status = animalStatusList.get(item);

                                statusTV.setText(status.getName());

                            }
                        });
            }
        });

        FloatingActionButton addPhotoFab = (FloatingActionButton) findViewById(R.id.add_photo);
        addPhotoFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createPhoto();

            }

        });

        useExternalStorage = Boolean.valueOf(dataRepository.getUserSetting(USE_EXTERNAL_STORAGE));
        pathPictureDir = photoFileUtils.getPictureDir(useExternalStorage);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
            saveInDB();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        activityUtils.showQuestion(NewAnimalActivity.this, getString(R.string.questions_title_info),
                getString(R.string.questions_data_save),
                new ActivityUtils.QuestionAnswer() {
                    @Override
                    public void onPositiveAnsver() {
                        saveInDB();
                        onSuperBackPressed();
                    }

                    @Override
                    public void onNegativeAnsver() {
                        onSuperBackPressed();
                        if(selectedPhoto!=null){
                            photoFileUtils.deleteFile(useExternalStorage, selectedPhoto.getExternalId());
                        }
                    }

                    @Override
                    public void onNeutralAnsver() {
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            rfidCode = extras.getString(ANIMAL_ACTIVITY_PARAM_RFID_ID);
            typeAnimal = extras.getInt(ANIMAL_ACTIVITY_PARAM_TYPE_ANIMAL);
        }else{
            Toast.makeText(getApplicationContext(),
                    getResources().getString(R.string.animal_no_rfid),
                    Toast.LENGTH_SHORT).show();
        }

        animalTypeList = dataRepository.getAnimalTypeList();

        if (animalTypeList.size() == 0) {
            Toast.makeText(getApplicationContext(),
                    getResources().getString(R.string.animal_no_rfid),
                    Toast.LENGTH_SHORT).show();
            finish();
        }


        showDialogSelectGroupe();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String pathPhoto = null;

        initedWindow = false;

        if (requestCode == CAPTURE_CAMERA_ACTIVITY_REQ) {
            if (resultCode == RESULT_OK) {
                Uri photoUri = data == null ? path : data.getData();
                pathPhoto = photoUri.getPath();
                Log.d(TAGLOG_IMAGE, "Image saved successfully to " + pathPhoto);
            } else if (resultCode == RESULT_CANCELED) {
                Log.d(TAGLOG_IMAGE, "Camera Cancelled");
                return;
            }

            if(pathPhoto==null || pathPhoto.isEmpty()){return;}

            final String nameFile = photoFileUtils.getNameFileFromPath(pathPhoto);

            if(selectedPhoto==null){
                saveNewImage(pathPhoto, nameFile);
                loadBackdrop();
                return;
            }

            final String finalPathPhoto = pathPhoto;
            activityUtils.showQuestion(NewAnimalActivity.this, getString(R.string.questions_title_info),
                    getString(R.string.questions_rewrite_photo),
                    new ActivityUtils.QuestionAnswer() {
                        @Override
                        public void onPositiveAnsver() {
                            photoFileUtils.deleteFile(useExternalStorage, selectedPhoto.getExternalId());
                            saveNewImage(finalPathPhoto, nameFile);
                            loadBackdrop();
                        }

                        @Override
                        public void onNegativeAnsver() {
                            if(nameFile!=null&&!nameFile.isEmpty()){
                                if(useExternalStorage){
                                    photoFileUtils.deleteFile(useExternalStorage, nameFile.replace(NAME_TYPEFILE_PHOTO, ""));
                                }else {
                                    photoFileUtils.deleteFileInCameraTemp(nameFile.replace(NAME_TYPEFILE_PHOTO, ""));
                                }
                            }
                        }

                        @Override
                        public void onNeutralAnsver() {}
                    });
        }

    }

    private void saveNewImage(String pathPhoto, String nameFile) {

        if(!useExternalStorage) {
            photoFileUtils.saveImage(false,
                    photoFileUtils.getBitmapFromPathImage(pathPhoto),
                    nameFile.replace(NAME_TYPEFILE_PHOTO, ""));

            photoFileUtils.deleteFileInCameraTemp(nameFile.replace(NAME_TYPEFILE_PHOTO, ""));
        }

        selectedPhoto = new PhotoItem(
                String.valueOf(nameTV.getText()),
                nameFile.replace(NAME_TYPEFILE_PHOTO,""));


    }

    private void initData() {

        collapsingToolbar.setTitle(getResources().getString(R.string.animal_registration)
                +(isGroupe ? getResources().getString(R.string.animal_group) :
                getResources().getString(R.string.animal_name_name)));

        for (AnimalType animalType: animalTypeList){
            listSearchSettings.add(animalType.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.item_with_spinner_white,listSearchSettings);
        adapter.setDropDownViewResource(R.layout.item_with_spinner_white);
        mSpinner.setAdapter(adapter);

        if (typeAnimal < TYPE_GROUP_ANIMAL_ALL) {
            if (animalTypeList.size() > 0) {
                selectedAnimalType = animalTypeList.get(typeAnimal - 1);
            }
            mSpinner.setSelection(typeAnimal - 1);
        }else{
            if (animalTypeList.size() > 0) {
                selectedAnimalType = animalTypeList.get(0);
            }
            mSpinner.setSelection(0);
        }

        rfidTV.setText(getResources().getString(R.string.service_item_rfid_name)+(rfidCode==null || rfidCode.isEmpty() ? "" : rfidCode));
        if(isGroupe) {
            codeLL.setVisibility(View.GONE);
            nameLL.setVisibility(View.GONE);
            groupNameLL.setVisibility(View.VISIBLE);
        }else{
            codeLL.setVisibility(View.VISIBLE);
            nameLL.setVisibility(View.VISIBLE);
            groupNameLL.setVisibility(View.GONE);
        }

    }

    private void loadBackdrop() {

        File photoFile = (selectedPhoto == null ? null : photoFileUtils.getPhotoFile(useExternalStorage, selectedPhoto.getExternalId()));

        Glide.with(this).
                load(photoFile == null ? R.drawable.ic_pig_photo : photoFile)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);


    }

    private void saveInDB(){

        codeTV.setError(null);
        codeAddTV.setError(null);
        nameTV.setError(null);
        //mSpinner.setError(null);

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(codeTV.getText().toString())) {
            codeTV.setError(getString(R.string.error_field_required));
            focusView = codeTV;
            cancel = true;
        }

        if (TextUtils.isEmpty(codeAddTV.getText().toString())) {
            codeAddTV.setError(getString(R.string.error_field_required));
            focusView = codeAddTV;
            cancel = true;
        }

        if (isGroupe && TextUtils.isEmpty(nameTV.getText().toString())) {
            nameTV.setError(getString(R.string.error_field_required));
            focusView = nameTV;
            cancel = true;
        }

        if (!isGroupe &(selectedAnimalType.getTypeAnimal() == TYPE_GROUP_ANIMAL_SOW ||
                selectedAnimalType.getTypeAnimal() == TYPE_GROUP_ANIMAL_BOAR)) {

            if (breed == null) {
                breedTV.setError(getString(R.string.error_field_required));
                focusView = nameTV;
                cancel = true;
            }

            if (hertType == null) {
                herdTypeTV.setError(getString(R.string.error_field_required));
                focusView = nameTV;
                cancel = true;
            }

            if (selectedAnimalType.getTypeAnimal() == TYPE_GROUP_ANIMAL_SOW) {

                if (status == null) {
                    statusTV.setError(getString(R.string.error_field_required));
                    focusView = nameTV;
                    cancel = true;
                }

            }

        }

        if (selectedAnimalType == null) {
            focusView = mSpinner;
            cancel = true;
        }


        if (cancel) {

            focusView.requestFocus();

        } else {

            if(externalId==null || externalId.isEmpty()) {
                externalId = "app-" + UUID.randomUUID().toString();
            }

            String numberAnimals = String.valueOf(animalNumberTV.getText());

            dataRepository.insertAnimal(Animal.builder()
                    .externalId(externalId)
                    .typeAnimal(selectedAnimalType.getTypeAnimal())
                    .rfid(rfidCode == null || rfidCode.isEmpty() ? "" : rfidCode)
                    .code(String.valueOf(codeTV.getText()))
                    .addCode(String.valueOf(codeAddTV.getText()))
                    .name(isGroupe ?String.valueOf(nameGroupeTV.getText()) : String.valueOf(nameTV.getText()))
                    .isGroup(false)
                    .isGroupAnimal(isGroupe)
                    .group(CLEAR_GUID)
                    .number(isGroupe ? Integer.valueOf(numberAnimals == null || numberAnimals.isEmpty() ? "0" : numberAnimals ) : 0)
                    .photo(selectedPhoto == null ? "" : selectedPhoto.getExternalId())
                    .dateRec(dateRec)
                    .status(status==null? null : status.getExternalId())
                    .breed(breed==null? null : breed.getExternalId())
                    .herd(hertType==null? null : hertType.getExternalId())
                    .build());

            Animal animal = dataRepository.getAnimalById(externalId);

            if(animal!=null) {
                dataRepository.insertChangedElement(DbContract.AnimalContract.TABLE_NAME, animal.getExternalId());
            }

            Intent intent = new Intent();
            intent.putExtra(ID_ANIMAL_ELEMENT_FOR_SERVICE, externalId);
            setResult(RESULT_OK, intent);
            finish();
        }

    }
    public void onSuperBackPressed(){
        super.onBackPressed();
    }

    private void showDialogSelectGroupe(){

        if(!inited){
            List<String> serviceList = new ArrayList<String>();
            serviceList.add(getResources().getString(R.string.animal_name_name));
            serviceList.add(getResources().getString(R.string.animal_group));

            activityUtils.showSelectionList(NewAnimalActivity.this,
                    getString(R.string.select_what_you_create), serviceList,
                    new ActivityUtils.ListItemClick() {
                        @Override
                        public void onItemClik(int item, String text) {
                            if(item == 1){
                                isGroupe = true;
                            }else{
                                isGroupe = false;
                            }
                            initData();
                        }
                    });
            inited = true;
        }

    }

    private void createPhoto() {

        path = Uri.fromFile(new File(pathPictureDir, File.separator +
                String.valueOf(UUID.randomUUID().toString()) + NAME_TYPEFILE_PHOTO));
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, path);
        cameraIntent.setClipData(ClipData.newRawUri(null, path));
        cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        cameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        startActivityForResult(cameraIntent, CAPTURE_CAMERA_ACTIVITY_REQ);
    }
}