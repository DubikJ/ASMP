package com.avatlantik.asmp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.avatlantik.asmp.R;
import com.avatlantik.asmp.activity.AnimalActivity;
import com.avatlantik.asmp.activity.AnimalsActivity;
import com.avatlantik.asmp.activity.GroupeAnimalActivity;
import com.avatlantik.asmp.activity.HousingActivity;
import com.avatlantik.asmp.activity.ServiceActivity;
import com.avatlantik.asmp.activity.TecnikalGroupeAnimalActivity;
import com.avatlantik.asmp.model.ServiceDoneListItem;
import com.avatlantik.asmp.model.db.Animal;
import com.avatlantik.asmp.model.db.ServiceData;

import java.util.List;

import static com.avatlantik.asmp.activity.AnimalsActivity.ANIMALS_ACTIVITY_OPEN_FOR_RESULT;
import static com.avatlantik.asmp.activity.AnimalsActivity.ANIMALS_ACTIVITY_TYPE_ANIMAL;
import static com.avatlantik.asmp.activity.HousingActivity.HOUSING_ACTIVITY_PARENT_ID;
import static com.avatlantik.asmp.activity.HousingActivity.HOUSING_ACTIVITY_TYPE_HOUSING;
import static com.avatlantik.asmp.activity.TecnikalGroupeAnimalActivity.GROUPE_ANIMAL_ACTIVITY_TYPE_ANIMAL;
import static com.avatlantik.asmp.common.Consts.TYPE_GROUP_ANIMAL_ALL;
import static com.avatlantik.asmp.common.Consts.TYPE_GROUP_ANIMAL_BOAR;
import static com.avatlantik.asmp.common.Consts.TYPE_HOUSING_CORP;
import static com.avatlantik.asmp.common.Consts.TYPE_HOUSING_SECTOR;
import static com.avatlantik.asmp.common.Consts.TYPE_RESULT_SERVICE_ASSESSMENT;
import static com.avatlantik.asmp.common.Consts.TYPE_RESULT_SERVICE_DISTILLATION;
import static com.avatlantik.asmp.common.Consts.TYPE_RESULT_SERVICE_FARROW;
import static com.avatlantik.asmp.common.Consts.TYPE_RESULT_SERVICE_INSEMINATION;
import static com.avatlantik.asmp.common.Consts.TYPE_RESULT_SERVICE_INSPECTION;
import static com.avatlantik.asmp.common.Consts.TYPE_RESULT_SERVICE_MOVEMENT;
import static com.avatlantik.asmp.common.Consts.TYPE_RESULT_SERVICE_MOVEMENT_GROUP;
import static com.avatlantik.asmp.common.Consts.TYPE_RESULT_SERVICE_MOVEMENT_NO_NUMBER;
import static com.avatlantik.asmp.common.Consts.TYPE_RESULT_SERVICE_NEW_NUMBER;
import static com.avatlantik.asmp.common.Consts.TYPE_RESULT_SERVICE_REGISTRATION;
import static com.avatlantik.asmp.common.Consts.TYPE_RESULT_SERVICE_RETIREMENT;
import static com.avatlantik.asmp.common.Consts.TYPE_RESULT_SERVICE_SELECTION;
import static com.avatlantik.asmp.common.Consts.TYPE_RESULT_SERVICE_VETERINARY;
import static com.avatlantik.asmp.common.Consts.TYPE_RESULT_SERVICE_WEANIMG;
import static com.avatlantik.asmp.common.Consts.TYPE_RESULT_SERVICE_WEIGNING;
import static com.avatlantik.asmp.fragment.AnimalBasicFragment.CAPTURE_SELECT_BOAR1_FRAGMENT_REQ;
import static com.avatlantik.asmp.fragment.AnimalBasicFragment.CAPTURE_SELECT_BOAR2_FRAGMENT_REQ;
import static com.avatlantik.asmp.fragment.AnimalBasicFragment.CAPTURE_SELECT_BOAR3_FRAGMENT_REQ;
import static com.avatlantik.asmp.fragment.AnimalBasicFragment.CAPTURE_SELECT_GROUP_ANIMAL_TO_REQ;
import static com.avatlantik.asmp.fragment.AnimalBasicFragment.CAPTURE_SELECT_GROUP_TO_REQ;
import static com.avatlantik.asmp.fragment.AnimalBasicFragment.CAPTURE_SELECT_HOUSING_CELL_TO_REQ;
import static com.avatlantik.asmp.fragment.AnimalBasicFragment.CAPTURE_SELECT_HOUSING_CORP_TO_REQ;
import static com.avatlantik.asmp.fragment.AnimalBasicFragment.CAPTURE_SELECT_HOUSING_SECTOR_TO_REQ;

