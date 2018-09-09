package com.simernes.phonemic;

import java.net.DatagramPacket;

public interface OnDiscoveredHostListener {
    void onDiscoveredHost(DatagramPacket datagramPacket);
}
