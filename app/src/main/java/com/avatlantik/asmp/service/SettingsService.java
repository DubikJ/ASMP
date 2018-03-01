package com.avatlantik.asmp.service;

public interface SettingsService {

    String readData(String key);

    void saveData(String key, String value);

    void clearData();

    enum ConnectionType {
        WIFI, USB
    }
}