public class AnimalServiseDoneRecyclerAdapter extends RecyclerView.Adapter {

    private List<ServiceDoneListItem> list;
    private LayoutInflater layoutInflater;
    private Context context;
    private ServiceDoneListItem selectedService;

    public static class ServiceViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;

        public ServiceViewHolder(View itemView) {
            super(itemView);
            this.cardView = (CardView) itemView.findViewById(R.id.card_view);
        }
    }

    public AnimalServiseDoneRecyclerAdapter(Context context, List<ServiceDoneListItem> list) {
        this.context = context;
        this.list = list;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        switch (viewType) {
            case TYPE_RESULT_SERVICE_DISTILLATION:
                view = layoutInflater.inflate(R.layout.animal_service_item_distillation, parent, false);
                return new ServiceViewHolder(view);
            case TYPE_RESULT_SERVICE_FARROW:
                view = layoutInflater.inflate(R.layout.animal_service_item_farrowing, parent, false);
                return new ServiceViewHolder(view);
            case TYPE_RESULT_SERVICE_INSEMINATION:
                view = layoutInflater.inflate(R.layout.animal_service_item_insemination, parent, false);
                return new ServiceViewHolder(view);
            case TYPE_RESULT_SERVICE_MOVEMENT :
                view = layoutInflater.inflate(R.layout.animal_service_item_movement, parent, false);
                return new ServiceViewHolder(view);
            case TYPE_RESULT_SERVICE_MOVEMENT_GROUP:
                view = layoutInflater.inflate(R.layout.animal_service_item_movement, parent, false);
                return new ServiceViewHolder(view);
            case TYPE_RESULT_SERVICE_MOVEMENT_NO_NUMBER:
                view = layoutInflater.inflate(R.layout.animal_service_item_movement, parent, false);
                return new ServiceViewHolder(view);
            case TYPE_RESULT_SERVICE_WEIGNING :
                view = layoutInflater.inflate(R.layout.animal_service_item_movement, parent, false);
                return new ServiceViewHolder(view);
            case TYPE_RESULT_SERVICE_WEANIMG:
                view = layoutInflater.inflate(R.layout.animal_service_item_weaning, parent, false);
                return new ServiceViewHolder(view);
            case TYPE_RESULT_SERVICE_REGISTRATION:
                view = layoutInflater.inflate(R.layout.animal_service_item_registration, parent, false);
                return new ServiceViewHolder(view);
            case TYPE_RESULT_SERVICE_INSPECTION:
                view = layoutInflater.inflate(R.layout.animal_service_item_inspection, parent, false);
                return new ServiceViewHolder(view);
            case TYPE_RESULT_SERVICE_RETIREMENT:
                view = layoutInflater.inflate(R.layout.animal_service_item_retirement, parent, false);
                return new ServiceViewHolder(view);
            case TYPE_RESULT_SERVICE_ASSESSMENT:
                view = layoutInflater.inflate(R.layout.animal_service_item_assesment, parent, false);
                return new ServiceViewHolder(view);
            case TYPE_RESULT_SERVICE_NEW_NUMBER:
                view = layoutInflater.inflate(R.layout.animal_service_item_new_number, parent, false);
                return new ServiceViewHolder(view);
            case TYPE_RESULT_SERVICE_SELECTION:
                view = layoutInflater.inflate(R.layout.animal_service_item_selection, parent, false);
                return new ServiceViewHolder(view);
            case TYPE_RESULT_SERVICE_VETERINARY:
                view = layoutInflater.inflate(R.layout.animal_service_item_veterinary, parent, false);
                return new ServiceViewHolder(view);
            default:
                view = layoutInflater.inflate(R.layout.animal_service_item_default, parent, false);
                return new ServiceViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {

        ServiceData serviceData = getServiceDone(position).getServiceData();
        if(serviceData==null) {
            return -1;
        }
        return serviceData.getTypeResult();
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {


        ServiceDoneListItem selectedItem = getServiceDone(listPosition);
        if (selectedItem != null) {
            fillView(((ServiceViewHolder) holder).cardView, selectedItem);
        }
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    private ServiceDoneListItem getServiceDone(int position) {
        return (ServiceDoneListItem) list.get(position);
    }

    public ServiceDoneListItem getSelectedService() {
        return selectedService;
    }


    public View fillView(View view, final ServiceDoneListItem selectedItem) {

        final int typeResult = selectedItem.getServiceData().getTypeResult();

        final Animal animal = selectedItem.getAnimal();

        TextView name = (TextView) view.findViewById(R.id.list_service_done_name);
        name.setText(animal == null ? "" : selectedItem.getServiceData().getName());

        final CheckBox isresult = (CheckBox) view.findViewById(R.id.list_service_done_isresult);
        isresult.setChecked(selectedItem.getDone());
        setVisualToIsResult(isresult);
        isresult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisualToIsResult(isresult);
                selectedItem.setDone(isresult.isChecked());
                selectedService = selectedItem;
            }
        });

        EditText note = (EditText) view.findViewById(R.id.list_service_done_note);
        note.setText(String.valueOf(selectedItem.getNote() == null ? "" : selectedItem.getNote()));
        note.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                selectedItem.setNote(String.valueOf(s));
                selectedService = selectedItem;
            }
        });

        if (typeResult == TYPE_RESULT_SERVICE_FARROW) {

            EditText live = (EditText) view.findViewById(R.id.list_service_done_live);
            live.setText(selectedItem.getLive() == 0 ? "" : String.valueOf(selectedItem.getLive()));
            live.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    selectedItem.setLive(getIntFromString(s));
                    selectedService = selectedItem;
                }
            });

            EditText normal = (EditText) view.findViewById(R.id.list_service_done_normal);
            normal.setText(selectedItem.getNormal() == 0 ? "" : String.valueOf(selectedItem.getNormal()));
            normal.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    selectedItem.setNormal(getIntFromString(s));
                    selectedService = selectedItem;
                }
            });

            EditText weak = (EditText) view.findViewById(R.id.list_service_done_weak);
            weak.setText(selectedItem.getWeak() == 0 ? "" : String.valueOf(selectedItem.getWeak()));
            weak.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    selectedItem.setWeak(getIntFromString(s));
                    selectedService = selectedItem;
                }
            });

            EditText death = (EditText) view.findViewById(R.id.list_service_done_death);
            death.setText(selectedItem.getDeath() == 0 ? "" : String.valueOf(selectedItem.getDeath()));
            death.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    selectedItem.setDeath(getIntFromString(s));
                    selectedService = selectedItem;
                }
            });

            EditText mummy = (EditText) view.findViewById(R.id.list_service_done_mummy);
            mummy.setText(selectedItem.getDeath() == 0 ? "" : String.valueOf(selectedItem.getDeath()));
            mummy.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    selectedItem.setMummy(getIntFromString(s));
                    selectedService = selectedItem;
                }
            });

            EditText weight = (EditText) view.findViewById(R.id.list_service_done_weight);
            weight.setText(selectedItem.getWeight() == 0.0 ? "" : String.valueOf(selectedItem.getWeight()));
            weight.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    selectedItem.setWeight(getDoubleFromString(s));
                    selectedService = selectedItem;
                }
            });

        }else if (typeResult == TYPE_RESULT_SERVICE_INSEMINATION) {
            EditText boar1 = (EditText) view.findViewById(R.id.list_service_done_boar1);
            boar1.setText(selectedItem.getBoar1A() == null ? "" : selectedItem.getBoar1A().getFullNameType(context));
            boar1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, AnimalsActivity.class);
                    intent.putExtra(ANIMALS_ACTIVITY_TYPE_ANIMAL, TYPE_GROUP_ANIMAL_BOAR);
                    intent.putExtra(ANIMALS_ACTIVITY_OPEN_FOR_RESULT, true);
                    ((Activity) context).startActivityForResult(intent, CAPTURE_SELECT_BOAR1_FRAGMENT_REQ);
                    selectedService = selectedItem;
                }
            });

            EditText boar2 = (EditText) view.findViewById(R.id.list_service_done_boar2);
            boar2.setText(selectedItem.getBoar2A() == null ? "" : selectedItem.getBoar2A().getFullNameType(context));
            boar2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, AnimalsActivity.class);
                    intent.putExtra(ANIMALS_ACTIVITY_TYPE_ANIMAL, TYPE_GROUP_ANIMAL_BOAR);
                    intent.putExtra(ANIMALS_ACTIVITY_OPEN_FOR_RESULT, true);
                    ((Activity) context).startActivityForResult(intent, CAPTURE_SELECT_BOAR2_FRAGMENT_REQ);
                    selectedService = selectedItem;
                }
            });

            EditText boar3 = (EditText) view.findViewById(R.id.list_service_done_boar1);
            boar3.setText(selectedItem.getBoar3A() == null ? "" : selectedItem.getBoar3A().getFullNameType(context));
            boar3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, AnimalsActivity.class);
                    intent.putExtra(ANIMALS_ACTIVITY_TYPE_ANIMAL, TYPE_GROUP_ANIMAL_BOAR);
                    intent.putExtra(ANIMALS_ACTIVITY_OPEN_FOR_RESULT, true);
                    ((Activity) context).startActivityForResult(intent, CAPTURE_SELECT_BOAR3_FRAGMENT_REQ);
                    selectedService = selectedItem;
                }
            });

            final Switch aSwitch = (Switch) view.findViewById(R.id.list_service_artif_insem);
            aSwitch.setChecked(selectedItem.isArtifInsemen());
            aSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedItem.setArtifInsemen(aSwitch.isChecked());
                    selectedService = selectedItem;
                }
            });

        }else if (typeResult == TYPE_RESULT_SERVICE_MOVEMENT || typeResult == TYPE_RESULT_SERVICE_MOVEMENT_GROUP) {

            LinearLayout toGroupLL = (LinearLayout) view.findViewById(R.id.list_service_done_to_group_ll);
            toGroupLL.setVisibility(typeResult == TYPE_RESULT_SERVICE_MOVEMENT && selectedItem.getTecnGroupToA() != null ? View.VISIBLE : View.GONE);

            EditText toGroup = (EditText) view.findViewById(R.id.list_service_done_to_group);
            toGroup.setText(String.valueOf(selectedItem.getTecnGroupToA() == null ? "" : selectedItem.getTecnGroupToA().getFullNameType(context)));
            toGroup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, TecnikalGroupeAnimalActivity.class);
                    intent.putExtra(GROUPE_ANIMAL_ACTIVITY_TYPE_ANIMAL, typeResult);
                    ((Activity) context).startActivityForResult(intent, CAPTURE_SELECT_GROUP_TO_REQ);
                    selectedService = selectedItem;
                }
            });

            EditText number = (EditText) view.findViewById(R.id.list_service_done_number);
            number.setVisibility(selectedItem.getAnimal() == null ?
                    View.GONE : selectedItem.getAnimal().isGroup() ? View.VISIBLE : View.GONE);
            number.setText(selectedItem.getNumber() == 0 ? "" : String.valueOf(selectedItem.getNumber()));
            number.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    selectedItem.setNumber(getIntFromString(s));
                    selectedService = selectedItem;
                }
            });

            EditText weight = (EditText) view.findViewById(R.id.list_service_done_weight);
            weight.setText(selectedItem.getWeight() == 0.0 ? "" : String.valueOf(selectedItem.getWeight()));
            weight.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    selectedItem.setWeight(getDoubleFromString(s));
                    selectedService = selectedItem;
                    selectedService.setChanged(true);
                }
            });

        }else if (typeResult == TYPE_RESULT_SERVICE_SELECTION ){
            EditText toGroup = (EditText) view.findViewById(R.id.list_service_done_to_group);
            toGroup.setText(String.valueOf(selectedItem.getTecnGroupToA() == null ? "" : selectedItem.getTecnGroupToA().getFullNameType(context)));
            toGroup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, TecnikalGroupeAnimalActivity.class);
                    intent.putExtra(GROUPE_ANIMAL_ACTIVITY_TYPE_ANIMAL, selectedItem.getAnimal().getTypeAnimal());
                    ((Activity) context).startActivityForResult(intent, ServiceActivity.CAPTURE_SELECT_GROUP_TO_REQ);
                    selectedService = selectedItem;
                }
            });

            EditText toGroupAnimal = (EditText) view.findViewById(R.id.list_service_done_to_group_animal);
            toGroupAnimal.setText(String.valueOf(selectedItem.getAnimalGroupToA() == null ? "" : selectedItem.getAnimalGroupToA().getFullNameType(context)));
            toGroupAnimal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, GroupeAnimalActivity.class);
                    intent.putExtra(GROUPE_ANIMAL_ACTIVITY_TYPE_ANIMAL, TYPE_GROUP_ANIMAL_ALL);
                    ((Activity) context).startActivityForResult(intent, ServiceActivity.CAPTURE_SELECT_GROUP_ANIMAL_TO_REQ);
                    selectedService = selectedItem;
                }
            });

            EditText number = (EditText) view.findViewById(R.id.list_service_done_number);
            number.setVisibility(selectedItem.getAnimal() == null ?
                    View.GONE : selectedItem.getAnimal().isGroupAnimal() ? View.VISIBLE : View.GONE);
            number.setText(selectedItem.getNumber() == 0 ? "" : String.valueOf(selectedItem.getNumber()));
            number.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    selectedItem.setNumber(getIntFromString(s));
                    selectedService = selectedItem;
                    selectedService.setChanged(true);
                }
            });

            EditText weight = (EditText) view.findViewById(R.id.list_service_done_weight);
            weight.setText(selectedItem.getWeight() == 0.0 ? "" : String.valueOf(selectedItem.getWeight()));
            weight.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    selectedItem.setWeight(getDoubleFromString(s));
                    selectedService = selectedItem;
                    selectedService.setChanged(true);
                }
            });

        }else if (typeResult == TYPE_RESULT_SERVICE_DISTILLATION) {
            EditText toCorp = (EditText) view.findViewById(R.id.list_service_done_to_corp);
            toCorp.setText(selectedItem.getCorpToH() == null ? "" : selectedItem.getCorpToH().getName());
            toCorp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, HousingActivity.class);
                    intent.putExtra(HOUSING_ACTIVITY_TYPE_HOUSING, TYPE_HOUSING_CORP);
                    intent.putExtra(HOUSING_ACTIVITY_PARENT_ID,
                            selectedItem.getCorpToH()!=null ? selectedItem.getCorpToH().getParentId() : null);
                    ((Activity) context).startActivityForResult(intent, CAPTURE_SELECT_HOUSING_CORP_TO_REQ);
                    selectedService = selectedItem;
                }
            });

            EditText toSector = (EditText) view.findViewById(R.id.list_service_done_to_sector);
            toSector.setText(selectedItem.getSectorToH() == null ? "" : selectedItem.getSectorToH().getName());
            toSector.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, HousingActivity.class);
                    intent.putExtra(HOUSING_ACTIVITY_TYPE_HOUSING, TYPE_HOUSING_SECTOR);
                    intent.putExtra(HOUSING_ACTIVITY_PARENT_ID,
                            selectedItem.getSectorToH() != null ? selectedItem.getSectorToH().getParentId() :
                                    (selectedItem.getCorpToH() != null ? selectedItem.getCorpToH().getExternalId() : null));
                    ((Activity) context).startActivityForResult(intent, CAPTURE_SELECT_HOUSING_SECTOR_TO_REQ);
                    selectedService = selectedItem;
                }
            });

            EditText toCell = (EditText) view.findViewById(R.id.list_service_done_to_cell);
            toCell.setText(selectedItem.getCellToH() == null ? "" : selectedItem.getCellToH().getName());
            toCell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, HousingActivity.class);
                    intent.putExtra(HOUSING_ACTIVITY_TYPE_HOUSING, TYPE_HOUSING_SECTOR);
                    intent.putExtra(HOUSING_ACTIVITY_PARENT_ID,
                            selectedItem.getCellToH() != null ? selectedItem.getCellToH().getParentId() :
                                    (selectedItem.getSectorToH() != null ? selectedItem.getSectorToH().getExternalId() : null));
                    ((Activity) context).startActivityForResult(intent, CAPTURE_SELECT_HOUSING_CELL_TO_REQ);
                    selectedService = selectedItem;
                }
            });

            EditText number = (EditText) view.findViewById(R.id.list_service_done_number);
            number.setVisibility(selectedItem.getAnimal() == null ?
                    View.GONE : selectedItem.getAnimal().isGroup() ? View.VISIBLE : View.GONE);
            number.setText(selectedItem.getNumber() == 0 ? "" : String.valueOf(selectedItem.getNumber()));
            number.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    selectedItem.setNumber(getIntFromString(s));
                    selectedService = selectedItem;
                }
            });
        }else if (typeResult == TYPE_RESULT_SERVICE_WEANIMG) {
            EditText number = (EditText) view.findViewById(R.id.list_service_done_number);
            number.setText(selectedItem.getNumber() == 0 ? "" : String.valueOf(selectedItem.getNumber()));
            number.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    selectedItem.setNumber(getIntFromString(s));
                    selectedService = selectedItem;
                }
            });

            EditText weight = (EditText) view.findViewById(R.id.list_service_done_weight);
            weight.setText(selectedItem.getWeight() == 0.0 ? "" : String.valueOf(selectedItem.getWeight()));
            weight.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    selectedItem.setWeight(getDoubleFromString(s));
                    selectedService = selectedItem;
                }
            });
        }else if (typeResult == TYPE_RESULT_SERVICE_REGISTRATION) {

            EditText toGroup = (EditText) view.findViewById(R.id.list_service_done_to_group);
            toGroup.setText(String.valueOf(selectedItem.getAnimalGroupToA() == null ? "" : selectedItem.getAnimalGroupToA().getFullNameType(context)));
            toGroup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, GroupeAnimalActivity.class);
                    intent.putExtra(GROUPE_ANIMAL_ACTIVITY_TYPE_ANIMAL, typeResult);
                    ((Activity) context).startActivityForResult(intent, CAPTURE_SELECT_GROUP_ANIMAL_TO_REQ);
                    selectedService = selectedItem;
                }
            });

            EditText toCorp = (EditText) view.findViewById(R.id.list_service_done_to_corp);
            toCorp.setText(selectedItem.getCorpToH() == null ? "" : selectedItem.getCorpToH().getName());
            toCorp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, HousingActivity.class);
                    intent.putExtra(HOUSING_ACTIVITY_TYPE_HOUSING, TYPE_HOUSING_CORP);
                    intent.putExtra(HOUSING_ACTIVITY_PARENT_ID,
                            selectedItem.getCorpToH()!=null ? selectedItem.getCorpToH().getParentId() : null);
                    ((Activity) context).startActivityForResult(intent, CAPTURE_SELECT_HOUSING_CORP_TO_REQ);
                    selectedService = selectedItem;
                }
            });

            EditText toSector = (EditText) view.findViewById(R.id.list_service_done_to_sector);
            toSector.setText(selectedItem.getSectorToH() == null ? "" : selectedItem.getSectorToH().getName());
            toSector.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, HousingActivity.class);
                    intent.putExtra(HOUSING_ACTIVITY_TYPE_HOUSING, TYPE_HOUSING_SECTOR);
                    intent.putExtra(HOUSING_ACTIVITY_PARENT_ID,
                            selectedItem.getSectorToH() != null ? selectedItem.getSectorToH().getParentId() :
                                    (selectedItem.getCorpToH() != null ? selectedItem.getCorpToH().getExternalId() : null));
                    ((Activity) context).startActivityForResult(intent, CAPTURE_SELECT_HOUSING_SECTOR_TO_REQ);
                    selectedService = selectedItem;
                }
            });

            EditText toCell = (EditText) view.findViewById(R.id.list_service_done_to_cell);
            toCell.setText(selectedItem.getCellToH() == null ? "" : selectedItem.getCellToH().getName());
            toCell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, HousingActivity.class);
                    intent.putExtra(HOUSING_ACTIVITY_TYPE_HOUSING, TYPE_HOUSING_SECTOR);
                    intent.putExtra(HOUSING_ACTIVITY_PARENT_ID,
                            selectedItem.getCellToH() != null ? selectedItem.getCellToH().getParentId() :
                                    (selectedItem.getSectorToH() != null ? selectedItem.getSectorToH().getExternalId() : null));
                    ((Activity) context).startActivityForResult(intent, CAPTURE_SELECT_HOUSING_CELL_TO_REQ);
                    selectedService = selectedItem;
                }
            });

            EditText weight = (EditText) view.findViewById(R.id.list_service_done_weight);
            weight.setText(selectedItem.getWeight() == 0.0 ? "" : String.valueOf(selectedItem.getWeight()));
            weight.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    selectedItem.setWeight(getDoubleFromString(s));
                    selectedService = selectedItem;
                }
            });

            EditText admNum = (EditText) view.findViewById(R.id.list_service_done_adm_num);
            admNum.setText(selectedItem.getAdmNumber() == 0 ? "" : String.valueOf(selectedItem.getAdmNumber()));
            admNum.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    selectedItem.setAdmNumber(getIntFromString(s));
                    selectedService = selectedItem;
                }
            });

        }else if (typeResult == TYPE_RESULT_SERVICE_INSPECTION) {
            final EditText statusAnimal = (EditText) view.findViewById(R.id.list_service_done_status_animal);
            statusAnimal.setText(selectedItem.getStatusA()==null ? "" : selectedItem.getStatusA().getName());
            statusAnimal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedService = selectedItem;
                    ((AnimalActivity) context).showeQuestionSelectStatusAnimal(selectedService, statusAnimal);
                }
            });
        }else if (typeResult == TYPE_RESULT_SERVICE_RETIREMENT) {
            final EditText typeDisposAnimal = (EditText) view.findViewById(R.id.list_service_done_type_dispos_anim);
            typeDisposAnimal.setText(selectedItem.getDisposAnimA()==null ? "" : selectedItem.getDisposAnimA().getName());
            typeDisposAnimal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedService = selectedItem;
                    ((AnimalActivity) context).showeQuestionSelectDisposAnimal(selectedService, typeDisposAnimal);
                }
            });

        }else if (typeResult == TYPE_RESULT_SERVICE_ASSESSMENT) {

            EditText weight = (EditText) view.findViewById(R.id.list_service_done_weight);
            weight.setText(selectedItem.getWeight() == 0.0 ? "" : String.valueOf(selectedItem.getWeight()));
            weight.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    selectedItem.setWeight(getDoubleFromString(s));
                    selectedService = selectedItem;
                }
            });

            EditText lenght = (EditText) view.findViewById(R.id.list_service_done_lenght);
            lenght.setText(selectedItem.getLength() == 0 ? "" : String.valueOf(selectedItem.getLength()));
            lenght.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    selectedItem.setLength(getIntFromString(s));
                    selectedService = selectedItem;
                }
            });

            EditText breadTrikness = (EditText) view.findViewById(R.id.list_service_done_bread_trikness);
            breadTrikness.setText(selectedItem.getBread() == 0 ? "" : String.valueOf(selectedItem.getBread()));
            breadTrikness.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    selectedItem.setBread(getIntFromString(s));
                    selectedService = selectedItem;
                }
            });

            EditText depthMysz = (EditText) view.findViewById(R.id.list_service_done_depth_mysz);
            depthMysz.setText(selectedItem.getDepthMysz() == 0 ? "" : String.valueOf(selectedItem.getDepthMysz()));
            depthMysz.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    selectedItem.setDepthMysz(getIntFromString(s));
                    selectedService = selectedItem;
                }
            });

            EditText exterior = (EditText) view.findViewById(R.id.list_service_done_exterior);
            exterior.setText(selectedItem.getExterior() == 0 ? "" : String.valueOf(selectedItem.getExterior()));
            exterior.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    selectedItem.setExterior(getIntFromString(s));
                    selectedService = selectedItem;
                }
            });

        }else if (typeResult == TYPE_RESULT_SERVICE_NEW_NUMBER) {

            EditText weight = (EditText) view.findViewById(R.id.list_service_done_new_number);
            weight.setText(selectedItem.getNewCode());
            weight.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    selectedItem.setNewCode(String.valueOf(s));
                    selectedService = selectedItem;
                }
            });

        }else if (typeResult == TYPE_RESULT_SERVICE_WEIGNING) {
            LinearLayout toGroupLL = (LinearLayout) view.findViewById(R.id.list_service_done_to_group_ll);
            toGroupLL.setVisibility(View.GONE);
            EditText toGroup = (EditText) view.findViewById(R.id.list_service_done_to_group);
            toGroup.setText(String.valueOf(selectedItem.getTecnGroupToA() == null ? "" : selectedItem.getTecnGroupToA().getFullNameType(context)));
            toGroup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, TecnikalGroupeAnimalActivity.class);
                    intent.putExtra(GROUPE_ANIMAL_ACTIVITY_TYPE_ANIMAL, selectedItem.getAnimal().getTypeAnimal());
                    ((Activity) context).startActivityForResult(intent, ServiceActivity.CAPTURE_SELECT_GROUP_TO_REQ);
                    selectedService = selectedItem;
                }
            });

            EditText number = (EditText) view.findViewById(R.id.list_service_done_number);
            number.setVisibility(selectedItem.getAnimal() == null ?
                    View.GONE : selectedItem.getAnimal().isGroupAnimal() ? View.VISIBLE : View.GONE);
            number.setText(selectedItem.getNumber() == 0 ? "" : String.valueOf(selectedItem.getNumber()));
            number.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    selectedItem.setNumber(getIntFromString(s));
                    selectedService = selectedItem;
                }
            });

            EditText weight = (EditText) view.findViewById(R.id.list_service_done_weight);
            weight.setText(selectedItem.getWeight() == 0.0 ? "" : String.valueOf(selectedItem.getWeight()));
            weight.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    selectedItem.setWeight(getDoubleFromString(s));
                    selectedService = selectedItem;
                }
            });

        }else if (typeResult == TYPE_RESULT_SERVICE_MOVEMENT_NO_NUMBER) {
            LinearLayout toGroupLL = (LinearLayout) view.findViewById(R.id.list_service_done_to_group_ll);
            toGroupLL.setVisibility(View.VISIBLE);
            EditText toGroup = (EditText) view.findViewById(R.id.list_service_done_to_group);
            toGroup.setText(String.valueOf(selectedItem.getTecnGroupToA() == null ? "" : selectedItem.getTecnGroupToA().getFullNameType(context)));
            toGroup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((AnimalActivity)context).showeQuestionSelectGroupActivity();
                    selectedService = selectedItem;
                }
            });

            EditText number = (EditText) view.findViewById(R.id.list_service_done_number);
            number.setVisibility(selectedItem.getAnimal() == null ?
                    View.GONE : selectedItem.getAnimal().isGroupAnimal() ? View.VISIBLE : View.GONE);
            number.setText(selectedItem.getNumber() == 0 ? "" : String.valueOf(selectedItem.getNumber()));
            number.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    selectedItem.setNumber(getIntFromString(s));
                    selectedService = selectedItem;
                }
            });

            EditText weight = (EditText) view.findViewById(R.id.list_service_done_weight);
            weight.setText(selectedItem.getWeight() == 0.0 ? "" : String.valueOf(selectedItem.getWeight()));
            weight.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    selectedItem.setWeight(getDoubleFromString(s));
                    selectedService = selectedItem;
                }
            });

        }else if (typeResult == TYPE_RESULT_SERVICE_VETERINARY) {
            final EditText vetExercise = (EditText) view.findViewById(R.id.list_service_done_vet_exercise);
//            vetExercise.setText(selectedItem.getVetExercise() == null ? "" : selectedItem.getVetExercise().getName());
            vetExercise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedService = selectedItem;
                    ((AnimalActivity)context).showeQuestionSelectVetExercise(selectedService, vetExercise);
                }
            });

            final EditText vetPreparat = (EditText) view.findViewById(R.id.list_service_done_vet_preparat);
