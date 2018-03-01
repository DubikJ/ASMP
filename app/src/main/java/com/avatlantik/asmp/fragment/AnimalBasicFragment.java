package com.avatlantik.asmp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avatlantik.asmp.R;
import com.avatlantik.asmp.activity.AnimalActivity;
import com.avatlantik.asmp.adapter.AnimalServiseDoneRecyclerAdapter;
import com.avatlantik.asmp.model.ServiceDoneListItem;
import com.avatlantik.asmp.model.db.Animal;
import com.avatlantik.asmp.model.db.AnimalStatus;
import com.avatlantik.asmp.model.db.Breed;
import com.avatlantik.asmp.model.db.HertType;
import com.avatlantik.asmp.model.db.ServiceData;
import com.avatlantik.asmp.model.db.ServiceDone;
import com.avatlantik.asmp.model.db.ServiceDoneVetExercise;
import com.avatlantik.asmp.repository.DataRepository;
import com.avatlantik.asmp.utils.ActivityUtils;

import org.joda.time.LocalDateTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.avatlantik.asmp.activity.NewAnimalActivity.ID_GROUP_ELEMENT_FOR_ANIMAL;
import static com.avatlantik.asmp.activity.NewAnimalActivity.ID_SELECTED_ELEMENT_FOR_ANIMAL;
import static com.avatlantik.asmp.common.Consts.TAGLOG;
import static com.avatlantik.asmp.common.Consts.TYPE_GROUP_ANIMAL_SOW;
import static com.avatlantik.asmp.common.Consts.TYPE_RESULT_SERVICE_DISTILLATION;
import static com.avatlantik.asmp.common.Consts.TYPE_RESULT_SERVICE_INSEMINATION;
import static com.avatlantik.asmp.common.Consts.TYPE_RESULT_SERVICE_INSPECTION;
import static com.avatlantik.asmp.common.Consts.TYPE_RESULT_SERVICE_MOVEMENT;
import static com.avatlantik.asmp.common.Consts.TYPE_RESULT_SERVICE_MOVEMENT_GROUP;
import static com.avatlantik.asmp.common.Consts.TYPE_RESULT_SERVICE_REGISTRATION;
import static com.avatlantik.asmp.common.Consts.TYPE_RESULT_SERVICE_RETIREMENT;
import static com.avatlantik.asmp.common.Consts.TYPE_RESULT_SERVICE_SELECTION;
import static com.avatlantik.asmp.common.Consts.TYPE_RESULT_SERVICE_VETERINARY;

public class AnimalBasicFragment extends Fragment {

    private static  final int LAYOUT = R.layout.fragment_animal_basic;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
    public static final int  CAPTURE_SELECT_BOAR1_FRAGMENT_REQ = 1019;
    public static final int  CAPTURE_SELECT_BOAR2_FRAGMENT_REQ = 1020;
    public static final int  CAPTURE_SELECT_BOAR3_FRAGMENT_REQ = 1021;
    public static final int  CAPTURE_SELECT_GROUP_TO_REQ = 1022;
    public static final int  CAPTURE_SELECT_HOUSING_CORP_TO_REQ = 1023;
    public static final int  CAPTURE_SELECT_HOUSING_SECTOR_TO_REQ = 1024;
    public static final int  CAPTURE_SELECT_HOUSING_CELL_TO_REQ = 1025;
    public static final int  CAPTURE_SELECT_GROUP_ANIMAL_TO_REQ = 1026;
    public static final String ID_ANIMAL_ELEMENT_FOR_SERVICE = "id_animal_element_for_service";

    private RecyclerView recyclerView;
    private TextView typeTV, rfidTV, codeTV, codeAddTV, nameTV, statusTV, groupTV, nameGroupeTV,
            animalNumberTV, dateRecTV, housingTV, breedTV, hertTypeTV;
    private LinearLayout nameLL, groupLL, codeLL, groupNameLL, statusLL, breedLL, herdLL;
    private Animal selectedAnimal;
    private List<ServiceDoneListItem> listServiceDone;
    private AnimalServiseDoneRecyclerAdapter adapter;

