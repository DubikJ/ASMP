package com.avatlantik.asmp.activity;


import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.avatlantik.asmp.R;
import com.avatlantik.asmp.adapter.ServiseDoneListAdapter;
import com.avatlantik.asmp.app.ASMPApplication;
import com.avatlantik.asmp.db.DbContract;
import com.avatlantik.asmp.model.ServiceDoneListItem;
import com.avatlantik.asmp.model.db.Animal;
import com.avatlantik.asmp.model.db.AnimalDispos;
import com.avatlantik.asmp.model.db.AnimalStatus;
import com.avatlantik.asmp.model.db.AnimalType;
import com.avatlantik.asmp.model.db.Housing;
import com.avatlantik.asmp.model.db.ServiceData;
import com.avatlantik.asmp.model.db.ServiceDone;
import com.avatlantik.asmp.model.db.ServiceDoneVetExercise;
import com.avatlantik.asmp.model.db.VetExercise;
import com.avatlantik.asmp.model.db.VetPreparat;
import com.avatlantik.asmp.repository.DataRepository;
import com.avatlantik.asmp.ui.ViewModel;
import com.avatlantik.asmp.utils.ActivityUtils;

import org.joda.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.avatlantik.asmp.activity.AnimalsActivity.ANIMALS_ACTIVITY_OPEN_FOR_RESULT;
import static com.avatlantik.asmp.activity.AnimalsActivity.ANIMALS_ACTIVITY_TYPE_ANIMAL;
import static com.avatlantik.asmp.activity.NewAnimalActivity.ANIMAL_ACTIVITY_PARAM_TYPE_ANIMAL;
import static com.avatlantik.asmp.activity.NewAnimalActivity.ID_GROUP_ELEMENT_FOR_ANIMAL;
import static com.avatlantik.asmp.activity.NewAnimalActivity.ID_SELECTED_ELEMENT_FOR_ANIMAL;
import static com.avatlantik.asmp.activity.ScanActivity.ID_ANIMAL_ELEMENT_FROM_SCAN;
import static com.avatlantik.asmp.activity.ServiceGroupProcessingActivity.SERVICE_GROUP_PROCESSING_ACTIVITY_PARAM_SERVICE_ID;
import static com.avatlantik.asmp.activity.TecnikalGroupeAnimalActivity.GROUPE_ANIMAL_ACTIVITY_TYPE_ANIMAL;
import static com.avatlantik.asmp.common.Consts.CLEAR_GUID;
import static com.avatlantik.asmp.common.Consts.TAGLOG;
import static com.avatlantik.asmp.common.Consts.TYPE_RESULT_SERVICE_DEFAULT;
import static com.avatlantik.asmp.common.Consts.TYPE_RESULT_SERVICE_DISTILLATION;
import static com.avatlantik.asmp.common.Consts.TYPE_RESULT_SERVICE_INSEMINATION;
import static com.avatlantik.asmp.common.Consts.TYPE_RESULT_SERVICE_INSPECTION;
import static com.avatlantik.asmp.common.Consts.TYPE_RESULT_SERVICE_MOVEMENT;
import static com.avatlantik.asmp.common.Consts.TYPE_RESULT_SERVICE_MOVEMENT_GROUP;
import static com.avatlantik.asmp.common.Consts.TYPE_RESULT_SERVICE_MOVEMENT_NO_NUMBER;
import static com.avatlantik.asmp.common.Consts.TYPE_RESULT_SERVICE_REGISTRATION;
import static com.avatlantik.asmp.common.Consts.TYPE_RESULT_SERVICE_RETIREMENT;
import static com.avatlantik.asmp.common.Consts.TYPE_RESULT_SERVICE_SELECTION;
import static com.avatlantik.asmp.common.Consts.TYPE_RESULT_SERVICE_VETERINARY;
import static com.avatlantik.asmp.common.Consts.TYPE_RESULT_SERVICE_WEIGNING;
import static com.avatlantik.asmp.common.Consts.USING_SCAN;

public class ServiceActivity extends AppCompatActivity {

    private static final int CAPTURE_SCAN_ACTIVITY_REQ = 1001;
    private static final int CAPTURE_SELECT_ANIMAL_ACTIVITY_REQ = 1002;
    public static final int CAPTURE_SELECT_BOAR1_ACTIVITY_REQ = 1010;
    public static final int CAPTURE_SELECT_BOAR2_ACTIVITY_REQ = 1011;
    public static final int CAPTURE_SELECT_BOAR3_ACTIVITY_REQ = 1012;
    public static final int CAPTURE_SELECT_GROUP_TO_REQ = 1013;
    public static final int CAPTURE_SELECT_HOUSING_CORP_TO_REQ = 1014;
    public static final int CAPTURE_SELECT_HOUSING_SECTOR_TO_REQ = 1015;
    public static final int CAPTURE_SELECT_HOUSING_CELL_TO_REQ = 1016;
    private static final int CAPTURE_NEW_ANIMAL_ACTIVITY_REQ = 1018;
    private static final int CAPTURE_GROUP_PROCESS_ACTIVITY_REQ = 1040;
    public static final int CAPTURE_SELECT_GROUP_ANIMAL_TO_REQ = 1041;
    public static final String ID_ANIMAL_ELEMENT_FOR_SERVICE = "id_animal_element_for_service";
    public static final String SERVICE_ACTIVITY_PARAM_SERVICE_ID = "service_activity_param_service_id";


