package com.avatlantik.asmp.service.sync;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.provider.Settings;
import android.util.Base64;

import com.avatlantik.asmp.R;
import com.avatlantik.asmp.app.ASMPApplication;
import com.avatlantik.asmp.db.DbContract;
import com.avatlantik.asmp.model.APIError;
import com.avatlantik.asmp.model.ParameterInfo;
import com.avatlantik.asmp.model.db.Animal;
import com.avatlantik.asmp.model.db.ServiceData;
import com.avatlantik.asmp.model.db.ServiceDone;
import com.avatlantik.asmp.model.db.ServiceDoneVetExercise;
import com.avatlantik.asmp.model.json.DownloadResponse;
import com.avatlantik.asmp.model.json.DownloadServiceDTO;
import com.avatlantik.asmp.model.json.DownloadWorkDayDTO;
import com.avatlantik.asmp.model.json.ExternalIdPair;
import com.avatlantik.asmp.model.json.PhotoDTO;
import com.avatlantik.asmp.model.json.UploadAnimalDTO;
import com.avatlantik.asmp.model.json.UploadDataDTO;
import com.avatlantik.asmp.model.json.UploadRequest;
import com.avatlantik.asmp.model.json.UploadResponse;
import com.avatlantik.asmp.model.json.UploadServiceDoneDTO;
import com.avatlantik.asmp.repository.DataRepository;
import com.avatlantik.asmp.utils.ErrorUtils;
import com.avatlantik.asmp.utils.NetworkUtils;
import com.avatlantik.asmp.utils.PhotoFIleUtils;

import org.joda.time.LocalDateTime;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

import static com.avatlantik.asmp.common.Consts.DATE_SYNC;
import static com.avatlantik.asmp.common.Consts.NUMBER_MESSAGE;
import static com.avatlantik.asmp.common.Consts.STATUS_ERROR_SYNC;
import static com.avatlantik.asmp.common.Consts.STATUS_FINISHED_SYNC;
import static com.avatlantik.asmp.common.Consts.STATUS_STARTED_SYNC;
import static com.avatlantik.asmp.common.Consts.TYPE_RESULT_SERVICE_VETERINARY;
import static com.avatlantik.asmp.common.Consts.USE_EXTERNAL_STORAGE;
import static com.avatlantik.asmp.utils.Db2JsonModelConverter.convertAnimal2Json;
import static com.avatlantik.asmp.utils.Db2JsonModelConverter.convertServiceDone2Json;
import static com.avatlantik.asmp.utils.Db2JsonModelConverter.convertServiceDoneVetExercise2Json;
import static com.avatlantik.asmp.utils.Json2DbModelConverter.convertAnimal;
import static com.avatlantik.asmp.utils.Json2DbModelConverter.convertAnimalDisposList;
import static com.avatlantik.asmp.utils.Json2DbModelConverter.convertAnimalStatusList;
import static com.avatlantik.asmp.utils.Json2DbModelConverter.convertAnimalType;
import static com.avatlantik.asmp.utils.Json2DbModelConverter.convertBreedList;
import static com.avatlantik.asmp.utils.Json2DbModelConverter.convertFarrowingCycle;
import static com.avatlantik.asmp.utils.Json2DbModelConverter.convertHertTypeList;
import static com.avatlantik.asmp.utils.Json2DbModelConverter.convertHistoryList;
import static com.avatlantik.asmp.utils.Json2DbModelConverter.convertHousings;
import static com.avatlantik.asmp.utils.Json2DbModelConverter.convertServiceData;
import static com.avatlantik.asmp.utils.Json2DbModelConverter.convertServiceDone;
import static com.avatlantik.asmp.utils.Json2DbModelConverter.convertVetExerciseList;
import static com.avatlantik.asmp.utils.Json2DbModelConverter.convertVetPreparatList;

public class SyncIntentService extends IntentService {
    public static final String SYNC_RECEIVER = "sync_receiver";
    public static final String DELIMITER = "_";

    @Inject
    DataRepository dataRepository;
    @Inject
    NetworkUtils networkUtils;
    @Inject
    ErrorUtils errorUtils;
    @Inject
    PhotoFIleUtils photoFIleUtils;

    private SyncService syncService;
    private boolean useExternalStorage;

