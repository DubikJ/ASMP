package com.avatlantik.asmp.activity;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.avatlantik.asmp.R;
import com.avatlantik.asmp.adapter.AnimalsListAdapter;
import com.avatlantik.asmp.adapter.treelistview.AnimalsViewItemClickListener;
import com.avatlantik.asmp.adapter.treelistview.ElementAnimal;
import com.avatlantik.asmp.app.ASMPApplication;
import com.avatlantik.asmp.model.db.Animal;
import com.avatlantik.asmp.model.db.Housing;
import com.avatlantik.asmp.repository.DataRepository;
import com.avatlantik.asmp.utils.ActivityUtils;
import com.avatlantik.asmp.utils.PhotoFIleUtils;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import static com.avatlantik.asmp.activity.AnimalActivity.ANIMAL_ACTIVITY_PARAM_ANIMAL_ID;
import static com.avatlantik.asmp.activity.ServiceActivity.ID_ANIMAL_ELEMENT_FOR_SERVICE;
import static com.avatlantik.asmp.adapter.treelistview.Element.TOP_LEVEL;
import static com.avatlantik.asmp.adapter.treelistview.ElementAnimal.NO_PARENT;
import static com.avatlantik.asmp.common.Consts.CLEAR_GUID;
import static com.avatlantik.asmp.common.Consts.TYPE_GROUP_ANIMAL_ALL;
import static com.avatlantik.asmp.common.Consts.TYPE_GROUP_ANIMAL_BOAR;
import static com.avatlantik.asmp.common.Consts.TYPE_GROUP_ANIMAL_CULTIVATION;
import static com.avatlantik.asmp.common.Consts.TYPE_GROUP_ANIMAL_FATTENING;
import static com.avatlantik.asmp.common.Consts.TYPE_GROUP_ANIMAL_SOW;
import static com.avatlantik.asmp.common.Consts.TYPE_GROUP_ANIMAL_WEANING;
import static com.avatlantik.asmp.common.Consts.USE_EXTERNAL_STORAGE;
import static com.avatlantik.asmp.common.Consts.USING_SCAN;

public class AnimalsActivity extends ActionBarActivity{

    private static final int CAPTURE_OPEN_ANIMAL_ACTIVITY_REQ = 1031;
    public static final String ANIMALS_ACTIVITY_TYPE_ANIMAL = "animals_activity_type_animal";
    public static final String ANIMALS_ACTIVITY_OPEN_FOR_RESULT = "animals_activity_open_for_result";
    private static final int  CAPTURE_SCAN_ACTIVITY_REQ = 1002;


    @Inject
    DataRepository dataRepository;
    @Inject
    ActivityUtils activityUtils;
    @Inject
    PhotoFIleUtils photoFileUtils;

