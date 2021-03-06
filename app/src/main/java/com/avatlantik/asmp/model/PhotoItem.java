package com.avatlantik.asmp.model;

import java.io.File;

public class PhotoItem {
    private String title;
    private String externalId;
    private File file;

    public PhotoItem(String title, String externalId) {
        this.title = title;
        this.externalId = externalId;
    }

    public PhotoItem(String title, String externalId, File file) {
        this.title = title;
        this.externalId = externalId;
        this.file = file;
    }

    public String getTitle() {
        return title;
    }

    public File getFile() {
        return file;
    }

    public String getExternalId() {
        return externalId;
    }
}