    public static AnimalBasicFragment getInstance() {

        Bundle args = new Bundle();
        AnimalBasicFragment fragment = new AnimalBasicFragment();
        fragment.setArguments(args);
        return  fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(LAYOUT, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.list_animal_service_done);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),
                OrientationHelper.VERTICAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        typeTV = (TextView)view.findViewById(R.id.animal_type);
        codeLL = (LinearLayout) view.findViewById(R.id.animal_code_ll);
        rfidTV = (TextView)view.findViewById(R.id.animal_rfid);
        codeTV = (TextView)view.findViewById(R.id.animal_code);
        codeAddTV = (TextView)view.findViewById(R.id.animal_add_code);
        groupLL = (LinearLayout)view.findViewById(R.id.animal_group_ll);
        statusLL = (LinearLayout)view.findViewById(R.id.animal_status_ll);
        statusTV = (TextView)view.findViewById(R.id.animal_status);
        nameLL = (LinearLayout) view.findViewById(R.id.animal_name_ll);
        groupTV = (TextView)view.findViewById(R.id.animal_group);
        nameTV = (TextView)view.findViewById(R.id.animal_name);
        groupNameLL = (LinearLayout) view.findViewById(R.id.animal_group_name_ll);
        nameGroupeTV = (TextView)view.findViewById(R.id.animal_name_group);
        animalNumberTV = (TextView)view.findViewById(R.id.animal_group_number);
        dateRecTV = (TextView)view.findViewById(R.id.animal_date_rec);
        housingTV = (TextView)view.findViewById(R.id.animal_housing);
        breedLL  = (LinearLayout) view.findViewById(R.id.animal_breed_ll);
        breedTV = (TextView)view.findViewById(R.id.animal_breed);
        herdLL  = (LinearLayout) view.findViewById(R.id.animal_herd_ll);
        hertTypeTV = (TextView)view.findViewById(R.id.animal_herd);

        FloatingActionButton addServiceButton = (FloatingActionButton) view.findViewById(R.id.add_service);
        addServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<String> serviceList = new ArrayList<String>();

                final List<ServiceData> servicesNotInList = ((AnimalActivity)getActivity()).getServiceNotUsedToday();

                for (ServiceData serviceData : servicesNotInList) {
                    serviceList.add(serviceData.getName());
                }

