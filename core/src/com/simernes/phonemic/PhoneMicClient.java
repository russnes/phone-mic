package com.simernes.phonemic;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;
import java.net.DatagramPacket;

import static com.simernes.phonemic.PhoneMicServer.*;

public class PhoneMicClient extends Listener implements OnDiscoveredHostListener {

    private Client discoveryClient;
    private Client connectionClient;
    private boolean waitingForServer = true;
    private String serverAdress = null;
    int id = Integer.MIN_VALUE;
    private MicSampler micSampler;
    private SampleSender sampleSender;

    public PhoneMicClient() {
        micSampler = new MicSampler();
        sampleSender = new SampleSender(micSampler, this);
        new Thread() {
            @Override
            public void run() {
                try {
                    waitForConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void discoverMicrophoneServers() throws IOException {
        discoveryClient = new Client(TCPPORT_DISCOVERY, UDPPORT_DISCOVERY);
        discoveryClient.setDiscoveryHandler(new DiscoveryHandler(this));
    }

    @Override
    public void onDiscoveredHost(DatagramPacket datagramPacket) {
        if(waitingForServer) {
            waitingForServer = false;
            serverAdress = datagramPacket.getAddress().toString();
        }
    }

    public void waitForConnection() throws IOException {
        while (waitingForServer) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        while(serverAdress == null) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        connectionClient = new Client(WRITE_BUFFER_SIZE_SERVER, OBJECT_BUFFER_SIZE);
        Kryo kryo = connectionClient.getKryo();
        NetworkUtils.registerKryoClasses(kryo);
        connectionClient.connect(5000, serverAdress, TCPPORT_CONNECTION, UDPPORT_CONNECTION);
    }

    public void sendSample(short[] sample) {
        int id = this.id;
        this.id ++;
        Message message = new Message(id, sample);
        connectionClient.sendUDP(message);
    }

    @Override
    public void connected(Connection connection) {
        sampleSender.startSendingSamples();
    }

    @Override
    public void disconnected(Connection connection) {
        sampleSender.stopSendingSamples();
    }
}
