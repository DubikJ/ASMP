package com.avatlantik.asmp.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.transition.Transition;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.hdhe.uhf.reader.Tools;
import com.android.hdhe.uhf.reader.UhfReader;
import com.android.hdhe.uhf.reader.Util;
import com.avatlantik.asmp.R;
import com.avatlantik.asmp.adapter.ScanListAdapter;
import com.avatlantik.asmp.app.ASMPApplication;
import com.avatlantik.asmp.model.db.Animal;
import com.avatlantik.asmp.repository.DataRepository;
import com.avatlantik.asmp.utils.ActivityUtils;
import com.avatlantik.asmp.utils.PhotoFIleUtils;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import static com.avatlantik.asmp.activity.AnimalActivity.ANIMAL_ACTIVITY_PARAM_ANIMAL_ID;
import static com.avatlantik.asmp.activity.NewAnimalActivity.ANIMAL_ACTIVITY_PARAM_RFID_ID;
import static com.avatlantik.asmp.activity.NewAnimalActivity.ANIMAL_ACTIVITY_PARAM_TYPE_ANIMAL;
import static com.avatlantik.asmp.activity.ServiceActivity.ID_ANIMAL_ELEMENT_FOR_SERVICE;
import static com.avatlantik.asmp.common.Consts.TAGLOG_SCAN;
import static com.avatlantik.asmp.common.Consts.TYPE_GROUP_ANIMAL_ALL;
import static com.avatlantik.asmp.common.Consts.USE_EXTERNAL_STORAGE;

public class ScanActivity extends Activity {
    public static final String ID_ANIMAL_ELEMENT_FROM_SCAN = "id_animal_element_from_scan";
    public static final String ANIMALS_ACTIVITY_OPEN_FOR_RESULT = "animals_activity_open_for_result";
    private static final int CAPTURE_NEW_ANIMAL_ACTIVITY_REQ = 1050;

    @Inject
    DataRepository dataRepository;
    @Inject
    ActivityUtils activityUtils;
    @Inject
    PhotoFIleUtils photoFileUtils;

    private View lineScan;
    private Animation animationScan;
    private int height, width;

    private LinearLayout scanLayout, listLayout;
    private ListView listView;
    private FloatingActionButton mFab;
    private ConstraintLayout mConstraintLayout;
    private int duration = 300;
    private Transition sharedElementEnterTransition;
    private Transition.TransitionListener mTransitionListener;
    private UhfReader reader;
    private InventoryThread threadScan;
    private List<String> rfidList;
    private List<Animal> scanAnimalList;
    private ScanListAdapter adapter;
    private Boolean openForResult;
    private boolean useExternalStorage;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        ((ASMPApplication) getApplication()).getComponent().inject(this);

        scanLayout = (LinearLayout) findViewById(R.id.scan_layout);
        listLayout = (LinearLayout) findViewById(R.id.scan_listanimal_layout);
        listLayout.setVisibility(View.GONE);
        lineScan = findViewById(R.id.bar_scan);
        listView = (ListView) findViewById(R.id.scan_animal_list);
        animationScan = AnimationUtils.loadAnimation(ScanActivity.this, R.anim.scan);
        animationScan.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                lineScan.setVisibility(View.GONE);
                scanLayout.setVisibility(View.GONE);
                listLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        mFab = (FloatingActionButton) findViewById(R.id.next_fab);
        mConstraintLayout = (ConstraintLayout) findViewById(R.id.bg);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
            getWindow().setEnterTransition(null);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            height = displayMetrics.heightPixels;
            width = displayMetrics.widthPixels;
            sharedElementEnterTransition = getWindow().getSharedElementEnterTransition();


            mTransitionListener = new Transition.TransitionListener() {
                @Override
                public void onTransitionStart(Transition transition) {}
                @Override
                public void onTransitionEnd(Transition transition) {
                    setAnim(mConstraintLayout, true);
                    setFab(mFab, false);
                }
                @Override
                public void onTransitionCancel(Transition transition) {}
                @Override
                public void onTransitionPause(Transition transition) {}
                @Override
                public void onTransitionResume(Transition transition) {}
            };

