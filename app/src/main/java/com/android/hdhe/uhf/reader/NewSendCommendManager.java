package com.android.hdhe.uhf.reader;

import android.util.Log;

import com.android.hdhe.uhf.readerInterface.CommendManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class NewSendCommendManager implements CommendManager {

    private InputStream in;
    private OutputStream out;

    private final byte HEAD = (byte) 0xBB;
    private final byte END = (byte) 0x7E;

    public static final byte RESPONSE_OK = 0x00; //砒茼痋0K
    /**
     * 溼恀囮啖ㄛ躇鎢渣昫
     */
    public static final byte ERROR_CODE_ACCESS_FAIL = 0x16;
    /**
     * 毞盄⑹郖拸縐え麼EPC測鎢渣昫
     */
    public static final byte ERROR_CODE_NO_CARD = 0x09;
    /**
     * 黍杅擂奀ㄛれ宎ぇ痄講麼杅擂酗僅閉徹杅擂湔揣⑹
     */
    public static final byte ERROR_CODE_READ_SA_OR_LEN_ERROR = (byte)0xA3;
    /**
     * 迡杅擂奀ㄛれ宎ぇ痄講麼杅擂酗僅閉徹杅擂湔揣⑹
     */
    public static final byte ERROR_CODE_WRITE_SA_OR_LEN_ERROR = (byte)0xB3;
    /**
     * 鍾鏗僅詢
     */
    public static final int SENSITIVE_HIHG = 3;
    /**
     * 鍾鏗僅笢
     */
    public static final int SENSITIVE_MIDDLE = 2;
    /**
     * 鍾鏗僅腴
     */
    public static final int SENSITIVE_LOW = 1;
    /**
     * 鍾鏗僅憤腴
     */
    public static final int SENSITIVE_VERY_LOW = 0;


    public static final int LOCK_TYPE_OPEN = 0; // 羲溫
    public static final int LOCK_TYPE_PERMA_OPEN = 1; // 蚗壅羲溫
    public static final int LOCK_TYPE_LOCK = 2; // 坶隅
    public static final int LOCK_TYPE_PERMA_LOCK = 3; // 蚗壅坶隅

    public static final int LOCK_MEM_KILL = 0; // 种障躇鎢
    public static final int LOCK_MEM_ACCESS = 1; // 溼恀躇鎢
    public static final int LOCK_MEM_EPC = 2; // EPC
    public static final int LOCK_MEM_TID = 3; // TID
    public static final int LOCK_MEM_USER = 4; // USER


    private byte[] selectEPC = null;

    public NewSendCommendManager(InputStream serialPortInput, OutputStream serialportOutput){
        in = serialPortInput;
        out = serialportOutput;
    }
    /**
     * 楷冞硌鍔
     * @param cmd
     */
    private void sendCMD(byte[] cmd){
        try {
            out.write(cmd);
            out.flush();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    /**
     * 扢离疏杻薹
     */
    @Override
    public boolean setBaudrate() {
        byte[] cmd = {};
        return false;
    }

    /**
     * 鳳�※瘙憶欐�
     */
    @Override
    public byte[] getFirmware() {
        byte[] cmd = {(byte)0xBB ,(byte)0x00,(byte) 0x03 ,(byte)0x00 ,(byte)0x01 ,(byte)0x00 ,(byte)0x04 ,(byte)0x7E };
        byte[] version = null;
        sendCMD(cmd);
        byte[] response = this.read();
        if(response != null){
            Log.e("", Tools.Bytes2HexString(response, response.length));
            byte[] resolve = handlerResponse(response);
            if(resolve != null &&resolve.length > 1){
                version = new byte[resolve.length - 1];
                System.arraycopy(resolve, 1, version, 0, resolve.length - 1);
            }
        }
        return version;
    }


    /**
     * 扢离黍迡ん腔鍾鏗僅ㄛ
     * @param value
     */
    public void setSensitivity(int value){
        byte[] cmd = {(byte)0xBB ,(byte)0x00 ,(byte)0xF0 ,(byte)0x00 ,(byte)0x04 ,(byte)0x02 ,
                (byte)0x06 ,(byte)0x00 ,(byte)0xA0 ,(byte)0x9C ,(byte)0x7E };
        cmd[5] = (byte)value;
        cmd[cmd.length - 2] = checkSum(cmd);
        sendCMD(cmd);

        byte[] response = this.read();
        if(response != null){
            Log.e("setSensitivity ", Tools.Bytes2HexString(response, response.length));
        }
    }

    /**
     * 黍�＋嬧聿�
     * @return
     */
    private byte[] read(){
        byte[] responseData = null;
        byte[] response = null;
        int available = 0 ;
        int index = 0;
        int headIndex = 0;
        //500ms囀謫戙拸杅擂寀ㄛ拸杅擂殿隙
        try {
            while(index < 10){
                Thread.sleep(50);
                available = in.available();
                //遣喳⑹衄杅擂
                if(available > 7) break;
                index++;
            }
            if(available > 0){
                responseData = new byte[available];
                in.read(responseData);
                for(int i = 0; i < available; i++){
                    if(responseData[i] == HEAD){
                        headIndex = i;
                        break;
                    }
                }
                response = new byte[available - headIndex];
                System.arraycopy(responseData, headIndex, response, 0, response.length);

            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        return response;
    }

    /**
     * 扢离怀堤髡薹
     */
    @Override
    public boolean setOutputPower(int value) {
//		byte[] cmd = {(byte)0xBB ,(byte)0x00 ,(byte)0xB6 ,(byte)0x00 ,(byte)0x02 ,(byte)0x0A ,(byte)0x28
//				,(byte)0xEA ,(byte)0x7E };
//		cmd[5] = (byte)((0xff00 & value)>>8);
//		cmd[6] = (byte)(0xff & value);
//		cmd[cmd.length - 2] = checkSum(cmd);
//		Log.e("", Tools.Bytes2HexString(cmd, cmd.length));
//		sendCMD(cmd);
//		byte[] recv = read();
//		if(recv != null){
////			Log.e("", Tools.Bytes2HexString(recv, recv.length));
//			return true;
//		}
        int mixer = 1;
        int if_g = 3;
        int trd = 432;
        switch (value) {
            case 16:
                mixer = 1;
                if_g = 1;
                trd = 432;
                break;
            case 17:
                mixer = 1;
                if_g = 3;
                trd = 432;
                break;
            case 18:
                mixer = 2;
                if_g = 4;
                trd = 432;
                break;
            case 19:
                mixer = 2;
                if_g = 5;
                trd = 432+64;
            case 20:
                mixer = 2;
                if_g = 6;
                trd = 432+64;
            case 21:
                mixer = 2;
                if_g = 6;
                trd = 432+128;
                break;
            case 22:
                mixer = 3;
                if_g = 6;
                trd = 432+192;
                break;
            case 23:
                mixer = 4;
                if_g = 6;
                trd = 432+192;
                break;
            default:
                mixer = 6;
                if_g = 7;
                trd = 432+192;
                break;
        }


        return setRecvParam(mixer, if_g, trd);
    }

    /**
     * 扢离諉彶賤覃ん統杅
     * 統杅佽隴ㄩ
     * int mixer_g,髦けん崝祔(崝祔峈9dbm,mixer_g腔毓峓峈0~6)
     * int if_g, 笢け溫湮ん崝祔(崝祔峈36dbmㄛif_g腔毓峓峈0~7)
     * int trd 陓瘍賤覃概硉ㄗtrd埣湮擒燭埣輪ㄛ埣苤擒燭埣堈ㄛ毓峓0x01b0(432)~0x0360(864)ㄘ
     */
    public boolean setRecvParam(int mixer_g, int if_g, int trd){
        byte[] cmd = {(byte)0xBB ,(byte)0x00 ,(byte)0xF0 ,(byte)0x00 ,
                (byte)0x04 ,(byte)0x03 ,(byte)0x06 ,(byte)0x01 ,(byte)0xB0 ,
                (byte)0xAE ,(byte)0x7E };
        byte[] recv = null;
        byte[] content = null;
        cmd[5] = (byte)mixer_g;
        cmd[6] = (byte)if_g;
        cmd[7] = (byte)(trd/256);
        cmd[8] = (byte)(trd%256);
        cmd[9] = checkSum(cmd);
        sendCMD(cmd);
        recv = read();
        if(recv != null){
//			Log.e("setRecvParam", Tools.Bytes2HexString(recv, recv.length));
            content = handlerResponse(recv);
            if(content != null){
                return true;
            }
        }
        return false;
    }

    /**
     * 嗣梓ワ攫湔
     * @return
     */
    public List<byte[]> inventoryMulti(){
        unSelectEPC();
        List<byte[]> list = new ArrayList<byte[]>();
        byte[] cmd = {(byte)0xBB ,(byte)0x00 ,(byte)0x27 ,(byte)0x00 ,
                (byte)0x03 ,(byte)0x22 ,(byte)0x27 ,(byte)0x10 ,(byte)0x83 ,(byte)0x7E};
        sendCMD(cmd);
        byte[] response = this.read();
        if(response != null){
            int responseLength = response.length;
//			Log.e("", Tools.Bytes2HexString(response, response.length));
            int start = 0;
            //等桲縐殿隙腔杅擂
//			byte[] sigleCard = new byte[24];
            //嗣桲縐えㄛ蔚眕嗣沭痋 腔跡宒殿隙
            if(responseLength > 15){
//				Log.e("嗣桲縐", Tools.Bytes2HexString(response, response.length));
                //猁�△褊�擂酗僅
                while(responseLength > 5){
//					Log.e("嗣桲縐", Tools.Bytes2HexString(response, response.length));
                    //鳳�﹠縕�腔珨沭硌鍔
                    int paraLen = response[start + 4]&0xff;
                    int singleCardLen = paraLen + 7;
                    //硌鍔祥俇淕泐堤
                    if(singleCardLen > responseLength) break;
                    byte[] sigleCard = new byte[singleCardLen];
                    System.arraycopy(response, start, sigleCard, 0, singleCardLen);

                    byte[] resolve = handlerResponse(sigleCard);
//					Log.e("嗣桲縐", Tools.Bytes2HexString(resolve, resolve.length));
                    //揭燴綴腔杅擂菴珨弇岆硌鍔測鎢ㄛ菴2弇RSSIㄛ菴3-4弇岆PCㄛ菴5弇善郔綴岆EPC
                    if(resolve != null && paraLen > 5){
                        byte[] epcBytes = new byte[paraLen - 5];
                        System.arraycopy(resolve, 4, epcBytes, 0, paraLen - 5);
//						Log.e("揭燴EPC", Tools.Bytes2HexString(epcBytes, epcBytes.length));
                        list.add(epcBytes);
                    }
                    start+=singleCardLen;
                    responseLength-=singleCardLen;
                }
            }else{
                handlerResponse(response);
            }
        }
        return list;
    }

    /**
     * 礿砦嗣梓ワ攫湔
     */
    public void stopInventoryMulti(){
        byte[] cmd = {(byte)0xBB ,(byte)0x00 ,(byte)0x28 ,(byte)0x00 ,
                (byte)0x00 ,(byte)0x28 ,(byte)0x7E};
        sendCMD(cmd);
        byte[] recv = read();

    }

    /**
     * 妗奀攫湔
     */
    @Override
    public List<byte[]> inventoryRealTime() {
        //�＋�恁隅
        unSelectEPC();
        byte[] cmd = {(byte)0xBB ,(byte)0x00 ,(byte)0x22 ,(byte)0x00 ,
                (byte)0x00 ,(byte)0x22 ,(byte)0x7E};
        sendCMD(cmd);
        List<byte[]> list = new ArrayList<byte[]>();
        byte[] response = this.read();
        if(response != null){
            int responseLength = response.length;
//			Log.e("", Tools.Bytes2HexString(response, response.length));
            int start = 0;
            //等桲縐殿隙腔杅擂
//			byte[] sigleCard = new byte[24];
            //嗣桲縐えㄛ蔚眕嗣沭痋 腔跡宒殿隙
            if(responseLength > 6){
//				Log.e("嗣桲縐", Tools.Bytes2HexString(response, response.length));
                //猁�△褊�擂酗僅
                while(responseLength > 5){
                    Log.e("嗣桲縐", Tools.Bytes2HexString(response, response.length));
                    //鳳�﹠縕�腔珨沭硌鍔
                    int paraLen = response[start + 4]&0xff;
                    int singleCardLen = paraLen + 7;
                    //硌鍔祥俇淕泐堤
                    if(singleCardLen > responseLength) break;
                    byte[] sigleCard = new byte[singleCardLen];
                    System.arraycopy(response, start, sigleCard, 0, singleCardLen);

                    byte[] resolve = handlerResponse(sigleCard);
//					Log.e("嗣桲縐", Tools.Bytes2HexString(resolve, resolve.length));
                    //揭燴綴腔杅擂菴珨弇岆硌鍔測鎢ㄛ菴2弇RSSIㄛ菴3-4弇岆PCㄛ菴5弇善郔綴岆EPC
                    if(resolve != null && paraLen > 5){
                        byte[] epcBytes = new byte[paraLen - 5];
                        System.arraycopy(resolve, 4, epcBytes, 0, paraLen - 5);
//						Log.e("揭燴EPC", Tools.Bytes2HexString(epcBytes, epcBytes.length));
                        list.add(epcBytes);
                    }
                    start+=singleCardLen;
                    responseLength-=singleCardLen;
                }
            }else{
                handlerResponse(response);
            }
        }
        return list;
    }

    /**
     * 恁寁梓ワ
     */
    //BB 00 0C 00 13 01 00 00 00 20 60 00 30 75 1F EB 70 5C 59 04 E3 D5 0D 70 AD 7E
    @Override
    public void selectEPC(byte[] epc) {
        byte[] cmd = {(byte)0xBB ,(byte)0x00 ,(byte)0x12 ,(byte)0x00,
                (byte)0x01 ,(byte)0x00 ,(byte)0x13 ,(byte)0x7E };
        this.selectEPC = epc;
        sendCMD(cmd);
        byte[] response = this.read();
        if(response != null){
//			Log.e("select epc", Tools.Bytes2HexString(response, response.length));
        }

    }

    /**
     * �＋�恁寁
     * @return
     */
    public int unSelectEPC(){
        byte[] cmd = {(byte)0xBB ,(byte)0x00 ,(byte)0x12 ,(byte)0x00,
                (byte)0x01 ,(byte)0x01 ,(byte)0x14 ,(byte)0x7E };
        sendCMD(cmd);
        byte[] response = this.read();
        if(response != null){

        }
        return 0;
    }

    /**
     * 扢离select腔統杅ㄛ婓勤縐輛俴紱釬眳ヶ覃蚚
     */
    private void setSelectPara(){
//		byte[] cmd = {(byte)0xBB ,(byte)0x00,(byte)0x0C ,(byte)0x00 ,(byte)0x13 ,
//				(byte)0x01 ,(byte)0x00 ,(byte)0x00 ,(byte)0x00 ,(byte)0x20 ,(byte)0x60 ,
//				(byte)0x00 ,(byte)0x01 ,(byte)0x61 ,(byte)0x05 ,(byte)0xB8 ,(byte)0x03 ,
//				(byte)0x48 ,(byte)0x0C ,(byte)0xD0 ,(byte)0x00 ,(byte)0x03 ,(byte)0xD1 ,
//				(byte)0x9E ,(byte)0x58 ,(byte)0x7E};
        //BB 00 0C 00 0B 01 00 00 00 20 20 00 12 34 56 78 6C 7E
        //BB 00 0C 00 09 01 00 00 00 20 10 00 66 66 12 7E
        if(this.selectEPC != null ){
            byte[] para = new byte[]{(byte)0x01 ,(byte)0x00 ,(byte)0x00 ,
                    (byte)0x00 ,(byte)0x20 ,(byte)(this.selectEPC.length*8) ,(byte)0x00};
            byte[] cmd = new byte[14 + this.selectEPC.length];
            cmd[0] = (byte)0xBB;
            cmd[1] = (byte)0x00;
            cmd[2] = (byte)0x0C ;
            cmd[3] = (byte)0x00;
            cmd[4] = (byte)(7 + this.selectEPC.length);
            System.arraycopy(para, 0, cmd, 5, para.length);
            Log.e("", "select epc");
            System.arraycopy(selectEPC, 0, cmd, 12, selectEPC.length);
            cmd[cmd.length - 2] = checkSum(cmd);
            cmd[cmd.length - 1] = (byte)0x7E;
            Log.e("setSelectPara", Tools.Bytes2HexString(cmd, cmd.length));
            sendCMD(cmd);
            byte[] response = this.read();
            if(response != null){
                Log.e("setSelectPara response", Tools.Bytes2HexString(response, response.length));
            }
//			Log.e("setSelectPara response", Tools.Bytes2HexString(response, response.length));
        }
    }

    /**
     * 黍��6C梓ワ滲杅
     */
    @Override
    public byte[] readFrom6C(int memBank, int startAddr, int length, byte[] accessPassword) {
        //輛俴黍迡紱釬ヶ盄恁寁紱釬腔縐
        this.setSelectPara();
        byte[] cmd = {(byte)0xBB ,(byte)0x00 ,(byte)0x39 ,(byte)0x00 ,(
                byte)0x09 ,(byte)0x00 ,(byte)0x00 ,(byte)0x00 ,
                (byte)0x00 ,(byte)0x03 ,(byte)0x00 ,(byte)0x00 ,(byte)0x00 ,
                (byte)0x08 ,(byte)0x4D ,(byte)0x7E };
        byte[] data = null;
        if(accessPassword == null || accessPassword.length != 4){
            return null;
        }

        System.arraycopy(accessPassword, 0, cmd, 5, 4);
        cmd[9] = (byte) memBank;
        if(startAddr <= 255){
            cmd[10] = 0x00;
            cmd[11] = (byte)startAddr;
        }else{
            int addrH = startAddr/256;
            int addrL = startAddr%256;
            cmd[10] = (byte)addrH;
            cmd[11] = (byte)addrL;
        }
        if(length <= 255){
            cmd[12] = 0x00;
            cmd[13] = (byte)length;
        }else{
            int lengH = length/256;
            int lengL = length%256;
            cmd[12] = (byte)lengH;
            cmd[13] = (byte)lengL;
        }
        cmd[14] = checkSum(cmd);
        sendCMD(cmd);
        byte[] response = this.read();
        if(response != null){
            Log.e("readFrom6c response", Tools.Bytes2HexString(response, response.length));
            byte[] resolve = handlerResponse(response);

            if(resolve != null){
                Log.e("readFrom6c resolve", Tools.Bytes2HexString(resolve, resolve.length));
                //淏都砒茼痋BB 01 39 00 1F 0E 30 00 01 61 05 B8 03 48 0C D0 00 03 D1 9E 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 4F 7E
                if(resolve[0] == (byte)0x39){
                    int lengData = resolve.length - resolve[1] - 2;
                    data = new byte[lengData];
                    System.arraycopy(resolve, resolve[1] + 2, data, 0, lengData);
//					Log.e("readFrom6c", Tools.Bytes2HexString(data, data.length));
                }else{
                    //渣昫痋ㄛ殿隙渣昫測鎢
                    data = new byte[1];
                    data[0] = resolve[1];
//					Log.e("readFrom6c", Tools.Bytes2HexString(data, data.length));
                }
            }
        }
        return data;
    }

    /**
     * 迡6C梓ワ
     */
    @Override
    public boolean writeTo6C(byte[] password, int memBank, int startAddr,
                             int dataLen, byte[] data) {


        //輛俴黍迡紱釬ヶ盄恁寁紱釬腔縐
        this.setSelectPara();
        if(password == null || password.length != 4){
            return false;
        }
        boolean writeFlag = false;
        int cmdLen = 16 + data.length;
        int parameterLen = 9 + data.length;
        byte[] cmd = new byte[cmdLen];
        cmd[0] = (byte) 0xBB;
        cmd[1] = 0x00;
        cmd[2] = 0x49;
        if(parameterLen < 256){
            cmd[3] = 0x00;
            cmd[4] = (byte)parameterLen;
        }else{
            int paraH = parameterLen/256;
            int paraL = parameterLen%256;
            cmd[3] = (byte)paraH;
            cmd[4] = (byte)paraL;
        }
        System.arraycopy(password, 0, cmd, 5, 4);
        cmd[9] = (byte)memBank;
        if(startAddr < 256){
            cmd[10] = 0x00;
            cmd[11] = (byte) startAddr;
        }else{
            int startH = startAddr/256;
            int startL = startAddr%256;
            cmd[10] = (byte) startH;
            cmd[11] = (byte) startL;
        }
        if(dataLen < 256){
            cmd[12] = 0x00;
            cmd[13] = (byte) dataLen;
        }else{
            int dataLenH = dataLen/256;
            int dataLenL = dataLen%256;
            cmd[12] = (byte)dataLenH;
            cmd[13] = (byte)dataLenL;
        }
        System.arraycopy(data, 0, cmd, 14, data.length);
        cmd[cmdLen -2] = checkSum(cmd);
        cmd[cmdLen - 1] = (byte)0x7E;
//		Log.e("write data", Tools.Bytes2HexString(cmd, cmdLen));
        sendCMD(cmd);
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        byte[] response = this.read();
        if(response != null) {
//			Log.e("write data response", Tools.Bytes2HexString(response, response.length));
            byte[] resolve = this.handlerResponse(response);
            if(resolve != null){
//				Log.e("write data resolve", Tools.Bytes2HexString(resolve, resolve.length));
                if(resolve[0] == 0x49 && resolve[resolve.length - 1] == RESPONSE_OK){
                    writeFlag = true;
                }
            }
        }

        return writeFlag;
    }

    /**
     * 坶隅6C梓ワ
     */
    @Override
    public boolean lock6C(byte[] password, int memBank, int lockType) {
        this.setSelectPara();
        byte[] cmd = { HEAD, 0x00, (byte) 0x82, 0x00, 0x07, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x00, 0x00, END };
        int lockPay = 0;
        byte[] recv = null;
        byte[] lockPara = new byte[3];
        // 羲溫
        if (lockType == LOCK_TYPE_OPEN) {
            // System.out.println("羲溫");
//			lockPay = (1 << (20 - 2 * memBank + 1));
            lockPay = (1<<(19 - 2*memBank));
        }
        // 蚗壅羲
        if (lockType == LOCK_TYPE_PERMA_OPEN) {
            // System.out.println("蚗壅羲");
//			lockPay = (1 << (20 - 2 * memBank + 1)) + (1 << (20 - 2 * memBank))
//					+ (1 << (2 * (5 - memBank)));

            lockPay = (1<<(19 - 2*memBank)) + (1<<(19 - 2*memBank - 1)) + (1<<(8 - 2*memBank));
        }
        // 坶隅
        if (lockType == LOCK_TYPE_LOCK) {
            // System.out.println("坶隅");
//			lockPay = (1 << (20 - 2 * memBank + 1))
//					+ (2 << (2 * (5 - memBank)));
            if(memBank == LOCK_MEM_ACCESS){
                lockPay = (1<<17)  + (1<<7);
            }else{
                lockPay = (1<<(19 - 2*memBank))+ (1<<17) + (1<<(9 - 2*memBank)) + (1<<7);
            }

        }
        // 蚗壅坶隅
        if (lockType == LOCK_TYPE_PERMA_LOCK) {
            // System.out.println("蚗壅坶隅");
//			lockPay = (1 << (20 - 2 * memBank + 1)) + (1 << (20 - 2 * memBank))
//					+ (3 << (2 * (5 - memBank)));
            lockPay = (1<<(19 - 2*memBank))+ (1<<(19 - 2*memBank - 1)) + (1<<(9 - 2*memBank)) + (1<<(8 - 2*memBank));
        }
        lockPara = Tools.intToByte(lockPay);
        // 躇鎢
        System.arraycopy(password, 0, cmd, 5, password.length);
        // 坶隅統杅
        System.arraycopy(lockPara, 0, cmd, 9, lockPara.length);
        cmd[cmd.length - 2] = checkSum(cmd);
        Log.e("Lock cmd", Tools.Bytes2HexString(cmd, cmd.length));
        sendCMD(cmd);
        recv = this.read();
        if(recv != null){
            byte[] resolve = this.handlerResponse(recv);
            Log.e("Lock recv", Tools.Bytes2HexString(recv, recv.length));
            if(resolve != null && resolve[0] == (byte)0x82 && resolve[resolve.length - 1] == RESPONSE_OK){
                return true ;
            }
        }
        return false;
    }

    @Override
    public void close(){
        try {
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    //數呾苺桄睿
    @Override
    public byte checkSum(byte[] data) {
        byte crc = 0x00;
        //植硌鍔濬倰濛樓善統杅郔綴珨弇
        for(int i = 1; i < data.length - 2; i++){
            crc+=data[i];
        }
        return crc;
    }

    /**
     * 揭燴砒茼痋
     * @param response
     * @return
     */
    private byte[] handlerResponse(byte[] response){
        byte[] data = null;
        byte crc = 0x00;
        int responseLength = response.length;
        if(response[0] != HEAD) {
            Log.e("handlerResponse", "head error");
            return data;
        }
        if(response[responseLength - 1] != END){
            Log.e("handlerResponse", "end error");
            return data;
        }
        if(responseLength < 7) return data;
        //蛌傖拸睫瘍int
        int lengthHigh = response[3]&0xff;
        int lengthLow = response[4]&0xff;
        int dataLength = lengthHigh*256 + lengthLow;
        //數呾CRC
        crc = checkSum(response);
        if(crc != response[responseLength - 2]){
            Log.e("handlerResponse", "crc error");
            return data;
        }
        if(dataLength != 0 && responseLength == dataLength + 7){
            Log.e("handlerResponse", "response right");
            data = new byte[dataLength + 1];
            data[0] = response[2];
            System.arraycopy(response, 5, data, 1, dataLength);
        }
        return data;
    }

    //扢离け薹
    @Override
    public int setFrequency(int startFrequency, int freqSpace, int freqQuality) {
        int frequency = 1;//峈921.125Mけ薹

        if(startFrequency > 840125 && startFrequency < 844875){//笢弊1
            frequency = (startFrequency - 840125)/250;
        }else if(startFrequency > 920125 && startFrequency < 924875){//笢弊2
            frequency = (startFrequency - 920125)/250;
        }else if(startFrequency > 865100 && startFrequency < 867900){//韁粔
            frequency = (startFrequency - 865100)/200;
        }else if(startFrequency > 902250 && startFrequency < 927750){//藝弊
            frequency = (startFrequency - 902250)/500;
        }
        byte[] cmd = {(byte)0xBB, (byte)0x00, (byte)0xAB, (byte)0x00, (byte)0x01, (byte)0x04, (byte)0xB0, (byte)0x7E};
        cmd[5] = (byte)frequency;
        cmd[6] = checkSum(cmd);
        sendCMD(cmd);
        byte[] recv = read();
        if(recv != null){
            Log.e("frequency",Tools.Bytes2HexString(recv, recv.length));
        }
        return 0;
    }

    /**
     * 扢离馱釬⑹郖
     * @param area
     * @return
     */
    public int setWorkArea(int area){
        //BB 00 07 00 01 01 09 7E
        byte[] cmd = {(byte)0xBB ,(byte)0x00 ,(byte)0x07 ,(byte)0x00 ,(byte)0x01 ,(byte)0x01 ,(byte)0x09 ,(byte)0x7E };
        cmd[5] = (byte) area;
        cmd[6] = checkSum(cmd);
        sendCMD(cmd);
        byte[] recv = read();
        if(recv != null){
            Log.e("setWorkArea",Tools.Bytes2HexString(recv, recv.length));
            handlerResponse(recv);

        }
        return 0;
    }
    /**
     * 鳳�﹉肅妦恀�
     * @return
     */
    public int getFrequency(){
        byte[] cmd = {(byte)0xBB ,(byte)0x00 ,(byte)0xAA ,(byte)0x00 ,(byte)0x00 ,(byte)0xAA ,(byte)0x7E };
        sendCMD(cmd);
        byte[] recv = read();
        if(recv != null){
            Log.e("getFrequency",Tools.Bytes2HexString(recv, recv.length));
            handlerResponse(recv);
        }
        return 0;
    }

    //覃誹擒燭腔滲杅

}