    @Inject
    DataRepository dataRepository;

    @Inject
    ActivityUtils activityUtils;

    private ListView listView;
    private ServiceData selectedServive;
    private List<ServiceDoneListItem> listServiceDone;
    private ServiseDoneListAdapter adapter;
    private Boolean changed;
    private BottomSheetDialog bottomSheetDialog;
    private View textEntryView;
    private String searchName = "";
    private String groupAnimalToMov;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        ((ASMPApplication) getApplication()).getComponent().inject(this);

        listView = (ListView) findViewById(R.id.list_service_line);


        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            String serviceId = extras.getString(SERVICE_ACTIVITY_PARAM_SERVICE_ID);
            selectedServive = dataRepository.getServiceById(serviceId);
        } else {
            Toast.makeText(getApplicationContext(),
                    getResources().getString(R.string.task_not_found),
                    Toast.LENGTH_LONG).show();
            finish();
        }
        if (selectedServive == null) {
            Toast.makeText(getApplicationContext(),
                    getResources().getString(R.string.task_not_found),
                    Toast.LENGTH_LONG).show();
            finish();
        }

        setTitle(selectedServive.getName());

        final LinearLayout animalAddLL = (LinearLayout) findViewById(R.id.service_animal_add_layout);
        FloatingActionButton addAnimalButton = (FloatingActionButton) findViewById(R.id.service_animal_add);
        addAnimalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Boolean.valueOf(dataRepository.getUserSetting(USING_SCAN))) {
                    activityUtils.showQuestion(ServiceActivity.this, getString(R.string.scan),
                            getString(R.string.questions_start_scan),
                            new ActivityUtils.QuestionAnswer() {
                                @Override
                                public void onPositiveAnsver() {
                                    Intent intent = new Intent(ServiceActivity.this, ScanActivity.class);
                                    intent.putExtra(ANIMALS_ACTIVITY_OPEN_FOR_RESULT, true);
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                                        final ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                                                ServiceActivity.this,
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
                } else {
                    showeAnimalsActivity();
                }
            }
        });

        final LinearLayout animalGPLL = (LinearLayout) findViewById(R.id.service_animal_group_processing_layout);
        if (selectedServive.getTypeResult() == TYPE_RESULT_SERVICE_DEFAULT ||
                selectedServive.getTypeResult() == TYPE_RESULT_SERVICE_DISTILLATION ||
                selectedServive.getTypeResult() == TYPE_RESULT_SERVICE_REGISTRATION ||
                selectedServive.getTypeResult() == TYPE_RESULT_SERVICE_RETIREMENT ||
                selectedServive.getTypeResult() == TYPE_RESULT_SERVICE_MOVEMENT ||
                selectedServive.getTypeResult() == TYPE_RESULT_SERVICE_MOVEMENT_GROUP ||
                selectedServive.getTypeResult() == TYPE_RESULT_SERVICE_MOVEMENT_NO_NUMBER ||
                selectedServive.getTypeResult() == TYPE_RESULT_SERVICE_WEIGNING ||
                selectedServive.getTypeResult() == TYPE_RESULT_SERVICE_SELECTION) {
            animalGPLL.setVisibility(View.VISIBLE);
        } else {
            animalGPLL.setVisibility(View.GONE);
        }
        FloatingActionButton groupProcessButton = (FloatingActionButton) findViewById(R.id.service_animal_group_processing);
        groupProcessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ServiceActivity.this, ServiceGroupProcessingActivity.class);
                intent.putExtra(SERVICE_GROUP_PROCESSING_ACTIVITY_PARAM_SERVICE_ID, selectedServive.getExternalId());
                startActivityForResult(intent, CAPTURE_GROUP_PROCESS_ACTIVITY_REQ);
            }
        });

        initData();

        changed = false;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.service_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {

            saveInDB(false);

            return true;
        }


        if (id == R.id.action_selection_search) {

            adapter.setFilterByName(true);

            adapter.getFilter().filter("");

            initSearch();

            bottomSheetDialog.setContentView(textEntryView);

            bottomSheetDialog.show();

            return true;
        }

        if (id == R.id.action_selection_all) {

            adapter.setFilterByName(false);

            adapter.getFilter().filter("");

            getSupportActionBar().setSubtitle("");

            return true;
        }

        if (id == R.id.action_selection_done) {

            adapter.setFilterByName(false);

            adapter.getFilter().filter("true");

            getSupportActionBar().setSubtitle(R.string.action_selection_done);

            return true;
        }

        if (id == R.id.action_selection_not_done) {

            adapter.setFilterByName(false);

            adapter.getFilter().filter("false");

            getSupportActionBar().setSubtitle(R.string.action_selection_not_done);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
//        if (changed || adapter.isDataChanged()) {
        activityUtils.showQuestion(ServiceActivity.this, getString(R.string.questions_title_info),
                getString(R.string.questions_data_save),
                new ActivityUtils.QuestionAnswer() {
                    @Override
                    public void onPositiveAnsver() {
                        if (saveInDB(true)) {
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
//        }else{
//            onSuperBackPressed();
//        }
    }

    public void onSuperBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }

    public void showeAnimalsActivity() {


        List<Integer> animalTypes = dataRepository.getAnimalTypeListByServiceID(selectedServive.getExternalId());

        if (animalTypes.size() > 0) {

            List<AnimalType> animalTypeList = dataRepository.getAnimalTypeListByListTypes(animalTypes);

            if (animalTypeList.size() == 1) {
                Intent intent = new Intent(ServiceActivity.this, AnimalsActivity.class);
                intent.putExtra(ANIMALS_ACTIVITY_TYPE_ANIMAL, animalTypeList.get(0).getTypeAnimal());
                intent.putExtra(ANIMALS_ACTIVITY_OPEN_FOR_RESULT, true);
                startActivityForResult(intent, CAPTURE_SELECT_ANIMAL_ACTIVITY_REQ);
            } else {
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

        activityUtils.showSelectionGrid(ServiceActivity.this,
                getResources().getString(R.string.select_from_the_list),
                listGroupsAnimals, 2, new ActivityUtils.ListGridItemClick() {
                    @Override
                    public void onItemClik(int item) {

                        int idSelection = listGroupsAnimals.get(item).getId();
                        if (selectedServive.getTypeResult() == TYPE_RESULT_SERVICE_REGISTRATION) {
                            Intent intent = new Intent(ServiceActivity.this, NewAnimalActivity.class);
                            intent.putExtra(ANIMAL_ACTIVITY_PARAM_TYPE_ANIMAL, idSelection);
                            startActivityForResult(intent, CAPTURE_NEW_ANIMAL_ACTIVITY_REQ);
                        } else {
                            Intent intent = new Intent(ServiceActivity.this, AnimalsActivity.class);
                            intent.putExtra(ANIMALS_ACTIVITY_TYPE_ANIMAL, idSelection);
                            intent.putExtra(ANIMALS_ACTIVITY_OPEN_FOR_RESULT, true);
                            startActivityForResult(intent, CAPTURE_SELECT_ANIMAL_ACTIVITY_REQ);
                        }
                    }
                });

    }


    private void initData() {
        List<ServiceDone> listService = dataRepository.getServiceDoneByServiceId(selectedServive.getExternalId());

        listServiceDone = new ArrayList<ServiceDoneListItem>();

        for (ServiceDone serviceDone : listService) {

            ServiceDoneListItem serviceDoneListItem = builServiceDoneListItem(serviceDone);
            if (serviceDoneListItem != null) {
                serviceDoneListItem.setChanged(false);
                listServiceDone.add(serviceDoneListItem);
            }

        }
        adapter = new ServiseDoneListAdapter(
                ServiceActivity.this,
                listServiceDone,
                selectedServive.getTypeResult());
        listView.setAdapter(adapter);
    }

    private ServiceDoneListItem builServiceDoneListItem(ServiceDone serviceDone) {

        Animal animal = dataRepository.getAnimalById(serviceDone.getAnimalId());

        if (animal == null) return null;

        ServiceDoneListItem serviceDoneListItem = new ServiceDoneListItem(serviceDone.getId(),
                serviceDone.getDate(),
                serviceDone.getDateDay(),
                serviceDone.getAnimalId(),
                serviceDone.getServiceId(),
                serviceDone.getPlane(),
                serviceDone.getDone(),
                serviceDone.getNote(),
                serviceDone.getNumber(),
                serviceDone.getLive(),
                serviceDone.getNormal(),
                serviceDone.getWeak(),
                serviceDone.getDeath(),
                serviceDone.getMummy(),
                serviceDone.getWeight(),
                serviceDone.getBoar1(),
                serviceDone.getBoar2(),
                serviceDone.getBoar3(),
                serviceDone.getTecnGroupTo(),
                serviceDone.getCorpTo(),
                serviceDone.getSectorTo(),
                serviceDone.getCellTo(),
                serviceDone.getResultService(),
                serviceDone.getAdmNumber(),
                serviceDone.getStatus(),
                serviceDone.getDisposAnim(),
                serviceDone.getLength(),
                serviceDone.getBread(),
                serviceDone.getExterior(),
                serviceDone.getDepthMysz(),
                serviceDone.getNewCode(),
                serviceDone.isArtifInsemen(),
                serviceDone.getAnimalGroupTo());

        if (serviceDone.getResultService() != null && !serviceDone.getResultService().isEmpty()) {
            serviceDoneListItem.setResultService(dataRepository.getServiceById(serviceDone.getResultService()));
        }
        if (selectedServive.getTypeResult() == TYPE_RESULT_SERVICE_INSEMINATION) {
            serviceDoneListItem.setBoar1A(dataRepository.getAnimalById(serviceDone.getBoar1()));
            serviceDoneListItem.setBoar2A(dataRepository.getAnimalById(serviceDone.getBoar2()));
            serviceDoneListItem.setBoar3A(dataRepository.getAnimalById(serviceDone.getBoar3()));
        }
        if (selectedServive.getTypeResult() == TYPE_RESULT_SERVICE_MOVEMENT ||
                selectedServive.getTypeResult() == TYPE_RESULT_SERVICE_MOVEMENT_GROUP ||
                selectedServive.getTypeResult() == TYPE_RESULT_SERVICE_REGISTRATION ||
                selectedServive.getTypeResult() == TYPE_RESULT_SERVICE_MOVEMENT_NO_NUMBER) {
            serviceDoneListItem.setTecnGroupToA(dataRepository.getAnimalById(serviceDone.getTecnGroupTo()));
        }

        if (selectedServive.getTypeResult() == TYPE_RESULT_SERVICE_SELECTION) {
            serviceDoneListItem.setTecnGroupToA(dataRepository.getAnimalById(serviceDone.getTecnGroupTo()));
            serviceDoneListItem.setAnimalGroupToA(dataRepository.getAnimalById(serviceDone.getAnimalGroupTo()));
        }

        if (selectedServive.getTypeResult() == TYPE_RESULT_SERVICE_DISTILLATION ||
                selectedServive.getTypeResult() == TYPE_RESULT_SERVICE_REGISTRATION) {
            serviceDoneListItem.setCorpToH(dataRepository.getHousingById(serviceDone.getCorpTo()));
            serviceDoneListItem.setSectorToH(dataRepository.getHousingById(serviceDone.getSectorTo()));
            serviceDoneListItem.setCellToH(dataRepository.getHousingById(serviceDone.getCellTo()));
            serviceDoneListItem.setAnimalGroupToA(dataRepository.getAnimalById(serviceDone.getAnimalGroupTo()));
        }

        if (selectedServive.getTypeResult() == TYPE_RESULT_SERVICE_INSPECTION) {
            serviceDoneListItem.setStatusA(dataRepository.getAnimalStatusById(serviceDone.getStatus()));
        }

        if (selectedServive.getTypeResult() == TYPE_RESULT_SERVICE_RETIREMENT) {
            serviceDoneListItem.setDisposAnimA(dataRepository.getAnimalDisposById(serviceDone.getDisposAnim()));
        }

        if (selectedServive.getTypeResult() == TYPE_RESULT_SERVICE_VETERINARY) {
            List<ServiceDoneVetExercise> serviceDoneVetExerciseList = dataRepository.getServiceDoneVetExerciseByAnimalId(animal.getExternalId());
            for (ServiceDoneVetExercise serviceDoneVetExercise : serviceDoneVetExerciseList) {

                if (serviceDoneVetExercise.getExerciseId() == null || serviceDoneVetExercise.getExerciseId().isEmpty()) {
                    continue;
                }

                serviceDoneVetExercise.setVetExercise(dataRepository.getVetExerciseById(serviceDoneVetExercise.getExerciseId()));
                serviceDoneVetExercise.setVetPreparat(dataRepository.getVetPreparatById(serviceDoneVetExercise.getPreparatId()));
            }

            serviceDoneListItem.setVetExerciseList(serviceDoneVetExerciseList);
        }

        if (animal != null) {
            serviceDoneListItem.setAnimal(animal);
            serviceDoneListItem.setGroup(dataRepository.getAnimalById(animal.getGroup()));
            String housing = "";
            Housing corp = dataRepository.getHousingById(animal.getCorp());
            if (corp != null) {
                housing = housing + " " + corp.getNameType(this);
            }
            Housing sector = dataRepository.getHousingById(animal.getSector());
            if (sector != null) {
                housing = housing + " " + sector.getNameType(this);
            }
            Housing cell = dataRepository.getHousingById(animal.getCell());
            if (cell != null) {
                housing = housing + " " + cell.getNameType(this);
            }
            serviceDoneListItem.setHousing(housing);
        }

        return serviceDoneListItem;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case CAPTURE_SCAN_ACTIVITY_REQ:
            case CAPTURE_SELECT_ANIMAL_ACTIVITY_REQ:

                if (resultCode == RESULT_OK) {

                    Boolean createNew = true;
                    String selectedID = "";
                    if (requestCode == CAPTURE_SCAN_ACTIVITY_REQ) {
                        selectedID = data.getStringExtra(ID_ANIMAL_ELEMENT_FROM_SCAN);
                    } else {
                        selectedID = data.getStringExtra(ID_ANIMAL_ELEMENT_FOR_SERVICE);
                    }

                    for (ServiceDoneListItem serviceDoneListItem : listServiceDone) {
                        if (serviceDoneListItem.getAnimalId().equalsIgnoreCase(selectedID)) {
                            createNew = false;
                            int position = listServiceDone.indexOf(serviceDoneListItem);
                            listView.setSelection(position);
                            adapter.showWindowPopup(listView.getChildAt(position), serviceDoneListItem);
                            break;
                        }
                    }

                    if (createNew) {

                        if (selectedServive.getTypeResult() == TYPE_RESULT_SERVICE_MOVEMENT ||
                                selectedServive.getTypeResult() == TYPE_RESULT_SERVICE_SELECTION) {
                            Animal animal = dataRepository.getAnimalById(selectedID);
                            if (animal.isGroupAnimal()) {

                                groupAnimalToMov = selectedID;

                                List<AnimalType> animalTypeList = dataRepository.getAnimalTypeList();

                                final List<ViewModel> listGroupsAnimals = new ArrayList<>();
                                for (AnimalType animalType : animalTypeList) {
                                    listGroupsAnimals.add(new ViewModel(animalType.getName(), animalType.getDravableTypeAnimal(), animalType.getTypeAnimal()));
                                }

                                activityUtils.showSelectionGrid(ServiceActivity.this,
                                        getResources().getString(R.string.select_from_the_list),
                                        listGroupsAnimals, 2, new ActivityUtils.ListGridItemClick() {
                                            @Override
                                            public void onItemClik(int item) {

                                                int idSelection = listGroupsAnimals.get(item).getId();
                                                Intent intent = new Intent(ServiceActivity.this, NewAnimalActivity.class);
                                                intent.putExtra(ANIMAL_ACTIVITY_PARAM_TYPE_ANIMAL, idSelection);
                                                startActivityForResult(intent, CAPTURE_NEW_ANIMAL_ACTIVITY_REQ);

                                            }
                                        });
                                return;

                            }

                        }
                        ServiceDone serviceDone = ServiceDone.builder()
                                .date(LocalDateTime.now().toDate())
                                .animalId(selectedID)
                                .serviceId(selectedServive.getExternalId())
                                .done(false)
                                .isPlane(false).build();

                        ServiceDoneListItem serviceDoneListItem = builServiceDoneListItem(serviceDone);
                        if (serviceDoneListItem != null) {
                            serviceDoneListItem.setChanged(true);
                            listServiceDone.add(serviceDoneListItem);
                        }
                        adapter.notifyDataSetChanged();
                    }

                } else if (resultCode == RESULT_CANCELED) {
                    Log.d(TAGLOG, "Animals cancelled");
                }

                break;


            case CAPTURE_GROUP_PROCESS_ACTIVITY_REQ:
                if (resultCode == RESULT_OK) {

                    initData();

                    changed = false;

                } else if (resultCode == RESULT_CANCELED) {
                    Log.d(TAGLOG, "Animals cancelled");
                }

                break;

            case CAPTURE_NEW_ANIMAL_ACTIVITY_REQ:
                if (resultCode == RESULT_OK) {

                    Boolean createNew = true;
                    String selectedID = data.getStringExtra(ID_ANIMAL_ELEMENT_FOR_SERVICE);
                    for (ServiceDoneListItem serviceDoneListItem : listServiceDone) {
                        if (serviceDoneListItem.getAnimalId().equalsIgnoreCase(selectedID)) {
                            createNew = false;
                            listView.setSelection(listServiceDone.indexOf(serviceDoneListItem));
                            break;
                        }
                    }

                    if (createNew) {
                        ServiceDone serviceDone = ServiceDone.builder()
                                .date(LocalDateTime.now().toDate())
                                .animalId(selectedID)
                                .serviceId(selectedServive.getExternalId())
                                .done(false)
                                .tecnGroupTo(selectedServive.getTypeResult() == TYPE_RESULT_SERVICE_MOVEMENT ||
                                        selectedServive.getTypeResult() == TYPE_RESULT_SERVICE_SELECTION
                                        ? groupAnimalToMov : null)
                                .isPlane(false).build();

                        ServiceDoneListItem serviceDoneListItem = builServiceDoneListItem(serviceDone);
                        if (serviceDoneListItem != null) {
                            serviceDoneListItem.setChanged(true);
                            listServiceDone.add(serviceDoneListItem);

                            saveServiceDoneInDB(serviceDoneListItem);

                        }
                        adapter.notifyDataSetChanged();
                    }

                    changed = true;

                } else if (resultCode == RESULT_CANCELED) {
                    Log.d(TAGLOG, "Animals cancelled");
                }

                break;

            case CAPTURE_SELECT_BOAR1_ACTIVITY_REQ:

                if (resultCode == RESULT_OK) {

                    String selectedID = data.getStringExtra(ID_ANIMAL_ELEMENT_FOR_SERVICE);

                    ServiceDoneListItem selectedItem = adapter.getSelectedService();

                    selectedItem.setBoar1A(dataRepository.getAnimalById(selectedID));
                    selectedItem.setChanged(true);
                    initDataToItem(selectedItem);

                } else if (resultCode == RESULT_CANCELED) {
                    Log.d(TAGLOG, "Animals cancelled");
                }

                break;

            case CAPTURE_SELECT_BOAR2_ACTIVITY_REQ:

                if (resultCode == RESULT_OK) {

                    String selectedID = data.getStringExtra(ID_ANIMAL_ELEMENT_FOR_SERVICE);

                    ServiceDoneListItem selectedItem = adapter.getSelectedService();

                    selectedItem.setBoar2A(dataRepository.getAnimalById(selectedID));
                    selectedItem.setChanged(true);
                    initDataToItem(selectedItem);

                } else if (resultCode == RESULT_CANCELED) {
                    Log.d(TAGLOG, "Animals cancelled");
                }

                break;

            case CAPTURE_SELECT_BOAR3_ACTIVITY_REQ:

                if (resultCode == RESULT_OK) {

                    String selectedID = data.getStringExtra(ID_ANIMAL_ELEMENT_FOR_SERVICE);

                    ServiceDoneListItem selectedItem = adapter.getSelectedService();

                    selectedItem.setBoar3A(dataRepository.getAnimalById(selectedID));
                    selectedItem.setChanged(true);
                    initDataToItem(selectedItem);

                } else if (resultCode == RESULT_CANCELED) {
                    Log.d(TAGLOG, "Animals cancelled");
                }

                break;

            case CAPTURE_SELECT_GROUP_TO_REQ:
                if (resultCode == RESULT_OK) {

                    String selectedID = data.getStringExtra(ID_GROUP_ELEMENT_FOR_ANIMAL);

                    ServiceDoneListItem selectedItem = adapter.getSelectedService();

                    selectedItem.setTecnGroupToA(dataRepository.getAnimalById(selectedID));
                    selectedItem.setChanged(true);
                    initDataToItem(selectedItem);

                } else if (resultCode == RESULT_CANCELED) {
                    Log.d(TAGLOG, "Animals cancelled");
                }
                break;

            case CAPTURE_SELECT_GROUP_ANIMAL_TO_REQ:
                if (resultCode == RESULT_OK) {

                    String selectedID = data.getStringExtra(ID_GROUP_ELEMENT_FOR_ANIMAL);

                    ServiceDoneListItem selectedItem = adapter.getSelectedService();

                    selectedItem.setAnimalGroupToA(dataRepository.getAnimalById(selectedID));
                    selectedItem.setChanged(true);
                    initDataToItem(selectedItem);

                } else if (resultCode == RESULT_CANCELED) {
                    Log.d(TAGLOG, "Animals cancelled");
                }
                break;

            case CAPTURE_SELECT_HOUSING_CORP_TO_REQ:
                if (resultCode == RESULT_OK) {

                    String selectedID = data.getStringExtra(ID_SELECTED_ELEMENT_FOR_ANIMAL);

                    ServiceDoneListItem selectedItem = adapter.getSelectedService();
                    selectedItem.setCorpToH(dataRepository.getHousingById(selectedID));
                    selectedItem.setChanged(true);
                    initDataToItem(selectedItem);

                } else if (resultCode == RESULT_CANCELED) {
                    Log.d(TAGLOG, "Housing cancelled");
                }
                break;

            case CAPTURE_SELECT_HOUSING_SECTOR_TO_REQ:
                if (resultCode == RESULT_OK) {

                    String selectedID = data.getStringExtra(ID_SELECTED_ELEMENT_FOR_ANIMAL);

                    ServiceDoneListItem selectedItem = adapter.getSelectedService();
                    selectedItem.setSectorToH(dataRepository.getHousingById(selectedID));
                    selectedItem.setChanged(true);
                    initDataToItem(selectedItem);

                } else if (resultCode == RESULT_CANCELED) {
                    Log.d(TAGLOG, "Housing cancelled");
                }
                break;
            case CAPTURE_SELECT_HOUSING_CELL_TO_REQ:
                if (resultCode == RESULT_OK) {

                    String selectedID = data.getStringExtra(ID_SELECTED_ELEMENT_FOR_ANIMAL);

                    ServiceDoneListItem selectedItem = adapter.getSelectedService();
                    selectedItem.setCellToH(dataRepository.getHousingById(selectedID));
                    selectedItem.setChanged(true);
                    initDataToItem(selectedItem);

                } else if (resultCode == RESULT_CANCELED) {
                    Log.d(TAGLOG, "Housing cancelled");
                }
                break;
        }
    }

    private void initDataToItem(ServiceDoneListItem selectedItem) {
//        int position = listServiceDone.indexOf(selectedItem);
//        View view = listView.getChildAt(position - listView.getFirstVisiblePosition());
//        adapter.fillCapView(view, selectedItem);
        //adapter.fillView(view, selectedItem);
        //adapter.notifyDataSetChanged();
        adapter.fillView(adapter.getSelectedView(), selectedItem);
        changed = true;
    }

    private Boolean saveInDB(Boolean usedCahnge) {

        Boolean result = true;

        for (ServiceDoneListItem serviceDone : listServiceDone) {

            if (usedCahnge) {
                if (!serviceDone.getChanged()) {
                    continue;
                }
            }

            boolean cancel = false;
            View focusView = null;

//            if (selectedServive.getTypeResult() == TYPE_RESULT_SERVICE_MOVEMENT) {
//
//                if (serviceDone.getDone() && serviceDone.getGroupToA() == null) {
//
//                    int position = listServiceDone.indexOf(serviceDone);
//
//                    View view = null;
//
//                    if(position < listView.getFirstVisiblePosition()) {
//                        listView.setSelection(position);
//                        view = listView.getChildAt(position);
//                    }else{
//                        view = listView.getChildAt(position - listView.getFirstVisiblePosition());
//                    }
//
//                    if(view!=null) {
//                        EditText toGroup = (EditText) view.findViewById(R.id.list_service_done_to_group);
//                        toGroup.setError(getString(R.string.error_field_required));
//                        focusView = toGroup;
//                    }
//                    cancel = true;
//                }
//            }

//            if (selectedServive.getTypeResult() == TYPE_RESULT_SERVICE_INSEMINATION) {
//
//                if (serviceDone.getDone() && serviceDone.getBoar1A() == null) {
//
//                    int position = listServiceDone.indexOf(serviceDone);
//
//                    View view = null;
//
//                    if(position < listView.getFirstVisiblePosition()) {
//                        listView.setSelection(position);
//                        view = listView.getChildAt(position);
//                    }else{
//                        view = listView.getChildAt(position - listView.getFirstVisiblePosition());
//                    }
//                    if(view!=null) {
//                        EditText boar1 = (EditText) view.findViewById(R.id.list_service_done_boar1);
//
//                        boar1.setError(getString(R.string.error_field_required));
//                        focusView = boar1;
//                    }
//                    cancel = true;
//                }
//            }


            if (cancel) {

                focusView.requestFocus();

                result = false;

            } else {

                saveServiceDoneInDB(serviceDone);

                changed = false;
                adapter.setDataChanged(false);

            }

            serviceDone.setChanged(false);

        }

        return result;
    }

    private void saveServiceDoneInDB(ServiceDoneListItem serviceDone) {

        dataRepository.insertServiceDone(ServiceDone.builder()
                .date(serviceDone.getDate())
                .dateDay(serviceDone.getDate())
                .animalId(serviceDone.getAnimalId())
                .serviceId(serviceDone.getServiceId())
                .isPlane(serviceDone.getPlane())
                .done(serviceDone.getDone())
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

        ServiceDone selectedSD = dataRepository.getServiceDoneByServiceIdAnimalId(serviceDone.getServiceId(), serviceDone.getAnimalId());
        if (selectedSD != null) {
            dataRepository.insertChangedElement(DbContract.ServiceDoneContract.TABLE_NAME, String.valueOf(selectedSD.getId()));
        }

        if (selectedServive.getTypeResult() == TYPE_RESULT_SERVICE_MOVEMENT) {
            Animal animal = dataRepository.getAnimalById(serviceDone.getAnimalId());
            Animal group = dataRepository.getGroupByServiceIdAndTypeAnimal(selectedServive.getExternalId(), animal.getTypeAnimal());
            if (group != null) {
                animal.setGroup(group.getExternalId());
                dataRepository.insertAnimal(animal);
            }
        }

        if (selectedServive.getTypeResult() == TYPE_RESULT_SERVICE_INSPECTION) {
            Animal animal = dataRepository.getAnimalById(serviceDone.getAnimalId());
            if (animal != null && serviceDone.getStatusA() != null) {
                animal.setStatus(serviceDone.getStatusA().getExternalId());
                dataRepository.insertAnimal(animal);
            }
        }

        if (selectedServive.getTypeResult() == TYPE_RESULT_SERVICE_DISTILLATION) {
            Animal animal = dataRepository.getAnimalById(serviceDone.getAnimalId());
            animal.setCorp(serviceDone.getCorpToH() == null ? null : serviceDone.getCorpToH().getExternalId());
            animal.setSector(serviceDone.getSectorToH() == null ? null : serviceDone.getSectorToH().getExternalId());
            animal.setCell(serviceDone.getCellToH() == null ? null : serviceDone.getCellToH().getExternalId());
            dataRepository.insertAnimal(animal);
        }

        if (selectedServive.getTypeResult() == TYPE_RESULT_SERVICE_REGISTRATION) {
            Animal animal = dataRepository.getAnimalById(serviceDone.getAnimalId());
            animal.setGroup(serviceDone.getTecnGroupToA() == null ? CLEAR_GUID : serviceDone.getTecnGroupToA().getExternalId());
            animal.setCorp(serviceDone.getCorpToH() == null ? null : serviceDone.getCorpToH().getExternalId());
            animal.setSector(serviceDone.getSectorToH() == null ? null : serviceDone.getSectorToH().getExternalId());
            animal.setCell(serviceDone.getCellToH() == null ? null : serviceDone.getCellToH().getExternalId());
            dataRepository.insertAnimal(animal);
        }

        if (selectedServive.getTypeResult() == TYPE_RESULT_SERVICE_VETERINARY) {

            for(ServiceDoneVetExercise serviceDoneVetExercise : serviceDone.getVetExerciseList()) {
                dataRepository.insertServiceDoneVetExercise(ServiceDoneVetExercise.builder()
                        .animalId(serviceDone.getAnimalId())
                        .exerciseId(serviceDoneVetExercise.getExerciseId())
                        .preparatId(serviceDoneVetExercise.getPreparatId())
                        .build());
            }
        }

    }



    public ActivityUtils getActivityUtils() {

        return activityUtils;
    }


    public List<ServiceData> getListServiceData() {

        return dataRepository.getServiceList();
    }

    public void showeQuestionSelectStatusAnimal(final ServiceDoneListItem selectedService, final EditText textView) {

        final List<AnimalStatus> animalStatusList = dataRepository.getAnimalStatusList();

        List<String> animalStatusListNew = new ArrayList<String>();

        for (AnimalStatus serviceData : animalStatusList) {
            animalStatusListNew.add(serviceData.getName());
        }

        activityUtils.showSelectionList(ServiceActivity.this,
                getString(R.string.select_from_the_list), animalStatusListNew,
                new ActivityUtils.ListItemClick() {
                    @Override
                    public void onItemClik(int item, String text) {

                        selectedService.setStatusA(animalStatusList.get(item));

                        textView.setText(animalStatusList.get(item).getName());

                        selectedService.setChanged(true);

                    }
                });

    }

    public void showeQuestionSelectDisposAnimal(final ServiceDoneListItem selectedService, final EditText textView) {

        final List<AnimalDispos> animalDisposList = dataRepository.getAnimalDisposList();

        List<String> animalDisposListNew = new ArrayList<String>();

        for (AnimalDispos animalDispos : animalDisposList) {
            animalDisposListNew.add(animalDispos.getName());
        }

        activityUtils.showSelectionList(ServiceActivity.this,
                getString(R.string.select_from_the_list), animalDisposListNew,
                new ActivityUtils.ListItemClick() {
                    @Override
                    public void onItemClik(int item, String text) {

                        selectedService.setDisposAnimA(animalDisposList.get(item));

                        textView.setText(animalDisposList.get(item).getName());

                        selectedService.setChanged(true);

                    }
                });

    }

    public void showeQuestionSelectGroupActivity() {

        List<AnimalType> animalTypeList = dataRepository.getAnimalTypeList();

        final List<ViewModel> listGroupsAnimals = new ArrayList<>();
        for (AnimalType animalType : animalTypeList) {
            listGroupsAnimals.add(new ViewModel(animalType.getName(), animalType.getDravableTypeAnimal(), animalType.getTypeAnimal()));
        }

        activityUtils.showSelectionGrid(ServiceActivity.this,
                getResources().getString(R.string.select_from_the_list),
                listGroupsAnimals, 2, new ActivityUtils.ListGridItemClick() {
                    @Override
                    public void onItemClik(int item) {

                        int idSelection = listGroupsAnimals.get(item).getId();
                        Intent intent = new Intent(ServiceActivity.this, TecnikalGroupeAnimalActivity.class);
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

        activityUtils.showSelectionList(ServiceActivity.this,
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

        activityUtils.showSelectionList(ServiceActivity.this,
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

    private void initSearch(){

        bottomSheetDialog = new BottomSheetDialog(this, R.style.BottomSheetDialog);

        textEntryView = getLayoutInflater().inflate(R.layout.dialog_search_name, null);

        final EditText searchEditText = (EditText) textEntryView.findViewById(R.id.search_textView);
        searchEditText.setText(searchName, TextView.BufferType.EDITABLE);
        searchEditText.setOnTouchListener(new View.OnTouchListener() {
            final int DRAWABLE_LEFT = 0;
            final int DRAWABLE_TOP = 1;
            final int DRAWABLE_RIGHT = 2;
            final int DRAWABLE_BOTTOM = 3;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    int leftEdgeOfRightDrawable = searchEditText.getRight()
                            - searchEditText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width();
                    if (event.getRawX() >= leftEdgeOfRightDrawable) {
                        searchEditText.setText("");
                        return true;
                    }
                }
                return false;
            }
        });
        searchEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        Button okButton = (Button) textEntryView.findViewById(R.id.okButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                searchName = String.valueOf(searchEditText.getText());
                adapter.getFilter().filter(searchName);

                bottomSheetDialog.hide();

                if(searchName==null||searchName.isEmpty()) {
                    getSupportActionBar().setSubtitle("");
                }else{
                    getSupportActionBar().setSubtitle(R.string.search+": "+searchName);
                }
            }
        });

        Button cancelButton = (Button) textEntryView.findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                adapter.getFilter().filter(searchName);

                bottomSheetDialog.hide();
            }
        });
    }

}