            sharedElementEnterTransition.addListener(mTransitionListener);
        } else{
            mFab.setVisibility(View.GONE);
            mConstraintLayout.setVisibility(View.VISIBLE);
        }

        lineScan.startAnimation(animationScan);

        reader = UhfReader.getInstance();
        if(reader == null) {
            Toast.makeText(this, getString(R.string.serialport_init_fail), Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Util.initSoundPool(this);
            threadScan = new InventoryThread();
            threadScan.start();
        }

        rfidList = new ArrayList<>();
        scanAnimalList = new ArrayList<>();

        adapter = new ScanListAdapter(ScanActivity.this, scanAnimalList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                final Animal selectedAnimal = scanAnimalList.get(position);

                if(openForResult) {

                    activityUtils.showQuestion(ScanActivity.this, getString(R.string.action_selection),
                            getString(R.string.questions_select_element),
                            new ActivityUtils.QuestionAnswer() {
                                @Override
                                public void onPositiveAnsver() {

                                    if (selectedAnimal.getExternalId() == null || selectedAnimal.getExternalId().isEmpty()) {
                                        activityUtils.showQuestion(ScanActivity.this, getString(R.string.action_selection),
                                                getString(R.string.questions_animal_not_added),
                                                new ActivityUtils.QuestionAnswer() {
                                                    @Override
                                                    public void onPositiveAnsver() {
                                                        Intent intent = new Intent(ScanActivity.this, NewAnimalActivity.class);
                                                        intent.putExtra(ANIMAL_ACTIVITY_PARAM_TYPE_ANIMAL, TYPE_GROUP_ANIMAL_ALL);
                                                        intent.putExtra(ANIMAL_ACTIVITY_PARAM_RFID_ID, selectedAnimal.getRfid());
                                                        startActivityForResult(intent, CAPTURE_NEW_ANIMAL_ACTIVITY_REQ);
                                                    }

                                                    @Override
                                                    public void onNegativeAnsver() {
                                                    }

                                                    @Override
                                                    public void onNeutralAnsver() {
                                                    }
                                                });
                                    } else {
                                        Intent intent = new Intent();
                                        intent.putExtra(ID_ANIMAL_ELEMENT_FROM_SCAN, selectedAnimal.getExternalId());
                                        setResult(RESULT_OK, intent);
                                        finish();
                                    }
                                }

                                @Override
                                public void onNegativeAnsver() {
                                }

                                @Override
                                public void onNeutralAnsver() {
                                }
                            });
                }else{

                    if (selectedAnimal.getExternalId() == null || selectedAnimal.getExternalId().isEmpty()) {
                        Toast.makeText(ScanActivity.this, getString(R.string.animal_not_added), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Intent intent = new Intent(ScanActivity.this, AnimalActivity.class);
                    intent.putExtra(ANIMAL_ACTIVITY_PARAM_ANIMAL_ID, selectedAnimal.getExternalId());
                    startActivity(intent);
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
                }

            }
        });

        final FloatingActionButton stopStartScan = (FloatingActionButton) findViewById(R.id.stop_start_scan);
        stopStartScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(threadScan == null){return;}

                if(threadScan.isRunnung()){
                    threadScan.setCancel();
                    stopStartScan.setImageResource(R.drawable.ic_input_antenna);
                }else{
                    threadScan = new InventoryThread();
                    threadScan.start();
                    stopStartScan.setImageResource(R.drawable.ic_close);
                }

            }
        });

        useExternalStorage = Boolean.valueOf(dataRepository.getUserSetting(USE_EXTERNAL_STORAGE));
    }


    @Override
    public void onBackPressed() {

        activityUtils.showQuestion(ScanActivity.this, getString(R.string.scan),
                getString(R.string.questions_cancel_scan),
                new ActivityUtils.QuestionAnswer() {
                    @Override
                    public void onPositiveAnsver() {
                        closeAnimation();
                        finish();
                        animationScan.cancel();
                    }

                    @Override
                    public void onNegativeAnsver() {
                    }

                    @Override
                    public void onNeutralAnsver() {
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();

        openForResult = false;

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            openForResult = extras.getBoolean(ANIMALS_ACTIVITY_OPEN_FOR_RESULT);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(threadScan!=null) {
            threadScan.setCancel();
            threadScan.interrupt();
            threadScan=null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case CAPTURE_NEW_ANIMAL_ACTIVITY_REQ:
                if (resultCode == RESULT_OK) {

                    String selectedID = data.getStringExtra(ID_ANIMAL_ELEMENT_FOR_SERVICE);

                    Intent intent = new Intent();
                    intent.putExtra(ID_ANIMAL_ELEMENT_FROM_SCAN, selectedID);
                    setResult(RESULT_OK, intent);
                    finish();

                } else if (resultCode == RESULT_CANCELED) {
                    Log.d(TAGLOG_SCAN, "Animals cancelled");
                }

                break;
        }

    }

    private void closeAnimation(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sharedElementEnterTransition.removeListener(mTransitionListener);
            setAnim(mConstraintLayout, false);
           // setFab(mFab, true);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setAnim(final View myView, boolean isShow) {

        int cx = mFab.getWidth() / 2;
        int cy = mFab.getHeight() / 2;

        float finalRadius = (float) Math.hypot(width, height);

        int[] startingLocation = new int[2];
        mFab.getLocationInWindow(startingLocation);

        Animator anim;
        if (isShow) {
            anim =
                    ViewAnimationUtils.createCircularReveal(myView, (int) (mFab.getX() + cx), (int) (mFab.getY() + cy), 0, finalRadius);
            myView.setVisibility(View.VISIBLE);
        } else {
            anim =
                    ViewAnimationUtils.createCircularReveal(myView, (int) (mFab.getX() + cx), (int) (mFab.getY() + cy), finalRadius, 0);
            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    myView.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
        }

        anim.setDuration(duration);
        anim.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    void setFab(final View myView, boolean isShow) {

        int cx = myView.getWidth() / 2;
        int cy = myView.getHeight() / 2;

        float initialRadius = (float) Math.hypot(cx, cy);
        Animator anim;
        if (isShow) {
            anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, initialRadius);
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    myView.setVisibility(View.VISIBLE);
                    finishAfterTransition();
                }
            });
            anim.setDuration(duration);
        } else {
            anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, initialRadius, 0);
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    myView.setVisibility(View.INVISIBLE);
                }
            });
        }
        anim.start();

    }

    private void addNewCodeToList(final String rfidCode){
        List<String> findRfidList = (List<String>) CollectionUtils.select(rfidList, new Predicate() {
            public boolean evaluate(Object sample) {
                return ((String) sample).toString().equals(rfidCode);
            }});

        if(findRfidList.size()>0){return;}

        rfidList.add(rfidCode);

        Animal findAnimal = dataRepository.getAnimalByRFID(rfidCode);

        if(findAnimal==null){
            findAnimal = Animal.builder().rfid(rfidCode).build();

        }
        findAnimal.setPhotoFile(photoFileUtils.getPhotoFile(useExternalStorage, findAnimal.getPhoto()));

        scanAnimalList.add(findAnimal);

        adapter.notifyDataSetChanged();

    }



    class InventoryThread extends Thread {
        byte[] accessPassword = Tools.HexString2Bytes("00000000");
        private List<byte[]> epcList;
        private boolean keepRunning = true;

        InventoryThread() {
        }

        public void setCancel(){
            keepRunning = false;
        }

        public Boolean isRunnung(){
            return keepRunning;
        }

        public void run() {
            super.run();
            while(keepRunning) {

                epcList = reader.inventoryRealTime();
                if(epcList != null && !epcList.isEmpty()) {
                    Util.play(1, 0);
                    Iterator var2 = epcList.iterator();

                    while(var2.hasNext()) {
                        byte[] e = (byte[])var2.next();
                        final String epcStr = Tools.Bytes2HexString(e, e.length);
                        ScanActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                animationScan.cancel();
                              //  Toast.makeText(ScanActivity.this, epcStr, Toast.LENGTH_SHORT).show();
                                addNewCodeToList(epcStr);
                            }
                        });

                    }
                }

                epcList = null;

                try {
                    Thread.sleep(20L);
                } catch (InterruptedException var4) {
                    var4.printStackTrace();
                }
            }
        }
    }
}
