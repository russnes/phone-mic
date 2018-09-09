package com.simernes.phonemic;

import com.esotericsoftware.kryo.Kryo;

public class NetworkUtils {
    public static void registerKryoClasses(Kryo kryo) {
        kryo.register(short[].class);
        kryo.register(int.class);
        kryo.register(Message.class);
    }
}
