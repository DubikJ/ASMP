package com.android.hdhe.uhf.readerInterface;

public interface GetCommendInterface {
    //扢离疏杻薹
    public abstract void setBaudrate();
    //嘐璃脤戙
    public abstract void getFirmware();
    ///////////////////ISO18000-6C////////////////////////////
    //扢离馱釬毞盄
    public abstract void setWorkAntenna();
    //妗奀攫湔耀宒
    public abstract void inventoryRealTime();
    //恁寁梓ワ
    public abstract void selectEPC(byte[] epc);
    //黍杅擂
    /**
     *@Method Description:int menBank梓ワ湔揣⑹郖ㄩ0峈RESERVED⑹ㄛ1峈EPC⑹ㄛ2峈TID⑹ㄛ3峈USER⑹˙
     *int startAddrㄩ岆黍�﹋�擂れ宎華硊˙
     *int lengthㄩ岆黍�﹋�擂酗僅
     *@Autor Jimmy
     *@Date 2014-1-13
     */
    public abstract void readFrom6C(int menBank, int startAddr, int length);
    //迡杅擂
    /**
     *@return void
     *@Method Description:byte[] passwordㄛ4Bytes躇鎢
     *int wordAdd 杅擂忑華硊
     *int wordCnt 迡�賮儷硈介�ㄗ1趼=2Bytesㄘ
     *byte[] data 迡�賮騫�擂ㄛ酗僅峈wordCnt*2 Bytes
     *@Autor Jimmy
     *@Date 2014-1-15
     */
    public abstract void writeTo6C(byte[] password, int wordAdd, int wordCnt, byte[] data);


    //數呾苺桄睿
//	public abstract static byte checkSum(byte[] uBuff, int uBuffLen);

}