package com.avatlantik.asmp.service.sync;

import com.avatlantik.asmp.model.json.DownloadResponse;
import com.avatlantik.asmp.model.json.UploadRequest;
import com.avatlantik.asmp.model.json.UploadResponse;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;

import static com.avatlantik.asmp.common.Consts.CONNECT_PATTERN_URL;

public interface SyncService {

    @GET(CONNECT_PATTERN_URL)
    Call<DownloadResponse> download(@QueryMap Map<String, String> params);

    @Multipart
    @POST(CONNECT_PATTERN_URL)
    Call<UploadResponse> uploadWithDocuments(@QueryMap Map<String, String> params,
                                             @Part("data_dto") UploadRequest request,
                                             @Part List<MultipartBody.Part> documents);
}
