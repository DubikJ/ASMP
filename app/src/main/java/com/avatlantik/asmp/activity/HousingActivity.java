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
import com.avatlantik.asmp.model.db.Housing;
import com.avatlantik.asmp.repository.DataRepository;
import com.avatlantik.asmp.utils.ActivityUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.avatlantik.asmp.activity.NewAnimalActivity.ID_SELECTED_ELEMENT_FOR_ANIMAL;
import static com.avatlantik.asmp.common.Consts.TYPE_HOUSING_CELL;
import static com.avatlantik.asmp.common.Consts.TYPE_HOUSING_CORP;
import static com.avatlantik.asmp.common.Consts.TYPE_HOUSING_SECTOR;

public class HousingActivity extends ActionBarActivity{

    public static final String HOUSING_ACTIVITY_TYPE_HOUSING = "housing_activity_type_housing";
    public static final String HOUSING_ACTIVITY_PARENT_ID = "animals_activity_parent_id";


    @Inject
    DataRepository dataRepository;
    @Inject
    ActivityUtils activityUtils;

    private ListView housingListView;
    private HierarchicalListAdapter adapter;
    private EditText searchNameView;
    private int typeHousing;
    private String parentId;
    private Toolbar mToolbar;
    private View mContainerHeader;
    private ObjectAnimator fade;
    private LinearLayout housingUpLL;

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

        housingListView = (ListView)findViewById(R.id.list_housing);

        View headerView = LayoutInflater.from(this)
                .inflate(R.layout.listview_header_search_short, housingListView, false);
        mContainerHeader = headerView.findViewById(R.id.container);
        housingListView.addHeaderView(headerView);
        fade =  ObjectAnimator.ofFloat(mContainerHeader, "alpha", 0f, 1f);
        fade.setInterpolator(new DecelerateInterpolator());
        fade.setDuration(400);

        housingUpLL = (LinearLayout) findViewById(R.id.housing_up_layout);

        housingListView.setOnScrollListener(new AbsListView.OnScrollListener() {
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
                        housingUpLL.animate().translationX(0).setInterpolator(new LinearInterpolator()).start();
                        housingUpLL.setVisibility(View.VISIBLE);
                        toggleHeader(false, false);
                    } else {
                        housingUpLL.animate().translationX(housingUpLL.getWidth() + getResources().getDimension(R.dimen.activity_animals_fab_margin_top)).setInterpolator(new LinearInterpolator()).start();
                        housingUpLL.setVisibility(View.GONE);
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

        housingUpLL = (LinearLayout) findViewById(R.id.housing_up_layout);
        FloatingActionButton housingUpFab = (FloatingActionButton) findViewById(R.id.housing_up);
        housingUpFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            housingListView.setSelection(0);
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
            typeHousing = extras.getInt(HOUSING_ACTIVITY_TYPE_HOUSING);
            parentId = extras.getString(HOUSING_ACTIVITY_PARENT_ID);
        }else{
            Toast.makeText(getApplicationContext(),
                    getResources().getString(R.string.type_housing_not_found),
                    Toast.LENGTH_LONG).show();
            finish();
        }

        if(typeHousing == TYPE_HOUSING_CORP) {
            mToolbar.setTitle(getResources().getString(R.string.select_from_the_list)
             + ": " + getResources().getString(R.string.housing_corp) );
        }else if(typeHousing == TYPE_HOUSING_SECTOR) {
            mToolbar.setTitle(getResources().getString(R.string.select_from_the_list)
                    + ": " + getResources().getString(R.string.housing_sector) );
        }else if(typeHousing == TYPE_HOUSING_CELL) {
            mToolbar.setTitle(getResources().getString(R.string.select_from_the_list)
                    + ": " + getResources().getString(R.string.housing_corp) );
        }else{
                mToolbar.setTitle(getResources().getString(R.string.select_from_the_list));
        }

        initData();
    }

    private void initData(){

        initDataToListView();
    }

    private void initDataToListView(){
        List<Housing> housingList = dataRepository.getHousingListByTypeAndParenID(typeHousing, parentId);

        ArrayList<Element> elementsList = new ArrayList<Element>();
        for(Housing housing : housingList){
            elementsList.add(new Element(housing.getName(), 0, 0, 0,
                    false, false, housing.getExternalId(), housing.getParentId()));
        }

        adapter = new HierarchicalListAdapter(this, elementsList, parentId);
        TreeViewItemClickListener treeViewItemClickListener = new TreeViewItemClickListener(adapter, new TreeViewItemClickListener.ListItemClick() {
            @Override
            public void onItemClik(final Element element) {
                activityUtils.showQuestion(HousingActivity.this, getString(R.string.action_selection),
                        getString(R.string.questions_select_element),
                        new ActivityUtils.QuestionAnswer() {
                            @Override
                            public void onPositiveAnsver() {
                                Intent intent = new Intent();
                                intent.putExtra(ID_SELECTED_ELEMENT_FOR_ANIMAL, element.getExternalId());
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
        housingListView.setAdapter(adapter);
        housingListView.setOnItemClickListener(treeViewItemClickListener);

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
