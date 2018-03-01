package com.avatlantik.asmp.activity;

import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.avatlantik.asmp.R;
import com.avatlantik.asmp.adapter.ServiceGroupProcessingListAdapter;
import com.avatlantik.asmp.app.ASMPApplication;
import com.avatlantik.asmp.db.DbContract;
import com.avatlantik.asmp.model.db.Animal;
import com.avatlantik.asmp.model.db.AnimalDispos;
import com.avatlantik.asmp.model.db.AnimalType;
import com.avatlantik.asmp.model.db.Housing;
import com.avatlantik.asmp.model.db.ServiceData;
import com.avatlantik.asmp.model.db.ServiceDone;
import com.avatlantik.asmp.repository.DataRepository;
import com.avatlantik.asmp.ui.ViewModel;
import com.avatlantik.asmp.utils.ActivityUtils;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;
import org.joda.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.avatlantik.asmp.activity.AnimalsActivity.ANIMALS_ACTIVITY_OPEN_FOR_RESULT;
import static com.avatlantik.asmp.activity.AnimalsActivity.ANIMALS_ACTIVITY_TYPE_ANIMAL;
import static com.avatlantik.asmp.activity.HousingActivity.HOUSING_ACTIVITY_PARENT_ID;
import static com.avatlantik.asmp.activity.HousingActivity.HOUSING_ACTIVITY_TYPE_HOUSING;
import static com.avatlantik.asmp.activity.NewAnimalActivity.ANIMAL_ACTIVITY_PARAM_TYPE_ANIMAL;
import static com.avatlantik.asmp.activity.NewAnimalActivity.ID_GROUP_ELEMENT_FOR_ANIMAL;
import static com.avatlantik.asmp.activity.NewAnimalActivity.ID_SELECTED_ELEMENT_FOR_ANIMAL;
import static com.avatlantik.asmp.activity.ScanActivity.ID_ANIMAL_ELEMENT_FROM_SCAN;
import static com.avatlantik.asmp.activity.ServiceActivity.ID_ANIMAL_ELEMENT_FOR_SERVICE;
import static com.avatlantik.asmp.activity.TecnikalGroupeAnimalActivity.GROUPE_ANIMAL_ACTIVITY_TYPE_ANIMAL;
import static com.avatlantik.asmp.common.Consts.CLEAR_GUID;
import static com.avatlantik.asmp.common.Consts.TAGLOG;
import static com.avatlantik.asmp.common.Consts.TYPE_HOUSING_CORP;
import static com.avatlantik.asmp.common.Consts.TYPE_HOUSING_SECTOR;
import static com.avatlantik.asmp.common.Consts.TYPE_RESULT_SERVICE_DISTILLATION;
import static com.avatlantik.asmp.common.Consts.TYPE_RESULT_SERVICE_MOVEMENT;
import static com.avatlantik.asmp.common.Consts.TYPE_RESULT_SERVICE_MOVEMENT_GROUP;
import static com.avatlantik.asmp.common.Consts.TYPE_RESULT_SERVICE_MOVEMENT_NO_NUMBER;
import static com.avatlantik.asmp.common.Consts.TYPE_RESULT_SERVICE_REGISTRATION;
import static com.avatlantik.asmp.common.Consts.TYPE_RESULT_SERVICE_RETIREMENT;
import static com.avatlantik.asmp.common.Consts.TYPE_RESULT_SERVICE_SELECTION;
import static com.avatlantik.asmp.common.Consts.USING_SCAN;

public class ServiceGroupProcessingActivity extends ActionBarActivity{


    private static final int  CAPTURE_SCAN_ACTIVITY_REQ = 1041;
    private static final int  CAPTURE_SELECT_GROUP_TO_REQ = 1042;
    private static final int  CAPTURE_SELECT_HOUSING_CORP_TO_REQ = 1043;
    private static final int  CAPTURE_SELECT_HOUSING_SECTOR_TO_REQ = 1044;
    private static final int  CAPTURE_SELECT_HOUSING_CELL_TO_REQ = 1045;
    private static final int  CAPTURE_SELECT_ANIMAL_ACTIVITY_REQ = 1046;
    private static final int  CAPTURE_NEW_ANIMAL_ACTIVITY_REQ = 1047;
    private static final int  CAPTURE_SELECT_GROUP_TECNICAL_TO_REQ = 1048;

