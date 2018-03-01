package com.avatlantik.asmp.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.avatlantik.asmp.R;
import com.avatlantik.asmp.app.ASMPApplication;
import com.avatlantik.asmp.model.ParameterInfo;
import com.avatlantik.asmp.repository.DataRepository;
import com.avatlantik.asmp.service.sync.SyncIntentService;
import com.avatlantik.asmp.service.sync.SyncReceiver;
import com.avatlantik.asmp.utils.ActivityUtils;

import java.util.List;

import javax.inject.Inject;

import static com.avatlantik.asmp.common.Consts.AUTHORIZED;
import static com.avatlantik.asmp.common.Consts.LOGIN;
import static com.avatlantik.asmp.common.Consts.PASSWORD;
import static com.avatlantik.asmp.common.Consts.SERVER;
import static com.avatlantik.asmp.common.Consts.STATUS_ERROR_SYNC;
import static com.avatlantik.asmp.common.Consts.STATUS_FINISHED_SYNC;
import static com.avatlantik.asmp.common.Consts.STATUS_STARTED_SYNC;
import static com.avatlantik.asmp.common.Consts.USE_EXTERNAL_STORAGE;
import static com.avatlantik.asmp.common.Consts.USING_SCAN;
import static com.avatlantik.asmp.service.sync.SyncIntentService.SYNC_RECEIVER;

public class BootAct extends Activity implements SyncReceiver.Receiver {

    @Inject
    DataRepository dataRepository;

    @Inject
    ActivityUtils activityUtils;

    private AlertDialog alertQuestion;
    private LinearLayout contentViev;
    private ImageView imageView;
    private Animation animation;
    private EditText serverET, passwordET;
    private AutoCompleteTextView loginET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        ((ASMPApplication) getApplication()).getComponent().inject(this);

        contentViev = (LinearLayout) findViewById(R.id.content);
        contentViev.setVisibility(View.GONE);

        imageView = (ImageView) findViewById(R.id.splash);
        animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate_right);

        final String ads = dataRepository.getUserSetting(SERVER);
        final String ul = dataRepository.getUserSetting(LOGIN);
        final String ps = dataRepository.getUserSetting(PASSWORD);

        if (!Boolean.valueOf(dataRepository.getUserSetting(AUTHORIZED))) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.DialogTheme);
            builder.setTitle(getResources().getString(R.string.welcome));
            final View viewInflated = LayoutInflater.from(this).inflate(R.layout.boot_activity, null);

            serverET = (EditText) viewInflated.findViewById(R.id.serverAddress);
            serverET.setText(ads);
            if(ads!=null&&!ads.isEmpty()){
                TextInputLayout adsLayout = (TextInputLayout) viewInflated.findViewById(R.id.serverAddresslayout);
                adsLayout.setVisibility(View.GONE);
            }

            loginET = (AutoCompleteTextView) viewInflated.findViewById(R.id.login);
            loginET.setText(ul);

            List<String> users = dataRepository.getUsersServer();
            ArrayAdapter<String> adapter = new ArrayAdapter<String>
                    (this,android.R.layout.select_dialog_item,users);
            loginET.setThreshold(1);
            loginET.setOnTouchListener(new View.OnTouchListener() {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        int leftEdgeOfRightDrawable = loginET.getRight()
                                - loginET.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width();
                        if (event.getRawX() >= leftEdgeOfRightDrawable) {
                            loginET.showDropDown();
                            return true;
                        }
                    }
                    return false;
                }
            });
            loginET.setAdapter(adapter);

            passwordET = (EditText) viewInflated.findViewById(R.id.password);
            passwordET.setText(ps);

            builder.setView(viewInflated);

            builder.setPositiveButton(getResources().getString(R.string.questions_answer_ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.setNegativeButton(getResources().getString(R.string.action_exit), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

            builder.setNeutralButton(getResources().getString(R.string.server), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });

            builder.setCancelable(true);
            builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    finish();
                }
            });
            alertQuestion = builder.create();
            alertQuestion.show();
            alertQuestion.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            alertQuestion.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorAccent));
            alertQuestion.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    boolean cancel = false;
                    View focusView = null;

                    String serverText = String.valueOf(serverET.getText());
                    if (serverText==null || serverText.isEmpty()) {

                        serverET.setError(getString(R.string.error_field_required));
                        focusView = serverET;
                        cancel = true;
                    }

                    String loginText = String.valueOf(loginET.getText());

                    if (loginText==null || loginText.isEmpty()) {

                        loginET.setError(getString(R.string.error_field_required));
                        focusView = loginET;
                        cancel = true;
                    }
                    if (cancel) {

                        focusView.requestFocus();

                    } else {

                        alertQuestion.dismiss();
                        dataRepository.insertUserSetting(new ParameterInfo(SERVER, serverText));
                        dataRepository.insertUserSetting(new ParameterInfo(LOGIN, loginText));
                        dataRepository.insertUserSetting(new ParameterInfo(PASSWORD, String.valueOf(passwordET.getText())));

                        sync();

                    }

                }
            });
            alertQuestion.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorAccent));
            if(ads!=null&&!ads.isEmpty()) {
                alertQuestion.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(getResources().getColor(R.color.colorAccent));
            }else{
                alertQuestion.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(getResources().getColor(R.color.colorBackground));
            }
            alertQuestion.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    TextInputLayout adsLayout = (TextInputLayout) viewInflated.findViewById(R.id.serverAddresslayout);
                    adsLayout.setVisibility(View.VISIBLE);
                    alertQuestion.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(getResources().getColor(R.color.colorBackground));
                }
            });

        }else{
            showActivity();
        }

    }

    @Override
    public void onBackPressed() {
        activityUtils.showQuestion(BootAct.this, getString(R.string.action_exit),
                getString(R.string.questions_exit),
                new ActivityUtils.QuestionAnswer() {
                    @Override
                    public void onPositiveAnsver() {
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

    private void sync(){

        SyncReceiver mReceiver = new SyncReceiver(new Handler(), this);
        Intent intent = new Intent(Intent.ACTION_SYNC, null, this, SyncIntentService.class);
        intent.putExtra(SYNC_RECEIVER, mReceiver);
        startService(intent);

    }

    private void showAnimation(){

        contentViev.setVisibility(View.VISIBLE);
        imageView.startAnimation(animation);

    }

    private void showActivity(){

        Intent intent = new Intent(BootAct.this, BasicActivity.class);
        startActivity(intent);
        finish();
        BootAct.this.finish();

    }



    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        switch (resultCode) {
            case STATUS_STARTED_SYNC:
                showAnimation();
                break;
            case STATUS_FINISHED_SYNC:

                dataRepository.insertUserSetting(new ParameterInfo(AUTHORIZED, "true"));
                dataRepository.insertUserSetting(new ParameterInfo(USING_SCAN, "true"));
                dataRepository.insertUserSetting(new ParameterInfo(USE_EXTERNAL_STORAGE, "false"));
                showActivity();

                break;
            case STATUS_ERROR_SYNC:

                String error = resultData.getString(Intent.EXTRA_TEXT);

                activityUtils.hideKeyboard(this);

                contentViev.setVisibility(View.GONE);

                animation.cancel();

                alertQuestion.show();

                activityUtils.showMessage(error, this);

                break;
        }
    }

}