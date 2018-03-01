package com.avatlantik.asmp.activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Switch;

import com.avatlantik.asmp.R;
import com.avatlantik.asmp.app.ASMPApplication;
import com.avatlantik.asmp.model.ParameterInfo;
import com.avatlantik.asmp.repository.DataRepository;
import com.avatlantik.asmp.utils.ActivityUtils;

import javax.inject.Inject;

import static com.avatlantik.asmp.common.Consts.LOGIN;
import static com.avatlantik.asmp.common.Consts.PASSWORD;
import static com.avatlantik.asmp.common.Consts.SERVER;
import static com.avatlantik.asmp.common.Consts.USE_EXTERNAL_STORAGE;
import static com.avatlantik.asmp.common.Consts.USING_SCAN;

public class SettingActivity extends AppCompatActivity{

    @Inject
    DataRepository dataRepository;
    @Inject
    ActivityUtils activityUtils;

    private EditText server, login, password;
    private Switch usingScan, usingExtStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        ((ASMPApplication) getApplication()).getComponent().inject(this);

        server = (EditText) findViewById(R.id.serverAddress);
        login = (EditText) findViewById(R.id.login);
        password = (EditText) findViewById(R.id.password);
        usingScan = (Switch) findViewById(R.id.using_scan);
        usingExtStorage = (Switch) findViewById(R.id.using_ext_storage);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        activityUtils.showQuestion(SettingActivity.this, getString(R.string.questions_title_info),
                getString(R.string.questions_data_save),
                new ActivityUtils.QuestionAnswer() {
                    @Override
                    public void onPositiveAnsver() {
                        dataRepository.insertUserSetting(new ParameterInfo(SERVER, String.valueOf(server.getText())));
                        dataRepository.insertUserSetting(new ParameterInfo(LOGIN, String.valueOf(login.getText())));
                        dataRepository.insertUserSetting(new ParameterInfo(PASSWORD, String.valueOf(password.getText())));
                        dataRepository.insertUserSetting(new ParameterInfo(USING_SCAN, String.valueOf(usingScan.isChecked())));
                        dataRepository.insertUserSetting(new ParameterInfo(USE_EXTERNAL_STORAGE, String.valueOf(usingExtStorage.isChecked())));
                        onSuperBackPressed();
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
            initData();
    }

    private void initData(){
        server.setText(dataRepository.getUserSetting(SERVER));
        login.setText(dataRepository.getUserSetting(LOGIN));
        password.setText(dataRepository.getUserSetting(PASSWORD));
        usingScan.setChecked(Boolean.valueOf(dataRepository.getUserSetting(USING_SCAN)));
        usingExtStorage.setChecked(Boolean.valueOf(dataRepository.getUserSetting(USE_EXTERNAL_STORAGE)));
    }
}