    public static final String SERVICE_GROUP_PROCESSING_ACTIVITY_PARAM_SERVICE_ID
            = "service_group_processing_activity_param_service_id";

    @Inject
    DataRepository dataRepository;

    @Inject
    ActivityUtils activityUtils;

    private Toolbar mToolbar;
    private ListView listView;
    private View mContainerHeader;
    private ObjectAnimator fade;
    private ServiceData selectedServive;
    private List<Animal> animals;
    private ServiceGroupProcessingListAdapter adapter;
    private LinearLayout animalAddLL;
    private EditText toGroup, toTecnicalGroup, toCorp, toSector, toCell, admNumET, typeDispos;
    private Animal group, tecnicalGroup;
    private Housing corp, sector, cell;
    private AnimalDispos animalDispos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_group_processin);

        ((ASMPApplication) getApplication()).getComponent().inject(this);

        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            String serviceId = extras.getString(SERVICE_GROUP_PROCESSING_ACTIVITY_PARAM_SERVICE_ID);
            selectedServive = dataRepository.getServiceById(serviceId);
        }else{
            Toast.makeText(getApplicationContext(),
                    getResources().getString(R.string.task_not_found),
                    Toast.LENGTH_LONG).show();
            finish();
        }
        if(selectedServive == null){
            Toast.makeText(getApplicationContext(),
                    getResources().getString(R.string.task_not_found),
                    Toast.LENGTH_LONG).show();
            finish();
        }

        listView = (ListView) findViewById(R.id.list_animals);

        animalAddLL = (LinearLayout) findViewById(R.id.animal_add_layout);

        View headerView = LayoutInflater.from(this)
                .inflate(R.layout.listview_header_group_processing, listView, false);
        mContainerHeader = headerView.findViewById(R.id.container);
        listView.addHeaderView(headerView);

        fade = ObjectAnimator.ofFloat(mContainerHeader, "alpha", 0f, 1f);
        fade.setInterpolator(new DecelerateInterpolator());
        fade.setDuration(400);


        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (view != null && view.getChildCount() > 0 && firstVisibleItem == 0) {
                    if (view.getChildAt(0).getTop() < -dpToPx(16)) {
                        toggleHeader(false, false);
                    } else {
                        toggleHeader(true, true);
                    }
                } else {
                    toggleHeader(false, false);
                }
                if (isLollipop()) {
                    if (firstVisibleItem == 0) {
                        mToolbar.setElevation(0);
                    } else {
                        mToolbar.setElevation(dpToPx(4));
                    }
                }
            }
        });

        FloatingActionButton addAnimalFab = (FloatingActionButton) findViewById(R.id.animal_add);
        addAnimalFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Boolean.valueOf(dataRepository.getUserSetting(USING_SCAN))) {
                    activityUtils.showQuestion(ServiceGroupProcessingActivity.this, getString(R.string.scan),
                            getString(R.string.questions_start_scan),
                            new ActivityUtils.QuestionAnswer() {
                                @Override
                                public void onPositiveAnsver() {
                                    Intent intent = new Intent(ServiceGroupProcessingActivity.this, ScanActivity.class);
                                    intent.putExtra(ANIMALS_ACTIVITY_OPEN_FOR_RESULT, true);
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                                        final ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                                                ServiceGroupProcessingActivity.this,
                                                android.util.Pair.create((View) animalAddLL, "bg"));
                                        startActivityForResult(intent, CAPTURE_SCAN_ACTIVITY_REQ, options.toBundle());
                                    } else {
                                        startActivityForResult(intent, CAPTURE_SCAN_ACTIVITY_REQ);
                                    }
                                }

                                @Override
                                public void onNegativeAnsver() {
                                    showeAnimalsActivity();
                                }

                                @Override
                                public void onNeutralAnsver() {
                                }
                            });
                }else {
                    showeAnimalsActivity();
                }
            }
        });

        TextInputLayout toGroupTIL = (TextInputLayout) headerView.findViewById(R.id.list_service_done_to_group_til);
        toGroupTIL.setVisibility(
                selectedServive.getTypeResult() == TYPE_RESULT_SERVICE_SELECTION ||
                selectedServive.getTypeResult() == TYPE_RESULT_SERVICE_REGISTRATION ? View.VISIBLE : View.GONE);

        toGroup = (EditText) headerView.findViewById(R.id.list_service_done_to_group);
        toGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<AnimalType> animalTypeList = dataRepository.getAnimalTypeList();

                final List<ViewModel> listGroupsAnimals = new ArrayList<>();
                for (AnimalType animalType : animalTypeList) {
                    listGroupsAnimals.add(new ViewModel(animalType.getName(), animalType.getDravableTypeAnimal(), animalType.getTypeAnimal()));
                }

                activityUtils.showSelectionGrid(ServiceGroupProcessingActivity.this,
                        getResources().getString(R.string.select_from_the_list),
                        listGroupsAnimals, 2, new ActivityUtils.ListGridItemClick() {
                            @Override
                            public void onItemClik(int item) {

                                int idSelection = listGroupsAnimals.get(item).getId();
                                Intent intent = new Intent(ServiceGroupProcessingActivity.this, GroupeAnimalActivity.class);
                                intent.putExtra(GROUPE_ANIMAL_ACTIVITY_TYPE_ANIMAL, idSelection);
                                startActivityForResult(intent, CAPTURE_SELECT_GROUP_TO_REQ);
                            }
                        });
            }
        });

        TextInputLayout toGroupTecnicalTIL = (TextInputLayout) headerView.findViewById(R.id.list_service_done_to_group_tecnical_til);
        toGroupTecnicalTIL.setVisibility(
                selectedServive.getTypeResult() == TYPE_RESULT_SERVICE_MOVEMENT_GROUP ||
                        selectedServive.getTypeResult() == TYPE_RESULT_SERVICE_MOVEMENT_NO_NUMBER ||
                        selectedServive.getTypeResult() == TYPE_RESULT_SERVICE_SELECTION ? View.VISIBLE : View.GONE);

        toTecnicalGroup = (EditText) headerView.findViewById(R.id.list_service_done_to_group_tecnical);
        toTecnicalGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<AnimalType> animalTypeList = dataRepository.getAnimalTypeList();

                final List<ViewModel> listGroupsAnimals = new ArrayList<>();
                for (AnimalType animalType : animalTypeList) {
                    listGroupsAnimals.add(new ViewModel(animalType.getName(), animalType.getDravableTypeAnimal(), animalType.getTypeAnimal()));
                }

                activityUtils.showSelectionGrid(ServiceGroupProcessingActivity.this,
                        getResources().getString(R.string.select_from_the_list),
                        listGroupsAnimals, 2, new ActivityUtils.ListGridItemClick() {
                            @Override
                            public void onItemClik(int item) {

                                int idSelection = listGroupsAnimals.get(item).getId();
                                Intent intent = new Intent(ServiceGroupProcessingActivity.this, TecnikalGroupeAnimalActivity.class);
                                intent.putExtra(GROUPE_ANIMAL_ACTIVITY_TYPE_ANIMAL, idSelection);
                                startActivityForResult(intent, CAPTURE_SELECT_GROUP_TECNICAL_TO_REQ);

                            }
                        });
            }
        });


        TextInputLayout toCorpTIL = (TextInputLayout) headerView.findViewById(R.id.list_service_done_to_corp_til);
        toCorpTIL.setVisibility(
                selectedServive.getTypeResult() == TYPE_RESULT_SERVICE_DISTILLATION ||
                selectedServive.getTypeResult() == TYPE_RESULT_SERVICE_REGISTRATION  ? View.VISIBLE : View.GONE);

        toCorp = (EditText) findViewById(R.id.list_service_done_to_corp);
        toCorp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServiceGroupProcessingActivity.this, HousingActivity.class);
                intent.putExtra(HOUSING_ACTIVITY_TYPE_HOUSING, TYPE_HOUSING_CORP);
                intent.putExtra(HOUSING_ACTIVITY_PARENT_ID,
                        corp!=null ? corp.getParentId() : null);
                startActivityForResult(intent, CAPTURE_SELECT_HOUSING_CORP_TO_REQ);
            }
        });

        LinearLayout toSectorLL = (LinearLayout) headerView.findViewById(R.id.list_service_done_to_sector_ll);
        toSectorLL.setVisibility(
                selectedServive.getTypeResult() == TYPE_RESULT_SERVICE_DISTILLATION ||
                selectedServive.getTypeResult() == TYPE_RESULT_SERVICE_REGISTRATION  ? View.VISIBLE : View.GONE);

        toSector = (EditText) findViewById(R.id.list_service_done_to_sector);
        toSector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServiceGroupProcessingActivity.this, HousingActivity.class);
                intent.putExtra(HOUSING_ACTIVITY_TYPE_HOUSING, TYPE_HOUSING_SECTOR);
                intent.putExtra(HOUSING_ACTIVITY_PARENT_ID,
                        sector != null ? sector.getParentId() :
                                (corp != null ? corp.getExternalId() : null));
                startActivityForResult(intent, CAPTURE_SELECT_HOUSING_SECTOR_TO_REQ);
            }
        });

        toCell = (EditText) findViewById(R.id.list_service_done_to_cell);
        toCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServiceGroupProcessingActivity.this, HousingActivity.class);
                intent.putExtra(HOUSING_ACTIVITY_TYPE_HOUSING, TYPE_HOUSING_SECTOR);
                intent.putExtra(HOUSING_ACTIVITY_PARENT_ID,
                        cell != null ? cell.getParentId() :
                                (sector != null ? sector.getExternalId() : null));
                startActivityForResult(intent, CAPTURE_SELECT_HOUSING_CELL_TO_REQ);
            }
        });

        TextInputLayout admNumTIL = (TextInputLayout) headerView.findViewById(R.id.list_service_done_adm_num_til);
        admNumTIL.setVisibility(
                selectedServive.getTypeResult() == TYPE_RESULT_SERVICE_REGISTRATION ? View.VISIBLE : View.GONE);

        admNumET = (EditText) findViewById(R.id.list_service_done_adm_num);


        TextInputLayout typeDisposTIL = (TextInputLayout) headerView.findViewById(R.id.list_service_done_type_dispos_anim_til);
        typeDisposTIL.setVisibility(
                selectedServive.getTypeResult() == TYPE_RESULT_SERVICE_RETIREMENT ? View.VISIBLE : View.GONE);

        typeDispos = (EditText) headerView.findViewById(R.id.list_service_done_type_dispos_anim);
        typeDispos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final List<AnimalDispos> animalDisposList = dataRepository.getAnimalDisposList();

                List<String> animalDisposListNew = new ArrayList<String>();

                for (AnimalDispos animalDispos : animalDisposList) {
                    animalDisposListNew.add(animalDispos.getName());
                }

                activityUtils.showSelectionList(ServiceGroupProcessingActivity.this,
                        getString(R.string.select_from_the_list), animalDisposListNew,
                        new ActivityUtils.ListItemClick() {
                            @Override
                            public void onItemClik(int item, String text) {

                                animalDispos = animalDisposList.get(item);

                                typeDispos.setText(animalDispos.getName());

                            }
                        });
            }
        });

        animals =  new ArrayList<>();
        adapter = new ServiceGroupProcessingListAdapter(this, animals);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        activityUtils.showQuestion(ServiceGroupProcessingActivity.this, getString(R.string.questions_title_info),
                getString(R.string.questions_data_save),
                new ActivityUtils.QuestionAnswer() {
                    @Override
                    public void onPositiveAnsver() {
                        if (saveInDB()) {
                            onSuperBackPressed();
                        }
                    }

                    @Override
                    public void onNegativeAnsver() {
                        onSuperBackPressed();
                    }

                    @Override
                    public void onNeutralAnsver() {
                    }
                });
    }

    public void onSuperBackPressed(){
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mToolbar.setTitle(getResources().getString(R.string.task_title) + selectedServive.getName());

        if(adapter!=null) {
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case CAPTURE_SCAN_ACTIVITY_REQ:
            case CAPTURE_SELECT_ANIMAL_ACTIVITY_REQ:

                if (resultCode == RESULT_OK) {

                    String selectedID ="";
                    if(requestCode == CAPTURE_SCAN_ACTIVITY_REQ){
                        selectedID = data.getStringExtra(ID_ANIMAL_ELEMENT_FROM_SCAN);
                    }else {
                        selectedID = data.getStringExtra(ID_ANIMAL_ELEMENT_FOR_SERVICE);
                    }

                    Animal animal = dataRepository.getAnimalById(selectedID);
                    if(animal==null)return;

                    final String finalSelectedID = selectedID;
                    List<Animal>foundAnimals = (List<Animal>) CollectionUtils.select(animals, new Predicate() {
                        public boolean evaluate(Object sample) {
                            return ((Animal) sample).getExternalId().equals(finalSelectedID);
                        }});

                    if(foundAnimals.size()>0){
                        listView.setSelection(animals.indexOf(animal));
                        Toast.makeText(this, getResources().getText(R.string.element_is_added), Toast.LENGTH_SHORT);
                        return;
                    }

                    animals.add(animal);
                    adapter.notifyDataSetChanged();

                } else if (resultCode == RESULT_CANCELED) {
                    Log.d(TAGLOG, "Animals cancelled");
                }

                break;

            case CAPTURE_NEW_ANIMAL_ACTIVITY_REQ:
                if (resultCode == RESULT_OK) {

                    final String selectedID = data.getStringExtra(ID_ANIMAL_ELEMENT_FOR_SERVICE);
                    Animal animal = dataRepository.getAnimalById(selectedID);
                    if(animal==null)return;

                    List<Animal>foundAnimals = (List<Animal>) CollectionUtils.select(animals, new Predicate() {
                        public boolean evaluate(Object sample) {
                            return ((Animal) sample).getExternalId().equals(selectedID);
                        }});
                    if(foundAnimals.size()>0){
                        listView.setSelection(animals.indexOf(animal));
                        Toast.makeText(this, getResources().getText(R.string.element_is_added), Toast.LENGTH_SHORT);
                        return;
                    }

                    animals.add(animal);
                    adapter.notifyDataSetChanged();

                } else if (resultCode == RESULT_CANCELED) {
                    Log.d(TAGLOG, "Animals cancelled");
                }

                break;

            case CAPTURE_SELECT_GROUP_TO_REQ:
                if (resultCode == RESULT_OK) {

                    String selectedID = data.getStringExtra(ID_GROUP_ELEMENT_FOR_ANIMAL);
                    group = dataRepository.getAnimalById(selectedID);
                    toGroup.setText(group.getName());

                } else if (resultCode == RESULT_CANCELED) {
                    Log.d(TAGLOG, "Animals cancelled");
                }
                break;

            case CAPTURE_SELECT_GROUP_TECNICAL_TO_REQ:
                if (resultCode == RESULT_OK) {

                    String selectedID = data.getStringExtra(ID_GROUP_ELEMENT_FOR_ANIMAL);
                    tecnicalGroup = dataRepository.getAnimalById(selectedID);
                    toTecnicalGroup.setText(group.getName());

                } else if (resultCode == RESULT_CANCELED) {
                    Log.d(TAGLOG, "Animals cancelled");
                }
                break;

            case CAPTURE_SELECT_HOUSING_CORP_TO_REQ:
                if (resultCode == RESULT_OK) {

                    String selectedID = data.getStringExtra(ID_SELECTED_ELEMENT_FOR_ANIMAL);
                    corp = dataRepository.getHousingById(selectedID);
                    toCorp.setText(corp.getName());

                } else if (resultCode == RESULT_CANCELED) {
                    Log.d(TAGLOG, "Housing cancelled");
                }
                break;

            case CAPTURE_SELECT_HOUSING_SECTOR_TO_REQ:
                if (resultCode == RESULT_OK) {

                    String selectedID = data.getStringExtra(ID_SELECTED_ELEMENT_FOR_ANIMAL);

                    sector = dataRepository.getHousingById(selectedID);
                    toSector.setText(sector.getName());

                } else if (resultCode == RESULT_CANCELED) {
                    Log.d(TAGLOG, "Housing cancelled");
                }
                break;
            case CAPTURE_SELECT_HOUSING_CELL_TO_REQ:
                if (resultCode == RESULT_OK) {

                    String selectedID = data.getStringExtra(ID_SELECTED_ELEMENT_FOR_ANIMAL);

                    cell = dataRepository.getHousingById(selectedID);
                    toCell.setText(cell.getName());

                } else if (resultCode == RESULT_CANCELED) {
                    Log.d(TAGLOG, "Housing cancelled");
                }
                break;
        }
    }

    public void showeAnimalsActivity() {


        List<Integer> animalTypes = dataRepository.getAnimalTypeListByServiceID(selectedServive.getExternalId());

        if (animalTypes.size()>0) {

            List<AnimalType> animalTypeList = dataRepository.getAnimalTypeListByListTypes(animalTypes);

            if(animalTypeList.size()==1) {
                Intent intent = new Intent(ServiceGroupProcessingActivity.this, AnimalsActivity.class);
                intent.putExtra(ANIMALS_ACTIVITY_TYPE_ANIMAL, animalTypeList.get(0).getTypeAnimal());
                intent.putExtra(ANIMALS_ACTIVITY_OPEN_FOR_RESULT, true);
                startActivityForResult(intent, CAPTURE_SELECT_ANIMAL_ACTIVITY_REQ);
            }else{
                showeSelectionGroupActivity(animalTypeList);
            }

        } else {
            showeSelectionGroupActivity(dataRepository.getAnimalTypeList());
        }

    }

    public void showeSelectionGroupActivity(List<AnimalType> animalTypeList) {

        final List<ViewModel> listGroupsAnimals = new ArrayList<>();
        for (AnimalType animalType : animalTypeList) {
            listGroupsAnimals.add(new ViewModel(animalType.getName(), animalType.getDravableTypeAnimal(), animalType.getTypeAnimal()));
        }

        activityUtils.showSelectionGrid(ServiceGroupProcessingActivity.this,
                getResources().getString(R.string.select_from_the_list),
                listGroupsAnimals, 2, new ActivityUtils.ListGridItemClick() {
                    @Override
                    public void onItemClik(int item) {

                        int idSelection = listGroupsAnimals.get(item).getId();
                        if (selectedServive.getTypeResult() == TYPE_RESULT_SERVICE_REGISTRATION ||
                                selectedServive.getTypeResult() == TYPE_RESULT_SERVICE_SELECTION) {
                            Intent intent = new Intent(ServiceGroupProcessingActivity.this, NewAnimalActivity.class);
                            intent.putExtra(ANIMAL_ACTIVITY_PARAM_TYPE_ANIMAL, idSelection);
                            startActivityForResult(intent, CAPTURE_NEW_ANIMAL_ACTIVITY_REQ);
                        } else {
                            Intent intent = new Intent(ServiceGroupProcessingActivity.this, AnimalsActivity.class);
                            intent.putExtra(ANIMALS_ACTIVITY_TYPE_ANIMAL, idSelection);
                            intent.putExtra(ANIMALS_ACTIVITY_OPEN_FOR_RESULT, true);
                            startActivityForResult(intent, CAPTURE_SELECT_ANIMAL_ACTIVITY_REQ);
                        }
                    }
                });

    }


    private Boolean saveInDB(){

        Boolean result = true;


        String textAdmNumber = String.valueOf(admNumET.getText());
        int admNumber =  textAdmNumber==null || textAdmNumber.isEmpty() ? 0 : Integer.valueOf(textAdmNumber);

        boolean cancel = false;
        View focusView = null;

        if (selectedServive.getTypeResult() == TYPE_RESULT_SERVICE_MOVEMENT_GROUP ||
                selectedServive.getTypeResult() == TYPE_RESULT_SERVICE_MOVEMENT_NO_NUMBER ||
                selectedServive.getTypeResult() == TYPE_RESULT_SERVICE_SELECTION) {

            if (tecnicalGroup == null) {

                toTecnicalGroup.setError(getString(R.string.error_field_required));
                focusView = toTecnicalGroup;
                cancel = true;
            }
        }

        if (selectedServive.getTypeResult() == TYPE_RESULT_SERVICE_DISTILLATION) {

            if (corp == null) {

                toCorp.setError(getString(R.string.error_field_required));
                focusView = toCorp;
                cancel = true;
            }
        }

        if (selectedServive.getTypeResult() == TYPE_RESULT_SERVICE_REGISTRATION) {

            if (group == null) {

                toGroup.setError(getString(R.string.error_field_required));
                focusView = toGroup;
                cancel = true;
            }

            if (corp == null) {

                toCorp.setError(getString(R.string.error_field_required));
                focusView = toCorp;
                cancel = true;
            }

            if (admNumber == 0) {

                admNumET.setError(getString(R.string.error_field_required));
                focusView = admNumET;
                cancel = true;
            }
        }

        if (selectedServive.getTypeResult() == TYPE_RESULT_SERVICE_RETIREMENT) {

            if (animalDispos == null) {

                typeDispos.setError(getString(R.string.error_field_required));
                focusView = typeDispos;
                cancel = true;
            }
        }

        if (cancel) {

            focusView.requestFocus();

            result = false;

        } else {

            for (Animal animal : animals) {

                dataRepository.insertServiceDone(ServiceDone.builder()
                        .date(LocalDateTime.now().toDate())
                        .dateDay(LocalDateTime.now().toDate())
                        .animalId(animal.getExternalId())
                        .serviceId(selectedServive.getExternalId())
                        .isPlane(false)
                        .done(true)
                        .number(animal.getNumber())
                        .weight(animal.getWeight() == null ? 0.0 : animal.getWeight())
                        .tecnGroupTo(
                                selectedServive.getTypeResult() == TYPE_RESULT_SERVICE_REGISTRATION ||
                                               group == null ? null : group.getExternalId())
                        .corpTo(corp == null ? null : corp.getExternalId())
                        .sectorTo(sector == null ? null : sector.getExternalId())
                        .cellTo(cell == null ? null : cell.getExternalId())
                        .admNumber(admNumber)
                        .disposAnim(animalDispos == null ? null : animalDispos.getExternalId())
                        .animalGroupTo(
                                selectedServive.getTypeResult() == TYPE_RESULT_SERVICE_REGISTRATION ||
                                        group !=null ? group.getExternalId() : null)
                        .build());

                ServiceDone selectedSD = dataRepository.getServiceDoneByServiceIdAnimalId(selectedServive.getExternalId(), animal.getExternalId());
                if (selectedSD != null) {
                    dataRepository.insertChangedElement(DbContract.ServiceDoneContract.TABLE_NAME, String.valueOf(selectedSD.getId()));
                }

                if (selectedServive.getTypeResult() == TYPE_RESULT_SERVICE_MOVEMENT) {
                    animal.setGroup(group == null ? CLEAR_GUID : group.getExternalId());
                    dataRepository.insertAnimal(animal);
                }

                if (selectedServive.getTypeResult() == TYPE_RESULT_SERVICE_REGISTRATION) {
                    animal.setCorp(corp == null ? null : corp.getExternalId());
                    animal.setSector(sector == null ? null : sector.getExternalId());
                    animal.setCell(cell == null ? null : cell.getExternalId());
                    dataRepository.insertAnimal(animal);
                }

                if (selectedServive.getTypeResult() == TYPE_RESULT_SERVICE_REGISTRATION) {
                    animal.setGroup(group == null ? CLEAR_GUID : group.getExternalId());
                    animal.setCorp(corp == null ? null : corp.getExternalId());
                    animal.setSector(sector == null ? null : sector.getExternalId());
                    animal.setCell(cell == null ? null : cell.getExternalId());
                    dataRepository.insertAnimal(animal);
                }
            }
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);

        }

        return result;
    }

    private void toggleHeader(boolean visible, boolean force) {
        if ((force && visible) || (visible && mContainerHeader.getAlpha() == 0f)) {
            fade.setFloatValues(mContainerHeader.getAlpha(), 1f);
            fade.start();
        } else if (force || (!visible && mContainerHeader.getAlpha() == 1f)){
            fade.setFloatValues(mContainerHeader.getAlpha(), 0f);
            fade.start();
        }
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return (int)(dp * (displayMetrics.densityDpi / 160f));
    }

    public static boolean isLollipop() {
        return android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

}
