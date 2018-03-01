package com.android.hdhe.uhf.reader;

import android.util.Log;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SerialPort {

    private static final String TAG = "SerialPort";

    /*
     * Do not remove or rename the field mFd: it is used by native method close();
     */
    private FileDescriptor mFd;
    private FileInputStream mFileInputStream;
    private FileOutputStream mFileOutputStream;
    private boolean trig_on=false;
    public SerialPort(int port, int baudrate, int flags) throws SecurityException, IOException {
        mFd = open(port, baudrate);
        if (mFd == null) {
            Log.e(TAG, "native open returns null");
            throw new IOException();
        }
        mFileInputStream = new FileInputStream(mFd);
        mFileOutputStream = new FileOutputStream(mFd);
    }

    // Getters and setters
    public InputStream getInputStream() {
        return mFileInputStream;
    }

    public OutputStream getOutputStream() {
        return mFileOutputStream;
    }
    public void uhfPowerOn(){
        psampoweron();
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        byte[] bytes = new byte[8];
        try {
            mFileInputStream.read(bytes);
            System.out.println(new String(bytes));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void uhfPowerOff(){
        psampoweroff();
    }


    // JNI
    //湖羲揹諳ㄛ鳳�﹡躁�紱釬曆梟
    private native static FileDescriptor open(int port, int baudrate);
    //壽敕揹諳
    public native void close(int port);

    //耀輸萇埭
    public native void psampoweron();
    public native void psampoweroff();

    static {
        System.loadLibrary("devapi");
        System.loadLibrary("uhf");
    }

}
