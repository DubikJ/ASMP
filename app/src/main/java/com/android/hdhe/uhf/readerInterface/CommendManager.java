package com.android.hdhe.uhf.readerInterface;

import java.util.List;

public interface CommendManager {

    /**
     * 扢离疏杻薹
     * @return
     */
    public boolean setBaudrate();

    /**
     * 鳳�±票�唳掛
     */
    public byte[] getFirmware() ;

    /**
     * 扢离怀�輮銙鶾汕�
     * @param value
     * @return
     */
    public boolean setOutputPower(int value);

    /**
     * 妗奀攫湔
     * @return
     */
    public List<byte[]> inventoryRealTime();

    /**
     * 恁隅梓ワ
     * @param epc
     */
    public void selectEPC(byte[] epc);

    /**
     * 黍杅擂
     * int memBank杅擂⑹
     * int startAddr杅擂⑹れ宎華硊ㄗ眕趼峈等弇ㄘ
     * int length猁黍�△騫�擂酗僅(眕趼峈等弇)
     * byte[] accessPassword 溼恀躇鎢
     * 殿隙腔byte[] 峈  EPC + 黍�△騫�擂
     */
    public byte[] readFrom6C(int memBank, int startAddr, int length, byte[] accessPassword);

    /**
     * 迡杅擂
     * byte[] password 溼恀躇鎢
     * int memBank 杅擂⑹
     * int startAddr れ宎華硊ㄗ眕WORD峈等弇ㄘ
     * int wordCnt 迡�輮�擂腔酗僅ㄗ眕WORD峈等弇 1word = 2bytesㄘ
     * byte[] data 迡�輮�擂
     * 殿隙 booleanㄛtrue迡�輮�擂淏�楠柏alse迡�輮�擂囮啖
     */
    public boolean writeTo6C(byte[] password, int memBank, int startAddr, int dataLen, byte[] data);

    /**
     * 扢离鍾鏗僅
     * @param value
     */
    public void setSensitivity(int value);

    /**
     * 坶隅梓ワ
     * @param password 溼恀躇鎢
     * @param memBank  杅擂⑹
     * @param lockType 坶隅濬倰
     * @return
     */
    public boolean lock6C(byte[] password, int memBank, int lockType);


    /**
     *
     */
    public void close();

    /**
     * 數呾苺桄睿
     * @param data
     * @return
     */
    public byte checkSum(byte[] data);


    /**
     *
     * @param startFrequency れ宎け萸
     * @param freqSpace け萸潔路
     * @param freqQuality け萸杅講
     * @return
     */
    public int setFrequency(int startFrequency, int freqSpace, int freqQuality);



}
