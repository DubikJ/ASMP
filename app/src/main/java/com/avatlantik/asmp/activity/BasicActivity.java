package com.avatlantik.asmp.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avatlantik.asmp.R;
import com.avatlantik.asmp.adapter.ServiseListAdapter;
import com.avatlantik.asmp.app.ASMPApplication;
import com.avatlantik.asmp.model.ParameterInfo;
import com.avatlantik.asmp.model.db.ServiceData;
import com.avatlantik.asmp.repository.DataRepository;
import com.avatlantik.asmp.service.SettingsService;
import com.avatlantik.asmp.service.sync.SyncIntentService;
import com.avatlantik.asmp.service.sync.SyncReceiver;
import com.avatlantik.asmp.ui.widget.PullToRefreshListView;
import com.avatlantik.asmp.utils.ActivityUtils;
import com.avatlantik.asmp.utils.PhotoFIleUtils;
import com.michael.easydialog.EasyDialog;

import org.joda.time.LocalDateTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import static com.avatlantik.asmp.activity.ServiceActivity.SERVICE_ACTIVITY_PARAM_SERVICE_ID;
import static com.avatlantik.asmp.common.Consts.CLEAR_DATABASE_IN_LOGOUT;
import static com.avatlantik.asmp.common.Consts.DATE_SYNC;
import static com.avatlantik.asmp.common.Consts.LOGIN;
import static com.avatlantik.asmp.common.Consts.SERVER;
import static com.avatlantik.asmp.common.Consts.STATUS_ERROR_SYNC;
import static com.avatlantik.asmp.common.Consts.STATUS_FINISHED_SYNC;
import static com.avatlantik.asmp.common.Consts.STATUS_STARTED_SYNC;
import static com.avatlantik.asmp.common.Consts.USING_SCAN;
import static com.avatlantik.asmp.service.sync.SyncIntentService.SYNC_RECEIVER;

public class BasicActivity extends AppCompatActivity implements SyncReceiver.Receiver {
    private boolean doubleBackToExitPressedOnce = false;

    @Inject
    DataRepository dataRepository;
    @Inject
    SettingsService settingsService;
    @Inject
    ActivityUtils activityUtils;
    @Inject
    PhotoFIleUtils photoFileUtils;

    private PullToRefreshListView listView;
    private List<ServiceData> serviceDataList;
    private ServiseListAdapter adapter;
    private EasyDialog bubbleMessage;
    private Date dateSync;
    private TextView nameUserTV, dateSyncTV;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
    private FrameLayout container;
    private LinearLayout userLayout;
    private boolean inited, isSync;
    private ImageButton scanButton, animButton, addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
        getSupportActionBar().setIcon(R.drawable.ic_launcher_animal);

        ((ASMPApplication) getApplication()).getComponent().inject(this);

