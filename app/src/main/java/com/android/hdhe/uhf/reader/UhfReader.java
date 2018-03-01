package com.android.hdhe.uhf.reader;

import com.android.hdhe.uhf.readerInterface.CommendManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class UhfReader implements CommendManager{

    private static NewSendCommendManager manager ;//////////

//	private static SendCommendManager manager ;

    private static SerialPort serialPort ;//揹諳

    private static int port = 12;//揹諳瘍

    private static int baudRate = 115200;//疏杻薹

    private static InputStream in ;//揹諳怀�蹅�

    private static OutputStream os;//揹諳怀堤霜

    private UhfReader(){};

    private static UhfReader reader;
    //杅擂揣湔⑹
    public static final int MEMBANK_RESEVER = 0;
    public static final int MEMBANK_EPC = 1;
    public static final int MEMBANK_TID = 2;
    public static final int MEMBANK_USER = 3;
    //杅擂悵誘濬倰
    public static final int LOCK_TYPE_OPEN = 0; // 羲溫
    public static final int LOCK_TYPE_PERMA_OPEN = 1; // 蚗壅羲溫
    public static final int LOCK_TYPE_LOCK = 2; // 坶隅
    public static final int LOCK_TYPE_PERMA_LOCK = 3; // 蚗壅坶隅
    //杅擂悵誘⑹
    public static final int LOCK_MEM_KILL = 0; // 种障躇鎢
    public static final int LOCK_MEM_ACCESS = 1; // 溼恀躇鎢
    public static final int LOCK_MEM_EPC = 2; // EPC
    public static final int LOCK_MEM_TID = 3; // TID
    public static final int LOCK_MEM_USER = 4; // USER

//	private UhfReader(InputStream input, OutputStream output){
//		manager = new NewSendCommendManager(input, output);
//	}
	/*
	 * 旮詀惘樁
	 */
//	public static UhfReader getInstance(InputStream input, OutputStream output){
//		if(reader == null){
//			reader = new UhfReader(input, output);
//		}
//		return reader;
//	}

    /**
     * 妏蚚等瞰
     * @return
     */
    public static UhfReader getInstance(){
        if(serialPort == null){
            try {
                serialPort = new SerialPort(port, baudRate, 0);
            } catch (Exception e) {
                return null ;
            }
            serialPort.psampoweron();
            in = serialPort.getInputStream();
            os = serialPort.getOutputStream();
        }
        if(manager == null){
            manager = new NewSendCommendManager(in, os);
//			manager = new SendCommendManager(in, os);
        }
        if(reader == null){
            reader = new UhfReader();
        }
        return reader;
    }

    public void powerOn(){
        serialPort.psampoweron();
    }

    public void powerOff(){
        serialPort.psampoweroff();
    }

    @Override
    public boolean setBaudrate() {

        return manager.setBaudrate();
    }

    @Override
    public byte[] getFirmware() {

        return manager.getFirmware();
    }

    @Override
    public boolean setOutputPower(int value) {
        return manager.setOutputPower(value);
    }

    @Override
    public List<byte[]> inventoryRealTime() {

        return manager.inventoryRealTime();
    }

    @Override
    public void selectEPC(byte[] epc) {
        manager.selectEPC(epc);

    }

    @Override
    public byte[] readFrom6C(int memBank, int startAddr, int length,
                             byte[] accessPassword) {

        return manager.readFrom6C(memBank, startAddr, length, accessPassword);
    }

    @Override
    public boolean writeTo6C(byte[] password, int memBank, int startAddr,
                             int dataLen, byte[] data) {
        return manager.writeTo6C(password, memBank, startAddr, dataLen, data);
    }

    @Override
    public void setSensitivity(int value) {
        manager.setSensitivity(value);

    }

    @Override
    public boolean lock6C(byte[] password, int memBank, int lockType) {
        return manager.lock6C(password, memBank, lockType);
    }

    @Override
    public void close() {
        if(manager != null){
            manager.close();
            manager = null;
        }

        if(serialPort != null){
            serialPort.psampoweroff();
            serialPort.close(port);
            serialPort = null ;
        }
        if(reader != null){
            reader = null;
        }
    }

    @Override
    public byte checkSum(byte[] data) {
        return 0;
    }

    @Override
    public int setFrequency(int startFrequency, int freqSpace, int freqQuality) {

        return manager.setFrequency(startFrequency, freqSpace, freqQuality);
    }

    public void setDistance(int distance){

    }

    public void close(InputStream input, OutputStream output){
        if(manager != null){
            manager = null;
            try {
                input.close();
                output.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }


    /**
     * 陔唳API
     * 扢离馱釬華⑹
     * @param area
     * @return
     */
    public int setWorkArea(int area){

        return manager.setWorkArea(area);
    }

    /**
     * 陔唳API
     * 嗣梓ワ攫湔
     */
    public List<byte[]> inventoryMulti(){

        return manager.inventoryMulti();
    }

    /**
     * 陔唳API
     * 礿砦嗣梓ワ攫湔
     */
    public void stopInventoryMulti(){
        manager.stopInventoryMulti();
    }

    /**
     *  陔唳API
     * 黍�﹉絮�
     */
    public int getFrequency(){
        return manager.getFrequency();
    }
    /**
     *  陔唳API
     * �＋�恁寁
     */
    public int unSelect(){
        return manager.unSelectEPC();
    }

    /**
     * 陔唳API
     *
     * @param mixer_g,髦けん崝祔(mixer_g腔毓峓峈0~6)
     * @param if_g,笢け溫湮ん崝祔(if_g腔毓峓峈0~7)
     * @param trd,陓瘍賤覃概硉ㄗtrd埣湮擒燭埣輪ㄛ埣苤擒燭埣堈ㄛ毓峓0x01b0(432)~0x0360(864)ㄘ
     */
    public void setRecvParam(int mixer_g, int if_g, int trd){
        manager.setRecvParam(mixer_g, if_g, trd);
    }
}
