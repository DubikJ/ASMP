package com.avatlantik.asmp.app;


import android.app.Application;

import com.avatlantik.asmp.component.DIComponent;
import com.avatlantik.asmp.component.DaggerDIComponent;
import com.avatlantik.asmp.logs.ReportHandler;
import com.avatlantik.asmp.modules.ActivityUtilsApiModule;
import com.avatlantik.asmp.modules.DataApiModule;
import com.avatlantik.asmp.modules.ErrorUtilsApiModule;
import com.avatlantik.asmp.modules.NetworkUtilsApiModule;
import com.avatlantik.asmp.modules.PhotoFileUtilsApiModule;
import com.avatlantik.asmp.modules.ServiceApiModule;
import com.avatlantik.asmp.utils.PropertyUtils;

import net.danlew.android.joda.JodaTimeAndroid;

import java.io.IOException;
import java.util.Properties;

import static com.avatlantik.asmp.common.Consts.APPLICATION_PROPERTIES;

public class ASMPApplication extends Application {

    DIComponent diComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
        diComponent = DaggerDIComponent.builder()
                .dataApiModule(new DataApiModule(this))
                .serviceApiModule(new ServiceApiModule(this))
                .networkUtilsApiModule(new NetworkUtilsApiModule(this))
                .activityUtilsApiModule(new ActivityUtilsApiModule())
                .errorUtilsApiModule(new ErrorUtilsApiModule(this))
                .photoFileUtilsApiModule(new PhotoFileUtilsApiModule(this))
                .build();

        initReportHandler();

//        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
//            @Override
//            public void uncaughtException(Thread thread, Throwable ex) {
//                handleUncaughtException(thread, ex);
//            }
//        });
    }

    public DIComponent getComponent() {
        return diComponent;
    }

    private void initReportHandler(){
        String mail;

        try {
            Properties properties = PropertyUtils.getProperties(APPLICATION_PROPERTIES, this);
            mail = properties.getProperty("dev-mail");
        } catch (IOException e) {
            throw new IllegalStateException("Cannot read auth properties", e);
        }

        ReportHandler.install(this, mail);

    }

//    public void handleUncaughtException (Thread thread, Throwable e){
//
//        String mail;
//
//        try {
//            Properties properties = PropertyUtils.getProperties(APPLICATION_PROPERTIES, this);
//            mail = properties.getProperty("dev-mail");
//        } catch (IOException ex) {
//            throw new IllegalStateException("Cannot read auth properties", e);
//        }
//
//        String stackTrace = Log.getStackTraceString(e);
//        String message = e.getMessage();
//        Intent intent = new Intent(Intent.ACTION_SEND);
//        intent.setType("text/plain");
//        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{mail});
//        intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.log_error_name));
//        intent.putExtra(Intent.EXTRA_TEXT, message);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//        ArrayList<Uri> attachments = new ArrayList<Uri>();
//
//        if(stackTrace != null){
//            attachments.add(ReportFilesProvider.setFilePath(
//                    ReportFilesProvider.FILE_INDEX_SYSTEMLOG, stackTrace));
//        }
//        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, attachments);
//
//        startActivity(intent);
//
//        //startActivity(Intent.createChooser(intent, "Send Crash Report Using"));
//
//        }
}
