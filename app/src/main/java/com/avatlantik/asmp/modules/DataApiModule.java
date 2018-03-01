package com.avatlantik.asmp.modules;

import android.app.Application;
import com.avatlantik.asmp.repository.DataRepository;
import com.avatlantik.asmp.repository.DataRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataApiModule {

    private Application application;

    public DataApiModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public DataRepository getDataRepository() {
        return new DataRepositoryImpl(application.getBaseContext().getContentResolver());
    }
}