    private ListView listView;
    private AnimalsListAdapter adapter;
    private EditText searchNameView;
    private Spinner mSpinner;
    private int type_animal;
    private boolean usedHousing, openForResult;
    private String searchObject;
    private Toolbar mToolbar;
    private View mContainerHeader;
    private ObjectAnimator fade;
    private LinearLayout animalScanLL, animalUpLL;
    private ProgressBar progressBarTask;
    private List<String> listSearchSettings = new ArrayList<String>();
    ArrayList<ElementAnimal> animalsListParentOriginal, animalsListChildOriginal, animalsListParent, animalsListChild;
    private ElementAnimal selectedAnimal;
    private Boolean useExternalStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animals);
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        ((ASMPApplication) getApplication()).getComponent().inject(this);

        listView = (ListView)findViewById(R.id.list_animals);

        View headerView = LayoutInflater.from(this)
                .inflate(R.layout.listview_header_search, listView, false);
        mContainerHeader = headerView.findViewById(R.id.container);
        listView.addHeaderView(headerView);
        fade =  ObjectAnimator.ofFloat(mContainerHeader, "alpha", 0f, 1f);
        fade.setInterpolator(new DecelerateInterpolator());
        fade.setDuration(400);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {}

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onScroll(AbsListView view,
                                 int firstVisibleItem,
                                 int visibleItemCount,
                                 int totalItemCount) {
                if (view != null && view.getChildCount() > 0 && firstVisibleItem == 0) {
                    if (view.getChildAt(0).getTop() < -dpToPx(16)) {
                        animalUpLL.animate().translationX(0).setInterpolator(new LinearInterpolator()).start();
                        animalUpLL.setVisibility(View.VISIBLE);
                        toggleHeader(false, false);
                    } else {
                        animalUpLL.animate().translationX(animalUpLL.getWidth() + getResources().getDimension(R.dimen.activity_animals_fab_margin_top)).setInterpolator(new LinearInterpolator()).start();
                        animalUpLL.setVisibility(View.GONE);
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

        searchNameView = (EditText) headerView.findViewById(R.id.search_animals);
        searchNameView.setSelected(false);
        searchNameView.setOnTouchListener(new View.OnTouchListener() {
            final int DRAWABLE_LEFT = 0;
            final int DRAWABLE_TOP = 1;
            final int DRAWABLE_RIGHT = 2;
            final int DRAWABLE_BOTTOM = 3;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    int leftEdgeOfRightDrawable = searchNameView.getRight()
                            - searchNameView.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width();
                    if (event.getRawX() >= leftEdgeOfRightDrawable) {
                        searchNameView.setText("");
                        return true;
                    }
                }
                return false;
            }
        });
        searchNameView.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                 setFilterToData(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mSpinner = (Spinner) headerView.findViewById(R.id.type_gruoup_spinner);
        searchObject = getResources().getString(R.string.animal_number);
        listSearchSettings.add(searchObject);
        listSearchSettings.add(getResources().getString(R.string.name_short));
        ArrayAdapter<String> adapterSearch = new ArrayAdapter<String>(this,
                R.layout.item_with_spinner,listSearchSettings);

        adapterSearch.setDropDownViewResource(R.layout.item_with_spinner);
        mSpinner.setAdapter(adapterSearch);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                searchObject = listSearchSettings.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSpinner.setSelection(0);


        animalUpLL = (LinearLayout) findViewById(R.id.animal_up_layout);
        FloatingActionButton animalUpFab = (FloatingActionButton) findViewById(R.id.animal_up);
        animalUpFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listView.setSelection(0);
            }
        });


        animalScanLL = (LinearLayout) findViewById(R.id.animal_scan_layout);
        animalScanLL.setVisibility(Boolean.valueOf(dataRepository.getUserSetting(USING_SCAN)) ? View.VISIBLE : View.GONE);
        FloatingActionButton animalScanFab = (FloatingActionButton) findViewById(R.id.animal_scan);
        animalScanFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AnimalsActivity.this, ScanActivity.class);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    final ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                            AnimalsActivity.this,
                            android.util.Pair.create((View) animalScanLL, "bg"));
                    startActivityForResult(intent, CAPTURE_SCAN_ACTIVITY_REQ, options.toBundle());
                }else {
                    startActivityForResult(intent, CAPTURE_SCAN_ACTIVITY_REQ);
                }
            }
        });

        usedHousing = false;
        openForResult = false;

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            type_animal = extras.getInt(ANIMALS_ACTIVITY_TYPE_ANIMAL);
            openForResult = extras.getBoolean(ANIMALS_ACTIVITY_OPEN_FOR_RESULT);
        }else{
            Toast.makeText(getApplicationContext(),
                    getResources().getString(R.string.type_animal_not_found),
                    Toast.LENGTH_LONG).show();
            finish();
        }

        if(type_animal<1&& type_animal > TYPE_GROUP_ANIMAL_ALL){
            Toast.makeText(getApplicationContext(),
                    getResources().getString(R.string.type_animal_not_found),
                    Toast.LENGTH_LONG).show();
            finish();
        }

        progressBarTask = (ProgressBar) findViewById(R.id.animals_progress_bar);

        animalsListParentOriginal = new ArrayList<ElementAnimal>();
        animalsListChildOriginal = new ArrayList<ElementAnimal>();

        useExternalStorage = Boolean.valueOf(dataRepository.getUserSetting(USE_EXTERNAL_STORAGE));

        new TaskInitAdapter().execute();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.structure_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_open_structure) {
            usedHousing = usedHousing ? false : true;
            new TaskInitAdapter().execute();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(type_animal == TYPE_GROUP_ANIMAL_SOW) {
            mToolbar.setTitle(getResources().getString(R.string.groupe_animals_sow));
        }else if(type_animal == TYPE_GROUP_ANIMAL_BOAR) {
            mToolbar.setTitle(getResources().getString(R.string.groupe_animals_boar));
        }else if(type_animal == TYPE_GROUP_ANIMAL_WEANING) {
            mToolbar.setTitle(getResources().getString(R.string.groupe_animals_weaning));
        }else if(type_animal == TYPE_GROUP_ANIMAL_CULTIVATION) {
            mToolbar.setTitle(getResources().getString(R.string.groupe_animals_cultivation));
        }else if(type_animal == TYPE_GROUP_ANIMAL_FATTENING) {
            mToolbar.setTitle(getResources().getString(R.string.groupe_animals_fatenting));
        }else {
            if (openForResult) {
                mToolbar.setTitle(getResources().getString(R.string.select_from_the_list));
            } else {
                mToolbar.setTitle(getResources().getString(R.string.animals_list_name));
            }
        }

        if(adapter!=null){
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAPTURE_OPEN_ANIMAL_ACTIVITY_REQ) {
            if (resultCode == RESULT_OK) {

                if(selectedAnimal == null){return;}

                Animal animalOld = selectedAnimal.getAnimal();

                if(animalOld==null) return;

                Animal animal = dataRepository.getAnimalById(animalOld.getExternalId());

                if(animal!=null && adapter!=null) {

                    ElementAnimal newSelectedAnimal = new ElementAnimal(animal.getNameType(this),
                            selectedAnimal.getLevel(),
                            selectedAnimal.getId(),
                            selectedAnimal.getParendId(),
                            selectedAnimal.isHasChildren(),
                            selectedAnimal.isExpanded(),
                            selectedAnimal.getExternalId(),
                            selectedAnimal.getExternalparentId(),
                            animal,
                            animal.getPhoto() == null || animal.getPhoto().isEmpty() ? null :
                                    photoFileUtils.getPhotoFile(useExternalStorage, animal.getPhoto()),
                            animal.isGroupAnimal());

                    int positionParent = animalsListParent.indexOf(selectedAnimal);

                    if(positionParent>-1) {
                        animalsListParent.set(positionParent, newSelectedAnimal);
                    }

                    int positionChild = animalsListChild.indexOf(selectedAnimal);

                    if(positionChild>-1) {
                        animalsListChild.set(positionChild, newSelectedAnimal);
                    }
                }

            } else if (resultCode == RESULT_CANCELED) {
                return;
            }
        }


    }

    private void initData(){

        animalsListParentOriginal.clear();
        animalsListChildOriginal.clear();

        if(usedHousing){

            List<Animal> animalsList = dataRepository.getAnimalsByTypeAnimalOnlyElement(type_animal);

            for (Animal animal : animalsList) {

                Housing corp = dataRepository.getHousingById(animal.getCorp());
                if(corp!=null) {
                    List<ElementAnimal> addedElemetns = getElementListbyId("corp-" + corp.getExternalId(), animalsListParentOriginal);
                    if(addedElemetns.size()==0) {
                        animalsListParentOriginal.add(new ElementAnimal(corp.getNameType(this),
                                TOP_LEVEL,
                                1000000+corp.getId(),
                                NO_PARENT,
                                true,
                                false,
                                "corp-" + corp.getExternalId(),
                                null,
                                null,
                                null,
                                false));
                    }

                }
                Housing sector = dataRepository.getHousingById(animal.getSector());
                if(sector!=null) {
                    List<ElementAnimal> addedElemetns = getElementListbyId("sector-" + sector.getExternalId(), animalsListChildOriginal);
                    if(addedElemetns.size()==0) {
                        animalsListChildOriginal.add(new ElementAnimal(sector.getNameType(this),
                                TOP_LEVEL+1,
                                1000000+sector.getId(),
                                (corp == null ? NO_PARENT : 1000000+corp.getId()),
                                true,
                                false,
                                "sector-" + sector.getExternalId(),
                                null,
                                null,
                                null,
                                false));
                    }
                }
                Housing cell = dataRepository.getHousingById(animal.getCell());
                if(cell!=null) {
                    List<ElementAnimal> addedElemetns = getElementListbyId("cell-" + cell.getExternalId(), animalsListChildOriginal);
                    if(addedElemetns.size()==0) {

                        animalsListChildOriginal.add(new ElementAnimal(cell.getNameType(this),
                                TOP_LEVEL + 2,
                                1000000+cell.getId(),
                                (sector == null ? (corp == null ? NO_PARENT : 1000000+corp.getId()) : 1000000+sector.getId()),
                                true,
                                false,
                                "cell-" + cell.getExternalId(),
                                null,
                                null,
                                null,
                                false));
                    }
                }


                animalsListChildOriginal.add(new ElementAnimal(animal.getNameType(this),
                        TOP_LEVEL + 3,
                        animal.getId(),
                        (cell==null ?
                                (sector==null ?
                                        (corp==null ? NO_PARENT :1000000+corp.getId())
                                        :1000000+sector.getId())
                                :1000000+cell.getId()),
                        false,
                        false,
                        animal.getExternalId(),
                        animal.getGroup(),
                        animal,
                        animal.getPhoto() == null || animal.getPhoto().isEmpty() ? null :
                                photoFileUtils.getPhotoFile(useExternalStorage, animal.getPhoto()),
                        animal.isGroupAnimal()));


            }

        }else {

            addingElement(CLEAR_GUID, TOP_LEVEL);

        }

        animalsListParent = new ArrayList<ElementAnimal>(animalsListParentOriginal);
        animalsListChild = new ArrayList<ElementAnimal>(animalsListChildOriginal);

        initAdapter();

    }

    private void addingElement(String externalId, int level){

        List<Animal> animalsList = dataRepository.getAnimalsByTypeAnimalAndParenId(type_animal, externalId);

        if(animalsList!=null) {

            if (animalsList.size() == 0) return;

            for (Animal animal : animalsList) {

                Boolean notHaveParent = animal.getGroup().equals(CLEAR_GUID);
                Boolean haveChild = dataRepository.getAnimalsByTypeAnimalAndParenId(type_animal, animal.getExternalId()).size() > 0;

                if (!haveChild&& animal.isGroup()) continue;

                Animal group = dataRepository.getAnimalById(animal.getGroup());

                ElementAnimal element = new ElementAnimal(haveChild ? animal.getFullNameType(this)  : animal.getNameType(this),
                        TOP_LEVEL + level,
                        animal.getId(),
                        group == null ? NO_PARENT : group.getId(),
                        haveChild,
                        false,
                        animal.getExternalId(),
                        animal.getGroup(),
                        animal,
                        animal.getPhoto() == null || animal.getPhoto().isEmpty() ? null :
                                photoFileUtils.getPhotoFile(useExternalStorage, animal.getPhoto()),
                        animal.isGroup());


                if (notHaveParent) {
                    animalsListParentOriginal.add(element);
                } else {
                    animalsListChildOriginal.add(element);
                }
                addingElement(animal.getExternalId(), (level + 1));
            }
        }

    }

    private void setFilterToData(CharSequence constraint){

        animalsListParent.clear();
        animalsListChild.clear();

        if (constraint == null || constraint.length() == 0) {
            for (ElementAnimal elementAnimal : animalsListParentOriginal)
                animalsListParent.add(elementAnimal);

            for (ElementAnimal elementAnimal : animalsListChildOriginal)
                animalsListChild.add(elementAnimal);
        } else {
            Locale locale = Locale.getDefault();
            constraint = constraint.toString().toLowerCase(locale);

            List<ElementAnimal> selectedList = animalsListChildOriginal;
            if(selectedList.size()==0){
                selectedList = animalsListParentOriginal;
            }

            for (ElementAnimal elementAnimal : selectedList) {
                if (elementAnimal != null) {

                    if (elementAnimal.isGroupe() || elementAnimal.getAnimal() == null) {
                        continue;
                    }

                    String data = elementAnimal.getAnimal().getCode();
                    if (searchObject.equalsIgnoreCase(getResources().getString(R.string.name_short))) {
                        if(elementAnimal.getAnimal().isGroupAnimal()){
                            data = elementAnimal.getAnimal().getNameType(this);
                        }else {
                            data = elementAnimal.getAnimal().getName();
                        }
                    }
                    if (data.toLowerCase(locale).contains(constraint.toString())) {
                        addingFilteredElement(elementAnimal.getExternalId());
                    }
                }
            }
        }

        adapter.notifyDataSetChanged();

    }

    private void addingFilteredElement(String externalId){

        List<ElementAnimal> addedElemetns = getElementListbyId(externalId, animalsListChild);
        if(addedElemetns.size()==0) {
            List<ElementAnimal> newElemetns = getElementListbyId(externalId, animalsListChildOriginal);
            if(newElemetns.size()==0) {
                List<ElementAnimal> addedParentElemetns = getElementListbyId(externalId, animalsListParent);
                if (addedParentElemetns.size() == 0) {
                    List<ElementAnimal> newParentElemetns = getElementListbyId(externalId, animalsListParentOriginal);
                    for (ElementAnimal parentAnimal : newParentElemetns) {
                        parentAnimal.setExpanded(false);
                        animalsListParent.add(parentAnimal);
                    }
                }
            }else {
                for (ElementAnimal elementAnimal : newElemetns) {
                    animalsListChild.add(elementAnimal);
                    addingFilteredElement(elementAnimal.getExternalparentId());
                }
            }

        }
    }


    private List<ElementAnimal> getElementListbyId(final String externalId, final List<ElementAnimal> elementssList) {

        return  (List<ElementAnimal>) CollectionUtils.select(elementssList, new Predicate() {
            public boolean evaluate(Object sample) {
                return ((ElementAnimal) sample).getExternalId().equals(externalId);
            }});
    }

    private void initAdapter(){

        adapter = new AnimalsListAdapter(this, animalsListParent, animalsListChild);

        AnimalsViewItemClickListener animalsViewItemClickListener = new AnimalsViewItemClickListener(adapter, new AnimalsViewItemClickListener.ListItemClick() {
            @Override
            public void onItemClik(final ElementAnimal element) {

                selectedAnimal = element;

                if (openForResult){
                    activityUtils.showQuestion(AnimalsActivity.this, getString(R.string.action_selection),
                            getString(R.string.questions_select_element),
                            new ActivityUtils.QuestionAnswer() {
                                @Override
                                public void onPositiveAnsver() {
                                    Intent intent = new Intent();
                                    intent.putExtra(ID_ANIMAL_ELEMENT_FOR_SERVICE, element.getExternalId());
                                    setResult(RESULT_OK, intent);
                                    finish();
                                }

                                @Override
                                public void onNegativeAnsver() {
                                }

                                @Override
                                public void onNeutralAnsver() {
                                }
                            });
                }else {
                    Intent intent = new Intent(AnimalsActivity.this, AnimalActivity.class);
                    intent.putExtra(ANIMAL_ACTIVITY_PARAM_ANIMAL_ID, element.getExternalId());
                    startActivityForResult(intent, CAPTURE_OPEN_ANIMAL_ACTIVITY_REQ);
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
                }
            }
        });
        listView.setOnItemClickListener(animalsViewItemClickListener);
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

    class TaskInitAdapter extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBarTask.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            initData();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progressBarTask.setVisibility(View.GONE);
            listView.setAdapter(adapter);
        }
    }

}
