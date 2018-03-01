package com.android.hdhe.uhf.reader;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.avatlantik.asmp.R;

import java.util.HashMap;
import java.util.Map;

public class Util {


    public static SoundPool sp ;
    public static Map<Integer, Integer> suondMap;
    public static Context context;

    //場宎趙汒秞喀
    public static void initSoundPool(Context context){
        Util.context = context;
        sp = new SoundPool(1, AudioManager.STREAM_MUSIC, 1);
        suondMap = new HashMap<Integer, Integer>();
        suondMap.put(1, sp.load(context, R.raw.msg, 1));
    }

    //畦溫汒秞喀汒秞
    public static  void play(int sound, int number){
        AudioManager am = (AudioManager)Util.context.getSystemService(Util.context.AUDIO_SERVICE);
        //殿隙絞ヶAlarmManager郔湮秞講
        float audioMaxVolume = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        //殿隙絞ヶAudioManager勤砓腔秞講硉
        float audioCurrentVolume = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        float volumnRatio = audioCurrentVolume/audioMaxVolume;
        sp.play(
                suondMap.get(sound), //畦溫腔秞氈Id
                audioCurrentVolume, //酘汒耋秞講
                audioCurrentVolume, //衵汒耋秞講
                1, //蚥珂撰ㄛ0峈郔腴
                number, //悜遠棒杅ㄛ0拸祥悜遠ㄛ-1拸蚗堈悜遠
                1);//隙溫厒僅ㄛ硉婓0.5-2.0眳潔ㄛ1峈淏都厒僅
    }

}
