package com.android.hdhe.uhf.Constants;

public class Constants {

    //婦芛
    public static final byte HEAD = (byte) (0xA0);
    //華硊
    public static final byte ADDR = (byte) (0x01);

    ///////////////////////炵苀扢离硌鍔///////////////////////////////
    //黍迡ん笭ゐ
    public static final byte CMD_RESET_READER = (byte) (0x70);
    //扢离疏杻薹
    public static final byte CMD_SET_BAUD = (byte) (0x71);
    //鳳�★抯�唳掛
    public static final byte CMD_GET_FIRMWARE_VERSION = (byte) (0x72);
    //扢离黍迡ん華硊
    public static final byte CMD_SET_NEW_ADDRESS = (byte) (0x73);
    //扢离馱釬毞盄
    public static final byte CMD_SET_WORK_ANTENNA = (byte) (0x74);
    //鳳�△掏偎尤齂嬥�
    public static final byte CMD_GET_WORK_ANTENNA = (byte) (0x75);
    //扢离怀堤髡薹
    public static final byte CMD_SET_OUTPUT_POWER = (byte) (0x76);
    //鳳�△掏動銙鶾汕�
    public static final byte CMD_GET_OUTPUT_POWER = (byte) (0x77);
    //扢离扞けけび
    public static final byte CMD_SET_FREQUENCY_REGION = (byte) (0x78);
    //鳳�﹎鞄腑腑�
    public static final byte CMD_GET_FREQUENCY_REGION = (byte) (0x79);
    //扢离瑚霪ん耀宒
    public static final byte CMD_SET_BEEPER_MODE = (byte) (0x7A);
    //鳳�▲蹈棘龕繞�
    public static final byte CMD_GET_READER_TEMPERATURE = (byte) (0x7B);
    //扢离DRM耀宒
    public static final byte CMD_SET_DRM_MODE = (byte) (0x7C);
    //鳳�．RM耀宒
    public static final byte CMD_GET_DRM_MODE = (byte) (0x7D);
    //黍GPIO硉
    public static final byte CMD_READ_GPIO_VALUE = (byte) (0x60);
    //迡GPIO硉
    public static final byte CMD_WRITE_GPIO_VALUE = (byte) (0x61);
    //扢离毞盄蟀諉潰聆
    public static final byte CMD_SET_ANT_CONNECTION_DETECTOR = (byte) (0x62);
    //鳳�﹟嬥萴狠�
    public static final byte CMD_GET_ANT_CONNECTION_DETECTOR = (byte) (0x63);
    //扢离還奀怀堤髡薹
    public static final byte CMD_SET_TEMPORARY_OUTPUT_POWER = (byte) (0x66);
    //扢离黍迡ん妎梗趼睫
    public static final byte CMD_SET_READER_IDENTIFIER = (byte) (0x67);
    //鳳�▲蹈棘鼳雇襐硊�
    public static final byte CMD_GET_READER_IDENTIFIER = (byte) (0x68);


    ////////////////////////18000-6C////////////////////////////////////////////
    //攫湔
    public static final byte CMD_6C_INVENTORY = (byte) (0x80);
    //黍硌鍔
    public static final byte CMD_6C_READ = (byte) (0x81);
    //迡硌鍔
    public static final byte CMD_6C_WRITE = (byte) (0x82);
    //坶隅梓ワ
    public static final byte CMD_6C_LOCK = (byte) (0x83);
    //鏢魂梓ワ
    public static final byte CMD_6C_KILL = (byte) (0x84);
    //扢离EPCぁ饜籵耋
    public static final byte CMD_6C_SET_ACCESS_EPC_MATCH = (byte) (0x85);
    //鳳�•PCぁ饜籵耋
    public static final byte CMD_6C_GET_ACCESS_EPC_MATCH = (byte) (0x86);
    //妗奀攫湔
    public static final byte CMD_6C_REAL_TIME_INVENTORY = (byte) (0x89);
    //辦厒毞盄攫湔
    public static final byte CMD_6C_FAST_SWITCH_ANT_INVENTORY = (byte) (0x8A);

    /////////////////////////ISO18000-6B////////////////////////////////////////
    //攫湔ISO18000-6B梓ワ
    public static final byte CMD_ISO18000_6B_INVENTORY = (byte) (0xB0);
    //黍ISO18000-6B梓ワ
    public static final byte CMD_ISO18000_6B_READ = (byte) (0xB1);
    //迡ISO18000-6B梓ワ
    public static final byte CMD_ISO18000_6B_WRITE = (byte) (0xB2);
    //坶隅ISO18000-6B梓ワ
    public static final byte CMD_ISO18000_6B_LOCK = (byte) (0xB3);
    //脤戙ISO18000-6B梓ワ
    public static final byte CMD_ISO18000_6B_QUERY_LOCK = (byte) (0xB3);


