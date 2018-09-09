package com.simernes.phonemic;

import java.util.Timer;
import java.util.TimerTask;

public class SampleSender {
    Timer sendTimer;
    TimerTask sendSampleTask;
    private MicSampler micSampler;
    private PhoneMicClient phoneMicClient;

    public SampleSender(MicSampler micSampler, PhoneMicClient phoneMicClient) {
        this.micSampler = micSampler;
        this.phoneMicClient = phoneMicClient;
        sendTimer = new Timer();
    }

    public void startSendingSamples() {
        sendSampleTask = new TimerTask() {
            @Override
            public void run() {
                micSampler.readSample();
                short[] sample = micSampler.getSample();
                phoneMicClient.sendSample(sample);
            }
        };

        sendTimer.scheduleAtFixedRate(sendSampleTask, 25, 25);
    }

    public void stopSendingSamples() {
        sendSampleTask.cancel();
        sendTimer.purge();
    }
}
