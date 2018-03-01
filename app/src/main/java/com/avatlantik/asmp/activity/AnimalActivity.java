package com.avatlantik.asmp.activity;


import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.avatlantik.asmp.R;
import com.avatlantik.asmp.app.ASMPApplication;
import com.avatlantik.asmp.db.DbContract;
import com.avatlantik.asmp.fragment.AnimalBasicFragment;
import com.avatlantik.asmp.fragment.AnimalCyclesFarrowingFragment;
import com.avatlantik.asmp.fragment.AnimalHistoryFragment;
import com.avatlantik.asmp.model.PhotoItem;
import com.avatlantik.asmp.model.ServiceDoneListItem;
import com.avatlantik.asmp.model.db.Animal;
import com.avatlantik.asmp.model.db.AnimalDispos;
import com.avatlantik.asmp.model.db.AnimalHistory;
import com.avatlantik.asmp.model.db.AnimalStatus;
import com.avatlantik.asmp.model.db.AnimalType;
import com.avatlantik.asmp.model.db.FarrowingCycle;
import com.avatlantik.asmp.model.db.Housing;
import com.avatlantik.asmp.model.db.ServiceData;
import com.avatlantik.asmp.model.db.ServiceDone;
import com.avatlantik.asmp.model.db.VetExercise;
import com.avatlantik.asmp.model.db.VetPreparat;
import com.avatlantik.asmp.repository.DataRepository;
import com.avatlantik.asmp.ui.ViewModel;
import com.avatlantik.asmp.utils.ActivityUtils;
import com.avatlantik.asmp.utils.PhotoFIleUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import static com.avatlantik.asmp.activity.ImageActivity.IMAGE_FULL_NAME;
import static com.avatlantik.asmp.activity.ImageActivity.IMAGE_NAME;
import static com.avatlantik.asmp.activity.ServiceActivity.CAPTURE_SELECT_GROUP_TO_REQ;
import static com.avatlantik.asmp.activity.TecnikalGroupeAnimalActivity.GROUPE_ANIMAL_ACTIVITY_TYPE_ANIMAL;
import static com.avatlantik.asmp.common.Consts.CLEAR_GUID;
import static com.avatlantik.asmp.common.Consts.NAME_TYPEFILE_PHOTO;
import static com.avatlantik.asmp.common.Consts.TAGLOG_IMAGE;
import static com.avatlantik.asmp.common.Consts.TYPE_GROUP_ANIMAL_SOW;
import static com.avatlantik.asmp.common.Consts.TYPE_RESULT_SERVICE_DISTILLATION;
import static com.avatlantik.asmp.common.Consts.TYPE_RESULT_SERVICE_MOVEMENT;
import static com.avatlantik.asmp.common.Consts.TYPE_RESULT_SERVICE_VETERINARY;
import static com.avatlantik.asmp.common.Consts.USE_EXTERNAL_STORAGE;

public class AnimalActivity extends AppCompatActivity{

    private static final int CAPTURE_CAMERA_ACTIVITY_REQ = 1030;
    public static final String ANIMAL_ACTIVITY_PARAM_ANIMAL_ID = "animal_activity_param_animal_id";

    @Inject
    DataRepository dataRepository;
    @Inject
    ActivityUtils activityUtils;
    @Inject
    PhotoFIleUtils photoFileUtils;

    private ImageView imageView;
    private CollapsingToolbarLayout collapsingToolbar;
    private static TabLayout tabLayout;
    private static ViewPager viewPager;
    private static ViewPagerAdapter viewPagerAdapter;
    private Animal selectedAnimal;
    private Uri path = null;
    private File pathPictureDir;
    private PhotoItem selectedPhoto;
    //private File photoFile;
    private AnimalBasicFragment basicAnimalFragment;
    private boolean useExternalStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Bundle extras = getIntent().getExtras();

        ((ASMPApplication) getApplication()).getComponent().inject(this);

