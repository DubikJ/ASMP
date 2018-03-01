package com.avatlantik.asmp.activity;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.avatlantik.asmp.R;
import com.avatlantik.asmp.adapter.HierarchicalListAdapter;
import com.avatlantik.asmp.adapter.treelistview.Element;
import com.avatlantik.asmp.adapter.treelistview.TreeViewItemClickListener;
import com.avatlantik.asmp.app.ASMPApplication;
import com.avatlantik.asmp.model.db.Animal;
import com.avatlantik.asmp.repository.DataRepository;
import com.avatlantik.asmp.utils.ActivityUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.avatlantik.asmp.activity.NewAnimalActivity.ID_GROUP_ELEMENT_FOR_ANIMAL;
import static com.avatlantik.asmp.common.Consts.CLEAR_GUID;
import static com.avatlantik.asmp.common.Consts.TYPE_GROUP_ANIMAL_BOAR;
import static com.avatlantik.asmp.common.Consts.TYPE_GROUP_ANIMAL_CULTIVATION;
import static com.avatlantik.asmp.common.Consts.TYPE_GROUP_ANIMAL_FATTENING;
import static com.avatlantik.asmp.common.Consts.TYPE_GROUP_ANIMAL_SOW;
import static com.avatlantik.asmp.common.Consts.TYPE_GROUP_ANIMAL_WEANING;

public class GroupeAnimalActivity extends ActionBarActivity{

    public static final String GROUPE_ANIMAL_ACTIVITY_TYPE_ANIMAL = "groupe_animal_activity_type_animal";

    @Inject
    DataRepository dataRepository;
    @Inject
    ActivityUtils activityUtils;

    private ListView groupeListView;
    private HierarchicalListAdapter adapter;
    private EditText searchNameView;
    private Toolbar mToolbar;
    private View mContainerHeader;
    private ObjectAnimator fade;
    private LinearLayout groupUpLL;
    private int typeAnimal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_housing);
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        ((ASMPApplication) getApplication()).getComponent().inject(this);

        groupeListView = (ListView)findViewById(R.id.list_housing);

        View headerView = LayoutInflater.from(this)
                .inflate(R.layout.listview_header_search_short, groupeListView, false);
        mContainerHeader = headerView.findViewById(R.id.container);
        groupeListView.addHeaderView(headerView);
        fade =  ObjectAnimator.ofFloat(mContainerHeader, "alpha", 0f, 1f);
        fade.setInterpolator(new DecelerateInterpolator());
        fade.setDuration(400);

        groupUpLL = (LinearLayout) findViewById(R.id.housing_up_layout);
        groupeListView.setOnScrollListener(new AbsListView.OnScrollListener() {
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
                        groupUpLL.animate().translationX(0).setInterpolator(new LinearInterpolator()).start();
                        groupUpLL.setVisibility(View.VISIBLE);
                        toggleHeader(false, false);
                    } else {
                        groupUpLL.animate().translationX(groupUpLL.getWidth() + getResources().getDimension(R.dimen.activity_animals_fab_margin_top)).setInterpolator(new LinearInterpolator()).start();
                        groupUpLL.setVisibility(View.GONE);
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

        searchNameView = (EditText) headerView.findViewById(R.id.search);
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
                adapter.setFilter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        FloatingActionButton housingUpFab = (FloatingActionButton) findViewById(R.id.housing_up);
        housingUpFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groupeListView.setSelection(0);
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            typeAnimal = extras.getInt(GROUPE_ANIMAL_ACTIVITY_TYPE_ANIMAL);
        }else{
            Toast.makeText(getApplicationContext(),
                    getResources().getString(R.string.type_animal_not_found),
                    Toast.LENGTH_LONG).show();
            finish();
        }

        if(typeAnimal == TYPE_GROUP_ANIMAL_SOW) {
            mToolbar.setTitle(getResources().getString(R.string.select_from_the_list_groupe)
                    + ": " + getResources().getString(R.string.groupe_animals_sow));
        }else if(typeAnimal == TYPE_GROUP_ANIMAL_BOAR) {
            mToolbar.setTitle(getResources().getString(R.string.select_from_the_list_groupe)
                    + ": " + getResources().getString(R.string.groupe_animals_boar));
        }else if(typeAnimal == TYPE_GROUP_ANIMAL_WEANING) {
            mToolbar.setTitle(getResources().getString(R.string.select_from_the_list_groupe)
                    + ": " + getResources().getString(R.string.groupe_animals_weaning));
        }else if(typeAnimal == TYPE_GROUP_ANIMAL_CULTIVATION) {
            mToolbar.setTitle(getResources().getString(R.string.select_from_the_list_groupe)
                    + ": " + getResources().getString(R.string.groupe_animals_cultivation));
        }else if(typeAnimal == TYPE_GROUP_ANIMAL_FATTENING) {
            mToolbar.setTitle(getResources().getString(R.string.select_from_the_list_groupe)
                    + ": " + getResources().getString(R.string.groupe_animals_fatenting));
        }else{
            Toast.makeText(getApplicationContext(),
                    getResources().getString(R.string.type_animal_not_found),
                    Toast.LENGTH_LONG).show();
            finish();
        }

        initData();
    }

    private void initData(){

        initDataToListView();
    }

    private void initDataToListView(){
        List<Animal> groupeList = dataRepository.getAllGroupsAnimalsByTypeAnimal(typeAnimal);

        ArrayList<Element> elementsList = new ArrayList<Element>();
        for(Animal groupe : groupeList){
            elementsList.add(new Element(groupe.getFullNameType(this), 0, 0, 0,
                    false, false, groupe.getExternalId(), groupe.getGroup()));
        }

        adapter = new HierarchicalListAdapter(this, elementsList, CLEAR_GUID);
        TreeViewItemClickListener treeViewItemClickListener = new TreeViewItemClickListener(adapter,
                new TreeViewItemClickListener.ListItemClick() {
                    @Override
                    public void onItemClik(final Element element) {
                        activityUtils.showQuestion(GroupeAnimalActivity.this, getString(R.string.action_selection),
                                getString(R.string.questions_select_element),
                                new ActivityUtils.QuestionAnswer() {
                                    @Override
                                    public void onPositiveAnsver() {
                                        Intent intent = new Intent();
                                        intent.putExtra(ID_GROUP_ELEMENT_FOR_ANIMAL, element.getExternalId());
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
                    }
                });
        groupeListView.setAdapter(adapter);
        groupeListView.setOnItemClickListener(treeViewItemClickListener);

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
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

}