                ((AnimalActivity)getActivity()).getActivityUtils().showSelectionList(getActivity(),
                        getString(R.string.select_from_the_list), serviceList,
                        new ActivityUtils.ListItemClick() {
                            @Override
                            public void onItemClik(int item, String text) {
                                Boolean createNew = true;
                                String selectedID = servicesNotInList.get(item).getExternalId();
                                for(ServiceDoneListItem serviceDoneListItem : listServiceDone) {
                                    if (serviceDoneListItem.getAnimalId().equalsIgnoreCase(selectedID)) {
                                        createNew = false;
                                        recyclerView.getLayoutManager().scrollToPosition(listServiceDone.indexOf(serviceDoneListItem));
                                        break;
                                    }
                                }
                                if(createNew) {
                                    ServiceDone serviceDone = ServiceDone.builder()
                                            .date(LocalDateTime.now().toDate())
                                            .animalId(selectedAnimal.getExternalId())
                                            .serviceId(selectedID)
                                            .done(false)
                                            .isPlane(false).build();

                                    listServiceDone.add(builServiceDoneListItem(serviceDone));
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        });


            }
        });

        selectedAnimal = ((AnimalActivity)getActivity()).getSelectedAnimal();

        if (selectedAnimal!= null){initData();}

        return view;
    }

    public List<ServiceDoneListItem> getListServiceDone() {
        return listServiceDone;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    private void initData() {

        typeTV.setText(selectedAnimal.getNameType(getActivity()));
        rfidTV.setText(selectedAnimal.getRfid());

        if(selectedAnimal.isGroupAnimal()) {
            groupLL.setVisibility(View.GONE);
            codeLL.setVisibility(View.GONE);
            nameLL.setVisibility(View.GONE);
            groupNameLL.setVisibility(View.VISIBLE);
            nameGroupeTV.setText(selectedAnimal.getName());
            animalNumberTV.setText(String.valueOf(selectedAnimal.getNumber()));
            statusLL.setVisibility(View.GONE);
            breedLL.setVisibility(View.GONE);
            herdLL.setVisibility(View.GONE);
        }else{
            breedLL.setVisibility(View.VISIBLE);
            herdLL.setVisibility(View.VISIBLE);
            Breed breed = (getDataRepository().getBreedById(selectedAnimal.getBreed()));
            breedTV.setText(breed==null?"":breed.getName());
            HertType hertType = (getDataRepository().getHertTypeById(selectedAnimal.getHerd()));
            hertTypeTV.setText(hertType==null?"":hertType.getName());
            if(selectedAnimal.getTypeAnimal() == TYPE_GROUP_ANIMAL_SOW){
                statusLL.setVisibility(View.VISIBLE);
                AnimalStatus animalStatus = (getDataRepository().getAnimalStatusById(selectedAnimal.getStatus()));
                statusTV.setText(animalStatus==null?"":animalStatus.getName());
            }else{
                statusLL.setVisibility(View.GONE);
            }
            groupLL.setVisibility(View.VISIBLE);
            codeLL.setVisibility(View.VISIBLE);
            nameLL.setVisibility(View.VISIBLE);
            groupNameLL.setVisibility(View.GONE);
            codeTV.setText(selectedAnimal.getCode());
            codeAddTV.setText(selectedAnimal.getAddCode());
            nameTV.setText(selectedAnimal.getName());
            Animal group = ((AnimalActivity)getActivity()).getGroupAnimal();
            groupTV.setText(group == null ? "" : group.getName());
        }

        dateRecTV.setText(dateFormatter.format(selectedAnimal.getDateRec()));

        housingTV.setText(((AnimalActivity)getActivity()).getFullHousingAnimal());

        List<ServiceDone> listService = ((AnimalActivity)getActivity()).getServiceDoneList();

        listServiceDone = new ArrayList<ServiceDoneListItem>();

        for (ServiceDone serviceDone : listService){

            ServiceDoneListItem serviceDoneListItem = builServiceDoneListItem(serviceDone);

            if(serviceDoneListItem!=null) {
                listServiceDone.add(serviceDoneListItem);
            }

        }

        adapter = new AnimalServiseDoneRecyclerAdapter(getActivity(), listServiceDone);
        recyclerView.setAdapter(adapter);
        codeTV.requestFocus();
    }

    private ServiceDoneListItem builServiceDoneListItem(ServiceDone serviceDone){

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

        ServiceData serviceData = (getDataRepository().getServiceById(serviceDone.getServiceId()));

        if(serviceData == null){
            return null;
        }

        serviceDoneListItem.setServiceData(serviceData);
        serviceDoneListItem.setAnimal(selectedAnimal);
        serviceDoneListItem.setGroup(((AnimalActivity)getActivity()).getGroupAnimal());
        if(serviceDone.getResultService()!=null&&!serviceDone.getResultService().isEmpty()) {
            serviceDoneListItem.setResultService(getDataRepository().getServiceById(serviceDone.getResultService()));
        }
        if(serviceData.getTypeResult() == TYPE_RESULT_SERVICE_INSEMINATION) {
            serviceDoneListItem.setBoar1A(getDataRepository().getAnimalById(serviceDone.getBoar1()));
            serviceDoneListItem.setBoar2A(getDataRepository().getAnimalById(serviceDone.getBoar2()));
            serviceDoneListItem.setBoar3A(getDataRepository().getAnimalById(serviceDone.getBoar3()));
        }
        if(serviceData.getTypeResult() == TYPE_RESULT_SERVICE_MOVEMENT ||
                serviceData.getTypeResult() == TYPE_RESULT_SERVICE_MOVEMENT_GROUP ||
                serviceData.getTypeResult() == TYPE_RESULT_SERVICE_REGISTRATION ||
                serviceData.getTypeResult() == TYPE_RESULT_SERVICE_REGISTRATION) {
            serviceDoneListItem.setTecnGroupToA(getDataRepository().getAnimalById(serviceDone.getTecnGroupTo()));
        }

        if(serviceData.getTypeResult() == TYPE_RESULT_SERVICE_SELECTION) {
            serviceDoneListItem.setTecnGroupToA(getDataRepository().getAnimalById(serviceDone.getTecnGroupTo()));
            serviceDoneListItem.setAnimalGroupToA(getDataRepository().getAnimalById(serviceDone.getAnimalGroupTo()));
        }

        if(serviceData.getTypeResult() == TYPE_RESULT_SERVICE_DISTILLATION ||
                serviceData.getTypeResult() == TYPE_RESULT_SERVICE_REGISTRATION) {

            serviceDoneListItem.setCorpToH(getDataRepository().getHousingById(serviceDone.getCorpTo()));
            serviceDoneListItem.setSectorToH(getDataRepository().getHousingById(serviceDone.getSectorTo()));
            serviceDoneListItem.setCellToH(getDataRepository().getHousingById(serviceDone.getCellTo()));
            serviceDoneListItem.setAnimalGroupToA(getDataRepository().getAnimalById(serviceDone.getAnimalGroupTo()));
        }

        if(serviceData.getTypeResult() == TYPE_RESULT_SERVICE_INSPECTION) {
            serviceDoneListItem.setStatusA(getDataRepository().getAnimalStatusById(serviceDone.getStatus()));
        }

        if(serviceData.getTypeResult() == TYPE_RESULT_SERVICE_RETIREMENT) {
            serviceDoneListItem.setDisposAnimA(getDataRepository().getAnimalDisposById(serviceDone.getDisposAnim()));
        }

        if(serviceData.getTypeResult() == TYPE_RESULT_SERVICE_VETERINARY) {
            List<ServiceDoneVetExercise> serviceDoneVetExerciseList = getDataRepository().getServiceDoneVetExerciseByAnimalId(selectedAnimal.getExternalId());
            for (ServiceDoneVetExercise serviceDoneVetExercise : serviceDoneVetExerciseList) {

                if(serviceDoneVetExercise.getExerciseId() == null || serviceDoneVetExercise.getExerciseId().isEmpty()){continue;}

//                serviceDoneListItem.setVetExercise(getDataRepository().getVetExerciseById(serviceDoneVetExercise.getExerciseId()));
//                serviceDoneListItem.setVetPreparat(getDataRepository().getVetPreparatById(serviceDoneVetExercise.getPreparatId()));
            }
        }

        return serviceDoneListItem;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            switch (requestCode) {

                case CAPTURE_SELECT_BOAR1_FRAGMENT_REQ :

                    if (resultCode == RESULT_OK) {

                        String selectedID = data.getStringExtra(ID_ANIMAL_ELEMENT_FOR_SERVICE);

                        ServiceDoneListItem selectedItem = adapter.getSelectedService();

                        selectedItem.setBoar1A(((AnimalActivity)getActivity()).getAnimalById(selectedID));
                        initDataToItem(selectedItem);

                    } else if (resultCode == RESULT_CANCELED) {
                        Log.d(TAGLOG, "Animals cancelled");
                    }

                    break;

                case CAPTURE_SELECT_BOAR2_FRAGMENT_REQ :

                    if (resultCode == RESULT_OK) {

                        String selectedID = data.getStringExtra(ID_ANIMAL_ELEMENT_FOR_SERVICE);

                        ServiceDoneListItem selectedItem = adapter.getSelectedService();

                        selectedItem.setBoar2A(((AnimalActivity)getActivity()).getAnimalById(selectedID));
                        initDataToItem(selectedItem);

                    } else if (resultCode == RESULT_CANCELED) {
                        Log.d(TAGLOG, "Animals cancelled");
                    }

                    break;

                case CAPTURE_SELECT_BOAR3_FRAGMENT_REQ :

                    if (resultCode == RESULT_OK) {

                        String selectedID = data.getStringExtra(ID_ANIMAL_ELEMENT_FOR_SERVICE);

                        ServiceDoneListItem selectedItem = adapter.getSelectedService();

                        selectedItem.setBoar3A(((AnimalActivity)getActivity()).getAnimalById(selectedID));
                        initDataToItem(selectedItem);

                    } else if (resultCode == RESULT_CANCELED) {
                        Log.d(TAGLOG, "Animals cancelled");
                    }

                    break;

                case CAPTURE_SELECT_GROUP_TO_REQ :
                    if (resultCode == RESULT_OK) {

                        String selectedID = data.getStringExtra(ID_GROUP_ELEMENT_FOR_ANIMAL);

                        ServiceDoneListItem selectedItem = adapter.getSelectedService();

                        selectedItem.setTecnGroupToA(((AnimalActivity)getActivity()).getAnimalById(selectedID));
                        initDataToItem(selectedItem);

                    }else if (resultCode == RESULT_CANCELED) {
                        Log.d(TAGLOG, "Animals cancelled");
                    }
                    break;

                case CAPTURE_SELECT_GROUP_ANIMAL_TO_REQ :
                    if (resultCode == RESULT_OK) {

                        String selectedID = data.getStringExtra(ID_GROUP_ELEMENT_FOR_ANIMAL);

                        ServiceDoneListItem selectedItem = adapter.getSelectedService();

                        selectedItem.setAnimalGroupToA(((AnimalActivity)getActivity()).getAnimalById(selectedID));
                        selectedItem.setChanged(true);
                        initDataToItem(selectedItem);

                    }else if (resultCode == RESULT_CANCELED) {
                        Log.d(TAGLOG, "Animals cancelled");
                    }
                    break;

                case CAPTURE_SELECT_HOUSING_CORP_TO_REQ :
                    if (resultCode == RESULT_OK) {

                        String selectedID = data.getStringExtra(ID_SELECTED_ELEMENT_FOR_ANIMAL);

                        ServiceDoneListItem selectedItem = adapter.getSelectedService();
                        selectedItem.setCorpToH(getDataRepository().getHousingById(selectedID));
                        initDataToItem(selectedItem);

                    }else if (resultCode == RESULT_CANCELED) {
                        Log.d(TAGLOG, "Housing cancelled");
                    }
                    break;

                case CAPTURE_SELECT_HOUSING_SECTOR_TO_REQ :
                    if (resultCode == RESULT_OK) {

                        String selectedID = data.getStringExtra(ID_SELECTED_ELEMENT_FOR_ANIMAL);

                        ServiceDoneListItem selectedItem = adapter.getSelectedService();
                        selectedItem.setSectorToH(getDataRepository().getHousingById(selectedID));
                        initDataToItem(selectedItem);

                    }else if (resultCode == RESULT_CANCELED) {
                        Log.d(TAGLOG, "Housing cancelled");
                    }
                    break;
                case CAPTURE_SELECT_HOUSING_CELL_TO_REQ:
                    if (resultCode == RESULT_OK) {

                        String selectedID = data.getStringExtra(ID_SELECTED_ELEMENT_FOR_ANIMAL);

                        ServiceDoneListItem selectedItem = adapter.getSelectedService();
                        selectedItem.setCellToH(getDataRepository().getHousingById(selectedID));
                        initDataToItem(selectedItem);

                    }else if (resultCode == RESULT_CANCELED) {
                        Log.d(TAGLOG, "Housing cancelled");
                    }
                    break;
            }
        }

    private void initDataToItem(ServiceDoneListItem selectedItem){
        int position = listServiceDone.indexOf(selectedItem);
        View viewItem = recyclerView.getLayoutManager().findViewByPosition(position);
        adapter.fillView(viewItem, selectedItem);
    }


    private DataRepository getDataRepository(){
        return ((AnimalActivity)getActivity()).getDataRepository();
    }

}
