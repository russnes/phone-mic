package com.simernes.phonemic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.AudioRecorder;

import java.util.Timer;
import java.util.TimerTask;

public class MicSampler {

    private AudioRecorder recorder;
    private short[] shortPCM;

//    private Timer sampleTimer;
//    private TimerTask sampleTimerTask;

    public MicSampler() {
        recorder = Gdx.audio.newAudioRecorder(22050, true);
        shortPCM = new short[1024]; // 1024 samples
//        sampleTimer = new Timer();
    }

    public void readSample() {
        recorder.read(shortPCM, 0, shortPCM.length);
    }

    public void dispose() {
        if(recorder != null) {
            recorder.dispose();
            recorder = null;
        }
    }

    public short[] getSample() {
        return shortPCM;
    }

}