    //////////////////////////////遣湔紱釬硌鍔//////////////////////////////////////////
    //枑�§糒性�擂甜刉壺遣湔
    public static final byte CMD_GET_INVENTORY_BUFFER = (byte) (0x90);
    //枑�§糒性�擂悵隱遣湔掘爺
    public static final byte CMD_GET_AND_RESET_INVENTORY_BUFFER = (byte) (0x91);
    //脤戙遣湔笢眒黍梓ワ跺杅
    public static final byte CMD_GET_INVENTORY_BUFFER_TAG_COUNT = (byte) (0x92);
    //ь諾梓ワ杅擂遣湔
    public static final byte CMD_RESET_INVENTORY_BUFFER = (byte) (0x93);


    ////////////////////////////渣昫硌鍔桶//////////////////////////////////////////////////
    public static final byte CMD_SUCCESS = (byte) (0x10);//硌鍔傖髡俇傖
    public static final byte CMD_FAIL = (byte) (0x11);//硌鍔硒俴囮啖
    public static final byte MCU_RESET_ERROR = (byte) (0x20);//黍迡ん葩弇渣昫
    public static final byte CW_ON_ERROR = (byte) (0x21);//CW湖羲囮啖
    public static final byte ANTENNA_MISSING_ERROR = (byte) (0x22);//毞盄帤蟀諉
    public static final byte WRITE_FLASH_ERROR = (byte) (0x23);//迡FLASH渣昫
    public static final byte READ_FLASH_ERROR = (byte) (0x24);//黍FLASH渣昫
    public static final byte SET_OUTPUT_POWER_ERROR = (byte) (0x25);//扢离楷扞髡薹渣昫
    public static final byte TAG_INVENTORY_ERROR = (byte) (0x31);//梓ワ攫湔渣昫
    public static final byte TAG_READ_ERROR = (byte) (0x32);//梓ワ黍�●簊�
    public static final byte TAG_WRITE_ERROR = (byte) (0x33);//迡梓ワ渣昫
    public static final byte TAG_LOCK_ERROR = (byte) (0x34);//坶隅梓ワ渣昫
    public static final byte TAG_KILL_ERROR = (byte) (0x35);//鏢魂梓ワ渣昫
    public static final byte NO_TAG_ERROR = (byte) (0x36);//拸褫紱釬梓ワ渣昫
    public static final byte INVENTORY_OK_BUT_ACCESS_FAIL = (byte) (0x37);//傖髡攫湔筍溼恀囮啖
    public static final byte BUFFER_IS_EMPTY_ERROR = (byte) (0x38);//遣喳峈諾渣昫
    public static final byte ACCESS_OR_PASSWORD_ERROR = (byte) (0x40);//溼恀梓ワ渣昫麼躇鎢渣昫
    public static final byte PARAMETER_INVAILID = (byte) (0x41);//拸虴統杅
    public static final byte PARAMETER_INVAILED_WORDCNT_TOO_LONG = (byte) (0x42);//WORDcnt酗僅
    public static final byte PARAMETER_INVAILED_MEMBANK_OUT_OF_RANGE = (byte) (0x43);//MEMBANK統杅閉堤毓峓
    public static final byte PARAMETER_INVAILED_LOCK_REGION_OUT_OF_RANGE = (byte) (0x36);//lock杅擂⑹統杅閉堤毓峓
    public static final byte PARAMETER_INVAILED_LOCK_ACTION_OUT_OF_RANGE = (byte) (0x36);//lock TYPE統杅閉堤毓峓
    public static final byte PARAMETER_READER_ADDRESS_INVAILED = (byte) (0x36);//拸褫紱釬梓ワ渣昫
    public static final byte PARAMETER_INVAILED_ATNNEN_ID_OUT_OF_RANGE = (byte) (0x36);//ATNNEN ID 閉堤毓峓
    public static final byte PARAMETER_INVAILED_OUTPUT_POWER_OUT_OF_RANGE = (byte) (0x36);//怀堤髡薹閉堤毓峓
    public static final byte PARAMETER_INVAILED_FREQUENCY_REGION_OUT_OF_RANGE = (byte) (0x36);//扞け寞毓⑹統杅閉堤毓峓


}