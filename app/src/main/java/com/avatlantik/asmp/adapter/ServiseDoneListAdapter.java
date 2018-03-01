package com.avatlantik.asmp.adapter;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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
import com.avatlantik.asmp.model.db.ServiceDoneVetExercise;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.avatlantik.asmp.activity.AnimalActivity.ANIMAL_ACTIVITY_PARAM_ANIMAL_ID;
import static com.avatlantik.asmp.activity.AnimalsActivity.ANIMALS_ACTIVITY_OPEN_FOR_RESULT;
import static com.avatlantik.asmp.activity.AnimalsActivity.ANIMALS_ACTIVITY_TYPE_ANIMAL;
import static com.avatlantik.asmp.activity.HousingActivity.HOUSING_ACTIVITY_PARENT_ID;
import static com.avatlantik.asmp.activity.HousingActivity.HOUSING_ACTIVITY_TYPE_HOUSING;
import static com.avatlantik.asmp.activity.ServiceActivity.CAPTURE_SELECT_BOAR1_ACTIVITY_REQ;
import static com.avatlantik.asmp.activity.ServiceActivity.CAPTURE_SELECT_BOAR2_ACTIVITY_REQ;
import static com.avatlantik.asmp.activity.ServiceActivity.CAPTURE_SELECT_BOAR3_ACTIVITY_REQ;
import static com.avatlantik.asmp.activity.ServiceActivity.CAPTURE_SELECT_GROUP_ANIMAL_TO_REQ;
import static com.avatlantik.asmp.activity.ServiceActivity.CAPTURE_SELECT_GROUP_TO_REQ;
import static com.avatlantik.asmp.activity.ServiceActivity.CAPTURE_SELECT_HOUSING_CELL_TO_REQ;
import static com.avatlantik.asmp.activity.ServiceActivity.CAPTURE_SELECT_HOUSING_CORP_TO_REQ;
import static com.avatlantik.asmp.activity.ServiceActivity.CAPTURE_SELECT_HOUSING_SECTOR_TO_REQ;
import static com.avatlantik.asmp.activity.TecnikalGroupeAnimalActivity.GROUPE_ANIMAL_ACTIVITY_TYPE_ANIMAL;
import static com.avatlantik.asmp.common.Consts.TYPE_GROUP_ANIMAL_ALL;
import static com.avatlantik.asmp.common.Consts.TYPE_GROUP_ANIMAL_BOAR;
import static com.avatlantik.asmp.common.Consts.TYPE_HOUSING_CELL;
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

public class ServiseDoneListAdapter extends BaseAdapter implements Filterable{

    private static final int ANIM_DURATION = 200;

    private List<ServiceDoneListItem> modelValues;
    private List<ServiceDoneListItem> mOriginalValues;
    private LayoutInflater layoutInflater;
    private int typeResult;
    private Context context;
    private ServiceDoneListItem selectedService;
    private Boolean changedItem;
    private Boolean filterByName;
    private PopupWindow window;
    private View selectedView;
    private int mLeftDelta;
    private int mTopDelta;
    private float mWidthScale;
    private float mHeightScale;

    public ServiseDoneListAdapter(Context context, List<ServiceDoneListItem> list, int typeResult) {
        this.context = context;
        this.modelValues = list;
        this.typeResult = typeResult;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.changedItem = false;
        this.filterByName = false;
    }

    public ServiceDoneListItem getSelectedService() {
        return selectedService;
    }

    public Boolean isDataChanged() {
        return changedItem;
    }

    public void setDataChanged(Boolean changedItem) {
        this.changedItem = changedItem;
    }

    public Boolean isFilterByName() {
        return filterByName;
    }

    public void setFilterByName(Boolean filterByName) {
        this.filterByName = filterByName;
    }

    public View getSelectedView() {
        return selectedView;
    }

    @Override
    public int getCount() {
        return modelValues.size();
    }