    public SyncIntentService() {
        super(SyncIntentService.class.getName());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ((ASMPApplication) getApplication()).getComponent().inject(this);
        useExternalStorage = Boolean.valueOf(dataRepository.getUserSetting(USE_EXTERNAL_STORAGE));
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        final ResultReceiver receiver = intent.getParcelableExtra(SYNC_RECEIVER);
        final Bundle bundle = new Bundle();

        if (!networkUtils.checkEthernet()) {
            bundle.putString(Intent.EXTRA_TEXT, getResources().getString(R.string.error_internet_connecting));
            receiver.send(STATUS_ERROR_SYNC, bundle);
            return;
        }

        Map<String, String> paramsUrl = new HashMap<String, String>();
        paramsUrl.put("dev_id", Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
        paramsUrl.put("dev_serial", Build.SERIAL);
        String numMessage = dataRepository.getUserSetting(NUMBER_MESSAGE);
        paramsUrl.put("num_message", numMessage == null || numMessage.isEmpty() ? "" : numMessage);
        paramsUrl.put("device", Build.MANUFACTURER
                + " " + Build.MODEL + " " + Build.VERSION.RELEASE
                + " " + Build.VERSION_CODES.class.getFields()[android.os.Build.VERSION.SDK_INT].getName());



        syncService = SyncServiceFactory.createService(
                SyncService.class,
                this.getBaseContext());
        receiver.send(STATUS_STARTED_SYNC, bundle);
        List<Animal> changedAnimals = dataRepository.getAnimalsByListId(dataRepository.getChangedElements(DbContract.AnimalContract.TABLE_NAME));
        List<ServiceDone> changedSevices = dataRepository.getServiceDoneByListTableId(dataRepository.getChangedElements(DbContract.ServiceDoneContract.TABLE_NAME));
        if (changedSevices.size() > 0 || changedAnimals.size() > 0) {
            final UploadRequest request = new UploadRequest(getUploadData(changedAnimals, changedSevices));
            List<MultipartBody.Part> documents = getDocuments(changedAnimals);
            try {
                Response<UploadResponse> uploadResponse = syncService.uploadWithDocuments(paramsUrl, request, documents).execute();
                if (uploadResponse.isSuccessful()) {
                    UploadResponse response = uploadResponse.body();
                    if(response.getError()!=null&&!response.getError().isEmpty()){
                        bundle.putString(Intent.EXTRA_TEXT, response.getError());
                        receiver.send(STATUS_ERROR_SYNC, bundle);
                        return;
                    }else if (response.getExternalIdPairs() != null) {
                        for (ExternalIdPair externalIdPair : response.getExternalIdPairs()) {
                            dataRepository.updateAnimalExternalId(
                                    externalIdPair.getAppExternalId(),
                                    externalIdPair.getNewExternalId());
                        }
                    }
                    dataRepository.clearChangedElement();
                } else {
                    APIError error = errorUtils.parseErrorCode(uploadResponse.code());
                    bundle.putString(Intent.EXTRA_TEXT, error.getMessage());
                    receiver.send(STATUS_ERROR_SYNC, bundle);
                    return;
                }
            } catch (Exception exception) {
                APIError error = errorUtils.parseErrorMessage(exception);
                bundle.putString(Intent.EXTRA_TEXT, error.getMessage());
                receiver.send(STATUS_ERROR_SYNC, bundle);
                return;
            }
        }
        try {
            Response<DownloadResponse> downloadResponse = syncService.download(paramsUrl).execute();
            if (downloadResponse.isSuccessful()) {
                DownloadResponse body = downloadResponse.body();
                if(body.getError() != null && !body.getError().isEmpty()){
                    bundle.putString(Intent.EXTRA_TEXT, body.getError());
                    receiver.send(STATUS_ERROR_SYNC, bundle);
                    return;
                }else if(body.getWorkDay() == null) {
                    bundle.putString(Intent.EXTRA_TEXT, getResources().getString(R.string.error_no_data_sync));
                    receiver.send(STATUS_ERROR_SYNC, bundle);
                    return;
                }
                updateDb(body);
                bundle.putString(Intent.EXTRA_TEXT, getResources().getString(R.string.sync_success));
                receiver.send(STATUS_FINISHED_SYNC, bundle);
            } else {
                APIError error = errorUtils.parseErrorCode(downloadResponse.code());
                bundle.putString(Intent.EXTRA_TEXT, error.getMessage());
                receiver.send(STATUS_ERROR_SYNC, bundle);

            }
        } catch (Exception exception) {
            APIError error = errorUtils.parseErrorMessage(exception);
            bundle.putString(Intent.EXTRA_TEXT, error.getMessage());
            receiver.send(STATUS_ERROR_SYNC, bundle);
        }

    }

    private List<MultipartBody.Part> getDocuments(List<Animal> animals ) {

        List<MultipartBody.Part> documentsDiff = new ArrayList<>();

        for (Animal animal : animals) {
            if (animal.getPhoto() != null && !animal.getPhoto().isEmpty()) {
                File documentFile = photoFIleUtils.getPhotoFile(useExternalStorage, animal.getPhoto());
                if (documentFile == null) continue;

                RequestBody request = RequestBody.create(MediaType.parse("multipart/form-data"), documentFile);
                documentsDiff.add(MultipartBody.Part.createFormData(
                        "photo" + DELIMITER + "|" + animal.getTypeAnimal()  + "|" + animal.getExternalId(),
                        documentFile.getName(),
                        request));
            }
        }

        return documentsDiff;
    }

    private UploadDataDTO getUploadData( List<Animal> changedAnimals, List<ServiceDone> changedSevices) {

        List<UploadServiceDoneDTO> resultServiceDone = new ArrayList<>();

        for (ServiceDone serviceDone : changedSevices) {

            Animal animal = dataRepository.getAnimalById(serviceDone.getAnimalId());

            ServiceData serviceData = dataRepository.getServiceById(serviceDone.getServiceId());
            if(serviceData.getTypeResult() == TYPE_RESULT_SERVICE_VETERINARY){
                List<ServiceDoneVetExercise> serviceDoneVetExerciseList = dataRepository.getServiceDoneVetExerciseByAnimalId(serviceDone.getAnimalId());
                resultServiceDone.addAll(convertServiceDoneVetExercise2Json(serviceDone, animal, serviceDoneVetExerciseList));
            }else {
                resultServiceDone.add(convertServiceDone2Json(serviceDone, animal));
            }

        }

        List<UploadAnimalDTO> resultAnimals = new ArrayList<>();

        for (Animal animal : changedAnimals) {

            resultAnimals.add(convertAnimal2Json(animal));

        }

        return new UploadDataDTO(
                resultServiceDone,
                resultAnimals);
    }

    private void updateDb(DownloadResponse response) {
        DownloadWorkDayDTO workDay = response.getWorkDay();

        insertServiceListInDB(workDay.getServices());

        List<ServiceDone> serviceDoneList = new ArrayList<>();
        List<ServiceDoneVetExercise> serviceDoneVetExerciseList = new ArrayList<>();
        convertServiceDone(workDay.getServiceDoneDTO(), serviceDoneList, serviceDoneVetExerciseList);

        dataRepository.insertServiceDoneList(serviceDoneList);
        dataRepository.insertServiceDoneVetExerciseList(serviceDoneVetExerciseList);
        dataRepository.insertAnimalTypeList(convertAnimalType(workDay.getAnimals()));
        dataRepository.insertAnimalsList(convertAnimal(workDay.getAnimals()));
        dataRepository.insertHousingList(convertHousings(workDay.getHousings()));
        dataRepository.insertAnimalHistoryList(convertHistoryList(workDay.getAnimalHistoryDTOList()));
        dataRepository.insertFarrowingCycleList(convertFarrowingCycle(workDay.getFarrowingCyclesDTOList()));
        dataRepository.insertBreedList(convertBreedList(workDay.getBreedDTOList()));
        dataRepository.insertAnimalStatusList(convertAnimalStatusList(workDay.getAnimalStatusDTOList()));
        dataRepository.insertAnimalDisposList(convertAnimalDisposList(workDay.getAnimalDisposDTOList()));
        dataRepository.insertHertTypeList(convertHertTypeList(workDay.getHertTypeDTOList()));
        dataRepository.insertUsersServerList(workDay.getUsersServer());
        dataRepository.insertConformityServiceGroup(workDay.getConfServGroupDTOList());
        dataRepository.insertVetExerciseList(convertVetExerciseList(workDay.getVetExerciseDTOList()));
        dataRepository.insertVetPreparatList(convertVetPreparatList(workDay.getVetPreparatDTOList()));

        savePhoto(workDay.getPhotoDTOList());

        dataRepository.insertUserSetting(new ParameterInfo(NUMBER_MESSAGE, String.valueOf(workDay.getNumMessage())));
        dataRepository.insertUserSetting(new ParameterInfo(DATE_SYNC, String.valueOf(LocalDateTime.now().toDate().getTime())));
    }

    private void insertServiceListInDB(List<DownloadServiceDTO> services) {

        dataRepository.insertServiceDataList(convertServiceData(services));

        for (DownloadServiceDTO downloadServiceDTO : services){
            dataRepository.insertAnimalTypeToServiceList(downloadServiceDTO.getExternalId(), downloadServiceDTO.getAnimalsTypeDTO());
        }
    }

    private void savePhoto(List<PhotoDTO> photoDTOList) {

       // File pathPictureDir = photoFIleUtils.getPictureDir();

        for (PhotoDTO photoDTO : photoDTOList) {
            String photoName = photoDTO.getName();

            try {
                byte[] imageBytes = Base64.decode(photoDTO.getPhoto(), Base64.DEFAULT);
                Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

                photoFIleUtils.saveImage(useExternalStorage, decodedImage, photoName);
            }catch (Exception e){

            }

//            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//            decodedImage.compress(Bitmap.CompressFormat.JPEG, 60, bytes);
//            File f = new File(pathPictureDir
//                    + File.separator + photoName + NAME_TYPEFILE_PHOTO);
//            try {
//                f.createNewFile();
//                FileOutputStream fo = new FileOutputStream(f);
//                fo.write(bytes.toByteArray());
//                fo.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

        }
    }
}
