package com.simernes.phonemic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.AudioDevice;

public class SoundPlayer {

    private AudioDevice audioDevice;

    public SoundPlayer() {
        audioDevice = Gdx.audio.newAudioDevice(22050, false);
    }

    public void playSample(short[] sample) {
        audioDevice.writeSamples(sample, 0, sample.length);
    }
}