    @Override
    public Object getItem(int position) {
        return modelValues.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.service_item, parent, false);
        }
        ServiceDoneListItem selectedItem = getServiceDone(position);

        return fillCapView(view, selectedItem);
    }

    private ServiceDoneListItem getServiceDone(int position) {
        return (ServiceDoneListItem) getItem(position);
    }

    public View fillCapView(final View view, final ServiceDoneListItem selectedItem) {

        final Animal animal = selectedItem.getAnimal();

        if(animal==null) return null;

        ImageView openAnimal = (ImageView) view.findViewById(R.id.list_service_done_open);
        openAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AnimalActivity.class);
                intent.putExtra(ANIMAL_ACTIVITY_PARAM_ANIMAL_ID, animal.getExternalId());
                context.startActivity(intent);
            }
        });

        final ImageView more = (ImageView) view.findViewById(R.id.list_service_done_more);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showWindowPopup(view, selectedItem);

            }
        });

        final LinearLayout nameLayout = (LinearLayout) view.findViewById(R.id.list_service_done_name_ll);
        nameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showWindowPopup(view, selectedItem);

            }
        });

        final LinearLayout capGroupLayout = (LinearLayout) view.findViewById(R.id.list_service_done_cap_group_ll);
        capGroupLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showWindowPopup(view, selectedItem);

            }
        });

        TextView type = (TextView) view.findViewById(R.id.list_service_done_name);
        type.setText(animal == null ? "" : animal.getFullNameType(context));

        TextView group = (TextView) view.findViewById(R.id.list_service_done_group);
        group.setVisibility(selectedItem.getAnimal() == null ?
                View.GONE : selectedItem.getAnimal().isGroupAnimal() ? View.GONE : View.VISIBLE);
        group.setText(String.valueOf(selectedItem.getGroup() == null ? "" : selectedItem.getGroup().getName()));

        TextView groupName = (TextView) view.findViewById(R.id.list_service_done_group_name);
        groupName.setVisibility(selectedItem.getAnimal() == null ?
                View.GONE : selectedItem.getAnimal().isGroupAnimal() ? View.GONE : View.VISIBLE);

        final CheckBox isresult = (CheckBox) view.findViewById(R.id.list_service_done_isresult);
        isresult.setChecked(selectedItem.getDone());
        setVisualToIsResult(isresult);
        isresult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisualToIsResult(isresult);
                selectedItem.setDone(isresult.isChecked());
                selectedService = selectedItem;
                selectedService.setChanged(true);
                changedItem = true;
            }
        });

        return view;
    }

    public void showWindowPopup(final View view, final ServiceDoneListItem selectedItem){

        if (typeResult == TYPE_RESULT_SERVICE_DISTILLATION) {
            selectedView = layoutInflater.inflate(R.layout.service_item_distillation, null);
        }else if (typeResult == TYPE_RESULT_SERVICE_FARROW) {
            selectedView = layoutInflater.inflate(R.layout.service_item_farrowing, null);
        }else if (typeResult == TYPE_RESULT_SERVICE_INSEMINATION) {
            selectedView = layoutInflater.inflate(R.layout.service_item_insemination, null);
        }else if (typeResult == TYPE_RESULT_SERVICE_MOVEMENT ||
                typeResult == TYPE_RESULT_SERVICE_MOVEMENT_GROUP ||
                typeResult == TYPE_RESULT_SERVICE_WEIGNING ||
                typeResult == TYPE_RESULT_SERVICE_MOVEMENT_NO_NUMBER) {
            selectedView = layoutInflater.inflate(R.layout.service_item_movement, null);
        }else if (typeResult == TYPE_RESULT_SERVICE_WEANIMG) {
            selectedView = layoutInflater.inflate(R.layout.service_item_weaning, null);
        }else if (typeResult == TYPE_RESULT_SERVICE_REGISTRATION) {
            selectedView = layoutInflater.inflate(R.layout.service_item_registration, null);
        }else if (typeResult == TYPE_RESULT_SERVICE_INSPECTION) {
            selectedView = layoutInflater.inflate(R.layout.service_item_inspection, null);
        }else if (typeResult == TYPE_RESULT_SERVICE_RETIREMENT) {
            selectedView = layoutInflater.inflate(R.layout.service_item_retirement, null);
        }else if (typeResult == TYPE_RESULT_SERVICE_ASSESSMENT) {
            selectedView = layoutInflater.inflate(R.layout.service_item_assesment, null);
        }else if (typeResult == TYPE_RESULT_SERVICE_NEW_NUMBER) {
            selectedView = layoutInflater.inflate(R.layout.service_item_new_number, null);
        }else if (typeResult == TYPE_RESULT_SERVICE_VETERINARY) {
            selectedView = layoutInflater.inflate(R.layout.service_item_veterinary, null);
        }else if (typeResult == TYPE_RESULT_SERVICE_SELECTION) {
            selectedView = layoutInflater.inflate(R.layout.service_item_selection, null);
        }else {
            selectedView = layoutInflater.inflate(R.layout.service_item_default, null);
        }


        if(window!=null){
            if(window.isShowing()){
                window.dismiss();
            }
        }

        window = new PopupWindow(selectedView, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setFocusable(true);
        window.setOutsideTouchable(true);
        window.update();
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                fillCapView(view, selectedItem);
            }
        });

        window.showAtLocation(view, Gravity.CENTER, 0, 0);
        enterAnimation(view, selectedView);

        fillView(selectedView, selectedItem);
    }


    public View fillView(final View view, final ServiceDoneListItem selectedItem) {

        final Animal animal = selectedItem.getAnimal();

        if(animal==null) return null;

        ImageView openAnimal = (ImageView) view.findViewById(R.id.list_service_done_open);
        openAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AnimalActivity.class);
                intent.putExtra(ANIMAL_ACTIVITY_PARAM_ANIMAL_ID, animal.getExternalId());
                context.startActivity(intent);
            }
        });

        final ImageView more = (ImageView) view.findViewById(R.id.list_service_done_more);
        more.setImageResource(R.drawable.ic_close);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(window!=null){
                    if(window.isShowing()){
                        exitAnimation(view, new Runnable() {
                            public void run() {
                                window.dismiss();
                            }
                        });
                    }
                }
            }
        });

        TextView type = (TextView) view.findViewById(R.id.list_service_done_name);
        type.setText(animal == null ? "" : animal.getFullNameType(context));

        TextView group = (TextView) view.findViewById(R.id.list_service_done_group);
        group.setVisibility(selectedItem.getAnimal() == null ?
                View.GONE : selectedItem.getAnimal().isGroupAnimal() ? View.GONE : View.VISIBLE);
        group.setText(String.valueOf(selectedItem.getGroup() == null ? "" : selectedItem.getGroup().getName()));

        TextView groupName = (TextView) view.findViewById(R.id.list_service_done_group_name);
        groupName.setVisibility(selectedItem.getAnimal() == null ?
                View.GONE : selectedItem.getAnimal().isGroupAnimal() ? View.GONE : View.VISIBLE);

        TextView rfid = (TextView) view.findViewById(R.id.list_service_done_rfid);
        rfid.setText(String.valueOf(animal == null ? "" : animal.getRfid()));

        final CheckBox isresult = (CheckBox) view.findViewById(R.id.list_service_done_isresult);
        isresult.setChecked(selectedItem.getDone());
        setVisualToIsResult(isresult);
        isresult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisualToIsResult(isresult);
                selectedItem.setDone(isresult.isChecked());
                selectedService = selectedItem;
                selectedService.setChanged(true);
                changedItem = true;
            }
        });

        TextView housing = (TextView) view.findViewById(R.id.list_service_done_housing);
        housing.setText(String.valueOf(selectedItem.getHousing()));

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
                selectedService.setChanged(true);
                changedItem = true;
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
                    selectedItem.setLive(getIntForString(s));
                    selectedService = selectedItem;
                    selectedService.setChanged(true);
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
                    selectedItem.setNormal(getIntForString(s));
                    selectedService = selectedItem;
                    selectedService.setChanged(true);
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
                    selectedItem.setWeak(getIntForString(s));
                    selectedService = selectedItem;
                    selectedService.setChanged(true);
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
                    selectedItem.setDeath(getIntForString(s));
                    selectedService = selectedItem;
                    selectedService.setChanged(true);
                }
            });

            EditText mummy = (EditText) view.findViewById(R.id.list_service_done_mummy);
            mummy.setText(selectedItem.getMummy() == 0 ? "" : String.valueOf(selectedItem.getMummy()));
            mummy.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    selectedItem.setMummy(getIntForString(s));
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
                    selectedItem.setWeight(getDoubleForString(s));
                    selectedService = selectedItem;
                    selectedService.setChanged(true);
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
                    ((Activity) context).startActivityForResult(intent, CAPTURE_SELECT_BOAR1_ACTIVITY_REQ);
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
                    ((Activity) context).startActivityForResult(intent, CAPTURE_SELECT_BOAR2_ACTIVITY_REQ);
                    selectedService = selectedItem;
                }
            });

            EditText boar3 = (EditText) view.findViewById(R.id.list_service_done_boar3);
            boar3.setText(selectedItem.getBoar3A() == null ? "" : selectedItem.getBoar3A().getFullNameType(context));
            boar3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, AnimalsActivity.class);
                    intent.putExtra(ANIMALS_ACTIVITY_TYPE_ANIMAL, TYPE_GROUP_ANIMAL_BOAR);
                    intent.putExtra(ANIMALS_ACTIVITY_OPEN_FOR_RESULT, true);
                    ((Activity) context).startActivityForResult(intent, CAPTURE_SELECT_BOAR3_ACTIVITY_REQ);
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
        }else if (typeResult == TYPE_RESULT_SERVICE_MOVEMENT ||
                typeResult == TYPE_RESULT_SERVICE_MOVEMENT_GROUP) {
            LinearLayout toGroupLL = (LinearLayout) view.findViewById(R.id.list_service_done_to_group_ll);
            toGroupLL.setVisibility(typeResult == TYPE_RESULT_SERVICE_MOVEMENT && selectedItem.getTecnGroupToA() != null ? View.VISIBLE : View.GONE);
            EditText toGroup = (EditText) view.findViewById(R.id.list_service_done_to_group);
            toGroup.setText(String.valueOf(selectedItem.getTecnGroupToA() == null ? "" : selectedItem.getTecnGroupToA().getFullNameType(context)));
            toGroup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, TecnikalGroupeAnimalActivity.class);
                    intent.putExtra(GROUPE_ANIMAL_ACTIVITY_TYPE_ANIMAL, selectedItem.getAnimal().getTypeAnimal());
                    ((Activity) context).startActivityForResult(intent, CAPTURE_SELECT_GROUP_TO_REQ);
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
                    selectedItem.setNumber(getIntForString(s));
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
                    selectedItem.setWeight(getDoubleForString(s));
                    selectedService = selectedItem;
                    selectedService.setChanged(true);
                }
            });

        }else if (typeResult == TYPE_RESULT_SERVICE_SELECTION ){
            EditText toGroup = (EditText) view.findViewById(R.id.list_service_done_to_group);
            toGroup.setText(String.valueOf(selectedItem.getTecnGroupToA() == null ? "" : selectedItem.getTecnGroupToA().getFullNameType(context)));
            toGroup.setVisibility(animal.isGroupAnimal() ? View.GONE : View.VISIBLE);
            toGroup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, TecnikalGroupeAnimalActivity.class);
                    intent.putExtra(GROUPE_ANIMAL_ACTIVITY_TYPE_ANIMAL, TYPE_GROUP_ANIMAL_ALL);
                    ((Activity) context).startActivityForResult(intent, CAPTURE_SELECT_GROUP_TO_REQ);
                    selectedService = selectedItem;
                }
            });

            EditText toGroupAnimal = (EditText) view.findViewById(R.id.list_service_done_to_group_animal);
            toGroupAnimal.setText(String.valueOf(selectedItem.getAnimalGroupToA() == null ? "" : selectedItem.getAnimalGroupToA().getFullNameType(context)));
            toGroupAnimal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, GroupeAnimalActivity.class);
                    intent.putExtra(GROUPE_ANIMAL_ACTIVITY_TYPE_ANIMAL, selectedItem.getAnimal().getTypeAnimal());
                    ((Activity) context).startActivityForResult(intent, CAPTURE_SELECT_GROUP_ANIMAL_TO_REQ);
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
                    selectedItem.setNumber(getIntForString(s));
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
                    selectedItem.setWeight(getDoubleForString(s));
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
                    View.GONE : selectedItem.getAnimal().isGroupAnimal() ? View.VISIBLE : View.GONE);
            number.setText(selectedItem.getNumber() == 0 ? "" : String.valueOf(selectedItem.getNumber()));
            number.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    selectedItem.setNumber(getIntForString(s));
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
                    selectedItem.setWeight(getDoubleForString(s));
                    selectedService = selectedItem;
                    selectedService.setChanged(true);
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
                    selectedItem.setNumber(getIntForString(s));
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
                    selectedItem.setWeight(getDoubleForString(s));
                    selectedService = selectedItem;
                    selectedService.setChanged(true);
                }
            });
        }else if (typeResult == TYPE_RESULT_SERVICE_REGISTRATION) {


            EditText toGroup = (EditText) view.findViewById(R.id.list_service_done_to_group);
            toGroup.setText(String.valueOf(selectedItem.getAnimalGroupToA() == null ? "" : selectedItem.getAnimalGroupToA().getFullNameType(context)));
            toGroup.setVisibility(animal.isGroupAnimal() ? View.GONE : View.VISIBLE);
            toGroup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, GroupeAnimalActivity.class);
                    intent.putExtra(GROUPE_ANIMAL_ACTIVITY_TYPE_ANIMAL, selectedItem.getAnimal().getTypeAnimal());
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
                    intent.putExtra(HOUSING_ACTIVITY_TYPE_HOUSING, TYPE_HOUSING_CELL);
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
                    selectedItem.setWeight(getDoubleForString(s));
                    selectedService = selectedItem;
                    selectedService.setChanged(true);
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
                    selectedItem.setAdmNumber(getIntForString(s));
                    selectedService = selectedItem;
                    selectedService.setChanged(true);
                }
            });

        }else if (typeResult == TYPE_RESULT_SERVICE_INSPECTION) {
            final EditText statusAnimal = (EditText) view.findViewById(R.id.list_service_done_status_animal);
            statusAnimal.setText(selectedItem.getStatusA()==null ? "" : selectedItem.getStatusA().getName());
            statusAnimal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedService = selectedItem;
                    ((ServiceActivity)context).showeQuestionSelectStatusAnimal(selectedService, statusAnimal);
                    changedItem = true;
                }
            });
        }else if (typeResult == TYPE_RESULT_SERVICE_RETIREMENT) {
            final EditText typeDisposAnimal = (EditText) view.findViewById(R.id.list_service_done_type_dispos_anim);
            typeDisposAnimal.setText(selectedItem.getDisposAnimA()==null ? "" : selectedItem.getDisposAnimA().getName());
            typeDisposAnimal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedService = selectedItem;
                    ((ServiceActivity) context).showeQuestionSelectDisposAnimal(selectedService, typeDisposAnimal);
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
                    selectedItem.setWeight(getDoubleForString(s));
                    selectedService = selectedItem;
                    selectedService.setChanged(true);
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
                    selectedItem.setLength(getIntForString(s));
                    selectedService = selectedItem;
                    selectedService.setChanged(true);
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
                    selectedItem.setBread(getIntForString(s));
                    selectedService = selectedItem;
                    selectedService.setChanged(true);
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
                    selectedItem.setDepthMysz(getIntForString(s));
                    selectedService = selectedItem;
                    selectedService.setChanged(true);
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
                    selectedItem.setExterior(getIntForString(s));
                    selectedService = selectedItem;
                    selectedService.setChanged(true);
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
                    selectedService.setChanged(true);
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
                    ((Activity) context).startActivityForResult(intent, CAPTURE_SELECT_GROUP_TO_REQ);
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
                    selectedItem.setNumber(getIntForString(s));
                    selectedService = selectedItem;
                    selectedService.setChanged(true);
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
                    selectedItem.setWeight(getDoubleForString(s));
                    selectedService = selectedItem;
                    selectedService.setChanged(true);
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
                    ((ServiceActivity) context).showeQuestionSelectGroupActivity();
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
                    selectedItem.setNumber(getIntForString(s));
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
                    selectedItem.setWeight(getDoubleForString(s));
                    selectedService = selectedItem;
                    selectedService.setChanged(true);
                }
            });

        }else if (typeResult == TYPE_RESULT_SERVICE_VETERINARY) {


            final SwipeMenuListView listVeterinary = (SwipeMenuListView) view.findViewById(R.id.list_service_veterinary);


            final VetExertiseListAdapter vetAdapter = new VetExertiseListAdapter(
                    context, selectedItem.getVetExerciseList());
            listVeterinary.setAdapter(vetAdapter);

            final Button addVeterinary = (Button) view.findViewById(R.id.list_service_add_veterinary);
            addVeterinary.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedItem.getVetExerciseList().add(
                            new ServiceDoneVetExercise(selectedItem.getAnimalId(),
                                    null,
                                    null));
                    vetAdapter.notifyDataSetChanged();

                }
            });

            SwipeMenuCreator creator = new SwipeMenuCreator() {
                @Override
                public void create(SwipeMenu menu) {
                    SwipeMenuItem deleteItem = new SwipeMenuItem(
                            context.getApplicationContext());
                    deleteItem.setBackground(R.color.colorPrimary);
                    deleteItem.setWidth(dpToPx((int)context.getResources().getDimension(R.dimen.width_swipe_item)));
                    deleteItem.setIcon(R.drawable.ic_delete);
                    menu.addMenuItem(deleteItem);
                }
            };

            listVeterinary.setMenuCreator(creator);
          //  listVeterinary.setSwipeDirection(SwipeMenuListView.DIRECTION_RIGHT);

            listVeterinary.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                    switch (index) {
                        case 0:
                            selectedItem.getVetExerciseList().remove(position);
                            vetAdapter.notifyDataSetChanged();
                            break;
                    }
                    return false;
                }
            });

