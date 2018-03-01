package com.avatlantik.asmp.component;

import com.avatlantik.asmp.activity.AnimalActivity;
import com.avatlantik.asmp.activity.AnimalsActivity;
import com.avatlantik.asmp.activity.AnimalsGroupeActivity;
import com.avatlantik.asmp.activity.BasicActivity;
import com.avatlantik.asmp.activity.BootAct;
import com.avatlantik.asmp.activity.GroupeAnimalActivity;
import com.avatlantik.asmp.activity.HousingActivity;
import com.avatlantik.asmp.activity.ImageActivity;
import com.avatlantik.asmp.activity.NewAnimalActivity;
import com.avatlantik.asmp.activity.ScanActivity;
import com.avatlantik.asmp.activity.ServiceActivity;
import com.avatlantik.asmp.activity.ServiceGroupProcessingActivity;
import com.avatlantik.asmp.activity.SettingActivity;
import com.avatlantik.asmp.activity.TecnikalGroupeAnimalActivity;
import com.avatlantik.asmp.modules.ActivityUtilsApiModule;
import com.avatlantik.asmp.modules.DataApiModule;
import com.avatlantik.asmp.modules.ErrorUtilsApiModule;
import com.avatlantik.asmp.modules.NetworkUtilsApiModule;
import com.avatlantik.asmp.modules.PhotoFileUtilsApiModule;
import com.avatlantik.asmp.modules.ServiceApiModule;
import com.avatlantik.asmp.service.sync.SyncIntentService;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {DataApiModule.class, ServiceApiModule.class,
        NetworkUtilsApiModule.class, ActivityUtilsApiModule.class,
        ErrorUtilsApiModule.class, PhotoFileUtilsApiModule.class})
public interface DIComponent {
    void inject(BootAct bootActivity);
    void inject(BasicActivity basicActivity);
    void inject(SyncIntentService syncIntentService);
    void inject(SettingActivity settingActivity);
    void inject(ScanActivity scanActivity);
    void inject(ServiceActivity serviceActivity);
    void inject(AnimalsActivity animalsActivity);
    void inject(AnimalsGroupeActivity animalsGroupeActivity);
    void inject(GroupeAnimalActivity groupeAnimalActivity);
    void inject(NewAnimalActivity newAnimalActivity);
    void inject(HousingActivity housingActivity);
    void inject(TecnikalGroupeAnimalActivity tecnikalGroupeAnimalActivity);
    void inject(AnimalActivity animalActivity);
    void inject(ServiceGroupProcessingActivity serviceGroupProcessingActivity);
    void inject(ImageActivity imageActivity);
}
