package com.avatlantik.asmp.model.json;

import com.google.gson.annotations.Expose;

public class DownloadResponse {
    @Expose
    private DownloadWorkDayDTO workDay;
    @Expose
    private String error;

    public DownloadResponse(DownloadWorkDayDTO workDay) {
        this.workDay = workDay;
    }

    public DownloadWorkDayDTO getWorkDay() {
        return workDay;
    }

    public String getError() {
        return error;
    }
}