//            vetPreparat.setText(selectedItem.getVetPreparat() == null ? "" : selectedItem.getVetPreparat().getName());
            vetPreparat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedService = selectedItem;
                    ((AnimalActivity)context).showeQuestionSelectVetPreparat(selectedService, vetPreparat);
                }
            });
        }else{
//            final EditText resultService = (EditText) view.findViewById(R.id.list_service_done_result_service);
//            resultService.setText(selectedItem.getResultServiceS() == null ? "" : selectedItem.getResultServiceS().getName());
//            resultService.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    List<String> serviceList = new ArrayList<String>();
//
//                    final List<ServiceData> servicesNotInList = ((AnimalActivity)context).getListServiceData();
//
//                    for (ServiceData serviceData : servicesNotInList) {
//                        serviceList.add(serviceData.getName());
//                    }
//
//                    ((AnimalActivity)context).getActivityUtils().showSelectionList(((AnimalActivity)context),
//                            context.getString(R.string.select_from_the_list), serviceList,
//                            new ActivityUtils.ListItemClick() {
//                                @Override
//                                public void onItemClik(int item, String text) {
//                                    selectedItem.setResultService(servicesNotInList.get(item));
//                                    resultService.setText(selectedItem.getResultServiceS() == null ? "" : selectedItem.getResultServiceS().getName());
//                                }
//                            });
//
//                    selectedService = selectedItem;
//
//                }
//            });
        }

        return view;
    }

    private void setVisualToIsResult(CheckBox isresult){
        if(!isresult.isChecked()){
            isresult.setTextColor(context.getResources().getColor(android.R.color.holo_red_light));
            isresult.setText(context.getResources().getString(R.string.service_item_not_done));
        }else{
            isresult.setTextColor(context.getResources().getColor(R.color.colorAccent));
            isresult.setText(context.getResources().getString(R.string.service_item_done));
        }
    }

    private int getIntFromString(Editable s){
        String text = String.valueOf(s);
        return Integer.valueOf(String.valueOf(text==null || text.isEmpty() ? "0" : s));
    }
    private Double getDoubleFromString(Editable s){
        String text = String.valueOf(s);
        return Double.valueOf(String.valueOf(text==null || text.isEmpty() ? "0" : s));
    }
}
