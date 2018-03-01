package com.android.hdhe.uhf.reader;

import android.util.Log;

import com.android.hdhe.uhf.Constants.Constants;
import com.android.hdhe.uhf.readerInterface.CommendManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class SendCommendManager implements CommendManager{

    private InputStream in;
    private OutputStream out;

    public SendCommendManager(){}
    public SendCommendManager(InputStream in, OutputStream out){
        this.in = in ;
        this.out = out;
    }

//	public SendCommendManager(int serialPort, int baud, int flag ){
//
//	}
    /////////////////////////炵苀扢离滲杅//////////////////////////////////////////

    //扢离疏杻薹
    public boolean setBaudrate() {
        boolean flag = false;
        byte[] cmd = new byte[6];
        cmd[0] = Constants.HEAD;
        cmd[1] = (byte) (0x04);//len
        cmd[2] = (byte) (0xFF);//address
        cmd[3] = Constants.CMD_SET_BAUD;//cmd
        cmd[4] = (byte) (0x04);//04峈疏杻薹115200,03峈38400
        cmd[5] = checkSum(cmd, cmd.length - 1);
        sendToReader(cmd);
        //諉彶殿隙杅擂
        byte[] recv = this.read();
        if(recv != null){
//			Log.i("set Baudrate", Tools.Bytes2HexString(recv, recv.length));
            if(isRecvData(recv) == 0) {
                if(Integer.valueOf(recv[3]) == Constants.CMD_SET_BAUD && Integer.valueOf(recv[4]) == Constants.CMD_SUCCESS) {
                    flag = true;
                }
            }
        }else{
            Log.i("readFrom6C", "硌鍔閉奀");
        }
        return flag;
    }

    //鳳�±票�唳掛
    public byte[] getFirmware() {
        byte[] cmd = new byte[5];
        cmd[0] = Constants.HEAD;
        cmd[1] = (byte) (0x03);
        cmd[2] = Constants.ADDR;
        cmd[3] = Constants.CMD_GET_FIRMWARE_VERSION;
        cmd[4] = checkSum(cmd, cmd.length - 1);
        sendToReader(cmd);
        byte[] recv = this.read();
        if(recv != null){
            System.out.println(new String(recv));
        }
        return recv;
        //
    }

    public byte[] getFrequency(){
        byte[] cmd = {(byte)0xA0 ,(byte)0x03 ,(byte)0x01 ,(byte)0x79 ,(byte)0xE3};
        sendToReader(cmd);
        byte[] recv = this.read();
        if(recv != null){
            System.out.println(Tools.Bytes2HexString(recv, recv.length));
        }
        return null;
    }

    /**
     *
     * @param startFrequency れ宎け萸
     * @param freqSpace け萸潔路
     * @param freqQuality け萸杅講
     * @return
     */
    public int setFrequency(int startFrequency, int freqSpace, int freqQuality){
        byte[] cmd = new byte[11];
        cmd[0] = Constants.HEAD;
        cmd[1] = 0x09;
        cmd[2] = Constants.ADDR;
        cmd[3] = 0x78; //赻隅砱扢离け萸
        cmd[4] = 0x04;
        cmd[5] = (byte) (freqSpace/10);
        cmd[6] = (byte) freqQuality;
        cmd[7] = (byte) ((0xff0000&startFrequency)>>16);
        cmd[8] = (byte) ((0xff00&startFrequency)>>8);
        cmd[9] = (byte) (0xff&startFrequency);
        cmd[10] = checkSum(cmd, cmd.length - 1);
        System.out.println(Tools.Bytes2HexString(cmd, cmd.length));
        sendToReader(cmd);
        byte[] recv = this.read();
        if(recv != null){
            System.out.println(Tools.Bytes2HexString(recv, recv.length));
        }
        return 0;
    }

    //扢离馱釬毞盄
    public void setWorkAntenna() {
        byte[] cmd = new byte[6];
        cmd[0] = Constants.HEAD;
        cmd[1] = (byte) (0x04);
        cmd[2] = Constants.ADDR;
        cmd[3] = Constants.CMD_SET_WORK_ANTENNA;
        cmd[4] = (byte) (0x00);//00峈毞盄1ㄛ01峈毞盄2ㄛ02峈毞盄3ㄛ03峈毞盄4ㄛ蘇�玴者嬥�1
        cmd[5] = checkSum(cmd, cmd.length - 1);
        sendToReader(cmd);

    }
    //黍�﹋銙鶾汕�
//	public byte[] getOutputPower(){
//
//		return null;
//	}
    //扢离怀�輮銙鶾汕�
    public boolean setOutputPower(int value){
        byte[] cmd = new byte[6];
        cmd[0] = Constants.HEAD;;
        cmd[1] = (byte) (0x04);
        cmd[2] = Constants.ADDR;
        cmd[3] = (byte) (0x76);
        cmd[4] = (byte) value;
        cmd[5] = checkSum(cmd, cmd.length - 1);
//		Log.i("setOutputPower*****", Tools.Bytes2HexString(cmd, cmd.length));
        sendToReader(cmd);
        this.read();
        return true;
    }
    ///////////////////////////////////ISO18000-6C///////////////////////////////////////
    //妗奀攫湔
    public List<byte[]> inventoryRealTime() {
        List<byte[]> epcList = new ArrayList<byte[]>();
        byte[] cmd = new byte[6];
        cmd[0] = Constants.HEAD;
        cmd[1] = (byte) (0x04);
        cmd[2] = Constants.ADDR;//adress
        cmd[3] = Constants.CMD_6C_REAL_TIME_INVENTORY;
        cmd[4] = (byte) (0x01);
        cmd[5] = checkSum(cmd, cmd.length - 1);
        sendToReader(cmd);
        Log.i("", "send inventory real time***");
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //諉彶殿隙杅擂
        byte[] recv = this.read();
        if(recv != null){
            int length = recv.length;
            List<byte[]> srcData = new ArrayList<byte[]>();
            int start = 0;
            while(length > 0){
                //瓚剿婦芛
                if(recv[start] != Constants.HEAD){
                    break;
                }else{
                    int dataLen = (recv[start + 1]&(0xFF));
                    byte[] data = new byte[dataLen + 2];
                    if(data.length > 12 && data.length <= length){
                        System.arraycopy(recv, start, data, 0, data.length);
//					Log.i("data *******", Tools.Bytes2HexString(data, data.length));
                        byte []epc = new byte[data.length - 9];
                        System.arraycopy(data, 7, epc, 0, data.length - 9);
//					System.out.println(Tools.Bytes2HexString(epc, epc.length));
                        epcList.add(epc);
                    }
                    start += data.length;
                    length -= data.length;

                }
            }

        }else{
            Log.i("realTimeInventory", "硌鍔閉奀");
        }
        return epcList;
    }
    //恁隅梓ワ
    public void selectEPC(byte[] epc) {
        int epcLen = epc.length;
        byte[] cmd = new byte[7 + epcLen];
        cmd[0] = Constants.HEAD;
        cmd[1] = (byte) (0x11);
        cmd[2] = Constants.ADDR;//adress
        cmd[3] = Constants.CMD_6C_SET_ACCESS_EPC_MATCH;
        cmd[4] = (byte) (0x00);
        cmd[5] = (byte) (0x0C);//EPC酗僅
        System.arraycopy(epc, 0, cmd, 6, epcLen);//6-18峈EPC
        cmd[6 + epcLen] = checkSum(cmd, cmd.length - 1);
//		Log.i("Select epc****", Tools.Bytes2HexString(cmd, cmd.length));
        //楷冞硌鍔
        sendToReader(cmd);
        //諉彶殿隙杅擂
        byte[] recv = this.read();
        if(recv != null){
//			Log.i("", Tools.Bytes2HexString(recv, recv.length));
        }else{
            Log.i("selectEPC", "硌鍔閉奀");
        }
    }

    /*
     * 黍杅擂
     * int memBank杅擂⑹
     * int startAddr杅擂⑹れ宎華硊ㄗ眕趼峈等弇ㄘ1趼=2趼誹
     * int length猁黍�△騫�擂酗僅(眕趼峈等弇)
     * 殿隙腔byte[] 峈  EPC + 黍�△騫�擂
     */
    public byte[] readFrom6C(int memBank, int startAddr, int length) {
        byte[] data = null;
        byte[] cmd = new byte[8];
        cmd[0] = Constants.HEAD;
        cmd[1] = (byte) (0x06);
        cmd[2] = Constants.ADDR;//address
        cmd[3] = Constants.CMD_6C_READ;
        cmd[4] = (byte) memBank;
        cmd[5] = (byte) startAddr;
        cmd[6] = (byte) length;
        cmd[7] = checkSum(cmd, cmd.length - 1);
        //楷冞硌鍔
        sendToReader(cmd);
//		Log.i("READ DATA*****", Tools.Bytes2HexString(cmd, cmd.length));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        byte[] recv = this.read();
        if(recv != null){
//			Log.i("read data", Tools.Bytes2HexString(recv, recv.length));
            int len = recv.length;
            //瓚剿杅擂婦芛
            if(recv[0] != Constants.HEAD){
                return data;
            }
            byte check = checkSum(recv, len - 1);
            //苺桄睿
            if(check != recv[len - 1]){
                Log.i("read data ", "checksum error");
                return data;
            }
            //紱釬囮啖
            if(len == 6){
                Log.i("read data ", "read fail!!");
                return data;
            }
            //紱釬傖髡
            //傖髡紱釬腔梓ワ軞杅
            int tagCount = (recv[4]&(0xFF))*256 + (recv[5]&(0xFF));
            //衄虴杅擂酗僅
            int dataLen = recv[6]&(0xFF);
            //黍�△騫�擂酗僅
            int readLen = recv[len - 4];

            int epcLen = dataLen - readLen - 4;
            if(dataLen <= readLen || dataLen < epcLen || epcLen < 0){
                return data;
            }
            data = new byte[readLen + epcLen];
            System.arraycopy(recv, 9, data, 0, epcLen);
            System.arraycopy(recv, 11 + epcLen, data, epcLen, readLen);

        }else{
            Log.i("read data ", "硌鍔閉奀");
        }
        return data;
    }



    /**
     * 籵徹躇鎢黍杅擂
     * @param memBank
     * @param startAddr
     * @param length
     * @param password
     * @return
     */
    public byte[] readFrom6C(int memBank, int startAddr, int length, byte[] password) {
        byte[] data = null;
        byte[] cmd = new byte[12];
        cmd[0] = Constants.HEAD;
        cmd[1] = (byte) (0x0A);
        cmd[2] = Constants.ADDR;//address
        cmd[3] = Constants.CMD_6C_READ;
        cmd[4] = (byte) memBank;
        cmd[5] = (byte) startAddr;
        cmd[6] = (byte) length;
        if(password.length != 4 ){
            Log.e("readFrom6C", "password error");
            return null;
        }
//		System.arraycopy(cmd, 7, password, 0, 4);
        cmd[7] = password[0];
        cmd[8] = password[1];
        cmd[9] = password[2];
        cmd[10] = password[3];
        cmd[11] = checkSum(cmd, cmd.length - 1);
        //楷冞硌鍔
        sendToReader(cmd);
//		Log.i("READ DATA*****", Tools.Bytes2HexString(cmd, cmd.length));
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        byte[] recv = this.read();
        if(recv != null){
//			Log.i("read data", Tools.Bytes2HexString(recv, recv.length));
            int len = recv.length;
            //瓚剿杅擂婦芛
            if(recv[0] != Constants.HEAD){
                return data;
            }
            byte check = checkSum(recv, len - 1);
            //苺桄睿
            if(check != recv[len - 1]){
                Log.i("read data ", "checksum error");
                return data;
            }
            //紱釬囮啖
            if(len == 6){
                Log.i("read data ", "read fail!!");
                return data;
            }
            //紱釬傖髡
            //傖髡紱釬腔梓ワ軞杅
            int tagCount = (recv[4]&(0xFF))*256 + (recv[5]&(0xFF));
            //衄虴杅擂酗僅
            int dataLen = recv[6]&(0xFF);
            //黍�△騫�擂酗僅
            int readLen = recv[len - 4];

            int epcLen = dataLen - readLen - 4;
            if(dataLen <= readLen || dataLen < epcLen || epcLen < 0){
                return data;
            }
            if(recv.length < (epcLen+9)){
                return data;
            }
            data = new byte[readLen + epcLen];

            System.arraycopy(recv, 9, data, 0, epcLen);
            System.arraycopy(recv, 11 + epcLen, data, epcLen, readLen);

        }else{
            Log.i("read data ", "硌鍔閉奀");
        }
        return data;
    }

    /*
     * 迡杅擂
     * byte[] password 溼恀躇鎢
     * int memBank 杅擂⑹
     * int wordAdd れ宎華硊ㄗ眕WORD峈等弇ㄘ
     * int wordCnt 迡�輮�擂腔酗僅
     * byte[] data 迡�輮�擂
     * 殿隙 booleanㄛtrue迡�輮�擂淏�楠柏alse迡�輮�擂囮啖
     */
    public boolean writeTo6C(byte[] password, int memBank, int wordAdd, int dataLen, byte[] data) {
        boolean writeFlag = false;
        dataLen = dataLen/2;
        int cmdLen = 12 + dataLen*2;
        byte[] cmd = new byte[cmdLen];
        cmd[0] = Constants.HEAD;
        cmd[1] = (byte) (cmdLen - 2);
        cmd[2] = Constants.ADDR;
        cmd[3] = Constants.CMD_6C_WRITE;
        System.arraycopy(password, 0, cmd, 4, password.length);
        cmd[8] = (byte) memBank;
        cmd[9] = (byte) wordAdd;
        cmd[10] = (byte) dataLen;
        System.arraycopy(data, 0, cmd, 11, data.length);
        cmd[11 + data.length] = checkSum(cmd, 11 + data.length);
        sendToReader(cmd);
//		Log.i("write DATA*****", Tools.Bytes2HexString(cmd, cmd.length));
        try {
            Thread.sleep(150);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        byte[] recv = this.read();
        if(recv != null){
//			Log.i("write data======", Tools.Bytes2HexString(recv, recv.length));
            int recvLength = recv.length;
            //紱釬囮啖
            if(recvLength == 6){
                return writeFlag;
            }
            if(isRecvData(recv) == 0){
                writeFlag = true;
            }
        }else{
            Log.i("write data", "硌鍔閉奀");
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            this.read();
        }

        return writeFlag;
    }

    /**
     *
     * @param password
     * @param memBank
     * @param lockType
     * @return
     */
    public boolean lock6C(byte[] password, int memBank, int lockType){
        boolean lockFlag = false;
        byte [] cmd = new byte[11];
        cmd[0] = Constants.HEAD;
        cmd[1] = (byte) 0x09;
        cmd[2] = Constants.ADDR;
        cmd[3] = Constants.CMD_6C_LOCK;
        System.arraycopy(password, 0, cmd, 4, password.length);
        cmd[8] = (byte) memBank;
        cmd[9] = (byte) lockType;
        cmd[10] = checkSum(cmd, cmd.length - 1);
//		Log.i("SendCommendManager","Lock" + Tools.Bytes2HexString(cmd, cmd.length));
        //楷冞硌鍔
        sendToReader(cmd);
        //諉彶殿隙杅擂
        byte[] recv = this.read();
        if(recv != null){
//			Log.i("Lock..6c", Tools.Bytes2HexString(recv, recv.length));
            //紱釬囮啖
            if(recv.length == 6){
                return lockFlag;
            }
            if(isRecvData(recv) == 0){
                lockFlag = true;
            }
        }else{
            Log.i("Lock..6c", "閉奀");
        }
        return lockFlag;

    }
    /**
     *@return void
     *@Method Description:种障梓ワ
     *@Autor Jimmy
     *@Date 2014-1-18
     */
    public boolean kill6C(byte[]password){
        boolean killFlag = false;
        byte[] cmd = new byte[9];
        cmd[0] = Constants.HEAD;
        cmd[1] = (byte) 0x07;
        cmd[2] = (byte) 0xFF;
        cmd[3] = Constants.CMD_6C_KILL;
        System.arraycopy(password, 0, cmd, 4, password.length);
        cmd[8] = checkSum(cmd, cmd.length - 1);

//		Log.i("Kill ***", Tools.Bytes2HexString(cmd, cmd.length));
        byte[] recv = this.read();
        if(recv != null){
//			Log.i("Kill ***", Tools.Bytes2HexString(recv, recv.length));
            if(recv.length == 6){
                return killFlag;
            }
            if(isRecvData(recv) == 0){
                killFlag = true;
            }
        }else{
            Log.i("Kill ***", "閉奀");
        }
        return killFlag;
    }

    ///////////////////////////ISO18000-6B///////////////////////////////////////
    public void inventory6B(){
        byte[] cmd = {(byte) 0xA0, (byte) 0x03, (byte) 0x01, Constants.CMD_ISO18000_6B_INVENTORY, 0x00};

    }

    //諉彶遣湔⑹腔杅擂
    private  byte[] read(){
        int count = 0;
        int index = 0;
        int allCount = 0;
        byte [] resp = null;
        try {
            while(count < 3){
                count = this.in.available();
                if(index > 50){
                    return null;//閉奀
                }else{
                    index++;
                    try{
                        Thread.sleep(10);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
            Thread.sleep(50);
            allCount = this.in.available();
            resp = new byte[allCount];
            Log.i("read allCount****", allCount +"");
            int mcount = 0;
            while(count != 0){
                byte[] bytes = new byte[2];
                this.in.read(bytes);
                int length = bytes[1];
                count = 0;
//				  while(count < length){
                count = this.in.available();
                Log.i("read count", count +"");
//				   }
                byte[] data = new byte[length];

                int dataSize = this.in.read(data);
//				  Log.i("DATA SIZE", dataSize + "  data size  " + Tools.Bytes2HexString(data, dataSize));
                System.arraycopy(bytes, 0, resp, mcount, 2);
                System.arraycopy(data, 0, resp, mcount + 2, length);
                mcount = mcount + 2 + length;
                count  = this.in.available();
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return resp;
    }

    //楷冞硌鍔
    public boolean sendToReader(byte[] cmd){
        try {
            out.write(cmd);
            out.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        return false;
    }


    //數呾苺桄睿
    public  byte checkSum(byte[] uBuff, int uBuffLen){
        byte crc = 0;
        for(int i = 0; i < uBuffLen; i++){
            crc = (byte) (crc + uBuff[i]);
        }
        crc = (byte) (0xFF&((~crc)+1));
        return crc;
    }

    //瓚剿殿隙杅擂岆瘁睫磁衪祜
    /**
     *@return int殿隙硉峈0寀杅擂婦淏�楠�-1峈杅擂徹傻ㄛ-2婦芛祥淏�楠�-3杅擂婦酗僅祥淏�楠�-4苺桄鎢堤渣
     *@Method Description:
     *@Autor Jimmy
     *@Date 2014-1-11
     */
    public int isRecvData(byte[] recv){
        if(recv.length <5){
            return -1;//苤衾郔苤杅擂婦酗僅ㄛ渣昫
        }
        String data = Tools.Bytes2HexString(recv, recv.length);
        if(recv[0] != Constants.HEAD) return -2;//杅擂婦婦芛祥淏��
        int recvDataLen = Integer.parseInt(data.substring(2, 4), 16);
        if(recvDataLen != recv.length - 2) return -3;//杅擂婦酗僅祥淏��
        byte crc = checkSum(recv, recv.length - 1);
        if(crc != recv[recv.length - 1]) return -4;//苺桄鎢堤渣
        return 0;
    }
    @Override
    public void setSensitivity(int value) {
        // TODO Auto-generated method stub

    }
    @Override
    public void close() {
        // TODO Auto-generated method stub

    }
    @Override
    public byte checkSum(byte[] data) {
        // TODO Auto-generated method stub
        return 0;
    }



}