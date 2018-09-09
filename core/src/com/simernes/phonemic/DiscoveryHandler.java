package com.simernes.phonemic;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.ClientDiscoveryHandler;

import java.net.DatagramPacket;

public class DiscoveryHandler implements ClientDiscoveryHandler {

    private final OnDiscoveredHostListener onDiscoveredHostListener;

    public DiscoveryHandler(OnDiscoveredHostListener onDiscoveredHostListener) {
        this.onDiscoveredHostListener = onDiscoveredHostListener;
    }

    @Override
    public DatagramPacket onRequestNewDatagramPacket() {
        return new DatagramPacket(new byte[0], 0);
    }

    @Override
    public void onDiscoveredHost(DatagramPacket datagramPacket, Kryo kryo) {
        onDiscoveredHostListener.onDiscoveredHost(datagramPacket);
    }

    @Override
    public void onFinally() {

    }
}
