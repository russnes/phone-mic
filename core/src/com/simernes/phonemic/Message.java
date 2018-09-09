package com.simernes.phonemic;

public class Message {
    int msgId;
    short[] sample;

    public Message(int msgId, short[] sample) {
        this.msgId = msgId;
        this.sample = sample;
    }
}
