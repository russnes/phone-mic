package com.simernes.phonemic;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

public class PhoneMicServer extends Listener {

    public static final int TCPPORT_DISCOVERY = 37182;
    public static final int UDPPORT_DISCOVERY = 37172;

    public static final int TCPPORT_CONNECTION = 36182;
    public static final int UDPPORT_CONNECTION = 36172;

    public static final int WRITE_BUFFER_SIZE_SERVER = 1024 * 4;
    public static final int OBJECT_BUFFER_SIZE = 1024 * 4;

    private Server discoveryServer;
    private Server connectionServer;

    private SoundPlayer soundPlayer;

    Integer lastReceived = null;

    public PhoneMicServer(SoundPlayer soundPlayer) {
        this.soundPlayer = soundPlayer;
    }

    public void discoverReceivers() throws IOException {
        connectionServer = new Server(WRITE_BUFFER_SIZE_SERVER, OBJECT_BUFFER_SIZE);
//        PhoneMicServer.registerKryoClasses(server.getKryo());

        Kryo kryo = connectionServer.getKryo();
        NetworkUtils.registerKryoClasses(kryo);
        connectionServer.start();
        connectionServer.bind(TCPPORT_CONNECTION, UDPPORT_CONNECTION);
        connectionServer.addListener(this);

        discoveryServer = new Server();
        discoveryServer.start();
        discoveryServer.bind(PhoneMicServer.TCPPORT_DISCOVERY, PhoneMicServer.UDPPORT_DISCOVERY);
    }

    @Override
    public void connected(Connection connection) {

    }

    @Override
    public void disconnected(Connection connection) {

    }

    @Override
    public void received(Connection connection, Object object) {
        if(object instanceof Message) {
            Message message = (Message) object;
            if(lastReceived == null || message.msgId > lastReceived) {
                lastReceived = message.msgId;
                soundPlayer.playSample(message.sample);
            }
        }
    }
}