//            vetExercise.setText(selectedItem.getVetExercise()==null ? "" : selectedItem.getVetExercise().getName());
//            vetExercise.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    selectedService = selectedItem;
//                    ((ServiceActivity) context).showeQuestionSelectVetExercise(selectedService, vetExercise);
//                }
//            });

//            final EditText vetPreparat = (EditText) view.findViewById(R.id.list_service_done_vet_preparat);
//            vetPreparat.setText(selectedItem.getVetPreparat()==null ? "" : selectedItem.getVetPreparat().getName());
//            vetPreparat.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    selectedService = selectedItem;
//                    ((ServiceActivity) context).showeQuestionSelectVetPreparat(selectedService, vetPreparat);
//                }
//            });
        }else{
//            final EditText resultService = (EditText) view.findViewById(R.id.list_service_done_result_service);
//            resultService.setText(selectedItem.getResultServiceS() == null ? "" : selectedItem.getResultServiceS().getName());
//            resultService.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    List<String> serviceList = new ArrayList<String>();
//
//                    final List<ServiceData> servicesNotInList = ((ServiceActivity)context).getListServiceData();
//
//                    for (ServiceData serviceData : servicesNotInList) {
//                        serviceList.add(serviceData.getName());
//                    }
//
//                    ((ServiceActivity)context).getActivityUtils().showSelectionList(((ServiceActivity)context),
//                            context.getString(R.string.select_from_the_list), serviceList,
//                            new ActivityUtils.ListItemClick() {
//                                @Override
//                                public void onItemClik(int item, String text) {
//                                    selectedItem.setResultService(servicesNotInList.get(item));
//                                    resultService.setText(selectedItem.getResultServiceS() == null ? "" : selectedItem.getResultServiceS().getName());
//                                }
//                            });
//                    selectedService = selectedItem;
//
//                }
//            });
        }

        return view;
    }

    public void enterAnimation(final View viewParent, final View viewChild) {

        ViewTreeObserver observer = viewChild.getViewTreeObserver();
        observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

            @Override
            public boolean onPreDraw() {
                viewChild.getViewTreeObserver().removeOnPreDrawListener(this);

                int[] screenLocation1 = new int[2];
                viewParent.getLocationOnScreen(screenLocation1);

                int thumbnailTop = screenLocation1[1];
                int thumbnailLeft = screenLocation1[0];
                int thumbnailWidth = viewParent.getWidth();
                int thumbnailHeight = viewParent.getHeight();

                int[] screenLocation = new int[2];
                viewChild.getLocationOnScreen(screenLocation);

                mLeftDelta = thumbnailLeft - screenLocation[0];
                mTopDelta = thumbnailTop - screenLocation[1];
                mWidthScale = (float) thumbnailWidth / viewChild.getWidth();
                mHeightScale = (float) thumbnailHeight / viewChild.getHeight();

                viewChild.setPivotX(0);
                viewChild.setPivotY(0);
                viewChild.setScaleX(mWidthScale);
                viewChild.setScaleY(mHeightScale);
                viewChild.setTranslationX(mLeftDelta);
                viewChild.setTranslationY(mTopDelta);

                // interpolator where the rate of change starts out quickly and then decelerates.
                TimeInterpolator sDecelerator = new DecelerateInterpolator();

                // Animate scale and translation to go from thumbnail to full size
                viewChild.animate().setDuration(ANIM_DURATION).scaleX(1).scaleY(1).
                        translationX(0).translationY(0).setInterpolator(sDecelerator);

                ObjectAnimator bgAnim = ObjectAnimator.ofInt(R.color.colorTrans, "alpha", 0, 255);
                bgAnim.setDuration(ANIM_DURATION);
                bgAnim.start();

                return true;
            }
        });
    }

    public void exitAnimation(View view, final Runnable endAction) {

        TimeInterpolator sInterpolator = new AccelerateInterpolator();
        view.animate().setDuration(ANIM_DURATION).scaleX(mWidthScale).scaleY(mHeightScale).
                translationX(mLeftDelta).translationY(mTopDelta)
                .setInterpolator(sInterpolator).withEndAction(endAction);

        // Fade out background
        ObjectAnimator bgAnim = ObjectAnimator.ofInt(R.color.colorTrans, "alpha", 0);
        bgAnim.setDuration(ANIM_DURATION);
        bgAnim.start();
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

    private int getIntForString(Editable s){
        changedItem = true;
        String text = String.valueOf(s);
        return text==null || text.isEmpty() ? 0 : Integer.valueOf(text);
    }

    private Double getDoubleForString(Editable s){
        changedItem = true;
        String text = String.valueOf(s);
        return text==null || text.isEmpty() ? 0.0 : Double.valueOf(text);
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                modelValues = (ArrayList<ServiceDoneListItem>) results.values; // has

                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults(); // Holds the

                List<ServiceDoneListItem> FilteredArrList = new ArrayList<ServiceDoneListItem>();

                if (mOriginalValues == null) {
                    mOriginalValues = new ArrayList<>(modelValues); // saves

                }
                if (constraint == null || constraint.length() == 0) {
                    results.count = mOriginalValues.size();
                    results.values = mOriginalValues;
                } else {
                    Locale locale = Locale.getDefault();
                    constraint = constraint.toString().toLowerCase(locale);

                    if(filterByName){
                        for (int i = 0; i < mOriginalValues.size(); i++) {
                            ServiceDoneListItem serviceDoneListItem = mOriginalValues.get(i);

                            String data = serviceDoneListItem.getAnimal().getFullNameType(context);
                            if (data.toLowerCase(locale).contains(constraint.toString())) {

                                FilteredArrList.add(serviceDoneListItem);
                            }
                        }
                    }else {
                        Boolean serviceDone = Boolean.valueOf(constraint.toString());
                        for (int i = 0; i < mOriginalValues.size(); i++) {
                            ServiceDoneListItem serviceDoneListItem = mOriginalValues.get(i);
                            if (serviceDoneListItem.getDone() == serviceDone) {

                                FilteredArrList.add(serviceDoneListItem);
                            }
                        }
                    }
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;

                }
                return results;
            }
        };
        return filter;
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int)(dp * (displayMetrics.densityDpi / 160f));
    }
}
