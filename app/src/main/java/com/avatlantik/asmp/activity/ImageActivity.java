package com.avatlantik.asmp.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import com.avatlantik.asmp.R;
import com.avatlantik.asmp.app.ASMPApplication;
import com.avatlantik.asmp.repository.DataRepository;
import com.avatlantik.asmp.ui.widget.TouchImageView;
import com.avatlantik.asmp.utils.PhotoFIleUtils;
import com.bumptech.glide.Glide;

import java.io.File;

import javax.inject.Inject;

import static com.avatlantik.asmp.common.Consts.USE_EXTERNAL_STORAGE;

public class ImageActivity extends AppCompatActivity {

    @Inject
    PhotoFIleUtils photoFileUtils;
    @Inject
    DataRepository dataRepository;

    public static final String IMAGE_FULL_NAME = "image_full_name";
    public static final String IMAGE_NAME = "image_name";

    private TouchImageView imgDisplay;
    private boolean useExternalStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        ((ASMPApplication) getApplication()).getComponent().inject(this);

        imgDisplay = (TouchImageView) findViewById(R.id.image);
        imgDisplay.setMaxZoom(5f);

        SeekBar sb = (SeekBar) findViewById(R.id.seekBar);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                float scale =  ((progress / 10.0f)+1);
                imgDisplay.setScaleX(scale);
                imgDisplay.setScaleY(scale);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        FloatingActionButton closeView = (FloatingActionButton) findViewById(R.id.return_image);
        closeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        Bundle extras = getIntent().getExtras();

        String imageFullName = null;
        String imageName = null;
        if (extras != null) {
            imageFullName = extras.getString(IMAGE_FULL_NAME);
            imageName = extras.getString(IMAGE_NAME);
        }else{
            Toast.makeText(getApplicationContext(),
                    getResources().getString(R.string.photo_not_found), Toast.LENGTH_SHORT)
                    .show();
            finish();
            return;
        }
        if(imageFullName == null || imageFullName.isEmpty()){
            Toast.makeText(getApplicationContext(),
                    getResources().getString(R.string.photo_not_found), Toast.LENGTH_SHORT)
                    .show();
            finish();
            return;
        }

        getSupportActionBar().setTitle(imageName);

        useExternalStorage = Boolean.valueOf(dataRepository.getUserSetting(USE_EXTERNAL_STORAGE));

        File photoFile = photoFileUtils.getPhotoFile(useExternalStorage, imageFullName);


        Glide.with(this).load(photoFile == null ? R.drawable.ic_pig_photo : photoFile).centerCrop().into(imgDisplay);

    }

}