        if (extras != null) {
            String idSelectedAnimal = extras.getString(ANIMAL_ACTIVITY_PARAM_ANIMAL_ID);
            selectedAnimal = dataRepository.getAnimalById(idSelectedAnimal);
        }else{
            Toast.makeText(getApplicationContext(),
                    getResources().getString(R.string.animal_not_found),
                    Toast.LENGTH_LONG).show();
            finish();
        }

        if (selectedAnimal == null) {
            Toast.makeText(getApplicationContext(),
                    getResources().getString(R.string.animal_not_found),
                    Toast.LENGTH_LONG).show();
            finish();
        }
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        imageView = (ImageView) findViewById(R.id.backdrop);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout.setupWithViewPager(viewPager);

        ImageView selectedImage = (ImageView)findViewById(R.id.backdrop);
        selectedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedPhoto ==null){
                    createPhoto();
                }else {
                    Intent intent = new Intent(AnimalActivity.this, ImageActivity.class);
                    intent.putExtra(IMAGE_NAME, selectedPhoto.getTitle());
                    intent.putExtra(IMAGE_FULL_NAME, selectedPhoto.getExternalId());
                    startActivity(intent);
                }
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

        initData();

        loadBackdrop();
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

        activityUtils.showQuestion(AnimalActivity.this, getString(R.string.questions_title_info),
                getString(R.string.questions_data_save),
                new ActivityUtils.QuestionAnswer() {
                    @Override
                    public void onPositiveAnsver() {
                        if(saveInDB()) {
                            onSuperBackPressed();
                        }
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAPTURE_CAMERA_ACTIVITY_REQ) {
            String pathPhoto = null;
            if (resultCode == RESULT_OK) {
                Uri photoUri = data == null ? path : data.getData();
                pathPhoto = photoUri.getPath();
                Log.d(TAGLOG_IMAGE, "Image saved successfully to " + pathPhoto);
            } else if (resultCode == RESULT_CANCELED) {
                Log.d(TAGLOG_IMAGE, "Camera Cancelled");
                return;
            }

            if(pathPhoto==null || pathPhoto.isEmpty()){
                return;
            }

            final String nameFile = photoFileUtils.getNameFileFromPath(pathPhoto);

            if(selectedPhoto == null){
//                selectedPhoto = new PhotoItem(selectedAnimal.getFullNameType(AnimalActivity.this),
//                        pathPictureDir + File.separator + nameFile,
//                        nameFile.replace(NAME_TYPEFILE_PHOTO, ""));
                saveNewImage(pathPhoto, nameFile);

                loadBackdrop();
            }else {
                final String finalPathPhoto = pathPhoto;
                activityUtils.showQuestion(AnimalActivity.this, getString(R.string.questions_title_info),
                        getString(R.string.questions_rewrite_photo),
                        new ActivityUtils.QuestionAnswer() {
                            @Override
                            public void onPositiveAnsver() {

//                                if(selectedPhoto!= null) {
//                                    File photoFile = initPhotoFile(selectedPhoto.getExternalId());
//
//                                    if (photoFile!=null && photoFile.exists()){
//                                        photoFile.delete();
//                                    }
//                                }
//
//                                selectedPhoto = new PhotoItem(selectedAnimal.getFullNameType(AnimalActivity.this),
//                                        pathPictureDir + File.separator + nameFile,
//                                        nameFile.replace(NAME_TYPEFILE_PHOTO, ""));
                                photoFileUtils.deleteFile(useExternalStorage, selectedPhoto.getExternalId());
                                saveNewImage(finalPathPhoto, nameFile);

                                loadBackdrop();

                            }

                            @Override
                            public void onNegativeAnsver() {
//
//                                File photoFile = (nameFile == null ? null :
//                                        initPhotoFile(nameFile.replace(NAME_TYPEFILE_PHOTO, "")));
//
//                                if (photoFile != null) {
//                                    photoFile.delete();
//                                }
                                if(nameFile!=null&&!nameFile.isEmpty()){
                                    if(useExternalStorage){
                                        photoFileUtils.deleteFile(useExternalStorage, nameFile.replace(NAME_TYPEFILE_PHOTO, ""));
                                    }else {
                                        photoFileUtils.deleteFileInCameraTemp(nameFile.replace(NAME_TYPEFILE_PHOTO, ""));
                                    }
                                }
                            }

                            @Override
                            public void onNeutralAnsver() {
                            }
                        });
            }
        }

        basicAnimalFragment.onActivityResult(requestCode, resultCode, data);
    }

    private void saveNewImage(String pathPhoto, String nameFile) {

        if(!useExternalStorage) {
            photoFileUtils.saveImage(false,
                    photoFileUtils.getBitmapFromPathImage(pathPhoto),
                    nameFile.replace(NAME_TYPEFILE_PHOTO, ""));

            photoFileUtils.deleteFileInCameraTemp(nameFile.replace(NAME_TYPEFILE_PHOTO, ""));
        }

        selectedPhoto = new PhotoItem(
                selectedAnimal.getFullNameType(AnimalActivity.this),
                nameFile.replace(NAME_TYPEFILE_PHOTO,""));


    }

    private void initData() {

        collapsingToolbar.setTitle(selectedAnimal.getFullNameType(this));

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        basicAnimalFragment = new AnimalBasicFragment().getInstance();
        viewPagerAdapter.addFrag(basicAnimalFragment, getResources().getString(R.string.animal_card_name));
        if(selectedAnimal.getTypeAnimal() == TYPE_GROUP_ANIMAL_SOW){
            viewPagerAdapter.addFrag(new AnimalCyclesFarrowingFragment().getInstance(), getResources().getString(R.string.animal_card_cycles_farrowing));
        }
        viewPagerAdapter.addFrag(new AnimalHistoryFragment().getInstance(), getResources().getString(R.string.animal_history_name));
        viewPager.setAdapter(viewPagerAdapter);

        if(selectedAnimal.getPhoto()!=null && !selectedAnimal.getPhoto().isEmpty()) {
            selectedPhoto = new PhotoItem(
                    selectedAnimal.getFullNameType(this),
                    selectedAnimal.getPhoto());
        }

    }

//    private File initPhotoFile(String nameFile) {
//        return photoFileUtils.getPhotoFile(nameFile);
//    }

    private void loadBackdrop() {

//        photoFile = selectedPhoto == null ? null : initPhotoFile(selectedPhoto.getExternalId());
//
//        Glide.with(this).load(photoFile == null ? R.drawable.ic_pig_photo : photoFile).centerCrop().into(imageView);

        File photoFile = (selectedPhoto == null ? null : photoFileUtils.getPhotoFile(useExternalStorage, selectedPhoto.getExternalId()));

        Glide.with(this).
                load(photoFile == null ? R.drawable.ic_pig_photo : photoFile)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);
    }

    private Boolean saveInDB(){

        if(selectedPhoto != null) {
            selectedAnimal.setPhoto(selectedPhoto.getExternalId());
        }

        Boolean result = true;

        List<ServiceDoneListItem> listServiceDone = basicAnimalFragment.getListServiceDone();

        for (ServiceDoneListItem serviceDone : listServiceDone) {

            boolean cancel = false;
            View focusView = null;

//            if (serviceDone.getServiceData().getTypeResult() == TYPE_RESULT_SERVICE_MOVEMENT) {
//
//                if (serviceDone.getDone() && serviceDone.getGroupTo() == null) {
//
//                    int position = listServiceDone.indexOf(serviceDone);
//
//                    View viewItem = basicAnimalFragment.getRecyclerView().getLayoutManager().findViewByPosition(position);
//
//                    EditText toGroup = (EditText) viewItem.findViewById(R.id.list_service_done_to_group);
//
//                    toGroup.setError(getString(R.string.error_field_required));
//                    focusView = toGroup;
//                    cancel = true;
//                }
//            }
//
//            if (serviceDone.getServiceData().getTypeResult() == TYPE_RESULT_SERVICE_INSEMINATION) {
//
//                if (serviceDone.getDone() && serviceDone.getBoar1() == null) {
//
//                    int position = listServiceDone.indexOf(serviceDone);
//
//                    View viewItem = basicAnimalFragment.getRecyclerView().getLayoutManager().findViewByPosition(position);
//
//                    EditText boar1 = (EditText) viewItem.findViewById(R.id.list_service_done_boar1);
//
//                    boar1.setError(getString(R.string.error_field_required));
//                    focusView = boar1;
//                    cancel = true;
//                }
//            }


            if (cancel) {

                focusView.requestFocus();

                result = false;

            } else {

                dataRepository.insertServiceDone(ServiceDone.builder()
                        .date(serviceDone.getDate())
                        .dateDay(serviceDone.getDate())
                        .animalId(selectedAnimal.getExternalId())
                        .serviceId(serviceDone.getServiceId())
                        .isPlane(serviceDone.getPlane())
                        .done(serviceDone.getDone())
                        .note(serviceDone.getNote())
                        .number(serviceDone.getNumber())
                        .live(serviceDone.getLive())
                        .normal(serviceDone.getNormal())
                        .weak(serviceDone.getWeak())
                        .death(serviceDone.getDeath())
                        .mummy(serviceDone.getMummy())
                        .tecnGroupTo(serviceDone.getTecnGroupToA() == null ? null : serviceDone.getTecnGroupToA().getExternalId())
                        .weight(serviceDone.getWeight())
                        .boar1(serviceDone.getBoar1A() == null ? null : serviceDone.getBoar1A().getExternalId())
                        .boar2(serviceDone.getBoar2A() == null ? null : serviceDone.getBoar2A().getExternalId())
                        .boar3(serviceDone.getBoar3A() == null ? null : serviceDone.getBoar3A().getExternalId())
                        .note(serviceDone.getNote())
                        .corpTo(serviceDone.getCorpToH() == null ? null : serviceDone.getCorpToH().getExternalId())
                        .sectorTo(serviceDone.getSectorToH() == null ? null : serviceDone.getSectorToH().getExternalId())
                        .cellTo(serviceDone.getCellToH() == null ? null : serviceDone.getCellToH().getExternalId())
                        .resultService(serviceDone.getResultServiceS() == null ? null : serviceDone.getResultServiceS().getExternalId())
                        .admNumber(serviceDone.getAdmNumber())
                        .status(serviceDone.getStatusA() == null ? null : serviceDone.getStatusA().getExternalId())
                        .disposAnim(serviceDone.getDisposAnimA() == null ? null : serviceDone.getDisposAnimA().getExternalId())
                        .length(serviceDone.getLength())
                        .bread(serviceDone.getBread())
                        .exterior(serviceDone.getExterior())
                        .depthMysz(serviceDone.getDepthMysz())
                        .newCode(serviceDone.getNewCode())
                        .artifInsemen(serviceDone.isArtifInsemen())
                        .animalGroupTo(serviceDone.getAnimalGroupToA() == null ? null : serviceDone.getAnimalGroupToA().getExternalId())
                        .build());

                ServiceDone selectedSD = dataRepository.getServiceDoneByServiceIdAnimalId(serviceDone.getServiceId(), selectedAnimal.getExternalId());
                if(selectedSD!=null) {
                    dataRepository.insertChangedElement(DbContract.ServiceDoneContract.TABLE_NAME,
                            String.valueOf(selectedSD.getId()));
                }

                if (serviceDone.getServiceData().getTypeResult() == TYPE_RESULT_SERVICE_MOVEMENT) {
                    Animal group = dataRepository.getGroupByServiceIdAndTypeAnimal(serviceDone.getServiceId(), selectedAnimal.getTypeAnimal());
                    if(group!=null) {
                        selectedAnimal.setGroup(group == null ? CLEAR_GUID : group.getExternalId());
                    }
                }

                if (serviceDone.getServiceData().getTypeResult() == TYPE_RESULT_SERVICE_DISTILLATION) {
                    selectedAnimal.setCorp(serviceDone.getCorpTo() == null ? null : serviceDone.getCorpToH().getExternalId());
                    selectedAnimal.setSector(serviceDone.getSectorTo() == null ? null : serviceDone.getSectorToH().getExternalId());
                    selectedAnimal.setCell(serviceDone.getCellTo() == null ? null : serviceDone.getCellToH().getExternalId());
                }

                if (serviceDone.getServiceData().getTypeResult() == TYPE_RESULT_SERVICE_VETERINARY) {
//                    dataRepository.insertServiceDoneVetExercise(ServiceDoneVetExercise.builder()
//                            .animalId(serviceDone.getAnimalId())
//                            .exerciseId(serviceDone.getVetExercise() == null ? null : serviceDone.getVetExercise().getExternalId())
//                            .preparatId(serviceDone.getVetPreparat() == null ? null : serviceDone.getVetPreparat().getExternalId())
//                            .build());
                }


            }

        }

        dataRepository.insertAnimal(selectedAnimal);

        dataRepository.insertChangedElement(DbContract.AnimalContract.TABLE_NAME, selectedAnimal.getExternalId());

        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();

        return result;

    }

    public void onSuperBackPressed(){
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
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

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public List<Fragment> getFragmentList() {
            return mFragmentList;
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        public void setFrag(int index, Fragment fragment, String title) {
            mFragmentList.set(index, fragment);
            mFragmentTitleList.set(index, title);
        }

        public void clearFrags() {
            mFragmentList.clear();
            mFragmentTitleList.clear();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }

    public DataRepository getDataRepository() {

        return dataRepository;
    }

    public Animal getSelectedAnimal() {
        return selectedAnimal;
    }


    public Animal getAnimalById(String externalId) {

        return dataRepository.getAnimalById(externalId);
    }

    public Animal getGroupAnimal() {

        return dataRepository.getAnimalById(selectedAnimal.getGroup());
    }

    public String getFullHousingAnimal() {

        String housing = "";
        Housing corp = dataRepository.getHousingById(selectedAnimal.getCorp());
        if (corp != null) {
            housing = housing +" " + corp.getNameType(this);
        }
        Housing sector = dataRepository.getHousingById(selectedAnimal.getSector());
        if (sector != null) {
            housing = housing +" " + sector.getNameType(this);
        }
        Housing cell = dataRepository.getHousingById(selectedAnimal.getCell());
        if (cell != null) {
            housing = housing +" " + cell.getNameType(this);
        }

        return housing;
    }

    public List<AnimalHistory>  getAnimalHistoryList() {

        return dataRepository.getAnimalHistoryByAnimalId(selectedAnimal.getExternalId());
    }

    public List<FarrowingCycle>  getFarrowingCyclesByAnimalId() {

        return dataRepository.getFarrowingCyclesByAnimalId(selectedAnimal.getExternalId());
    }

    public List<ServiceDone>  getServiceDoneList() {

        return dataRepository.getServiceDoneByAnimalId(selectedAnimal.getExternalId());
    }

    public List<ServiceData> getServiceNotUsedToday() {

        return dataRepository.getServiceNotUsetTodayByAnimalId(selectedAnimal.getExternalId());
    }

    public ActivityUtils getActivityUtils() {

        return activityUtils;
    }

    public void showeQuestionSelectStatusAnimal(final ServiceDoneListItem selectedService, final EditText textView) {

        final List<AnimalStatus> animalStatusList = dataRepository.getAnimalStatusList();

        List<String> animalStatusListNew = new ArrayList<String>();

        for (AnimalStatus serviceData : animalStatusList) {
            animalStatusListNew.add(serviceData.getName());
        }

        activityUtils.showSelectionList(AnimalActivity.this,
                getString(R.string.select_from_the_list), animalStatusListNew,
                new ActivityUtils.ListItemClick() {
                    @Override
                    public void onItemClik(int item, String text) {

                        selectedService.setStatusA(animalStatusList.get(item));

                        textView.setText(animalStatusList.get(item).getName());

                    }
                });

    }

    public void showeQuestionSelectDisposAnimal(final ServiceDoneListItem selectedService, final EditText textView) {

        final List<AnimalDispos> animalDisposList = dataRepository.getAnimalDisposList();

        List<String> animalDisposListNew = new ArrayList<String>();

        for (AnimalDispos animalDispos : animalDisposList) {
            animalDisposListNew.add(animalDispos.getName());
        }

        activityUtils.showSelectionList(AnimalActivity.this,
                getString(R.string.select_from_the_list), animalDisposListNew,
                new ActivityUtils.ListItemClick() {
                    @Override
                    public void onItemClik(int item, String text) {

                        selectedService.setDisposAnimA(animalDisposList.get(item));

                        textView.setText(animalDisposList.get(item).getName());

                    }
                });

    }

    public void showeQuestionSelectGroupActivity() {

        List<AnimalType> animalTypeList = dataRepository.getAnimalTypeList();

        final List<ViewModel> listGroupsAnimals = new ArrayList<>();
        for (AnimalType animalType : animalTypeList) {
            listGroupsAnimals.add(new ViewModel(animalType.getName(), animalType.getDravableTypeAnimal(), animalType.getTypeAnimal()));
        }

        activityUtils.showSelectionGrid(AnimalActivity.this,
                getResources().getString(R.string.select_from_the_list),
                listGroupsAnimals, 2, new ActivityUtils.ListGridItemClick() {
                    @Override
                    public void onItemClik(int item) {

                        int idSelection = listGroupsAnimals.get(item).getId();
                        Intent intent = new Intent(AnimalActivity.this, TecnikalGroupeAnimalActivity.class);
                        intent.putExtra(GROUPE_ANIMAL_ACTIVITY_TYPE_ANIMAL, idSelection);
                        startActivityForResult(intent, CAPTURE_SELECT_GROUP_TO_REQ);
                    }
                });

    }

    public void showeQuestionSelectVetExercise(final ServiceDoneListItem selectedService, final EditText textView) {

        final List<VetExercise> vetExerciseList = dataRepository.getVetExerciseList();

        List<String> vetExerciseListNew = new ArrayList<String>();

        for (VetExercise vetExercise : vetExerciseList) {
            vetExerciseListNew.add(vetExercise.getName());
        }

        activityUtils.showSelectionList(AnimalActivity.this,
                getString(R.string.select_from_the_list), vetExerciseListNew,
                new ActivityUtils.ListItemClick() {
                    @Override
                    public void onItemClik(int item, String text) {

//                        selectedService.setVetExercise(vetExerciseList.get(item));
//
//                        textView.setText(vetExerciseList.get(item).getName());
//
//                        selectedService.setChanged(true);

                    }
                });

    }

    public void showeQuestionSelectVetPreparat(final ServiceDoneListItem selectedService, final EditText textView) {

        final List<VetPreparat> vetPreparatList = dataRepository.getVetPreparatList();

        List<String> vetPreparatListListNew = new ArrayList<String>();

        for (VetPreparat vetPreparat : vetPreparatList) {
            vetPreparatListListNew.add(vetPreparat.getName());
        }

        activityUtils.showSelectionList(AnimalActivity.this,
                getString(R.string.select_from_the_list), vetPreparatListListNew,
                new ActivityUtils.ListItemClick() {
                    @Override
                    public void onItemClik(int item, String text) {

//                        selectedService.setVetPreparat(vetPreparatList.get(item));
//
//                        textView.setText(vetPreparatList.get(item).getName());
//
//                        selectedService.setChanged(true);

                    }
                });

    }


}