        listView = (PullToRefreshListView) findViewById(R.id.list_service);
        listView.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startSync();
            }
        });

        userLayout = (LinearLayout) findViewById(R.id.user_name_layout);
        userLayout.setVisibility(View.VISIBLE);

        nameUserTV = (TextView)findViewById(R.id.basic_login_user);
        dateSyncTV = (TextView)findViewById(R.id.basic_date_sync);

        container = (FrameLayout) findViewById(R.id.content);
        container.setVisibility(View.VISIBLE);

        FloatingActionButton addServiceButton = (FloatingActionButton) findViewById(R.id.add_service);
        addServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<String> serviceList = new ArrayList<String>();

                final List<ServiceData> servicesNotInList = dataRepository.getServiceNotUsetToday();

                for (ServiceData serviceData : servicesNotInList) {
                    serviceList.add(serviceData.getName());
                }

                activityUtils.showSelectionList(BasicActivity.this,
                        getString(R.string.select_from_the_list), serviceList,
                        new ActivityUtils.ListItemClick() {
                            @Override
                            public void onItemClik(int item, String text) {

                                Intent intent = new Intent(BasicActivity.this, ServiceActivity.class);
                                intent.putExtra(SERVICE_ACTIVITY_PARAM_SERVICE_ID, servicesNotInList.get(item).getExternalId());
                                startActivity(intent);
                                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                            }
                        });


            }
        });

        animButton = (ImageButton) findViewById(R.id.list_but);
        animButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BasicActivity.this, AnimalsGroupeActivity.class));
            }
        });

        scanButton = (ImageButton) findViewById(R.id.scan_but);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startScan();

            }
        });

        isSync = false;
        initBubbleMessage();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(BasicActivity.this, SettingActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_exit) {
            logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        scanButton.setVisibility(Boolean.valueOf(dataRepository.getUserSetting(USING_SCAN)) ? View.VISIBLE : View.GONE);

        initData();

    }

    @Override
    public void onBackPressed() {
        if(isSync){
            activityUtils.showQuestion(BasicActivity.this, getString(R.string.download_data),
                    getString(R.string.questions_cancel_download_data),
                    new ActivityUtils.QuestionAnswer() {
                        @Override
                        public void onPositiveAnsver() {
                            userLayout.setVisibility(View.VISIBLE);
                            listView.onRefreshComplete();
                            isSync = false;
                        }

                        @Override
                        public void onNegativeAnsver() {
                        }

                        @Override
                        public void onNeutralAnsver() {
                        }
                    });
        }else{

            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
            }

            doubleBackToExitPressedOnce = true;
            Toast.makeText(this, getString(R.string.double_press_exit), Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus && !inited){
            Date dateDay = LocalDateTime.now().toDate();
            dateDay.setHours(0);
            dateDay.setMinutes(0);
            dateDay.setSeconds(0);

            if(dateSync.getTime() < dateDay.getTime()){
                bubbleMessage.show();
                inited = true;
            }
        }

    }

    private void initData(){

        initDataToCap();

        initDataToListView();

    }

    private void initBubbleMessage(){
        inited = true;

        View bubbleMessageView = this.getLayoutInflater().inflate(R.layout.layout_tip_content_horizontal, null);
        bubbleMessage = new EasyDialog(BasicActivity.this)
                .setLayout(bubbleMessageView)
                .setBackgroundColor(BasicActivity.this.getResources().getColor(R.color.colorAccent))
                .setLocationByAttachedView(dateSyncTV)
                .setGravity(EasyDialog.GRAVITY_BOTTOM)
                .setAnimationTranslationShow(EasyDialog.DIRECTION_X, 1000, -600, 100, -50, 50, 0)
                .setAnimationAlphaShow(1000, 0.3f, 1.0f)
                .setAnimationTranslationDismiss(EasyDialog.DIRECTION_X, 500, -50, 800)
                .setAnimationAlphaDismiss(500, 1.0f, 0.0f)
                .setTouchOutsideDismiss(true)
                .setMatchParent(true)
                .setMarginLeftAndRight(25, 25)
                .setOutsideColor(BasicActivity.this.getResources().getColor(R.color.outside_color_dark_gray));

        Button buttonMessage = (Button) bubbleMessageView.findViewById(R.id.buttonMessage);

        buttonMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSync();
                bubbleMessage.dismiss();
            }
        });
    }

    private void startSync(){
        if(!isSync) {
            SyncReceiver mReceiver = new SyncReceiver(new Handler(), this);
            Intent intent = new Intent(Intent.ACTION_SYNC, null, this, SyncIntentService.class);
            intent.putExtra(SYNC_RECEIVER, mReceiver);
            startService(intent);
        }
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        switch (resultCode) {
            case STATUS_STARTED_SYNC:
                userLayout.setVisibility(View.GONE);
                getSupportActionBar().setTitle(getResources().getString(R.string.download)+": "+nameUserTV.getText());
                isSync = true;
                break;
            case STATUS_FINISHED_SYNC:
                initDataToCap();
                serviceDataList.clear();
                initDataToListView();
                userLayout.setVisibility(View.VISIBLE);
                getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
                listView.onRefreshComplete();
                isSync = false;

                break;
            case STATUS_ERROR_SYNC:
                String error = resultData.getString(Intent.EXTRA_TEXT);
                activityUtils.showMessage(error, this);

                userLayout.setVisibility(View.VISIBLE);
                getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
                listView.onRefreshComplete();
                isSync = false;

                break;
        }
    }

    private void startScan(){
        if(!isSync) {

            Intent intent = new Intent(BasicActivity.this, ScanActivity.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                final ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                        BasicActivity.this,
                        android.util.Pair.create((View) scanButton, "bg"));
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        }
    }

    private void logout() {

        activityUtils.showQuestion(BasicActivity.this, getString(R.string.action_exit),
                getString(R.string.questions_exit_clear),
                new ActivityUtils.QuestionAnswer() {
                    @Override
                    public void onPositiveAnsver() {
                        settingsService.clearData();

                        if (CLEAR_DATABASE_IN_LOGOUT) {
                            String ads = dataRepository.getUserSetting(SERVER);
                            dataRepository.clearDataBase();
                            photoFileUtils.clearAllImageFiles();

                            dataRepository.insertUserSetting(new ParameterInfo(SERVER, ads));
                        }

                        Intent intent = new Intent(getBaseContext(), BootAct.class);
                        startActivity(intent);

                        finish();
                    }
                    @Override
                    public void onNegativeAnsver() {}
                    @Override
                    public void onNeutralAnsver() {}});

    }

    private void initDataToCap(){
        nameUserTV.setText(dataRepository.getUserSetting(LOGIN));
        String dateString = dataRepository.getUserSetting(DATE_SYNC);
        dateSync = (dateString == null || dateString.isEmpty() ? new Date(0,0,0) :  new Date(Long.valueOf(dateString)));
        dateSyncTV.setText(getResources().getString(R.string.task_list) +
                (dateSync.getTime() == new Date(0,0,0).getTime() ? "00.00.0000" : dateFormatter.format(dateSync)));
    }

    private void initDataToListView(){

        serviceDataList = dataRepository.getServiceUsetToday();

        adapter = new ServiseListAdapter(BasicActivity.this, serviceDataList);
        listView.setAdapter(adapter);

    }


}
