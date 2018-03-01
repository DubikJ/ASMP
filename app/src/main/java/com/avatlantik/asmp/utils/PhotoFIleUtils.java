package com.avatlantik.asmp.utils;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.avatlantik.asmp.model.PhotoItem;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import static android.os.Environment.DIRECTORY_DCIM;
import static android.os.Environment.getExternalStoragePublicDirectory;
import static com.avatlantik.asmp.common.Consts.DIRECTORY_APP;
import static com.avatlantik.asmp.common.Consts.DIR_PICTURES;
import static com.avatlantik.asmp.common.Consts.IMAGE_QUALITY;
import static com.avatlantik.asmp.common.Consts.NAME_TYPEFILE_PHOTO;
import static com.avatlantik.asmp.common.Consts.TAGLOG_IMAGE;

public class PhotoFIleUtils {
    private Context mContext;

    public PhotoFIleUtils(Context mContext) {
        this.mContext = mContext;
    }

    public Boolean saveImage(boolean extStorage, Bitmap finalBitmap, String fname) {

        if(extStorage) {

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, IMAGE_QUALITY, bytes);
            File pathFile = new File(getPictureDir(extStorage)
                    + File.separator + fname + NAME_TYPEFILE_PHOTO);
            try {
                pathFile.createNewFile();
                FileOutputStream fo = new FileOutputStream(pathFile);
                fo.write(bytes.toByteArray());
                fo.close();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }

        }else{

            ContextWrapper cw = new ContextWrapper(mContext);

            File directory = cw.getDir(DIR_PICTURES, Context.MODE_PRIVATE);
            File pathFile = new File(directory, fname + NAME_TYPEFILE_PHOTO);
            try {

                FileOutputStream out = new FileOutputStream(pathFile);
                finalBitmap.compress(Bitmap.CompressFormat.JPEG, IMAGE_QUALITY, out);
                out.flush();
                out.close();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }


        }
    }

    public ArrayList<PhotoItem> getAllShownImagesPath() {
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        ArrayList<PhotoItem> listOfAllImages = new ArrayList<PhotoItem>();
        String absolutePathOfImage = null;
        String nameImage = null;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = { MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME };

        cursor = mContext.getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);

            listOfAllImages.add(
                    new PhotoItem(
                            getNameFileFromPath(absolutePathOfImage),
                            "",
                            new File(absolutePathOfImage)));
        }
        return listOfAllImages;
    }

    public File getPhotoFile(boolean extStorage, String nameFile) {
        if(extStorage) {
            File directory = getPictureDir(extStorage);
            if (!directory.exists()) {
                Log.e(TAGLOG_IMAGE, "Picture directory not exist.");
                return null;
            }

            File outfile = new File(directory, File.separator + nameFile + NAME_TYPEFILE_PHOTO);
            if (!outfile.exists()) {
                Log.e(TAGLOG_IMAGE, "Picture not exist.");
                return null;
            }
            return outfile;
        }else{
            ContextWrapper cw = new ContextWrapper(mContext);
            File directory = cw.getDir(DIR_PICTURES, Context.MODE_PRIVATE);

            if (!directory.exists()) {
                Log.e(TAGLOG_IMAGE, "Picture directory not exist.");
                return null;
            }

            File outfile = new File(directory,nameFile + NAME_TYPEFILE_PHOTO);
            if (!outfile.exists()) {
                Log.e(TAGLOG_IMAGE, "Picture not exist.");
                return null;
            }
            return outfile;
        }

    }

    public File getRootDir() {
        File directory =  getExternalStoragePublicDirectory(DIRECTORY_APP);
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                Log.e(TAGLOG_IMAGE, "Failed to create storage directory.");
                return null;
            }
        }

        return directory;
    }

    public File getPictureDir(Boolean extStorage) {
        if(extStorage) {
            File rootDir = getRootDir();
            if (!rootDir.exists()) {
                Log.e(TAGLOG_IMAGE, "Root directory not exist.");
                return null;
            }

            File directory = new File(rootDir.getPath() + File.separator + DIR_PICTURES);
            if (!directory.exists()) {
                if (!directory.mkdirs()) {
                    Log.e(TAGLOG_IMAGE, "Failed to create picture directory.");
                    return null;
                }
            }

            return directory;
        }else{
            return getCameraTempDir();
        }
    }

    public File getCameraTempDir() {
        File dcimDirectory = getExternalStoragePublicDirectory(DIRECTORY_DCIM);
        if (!dcimDirectory.exists()) {
            if (!dcimDirectory.mkdirs()) {
                Log.e(TAGLOG_IMAGE, "Failed to create storage directory.");
                return null;
            }
        }
        File directory = new File(dcimDirectory.getPath() + File.separator + DIRECTORY_APP);
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                Log.e(TAGLOG_IMAGE, "Failed to create picture directory.");
                return null;
            }
        }
        return directory;
    }

    public String getNameFileFromPath(String path){
        return path.substring(path.lastIndexOf("/")+1);
    }

    public boolean copyFile(File sourceFile, File destFile) throws IOException {
        if (!sourceFile.exists()) {
            return false;
        }

        FileChannel source = null;
        FileChannel destination = null;
        source = new FileInputStream(sourceFile).getChannel();
        destination = new FileOutputStream(destFile).getChannel();
        if (destination != null && source != null) {
            destination.transferFrom(source, 0, source.size());
        }
        if (source != null) {
            source.close();
        }
        if (destination != null) {
            destination.close();
        }
        return true;
    }


    public Bitmap getBitmapFromPathImage(String pathPhoto) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        return BitmapFactory.decodeFile(pathPhoto, bmOptions);
    }

    public Bitmap getBitmapFromArray(byte[] imageBytes) {
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
    }

    public byte[] getArrayBytesFromBitmap(Bitmap image) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, IMAGE_QUALITY, stream);
        return stream.toByteArray();
    }

    public Uri getUriFromBitmap(Context context, Bitmap image) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, IMAGE_QUALITY, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), image, "Title", null);
        return Uri.parse(path);
    }

    public Bitmap getBitmapFromUri(Context context, Uri imageUri) {
        try {
            return MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean bitmapWriteToFile(byte[] data, String fileName){
        try {
            FileOutputStream out = new FileOutputStream(fileName);
            out.write(data);
            out.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean deleteFile(boolean extStorage, String nameFile){
        if(extStorage) {
            File photoFile = getPhotoFile(extStorage, nameFile);
            if (photoFile != null && photoFile.exists()) {
                photoFile.delete();
                return true;
            }
            return false;
        }else{
            ContextWrapper cw = new ContextWrapper(mContext);
            File directory = cw.getDir(DIR_PICTURES, Context.MODE_PRIVATE);
            File pathFile =new File(directory,nameFile + NAME_TYPEFILE_PHOTO);
            pathFile.delete();
            mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(pathFile)));
            return true;
        }
    }

    public File getPhotoFileFromCameraTemp(String nameFile) {
        File directory = getCameraTempDir();
        if (!directory.exists()) {
            Log.e(TAGLOG_IMAGE, "Picture directory not exist.");
            return null;
        }

        return new File(directory, File.separator + nameFile + NAME_TYPEFILE_PHOTO);

    }

    public Boolean deleteFileInCameraTemp(String nameFile){

        File photoFile = getPhotoFileFromCameraTemp(nameFile);

        if (photoFile!=null && photoFile.exists()){
            photoFile.delete();
            return true;
        }
        return false;
    }

    public void clearAllImageFiles(){

        File rootDir = getRootDir();
        if (!rootDir.exists()) {
            if (!rootDir.exists()) {
                return;
            }
        }
        if (rootDir!=null && rootDir.exists()){
            rootDir.delete();
        }

        ContextWrapper cw = new ContextWrapper(mContext);
        File directoryIntStor = cw.getDir(DIR_PICTURES, Context.MODE_PRIVATE);

        if (directoryIntStor!=null && directoryIntStor.exists()){
            directoryIntStor.delete();
        }
    }

}
